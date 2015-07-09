package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Specialized PropertySourceAdapter for widget's ID properties. This generates
 * a unique identifier.
 * 
 * @author atr
 */
public class IdPropertySourceAdapter extends AbstractPropertySourceAdapter {

	/**
	 * Creates a new IdPropertySourceAdapter.
	 * 
	 * @param property
	 *            The Property
	 * @param commandStack
	 *            The command stack to execute the command
	 */
	public IdPropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);

		/**
		 * The ID property is set readonly only for MatrixCellItem so the user
		 * cannot change the value
		 * 
		 * @TODO refactor the PropertyType/Property model in order to remove
		 *       this code (hint: PropertyType must not be shared by WidgetType)
		 */
		if (WidgetTypeConstants.MATRIX_CELLITEM.equals(getWidget().getTypeName())) {
			property.setReadonly(true);
		}

	}

	/**
	 * Gets the value of the property.
	 * 
	 * @return Object The value of the property
	 * @see IPropertySource#setPropertyValue(Object, Object)
	 */
	public Object getPropertyValue() {
		Property prop = getProperty();
		String value = prop.getValue();
		if (value == null) {
			value = "";
		}
		String id = value;

		if (StringUtils.isBlank(id)) {
			id = UniqueIdGenerator.generateId(prop.getWidget());
			if (!value.equals(id)) {
				// save new id value.
				setPropertyValue(id);
			}
		}
		return id;
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
		String da = "";
		if (newValue != null) {
			da = String.valueOf(newValue);
		}

		String oldValue = getProperty().getValue();
		if (oldValue == null) {
			oldValue = "";
		}

		// Update the ID Property
		if (!oldValue.equals(da)) {
			UpdatePropertyCommand upc = new UpdatePropertyCommand(getProperty(), da);
			getCommandStack().execute(upc);
		}
	}

}