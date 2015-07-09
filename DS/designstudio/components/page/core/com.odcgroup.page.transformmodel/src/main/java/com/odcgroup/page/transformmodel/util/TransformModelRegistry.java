package com.odcgroup.page.transformmodel.util;

import com.odcgroup.page.common.ModelRegistry;
import com.odcgroup.page.transformmodel.TransformModel;

/**
 * This is a singleton class containing the main meta-model. In other
 * words it contains the definitions of the WidgetTypeTransformations, PropertyTypeTransformations etc.
 * 
 * @author Gary Hayes
 */
public class TransformModelRegistry extends ModelRegistry {
	
	/** The platform-relative path to the resources. */
	private static final String RESOURCES_PATH = "platform:/plugin/com.odcgroup.page.metamodel/src/main/resources";

	/** The location of the transform-model configuration file relative to the project base. */
	private static final String TRANSFORM_MODEL_FILE_LOCATION = RESOURCES_PATH + "/page-designer.transformmodel";	

	/** The unique instance of the TransformModelRegistry. */
	private static TransformModelRegistry INSTANCE = new TransformModelRegistry();
		
	/**
	 * Creates a new instance of the TransformModelRegistry.
	 */
	private TransformModelRegistry() {
		loadResource(TRANSFORM_MODEL_FILE_LOCATION);
	}
	
	/**
	 * Gets the TransformModel
	 * 
	 * @return TransformModel The TransformModel
	 */
	public static TransformModel getTransformModel() {
		return (TransformModel)INSTANCE.getModel();
	}

}