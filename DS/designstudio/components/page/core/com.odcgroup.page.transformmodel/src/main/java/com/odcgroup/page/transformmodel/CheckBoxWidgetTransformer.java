package com.odcgroup.page.transformmodel;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.mdf.DomainConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.properties.enabled.EnabledConstants;
import com.odcgroup.page.model.properties.enabled.EnabledIsBaseOnCondition;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * Transformer for the CheckBox widget.
 * 
 * @author pkk
 * 
 */
public class CheckBoxWidgetTransformer extends BaseWidgetTransformer {
	
	private Element checkboxField = null;
	
	/**
	 * Constructor
	 * @param type The widget type
	 */
	public CheckBoxWidgetTransformer(WidgetType type) {
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
		return checkboxField;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		checkboxField = null;
		
		boolean tablespec = false;
		ITableColumn tcol = null;;
		if (!context.isEditableTableTree()) {
			//DS-4949
			Widget tableCol = widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
			if (tableCol != null) {
				tcol = TableHelper.getTableColumn(tableCol);
				if (tcol.isPlaceHolder() && !tcol.isDisplayGrouping()) {
					tablespec = true;
				}
			}
		}
		
		if (tablespec) {
			transformTableColumnCheckbox(context, widget, tcol);
		} else {
			checkboxField = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_CHECKBOX);
			TransformUtils.appendChild(context, checkboxField);
			// Set the parent so that the Attributes are set on the correct element
			Element oldParent = context.setParentElement(checkboxField);
			transformTranslations(context, widget);
			Property propEnumValue = widget.findProperty(PropertyTypeConstants.ENUM_VALUE);
			if (propEnumValue != null && StringUtils.isNotBlank(propEnumValue.getValue())) {
				// DS-4763 - checkbox for enumeration / EnumMask
				transformProperties(context, widget);		
				TransformUtils.convertToAttribute(context, checkboxField, "id", widget.getID());
			} else {
				super.transformProperties(context, widget);		
			}
			transformEvents(context, widget);
			transformChildren(context, widget);
			context.setParentElement(oldParent);
		}
	}
	
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException  {
		for (Property property : widget.getProperties()) {
			PropertyTransformer propertyTransformer = null;
			if (property.getTypeName().equals(PropertyTypeConstants.SELECTED)) {
				propertyTransformer = new SelectedPropertyTransformer(property.getType());
			}
			else {
				propertyTransformer = context.getTransformModel().findPropertyTransformer(property);				
			}
			propertyTransformer.transform(context, property);	
		}		
	}
	
	/**
	 * @param context
	 * @param widget
	 * @param column
	 */
	private void transformTableColumnCheckbox(WidgetTransformerContext context, Widget widget, ITableColumn column) {
		boolean reactToMainCheckbox = context.reactToMainCheckbox();
		ITable table = column.getTable();
		Property chkBxGrp = widget.findProperty("checkbox-group-names");
		String groupnames = chkBxGrp != null ? chkBxGrp.getValue().trim() : "";
		 if (!StringUtils.isEmpty(groupnames) && isSimpleGroup(table) && !reactToMainCheckbox) { 
			Element checkbox = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_CHECKBOX);	
			TransformUtils.appendChild(context, checkbox);
			String exclusive = Boolean.toString(table.makeCheckboxesExclusive());
			Element op = context.setParentElement(checkbox);
			renderExclusiveCheckboxEventDetails(context, exclusive);
			context.setParentElement(op);
		} else {
			Element checkbox = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_CHECKBOX);	
			TransformUtils.appendChild(context, checkbox);
			checkbox.setAttribute("widget-group", "ReactToMainCheckBox");
			
			Property identifier = widget.findProperty("column-checkbox-identifier");
			String value = identifier != null ? identifier.getValue().trim() : "";
			String action = widget.getPropertyValue("column-checkbox-action");
			if (StringUtils.isEmpty(action)) {
				action = "sel";
			}
			
			
			Element oldParent = context.setParentElement(checkbox);
			
			Element xspAttribute = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_ATTRIBUTE_ELEMENT); 
	        xspAttribute.setAttribute(XSPConstants.XSP_NAME, "id");
	        xspAttribute.setTextContent(table.getID()+"_"+action+".");	
			TransformUtils.appendChild(context, xspAttribute);
			
			context.setParentElement(xspAttribute);
			Element udpItem = createElement(context, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_ITEM_ELEMENT);
			udpItem.setAttribute(UdpConstants.UDP_ITEM_COLUMN, value);
			TransformUtils.appendChild(context, udpItem);
			context.setParentElement(checkbox);
			
			Element xspAttribute2 = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_ATTRIBUTE_ELEMENT);  
			xspAttribute2.setAttribute(XSPConstants.XSP_NAME, "selected");
			TransformUtils.appendChild(context, xspAttribute2);
			context.setParentElement(xspAttribute2);
			Element udpItem2 = createElement(context, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_ROW_IS_SELECTED);
			udpItem2.setAttribute(UdpConstants.UDP_ITEM_COLUMN, action);
			TransformUtils.appendChild(context, udpItem2);
			context.setParentElement(checkbox);
			
			/**
			 * The Enabled property should be displayed irrespective of the property Security Column set or not. 
			 * The Property Security Column is not mandatory - DS - 4730
			 */		
			
			Element xspAttribute3 = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_ATTRIBUTE_ELEMENT);  
			xspAttribute3.setAttribute(XSPConstants.XSP_NAME, "enabled");
			TransformUtils.appendChild(context, xspAttribute3);
			context.setParentElement(xspAttribute3);
			
			String secColumn = widget.getPropertyValue("column-checkbox-security");
			String enabled = widget.getPropertyValue(PropertyTypeConstants.ENABLED);
			
			if (!StringUtils.isEmpty(secColumn) || "true".equals(enabled)) {				
				Element udpItem3 = createElement(context, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_ROW_IS_ENABLED);
				udpItem3.setAttribute(UdpConstants.UDP_ITEM_COLUMN, action);
				TransformUtils.appendChild(context, udpItem3);
			} 
			
			if (StringUtils.isEmpty(secColumn)) {
				if ("false".equals(enabled)) {
					xspAttribute3.setTextContent("false");
				} else if (EnabledConstants.ENABLED_CONDITIONAL_VALUE.equals(enabled)) {
					EnabledIsBaseOnCondition condition = new EnabledIsBaseOnCondition(widget);
					if (!condition.isSimplified()) {
						// Advanced condition
		            	Element xspLogic = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
		            	xspLogic.setTextContent(condition.getAdvancedCondition());
		            	oldParent.insertBefore(xspLogic, oldParent.getFirstChild());
			            
		            	Element xspExpr = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_EXPR);
		            	xspExpr.setTextContent(EnabledPropertyTransformer.CONDITION_VAL);
		            	xspAttribute3.appendChild(xspExpr);
					} else {
						String localName = condition.getSimplifiedCondition().getLocalName(); 
						String parts[] = localName.split(DomainConstants.ENTITY_SEPARATOR);
						String columnAttribute = "";
						if (parts.length > 1) {
							columnAttribute = parts[1];
						}
						Element udpItem3 = createElement(context, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_ITEM_ELEMENT);
						udpItem3.setAttribute(UdpConstants.UDP_ITEM_COLUMN, columnAttribute);
						TransformUtils.appendChild(context, udpItem3);
					}
				}
			}			
			context.setParentElement(checkbox);
			if (table.makeCheckboxesExclusive()) {
				String exclusive =  Boolean.toString(table.makeCheckboxesExclusive());
				renderExclusiveCheckboxEventDetails(context, exclusive);
			}
			context.setParentElement(oldParent);		
		}
		/*} else {
			
		}*/
	}

	/**
	 * @param table
	 * @return
	 */
	private boolean isSimpleGroup(ITable table) {
		List<ITableGroup> groups = table.getGroupsByRank();
		if(!groups.isEmpty()) {
			for(ITableGroup group : groups) {
				if (group.isHierarchy() && !group.isAggregatedData()) {
					return false;
				}	
			}
		}
		return true;
	}
	
	/**
	 * @param context
	 */
	private void renderExclusiveCheckboxEventDetails(WidgetTransformerContext context, String value) {
		Element xguiOnevent = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_ON_EVENT_ELEMENT); 
		TransformUtils.appendChild(context, xguiOnevent);
		Element oldParent = context.setParentElement(xguiOnevent);
		Element xguicheck = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_CHECKBOX_CHECK); 
		xguicheck.setAttribute("exclusive", value);
		TransformUtils.appendChild(context, xguicheck);
		context.setParentElement(oldParent);	
	}
	
	/**
	 * @param context
	 * @param widget
	 * @throws CoreException
	 */
	protected void transformTranslations(WidgetTransformerContext context, Widget widget) throws CoreException  {
		Property displayLabelProp = widget.findProperty("displayLabel");
		boolean display = displayLabelProp != null ? displayLabelProp.getBooleanValue() : false;
		ITranslation translation = context.getTranslation(widget);
		if (display)  {
			// label
			TransformUtils.transformTranslation(context, translation, ITranslationKind.NAME);
		}
		
		// tooltip
		TransformUtils.transformTranslation(context, translation, ITranslationKind.TEXT);
	}

}
