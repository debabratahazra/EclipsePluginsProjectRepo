package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.GROUP_SORT_RANK;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.COLUMN_HEADER_INDEX;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_BUILD_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_MUNGE_COLUMNS_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ON_ROW_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_ROW_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TABLE_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TREE_ELEMENT;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;

/**
 * This transforms a table into its UDP and XGui parts. This creates either
 * an XGUI Table or a Tree. The decision is determined by whether at least one of the
 * ColumnHeaders has been grouped or not.
 * 
 * The table transformer will generate an XSP fragment similar this sample:
 * 
 * <pre>
 * &lt;udp:udp model-ref="" sticky="false" view-ref=""&gt;
 *   &lt;udp:build&gt;
 *     &lt;udp:munge-columns&gt;
 *     	 [FOR EACH Column Header]
 *       	&lt;udp:keep&gt;Header&lt;/udp:keep&gt;
 *       [END FOR EACH]
 *     &lt;/udp:munge-columns&gt;
 *     &lt;udp:list/&gt;
 *   &lt;/udp:build&gt;
 *   &lt;udp:render-list&gt;
 *     &lt;xgui:table&gt;
 *       &lt;xgui:columns&gt;
 *         &lt;udp:for-each-column&gt;
 *           &lt;xgui:column&gt;
 *             &lt;xgui:text&gt;
 *               &lt;udp:column-name/&gt;
 *             &lt;/xgui:text&gt;
 *           &lt;/xgui:column&gt;
 *         &lt;/udp:for-each-column&gt;
 *       &lt;/xgui:columns&gt;
 *       &lt;udp:for-each-row&gt;
 *         &lt;xgui:row&gt;
 *           &lt;udp:for-each-item&gt;
 *             &lt;xgui:cell&gt;
 *               &lt;xgui:label&gt;
 *                 &lt;xgui:text&gt;
 *                   &lt;udp:item/&gt;
 *                 &lt;/xgui:text&gt;
 *               &lt;/xgui:label&gt;
 *             &lt;/xgui:cell&gt;
 *           &lt;/udp:for-each-item&gt;
 *         &lt;/xgui:row&gt;
 *       &lt;/udp:for-each-row&gt;
 *     &lt;/xgui:table&gt;
 *   &lt;/udp:render-list&gt;
 * &lt;/udp:udp&gt;
 * </pre>
 * 
 * <pre>
 * &lt;udp:udp model-ref=&quot;&quot; sticky=&quot;false&quot;&gt;
 * 	&lt;udp:build&gt;
 * 		&lt;udp:munge-columns&gt;
 * 			[FOR EACH Column Header]
 * 				&lt;udp:keep&gt;name&lt;/udp:keep&gt; 
 * 			[END FOR EACH]
 * 		&lt;/udp:munge-columns&gt;
 * 						
 * 		&lt;udp:list&gt;
 * 			&lt;udp:sort handle=&quot;s&quot;/&gt;
 * 			[FOR EACH Column Header With Grouping == 'true']
 * 				&lt;udp:group&gt;
 * 					&lt;udp:group-column&gt;address&lt;/udp:group-column&gt;
 * 				&lt;/udp:group&gt;
 * 			[END FOR EACH]
 * 		&lt;/udp:list&gt;
 * 
 * 	&lt;/udp:build&gt;
 * 		&lt;udp:render-list&gt;
 * 			&lt;xgui:tree&gt;
 * 						
 * 			&lt;xgui:columns&gt;
 * 				&lt;udp:for-each-column&gt;
 * 					&lt;xgui:column&gt;
 * 						&lt;xgui:text&gt;&lt;udp:column-name/&gt;&lt;/xgui:text&gt;
 * 					&lt;/xgui:column&gt;
 * 				&lt;/udp:for-each-column&gt;
 * 			&lt;/xgui:columns&gt;			
 * 
 * 			&lt;udp:recurse-data&gt;
 * 				&lt;xgui:row&gt;
 * 					&lt;udp:for-each-item&gt;
 * 						&lt;xgui:cell&gt;&lt;xgui:label&gt;&lt;xgui:text&gt;&lt;udp:item/&gt;&lt;/xgui:text&gt;&lt;/xgui:label&gt;&lt;/xgui:cell&gt;
 * 					&lt;/udp:for-each-item&gt;
 * 					&lt;udp:on-row&gt;
 * 						&lt;xgui:row&gt;
 *  							&lt;udp:for-each-item&gt;  
 *  				   			&lt;xgui:cell&gt;
 *  				   				&lt;xgui:label&gt;&lt;xgui:text&gt;&lt;udp:item/&gt;&lt;/xgui:text&gt;&lt;/xgui:label&gt;
 *  				   			&lt;/xgui:cell&gt;		
 *  				   		&lt;/udp:for-each-item&gt;		
 *  				  	&lt;/xgui:row&gt;			
 * 					&lt;/udp:on-row&gt;
 * 				&lt;/xgui:row&gt;
 * 			&lt;/udp:recurse-data&gt;					
 * 			
 * 		&lt;/xgui:tree&gt;
 * 	&lt;/udp:render-list&gt;			
 * &lt;/udp:udp&gt;	
 * </pre>
 * 
 * @author Gary Hayes
 * @author Alexandre Jaquet
 */
public class TableWidgetTransformer extends BaseWidgetTransformer {	

	/**
	 * Creates a new TableWidgetTransformer.
	 * 
	 * @param type the widget type
	 */
	public TableWidgetTransformer(WidgetType type) {
		super(type);	
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		List<Widget> columnHeaders = getColumnHeaders(context, widget);
		List<Widget> sortedColumnHeaders = getSortedColumnHeaders(context, widget, columnHeaders);
		List<Widget> visibleColumnHeaders = getVisibleColumnHeaders(context, widget, columnHeaders);
		int indexOfFirstVisibleColumn = getIndexOfFirstVisibleColumns(context, sortedColumnHeaders);
		
		Element udp = buildUdp(context, widget);
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
		Element mungeColumns = appendElement(context, build, UDP_NAMESPACE_URI, UDP_MUNGE_COLUMNS_ELEMENT);
		buildUdpKeepOrComputeElements(context, mungeColumns, widget, sortedColumnHeaders);
		Element list = buildUdpList(context, build, widget);
		
		buildUdpSort(context, widget, list, columnHeaders);
		
		buildUdpDumpModel(context, widget, build);
		
		boolean isGrouped = isGrouped(widget);
		if (isGrouped) {
			buildUdpGroups(context, list, columnHeaders);
		}
		
		Element renderListParent = udp;
		if (context.isRootUDPOpen()) {
			renderListParent = context.getParentElement();
		}
		
		Element renderList = buildUdpRenderList(context, renderListParent, widget);
		Element oldParent = context.setParentElement(renderList);
		buildLogicNotEmpty(context, widget);
		context.setParentElement(oldParent);
		
		if (isGrouped) {
			Element tree = appendElement(context, renderList, XGUI_NAMESPACE_URI, XGUI_TREE_ELEMENT);
	        // OCS-26876 - Add ID tag in table/tree
			String id = UniqueIdGenerator.generateId(widget);
			addNonEmptyAttribute(context, tree, "id", id);

			// Set the parent to Tree
			oldParent = context.setParentElement(tree);
			if (showColumnHeaders(widget)) {
				buildXGuiColumns(context, visibleColumnHeaders, indexOfFirstVisibleColumn);
			}
			Element recurseData = buildUdpRecurseData(context, tree, widget);
			// Set the parent to recurse data
			context.setParentElement(recurseData);
			buildXGuiRows(context, widget, indexOfFirstVisibleColumn);
		} else {			
			Element table = appendElement(context, renderList, XGUI_NAMESPACE_URI, XGUI_TABLE_ELEMENT);		
	        // OCS-26876 - Add ID tag in table/tree
			String id = widget.getID();
			if (StringUtils.isEmpty(id)) {
				id = UniqueIdGenerator.generateId(widget);
			}
			addNonEmptyAttribute(context, table, "id", id);

	        oldParent = context.setParentElement(table);
			if (showColumnHeaders(widget)) {
				buildXGuiColumns(context, visibleColumnHeaders, indexOfFirstVisibleColumn); 
			}
			buildUdpRows(context, widget, indexOfFirstVisibleColumn);
		}
		
		
		context.setParentElement(oldParent);
		buildLogicNotEmptyClosingTag(context, widget,renderList);
		if (hasPageSize(context, widget)) {
			buildUdpPage(context, list, widget);
			buildTableFooter(context, widget, renderList);
		}	
	}		

	/**
	 * Returns true if this Table should be displayed as a Tree. This is true if at least one of the ColumnBodies
	 * grouping properties is true.
	 * 
	 * @param table The Table
	 * @return boolean Returns true if this Table should be displayed as a Tree
	 */
	protected boolean isGrouped(Widget table) {
		for (Widget column : table.getContents()) {
			Widget header = column.getContents().get(COLUMN_HEADER_INDEX);
			String rankStr = header.getPropertyValue(GROUP_SORT_RANK);
			if (! StringUtils.isEmpty(rankStr)) {
				int rank = Integer.parseInt(rankStr);
				if (rank > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Builds the %lt;udp:row&gt; section.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to transform
	 * @param indexOfFirstVisibleColumn The index of the first visible column
	 * @exception CoreException
	 */
	private void buildXGuiRows(WidgetTransformerContext context, Widget widget, int indexOfFirstVisibleColumn) throws CoreException {
		Element row = appendElement(context, XGUI_NAMESPACE_URI, XGUI_ROW_ELEMENT);	
		
		buildUdpForEachItem(context, row, widget, indexOfFirstVisibleColumn);
		
		Element onRow = appendElement(context, row, UDP_NAMESPACE_URI, UDP_ON_ROW_ELEMENT);
		Element innerRow = appendElement(context, onRow, XGUI_NAMESPACE_URI, XGUI_ROW_ELEMENT);
		
		buildUdpForEachItem(context, innerRow, widget, indexOfFirstVisibleColumn);
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
