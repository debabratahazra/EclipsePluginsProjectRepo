package com.odcgroup.page.model.widgets.table.impl;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableExtra;
import com.odcgroup.page.model.widgets.table.ITableFactory;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.model.widgets.table.ITableSort;

/**
 * This is the reference implementation for the interface {@link ITableFactory}
 * 
 * @author atr
 * @since DS.140.0
 */
public class TableFactory implements ITableFactory {

	/** name of the xgui library */
	private static final String XGUI_LIBRARY = "xgui";
	/** name of the table/tree aggregate widget */
	private static final String TABLE_AGGREGATE_WIDGET_TYPE = "TableAggregate";
	/** name of the table/tree column widget */
	private static final String COMPUTED_COLUMN_WIDGET_TYPE = "TableColumn";
	/** name of the table/tree column-item widget */
	private static final String TABLE_COLUMN_ITEM_TYPE = "TableColumnItem";
	/** name of the table/tree column widget */
	private static final String DYNAMIC_COLUMN_WIDGET_TYPE = "DynamicColumn";
	/** name of the table/tree column widget */
	private static final String PLACEHOLDER_COLUMN_WIDGET_TYPE = "PlaceHolderColumn";
	/** name of the table/tree extra widget */
	private static final String TABLE_EXTRA_WIDGET_TYPE = "TableExtra";
	/** name of the table/tree group widget */
	private static final String TABLE_GROUP_WIDGET_TYPE = "TableGroup";
	/** name of the table/tree keep filter widget */
	private static final String TABLE_KEEP_FILTER_WIDGET_TYPE = "TableKeepFilter";
	/** name of the table/tree sort widget */
	private static final String TABLE_SORT_WIDGET_TYPE = "TableSort";
	/** name of the table/tree widget */
	private static final String TABLE_WIDGET_TYPE = "TableTree";
	/** name of the template to build a table/tree widget */
	private static final String TABLE_TEMPLATE = "TableTree";
	/** name of the template to build a table/tree column widget */
	private static final String TABLE_COLUMN_TEMPLATE = "TableColumn";
	/** name of the template to build an aggregate item in a table/tree */
	private static final String TABLE_AGGREGATE_TEMPLATE = "TableAggregate";
	/** name of the template to build an extra item in a table/tree */
	private static final String TABLE_EXTRA_TEMPLATE = "TableExtra";
	/** name of the template to build a group item in a table/tree */
	private static final String TABLE_GROUP_TEMPLATE = "TableGroup";
	/** name of the template to build a keep-filter item in a table/tree */
	private static final String TABLE_KEEP_FILTER_TEMPLATE = "TableKeepFilter";
	/** name of the template to build a sort item in a table/tree */
	private static final String TABLE_SORT_TEMPLATE = "TableSort";
	
	/**
	 * Create a new Widget instance given its template name
	 * @param TemplateName the name of the widget template
	 * @return Widget
	 */
	protected Widget createWidget(String TemplateName) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(XGUI_LIBRARY);
		WidgetTemplate template = library.findWidgetTemplate(TemplateName);
		WidgetFactory factory = new WidgetFactory();
		Widget widget = factory.create(template);
		return widget;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#adaptTableAggregateWidget(com.odcgroup.page.model.Widget)
	 */
	public ITableAggregate adaptTableAggregateWidget(Widget widget) {
		if (!TABLE_AGGREGATE_WIDGET_TYPE.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a TableAggregate widget");
		}
		return new TableAggregate(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#adaptTableColumnWidget(com.odcgroup.page.model.Widget)
	 */
	public ITableColumn adaptTableColumnWidget(Widget widget) {
		if (!COMPUTED_COLUMN_WIDGET_TYPE.equalsIgnoreCase(widget.getTypeName()) 
				&& ! PLACEHOLDER_COLUMN_WIDGET_TYPE.equals(widget.getTypeName())
				&& ! DYNAMIC_COLUMN_WIDGET_TYPE.equals(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a TableColumn widget");
		}
		return new TableColumn(widget);
	}

	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#adaptTableExtraWidget(com.odcgroup.page.model.Widget)
	 */
	public ITableExtra adaptTableExtraWidget(Widget widget) {
		if (!TABLE_EXTRA_WIDGET_TYPE.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a TableExtra widget");
		}
		return new TableExtra(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#adaptTableGroupWidget(com.odcgroup.page.model.Widget)
	 */
	public ITableGroup adaptTableGroupWidget(Widget widget) {
		if (!TABLE_GROUP_WIDGET_TYPE.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a TableGroup widget");
		}
		return new TableGroup(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#adaptTableKeepFilterWidget(com.odcgroup.page.model.Widget)
	 */
	public ITableKeepFilter adaptTableKeepFilterWidget(Widget widget) {
		if (!TABLE_KEEP_FILTER_WIDGET_TYPE.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a TableKeepFilter widget");
		}
		return new TableKeepFilter(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#adaptTableSortWidget(com.odcgroup.page.model.Widget)
	 */
	public ITableSort adaptTableSortWidget(Widget widget) {
		if (!TABLE_SORT_WIDGET_TYPE.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a TableSort widget");
		}
		return new TableSort(widget);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#adaptTableWidget(com.odcgroup.page.model.Widget)
	 */
	public ITable adaptTableWidget(Widget widget) {
		if (!TABLE_WIDGET_TYPE.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a Table widget");
		}
		return new Table(widget);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#createTable()
	 */
	public ITable createTable() {
		Widget widget = createWidget(TABLE_TEMPLATE);
		return new Table(widget);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#createTableAggregate()
	 */
	public ITableAggregate createTableAggregate() {
		Widget widget = createWidget(TABLE_AGGREGATE_TEMPLATE);
		return new TableAggregate(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#createTableColumn()
	 */
	public ITableColumn createTableColumn() {
		Widget widget = createWidget(TABLE_COLUMN_TEMPLATE);
		return new TableColumn(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#createTableColumn(String)
	 */
	public ITableColumn createTableColumn(String templateType) {
		if (!COMPUTED_COLUMN_WIDGET_TYPE.equalsIgnoreCase(templateType) 
				&& ! PLACEHOLDER_COLUMN_WIDGET_TYPE.equals(templateType)
				&& ! DYNAMIC_COLUMN_WIDGET_TYPE.equals(templateType)) {
			throw new IllegalArgumentException("This is not a TableColumn widget");
		}
		Widget widget = createWidget(templateType);
		return new TableColumn(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#createTableExtra()
	 */
	public ITableExtra createTableExtra() {
		Widget widget = createWidget(TABLE_EXTRA_TEMPLATE);
		return new TableExtra(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#createTableGroup()
	 */
	public ITableGroup createTableGroup() {
		Widget widget = createWidget(TABLE_GROUP_TEMPLATE);
		return new TableGroup(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#createTableKeepFilter()
	 */
	public ITableKeepFilter createTableKeepFilter() {
		Widget widget = createWidget(TABLE_KEEP_FILTER_TEMPLATE);
		return new TableKeepFilter(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#createTableSort()
	 */
	public ITableSort createTableSort() {
		Widget widget = createWidget(TABLE_SORT_TEMPLATE);
		return new TableSort(widget);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFactory#adaptTableColumnItemWidget(com.odcgroup.page.model.Widget)
	 */
	public ITableColumnItem adaptTableColumnItemWidget(Widget widget) {
		WidgetType expectedWidgetType = widget.getLibrary().findWidgetType(TABLE_COLUMN_ITEM_TYPE);
		if (!widget.getType().isInstanceOf(expectedWidgetType)) {
			throw new IllegalArgumentException("This is not a TableColumnItem widget");
		}
		return new TableColumnItem(widget);
	}

	@Override
	public ITableColumnItem createTableColumnItem(String templateType) {
		return new TableColumnItem(createWidget(templateType));
	}

}
