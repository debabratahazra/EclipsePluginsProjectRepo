package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_BEAN_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_GET_PROPERTY_ELEMENT;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_PROPERTY_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_URI;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.util.TransformUtils;

public class UrlPropertyTransformer extends BasePropertyTransformer {

	public UrlPropertyTransformer(PropertyType type) {
		super(type);
	}

	/**
	 * property url should be src attribute with url as value
	 */
	@Override
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {

		String domainAttributValue = property.getWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE_WITHOUT_VALIDATOR);
		
		if(domainAttributValue != null && domainAttributValue.length() > 0) {
			Property beanNameProperty = BeanPropertyUtils.findBeanNameProperty(context, property.getWidget());
			String beanNamePropertyValue = beanNameProperty.getValue();
			
			// Create the element <xsp:attribute name="src">...</xsp:attribute>
			Namespace ns = context.getTransformModel().findNamespace(XSP_NAMESPACE_URI);
			Element valElmt = context.getDocument().createElementNS(ns.getUri(), TransformerConstants.ATTRIBUTE_ELEMENT_NAME);
			valElmt.setPrefix(ns.getPrefix());
			TransformUtils.appendChild(context, valElmt);	
			// Create the attribute name="src"
			Attr a = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
			a.setValue("src");	
			valElmt.setAttributeNode(a);

			// create the <bean:get-property bean="..." property="..."/>
			Namespace beanNamespace = context.getTransformModel().findNamespace(BEAN_URI);
			Element defElmt = context.getDocument().createElementNS(beanNamespace.getUri(), BEAN_GET_PROPERTY_ELEMENT);
			defElmt.setPrefix(beanNamespace.getPrefix());
			addAttribute(context, defElmt, BEAN_BEAN_ATTRIBUTE, beanNamePropertyValue);
			addAttribute(context, defElmt, BEAN_PROPERTY_ATTRIBUTE, domainAttributValue);
			valElmt.appendChild(defElmt);
			
		}
		else {
			String url = property.getValue();
			if (url != null && url.length() != 0) {
				addAttribute(context, "src", url);
			}
		}
	}
}
