package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.Request;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.TabContents;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.util.ModelUtils;
import com.odcgroup.page.ui.WidgetCreationFactory;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.wizard.ModelSpecification;
import com.odcgroup.page.ui.wizard.ModelType;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 *
 * @author pkk
 *
 */
public class BasePageDesignerTest extends AbstractDSUIUnitTest {
	
	private IEditorPart editorPart = null;
	private WidgetEditPart moduleEditPart = null;
	private RootEditPart rootEditPart = null;

	private IOfsProject ofsProject = null;
	
	private static final String PAGEMODELS_PROJECT = "pagetest-models";

	@Before
	public void setUp() throws Exception {
    	ofsProject = createNamedModelsProject(PAGEMODELS_PROJECT);
        closeWelcomeScreen();
    }
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
    }
	
	/**
	 * @param location
	 * @param name
	 */
	protected IFile createModuleFile(String location, String name) {
		return createPageModelFile(location, name, ModelType.MODULE);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest#getOfsProject()
	 */
	@Override
	public IOfsProject getOfsProject() {
        return ofsProject;
	}
	
	protected void openModuleEditor(IFile targetFile) {
		IEditorPart ep = openDefaultEditor(targetFile);
		setEditorPart(ep);
		if (ep instanceof MultiPageEditorPart) {
			MultiPageEditorPart mep = (MultiPageEditorPart) ep;
			DesignEditor editor = (DesignEditor) mep.getSelectedPage();
			RootEditPart rep = editor.getViewer().getRootEditPart();
			setRootEditPart(rep);
			List<?> list = rep.getChildren();
			if (!list.isEmpty()) {
				setModuleEditPart((WidgetEditPart) list.get(0));
			}
		}
	}

	/**
	 * @param widgetType
	 */
	protected WidgetEditPart createWidget(String templateType, WidgetEditPart container) {
		
		List<?> children = container.getChildren();
		int size = children.size();
		WidgetTemplate template = getWidgetTemplate(templateType);
		WidgetCreationFactory factory = new WidgetCreationFactory(template);
		
		WidgetCreationTool creationTool = new WidgetCreationTool(factory);
		CreateRequest request = (CreateRequest)creationTool.createTargetRequest();
		Rectangle containerBounds = container.getFigure().getBounds();
		Point location = containerBounds.getLocation();
		location.x +=10;
		location.y +=10;
		request.setLocation(location);
		Command cmd = container.getCommand(request);
		container.getCommandStack().execute(cmd);
		saveDiagram();		
		Assert.assertEquals(size+1, container.getChildren().size());
		
		Object element = request.getNewObject();
		for (Object object : container.getChildren()) {
			if (object instanceof WidgetEditPart) {
				WidgetEditPart wep = (WidgetEditPart) object;
				if(wep.getWidget().equals(element)) {
					return wep;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @param location
	 * @param name
	 * @param type
	 */
	private IFile createPageModelFile(String location, String name, ModelType type) {
		IOfsProject ofsProject = getOfsProject();
		IFile targetFile =  null;
		try {
			mkdirs(ofsProject.getProject().getFolder(location));
		} catch (CoreException e) {
			throw new RuntimeException("Unable to create the folders", e);
		}
		targetFile = ofsProject.getProject().getFile(location+name+"."+type.getTypeName());
		if (targetFile != null) {
	        try {
				targetFile.create(null, true, null);
			} catch (Exception e) {
				throw new RuntimeException("Unable to create the test project because " +
						"a project occurs during the copy of the " +
						"resource to the " + ofsProject + " models project", e);
			}
		
			ModelSpecification spec = new ModelSpecification(ModelType.MODULE, targetFile.getParent().getFullPath());
			Model model = spec.createModel();
			ModelUtils.saveModel(model, targetFile);
		}	
		
		return targetFile;		
	}

	/**
	 * @return the editorPart
	 */
	public IEditorPart getEditorPart() {
		return editorPart;
	}

	/**
	 * @param editorPart the editorPart to set
	 */
	public void setEditorPart(IEditorPart editorPart) {
		this.editorPart = editorPart;
	}

	/**
	 * @return the ModuleEditPart
	 */
	public WidgetEditPart getModuleEditPart() {
		return moduleEditPart;
	}

	/**
	 * @param moduleEditPart the moduleEditPart to set
	 */
	public void setModuleEditPart(WidgetEditPart moduleEditPart) {
		this.moduleEditPart = moduleEditPart;
	}

	/**
	 * @param widgetType
	 * @return
	 */
	protected WidgetTemplate getWidgetTemplate(String widgetType) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetTemplate template = library.findWidgetTemplate(widgetType);
		return template;
	}
	
	/**
     * 
     */
    protected void saveDiagram() {
        if (getEditorPart() instanceof IEditorPart) {
            IWorkbenchPage page = getEditorPart().getSite().getPage();
            page.saveEditor(getEditorPart(), false);
        }
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
	 * @return the rootEditPart
	 */
	public RootEditPart getRootEditPart() {
		return rootEditPart;
	}

	/**
	 * @param rootEditPart the rootEditPart to set
	 */
	public void setRootEditPart(RootEditPart rootEditPart) {
		this.rootEditPart = rootEditPart;
	}
	
	/**
	 *
	 */
	class WidgetCreationTool extends CreationTool {
		public WidgetCreationTool(CreationFactory factory) {
			super(factory);
		}

		/** Make public. */
		public Request createTargetRequest() {
			return super.createTargetRequest();
		}
	}


}
