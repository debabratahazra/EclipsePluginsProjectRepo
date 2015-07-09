package com.odcgroup.page.transformmodel.cdm;

import static com.odcgroup.page.transformmodel.UdpConstants.UDP_BUILD_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TABLE_ELEMENT;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.transformmodel.BaseWidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 * This class implement the transformer for the CDM Table
 * 
 * @author Alexandre Jaquet
 *
 */
public class CdmTableWidgetTransformer extends BaseWidgetTransformer{
	

	/**
	 * Constructor
	 * 
	 * @param type 
	 * 			The widget type
	 */
	public CdmTableWidgetTransformer(WidgetType type) {
		super(type);
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		List<Widget> columnHeaders = getCdmColumnHeaders(context, widget);
		List<Widget> sortedColumnHeaders = getSortedColumnHeaders(context, widget, columnHeaders);
		List<Widget> visibleColumnHeaders = getVisibleColumnHeaders(context, widget, columnHeaders);
		int indexOfFirstVisibleColumn = getIndexOfFirstVisibleColumns(context, sortedColumnHeaders);
		
		buildCdmCompTableModel(context, widget);
		
		Element udp =  buildUdp(context, widget);
		Element buildParent = udp;
		if (context.isRootUDPOpen()) {
			buildParent = context.getParentElement();
		}
		Element build = appendElement(context, buildParent, UDP_NAMESPACE_URI, UDP_BUILD_ELEMENT);
			
		if (hasFilter(context, widget)) {
			buildUdpFilter(context, widget, build);
		}
		if (hasUdpKeepFilter(context, widget)) {
			buildUdpKeepFilter(context, widget, build);
		}

		Element list = buildUdpList(context, build, widget);
		buildUdpSort(context, widget, list,columnHeaders);
		
		buildUdpDumpModel(context, widget, build);
		
		Element renderListParent = udp;
		if (context.isRootUDPOpen()) {
			renderListParent = context.getParentElement();
		}
		Element renderList = buildUdpRenderList(context, renderListParent, widget);
        Element oldParent = context.setParentElement(renderList);
        buildLogicNotEmpty(context, widget);
        context.setParentElement(oldParent);

        Element table = appendElement(context, renderList, XGUI_NAMESPACE_URI, XGUI_TABLE_ELEMENT);
        // OCS-26876 - Add ID tag in table/tree
		addNonEmptyAttribute(context, table, "id", widget.getID());

        oldParent = context.setParentElement(table);
        if (showColumnHeaders(widget)) {
            buildXGuiColumns(context, visibleColumnHeaders, indexOfFirstVisibleColumn); 
        }
        buildUdpRows(context, widget, indexOfFirstVisibleColumn);
		
        context.setParentElement(oldParent);
        buildLogicNotEmptyClosingTag(context, widget,renderList);
        if (hasPageSize(context, widget)) {
            buildUdpPage(context, list, widget);
            buildTableFooter(context, widget, renderList);
        }	

	}	

	/**
	 * Builds the  %lt;cdmcomp-table-model&gt; section.
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 */
	protected void buildCdmCompTableModel(WidgetTransformerContext context, Widget widget) {
		Element tableModel = appendElement(context, CdmConstants.CDMCOMP_NAMESPACE_URI, CdmConstants.CDMCOMP_TABLE_MODEL);
		buildTableModelAttributes(context, widget, tableModel);
		List<Widget> headers = getCdmColumnHeaders(context, widget);
		for (Widget header : headers) {
			Element tableColumn = appendElement(context, tableModel, CdmConstants.CDMCOMP_NAMESPACE_URI, CdmConstants.CDMCOMP_TABLE_COLUMN);
			String tableColumnName = header.getPropertyValue(PropertyTypeConstants.COLUMN_NAME);
			String type = header.getPropertyValue(PropertyTypeConstants.TYPE);
			if (!StringUtils.isEmpty(tableColumnName)) {
				addAttribute(context, tableColumn, "name", tableColumnName);
				addNonEmptyAttribute(context, tableColumn, PropertyTypeConstants.TYPE, type);
			}
			String actionToCheck = header.getPropertyValue(PropertyTypeConstants.ACTION_TO_CHECK);
			if (!StringUtils.isEmpty(actionToCheck)) {
				Element columnAction = appendElement(context, tableColumn, CdmConstants.CDMCOMP_NAMESPACE_URI, CdmConstants.CDMCOMP_COLUMN_ACTION);
				buildCdmColumnActionAttributes(context, header, columnAction);
			}
			String decorator = header.getPropertyValue(CdmConstants.CDM_DECORATOR);
			if (!StringUtils.isEmpty(decorator)) {
				Element decoratorElement = appendElement(context, tableColumn,CdmConstants.CDMCOMP_NAMESPACE_URI,CdmConstants.CDMCOMP_COLUMN_DECORATOR);
				buildCdmColumnDecoratorAttributes(context, header, decoratorElement);
			}
			String columnDifference = header.getPropertyValue(CdmConstants.CDM_COLUMN_DIFFERENCE_ON_ADDED_PROPERTY_TYPE);
			if (!StringUtils.isEmpty(columnDifference)) {
				Element columnDifferenceElement = appendElement(context, tableColumn,CdmConstants.CDMCOMP_NAMESPACE_URI,CdmConstants.CDMPCOMP_COLUMN_DIFFERENCE);
				buildColumnDifferenceAttributes(context, header, columnDifferenceElement);
			}
			String columnPropertyName = header.getPropertyValue(CdmConstants.CDM_PROPERTY_NAME);
			String columnPropertyProperty = header.getPropertyValue(CdmConstants.CDM_PROPERTY_PROPERTY);
			if (!StringUtils.isEmpty(columnPropertyName)|| !StringUtils.isEmpty(columnPropertyProperty)) {
				Element columnProperty = appendElement(context, tableColumn,CdmConstants.CDMCOMP_NAMESPACE_URI,CdmConstants.CDMCOMP_COLUMN_PROPERTY);
				buildCdmColumnPropertyAttributes(context, header, columnProperty);
			}
		}
	}

	/** 
	 * Builds the column-property attributes.
	 * 
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 * @param parent
	 * 			The parent element
	 */
	protected void buildCdmColumnPropertyAttributes(WidgetTransformerContext context, Widget widget, Element parent) {
		String name = widget.getPropertyValue(CdmConstants.CDM_PROPERTY_NAME);
		String format = widget.getPropertyValue(CdmConstants.CDM_PROPERTY_FORMAT);
		String property = widget.getPropertyValue(CdmConstants.CDM_PROPERTY_PROPERTY);
		
		addNonEmptyAttribute(context, parent, "name", name);
		addNonEmptyAttribute(context, parent, PropertyTypeConstants.FORMAT, format);
		addNonEmptyAttribute(context, parent, PropertyTypeConstants.PROPERTY, property);
		
	}
	/**
	 * Builds the column-decorator attributes.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param parent
	 *            The parent element
	 */
	protected void buildCdmColumnDecoratorAttributes(WidgetTransformerContext context, Widget widget, Element parent) {
		String property = widget.getPropertyValue(CdmConstants.CDM_DECORATOR_PROPERTY);
		String decorator = widget.getPropertyValue(CdmConstants.CDM_DECORATOR);
		
		addNonEmptyAttribute(context, parent, PropertyTypeConstants.PROPERTY, property);
		addNonEmptyAttribute(context, parent, CdmConstants.CDM_DECORATOR_ATTRIBUTE, decorator);
	}
	
	/**
	 * Builds the column-action attributes.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param parent
	 *            The parent element
	 */
	protected void buildCdmColumnActionAttributes(WidgetTransformerContext context, Widget widget, Element parent) {
		String property = widget.getPropertyValue(CdmConstants.CDM_COLUMN_ACTION_PROPERTY);
		String trueResult = widget.getPropertyValue(CdmConstants.CDM_TRUE_RESULT_PROPERTY_TYPE);
		String falseResult = widget.getPropertyValue(CdmConstants.CDM_FALSE_RESULT_PROPERTY_TYPE);
		String actionToCheck = widget.getPropertyValue(PropertyTypeConstants.ACTION_TO_CHECK);
		
		addNonEmptyAttribute(context, parent, CdmConstants.CDM_PROPERTY, property);
		addNonEmptyAttribute(context, parent, CdmConstants.CDM_TRUE_RESULT, trueResult);
		addNonEmptyAttribute(context, parent, CdmConstants.CDM_FALSE_RESULT, falseResult);
		addNonEmptyAttribute(context, parent, CdmConstants.CDMCOMP_ACTION_TO_CHECK, actionToCheck);
	}
	

	/**
	 * Builds the column-difference attributes.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param parent
	 *            The parent element
	 */
	protected void buildColumnDifferenceAttributes(WidgetTransformerContext context, Widget widget, Element parent) {
		String onAdded = widget.getPropertyValue(CdmConstants.CDM_COLUMN_DIFFERENCE_ON_ADDED_PROPERTY_TYPE);
		String onRemoved = widget.getPropertyValue(CdmConstants.CDM_COLUMN_DIFFERENCE_ON_REMOVED_PROPERTY_TYPE);
		String onCommon = widget.getPropertyValue(CdmConstants.CDM_COLUMN_DIFFERENCE_ON_COMMON_PROPERTY_TYPE);
		
		addNonEmptyAttribute(context, parent, CdmConstants.CDM_ON_ADDED, onAdded);
		addNonEmptyAttribute(context, parent, CdmConstants.CDM_ON_REMOVED, onRemoved);
		addNonEmptyAttribute(context, parent, CdmConstants.CDM_ON_COMMON, onCommon);
	}
	
	/**
	 * Gets the ColumnHeaders of the Widget. The Widget structure is assumed to be as follows:
	 * 
	 * MainWidget -> Column -> Column Header
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The main Widget being transformed
	 * @return List of ColumnHeader's
	 */
	protected List<Widget> getCdmColumnHeaders(WidgetTransformerContext context, Widget widget) {
		// First find the Columns
		WidgetType cwt = context.getMetaModel().findWidgetType(WidgetLibraryConstants.CDM_COMP, WidgetTypeConstants.CDM_COLUMN);
		List<Widget> columns = WidgetUtils.filter(widget.getContents(), cwt);
		List allChildren = WidgetUtils.getChildren(columns);

		WidgetType chwt = context.getMetaModel().findWidgetType(WidgetLibraryConstants.CDM_COMP, WidgetTypeConstants.CDM_COLUMN_HEADER);
		List<Widget> headers = WidgetUtils.filter(allChildren, chwt);
		return headers;
	}

	/**
	 * Gets a list of ColumnBody where the visible property of the ColumnHeader associated within the Column is set to
	 * true.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @return List<Widget> The list of the ColumnBody
	 */
	protected List<Widget> getVisibleColumnBodies(WidgetTransformerContext context, Widget widget) {
		List<Widget> result = new ArrayList<Widget>();
		List<Widget> columns = getColumns(context, widget);
		for (int i = 0; i < columns.size(); i++) {
			List<Widget> columnChildren = columns.get(i).getContents();
			for (int j = 0; j < columnChildren.size() - 1; j++) {
				Widget header = columnChildren.get(j);
				Widget body = columnChildren.get(j + 1);
				if (header.getPropertyValue(PropertyTypeConstants.VISIBLE).equals("true")) {
					result.add(body);
				}
			}
		}
		return result;
	}

	/**
	 * Gets the Columns of the Widget.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @return List<Widget> The list of the Columns
	 */
	protected List<Widget> getColumns(WidgetTransformerContext context, Widget widget) {
		WidgetType cwt = context.getMetaModel().findWidgetType(WidgetLibraryConstants.CDM_COMP, WidgetTypeConstants.CDM_COLUMN);
		List<Widget> columns = WidgetUtils.filter(widget.getContents(), cwt);
		return columns;
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
		return null;
	}	
}
