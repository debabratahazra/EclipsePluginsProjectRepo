package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Specialized PropertySourceAdapter for property that can contains symbols
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class DefaultPropertySourceAdapter extends AbstractPropertySourceAdapter {

	/**
	 * @return the actual value of the underlying property
	 */
	protected String doGetPropertyValue() {
		String s = getProperty().getValue();
		return s == null ? "" : s;
	}
	
	/**
	 * Creates a new DomainAttributePropertySourceAdapter.
	 * 
	 * @param property
	 *            The Property
	 * @param commandStack
	 *            The command stack to execute the command
	 */
	public DefaultPropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
	}

	/**
	 * Gets the value of the property.
	 * 
	 * @return Object The value of the property
	 * @see IPropertySource#setPropertyValue(Object, Object)
	 */
	public Object getPropertyValue() {
		String s = doGetPropertyValue();
		SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
		if (expander != null) {
			s = expander.substitute((String) s, null);
		}
		return s == null ? "" : s;
	}

	/**
	 * Sets the value of the property.
	 * 
	 * @param newValue
	 *            The value of the property
	 * 
	 * @see IPropertySource#setPropertyValue(Object, Object)
	 */
	public void setPropertyValue(Object newValue) {
		getCommandStack().execute(
				new UpdatePropertyCommand(getProperty(), newValue != null ? String.valueOf(newValue) : ""));
	}

}