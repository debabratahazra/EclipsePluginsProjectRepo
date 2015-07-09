package com.odcgroup.page.ui.editor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.page.domain.ui.outline.DomainOutlineView;
import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.external.mdf.MdfElementTransferDropListener;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.impl.ModelImpl;
import com.odcgroup.page.ui.PageUIConstants;
import com.odcgroup.page.ui.PageUIContextMenuProvider;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.action.CopyWidgetAction;
import com.odcgroup.page.ui.action.CutWidgetAction;
import com.odcgroup.page.ui.action.PageActionRegistry;
import com.odcgroup.page.ui.action.PasteWidgetAction;
import com.odcgroup.page.ui.dnd.PageElementTransferDropListener;
import com.odcgroup.page.ui.dnd.WidgetTemplateTransferDropTargetListener;
import com.odcgroup.page.ui.edit.DesignEditPartFactory;
import com.odcgroup.page.ui.palette.PaletteRootFactory;
import com.odcgroup.page.ui.properties.TabbedWidgetPropertySheetPage;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.Palette;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.util.UIModelRegistry;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.ModelEditorInput;
import com.odcgroup.workbench.ui.OfsUIResourceHelper;

/**
 * The design editor for the PageDesigner.
 * 
 * @author Gary Hayes
 */
public class DesignEditor extends GraphicalEditorWithFlyoutPalette implements ITabbedPropertySheetPageContributor {

	/** Provides a View of the MDF model element */
    private DomainOutlineView domainOutlinePage;
    
    /**
     * The PaletteRoot. We keep a reference to it in order to ensure that we only create it once.
     */
    private PaletteRoot paletteRoot;

    /** The Model is the root EMF object which is saved and loaded. */
    private Model model;
    
    /** The Widget is the root object which is displayed on the screen. */
    private Widget rootWidget;

    /** The editor page listener */
    private PageListener pageListener;

    /** The editor part listener */
    private PartListener partListener;

    /** The window listener */
    private IWindowListener windowListener;

    /** The page name */
    private String pageName;

    /** Flag indicating that an Exception occurred when saving a resource. */
    private Exception exception;
    
    /** Flag indicating the model is readonly */
    private boolean readonly = false;

    /**
     * Resources that have been changed since last activation.
     */
    protected Collection<Resource> changedResources = new ArrayList<Resource>();

    /**
     * Resources that have been saved.
     */
    protected boolean resourceSaved = false;
    
    /**
     * If a file is deleted from the navigator we need to close the associated editor. It not errors occurs since the
     * file no longer exists. This resource change listener listens for changes and closes the design editor.
     */
    private IResourceChangeListener resourceChangeListener = new IResourceChangeListener() {
        public void resourceChanged(IResourceChangeEvent event) {
            {
                IResourceDelta delta = event.getDelta();
                try {
                    final ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
                    delta.accept(visitor);                    
                    if (!visitor.getChangedResources().isEmpty()) { 
                        changedResources.addAll(visitor.getChangedResources()); 
                        getSite().getShell().getDisplay().asyncExec  
                        (new Runnable() {  
                             public void run() {   
                            	 handleActivate();
                             }  
                         });  
                    }  
                } catch (CoreException exception) {
                    Logger.error("Exception", exception);
                }
            }

        }
    };
    
    /**
     * 
     */
    private void handleChangedResources() {
        if (!changedResources.isEmpty()
                && (!isDirty() || handleDirtyConflict())) {
    		Resource mresource = model.eResource();
        	String msg = "The resource \'"+mresource.getURI().toString()+" has been changed " +
 				"on the file system. Do you want to replace the editor contents with these changes?";
            if(MessageDialog.openConfirm(getSite().getShell(), "Resource Changed", msg)) { 
            	for (Resource resource :changedResources) {
	                if (resource.isLoaded()) {
	       	 			resource.unload();
	     				try {
	       					resource.load(Collections.EMPTY_MAP);
	       					model = (Model)resource.getContents().get(0);
	       					getViewer().setContents(model.getWidget());
	       				} catch (IOException e) {
	       	    			handleError("Unable to load resource ["+resource.getURI()+"]");
	       				}
	       	 		}
            	}
            }        	
        }
    }
    
    
    
    /**
     * @param visitor
     */
    protected void handleActivate() {
    	if (!changedResources.isEmpty() && !resourceSaved) {
            handleChangedResources();
            changedResources.clear();
        }
    	resourceSaved = false;
    }

    /**
     * Shows a dialog that asks if conflicting changes should be discarded.
     */
    protected boolean handleDirtyConflict() {
        return MessageDialog.openQuestion(getSite().getShell(),
                "File Conflict", "There are unsaved changes that conflict "
                        + "with changes made outside the editor.  "
                        + "Do you wish to discard this editor's changes?");
    }

    /**
     * @return True if the model is read only
     */
    private final boolean isReadonly() {
    	return readonly;
    }

    /**
     * Creates a new PageUIEditor.
     */
    public DesignEditor() {
        setEditDomain(new DefaultEditDomain(this));
        setActionRegistry(new PageActionRegistry(this));

        // This really should be in the init method. However an Exception
        // occurs in this case. Since we always pass by init and then
        // dispose this should not produce a memory-leak.
        pageListener = new PageListener();
        partListener = new PartListener();
        windowListener = new WindowListener();
        IWorkbench workbench = PlatformUI.getWorkbench();
        workbench.addWindowListener(windowListener);
        IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
        for (int i = 0; i < windows.length; i++) {
            IWorkbenchWindow iWorkbenchWindow = windows[i];
            iWorkbenchWindow.addPageListener(pageListener);
            IWorkbenchPage[] pages = iWorkbenchWindow.getPages();

            for (int j = 0; j < pages.length; j++) {
                IWorkbenchPage page = pages[j];
                page.addPartListener(partListener);
            }
        }
    }

	/** 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPalettePreferences()
	 */
	protected FlyoutPreferences getPalettePreferences() {
		return new FlyoutPreferences(){

			public int getDockLocation() {
				return 0;
			}

			public int getPaletteState() {
				return FlyoutPaletteComposite.STATE_PINNED_OPEN;
			}

			public int getPaletteWidth() {
				return 120;
			}

			public void setDockLocation(int location) {
				
			}

			public void setPaletteState(int state) {
				
			}

			public void setPaletteWidth(int width) {
				
			}
			
		};
	}    

    /**
     * Initialize the Design Editor.
     * 
     * @param site The site
     * @param input The input
     * @throws PartInitException
     */
    public void init(IEditorSite site, IEditorInput input) throws PartInitException {
        super.init(site, input);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener,
                IResourceChangeEvent.POST_CHANGE);
    }

    /**
     * Dispose the current Design Editor. Take care of previously initialized listener.
     */
    public void dispose() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
        IWorkbench workbench = PlatformUI.getWorkbench();
        workbench.removeWindowListener(windowListener);
        IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
        for (int i = 0; i < windows.length; i++) {
            IWorkbenchWindow iWorkbenchWindow = windows[i];
            iWorkbenchWindow.removePageListener(pageListener);
            IWorkbenchPage[] pages = iWorkbenchWindow.getPages();

            for (int j = 0; j < pages.length; j++) {
                IWorkbenchPage page = pages[j];
                page.removePartListener(partListener);
            }
        }
        
        // release the memory again
		if (model != null && model.eResource() != null) {
			model.eResource().unload();
		}
    }

    /**
     * Gets the specified Adapter. We override this method to add the PaletteRoot.
     * 
     * @param type The type of Class to adapt to
     * @return Object The adapted class
     */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type) {
    	
        if (type == PaletteRoot.class) {
            return paletteRoot;
        }

        if (type == IContentOutlinePage.class) {
            if (domainOutlinePage == null) {
        		DomainRepository reposity = 
        			DomainRepository.getInstance(OfsUIResourceHelper.getOfsProject(getEditorInput()));
            	domainOutlinePage = new DomainOutlineView(reposity, getRootWidget());
            }
            return domainOutlinePage;
        }

        if (type == IResource.class) {
            if (getEditorInput() instanceof IFileEditorInput) {
                IFileEditorInput fei = (IFileEditorInput) getEditorInput();
                return fei.getFile();
            }
        }

        if (type == IPropertySheetPage.class) {
        	return new TabbedWidgetPropertySheetPage(this);
        }

        return super.getAdapter(type);
    }
    
    /**
     * Installs the EditPartFactory.
     * 
     * @param viewer The GraphicalViewer
     */
    protected void installEditPartFactory(GraphicalViewer viewer) {
        IOfsProject ofsProject = OfsUIResourceHelper.getOfsProject(getEditorInput());
        viewer.setEditPartFactory(new DesignEditPartFactory(ofsProject.getProject(), isReadonly(), getCommandStack()));
    }

    /**
     * Install a context menu provider.
     * 
     * @param viewer the graphical view
     */
    protected void installContextMenuProvider(GraphicalViewer viewer) {

        ContextMenuProvider provider = new PageUIContextMenuProvider(viewer, getActionRegistry(),
                EditorMode.DESIGN_MODE);
        viewer.setContextMenu(provider);

        boolean includeEditorInput = false;

        getEditorSite().registerContextMenu("com.odcgroup.page.ui.designeditorcontextmenu", //$NON-NLS-1$
                provider, viewer, includeEditorInput);
    }

    /**
     * Installs a key handler
     * 
     * @param viewer the graphical view
     */
    protected void installKeyHandler(GraphicalViewer viewer) {
        KeyHandler kh = viewer.getKeyHandler();
        if (kh == null) {
            kh = new KeyHandler();
            viewer.setKeyHandler(kh);
        } else {
            KeyHandler wkh = new KeyHandler();
            wkh.setParent(kh);
            viewer.setKeyHandler(wkh);
        }
    }

    /**
     * Configures the GraphicalViewer before it receives its contents.
     */
    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();

        GraphicalViewer viewer = getGraphicalViewer();
        viewer.setProperty("editor-mode", EditorMode.DESIGN_MODE);

        // install an edit part factory
        installEditPartFactory(viewer);

        // install a context menu provider
        installContextMenuProvider(viewer);

        // install a key handler
        installKeyHandler(viewer);
    }

    /**
     * Initialize the GraphicalViewer after it has been created.
     */
    @SuppressWarnings("deprecation")
	protected void initializeGraphicalViewer() {
    	
    	GraphicalViewer gv = getGraphicalViewer();
    	gv.setContents(getRootWidget());

        if ( ! isReadonly()) {

        	// Enable drag-and-drop from the Palette
        	gv.addDropTargetListener(new WidgetTemplateTransferDropTargetListener(gv));
	
	        // Enable drap-and-drop of resources from the Navigator
        	gv.addDropTargetListener(new PageElementTransferDropListener(gv));
		
	        // Enable drap-and-drop of resources from the MDF Outline view
			gv.addDropTargetListener(new MdfElementTransferDropListener(gv));
        }

        // Create a KeyBindingService. If we do not do this then when the
        // DesignEditor is used in a MultiPartEditor the other editors key
        // services may be used instead.
        // This causes problems. For example the KeyBinding for the delete
        // method no longer works in the DesignEditor after using a TextEditor.
        getSite().getKeyBindingService();
    }

    /**
     * Creates the PaletteViewerProvider.
     * 
     * @return PaletteViewerProvider The PaletteViewerProvider
     */
    protected PaletteViewerProvider createPaletteViewerProvider() {
        return new PaletteViewerProvider(getEditDomain()) {

            protected void configurePaletteViewer(PaletteViewer viewer) {
                super.configurePaletteViewer(viewer);
                //DS-3141 protect palette for standard models in custo
                IEditorInput editInput = getEditorInput();
                if (editInput instanceof IFileEditorInput) {
                	viewer.getControl().setEnabled(true);
                } else {
                	viewer.getControl().setEnabled(false);
                	return;                	
                }
                // Create a DragSourceListener for this Palette Viewer
                // The DropSourceListener is added to the Palette in
                // initializeGraphicalViewer()
                viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
            }
        };
    }

    /**
     * Gets the contributor id. This is also defined in the plugin.xml file.
     * 
     * @return String The contributor id
     */
    public String getContributorId() {
        return PageUIConstants.TABBED_PROPERTY_SHEET_CONTRIBUTER_ID;
    }
    
    /**
     * @return
     */
    public GraphicalViewer getViewer() {
    	return super.getGraphicalViewer();
    }

    /**
     * Gets the PaletteRoot. This is called when you call the method setEditDomain. In the PageUIEditor setEditDomain is
     * called in the constructor. It is also called by setInput since the Palette depends upon the type of the
     * rootWidget.
     * 
     * @return PaletteRoot The PaletteRoot
     */
    protected PaletteRoot getPaletteRoot() {
    	PaletteRoot paletteRoot = null;
    	
		UIModel uiModel = UIModelRegistry.getUIModel();
    	if (uiModel != null) {
    		
	        Palette palette=null;
	        if (rootWidget == null) {
	            palette = uiModel.getDefaultPalette();
	        } else {
	        	WidgetType widget = rootWidget.getType();
	        	if (WidgetTypeConstants.MODULE.equals(widget.getName())) {
	        		PropertyType propertyType = rootWidget.findProperty(PropertyTypeConstants.SEARCH).getType();
	        		String value = rootWidget.getPropertyValue(PropertyTypeConstants.SEARCH);
	        		palette = uiModel.findPalette(widget, propertyType, value);
	        	}else if (WidgetTypeConstants.FRAGMENT.equals(widget.getName())){
	        		  //get the palette for the xtooltip type fragment
	        		    Property property = rootWidget.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
	        		    if(property!=null){
		        		String value = property.getValue();
		        		palette = uiModel.findPalette(widget, property.getType(), value);
	        		    }
	        	}else {
	        		palette = uiModel.findPalette(rootWidget.getType());
	        	}
	        }
	        PaletteRootFactory factory = new PaletteRootFactory(palette);
	        paletteRoot = factory.create();
    	}
    	
        return paletteRoot;
    }

    /**
     * Gets the Model. This is the root EMF object which is saved.
     * 
     * @return Model The root EMF object which is saved
     */
    public Model getModel() {
        return model;
    }

    /**
     * Gets the root Widget. This is the root object of the diagram.
     * 
     * @return Widget The root Object of the diagram
     */
    public Widget getRootWidget() {
        return rootWidget;
    }
    

    /**
     * Saves the diagram.
     * 
     * @param progressMonitor The monitor to display the progress of the save operation
     */
    public void doSave(IProgressMonitor progressMonitor) {
        try {
        	if (model.eIsProxy()) {
                IOfsProject ofsProject = OfsUIResourceHelper.getOfsProject(getEditorInput());                   
            	ResourceSet resourceSet = ofsProject.getModelResourceSet();
        		model = (ModelImpl) EcoreUtil.resolve(model, resourceSet);
        	}
	    	Resource resource = model.eResource();

	    	// set the metainformation to the root 
         		model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion(resource.getURI().fileExtension()));
    		
	    	if (validateFileNotReadOnly(model)) {
		    	performSave(resource, progressMonitor);
		    	if(model.eIsProxy()) {
			    	model = (Model) resource.getContents().get(0);
			    	getGraphicalViewer().setContents(model.getWidget());
			    	getGraphicalViewer().getRootEditPart().activate();
		    	}
			} else {
	            getEditDomain().getCommandStack().markSaveLocation();
	            firePropertyChange(IEditorPart.PROP_DIRTY);
			}
	    	
		} catch (ModelNotFoundException ex) {
            Shell s = getSite().getShell();
            Dialog d = new MessageDialog(s, "Error", null, ex.getMessage(), 0, new String[] { "OK" }, 0);
            d.open();
		}
    }

    /**
     * Saves the diagram.
     */
    public void doSaveAs() {
        SaveAsDialog saveAsDialog = new SaveAsDialog(getSite().getShell());
        saveAsDialog.open();
        IPath path = saveAsDialog.getResult();
        if (path != null) {
            IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            if (file != null) {
                if (validateFileNotReadOnly(file) == false) {
                    return;
                }
                doSaveAs(URI.createPlatformResourceURI(file.getFullPath().toString(), true), new FileEditorInput(file), file.getProject());
            }
        }
    }

    /**
     * Allows the user to change the settings of a readonly file.
     * 
     * @param file The file to test
     * @return boolean False if the user choose to set the file to read-only
     */
    private boolean validateFileNotReadOnly(IFile file) {
    	if (file == null) {
    		return false;
    	}
        try {
            if (file.isReadOnly()) {
                Shell s = getSite().getShell();
                String message = "File '" + file.getProjectRelativePath().toString() + "' is read-only. Do you wish to make it writable?";
                Dialog d = new MessageDialog(s, "Read-only File Encountered", null, message, MessageDialog.QUESTION, new String[] { "Yes", "No" }, 0);
                int result = d.open();
                if (result == 0) {
                    ResourceAttributes attributes = file.getResourceAttributes();
                    attributes.setReadOnly(false);
                    file.setResourceAttributes(attributes);
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        } catch (CoreException ce) {
            throw new PageException("Unable to modify the file", ce);
        }
    }

    /**
     * Allows the user to change the settings of a readonly model.
     * @param model The model to test
     * @return boolean True if the user choose to save the model even if it is read-only
     * @throws ModelNotFoundException 
     */
    private boolean validateFileNotReadOnly(Model model) throws ModelNotFoundException {
    	
    	if (isReadonly()) {
    		return false;
    	}
    	
    	Resource resource = model.eResource();
    	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(model.eResource());
    	if (ofsProject == null) {
    		return false;
    	}
    	
    	IFile file = OfsResourceHelper.getFile(resource);
    	return validateFileNotReadOnly(file);
    }
    
    
    /**
     * Saves the diagram.
     * 
     * @param uri The URI of the resource to save
     * @param editorInput The EditorInput (file) to save
     * @param project the project
     */
    private void doSaveAs(URI uri, IEditorInput editorInput, IProject project) {
    	IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
        Resource resource = ofsProject.getModelResourceSet().createResource(uri);

        resource.getContents().add(getModel());
        resource.setURI(uri);

        setInputWithNotify(editorInput);
        setPartName(editorInput.getName());

        IProgressMonitor progressMonitor = new NullProgressMonitor();
        performSave(resource, progressMonitor);
    }

    /**
     * Performs the save.
     * 
     * @param resource The resource to save
     * @param progressMonitor The ProgressMonitor to use
     */
    private void performSave(final Resource resource, IProgressMonitor progressMonitor) {
        // Do the work within an operation because this is a long running
        // activity that modifies the workbench.
        // final Exception ex = null;
        WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

            public void execute(IProgressMonitor monitor) {
                try {
					//Map<Object, Object> options = new HashMap<Object, Object>();
					//options.put(XMLResource.OPTION_URI_HANDLER,	new ResourceEntityHandlerImpl(""));
                    resource.save(null);
                    resourceSaved = true;
                } catch (IOException ioe) {
                    Logger.error("Unable to save the resource", ioe);
                    exception = ioe;
                }
            }
        };

        try {
            // This runs the options, and shows progress.
            new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
            getEditDomain().getCommandStack().markSaveLocation();
            firePropertyChange(IEditorPart.PROP_DIRTY);
        } catch (InterruptedException ie) {
            Logger.error("Unable to save the resource", ie);
        } catch (InvocationTargetException ite) {
            Logger.error("Unable to save the resource", ite);
        }

        if (exception != null) {
            String message = exception.getMessage();
            exception = null;
            Shell s = getSite().getShell();
            Dialog d = new MessageDialog(s, "Error", null, message, 0, new String[] { "OK" }, 0);
            d.open();
            return;
        }
    }

    /**
     * Returns true if the saveAs operation is allowed.
     * 
     * @return boolean True if the saveAs operation is allowed
     */
    public boolean isSaveAsAllowed() {
        return true;
    }
    
    private void handleRemoteResourceError(String inputPath) throws PageException {
    	handleError("Unable to load remote resource: "+inputPath);
    }
    
    /**
     * @param reason
     * @throws PageException
     */
    private void handleError(String reason) throws PageException {
    	handleError(reason, null);
    }

    /**
     * @param reason
     * @param exception 
     * @throws PageException
     */
    private void handleError(String reason, Throwable exception) throws PageException {
		Logger.error(reason);
		throw new PageException("Page Design Editor cannot be opened", 
				new Status(IStatus.ERROR,
						   PageUIPlugin.PLUGIN_ID,
						   1,
						   reason,
						   exception));
    }

    /**
     * Sets the input of the diagram.
     * 
     * @param input The input of the diagram
     */
    protected void setInput(IEditorInput input) {
        super.setInput(input);

        /** The ofs project */
        IOfsProject ofsProject = null;
        
        model = null;
        
        if (input instanceof IFileEditorInput){
            // retrieve the ofs project
            IFile file = ((IFileEditorInput) input).getFile();
            String filePath = file.getFullPath().toString();
            URI uri = URI.createPlatformResourceURI(filePath, true);
        	ofsProject = OfsCore.getOfsProjectManager().getOfsProject(file.getProject());
        	if (ofsProject == null) {
        		handleError("Unable to find the project for the file ["+file.getName()+"]"); 
        	}
        	
        	ResourceSet rs = ofsProject.getModelResourceSet();
        	if (rs == null) {
        		handleError("No resource-set defined for the project ["+ofsProject.getName()+"]");
        	}
        	
    		Resource resource = rs.getResource(uri, true);
    		if (resource == null) {
    			handleError("Unable to find resource ["+uri+"]");
    		}
    		
            model = (Model) resource.getContents().iterator().next();
            if (model == null) {
            	handleError("The resource's model is invalid [" + uri+"]");
            }
            // set the metainformation to the root 
    		model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion(uri.fileExtension()));
            rootWidget = model.getWidget();

	        // Update the Palette
	        paletteRoot = null;
	        getEditDomain().setPaletteRoot(getPaletteRoot());
	        pageListener.setFile(file);

	        // These are needed otherwise the pages cannot be printed
	        setPartName(file.getName());
	        setContentDescription(file.getName());

        } else if (input instanceof ModelEditorInput) { 
        	// also consider modeleditorinput (readonly)
        	ModelEditorInput mInput = (ModelEditorInput)input;
        	try {
        		IOfsModelResource mResource = (IOfsModelResource) mInput.getStorage();
        		ofsProject = mResource.getOfsProject();
        		model = (Model) mResource.getEMFModel().get(0);
                if (model == null) {
                	handleError("Unable to load resource "+mResource.getURI().toString());
                }
    			readonly = ! mResource.hasScope(IOfsProject.SCOPE_PROJECT);
                rootWidget = model.getWidget();
                // Update the Palette
                paletteRoot = null;
                getEditDomain().setPaletteRoot(getPaletteRoot());

                // These are needed otherwise the pages cannot be printed
                setPartName(mResource.getName());
                setContentDescription(mResource.getName());
			} catch (CoreException e) {
				PageUIPlugin.getDefault().logError("Error setting page editor input", e);
			} catch (IOException e) {
				PageUIPlugin.getDefault().logError("Error setting page editor input", e);
			} catch (InvalidMetamodelVersionException e) {
				PageUIPlugin.getDefault().logError("Error setting page editor input", e);
			}
        } else if (input instanceof IStorageEditorInput) {
        	IStorageEditorInput storageEditorInput = (IStorageEditorInput) input;
        	try {
	        	ofsProject = OfsUIResourceHelper.getOfsProject(storageEditorInput);
	        	model = (Model)getModel(storageEditorInput);
	        	if (model == null) {
	        		handleRemoteResourceError(model.eResource().getURI().toString());
	        	}
	        	readonly = true;
	        	rootWidget = model.getWidget();// Update the Palette
                if(rootWidget == null) {
                	handleRemoteResourceError(model.eResource().getURI().toString());
                }
                paletteRoot = null;
                getEditDomain().setPaletteRoot(getPaletteRoot());

                // These are needed otherwise the pages cannot be printed
                setPartName(storageEditorInput.getName());
                setContentDescription(storageEditorInput.getName());
        	} catch (CoreException e) {
        		handleError(e.getLocalizedMessage(), e);      		
        	}
        }
        
    }

    private Model getModel(IStorageEditorInput input) throws CoreException {
		return (Model)OfsUIResourceHelper.getModelFromEditorInput(input, PageUIPlugin.PLUGIN_ID);
	}

	/**
     * Ensures that the save button is enabled when the command stack is changed.
     * 
     * @param event The event which occurred
     */
    public void commandStackChanged(EventObject event) {
        firePropertyChange(IEditorPart.PROP_DIRTY);
        super.commandStackChanged(event);
    }

    /**
     * Override to take into account the MultiPageUIEditor. The problem is that the super-class version only tests if
     * this editor is the active editor. However since the PageUIEditor can be contained within a MultiPageUIEditor the
     * active editor may be the multi-page editor. Note that we shouldn't really need to know that the PageUIEditor can
     * be contained within the MultiPageUIEditor however I haven't found a better solution. Without this override the
     * selection actions, such as Delete did not work.
     * 
     * @param part The WorkbenchPart
     * @param selection The ISelectiob
     */
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        // If not the active editor, ignore selection changed.
        if (getSite().getPage().getActiveEditor() instanceof PageDesignerEditor) {
            updateActions(getSelectionActions());
        }
    }
    
    /** 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#setFocus()
	 */
	public void setFocus() {
		super.setFocus();
	}   
	
    /**
     * The page listener of the editor
     * 
     * @author Alexandre Jaquet
     * 
     */
    public class PageListener implements IPageListener {

        /**
         * Sets the part listener file.
         * 
         * @param file
         */
        public void setFile(IFile file) {
            partListener.setFile(file);
        }

        /**
         * Activate the page and add the edit part listener
         * 
         * @param page The activated page
         */
        public void pageActivated(IWorkbenchPage page) {
            page.addPartListener(partListener);
        }

        /**
         * Close the activated page
         * 
         * @param page The activated page
         */
        public void pageClosed(IWorkbenchPage page) {
            // do nothing no needs actually
        }

        /**
         * On opened page
         * 
         * @param page The activated page
         */
        public void pageOpened(IWorkbenchPage page) {
            page.addPartListener(partListener);
        }

    }

    /**
     * The edit part listener for the editor
     * 
     * @author Alexandre Jaquet
     */
    public class PartListener implements IPartListener {

        /** The current file */
        private IFile file;

        /**
         * Gets the current file
         * 
         * @return IFile The file
         */
        public IFile getFile() {
            return this.file;
        }

        /**
         * Sets the file
         * 
         * @param file
         */
        public void setFile(IFile file) {
            this.file = file;
        }

        /**
         * Occurs once the part is activated
         * 
         * @param part The edit part
         */
        public void partActivated(IWorkbenchPart part) {
            // do nothing no needs actually
        }

        /**
         * Occurs when the part is brough to the top
         * 
         * @param part The edit part
         */
        public void partBroughtToTop(IWorkbenchPart part) {
            // do nothing no needs actually
        }

        /**
         * Occurs once the edit part close event is fired
         * 
         * @param part The edit part
         */
        public void partClosed(IWorkbenchPart part) {

        	/**
        	 * DS-1296 - BUG FIXED 
        	 * the following statements have been commented: Indeed, this code will 
        	 * simply remove the resource from the resource-set of the underlying
        	 * ofs project. A consequence of this the double click on included 
        	 * widget doesn't work anymore.  
        	 */ 
//
//            if (file != null && part instanceof PageDesignerEditor) {
//                // load resource
//                String filePath = file.getFullPath().toString();
//                URI uri = URI.createPlatformResourceURI(filePath, true);
//            	IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(file.getProject());
//                Resource resource = ofsProject.getResourceSet().getResource(uri, false);
//
//                List resources = ofsProject.getResourceSet().getResources();
//                for (int i = 0; i < resources.size(); i++) {
//                    Object o = resources.get(i);
//                    // delete the resource from the list if exist
//                    if (o.equals(resource)) {
//                        //resources.remove(o);
//                    }
//                }
//            }

            file = null;
        }

        /**
         * Desactivate the edit part
         * 
         * @param part The edit part
         */
        public void partDeactivated(IWorkbenchPart part) {
            // do nothing no needs actually
        }

        /**
         * Occurs once the part is opened
         * 
         * @param part The edit part
         */
        public void partOpened(IWorkbenchPart part) {
            // do nothing no needs actually
        }

    }

    /**
     * Window listener for the editor
     * 
     * @author Alexandre Jaquet
     * 
     */
    public class WindowListener implements IWindowListener {

        /**
         * Triggered when the window is activated
         * 
         * @param window The active window
         */
        public void windowActivated(IWorkbenchWindow window) {
            window.addPageListener(pageListener);
        }

        /**
         * Triggered when the window is closed
         * 
         * @param window The active window
         */
        public void windowClosed(IWorkbenchWindow window) {
            window.removePageListener(pageListener);
        }

        /**
         * Triggered when the window is desactived
         * 
         * @param window The active window
         */
        public void windowDeactivated(IWorkbenchWindow window) {
            window.removePageListener(pageListener);
        }

        /**
         * Triggered when the window is opened
         * 
         * @param window The active window
         */
        public void windowOpened(IWorkbenchWindow window) {
            window.addPageListener(pageListener);

        }

    }

    /**
     * Registers an action.
     * 
     * @param newAction The action to register
     */
    @SuppressWarnings("unchecked")
	protected void registerAction(IAction newAction) {
        getActionRegistry().registerAction(newAction);
        getSelectionActions().add(newAction.getId());
    }

    /**
     * creates the actions in the diagram menu
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#createActions()
     */
    @Override
    protected void createActions() {

        super.createActions();

        /** Create and register Cut action */
        registerAction(new CutWidgetAction(this));

        /** Create and register Copy action */
        registerAction(new CopyWidgetAction(this));

        /** Create and register Paste action */
        registerAction(new PasteWidgetAction(this));
    }
    
    /**
     * Visitor for resource changes.
     * 
     * @author Gary Hayes
     */
    private class ResourceDeltaVisitor implements IResourceDeltaVisitor {  
        IOfsProject ofsProject = OfsUIResourceHelper.getOfsProject(getEditorInput());                   
    	protected ResourceSet resourceSet = (ofsProject != null) ? ofsProject.getModelResourceSet() : null;
        protected Collection<Resource> changedResources = new ArrayList<Resource>();  
        
        /**
         * @return
         */
        public Collection<Resource> getChangedResources() {  
            return changedResources;  
        }  
  	
        /**
         * Visits the change.
         * 
         * @param delta
         * @return boolean
         */
        public boolean visit(final IResourceDelta delta) {  
        	
        	IResource resource = (IResource)delta.getResource();
        	if (resource == null) {
        		return false;
        	}
        	// ignore non OFS project 
        	IProject project = resource.getProject(); 
        	if (project != null && project.isOpen()) {
        		try {
					if (!project.hasNature(OfsCore.NATURE_ID)) {
						return false;
					}
				} catch (CoreException e) {
					if (project.getName().toLowerCase().endsWith("-gen")) {
						return false;
					}
				}
        	}
        	
        	//int kind = delta.getKind();

            if (delta.getResource().getType() == IResource.FILE) {
                if ((delta.getKind() & (IResourceDelta.REMOVED)) != 0) {
                    String deltaPath = delta.getFullPath().toString();
                    pageName = deltaPath.substring(deltaPath.lastIndexOf('/') + 1);
                    getSite().getShell().getDisplay().asyncExec(new Runnable() {

                        public void run() {
                            IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
                            for (int i = 0; i < pages.length; i++) {
                                IWorkbenchPage page = pages[i];
                                IEditorPart ep = page.getActiveEditor();
                                if (ep != null) {
                                    if (ep.getTitle().equals(pageName)) {
                                        getSite().getPage().closeEditor(ep, false);
                                    }
                                }
                            }
                        }
                    });
                } else if (delta.getKind() == IResourceDelta.CHANGED && delta.getFlags() != IResourceDelta.MARKERS) {   
                	URI uri = URI.createPlatformResourceURI(delta.getFullPath().toString(), true);
			if (model != null && model.eResource() != null && ModelURIConverter.toResourceURI(uri) == model.eResource().getURI()) {
                		Resource res = resourceSet.getResource(uri, false);  
                        changedResources.add(res);  
                    }  
                }
            }
            return true;
        }
    }

}