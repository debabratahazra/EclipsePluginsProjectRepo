package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * FilterCriterial widget transformer implementation
 *
 * @author pkk
 *
 */
public class FilterCriteriaTransformer extends BaseWidgetTransformer {

	/**
	 * @param type
	 */
	public FilterCriteriaTransformer(WidgetType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#getParentElement(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return null;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		String uri = TransformerConstants.C_INCLUDE_NAMESPACE_URI;// the included model is reachable, so generate the inclusion
		Namespace ns = context.getTransformModel().findNamespace(uri);
		String elementName = TransformerConstants.INCLUDE_ELEMENT_NAME;
		Element element = context.getDocument().createElementNS(ns.getUri(), elementName);
		element.setPrefix(ns.getPrefix());
		TransformUtils.appendChild(context, element);			
		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(element);		
		transformProperties(context, widget);		
		// Reset the parent since this element should not contain any children
		context.setParentElement(oldParent);		
	}

}
