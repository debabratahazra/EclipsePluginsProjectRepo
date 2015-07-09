package com.odcgroup.page.transformmodel.impl;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.PropertyTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * The DefaultWidgetTransformer transforms the Widget to an Xml Attribute having
 * the same name but in lowercase.
 * 
 * @author Gary Hayes
 */
public class DefaultPropertyTransformerImpl implements PropertyTransformer {
	
	/**
	 * Returns true if the PropertyTransformer is designed to transform the specified PropertyType.
	 * 
	 * @param property The Property to test
	 * @return boolean True if the PropertyTransformer is designed to transform the specified PropertyType
	 */
	public boolean isTransformer(Property property) {
		// This transformer can transform any Property
		return true;
	}
	

	/**
	 * Transforms the Property.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param property The Property to transform
	 */
	public void transform(WidgetTransformerContext context, Property property) {
		if (TransformUtils.isDefaultOrEmpty(property)) {
			// Don't append the value if it has not changed otherwise the XSP pages will be huge
			return;
		}
		
		String name = property.getTypeName().toLowerCase();
		String value = property.getValue();
		
		TransformUtils.convertToAttribute(context, context.getParentElement(), name, value);
	}
}