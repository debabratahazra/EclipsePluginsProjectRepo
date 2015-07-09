package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;


/**
 *
 * @author pkk
 */
public class RadioButtonWidgetTransformer extends BaseWidgetTransformer {

	/**
	 * @param type
	 */
	public RadioButtonWidgetTransformer(WidgetType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#getParentElement(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	@Override
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return null;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	@Override
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		Element radio = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_RADIO);	
		TransformUtils.appendChild(context, radio);
		
		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(radio);	
		transformTranslations(context, widget);
		transformProperties(context, widget);
		if (widget.getEvents().isEmpty()) {
			transformDefaultEvent(context, widget);
		}
		transformEvents(context, widget);

//		TransformUtils.convertToAttribute(context, radio, "id", widget.getID());
		
		transformChildren(context, widget);

		context.setParentElement(oldParent);
	}
	
	/**
	 * @param context
	 * @param widget
	 * @throws CoreException
	 */
	private void transformDefaultEvent(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		Element param = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, TransformerConstants.EVENT_USER_PARAMETER);
		
		if(StringUtils.isBlank(widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE))) {
			
			// event
			Element event = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, TransformerConstants.ONEVENT_ELEMENT_NAME);
			TransformUtils.convertToAttribute(context, event, TransformerConstants.TYPE_ATTRIBUTE_NAME, "click");
			// submit
			Element submit = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, TransformerConstants.SUBMIT_NAME);
			TransformUtils.convertToAttribute(context, submit, TransformerConstants.METHOD_ATTRIBUTE_NAME, "post");
			//DS-4062:TransformationUtils.convertToAttribute(context, submit, "stabs", "true");
			// params
			Element flow = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, TransformerConstants.EVENT_USER_PARAMETER);
			TransformUtils.convertToAttribute(context, flow, "name", "flow-action");
			flow.setTextContent("reload");
			
			
			Element attribute = createElement(context, XSP_NAMESPACE_URI, TransformerConstants.ATTRIBUTE_ELEMENT_NAME);
			TransformUtils.convertToAttribute(context, attribute, TransformerConstants.NAME_ATTRIBUTE_NAME, TransformerConstants.NAME_ATTRIBUTE_NAME);
			Element nav = createElement(context, TransformerConstants.NAV_NAMESPACE_URI, "get-nav-param");
			TransformUtils.convertToAttribute(context, nav, "group-id", widget.getPropertyValue("group"));
			TransformUtils.convertToAttribute(context, nav, "element-id", widget.getID());
			
			//Element text = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_TEXT_ELEMENT);
			//text.setTextContent(widget.getID());
	
			TransformUtils.appendChild(attribute, nav);
			String attributeXml = TransformUtils.transformDomNodeToXML(attribute);
			param.setTextContent(attributeXml+widget.getID());
			//TransformerUtils.appendChild(param, attribute);
			//TransformerUtils.appendChild(param, text);
	
			TransformUtils.appendChild(event, submit);
			TransformUtils.appendChild(submit, flow);
			TransformUtils.appendChild(submit, param);
			TransformUtils.appendChild(context, event);
		}
	}
		
	/**
	 * Transforms the Properties of the Widget.
	 *
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget whose properties are to be transformed
	 * @exception CoreException
	 */
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException  {
		for (Property property : widget.getProperties()) {
			PropertyTransformer propertyTransformer = null;
			
			
			if (property.getTypeName().equals(PropertyTypeConstants.SELECTED)) {
				propertyTransformer = new SelectedPropertyTransformer(property.getType());
			}
			else {
				propertyTransformer = context.getTransformModel().findPropertyTransformer(property);				
			}

			if (!property.getTypeName().equals(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION)) {
				propertyTransformer.transform(context, property);	
			} else {
				String textPosition = property.getValue();
				if(!textPosition.equalsIgnoreCase(PropertyTypeConstants.TRAIL)) {
					propertyTransformer.transform(context, property);
				} 
			}
		}		
	}
	
	
	/**
	 * @param context
	 * @param widget
	 * @throws CoreException
	 */
	protected void transformTranslations(WidgetTransformerContext context, Widget widget) throws CoreException  {
		Property displayLabelProp = widget.findProperty("displayLabel");
		boolean display = displayLabelProp.getBooleanValue();
		ITranslation translation = context.getTranslation(widget);
		if (display)  {
			// label
			TransformUtils.transformTranslation(context, translation, ITranslationKind.NAME);
		}
		
		// tooltip
		TransformUtils.transformTranslation(context, translation, ITranslationKind.TEXT);
	}

}
