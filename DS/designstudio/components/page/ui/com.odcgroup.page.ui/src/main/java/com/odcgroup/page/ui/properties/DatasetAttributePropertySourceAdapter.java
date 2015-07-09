package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Specialized PropertySourceAdapter for Dataset Attributes for editable widget within a Table/Tree
 */
public class DatasetAttributePropertySourceAdapter extends AbstractPropertySourceAdapter  {
	
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
    	
        String domainAttributeName = "";
        if (newValue != null) {
        	domainAttributeName = String.valueOf(newValue);
        	domainAttributeName = domainAttributeName.trim();
        }
		
        CompoundCommand compoundCommand = new CompoundCommand("Change Dataset Attribute");
        
        // update property with the new name
        compoundCommand.add(new UpdatePropertyCommand(getProperty(), domainAttributeName));

        // update dependent property

    	// update all properties
    	getCommandStack().execute(compoundCommand);
    }

	@Override
    public void resetPropertyValue() {
    	Property attribute = getProperty();
    	if (!attribute.isReadonly()) {

			CompoundCommand cc = new CompoundCommand("Reset Dataset Attribute");
			
	    	// reset the attribute
	    	String oldValue = attribute.getValue();
	   		String newValue = attribute.getType().getDefaultValue();
	    	if (! oldValue.equals(newValue)) {
	    		cc.add(new UpdatePropertyCommand(attribute, newValue));
	    	}

	    	// reset dependent properties
	    	Property maxChars = getWidget().findProperty(PropertyTypeConstants.CHARS);
	    	if (maxChars != null) {
		    	oldValue = maxChars.getValue();
		   		newValue = maxChars.getType().getDefaultValue();
		    	if (! oldValue.equals(newValue)) {
		    		cc.add(new UpdatePropertyCommand(maxChars, newValue));
		    	}
	    	}
	    	
	        getCommandStack().execute(cc);

    	}
    }

    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public DatasetAttributePropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
    }
    
}