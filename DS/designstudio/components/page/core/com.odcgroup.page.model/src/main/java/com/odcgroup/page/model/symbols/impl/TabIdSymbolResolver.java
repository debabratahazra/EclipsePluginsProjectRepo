package com.odcgroup.page.model.symbols.impl;

import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;

/**
 * @author atr
 */
public class TabIdSymbolResolver extends AbstractSymbolResolver {

	/*
	 * @see
	 * com.odcgroup.page.model.symbols.SymbolResolver#getValue(java.lang.String,
	 * java.lang.Object)
	 */
	public <T> String getValue(String symbol, T context) {
		String result = null;
		Widget widget = (Widget) context;
		
		ITranslationManager manager = TranslationCore.getTranslationManager(getProject(widget));
		ITranslation translation = manager.getTranslation(widget);
		if (translation != null) {
			result = "T"+System.nanoTime();	// pourquoi le TabId fut basée sur la clé du nom du tab ??
			int pos = result.indexOf('.');
			if (pos != -1) {
				result = result.substring(0,pos);
			}
		}
//			Property prop = widget.findProperty(PropertyTypeConstants.CAPTION);
//			if (prop != null) {
//				result = "T"+prop.getValue();
//				int pos = result.indexOf('.');
//				if (pos != -1) {
//					result = result.substring(0,pos);
//				}
//			}
		return result;
	}

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public TabIdSymbolResolver(String name, String description) {
		super(name, description);
	}

	/**
	 * 
	 */
	public TabIdSymbolResolver() {

	}

}
