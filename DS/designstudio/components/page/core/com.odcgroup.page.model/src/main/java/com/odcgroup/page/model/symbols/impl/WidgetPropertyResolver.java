package com.odcgroup.page.model.symbols.impl;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * @author atr
 */
public class WidgetPropertyResolver extends AbstractSymbolResolver {

	/*
	 * @see
	 * com.odcgroup.page.model.symbols.SymbolResolver#getValue(java.lang.String,
	 * java.lang.Object)
	 */
	public <T> String getValue(String symbol, T context) {
		String result = symbol;
		Widget widget = (Widget) context;
		Property prop = widget.findProperty(symbol);
		if (prop != null) {
			result = prop.getValue();
		}
		return result;
	}

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public WidgetPropertyResolver(String name, String description) {
		super(name, description);
	}

	/**
	 * 
	 */
	public WidgetPropertyResolver() {

	}

}
