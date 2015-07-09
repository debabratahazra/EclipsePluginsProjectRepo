package com.odcgroup.page.uimodel.util;

import com.odcgroup.page.common.ModelRegistry;
import com.odcgroup.page.uimodel.UIModel;

/**
 * This is a singleton class containing the main ui-model. In other
 * words it contains the definitions of the PaletteGroups, RendererInfos etc.
 * 
 * @author Gary Hayes
 */
public class UIModelRegistry extends ModelRegistry {

	/** The platform-relative path to the resources. */
	private static final String RESOURCES_PATH = "platform:/plugin/com.odcgroup.page.metamodel/src/main/resources";

	/** The location of the ui-model configuration file relative to the project base. */
	private static final String UI_MODEL_FILE_LOCATION = RESOURCES_PATH + "/page-designer.uimodel";
	
	/** The unique instance of the UIModelRegistry  */
	private static UIModelRegistry INSTANCE = new UIModelRegistry();
	
	/**
	 * Creates a new instance of the UIModelRegistry.
	 */
	private UIModelRegistry() {
		loadResource(UI_MODEL_FILE_LOCATION);
	}
	
	/**
	 * Gets the UIModel.
	 * 
	 * @return UIModel The UIModel
	 */
	public static UIModel getUIModel() {
		return (UIModel)INSTANCE.getModel();
	}

}