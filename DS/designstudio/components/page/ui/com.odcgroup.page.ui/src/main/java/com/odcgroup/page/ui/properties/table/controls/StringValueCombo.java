package com.odcgroup.page.ui.properties.table.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author pkk
 *
 */
public class StringValueCombo extends AbstractTypeCombo<String> {

	/**
	 * @param parent
	 * @param values 
	 * @param filterValues 
	 * @param editMode 
	 */
	public StringValueCombo(Composite parent, Collection<String> values, Collection<String> filterValues, boolean editMode) {
		this(parent, values, filterValues, editMode, false);
	}
	
	/**
	 * @param parent
	 * @param values
	 * @param editMode
	 */
	public StringValueCombo(Composite parent, Collection<String> values, boolean editMode) {
		this(parent, values, new ArrayList<String>(), editMode, false);
	}
	
	/**
	 * @param parent
	 * @param values
	 * @param filterValues
	 * @param editMode
	 * @param sort
	 */
	public StringValueCombo(Composite parent, Collection<String> values, Collection<String> filterValues, boolean editMode, boolean sort) {
		super(parent, getFilteredList(values, filterValues, sort));
	}
	
	/**
	 * @param values
	 * @param filterValues
	 * @param sort 
	 * @return list
	 */
	private static String[] getFilteredList(Collection<String> values, Collection<String> filterValues, boolean sort) {
		List<String> filteredList = new ArrayList<String>();
		for (String value : values) {
//			if (!filterValues.contains(value)) {
				filteredList.add(value);
//			}
			}
		String[] retVal = filteredList.toArray(new String[0]);
		if (sort) {
			Arrays.sort(retVal, String.CASE_INSENSITIVE_ORDER);
		}
		return retVal;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ITypeCombo#getSelectedValue()
	 */
	@Override
	public String getSelectedValue() {
		return getSelectedItem();
	}

}
