package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transforms the horizontalAlignemnt property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class HorizontalAlignmentPropertyTransformer extends BasePropertyTransformer {
	
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The PropertyType
	 */
	public HorizontalAlignmentPropertyTransformer(PropertyType type) {
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
	        SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
	        if (expander != null) {
	        	value = expander.substitute(value, property.getWidget());
	        }	
	        if (! value.equals(property.getType().getDefaultValue())) {
	        	TransformUtils.convertToAttribute(context, context.getParentElement(), "halign", value);
	        }
		}
	}
}
