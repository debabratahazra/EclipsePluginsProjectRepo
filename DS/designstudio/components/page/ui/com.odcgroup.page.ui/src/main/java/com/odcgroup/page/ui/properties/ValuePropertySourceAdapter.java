package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * The adapter for the value of a Property.
 * 
 * @author Gary Hayes
 */
public class ValuePropertySourceAdapter extends AbstractPropertySourceAdapter {

	/**
	 * Creates a new ValuePropertySourceAdapter.
	 * 
	 * @param property The Property
	 * @param commandStack The command stack to execute the command
	 */
	public ValuePropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
	}

	/**
	 * Gets the value of the property.
	 * 
	 * @return Object The value of the property
	 * @see IPropertySource#setPropertyValue(Object, Object)
	 */
	public Object getPropertyValue() {
	    String s = getProperty().getValue();
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
	    String s = "";
        if (newValue != null) {
            s = String.valueOf(newValue);
        }
	    
		UpdatePropertyCommand command = new UpdatePropertyCommand(getProperty(), s);
		getCommandStack().execute(command);
	}
}