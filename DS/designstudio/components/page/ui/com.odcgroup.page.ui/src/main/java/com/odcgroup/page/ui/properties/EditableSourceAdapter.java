package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Editable source adapter used to erase the "Editable is based on" property
 * if the "Editable" property is not "Conditional"
 * @author scn
 */
public class EditableSourceAdapter extends DataValuePropertySourceAdapter {
	
	/**
	 * Creates a new EditableSourceAdapter.
	 * 
	 * @param property The Property
	 * @param commandStack The command stack to execute the command
	 */
	public EditableSourceAdapter(Property property, CommandStack commandStack) {
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
	 * Erase the editable is based on value if the editable value is not
	 * conditional.
	 * @param value
	 */
	private void updateDependentProperties(String value) {
		if (!("conditional").equalsIgnoreCase(value)) {
			Property editableIsBasedOn = getProperty().getWidget().findProperty("editableIsBasedOn");
			System.out.println("TRUE OR FALSE SLEC + " + editableIsBasedOn);
			if (editableIsBasedOn != null) {
				editableIsBasedOn.setValue("");
			}
		}
	}
	
}
