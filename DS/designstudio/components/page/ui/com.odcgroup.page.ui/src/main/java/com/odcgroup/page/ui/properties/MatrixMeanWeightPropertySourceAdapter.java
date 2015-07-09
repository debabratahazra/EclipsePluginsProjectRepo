package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * @author pkk
 * 
 */
public class MatrixMeanWeightPropertySourceAdapter extends
		AbstractPropertySourceAdapter {

	/**
	 * @param property
	 * @param commandStack
	 */
	public MatrixMeanWeightPropertySourceAdapter(Property property,
			CommandStack commandStack) {
		super(property, commandStack);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
		String s = getProperty().getValue();
		if (s == null) {
			return "";
		} else {
			return s;
		}
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#setPropertyValue(java.lang.Object)
	 */
	public void setPropertyValue(Object newValue) {
		UpdatePropertyCommand command = new UpdatePropertyCommand(
				getProperty(), (String) newValue);
		getCommandStack().execute(command);
	}

}
