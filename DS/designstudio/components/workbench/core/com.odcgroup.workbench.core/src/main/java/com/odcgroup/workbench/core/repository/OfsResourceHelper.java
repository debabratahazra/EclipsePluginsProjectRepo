package com.odcgroup.workbench.core.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.internal.repository.ModelResourceSetImpl;
import com.odcgroup.workbench.core.xtext.ClasspathTypeProviderJavaURIConverterUtil;

public class OfsResourceHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(OfsResourceHelper.class);	

	

	static public Collection<IOfsModelResource> filter(Collection<IOfsModelResource> modelResources, final String[] modelTypes) {
		
		Collection<IOfsModelResource> filteredResources = new HashSet<IOfsModelResource>(modelResources);

		Predicate predicate = new Predicate() {
			public boolean evaluate(Object obj) {
				if(obj instanceof IOfsModelResource) {
					IOfsModelResource modelResource = (IOfsModelResource) obj;
					if(ArrayUtils.contains(modelTypes, modelResource.getURI().fileExtension()))
						return true;
				}
				return false;
			}
		};
		CollectionUtils.filter(filteredResources, predicate);
		
		return filteredResources;
	}
	
	/*
	 * checks if the given URI is of a valid model, 
	 * by checking if it is from the right model folder
	 */
	public static boolean isURIofValidModelFolder(URI uri) {
		String extn = uri.fileExtension();
		String warningPrefix = "Model URI is not in valid folder, will be ignored in indexing: ";
		if (uri.isPlatformResource()) {
			String[] segments = uri.segments();
			if (!extn.equals(segments[2])) {
				logger.warn(warningPrefix + uri.toString());
				return false;
			}
		} else if (ModelURIConverter.isModelUri(uri)) {
			if (!extn.equals(uri.segment(0))) {
				logger.warn(warningPrefix + uri.toString());
				return false;
			}
		} else {
			logger.warn(uri.scheme()+": unsupported URI scheme: " + uri.toString());
			return false;
		}
		return true;
	}

	/**
	 * @param resource
	 * @return
	 */
	public static IFile getFile(Resource resource) {
		if(resource==null) return null;
		URI uri = resource.getURI();
	    uri = resource.getResourceSet().getURIConverter().normalize(uri);
	    if(ModelURIConverter.isModelUri(uri)) {
	    	uri = new ModelURIConverter(OfsResourceHelper.getOfsProject(resource)).toPlatformURI(uri);
	    }
	    if (uri != null && "platform".equals(uri.scheme()) && uri.segmentCount() > 1
	            && "resource".equals(uri.segment(0))) {
	        StringBuffer platformResourcePath = new StringBuffer();
	        int j = 1;
	        for (int size = uri.segmentCount(); j < size; j++) {
	            platformResourcePath.append('/');
	            platformResourcePath.append(uri.segment(j));
	        }
	
	        return ResourcesPlugin.getWorkspace().getRoot().getFile(
	                new Path(platformResourcePath.toString()));
	    } 
	    return null;
	}
	
	
	/**
	 * strip the resource, project-name, model folder segments from the platform URI
	 * 
	 * @param uri - platformUri
	 * @return
	 */
	public static String getPathFromPlatformURI(URI uri) {
		String str = uri.path();
		if (uri.isPlatformResource()) {
			List<String> segments = uri.segmentsList();
			String result = "";
			for(int i=3;i<segments.size();i++) {
				result += "/"+segments.get(i);				
			}
			str = result;
		}
		return str;
	}

	/**
	 * check whether the resource is a readonly one
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isResourceReadOnly(Resource resource) {
		if (resource == null)
			return true;
		IFile file = OfsResourceHelper.getFile(resource);
		if (file != null && file.isReadOnly()) {
			return true;
		} else if (file != null && !file.isReadOnly()) {
			return false;
		}

		IOfsProject ofsProject = getOfsProject(resource);
		if (ofsProject != null) {
			IOfsModelResource mResource = null;
			try {
				mResource = ofsProject.getOfsModelResource(resource.getURI());
			} catch (ModelNotFoundException e) {
			}
			if (mResource == null || mResource.isReadOnly()) {
				return true;
			}
		}
		return false;
	}

	public static IOfsProject getOfsProject(Resource resource) {
		if(resource==null || resource.getResourceSet()==null) return null;
		
		ResourceSet rSet = resource.getResourceSet();
		URIConverter uriConverter = rSet.getURIConverter();
		uriConverter = ClasspathTypeProviderJavaURIConverterUtil.getUnderlyingExistingURIConverterOrSame(uriConverter); // DS-7338
		if (uriConverter instanceof ModelURIConverter) {
			ModelURIConverter mConverter = (ModelURIConverter)uriConverter;
			return mConverter.getOfsProject();
		} else if (rSet instanceof ModelResourceSetImpl) { 
			// This is needed because since v9.2.0 with Xtext Upgrade & Itemis Domain improvements
			// rSet.getURIConverter() may be a ClasspathTypeProvider$JavaURIConverter
			// which wraps an existing ModelURIConverter - so above won't catch it,
			// but this will do the trick
			final ModelResourceSetImpl modelResourceSet = (ModelResourceSetImpl) rSet;
			return modelResourceSet.getOfsProject();
		} else {
			// check for platform resource
			IFile file = getFile(resource);
			if (file != null) {
				return OfsCore.getOfsProjectManager().getOfsProject(file.getProject());
			}
		}
		return null;
	}

	/**
	 * @param resource
	 * @return
	 */
	public static IProject getProject(Resource resource){
		if (resource == null) {
			return null;
		}
		URI uri = resource.getURI();
		IFile file = null;
		if (uri.isPlatformResource()){
			String path = uri.toPlatformString(true);
			file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
		} else {
			file = OfsResourceHelper.getFile(resource);
		}
		if (file == null) {
			return null;
		}
		return file.getProject();
	}

	/**
	 * @param resource
	 * @param path
	 * @return
	 */
	public static IFile getFile(Resource resource, URI uri){
		IOfsProject ofsProject = getOfsProject(resource);
		if (ofsProject != null){
			return getFile(ofsProject, uri);
		}
		return null;
	}

	/**
	 * fetch storage for IOfsModelResource
	 * @param mResource
	 * @return
	 */
	public static IFile getFile(IOfsModelResource mResource) {
		IFile file = null;
		if (mResource != null){
			if (mResource.getResource() instanceof IFile) {
				file = (IFile) mResource.getResource();
			}
		}
		return file;
	}

	/**
	 * 
	 * @param ofsProject
	 * @param uri
	 * @return
	 */
	public static IFile getFile(IOfsProject ofsProject, URI uri) {
		IFile file = null;
		if(uri.isPlatformResource()) {
			String path = uri.toPlatformString(true);
			file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
		}
		if(file == null) {
			IOfsModelResource mResource;
			try {
				mResource = ofsProject.getOfsModelResource(uri);
				file = getFile(mResource);
			} catch (ModelNotFoundException e) {
				OfsCore.getDefault().logError("Model not found for URI ["+uri.toString()+"]", e);
				return null;
			}
		}
		return file;
	}
}
