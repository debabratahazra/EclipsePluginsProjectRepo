package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.BOX_TYPE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FIELD_TYPE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SHOW_LABEL;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SHOW_LABEL_LABEL_ONLY;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SHOW_LABEL_NO_LABEL;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.BOX;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.LABEL;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.util.CdmUtils;
import com.odcgroup.page.model.util.WidgetCopier;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.transformmodel.cdm.CdmGenericWidgetTransformer;

/**
 * The WidgetTransformer for attributes.
 * 
 * @author Gary Hayes
 */
public class AttributeWidgetTransformer extends BaseWidgetTransformer {

	/** The factory used to create Widgets. */
	private WidgetFactory widgetFactory;

	/**
	 * Creates a new AttributeWidgetTransformer
	 * 
	 * @param type
	 *            The widget type
	 */
	public AttributeWidgetTransformer(WidgetType type) {
		super(type);

		widgetFactory = new WidgetFactory();
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		// Hack. If we are in the CDM domain we MUST perform a CDM-specific transformation.
		if (CdmUtils.isCdmDomain(widget)) {
			context.getParentWidgets().add(widget);
			transformCdm(context, widget);
			context.getParentWidgets().remove(widget);
			return;
		}
		
		Widget box = createWidget(BOX);
		Widget label = createWidget(LABEL);
		Widget field = createWidget(widget.getPropertyValue(FIELD_TYPE));
		
		// Copy the events
		for (Event e : widget.getEvents()) {
			Event ce = WidgetCopier.copy(e);
			field.getEvents().add(ce);
		}
		
		WidgetUtils.updateWidgetFromAttribute(widget, label);
		WidgetUtils.updateWidgetFromAttribute(widget, field);
		
		
		Property forProp = label.findProperty(PropertyTypeConstants.FOR);
		if (forProp != null) {
			Property group = field.findProperty(PropertyTypeConstants.WIDGET_GROUP);
			if (group != null) {
				String groupValue = group.getValue();
				if (StringUtils.isNotEmpty(groupValue)) {
					String bpp = BeanPropertyUtils.findBeanPropertyPrefix(context, widget);
					String forValue = groupValue + "." + bpp + forProp.getValue();
					forProp.setValue(forValue);
				} else {
					// do not generate "for" attribute if the widget group property has no value
					forProp.setValue("");
				}
			} else {
				// do not generate "for" attribute if no widget group is defined.
				forProp.setValue("");
			}
		}

		setBoxType(context, box);
		setLabelAlignment(context, label);
		setFieldAlignment(context, field);
		addLabel(context, widget, box, label);
		addField(context, widget, box, field);
		
		context.getParentWidgets().add(widget);
		WidgetTransformer bwt = context.getTransformModel().findWidgetTransformer(box);
		bwt.transform(context, box);
		context.getParentWidgets().remove(widget);
	}

	/**
	 * Gets the Xml element which represents the parent Element to which children will be attached. Note that this does
	 * not return all the XML that this transformer will generate. It is essentially used to help in the content-assist
	 * and auto-completion facilities.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The Widget to get the Element for
	 * @return Element The Element
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		// Attribute Widgets cannot include Code Widgets
		return null;
	}

	/**
	 * Creates a new empty widget of the given type
	 * 
	 * @param type
	 *            The widget type
	 * @return Widget The new widget
	 */
	protected Widget createWidget(String type) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel(); 
		WidgetType wt = metamodel.findWidgetType(WidgetLibraryConstants.XGUI, type);
		return widgetFactory.create(wt);
	}
	
	/**
	 * Sets the box type according to the corporate design.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param box The box containing the label and the field
	 */
	protected void setBoxType(WidgetTransformerContext context, Widget box) {
		box.setPropertyValue(BOX_TYPE, PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
	}
	
	/**
	 * Adds the label to the box only if the ShowLabel property is not set to 'NoLabel'.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param attribute The Attribute Widget
	 * @param box The box containing the label and the field
	 * @param label The Label
	 */
	private void addLabel(WidgetTransformerContext context, Widget attribute, Widget box, Widget label) {
		String sl = attribute.getPropertyValue(SHOW_LABEL);
		if (! SHOW_LABEL_NO_LABEL.equals(sl)) {
			box.getContents().add(label);
		}
	}
	
	/**
	 * Adds the field to the box only if the ShowLabel property is not set to 'LabelOnly'.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param attribute The Attribute Widget
	 * @param box The box containing the label and the field
	 * @param field The Field
	 */
	private void addField(WidgetTransformerContext context, Widget attribute, Widget box, Widget field) {
		String sl = attribute.getPropertyValue(SHOW_LABEL);
		if (! SHOW_LABEL_LABEL_ONLY.equals(sl)) {
			box.getContents().add(field);
		}
	}	
	
	/**
	 * Sets the alignment of the label using the Corporate Design.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param label The Label
	 */
	private void setLabelAlignment(WidgetTransformerContext context, Widget label) {
		CorporateDesign cd = context.getCorporateDesign();
		String ha = cd.getLabelHorizontalAlignment();
		label.setPropertyValue(PropertyTypeConstants.HORIZONTAL_ALIGNMENT, ha);
		
		String va = cd.getLabelVerticalAlignment();
		label.setPropertyValue(PropertyTypeConstants.VERTICAL_ALIGNMENT, va);			
	}	
	
	/**
	 * Sets the alignment of the field using the Corporate Design.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param field The Field
	 */
	private void setFieldAlignment(WidgetTransformerContext context, Widget field) {
		CorporateDesign cd = context.getCorporateDesign();
		String ha = cd.getFieldHorizontalAlignment();
		field.setPropertyValue(PropertyTypeConstants.HORIZONTAL_ALIGNMENT, ha);
		
		String va = cd.getFieldVerticalAlignment();
		field.setPropertyValue(PropertyTypeConstants.VERTICAL_ALIGNMENT, va);			
	}
	
	/**
	 * Transforms the Attribute Widget for CDM.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param attribute The Widget of Type Attribute
	 * @exception CoreException
	 */
	private void transformCdm(WidgetTransformerContext context, Widget attribute) throws CoreException {
		WidgetTransformer wt = new CdmGenericWidgetTransformer(attribute.getType());
		wt.transform(context, attribute);
	}
}
