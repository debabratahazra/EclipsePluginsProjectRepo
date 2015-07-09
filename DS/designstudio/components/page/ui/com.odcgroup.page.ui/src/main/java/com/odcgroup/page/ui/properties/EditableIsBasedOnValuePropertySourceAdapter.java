package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.properties.editable.EditableIsBasedOnCondition;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * The adapter for the value of a Property.
 * @author scn
 */
public class EditableIsBasedOnValuePropertySourceAdapter extends AbstractPropertySourceAdapter {

	/**
	 * Creates a new ValuePropertySourceAdapter.
	 * 
	 * @param property The Property
	 * @param commandStack The command stack to execute the command
	 */
	public EditableIsBasedOnValuePropertySourceAdapter(Property property, CommandStack commandStack) {
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
	    if (s == null) {
	    	return "";
	    } else {
	    	return s;
	    }
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
		EditableIsBasedOnCondition condition = (EditableIsBasedOnCondition)newValue;
		UpdatePropertyCommand command = new UpdatePropertyCommand(getProperty(), condition.toString());
		getCommandStack().execute(command);
		updateDepdendentProperties(condition);
	}

	/**
	 * @param newValue
	 */
	private void updateDepdendentProperties(EditableIsBasedOnCondition newValue) {
		Property simplifiedProperty = getProperty().getWidget().findProperty("editableIsBasedOn-simplified");
		Property advancedProperty = getProperty().getWidget().findProperty("editableIsBasedOn-advanced");
		simplifiedProperty.setValue(newValue.getSimplifiedCondition()!=null?newValue.getSimplifiedCondition().toString():"");
		advancedProperty.setValue(newValue.getAdvancedCondition());
	}

}