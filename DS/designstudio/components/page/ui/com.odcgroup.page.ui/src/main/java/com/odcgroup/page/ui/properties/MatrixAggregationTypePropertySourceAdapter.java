package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * @author pkk
 *
 */
public class MatrixAggregationTypePropertySourceAdapter extends
		AbstractPropertySourceAdapter {

	/**
	 * @param property
	 * @param commandStack
	 */
	public MatrixAggregationTypePropertySourceAdapter(Property property,
			CommandStack commandStack) {
		super(property, commandStack);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
        String s = getProperty().getValue();
        DataType dt = getProperty().getType().getDataType();
        DataValue dv = dt.findDataValue(s);
        return dv;
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#setPropertyValue(java.lang.Object)
	 */
	public void setPropertyValue(Object newValue) {
		DataValue dv = (DataValue) newValue;
        if (dv != null) {
	        String s = dv.getValue();
	        UpdatePropertyCommand command = new UpdatePropertyCommand(getProperty(), s);
	        getCommandStack().execute(command);
	        updateDepdendentProperties(s);
        }	
	}
	
	/**
	 * @param newvalue
	 */
	private void updateDepdendentProperties(String newvalue) {
		if (!newvalue.equals("weighted-mean")) {
			Property property = getProperty().getWidget().findProperty("matrixMeanWeight");
			if (property != null) {
				property.setValue("");
			}
		}
	}

}
