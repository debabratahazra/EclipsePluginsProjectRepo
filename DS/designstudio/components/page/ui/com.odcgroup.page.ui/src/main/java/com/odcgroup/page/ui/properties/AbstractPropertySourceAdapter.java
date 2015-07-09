package com.odcgroup.page.ui.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Base class for PropertySourceAdapters.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractPropertySourceAdapter implements PropertySourceAdapter {

    /** The Property. */
    private Property property;

    /** The command stack */
    private CommandStack commandStack;

    /**
     * Creates a new AbstractPropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public AbstractPropertySourceAdapter(Property property, CommandStack commandStack) {
        Assert.isNotNull(property);
        Assert.isNotNull(commandStack);
        this.property = property;
        this.commandStack = commandStack;
    }
    
    /**
     * @return the widget that owns the property
     */
    protected final Widget getWidget() {
    	return getProperty().getWidget();
    }
    
    /**
     * Gets the Property.
     * 
     * @return String The Property
     */
    protected final Property getProperty() {
        return property;
    } 
    
    /**
     * Gets the CommandStack.
     * 
     * @return CommandStack
     */
    protected final CommandStack getCommandStack() {
        return commandStack;
    }
    
    /**
     * Returns true if the value of the Property is set.
     * 
     * @return boolean True if the value of the Property is set
     * @see IPropertySource#setPropertyValue(Object, Object)
     */
    public boolean isPropertySet() {
        return ! getProperty().isDefaultValue();
    }

    /**
     * Resets the value of the property.
     * 
     * @see IPropertySource#resetPropertyValue(Object)
     */
    public void resetPropertyValue() {
    	Property p = getProperty();
    	String oldValue = p.getValue();
   		String newValue = p.getType().getDefaultValue();
    	// Update the Property
    	if (! oldValue.equals(newValue)) {
    		UpdatePropertyCommand upc = new UpdatePropertyCommand(getProperty(), newValue);
        	getCommandStack().execute(upc);
    	}
    }    
}
