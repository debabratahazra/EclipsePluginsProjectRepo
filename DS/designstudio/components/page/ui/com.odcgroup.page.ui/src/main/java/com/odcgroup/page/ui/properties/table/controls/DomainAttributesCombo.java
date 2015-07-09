package com.odcgroup.page.ui.properties.table.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class DomainAttributesCombo extends AbstractTypeCombo<MdfDatasetProperty> {
	/**  */
	private List<MdfDatasetProperty> attributes;

	/**
	 * @param parent
	 * @param attributes 
	 * @param filter 
	 * @param editMode 
	 */
	public DomainAttributesCombo(Composite parent, List<MdfDatasetProperty> attributes, List<MdfDatasetProperty> filter, boolean editMode) {
		super(parent, getFilteredAttributeList(attributes, filter).toArray(new String[0]));
		this.attributes = attributes;
	}
	
	/**
	 * @param attributes
	 * @param filters
	 * @return list
	 */
	private static List<String> getFilteredAttributeList(List<MdfDatasetProperty> attributes, List<MdfDatasetProperty> filters) {
		List<String> filteredList = new ArrayList<String>();
		for (MdfDatasetProperty attribute : attributes) {
			if (!filters.contains(attribute)) {
				filteredList.add(attribute.getName());
			}
		}
		Collections.sort(filteredList, String.CASE_INSENSITIVE_ORDER);
		return filteredList;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ITypeCombo#getSelectedValue()
	 */
	@Override
	public MdfDatasetProperty getSelectedValue() {
		String item = getSelectedItem();
		for(MdfDatasetProperty attr : attributes) {
			if (attr.getName().equals(item)) {
				return attr;
			}
		}
		return null;
	}

}
