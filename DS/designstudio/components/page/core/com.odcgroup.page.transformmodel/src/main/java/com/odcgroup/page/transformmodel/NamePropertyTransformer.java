package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_TEXT_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;

/**
 * Transforms the name property.
 * 
 * @author atr
 */
public class NamePropertyTransformer extends BasePropertyTransformer {

	/**
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	@Override
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {
		
		Namespace ns = context.getTransformModel().findNamespace(XGUI_NAMESPACE_URI);
		Element element = context.getDocument().createElementNS(ns.getUri(), property.getTypeName().toLowerCase());
		element.setPrefix(ns.getPrefix());		
		context.getParentElement().appendChild(element);

		if (StringUtils.isNotEmpty(property.getValue())) {
			ns = context.getTransformModel().findNamespace(I18N_NAMESPACE_URI);
			Element i18nElement = context.getDocument().createElementNS(ns.getUri(), I18N_TEXT_ELEMENT_NAME);
			i18nElement.setPrefix(ns.getPrefix());
			element.appendChild(i18nElement);
			i18nElement.setTextContent(property.getValue());
		}
	}
	
	/**
	 * @param type
	 */
	public NamePropertyTransformer(PropertyType type) {
		super(type);
	}


}
