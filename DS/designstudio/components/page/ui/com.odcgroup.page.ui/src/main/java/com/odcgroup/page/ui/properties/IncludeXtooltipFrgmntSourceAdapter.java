package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
/**
 * IncludeXtooltip FramentSource adapter class
 * @author snn
 *
 */
public class IncludeXtooltipFrgmntSourceAdapter extends
		IncludePropertySourceAdapter {
    /**
     * constructor .set the property  to read only.
     * @param property
     * @param commandStack
     */
	public IncludeXtooltipFrgmntSourceAdapter(Property property,
			CommandStack commandStack) {
		super(property, commandStack);
		Widget widget=property.getWidget();
		
	}

}
