package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transforms the widget-group property.
 * 
 * @author ATR
 */
public class WidgetGroupPropertyTransformer extends BasePropertyTransformer {

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public WidgetGroupPropertyTransformer(PropertyType type) {
		super(type);
	}
	
	/**
	 * Transforms the Property.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param property
	 *            The Property to transform
	 */
	public void transform(WidgetTransformerContext context, Property property) {
		String value = property.getValue();
		// Do not generate the attribute if no value is defined
		if (StringUtils.isNotBlank(value)) {
			TransformUtils.convertToAttribute(context, context.getParentElement(), "widget-group", value);
		}
	}
}
