package com.odcgroup.workbench.core.resources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.transaction.util.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IMetaModelVersioned;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.core.internal.resources.ProxyResolutionJob;
import com.odcgroup.workbench.core.internal.resources.ProxyResolutionJobQueue;
import com.odcgroup.workbench.core.repository.IModelVisitor;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.memoryanalyzer.MemoryAnalyzerCore;
import com.odcgroup.workbench.security.DSAuthorizationResult;
import com.odcgroup.workbench.security.DSPermission;
import com.odcgroup.workbench.security.SecurityCore;

public class OfsModelResource extends OfsElement implements IOfsModelResource {
	private static Logger logger = LoggerFactory.getLogger(OfsModelResource.class);
	
	private final IStorage storage;
	
	// DS-5997
	private IOfsElement parent;
	
	private String shortName = null;

	static private Map<String, Boolean> needsMigrationMap = new HashMap<String, Boolean>();
	
	static private final long timeToWait = 1000L;

	static private boolean migrationCheckActivated = true;
	
	private final Lock resourceLock = new Lock(); 
	
	public static void setMigrationCheckActivated(boolean activated) {
		migrationCheckActivated = activated;
	}
	
	public OfsModelResource(IOfsProject ofsProject, URI uri, IStorage storage, int scope) {
		super(ofsProject, uri, storage instanceof IFile ? (IResource) storage : null);
		this.storage = storage;
		setScope(scope);
	}

	public OutputStream getOutputStream() {
        if (storage instanceof IFile) {
            return new IFileOutputStream((IFile) storage);
        } else {
            return null;
        }
	}
	
	public String getShortName() {
		if (shortName != null) return shortName;
		String sn = StringHelper.withoutExtension(getName());
		if (getURI() != null) shortName = sn; 
		return sn;	
	}

	public InputStream getContents() throws CoreException {
        if (storage == null) {
            return null;
        } else {
        	try {
        		return storage.getContents();
        	} catch (ResourceException e) {
        		IPath iPath = storage.getFullPath();
        		if (iPath == null)
        			throw e;
        		ResourcesPlugin.getWorkspace().getRoot().getFile(iPath).refreshLocal(IResource.DEPTH_ZERO, null);
        		InputStream is = storage.getContents();
        		logger.warn("{} was out of sync with the file system, but OfsModelResource.getContents() auto-refreshed it", iPath);
        		return is;
        	}
        }
	}

	public IStorage getStorage() {
		return storage;
	}

	public IPath getFullPath() {
		return new Path(getURI().path());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IStorage#isReadOnly()
	 */
	public boolean isReadOnly() {
		if (getResource() == null) {
			return true;
		}
		
		if( getResource() != null && !getResource().exists()) {
			return true;
		}
		
		if(SecurityCore.getAuthorizationManager().permissionGranted(getResource().getProject(), getURI(), DSPermission.EDIT_MODEL, null)==DSAuthorizationResult.REJECTED) {
			return true;
		}
		
		return storage.isReadOnly();
	}
		
    public EList<EObject> getEMFModel() throws IOException, InvalidMetamodelVersionException {
    	URI uri = getURI();
    	try {
    		if(needsMigration()) {
				String metamodelVersion = getMetamodelVersion();
				throw new InvalidMetamodelVersionException("Model resource '" + uri.toString() + "' has a metamodel version " + metamodelVersion
						+ " and needs to be migrated.", metamodelVersion);
			}
		} catch (CoreException e) {
        	throw new IOException("Metamodel version cannot be read for model: " + getURI().toString(), e);
		}

		Resource resource = getOfsProject().getModelResourceSet().getResource(uri, getScope());

    	if(resource==null) {
        	try {
        		resourceLock.uiSafeAcquire(true);
    		} catch (InterruptedException e) {
            	throw new IOException("Resource access has been interrupted while waiting for the lock: " + getURI().toString());
    		}
        	try {
        		resource = getOfsProject().getModelResourceSet().createResource(uri, getScope());
			} catch (Throwable e) {
				logger.error("getOfsProject().getModelResourceSet().createResource() caused Exception for URI: " + getURI().toString(), e);
        	} finally {
        		resourceLock.release();
        	}
    	}

    	if(resource==null) {
    		// This should normally never occur, I think;
    		// because ResourceSetImpl.createResource(URI, String)
    		// only returns null if there is no Resource.Factory for the extension (or contentType)..  
    		// This is probably NOT an indication that the model could not be loaded (as that happens later).
    		logger.warn("getEMFModel() returning null because getOfsProject().getModelResourceSet().createResource(uri, getScope()) == null: " + uri);
    		return null;
    	}
    	
        if(!resource.isLoaded()) {
	        synchronized (resource) {
	        	// do a second check as now after the lock has been released the resource could be loaded already
	        	final Resource unloadedResource = resource;
	        	if (!unloadedResource.isLoaded()) {
	        		logger.debug("Loading " + resource.getURI());
		        	InputStream is = null;
	            	try {
	            		resourceLock.uiSafeAcquire(true);
		    		} catch (InterruptedException e) {
		            	throw new IOException("Resource access has been interrupted while waiting for the lock: " + getURI().toString());
		    		}
			        try {
		            	is = getContents();
		            	unloadedResource.load(is, null);
		            	if(ArrayUtils.contains(OfsCore.getEagerlyLoadedModelNames(),getURI().fileExtension())) {
			            	ProxyResolutionJob job = new ProxyResolutionJob("Resolving proxies of '" + unloadedResource.getURI() + "'") {
								@Override
								protected IStatus run(IProgressMonitor monitor) {
									if(unloadedResource.isLoaded()) {
										try {
					            			resourceLock.acquire(true);
										} catch (InterruptedException e) {
											logger.debug("Waiting for lock has been interrupted.", unloadedResource.getURI());
											return Status.CANCEL_STATUS;
										}
										try {
											if(OfsCore.getDefault().isDebugging())
												logger.trace("Resolving proxies for {}", unloadedResource.getURI());
											try {
												EcoreUtil.resolveAll(unloadedResource);
											} catch(ConcurrentModificationException e) {
												logger.trace("ConcurrentModificationException occurred while resolving proxies for " + unloadedResource.getURI());
											}
											if(OfsCore.getDefault().isDebugging())
												logger.trace("Finished resolving proxies for {}", unloadedResource.getURI());
										} catch (Throwable e) {
											logger.error("Waiting for lock has been interrupted by Exception for URI: " + unloadedResource.getURI(), e);
										} finally {
											resourceLock.release();
										}
									}
									return Status.OK_STATUS;
								}            		
			            	};
			            	job.setSystem(true);
			            	job.setPriority(Job.DECORATE);
			            	ProxyResolutionJobQueue.queue(job);
		            	}
		            } catch (CoreException e) {
		                throw new IOException(getURI().toString(), e);
		            } finally {
		                if(is!=null) is.close();
		                resourceLock.release();
		                try {
		                	if(logger.isTraceEnabled()) {
		                        if(resource.getContents().size()>0 ) {
		                        	long size = MemoryAnalyzerCore.deepMemoryUsageOf(resource.getAllContents());
		                        	if(size > 0) logger.trace("Model size: " + size + " bytes.");
		                        }
		                	}
		                } catch(NoClassDefFoundError e) {}
		            }
	            }
	        }
        } 
        
        // if the resource is empty, we can consider it as an error
        if(resource.getContents().size()==0) {
        	
        	// this code is only needed on a migration of a full project from XMI to DSL. It can be removed in future versions
        	String ext = resource.getURI().fileExtension();
        	if(ArrayUtils.contains(new String[] {"page", "module", "fragment"}, ext) && resource instanceof XMIResource) {
        		ModelResourceSet rs = (ModelResourceSet) resource.getResourceSet();
        		rs.removeResource(getURI(), getScope());
        		rs.createOfsModelResource(getURI(), getStorage(), getScope());
        		resource = rs.getResource(resource.getURI(), true);
        	}
        	
        	// sometimes EMF is not quick enough to have the content in place after loading the resource
        	// if ANYBODY knows a cleaner solution for this, please step up!!!
       		/*
       		 * @atr: not yet !! resource.isLoaded() is always true !!!, hint: async notification
       		 * However, this pragmatic retry loop will avoid to raise an 
       		 * exception too often (see below), and so to delete a valid
       		 * resource. 
       		 */
       		if (logger.isDebugEnabled()) {
       			logger.debug("Waiting for the loading of " +  resource.getURI());
       		}

       		final int MAX_RETRY_COUNT = 6; // 3 or lower is bad, 5 seems to be a good choice.
        	int retry = 0;
        	while(++retry < MAX_RETRY_COUNT && resource.getContents().size()==0) {
	        	try {
					Thread.sleep(timeToWait);
				} catch (InterruptedException e) {
					// ignore
				}
	        	// try again ?
        	}
       		if (logger.isDebugEnabled()) {
       			logger.debug("Waited "+((retry-1)*timeToWait)+" [ms] to load " + resource.getURI());
       		}

            if(resource.getContents().size()==0) {	
	        	// reset the flag to make sure that it is evaluated anew on the next request
	        	needsMigrationMap.remove(getOfsProject().getName()+getURI().toString());
	        	resource.delete(null);
	        		// If the resource was really valid, the user has to restart DS.
	        	throw new IOException("Resource does not contain any content: " + getURI().toString());
            }
        }
    	return resource.getContents();
    }

    protected static class IFileOutputStream extends ByteArrayOutputStream {

        private final IFile file;

        public IFileOutputStream(IFile file) {
            this.file = file;
        }

        /**
         * @see java.io.OutputStream#close()
         */
        public synchronized void close() throws IOException {
            super.close();

            InputStream in = new ByteArrayInputStream(toByteArray());

            try {
                createParent(file.getParent());

                if (file.exists()) {
                    file.setContents(in, true, true, new NullProgressMonitor());
                } else {
                    file.create(in, true, new NullProgressMonitor());
                }
            } catch (CoreException e) {
                OfsCore.getDefault().logWarning(e.getMessage(), e);
                throw new IOException(e.getLocalizedMessage());
            } finally {
            }
        }

        private void createParent(IContainer parent) throws CoreException {
            if (!parent.exists()) {
                createParent(parent.getParent());

                if (parent instanceof IFolder) {
                    ((IFolder) parent).create(true, false,
                            new NullProgressMonitor());
                }
            }
        }
    }

	public void accept(IModelVisitor visitor) {
		if(visitor.enter(this)) {
//            try {
//                walkEMFModel(getEMFModel(), visitor);
//            } catch (IOException e) {
//                OfsCore.getDefault().logWarning(e.getMessage(), e);
//            } catch (InvalidMetamodelVersionException e) {
//                OfsCore.getDefault().logWarning(e.getMessage(), e);
//			}
		}
		visitor.leave(this);
	}

    protected void walkEMFModel(EList<EObject> contents, IModelVisitor visitor) {
        Iterator<EObject> it = contents.iterator();
        while (it.hasNext()) {
            EObject element = it.next();

            if (visitor.enter(element)) {
                walkEMFModel(element.eContents(), visitor);
            }
            visitor.leave(element);
        }
    }

    protected synchronized boolean needsMigration() throws CoreException, InvalidMetamodelVersionException {
    	if(!migrationCheckActivated) return false;
    	Boolean needsMigration = needsMigrationMap.get(getOfsProject().getName()+getURI().toString());
    	if(needsMigration==null) {
        	IMetaModelVersioned versionedResource = (IMetaModelVersioned) this.getAdapter(IMetaModelVersioned.class);
        	if(versionedResource!=null) {
        		needsMigration = versionedResource.needsMigration();
        	} else {
        		needsMigration = false;
        	}
        	if(!needsMigration) {
        		// we put only valid ones in the map, all others should be checked again and again until
        		// they are migrated as well
            	needsMigrationMap.put(getOfsProject().getName()+getURI().toString(), needsMigration);
        	}
    	}
    	return needsMigration;
    }
    
    protected String getMetamodelVersion() throws CoreException {
    	IMetaModelVersioned versionedResource = (IMetaModelVersioned) this.getAdapter(IMetaModelVersioned.class);
    	if(versionedResource!=null) {
    		return versionedResource.getMetaModelVersion();
    	} else {
    		return "unknown";
    	}
    }

	public boolean isLoaded() {
		Resource resource = getOfsProject().getModelResourceSet().getResource(uri, getScope());
		if(resource==null) return false;
		return resource.isLoaded();
	}

	public void unload() {
    	try {
    		if(!resourceLock.acquire(1000L, true)) {
    			logger.warn("Waiting for resource lock for {} has been timed out.", uri.toString());
    			return;
    		}
		} catch (InterruptedException e) {
			logger.warn("Waiting for resource lock for {} has been interrupted.", uri.toString(), e);
		}
		try {
    		Resource resource = getOfsProject().getModelResourceSet().getResource(uri, getScope());
			if(logger.isTraceEnabled()) {
				logger.trace("Unloading resource '{}'.", uri.toString());
			}
    		if(resource!=null) {
    			resource.unload();
    		}
			if(logger.isTraceEnabled()) {
				logger.trace("Resource '{}' has been unloaded.", uri.toString());
			}
		} catch (Throwable t) {
			logger.error("Error during unloading of resource: " + uri.toString(), t);
		} finally {
			resourceLock.release();
		}
	}
	
	@Override
	public Lock getLock() {
		return resourceLock;
	}

	@Override
	public String toString() {
		return uri.toString();
	}
	
	@Override
	public synchronized IOfsElement getParent() {
		if (parent == null) {
			URI parentURI = getURI().trimSegments(1);
			IOfsModelFolder folder = getOfsProject().getModelFolder(getURI().fileExtension());
			if (folder != null) { 
				parent = folder.getOfsElement(parentURI);
			}
		}
		return parent;
	}
	
}
