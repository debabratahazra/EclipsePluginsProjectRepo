package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * @author pkk
 *
 */
public class MatrixAggregationComboEditor extends PropertyComboBoxEditor {

	/**
	 * @param property
	 */
	public MatrixAggregationComboEditor(Property property) {
		super(property);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.PropertyComboBoxEditor#filter(java.util.List)
	 */
	protected List<DataValue> filter(List<DataValue> values) {
		Widget widget = getProperty().getWidget();
		if (widget.getTypeName().equals("MatrixExtraColumnItem")) {
			List<DataValue> dups = new ArrayList<DataValue>();
			dups.addAll(values);
			for (DataValue dataValue : values) {
				if(dataValue.getValue().equals("weighted-mean")) {
					dups.remove(dataValue);
				}
			}
			return dups;
		}
		return values;
	}

}
