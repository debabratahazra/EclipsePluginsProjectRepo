package com.odcgroup.pageflow.editor.tests.setup;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.DiagramEditingDomainFactory;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.OffscreenEditPartFactory;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.TabContents;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditor;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.tap.validation.ValidationUtil;

/**
 * @author pkk
 *
 */
public abstract class AbstractPageflowEditorTests {
	
	private IProject project = null;

	private IDiagramWorkbenchPart diagramWorkbenchPart = null;
	private IFile diagramFile = null;
    private TransactionalEditingDomain editingDomain;
    private Resource resource;
	
	private Diagram diagram;
	private DiagramEditPart diagramEditPart;
	
	private Edge connectorView = null;  
	// connector view for the test fixture

    /**
     * Temporary shell to be used when creating the diagram editpart.
     */
    private Shell tempShell;

    /**
	 * Setup up the data for a test.  It will create the project,diagram and then opent the diagram
	 * and then create the test shapes and connectors on the diagram for the tests.
	 */
    @Before
	public void setUp() throws Exception {
    	IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        Assert.assertNotNull(workbenchWindow);
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        Assert.assertNotNull(workbenchPage);

        workbenchPage.closeAllPerspectives(false, false);
        PlatformUI.getWorkbench().showPerspective(
                "com.odcgroup.workbench.ui.perspectives.ofs", workbenchWindow);
        
		project = createProject();

		createDiagram();
		createDiagramEditPart();
		
		flushEventQueue(); // so that all editor related events are fired
	}
    
    @After
    public void tearDown() throws Exception { 
		saveDiagram();
		closeDiagram();
    	project.delete(true, null);	
    }

	/**
     * 
     */
    protected void saveDiagram() {
        if (getDiagramWorkbenchPart() instanceof IEditorPart) {
            IWorkbenchPage page = getDiagramWorkbenchPart().getSite().getPage();
            page.saveEditor((IEditorPart) getDiagramWorkbenchPart(), false);
            flushEventQueue();
        }
    }

    /**
     * @throws Exception
     */
    protected void openDiagram() throws Exception {
    	 if (getDiagramFile() == null) {
            createDiagram();
        }

        Assert.assertTrue("creation of diagram failed", getDiagramFile() != null); //$NON-NLS-1$
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        setDiagramWorkbenchPart((IDiagramWorkbenchPart) page.openEditor(
                new FileEditorInput(getDiagramFile()), PageflowDiagramEditor.ID));
        setDiagramEditPart(getDiagramWorkbenchPart().getDiagramEditPart());
        setDiagram(getDiagramEditPart().getDiagramView());
        setResource(getDiagram().eResource());
    }
    
    /**
     * @return
     */
    public boolean closeDiagram() {
		boolean closed = true;
		if (diagramWorkbenchPart != null // only close if it was opened
			&& diagramWorkbenchPart instanceof IEditorPart) {
			IWorkbenchPage page =
			getDiagramWorkbenchPart().getSite().getPage();
			closed = page.closeEditor((IEditorPart)getDiagramWorkbenchPart(), false);
			setDiagramWorkbenchPart(null);
		}
		return closed;
	}
    
    /**
     * @return
     * @throws Exception
     */
    protected TabContents getCurrentTab() throws Exception {
    	IViewPart view = getWorkbenchPage().showView("org.eclipse.ui.views.PropertySheet");
    	Assert.assertNotNull(view);
        TabbedPropertySheetPage propertySheetPage = null;
        TabContents currTab = null;
        if (view instanceof PropertySheet) {
            PropertySheet ps = (PropertySheet) view;
            propertySheetPage = (TabbedPropertySheetPage) ps.getCurrentPage();
            currTab = propertySheetPage.getCurrentTab();
        }
        Assert.assertNotNull(currTab);
        return currTab;
    }
    
    /**
     * @param editPart
     * @return
     */
    protected String[] validateModel(EditPart editPart) {
    	EObject eObject = null;
    	Object model = editPart.getModel();
    	if (model instanceof Node) {
    		eObject = ((Node) model).getElement();
    	} else if (model instanceof Edge) {
    		eObject = ((Edge) model).getElement();
    	} else if (model instanceof Diagram) {
    		eObject = ((Diagram) model).getElement();
    	}
		String[] msgs = new String[2];
		StringBuffer errorMsg = new StringBuffer("| ");
		StringBuffer warnMsg = new StringBuffer("| ");

		IBatchValidator validator = ValidationUtil.getInstance().getBatchValidator();
				
		IStatus status = validator.validate(eObject);
		
		if(!status.isOK()) {
			if(!status.isMultiStatus()) {
				if (status.getSeverity() == IStatus.ERROR){
					errorMsg = new StringBuffer(status.getMessage());
				} else if (status.getSeverity() == IStatus.WARNING){
					warnMsg = new StringBuffer(status.getMessage());
				}
			} else {
				for(IStatus childStatus : status.getChildren()) {
					if (childStatus.getSeverity() == IStatus.ERROR){
						errorMsg.append(childStatus.getMessage()+" | ");
					} else if (childStatus.getSeverity() == IStatus.WARNING){
						warnMsg.append(childStatus.getMessage()+" | ");
					}
				}
			}
		}	
		msgs[0] = errorMsg.toString();
		msgs[1] = warnMsg.toString();
		
		return msgs;
	}

	/**
	 * Creates and sets the diagram editpart using the offscreen rendering
	 * capabilities.
	 */
	protected void createDiagramEditPart()
		throws Exception
	{
		if (getDiagramEditPart() == null) {
			setDiagramEditPart(OffscreenEditPartFactory.getInstance()
				.createDiagramEditPart(getDiagram(), getTempShell()));
		}
	}
    
    /**
     * Lazily creates a new shell.
     * @return
     */
    private Shell getTempShell() {
        if (tempShell == null) {
            tempShell = new Shell();
        }
        return tempShell;
    }

	
	/**
	 * Returns the diagramFile.
	 * @return IFile
	 */
	public IFile getDiagramFile() {
		return diagramFile;
	}

    /** Executes the supplied command. */
    @SuppressWarnings("rawtypes")
	protected Collection execute(ICommand cmd) {
        ICommandProxy command = new ICommandProxy(cmd);
        execute(command);
        return DiagramCommandStack.getReturnValues(command);
    }

    /** Executes the supplied command. */
    protected void execute(Command cmd) {
        getCommandStack().execute(cmd);
    }
	
	/**
	 * Returns the editor.
	 * @return IDiagramWorkbenchPart
	 */
	public IDiagramWorkbenchPart getDiagramWorkbenchPart() {
		if (diagramWorkbenchPart == null) {
			Assert.assertTrue(
				"It appears that the diagram needs to be opened for this test.  Call openDiagram().", false); //$NON-NLS-1$
		}
		return diagramWorkbenchPart;
	}

	/**
	 * @return
	 */
	public IWorkbenchPage getWorkbenchPage() {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		Assert.assertNotNull(workbenchWindow);
        return workbenchWindow.getActivePage();
	}
	
	/**
	 * @return
	 */
	public IProject getProject(String projectName){
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot wsroot = workspace.getRoot();
        return wsroot.getProject(projectName);
	}

	/**
	 * Sets the diagramFile.
	 * @param diagramFile The diagramFile to set
	 */
	protected void setDiagramFile(IFile diagramFile) {
		this.diagramFile = diagramFile;
	}

	/**
	 * Method getCommandStack.
	 * @return CommandStack  Command stack for the diagram edit domain
	 */
	public CommandStack getCommandStack() {
		return getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack();
	}
	/**
	 * Sets the diagramWorkbenchPart.
	 * @param diagramWorkbenchPart The editorPart to set
	 */
	public void setDiagramWorkbenchPart(IDiagramWorkbenchPart diagramWorkbenchPart) {
		this.diagramWorkbenchPart = diagramWorkbenchPart;
	}
	
	/**
	 * Returns the connectorView.
	 * Maybe null if there is no connector view for this test
	 * @return IConnectorView
	 */
	public Edge getConnectorView() {
		// maybe null if there is no connector view for this test
		return connectorView;
	}

	/**
	 * Sets the connectorView.
	 * @param connectorView The connectorView to set
	 */
	protected void setConnectorView(Edge connectorView) {
		this.connectorView = connectorView;
	}

	/** Clears the diaplay's event queue. */
	public void flushEventQueue() {
		final Display display = Display.getDefault();
		while (display != null && display.readAndDispatch()) {
			
		}
	}
	
	/**
	 * @return
	 */
	protected boolean isDirty() {
		return ((EditorPart) getDiagramWorkbenchPart()).isDirty();
	}
	
	/**
	 * @param diagram The diagram to set.
	 */
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}
	
	/**
	 * @param diagramEditPart The diagramEditPart to set.
	 */
	public void setDiagramEditPart(DiagramEditPart diagramEditPart) {
		this.diagramEditPart = diagramEditPart;
	}

	/**
	 * @return
	 */
	public Diagram getDiagram() {
		return diagram;
	}

	/**
	 * @return
	 */
	public DiagramEditPart getDiagramEditPart() {
		return diagramEditPart;
	} 
	
	/**
	 * @return
	 */
	public TransactionalEditingDomain getEditingDomain() {
    	if (editingDomain == null) {
            if (getDiagram() != null) {
                editingDomain = TransactionUtil.getEditingDomain(getDiagram());
            } else {
                editingDomain = DiagramEditingDomainFactory.getInstance()
                    .createEditingDomain();
            }
        }
        return editingDomain;
    }
    
    /**
     * @return
     */
    protected Resource getResource() {
        return resource;
    }
    
    /**
     * @param resource
     */
    protected void setResource(Resource resource) {
        this.resource = resource;
    }
    
    /**
     * @param elementType
     * @param location
     * @param containerEP
     * @return
     */
    public ShapeEditPart createShapeUsingTool(IElementType elementType,
            Point location, IGraphicalEditPart containerEP) {
        return createShapeUsingTool(elementType, location, null, containerEP);
    }
    
	/**
	 * Creates a new shape using the request created by the
	 * <code>CreationTool</code>.
	 * 
	 * @param elementType
	 *            the type of the shape/element to be created
	 * @param location
	 *            the location for the new shape
	 * @return the new shape's editpart
	 */
	@SuppressWarnings("rawtypes")
	public ShapeEditPart createShapeUsingTool(IElementType elementType,
			Point location, Dimension size, IGraphicalEditPart containerEP) {

		CreateRequest request = getCreationRequest(elementType);
		request.setLocation(location);
		if (size != null) {
		    request.setSize(size);
		}
		Command cmd = containerEP.getCommand(request);

		int previousNumChildren = containerEP.getChildren().size();

		getCommandStack().execute(cmd);
		Assert.assertEquals(previousNumChildren + 1, containerEP.getChildren().size());

		Object newView = ((IAdaptable) ((List) request.getNewObject()).get(0)).getAdapter(View.class);
		Assert.assertNotNull(newView);
		Assert.assertTrue(!ViewUtil.isTransient((View)newView));
		
		EObject element = ((View)newView).getElement();
		
		getCommandStack().undo();
		Assert.assertEquals(previousNumChildren, containerEP.getChildren().size());

		getCommandStack().redo();
		Assert.assertEquals(previousNumChildren + 1, containerEP.getChildren().size());

		IGraphicalEditPart newShape = null;
		if (element != null) {
			List children = containerEP.getChildren();
			ListIterator li = children.listIterator();
			while (li.hasNext()) {
				IGraphicalEditPart gep = (IGraphicalEditPart)li.next();
				if (gep.getNotationView().getElement().equals(element)) {
					newShape = gep;
				}
			}
		}
		else {
			newShape = (ShapeEditPart) getDiagramEditPart()
			.getViewer().getEditPartRegistry().get(newView);
			Assert.assertNotNull(newShape);
		}
		
		Assert.assertTrue(newShape != null && newShape instanceof ShapeEditPart);
		return (ShapeEditPart)newShape;
	}

	/**
	 * Given an <code>IElementType</code>, gets the creation request that can be used to 
	 * retrieve the command to creation the element for the type.
	 * 
	 * @param elementType
	 * @return
	 */
	public CreateRequest getCreationRequest(IElementType elementType) {
		class CreationTool
			extends org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool {

			public CreationTool(IElementType theElementType) {
				super(theElementType);
			}

			/** Make public. */
			public Request createTargetRequest() {
				return super.createTargetRequest();
			}
			
			protected PreferencesHint getPreferencesHint() {
				return PageflowDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
			}
		}

		CreationTool tool = new CreationTool(elementType);
		CreateRequest request = (CreateRequest) tool.createTargetRequest();
		return request;
	}

	/**
	 * Creates a new shape using the request created by the
	 * <code>CreationTool</code>.
	 * 
	 * @param elementType
	 *            the type of the shape/element to be created
	 * @param location
	 *            the location for the new shape
	 * @return the new shape's editpart
	 */
	public ShapeEditPart createShapeUsingTool(IElementType elementType,
			Point location) {

		return createShapeUsingTool(elementType, location, getDiagramEditPart());

	}
	
	/**
	 * Creates a new connector using the request created by the
	 * <code>ConnectionCreationTool</code>.
	 * 
	 * @param sourceEditPart
	 *            the new connector's source
	 * @param targetEditPart
	 *            the new connector's target
	 * @param elementType
	 *            the type of the connector/relationship to be created
	 * @return the new connector's editpart
	 */
	public ConnectionEditPart createConnectorUsingTool(
			final IGraphicalEditPart sourceEditPart,
			final IGraphicalEditPart targetEditPart, IElementType elementType) {

		class ConnectorCreationTool
			extends
			org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool {

			public ConnectorCreationTool(IElementType theElementType) {
				super(theElementType);
			}

			/** Make public. */
			public Request createTargetRequest() {
				return super.createTargetRequest();
			}
			
			protected PreferencesHint getPreferencesHint() {
				return PageflowDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
			}
		}

		ConnectorCreationTool tool = new ConnectorCreationTool(elementType);
		CreateConnectionRequest request = (CreateConnectionRequest) tool
			.createTargetRequest();
		request.setTargetEditPart(sourceEditPart);
		request.setType(RequestConstants.REQ_CONNECTION_START);
		sourceEditPart.getCommand(request);
		request.setSourceEditPart(sourceEditPart);
		request.setTargetEditPart(targetEditPart);
		request.setType(RequestConstants.REQ_CONNECTION_END);
		Command cmd = targetEditPart.getCommand(request);

		int previousNumConnectors = getDiagramEditPart().getConnections().size();

		getCommandStack().execute(cmd);
		Assert.assertEquals(previousNumConnectors + 1, getDiagramEditPart()
			.getConnections().size());
		getCommandStack().undo();
		Assert.assertEquals(previousNumConnectors, getDiagramEditPart()
			.getConnections().size());
		getCommandStack().redo();
		Assert.assertEquals(previousNumConnectors + 1, getDiagramEditPart()
			.getConnections().size());

		Object newView = ((IAdaptable) request.getNewObject())
			.getAdapter(View.class);
		Assert.assertNotNull(newView);

		ConnectionEditPart newConnector = (ConnectionEditPart) getDiagramEditPart()
			.getViewer().getEditPartRegistry().get(newView);
		Assert.assertNotNull(newConnector);

		return newConnector;
	}	
	
	
	/**
	 * @param eObject
	 * @param feature
	 * @param val
	 */
	protected void setProperty(EObject eObject, EStructuralFeature feature, Object val){
		final EObject obj = eObject;
		final EStructuralFeature ftr = feature;
		final Object value = val;
		TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();							
		domain.getCommandStack().execute(
				new RecordingCommand(domain) {
					protected void doExecute() {														
						obj.eSet(ftr, value);
					}
				});
	}
    
    /*______________________________________________________ abstract methods */
	
	/**
	 * @throws Exception
	 */
	protected abstract IProject createProject() throws Exception;
	
	/**
	 * Implement to create the diagram and the diagram file for which
	 * the test should run under.  Please set the diagramFile variable.
	 */
	protected abstract void createDiagram() throws Exception;

}
