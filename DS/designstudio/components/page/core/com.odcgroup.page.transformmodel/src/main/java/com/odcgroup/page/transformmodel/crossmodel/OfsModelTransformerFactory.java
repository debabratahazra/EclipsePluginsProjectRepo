package com.odcgroup.page.transformmodel.crossmodel;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.odcgroup.page.log.Logger;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * @author pkk
 *
 */
public class OfsModelTransformerFactory {

	/** */
	public static final String EXTENSION_POINT = "com.odcgroup.page.transformmodel.ofsModelTransformer";
	/** */
	public static final String TRANSFORMER_CONFIG_ELEMENT = "transformer";
	/** */
	public static final String CONFIG_EXTN_ATTRIBUTE = "type";
	/** */
	public static final String CLASS_ATTRIBUTE = "class";	
	/** ofsmodel type-transformer map */
	private Map<String, IOfsModelTransformer> transformerMap = new HashMap<String, IOfsModelTransformer>();
	/** */
	private static OfsModelTransformerFactory INSTANCE = null;
	
	/**
	 * private default constructor
	 */
	private OfsModelTransformerFactory() {
		loadTransformersFromRegistry();
	}
	
	/**
	 * @return OfsModelTransformerFactory
	 */
	public static OfsModelTransformerFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new OfsModelTransformerFactory();
		}
		return INSTANCE;
	}
	
	/**
	 * fetches the appropriate transformer for the given resource
	 * if not found, returns the basic transformer
	 * @param modelResource
	 * @return IOfsModelTransformer
	 */
	public IOfsModelTransformer getTransformer(IOfsModelResource modelResource) {
		String type = modelResource.getURI().fileExtension();
		IOfsModelTransformer transformer = transformerMap.get(type);
		if (transformer == null) {
			transformer = new BasicTransformer();
		}
		return transformer;
	}
	
	/**
	 * load all the transformers from the defined extension points
	 * 
	 */
	private void loadTransformersFromRegistry() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
        IOfsModelTransformer transformer = null;
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if (TRANSFORMER_CONFIG_ELEMENT.equals(elements[j].getName())) {					
					try {
						String extn = elements[j].getAttribute(CONFIG_EXTN_ATTRIBUTE);
						transformer = (IOfsModelTransformer) elements[j].createExecutableExtension(CLASS_ATTRIBUTE);		
						transformerMap.put(extn, transformer);
					} catch (CoreException e) {
						Logger.error("Error fetch ModelLookupProvider", e);
					}				
				}
			}
		}
	}

}
