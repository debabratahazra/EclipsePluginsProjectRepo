package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NO_WRAP;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;

/**
 * Transformer for the nowrap property.
 * 
 * @author Gary Hayes
 */
public class NoWrapPropertyTransformer extends BasePropertyTransformer {

	/**
	 * Constructs a new BeanProperty transformer.
	 * 
	 * @param type The PropertyType
	 */
	public NoWrapPropertyTransformer(PropertyType type) {
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
		// The Property is called 'nowrap', however the Display Name is 'Wrapped'
		// This is the inverse. Therefore we need to inverse the value
		if (property.isDefaultValue()) {
			return;
		}
		
		String value = property.getValue();
		String s = "true";
		if ("true".equals(value)) {
			s = "false";
		}
		
		addAttribute(context, XGUI_NO_WRAP, s);
	}				
}
