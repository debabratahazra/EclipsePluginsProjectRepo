package com.odcgroup.mdf.ecore.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.core.internal.resources.ProxyResolutionJob;
import com.odcgroup.workbench.core.repository.ModelMatcher;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.resources.OfsProject;
import com.odcgroup.workbench.core.resources.OfsProjectClearCacheListener;
import com.odcgroup.workbench.core.xtext.ClasspathTypeProviderJavaURIConverterUtil;

/**
 * DomainRepository adds two optimizations over the basic DomainLookup.
 * It has a cache of Domain-Name to Domain Resource (URI).
 * It has a cache of Dataset-Name to Dataset (instance).
 */
public class DomainRepository {

	public static final Object[] EXTENSIONS = DomainLookup.EXTENSIONS;

	final private static Logger logger = LoggerFactory.getLogger(DomainRepository.class);
	
	final private static Map<IProject, DomainRepository> repoMap = new HashMap<IProject, DomainRepository>();
	
	private static Object lock = new Object();
	
	/**
	 * This class listen to resource changes, and removes the projects from the
	 * repoMap whenever an OfsProject is closed.
	 */
	private static class RemoveProjectListener implements IResourceChangeListener {
		public synchronized void resourceChanged(IResourceChangeEvent event) {
			switch (event.getType()) {
			case IResourceChangeEvent.POST_CHANGE:
				IResource res = event.getDelta().getResource();

				// remove project from map, if it has been deleted from the
				// workspace
				if ((res instanceof IWorkspaceRoot)) {
					IResourceDelta[] children = event.getDelta().getAffectedChildren();
					for (int kx = 0; kx < children.length; kx ++) {
						IResource child = children[kx].getResource();
						if ((child instanceof IProject) && children[kx].getKind() == IResourceDelta.REMOVED) {
							IProject project = (IProject)child;
							invalidateProject(project);
						}
					}
				}
			}
		}
	}	

	private static void invalidateProject(IProject project) {
		synchronized (lock) {
			DomainRepository repo = repoMap.get(project);
			repoMap.remove(project);
			if (repo != null && repo.domainResourceListener != null) {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(repo.domainResourceListener);
			}
		}
	}

	private DomainModelResourceListener domainResourceListener;
	
    private DomainLookup lookup;
    private IProject project;
    private final Map<String, URI> domainURIMap = new HashMap<String, URI>();
    
    // NOTE: datasetCache may be null if FeatureSwitches.domainRepositoryDatasetCacheEnabled == -false. This is handled below where it's used. 
    private final Cache<MdfName, MdfDataset> datasetCache;

    private MdfDataset reallyLoadDataset(MdfName qname) throws ExecutionException {
		MdfDomain domain = getDomain(qname.getDomain());
		if(domain!=null) {
			MdfDataset ds = domain.getDataset(qname.getLocalName());
			if(ds!=null) {
				return ds;
			} else {
		    	throw new ExecutionException("Dataset " + qname.getLocalName() + " does not exist in domain " + domain.getName(), null);
			}
		} else {
			throw new ExecutionException("Domain " + qname.getDomain() + " does not exist.", null);
		}
	}
    
    /** @deprecated Why don't you just use PrimitivesDomain.DOMAIN directly now, mate? ;) */
    @Deprecated
    public static final MdfDomain CORE_DOMAIN = PrimitivesDomain.DOMAIN;

    static { 
    	RemoveProjectListener removeProjectListener = new RemoveProjectListener();
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			ResourcesPlugin.getWorkspace().addResourceChangeListener(removeProjectListener, IResourceChangeEvent.POST_CHANGE);
		} else {
			logger.warn("DomainRepository seems to run 'standalone' (outside Eclipse), and therefore cannot register the RemoveProjectListener (if you're just running a non-PDE test, that's probably OK)");
		}
    }
    
    private DomainRepository(final IOfsProject ofsProject) {
    	if (ofsProject == null) {
    		throw new IllegalArgumentException("ofsProject cannot be null");
    	}
        this.project = ofsProject.getProject();
        lookup = new DomainLookup(ofsProject);
        domainURIMap.putAll(lookup.getDomainURIMap());
        
        // add a domain model change listener
        domainResourceListener = new DomainModelResourceListener() {
			protected void notifyChange(IResource res) {
				handleDomainModelChange(ofsProject, res);
			}

			protected void notifyRemove(IResource res) {
		    	//URI resUri = ModelURIConverter.createModelURI(res);
			}
			
		};
		ResourcesPlugin.getWorkspace().addResourceChangeListener(domainResourceListener, IResourceChangeEvent.POST_CHANGE);
		
		if (FeatureSwitches.domainRepositoryDatasetCacheEnabled.get())
			datasetCache = CacheBuilder.newBuilder().weakValues().expireAfterAccess(60, TimeUnit.MINUTES)
		            .build(new CacheLoader<MdfName, MdfDataset>() {
		                @Override
		                public MdfDataset load(MdfName qname) throws Exception {
		        	        return reallyLoadDataset(qname);
		                }
		            });
		else
			datasetCache = null;
		
		Bundle b = Platform.getBundle("org.eclipse.ui.workbench"); 
		if (b == null || b.getState() != Bundle.ACTIVE) {
			// headless mode:
			((OfsProject) ofsProject).setClearCacheListener(new OfsProjectClearCacheListener() {
				@Override public void cacheCleared() {
					invalidateProject(ofsProject.getProject());
				}
			});
		}
    }
    
    /**
     * update the dataset cache with respect to the datasets in the changed domain model resource
     */
    private void handleDomainModelChange(IOfsProject ofsProject, IResource res) {
    	if (!FeatureSwitches.domainRepositoryDatasetCacheEnabled.get())
    		return;
    	URI resUri = ModelURIConverter.createModelURI(res);
    	Resource mres = ofsProject.getModelResourceSet().getResource(resUri, IOfsProject.SCOPE_ALL);
	if(mres != null && mres.isLoaded()) { // DS-8364
		mres.unload();
	}
    	// add the datasets back to cache
		if (mres != null) {
			EObject eObj = mres.getResourceSet().getEObject(resUri, true);
			Map<MdfName, MdfDataset> map = datasetCache.asMap();
			if (eObj instanceof MdfDomain) {
				MdfDomain dom = (MdfDomain) eObj;
				List datasets = dom.getDatasets();
				for (Object object : datasets) {
					MdfDatasetImpl ds = (MdfDatasetImpl) object;
					MdfName qname = ds.getQualifiedName();
					if (map.containsKey(qname)) {
						map.put(qname, ds);
					}
				}
				removeDatasetFromCache(dom, map);
			}
		}
    }
    
    private void removeDatasetFromCache(MdfDomain domain, Map<MdfName, MdfDataset> cache) {
    	Set<MdfName> keys = cache.keySet();
    	Set<MdfName> delkeys = new HashSet<MdfName>();
    	delkeys.addAll(keys);
    	List datasets = domain.getDatasets();
		for (Object object : datasets) {
			MdfDatasetImpl ds = (MdfDatasetImpl) object;
			MdfName qname = ds.getQualifiedName();
			if (cache.containsKey(qname)) {
				delkeys.remove(qname);
			}
		}
		for (MdfName qname : delkeys) {
			cache.remove(qname);
		}
    }

    public static DomainRepository getInstance(IOfsProject ofsProject) {
    	synchronized (lock) {
    		if (ofsProject == null)
    			throw new IllegalArgumentException("getInstance(ofsProject == null)");
    		
        	IProject project = ofsProject.getProject();
	    	DomainRepository repo = repoMap.get(project);
	    	if(repo==null) {
	    		repo = new DomainRepository(ofsProject);
	    		repoMap.put(project, repo);
	    	}
	    	return repo;
    	}
    }
    
    public static DomainRepository getInstance(ResourceSet resourceSet) {
    	if(resourceSet!=null && !(ClasspathTypeProviderJavaURIConverterUtil.getUnderlyingExistingURIConverterOrSame(resourceSet.getURIConverter()) instanceof ModelURIConverter)) {
    		throw new RuntimeException("ResourceSet does not belong to an IOfsProject!");
    	}

    	ModelURIConverter converter = resourceSet==null ? 
    			new ModelURIConverter(null) : (ModelURIConverter) ClasspathTypeProviderJavaURIConverterUtil.getUnderlyingExistingURIConverterOrSame(resourceSet.getURIConverter());
    	IOfsProject ofsProject = converter.getOfsProject();

    	if(ofsProject==null) return null;
    	
    	return getInstance(ofsProject);
    }
    
	public static DomainRepository getInstance(Resource resource) {
		Preconditions.checkNotNull(resource, "No domain repository from resource null");
		final URI uri = Preconditions.checkNotNull(resource.getURI(), "No domain repository from resource null or resource.getURI null");

		if (!uri.isPlatform()) {
			// If this isn't a platform:// scheme URI, then we got a bit of a
			// problem here, because the following code assumes (and can only work)
			// if it is... we can try below -- but it won't work either, if the
			// Resource is not in an OfsModelResourceSet (which we want to get
			// rid of).  However, logically, normally, if it's not in an 
			// OfsModelResourceSet, then it's probably a platform URI.. 
			// Else, you would have to either have to use the alternative
			// getInstance(IOfsProject ofsProject) if you can, or, better,
			// start looking at new (TBC...) infra. based on Index, à la
			// DS-7124 ModelLoader.getNamedEObject(), or
			// DS-6554 DomainRepositoryIndexBased (DomainRepository2)
			return getInstance(resource.getResourceSet());
		}
		
		String platformString = uri.toPlatformString(true);
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString));
		if (!file.exists()) {
			logger.debug("The file of the resource " + uri + " doesn't exist.");
			return null;
		}
		IProject project = file.getProject();
		if (project == null || !project.isOpen()) {
			logger.debug("The project of the resource " + uri + " doesn't exist or is closed.");
			return null;
		}
		IOfsProject ofsProject = OfsCore.getOfsProject(project);
		if (ofsProject == null) {
			logger.debug("There is no OFSProject linked to the resource " + uri);
			return null;
		}
		return DomainRepository.getInstance(ofsProject);
	}
    
	public MdfDataset getDataset(MdfName qname) {
		try {
			if (FeatureSwitches.domainRepositoryDatasetCacheEnabled.get()) {
				MdfDataset dataset = datasetCache.get(qname);
				if (((MdfDatasetImpl)dataset).eIsProxy()) {
					// This could happen if we have a Dataset in the cache which was unloaded by something else,
					// most notably & likely e.g. the ModelResourceSetImpl resourceCache (but 
					// but could have been something else as well)
					dataset = reallyLoadDataset(qname);
				}
				return dataset;
			} else
				return reallyLoadDataset(qname);
		} catch (ExecutionException e) {
			logger.debug("Failed to load dataset {} (from DomainRepository DataSet memory cache, if's enabled; else from project)", qname.getQualifiedName(), e);
			return null;
		}
    }

    public MdfDomain getDomain(String name) {
    	if ("mml".equals(name)) {
    		return PrimitivesDomain.DOMAIN;
    	}
    	
        try {
			return privateGetDomain(name);
		} catch (IOException e) {
			logger.debug("Cannot load domain model", e);
		} catch (InvalidMetamodelVersionException e) {
			logger.trace("Domain model '" + name + "' has an invalid meta-model version");
		}
        return null;
    }
    
    /**
     * Do NOT use this method unless you absolutely must.. it will be very slow, because it will load all *.domain.
     */
    public Collection<MdfDomain> getDomains(int scope) {
        return Collections.unmodifiableCollection(lookup.getAllDomainModels(scope));
    }

    /**
     * Do NOT use this method unless you absolutely must.. it will be very slow, because it will load all *.domain.
     */
    public Collection<MdfDomain> getDomains() {
        return Collections.unmodifiableCollection(lookup.getAllDomainModels(IOfsProject.SCOPE_ALL));
    }

    /**
     * Do NOT use this method unless you absolutely must.. it will be very slow, because it will load all *.domain.
     */
    // This is only used by com.odcgroup.mdf.editor.ui.actions.DatasetFacility.fetchAllDomains(IProgressMonitor, IOfsModelPackage)
    // which does has a ModelMatcher that would NOT need to match EObject, just the Resource.. could be optimized - but not sure about actual gain
    public Collection<MdfDomain> getDomains(ModelMatcher matcher) {
        return Collections.unmodifiableCollection(lookup.getAll(matcher));
    }
    
    public MdfEntity getEntity(MdfName qname) {
    	if(qname==null) return null;
        MdfDomain domain = getDomain(qname.getDomain());
        return (domain == null) ? null : domain.getEntity(qname.getLocalName());
    }
    
    public Map<String, URI> getDomainNameURIMapping() {
    	return domainURIMap;
    }

    private MdfDomain privateGetDomain(String name) throws IOException, InvalidMetamodelVersionException {
    	synchronized (name) {
	    	URI uri = domainURIMap.get(name);
	    	if(uri!=null) {
	    		try {
	    			IOfsProject ofsProject = OfsCore.getOfsProject(project);
					return (MdfDomain) ofsProject.getOfsModelResource(uri).getEMFModel().get(0);
				} catch (Exception e) {
					// domain cannot be loaded anymore (deleted, moved?), so remove it from our cache
					logger.warn("Domain '{}' cannot be read, removing it from domain repository cache. " +
							"(Activate trace level to get the full stacktrace.)", name);
					logger.trace("Domain '{}' cannot be read, removing it from domain repository cache.", name, e);
					domainURIMap.remove(name);
					uri = null;
				}
	    	}
	    	
	    	if(Job.getJobManager().currentJob() == null || 
	    			!Job.getJobManager().currentJob().belongsTo(ProxyResolutionJob.FAMILY_PROXY_RESOLUTION_JOB)) {
		    	// uri is null, so retrieve the domain by the lookup
		    	MdfDomainImpl domain = (MdfDomainImpl) lookup.getDomain(name);
		    	if(domain!=null) {
		    		// if we found something, put it in the cache
			    	uri = domain.eResource().getURI();
					domainURIMap.put(name, uri);
					return domain;
		    	}
	    	}
			return null;
    	}
    }

	public MdfBusinessType getBusinessType(MdfName qname) {
        MdfDomain domain = getDomain(qname.getDomain());
        return (domain == null) ? null
                : domain.getBusinessType(qname.getLocalName());
	}
	
	public MdfClass getClass(MdfName qname) {
        MdfDomain domain = getDomain(qname.getDomain());
        return (domain == null) ? null
                : domain.getClass(qname.getLocalName());
	}
	
	public MdfEnumeration getEnumeration(MdfName qname) {
        MdfDomain domain = getDomain(qname.getDomain());
        return (domain == null) ? null
                : domain.getEnumeration(qname.getLocalName());
	}
		
	/**
	 * domain model resource change listener
	 * listens to the changes made to the domain model resources
	 * used to update the dataset cache
	 */
	class DomainModelResourceListener implements IResourceChangeListener {

		/**
	     * Visitor for resource changes.
	     */
	    private class ResourceDeltaVisitor implements IResourceDeltaVisitor {
	        public boolean visit(final IResourceDelta delta) throws CoreException {  
	            if (delta.getFlags() != IResourceDelta.MARKERS
	                    && delta.getResource().getType() == IResource.FILE) {
	                if ((delta.getKind() & (IResourceDelta.CHANGED | IResourceDelta.REMOVED)) != 0) {
	                	String path = delta.getFullPath().toString();
	                	if (path.endsWith(".domain")) {
	                		if (delta.getKind() == IResourceDelta.CHANGED) {
	                			notifyChange(delta.getResource());
	                		} else if (delta.getKind() == IResourceDelta.REMOVED) {
	                			notifyRemove(delta.getResource());
	                		}
	                	}
	                }
	            }
	            return true;
	        }    
	    }    	
	    
		protected void notifyChange(IResource res) {
		}    
		
		protected void notifyRemove(IResource res) {			
		}
		
	    public void resourceChanged(IResourceChangeEvent event) {
	        IResourceDelta delta = event.getDelta();
	        try {
				delta.accept(new ResourceDeltaVisitor());
			} catch (CoreException e) {
				logger.error("CoreException from delta.accept(new ResourceDeltaVisitor())", e);
			}
	    }
		
	}

}
