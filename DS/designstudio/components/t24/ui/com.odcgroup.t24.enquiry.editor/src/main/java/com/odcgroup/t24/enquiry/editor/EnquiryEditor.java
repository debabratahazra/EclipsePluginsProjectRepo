package com.odcgroup.t24.enquiry.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.enquiry.editor.dnd.FieldTransferDragSourceListener;
import com.odcgroup.t24.enquiry.editor.dnd.FieldTransferDropTargetListener;
import com.odcgroup.t24.enquiry.editor.menu.EnquiryEditorContextMenuProvider;
import com.odcgroup.t24.enquiry.editor.part.EnquiryEditPartFactory;
import com.odcgroup.t24.enquiry.editor.part.actions.AddCustomSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddDrillDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddFieldAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddHeaderAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddItemAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddJBCRoutineAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddJavaRoutineAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddTargetAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddToolAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddWebServiceAction;
import com.odcgroup.t24.enquiry.editor.part.actions.ColumnFieldMoveLeftAction;
import com.odcgroup.t24.enquiry.editor.part.actions.ColumnFieldMoveRightAction;
import com.odcgroup.t24.enquiry.editor.part.actions.CopyCustomSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.CopyFieldAction;
import com.odcgroup.t24.enquiry.editor.part.actions.CopyFixedSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.FieldMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.FieldMoveUpAction;
import com.odcgroup.t24.enquiry.editor.part.actions.PasteCustomSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.PasteFieldAction;
import com.odcgroup.t24.enquiry.editor.part.actions.PasteFixedSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.RoutineMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.RoutineMoveUpAction;
import com.odcgroup.t24.enquiry.editor.part.actions.SelectionMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.SelectionMoveUpAction;
import com.odcgroup.t24.enquiry.editor.part.actions.TargetMappingMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.TargetMappingMoveUpAction;
import com.odcgroup.t24.enquiry.editor.part.actions.ToolMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.ToolMoveUpAction;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.properties.EnquiryTabbedPropertySheetPage;

/**
 * 
 * @author phanikumark
 * 
 */
public class EnquiryEditor extends GraphicalEditor implements ITabbedPropertySheetPageContributor {

	private Enquiry model = null;
	protected boolean changeFromOtherTab;
    private Logger logger = LoggerFactory.getLogger(EnquiryEditor.class);

	private URI resourceURI = null;
	private Resource resource;
	private ResourceSet rs;
	/**
	 * Resources that have been changed since last activation.
	 */
	protected Collection<Resource> changedResources = new ArrayList<Resource>();

	/**
	 * Resources that have been saved.
	 */
	protected Collection<Resource> savedResources = new ArrayList<Resource>();
	
	/**
	 * the tabbed property sheet page for displaying properties of the selected editpart
	 */
	private TabbedPropertySheetPage tabbedPropertySheetPage;

	public EnquiryEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		this.tabbedPropertySheetPage = new EnquiryTabbedPropertySheetPage(this, getCommandStack());
		viewer.setContents(getModel());
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	/**
	 * This listens for workspace changes.
	 */
	protected IResourceChangeListener resourceChangeListener = new IResourceChangeListener() {
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			try {
				class ResourceDeltaVisitor implements IResourceDeltaVisitor {
					protected Collection<Resource> modResources = new ArrayList<Resource>();

					public boolean visit(IResourceDelta delta) {
						if (delta.getResource().getType() == IResource.FILE) {
							if (delta.getKind() == IResourceDelta.REMOVED
									|| delta.getKind() == IResourceDelta.CHANGED
									&& delta.getFlags() != IResourceDelta.MARKERS) {								
								URI uri = URI.createPlatformResourceURI(((IResource)delta.getResource()).getFullPath().toString(), true);
								Resource resource = rs.getResource(uri, false);
								if (resource != null) {
									if (!savedResources.remove(resource)) {
										if (uri.equals(resourceURI)) {
											modResources.add(resource);
										} 
									}
								}
							}
						}

						return true;
					}

					public Collection<Resource> getChangedResources() {
						return modResources;
					}
				}

				final ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
				delta.accept(visitor);

				if (!visitor.getChangedResources().isEmpty()) {
					getSite().getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							changedResources.addAll(visitor.getChangedResources());
							handleActivate();
						}
					});
				}
			} catch (CoreException exception) {
				logger.error(exception.getLocalizedMessage(), exception);
			}
		}
	};
	


	/**
	 * Handles activation of the editor or it's associated views.
	 */
	protected void handleActivate() {
		if (!changedResources.isEmpty()) {
			changedResources.removeAll(savedResources);
			handleChangedResources();
			changedResources.clear();
			savedResources.clear();
		}
	}	

	/**
	 * Handles what to do with changed resources on activation.
	 */
	protected void handleChangedResources() {
		if (!changedResources.isEmpty() && changeFromOtherTab) {
	    	getCommandStack().flush();  
	    	for(Resource resource : changedResources) {
	            if (resource.isLoaded()) {
	                resource.unload();
	            }
				try {
					resource.load(Collections.EMPTY_MAP);
					model = (Enquiry) resource.getContents().iterator().next();
					getGraphicalViewer().setContents(model);
					getGraphicalViewer().getRootEditPart().activate();
				} catch (IOException exception) {
					logger.error(exception.getLocalizedMessage(), exception);
				}
			}		
    		changeFromOtherTab = false;
    	}
	}

	@Override
 	protected void setInput(IEditorInput input) {
		super.setInput(input);		
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileInput = (IFileEditorInput) input;
			IFile file = fileInput.getFile();
			String filePath = file.getFullPath().toString();
			resourceURI = URI.createPlatformResourceURI(filePath, true);
			rs = new ResourceSetImpl();
			try {
				// Load the resource through the editing domain
				resource = rs.getResource(resourceURI, true);
				model = (Enquiry) resource.getContents().iterator().next();
			} catch (Exception e) {
				logger.error("Failed to load content from "+resourceURI.toString(), e);
			}
		} 
		
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if (type.equals(IPropertySheetPage.class)) {
			return tabbedPropertySheetPage;
		}
		return super.getAdapter(type);
	}

	/**
	 * @return the model
	 */
	public Enquiry getModel() {
		return model;
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new EnquiryEditPartFactory());
		ContextMenuProvider provider = new EnquiryEditorContextMenuProvider(viewer,
				getActionRegistry());
		viewer.setContextMenu(provider);
		viewer.addDragSourceListener(new FieldTransferDragSourceListener(viewer));
		viewer.addDropTargetListener(new FieldTransferDropTargetListener(viewer) {
		});
		getSite().registerContextMenu(provider, viewer);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
	
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				if (resource != null) {
					try {
						savedResources.add(resource);
						resource.save(saveOptions);
						getCommandStack().markSaveLocation();
						firePropertyChange(PROP_DIRTY);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(runnable, null);
		} catch (CoreException e) {
			logger.error("Resource Save error", e);
		}
	}

	@Override
	public String getContributorId() {
		return "com.odcgroup.t24.enquiry.editor.enquiryEditor";
	}

	@SuppressWarnings("unchecked")
	private void registerAction(IAction newAction){
        getActionRegistry().registerAction(newAction);
        getSelectionActions().add(newAction.getId());
	}

	@Override
	protected void createActions() {
		super.createActions();
		
		/* Add Predefined Selection action */
		registerAction(new AddItemAction(this));

		/* Add Custom Selection action */
		registerAction(new AddCustomSelectionAction(this));

		/* Add Field Action */
		registerAction(new AddFieldAction(this));

		/* Move Selection Field Down Action */
		registerAction(new SelectionMoveDownAction(this));

		/* Move Selection Field Up Action */
		registerAction(new SelectionMoveUpAction(this));

		/* Move Column Field Left Action */
		registerAction(new ColumnFieldMoveLeftAction(this));

		/* Move Column Field Right Action */
		registerAction(new ColumnFieldMoveRightAction(this));
		
		/* Add Tool action */
		registerAction(new AddToolAction(this));
		
		/* Add Java Routine action */
		registerAction(new AddJavaRoutineAction(this));

		/* Add JBC Routine action */
		registerAction(new AddJBCRoutineAction(this));

		/* Add Target action */
		registerAction(new AddTargetAction(this));
		
		/* Add Web Service action */
		registerAction(new AddWebServiceAction(this));

		/* Move Tool Up action */
		registerAction(new ToolMoveUpAction(this));

		/* Move Tool Down action */
		registerAction(new ToolMoveDownAction(this));

		/* Move Tool Up action */
		registerAction(new RoutineMoveUpAction(this));

		/* Move Tool Down action */
		registerAction(new RoutineMoveDownAction(this));

		/* Move Tool Up action */
		registerAction(new TargetMappingMoveUpAction(this));

		/* Move Tool Down action */
		registerAction(new TargetMappingMoveDownAction(this));

		/* Add Header action */
		registerAction(new AddHeaderAction(this));

		/* Add Drill Down action */
		registerAction(new AddDrillDownAction(this));
		
		/*Copy Fixed Selection */
		registerAction(new CopyFixedSelectionAction(this));

		/*Paste Fixed Selection */
		registerAction(new PasteFixedSelectionAction(this));

		/*Copy Custom Selection */
		registerAction(new CopyCustomSelectionAction(this));

		/*Paste Custom Selection */
		registerAction(new PasteCustomSelectionAction(this));

		/*Copy Custom Selection */
		registerAction(new CopyFieldAction(this));

		/*Paste Custom Selection */
		registerAction(new PasteFieldAction(this));
		
		/*Field Move Up Action */
		registerAction(new FieldMoveUpAction(this));
		
		/*Field Move Down Action */
		registerAction(new FieldMoveDownAction(this));

	}
	
	@Override
	public void dispose() {
		if(model != null && model.eResource() != null){
			model.eResource().unload();
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		updateActions(getSelectionActions());
	}

}
