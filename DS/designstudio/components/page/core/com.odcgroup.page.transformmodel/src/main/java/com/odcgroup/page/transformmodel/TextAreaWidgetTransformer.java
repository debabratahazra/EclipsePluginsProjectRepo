package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transformer for the TextArea widget.
 * 
 * @author atr
 * 
 */
public class TextAreaWidgetTransformer extends BaseWidgetTransformer {
	
	private Element textarea = null;
	
	/**
	 * Constructor
	 * @param type The widget type
	 */
	public TextAreaWidgetTransformer(WidgetType type) {
		super(type);
	}

	@Override
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return textarea;
	}

	@Override
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		textarea = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_TEXTAREA_ELEMENT);
		TransformUtils.appendChild(context, textarea);

		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(textarea);
		transformTranslations(context, widget);
		transformProperties(context, widget);
		transformEvents(context, widget);
		transformChildren(context, widget);

		context.setParentElement(oldParent);
	}
	

}
