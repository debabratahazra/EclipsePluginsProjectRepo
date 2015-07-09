package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdateSearchTypePropertyCommand;


/**
 * @author pkk
 *
 */
public class SearchTypePropertySourceAdapter extends DataValuePropertySourceAdapter {

	/**
	 * @param property
	 * @param commandStack
	 */
	public SearchTypePropertySourceAdapter(Property property,
			CommandStack commandStack) {
		super(property, commandStack);
	}
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.DataValuePropertySourceAdapter#setPropertyValue(java.lang.Object)
	 */
	public void setPropertyValue(Object newValue) {
		DataValue dv = (DataValue) newValue;
        if (dv != null) {
	        String s = dv.getValue();
	        UpdateSearchTypePropertyCommand command = new UpdateSearchTypePropertyCommand(getProperty(), s);
	        getCommandStack().execute(command);
        }
	}

}
