package com.odcgroup.workbench.core.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.internal.repository.DependencyManagerFactory;
import com.odcgroup.workbench.core.internal.repository.ModelResourceSetImpl;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.IModelVisitor;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;

public class OfsProject extends OfsModelContainer implements IOfsProject, IResourceDeltaVisitor {
	
	private static final Logger logger = LoggerFactory.getLogger(OfsProject.class);

	private IProject project;
	
	// never change the visibility 
	private List<IOfsModelFolder> modelFolders;
	
	// this lock protects the resource set.
	private final ReentrantLock modelFoldersLock = new ReentrantLock();

	// keep the visibility private as it is a critical resource (see the usage of the resourceSetLock)
	private ModelResourceSetImpl resourceSet;
	
	// this lock protects the resource set.
	private final ReentrantLock resourceSetLock = new ReentrantLock();
    
	private OfsProjectClearCacheListener clearCacheListener; 
	
	/** 
	 * This constructor should never be used anywhere in the code.
	 * It is only necessary for creating instances through the Eclipse extension mechanism.
	 */
	public OfsProject() {}
	
	public OfsProject(IProject project) {
		setProject(project);
	}
	
	public final IProject getProject() {
		return this.project;
	}

	@Override
	public IContainerIdentifier getIdentifier() {
		IContainerIdentifier identifier = null;
		IDependencyManager depMgr = DependencyManagerFactory.getDependencyManager(getProject());
		if(depMgr!=null) {
			identifier = depMgr.getIdentifier(getProject());
		}
		// as a fall-back, use the project name
		if(identifier==null) {
			identifier = new IContainerIdentifier() {
				public String getGroupId() {
					return project.getName();
				}
				public String getName() {
					return project.getName();
				}
				public String getVersion() {
					return "1.0.0-SNAPSHOT";
				}
				
				public String toString() {
					return "(" + getName() + ", " + getVersion() + ")"; 
				}
			};
		}
		return identifier;
	}
	
	private final List<IOfsModelFolder> getModelFolders() {
		modelFoldersLock.lock();

		try {
			if (modelFolders == null) {
				modelFolders = new ArrayList<IOfsModelFolder>();
				for(String modelType : OfsCore.getRegisteredModelNames()) {
					IOfsModelFolder folder = new OfsModelFolder(this, getProject().getFolder(modelType));
					modelFolders.add(folder);
				}
			}
			return modelFolders;
		} finally {
			modelFoldersLock.unlock();
		}
	}
	
	public IOfsModelFolder[] getAllModelFolders() {
		List<IOfsModelFolder> list = getModelFolders();
		return list.toArray(new IOfsModelFolder[list.size()]);
	}
	
	public boolean hasModelFolders() {
		return getModelFolders().size() > 0;
	}

	public IOfsModelFolder getModelFolder(String modelType) {		
		for (IOfsModelFolder folder : getModelFolders()) {
			if (folder.acceptFileExtension(modelType)) {
				return folder;
			}
		}
		return null;
	}

    public ModelResourceSet getModelResourceSet() {
    	 resourceSetLock.lock();  
    	 try {
			if (resourceSet == null) {
	   	 		this.resourceSet = newModelResourceSetImpl();
	   	 		
	   			ModelURIConverter converter = new ModelURIConverter(this);
	   			this.resourceSet.setURIConverter(converter);
	   			
	   			// we need to initialize the model resources in the resource set 
	   			walkModelFolders(new IModelVisitor() {
	   				public void leave(Object model) { }
	   				public int getScope() {
	   					return IOfsProject.SCOPE_ALL;
	   				}
	   				public boolean enter(Object model) {
	   					return (model instanceof IOfsModelFolder || model instanceof IOfsModelPackage); 
	   				}
	   			});
   	    	}
   	        return resourceSet;
    	 } finally {
    		 resourceSetLock.unlock();
    	 }
    }

	private ModelResourceSetImpl newModelResourceSetImpl() {
		ModelResourceSetImpl newResourceSet = XtextResourceSetProviderDS.newXtextResourceSet(ModelResourceSetImpl.class, project);
	 	newResourceSet.setOfsProject(this);
		return newResourceSet;
	}

	public IOfsModelResource getOfsModelResource(URI uri) throws ModelNotFoundException {
		IOfsModelResource modelResource = getModelResourceSet().getOfsModelResource(uri);
		if(modelResource==null) {
			logger.debug("### ("+Thread.currentThread().getId()+") NOT FOUND getOfsModelResource(URI " +uri.toString());
			throw new ModelNotFoundException("No model for URI '" + uri.toString() + "' could  be found.");
		}
		return modelResource;
	}
	
	public boolean ofsModelResourceExists(URI uri) {
		try {
			IOfsModelResource mresource = getOfsModelResource(uri);
			return mresource != null;
		} catch (ModelNotFoundException e) {
			return false;
		}
	}

    public void lookup(IModelVisitor visitor) {
            walkModelFolders(visitor);
    }

	/**
     * This method consider the model folders of this project as the resource containers.
     */
    protected void walkModelFolders(final IModelVisitor visitor) {
    	if(project.exists() && project.isOpen()) {
	    	for(IOfsModelFolder modelFolder : getAllModelFolders()) {
	    		modelFolder.accept(visitor);
	    	}
    	}
    }

    /**
     * Implements the resourceChanged event
     * 
     * @param event The received event
     */
    public void resourceChanged(IResourceChangeEvent event) {
        switch (event.getType()) {
            // On update
            case IResourceChangeEvent.POST_CHANGE:
                try {
                    event.getDelta().accept(this);
                } catch (CoreException e) {
                    OfsCore.getDefault().logError(e.getMessage(), e);
                }
        }
    }

    /**
     * Visit the children of the delta
     * 
     * @param delta The delta to visit
     * @return boolean True since we want to visit the children
     */
    public boolean visit(IResourceDelta delta) {
        switch (delta.getKind()) {
            case IResourceDelta.REMOVED: {
                visitRemoved(delta);
                visitChanged(delta);
                break;
            }
            case IResourceDelta.CHANGED: {
                visitChanged(delta);
                break;
            }
            case IResourceDelta.ADDED: {
            	visitAdded(delta); // should return false for a model
            	break;
            }
        }

        return true;
    }
    
    /**
     * Verify the given project is contained in the dependency list.
     * 
     * @param project the project to be checked
     * 
     * @return <tt>true</true> if the given project is in the dependency 
     *         list, otherwise it return <tt>false</tt> 
     */
    private boolean dependentProject(IProject project) {
		IDependencyManager depMgr = OfsCore.getDependencyManager(getProject());
		Set<IOfsModelContainer> containers = depMgr.getDependencies(this);
		for (IOfsModelContainer container : containers) {
			if (container instanceof IOfsProject) {
				if (project.equals(((IOfsProject)container).getProject())) {
					return true;
				}
			}
		}
		return false;
    }

    /**
     * Visits the resource delta in the case of a remove.
     * 
     * @param delta The IResourceDelta to visit
     * @return boolean True since we want to visit the children
     */
    private boolean visitRemoved(IResourceDelta delta) {
        IResource res = delta.getResource();
        int resourceType = res.getType();
        if (resourceType == IResource.FILE) { 
	        if (!acceptedExtension(res.getFullPath())) {
	            return true;
	        }
	        // check the resource is contained in this this project or in a dependent one
	        int scope = IOfsProject.SCOPE_PROJECT;
	        if (!getProject().equals(res.getProject())) {
	        	if (!dependentProject(res.getProject())) {
	        		return true;
	        	}
	        	scope = IOfsProject.SCOPE_DEPENDENCY;
	        }
	        URI uri = ModelURIConverter.createModelURI(res);
	        removeResource(uri, scope);
        } else if (resourceType == IResource.FOLDER) {
        	// check if this folder is created only in this project 
	        if (getProject().equals(res.getProject())) {
		        IPath resourcePath = res.getFullPath();
		        int nbSegments = resourcePath.segmentCount();
		        if (nbSegments > 3) {
		        	// remove sub-package from a parent package
		        	IOfsModelPackage parentPackage = getParentPackage(resourcePath);
		        	if (parentPackage != null) {
		        		parentPackage.removePackage(resourcePath.segment(nbSegments-1));
		        	}
		        } else if (nbSegments == 3) {
		        	// remove root package from a model folder
					IOfsModelFolder modelFolder = getModelFolder(res.getFullPath().segment(1));
					if (modelFolder != null)
						modelFolder.removePackage(resourcePath.segment(nbSegments-1));
		        }
	        }	

        }
        return true;
    }
    
    /**
     * visits the resource delta in case of a added resource
     * 
     * @param delta
     * @return
     */
    private boolean visitAdded(IResourceDelta delta) {
        IResource res = delta.getResource();
        int resourceType = res.getType();
        if (resourceType ==  IResource.FILE) {
	        if (!acceptedExtension(res.getFullPath())) {
	            return true;
	        }

	        // check the resource is contained in this this project or in a dependent one
	        int scope = IOfsProject.SCOPE_PROJECT;
	        if (!getProject().equals(res.getProject())) {
	        	if (!dependentProject(res.getProject())) {
	        		return true;
	        	}
	        	scope = IOfsProject.SCOPE_DEPENDENCY;
	        }        
	
			// DS-3683: check that we are inside some model folder otherwise return
			if(res.getFullPath().segmentCount()>2) {
				IOfsModelFolder modelFolder = getModelFolder(res.getFullPath().segment(1));
				if(modelFolder==null) return true;
				// DS-3108: do not accept "foreign" models in a model folder
				if(!modelFolder.acceptFileExtension(res.getFileExtension())) return true;
			}
	
	        URI uri = ModelURIConverter.createModelURI(res);
	        // add resource to resourceset
	        addResource(uri, (IFile) res, scope);
        } else if (resourceType == IResource.FOLDER) {
        	// check if this folder is created only in this project 
	        if (getProject().equals(res.getProject())) {
		        IPath resourcePath = res.getFullPath();
		        if (resourcePath.segmentCount() > 3) {
		        	// create a subpackage in a parent package
		        	IOfsModelPackage parentPackage = getParentPackage(resourcePath);
		        	if (parentPackage != null) {
		        		parentPackage.addPackage((IFolder)res);
		        	}
		        } else if (resourcePath.segmentCount() == 3) {
		        	// create a root package in a model folder
					IOfsModelFolder modelFolder = getModelFolder(res.getFullPath().segment(1));
					if (modelFolder != null) {
						modelFolder.addPackage((IFolder) res);
					}
		        }
	        }	
        }
        return true;
    }

    /**
     * Visits the resource delta in the case of a change.
     * 
     * @param delta The IResourceDelta to visit
     * @return boolean True since we want to visit the children
     */
    private boolean visitChanged(IResourceDelta delta) {
        IResource res = delta.getResource();
        
        // check if dependencies have changed
        if(getProject().equals(res.getProject()) && res.getName().equals("pom.xml")) {
        	// dispose existing model folders so that they are refreshed completely
        	//modelFolders = null;
        }
        
        // stop visiting if project is closed
        if (res.getProject()!=null && !res.getProject().isOpen()) {
            return false;
        }

        // stop visiting if resource does not exist anymore
        if(!res.exists()) {
        	return false;
        }
        
        if (!acceptedExtension(res.getFullPath())) {
            return true;
        }

        return true;
    }
    
    private void addResource(URI uri, IStorage storage, int scope) {
    	IOfsModelResource resource = getModelResourceSet().createOfsModelResource(uri, storage, scope);
    	IOfsElement element = resource.getParent();
    	if (element instanceof IOfsModelPackage) {
    		IOfsModelPackage pkg = (IOfsModelPackage)element;
    		pkg.add(resource);
    	}
    }
    
    private IOfsModelPackage getParentPackage(IPath path) {
    	IOfsModelPackage pkg = null;
    	int nbSegments = path.segmentCount();
    	if (nbSegments > 2) {
	    	String modelType = path.segment(1);
	    	IOfsModelFolder folder = getModelFolder(modelType);
	    	if (folder != null) {
	    		int max = path.segmentCount()-1;
	    		for (int sx = 2; sx < max ; sx++) {
	    			String name = path.segment(sx);
	    			if (sx == 2) {
	    				pkg = folder.getOfsPackage(name);
	    			} else {
	    				pkg = pkg.getOfsPackage(name);
	    			}
	    			if (pkg == null) break;
	    		}
	    	}
    	}
    	return pkg;
    }

    /**
     * Removes the resource given the URI.
     * 
     * @param uri The uri of the resource to remove
     * @param scope the scope of the URI
     */
    private void removeResource(URI uri, int scope) {
    	ModelResourceSet mrs = getModelResourceSet();
    	IOfsModelResource ofsResource = mrs.getOfsModelResource(uri, scope);
    	if (ofsResource != null) {
    		IResource resource = ofsResource.getResource();
    		switch (resource.getType()) {
    		
	    		case IResource.FILE: 
	    		{
	    			IPath path = ofsResource.getResource().getFullPath();
	    			IOfsModelPackage pkg = getParentPackage(path);
	    			if (pkg != null) {
	    				pkg.removeModel(ofsResource.getURI());
	    			}
	    			break;
	   			}
	    		
	    		case IResource.FOLDER:
	    		{
	    			IResource parent = resource.getParent();
	    			if (parent.getType() != IResource.PROJECT) {
	    				// resource is a (sub)package.
	    			}
	    			break;
	    		}
	    		default:
	    			break;
    		}
        	mrs.removeResource(uri, scope);
    	}
    }
    
    /**
     * Return true if the path ended with a recognized extension
     * 
     * @param path The path to the file to test
     * @return boolean The boolean result
     */
    private boolean acceptedExtension(IPath path) {
    	String extension = path.getFileExtension();
    	for(IOfsModelFolder modelFolder : getAllModelFolders()) {
    		if(modelFolder.acceptFileExtension(extension)) {
    			return true;
    		}
    	}
    	
        return false;
    }

	public void setProject(IProject project) {
		this.project = project;
	}

	public void refresh() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OfsProject other = (OfsProject) obj;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

	public void setClearCacheListener(OfsProjectClearCacheListener _clearCacheListener) {
		if (clearCacheListener != null) {
			throw new IllegalStateException("There already was a ClearCacheListener set - something's funny..");
		}
		this.clearCacheListener = _clearCacheListener; 
	}

	public void clearCache() {
		resourceSetLock.lock();
		modelFoldersLock.lock();
		try {
			for (IOfsModelFolder folder : getModelFolders()) {
				folder.removeAll();
			}
			for (IOfsModelResource modelResource : getModelResourceSet().getAllOfsModelResources()) {
				modelResource.unload();
			}
			modelFolders = null;
			resourceSet = null;
		} finally {
			modelFoldersLock.unlock();
			resourceSetLock.unlock();
		}
		
		if (clearCacheListener != null) {
			clearCacheListener.cacheCleared();
			// we need to set this to null, because whatever listens to clear cache (DomainRepository..) need to get, well, cleared!
			clearCacheListener = null;
		}
	}

	@Override
	public String toString() {
		return "OfsProject [projectName=" + getProject().getName() 
				+ ", identifier=" + getIdentifier().getGroupId() + "::" 
				+ getIdentifier().getName() + "," + getIdentifier().getVersion() + "]";
	}

}
