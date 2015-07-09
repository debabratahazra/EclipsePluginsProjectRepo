package com.odcgroup.mdf.editor.ui.editors;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.xtext.naming.QualifiedName;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.MdfMarkerUtils;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.security.MdfPermission;
import com.odcgroup.mdf.editor.ui.MdfViewerFilter;
import com.odcgroup.mdf.editor.ui.dialogs.MdfPropertySheetPage;
import com.odcgroup.mdf.editor.ui.editors.providers.DomainItemProvider;
import com.odcgroup.mdf.editor.ui.editors.providers.MdfItemProviderAdapterFactory;
import com.odcgroup.mdf.editor.ui.editors.providers.MdfTreeItemReferenceProvider;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfReverseAssociationWrapper;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;
import com.odcgroup.workbench.security.DSAuthorizationResult;
import com.odcgroup.workbench.security.SecurityCore;
import com.odcgroup.workbench.ui.ModelEditorInput;
import com.odcgroup.workbench.ui.OfsUIResourceHelper;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

public class DomainModelEditor extends MultiPageEditorPart implements
        IEditingDomainProvider, ISelectionProvider, IMenuListener,
        IViewerProvider, IGotoMarker, ILabelProviderListener {

    private static final Logger LOGGER = Logger.getLogger(DomainModelEditor.class);

	private static final String EXTENSION_ID = "com.odcgroup.mdf.editor.domain_attribute_sorter_override";
	
	private final String DOMAINS_HIDDEN_BY_FILTERS_MSG = "All Domain items are hidden by filters!";
	
	private static MdfElementSorter sorter = null;
	// private static MdfElementSorter extendedSorter = null;

    /**
     * This keeps track of the editing domain that is used to track all changes
     * to the model.
     */
    protected AdapterFactoryEditingDomain editingDomain;

    /**
     * This is the one adapter factory used for providing views of the model.
     */
    protected ComposedAdapterFactory adapterFactory;

    /**
     * This is the content outline page.
     */
    protected IContentOutlinePage contentOutlinePage;

    /**
     * This is a kludge...
     */
    protected IStatusLineManager contentOutlineStatusLineManager;

    /**
     * This is the content outline page's viewer.
     */
    protected TreeViewer contentOutlineViewer;

    /**
     * This is the property sheet page.
     */
    protected MdfPropertySheetPage propertySheetPage;

    /**
     * This is the viewer that shadows the selection in the content outline. The
     * parent relation must be correctly defined for this to work.
     */
    protected TreeViewer selectionViewer;

    /**
     * This keeps track of the active content viewer, which may be either one of
     * the viewers in the pages or the content outline viewer.
     */
    protected Viewer currentViewer;
    
    /**
     * 
     */
    protected PrevSelectionStack selectionStack = new PrevSelectionStack();

    /**
     * This listens to which ever viewer is active.
     */
    protected ISelectionChangedListener selectionChangedListener;

    /**
     * This keeps track of all the
     * {@link org.eclipse.jface.viewers.ISelectionChangedListener}s that are
     * listening to this editor.
     */
    protected Collection selectionChangedListeners = new ArrayList();

    /**
     * This keeps track of the selection of the editor as a whole.
     */
    protected ISelection editorSelection = StructuredSelection.EMPTY;

    /**
     * The MarkerHelper is responsible for creating workspace resource markers
     * presented in Eclipse's Problems View.
     */
    protected MarkerHelper markerHelper = new EditUIMarkerHelper();
    
    /**
     * This listens for when the outline becomes active
     */
    protected IPartListener partListener = new IPartListener() {

        public void partActivated(IWorkbenchPart p) {
            if (p instanceof ContentOutline) {
                if (((ContentOutline) p).getCurrentPage() == contentOutlinePage) {
                    getActionBarContributor().setActiveEditor(
                            DomainModelEditor.this);

                    setCurrentViewer(contentOutlineViewer);
                }
            } else if (p instanceof PropertySheet) {
                if (((PropertySheet) p).getCurrentPage() == propertySheetPage) {
                    getActionBarContributor().setActiveEditor(
                            DomainModelEditor.this);
                    handleActivate();
                }
            } else if (p == DomainModelEditor.this) {
                handleActivate();
            }
        }

        public void partBroughtToTop(IWorkbenchPart p) {
        }

        public void partClosed(IWorkbenchPart p) {
        }

        public void partDeactivated(IWorkbenchPart p) {
        }

        public void partOpened(IWorkbenchPart p) {
        }
    };

    /**
     * Resources that have been removed since last activation.
     */
    protected Collection removedResources = new ArrayList();

    /**
     * Resources that have been changed since last activation.
     */
    protected Collection changedResources = new ArrayList();

    /**
     * Resources that have been saved.
     */
    protected Collection savedResources = new ArrayList();

    /**
     * Map to store the diagnostic associated with a resource.
     */
    protected Map resourceToDiagnosticMap = new LinkedHashMap();

    /**
     * Controls whether the problem indication should be updated.
     */
    protected boolean updateProblemIndication = true;

    MdfDomainImpl editedDomain = null;

    protected TextEditor dslEditor;
    
    protected boolean textMode = false;


    /**
     * Adapter used to update the problem indication when resources are demanded
     * loaded.
     */
    protected EContentAdapter problemIndicationAdapter = new EContentAdapter() {

        public void notifyChanged(Notification notification) {
            if (notification.getNotifier() instanceof Resource) {
                switch (notification.getFeatureID(Resource.class)) {
                    case Resource.RESOURCE__IS_LOADED:
                    case Resource.RESOURCE__ERRORS:
                    case Resource.RESOURCE__WARNINGS: {
                        Resource resource = (Resource) notification.getNotifier();
                        Diagnostic diagnostic = analyzeResourceProblems(
                                (Resource) notification.getNotifier(), null);
                        if (diagnostic.getSeverity() != Diagnostic.OK) {
                            resourceToDiagnosticMap.put(resource, diagnostic);
                        } else {
                            resourceToDiagnosticMap.remove(resource);
                        }

                        if (updateProblemIndication) {
                            getSite().getShell().getDisplay().asyncExec(
                                    new Runnable() {

                                        public void run() {
                                            updateProblemIndication();
                                        }
                                    });
                        }
                    }
                }
            } else {
                super.notifyChanged(notification);
            }
        }

        protected void setTarget(Resource target) {
            basicSetTarget(target);
        }

        protected void unsetTarget(Resource target) {
            basicUnsetTarget(target);
        }
    };

    /**
     * This listens for workspace changes.
     */
    protected IResourceChangeListener resourceChangeListener = new IResourceChangeListener() {

        public void resourceChanged(IResourceChangeEvent event) {
            // Only listening to these.
            // if (event.getType() == IResourceDelta.POST_CHANGE)
            {
                IResourceDelta delta = event.getDelta();
                try {
                    
                    ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
                    delta.accept(visitor);

                    if (!visitor.getRemovedResources().isEmpty()) {
                        removedResources.addAll(visitor.getRemovedResources());
                        if (!isDirty()) {
                            getSite().getShell().getDisplay().asyncExec(
                                    new Runnable() {

                                        public void run() {
                                            getSite().getPage().closeEditor(
                                                    DomainModelEditor.this,
                                                    false);
                                            DomainModelEditor.this.dispose();
                                        }
                                    });
                        }
                    }

                    if (!visitor.getChangedResources().isEmpty()) {
                        changedResources.addAll(visitor.getChangedResources());
                        if (getSite().getPage().getActiveEditor() == DomainModelEditor.this) {
                            getSite().getShell().getDisplay().asyncExec(
                                    new Runnable() {

                                        public void run() {
                                            handleActivate();
                                        }
                                    });
                        }
                    }
                } catch (CoreException exception) {
                    LOGGER.error(exception, exception);
                }
            }
        }
    };

    /**
     * Handles activation of the editor or it's associated views.
     */
    protected void handleActivate() {
        // Recompute the read only state.
        //
        if (editingDomain.getResourceToReadOnlyMap() != null) {
            editingDomain.getResourceToReadOnlyMap().clear();

            // Refresh any actions that may become enabled or disabled.
            //
            setSelection(getSelection());
        }

        if (!removedResources.isEmpty()) {
            if (handleDirtyConflict()) {
                getSite().getPage().closeEditor(DomainModelEditor.this, false);
                DomainModelEditor.this.dispose();
            } else {
                removedResources.clear();
                changedResources.clear();
                savedResources.clear();
            }
        } else if (!changedResources.isEmpty()) {
            changedResources.removeAll(savedResources);
            handleChangedResources();
            changedResources.clear();
            savedResources.clear();
        }
    	refreshViewerFilters();
    	if (selectionViewer != null) { // DS-5843
	    	selectionViewer.setSorter(sorter);
	    	selectionViewer.refresh();
    	}
    }
    
    /**
     * DS-1692
     * refreshes the filters for the viewer
     */
    protected void refreshViewerFilters() {
    	Collection filters = MdfPlugin.getDefault().getFilters();
        Iterator iter = filters.iterator();
        MdfViewerFilter filter = null;
        if(editingDomain.getResourceSet().getURIConverter() instanceof ModelURIConverter) {
	        ModelURIConverter mConverter = (ModelURIConverter) editingDomain.getResourceSet().getURIConverter();
	        while(iter.hasNext()) {
	        	filter = (MdfViewerFilter) iter.next();
	        	if (filter.isApplicable(mConverter.getOfsProject().getProject())) {
	        		((StructuredViewer)currentViewer).addFilter(filter);
	        	}
	        }
        }
    }
    
    protected boolean changeFromOtherTab = false;

    /**
     * Handles what to do with changed resources on activation.
     */
    protected void handleChangedResources() {
    	if (!changedResources.isEmpty() && changeFromOtherTab) {
    		updateToResourcechanges();
    		changeFromOtherTab = false;
    		return;
    	}
        if (!changedResources.isEmpty()
                && (!isDirty() || handleDirtyConflict())) {
        	
        	String msg = "The resource \'"+editedDomain.eResource().getURI().toString()+" has been changed " +
	 			"on the file system. Do you want to replace the editor contents with these changes?";
        	if(MessageDialog.openConfirm(getSite().getShell(), "Resource Changed", msg)) { 
        		updateToResourcechanges();
        	}
        }
    }
    
    /**
     * 
     */
    private void updateToResourcechanges() {
    	editingDomain.getCommandStack().flush();
    	
        updateProblemIndication = false;
        for (Iterator i = changedResources.iterator(); i.hasNext();) {
            Resource resource = (Resource) i.next();
            if (resource.isLoaded()) {
                resource.unload();
                try {
                    resource.load(Collections.EMPTY_MAP);
                    editedDomain = (MdfDomainImpl) resource.getContents().get(0);
                    if (selectionViewer != null) { // DS-5843
                    	selectionViewer.setInput(new DomainItemProvider(editedDomain));
                    	selectionViewer.expandToLevel(editedDomain, 1);
                    }
                } catch (IOException exception) {
                    if (!resourceToDiagnosticMap.containsKey(resource)) {
                        resourceToDiagnosticMap.put(
                                resource,
                                analyzeResourceProblems(resource, exception));
                    }
                }
            }
        }
        updateProblemIndication = true;
        updateProblemIndication();
    }

    /**
     * Updates the problems indication with the information described in the
     * specified diagnostic.
     */
    protected void updateProblemIndication() {
        if (updateProblemIndication) {
            BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.OK,
                    MdfCore.PLUGIN_ID, 0, null,
                    new Object[] { editingDomain.getResourceSet() });

            for (Iterator i = resourceToDiagnosticMap.values().iterator(); i.hasNext();) {
                Diagnostic childDiagnostic = (Diagnostic) i.next();

                if (childDiagnostic.getSeverity() != Diagnostic.OK) {
                    diagnostic.add(childDiagnostic);
                }
            }
            if (markerHelper.hasMarkers(editingDomain.getResourceSet())) {
                markerHelper.deleteMarkers(editingDomain.getResourceSet());
                if (diagnostic.getSeverity() != Diagnostic.OK) {
                    try {
                        markerHelper.createMarkers(diagnostic);
                    } catch (CoreException exception) {
                        LOGGER.error(exception, exception);
                    }
                }
            }
        }
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
     * This creates a model editor.
     */
    public DomainModelEditor() {
        super();

        // Create an adapter factory that yields item providers.
        //
        List factories = new ArrayList();
        factories.add(new ResourceItemProviderAdapterFactory());
        factories.add(new MdfItemProviderAdapterFactory());
        factories.add(new ReflectiveItemProviderAdapterFactory());

        adapterFactory = new ComposedAdapterFactory(factories);

        // Create the command stack that will notify this editor as commands are
        // executed.
        //
        BasicCommandStack commandStack = new BasicCommandStack();

        // Add a listener to set the most recent command's affected objects to
        // be the selection of the viewer with focus.
        //
        commandStack.addCommandStackListener(new CommandStackListener() {

            public void commandStackChanged(final EventObject event) {
                getContainer().getDisplay().asyncExec(new Runnable() {

                    public void run() {
                        firePropertyChange(IEditorPart.PROP_DIRTY);

                        // Try to select the affected objects.
                        //
                        Command mostRecentCommand = ((CommandStack) event.getSource()).getMostRecentCommand();
                        if (mostRecentCommand != null) {
                            setSelectionToViewer(mostRecentCommand.getAffectedObjects());
                        }
                        //#DS-3442 -removed  propertysheetpage refresh action statement .Ref file version:86440
                       
                    }
                });
            }
        });

        // Create the editing domain with a special command stack.
        // handle the URI with "resource" scheme
        editingDomain = new AdapterFactoryEditingDomain(adapterFactory,
                commandStack, new HashMap()) {					
			protected boolean isReadOnlyURI(URI uri) {
				if (uri.isArchive())  {
			      return isReadOnlyURI(URI.createURI(uri.authority()));
			    }
				if (uri.scheme().equals(ModelURIConverter.SCHEME)) {	
					ModelURIConverter uriCon = (ModelURIConverter)getResourceSet().getURIConverter();
					try {
						IOfsModelResource res = uriCon.getOfsProject().getOfsModelResource(uri);
						if (res != null && !res.isReadOnly()) {
							return false;
						} 
					} catch (ModelNotFoundException e) {
						LOGGER.error(e, e);
					}
					return true;
				}
				return super.isReadOnlyURI(uri);
			}

			@Override
			public Collection<Object> getClipboard() {
				return MdfPlugin.getDefault().getClipboard();
			}

			@Override
			public void setClipboard(Collection<Object> clipboard) {
				MdfPlugin.getDefault().setClipboard(clipboard);
			} 
			
			
        };
    }

    /**
     * This is here for the listener to be able to call it.
     */
    protected void firePropertyChange(int action) {
        super.firePropertyChange(action);
    }

    /**
     * This sets the selection into whichever viewer is active.
     */
    public void setSelectionToViewer(Collection collection) {
        final Collection theSelection = collection;
        // Make sure it's okay.
        //
        if (theSelection != null && !theSelection.isEmpty()) {
            // I don't know if this should be run this deferred
            // because we might have to give the editor a chance to process the
            // viewer update events
            // and hence to update the views first.
            //
            //
            Runnable runnable = new Runnable() {

                public void run() {
                    // Try to select the items in the current content viewer of
                    // the editor.
                    //
                    if (currentViewer != null) {
                        currentViewer.setSelection(new StructuredSelection(
                                theSelection.toArray()), true);
                    }
                }
            };
            runnable.run();
        }
    }

    /**
     * This returns the editing domain as required by the
     * {@link IEditingDomainProvider} interface. This is important for
     * implementing the static methods of {@link AdapterFactoryEditingDomain}
     * and for supporting {@link org.eclipse.emf.edit.ui.action.CommandAction}.
     */
    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    private static final class RefreshRunnable implements Runnable {

    	static private final Set<Viewer> queued = Collections.synchronizedSet(new HashSet<Viewer>());

    	private final Viewer viewer;
    	private boolean active;
    	
    	public RefreshRunnable(Viewer viewer) {
    		this.viewer = viewer;
    		// only activate this runnable if we do not have another instance already queued
    		active = !queued.contains(viewer);
    		if(active) queued.add(viewer);
    	}
    	
		@Override
		public void run() {
			if(active) {
				queued.remove(viewer);
				viewer.refresh();
			}
		}
	}

	public class ReverseAdapterFactoryContentProvider extends
            AdapterFactoryContentProvider {

        public ReverseAdapterFactoryContentProvider(
                AdapterFactory adapterFactory) {
            super(adapterFactory);
        }

        public Object[] getElements(Object object) {
            Object parent = super.getParent(object);
            return (parent == null ? Collections.EMPTY_SET
                    : Collections.singleton(parent)).toArray();
        }

        public Object[] getChildren(Object object) {
            Object parent = super.getParent(object);
            return (parent == null ? Collections.EMPTY_SET
                    : Collections.singleton(parent)).toArray();
        }

        public boolean hasChildren(Object object) {
            Object parent = super.getParent(object);
            return parent != null;
        }

        public Object getParent(Object object) {
            return null;
        }
    }

    /**
     * This makes sure that one content viewer, either for the current page or
     * the outline view, if it has focus, is the current one.
     */
    public void setCurrentViewer(Viewer viewer) {
        // If it is changing...
        if (currentViewer != viewer) {
            if (selectionChangedListener == null) {
                // Create the listener on demand.
                selectionChangedListener = new ISelectionChangedListener() {

                    // This just notifies those things that are affected by the
                    // section.
                    public void selectionChanged(
                            SelectionChangedEvent selectionChangedEvent) {
                        setSelection(selectionChangedEvent.getSelection());
                    }
                };
            }

            // Stop listening to the old one.
            if (currentViewer != null) {
                currentViewer.removeSelectionChangedListener(selectionChangedListener);
            }

            // Start listening to the new one.
            if (viewer != null) {
                viewer.addSelectionChangedListener(selectionChangedListener);
            }

            // Remember it.
            currentViewer = viewer;

            // Set the editors selection based on the current viewer's
            // selection.
            setSelection(currentViewer == null ? StructuredSelection.EMPTY
                    : currentViewer.getSelection());
        }
    }

    /**
     * This returns the viewer as required by the {@link IViewerProvider}
     * interface.
     */
    public Viewer getViewer() {
        return currentViewer;
    }

    /**
     * This creates a context menu for the viewer and adds a listener as well
     * registering the menu for extension.
     */
    protected void createContextMenuFor(StructuredViewer viewer) {
        MenuManager contextMenu = new MenuManager("#PopUp");
        contextMenu.add(new Separator("additions"));
        contextMenu.setRemoveAllWhenShown(true);
        contextMenu.addMenuListener(this);
        Menu menu = contextMenu.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(contextMenu, viewer);

        int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
        Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
        viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(
                viewer) {
        	@Override
        	public void dragStart(DragSourceEvent event) {
        		EditingDomain domain = editingDomain;
        		URIConverter uriConverter = domain.getResourceSet().getURIConverter();
        		if (uriConverter instanceof ModelURIConverter) {
        			ModelURIConverter modelUriConverter = (ModelURIConverter) uriConverter;
        			IProject project = modelUriConverter.getOfsProject().getProject();
    				if (editedDomain.eResource() != null) {
    					URI uri = editedDomain.eResource().getURI();
    					if(domain.isReadOnly(editedDomain.eResource()) ||
    							SecurityCore.getAuthorizationManager().permissionGranted(project, uri,
    							MdfPermission.CONTEXT_MENU_NEW, null) == DSAuthorizationResult.REJECTED) {
    	    				event.doit = false;
    	    				return;
    					}
    				}
        		}

           		super.dragStart(event);
        	}
        });
        viewer.addDropSupport(dndOperations, transfers,
                new EditingDomainViewerDropAdapter(editingDomain, viewer));
    }

    /**
     * This is the method called to load a resource into the editing domain's
     * resource set based on the editor's input.
     * editorinput could be file or read-only (referenced)
     */
    public void createModel() {
    	
    	IOfsProject ofsProject = null;
    	URI resourceURI = null;
        Exception exception = null;
        Resource editedResource = null;
        IEditorInput editorInput = getEditorInput();
    	if (editorInput instanceof IFileEditorInput) {

            // Assumes that the input is a file object.
            IFileEditorInput modelFile = (IFileEditorInput) editorInput;
            IFile file = modelFile.getFile();
            String filePath = file.getFullPath().toString();
            resourceURI = URI.createPlatformResourceURI(filePath, true);

            try {
                // Load the resource through the editing domain.
                editedResource = editingDomain.getResourceSet().getResource(resourceURI, true);
            } catch (Exception e) {
                exception = e;
                editedResource = editingDomain.getResourceSet().getResource(
                        URI.createFileURI(file.getProjectRelativePath().toString()), false);
            }
            if(editedResource==null || editedResource.getContents().size()==0) {
            	MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Cannot read model", 
            			"Domain model cannot be read, please check if it is valid.");
            	exception = new IOException("Cannot read model, please check the log file for details");
            } else {
            	editedDomain = (MdfDomainImpl) editedResource.getContents().get(0);
            }

    	} else if (editorInput instanceof ModelEditorInput) {
    		// Assumes that the input is an OfsModelResource
    		ModelEditorInput input = (ModelEditorInput)editorInput;
    		IOfsModelResource resource ;
			try {
				resource = (IOfsModelResource)input.getStorage();
				ofsProject = resource.getOfsProject();
	            editingDomain.getResourceSet().setURIConverter(ofsProject.getModelResourceSet().getURIConverter());
	            if(resource.getEMFModel().size()==0) {
	            	MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Cannot read model", 
	            			"Domain model cannot be read, please check if it is valid.");
	            }
	    		editedDomain = (MdfDomainImpl) resource.getEMFModel().get(0);
	    		editedResource = editedDomain.eResource();
			} catch (CoreException e) {
				exception = e;
				LOGGER.error(e, e);
			} catch (IOException e) {
				exception = e;
				LOGGER.error(e, e);
			} catch (InvalidMetamodelVersionException e) {
				exception = e;
				LOGGER.error(e, e);
			}
    		
    	} else if (editorInput instanceof IStorageEditorInput) {
    		// remote resource
    		try {
	    		IStorageEditorInput sInput = (IStorageEditorInput) editorInput;
	        	editedDomain = getEditedDomain(sInput);
	    		editedResource = editedDomain.eResource();
	    		editingDomain.getResourceSet().setURIConverter(editedResource.getResourceSet().getURIConverter());
			} catch (CoreException e) {
				exception = e;
				LOGGER.error(e, e);
			}
    	}

        Diagnostic diagnostic = analyzeResourceProblems(editedResource, exception);
        if (diagnostic.getSeverity() != Diagnostic.OK) {
            resourceToDiagnosticMap.put(editedResource,
                    analyzeResourceProblems(editedResource, exception));
        }
        editingDomain.getResourceSet().eAdapters().add(problemIndicationAdapter);
        // DS-2998: Deactivate this
        // validate the model upon load
        //MdfMarkerUtils.updateMarkers(editedResource, new NullProgressMonitor());
    }

	private MdfDomainImpl getEditedDomain(IStorageEditorInput sInput) throws CoreException {
		MdfDomainImpl domain = (MdfDomainImpl)OfsUIResourceHelper.getModelFromEditorInput(sInput, MdfCore.PLUGIN_ID);
		return domain;
	}
    
    private boolean isModelReadOnly() {
    	if (editedDomain != null){
    		IProject prj =  OfsResourceHelper.getProject(editedDomain.eResource());
    		if (prj != null) {
    			return false;
    		}
    		return true;
    	} else {
    		return false;
    	}
    }

    /**
     * Returns a diagnostic describing the errors and warnings listed in the
     * resource and the specified exception (if any).
     */
    public Diagnostic analyzeResourceProblems(Resource resource,
            Exception exception) {
    	if (resource!=null && (!resource.getErrors().isEmpty()
                || !resource.getWarnings().isEmpty())) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(
                    Diagnostic.ERROR, "com.odcgroup.mdf.ecore.editor", 0,
                    "Problems encountered in file \"" + resource.getURI()
                            + "\"",
                    new Object[] { exception == null ? (Object) resource
                            : exception });
            basicDiagnostic.merge(EcoreUtil.computeDiagnostic(resource, true));
            return basicDiagnostic;
        } else if (exception != null) {
            return new BasicDiagnostic(Diagnostic.ERROR,
                    "com.odcgroup.mdf.ecore.editor", 0,
                    "Problems encountered while loading domain model", new Object[] { exception });
        } else {
            return Diagnostic.OK_INSTANCE;
        }
    }

    /**
     * This is the method used by the framework to install your own controls.
     */
    public void createPages() {
    	// Set help
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getContainer(), IOfsHelpContextIds.WORKBENCH);

        textMode = fileHasSyntaxErrors();
        if(!textMode) {
	        // Creates the model from the editor input
	        createModel();
	
	        // Only creates the other pages if there is something that can be edited
	        if (editedDomain != null) {
	            // Create a page for the selection tree view.
	            //
	            Tree tree = new Tree(getContainer(), SWT.MULTI);
	            selectionViewer = new DomainTreeViewer(tree);
	            setCurrentViewer(selectionViewer);
	            
	            selectionViewer.setUseHashlookup(true);
	            // Equals and hashCode methods of elements are causing trouble when
	            // changing the names so we need an identity comparison
	            selectionViewer.setComparer(new IElementComparer() {
	
	                public boolean equals(Object a, Object b) {
	                    return (a == b);
	                }
	
	                public int hashCode(Object element) {
	                    return element == null ? 0
	                            : System.identityHashCode(element);
	                }
	            });
	
	            selectionViewer.setContentProvider(new MdfAdapterFactoryContentProvider(
	                    adapterFactory) {
	
	                public void notifyChanged(Notification notification) {
	                    super.notifyChanged(notification);
	
	                    // Make sure to refresh everything whatever happen...
	                    Runnable runnable = new RefreshRunnable(viewer);
	                    Display.getDefault().asyncExec(runnable);
	                }
	
	            });
	
	            ILabelProvider labelProvider = new AdapterFactoryLabelProvider(
	                    adapterFactory);
	
	            ILabelDecorator decorator = MdfPlugin.getDefault().getWorkbench().getDecoratorManager().getLabelDecorator();
	            selectionViewer.setLabelProvider(new MdfDecoratingLabelProvider(labelProvider, decorator));
	            sorter = getSorter();
	            selectionViewer.setSorter(sorter);
	            selectionViewer.setAutoExpandLevel(1);
	
	            selectionViewer.setInput(new DomainItemProvider(editedDomain));
	            selectionViewer.expandToLevel(editedDomain, 1);
	            
	            /*
	            selectionViewer.addOpenListener(new IOpenListener() {
	
	                public void open(OpenEvent event) {
	                    try {
	                    	IWorkbenchPage  aPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	                        aPage.showView("org.eclipse.ui.views.PropertySheet");
	                        aPage.activate(DomainModelEditor.this);
	                    } catch (PartInitException e) {
	                        LOGGER.error(e, e);
	                    }
	                }
	            });
	            */
	            
	            //add the double click listener
	            selectionViewer.addDoubleClickListener(new DomainModelDoubleClickListener());
	
	            createContextMenuFor(selectionViewer);
	            int pageIndex = addPage(tree);
	            setPageText(pageIndex, "Domain Editor");
	
	            setActivePage(pageIndex);
	            
	            // create other tab if required
	            createOtherDomainEditor();
	            editingDomain.getCommandStack().addCommandStackListener(new CommandStackListener() {
					@Override
					public void commandStackChanged(EventObject event) {
						Command command = ((BasicCommandStack)event.getSource()).getMostRecentCommand();
						MdfUtility.checkSysnchronizedDataSetCommands(command);
					}
				});
	        }
	
	        // Ensures that this editor will only display the page's tab
	        // area if there are more than one page
	        getContainer().addControlListener(new ControlAdapter() {
	
	            boolean guard = false;
	
	            public void controlResized(ControlEvent event) {
	                if (!guard) {
	                    guard = true;
	                    hideTabs();
	                    guard = false;
	                }
	            }
	        });
	
	        updateProblemIndication();
        } else {
        	createDSLPage();
        }
    }
 
	/**
	 * 
	 */
	protected  void createOtherDomainEditor() {
		// let the subclass implement if required		
	}

	private MdfElementSorter getSorter() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_ID);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
					extensions[i].getConfigurationElements();
				if(elements.length > 0) {
					try {
						if(sorter == null)
							sorter = (MdfElementSorter)elements[0].createExecutableExtension("class"); 
							return sorter;
					} catch (CoreException e) {
						LOGGER.error("Unable to get the T24 Model Attribute Sorter", e);
						if(sorter != null)
							return sorter;
						sorter = new MdfElementSorter();
						return sorter;
					}	
				}
			}			
			if(sorter != null)
				return sorter;
			sorter = new MdfElementSorter();
			return sorter;
	}

	private void createDSLPage() {
		try {
			dslEditor = new TextEditor();
			addPage(0, dslEditor, getEditorInput()); 
			setPageText(0, "DSL");
		} catch (CoreException ex) {
			ErrorDialog.openError(getSite().getShell(), "Error creating DSL Editor", null, ex.getStatus());
		}
	}
    
    public PrevSelectionStack getSelectionStack() {
    	return selectionStack;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProviderListener#labelProviderChanged(org.eclipse.jface.viewers.LabelProviderChangedEvent)
     */
    public void labelProviderChanged(LabelProviderChangedEvent event) {
    	if (selectionViewer != null) { // DS-5843
    		selectionViewer.refresh(true);
    	}
    }

    /**
     * If there is just one page in the multi-page editor part, this hides the
     * single tab at the bottom.
     */
    protected void hideTabs() {
        if (getPageCount() <= 1) {
            setPageText(0, "");
            if (getContainer() instanceof CTabFolder) {
                ((CTabFolder) getContainer()).setTabHeight(1);
                Point point = getContainer().getSize();
                getContainer().setSize(point.x, point.y + 6);
            }
        }
    }

    /**
     * If there is more than one page in the multi-page editor part, this shows
     * the tabs at the bottom.
     */
    protected void showTabs() {
        if (getPageCount() > 1) {
            setPageText(0, "Selection");
            if (getContainer() instanceof CTabFolder) {
                ((CTabFolder) getContainer()).setTabHeight(SWT.DEFAULT);
                Point point = getContainer().getSize();
                getContainer().setSize(point.x, point.y - 6);
            }
        }
    }

    /**
     * This is used to track the active viewer.
     */
    protected void pageChange(int pageIndex) {
        super.pageChange(pageIndex);

        if (contentOutlinePage != null) {
            handleContentOutlineSelection(contentOutlinePage.getSelection());
        }
    }

    /**
     * This is how the framework determines which interfaces we implement.
     */
    public Object getAdapter(Class key) {
    	if (key.equals(IContentOutlinePage.class)) {
            return showOutlineView() ? getContentOutlinePage() : null;
        } else if (key.equals(IPropertySheetPage.class)) {
            return getPropertySheetPage();
        } else if (key.equals(IGotoMarker.class)) {
            return this;
        } else if (key.equals(MdfDomain.class)) {
            return editedDomain;
        } else if (key.equals(Viewer.class)){
            return currentViewer;
        } else if (key.equals(CommandStack.class)){
        	return editingDomain.getCommandStack();
        } else {
            return super.getAdapter(key);
        }
    }

    /**
     * This accesses a cached version of the content outliner.
     */
    public IContentOutlinePage getContentOutlinePage() {
        if (contentOutlinePage == null) {
            // The content outline is just a tree.
            class MyContentOutlinePage extends ContentOutlinePage {

                public void createControl(Composite parent) {
                    super.createControl(parent);
                    contentOutlineViewer = getTreeViewer();
                    contentOutlineViewer.addSelectionChangedListener(this);

                    // Set up the tree viewer.
                    contentOutlineViewer.setContentProvider(new AdapterFactoryContentProvider(
                            adapterFactory));
                    contentOutlineViewer.setLabelProvider(new AdapterFactoryLabelProvider(
                            adapterFactory));
                    contentOutlineViewer.setInput(editedDomain);

                    // Make sure our popups work.
                    createContextMenuFor(contentOutlineViewer);

                    if (!editingDomain.getResourceSet().getResources().isEmpty()) {
                        // Select the root object in the view.
                        ArrayList selection = new ArrayList();
                        selection.add(editingDomain.getResourceSet().getResources().get(
                                0));
                        contentOutlineViewer.setSelection(
                                new StructuredSelection(selection), true);
                    }
                }

                public void makeContributions(IMenuManager menuManager,
                        IToolBarManager toolBarManager,
                        IStatusLineManager statusLineManager) {
                    super.makeContributions(menuManager, toolBarManager,
                            statusLineManager);
                    contentOutlineStatusLineManager = statusLineManager;
                }

                public void setActionBars(IActionBars actionBars) {
                    super.setActionBars(actionBars);
                    getActionBarContributor().shareGlobalActions(this,
                            actionBars);
                }
            }

            contentOutlinePage = new MyContentOutlinePage();

            // Listen to selection so that we can handle it is a special way.
            contentOutlinePage.addSelectionChangedListener(new ISelectionChangedListener() {

                // This ensures that we handle selections correctly.
                public void selectionChanged(SelectionChangedEvent event) {
                    handleContentOutlineSelection(event.getSelection());
                }
            });
        }

        return contentOutlinePage;
    }

    /**
     * This accesses a cached version of the property sheet.
     */
    public IPropertySheetPage getPropertySheetPage() {
        if (propertySheetPage == null) {
            propertySheetPage = new MdfPropertySheetPage(editingDomain) {

                public void setActionBars(IActionBars actionBars) {
                    super.setActionBars(actionBars);
                    getActionBarContributor().shareGlobalActions(this,
                            actionBars);
                }

				@Override
				public boolean isReadOnly() {
					return isModelReadOnly();
				}
                
                
            };
        }

        return propertySheetPage;
    }

    /**
     * This deals with how we want selection in the outliner to affect the other
     * views.
     */
    public void handleContentOutlineSelection(ISelection selection) {
        if (selectionViewer != null && !selection.isEmpty()
                && selection instanceof IStructuredSelection) {
            Iterator selectedElements = ((IStructuredSelection) selection).iterator();
            if (selectedElements.hasNext()) {
                // Get the first selected element.
                Object selectedElement = selectedElements.next();

                ArrayList selectionList = new ArrayList();
                selectionList.add(selectedElement);
                while (selectedElements.hasNext()) {
                    selectionList.add(selectedElements.next());
                }

                // Set the selection to the widget.
                selectionViewer.setSelection(new StructuredSelection(
                        selectionList));
            }
        }
    }

    /**
     * This is for implementing {@link IEditorPart} and simply tests the command
     * stack.
     */
    public boolean isDirty() {
    	if(textMode) {
    		return dslEditor.isDirty();
    	} else {
    		return ((BasicCommandStack) editingDomain.getCommandStack()).isSaveNeeded();
    	}
    }

    /**
     * This is for implementing {@link IEditorPart} and simply saves the model
     * file.
     */
    public void doSave(IProgressMonitor progressMonitor) {
    	
    	if(textMode) {
    		dslEditor.doSave(progressMonitor);
    	} else {   
    		IEditorInput input = getEditorInput();
	    	if (input instanceof ModelEditorInput) {
	    		IOfsModelResource resource = null;
				try {
					resource = (IOfsModelResource)((ModelEditorInput)input).getStorage();
		    		if (IOfsProject.SCOPE_DEPENDENCY == resource.getScope()) {
		    			// readonly model
		                ((BasicCommandStack) editingDomain.getCommandStack()).saveIsDone();
		                firePropertyChange(IEditorPart.PROP_DIRTY);
		                return;
		    		}
				} catch (CoreException e) {
					LOGGER.error(e, e); 
					return;
				}
	    	}
	    	
	    	if (input instanceof FileEditorInput) {
		        IFile file = ((FileEditorInput) getEditorInput()).getFile();
		        if (validateFileNotReadOnly(file) == false) {
		            return;
		        }
	    	}
	    	
	        if (propertySheetPage != null) {
	        	propertySheetPage.refresh();
	        	if(propertySheetPage.isDirty()) {
	        		propertySheetPage.savePages();
	        	}
	        }
	
	        // Do the work within an operation because this is a long running
	        // activity that modifies the workbench.
	        WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
	
	            // This is the method that gets invoked when the operation runs.
	            public void execute(IProgressMonitor monitor) {
	                // Save the resources to the file system.
	                Resource editedResource = editedDomain.eResource();
	                boolean saved = false;
	                try {
	                    savedResources.add(editedResource);                   
	                    editedResource.save(Collections.EMPTY_MAP);
	                    saved = true;
	                } catch (Exception exception) {
	                    resourceToDiagnosticMap.put(editedResource,
	                            analyzeResourceProblems(editedResource, exception));
	                    saved = false;
	                }
	                
	                if (saved) {
	                    // Revalidate the domain
	                	MdfMarkerUtils.updateMarkers(editedResource, monitor);
	                }
	            }
	        };
	
	        updateProblemIndication = false;
	        try {
	            // This runs the options, and shows progress.
	       		new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
	
	            // Refresh the necessary state.
	            ((BasicCommandStack) editingDomain.getCommandStack()).saveIsDone();
	            firePropertyChange(IEditorPart.PROP_DIRTY);
	        } catch (Exception exception) {
	            // Something went wrong that shouldn't.
	            LOGGER.error(exception, exception);
	        }
	        updateProblemIndication = true;
	        updateProblemIndication();
    	}
    }

    
    /**
     * This returns wether something has been persisted to the URI of the
     * specified resource. The implementation uses the URI converter from the
     * editor's resource set to try to open an input stream.
     */
    protected boolean isPersisted(Resource resource) {
        boolean result = false;
        try {
            InputStream stream = editingDomain.getResourceSet().getURIConverter().createInputStream(
                    resource.getURI());
            if (stream != null) {
                result = true;
                stream.close();
            }
        } catch (IOException e) {
        }
        return result;
    }

    /**
     * This always returns true because it is not currently supported.
     */
    public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     * This also changes the editor's input.
     */
    public void doSaveAs() {
        SaveAsDialog saveAsDialog = new SaveAsDialog(getSite().getShell());
        saveAsDialog.open();
        IPath path = saveAsDialog.getResult();
        if (path != null) {
            IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            if (file != null) {
                doSaveAs(URI.createPlatformResourceURI(
                        file.getFullPath().toString(), true),
                        new FileEditorInput(file));
            }
        }
    }

    protected void doSaveAs(URI uri, IEditorInput editorInput) {
        editedDomain.eResource().setURI(uri);
        setInputWithNotify(editorInput);
        setPartName(editorInput.getName());
        IProgressMonitor progressMonitor = getActionBars().getStatusLineManager() != null ? getActionBars().getStatusLineManager().getProgressMonitor()
                : new NullProgressMonitor();
        doSave(progressMonitor);
    }

    public void gotoMarker(IMarker marker) {
        try {
            if (marker.exists() && marker.isSubtypeOf(EValidator.MARKER)) {
                String uriAttribute = marker.getAttribute(EValidator.URI_ATTRIBUTE, null);
                if (uriAttribute != null) {
                    URI uri = null;
                	if (isMdfNameURI(uriAttribute)) {
                    	MdfName qname = MdfNameFactory.createMdfName(uriAttribute);
    					try {
    						uri = MdfNameURIUtil.createURI(qname);
    					} catch (IllegalArgumentException illegalArg) {
    						 LOGGER.error("URI is null", illegalArg);
    					}
                	} else {
                		uri = URI.createURI(uriAttribute);
                	}
                    if (uri != null) {
						EObject eObject = editingDomain.getResourceSet().getEObject(uri, true);
						if (eObject != null) {
							setSelectionToViewer(Collections.singleton(editingDomain.getWrapper(eObject)));
						}
					}
                }
            }
        } catch (CoreException exception) {
            LOGGER.error(exception, exception);
        }
    }
    
    private boolean isMdfNameURI(String uriAttribute) {
    	URI uri = URI.createURI(uriAttribute);
    	if (MdfNameURIUtil.isMdfNameURI(uri)) {
    		return true;
    	}
    	return false;
    }

    /**
     * This is called during startup.
     */
    public void init(IEditorSite site, IEditorInput editorInput) {
        setSite(site);
        setInputWithNotify(editorInput);
        setPartName(editorInput.getName());
        site.setSelectionProvider(this);
        site.getPage().addPartListener(partListener);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(
                resourceChangeListener, IResourceChangeEvent.POST_CHANGE); 
        IContextService contextService = (IContextService) getSite().getService(IContextService.class);
		if (contextService != null) {
			contextService.activateContext("com.odcgroup.mdf.editor.context");
		}
    }

    public void setFocus() {
        getControl(getActivePage()).setFocus();
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
     */
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        selectionChangedListeners.add(listener);
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
     */
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        selectionChangedListeners.remove(listener);
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to
     * return this editor's overall selection.
     */
    public ISelection getSelection() {
        return editorSelection;
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to
     * set this editor's overall selection. Calling this result will notify the
     * listeners.
     */
    public void setSelection(ISelection selection) {
        editorSelection = selection;

        for (Iterator listeners = selectionChangedListeners.iterator(); listeners.hasNext();) {
            ISelectionChangedListener listener = (ISelectionChangedListener) listeners.next();
            listener.selectionChanged(new SelectionChangedEvent(this, selection));
        }
        setStatusLineManager(selection);
    }

    public void setStatusLineManager(ISelection selection) {
        IStatusLineManager statusLineManager = currentViewer != null
                && currentViewer == contentOutlineViewer ? contentOutlineStatusLineManager
                : getActionBars().getStatusLineManager();

        if (statusLineManager != null) {
            if (selection instanceof IStructuredSelection) {
                Collection collection = ((IStructuredSelection) selection).toList();
                switch (collection.size()) {
                    case 0: {
                        statusLineManager.setMessage("Selected Nothing");
                        break;
                    }
                    case 1: {
                    	Object select = collection.iterator().next();
                    	String text = null;
                    	if (select instanceof MdfReverseAssociationWrapper) {
                    		text = ((MdfReverseAssociationWrapper) select).getName();
                    	} else {
                    		text = new AdapterFactoryItemDelegator(
                                    adapterFactory).getText(select);
                    	}
                        
                        statusLineManager.setMessage("Selected Object:" + text);
                        break;
                    }
                    default: {
                        statusLineManager.setMessage("Selected "
                                + Integer.toString(collection.size())
                                + " Objects");
                        break;
                    }
                }
            } else {
                statusLineManager.setMessage("");
            }
        }
    }

    /**
     * This implements {@link org.eclipse.jface.action.IMenuListener} to help
     * fill the context menus with contributions from the Edit menu.
     */
    public void menuAboutToShow(IMenuManager menuManager) {
        ((IMenuListener) getEditorSite().getActionBarContributor()).menuAboutToShow(menuManager);
    }

    public EditingDomainActionBarContributor getActionBarContributor() {
        return (EditingDomainActionBarContributor) getEditorSite().getActionBarContributor();
    }

    public IActionBars getActionBars() {
        return getActionBarContributor().getActionBars();
    }

    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    public void dispose() {
        updateProblemIndication = false;

        ResourcesPlugin.getWorkspace().removeResourceChangeListener(
                resourceChangeListener);

        getSite().getPage().removePartListener(partListener);

        adapterFactory.dispose();

        if (getActionBarContributor().getActiveEditor() == this) {
            getActionBarContributor().setActiveEditor(null);
        }

        if (propertySheetPage != null) {
            propertySheetPage.dispose();
            propertySheetPage = null;
        }

        if (contentOutlinePage != null) {
            contentOutlinePage.dispose();
            contentOutlinePage = null;
        }
        
        editedDomain = null;
        editorSelection = StructuredSelection.EMPTY;

        super.dispose();
    }

    /**
     * Returns whether the outline view should be presented to the user.
     */
    protected boolean showOutlineView() {
        return false;
    }
    
    // TODO SAM: Grabbed from Page designer -> Make it util (Kai)
    private boolean validateFileNotReadOnly(IFile file) {
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
        	LOGGER.error("Unable to modify the file", ce);
        	return false;
        }
    }
    
	private boolean fileHasSyntaxErrors() {
		try {
			IEditorInput editorInput = getEditorInput();
			if (editorInput instanceof IFileEditorInput) {
				IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
				IFile file = fileEditorInput.getFile();
				String filePath = file.getFullPath().toString();
				URI uri = URI.createPlatformResourceURI(filePath, true);
				ResourceSet rs = editingDomain.getResourceSet();
				try {
					 rs.getResource(uri, true);
					return false;
				} catch (Exception e) {
					LOGGER.error("Failed to load content from "+uri.toString(), e);
					return true;
				}
			}
 			if(editorInput instanceof IStorageEditorInput) {
 				IStorageEditorInput storageEditorInput = (IStorageEditorInput)editorInput;
 				Resource resource = getEditedDomain(storageEditorInput).eResource();
 				if (resource instanceof AbstractDSLResource) {
 					AbstractDSLResource domainResource = (AbstractDSLResource) resource;
 					return modelHasErrors(domainResource);
 				}
 				else {
 					return true;
 				}
 			}
		} catch(NoClassDefFoundError e) {
			// the com.odcgroup.workbench.validation plugin is optional, so we have to catch this exception
		} catch (CoreException e) {
			return true;
		}
		return false;
	}

	private boolean modelHasErrors(AbstractDSLResource modelResource) {
		return false; // DS-6462
// DS-6462	Collection<Resource.Diagnostic> syntaxErrors = ValidationUtil.validateResourceSyntax(modelResource);
// DS-6462	return syntaxErrors.size()>0;
	}

    /**
     * Double click listener for the tree viewer
     *
     */
    class DomainModelDoubleClickListener implements IDoubleClickListener {	
    	
    	MdfModelElement selectedElement = null;
    	
        /**
         * Shows a dialog that asks if conflicting changes should be discarded.
         */
        private void handleUnresolvableReferece() {
	        MessageDialog.openInformation(getSite().getShell(), "Unresolvable Association", 
	        		"Please clean build the project first, so that references can be resolved.");
        }
    	
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
		 */
		public void doubleClick(DoubleClickEvent event) {					
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			MdfModelElement object = (MdfModelElement)selection.getFirstElement();
			
			final MdfModelElement reference = MdfTreeItemReferenceProvider.getTreeItemReference(object);			
			
			if (reference == null || reference instanceof MdfPrimitive && 
					!(reference instanceof MdfEnumeration || reference instanceof MdfBusinessType)){
				return;
			}
			
			if (reference.getQualifiedName() == null) { // DS-8340
				handleUnresolvableReferece();
		        return;
			}
			
			MdfDomain domain = MdfUtility.getDomain(object);
			QualifiedName name = QualifiedName.create(reference.getQualifiedName().getDomain());
			Resource res = ((EObject) domain).eResource();
			MdfDomainImpl refDomain = (MdfDomainImpl) DomainRepositoryUtil.getMdfDomain(res, name);
			
			if (refDomain == null) { // DS-3043, DS-8340
				handleUnresolvableReferece();
				return; 
			}
			if (reference != null && domain.equals(refDomain)){
				selectionViewer.setSelection(new StructuredSelection(reference), true);
				selectionViewer.setExpandedState(reference, true);
				
				// if not matching item found, select the double-clicked item
				if(selectionViewer.getSelection().equals(StructuredSelection.EMPTY)){
					selectionViewer.setSelection(new StructuredSelection(object), true);
				} else {
					// add the double-clicked element to stack
					selectionStack.addSelection(object);
				}
			} else if (reference != null && !domain.equals(refDomain)) {
				// if the domain is different, open the referenced domain
				DomainModelEditor ep = (DomainModelEditor) OfsEditorUtil.openEditor(refDomain.eResource());						
				if (ep != null) {
					MdfDomain md = (MdfDomain) ep.getAdapter(MdfDomain.class);
					ModelVisitor mv = new ModelVisitor() {
						public boolean accept(MdfModelElement model) {
							if (model.getQualifiedName().equals(reference.getQualifiedName())) {
								selectedElement = model;
								return false;
							}
							return true;
						}
					};
					ModelWalker mw = new ModelWalker(mv);
					mw.visit(md);
				}

				if(selectedElement!=null) {
					// if the element exists inside the domain, show it now and expand the node
					TreeViewer treeViewer = (TreeViewer) ep.getViewer();
					treeViewer.setSelection(new StructuredSelection(selectedElement), true);
					treeViewer.setExpandedState(selectedElement, true);
				}
			}
		}
    }
    
    /**
     * stack implementation to remember the navigations in the treeviewer
     */
    public class PrevSelectionStack {
    	
    	List<MdfModelElement> selectionList;
    	int top;
    	MdfModelElement mostRecentSelection;
    	
    	/**
    	 * 
    	 */
    	public PrevSelectionStack() {
    		selectionList = new ArrayList<MdfModelElement>();
    		top = -1;
    	}
    	
    	/**
    	 * @param selection
    	 */
    	public void addSelection(MdfModelElement selection) {
    		if (selection != null){ 
    			try {
	    			Iterator<MdfModelElement> selections = selectionList.listIterator(top + 1);
	    			while(selections.hasNext()){
	    				selections.remove();
	    			}
	    			mostRecentSelection = selection;
	    			selectionList.add(selection);
	    			++top;
    			} catch (Exception e){
    				//donothing
    			}
    		}
    	}
    	
    	/**
    	 * @return
    	 */
    	public MdfModelElement getPrevSelection() {
    		if (top > -1){
	    		MdfModelElement selection = selectionList.get(top--);
	    		mostRecentSelection = selection;
	    		selectionList.remove(selection);
	    		return selection;
    		}
    		return null;
    	}
    	
    }
    
    /**
     * ResourceDeltaVisitor implementation
     */
    class ResourceDeltaVisitor implements IResourceDeltaVisitor {

        protected ResourceSet resourceSet = editingDomain.getResourceSet();
        protected Collection changedResources = new ArrayList();
        protected Collection removedResources = new ArrayList();

        public boolean visit(IResourceDelta delta) {
            if (delta.getFlags() != IResourceDelta.MARKERS
                    && delta.getResource().getType() == IResource.FILE) {
                if ((delta.getKind() & (IResourceDelta.CHANGED | IResourceDelta.REMOVED)) != 0) {
                	URI uri = ModelURIConverter.createModelURI(delta.getResource());
                    Resource resource = resourceSet.getResource(uri, false);
                    if (resource != null) {
                        if ((delta.getKind() & IResourceDelta.REMOVED) != 0) {
                            removedResources.add(resource);
                        } else if (!savedResources.isEmpty() & !savedResources.remove(resource)) {
                            changedResources.add(resource);
                        }
                    }
                }
            }

            return true;
        }

        public Collection getChangedResources() {
            return changedResources;
        }

        public Collection getRemovedResources() {
            return removedResources;
        }
    }
    
    
    /**
     * 
     * DomainTreeViewer implementation for getting filtered list of domain
     *
     */
	class DomainTreeViewer extends TreeViewer {

		public DomainTreeViewer(Tree tree) {
			super(tree);
		}
        
		@Override
		public void refresh() {
			super.refresh();
			checkFilteredDomains();
		}

		public void checkFilteredDomains() {
			int filteredCount = getFilteredChildren(editedDomain).length;
			if (filteredCount == 0) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						getEditorSite()
								.getActionBars()
								.getStatusLineManager()
								.setMessage(Display.getDefault().getSystemImage(SWT.ICON_WARNING),
										DOMAINS_HIDDEN_BY_FILTERS_MSG);
					}
				});
			} else {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						getEditorSite()
								.getActionBars()
								.getStatusLineManager()
								.setMessage(null);
					}
				});
			}
		}

	}

}

