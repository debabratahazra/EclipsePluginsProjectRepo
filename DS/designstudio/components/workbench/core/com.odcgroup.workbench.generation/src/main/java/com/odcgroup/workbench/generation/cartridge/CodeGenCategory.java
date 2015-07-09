package com.odcgroup.workbench.generation.cartridge;

import com.odcgroup.workbench.generation.GenerationCore;

public enum CodeGenCategory {
	T24_XML ("T24 Models (XML)", false),
	T24_DATA_FRAMEWORK ("T24 Data Framework"),
	AAA_D    ("AAA (Domain)"),
	API_DYN_D("API Dynamic (Domain)"),
	API_D    ("API (Domain)"),
	API_R    ("API (Rules)"),
	T24_IRIS_JAVA("T24 Interaction Framework (IRIS)", false),
	T24_IRIS_METADATA("T24 Interaction Framework (IRIS) Metadata", false),
	T24_RIM("T24 Interaction Framework (RIM)"),
	T24_COMPONENT_FRAMEWORK_JBC_INSERT   ("T24 Component Framework JBC Insert"),
	T24_COMPONENT_FRAMEWORK_JBC_IMPL   ("T24 Component Framework JBC IMPL"),
	T24_COMPONENT_FRAMEWORK_JBC_API   ("T24 Component Framework JBC API"),
	T24_COMPONENT_FRAMEWORK_JAVA_DATA  ("T24 Component Framework Java Data"),
	T24_COMPONENT_FRAMEWORK_JAVA_WS   ("T24 Component Framework Java Web Service"),
	T24_COMPONENT_FRAMEWORK_JAVA_API   ("T24 Component Framework Java API"),
	T24_COMPONENT_FRAMEWORK_JAVA_PROXY   ("T24 Component Framework Java Proxy"),
	T24_COMPONENT_FRAMEWORK_JAVA_EJB_API   ("T24 Component Framework Java EJB API"),
	T24_COMPONENT_FRAMEWORK_CPP_SERVICE   ("T24 Component Framework CPP Service"),
	T24_COMPONENT_FRAMEWORK_CPP_DATA   ("T24 Component Framework CPP Data"),
	T24_COMPONENT_FRAMEWORK_CPP_PROXY   ("T24 Component Framework CPP Proxy"),
	T24_COMPONENT_FRAMEWORK_DOTNET_API   ("T24 Component Framework Dotnet API"),
	T24_COMPONENT_FRAMEWORK_DOTNET_WS   ("T24 Component Framework Dotnet Web Service"),
	T24_COMPONENT_FRAMEWORK_DOTNET_DATA   ("T24 Component Framework Dotnet Data"),
	T24_EDGE ("T24 Models Edge Browser", false),
	IMPL_D   ("Implementation (Domain)"), 
	IMPL_R   ("Implementation (Rules)"), 
	EJB_D    ("EJB (Domain)"),
	EJB_R    ("EJB (Rules)"),
	WUIBLOCK ("WUI Block"),
	IMPORT   ("WUI Block import"),
	
	// these are the categories for component specific code
	API_D_S    ("API (Domain) component-specific"),
	API_R_S    ("API (Rules) component-specific"),
	IMPL_D_S   ("Implementation (Domain) component-specific"), 
	IMPL_R_S   ("Implementation (Rules) component-specific"), 
	EJB_D_S    ("EJB (Domain) component-specific"),
	EJB_R_S    ("EJB (Rules) component-specific"),
	WUIBLOCK_S ("WUI Block component-specific"),
	CONFIG_S   ("config component-specific");
	
	
	private final String name;
	private final boolean containsJava;
	
	private CodeGenCategory(String name) {
		this.name = name;
		this.containsJava = true;
	}
	
	private CodeGenCategory(String name, boolean containsJava) {
		this.name = name;
		this.containsJava = containsJava;
	}
	
	public String toString() {
		return this.name;
	}
	
	public boolean isContainsJava() {
		return containsJava;
	}

	static public CodeGenCategory fromString(String categoryName) throws CategoryNotFoundException {
		for(CodeGenCategory category : CodeGenCategory.values()) {
			if(category.name.equals(categoryName)) {
				return category;
			}
		}
		GenerationCore.getDefault().logWarning(
				"Code Generation category '" + categoryName + "' could not be found, " +
				"using 'Implementation (Domain)' as a fallback.", null);
		return IMPL_D;
	}
}

