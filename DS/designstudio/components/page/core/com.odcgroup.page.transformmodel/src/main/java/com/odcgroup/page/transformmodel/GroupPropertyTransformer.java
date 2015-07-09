package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * @author phanikumark
 *
 */
public class GroupPropertyTransformer  extends IdPropertyTransformer {

	/**
	 * @param type
	 */
	public GroupPropertyTransformer(PropertyType type) {
		super(type);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.transformmodel.IdPropertyTransformer#transform
	 * (com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	@Override
	public void transform(WidgetTransformerContext context, Property property) {
		String value = property.getValue();
		if (StringUtils.isBlank(value)) {
			return;
		}
		String name = property.getTypeName().toLowerCase();
		Widget widget = property.getWidget();
		if (WidgetTypeConstants.RADIO_BUTTON.equals(widget.getTypeName())) {
			String domAttr = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
			StringBuilder sb = new StringBuilder();
			String association = getBeanProperty(context, widget);
			if (!StringUtils.isEmpty(domAttr) && !StringUtils.isEmpty(association)) {
				sb.append(association+"."+domAttr);
			} else {
				sb.append(value);					
			}
			value = sb.toString();
		}

		TransformUtils.convertToAttribute(context, context.getParentElement(), name, value);
	}
	
	

}
