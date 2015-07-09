package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;


/**
 * The PropertySourceAdapter for DataTypes which have a fixed number of DataValues.
 * 
 * @author Gary Hayes
 */
public class DataValuePropertySourceAdapter extends AbstractPropertySourceAdapter {

    /**
     * Creates a new DataValuePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public DataValuePropertySourceAdapter(Property property, CommandStack commandStack) {
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
        DataType dt = getProperty().getType().getDataType();
        DataValue dv = dt.findDataValue(s);
        return dv;
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
        if (dv != null) {
	        String s = dv.getValue();
	        UpdatePropertyCommand command = new UpdatePropertyCommand(getProperty(), s);
	        getCommandStack().execute(command);
        }
    }
}
