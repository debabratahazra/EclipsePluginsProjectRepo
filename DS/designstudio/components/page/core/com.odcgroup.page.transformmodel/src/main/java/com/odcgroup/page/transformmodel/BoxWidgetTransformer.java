package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.I18nConstants.I18N_ATTRIBUTE_NAME;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_PREFIX;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * Transforms the Box Widget into an hbox or vbox.
 * 
 * @author atr
 */
public class BoxWidgetTransformer extends BaseWidgetTransformer {

	/**
	 * Gets the name of the Xml Element to create.
	 * 
	 * @param widget
	 *            The Widget
	 * @return String The name of the Xml element to create
	 */
	private String getElementName(Widget widget) {
		Property p = widget.findProperty(TransformerConstants.BOX_TYPE_PROPERTY_NAME);
		String type = p.getValue();
		return TransformUtils.getBoxElementName(type);
	}

	@Override
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		String elementName = getElementName(widget);
		Element element = createElement(context, XGUI_NAMESPACE_URI, elementName);

		TransformUtils.appendChild(context, element);

		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(element);

		// translation key
		String key = context.getTranslationKey(widget, ITranslationKind.NAME);
		if (key != null) {
			TransformUtils.convertToAttribute(context, context.getParentElement(), "caption", key);
			Attr a = context.getDocument().createAttribute(I18N_NAMESPACE_PREFIX + ":" + I18N_ATTRIBUTE_NAME);
			a.setValue("caption");
			context.getParentElement().setAttributeNode(a);
		}

		transformProperties(context, widget);
		transformChildren(context, widget);

		context.setParentElement(oldParent);
	}

	/**
	 * Gets the Xml element which represents the parent Element to which
	 * children will be attached. Note that this does not return all the XML
	 * that this transformer will generate. It is essentially used to help in
	 * the content-assist and auto-completion facilities.
	 */
	@Override
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		String elementName = getElementName(widget);
		return createElement(context, XGUI_NAMESPACE_URI, elementName);
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 */
	public BoxWidgetTransformer(WidgetType type) {
		super(type);
	}

}