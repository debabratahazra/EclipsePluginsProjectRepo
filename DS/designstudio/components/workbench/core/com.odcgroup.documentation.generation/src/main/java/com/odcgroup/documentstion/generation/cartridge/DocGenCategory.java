package com.odcgroup.documentstion.generation.cartridge;

import com.odcgroup.documentation.generation.DocumentationCore;

public enum DocGenCategory {
//	LPD    ("List of project models (XLS)"),
	DMN		("Domain (XLS)"),
	TRNS    ("Translations (XLS)"),
//	WRKFLW   ("Workflow (HTML)"),
	PGFLW   ("Pageflow (HTML)"),
	PAGE   ("Page (HTML)"),
	MOD_FRG   ("Module/Fragment (HTML)"),
	MOD_TBL   ("Module table (HTML)"),
	MOD_MTRX   ("Module matrix (HTML)"); 
//	MENU   ("Menu (HTML)"), 
//	VR    ("Visual Rules (HTML)");
	
	
	// these are the categories for component specific code
//	API_D_S    ("API (Domain) component-specific"),
//	API_R_S    ("API (Rules) component-specific"),
//	IMPL_D_S   ("Implementation (Domain) component-specific"), 
//	IMPL_R_S   ("Implementation (Rules) component-specific"), 
//	EJB_D_S    ("EJB (Domain) component-specific"),
//	EJB_R_S    ("EJB (Rules) component-specific"),
//	WUIBLOCK_S ("WUI Block component-specific"),
	
	
	private final String name;
	
	private DocGenCategory(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	static public DocGenCategory fromString(String categoryName) throws CategoryNotFoundException {
		for(DocGenCategory category : DocGenCategory.values()) {
			if(category.name.equals(categoryName)) {
				return category;
			}
		}
		DocumentationCore.getDefault().logWarning(
				"Documentation Generation category '" + categoryName + "' could not be found, " +
				"using 'Implementation (Domain)' as a fallback.", null);
		return DMN;
	}
}
