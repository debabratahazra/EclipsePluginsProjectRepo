package com.odcgroup.workbench.core.internal.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.resources.OfsModelResource;

/**
 * This class extends the EMF ResourceSetImpl by DS specific functionality. It
 * allows handling IOfsModelResources besides standard EMF Resources. This
 * especially means that we can hold a scope of the resources.
 * 
 * @author Kai Kreuzer
 */
public class ModelResourceSetImpl extends SynchronizedXtextResourceSet implements ModelResourceSet {
	private static final Logger logger = LoggerFactory.getLogger(ModelResourceSetImpl.class);

	protected IOfsProject ofsProject;

	protected Map<URI, Map<Integer, IOfsModelResource>> resourceMap = new HashMap<URI, Map<Integer, IOfsModelResource>>();
	
	protected Map<String /* model file extension */, Set<URI>> resourceTypeMap = new HashMap<String, Set<URI>>();

	private RemovalListener<URI, Resource> listener = new RemovalListener<URI, Resource>() {
		@Override
		public void onRemoval(RemovalNotification<URI, Resource> removal) {
			if (removal != null && removal.getValue() != null) {
				logger.debug("Unloading ({}/{}) {}", new Object[] { 
						(Long) resourceCache.size(), 
						FeatureSwitches.modelResourceSetResourceCacheSize.get(), 
						removal.getKey().toString() });
				removal.getValue().unload();
			}
		}
	};

	// NOTE: resourceCache may be null if FeatureSwitches.modelResourceSetResourceCacheSize == -2. This is handled below where it's used. 
	protected final Cache<URI, Resource> resourceCache;
	
	public ModelResourceSetImpl(/* DS-7435 No Arg! */) {
		super();
		
    	setURIResourceMap(new HashMap<URI,Resource>());
		
		final Integer cacheSize = FeatureSwitches.modelResourceSetResourceCacheSize.get();
		if (cacheSize < -1) { // (-2)
			resourceCache = null;
			return;
		}
		
		// DS-6602 This cache is no more limited in time as this caused DS freezing for several minutes if not used for more than 30 minutes.
		// DS-6707 The weak references of resource was never garbage collected unless unload is called on it, because the ResourceSet has strong references to all Resource anyways, once loaded.
		//         So now we're just setting the maximumSize to evict the least referenced resource. This doesn't technical evict as and when memory is required (but it never did before either),
		//         but still allows some caching. In case of OOMs, at least we can now tune perf vs. memory with the RESOURCE_CACHE_MAXSIZE_PROPERTY. 
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder(); // NOT .weakValues() NOR .expireAfterAccess(30, TimeUnit.MINUTES)
		
		if (cacheSize > -1) // So if it's -1, then no maximumSize() is set.. 
			cacheBuilder = cacheBuilder.maximumSize(cacheSize);
			
		resourceCache = cacheBuilder.removalListener(listener).build(new CacheLoader<URI, Resource>() {
			@Override public Resource load(URI uri) throws Exception {
				return reallyGetResource(uri);
			}
		});
	}
	
	public void setOfsProject(IOfsProject ofsProject) {
		if (this.ofsProject != null)
			throw new IllegalStateException("Huh, why do you want to reset the ofsProject of the ModelResourceSet?!");
		this.ofsProject = ofsProject;
	}

	private Resource reallyGetResource(URI uri) throws ExecutionException {
		Resource res = super.getResource(uri, false);
		if (res != null) {
			return res;
		} else {
			throw new ExecutionException("Resource " + uri.toString() + " could not be found.", null);
		}
	}

	public Resource createResource(URI uri, String contentType, int scope) {
		if ((scope & IOfsProject.SCOPE_PROJECT) == 0) {
			uri = uri.appendQuery(Integer.toString(scope));
		}
		return super.createResource(uri, contentType);
	}

	public Resource createResource(URI uri, int scope) {
		if ((scope & IOfsProject.SCOPE_PROJECT) == 0) {
			uri = uri.appendQuery(Integer.toString(scope));
		}
		return super.createResource(uri);
	}

	public Resource createResource(URI uri, String contentType) {
		Resource.Factory resourceFactory = getResourceFactoryRegistry().getFactory(uri, contentType);
		if (resourceFactory != null) {
			Resource result = resourceFactory.createResource(uri);
			getResources().add(result);
			return result;
		} else {
			return null;
		}
	}

	public Resource getResource(URI uri, int scope) {
		if ((scope & IOfsProject.SCOPE_PROJECT) == 0) {
			uri = uri.appendQuery(Integer.toString(scope));
		}
		return getResource(uri, false);
	};
	
	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		if (ModelURIConverter.isModelUri(uri)) {
			// DS-7577 convert to platform uri
			// we have resource scheme URI as references in the models (included modules, fragments, pageflows etc)
			// when we load those models, we need to convert resource uri's to platform uris for resolving them
			ModelURIConverter converter = new ModelURIConverter(getOfsProject());
			uri = converter.toPlatformURI(uri);
		} 
		
		if(uri.isPlatformResource()) {
    		Resource res = null;
			try {
				if (resourceCache != null) {
					res = resourceCache.get(uri);
				} else {
					res = reallyGetResource(uri);
				}
				if(loadOnDemand) 
					res.load(Collections.EMPTY_MAP);
			} catch (ExecutionException e) {
				logger.error("Could not create EMF resource in cache for '{}'",uri.toString(), e);
			} catch (IOException e) {
				logger.error("Could not load EMF resource '{}'", uri.toString(), e);
			}
			if (res != null) {
				return res;
			}
    	} else if("java".equals(uri.scheme()) ) {
    		return super.getResource(uri, loadOnDemand);
    	} else if ("mdfname".equals(uri.scheme())) { // cannot use the (more appropriate) MdfNameURIUtil.isMdfNameURI(uri) here, due to (correct) dependency visibility
    		// due to http://rd.oams.com/browse/DS-5605 (this check can, probably, be removed after the DomainLinkingService has been simplified)
    		return null;
    	}
   		logger.warn("Trying super.getResource() for strange unknown (neither platform nor resource) scheme URI: " + uri); 
   		return super.getResource(uri, loadOnDemand);
    		
	}

	public synchronized Set<IOfsModelResource> getAllOfsModelResources(String[] modelTypes, int scope) {
		Set<IOfsModelResource> resources = new HashSet<IOfsModelResource>();
		for (String modelType : modelTypes) {
			Set<URI> uriSet  = resourceTypeMap.get(modelType);
			if (uriSet != null) {
				for (URI uri : uriSet) {
					IOfsModelResource resource = getOfsModelResource(uri, scope);
					if (resource != null)
						resources.add(resource);
				}
			}
		}
		return resources;
	}

	public synchronized Set<IOfsModelResource> getAllOfsModelResources() {
		Set<IOfsModelResource> resources = new HashSet<IOfsModelResource>();
		for (URI uri : resourceMap.keySet()) {
			IOfsModelResource resource = getOfsModelResource(uri);
			if (resource != null)
				resources.add(resource);
		}
		return resources;
	}

	public synchronized IOfsModelResource createOfsModelResource(URI uri, IStorage storage, int scope) {
		Map<Integer, IOfsModelResource> scope2resources = resourceMap.get(uri);
		if (scope2resources == null) {
			scope2resources = new HashMap<Integer, IOfsModelResource>();
			resourceMap.put(uri, scope2resources);
		}
		IOfsModelResource resource = scope2resources.get(scope);
		if (resource == null) {
			resource = new OfsModelResource(ofsProject, uri, storage, scope);
			scope2resources.put(scope, resource);
			createResource(uri, scope);
			String modelType = uri.fileExtension();
			Set<URI> uriSet = resourceTypeMap.get(modelType);
			if (uriSet == null) {
				uriSet = new HashSet<URI>();
				resourceTypeMap.put(modelType, uriSet);
			}
			uriSet.add(uri);
		}
		return resource;
	}

	public IOfsModelResource createOfsModelResource(IStorage storage, int scope) {
	    URI  uri = ModelURIConverter.createModelURI(storage);
	    //remove the resource from cache if the resource was deleted and created again.
	    if(resourceCache != null && resourceCache.asMap().containsKey(uri) && !ofsProject.ofsModelResourceExists(uri)) {
	    	resourceCache.asMap().remove(uri);
	    }

	    return createOfsModelResource(uri, storage, scope);
	}

	public synchronized IOfsModelResource getOfsModelResource(URI uri) {
		//DS-7577 convert model uri (arriving from model references) if any to platform 
		if (ModelURIConverter.isModelUri(uri)) {
			uri = getPlatformURI(uri);
		}
		Map<Integer, IOfsModelResource> scope2resource = resourceMap.get(uri
				.trimQuery().trimFragment());
		if (scope2resource == null)
			return null;
		if (scope2resource.values().size() == 1)
			return scope2resource.values().iterator().next();

		List<Integer> scopes = new ArrayList(scope2resource.keySet());
		Collections.sort(scopes);
		int newScope = 0;
		for (int scope : scopes) {
			newScope = newScope | scope;
		}
		// our resource to select is the one with the highest prio scope (i.e.
		// the smallest scope int)
		if(scopes.size() > 0) {
			OfsModelResource resource = (OfsModelResource) scope2resource.get(scopes.get(0));
			return new OfsModelResource(ofsProject, uri, resource.getStorage(),
					newScope);
		}
		return null;
	}

	public synchronized IOfsModelResource getOfsModelResource(URI uri, int scope) {
		//DS-7577 convert model uri (arriving from model references) if any to platform 
		if (ModelURIConverter.isModelUri(uri)) {
			uri = getPlatformURI(uri);
		}
		Map<Integer, IOfsModelResource> scope2resource = resourceMap.get(uri);
		if (scope2resource == null)
			return null;
		if (scope2resource.get(scope) != null)
			return scope2resource.get(scope);
		IOfsModelResource resource = scope2resource.get(scope);
		if (resource != null)
			return resource;

		List<Integer> scopes = new ArrayList(scope2resource.keySet());
		Collections.sort(scopes);
		int newScope = 0;
		for (int scp : scopes) {
			if ((scp & scope) > 0) {
				newScope = newScope | scp;
			}
		}
		if (newScope == 0)
			return null;
		// our resource to select is the one with the highest prio scope (i.e.
		// the smallest scope int)
		OfsModelResource mergedResource = (OfsModelResource) scope2resource
				.get(scopes.get(0));
		return new OfsModelResource(ofsProject, uri,
				mergedResource.getStorage(), newScope);
	}

	public synchronized void removeResource(URI uri, int scope) {
		String uriWithScope = (scope == IOfsProject.SCOPE_PROJECT) 
				? uri.toString() 
				: uri.appendQuery(Integer.toString(scope)).toString();

		// gets the list of all resources
		List list = getResources();
		for (int i = 0; i < list.size(); i++) {
			Resource r = (Resource) list.get(i);

			if(r != null && r.getURI().toString().equals(uriWithScope)) {
				// If resource equals current object then remove it
				r.unload();
				if (resourceCache != null)
					resourceCache.invalidate(r.getURI());
				list.remove(r);
				break;
			}
		}
		
		Map<Integer, IOfsModelResource> scope2resources = resourceMap.get(uri);
		if (scope2resources != null) {
			scope2resources.remove(scope);
			if (scope2resources.isEmpty()) {
				resourceMap.remove(uri);
				String modelType = uri.fileExtension();
				Set<URI> uriSet = resourceTypeMap.get(modelType);
				if (uriSet != null) {
					uriSet.remove(uri);
					if (uriSet.isEmpty()) {
						resourceTypeMap.remove(modelType);
					}
				}
			}
		}
	}

	// only needed & introduced due to need in OfsResourceHelper.getOfsProject(Resource)
	public IOfsProject getOfsProject() {
		return ofsProject;
	}

	@Override
	public ModelURIConverter getModelUriConverter() {
		return (ModelURIConverter) getURIConverter();
	}
	
	/*
	 * converts the model URI to platform URI
	 */
	private URI getPlatformURI(URI uri) {
		final IOfsProject ofsProject = getOfsProject();
		if (ofsProject == null)
			throw new IllegalStateException("the corresponding ofsProject is null");
		IProject project = ofsProject.getProject();
		String path = uri.path();
		String extn = uri.fileExtension();
		String fullpath = "/"+project.getName()+"/"+extn+path;
		return URI.createPlatformResourceURI(fullpath, false);
	}
}
