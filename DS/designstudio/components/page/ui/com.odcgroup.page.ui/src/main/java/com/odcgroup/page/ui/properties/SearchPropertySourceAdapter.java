/**
 * 
 */
package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Property;

/**
 * @author arajeshwari
 *
 */
public class SearchPropertySourceAdapter extends DataValuePropertySourceAdapter {

	public SearchPropertySourceAdapter(Property property,
			CommandStack commandStack) {
		super(property, commandStack);
	}

	@Override
	public Object getPropertyValue() {
		Property property = getProperty();
		property.setReadonly(true);
		return super.getPropertyValue();
	}
}
