package com.odcgroup.page.ui.properties.table.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.filter.DataTypeFilter;

/**
 *
 * @author pkk
 *
 */
public class DataTypeCombo extends AbstractTypeCombo<DataValue> {
	
	/**		 */
	private DataType type = null;
	/**      */
	private DataTypeFilter filter = null;

	/**
	 * @param parent
	 * @param type
	 * @param editMode 
	 */
	public DataTypeCombo(Composite parent, DataType type, boolean editMode) {
		this(parent, type, null, editMode);
	}
	
	/**
	 * @param parent
	 * @param type
	 * @param filter
	 * @param editMode
	 */
	public DataTypeCombo(Composite parent, DataType type, DataTypeFilter filter, boolean editMode) {
		super(parent);
		this.type = type;
		setFilter(filter);
		setItems(getComboItems(type));
	}
	
	/**
	 * @param parent
	 * @param editMode
	 */
	public DataTypeCombo(Composite parent, boolean editMode) {
		super(parent);
	}
	
	/**
	 * @param type
	 */
	public void setType(DataType type) {
		this.type = type;
		setItems(getComboItems(type));
	}
	
	/**
	 * 
	 */
	public void resetItems() {
		setItems(getComboItems(type));
	}
	
	/**
	 * @param value
	 */
	public void select(String value) {
		if (type == null) {
			return;
		}
		DataValue dVal = type.findDataValue(value);
		if (filter != null) {
			String[] items = getCombo().getItems();
			int index = 0;
			for (int i = 0; i < items.length; i++) {
				String string = items[i];
				if (dVal.getDisplayName().equals(string)) {
					index = i;
					break;
				}
			}
			getCombo().select(index);
		} else {
			if (dVal != null){
				getCombo().select(dVal.getOrdinal());
			}
		}
	}
	
	/**
	 * @param type 
	 * @return String[]
	 */
	private String[] getComboItems(DataType type) {
		List<DataValue> values = null;
		if (getFilter() == null) {
			values = type.getValues();
		} else {
			values = getFilter().filter(type);
		}
		List<String> strValues = new ArrayList<String>();
		for (DataValue dataValue : values) {
			String value = dataValue.getDisplayName();
			if (filter != null) {
				strValues.add((value != null) ? value : "");
			} else {
				strValues.add(dataValue.getOrdinal(), (value != null) ? value : "");
			}
		}
		return strValues.toArray(new String[0]);
	}
	
	/**
	 * @return DataValue
	 */
	public DataValue getSelectedValue() {
		int i = getCombo().getSelectionIndex();
		if (i == -1) return null;
		String val = getCombo().getItems()[i];
		return getDataValue(val);
	}
	
	/**
	 * @param displayName
	 * @return DataValue
	 */
	private DataValue getDataValue(String displayName) {
		for (DataValue dVal : type.getValues()) {
			if (displayName.equals(dVal.getDisplayName())) {
				return dVal;
			}
		}
		return null;
	}

	/**
	 * @return DataTypeFilter
	 */
	public DataTypeFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 */
	public void setFilter(DataTypeFilter filter) {
		this.filter = filter;
	}
	
	
	
}
