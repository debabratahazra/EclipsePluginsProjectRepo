package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.grid.UpdateGridColumnCssClassCommand;

/**
 * Specialized PropertySourceAdapter for widget's grid-column-css-class property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridColumnCssClassPropertySourceAdapter extends AbstractPropertySourceAdapter {
	
    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public GridColumnCssClassPropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
    }
    
    /**
     * Sets the value of the property.
     * 
     * @param newValue The value of the property
     * 
     * @see IPropertySource#setPropertyValue(Object, Object)
     */
    public void setPropertyValue(Object newValue) {
		UpdateGridColumnCssClassCommand upc = new UpdateGridColumnCssClassCommand(getProperty(), (String)newValue);
    	getCommandStack().execute(upc);    
    }

	/* 
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
		return getProperty().getValue();
	}
    
}