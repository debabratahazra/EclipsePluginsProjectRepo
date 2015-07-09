package com.odcgroup.page.transformmodel.matrix;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.ConditionUtils;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.model.widgets.matrix.IMatrixCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.transformmodel.BaseWidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.util.AttributeRenderer;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * 
 * @author pkk
 */
public class MatrixWidgetTransformer extends BaseWidgetTransformer {

	/**
	 * @param type
	 */
	public MatrixWidgetTransformer(WidgetType type) {
		super(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.page.transformmodel.WidgetTransformer#getParentElement(com
	 * .odcgroup.page.transformmodel.WidgetTransformerContext,
	 * com.odcgroup.page.model.Widget)
	 */
	@Override
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup
	 * .page.transformmodel.WidgetTransformerContext,
	 * com.odcgroup.page.model.Widget)
	 */
	@Override
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		IMatrix matrix = MatrixHelper.getMatrix(widget);
		if (matrix == null) {
			return;
		}
		StringBuilder script = new StringBuilder();
		
		script.append(BEGIN_ROOT_NODE);
		renderMatrixModel(context, matrix, script);
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

	}
	
	/**
	 * @param context
	 * @param matrix
	 * @param script
	 */
	private void renderMatrixModel(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) throws CoreException {
		
		boolean closeUDP = true;
		
		if (context.isRootUDPOpen()) {
			closeUDP = false;
		} else {
			script.append("<udp:udp>");
			context.openRootUDP();
			closeUDP = true;
		}
		
		//summary model
		renderDeclareSummaryModel(matrix, script);
		
		script.append("<udp:build>");
		renderDeclareMungeColumns(matrix, script);
		script.append("<udp:matrix>");
		// axis
		renderXAxisModel(matrix, script);
		renderYAxisModel(matrix, script);
		// cells
		renderMatrixCellsModel(matrix, script);
		
		script.append("</udp:matrix>");
		if (matrix.isDumpModelEnabled()) {
			script.append("<udp:dump-model/>");
		}
		script.append("</udp:build>");
		
		// render matrix
		renderMatrix(context, matrix, script);

		if (closeUDP) {
			script.append("</udp:udp>");
			context.closeRootUDP();
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
	 * @param matrix
	 * @param script
	 */
	private void renderDeclareKeepFilters(IMatrix matrix, StringBuilder script) { 
		List<ITableKeepFilter> list = matrix.getKeepFilters();
		if (list.size() > 0) {
			script.append("<udp:keep-filter>");
			String logic = matrix.getKeepFilterLogic().getValue();
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
			script.append("</udp:keep-filter>");
		}
		
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderMatrixColumnHeader(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) throws CoreException {
		script.append("<xgui:columns>");
		script.append("<xgui:column/>");
		script.append("<udp:for-each-column-header>");
		script.append("<xgui:column");
		//script.append("<xgui:text><udp:column-header/></xgui:text>");
		IMatrixAxis xAxis = matrix.getXAxis();
		if (!xAxis.isDefaultHorizontalAlignment()) {
			renderAttribute(script, "halign", xAxis.getHorizontalAlignment());
		}
		script.append(">");
		script.append("<xgui:text>");
		script.append("<i18n:text>");
		script.append("<udp:item");
		renderAttribute(script, "column", xAxis.getDomainAttribute().getValue());
		script.append("/>");
		script.append("</i18n:text>");
		script.append("</xgui:text>");	
		String column = xAxis.getDomainAttribute().getValue();
		renderAttributeToolTip(context, script, xAxis.getWidget(), column);
		renderEvents(context, script, xAxis.getWidget());
		script.append("</xgui:column>");;
		script.append("</udp:for-each-column-header>");	
		// empty header for last column
		if (matrix.displayLastColumn() || matrix.displayLastCell()) {
			script.append("<xgui:column/>");
		}
		// render extra column headers
		IMatrixExtraColumn extra = matrix.getExtraColumn();
		List<IMatrixExtraColumnItem> items = extra.getItems();
		for (IMatrixExtraColumnItem item : items) {
			script.append("<xgui:column>");
			renderAttributeText(context, script, item);
			String columnName = item.getDomainAttribute().getValue();
			renderAttributeToolTip(context, script, item.getWidget(), columnName);
			renderEvents(context, script, item.getWidget());
			script.append("</xgui:column>");
		}
		script.append("</xgui:columns>");
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderMatrixLastColumnAggregates(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) throws CoreException {
		IMatrixCell cell = matrix.getLastColumn();
		List<IMatrixCellItem> items = cell.getCellItems();
		script.append("<xgui:cell");
		renderAttribute(script, "class", "cl");		
		script.append(">");			
		if (matrix.displayLastColumn()) {
			Widget parent = matrix.getMatrixCell().getWidget();
			renderAggregationCellItems(context, script, items, parent, false);
		} else {
			List<IMatrixContentCellItem> mItems = matrix.getMatrixCell().getItems();
			for (int i = 0; i<mItems.size();i++) {
				script.append("<xgui:label/>");	
			}
		}
		script.append("</xgui:cell>");	
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderExtraColumn(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) throws CoreException {
		IMatrixExtraColumn cell = matrix.getExtraColumn();
		List<IMatrixExtraColumnItem> items = cell.getItems();
		if (items.isEmpty()) {
			return;
		}
		
		for (IMatrixExtraColumnItem item : items) {
			script.append("<xgui:cell");
			renderAttribute(script, "class", "cl");		
			script.append(">");
			String value = item.getDomainAttribute().getValue();
			script.append("<xgui:label");
			String css = item.getCSSClass();
			if (!StringUtils.isEmpty(css)) {
				renderAttribute(script, "class", css);
			}
			renderAttribute(script, "valign", item.getVerticalAlignment().getValue());
			renderAttribute(script, "halign", item.getHorizontalAlignment().getValue());
			script.append(">");
			script.append("<xgui:text>");
			script.append("<udp:item");
			renderAttribute(script, "column", value);
			script.append("/>");
			script.append("</xgui:text>");
			renderEvents(context, script, item.getWidget());
			script.append("</xgui:label>");
			script.append("</xgui:cell>");	
		}
				
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderMatrixContentCells(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) throws CoreException {
		script.append("<udp:for-each-cell>");
		IMatrixContentCell cell = matrix.getMatrixCell();
		ICssClass cssClass = cell.getCssClass();
		boolean conditionFound = false;
		if (cssClass != null) {
			if(cssClass.isConditional()) {
				List<IConditionalCssClass> conditions = cssClass.getConditionalCssClasses();
				if (!conditions.isEmpty()) {
					conditionFound = true;
					script.append("<xsp:logic>");
					script.append("\n String cell_class = \"\"; \n");
					String con = "";
					String result = "";
					for (IConditionalCssClass condition : conditions) {
						con = condition.getCondition().getValue();
						result = condition.getResult().getValue();
						String ifelse = (conditions.indexOf(condition)==0) ? "if" : "else if";
						script.append(ifelse+"("+con+") {");
						script.append("\n cell_class = \""+result+"\";");
						script.append("\n } \n");
					}
					script.append("</xsp:logic>");
				}
			}
		}
		
		Widget matrixContentCellWidget = cell.getWidget();
		script.append("<xgui:cell");
		if (!conditionFound && cssClass != null) {
			if(cssClass.isSpecific()) {
				renderAttribute(script, "class", cssClass.getSpecificCssClass().getValue());
			}
		}
		script.append(">");
		if(conditionFound) {
			script.append("<xsp:attribute");
			renderAttribute(script, "name", "class");
			script.append(">");
			script.append("<xsp:expr>cell_class</xsp:expr>");
			script.append("</xsp:attribute>");
		}
		renderContentBoxItems(context, script, matrixContentCellWidget,  false);		
		script.append("</xgui:cell>");	
		script.append("</udp:for-each-cell>");
	}
	

	/**
	 * @param context
	 * @param script
	 * @param parent
	 * @param conditionFound
	 * @param halign
	 * @throws CoreException
	 */
	private void renderContentBoxItems(WidgetTransformerContext context, StringBuilder script, Widget parent, boolean halign) throws CoreException {
		List<Widget> widgets = parent.getContents();
		for (Widget widget : widgets) {
			String type = widget.getTypeName();
			if (type.equals(WidgetTypeConstants.BOX)) {				
				String boxType = widget.getPropertyValue(PropertyTypeConstants.BOX_TYPE);
				String var = (boxType.equals("horizontal")) ? "hbox" : "vbox";
				halign = boxType.equals("horizontal");
				script.append("<xgui:"+var+">");
				if (!widget.getContents().isEmpty()) {
					renderContentBoxItems(context, script, widget, halign);
				}
				script.append("</xgui:"+var+">");
			} else if (type.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM)) {
				IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
				renderMatrixContentCellItem(context, script, item, null);
			} else if (type.equals(WidgetTypeConstants.CONDITIONAL)) {
				boolean first = true;
				boolean last = false;
				List<Widget> children = widget.getContents();
				for (Widget w : children) {
					if (children.indexOf(w) == children.size() - 1) {
						last = true;
					}
					String javaCode = ConditionUtils.getConditionAsJavaCode(w, true);
					script.append("<xsp:logic>");
					if (first) {
						script.append("if (" + javaCode+ ") {");
					} else {
						script.append("} else if (" + javaCode + ") {");
					}
					script.append("</xsp:logic>");
					renderContentBoxItems(context, script, w, halign);
					if (last) {
						script.append("<xsp:logic> }");
						script.append("</xsp:logic>");
					}
					first = false;
				}
			} else if (type.equals(WidgetTypeConstants.CONDITIONAL_BODY)) {
				List<Widget> children = widget.getContents();
				for (Widget w : children) {
					renderContentBoxItems(context, script, w, halign);					
				}
			} else if (type.equals(WidgetTypeConstants.SPACER)) {
				script.append("<xgui:spacer");
				renderAttribute(script, "height", widget.getPropertyValue(PropertyTypeConstants.HEIGHT));
				renderAttribute(script, "width", widget.getPropertyValue(PropertyTypeConstants.WIDTH));
				script.append("/>");				
			} else if (type.equals(WidgetTypeConstants.GLUE)) {
				script.append("<xgui:glue/>");
			}
		}
	}
	
	
	/**
	 * @param context
	 * @param script
	 * @param items
	 * @param parent
	 * @param conditionFound
	 * @param halign
	 * @throws CoreException
	 */
	private void renderAggregationCellItems(WidgetTransformerContext context, StringBuilder script, List<IMatrixCellItem> items, Widget parent, boolean halign) throws CoreException {
		List<Widget> widgets = parent.getContents();
		for (Widget widget : widgets) {
			String type = widget.getTypeName();
			if (type.equals(WidgetTypeConstants.BOX)) {				
				String boxType = widget.getPropertyValue(PropertyTypeConstants.BOX_TYPE);
				String var = (boxType.equals("horizontal")) ? "hbox" : "vbox";
				halign = boxType.equals("horizontal");
				script.append("<xgui:"+var);
				script.append(">");
				if (!widget.getContents().isEmpty()) {
					renderAggregationCellItems(context, script, items, widget, halign);
				}
				script.append("</xgui:"+var+">");
			} else if (type.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM)) {
				IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
				IMatrixCellItem mItem = getAggregationItem(items, item);
				if (mItem != null) {
					renderMatrixContentCellItem(context, script, item, mItem);
				} else {					
					script.append("<xgui:label/>");			
				}
			} else if (type.equals(WidgetTypeConstants.CONDITIONAL)) {
				boolean first = true;
				boolean last = false;
				List<Widget> children = widget.getContents();
				for (Widget w : children) {
					if (children.indexOf(w) == children.size() - 1) {
						last = true;
					}
					String javaCode = ConditionUtils.getConditionAsJavaCode(w, true);
					script.append("<xsp:logic>");
					if (first) {
						script.append("if (" + javaCode+ ") {");
					} else {
						script.append("} else if (" + javaCode + ") {");
					}
					script.append("</xsp:logic>");
					renderAggregationCellItems(context, script, items, w, halign);
					if (last) {
						script.append("<xsp:logic> }");
						script.append("</xsp:logic>");
					}
					first = false;
				}
			} else if (type.equals(WidgetTypeConstants.CONDITIONAL_BODY)) {
				List<Widget> children = widget.getContents();
				for (Widget w : children) {
					renderAggregationCellItems(context, script, items, w, halign);					
				}
			}
		}
	}
	
	/**
	 * @param items
	 * @param item
	 * @return
	 */
	private IMatrixCellItem getAggregationItem(List<IMatrixCellItem> items, IMatrixContentCellItem item) {
		List<String> aggrItems = item.getAggregationItems();
		for (IMatrixCellItem mItem : items) {
			if (mItem.isComputed() && aggrItems.contains(mItem.getID())) {
				return mItem;
			} else if (mItem.isDomainBound() 
					&& mItem.getDomainAttribute().getValue().equals(item.getDomainAttribute().getValue())) {
				return mItem;
			}
		}
		return null;
	}
	
	/**
	 * @param context
	 * @param script
	 * @param item
	 * @param conditionFound
	 * @param halign
	 * @throws CoreException
	 */
	private void renderMatrixContentCellItem(WidgetTransformerContext context, StringBuilder script, IMatrixContentCellItem item, IMatrixCellItem aggrItem) throws CoreException {
				
		String name = item.isComputed() ? item.getColumnName() : item.getDomainAttribute().getValue();
		if (item.isWeightedMeanAggregation() /* && aggrItem != null*/) {
			name = item.isComputed() ? item.getComputationParameters().get(0) : item.getDomainAttribute().getValue();
			name = "computed_"+ name;
		}

		ICssClass css = null;
		boolean conditionFound = false;
		if (aggrItem != null) {
			css = aggrItem.getCssClass();
			if (css != null) {
				if(css.isConditional()) {
					List<IConditionalCssClass> conditions = css.getConditionalCssClasses();
					if (!conditions.isEmpty()) {
						conditionFound = true;
						script.append("<xsp:logic>");
						script.append("\n String cell_class = \"\"; \n");
						String con = "";
						String result = "";
						for (IConditionalCssClass condition : conditions) {
							con = condition.getCondition().getValue();
							result = condition.getResult().getValue();
							String ifelse = (conditions.indexOf(condition)==0) ? "if" : "else if";
							script.append(ifelse+"("+con+") {");
							script.append("\n cell_class = \""+result+"\";");
							script.append("\n } \n");
						}
						script.append("</xsp:logic>");
					}
				}				
			}
		}
	
		script.append("<xgui:label");
		renderAttribute(script, "halign", item.getHorizontalAlignment().getValue());
		renderAttribute(script, "valign", item.getVerticalAlignment().getValue());
		if (aggrItem == null) {
			String cssClass = item.getWidget().getPropertyValue(PropertyTypeConstants.CSS_CLASS);
			if (!StringUtils.isEmpty(cssClass)) {
				renderAttribute(script, "class", cssClass);
			}
		} else {
			if (!conditionFound && css != null && css.isSpecific()){
				String cssClass = css.getSpecificCssClass().getValue();
				if(!StringUtils.isEmpty(cssClass)) {
					renderAttribute(script, "class", cssClass);			
				}
			}
		}
		String enabled = item.getEnabled().getValue();
		if ("false".equals(enabled)) {
			renderAttribute(script, "enabled", enabled);
		}
		script.append(">");
		if (conditionFound) {
			script.append("<xsp:attribute");
			renderAttribute(script, "name", "class");
			script.append(">");
			script.append("<xsp:expr>cell_class</xsp:expr>");
			script.append("</xsp:attribute>");
		}
		if ("conditional".equals(enabled)) {
			// check simplified
			String simplified = item.getWidget().getPropertyValue("enabledIsBasedOn-simplified");
			String advanced = item.getWidget().getPropertyValue("enabledIsBasedOn-advanced");
			String enabledBased = item.getEnabledIsBasedOn().getValue();
			if (enabledBased.equals("advanced") && !StringUtils.isEmpty(advanced)) {
				script.append("<xsp:attribute");
				renderAttribute(script, "name", "enabled");
				script.append(">");
				script.append("<xsp:expr>");
				script.append(advanced);
				script.append("</xsp:expr>");
				script.append("</xsp:attribute>");
			} else if (!StringUtils.isEmpty(simplified) && !enabledBased.equals("advanced")) {
				script.append("<xsp:attribute");
				renderAttribute(script, "name", "enabled");
				script.append(">");
				script.append("<udp:item");
				renderAttribute(script, "column", enabledBased);
				script.append("/>");
				script.append("</xsp:attribute>");				
			}
		}
		
		script.append("<xgui:text>");
		script.append("<udp:item");
		renderAttribute(script, "column", name);
		script.append("/>");
		script.append("</xgui:text>");
		Widget widget = (aggrItem != null) ? aggrItem.getWidget() : item.getWidget();
		String columnName = item.getDomainAttribute().getValue();
		renderAttributeToolTip(context, script, widget, columnName);
		renderEvents(context, script, widget);
		script.append("</xgui:label>");	

	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderMatrixLastRow(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) throws CoreException {
		IMatrixCell cell = matrix.getLastRow();
		List<IMatrixCellItem> items = cell.getCellItems();
		script.append("<udp:for-each-column-footer>");
		script.append("<xgui:cell>");
		if (matrix.displayLastRow()) {
			Widget parent = matrix.getMatrixCell().getWidget();
			renderAggregationCellItems(context, script, items, parent, false);
		} else {
			List<IMatrixContentCellItem> mItems = matrix.getMatrixCell().getItems();
			for (int i = 0; i < mItems.size(); i++) {	
				script.append("<xgui:label/>");				
			}
		}
		script.append("</xgui:cell>");			
		script.append("</udp:for-each-column-footer>");	
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderLastCellAggregations(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) throws CoreException {		
		IMatrixCell cell = matrix.getLastCell();
		List<IMatrixCellItem> items = cell.getCellItems();
		script.append("<xgui:cell");
		renderAttribute(script, "class", "cl");
		script.append(">");
		if (matrix.displayLastCell()) {
			Widget parent = matrix.getMatrixCell().getWidget();
			renderAggregationCellItems(context, script, items, parent, false);
		} else {
			List<IMatrixContentCellItem> mItems = matrix.getMatrixCell().getItems();
			for (int i = 0; i < mItems.size(); i++) {
				script.append("<xgui:label/>");					
			}			
		}
		script.append("</xgui:cell>");	
	}

	/**
	 * @param context
	 * @param table
	 * @param script
	 */
	private void renderMatrixEmptyContent(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) {
		script.append("<xsp:content>");
		script.append("<xgui:vbox>");
		script.append("<xgui:label>");
		script.append("<xgui:text><i18n:text>general.empty_result</i18n:text></xgui:text></xgui:label>");
		script.append("</xgui:vbox>");		
		
		script.append("<xgui:table");
		renderAttribute(script, "id", matrix.getID());
		script.append("/>");
		
		script.append("</xsp:content>");
	}
	
	/**
	 * @param context
	 * @param matrix
	 * @param script
	 */
	private void renderMatrix(WidgetTransformerContext context, IMatrix matrix, StringBuilder script) throws CoreException {
		script.append("<udp:render-matrix>");
		script.append("<xsp:logic>if(<udp:row-count/> == 0) {</xsp:logic>");
		renderMatrixEmptyContent(context, matrix, script);
		script.append("<xsp:logic>} else {</xsp:logic>");
		
		script.append("<xgui:matrix");
		renderAttribute(script, "id", matrix.getID());
		if (!StringUtils.isEmpty(matrix.getCssClass())) {
			renderAttribute(script, "class", matrix.getCssClass());
		}
		script.append(">");
		
		//columns
		renderMatrixColumnHeader(context, matrix, script);
		
		// rows
		script.append("<udp:for-each-row>");
		script.append("<xgui:row>");
		
		// rowHeader
		script.append("<xgui:cell");
		renderAttribute(script, "class", "cf");
		script.append(">");		
		script.append("<xgui:label>");
		script.append("<xgui:text>");
		//script.append("<udp:row-header/>");
		IMatrixAxis yAxis = matrix.getYAxis();
		script.append("<udp:item");
		renderAttribute(script, "column", yAxis.getDomainAttribute().getValue());
		script.append("/>");
		script.append("</xgui:text>");
		String columnName = yAxis.getDomainAttribute().getValue();
		renderAttributeToolTip(context, script, yAxis.getWidget(), columnName);
		renderEvents(context, script, yAxis.getWidget());
		script.append("</xgui:label>");
		script.append("</xgui:cell>");
		
		// content cell
		renderMatrixContentCells(context, matrix, script);

		// lastColumn
		if (matrix.displayLastColumn() || matrix.displayLastCell()) {
			renderMatrixLastColumnAggregates(context, matrix, script);
		}
		
		// extra column
		renderExtraColumn(context, matrix, script);
			
		script.append("</xgui:row>");		
		script.append("</udp:for-each-row>");		
		
		// render the third row of the matrix
		if (matrix.displayLastRow() || matrix.displayLastCell()) {	
			script.append("<xgui:row");
			renderAttribute(script, "class", "rl");
			script.append(">");		
			script.append("<xgui:cell/>");
			// lastRow
			renderMatrixLastRow(context, matrix, script);
						
			// aggregated cells
			script.append("<udp:aggregated-cell>");
			renderLastCellAggregations(context, matrix, script);
			
			// display extra if displayLastRow
			renderExtraLastRow(matrix, script);
			script.append("</udp:aggregated-cell>");
			
			script.append("</xgui:row>");		
		}		
		
		script.append("</xgui:matrix>");

		// end-if
		script.append("<xsp:logic>}</xsp:logic>");
		script.append("</udp:render-matrix>");
	}
	
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderExtraLastRow(IMatrix matrix, StringBuilder script) {
		IMatrixExtraColumn extra = matrix.getExtraColumn();
		List<IMatrixExtraColumnItem> items = extra.getItems();
		for (IMatrixExtraColumnItem item : items) {
			if(item.displayLastRow()) {
				script.append("<xgui:cell");
				renderAttribute(script, "class", "cl");
				script.append(">");
				script.append("<xgui:label");
				renderAttribute(script, "valign", item.getVerticalAlignment().getValue());
				renderAttribute(script, "halign", item.getHorizontalAlignment().getValue());
				script.append(">");
				script.append("<xgui:text>");
				script.append("<udp:item");
				renderAttribute(script, "column", item.getDomainAttribute().getValue());
				script.append("/>");
				script.append("</xgui:text>");
				script.append("</xgui:label>");
				script.append("</xgui:cell>");
			}
		}
	}
	
	/**
	 * renders the attributes in the content-cells
	 * 
	 * @param matrix
	 * @param script
	 */
	private void renderMatrixCellsModel(IMatrix matrix, StringBuilder script) {
		IMatrixContentCell cell = matrix.getMatrixCell();
		List<IMatrixContentCellItem> items = cell.getItems();
		script.append("<udp:show-in-cell>");
		String value = "";
		for (IMatrixContentCellItem item : items) {
			if (item.isWeightedMeanAggregation() ) {
				value = item.isComputed() ? item.getComputationParameters().get(0): item.getDomainAttribute().getValue();
				script.append("<udp:column>");
				script.append("computed_"+value);
				script.append("</udp:column>");
				if (item.isComputed()) {
					script.append("<udp:column>");
					script.append(item.getColumnName());
					script.append("</udp:column>");
				}
			} else {
				value = item.isComputed() ? item.getColumnName(): item.getDomainAttribute().getValue();
				Property comPro=item.getComputation();
				if(comPro !=null && comPro.getValue().equals("compute-percentage")) {
				  script.append("<udp:column compute-percentage = \"true\">" ) ;
				} else {
				  script.append("<udp:column>");
				}
				script.append(value);
				script.append("</udp:column>");
			}			
		}
		script.append("</udp:show-in-cell>");
		renderDeclareAggregates(matrix, script, true);
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderXAxisModel(IMatrix matrix, StringBuilder script) {
		IMatrixAxis axis = matrix.getXAxis();
		script.append("<udp:group-x");
		int grouping = axis.getMaxGrouping();
		if (grouping > -1){
			renderAttribute(script, "max-groupings", grouping+"");
		}
		script.append(">");
		script.append("<udp:group-column>");
		script.append(axis.getDomainAttribute().getValue());
		script.append("</udp:group-column>");
		renderSortColumn(axis, script);
		script.append("</udp:group-x>");
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderYAxisModel(IMatrix matrix, StringBuilder script) {
		IMatrixAxis axis = matrix.getYAxis();
		script.append("<udp:group-y");
		int grouping = axis.getMaxGrouping();
		if (grouping > -1){
			renderAttribute(script, "max-groupings", grouping+"");
		}
		script.append(">");
		script.append("<udp:group-column>");
		script.append(axis.getDomainAttribute().getValue());
		script.append("</udp:group-column>");
		renderSortColumn(axis, script);
		script.append("</udp:group-y>");
	}
	
	/**
	 * @param axis
	 * @param script
	 */
	private void renderSortColumn(IMatrixAxis axis, StringBuilder script) {
		String sortColumn = axis.getSortingColumnName();
		if (StringUtils.isEmpty(sortColumn)) {
			script.append("<udp:sort-column/>");
		} else {

			script.append("<udp:sort-column");
			if (axis.isDescending()) {
				renderAttribute(script, "reverse", "true");
			}
			script.append(">");
			String domAttr =getDomainAttributeAggregationFromContentCell(sortColumn, axis.getParent());
			if (domAttr != null) {
				script.append(sortColumn+"_"+domAttr+"_"+axis.getDomainAttribute().getValue());
			} else {
				script.append(sortColumn);
			}
			script.append("</udp:sort-column>");
		}
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderDeclareSummaryModel(IMatrix matrix, StringBuilder script) {
		script.append("<udp:build-summary-model");
		String modelRef = matrix.getModelReference();
		renderAttribute(script, "delegating-model-ref", modelRef);
		renderAttribute(script, "summary-model-ref", modelRef+".summary");
		script.append(">");		
		renderDeclareKeepFilters(matrix, script);
		script.append("<udp:list>");
		renderDeclareAggregates(matrix, script, false);
		renderDeclareGroups(matrix, script);
		script.append("</udp:list>");
		script.append("</udp:build-summary-model>");
		script.append("<udp:model-ref>");
		script.append(modelRef+".summary");
		script.append("</udp:model-ref>");	
	}
	
	/**
	 * returns the aggregation type
	 * 
	 * @param columnName
	 * @param matrix
	 * @return
	 */
	private String getDomainAttributeAggregationFromContentCell(String columnName, IMatrix matrix) {
		Map<String, String> domainItems = new HashMap<String, String>();
		// matrix content cell items
		for(IMatrixContentCellItem item : matrix.getMatrixCell().getDomainItems()) {
			domainItems.put(item.getDomainAttribute().getValue(), item.getAggregation().getValue());
		}
		// matrix content cell comp items
		for (IMatrixContentCellItem item : matrix.getMatrixCell().getComputedItems()) {
			List<String> cps = item.getComputationParameters();
			for (String string : cps) {
				domainItems.put(string, item.getAggregation().getValue());				
			}
		}
		// matrix additional columns
		for (IMatrixExtraColumnItem item : matrix.getExtraColumn().getItems()) {
			domainItems.put(item.getDomainAttribute().getValue(), item.getAggregationType().getValue());
		}
		
		// matrix extra
		for(IMatrixExtra extra: matrix.getExtras()) {
			domainItems.put(extra.getColumnName(), extra.getAggregationType());
		}
		
		
		if (domainItems.containsKey(columnName)) {
			return domainItems.get(columnName);
		}
		return null;
	}
	
	/**
	 * @param matrix
	 * @param axis
	 * @param names
	 * @param script
	 * @param displayFormat
	 */
	private void renderMungeAxisSortColumn(IMatrix matrix, IMatrixAxis axis, Set<String> names, StringBuilder script, String displayFormat) {
		String axisSortColumn = axis.getSortingColumnName();
		String aggrType =getDomainAttributeAggregationFromContentCell(axisSortColumn, matrix);
		//if the sorting column is same as a domain attribute in the main cell, use a computation
		if (aggrType != null) {
			/*
			String format = null;
			if (domAttr instanceof IMatrixContentCellItem) {
				format = ((IMatrixContentCellItem) domAttr).getDisplayFormat().getValue();
			} else if (domAttr instanceof IMatrixExtraColumnItem) {
				format = ((IMatrixExtraColumnItem) domAttr).getDisplayFormat().getValue();
			}
			*/
			script.append("<udp:compute");
			String name = axis.getSortingColumnName()+"_"+aggrType+"_"+axis.getDomainAttribute().getValue();
			renderAttribute(script, "name", name);
			renderAttribute(script, "option", aggrType);
			/*
			if (StringUtils.isNotEmpty(format)) {
				int pos = format.indexOf(".");
				if (pos < 0) {
					renderAttribute(script, "type", format);
				} else {
					renderAttribute(script, "type", format.substring(0, pos));
					renderAttribute(script, "format", format.substring(pos + 1));
				}
			}		
			*/
			renderAttribute(script, "computation", "aggregate");				
			script.append(">");
			script.append("<udp:param>");
			script.append(axis.getSortingColumnName());
			script.append("</udp:param>");
			script.append("<udp:param>");
			script.append(axis.getDomainAttribute().getValue());
			script.append("</udp:param>");
			script.append("</udp:compute>");
		} else {
			renderMungeColumn(axis.getSortingColumnName(), names, script, displayFormat);
		}
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderDeclareMungeColumns(IMatrix matrix, StringBuilder script) {
		script.append("<udp:munge-columns>");
		IMatrixContentCell cells = matrix.getMatrixCell();
		Set<String> visited = new HashSet<String>();
		String name = null;		
		
		// axis
		IMatrixAxis xAxis = matrix.getXAxis();
		IMatrixAxis yAxis = matrix.getYAxis();
		String xName = xAxis.getDomainAttribute().getValue();
		String yName = yAxis.getDomainAttribute().getValue();
		String xformat = xAxis.getDisplayFormat().getValue();
		String yformat = yAxis.getDisplayFormat().getValue();
		renderMungeColumn(xName, visited, script, xformat);
		renderMungeColumn(yName, visited, script, yformat);
		
		// axis sortingcolumn
		renderMungeAxisSortColumn(matrix, xAxis, visited, script, xformat);
		renderMungeAxisSortColumn(matrix, yAxis, visited, script, yformat);
		
		// domain items
		List<IMatrixContentCellItem> domainItems = cells.getDomainItems();
		for (IMatrixContentCellItem item : domainItems) {
			name = item.getDomainAttribute().getValue();
			renderMungeColumn(name, visited, script, item.getDisplayFormat().getValue());
		}
		
		// extra column
		IMatrixExtraColumn extra = matrix.getExtraColumn();
		List<IMatrixExtraColumnItem> extraItems = extra.getItems();
		for (IMatrixExtraColumnItem eItem : extraItems) {
			name = eItem.getDomainAttribute().getValue();
			renderMungeColumn(name, visited, script, eItem.getDisplayFormat().getValue());
		}
		
		// matrix extras
		List<IMatrixExtra> extras = matrix.getExtras();
		for (IMatrixExtra iExtra : extras) {
			name = iExtra.getColumnName();
			renderMungeColumn(name, visited, script);
		}
		
		// weighted-mean aggregations
		List<IMatrixContentCellItem> items = cells.getItems();
		for (IMatrixContentCellItem item : items) {
			if (item.isWeightedMeanAggregation()) {
				String weight = item.getMeanWeight().getValue();
				renderMungeColumn(weight, visited, script);
				script.append("<udp:compute");
				renderAttribute(script, "computation", "divide");
				String compname = item.isComputed() ? item.getComputationParameters().get(0) 
						: item.getDomainAttribute().getValue();
				renderAttribute(script, "name", "computed_"+compname);				
				String displayFormat = item.getDisplayFormat().getValue();
				if (StringUtils.isNotEmpty(displayFormat)) {
					int pos = displayFormat.indexOf(".");
					if (pos < 0) {
						renderAttribute(script, "type", displayFormat);
					} else {
						renderAttribute(script, "type", displayFormat.substring(0, pos));
						renderAttribute(script, "format", displayFormat.substring(pos + 1));
					}
				}
				script.append(">");
				
				script.append("<udp:param>");
				script.append(compname);
				script.append("</udp:param>");
				script.append("<udp:param>");
				script.append(item.getMeanWeight() != null ? item.getMeanWeight().getValue() : "");
				script.append("</udp:param>");

				script.append("</udp:compute>");
			}
		}
		
		// computed items
		List<IMatrixContentCellItem> computedItems = cells.getComputedItems();
		for (IMatrixContentCellItem item : computedItems) {
			List<String> parameterList = item.getComputationParameters();
			String columnName = item.getColumnName();
			if (parameterList.size() > 0) {
				script.append("<udp:compute");
				renderAttribute(script, "name", columnName);
				String displayFormat = item.getDisplayFormat().getValue();
				if (StringUtils.isNotEmpty(displayFormat)) {
					int pos = displayFormat.indexOf(".");
					if (pos < 0) {
						renderAttribute(script, "type", displayFormat);
					} else {
						renderAttribute(script, "type", displayFormat.substring(0, pos));
						renderAttribute(script, "format", displayFormat.substring(pos + 1));
					}
				}
				if(item.getComputation().getValue().equals("compute-percentage")){
				    renderAttribute(script, "computation", "same");
				} else {
				   renderAttribute(script, "computation", item.getComputation().getValue());
				}
				script.append(">");
				for (String parameter : parameterList) {
					if (StringUtils.isNotEmpty(parameter)) {
						script.append("<udp:param>");
						script.append(parameter);
						script.append("</udp:param>");
					}
				}
				script.append("</udp:compute>");
			}
		}

		// axis sort column
		renderMungeColumn(matrix.getXAxis().getSortingColumnName(), visited, script);
		renderMungeColumn(matrix.getYAxis().getSortingColumnName(), visited, script);
		
		script.append("</udp:munge-columns>");
	}
	
	/**
	 * @param item
	 * @param names
	 * @param script
	 */
	private void renderMungeColumn(String columnName, Set<String> names, StringBuilder script, String displayFormat) {
		if (StringUtils.isNotEmpty(columnName) && !names.contains(columnName)) {
			names.add(columnName);
			script.append("<udp:keep");
			if (StringUtils.isNotEmpty(displayFormat)) {
				int pos = displayFormat.indexOf(".");
				if (pos < 0) {
					renderAttribute(script, "as-type", displayFormat);
				} else {
					renderAttribute(script, "as-type", displayFormat.substring(0, pos));
					renderAttribute(script, "format", displayFormat.substring(pos + 1));
				}
			}
			script.append(">");
			script.append(columnName);
			script.append("</udp:keep>");
		}
	}

	/**
	 * @param columnName
	 * @param names
	 * @param script
	 */
	private void renderMungeColumn(String columnName, Set<String> names, StringBuilder script) {
		renderMungeColumn(columnName, names, script, null);
	}
	
	/**
	 * @param matrix
	 * @param script
	 */
	private void renderDeclareGroups(IMatrix matrix, StringBuilder script) {
		IMatrixAxis xAxis = matrix.getXAxis();
		IMatrixAxis yAxis = matrix.getYAxis();
		
		script.append("<udp:group>");
		// group-column x-axis
		script.append("<udp:group-column>");
		script.append(xAxis.getDomainAttribute().getValue());
		script.append("</udp:group-column>");
		script.append("</udp:group>");		
		// group-column y-axis
		script.append("<udp:group>");
		script.append("<udp:group-column>");
		script.append(yAxis.getDomainAttribute().getValue());
		script.append("</udp:group-column>");
		script.append("</udp:group>");		
	}
	
	/**
	 * @param script
	 * @param item
	 * @param aggregates
	 */
	private void renderMakeAmountComputationAggregates(StringBuilder script, IMatrixContentCellItem item, Map<String, List<String>> aggregates) {
		String computation = item.getComputation().getValue();
		if ("make-amount".equals(computation)) {
			String aggregation = item.getAggregationType().getValue();
			// two params - one number/amount and another text
			List<String> params = item.getComputationParameters();
			if (!params.isEmpty() && params.size() == 2) {
				// first param
				renderUdpAggregation(script, aggregates, params.get(0), aggregation, item, false);
				// second param
				renderUdpAggregation(script, aggregates, params.get(1), "max", item, false);
			}
		}
	}
	
	/**
	 * @param aggregates
	 * @param name
	 * @param type
	 */
	private void addAggregation(Map<String, List<String>> aggregates, String name, String type) {
		if (!aggregates.containsKey(name)) {
			List<String> types = new ArrayList<String>();
			types.add(type);
			aggregates.put(name, types);
		} else if((aggregates.containsKey(name)) && !aggregates.get(name).contains(type)) {
			aggregates.get(name).add(type);
		}
	}
	
	/**
	 * @param aggregates
	 * @param name
	 * @param type
	 * @return
	 */
	private boolean isDuplicateAggregation(Map<String, List<String>> aggregates, String name, String type) {
		if (!aggregates.containsKey(name) || 
				((aggregates.containsKey(name)) && !aggregates.get(name).contains(type))) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param script
	 * @param item
	 */
	private void renderRelativePercentOrSameComputationAggregates(StringBuilder script, IMatrixContentCellItem item, Map<String, List<String>> aggregates) {
		String computation = item.getComputation().getValue();
		if ("compute-percentage".equals(computation)|| "same".equals(computation)) {
			String aggregation = item.getAggregationType().getValue();
			List<String> params = item.getComputationParameters();
			if (!params.isEmpty()) {
				//only one param is allowed
				renderUdpAggregation(script, aggregates, params.get(0), aggregation, item, false);
			}			
		}
	}
	
	/**
	 * @param script
	 * @param aggregates
	 * @param name
	 * @param type
	 * @param item
	 */
	private void renderUdpAggregation(StringBuilder script, Map<String, List<String>> aggregates, String name, String type, IMatrixContentCellItem item, boolean inMatrix) {
		if (!isDuplicateAggregation(aggregates, name, type)) {
			String aggr = type;
			if (type.equals("weighted-mean") && !inMatrix) {
				aggr = "weighted-sum";
			}
			script.append("<udp:aggregate");
			renderAttribute(script, "aggregated-column", name);
			renderAttribute(script, "aggregation-computation", aggr);
			if (type.equals("weighted-mean") && item != null) {
				String extra = item.getMeanWeight() != null ? item.getMeanWeight().getValue() :"";
				renderAttribute(script, "extra-column", extra);
			}
			script.append("/>");
			addAggregation(aggregates, name, type);			
		}
	}
	
	/**
	 * renders the aggregates for the attributes in the content-cells
	 * 
	 * @param matrix
	 * @param script
	 */
	private void renderDeclareAggregates(IMatrix matrix, StringBuilder script, boolean refModel) {
		IMatrixContentCell contentCell = matrix.getMatrixCell();
		List<IMatrixContentCellItem> items = contentCell.getItems();
		IMatrixExtraColumn extra = matrix.getExtraColumn();
		List<IMatrixExtraColumnItem> eItems = extra.getItems();

		script.append("<udp:aggregation>");
		Map<String, List<String>> aggregates = new HashMap<String, List<String>>();
		
		// content cell items
		for (IMatrixContentCellItem item : items) {
			if (refModel && item.isComputed()) {
				String columnName = item.getColumnName();
				String aggregation = item.getAggregationType().getValue();
				if (item.isWeightedMeanAggregation()) {
					columnName = "computed_"+item.getComputationParameters().get(0);
					renderUdpAggregation(script, aggregates, columnName, aggregation, item, refModel);					
				} else {
					script.append("<udp:aggregate");
					renderAttribute(script, "aggregated-column", columnName);
					renderAttribute(script, "aggregation-computation", aggregation);
					script.append("/>");
				}
			} else if (!refModel && item.isComputed()) {
				String computation = item.getComputation().getValue();
				if ("make-amount".equals(computation)) {
					renderMakeAmountComputationAggregates(script, item, aggregates);
				} else if ("compute-percentage".equals(computation)) {
					renderRelativePercentOrSameComputationAggregates(script, item, aggregates);
				} else if ("same".equals(computation)) {
					renderRelativePercentOrSameComputationAggregates(script, item, aggregates);						
				}
			} else {
				String columnName = item.getDomainAttribute().getValue();
				String aggregation = item.getAggregationType().getValue();
				if (refModel && item.isWeightedMeanAggregation()) {
					columnName = "computed_"+columnName;
				}
				renderUdpAggregation(script, aggregates, columnName, aggregation, item, refModel);
			}
		}
		
		// weighted-mean weight
		for (IMatrixContentCellItem item : items) {
			if (item.isWeightedMeanAggregation()) {
				String meanWeight = item.getMeanWeight().getValue();
				renderUdpAggregation(script, aggregates, meanWeight, "sum", item, refModel);
			}
		}
		
		// matrix additional columns
		for (IMatrixExtraColumnItem eItem : eItems) {
			String columnName = eItem.getDomainAttribute().getValue();
			String aggregation = eItem.getAggregationType().getValue();
			renderUdpAggregation(script, aggregates, columnName, aggregation, null, false);
		}
		
		// matrix extra
		for (IMatrixExtra mextra : matrix.getExtras()) {
			String columnName = mextra.getColumnName();
			String aggregation = mextra.getAggregationType();
			renderUdpAggregation(script, aggregates, columnName, aggregation, null, false);				
		}
		
		if (!refModel) {
			//x-axis sort
			renderSortAggregates(script, matrix, matrix.getXAxis().getSortingColumnName(), aggregates);			
			//y-axis sort
			renderSortAggregates(script, matrix, matrix.getYAxis().getSortingColumnName(), aggregates);
		}
		
		script.append("</udp:aggregation>");
	
		
	}
	
	/**
	 * @param script
	 * @param columnName
	 */
	private void renderSortAggregates(StringBuilder script, IMatrix matrix, String columnName, Map<String, List<String>> aggregates) {
		if (!StringUtils.isEmpty(columnName) && getDomainAttributeAggregationFromContentCell(columnName, matrix) == null) {
			script.append("<udp:aggregate");
			renderAttribute(script, "aggregated-column", columnName);
			renderAttribute(script, "aggregation-computation", "max");
			script.append("/>");		
			addAggregation(aggregates, columnName, "max");
		}
	}

//	/**
//	 * @param table
//	 * @return the model reference
//	 */
//	private String getMatrixModelReference(IMatrix matrix) {
//		Property prop = matrix.getModelReference();
//		String linkedTo = prop.getType().getDefaultValue();
//		String value = prop.getValue();
//		if (StringUtils.isEmpty(value) || linkedTo.equals(value)) {
//			value = prop.getWidget().findPropertyValueInParent(linkedTo);
//			int pos = value.indexOf(":");
//			if (pos != -1) {
//				value = value.substring(pos + 1);
//			}
//		}
//		return value;
//	}

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
	private final void renderAttributeText(WidgetTransformerContext context, StringBuilder script, IMatrixCellItem item) {
		String columnName = item.getDomainAttribute().getValue();
		Widget widget = item.getWidget();
		ITranslationKind kind = ITranslationKind.NAME;
		String key = context.getTranslationKey(widget, kind);
		if (StringUtils.isEmpty(key)) {	
			MdfModelElement element = widget.findDomainObject(getDomainRepository(context), columnName);
			if (element != null)
				key = context.getTranslationKey(element, kind);
		}
		ITranslation translation = context.getTranslation(widget);
		if (!StringUtils.isEmpty(key) && translation.messagesFound(kind)) {
			script.append("<xgui:text>");
			script.append("<i18n:text>");
			script.append(key);
			script.append("</i18n:text>");
			script.append("</xgui:text>");
		} else {
			if (widget.getTypeName().equals("MatrixExtraColumnItem")) {
				script.append("<xgui:text>");
				script.append(columnName);
				script.append("</xgui:text>");	
			}
		}
	}
	
	
	
	/**
	 * @param context 
	 * @param script
	 * @param item
	 */
	private final void renderAttributeToolTip(WidgetTransformerContext context, StringBuilder script, Widget widget, String column) {
		String key = context.getTranslationKey(widget, ITranslationKind.TEXT);
		if (StringUtils.isEmpty(key)) {	
			MdfModelElement element = widget.findDomainObject(getDomainRepository(context), column);
			if (element != null)
				key = context.getTranslationKey(element, ITranslationKind.TEXT);
		}
		ITranslation translation = context.getTranslation(widget);
		if (key != null && translation.messagesFound(ITranslationKind.TEXT)) {
			script.append("<xgui:tooltip>");
			script.append("<i18n:text>");
			script.append(key);
			script.append("</i18n:text>");
			script.append("</xgui:tooltip>");
		}
	}

	/**
	 * @param builder
	 * @return NodeList
	 */
	private NodeList xmlToDOM(StringBuilder builder) {
		return xmlToDOM(builder.toString());
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

	/** begin of the xml root element */
	private static final String BEGIN_ROOT_NODE = "<root " + "xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" "
			+ "xmlns:i18n=\"http://apache.org/cocoon/i18n/2.1\" "
			+ "xmlns:ic=\"http://www.odcgroup.com/uif/inputcontrol/0.1\" "
			+ "xmlns:scope=\"http://www.odcgroup.com/uif/scope/0.1\" "
			+ "xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" "
			+ "xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" "
			+ "xmlns:xsp=\"http://apache.org/xsp\" language=\"java\" "
			+ "xmlns:pageflow=\"http://www.odcgroup.com/uif/pageflow-info/0.1\" " + ">";

	/** end of the xml root element */
	private static final String END_ROOT_NODE = "</root>";


}
