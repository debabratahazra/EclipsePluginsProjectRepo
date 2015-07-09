package com.odcgroup.page.ui.wizard;

import com.odcgroup.page.common.PageConstants;

/**
 * This enumeration defines the different types of models the user can create
 * through wizards for the page designer.
 * 
 * @author atr
 * @since 1.40.0
 */
public enum ModelType {

	/** A Layer model */
	LAYER("layer", "MyLayer", "."+PageConstants.LAYER_FILE_EXTENSION, "Layer"),

	/** A fragment model */
	FRAGMENT("fragment", "MyFragment", "."+PageConstants.FRAGMENT_FILE_EXTENSION, "Fragment"),

	/** A module model */
	MODULE("module", "MyModule", "."+PageConstants.MODULE_FILE_EXTENSION, "Module"),

	/** A page model */
	PAGE("page", "MyPage", "."+PageConstants.PAGE_FILE_EXTENSION, "Page");
	
	/** */
	private String fileExtension;
	/** */ 
	private String modelName;
	/** */ 
	private String templateName;
	/** */ 
	private String typeName;
	
	/**
	 * @param typeName
	 * @param modelName
	 * @param fileExtension
	 * @param templateName
	 */
	ModelType(String typeName, String modelName, String fileExtension, String templateName) {
		this.fileExtension = fileExtension;
		this.modelName = modelName;
		this.templateName = templateName;
		this.typeName = typeName;
	}

	/**
	 * @return the default name of the model
	 */
	public final String getDefaultModelName() {
		return this.modelName;
	}

	/**
	 * @return the extension of the file (including a dot as a prefix)
	 */
	public final String getFileExtension() {
		return this.fileExtension;
	}	

	/**
	 * @return the type name
	 */
	public final String getTypeName() {
		return this.typeName;
	}	
	
	/**
	 * @return the name of the widget template
	 */
	public final String getWidgetTemplateName() {
		return this.templateName;
	}


}
