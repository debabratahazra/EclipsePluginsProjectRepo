package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Specialized PropertySourceAdapter for the property "column-checkbox-header".
 * 
 * @author atr
 */
public class ColumnCheckboxPropertySourceAdapter extends DataValuePropertySourceAdapter {
	
	
    /**
     * Creates a new ColumnCheckboxPropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public ColumnCheckboxPropertySourceAdapter(Property property, CommandStack commandStack) {
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
        DataValue dv = (DataValue) newValue;
        String s = dv.getValue();
        CompoundCommand cc = new CompoundCommand();
        cc.add(new UpdatePropertyCommand(getProperty(), s));
        getCommandStack().execute(cc);
    }
    
}