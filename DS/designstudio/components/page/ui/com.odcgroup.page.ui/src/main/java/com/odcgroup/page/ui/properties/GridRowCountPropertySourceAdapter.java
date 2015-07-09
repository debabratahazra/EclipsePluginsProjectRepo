package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.grid.UpdateGridRowCountCommand;

/**
 * Specialized PropertySourceAdapter for widget's grid-row-count property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridRowCountPropertySourceAdapter extends AbstractPropertySourceAdapter {
	
    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public GridRowCountPropertySourceAdapter(Property property, CommandStack commandStack) {
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
    	int rowCount = Integer.parseInt((String)newValue);
    	UpdateGridRowCountCommand upc = new UpdateGridRowCountCommand(getProperty(), rowCount);
    	getCommandStack().execute(upc);    
    }

	/* 
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
		return getProperty().getValue();
	}
    
}