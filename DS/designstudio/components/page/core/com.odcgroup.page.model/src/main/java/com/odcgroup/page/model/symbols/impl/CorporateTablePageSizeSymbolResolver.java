
package com.odcgroup.page.model.symbols.impl;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.model.corporate.CorporateDesign;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class CorporateTablePageSizeSymbolResolver extends AbstractSymbolResolver {
	
	/**
	 * @see com.odcgroup.page.model.symbols.SymbolResolver#getValue(java.lang.String, java.lang.Object)
	 */
	public <T> String getValue(String symbol, T context) {
		CorporateDesign cd = getCorporateDesign((EObject)context);
		return cd.getTablePageSize();
	}

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public CorporateTablePageSizeSymbolResolver(String name, String description) {
		super(name, description);
	}

	/**
	 * 
	 */
	public CorporateTablePageSizeSymbolResolver() {

	}

}
