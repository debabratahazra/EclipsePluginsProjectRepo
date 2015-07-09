package com.odcgroup.workbench.core.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.IOfsProjectDisposalListener;
import com.odcgroup.workbench.core.OfsCore;


public class ModelURIConverter implements URIConverter {
	private static final Logger logger = LoggerFactory.getLogger(ModelURIConverter.class);

    /**
	 * This dummy implementation of IOfsProject is used if no ofs project is provided to the
	 * constructor of the URI converter. This might be the case if the resource to load is not
	 * contained in a model project.
	 *
	 * @author Kai Kreuzer
	 */
	static public class DummyOfsProject implements IOfsProject {

		public void addDisposalListener(IOfsProjectDisposalListener listener) {}
		public void clearCache() {}

		public IOfsModelFolder[] getAllModelFolders() {
			return null;
		}
		
		public boolean hasModelFolders() {
			return false;
		}

		public IOfsModelFolder getModelFolder(String modelType) {
			return null;
		}

		public ModelResourceSet getModelResourceSet() {
			return null;
		}

		public IOfsModelResource getOfsModelResource(URI uri) throws ModelNotFoundException {
			return null;
		}

		public boolean ofsModelResourceExists(URI uri) {
			return false;
		}
		
		public IProject getProject() {
			return null;
		}

		public void lookup(IModelVisitor visitor) {}
		public void refresh() {}
		public void removeDisposalListener(IOfsProjectDisposalListener listener) {}
		public void setProject(IProject project) {}
		public void resourceChanged(IResourceChangeEvent event) {}
		
		public IContainerIdentifier getIdentifier() {
			return null;
		}

		public String getName() {
			return null;
		}
		@Override
		public String toString() {
			return "DummyOfsProject [getClass()=" + getClass().getName() + "]";
		}
	}

	public static final String SCHEME = "resource";

    private final URIConverter delegate;
    private final IProject project;

    public static URI createModelURI(String path) {    
    	return URI.createPlatformResourceURI(path, true);
    }

    public static URI createModelURI(IResource resource) {
    	IPath path = resource.getFullPath().makeAbsolute();
    	return createModelURI(path.toString());
    }

    public static URI createModelURI(IStorage storage) {
    	if(storage instanceof IResource) return createModelURI((IResource) storage);    	
    	return createModelURI(storage.getFullPath().toString());
    }

    public ModelURIConverter(IOfsProject project) {
        this(project, new ExtensibleURIConverterImpl());
    }

    public ModelURIConverter(IOfsProject project, URIConverter delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException();
        }

        if(project == null) project = new DummyOfsProject();
        
        this.delegate = delegate;
        this.project = project.getProject();
    }

    /**
     * @see org.eclipse.emf.ecore.resource.URIConverter#createInputStream(org.eclipse.emf.common.util.URI)
     */
    public InputStream createInputStream(URI uri) throws IOException {
        URI normalized = normalize(uri);

        if (SCHEME.equals(normalized.scheme())) {
            return privateCreateInputStream(normalized);
        }

        return delegate.createInputStream(normalized);
    }

    /**
     * @see org.eclipse.emf.ecore.resource.URIConverter#createOutputStream(org.eclipse.emf.common.util.URI)
     */
    public OutputStream createOutputStream(URI uri) throws IOException {
    	assert uri!=null;
    	
    	URI platformURI = null;
    	
    	if (!uri.isPlatformResource()) {    	
    		platformURI = toPlatformURI(uri);
    	} else {
    		platformURI = uri;
    	}

        if(platformURI!=null) {
            return delegate.createOutputStream(platformURI);
        }
        
        if (uri.scheme()!=null && SCHEME.equals(uri.scheme())) {
            try {
				return privateCreateOutputStream(uri);
			} catch (ModelNotFoundException e) {
				OfsCore.getDefault().logError("Could not create output stream for " + uri, e);
				throw new IOException(e);
			}
        }

        return delegate.createOutputStream(uri);
    }

    /**
     * @see org.eclipse.emf.ecore.resource.URIConverter#getURIMap()
     */
    public Map getURIMap() {
        return delegate.getURIMap();
    }

    /**
     * @see org.eclipse.emf.ecore.resource.URIConverter#normalize(org.eclipse.emf.common.util.URI)
     */
    public URI normalize(URI uri) {
        return delegate.normalize(uri);
    }

    private OutputStream privateCreateOutputStream(URI uri) throws IOException, ModelNotFoundException {
        OutputStream os = getOfsProject().getOfsModelResource(uri).getOutputStream();

        if (os == null) {
            throw new IOException(uri.path() + " is not writeable");
        } else {
            return os;
        }
    }

    static private class FileSearchVisitor implements IResourceVisitor {
		final String modelFileName;
		IFile modelFile = null;
		
		public FileSearchVisitor(String modelFileName) {
			this.modelFileName = modelFileName;
		}
		
		public boolean visit(IResource resource) throws CoreException {
			if(resource instanceof IFile) {
				if(resource.getName().equals(modelFileName)) {
					modelFile = (IFile) resource;
				}
				return false;
			}
			return true;
		}
		
		public IFile getModelFile() {
			return modelFile;
		}
    }

    private InputStream privateCreateInputStream(URI uri) throws IOException {
        InputStream is = null;
		if(OfsCore.isOfsProject(project)) {
			try {
				is = getOfsProject().getOfsModelResource(uri).getContents();
			} catch (ResourceException e) {
	            throw new FileNotFoundException(uri.path());
			} catch (ModelNotFoundException e) {
	            throw new FileNotFoundException(uri.path());
			} catch (CoreException e) {
	            throw new FileNotFoundException(uri.path());
			}
		} else {
			// fallback solution for model files that are not stored in a proper ofs project, but somewhere else
			String filename = uri.lastSegment();
			FileSearchVisitor visitor = new FileSearchVisitor(filename);
			try {
				project.accept(visitor);
			} catch (CoreException e) {
				// ignore and log a warning
				OfsCore.getDefault().logWarning("Could not lookup the file " + filename + " in the workspace", e);
			}			
			IFile modelFile = visitor.getModelFile();
			if(modelFile!=null) {
				is = new FileInputStream(modelFile.getLocation().toFile());
			}
		}

        if (is == null) {
            throw new FileNotFoundException(uri.path());
        } else {
            return is;
        }
    }

	public IOfsProject getOfsProject() {
		return OfsCore.getOfsProjectManager().getOfsProject(project);
	}
	
	public URI toPlatformURI(URI uri) {
    	if(uri.isPlatform()) return uri;
    	if(uri.scheme()!=null && SCHEME.equals(uri.scheme())) {
    		final IOfsProject ofsProject = getOfsProject();
			if (ofsProject == null)
				throw new IllegalStateException("ofsProject == null, project: " + project);
			String path = uri.path();
			String extn = uri.fileExtension();
			String fullpath = "/"+project.getName()+"/"+extn+path;
			return URI.createPlatformResourceURI(fullpath, false);
    	}
    	logger.warn("URI is of neither platform:/ nor Model resource:/ scheme, what is this (will return null..): " + uri.toString());
    	return null;
	}

	public Map<String, ?> contentDescription(URI uri, Map<?, ?> options)
			throws IOException {
		return delegate.contentDescription(uri, options);
	}

	public InputStream createInputStream(URI uri, Map<?, ?> options)
			throws IOException {
		return createInputStream(uri);
	}

	public OutputStream createOutputStream(URI uri, Map<?, ?> options)
			throws IOException {
		return createOutputStream(uri);
	}

	public void delete(URI uri, Map<?, ?> options) throws IOException {
		// if it's not our resource scheme, delegate this request.
		// for the resource scheme, we do not want to physically delete anything
		if(!SCHEME.equals(uri.scheme())) {
			delegate.delete(uri, options);
		} else {
			int scope = uri.query()==null ? IOfsProject.SCOPE_PROJECT : Integer.valueOf(uri.query()); 
			getOfsProject().getModelResourceSet().removeResource(uri.trimQuery(), scope);
		}	
	}

	public boolean exists(URI uri, Map<?, ?> options) {
		return delegate.exists(uri, options);
	}

	public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
		Map<String, Object> attributes = new HashMap(delegate.getAttributes(uri, options));
		if(options==null || attributes.containsKey(URIConverter.ATTRIBUTE_READ_ONLY)) {
			if(uri.query()!=null && (Integer.valueOf(uri.query()) & IOfsProject.SCOPE_PROJECT) == 0)
				attributes.put(URIConverter.ATTRIBUTE_READ_ONLY, true);
		}
		return attributes;
	}

	public EList<ContentHandler> getContentHandlers() {
		return delegate.getContentHandlers();
	}

	public URIHandler getURIHandler(URI uri) {
		return delegate.getURIHandler(uri);
	}

	public EList<URIHandler> getURIHandlers() {
		return delegate.getURIHandlers();
	}

	public void setAttributes(URI uri, Map<String, ?> attributes,
			Map<?, ?> options) throws IOException {
		delegate.setAttributes(uri, attributes, options);		
	}

	/** 
	 * DS-7577 - we no longer need platform URI to be converted to "our" resource scheme URI
  	 * changes made to check if the passed uri is a platform one
  	 * 
	 * @param platformUri A platform uri for a local resource (in the workspace)
	 * @return the according URI
	 */	
	static public URI toResourceURI(URI uri) {		
    	if(uri.isPlatformResource()) {
    		return uri;
    	} else {
    		throw new IllegalArgumentException(uri + " is no platform resource uri!");
    	}
	}
	
	public static boolean isModelUri(URI uri) {
		return SCHEME.equals(uri.scheme());
		// Do NOT || (uri.isPlatformResource() && uri.segmentCount() > 0 && SCHEME.equals(uri.segment(0)));
		// we do NOT want code that relies on this method to think that standard platform:/ URIs
		// are our own resource:/ (no project) URIs - they are not!
	}

	/**
	 * Temporary helper utility, until we've solved the current resource:// vs.
	 * platform:// (needed by Xtext) URI architectural mess in DS.
	 * 
	 * The resource:// URIs were traditionally used by DS, and are relevant for
	 * DS TAP which uses them in page and pageflow models for cross resource
	 * EReference. In DS T24, all cross model references are Xtext QN based,
	 * based on the index which uses platform:// URIs; there the resource://
	 * URIs are causing much more pain than adding any value.
	 * 
	 * What this helper utility does works (it just replaces the URI), but is
	 * borderline a hack.  For example, the URI-based Caches in ModelResourceSetImpl
	 * probably don't digest too well that URI of Resource just suddenly change?
	 * 
	 * A better alternative than using this is to switch the Generator needing this
	 * over to CodeGenerator2, which is platform:// instead of resource:// based.
	 * 
	 * @param resource EMF Resource which (maybe) still has a DS proprietary
	 *            resource:// URI, which will be replaced by a standard Eclipse
	 *            platform:// URI.
	 */
	@Deprecated public static void replaceDSResourceURIByStandardPlatformURI(Resource resource) {
		URI uri = resource.getURI();
		if (ModelURIConverter.isModelUri(uri)) {
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
			if (ofsProject == null)
				throw new IllegalArgumentException("Resource with isModelUri, but cannot its getOfsProject: " + uri);
			ModelURIConverter uriConverter = new ModelURIConverter(ofsProject);
			uri = uriConverter.toPlatformURI(uri);
			resource.setURI(uri);
		}
	}
	/**
	 * Variant of helper taking an IOfsModelResource instead of an EMF Resource.
	 * THIS DOES *NOT* CHANGE THE URI OF THE IOfsModelResource - only of the EMF Resource contained in it; WHAT A HACK.
	 */
	@Deprecated public static void replaceDSResourceURIByStandardPlatformURI(IOfsModelResource ofsResource) {
		URI uri = ofsResource.getURI();
		if (ModelURIConverter.isModelUri(uri)) {
			Resource emfResource = ofsResource.getOfsProject().getModelResourceSet().getResource(uri, false);
			replaceDSResourceURIByStandardPlatformURI(emfResource);
		}
	}
	
}
