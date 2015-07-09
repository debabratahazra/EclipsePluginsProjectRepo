package com.odcgroup.workbench.ui.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.internal.events.ResourceChangeEvent;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyChangeListener;
import com.odcgroup.workbench.ui.OfsUICore;
import com.odcgroup.workbench.ui.action.AbstractScopeToggleAction;

public class OfsContentProvider implements ITreeContentProvider, IPropertyChangeListener, IResourceChangeListener, ILabelProviderListener, IDependencyChangeListener {

	private static Logger logger = LoggerFactory.getLogger(OfsContentProvider.class);

	private StructuredViewer viewer;
	
	private int scope;
	
	protected int getScope() {
		return scope;
	}
	
	
	/**
	 * Create the OfsContentProvider instance.
	 * 
	 * Adds the content provider as a resource change listener to track changes on disk.
	 *
	 */
	public OfsContentProvider() {
        OfsUICore.getDefault().getPreferenceStore().addPropertyChangeListener(this);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this, ResourceChangeEvent.POST_CHANGE);
        PlatformUI.getWorkbench().getDecoratorManager().addListener(this);
        OfsCore.getDependencyManager().addDependencyChangeListener(this);
        IPreferenceStore store = OfsUICore.getDefault().getPreferenceStore();
		scope = store.getInt(AbstractScopeToggleAction.PREFERENCE_KEY);
	}

	public Object getParent(Object element) {

		if(element instanceof IOfsModelResource) {
			return ((IOfsModelResource) element).getParent();
		}

		if(element instanceof IOfsModelPackage) {
			IOfsModelPackage ofsPackage = (IOfsModelPackage) element;
			return ofsPackage.getParent();
		}

		if(element instanceof IOfsModelFolder) {
			IOfsModelFolder modelFolder = (IOfsModelFolder) element;
			return modelFolder.getOfsProject().getProject();
		}
		
		if(element instanceof IOfsProject) {
			IOfsProject ofsProject = (IOfsProject) element;
			return ofsProject.getProject().getParent();
		}
		
		return null;
	}

	public boolean hasChildren(Object element) {

		if (element instanceof IOfsModelResource) {
			return false;
		}
		
		boolean hasChildren = false;

		if(element instanceof JavaProject) {
			element = ((JavaProject) element).getProject();
		}
		
		if(element instanceof IProject) {
			IProject project = (IProject) element;
			if(OfsCore.isOfsProject(project)) {
				IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
				hasChildren = ofsProject.hasModelFolders();
			}
			return hasChildren;
		}
		
		if(element instanceof IOfsModelFolder) {
			IOfsModelFolder modelFolder = (IOfsModelFolder) element;
			// DS-6488 navigator refresh, DS-6641 - DS performance
			hasChildren = ! modelFolder.getPackages(getScope()).isEmpty();
			if (!hasChildren) {
				hasChildren = checkChildrenAfterRefresh(modelFolder);
			}
			
			return hasChildren;
		}
		
		if(element instanceof IOfsModelPackage) {
			IOfsModelPackage modelPackage = (IOfsModelPackage) element;

			hasChildren = ! modelPackage.getModels(getScope()).isEmpty();
			if (!hasChildren) {
				hasChildren = checkChildrenAfterRefresh(modelPackage);
			}
			if (!hasChildren) {
				hasChildren = ! modelPackage.getSubPackages(getScope()).isEmpty();
			}
		}
		return hasChildren;
	}
	
	/**
	 * @param modelElement
	 * @return
	 */
	private boolean checkChildrenAfterRefresh(IOfsElement modelElement) {
		try {
			if (modelElement.getResource() != null) {
				modelElement.getResource().refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			}
			if(modelElement instanceof IOfsModelPackage){
				return !((IOfsModelPackage)modelElement).getModels(getScope()).isEmpty();
			}else if(modelElement instanceof IOfsModelFolder) {
				return !((IOfsModelFolder)modelElement).getPackages(getScope()).isEmpty();
			}
		} catch (CoreException e) {
		}
		return false;
	}
	
	
	public Object[] getElements(Object inputElement) {
		return this.getChildren(inputElement);
	}
	

	public Object[] getChildren(Object parentElement) {
				
		if(parentElement instanceof JavaProject) {
			parentElement = ((JavaProject) parentElement).getProject();
		}
		
		if(parentElement instanceof IProject) {
			IProject project = (IProject) parentElement;
			List<Object> children = new ArrayList<Object>();
			if(OfsCore.isOfsProject(project)) {
				IOfsProject ofsProject = 
					OfsCore.getOfsProjectManager().getOfsProject(project);
				for(IOfsModelFolder folder : ofsProject.getAllModelFolders()) {
					children.add(folder);
				}
			}
			return children.toArray();
		}
		
		if(parentElement instanceof IOfsModelFolder) {
			IOfsModelFolder modelFolder = (IOfsModelFolder) parentElement;
			return modelFolder.getPackages(getScope()).toArray();
		}
		
		if(parentElement instanceof IOfsModelPackage) {
			IOfsModelPackage modelPackage = (IOfsModelPackage) parentElement;
			List<Object> children = new ArrayList<Object>();
			for(Object obj : modelPackage.getModels(getScope())) {
				children.add(obj);
			}
			for(Object obj : modelPackage.getSubPackages(getScope())) {
				children.add(obj);
			}
			return children.toArray();
		}
		
		return new Object[0];
	}
	
	public void dispose() {
        OfsUICore.getDefault().getPreferenceStore().removePropertyChangeListener(this);
        PlatformUI.getWorkbench().getDecoratorManager().removeListener(this);
        OfsCore.getDependencyManager().removeDependencyChangeListener(this);
        viewer = null;
	}

	public void inputChanged(Viewer aViewer, Object oldInput, Object newInput) {
		viewer = (StructuredViewer) aViewer;
	}
	
	public void propertyChange(PropertyChangeEvent event) {
        if(event.getProperty().equals(AbstractScopeToggleAction.PREFERENCE_KEY)) {
	        IPreferenceStore store = OfsUICore.getDefault().getPreferenceStore();
			scope = store.getInt(AbstractScopeToggleAction.PREFERENCE_KEY);
        	scheduleUpdates(Collections.singleton(getRefreshRunnable(ResourcesPlugin.getWorkspace().getRoot())));
        }
	}
	
	@Override
	public void labelProviderChanged(LabelProviderChangedEvent event) {
		final Object finalElement = event.getElement();
		if(finalElement instanceof IAdaptable) {
			Object adapted = ((IAdaptable) finalElement).getAdapter(IOfsElement.class);
			if(adapted != null) {
				scheduleUpdates(Collections.singleton(getUpdateRunnable(adapted)));
			}
		}
	}

/*
	private class DumpResourceDeltaVisitor implements IResourceDeltaVisitor {
		private void dump(IResourceDelta delta) {
			int kind = delta.getKind();
			int flags = delta.getFlags();
			StringBuffer sb = new StringBuffer();

			sb.append(delta.getResource().getFullPath());

			switch (kind) {
			case IResourceDelta.NO_CHANGE:
				sb.append(" NO_CHANGE");
				break;
			case IResourceDelta.ADDED:
				sb.append(" ADDED");
				break;
			case IResourceDelta.REMOVED:
				sb.append(" REMOVED");
				break;
			case IResourceDelta.CHANGED:
				sb.append(" CHANGED");
				break;
			case IResourceDelta.ADDED_PHANTOM:
				sb.append(" ADDED_PHANTOM");
				break;
			case IResourceDelta.REMOVED_PHANTOM:
				sb.append(" REMOVED_PHANTOM");
				break;
			}

			if ((flags & IResourceDelta.CONTENT) != 0) {
				sb.append(" CONTENT");
			}
			if ((flags & IResourceDelta.MOVED_FROM) != 0) {
				sb.append(" MOVED_FROM");
			}
			if ((flags & IResourceDelta.MOVED_TO) != 0) {
				sb.append(" MOVED_TO");
			}
			if ((flags & IResourceDelta.OPEN) != 0) {
				sb.append(" OPEN");
			}
			if ((flags & IResourceDelta.TYPE) != 0) {
				sb.append(" TYPE");
			}
			if ((flags & IResourceDelta.SYNC) != 0) {
				sb.append(" SYNC");
			}
			if ((flags & IResourceDelta.MARKERS) != 0) {
				sb.append(" MARKERS");
			}
			if ((flags & IResourceDelta.REPLACED) != 0) {
				sb.append(" REPLACED");
			}
			if ((flags & IResourceDelta.DESCRIPTION) != 0) {
				sb.append(" DESCRIPTION");
			}
			if ((flags & IResourceDelta.ENCODING) != 0) {
				sb.append(" ENCODING");
			}

			logger.debug(sb.toString());
		}

		public boolean visit(final IResourceDelta delta) throws CoreException {
			dump(delta);
			return true;
		}
	}
*/
	
	private class ResourceDeltaVisitor implements IResourceDeltaVisitor {
		
		private Collection<Runnable> runnables = new ArrayList<Runnable>();
		
		public ResourceDeltaVisitor(Collection<Runnable> runnables) {
			this.runnables = runnables;
		}
		
		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			boolean visitChildren = true;
			int flags = delta.getFlags();
			int kind = delta.getKind();
			IResource resource = delta.getResource();
			switch (kind) {
				case IResourceDelta.REMOVED: 
				case IResourceDelta.ADDED: {
					IContainer parent = resource.getParent();
					if (parent != null) {
						Object ofsElement = parent.getAdapter(IOfsElement.class);
						if (ofsElement != null) {
							if (kind == IResourceDelta.REMOVED) {
								// DS-6268 - update the model (sync issue)
								if (ofsElement instanceof IOfsModelFolder) {
									((IOfsModelFolder)ofsElement).removePackage(resource.getName());
								} else if (ofsElement instanceof IOfsModelPackage) {
									((IOfsModelPackage)ofsElement).removePackage(resource.getName());
								}
							} else if (kind == IResourceDelta.ADDED && resource instanceof IFolder) {
								if (ofsElement instanceof IOfsModelFolder) {
									((IOfsModelFolder)ofsElement).addPackage((IFolder)resource);
								} else if (ofsElement instanceof IOfsModelPackage) {
									((IOfsModelPackage)ofsElement).addPackage((IFolder)resource);
								}
							}
							runnables.add(getRefreshRunnable(ofsElement));
						}
					}
					break;
				}
				
				case IResourceDelta.CHANGED: {
					boolean isFile = IResource.FILE == resource.getType();
					if((flags & IResourceDelta.CONTENT) != 0) {
						if (isFile) {
							Object ofsElement = resource.getAdapter(IOfsElement.class);
							final Object element = ofsElement == null ? resource : ofsElement;
							runnables.add(getUpdateRunnable(element));
						} 
					} else if ((flags & IResourceDelta.MARKERS) != 0) {
						// Refresh DS-Navigator DS-7084, DS-7245
						if(viewer != null && !(viewer.getControl().isDisposed()) && viewer instanceof AbstractTreeViewer){
							final Control control = ((AbstractTreeViewer) viewer).getControl();
							control.getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									// Double check for Widget dispose.
									if(control != null && !control.isDisposed()  && viewer instanceof AbstractTreeViewer){
										((AbstractTreeViewer) viewer).refresh(true);
									}
								}
							});
						}
					}
					
					visitChildren = !isFile;
					break;
				}
				
				default:
					break;
			}
			return visitChildren;
		}
	}


	@Override
	public final void resourceChanged(final IResourceChangeEvent event) {

		if (viewer == null) {
			// the input has not yet been defined, or this provider has been disposed.
			return;
		}
		
		IResourceDelta delta = event.getDelta();
		if (delta != null) {
/*
			if (logger.isDebugEnabled()) {
				try {
					delta.accept(new DumpResourceDeltaVisitor());
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
*/		
			final Collection<Runnable> runnables = new ArrayList<Runnable>();
			try {
				delta.accept(new ResourceDeltaVisitor(runnables));
			} catch (CoreException ex) {
				// ignore
			}
			if (!runnables.isEmpty()) {
				scheduleUpdates(runnables);
			}
		}
	}

	/**
	 * @param control
	 * @param runnables
	 */
	private void scheduleUpdates(final Collection<Runnable> runnables) {
		final Control control = viewer.getControl();
		if (control == null || control.isDisposed()) {
			return;
		}
		//Are we in the UIThread? If so spin it until we are done
		if (control.getDisplay().getThread() == Thread.currentThread()) {
			runUpdates(runnables);
		} else {
			control.getDisplay().asyncExec(new Runnable(){
				@Override
				public void run() {
					// Double Check for Widget dispose.
					if(control != null && !control.isDisposed()){
						runUpdates(runnables);
					}
				}
			});
		}
	}

	/**
	 * Run all of the runnables that are the widget updates
	 * @param runnables
	 */
	private void runUpdates(Collection runnables) {
		Iterator runnableIterator = runnables.iterator();
		while(runnableIterator.hasNext()){
			((Runnable)runnableIterator.next()).run();
		}		
	}

	/**
	 * Return a runnable for refreshing a resource.
	 * @param resource
	 * @return Runnable
	 */
	private Runnable getRefreshRunnable(final Object element) {
		return new Runnable() {
			@Override
			public void run() {
				if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
					((StructuredViewer) viewer).refresh(element);
					((AbstractTreeViewer) viewer).refresh(element);
				}
			}
		};
	}


	/**
	 * Return a runnable for refreshing a resource.
	 * @param resource
	 * @return Runnable
	 */
	private Runnable getUpdateRunnable(final Object element) {
		return new Runnable() {
			@Override
			public void run() {
				if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
					((StructuredViewer) viewer).update(element, null);
				}
			}
		};		
	}

	public void dependenciesChanged(IProject project) {
		scheduleUpdates(Collections.singleton(getRefreshRunnable(project)));
	}
}