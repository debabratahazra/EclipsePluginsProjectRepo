package com.odcgroup.page.transformmodel;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transforms the collapsed property.
 * 
 * @author atr
 */
public class CollapsiblePropertyTransformer extends BasePropertyTransformer {
	
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public CollapsiblePropertyTransformer(PropertyType type) {
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
		String name = property.getTypeName().toLowerCase();
		if (!property.getBooleanValue()) {
			String value = ""+property.getBooleanValue();
			TransformUtils.convertToAttribute(context, context.getParentElement(), name, value);
		}
	}
}
