package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_WIDTH;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;

/**
 * Transformer for the width property.
 * 
 * @author atr
 */
public class WidthPropertyTransformer extends BasePropertyTransformer {

	/**
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	@Override
	public void transform(WidgetTransformerContext context, Property property) {
		String value = property.getValue();
		if (property.isDefaultValue() || "0".equals(value)) {
			// do not generate 
			return;
		}
		addAttribute(context, XGUI_WIDTH, value);
	}				

	/**
	 * Constructs a new BeanProperty transformer.
	 * 
	 * @param type The PropertyType
	 */
	public WidthPropertyTransformer(PropertyType type) {
		super(type);
	}
	
}
