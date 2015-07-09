package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * This transformer transforms a label domain widget into xgui
 * @author atr
 * @since DS 1.40.0
 */
public class LabelDomainWidgetTransformer extends BaseWidgetTransformer {
	
	protected Widget findFieldDomain(List<Widget> widgets, Widget label, String domain) {
		Widget field = null;
		for (Widget widget : widgets) {
			if (widget != label && !widget.getTypeName().equals("LabelDomain")) {
				//System.out.println(widget.getTypeName());
				Property prop = widget.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
				if (prop != null) {
					if (domain.equals(prop.getValue())) {
						field = widget;
						break;
					}
				}
				field = findFieldDomain(widget.getContents(), label, domain);
				if (field != null) {
					break;
				}
			}
		}
		return field;
	}
	
	protected Widget findFieldDomain(Widget label) {
		Widget field = null;
		Property domain = label.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		if (domain != null && StringUtils.isNotEmpty(domain.getValue())) {
			Widget container = label.getRootWidget();
			if (container != null) {
				field = findFieldDomain(container.getContents(), label, domain.getValue());
			}
		}
		return field;
	}

	/*
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#getParentElement(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public Element getParentElement(WidgetTransformerContext context,Widget widget) {
		return null;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		Widget label = widget;
		Widget field = findFieldDomain(widget);
		
		Element labelElement = createElement(context, XGUI_NAMESPACE_URI, "label");
		TransformUtils.appendChild(context, labelElement);	
		Element oldParent = context.setParentElement(labelElement);
		
		Property forProp = label.findProperty(PropertyTypeConstants.FOR);
		String oldforValue = "";
		
		if (forProp != null) {
			oldforValue = forProp.getValue();
			if (field != null) {
				Property group = field.findProperty(PropertyTypeConstants.WIDGET_GROUP);
				if (group != null) {
					String groupValue = group.getValue();
					if (StringUtils.isNotEmpty(groupValue)) {
						String bpp = BeanPropertyUtils.findModuleBeanPropertyPrefix(context, field);
						if(!bpp.isEmpty()){
							bpp = bpp +".";
						}
						String forValue = groupValue + "." + bpp + forProp.getValue();
						if (WidgetTypeConstants.RADIO_BUTTON.equals(field.getTypeName())) {
							String radioId = field.getPropertyValue(PropertyTypeConstants.ID);
							if (StringUtils.isNotBlank(radioId)) {
								forValue += "." + radioId;
							}
						}
						forProp.setValue(forValue);
					} else {
						// do not generate "for" attribute if the widget group property has no value
						forProp.setValue("");
					}
				} else {
					// do not generate "for" attribute if no widget group is defined.
					forProp.setValue("");
				}
			} else {
				// do not generate "for" attribute if no field widget was found.
				forProp.setValue("");
			}
		}
		transformTranslations(context, label);
		transformProperties(context, label);
		
		if (forProp != null) {
			forProp.setValue(oldforValue);
		}
		
		context.setParentElement(oldParent);
	}

	/**
	 * @param type
	 */
	public LabelDomainWidgetTransformer(WidgetType type) {
		super(type);
	}

}
