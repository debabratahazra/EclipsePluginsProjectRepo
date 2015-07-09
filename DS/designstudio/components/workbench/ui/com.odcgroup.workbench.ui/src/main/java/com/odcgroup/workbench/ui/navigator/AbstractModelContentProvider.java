package com.odcgroup.workbench.ui.navigator;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.ui.OfsUICore;

/**  
 * This class provides an easy means to implement a content extension for standard OFS
 * model resources. It automatically adds the EMF model tree as a child of the resource.
 * These can be filtered or amended by the implementing subclass.
 * 
 * @author Kai Kreuzer, Odyssey
 *
 */
abstract public class AbstractModelContentProvider extends AdapterFactoryContentProvider 
		implements IResourceChangeListener, IResourceDeltaVisitor {

	/** resourceset to manage the loaded resources */
	private ResourceSet rs = new ResourceSetImpl(); 
	
	/** The name of the model that this content provider should handle */
	final private String MODEL;
	
	/** The root class of the model, the children of which should be displayed */
	final private Class MODELROOTCLASS;

	/** have the viewer avaiable for necessary refreshs */
	private StructuredViewer viewer;
	
	/**
	 * Create the ModelContentProvider instance.
	 * 
	 * Adds the content provider as a resource change listener to track changes on disk.
	 * 
	 * @param factory an EMF adapter factory that provides adapters for image providers, etc.
	 * @param model the name of the model that this content provider should handle
	 * @param modelRootClass the root class of the model, the children of which should be displayed
	 */
	public AbstractModelContentProvider(AdapterFactory factory, String model,  Class modelRootClass) {
		super(factory);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
		this.MODEL = model;
		this.MODELROOTCLASS = modelRootClass;
	}

	public boolean hasChildren(Object element) {
		// avoid loading the models
		if(element instanceof IOfsModelResource) return true;
		if(element instanceof IOfsModelPackage) return super.hasChildren(element);
		if(element instanceof IOfsModelFolder) return super.hasChildren(element);
		
		return this.getChildren(element).length > 0;
	}

	public Object[] getElements(Object inputElement) {
		return this.getChildren(inputElement);
	}

	public Object[] getChildren(Object object) {
	
		if (object instanceof IOfsModelResource){
			IOfsModelResource modelFile = (IOfsModelResource) object;
	      String path = ((IFile)object).getFullPath().toString();
	      URI uri = URI.createPlatformResourceURI(path, false);
	      
	      IOfsProject ofsProject = OfsCore.getOfsProjectManager()
	      	.getOfsProject(modelFile.getOfsProject().getProject());
	      
			try {
				EObject element = ofsProject.getOfsModelResource(uri).getEMFModel().get(0);
		    	  if(MODELROOTCLASS.isInstance(element)) {
		    		  return super.getChildren(element);
		    	  }
			} catch (ModelNotFoundException e) {
				OfsUICore.getDefault().logWarning("Error loading model " + uri, e);
			} catch (IOException e) {
				OfsUICore.getDefault().logWarning("Error loading model " + uri, e);
		    } catch (InvalidMetamodelVersionException e) {
				OfsUICore.getDefault().logWarning("Error loading model " + uri, e);
			}
		}
		return super.getChildren(object);
	  }
	 
	  public Object getParent(Object object) {
	    if (object instanceof IFile)
	      return ((IResource)object).getParent();
	    return super.getParent(object);
	  }
	  
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this); 
	}

	public void inputChanged(Viewer aViewer, Object oldInput, Object newInput) {
		viewer = (StructuredViewer) aViewer;
		this.rs = new ResourceSetImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		try {
			delta.accept(this);
		} catch (CoreException e) { 
			// ignore exceptions
		} 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
	 */
	public synchronized boolean visit(IResourceDelta delta) {

		IResource source = delta.getResource();
		switch (source.getType()) {
		case IResource.ROOT:
		case IResource.PROJECT:
		case IResource.FOLDER:
			return true;
		case IResource.FILE:
			if (viewer != null && !viewer.getControl().isDisposed()) {
				final IFile file = (IFile) source;
				if(MODEL.equals(file.getFileExtension())) {
					rs = new ResourceSetImpl();

					// launch a viewer refresh
					Runnable op = new Runnable() {
						public void run() {
							viewer.refresh(file);
						}
					};
					Display.getDefault().syncExec(op);
				}
			}
			return true;
		}
		return false;
	}
}