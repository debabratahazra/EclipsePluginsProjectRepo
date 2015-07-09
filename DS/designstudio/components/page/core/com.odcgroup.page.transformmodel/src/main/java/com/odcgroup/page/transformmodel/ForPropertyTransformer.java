package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transforms the For property.
 * 
 * @author atr
 */
public class ForPropertyTransformer extends BasePropertyTransformer {
	
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public ForPropertyTransformer(PropertyType type) {
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
		if (StringUtils.isNotEmpty(value)) {
        	String name = property.getTypeName().toLowerCase();
        	TransformUtils.convertToAttribute(context, context.getParentElement(), name, value);
		}
	}
}
