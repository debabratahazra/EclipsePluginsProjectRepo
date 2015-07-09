package com.odcgroup.page.ui.properties.tabs;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter;

/**
 * @author atr
 */
public class TabFilterPropertySourceAdapter extends AbstractPropertySourceAdapter {
	
	/**
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	@Override
	public Object getPropertyValue() {
		return getProperty().getValue();
	}

	/**
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#setPropertyValue(java.lang.Object)
	 */
	@Override
	public void setPropertyValue(Object newValue) {
		String oldValue = getProperty().getValue();
		if (!StringUtils.equals(oldValue, (String)newValue)) {
	        UpdatePropertyCommand updCommand = new UpdatePropertyCommand(getProperty(), (String)newValue);
	        getCommandStack().execute(updCommand);
		}
	}

	/**
	 * @param property
	 * @param commandStack
	 */
	public TabFilterPropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
	}

}
