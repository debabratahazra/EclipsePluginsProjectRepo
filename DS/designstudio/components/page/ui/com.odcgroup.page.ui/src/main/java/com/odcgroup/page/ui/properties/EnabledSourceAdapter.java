package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.properties.enabled.EnabledConstants;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Enabled source adapter used to erase the "Enabled is based on" property
 * if the "Enabled" property is not "Conditional"
 * @author yan
 */
public class EnabledSourceAdapter extends DataValuePropertySourceAdapter {
	
	/**
	 * Creates a new EnabledSourceAdapter.
	 * 
	 * @param property The Property
	 * @param commandStack The command stack to execute the command
	 */
	public EnabledSourceAdapter(Property property, CommandStack commandStack) {
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
	
	/**
	 * Erase the enabled is based on value if the enabled value is not
	 * conditional.
	 * @param value
	 */
	private void updateDependentProperties(String value) {
		if (!EnabledConstants.ENABLED_CONDITIONAL_VALUE.equalsIgnoreCase(value)) {
			Property enabledIsBasedOn = getProperty().getWidget().findProperty(EnabledConstants.ENABLED_IS_BASE_ON_PROPERTY_NAME);
			if (enabledIsBasedOn != null) {
				enabledIsBasedOn.setValue("");
			}
		}
	}
	
}
