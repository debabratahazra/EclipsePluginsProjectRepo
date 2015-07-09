package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class PropertyHAlignComboBoxEditor extends PropertyComboBoxEditor {
	
	/**
	 * @see com.odcgroup.page.ui.properties.PropertyComboBoxEditor#filter(java.util.List)
	 */
	protected List<DataValue> filter(List<DataValue> values) {
		List<DataValue> filteredValues = values;
		Widget w = getProperty().getWidget();
		if (!w.getTypeName().equals(WidgetTypeConstants.LABEL) && !w.getTypeName().equals(WidgetTypeConstants.ICON)) {
			Property p = w.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
			if (p == null) {
				filteredValues = new ArrayList<DataValue>();
				for (int kx = 0; kx < values.size(); kx ++) {
					DataValue value = values.get(kx);
					if (value.getOrdinal() != 4 /* corporate */) {
						filteredValues.add(value);
					}
				}
			}
		}
		return filteredValues;
	}
	
	/**
	 * @param property
	 */
	public PropertyHAlignComboBoxEditor(Property property) {
		super(property);
	}

}
