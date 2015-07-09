package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * This specific PropertySourceAdapter allows to link the value of a property 
 * to the value of another property of the same widget, or from a parent widget.
 * The name of the other property is the [default value] of the property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class DomainEntityLinkedPropertySourceAdapter extends AbstractPropertySourceAdapter {
	
    /**
     * Creates a new DomainEntityLinkedPropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public DomainEntityLinkedPropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
    }

	/**
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
    	Property prop = getProperty();
    	String linkedTo = prop.getType().getDefaultValue();
    	String value = prop.getValue();
        if (StringUtils.isEmpty(value) || linkedTo.equals(value)) {
        	value = getProperty().getWidget().findPropertyValueInParent(linkedTo);
        	int pos = value.indexOf(":");
        	if (pos != -1) {
        		value = value.substring(pos+1);
        	}
       		setPropertyValue(value);
    	}
		return getProperty().getValue();	}

	/**
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#setPropertyValue(java.lang.Object)
	 */
	public void setPropertyValue(Object newValue) {
		
		String newVal = "";
        if (newValue != null) {
        	newVal = (String)newValue;
        }
		
    	String oldValue = getProperty().getValue();
    	if (oldValue == null) {
    		oldValue = "";
    	}
    	
    	if (! oldValue.equals(newVal)) {
    		UpdatePropertyCommand upc = new UpdatePropertyCommand(getProperty(), (String)newValue);
        	getCommandStack().execute(upc);    
    	}
	}
   
}