package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.XSPConstants.XSP_LOGIC;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * Transformer for the combobox widget.
 * 
 * @author yan
 * 
 */
public class ComboBoxWidgetTransformer extends BaseWidgetTransformer {
	
	private Element combo = null;
	
	/**
	 * Constructor
	 * @param type The widget type
	 */
	public ComboBoxWidgetTransformer(WidgetType type) {
		super(type);
	}

	/**
	 * Gets the Xml element which represents the parent Element to which children will be attached.
	 * Note that this does not return all the XML that this transformer will generate. It is essentially
	 * used to help in the content-assist and auto-completion facilities.
	 *  
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to get the Element for
	 * @return Element The Element
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return combo;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		combo = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_COMBO);	
		TransformUtils.appendChild(context, combo);

		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(combo);
		
		
		// DS-3032-begin - keep a reference on the ID property in case we need to change its value
		Property idProp = widget.findProperty(PropertyTypeConstants.ID);
		String idPropOldValue = idProp.getValue();
		// DS-3032-end

		// Note: the xgui:selected-value and bean:getProperty are managed at the module level
		boolean xtool = false;
		MdfModelElement modelElement = getMdfModelElement(context, widget);
		if (modelElement instanceof MdfDatasetProperty) {
			MdfDatasetProperty datasetProperty = (MdfDatasetProperty)modelElement;
			MdfEntity type = datasetProperty.getType();
			
			// is enum ?
			if (type instanceof MdfEnumeration) {
				// Read the model values
				Map<String, String> comboValues = getComboValueForEnum(context, (MdfEnumeration)type);
				
				// Create the combobox values
				
				if (datasetProperty instanceof MdfDatasetMappedProperty) {
					boolean required = isRequiredProperty((MdfDatasetMappedProperty)datasetProperty);
					if (!required) {
						createEmptyComboValue(context, combo);
					}
				}
				for (Map.Entry<String, String> comboValue : comboValues.entrySet()) {
					createFilteredComboValue(context, widget, combo, comboValue.getKey(), comboValue.getValue(), ((MdfEnumeration)type).getType());
				}
				xtool = true;
			} else { // is linked dataset
				// Create the combobox values
				
				/* DS-3032-begin hack 
				 * change the value of the property ID,
				 * the value {1} will be substitued later (see class BeanPropertyPropertyTransformer) 
				 */
				idProp.setValue("{1}._oid"); 
				// DS-3032-end
				
				xtool = isBooleanType(type);
				
				createSelectedValues(context, combo, widget, (xtool && context.isInXTooltipFragment()));
				boolean required = isRequiredLinkedDataset(context, widget);
				boolean requiredPageProp = isRequiredPageProperty(context, widget); // Additional check for DS-8260
				if (!required && !requiredPageProp) {
					createEmptyComboValue(context, combo);
				}
				createComboPermValues(context, widget, combo);
			}
		}
		
		boolean inXtool = context.isInXTooltipFragment();
		if (context.isInXTooltipFragment()) {
			context.setInXTooltipFragment(xtool);
		}
		transformProperties(context, widget);
		context.setInXTooltipFragment(inXtool);
		
		// DS-3032-begin - restore the old value of the property ID 
		idProp.setValue(idPropOldValue);
		// DS-3032-end

		transformEvents(context, widget);
		transformChildren(context, widget);

		context.setParentElement(oldParent);
	}
	
	
	/**
	 * Method to check whether the required property is true/false.
	 * 
	 * @param context
	 * @param widget
	 * @return
	 */
	private boolean isRequiredPageProperty(WidgetTransformerContext context, Widget widget) {
		Property required = widget.findProperty(PropertyTypeConstants.REQUIRED);
		return required.getBooleanValue();
	}

	private boolean isBooleanType(MdfEntity entity) {
		if (entity instanceof MdfPrimitive) {
			MdfPrimitive prim = (MdfPrimitive) entity;
			if (prim.equals(PrimitivesDomain.BOOLEAN) ||
					prim.equals(PrimitivesDomain.BOOLEAN_OBJ)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param datasetMappedProperty
	 * @return whether the mapped property is required or not
	 */
	private boolean isRequiredProperty(MdfDatasetMappedProperty datasetMappedProperty) {
		MdfDataset dataset = datasetMappedProperty.getParentDataset();
		StringTokenizer st = new StringTokenizer(datasetMappedProperty.getPath(), ".");
		MdfProperty property = null;
		MdfClass mdfClass = dataset.getBaseClass();
		while (st.hasMoreTokens()) {
			property = mdfClass.getProperty(st.nextToken());
			if (property.getType() instanceof MdfClass) {
				mdfClass = (MdfClass)property.getType();
			} else {
				mdfClass = null;
			}
		}
		if (property != null) {
			return property.isRequired();
		} else {
			return false;
		}
	}

	/**
	 * @param context
	 * @param widget
	 * @return whether the linked dataset is required or not
	 */
	@SuppressWarnings("rawtypes")
	private boolean isRequiredLinkedDataset(WidgetTransformerContext context, Widget widget) {
		List parentWidgets = context.getParentWidgets();
		Property domainAssociation=null;
		Widget includeWidget=null;
		if (!parentWidgets.isEmpty()) {
			includeWidget = (Widget)parentWidgets.get(0);
			domainAssociation=includeWidget.findProperty("domainAssociation");
		} else {
			includeWidget=widget.getRootWidget();
		}
		if(includeWidget != null) {	
			DomainRepository repository = getDomainRepository(context);
			MdfDatasetMappedProperty datasetMappedProperty=null;
			String name=null;
			if(domainAssociation !=null && StringUtils.isNotEmpty(domainAssociation.getValue())){
				name=domainAssociation.getValue();
			} else {
				name = getPropertyDomainAttributeValue(widget);
			}
			if(name!=null && StringUtils.isNotEmpty(name)){
				datasetMappedProperty = (MdfDatasetMappedProperty)includeWidget.findDomainObject(repository, name);
			}
			if (datasetMappedProperty != null) {
				return isRequiredProperty(datasetMappedProperty);
			}
		}
		return false;
	}

	/**
	 * Create a map where key are enum value and value are i18n key
	 * @param context The WidgetTransformerContext
	 * @param enumType mml enumeration  
	 * @return a map of <enum value, i18n key>
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getComboValueForEnum(
			WidgetTransformerContext context, MdfEnumeration enumType) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		
		// call the extension point
		for (MdfEnumValue value : (List<MdfEnumValue>)enumType.getValues()) {
			String key = context.getTranslationKey(value, ITranslationKind.NAME);
			if (key != null) {
				result.put(value.getValue(), key);
			}
		}
		return result;
	}

	/**
	 * Create an empty value for the combobox
	 * @param context The WidgetTransformerContext
	 * @param parentCombo parent combobox
	 */
	private void createEmptyComboValue(WidgetTransformerContext context, Element parentCombo) {
		Element item = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_ITEM);
		parentCombo.appendChild(item);
		
		Element xguiValue = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_VALUE);
		item.appendChild(xguiValue);
		
		Element xguiText = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_TEXT_ELEMENT);
		item.appendChild(xguiText);
	}

	
	/**
	 * Create a new xgui:combobox value with filtering
	 * @param context The WidgetTransformerContext
	 * @param widget the combobox widget
	 * @param parentCombo parent combobox
	 * @param key key part of the new box value
	 * @param value text part of the new combobox value
	 * @param enumType
	 */
	private void createFilteredComboValue(WidgetTransformerContext context, Widget widget, Element parentCombo, String key, String value, MdfPrimitive enumType) {
		Element filteringXpsLogic = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSP_LOGIC);
		parentCombo.appendChild(filteringXpsLogic);
		filteringXpsLogic.setTextContent("if (");

		String permValueProperty = "permValues." + widget.getPropertyValue(PropertyTypeConstants.BEAN_PROPERTY) + "PermValues";
		String domAssoc = TransformUtils.getDomainAssociationIfAny(context, widget);
		if(!StringUtils.isEmpty(domAssoc)) {
			permValueProperty = domAssoc+"."+permValueProperty;
		}

		Element isNullOrEmptyContidion = createElement(context, BeanConstants.BEAN_URI, BeanConstants.BEAN_IS_NULL_OR_EMPTY);
		isNullOrEmptyContidion.setAttribute(BeanConstants.BEAN_BEAN_ATTRIBUTE, "{2}");
		isNullOrEmptyContidion.setAttribute(BeanConstants.BEAN_PROPERTY_ATTRIBUTE, permValueProperty);
		filteringXpsLogic.appendChild(isNullOrEmptyContidion);	
		
		Text orCondition = context.getDocument().createTextNode(" || ");
		filteringXpsLogic.appendChild(orCondition);

		Element containsCondition = createElement(context, BeanConstants.BEAN_URI, BeanConstants.BEAN_CONTAINS);
		containsCondition.setAttribute(BeanConstants.BEAN_BEAN_ATTRIBUTE, "{2}");
		containsCondition.setAttribute(BeanConstants.BEAN_PROPERTY_ATTRIBUTE, permValueProperty);
		if (enumType.equals(PrimitivesDomain.STRING)) {
			containsCondition.setAttribute(BeanConstants.BEAN_STRING_ATTRIBUTE, key);
		} else {
			containsCondition.setAttribute(BeanConstants.BEAN_OBJECT_ATTRIBUTE, buildWrapper(enumType, key)); // TODO : use autoboxing
		}
		filteringXpsLogic.appendChild(containsCondition);	

		Text endCondition = context.getDocument().createTextNode(") { ");
		filteringXpsLogic.appendChild(endCondition);
		
		Element item = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_ITEM);
		parentCombo.appendChild(item);
		
		Element xguiValue = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_VALUE);
		xguiValue.setTextContent(key);
		item.appendChild(xguiValue);
		
		Element xguiText = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_TEXT_ELEMENT);
		item.appendChild(xguiText);
		
		Element i18nText = createElement(context, I18nConstants.I18N_NAMESPACE_URI, I18nConstants.I18N_TEXT_ELEMENT_NAME);
		xguiText.appendChild(i18nText);
		i18nText.setTextContent(value);
		
		Element endXpsLogic = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSP_LOGIC);
		parentCombo.appendChild(endXpsLogic);
		endXpsLogic.setTextContent("}");
		
	}
	
	/**
	 * TODO: replace by autoboxing
	 * @param enumType
	 * @param value
	 * @return wrapper instanciation instructions
	 */
	private String buildWrapper(MdfPrimitive enumType, String value) {
		if (enumType.equals(PrimitivesDomain.BOOLEAN) ||
				enumType.equals(PrimitivesDomain.BOOLEAN_OBJ)) {
			return "new " + Boolean.class.getName() + "(" + value + ")";
		} else if (enumType.equals(PrimitivesDomain.BYTE) ||
				enumType.equals(PrimitivesDomain.BYTE_OBJ)) {
			return "new " + Byte.class.getName() + "(" + value + ")";
		} else if (enumType.equals(PrimitivesDomain.CHAR) ||
				enumType.equals(PrimitivesDomain.CHAR_OBJ)) {
			return "new " + Character.class.getName() + "(" + value + ")";
		} else if (enumType.equals(PrimitivesDomain.DOUBLE) ||
				enumType.equals(PrimitivesDomain.DOUBLE_OBJ)) {
			return "new " + Double.class.getName() + "(" + value + "d)";
		} else if (enumType.equals(PrimitivesDomain.FLOAT) ||
				enumType.equals(PrimitivesDomain.FLOAT_OBJ)) {
			return "new " + Float.class.getName() + "(" + value + "f)";
		} else if (enumType.equals(PrimitivesDomain.INTEGER) ||
				enumType.equals(PrimitivesDomain.INTEGER_OBJ)) {
			return "new " + Integer.class.getName() + "(" + value + ")";
		} else if (enumType.equals(PrimitivesDomain.LONG) ||
				enumType.equals(PrimitivesDomain.LONG_OBJ)) {
			return "new " + Long.class.getName() + "(" + value + ")";
		} else if (enumType.equals(PrimitivesDomain.SHORT) || 
				enumType.equals(PrimitivesDomain.SHORT_OBJ)) {
			return "new " + Short.class.getName() + "(" + value + ")";
		} else if (enumType.equals(PrimitivesDomain.STRING)) {
			return value;
		} else {
			throw new RuntimeException("Unsupported primitive type for enum : " + enumType);
		}
	}

	/**
	 * Create a combobox for an association based on PermValues
	 * @param context The WidgetTransformerContext
	 * @param widget the combobox widget
	 * @param parentCombo parent combobox
	 */
	private void createComboPermValues(WidgetTransformerContext context, Widget widget,
			Element parentCombo) {
		
		Element beanIterate = createElement(context, BeanConstants.BEAN_URI, BeanConstants.BEAN_ITERATE);
		beanIterate.setAttribute(BeanConstants.BEAN_BEAN_ATTRIBUTE, "{2}");
		beanIterate.setAttribute(BeanConstants.BEAN_PROPERTY_ATTRIBUTE, "permValues.{1}PermValues");
		beanIterate.setAttribute(BeanConstants.BEAN_NAME_ATTRIBUTE, "current_value");
		parentCombo.appendChild(beanIterate);

		Element item = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_ITEM);
		beanIterate.appendChild(item);
		
		Element xguiValue = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_VALUE);
		item.appendChild(xguiValue); 

		Element beanGetIdProperty = createElement(context, BeanConstants.BEAN_URI, BeanConstants.BEAN_GET_PROPERTY_ELEMENT);
		beanGetIdProperty.setAttribute(BeanConstants.BEAN_BEAN_ATTRIBUTE, "current_value");
		beanGetIdProperty.setAttribute(BeanConstants.BEAN_PROPERTY_ATTRIBUTE, "_oid");
		xguiValue.appendChild(beanGetIdProperty);

		Element xguiText = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_TEXT_ELEMENT);
		item.appendChild(xguiText);
		
		Element beanGetValueProperty = createElement(context, BeanConstants.BEAN_URI, BeanConstants.BEAN_GET_PROPERTY_ELEMENT);
		beanGetValueProperty.setAttribute(BeanConstants.BEAN_BEAN_ATTRIBUTE, "current_value");
		beanGetValueProperty.setAttribute(BeanConstants.BEAN_PROPERTY_ATTRIBUTE, getDatasetAttributeName(context, widget));
		xguiText.appendChild(beanGetValueProperty);
	}
	
	/**
	 * @param context
	 * @param widget
	 * @return
	 */
	private String getDatasetAttributeName(WidgetTransformerContext context, Widget widget) {
		String datasetAttributeName = "";
		if (context.isEditableTableTree()) {
			datasetAttributeName = context.getEditableDatasetAttribute();
		} else {
			datasetAttributeName = widget.getPropertyValue(PropertyTypeConstants.BEAN_PROPERTY);
		}
		return datasetAttributeName;
	}
	
	/**
	 * Create the selected-value tag for the combobox 
	 * @param context widget context
	 * @param parentCombo combobox element
	 */
	private void createSelectedValues(WidgetTransformerContext context, Element parentCombo, Widget widget, boolean xtool) {
		Element selectedValue = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_SELECTED_VALUE);
		parentCombo.appendChild(selectedValue);		
		if (!xtool) {
			Element beanGetProperty = createElement(context, BeanConstants.BEAN_URI, BeanConstants.BEAN_GET_PROPERTY_ELEMENT);
			selectedValue.appendChild(beanGetProperty);
			beanGetProperty.setAttribute(BeanConstants.BEAN_BEAN_ATTRIBUTE, "{2}");
			beanGetProperty.setAttribute(BeanConstants.BEAN_PROPERTY_ATTRIBUTE, "{1}._oid");
		}
	}


}
