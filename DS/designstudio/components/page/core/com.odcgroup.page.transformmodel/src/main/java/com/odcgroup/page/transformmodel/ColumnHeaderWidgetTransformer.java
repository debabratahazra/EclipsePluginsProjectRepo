package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.IS_SORTABLE;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_COLUMN_NAME_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_MODEL_REF;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_COLUMN_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TEXT_ELEMENT;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transformer for the widget ColumnHeader, this is used by the TableWidgetTransformer.
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class ColumnHeaderWidgetTransformer extends BaseWidgetTransformer  {
	
	
	/**
	 * Constructor
	 * 
	 * @param type 
	 * 			The widget type
	 */
	public ColumnHeaderWidgetTransformer(WidgetType type) {
		super(type);
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		// do nothing
	}

	/**
	 * Builds the column section
	 * 
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 * @return Element return the column
	 */
	protected Element buildColumn (WidgetTransformerContext context, Widget widget) {
		Element column = createElement(context, XGUI_NAMESPACE_URI, XGUI_COLUMN_ELEMENT);
		Element text = createElement(context, XGUI_NAMESPACE_URI, XGUI_TEXT_ELEMENT);
		Element udpColumnName = createElement(context, UDP_NAMESPACE_URI, UDP_COLUMN_NAME_ELEMENT);
		column.appendChild(text);
		text.appendChild(udpColumnName);
		TransformUtils.appendChild(context, column);
		if (hasSorter(context, widget)) {
			buildLogicSortColumn(context, widget, column);
		}
		return column;
	}
	
	/** 
	 * Builds the %lt;logic&gt; section who contain sorter.
	 * 
	 * @param context The widget transformer context
	 * @param widget The widget to transform
	 * @param parent The parent element
	 */
	private void buildLogicSortColumn(WidgetTransformerContext context, Widget widget, Element parent) {
		Element xspLogic = appendElement(context, parent, XSP_NAMESPACE_URI , TransformerConstants.LOGIC_ELEMENT_NAME);
		String modelRefValue = widget.getPropertyValue(UDP_MODEL_REF);
		StringBuffer content = new StringBuffer(1024);
		List<Integer> acceptedIndex = getAcceptedIndexes(context, widget);		
		for (int i = 0; i < acceptedIndex.size();i++) {
			int index = acceptedIndex.get(i);
			if (i == 0) {
				content.append("if (<udp:column-index/> ==");
				content.append(index);
			}else {
				content.append(" || <udp:column-index/> == ");
				content.append(index);
			}
			if (i == acceptedIndex.size() -1) {
				content.append(" ){\n");
			}
		}
		content.append("if (<udp:is-sorting-column/>) {\n");
		content.append(" if (<udp:is-sort-reversed/>) {\n");
		content.append("    <xsp:attribute name=\"sorting\">descending</xsp:attribute>\n");
		content.append("    <xsp:attribute name=\"action\">post('<pageflow:continuation/>','','','flow-action=reload,<scope:get-module-rank/>s=<udp:column-name/>,");
		content.append(modelRefValue);
		content.append("=<bean:value-of key=\"");
		content.append(modelRefValue);
		content.append("\"/>,scope-id=<scope:get-id/>')</xsp:attribute>\n");
		content.append("} else {");
		content.append("    <xsp:attribute name=\"sorting\">ascending</xsp:attribute>\n");
		content.append("    <xsp:attribute name=\"action\">post('<pageflow:continuation/>','','','flow-action=reload,<scope:get-module-rank/>s=-<udp:column-name/>,");
		content.append(modelRefValue);
		content.append("=<bean:value-of key=\"");
		content.append(modelRefValue);
		content.append("\"/>,scope-id=<scope:get-id/>')</xsp:attribute>\n");
		content.append("  }\n");
		content.append("} else {\n");
		content.append("    <xsp:attribute name=\"sorting\">none</xsp:attribute>\n");
		content.append("    <xsp:attribute name=\"action\">post('<pageflow:continuation/>','','','flow-action=reload,<scope:get-module-rank/>s=<udp:column-name/>,");
		content.append(modelRefValue);
		content.append("=<bean:value-of key=\"");
		content.append(modelRefValue);
		content.append("\"/>,scope-id=<scope:get-id/>')</xsp:attribute>\n");
		content.append("   }}");
		xspLogic.setTextContent(content.toString());
	}
	
	/**
	 * Gets the list of the accepted index.
	 * 
	 * @param context	
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget 	
	 * @return List
	 * 			The list of accepted index
	 * 
	 */
	private List<Integer> getAcceptedIndexes (WidgetTransformerContext context, Widget widget) {
		List<Widget> columnHeaders = getColumnHeaders(context, widget);
		int index = 0;
		List<Integer> acceptedIndex = new ArrayList<Integer>();
		for (Widget columnHeader : columnHeaders) {
			String sortColumn = columnHeader.getPropertyValue(IS_SORTABLE);
			if ("true".equals(sortColumn)) {
				acceptedIndex.add(index);
			}
			index++;
		}
		return acceptedIndex;
	}

	/**
	 * Does our Table contain sorter ?
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 * @return boolean
	 * 			return true if the Table contain a sorter
	 */	
	private boolean hasSorter(WidgetTransformerContext context, Widget widget) {
		List<Widget> headers = getColumnHeaders(context, widget);
		boolean result = false;		
		for (Widget header : headers) {
			if (header.getPropertyValue(PropertyTypeConstants.IS_SORTABLE).equals("true")) {
				return true;
			}
		}		
		return result;
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
		return createElement(context, XGUI_NAMESPACE_URI, XGUI_COLUMN_ELEMENT);
	}	
}
