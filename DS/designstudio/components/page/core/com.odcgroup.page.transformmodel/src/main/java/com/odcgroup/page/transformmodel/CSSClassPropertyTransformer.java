package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;

/**
 * Transforms the CSS class property.
 * 
 * @author ATR
 */
public class CSSClassPropertyTransformer extends BasePropertyTransformer {
	
	private static final String CLASS_ATTRIBUTE = "class";

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public CSSClassPropertyTransformer(PropertyType type) {
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
		if (value == null) value = "";
		Element element = context.getParentElement();
		String classValue = property.getValue() + " " + element.getAttribute(CLASS_ATTRIBUTE);
		// Do not generate the attribute if no value is defined
		if (StringUtils.isNotBlank(classValue)) {
			element.setAttribute(CLASS_ATTRIBUTE, classValue.trim());
		}
	}
}
