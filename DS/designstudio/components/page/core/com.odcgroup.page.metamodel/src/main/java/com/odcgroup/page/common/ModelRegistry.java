package com.odcgroup.page.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Abstract base class for all model registry used to configure and run the page
 * designer. All these models must me loaded in the same ResourceSet instance.
 * 
 * @author <a href="mailto:atripod@odyssey-group.com">Alain Tripod</a>
 */
public abstract class ModelRegistry {

	/**
	 * Contains all (meta)models used by the page designer itself. widget
	 * libraries (WidgetTypes, PropertyTypes,...), user interfaces (rendering
	 * info, palettes, menus...), transformations (namespaces, rules,...).
	 * <p>
	 * 
	 * These intrinsic models are shared by all Odyssey Projects that
	 * maintain their own ResourceSet instance. 
	 */
	private static ResourceSet RESOURCE_SET = new ResourceSetImpl();

	/**
	 * The resource containing the model
	 */
	private Resource resource;

	/**
	 * Constructor
	 */
	protected ModelRegistry() {
	}

	/**
	 * Loads the resources designated by the path
	 * 
	 * @param path
	 */
	protected void loadResource(String path) {
		URI uri = URI.createURI(path);
		resource = getResourceSet().getResource(uri, true);
		if (resource != null) {
			resourceLoaded(resource);
		}
	}

	/**
	 * @return the model from the resource;
	 */
	protected Object getModel() {
		return resource.getContents().iterator().next();
	}

	/**
	 * Callback to notify concrete registry when a resource is loaded.
	 * 
	 * @param resource
	 *            the resource loaded
	 */
	protected void resourceLoaded(Resource resource) {
		;// override it in concrete sub class
	}

	/**
	 * Gets the resource set containing all intrinsic model of the page
	 * designer.
	 * 
	 * @return ResourceSet the resource set
	 */
	protected final ResourceSet getResourceSet() {
		return ModelRegistry.RESOURCE_SET;
	}

}
