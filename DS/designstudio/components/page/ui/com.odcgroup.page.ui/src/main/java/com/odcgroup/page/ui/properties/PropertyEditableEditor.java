package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;

/**
 * The editor shows the Conditional value only for certain widgets, like
 * <ul>
 * <li>Icon</li>
 * <li>Label</li>
 * <li>LabelDomain</li>
 * <li>TableColumnItem</li>
 * <li>CheckBox</li>
 * <li>RadioButton</li>
 * <li>Button</li>
 * </ul>
 * 
 * @author SCN
 */
public class PropertyEditableEditor extends PropertyComboBoxEditor {

	/**
	 * Contains all values but the Conditional one  
	 */
	private static List<DataValue> filteredValues;
	
	/**
	 * @param property
	 */
	public PropertyEditableEditor(Property property) {
		super(property);
	}

	/**
	 * @see com.odcgroup.page.ui.properties.PropertyComboBoxEditor#filter(java.util.List)
	 */
	@Override
	protected List<DataValue> filter(List<DataValue> values) {
		if (getProperty().getWidget().findProperty("editableIsBasedOn") != null) {
			return values;
		} else {
			return filteredValue(values);
		}
	}
	
	/**
	 * Remove from the allValue list the Conditional element
	 * @param allValues
	 * @return all values without Conditional
	 */
	private List<DataValue> filteredValue(List<DataValue> allValues) {
		if (filteredValues == null) {
			filteredValues = new ArrayList<DataValue>();
			for (DataValue value : allValues) {
				if (!value.getDisplayName().equals("Conditional")) {
					filteredValues.add(value);
				}
			}
		}
		return filteredValues;
	}

}
