package com.odcgroup.page.transformmodel.table;

import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ITEM_COLUMN;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ITEM_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_LABEL_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TEXT_ELEMENT;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.Element;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.transformmodel.I18nConstants;
import com.odcgroup.page.transformmodel.PageTransformModelActivator;
import com.odcgroup.page.transformmodel.PropertyTransformer;
import com.odcgroup.page.transformmodel.UdpItemWidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.XSPConstants;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * This transformer transforms a Table/Tree TableColumnItem Widget into xgui
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnItemWidgetTransformer extends UdpItemWidgetTransformer {
	
	/**
	 * @see com.odcgroup.page.transformmodel.UdpItemWidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		ITableColumnItem item = TableHelper.getTableColumnItem(widget);
		if (item == null) {
			return;
		}
		//DS-2760 : Enumeration Key
		boolean enumType = false;
		String domainName = "";
		String enumName = "";
		MdfModelElement element = getMdfModelElement(context, widget);
		if (element != null && element instanceof MdfDatasetProperty) {
			MdfDatasetProperty prop = (MdfDatasetProperty) element;
			if (prop.getType() instanceof MdfEnumeration) {
				enumType = true;
				domainName = prop.getType().getQualifiedName().getDomain();
				enumName = prop.getType().getName().toLowerCase();
			}
		}
		Element logic0 = null;
		String columnName = getPropertyColumnName(widget);
		
		if (enumType) {
			logic0 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
			logic0.setTextContent("if(null == <udp:item column=\""+columnName+"\"/> || <udp:item column=\""+columnName+"\"/>.equals(\"\")) {");
			Element label0 = appendElement(context, XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
			appendElement(context, label0, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
			Element logic1 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
			logic1.setTextContent("} else {");
		}

		// create UDP/Item Node
		Element udpItem = null;
		
		// check if we are inside a dynamic column
		ITableColumn column = getTableColumn(widget);
		String dynamicGroupColumnName = "";
		boolean dynamicColumnWithoutAggregate = true;
		if (column != null && column.isDynamic()) {
			ITableGroup dynamicGroup = null;
			for (ITableGroup group : column.getTable().getGroups()) {
				if (group.isUsedForDynamicColumn()) {
					dynamicGroup = group;
					break;
				}
			}
			if (dynamicGroup != null) {
				dynamicGroupColumnName = dynamicGroup.getColumnName();
				String colName = column.getColumnName();
				boolean dynacolumnaggregate = false;
				// check aggregate for dynamic column
				for (ITableAggregate aggregate : column.getTable().getAggregatedColumns()) {
					if (colName.equals(aggregate.getColumnName())) {
						dynacolumnaggregate = true;
						break;
					}
				}
				// check aggregate for the column item
				if (dynacolumnaggregate) {
					for (ITableAggregate aggregate : column.getTable().getAggregatedColumns()) {
						if (columnName.equals(aggregate.getColumnName())) {
							dynacolumnaggregate = true;
							break;
						}
					}
				}
				if (dynacolumnaggregate) {
					dynamicColumnWithoutAggregate = false;
				}
			}
		}
		String align = item.getHorizontalAlignment();
		if (column != null && column.isDynamic() && dynamicColumnWithoutAggregate) {
			udpItem = createElement(context, UDP_NAMESPACE_URI, "child-item");
			addAttribute(context, udpItem, UDP_ITEM_COLUMN, columnName);
			addAttribute(context, udpItem, "child-name-column", dynamicGroupColumnName);
			udpItem.setTextContent("<udp:param name=\"child-name\"><udp:current-dynamic-column/></udp:param>");
		} else {
			udpItem = createElement(context, UDP_NAMESPACE_URI, UDP_ITEM_ELEMENT);
			if (!StringUtils.isEmpty(columnName)) {
				addAttribute(context, udpItem, UDP_ITEM_COLUMN, columnName);
			}
		}
		
		Element oldParent = null; 
		if (item.isNewLine()) {
			appendElement(context, XGUI_NAMESPACE_URI, "br");
		}
		Element label = appendElement(context,XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
		
		Property formatProperty = TransformUtils.getFormatProperty(widget);
		if (formatProperty != null && StringUtils.isNotEmpty(formatProperty.getValue())
				&& widget.getParent().getTypeName().equals(WidgetTypeConstants.CONDITIONAL_BODY)) {
			TransformUtils.insertFormatCondition(context, columnName, label, formatProperty);
		} else {

			Element text = appendElement(context, label, XGUI_NAMESPACE_URI, XGUI_TEXT_ELEMENT);
			String width = item.getItemPercentageWidth();
			if (!StringUtils.isEmpty(width)) {
				addAttribute(context, label, "width", width + "%");
				if (!StringUtils.isEmpty(align) && !align.equals("column")) {
					addAttribute(context, label, "halign", item.getHorizontalAlignment());
				}
			}
			if (enumType) {
				Element i18n = appendElement(context, text, I18nConstants.I18N_NAMESPACE_URI,
						I18nConstants.I18N_TEXT_ELEMENT_NAME);
				i18n.setTextContent(domainName.toLowerCase() + "." + enumName + ".<udp:item column=\"" + columnName
						+ "\"/>.text");
				text.appendChild(i18n);
				Element logic2 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
				logic2.setTextContent("}");
			} else {
				String xmlString = "";
				if (!StringUtils.isEmpty(width) && ("trail".equals(align) || "center".equals(align))) {
					xmlString = "&#160;" + TransformUtils.transformDomNodeToXML(udpItem);
				} else if (!StringUtils.isEmpty(width) && "lead".equals(align)) {
					xmlString = TransformUtils.transformDomNodeToXML(udpItem) + "&#160;";
				} else {
					xmlString = TransformUtils.transformDomNodeToXML(udpItem);
				}
				// TODO: insert logic
				text.setTextContent(xmlString);
			}
		}
		oldParent = context.setParentElement(label);
		
		transformProperties(context, widget);
		transformTranslations(context, widget);
        transformEvents(context, widget);
		context.setParentElement(oldParent);
	}
	
	
	/**
	 * @see com.odcgroup.page.transformmodel.BaseWidgetTransformer#transformProperties(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException  {
		for (Property p : widget.getProperties()) {
			if("item-column".equals(p.getTypeName()) 
					|| "item-halign".equals(p.getTypeName())
					|| "newLine".equals(p.getTypeName())
					|| "item-width".equals(p.getTypeName())){
				continue;
			}
			PropertyTransformer pt = context.getTransformModel().findPropertyTransformer(p);
			pt.transform(context, p);			
		}		
	}
	
	private ITableColumn getTableColumn(Widget widget) {
		ITableColumn column = null;
		Widget parent = widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
		try {
			column = TableHelper.getTableColumn(parent);
		} catch (IllegalArgumentException ex) {
			IStatus status = new Status(IStatus.WARNING,
					PageTransformModelActivator.PLUGIN_ID, 0,
					"The widget "+widget.getTypeName()+" must be in table column ", ex);
			PageTransformModelActivator.getDefault().getLog().log(status);
			column = null;
		}
		return column;
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.UdpItemWidgetTransformer#getPropertyColumnName(com.odcgroup.page.model.Widget)
	 */
	protected String getPropertyColumnName(Widget w) {
		String name = "";
		try {
			ITableColumnItem column = TableHelper.getTableColumnItem(w);
			name = column.getColumn();
		} catch (IllegalArgumentException ex) {
			IStatus status = new Status(IStatus.WARNING,
					PageTransformModelActivator.PLUGIN_ID, 0,
					"The parent widget of "+w.getTypeName()+" must be a table column ", ex);
			PageTransformModelActivator.getDefault().getLog().log(status);
		}
		return name;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.BaseWidgetTransformer#getPropertyDomainAttributeValue(com.odcgroup.page.model.Widget)
	 */
	protected String getPropertyDomainAttributeValue(Widget w) {
		String value = "";
		ITableColumnItem item = TableHelper.getTableColumnItem(w);
		try {
			ITableColumn column = item.getTableColumn();
			if (column.isBoundToDomain()) {
				//DS-5339 : since multiple items are allowed inside a single column, we must send the attribute of TableColumnItem
				value = getPropertyColumnName(w);
			}
		} catch (IllegalArgumentException ex) {
			IStatus status = new Status(IStatus.WARNING,
					PageTransformModelActivator.PLUGIN_ID, 0,
					"The widget "+w.getTypeName()+" must be in a table column ", ex);
			PageTransformModelActivator.getDefault().getLog().log(status);
		}
		return value;
	}
	
	/**
	 * @param type
	 */
	public TableColumnItemWidgetTransformer(WidgetType type) {
		super(type);
	}
	
}
