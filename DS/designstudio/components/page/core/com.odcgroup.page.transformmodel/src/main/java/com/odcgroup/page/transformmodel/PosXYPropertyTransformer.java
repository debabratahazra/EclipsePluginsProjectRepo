package com.odcgroup.page.transformmodel;

import org.w3c.dom.Attr;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;

/**
 * The posX and posY values should only be transformed if the BoxType of the parent Widget is an absolute box. In this
 * case they should ALWAYS be exported even if they still have their default value of 0.
 * 
 * @author Gary Hayes
 */
public class PosXYPropertyTransformer extends BasePropertyTransformer {

	/**
	 * Constructs a new PosXYPropertyTransformer.
	 * 
	 * @param type The PropertyType
	 */
	public PosXYPropertyTransformer(PropertyType type) {
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
		// If the parent is NOT a box ignore these values
		Property p = property.getWidget().getParent().findProperty(PropertyTypeConstants.BOX_TYPE);
		if (p == null) {
			return;
		}

		// Only export these values for Absolute Boxes.
		if (!TransformerConstants.ABSOLUTE_BOX_TYPE_VALUE.equals(p.getValue())) {
			return;
		}

		// Always export these values for Absolute Boxes even if they still have their default values
		Attr a = context.getDocument().createAttribute(property.getTypeName());
		a.setValue(property.getValue());
		context.getParentElement().setAttributeNode(a);
	}
}
