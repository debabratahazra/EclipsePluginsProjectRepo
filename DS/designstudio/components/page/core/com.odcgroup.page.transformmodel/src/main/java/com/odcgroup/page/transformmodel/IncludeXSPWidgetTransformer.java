package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * @author atr (DS-2737)
 */
public class IncludeXSPWidgetTransformer extends BaseWidgetTransformer {

	/**
	 * Constructor
	 * 
	 * @param type
	 * 			The widget type
	 */
	public IncludeXSPWidgetTransformer(WidgetType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		// DS-5925
		if (loadModuleAsynchronously(widget)) {
			transformAsync(context, widget);
			return;
		}
		
		// other kind of inclusion
		String elementName = TransformerConstants.INCLUDE_ELEMENT_NAME;
		String uri = TransformerConstants.C_INCLUDE_NAMESPACE_URI;
		
		// the included model is reachable, so generate the inclusion
		Namespace ns = context.getTransformModel().findNamespace(uri);
		Element element = context.getDocument().createElementNS(ns.getUri(), elementName);
		element.setPrefix(ns.getPrefix());

		TransformUtils.appendChild(context, element);	
		
		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(element);
		
		transformProperties(context, widget);
		
		// Reset the parent since this element should not contain any children
		context.setParentElement(oldParent);

	}
	
	/**
	 * DS-5925
	 * 
	 * @param context
	 * @param module
	 * @param includedModel
	 */
	private void transformAsync(WidgetTransformerContext context, Widget module) {

		Namespace ns = context.getTransformModel().findNamespace(XGuiConstants.XGUI_NAMESPACE_URI);
		Element element = context.getDocument().createElementNS(ns.getUri(), "module");
		element.setPrefix(ns.getPrefix());

		TransformUtils.appendChild(context, element);

		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(element);
		
		Property prop = module.findProperty(PropertyTypeConstants.XSP_PATH);
		String value = "";
		if (prop != null) {
			value = prop.getValue();
		}
		Attr src = context.getDocument().createAttribute("src");
		src.setValue(value);
		context.getParentElement().setAttributeNode(src);

		// Reset the parent since this element should not contain any children
		context.setParentElement(oldParent);
	}
	
	/**
	 * DS-5925
	 * 
	 * @param widget
	 * @param property
	 * @return
	 */
	private boolean loadModuleAsynchronously(Widget widget) {
		boolean asyncMode = false;
		Property prop = widget.findProperty(PropertyTypeConstants.INCLUDE_XSP_LOADING_MODE);
		if (prop != null) {
			asyncMode = "async".equals(prop.getValue());
		}
		return asyncMode;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#getParentElement(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return null;
	}

}
