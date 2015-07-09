package com.odcgroup.page.transformmodel;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transforms the collapsed property.
 * 
 * @author atr
 */
public class CollapsedPropertyTransformer extends BasePropertyTransformer {
	
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public CollapsedPropertyTransformer(PropertyType type) {
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
        Widget widget = property.getWidget();
        Property collapsible = widget.findProperty("collapsible");
        if (collapsible != null) {
    		String name = property.getTypeName().toLowerCase();
    		if (!collapsible.getBooleanValue() || collapsible.getBooleanValue()) {
    			String value = ""+property.getBooleanValue();
    			TransformUtils.convertToAttribute(context, context.getParentElement(), name, value);
    		} 
    		/*
    		 else {
    			TransformationUtils.convertToAttribute(context, context.getParentElement(), name, "false");
    		}*/
        }

	}
}
