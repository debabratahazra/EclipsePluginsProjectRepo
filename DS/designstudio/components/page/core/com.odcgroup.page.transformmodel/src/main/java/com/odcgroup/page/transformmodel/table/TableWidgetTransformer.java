package com.odcgroup.page.transformmodel.table;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableExtra;
import com.odcgroup.page.model.widgets.table.ITableFeature;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.model.widgets.table.ITableUtilities;
import com.odcgroup.page.model.widgets.table.TableGroupRankSorter;
import com.odcgroup.page.model.widgets.table.TableSortSorter;
import com.odcgroup.page.transformmodel.BaseWidgetTransformer;
import com.odcgroup.page.transformmodel.PropertyTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.util.AttributeRenderer;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This transformer transforms a Table/Tree Widget into xgui/udp
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableWidgetTransformer extends BaseWidgetTransformer {

	/** begin of the xml root element */
	private static final String BEGIN_ROOT_NODE = "<root " + "xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" "
			+ "xmlns:i18n=\"http://apache.org/cocoon/i18n/2.1\" "
			+ "xmlns:ic=\"http://www.odcgroup.com/uif/inputcontrol/0.1\" "
			+ "xmlns:scope=\"http://www.odcgroup.com/uif/scope/0.1\" "
			+ "xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" "
			+ "xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" "
			+ "xmlns:xsp=\"http://apache.org/xsp\" language=\"java\" "
			+ "xmlns:pageflow=\"http://www.odcgroup.com/uif/pageflow-info/0.1\" "
			+ ">";

	/** end of the xml root element */
	private static final String END_ROOT_NODE = "</root>";

	/** end of line */
	private static final String EOL = System.getProperty("line.separator");
	
	
	/**
	 * Appends a table footer
	 */
	private final void renderTableFooter(StringBuilder script, ITable table) {
		if (tableHasPagination(table)) {
			if (isDetailedDelegatedRenderingMode(table) && tableCountTreeElementsByLeaf(table)) {
				script.append("<xsp:logic>if(<udp:leaf-row-count/> &gt; 0) { </xsp:logic>");
			} else {
				script.append("<xsp:logic>if(<udp:grouped-row-count/> &gt; 0) { </xsp:logic>");
			}
			script.append("<xgui:tablefooter>");
			script.append("<xsp:attribute name=\"ref\">");
			script.append(table.getID());
			script.append("</xsp:attribute>");
			
			if (isDetailedDelegatedRenderingMode(table) && tableCountTreeElementsByLeaf(table)) {
				script.append("<xsp:attribute name=\"itemfirst\"><udp:first-visible-leaf-index/></xsp:attribute>");
				script.append("<xsp:attribute name=\"itemlast\"><udp:last-visible-leaf-index/></xsp:attribute>");
				script.append("<xsp:attribute name=\"itemtot\"><udp:leaf-row-count/></xsp:attribute>");
			} else {
				script.append("<xsp:attribute name=\"itemfirst\"><udp:first-visible-index/></xsp:attribute>");
				script.append("<xsp:attribute name=\"itemlast\"><udp:last-visible-index/></xsp:attribute>");
				script.append("<xsp:attribute name=\"itemtot\"><udp:grouped-row-count/></xsp:attribute>");
			}
			script.append("<xsp:attribute name=\"page\"><udp:current-page/></xsp:attribute>");
			script.append("<xsp:attribute name=\"pagelast\"><udp:last-page/></xsp:attribute>");
			script.append("</xgui:tablefooter>");
			script.append("<xsp:logic>}</xsp:logic>");
		}		
	}

	/**
	 * @param table
	 * @param column
	 * @return true if at least one aggregate exist on this column
	 */
	@SuppressWarnings("unused")
	private boolean isAggregatedColumn(ITable table, ITableColumn column) {
		String columnName = column.getColumnName();
		for (ITableAggregate aggregate : table.getAggregatedColumns()) {
			if (columnName.equals(aggregate.getColumnName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param table
	 * @param column
	 * @param groupName
	 * @return ITableAggregate
	 */
	private boolean hasAggregate(ITable table, ITableColumn column, String groupName) {
		String columnName = column.getColumnName();
		for (ITableAggregate aggregate : table.getAggregatedColumns()) {
			if (columnName.equals(aggregate.getColumnName())) {
				String[] groupNames = aggregate.getGroupNames();
				for (int gx = 0; gx < groupNames.length; gx++) {
					if (groupName.equals(groupNames[gx])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param table
	 * @param groupName
	 * @return ITableColumn
	 */
	private ITableColumn getGroupedColumn(ITable table, String groupName) {
		for (ITableColumn column : table.getColumns()) {
			if (groupName.equals(column.getColumnName()))
				return column;
		}
		return null;
	}
	
	/**
	 * @param table
	 *            the table
	 * 
	 * @return {@code true} if the table has a fixed size
	 */
	private final boolean hasTableFixedSize(ITable table) {
		Property p = table.getFixedPageSize();
		return (p != null) && p.getBooleanValue();
	}
	
	/**
	 * @param table
	 * @return boolean
	 */
	private final boolean isDetailedDelegatedRenderingMode(ITable table) {
		ITableUtilities utils = TableHelper.getTableUtilities();
		return utils.isDetailedDelegatedRenderingMode(table.getRenderingMode());
	}	
	
	/**
	 * @param table
	 * @return boolean
	 */
	private final boolean isDetailedTreeView(ITable table) {
		return !isSummaryTreeView(table);
	}

	/**
	 * @param table
	 * @return boolean
	 */
	private final boolean isSummaryTreeView(ITable table) {
		ITableUtilities utils = TableHelper.getTableUtilities();
		return utils.isSummaryRenderingMode(table.getRenderingMode());
	}

	/**
	 * @param script
	 * @param name
	 * @param value
	 */
	private final void renderAttribute(StringBuilder script, String name, boolean value) {
		renderAttribute(script, name, (value) ? "true" : "false");
	}

	/**
	 * @param script
	 * @param name
	 * @param value
	 */
	private final void renderAttribute(StringBuilder script, String name, int value) {
		renderAttribute(script, name, value + "");
	}

	/**
	 * @param script
	 * @param name
	 * @param property
	 */
	private final void renderAttribute(StringBuilder script, String name, Property property) {
		if (property != null) {
			String value = property.getValue();
			if (StringUtils.isNotEmpty(value) || StringUtils.isNotBlank(value)) {
				if (!value.equals(property.getType().getDefaultValue())) {
					renderAttribute(script, name, value.trim());
				}
			}
		}
	}

	/**
	 * @param script
	 * @param name
	 * @param value
	 */
	private final void renderAttribute(StringBuilder script, String name, String value) {
		AttributeRenderer.getInstance(script).render(name, value);
	}

	/**
	 * @param context
	 * @param script
	 * @param widget
	 */
	private final void renderAttributeText(WidgetTransformerContext context, StringBuilder script, Widget widget) {
		ITranslation translation = context.getTranslation(widget);
		if(!translation.messagesFound(ITranslationKind.NAME)) {
			return;
		}
		String key = context.getTranslationKey(widget, ITranslationKind.NAME);
		if (key != null) {
			script.append("<xgui:text");
//			if (translation.messagesFound(ITranslationKind.NAME, true)) {
//				script.append(" encode=\"false\"");
//				// add CSS class "richtext" on the parent element.
//			}
			script.append(">");
			script.append("<i18n:text>");
			script.append(key);
			script.append("</i18n:text>");
			script.append("</xgui:text>");
		}
	}
	
	/**
	 * @param script
	 * @param table
	 */
	private final void renderCollapseAttribute(StringBuilder script, ITable table) {
		int groupDepth = 1;
		// DS-2970 getGroupsByRank - in ascending rank order
		List<ITableGroup> groups = table.getGroupsByRank();
		int max = groups.size();
		if (isDynamicTable(table)) {
			max -= 1;
		}
		for (ITableGroup group : groups) {
			if (group.isUsedForDynamicColumn()) {
				continue;
			}
			script.append("<xsp:logic>");
			if (groupDepth > 1) {
				script.append("} else ");
			}
			script.append("if (groupDepth==" + groupDepth + ") {");
			script.append("</xsp:logic>");
			script.append("<xsp:content><xsp:attribute ");
			script.append("name=\"expanded\">");
			script.append(!group.isCollapsed());
			script.append("</xsp:attribute></xsp:content>");
			groupDepth++;
			if (groupDepth > max) {
				script.append("<xsp:logic>}</xsp:logic>");
			}
		}
		
	}

	/**
	 * @param context 
	 * @param script
	 * @param widget
	 */
	private final void renderAttributeToolTip(WidgetTransformerContext context, StringBuilder script, Widget widget) {
		ITranslation translation = context.getTranslation(widget);
		if(!translation.messagesFound(ITranslationKind.TEXT)) {
			return;
		}
		String key = context.getTranslationKey(widget, ITranslationKind.TEXT);
		if (key != null) {
			script.append("<xgui:tooltip>");
			script.append("<i18n:text>");
			script.append(key);
			script.append("</i18n:text>");
			script.append("</xgui:tooltip>");
		}
		
	}
	
	/**
	 * @param script
	 * @param table
	 * @param isTable
	 */
	private void renderTableTreeElement(StringBuilder script, ITable table, boolean isTable, boolean emptyContent) {
		
		if (!emptyContent) {
			String bd = getBeanDefinePropertyValue(table);
			if (table.isEditable() && StringUtils.isNotBlank(bd)) {
				script.append("<xsp:logic>{ boolean updateMode=(!<bean:is-true key=\""+bd+".updateMode\"/>); " +
						"String tableMode=(updateMode)?\"\":\"edit\";</xsp:logic>");
			}
		}

		if (isTable) {
			script.append("<xgui:table");
		} else {
			script.append("<xgui:tree");
		}
		
		renderAttribute(script, "id", table.getWidget().getID());
		
		//DS-3144 enabled the class attribute
		renderAttribute(script, "class", table.getCssClass());		
		
		//DS-2534 - highlight row attribute
		//DS-2743 - applicable for both table/tree
		//DS-7400 - highlight row attribute default true 
		if (!table.isRowHighLighted()) {
			renderAttribute(script, "rhl", "false");
		}

		if (!isTable) {
			// DS-2288 - expand only valid for <xgui:tree>
			renderAttribute(script, "expand-method", table.getExpandLevel());
			//DS-2844 -show number of items attribute.if num item is true
			if(table.isNumItems()){
			renderAttribute(script,"showNumItems", table.isNumItems());
			}
			
		}
		
		if (hasTableFixedSize(table)) {
			renderAttribute(script, "type", "delimited");
			renderAttribute(script, "height", table.getHeight());
			renderAttribute(script, "width", table.getWidth());
		}
		script.append(">");
		
		// disable sorting/pagination when table is edited.
		if (!emptyContent) {
			if (table.isEditable()) {
				String bd = getBeanDefinePropertyValue(table);
				if (StringUtils.isNotEmpty(bd)) {
					script.append("<xsp:attribute name=\"sortable\"><xsp:expr>updateMode</xsp:expr></xsp:attribute>");
					script.append("<xsp:attribute name=\"pagination\"><xsp:expr>updateMode</xsp:expr></xsp:attribute>");
					script.append("<xsp:attribute name=\"mode\"><xsp:expr>tableMode</xsp:expr></xsp:attribute>");
				}
			}
		}
		
	}

	/**
	 * @param context
	 * @param script
	 * @param widget
	 * @exception CoreException
	 */
	private final void renderEvents(WidgetTransformerContext context, StringBuilder script, Widget widget) throws CoreException {
		Element dummy = context.getDocument().createElement("dummy");
		Element oldParent = context.setParentElement(dummy);
		transformEvents(context, widget);
		NodeList nodeList = dummy.getChildNodes();
		for (int kx = 0; kx < nodeList.getLength(); kx++) {
			script.append(TransformUtils.transformDomNodeToXML(nodeList.item(kx)));
		}
		context.setParentElement(oldParent);
	}	
	
	/**
	 * @param context
	 * @param column
	 * @param script
	 * @param reactToMainCheckbox
	 * @throws CoreException
	 */
	private void renderCellData(WidgetTransformerContext context, ITableColumn column, 
			StringBuilder script, boolean reactToMainCheckbox) throws CoreException {
		context.setReactToMainCheckbox(reactToMainCheckbox);
		for (Widget child : column.getWidget().getContents()) {
			String typeName = child.getTypeName();
			boolean renderChildren = true;
			if (column.getCheckbox() != null && column.isPlaceHolder() && !column.isDisplayGrouping()) {
				if (WidgetTypeConstants.CHECKBOX.equals(typeName) 
						|| WidgetTypeConstants.CONDITIONAL.equals(typeName)) {
					Property chkBxGrp = column.getCheckbox().findProperty("checkbox-group-names");
					String groupnames = chkBxGrp != null ? chkBxGrp.getValue().trim() : "";
					if (!reactToMainCheckbox && StringUtils.isEmpty(groupnames)) { 
						renderChildren = false;
					}
				}
			}
			if (renderChildren) {
				Element dummy = context.getDocument().createElement("dummy-cell");
				Element oldParent = context.setParentElement(dummy);
				WidgetTransformer childTransformer = context.getTransformModel().findWidgetTransformer(child);
				childTransformer.transform(context, child);
				NodeList nodeList = dummy.getChildNodes();
				for (int kx = 0; kx < nodeList.getLength(); kx++) {
					String xmlString = TransformUtils.transformDomNodeToXML(nodeList.item(kx)); 
					script.append(xmlString);
				}
				context.setParentElement(oldParent);
			}
		}
	}
	
	/**
	 * @param column
	 * @return
	 */
	private ITableColumnItem getMatchingItem(ITableColumn column) {
		List<ITableColumnItem> items = column.getItems();
		String columnName = column.getColumnName();
		if (!StringUtils.isEmpty(columnName)) {
			for (ITableColumnItem item : items) {
				if(item.getColumn().equals(columnName)) {
					return item;
				}
			}
			if (!items.isEmpty()) {
				return items.get(0);
			}
		}
		return null;
	}

	/**
	 * @param context 
	 * @param column
	 * @param script
	 * @param table 
	 */
	private void renderColumnHeader(WidgetTransformerContext context, ITableColumn column, StringBuilder script, ITable table) {

		if (column.isPlaceHolder() && column.isNeverVisible()) {
			return;
		}
		
		String name = column.getColumnName();

		boolean isDynamicColumn = column.isDynamic();
		if (isDynamicColumn) {
			script.append("<udp:dynamic-columns");
			String columnId=column.getId().getValue();
			renderAttribute(script, "dynamic-columns-ref", table.getModelReference()+"."+columnId);
			script.append(">");
			name = column.getItems().get(0).getColumn();
		}
		// sorting logic
		if ( !column.isNeverVisible() && !column.isPlaceHolder()) {
			script.append("<xsp:logic>" + EOL);
			script.append("sorting=\"none\";" + EOL);
			String bd = getBeanDefinePropertyValue(table);
			script.append("if (<udp:is-sorting-column column=\"" + name + "\"/>) {" + EOL);
			script.append("  if (<udp:is-sort-reversed column=\"" + name + "\"/>) {" + EOL);
			script.append("    sorting=\"descending\";" + EOL);
			script.append("  } else {" + EOL);
			script.append("    sorting=\"ascending\";" + EOL);
			script.append("  }" + EOL);
			script.append("}" + EOL);
			script.append("</xsp:logic>");
		}
		// table/tree column
		if (!column.isNeverVisible() || column.isPlaceHolder()) {
			
			script.append("<xgui:column");
			Property property = column.getVisibility();
			String value = property.getValue();
			boolean val = true;
			if ("not-visible".equals(value)) {
				val = false;
			} else {
				val = true;
			}
			renderAttribute(script, "enabled", val);
			renderAttribute(script, "locked", column.getLocked());
			renderAttribute(script, "maxChar", column.getMaxCharacters());
			//DS-5273 nowrap=true if wrap=false.
			Property wrap=column.getWrap();
			if(wrap !=null && !wrap.getBooleanValue()){
				renderAttribute(script, "nowrap", true);
			}
			
// DS-3025	renderAttribute(script, "type", column.getDisplayType());
			renderAttribute(script, "width", column.getWidth());
			renderAttribute(script, "xwidth", column.getXWidth());
			if (column.isDisplayGrouping()) {
				renderAttribute(script, "tec", "true");				
			}
			script.append(">");
			
			String tableId = table.getWidget().getID();
			if (column.isDynamic()) {
				script.append("<xsp:attribute name=\"id\">" + tableId+".<udp:current-dynamic-column/></xsp:attribute>");
			} else {
				script.append("<xsp:attribute name=\"id\">" + tableId+"."+column.getId().getValue() + "</xsp:attribute>");
			}
			
			if (!column.isPlaceHolder() && column.isSortable()) {
				script.append("<xsp:attribute name=\"name\"><udp:column-name column=\"" + name + "\"/></xsp:attribute>");
			}
			
			//DS-3466
			ITableColumnItem item = getMatchingItem(column);
			if (item != null) {
				boolean enumType = isEnum(context, item);
				String halign = item.getHorizontalAlignment();
				if (StringUtils.isEmpty(halign)|| ("column".equals(halign) && !enumType)) {
					if (!column.isPlaceHolder()) {
						script.append("<xsp:attribute name=\"halign\"><udp:column-alignment column=\"" + name + "\"/></xsp:attribute>");					
					}
				} else if(!StringUtils.isEmpty(halign) && "column".equals(halign) && enumType) { // DS-6977
					script.append("<xsp:attribute name=\"halign\">lead</xsp:attribute>");	
				} else if(!StringUtils.isEmpty(halign) && !"column".equals(halign)){
					script.append("<xsp:attribute name=\"halign\">"+halign+"</xsp:attribute>");				
				}
			}
			
			if (!column.isPlaceHolder()) {
				script.append("<xsp:attribute name=\"type\"><udp:column-type column=\"" + name + "\"/></xsp:attribute>");
				script.append("<xsp:attribute name=\"sorting\"><xsp:expr>sorting</xsp:expr></xsp:attribute>");
			} else {
				script.append("<xsp:attribute name=\"type\">"+column.getDisplayType().getValue()+"</xsp:attribute>");
			}

//			DS-2754
//			if (column.isCheckboxHeader()) {
//				script.append("<xgui:checkbox>");
//				script.append("<xgui:onevent>");
//				script.append("<xgui:checkboxCheck group-ref=\"ReactToMainCheckBox\"/>");
//				script.append("</xgui:onevent>");
//				script.append("</xgui:checkbox>");
//			}
			
			Widget widget = column.getWidget();
			if (column.isDynamic()) {
				script.append("<xgui:text>");
				script.append("<udp:current-dynamic-column/>");
				script.append("</xgui:text>");
			} else {
				renderAttributeText(context, script, widget);
			}
			renderAttributeToolTip(context, script, widget);
			script.append("</xgui:column>");
		}

		if (isDynamicColumn) {
			script.append("</udp:dynamic-columns>");
		}
	}

	/**
	 * Renders the columns header according to the rendering mode of the table.
	 * 
	 * @param context
	 * @param table
	 *            the table
	 * @param script
	 *            the xml script
	 */
	private void renderColumnsHeader(WidgetTransformerContext context, ITable table, StringBuilder script) {

		// declare the columns header
		script.append("<xgui:columns>");
		script.append("<xsp:logic>");
		script.append(EOL + "String sorting = \"\";" + EOL);
		script.append("</xsp:logic>");		

		// render the columns header
		for (ITableColumn column : table.getColumns()) {
			renderColumnHeader(context, column, script, table);
		}

		script.append("</xgui:columns>");
	}	

	/**
	 * Generates a UDP script to declare keep-filters
	 * 
	 * @param table
	 * @param script
	 */
	private void renderDeclareKeepFilters(ITable table, StringBuilder script) {
		
		boolean detailedRenderingMode = isDetailedDelegatedRenderingMode(table);
		
		List<ITableKeepFilter> list = table.getKeepFilters();
		if (list.size() > 0) {
			script.append("<udp:keep-filter>");
			String logic = table.getKeepFilterLogic().getValue();
			boolean isAnd = "and".equalsIgnoreCase(logic);
			script.append((isAnd) ? "<udp:and>" : "<udp:or>");
			for (ITableKeepFilter filter : list) {
				script.append("<udp:compare");
				// column
				renderAttribute(script, "column", filter.getColumnName());
				// operations
				renderAttribute(script, "operation", filter.getOperator());
				script.append(">");
				// operand
				script.append(filter.getOperand());
				script.append("</udp:compare>");
			}
			script.append((isAnd) ? "</udp:and>" : "</udp:or>");
			
			// DS-2382 - begin
			//if (hasGroups(table) && isDetailedTreeView(table)) {
			if (detailedRenderingMode || isSummaryTreeView(table)) {
				script.append("<udp:handle><scope:get-module-rank/>f</udp:handle>");	
			}
			// DS-2382 - end
			
			script.append("</udp:keep-filter>");
		}/* else {
			// DS-2382 - begin
			// generate handler even if no keep filters are defined
			//if (hasGroups(table) && isDetailedTreeView(table)) {
			if (detailedRenderingMode) {
				script.append("<udp:keep-filter>");
				script.append("<udp:handle><scope:get-module-rank/>f</udp:handle>");	
				script.append("</udp:keep-filter>");
			}
			// DS-2382 - end
		}*/ //DS-3277 - no handler required if no user-defined filters found

		// DS-2754 - multi selection activation - begin
		List<ITableColumn> columns = table.getColumns();
		for (ITableColumn column : columns) {
			if (column.isPlaceHolder() && !column.isDisplayGrouping()){
				Widget widget = column.getCheckbox();
				if (widget != null) {
					String action = widget.getPropertyValue("column-checkbox-action");
					script.append("<udp:multi-selection");
					renderAttribute(script, "column-name", action);
					script.append(">");
					script.append("<udp:handle>"+table.getID()+"_"+action+"</udp:handle>");	
					Property identifier = widget.findProperty("column-checkbox-identifier");
					if (identifier != null && StringUtils.isNotEmpty(identifier.getValue())) {
						script.append("<udp:column-id>");
						script.append(identifier.getValue().trim());
						script.append("</udp:column-id>");
					}
					Property security = widget.findProperty("column-checkbox-security");
					if (security != null && StringUtils.isNotEmpty(security.getValue())) {
						script.append("<udp:column-filter>" );
						script.append(security.getValue().trim());
						script.append("</udp:column-filter>");
					}
					script.append("</udp:multi-selection>");
				}
			}
		}
		// DS-2754 - multi selection activation - end
		
	}

	/**
	 * Generates an UDP script to declare munge columns.
	 * 
	 * @param table
	 * @param script
	 * @param retainedColumnNames
	 */
	private void renderDeclareMungeColumns(ITable table, StringBuilder script, Set<String> retainedColumnNames) {

		script.append("<udp:munge-columns>");

		boolean isTreeView = table.hasGroups();
		boolean isDetailedTreeView = isTreeView && isDetailedTreeView(table);

		Set<String> alreadyVisited = new HashSet<String>();
	
		// keep drag & dropped columns (order not important
		for (ITableColumn column : table.getColumns()) {
			List<ITableColumnItem> items = column.getItems();
			for (ITableColumnItem item : items) {
				String columnName = item.getColumn();
				if ((retainedColumnNames == null) || retainedColumnNames.contains(columnName)) {
					if (StringUtils.isNotEmpty(columnName) 
							&& column.isBoundToDomain() 
							&& !alreadyVisited.contains(columnName)) {
						alreadyVisited.add(columnName);
						script.append("<udp:keep");
						String displayFormat = item.getDisplayFormat();
						renderAttributeForDisplayFormats(script, displayFormat);
						script.append(">");
						script.append(columnName);
						script.append("</udp:keep>");
					}
				}
			}
		}	
		// DS-4520
		for (ITableColumn column : table.getColumns()) {
			String columnName = column.getColumnName();
			if ((retainedColumnNames == null) || retainedColumnNames.contains(columnName)) {
				if (StringUtils.isNotEmpty(columnName) 
						&& column.isBoundToDomain() 
						&& !alreadyVisited.contains(columnName)) {
					alreadyVisited.add(columnName);
					script.append("<udp:keep");
					script.append(">");
					script.append(columnName);
					script.append("</udp:keep>");
				}
			}
		}
		
		boolean summaryView = isSummaryTreeView(table);
		// keep grouped columns
		if (isTreeView) {
			for (ITableGroup group : table.getGroups()) {
				//DS-5299
			    Property displayFormat = group.getDisplayFormat();
				String columnName = group.getColumnName();
				if ((retainedColumnNames == null) || retainedColumnNames.contains(columnName) || displayFormat != null) {
					renderMungeColumn(columnName, alreadyVisited, script, displayFormat);
				}
				//if(summaryView) { // also render munge for the group - sorting column DS-2710
					String sortCol = group.getSortingColumnName();
					if ((retainedColumnNames == null) || retainedColumnNames.contains(sortCol)) {
						renderMungeColumn(sortCol, alreadyVisited, script);
					}
				//}
			}
		}

		// keep sorting columns
		for (ITableSort sort : table.getSorts()) {
			String columnName = sort.getColumnName();
			ITableColumn column = table.getColumn(columnName);
			if (column != null && column.isComputed()) { //DS-4287
				continue;
			}
			if ((retainedColumnNames == null) || retainedColumnNames.contains(columnName)) {
				renderMungeColumn(columnName, alreadyVisited, script);
			}
		}

		// keep group sorting column
		if (isDetailedTreeView) {
			for (ITableGroup group : table.getGroups()) {
				String columnName = group.getColumnName();
				if ((retainedColumnNames == null) || retainedColumnNames.contains(columnName)) {
					renderMungeColumn(columnName, alreadyVisited, script);
				}
			}
		}
		
		// table aggregate is bound to domain
		List<String> domainAttributes = getDomainAttributes(table);
		for (ITableAggregate aggregate : table.getAggregatedColumns()) {
			String aggColumn = aggregate.getColumnName();
			if (!StringUtils.isEmpty(aggColumn) && domainAttributes.contains(aggColumn)) {
				renderMungeColumn(aggColumn, alreadyVisited, script);			
			}
		}

		// keep aggregate weighted-mean extra column
		if (isTreeView) {
			for (ITableAggregate aggregate : table.getAggregatedColumns()) {
				String otherColumnName = aggregate.getOtherColumnName();
				if ((retainedColumnNames == null) || retainedColumnNames.contains(otherColumnName)) {
					// do not render it, if the column name is a computation (make-amount,etc..)
					boolean isComputedColumn = false;
					for (ITableColumn column : table.getColumns()) {
						if (column.isComputed()) {
							if (otherColumnName.equals(column.getColumnName())) {
								isComputedColumn = true;
								break;
							}
						}
					}
					if (!isComputedColumn) {
						renderMungeColumn(otherColumnName, alreadyVisited, script);
					}
				}
			}
		}

		// keep extra columns
		for (ITableFeature extra : table.getExtras()) {
			String columnName = extra.getColumnName();
			if ((retainedColumnNames == null) || retainedColumnNames.contains(columnName)) {
				renderMungeColumn(columnName, alreadyVisited, script);
			}
		}

		// keep parameters used for computed columns
		for (ITableColumn column : table.getColumns()) {
			String columnName = column.getColumnName();
			if (column.isComputed() && StringUtils.isNotEmpty(columnName)) {
				for (String parameter : column.getParameters()) {
					renderMungeColumn(parameter, alreadyVisited, script);
				}
			}
		}		
		
		// checkboxes 
		for (ITableColumn column : table.getColumns()) {
			if (column.isPlaceHolder() 
					&& !column.isDisplayGrouping()
					&& column.getCheckbox() != null) {
				Widget checkbox = column.getCheckbox();
				Property identifier = checkbox.findProperty("column-checkbox-identifier");
				String value = identifier != null ? identifier.getValue().trim() : "";
				if (!StringUtils.isEmpty(value)) {
					renderMungeColumn(value, alreadyVisited, script);					
				}
				Property security = checkbox.findProperty("column-checkbox-security");
				String secval = security != null ? security.getValue().trim() : "";
				if (!StringUtils.isEmpty(secval)) {
					renderMungeColumn(secval, alreadyVisited, script);					
				}
			}
			
		}
		
		// computation & parameters
		List<String> computes = new ArrayList<String>();
		for (ITableColumn column : table.getColumns()) {
			String columnName = column.getColumnName();			
			if (column.isComputed() && StringUtils.isNotEmpty(columnName)) {
				List<ITableColumnItem> items = column.getItems();
				for (ITableColumnItem item : items) {
					String itemcolumn = item.getColumn();
					if (columnName.equals(itemcolumn)) {
						List<String> parameterList = column.getParameters();
						if (parameterList.size() > 0) {
							script.append("<udp:compute");
							renderAttribute(script, "name", columnName);
							renderAttribute(script, "type", column.getDisplayType());
							renderAttribute(script, "computation", column.getComputation());
							String format = item.getDisplayFormat(); 
							if (!StringUtils.isEmpty(format)) {
								int index = format.indexOf(".");
								if (index > -1) {
									format = format.substring(index+1);
								}
								renderAttribute(script, "format", format);
							}
							computes.add(columnName);
						}
						script.append(">");
						for (String parameter : column.getParameters()) {
							if (StringUtils.isNotEmpty(parameter)) {
								script.append("<udp:param>");
								script.append(parameter);
								script.append("</udp:param>");
							}
						}
						script.append("</udp:compute>");
					}
				}
			}
		}
		// column definitions
		for (ITableColumn column : table.getColumns()) {
			String columnName = column.getColumnName();
			if (column.isComputed() && StringUtils.isNotEmpty(columnName) 
					&& !computes.contains(columnName)) {
				List<String> parameterList = column.getParameters();
				if (parameterList.size() > 0) {
					script.append("<udp:compute");
					renderAttribute(script, "name", columnName);
					renderAttribute(script, "type", column.getDisplayType());
					renderAttribute(script, "computation", column.getComputation());
				}
				script.append(">");
				for (String parameter : column.getParameters()) {
					if (StringUtils.isNotEmpty(parameter)) {
						script.append("<udp:param>");
						script.append(parameter);
						script.append("</udp:param>");
					}
				}
				script.append("</udp:compute>");
				computes.add(columnName);
			}
		}
		
		// other items in computed columns
		for (ITableColumn column : table.getColumns()) {
			String columnName = column.getColumnName();			
			if (column.isComputed() && StringUtils.isNotEmpty(columnName)) {
				List<ITableColumnItem> items = column.getItems();
				for (ITableColumnItem item : items) {
					String itemcolumn = item.getColumn();
					if (!columnName.equals(itemcolumn) 
							&& !computes.contains(itemcolumn)) {
						renderMungeColumn(itemcolumn, alreadyVisited, script);
					}
				}
			}
		}
		
		ITableGroup hierarchyGroup = getHierarchyGroup(table);
		// render hierarchy related info
		if (hierarchyGroup != null) {
			script.append("<udp:add");
			renderAttribute(script, "name", "_nodeDisplay");
			renderAttribute(script, "type", "text");
			renderAttribute(script, "value", "");
			script.append("/>");
			//DS-3448-begin
			script.append("<udp:add");
			renderAttribute(script, "name", "_nodeCode");
			renderAttribute(script, "type", "text");
			renderAttribute(script, "value", "");
			script.append("/>");
			//DS-3448-end
			script.append("<udp:add");
			renderAttribute(script, "name", "_isNode");
			renderAttribute(script, "type", "boolean");
			renderAttribute(script, "value", "false");
			script.append("/>");
		}
		
		if (summaryView && isDynamicTable(table)) {
			String modelRef = table.getModelReference();
			ITableColumn dynamicColum=getDynamicColumn(table);
			String columnId="";
			if(dynamicColum!=null){
			 columnId=dynamicColum.getId().getValue();
			}
			// output a special block if the table has a dyanmic column
			if (isDynamicTable(table)) {
				script.append("<udp:build-dynamic-columns-list");
				renderAttribute(script, "delegating-model-ref", modelRef);
				//apend the column id to the modelRef
				renderAttribute(script, "dynamic-columns-ref", modelRef+"."+columnId);
				
				// retrieve the grouping used for dynamic column
				String dynamicColumn = "";
				String sortingColumn = "";
				ITableGroup group = getDynamicGroup(table);
				if (group != null) {
					dynamicColumn = group.getColumnName();
					sortingColumn = group.getSortingColumnName();
					if (StringUtils.isEmpty(sortingColumn)) {
						sortingColumn = dynamicColumn;
					}
				}
				renderAttribute(script, "dynamic-column", dynamicColumn);
				if (!StringUtils.isEmpty(sortingColumn)) {
					renderAttribute(script, "sorting-column", sortingColumn);
					if (group.isDescending()) {
						renderAttribute(script, "reverse", "true");
					}
				}
				script.append("/>");
			  }
			script.append("<udp:dynamic-columns");
		   //apend the column id to the modelRef
			renderAttribute(script, "dynamic-columns-ref", modelRef+"."+columnId);
			script.append(">");
			ITableGroup group = getDynamicGroup(table);
			if (dynamicColum != null) {
				for (ITableColumnItem columnItem : dynamicColum.getItems()) {
					script.append("<udp:add-dynamic");   
					renderAttribute(script, "name", columnItem.getColumn());
					renderAttribute(script, "dynamic-column", group.getColumnName());
					script.append("/>");
				}
			}
			script.append("</udp:dynamic-columns>");
		}

		script.append("</udp:munge-columns>");
	}

	private void renderAttributeForDisplayFormats(StringBuilder script,
			String displayFormat) {
		if (StringUtils.isNotEmpty(displayFormat)) {
			int pos = displayFormat.indexOf(".");
			if (pos < 0) {
				renderAttribute(script, "as-type", displayFormat);
			} else {
				renderAttribute(script, "as-type", displayFormat.substring(0, pos));
				renderAttribute(script, "format", displayFormat.substring(pos + 1));
			}
		}
	}
	
	/**
	 * @param table
	 * @return list
	 */
	private List<String> getDomainAttributes(ITable table) {	
		List<String> domainAttributes = new ArrayList<String>();
		if (table.getWidget().eResource() == null) {
			return domainAttributes;
		}
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(table.getWidget().eResource());
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		Widget root = table.getWidget().getRootWidget();
		String datasetName = root.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
		if (dataset != null) {
			MdfClass baseClass = dataset.getBaseClass();
			for (Object obj : dataset.getProperties()) {
				if (obj instanceof MdfDatasetMappedProperty) {
					MdfDatasetMappedProperty property = (MdfDatasetMappedProperty) obj;
					Object object = getBaseClassProperty(baseClass, property.getPath());
					if (object instanceof MdfAttribute) {
						domainAttributes.add(property.getName());
					}
				}
			}
		}
		return domainAttributes;
	}
	
	/**
	 * @param baseClass
	 * @param path
	 * @return object
	 */
	private static Object getBaseClassProperty(MdfClass baseClass, String path) {
		int index = path.indexOf(".");
		if (index == -1) {
			return baseClass.getProperty(path);
		} else {
			String associationName = path.substring(0, index);
			MdfProperty property = baseClass.getProperty(associationName);
			if (property instanceof MdfAssociation) {
				MdfAssociation assoc = (MdfAssociation) property;
				MdfClass type = (MdfClass) assoc.getType();
				return getBaseClassProperty(type, path.substring(index+1));
			}
		}
		return null;
	}
	
	public static boolean tableHasPagination(ITable table) {
		final String NO_PAGINATION = "0";
		Property pageSize = table.getPageSize();
		return (pageSize != null && !NO_PAGINATION.equals(pageSize.getValue()));
	}
	
	public static boolean tableCountTreeElementsByLeaf(ITable table) {
		Property p = table.getCountTreeElements();
		return (p != null && p.getValue().equals("leaf"));
	}

	/**
	 * Generates an UDP script to declare page size.
	 * 
	 * @param table
	 * @param script
	 */
	private void renderDeclarePageSize(ITable table, StringBuilder script) {
		Property pageSize = table.getPageSize();
		if (tableHasPagination(table)) {
			boolean noGroupsFound = table.getGroups().isEmpty();
			if (noGroupsFound) {
				script.append("<udp:page>");
			} else {
				script.append("<udp:group-page>");
			}
			String value = "";
			SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
	        if (expander != null) {
	        	value = expander.substitute(pageSize.getValue(), table.getWidget());	        
	        }	
			
			script.append("<udp:size>");
			script.append(value);
			script.append("</udp:size>");
			script.append("<udp:handle><scope:get-module-rank/>p</udp:handle>");
			if (noGroupsFound) {
				script.append("</udp:page>");
			} else {
				script.append("</udp:group-page>");
			}
		}
	}
	
	/**
	 * Generates an UDP script to declare sorts
	 * @param script
	 * @param list
	 */
	private void renderDeclareSorts(StringBuilder script, List<ITableSort> list, ITable table) {
		Collections.sort(list, new TableSortSorter());
		script.append("<udp:sort>");
		script.append("<udp:handle><scope:get-module-rank/>s</udp:handle>");
		if (list.size() > 0) {
			for (ITableSort sort : list) {
				script.append("<udp:sort-column");
				if (sort.isDescending()) {
					script.append(" reverse=\"true\"");
				}
				script.append(">");
				String str = sort.getColumnName();
				script.append(str != null ? str.trim() : "");
				script.append("</udp:sort-column>");
			}
		}
		script.append("</udp:sort>");
	}
	
	/**
	 * @param table
	 * @return
	 */
	private String getBeanDefinePropertyValue(ITable table) {
		Widget root = table.getWidget().getRootWidget();
		String beanKey = "";
		if (root != null && WidgetTypeConstants.MODULE.equals(root.getTypeName())) {
			beanKey = root.getPropertyValue(PropertyTypeConstants.BEAN_DEFINE);
		}
		return beanKey;
	}
	
	/**
	 * @param table
	 * @return
	 */
	private String getEditableDatasetBeanPropertyValue(ITable table) {
		String  beanKey = "";
		Widget root = table.getWidget().getRootWidget();
		if (root != null && WidgetTypeConstants.MODULE.equals(root.getTypeName())) {
			String domEnt = root.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
			beanKey = "DSTableModel."+domEnt.replace(':', '.');
		}
		return beanKey;
	}
	
	/**
	 * @param table
	 * @return
	 */
	private String getBeanNameValue(ITable table) {
		String eds = table.getWidget().getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
		if (StringUtils.isNotEmpty(eds)) {
			eds = eds.substring(eds.indexOf(":")+1);
		}
		return eds;
	}
	
	/**
	 * Generates an UDP script to declare sorts
	 * 
	 * @param table
	 * @param script
	 */
	private void renderDeclareSorts(ITable table, StringBuilder script) {
		renderDeclareSorts(script, table.getSorts(), table);
	}

	/**
	 * @param columnName
	 * @param names
	 * @param script
	 */
	private void renderMungeColumn(String columnName, Set<String> names, StringBuilder script) {
		if (StringUtils.isNotEmpty(columnName) && !names.contains(columnName)) {
			names.add(columnName);
			script.append("<udp:keep>");
			script.append(columnName);
			script.append("</udp:keep>");
		}
	}

	/**
	 * Render a standard table
	 * 
	 * @param context
	 * @param table
	 * @param script
	 * @exception CoreException
	 */
	private void renderTable(WidgetTransformerContext context, ITable table, StringBuilder script) throws CoreException {
		
		boolean closeUDP = false;
		
		if (context.isRootUDPOpen()) {
			closeUDP = false;
			// model reference
			script.append("<udp:model-ref>");
			script.append(table.getModelReference());
			script.append("</udp:model-ref>");
			// view reference
			script.append("<udp:view-ref>");
			script.append(table.getViewReference());
			script.append("</udp:view-ref>");
		} else {
			script.append("<udp:udp");
			renderAttribute(script, "model-ref", table.getModelReference());
			renderAttribute(script, "view-ref", table.getViewReference());
			script.append(">");
			context.openRootUDP();
			closeUDP = true;
		}


		// declare table model
		renderTableDeclareModel(context, table, script);

		// enable table dump
		if (table.isDumpModelEnabled()) {
			script.append("<udp:dumpModel/>");
		}

		// render table content & navigation mechanims
		script.append("<udp:render-list>");
		renderTableEmptyContent(context, table, script);
		renderTableNonEmptyContent(context, table, script);
		script.append("</udp:render-list>");

		if (closeUDP) {
			script.append("</udp:udp>");
			context.closeRootUDP();
		}
	}

	/**
	 * @param context
	 * @param table
	 * @param script
	 */
	private void renderTableDeclareModel(WidgetTransformerContext context, ITable table, StringBuilder script) {
		script.append("<udp:build>");
		renderDeclareKeepFilters(table, script);
		renderDeclareMungeColumns(table, script, null);
		script.append("<udp:list>");
		renderDeclareSorts(table, script);
		renderDeclarePageSize(table, script);
		script.append("</udp:list>");
		script.append("</udp:build>");
	}

	/**
	 * @param context
	 * @param table
	 * @param script
	 */
	private void renderTableEmptyContent(WidgetTransformerContext context, ITable table, StringBuilder script) {
		script.append("<xsp:logic>if(<udp:grouped-row-count/> == 0) {" + EOL);
		script.append("<xsp:content>");
		script.append("<xgui:vbox>");
		script.append("<xgui:label>");
		script.append("<xgui:text><i18n:text>general.empty_result</i18n:text></xgui:text></xgui:label>");
		script.append("</xgui:vbox>");
		if (table.hasFilter()) {
		script.append("<xsp:logic>if(<udp:has-dataset-filter delegating-model-ref=\""
				+table.getModelReference()+"\" filter-level=\""+100+"\"/>) {</xsp:logic>");
		}
		renderTableTreeElement(script, table, true, true);
		renderTableToolbar(script, table, context, true);
		script.append("</xgui:table>");

		if (table.hasFilter()) {
			/*
			 * DS-4400 -if for some reason the filter cannot be created, 
			 * an empty table must be generated.
			 */
			script.append("<xsp:logic> } else {</xsp:logic>");            
			script.append("<xgui:table id=\""+table.getID()+"\"/>");
			script.append("<xsp:logic> }</xsp:logic>");		
		}
		
		script.append("</xsp:content>}");
		script.append("</xsp:logic>");
	}
	
	/**
	 * @param script
	 * @param table
	 * @param context 
	 * @param zeroRowCount 
	 */
	private void renderTableToolbar(StringBuilder script, ITable table, WidgetTransformerContext context, boolean zeroRowCount) {
	        
	        renderRowSelectorAndColumnSelector(script,table);
		final String TOOLBAR_END = "</xgui:toolbar>";
		final String TABLE_ID = table.getWidget().getID();
		
		if (table.hasFilter() || (table.hasSelectDeselectAll() && table.hasMultiSelection())) {

			renderToolbarFilter(script, table, context, zeroRowCount);

			if (!zeroRowCount && table.hasSelectDeselectAll() && table.hasMultiSelection()) {
				ITableColumn msCol = table.getMultiSelectionColumn();
				Widget chkbox = msCol.getCheckbox();
				String action = chkbox.getPropertyValue("column-checkbox-action");
				if (StringUtils.isEmpty(action)) {
					action = "sel";
				}
				final String MULTI_SELECTION = 
					"<xgui:label id=\""+TABLE_ID+".selectall\">" +
	          			"<xgui:onevent>" +
	          				"<xgui:submit>" +
	          					"<xgui:param><xsp:attribute name=\"name\">"+TABLE_ID+"_"+action+".all</xsp:attribute></xgui:param>" +
	          				"</xgui:submit>" +
	          			"</xgui:onevent>" +
	          			"<xgui:text><i18n:text>general.cb.selectall.text</i18n:text></xgui:text>" +
	          			"<xgui:tooltip><i18n:text>general.cb.selectall.tooltip</i18n:text></xgui:tooltip>" +
	          		"</xgui:label>" +
	          	 /* "<xgui:spacer/>" + */ 
	          		"<xgui:label id=\""+TABLE_ID+".deselectall\">" +
	          			"<xgui:onevent>" +
	          				"<xgui:submit>" +
	          					"<xgui:param><xsp:attribute name=\"name\">"+TABLE_ID+"_"+action+".clear</xsp:attribute></xgui:param>" +
	          				"</xgui:submit>" +
	          			"</xgui:onevent>" +
	          			"<xgui:text><i18n:text>general.cb.deselectall.text</i18n:text></xgui:text>" +
	          			"<xgui:tooltip><i18n:text>general.cb.deselectall.tooltip</i18n:text></xgui:tooltip>" +
	                "</xgui:label>";

				script.append(MULTI_SELECTION);
			}
			
			
			
		}
	      script.append(TOOLBAR_END);
	}

	/**
	 * @param context
	 * @param table
	 * @param script
	 * @exception CoreException
	 */
	private void renderTableNonEmptyContent(WidgetTransformerContext context, ITable table, StringBuilder script) throws CoreException {

		// if (table has at least 1 row) then (draw rows)
		script.append("<xsp:logic>if(<udp:grouped-row-count/> &gt; 0) { </xsp:logic>");

		{ // begin table
			
			renderTableTreeElement(script, table, true, false);

			renderTableToolbar(script, table, context, false);

			// table header
			renderColumnsHeader(context, table, script);

			// table rows
			script.append("<udp:for-each-row>");
			script.append("<xgui:row>");
			if (table.isEditable()) {
				script.append("<bean:define key=\""+getEditableDatasetBeanPropertyValue(table)+".entities\" name=\""+getBeanNameValue(table)+"\" prefix-keyword=\"final\">");
				String rowId = table.getWidget().getPropertyValue("table-format-identifier");
				script.append("<bean:param name=\"property\">(<udp:item column=\""+rowId+"\"/>)</bean:param>");
				script.append("</bean:define>");
			}
			
			for (ITableColumn column : table.getColumns()) {
				if (column.isNeverVisible()) {
					continue;
				}
				script.append("<xgui:cell>");
				renderCellData(context, column, script, true);
				script.append("</xgui:cell>");
			}
			script.append("</xgui:row>");
			script.append("</udp:for-each-row>");

			script.append("</xgui:table>");
			
			// close '{' opened in renderTableTreeElement
			String bd = getBeanDefinePropertyValue(table);
			if (table.isEditable() && StringUtils.isNotBlank(bd)) {
				script.append("<xsp:logic>}</xsp:logic>");
			}


		} // end table

		// end-if
		script.append("<xsp:logic>}</xsp:logic>");

		// draw a navigation bar only if table is not empty
		renderTableFooter(script, table);
	}	
	

	/**
	 * Render either the detailed view or the summary view of a tree
	 * 
	 * @param context
	 * @param table
	 * @param script
	 * @exception CoreException
	 */
	private void renderTree(WidgetTransformerContext context, ITable table, StringBuilder script) throws CoreException {
		if (TableHelper.getTableUtilities().isSummaryRenderingMode(table.getRenderingMode()) 
				|| TableHelper.getTableUtilities().isDetailedDelegatedRenderingMode(table.getRenderingMode())) {
			renderTreeDetailledDelegated(context, table, script);
			
		} else {
			renderTreeDetailedView(context, table, script);
		}
	}
	
	/**
	 * @param columnName
	 * @param computation
	 * @return ITableAggregate
	 */
	private ITableAggregate createTableAggregate(String columnName, String computation) {
		ITableAggregate aggregate = TableHelper.getTableUtilities().getFactory().createTableAggregate();
		aggregate.setColumnName(columnName);
		aggregate.setComputation(computation);
		return aggregate;
	}
	
	// see DS-3847
	private boolean isColumnDeclaredInTable(ITable table, String columnName) {
		for (ITableColumn column : table.getColumns()) {
			if (columnName.equals(column.getColumnName())) {
				return true;
			}
		}
		for (ITableExtra extra : table.getExtras()) {
			if (columnName.equals(extra.getColumnName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Generates an UDP script to declare aggregates
	 * 
	 * @param table
	 * @param script
	 * @param retainedColumnNames
	 * @param declareSortsOnAggregates
	 */
	private void renderTreeDeclareAggregates(ITable table, StringBuilder script, Set<String> retainedColumnNames, 
			boolean declareSortsOnAggregates, boolean buildsummary) {

		boolean isSummaryView = isSummaryTreeView(table);

		// Get aggregates
		List<ITableAggregate> aggregateList = new ArrayList<ITableAggregate>();
		aggregateList.addAll(table.getAggregatedColumns());

		// Summary view: aggregates on computed columns will take the columns parameters
		if (isSummaryView && (aggregateList.size() > 0) && declareSortsOnAggregates) {
			Set<String> names = new HashSet<String>();
			for (ITableColumn column : table.getColumns()) {
				if (column.isComputed()) {
					names.add(column.getColumnName());
				}
			}
			List<ITableAggregate> computedAggregates = new ArrayList<ITableAggregate>();
			for (ITableAggregate aggregate : aggregateList) {
				if (names.contains(aggregate.getColumnName())) {
					computedAggregates.add(aggregate);
				}
			}
			if (!computedAggregates.isEmpty()) {
				aggregateList.removeAll(computedAggregates);
			}
		}
		
		// summary view && groups with a different sort column, add an aggregate
		if (isSummaryView && declareSortsOnAggregates) {
			List<ITableGroup> groups = table.getGroups();
			for (ITableGroup group : groups) {
				if(!StringUtils.isEmpty(group.getSortingColumnName()) 
						&& !group.getColumnName().equals(group.getSortingColumnName())) {
					if (! group.isUsedForDynamicColumn()) {
						aggregateList.add(createTableAggregate(group.getSortingColumnName(), "max"));
					}
				}
			}
		}
		
		// Gets the names of the sorted columns.
		if (isSummaryView && declareSortsOnAggregates) {
			Map<String, ITableSort> sortedColumns = new HashMap<String, ITableSort>();
			for (ITableSort sort : table.getSorts()) {
				String name = sort.getColumnName();
				if (StringUtils.isNotEmpty(name)) {
					sortedColumns.put(name, sort);
				}
			}
			
			// declare a sort on each aggregated column that have a sort
			List<ITableSort> sorts = new ArrayList<ITableSort>();
			for (ITableAggregate aggregate : aggregateList) {
				String name = aggregate.getColumnName();
				if (StringUtils.isNotEmpty(name)) {
					ITableSort s = sortedColumns.get(name);
					if (s != null) {
						sorts.add(s);
					}
				}
			}
			if (sorts.size() > 0) {
				renderDeclareSorts(script, sorts, table);
			}
		}

		// Declare aggregates
		// DS-2809
		List<String> additionalAggregatedColumns = new ArrayList<String>();
		for (ITableGroup tableGroup : table.getGroups()) {
			String sortingColumnName = tableGroup.getSortingColumnName();
			if (StringUtils.isNotEmpty(sortingColumnName)) {
				if ( ! isColumnDeclaredInTable(table, sortingColumnName)) {
					//DS-5365 control default aggregate generation, irrespective of sorting column
					if(! tableGroup.isUsedForDynamicColumn() && isDetailedDelegatedRenderingMode(table)){
						if(tableGroup.isAggregatedData()){
							additionalAggregatedColumns.add(sortingColumnName);	
						}
					}
					// DS-3847 do not declare additional aggregate for a column that is not declared
					// DS-3847 in the table as a regular column or as an extra column
					if (!isSummaryView) {
						continue;
					}
				}
				if ( ! tableGroup.getColumnName().equals(sortingColumnName)) {
					if ( ! tableGroup.isUsedForDynamicColumn() ) {
						//DS-5365 control default aggregate generation
						if(isDetailedDelegatedRenderingMode(table)){
							if(tableGroup.isAggregatedData()){
								additionalAggregatedColumns.add(sortingColumnName);	
							}
						}
						else{
							additionalAggregatedColumns.add(sortingColumnName);
						}
					}
				}
			}
		}
		// Remove duplicates
		for (ITableAggregate aggregate : aggregateList) {
			additionalAggregatedColumns.remove(aggregate.getColumnName());
		}
		
		// remove duplicates if defined as extra
		for (ITableExtra extra : table.getExtras()) {
			additionalAggregatedColumns.remove(extra.getColumnName());			
		}		
		
		if (aggregateList.size() > 0 || additionalAggregatedColumns.size() > 0) {
			script.append("<udp:aggregation>");			
			
			for (ITableAggregate aggregate : aggregateList) {
				String columnName = aggregate.getColumnName();
				ITableColumn tColumn = table.getColumn(columnName);
				if (tColumn != null && (tColumn.isPlaceHolder() || tColumn.isDynamic())) {
					continue;
				}
				script.append("<udp:aggregate");
				if (retainedColumnNames != null) {
					retainedColumnNames.add(columnName);
				}
				renderAttribute(script, "aggregated-column", columnName);
				renderAttribute(script, "aggregation-computation", aggregate.getComputation());
				ITableUtilities util = TableHelper.getTableUtilities();
				DataValue value = util.getAggregateComputations().findDataValue(aggregate.getComputation());
				if (value != null && util.isWeightedMean(value)) {
					renderAttribute(script, "extra-column", aggregate.getOtherColumnName());
				}
				script.append("/>");
			}
			for (String sortColumnName : additionalAggregatedColumns) {
				script.append("<udp:aggregate");
				renderAttribute(script, "aggregated-column", sortColumnName);
				renderAttribute(script, "aggregation-computation", "max");
				script.append("/>");
			}
			
			if (isDynamicTable(table)) {
				ITableColumn dynamicColumn = getDynamicColumn(table);
				if (dynamicColumn != null) {
					script.append("<udp:dynamic-columns");
					String columnId=dynamicColumn.getId().getValue();
					renderAttribute(script, "dynamic-columns-ref", table.getModelReference()+"."+columnId);
					script.append(">");
					String computation = getDynamicColumnComputation(table, dynamicColumn);
					for (ITableColumnItem columnItem : dynamicColumn.getItems()) {
						for (ITableAggregate aggregate : table.getAggregatedColumns()) {
							if (columnItem.getColumn().equals(aggregate.getColumnName())) {
								script.append("<udp:aggregate");   
								renderAttribute(script, "aggregated-column", aggregate.getColumnName());
								computation = (!StringUtils.isEmpty(computation)) ? computation : aggregate.getComputation();
								renderAttribute(script, "aggregation-computation", computation);
								script.append("/>");
							}
						}
					}
					script.append("</udp:dynamic-columns>");
				}
			}
			
			script.append("</udp:aggregation>");
		}
	}
	
	/**
	 * @param table
	 * @param dynamicColumn
	 * @return
	 */
	private String getDynamicColumnComputation(ITable table, ITableColumn dynamicColumn) {
		for (ITableAggregate aggregate : table.getAggregatedColumns()) {
			if(aggregate.getColumnName().equals(dynamicColumn.getColumnName())) {
				return aggregate.getComputation();
			}
		}
		return null;
	}

	/**
	 * Generates an UDP script to declare groups.
	 * 
	 * @param table
	 * @param script
	 * @param withSort
	 * @param withMaxGrouping
	 * @param withHighestGrouping
	 * @param retainedColumnNames
	 */
	private void renderTreeDeclareGroups(ITable table, StringBuilder script, boolean withSort, boolean withMaxGrouping,
			boolean withHighestGrouping, boolean declareDynamicGroup, Set<String> retainedColumnNames) {
		
		// Groups a sorted by rank
		List<ITableGroup> list = table.getGroups();
		Collections.sort(list, new TableGroupRankSorter());		

		// reverse the list of group in order to generate
		// from the highest to to lowest rank.
		Collections.reverse(list);
		int nbGroups = list.size();

		for (int gx = 0; gx < nbGroups; gx++) {

			ITableGroup group = list.get(gx);
			if (group.isUsedForDynamicColumn() && !declareDynamicGroup) {
				continue;
			}
			
			
			// DS-3034 - in case of hierarchy with raw data - do not generate the group
			if (isDetailedDelegatedRenderingMode(table) && group.isHierarchy() && !group.isAggregatedData()) {
				continue;
			}
			script.append("<udp:group>");

			// group-column
			script.append("<udp:group-column");
			if (withMaxGrouping) {
				int maxGrouping = group.getMaxGrouping();
				if (maxGrouping > 0) {
					renderAttribute(script, "max-groupings", maxGrouping);
				}
			}
			script.append(">");
			String columnName = group.getColumnName();
			if (retainedColumnNames != null) {
				retainedColumnNames.add(columnName);
			}
			script.append(columnName);
			script.append("</udp:group-column>");

			if (withSort) {
				// sort-column
				if (StringUtils.isNotEmpty(group.getSortingColumnName())) {
					script.append("<udp:sort-column");
					if (group.isDescending()) {
						script.append(" reverse=\"true\"");
					}
					script.append(">");
					script.append(group.getSortingColumnName());
					script.append("</udp:sort-column>");
					//DS-3551
					if (isSummaryTreeView(table)) {
						script.append("<udp:keep-children>false");
						script.append("</udp:keep-children>");
					}
				}
			}
			

			script.append("</udp:group>");
		}
		
		if (!withHighestGrouping && list.size() > 0) {
			// add default sort "ascending" in the Highest grouping column
			script.append("<udp:sort>");
			script.append("<udp:handle><scope:get-module-rank/>s</udp:handle>");
			script.append("</udp:sort>");
		}

	}

	/**
	 * Declare Local Variables
	 * 
	 * @param table
	 * @param script
	 */
	private void renderTreeDeclareLocalVariables(ITable table, StringBuilder script) {
		script.append("<xsp:logic>");
		script.append("int groupDepth = 0;");
		script.append("</xsp:logic>");
	}

	/**
	 * @param context
	 * @param table
	 * @param script
	 * @param withSort
	 * @param withMaxGrouping
	 * @param withHighestGrouping
	 */
	private void renderTreeDeclareModel(WidgetTransformerContext context, ITable table, StringBuilder script,
			boolean withSort, boolean withMaxGrouping, boolean withHighestGrouping) {	
		
		script.append("<udp:build>");
		renderDeclareKeepFilters(table, script);
		renderDeclareMungeColumns(table, script, null);

		script.append("<udp:list>");
		renderDeclareSorts(table, script);
		renderTreeDeclareAggregates(table, script, null, false, false);
		renderTreeDeclareGroups(table, script, withSort, withMaxGrouping, withHighestGrouping, true, null);
		
		// apply hierarchy
		ITableGroup hierarchyGroup = getHierarchyGroup(table);
		if (hierarchyGroup != null) {
			renderApplyHierarchy(script, table, hierarchyGroup);
		}

		renderDeclarePageSize(table, script);

		script.append("</udp:list>");

		script.append("</udp:build>");
	}	
	
	/**
	 * @param script
	 * @param table
	 * @param group
	 */
	private void renderApplyHierarchy(StringBuilder script, ITable table, ITableGroup group) {
		//String modelRef = table.getModelReference();
		script.append("<udp:apply-hierarchy");
		/*
		if (!TableHelper.getTableUtilities().isDetailedDelegatedRenderingMode(table.getRenderingMode())) {
			renderAttribute(script, "master-model-ref", modelRef+".summary.hierarchy");
		}
		*/
		renderAttribute(script, "col-in-master", group.getColumnName());
		renderAttribute(script, "col-in-detail", group.getColumnName());
		script.append(">");
		
		script.append("<udp:map");
		renderAttribute(script, "col-in-hierarchy", "_IS_NODE");
		renderAttribute(script, "col-in-information", "_isNode");
		script.append("/>");
		
		script.append("<udp:map");
		renderAttribute(script, "col-in-hierarchy", "_NODE_DISPLAY");
		renderAttribute(script, "col-in-information", "_nodeDisplay");
		script.append("/>");
		
		//DS-3448-begin
		script.append("<udp:map");
		renderAttribute(script, "col-in-hierarchy", "_NODE_CODE");
		renderAttribute(script, "col-in-information", "_nodeCode");
		script.append("/>");
		//DS-3448-end
		
		script.append("</udp:apply-hierarchy>");
	}
	
	private ITableColumn getDynamicColumn(ITable table) {
		for (ITableColumn column : table.getColumns()) {
			if (column.isDynamic()) {
				return column;
			}
		}
		return null;
	}

	private ITableGroup getDynamicGroup(ITable table) {
		for (ITableGroup group : table.getGroups()) {
			if (group.isUsedForDynamicColumn()) {
				return group;
			}
		}
		return null;
	}

	private boolean isDynamicTable(ITable table) { 
		return getDynamicColumn(table) != null;
	}
	
	/**
	 * Render a toolbar for the tree
	 * @param script
	 * @param table
	 * @param context
	 */
	@SuppressWarnings("unused")
	private void renderTreeToolbar(StringBuilder script, ITable table, WidgetTransformerContext context) {
	   
	    final String TOOLBAR_END = "</xgui:toolbar>";
	    renderRowSelectorAndColumnSelector(script, table);
	    if (table.hasFilter()) {
			renderToolbarFilter(script, table, context, false);
		 } 
	    script.append(TOOLBAR_END);
	}

	/**
	 * @param script
	 * @param table
	 * @param context
	 */
	private void renderToolbarFilter(StringBuilder script, ITable table, WidgetTransformerContext context, boolean zeroCount) {
		final String TABLE_TREE_ID = table.getWidget().getID();
		final int LEVEL = 100;
		
		if (table.hasFilter()) {
			Property domainEntity = findDomainEntity(context, table.getWidget());
			String domainDatasetName = domainEntity.getValue();
			StringBuilder sb = new StringBuilder();
			for (ITableColumn column : table.getColumns()) {
				if (column.isPartOfTheFilter()) {
					sb.append(column.getColumnName());
					sb.append(",");
				}
			}
			//DS-5808
			List<ITableGroup> groups = table.getGroups();
			for (ITableGroup group : groups) {
				if (group.isPartOfTheFilter()) {
					sb.append(group.getColumnName());
					sb.append(",");
				}
			}
			final String COLUMNS = StringUtils.removeEnd(sb.toString(), ",");
			
			final String FILTER = 
				"<xgui:icon id=\"" + TABLE_TREE_ID + ".filterIcon\" ref=\"filter\">" +
					"<xgui:onevent type=\"click\">" +
						//DS-4062"<xgui:submit stabs=\"true\" target=\"layer\" method=\"post\">" +
					    "<xgui:submit target=\"layer\" method=\"post\">" +
//DS4940					"<xsp:attribute name=\"call-URI\"><pageflow:continuation/></xsp:attribute>" +
							"<xgui:param name=\"flow-action\">openFilter</xgui:param>" +
							"<xgui:param name=\"Query.runAtStart\">false</xgui:param>" +
							"<xgui:param name=\"Query." + table.getFilterSetId() + ".TargetDS\">" + domainDatasetName + "</xgui:param>" + 
							"<xgui:param name=\"Query." + table.getFilterSetId() + ".level\">" + LEVEL + "</xgui:param>" +
							"<xgui:param name=\"Query.DSOutput\">" + domainDatasetName + "</xgui:param>" +
							"<xgui:param name=\"Query.attributeInclude\">" + COLUMNS + "</xgui:param>" +
						"</xgui:submit>" +
					"</xgui:onevent>" +
					"<xgui:tooltip><i18n:text>general.filter.tooltip</i18n:text></xgui:tooltip>" + 
				"</xgui:icon>";
			script.append(FILTER);
			
			//DS-3223 render filter label (after the icon)
			final String condition = "<xsp:logic>if(<udp:has-dataset-filter delegating-model-ref=\""
					+ table.getModelReference() + "\" filter-level=\"" + LEVEL + "\"/>) {</xsp:logic>";
			if (!zeroCount) {
				script.append(condition);
			}
			final String filterLabel = 
					"<xgui:label id=\""+TABLE_TREE_ID+".Filter\" halign=\"trail\">" +
						"<xgui:text>" +
							"<i18n:text>general.filter.applied.text</i18n:text>" +
						"</xgui:text>"+
					"</xgui:label>";
			script.append(filterLabel);
			if (!zeroCount) {
				script.append("<xsp:logic>}</xsp:logic>");
			}
		}
	}

	/**
	 * @param context
	 * @param table
	 * @param script
	 * @exception CoreException
	 */
	private void renderTreeDetailledDelegated(WidgetTransformerContext context, ITable table, StringBuilder script) throws CoreException {

		boolean closeUDP = false;
		
		if (context.isRootUDPOpen()) {
			// model reference
			script.append("<udp:model-ref>");
			script.append(table.getModelReference());
			script.append("</udp:model-ref>");			
			// view reference
			script.append("<udp:view-ref>");
			script.append(table.getViewReference());
			script.append("</udp:view-ref>");			
			closeUDP = false;
		} else {
			script.append("<udp:udp");
			renderAttribute(script, "model-ref", table.getModelReference());
			renderAttribute(script, "view-ref", table.getViewReference());
			script.append(">");
			closeUDP = true;
			context.openRootUDP();
		}
		
		
		
		renderTreeDeclareModel(context, table, script, true, true, true);

		if (table.isDumpModelEnabled()) {
			script.append("<udp:dumpModel/>");
		}

		script.append("<udp:render-list>");

		renderTreeDeclareLocalVariables(table, script);
		
		renderTableEmptyContent(context, table, script);
		
		{ // if (table has at least 1 row) then (draw rows)
			
			script.append("<xsp:logic>if(<udp:grouped-row-count/> &gt; 0) { </xsp:logic>");
			
			renderTableTreeElement(script, table, false, false);
			
			//renderTreeToolbar(script, table, context);
			renderTableToolbar(script, table, context, false);
	
			renderColumnsHeader(context, table, script);
	
			script.append("<udp:recurse-data>");
			
			ITableGroup hierarchyGroup = getHierarchyGroup(table);
			if (hierarchyGroup != null) {
				script.append("<xsp:logic>");
				script.append("if (null == <udp:item column=\"_isNode\"/> || <udp:item column=\"_isNode\"/>.equals(\"false\")) {");
				script.append("groupDepth = groupDepth + 1;");
				script.append("} else {");
				script.append("groupDepth = 1;");
				script.append("}");
				script.append("</xsp:logic>");
			} else {
				script.append("<xsp:logic>");
				script.append("groupDepth = groupDepth + 1;");
				script.append("</xsp:logic>");
			}
			renderCollapseAttribute(script, table);
			
			if (isDynamicTable(table)) {
				script.append("<xsp:logic>if ("+table.getGroups().size()+" &gt; groupDepth) {</xsp:logic>");
			}			
			script.append("<xgui:row>");
			if (isDynamicTable(table)) {
				script.append("<xsp:logic>}</xsp:logic>");
			}
			
			if (table.isEditable()) {
				script.append("<bean:define key=\""+getEditableDatasetBeanPropertyValue(table)+".entities\" name=\""+getBeanNameValue(table)+"\" prefix-keyword=\"final\">");
				String rowId = table.getWidget().getPropertyValue("table-format-identifier");
				script.append("<bean:param name=\"property\">(<udp:item column=\""+rowId+"\"/>)</bean:param>");
				script.append("</bean:define>");
			}
			
			renderTreeNonLeafNodes(context, table, script); // is "Display the grouped row � with  aggregation (as specified in 13.4)" ?
			renderTreeLeafNodes(context, table, script); // is "Detail generation � detailled and memory" ?
			if (isDynamicTable(table)) {
				script.append("<xsp:logic>if ("+table.getGroups().size()+" &gt; groupDepth) {</xsp:logic>");
			}			
			script.append("</xgui:row>");
			if (isDynamicTable(table)) {
				script.append("<xsp:logic>}</xsp:logic>");
			}

			if (hierarchyGroup != null) {
				script.append("<xsp:logic>");
				script.append("if (null == <udp:item column=\"_isNode\"/> || <udp:item column=\"_isNode\"/>.equals(\"false\")) {");
				script.append("groupDepth = groupDepth - 1;");
				script.append("}");
				script.append("</xsp:logic>");
			} else {
				// DS-3014
				script.append("<xsp:logic>");
				script.append("groupDepth = groupDepth - 1;");
				script.append("</xsp:logic>");
			}
	
			script.append("</udp:recurse-data>");
			script.append("</xgui:tree>");
			
			// close '{' opened in renderTableTreeElement
			String bd = getBeanDefinePropertyValue(table);
			if (table.isEditable() && StringUtils.isNotBlank(bd)) {
				script.append("<xsp:logic>}</xsp:logic>");
			}
			
			// end-if
			script.append("<xsp:logic>}</xsp:logic>");
		}

		// draw a navigation bar only if tree is not empty
		renderTableFooter(script, table);
		
		script.append("</udp:render-list>");

		if (closeUDP) {
			script.append("</udp:udp>");
			context.closeRootUDP();
		}
	}

	/**
	 * @param context
	 * @param table
	 * @param script
	 * @exception CoreException
	 */
	private void renderTreeDetailedView(WidgetTransformerContext context, ITable table, StringBuilder script) throws CoreException {

		boolean closeUDP = false;
		
		if (context.isRootUDPOpen()) {
			// model reference
			script.append("<udp:model-ref>");
			script.append(table.getModelReference());
			script.append("</udp:model-ref>");			
			// view reference
			script.append("<udp:view-ref>");
			script.append(table.getViewReference());
			script.append("</udp:view-ref>");			
			closeUDP = false;			
		} else {
			script.append("<udp:udp");
			renderAttribute(script, "model-ref", table.getModelReference());
			renderAttribute(script, "view-ref", table.getViewReference());
			script.append(">");
			closeUDP = true;
			context.openRootUDP();
		}
		
		renderTreeDeclareModel(context, table, script, true, true, true);

		renderTreeDeclareLocalVariables(table, script);

		if (table.isDumpModelEnabled()) {
			script.append("<udp:dumpModel/>");
		}

		script.append("<udp:render-list>");
		
		renderTableEmptyContent(context, table, script);
		
		{ // if (table has at least 1 row) then (draw rows)
			
			script.append("<xsp:logic>if(<udp:grouped-row-count/> &gt; 0) { </xsp:logic>");
			
			renderTableTreeElement(script, table, false, false);
			
			renderTableToolbar(script, table, context, false);
	
			renderColumnsHeader(context, table, script);
	
			script.append("<udp:recurse-data>");
			script.append("<xsp:logic>");
			script.append("groupDepth = groupDepth+1;");
			script.append("</xsp:logic>");
			renderCollapseAttribute(script, table);
			script.append("<xgui:row>");
			renderTreeNonLeafNodes(context, table, script);
			renderTreeLeafNodes(context, table, script);
			script.append("</xgui:row>");
	
			script.append("<xsp:logic>");
			script.append("groupDepth = groupDepth - 1;");
			script.append("</xsp:logic>");
	
			script.append("</udp:recurse-data>");
			script.append("</xgui:tree>");
			
			// close '{' opened in renderTableTreeElement
			String bd = getBeanDefinePropertyValue(table);
			if (table.isEditable() && StringUtils.isNotBlank(bd)) {
				script.append("<xsp:logic>}</xsp:logic>");
			}
			
			// end-if
			script.append("<xsp:logic>}</xsp:logic>");
		}

		// draw a navigation bar only if tree is not empty
		renderTableFooter(script, table);
		
		script.append("</udp:render-list>");

		if (closeUDP) {
			script.append("</udp:udp>");
			context.closeRootUDP();
		}
	}

	/**
	 * The content of this generated block is called each time a leaf is
	 * encountered.
	 * 
	 * @param context
	 * @param table
	 * @param script
	 * @exception CoreException
	 */
	private void renderTreeLeafNodes(WidgetTransformerContext context, ITable table, StringBuilder script) throws CoreException {

		// render leaf nodes
		script.append("<udp:on-row>");
		if (!isSummaryTreeView(table)) {
			script.append("<xgui:row>");
			if (table.isEditable()) {
				script.append("<bean:define key=\""+getEditableDatasetBeanPropertyValue(table)+".entities\" name=\""+getBeanNameValue(table)+"\" prefix-keyword=\"final\">");
				String rowId = table.getWidget().getPropertyValue("table-format-identifier");
				script.append("<bean:param name=\"property\">(<udp:item column=\""+rowId+"\"/>)</bean:param>");
				script.append("</bean:define>");
			}
			for (ITableColumn column : table.getColumns()) {
				if (column.isNeverVisible()) {
					continue;
				}
				if(column.isPlaceHolder() && column.isDisplayGrouping()) {
					ITableGroup hierarchyGroup = getHierarchyGroup(table);
					//DS-3034 - hierarch raw data
					if (hierarchyGroup != null && !hierarchyGroup.isAggregatedData()) {
						script.append("<xgui:cell>");
						script.append("<xgui:label>");
						//DS-3181
						if (isDetailedDelegatedRenderingMode(table) 
								&& !hierarchyGroup.isAggregatedData() 
								&& (hierarchyGroup.isLeafLevelEvent() || hierarchyGroup.isNodeLeafLevelEvent())) {
							renderEvents(context, script, hierarchyGroup.getWidget());
						}
						script.append("<xgui:text>");
						script.append("<udp:item");
						renderAttribute(script, "column", "_nodeDisplay");
						script.append("/>");
						script.append("</xgui:text>");
						script.append("</xgui:label>");
						script.append("</xgui:cell>");
					} else {
						script.append("<xgui:cell>");
						renderCellData(context, column, script, false);
						script.append("</xgui:cell>");
					}
				} else {
					script.append("<xgui:cell>");
					renderCellData(context, column, script, true);
					script.append("</xgui:cell>");
				}
			}
			script.append("</xgui:row>");
		}
		script.append("</udp:on-row>");
	}
	

	/**
	 * @param context
	 * @param table
	 * @param script
	 * @exception CoreException
	 */
	private void renderTreeNonLeafNodes(WidgetTransformerContext context, ITable table, StringBuilder script) throws CoreException {

		try {
			
			//generate on an intermediate line (hierarchy or grouping level).
			context.setEditableTableTreeLeafNode(false);
		
			/**
			 * Loads the groups defined on the table, and sort them by their rank
			 * value (ascending order).
			 */
			List<ITableGroup> groups = table.getGroups();
			Collections.sort(groups, new TableGroupRankSorter());
	
			boolean isSummaryView = isSummaryTreeView(table);
			
			//if (isSummaryView && groups.size() > 1) {
				/*
				 * If at least 2 groups are defined The group with the highest rank
				 * is remove from the least
				 */
			//  groups.remove(groups.size() - 1);
			//}
			
			/*
			script.append("<xsp:logic>");
			script.append("groupDepth = groupDepth+1;");
			script.append("</xsp:logic>");
			*/
	
			int groupDepth = 1;
			int max = groups.size();
			if (isDynamicTable(table)) {
				max -= 1;
			}
			for (ITableGroup group : groups) {
				
				if (group.isUsedForDynamicColumn()) {
					// the group is the last one
					continue;
				}
	
				String groupName = group.getColumnName();
				ITableColumn groupedColumn = getGroupedColumn(table, groupName);
	
				script.append("<xsp:logic>");
				if (groupDepth > 1) {
					script.append("} else ");
				}
				script.append("if (groupDepth==" + groupDepth + ") {");
				script.append("</xsp:logic>");			
						
				for (ITableColumn column : table.getColumns()) {
					
					if (column.isNeverVisible()) {
						continue;
					}
	
					boolean isDynamicColumn = column.isDynamic();
					// column display-grouping = true [ the display-grouping column display the group tree.]
					if (column.isDisplayGrouping()) {					
						ITableGroup hierarchyGroup = getHierarchyGroup(table);
						if (hierarchyGroup != null && hierarchyGroup.getColumnName().equals(groupName)) {
							script.append("<xgui:cell>");
							script.append("<xgui:label>");
							//DS-3181
							if (! (isDetailedDelegatedRenderingMode(table) 
									&& !hierarchyGroup.isAggregatedData() 
									&& hierarchyGroup.isLeafLevelEvent())) {
								renderEvents(context, script, group.getWidget());							
							}
							script.append("<xgui:text>");
							script.append("<udp:item");
							renderAttribute(script, "column", "_nodeDisplay");
							script.append("/>");
							script.append("</xgui:text>");
							script.append("</xgui:label>");
						} else {
							script.append("<xgui:cell>");
							if (groupedColumn != null) {
								renderAttributeToolTip(context, script, group.getWidget());
								renderEvents(context, script, group.getWidget());
								renderCellData(context, groupedColumn, script, false);
							} else {
								script.append("<xgui:label>");
								renderAttributeToolTip(context, script, group.getWidget());
								renderEvents(context, script, group.getWidget());
								script.append("<xgui:text>");
								renderNestedGroupAttribute(script, column, groupName);
								script.append("</xgui:text>");
								script.append("</xgui:label>");
							}
						}
						script.append("</xgui:cell>");
						
						if (!isSummaryView){
							continue;
						}
	
					} else {					
						// column display-grouping = false 
						boolean hierarchyRawData = false;
						ITableGroup hierarchyGroup = getHierarchyGroup(table);
						if (hierarchyGroup != null && !hierarchyGroup.isAggregatedData()) {
							hierarchyRawData = true;
						}
						// DS-3509 table with one grouping & not hierarchical
						/*
						if (hierarchyGroup == null && table.getGroups().size() == 1) {
							hierarchyRawData = true;
						}*/					
						context.setHierarchyNonLeafNode(hierarchyRawData);
						if (hierarchyRawData || hasAggregate(table, column, groupName)) {
							if (isDynamicColumn) {
								context.setDynamicColumnLeafNode(groupDepth == max);
								script.append("<udp:dynamic-columns");
								String columnId=column.getId().getValue();
								renderAttribute(script, "dynamic-columns-ref", column.getTable().getModelReference()+"."+columnId);
								script.append(">");
							} else {
								context.setDynamicColumnLeafNode(false);
								if (!hierarchyRawData) {
									context.setEditableTableTreeLeafNode(groupDepth == max);
								}
							}
							script.append("<xgui:cell>");
							boolean react = reactToMainCheckBox(table, hierarchyRawData) && isGroupExistInCheckbox(groupName, column);
							renderCellData(context, column, script, react);
							context.setEditableTableTreeLeafNode(false);
							context.setDynamicColumnLeafNode(false);
							script.append("</xgui:cell>");
							if (isDynamicColumn) {
								script.append("</udp:dynamic-columns>");
							}
						} else {
							if (column.isPlaceHolder()) {
								script.append("<xgui:cell>");
								//DS-4871
								if(isGroupExistInCheckbox(groupName, column)) {
									renderCellData(context, column, script, false);	
								}else if(column.getCheckbox()==null){
									renderCellData(context, column, script, false);	
								}
								script.append("</xgui:cell>");
							} else {
								script.append("<xgui:cell/>");
							}
						}
						context.setHierarchyNonLeafNode(false);
					}
				}
				groupDepth += 1;
				if (groupDepth > max) {
					script.append("<xsp:logic>}</xsp:logic>");
				}
			}
			
		} finally {
			context.setEditableTableTreeLeafNode(true);
		}
	}

	/**
	 * @param groupName
	 * @param column
	 * @return
	 */
	private boolean isGroupExistInCheckbox(String groupName, ITableColumn column) {
		for (Widget child : column.getWidget().getContents()) {
			String typeName = child.getTypeName();
			if (column.getCheckbox() != null && column.isPlaceHolder() && !column.isDisplayGrouping()) {
				if (WidgetTypeConstants.CHECKBOX.equals(typeName) 
						|| WidgetTypeConstants.CONDITIONAL.equals(typeName)) {
					Property chkBxGrp = column.getCheckbox().findProperty("checkbox-group-names");
					String groupnames = chkBxGrp != null ? chkBxGrp.getValue().trim() : "";
					if (!StringUtils.isEmpty(groupnames) && StringUtils.contains(groupnames,groupName)) { 
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * DS-5354
	 * 
	 * @param table
	 * @return
	 */
	private boolean reactToMainCheckBox(ITable table, boolean rawData) {
		if (isDetailedDelegatedRenderingMode(table) 
				&& rawData ) {
			return true;
		}
		return false;
	}
	
	private void renderGroup(WidgetTransformerContext context,ITable table) {
	    try {
		List<ITableGroup> groups = table.getGroups();
		for(ITableGroup group:groups){
		    for (Property p : group.getWidget().getProperties()) {
			Node  itemNode=null;
			if("includeXtooltipFragment".equals(p.getTypeName()) && p.getModel()!=null) {
			    NodeList newList=	context.getRootElement().getElementsByTagName("xgui:label");
			    for(int i=0;i<newList.getLength();i++) {
				Node node=newList.item(i);
				if(node != null && node.getParentNode() !=null ) {
				    Node parent = node.getParentNode().getParentNode();
				    Node xtooltipNode =null;
				    if(parent!=null){
				       xtooltipNode =parent.getParentNode();
				    }
				    if(xtooltipNode !=null && !xtooltipNode.getNodeName().equals("xgui:xtooltip")){
					Node child = node.getChildNodes().item(0);
					if(child != null) {
					    Node udpItem = child.getFirstChild();
					    if(udpItem != null && udpItem.getAttributes() != null) {
						Node value = udpItem.getAttributes().getNamedItem("column"); 
						if(value != null && (value.getNodeValue().equals(group.getColumnName()) || (group.isHierarchy() && value.getNodeValue().equals("_nodeDisplay")))) {
						    itemNode=node;
						    if(itemNode!=null){ 
							Element oldParent=context.setParentElement((Element)itemNode);
							PropertyTransformer pt = context.getTransformModel().findPropertyTransformer(p);							
							pt.transform(context, p);
							context.setParentElement(oldParent);
						    }
						}
					    }
					}	
				    }

				}
			    }		
			}
		    }	
		}

	    } catch (CoreException e) {
		e.printStackTrace();
	    }
	}

	/**
	 * @param script
	 * @param column
	 * @param groupName
	 */
	private void renderNestedGroupAttribute(StringBuilder script, ITableColumn column, String groupName) {
		NestedGroupAttributeRenderer renderer = NestedGroupAttributeRenderer.getInstance(script);
		renderer.render(column, groupName);
	}

	/**
	 * @param table
	 * @return table group
	 */
	private ITableGroup getHierarchyGroup(ITable table) {
		for (ITableGroup group : table.getGroups()) {
			if (group.isHierarchy()) {
				return group;
			}
		}
		return null;
	}

	/**
	 * @param xmlString
	 * @return NodeList
	 */
	private NodeList xmlToDOM(String xmlString) {
		NodeList nodes = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlString)));
			Node root = document.getFirstChild();
			nodes = root.getChildNodes();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}

	/**
	 * @param builder
	 * @return NodeList
	 */
	private NodeList xmlToDOM(StringBuilder builder) {
		return xmlToDOM(builder.toString());
	}
	
	/**
	 * @param script
	 */
	private void renderInputControlReporter(StringBuilder script) {
		script.append("<ic:reporter>");
		script.append("<ic:add-scope-reporter");
		renderAttribute(script, "report-key", "udp.reporter");
		script.append("/>");
		script.append("<ic:rendering>");
		script.append("<ic:param");
		renderAttribute(script, "name", "caption");
		script.append(">");
		script.append("<i18n:text>udp.reporter.label</i18n:text>");
		script.append("</ic:param>");
		script.append("</ic:rendering>");
		script.append("</ic:reporter>");
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#getParentElement(com.odcgroup.page.transformmodel.WidgetTransformerContext,
	 *      com.odcgroup.page.model.Widget)
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return null;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext,
	 *      com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {

		ITable table = TableHelper.getTable(widget);
		
		StringBuilder script = new StringBuilder(32768); // little optim
		script.append(BEGIN_ROOT_NODE);

		if (table.isEditable()) {
			String datasetName = table.getWidget().getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			if (StringUtils.isNotBlank(datasetName)) {
				// render a reporter to display error when the table is edited.
				script.append("<ic:reporter>");
				script.append("<ic:add-scope-reporter report-key=\"");
				script.append("DSBean.");
				script.append(datasetName.replace(":", "."));
				script.append(".reporter");
				script.append("\"/>");
				script.append("<ic:msg-rendering form-name=\"form\"/>");
				script.append("</ic:reporter>");
			}
		}
		
		if (table.hasGroups()) {
			renderTree(context, table, script);
			
		} else {
			renderTable(context, table, script);
		}
		renderInputControlReporter(script);

		script.append(END_ROOT_NODE);

		NodeList nodes = xmlToDOM(script);
		if (nodes == null) {
			return;
		}
		Document document = context.getDocument();
		document.setStrictErrorChecking(false);
		Element parent = context.getParentElement();
		for (int kx = 0; kx < nodes.getLength(); kx++) {
			Node node = document.importNode(nodes.item(kx), true);
			parent.appendChild(node);
		}
		
		if (table.hasGroups()) {
			renderGroup(context,table);
		}
	}


	/**
	 * @param type
	 */
	public TableWidgetTransformer(WidgetType type) {
		super(type);
	}
	
    /**
     * render the row selector and column selector attributes  
     * @param script
     * @param table
     */
	private void renderRowSelectorAndColumnSelector(StringBuilder script,
			ITable table) {
		String TOOLBAR_BEGIN = "<xgui:toolbar ";
		if ((!table.hasGroups() || !table.hasExpandCollapseAll())) {
			TOOLBAR_BEGIN = TOOLBAR_BEGIN + " rowselector =" + "\"false\"";
		}
		if (!table.hasShowColumnSelector()) {
			TOOLBAR_BEGIN = TOOLBAR_BEGIN + " columnselector =" + "\"false\"";
		}
		TOOLBAR_BEGIN = TOOLBAR_BEGIN + " >";
		script.append(TOOLBAR_BEGIN);

	}
	
	/**
	 * @param columnName
	 * @param names
	 * @param script
	 * @param displayFormat 
	 */
	private void renderMungeColumn(String columnName, Set<String> names, StringBuilder script, Property displayFormat) {
		if (StringUtils.isNotEmpty(columnName) && !names.contains(columnName)) {
			names.add(columnName);
			if(displayFormat != null && !displayFormat.getValue().isEmpty()){
				script.append("<udp:keep");
				renderAttributeForDisplayFormats(script, displayFormat.getValue());
				script.append(">");
			}else{
				script.append("<udp:keep>");
			}
			script.append(columnName);
			script.append("</udp:keep>");
		}
	}
	
	/**
	 * Method to check whether the column item is of type MdfEnumeration.
	 * 
	 * @param context
	 * @param item
	 * @return
	 */
	private boolean isEnum(WidgetTransformerContext context, ITableColumnItem item) {
		boolean enumType = false;
		String coulmnName = item.getColumn();
		MdfModelElement element = item.getWidget().findDomainObject(getDomainRepository(context));
		if (element != null && element instanceof MdfDataset) {
			MdfDataset dataset = (MdfDataset) element;
			if (dataset.getProperty(coulmnName) != null
					&& dataset.getProperty(coulmnName).getType() instanceof MdfEnumeration) {
				enumType = true;
			}
		}
		return enumType;
	}
}
