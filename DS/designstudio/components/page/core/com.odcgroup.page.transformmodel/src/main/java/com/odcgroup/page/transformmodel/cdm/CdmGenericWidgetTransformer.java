package com.odcgroup.page.transformmodel.cdm;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.CHARS;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.COLUMNS;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.CSS_CLASS;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FIELD_TYPE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.ROWS;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SHOW_LABEL;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SHOW_LABEL_HIDE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SHOW_LABEL_LABEL_ONLY;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SHOW_LABEL_NO_LABEL;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SHOW_LABEL_SHOW;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.BOX;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.CALDATE_FIELD;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.CHECKBOX;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.COMBOBOX;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.LABEL;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.TEXTAREA;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.TEXTFIELD;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDMCOMP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDMCOMP_PARAM_ELEMENT;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDMTYPES_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_CLASS_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_CTYPE_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_FORMAT_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_GENERIC_ELEMENT;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_GET_ENUM_ELEMENT;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_HEIGHT_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_KEY_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_LABEL_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_NAME_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_PATH_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_POSTFIX_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_PREFIX_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_PROPERTY_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_TRANSLATE_KEY_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.cdm.CdmConstants.CDM_WIDTH_ATTRIBUTE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.transformmodel.AttributeWidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.translation.core.ITranslationKind;


/**
 * Transforms an Attribute Widget into a Cdm Generic tag.
 * 
 * @author Gary Hayes
 */
public class CdmGenericWidgetTransformer extends AttributeWidgetTransformer {

    /** The default enum type. */
    private static final String DEFAULT_CONFIG_FILE_NAME = "CdmConfig/";

    /** Mapping between a FieldType DataValue and the CDM field type. */
    private static Map<String, String> FIELD_TYPE_MAP = new HashMap<String, String>();
    static {
        FIELD_TYPE_MAP.put(TEXTAREA, "area");
        FIELD_TYPE_MAP.put(TEXTFIELD, "text");
        FIELD_TYPE_MAP.put(COMBOBOX, "combo");
        FIELD_TYPE_MAP.put(CALDATE_FIELD, "date");
        FIELD_TYPE_MAP.put(CHECKBOX, "check");
    }

    /**
     * Creates a new CdmGenericWidgetTransformer.
     * 
     * @param type the widget type
     */
    public CdmGenericWidgetTransformer(WidgetType type) {
        super(type);
    }

    /**
     * @see com.odcgroup.page.transformmodel.AttributeWidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
     */
    public void transform(WidgetTransformerContext context, Widget attribute) throws CoreException {
		// Create and transform the box used to contain the Cdm Generic Widget
		Widget box = createWidget(BOX);
		setBoxType(context, box);
    	Element be = createBoxElement(context, box);
    	Element oldParent = context.setParentElement(be);
    	
		String sl = attribute.getPropertyValue(SHOW_LABEL);
		if (SHOW_LABEL_LABEL_ONLY.equals(sl)) {
			// Create a traditional XGui Label instead of a CdmGeneric tag
			createXGuiLabel(context, attribute, box);
			return;
		}
    	
        Element e = appendElement(context, CdmConstants.CDMCOMP_NAMESPACE_URI, CDM_GENERIC_ELEMENT);

        addCharsAttribute(context, e, attribute);
        addCssClassAttribute(context, e, attribute);
        addHeightAttribute(context, e, attribute);
        addFieldTypeAttribute(context, e, attribute);
        addWidthAttribute(context, e, attribute);
        addShowLabelAttribute(context, e, attribute);
        addTextAttribute(context, e, attribute);

        addKeyAndNameAttributes(context, e, attribute);
        addActionElement(context, e, attribute);
        addGetEnumerationElement(context, e, attribute);

        // CDM does not support Events
        // transformEvents(context, widget);
        	
		context.setParentElement(oldParent);
    }
    
    /**
     * Creates the Box Element used to align the CDMGeneric tag correctly.
     * 
     * @param context The WidgeTransformerContext
     * @param box The Box Widget
     * @return Element The Element for the Box, HBox, VBox...
     * @exception CoreException
     */
    private Element createBoxElement(WidgetTransformerContext context, Widget box) throws CoreException {		
		WidgetTransformer bwt = context.getTransformModel().findWidgetTransformer(box);
		bwt.transform(context, box);
		
		// Now find the box that we just created and set it as the parent Element
		Element p = context.getParentElement();
		Element be = (Element) p.getChildNodes().item(p.getChildNodes().getLength() - 1);
		return be;
    }
    
    /**
     * Creates an XGui Label.
     * 
     * @param context The WidgetTransformerContext
     * @param attribute The Widget to transform
     * @param box The parent Box Widget
     * @exception CoreException
     */ 
    private void createXGuiLabel(WidgetTransformerContext context, Widget attribute, Widget box) throws CoreException {    	
		Widget label = createWidget(LABEL);
		WidgetUtils.updateWidgetFromAttribute(attribute, label);
		
		box.getContents().add(label);
		WidgetTransformer bwt = context.getTransformModel().findWidgetTransformer(label);
		bwt.transform(context, label);
    }

    /**
     * Gets the Xml element which represents the parent Element to which children will be attached. Note that this does
     * not return all the XML that this transformer will generate. It is essentially used to help in the content-assist
     * and auto-completion facilities.
     * 
     * @param context The WidgetTransformerContext
     * @param widget The Widget to get the Element for
     * @return Element The Element
     */
    public Element getParentElement(WidgetTransformerContext context, Widget widget) {
        // Attribute Widgets cannot include Code Widgets
        return null;
    }

    /**
     * Adds the attribute related to the chars.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addCharsAttribute(WidgetTransformerContext context, Element e, Widget attribute) {
        Property p = attribute.findProperty(CHARS);
        addNonDefaultAttribute(context, e, p, CDM_CLASS_ATTRIBUTE);
    }

    /**
     * Adds the attribute related to the Css class.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addCssClassAttribute(WidgetTransformerContext context, Element e, Widget attribute) {
        Property p = attribute.findProperty(CSS_CLASS);
        addNonDefaultAttribute(context, e, p, CDM_CLASS_ATTRIBUTE);
    }

    /**
     * Adds the attribute related to the height.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addHeightAttribute(WidgetTransformerContext context, Element e, Widget attribute) {
        Property p = attribute.findProperty(ROWS);
        addNonDefaultAttribute(context, e, p, CDM_HEIGHT_ATTRIBUTE);
    }

    /**
     * Adds the attribute related to the field-type.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addFieldTypeAttribute(WidgetTransformerContext context, Element e, Widget attribute) {
        String fieldType = attribute.getPropertyValue(FIELD_TYPE);
        String value = FIELD_TYPE_MAP.get(fieldType);
        // DS-2169 (DS-1652:wrong) - begin workaround
        if (fieldType.equals(CALDATE_FIELD)) {
	        String type = attribute.getPropertyValue(PropertyTypeConstants.TYPE);
	        if (type.equals("datetime")) {
	        	value = "datetime";
	            addAttribute(context, e, CDM_FORMAT_ATTRIBUTE, value);
	        } else {
	            addAttribute(context, e, CDM_CTYPE_ATTRIBUTE, value);
	        }
        } else {
            addAttribute(context, e, CDM_CTYPE_ATTRIBUTE, value);
        }
        // DS-2169 - end
    }

    /**
     * Adds the attribute related to the Show Label.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addShowLabelAttribute(WidgetTransformerContext context, Element e, Widget attribute) {
        String sl = attribute.getPropertyValue(SHOW_LABEL);
        if (SHOW_LABEL_SHOW.equals(sl) || SHOW_LABEL_HIDE.equals(sl)) {
            addAttribute(context, e, CDM_LABEL_ATTRIBUTE, "true");
        } else if (SHOW_LABEL_NO_LABEL.equals(sl)) {
            addAttribute(context, e, CDM_LABEL_ATTRIBUTE, "false");
        }
    }

    /**
     * Adds the attribute related to the Text.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    protected void addTextAttribute(WidgetTransformerContext context, Element e, Widget attribute) {
        String sl = attribute.getPropertyValue(SHOW_LABEL);
        if (SHOW_LABEL_SHOW.equals(sl)) {
        	String key = context.getTranslationKey(attribute,ITranslationKind.NAME);
        	if (key != null) {
        		addAttribute(context, e, CDM_TRANSLATE_KEY_ATTRIBUTE, key);
        	}
        }
    }

    /**
     * Adds the attribute related to the width.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addWidthAttribute(WidgetTransformerContext context, Element e, Widget attribute) {
        Property p = attribute.findProperty(COLUMNS);

        // addNonDefaultAttribute(context, e, p, CDM_WIDTH_ATTRIBUTE);
		// OCS-26823 - 15/Jul/2008 - Resizing of drop down boxes 
        Property fieldType = attribute.findProperty(PropertyTypeConstants.FIELD_TYPE);
        if (null == fieldType) {
        	addNonDefaultAttribute(context, e, p, CDM_WIDTH_ATTRIBUTE);
        } else {
        	String type = fieldType.getValue();
        	if (type.equals(WidgetTypeConstants.COMBOBOX)) {
            	/*
            	 * do not generate width property whenever attribute widget 
            	 * has a 0-width combobox.
            	 */
        		String value = p.getValue();
        		if ((StringUtils.isNotEmpty(value)) && ! value.trim().equals("0")) {
                	addNonDefaultAttribute(context, e, p, CDM_WIDTH_ATTRIBUTE);
        		}
        	} else {
            	addNonDefaultAttribute(context, e, p, CDM_WIDTH_ATTRIBUTE);
        	}
        } 
		// OCS-26823 - end 
    }

    /**
     * Adds an attribute but only if its value is not equal to its default value.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param property The Property to get the value for
     * @param attributeName The name of the Attribute to create
     */
    private void addNonDefaultAttribute(WidgetTransformerContext context, Element e, Property property,
            String attributeName) {
        if (property.isDefaultValue()) {
            return;
        }

        String value = property.getValue();
        if (StringUtils.isEmpty(value)) {
            return;
        }

        addAttribute(context, e, attributeName, value);
    }

    /**
     * Adds the key and property attributes.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addKeyAndNameAttributes(WidgetTransformerContext context, Element e, Widget attribute) {
        Property pName = findBeanNameProperty(context, attribute);
        Property pProperty = attribute.findProperty(PropertyTypeConstants.BEAN_PROPERTY);

        if (pName == null || pProperty == null) {
            return;
        }

        if (StringUtils.isEmpty(pName.getValue()) || StringUtils.isEmpty(pProperty.getValue())) {
            return;
        }

        String bpp = findBeanPropertyPrefix(context, attribute);

        addAttribute(context, e, CDM_KEY_ATTRIBUTE, pName.getValue());
        addNonEmptyAttribute(context, e, CDM_PROPERTY_ATTRIBUTE, bpp);
        addAttribute(context, e, CDM_NAME_ATTRIBUTE, pProperty.getValue());
    }

    /**
     * Adds the key and property attributes.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addGetEnumerationElement(WidgetTransformerContext context, Element e, Widget attribute) {
        MdfModelElement mdfe = getMdfModelElement(context, attribute);

        MdfEntity entity = getMdfType(mdfe);

        // If the enumeration is on 'old' one. In other words it has existed in CDM for a long-time
        // it needs to have a specific name. This name is obtained from the annotation 'Custom'.
        // In general it is something like CdmConfig/Party/Person/Type.
        if (entity instanceof MdfEnumeration) {
            MdfEnumeration en = (MdfEnumeration) entity;
            MdfAnnotation cfa = en.getAnnotation("http://www.odcgroup.com/mdf/cdm", "CDM");

            String s = null;
            if (cfa != null) {
                // xml returns something like Party.Person.Gender. We need to transform this to
                // CdmConfig/Party/Person/Gender
                String xml = cfa.getProperty("xml").getValue().replace(".", "/");
                s = DEFAULT_CONFIG_FILE_NAME + xml;
            } else {
                s = DEFAULT_CONFIG_FILE_NAME + "General/" + en.getName();
            }

            Element enumElement = appendElement(context, e, CDMTYPES_NAMESPACE_URI, CDM_GET_ENUM_ELEMENT);
            addAttribute(context, enumElement, CDM_PATH_ATTRIBUTE, s);

            // Add the prefix and the postfix for the translation
            String prefix = (en.getParentDomain().getName() + "." + en.getName() + ".").toLowerCase();
            addAttribute(context, enumElement, CDM_PREFIX_ATTRIBUTE, prefix);
            addAttribute(context, enumElement, CDM_POSTFIX_ATTRIBUTE, ".text");
        }
    }

    /**
     * Adds the action element.
     * 
     * @param context The WidgetTransformerContext
     * @param e The Element to add the Attribute to
     * @param attribute The Attribute Widget
     */
    private void addActionElement(WidgetTransformerContext context, Element e, Widget attribute) {
        String a = attribute.getPropertyValue(PropertyTypeConstants.ACTION);
        if (StringUtils.isEmpty(a)) {
            return;
        }

        // Actions a re not available for Dates
        String ft = attribute.getPropertyValue(PropertyTypeConstants.FIELD_TYPE);
        if (ft.equals(WidgetTypeConstants.CALDATE_FIELD)) {
            return;
        }

        Element ce = appendElement(context, e, CDMCOMP_NAMESPACE_URI, CDMCOMP_PARAM_ELEMENT);
        addAttribute(context, ce, CDM_NAME_ATTRIBUTE, "action");
        ce.setTextContent(a);
    }

    /**
     * Finds the first BeanName property which is not empty. This looks for the BeanName in the parent Widgets.
     * 
     * @param context The WidgetTransformerContext
     * @param widget The Widget to look for the BeanName for
     * @return Property The BeanName or null if no BeanName could be found
     */
    private Property findBeanNameProperty(WidgetTransformerContext context, Widget widget) {
        return findNonNullProperty(context, widget, PropertyTypeConstants.BEAN_NAME);
    }

    /**
     * Finds the BeanPropertyPrefix property. This is a concatenation of prefixes found in parent Fragments.
     * 
     * @param context The WidgetTransformerContext
     * @param widget The Widget to look for the BeanPropertyPrefix for
     * @return String The prefix
     */
    private String findBeanPropertyPrefix(WidgetTransformerContext context, Widget widget) {
        Widget w = widget;
        List<Widget> parentWidgets = context.getParentWidgets();
        int parentIndex = parentWidgets.size() - 1;

        String s = "";
        while (w != null) {
            Property p = w.findProperty(PropertyTypeConstants.BEAN_PROPERTY_PREFIX);
            if (p != null) {
                s = p.getValue() + s;
            }

            w = w.getParent();
            if (w == null && parentIndex >= 0) {
                w = parentWidgets.get(parentIndex);
                parentIndex -= 1;
            }
        }

        return s;
    }

    /**
     * Finds the first property with the given name which is not empty. This looks for the property in the parent
     * Widgets.
     * 
     * @param context The WidgetTransformerContext
     * @param widget The Widget to look for the property for
     * @param propertyName The property name to look for
     * @return Property The property or null if no property could be found
     */
    private Property findNonNullProperty(WidgetTransformerContext context, Widget widget, String propertyName) {
        Widget w = widget;
        List<Widget> parentWidgets = context.getParentWidgets();
        int parentIndex = parentWidgets.size() - 1;

        while (w != null) {
            Property p = w.findProperty(propertyName);
            if (p != null && StringUtils.isNotEmpty(p.getValue())) {
                return p;
            }

            w = w.getParent();
            if (w == null && parentIndex >= 0) {
                w = parentWidgets.get(parentIndex);
                parentIndex -= 1;
            }
        }

        return null;
    }
}