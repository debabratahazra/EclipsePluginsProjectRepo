package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

public class ExternalIncludeWidgetTransformer extends BaseWidgetTransformer {

	public ExternalIncludeWidgetTransformer(WidgetType type) {
		super(type);
	}

	@Override
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		String elementName = TransformerConstants.EXTERNAL_INCLUDE_ELEMENT_NAME;
		String uri = TransformerConstants.XGUI_WIDGET_LIBRARY_NAME;
		
		Namespace namespace = context.getTransformModel().findNamespace(uri);
		Element element = context.getDocument().createElementNS(namespace.getUri(), elementName);
		element.setPrefix(namespace.getPrefix());

		
		TransformUtils.appendChild(context, element);	
		
		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(element);
		
		transformProperties(context, widget);
		
		Attr attributeNode = element.getAttributeNode(PropertyTypeConstants.DOMAIN_ATTRIBUTE_WITHOUT_VALIDATOR.toLowerCase());
		if (attributeNode != null) {
			element.removeAttributeNode(attributeNode);
		}
		
		// Reset the parent since this element should not contain any children
		context.setParentElement(oldParent);
	}

	@Override
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return null;
	}

}
