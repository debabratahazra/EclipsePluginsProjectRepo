package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.grid.UpdateGridColumnCountCommand;

/**
 * Specialized PropertySourceAdapter for widget's grid-column-count property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridColumnCountPropertySourceAdapter extends AbstractPropertySourceAdapter {
	
    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public GridColumnCountPropertySourceAdapter(Property property, CommandStack commandStack) {
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
    	int columnCount = Integer.parseInt((String)newValue);
		UpdateGridColumnCountCommand upc = new UpdateGridColumnCountCommand(getProperty(), columnCount);
    	getCommandStack().execute(upc);    
    }

	/* 
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
		return getProperty().getValue();
	}
    
}