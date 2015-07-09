package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Property;

/**
 * TODO: Document me!
 *
 * @author atr
 * @since DS 1.40.0
 */
public class CorporatePropertySourceAdapter extends DefaultPropertySourceAdapter {

	/**
	 * @param s
	 *            the string to be inspected.
	 *            
	 * @return {@true} if the property's value contains at least one corporate
	 *         symbol to be resolved.
	 */
	protected boolean hasCorportateSymbol(String s) {
		return s.contains("${corporate");
	}
	
	/**
	 * @see com.odcgroup.page.ui.properties.DefaultPropertySourceAdapter#doGetPropertyValue()
	 */
	protected String doGetPropertyValue() {
		String s = super.doGetPropertyValue();
		if (hasCorportateSymbol(s)) {
			s = "Corporate";
		}
		return s;
	}
	/**
	 * @param property
	 * @param commandStack
	 */
	public CorporatePropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
	}

}
