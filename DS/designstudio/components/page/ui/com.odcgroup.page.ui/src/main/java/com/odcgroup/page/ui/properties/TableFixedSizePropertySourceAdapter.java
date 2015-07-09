package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * The adapter for the value of the property table-fixed-size.
 * 
 * @author atr
 * @since Ds 1.40.0
 */
public class TableFixedSizePropertySourceAdapter extends DataValuePropertySourceAdapter {
	
	/**
	 * @param value
	 */
	private void updateDependentProperties(String value) {
        boolean hasFixedSize = "true".equalsIgnoreCase(value);
    	ITable table = TableHelper.getTable(getProperty().getWidget());
    	Property p = table.getWidth();
    	if (p != null) {
            p.setReadonly(!hasFixedSize);  
            if (p.isReadonly()) {
            	p.setValue("");
            }
    	}		
    	p = table.getHeight();
    	if (p != null) {
            p.setReadonly(!hasFixedSize);  
            if (p.isReadonly()) {
            	p.setValue("");
            }
    	}			
	}

	/**
	 * Creates a new ValuePropertySourceAdapter.
	 * 
	 * @param property The Property
	 * @param commandStack The command stack to execute the command
	 */
	public TableFixedSizePropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
        updateDependentProperties(property.getValue());
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
        DataValue dv = (DataValue) newValue;
        String s = dv.getValue();
        CompoundCommand cc = new CompoundCommand();
        cc.add(new UpdatePropertyCommand(getProperty(), s));
        updateDependentProperties(s);
        getCommandStack().execute(cc);
	}
}