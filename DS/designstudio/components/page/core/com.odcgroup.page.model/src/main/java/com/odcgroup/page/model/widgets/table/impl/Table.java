package com.odcgroup.page.model.widgets.table.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableExtra;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.model.widgets.table.TableGroupRankSorter;

/**
 * This is the reference implementation of ITable, as a Widget adapter.
 * <p>
 * This implementation helps to build the widget tree for table/tree.
 * 
 * <b>Implementation note:</b>
 * <p>
 * The widget that represent the columns are always stored a the beginning of
 * the collection return by {@code getWiodget().getContents()}, starting from
 * index 0 (zero). Just after the last coloumn widgets the collection contains
 * all other widgets (Group, Extra, KeepFilter, etc... These are not ordered.
 * 
 * @author atr
 */
public class Table extends WidgetAdapter implements ITable {

	/** The name of the widget type for table aggregate */
	private static final String TABLE_AGGREGATE_WIDGET_TYPE = "TableAggregate";

	/** The name of the property that counts the number of column in the table */
	private static final String TABLE_COLUMN_COUNT_PROPERTY = "table-column-count";

	/** The name of the template of the "table-column" widget */
	private static final String TABLE_COLUMN_TEMPLATE = "TableColumn";

	/** The name of the widget type for table column */
	private static final String TABLE_COLUMN_WIDGET_TYPE = "TableColumn";

	/** The name of the property that stores the width of one column */
	private static final String TABLE_COLUMN_WIDTH_PROPERTY = "table-column-width";

	/** The separator used to separate width in the TABLE_COLUMNS_WIDTH_PROPERTY */
	private static final String TABLE_COLUMN_WIDTH_SEPARATOR = ",";

	/** The name of the property that stores the visibility of the columns header */
	//private static final String TABLE_COLUMNS_HEADER_VISIBLE_PROPERTY = "table-columns-header-visible";

	/** The name of the property that stores the width of all columns */
	private static final String TABLE_COLUMNS_WIDTH_PROPERTY = "table-columns-width";

	/** The name of the property that stores the css class */
	private static final String TABLE_CSS_CLASS_PROPERTY = "cssClass";
	
	/** The name of the property that stores the dump-model flag */
	private static final String TABLE_DUMP_MODEL_PROPERTY = "dumpModel";

	/** The name of the property that stores the expand level */
	private static final String TABLE_EXPAND_LEVEL_PROPERTY = "table-expand-level";

	/** The name of the widget type for table extra */
	private static final String TABLE_EXTRA_WIDGET_TYPE = "TableExtra";

	/** The name of the property that stores fixed page size */
	private static final String TABLE_FIXED_PAGE_SIZE_PROPERTY = "table-fixed-size";

	/** The name of the widget type for table group */
	@SuppressWarnings("unused")
	private static final String TABLE_GROUP_WIDGET_TYPE = "TableGroup";

	/** The name of the property that stores the height of the table */
	private static final String TABLE_HEIGHT_PROPERTY = "table-height";

	/** The name of the property that stores the logic to connect all keep filter elements */
	private static final String TABLE_KEEP_FILTER_LOGIC_PROPERTY = "table-keep-filter-logic";
	
	/** The name of the property that stores the row highlight value for the table*/
	private static final String TABLE_HIGHLIGHTROW_PROPERTY = "highlightRow";

	/** The name of the widget type for table keep-filter */
	private static final String TABLE_KEEP_FILTER_WIDGET_TYPE = "TableKeepFilter";

	/** The name of the property that stores the model reference */
	private static final String TABLE_MODEL_REFERENCE_PROPERTY = "table-model-reference";

	/** The name of the property that stores the page size */
	private static final String TABLE_PAGE_SIZE_PROPERTY = "table-page-size";

	/** The name of the property that stores preview size */
	private static final String TABLE_PREVIEW_SIZE_PROPERTY = "table-preview-size";

	/** The name of the property that stores the rendering mode */
	private static final String TABLE_RENDERING_MODE_PROPERTY = "table-rendering-mode";

	/** The name of the widget type for table sort */
	private static final String TABLE_SORT_WIDGET_TYPE = "TableSort";

	/** The name of the property that stores summary show count */
	private static final String TABLE_SUMMARY_SHOW_COUNT_PROPERTY = "table-summary-show-count";

	/** The name of the property that stores the vertical alignement */
	private static final String TABLE_VERTICAL_ALIGNMENT_PROPERTY = "verticalAlignment";

	/** The name of the property that stores the view reference */
	private static final String TABLE_VIEW_REFERENCE_PROPERTY = "table-view-reference";

	/** The name of the property that stores width of the table */
	private static final String TABLE_WIDTH_PROPERTY = "table-width";

	/** The name of the property that stores width of the table */
	private static final String TABLE_EXPAND_COLLAPSE_ALL_PROPERTY = "table-expand-all";
	
	/** The name of the property that store the filter attribute */
	private static final String TABLE_FILTER_PROPERTY = "table-filter-layer";

	/** The name of the property that stores width of the table */
	private static final String TABLE_SELECT_DESELECT_ALL_PROPERTY = "table-select-all";

	/** of the library that contains widget template */
	private static final String XGUI_LIBRARY_NAME = WidgetLibraryConstants.XGUI;

	/** The name of the property that stores filter set id */
	private static final String TABLE_FILTERSET_ID_PROPERTY = "table-filterset-id";
	
	/** The name of the property that tells whether to display checkboxes on tree nodes */
	private static final String DISPLAY_CHECKBOX_PROPERTY = "display-checkbox";
	
	/** The name of the property that tells one checkbox can be selected for multiple */
	private static final String CHECKBOX_EXCLUSIVE_PROPERTY = "make-checkbox-exclusive";
	
	/**show number of item property */
	private static final String TABLE_NUM_PROPERTY = "showNumItems";
	/**show column selector  property */
	private static final String SHOW_COLUMNSELECTOR_PROPERTY = "show-column-selector";
	
	/** Tree how to count tree elements */
	String TABLE_COUNT_TREE_ELEMENTS = "table-count-tree-elements";


	/**
	 * Adds new child widget (column) of the table widget
	 * 
	 * @param widget
	 *            the column widget to be added
	 */
	private void add(Widget widget) {
		List<Widget> children = getWidget().getContents();
		int index = children.indexOf(widget);
		if (index == -1) {
			children.add(widget);
		}
	}

	/**
	 * Check the validity of the index value and raises an exception if the
	 * given index value is not in the range [0..upperBound].
	 * 
	 * @param index
	 *            the index value to be checked
	 * @param upperBound
	 *            range's upper bound
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index in not in the range [0..upperBound]
	 */
	private void checkIndex(final int index, final int upperBound) {
		if ((index < 0) || (index >= upperBound)) {
			StringBuilder builder = new StringBuilder();
			builder.append("The column index: ");
			builder.append(index);
			builder.append(" is not in the range [0,");
			builder.append(upperBound);
			builder.append("]");
			throw new IndexOutOfBoundsException(builder.toString());
		}
	}

	/**
	 * @param item
	 *            the name of an item that identifies an increment
	 * @param incr
	 *            the increment
	 * @return the new value
	 */
	private int doIncrementCount(String item, int incr) {
		int count = 0;
		Property prop = getWidget().findProperty(item);
		if (prop != null) {
			count = prop.getIntValue() + incr;
			prop.setValue(count);
		}
		return count;
	}
	
	/**
	 * Initializes some properties of this new column widget
	 * @param widget
	 */
	private void doInitNewColumn(Widget widget) {
		String type = widget.getPropertyValue("column-type");
		String prefix = "";
		if (type.equals("computed")) {
			prefix = "comp";
		} else if (type.equals("dynamic")){
			prefix = "dynamic";
		} else {
			prefix = "placeholder";
		}
		String suffix = widget.getID();
		if (StringUtils.isBlank(suffix)) {
			widget.setID(UniqueIdGenerator.generateId(widget));
		}
		if (!widget.getContents().isEmpty()) {		
			widget.getContents().get(0).setPropertyValue("item-column", prefix+"_"+suffix);
		}
		new TableColumn(widget).setColumnName(prefix+"_"+suffix);
	}

	/**
	 * Inserts a new column at the specified position
	 * 
	 * @param index
	 *            the position
	 * @return the position of the column
	 */
	private int doInsertColumnAt(int index) {

		/** Uses a script to create a table-column-widget. */
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(XGUI_LIBRARY_NAME);
		WidgetTemplate template = library.findWidgetTemplate(TABLE_COLUMN_TEMPLATE);
		WidgetFactory factory = new WidgetFactory();

		/** Creates the new column widget from its template. */
		Widget column = factory.create(template);
		
		/** Initializes some properties */
		doInitNewColumn(column);

		/** Inserts the new column at the specified position */
		return doInsertColumnAt(index, column);
	}

	/**
	 * Insert the column widget at the specified position
	 * 
	 * @param index
	 *            the position
	 * @param column
	 *            the column widget
	 * @return the position of the column
	 */
	private int doInsertColumnAt(int index, Widget column) {
		List<Widget> contents = getWidget().getContents();
		contents.add(index, column);
		incrColumnCount(+1);
		updateColumnsWidth();
		return index;
	}

	/**
	 * @param index
	 * @return the widget
	 */
	private Widget doRemoveColumn(int index) {
		checkIndex(index, getColumnCount());
		Widget widget = getWidget().getContents().remove(index);
		incrColumnCount(-1);
		updateColumnsWidth();
		return widget;
	}
	
	/**
	 * @return the name of the dataset [<Domain>.<Dataset>]  
	 */
	private String getDataset() {
		String dataset = getWidget().findPropertyValueInParent(PropertyTypeConstants.DOMAIN_ENTITY);
		return null != dataset ? dataset.replaceAll(":", ".") : "";
	}	

	/**
	 * Increments and returns the number of columns in the table
	 * 
	 * @param incr
	 *            the increment value
	 * @return the new column count
	 */
	private final int incrColumnCount(int incr) {
		return doIncrementCount(TABLE_COLUMN_COUNT_PROPERTY, incr);
	}

	/**
	 * Remove a widget from the collection of child widget
	 * 
	 * @param widget
	 *            the widget to be removed
	 */
	private void remove(Widget widget) {
		List<Widget> children = getWidget().getContents();
		int index = children.indexOf(widget);
		if (index != -1) {
			children.remove(index);
		}
	}

	/**
	 * Updates the width of all columns
	 */
	private void updateColumnsWidth() {
		int nbColumns = getColumnCount();
		Property prop = getWidget().findProperty(TABLE_COLUMNS_WIDTH_PROPERTY);
		StringBuffer widths = new StringBuffer();
		for (int cx = 0; cx < nbColumns; cx++) {
			if (cx > 0) {
				widths.append(TABLE_COLUMN_WIDTH_SEPARATOR);
			}
			widths.append(0);
		}
		prop.setValue(widths.toString());
		if (nbColumns > 0) {
			int width = 100 / nbColumns;
			for (int cx = 0; cx < nbColumns; cx++) {
				setColumnWidth(cx, width);
			}
		}
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#add(com.odcgroup.page.model.widgets.table.ITableAggregate)
	 */
	public void add(ITableAggregate aggregate) {
		add(aggregate.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#add(com.odcgroup.page.model.widgets.table.ITableExtra)
	 */
	public void add(ITableExtra extra) {
		add(extra.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#add(com.odcgroup.page.model.widgets.table.ITableKeepFilter)
	 */
	public void add(ITableKeepFilter keepFilter) {
		add(keepFilter.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#add(com.odcgroup.page.model.widgets.table.ITableSort)
	 */
	public void add(ITableSort sort) {
		add(sort.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getAggregatedColumns()
	 */
	@SuppressWarnings("unchecked")
	public List<ITableAggregate> getAggregatedColumns() {
		return (List<ITableAggregate>) CollectionUtils.collect(CollectionUtils.select(getWidget().getContents(),
				new Predicate() {
					public boolean evaluate(Object object) {
						return ((Widget) object).getTypeName().equals(TABLE_AGGREGATE_WIDGET_TYPE);
					}
				}), new Transformer() {
			public Object transform(Object object) {
				return new TableAggregate((Widget) object);
			}
		});
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getMultiSelectionColumn()
	 */
	public ITableColumn getMultiSelectionColumn() {
		for (ITableColumn column : getColumns()) {
			if (column.isPlaceHolder() 
					&& column.getCheckbox() != null) {
				return column;
			}
		}
		return null;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getColumn(int)
	 */
	public ITableColumn getColumn(final int index) {
		checkIndex(index, getColumnCount());
		return new TableColumn(getWidget().getContents().get(index));
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getColumn(java.lang.String)
	 */
	public ITableColumn getColumn(String columnName) {
		for (ITableColumn column : getColumns()) {
			if (columnName.equals(column.getColumnName())) {
				return column;
			}
		}
		return null;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getColumnCount()
	 */
	public int getColumnCount() {
		int count = 0;
		Property prop = getWidget().findProperty(TABLE_COLUMN_COUNT_PROPERTY);
		if (prop != null) {
			count = prop.getIntValue();
		}
		return count;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getColumns()
	 */
	public List<ITableColumn> getColumns() {
		List<ITableColumn> columns = new ArrayList<ITableColumn>();
		for (Widget widget : getWidget().getContents()) {
			if (!widget.getTypeName().equals(TABLE_COLUMN_WIDGET_TYPE)) {
				// we can break here because the following widget
				// are not column widget. See implementation note.
				break;
			}
			columns.add(new TableColumn(widget));
		}
		return columns;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getColumnWidget(int)
	 */
	public Widget getColumnWidget(final int index) {
		checkIndex(index, getColumnCount());
		return getWidget().getContents().get(index);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getColumnWidgets()
	 */
	public List<Widget> getColumnWidgets() {
		List<Widget> columns = new ArrayList<Widget>();
		for (Widget widget : getWidget().getContents()) {
			if (!widget.getTypeName().equals(TABLE_COLUMN_WIDGET_TYPE)) {
				// we can break here because the following widget
				// are not column widget. See implementation note.
				break;
			}
			columns.add(widget);

		}
		return columns;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getColumnWidth(int)
	 */
	public int getColumnWidth(int columnIndex) {
		checkIndex(columnIndex, getColumnCount());
		int width = 0;
		Property prop = getWidget().findProperty(TABLE_COLUMNS_WIDTH_PROPERTY);
		if (prop != null) {
			String[] widths = StringUtils.split(prop.getValue(), TABLE_COLUMN_WIDTH_SEPARATOR);
			width = Integer.parseInt(widths[columnIndex]);
		}
		return width;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getCssClass()
	 */
	public Property getCssClass() {
		return getProperty(TABLE_CSS_CLASS_PROPERTY);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getID()
	 */
	public String getID() {
		return getWidget().getID();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getExpandLevel()
	 */
	public Property getExpandLevel() {
		return getProperty(TABLE_EXPAND_LEVEL_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getExtras()
	 */
	@SuppressWarnings("unchecked")
	public List<ITableExtra> getExtras() {
		return (List<ITableExtra>) CollectionUtils.collect(CollectionUtils.select(getWidget().getContents(),
				new Predicate() {
					public boolean evaluate(Object object) {
						return ((Widget) object).getTypeName().equals(TABLE_EXTRA_WIDGET_TYPE);
					}
				}), new Transformer() {
			public Object transform(Object object) {
				return new TableExtra((Widget) object);
			}
		});
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getFixedPageSize()
	 */
	public Property getFixedPageSize() {
		return getProperty(TABLE_FIXED_PAGE_SIZE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getGroups()
	 */
	public List<ITableGroup> getGroups() {
		if(getDisplayGroupingColumn() != null)
			return getDisplayGroupingColumn().getGroups();
		return Collections.emptyList();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getGroupsByRank()
	 */
	public List<ITableGroup> getGroupsByRank() {
		List<ITableGroup> list = getGroups();
		Collections.sort(list, new TableGroupRankSorter());
		return list;
	}	

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getHeight()
	 */
	public Property getHeight() {
		return getProperty(TABLE_HEIGHT_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getKeepFilterLogic()
	 */
	public Property getKeepFilterLogic() {
		return getWidget().findProperty(TABLE_KEEP_FILTER_LOGIC_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getKeepFilters()
	 */
	@SuppressWarnings("unchecked")
	public List<ITableKeepFilter> getKeepFilters() {
		return (List<ITableKeepFilter>) CollectionUtils.collect(CollectionUtils.select(getWidget().getContents(),
				new Predicate() {
					public boolean evaluate(Object object) {
						return ((Widget) object).getTypeName().equals(TABLE_KEEP_FILTER_WIDGET_TYPE);
					}
				}), new Transformer() {
			public Object transform(Object object) {
				return new TableKeepFilter((Widget) object);
			}
		});
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getModelReference()
	 */
	public String getModelReference() {
		Property property = getProperty(TABLE_MODEL_REFERENCE_PROPERTY);
		String value = property.getValue();
		if (StringUtils.isBlank(value)) {
			value = "DSTableModel." + getDataset();
		}
		return null != value ? value : "";

		
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getPageSize()
	 */
	public Property getPageSize() {
		return getProperty(TABLE_PAGE_SIZE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getPreviewSize()
	 */
	public Property getPreviewSize() {
		return getProperty(TABLE_PREVIEW_SIZE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getRenderingMode()
	 */
	public Property getRenderingMode() {
		return getProperty(TABLE_RENDERING_MODE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getSorts()
	 */
	@SuppressWarnings("unchecked")
	public List<ITableSort> getSorts() {
		return (List<ITableSort>) CollectionUtils.collect(CollectionUtils.select(getWidget().getContents(),
				new Predicate() {
					public boolean evaluate(Object object) {
						return ((Widget) object).getTypeName().equals(TABLE_SORT_WIDGET_TYPE);
					}
				}), new Transformer() {
			public Object transform(Object object) {
				return new TableSort((Widget) object);
			}
		});
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getSummaryShowCount()
	 */
	public Property getSummaryShowCount() {
		return getProperty(TABLE_SUMMARY_SHOW_COUNT_PROPERTY);
	}
	
	public Property getCountTreeElements() {
		return getProperty(TABLE_COUNT_TREE_ELEMENTS);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getVerticalAlignment()
	 */
	public Property getVerticalAlignment() {
		return getProperty(TABLE_VERTICAL_ALIGNMENT_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getViewReference()
	 */
	public String getViewReference() {
		Property property = getProperty(TABLE_VIEW_REFERENCE_PROPERTY);
		String s = property.getValue();
		if (StringUtils.isBlank(s)) {
			Property modelRef = getWidget().findProperty(PropertyTypeConstants.TABLE_MODEL_REFERENCE);
			if (modelRef != null) {
				String modelRefValue = modelRef.getValue();
				if (StringUtils.isBlank(modelRefValue)) {
					modelRefValue = "DSTableModel." + getDataset();
				}
				if (StringUtils.isNotBlank(modelRefValue)) {
					s = modelRefValue + "." + getWidget().getID();
				}
			}
		}
		return null != s ? s : "";

	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getWidth()
	 */
	public Property getWidth() {
		return getProperty(TABLE_WIDTH_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasAggregatedColumns()
	 */
	public boolean hasAggregatedColumns() {
		return getAggregatedColumns().size() > 0;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasExtras()
	 */
	public boolean hasExtras() {
		return getExtras().size() > 0;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasGroups()
	 */
	public boolean hasGroups() {
		return getGroups().size() > 0;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasKeepFilters()
	 */
	public boolean hasKeepFilters() {
		return getKeepFilters().size() > 0;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasExpandCollapseAll()
	 */
	public boolean hasExpandCollapseAll() {
		Property property = getProperty(TABLE_EXPAND_COLLAPSE_ALL_PROPERTY);
		return property.getBooleanValue();
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasFilter()
	 */
	public boolean hasFilter() {
		Property property = getProperty(TABLE_FILTER_PROPERTY);
		return property.getBooleanValue();
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasSelectDeselectAll()
	 */
	public boolean hasSelectDeselectAll() {
		Property property = getProperty(TABLE_SELECT_DESELECT_ALL_PROPERTY);
		return property != null ? property.getBooleanValue() : false;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasMultiSelection()
	 */
	public boolean hasMultiSelection() {
		return getMultiSelectionColumn() != null;
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#hasSorts()
	 */
	public boolean hasSorts() {
		return getSorts().size() > 0;
	}	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumn()
	 */
	public int insertColumn() {
		int nbColumns = getColumnCount();
		insertColumnAt(nbColumns);
		return nbColumns;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumn(com.odcgroup.page.model.widgets.table.ITableColumn)
	 */
	public int insertColumn(ITableColumn column) {
		int nbColumns = getColumnCount();
		Widget widget = column.getWidget();
		doInitNewColumn(widget);
		doInsertColumnAt(nbColumns, widget);
		return nbColumns;
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumnAt(com.odcgroup.page.model.widgets.table.ITableColumn, int)
	 */
	public void insertColumnAt(ITableColumn column, int index) {
		checkIndex(index, getColumnCount() + 1);
		Widget widget = column.getWidget();
		doInitNewColumn(widget);
		doInsertColumnAt(index, widget);		
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumnAt(int)
	 */
	public void insertColumnAt(final int index) {
		checkIndex(index, getColumnCount() + 1);
		doInsertColumnAt(index);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumnAt(int,com.odcgroup.page.model.Widget)
	 */
	public void insertColumnAt(final int index, Widget column) {
		checkIndex(index, getColumnCount() + 1);
		doInsertColumnAt(index, column);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumnLeft(int)
	 */
	public void insertColumnLeft(final int index) {
		checkIndex(index, getColumnCount());
		doInsertColumnAt(index);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumnLeft(com.odcgroup.page.model.widgets.table.ITableColumn, int)
	 */
	public void insertColumnLeft(ITableColumn column, int index) {
		checkIndex(index, getColumnCount());
		Widget widget = column.getWidget();
		doInitNewColumn(widget);
		doInsertColumnAt(index, widget);				
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumnRight(com.odcgroup.page.model.widgets.table.ITableColumn, int)
	 */
	public void insertColumnRight(ITableColumn column, int index) {
		checkIndex(index, getColumnCount());
		Widget widget = column.getWidget();
		doInitNewColumn(widget);
		doInsertColumnAt(index+1, widget);			
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#insertColumnRight(int)
	 */
	public void insertColumnRight(final int index) {
		checkIndex(index, getColumnCount());
		doInsertColumnAt(index + 1);
	}
	
//	/**
//	 * @see com.odcgroup.page.model.widgets.table.ITable#isColumnsHeaderVisible()
//	 */
//	public boolean isColumnsHeaderVisible() {
//		boolean value = true;
//		Property p = getProperty(TABLE_COLUMNS_HEADER_VISIBLE_PROPERTY);
//		if (p != null) {
//			value = p.getBooleanValue();
//		}
//		return value;
//	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#isDumpModelEnabled()
	 */
	public boolean isDumpModelEnabled() {
		boolean value = false;
		Property p = getProperty(TABLE_DUMP_MODEL_PROPERTY);
		if (p != null) {
			value = p.getBooleanValue();
		}
		return value;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#isRowHighLighted()
	 */
	public boolean isRowHighLighted() {
		Property property = getProperty(TABLE_HIGHLIGHTROW_PROPERTY);
		return property.getBooleanValue();
	}	

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#moveColumnLeft(int)
	 */
	public void moveColumnLeft(final int index) {
		checkIndex(index, getColumnCount());
		getWidget().getContents().move(index-1, index);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#moveColumnRight(int)
	 */
	public void moveColumnRight(final int index) {
		checkIndex(index, getColumnCount());
		getWidget().getContents().move(index+1, index);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#remove(com.odcgroup.page.model.widgets.table.ITableAggregate)
	 */
	public void remove(ITableAggregate aggregate) {
		remove(aggregate.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#remove(com.odcgroup.page.model.widgets.table.ITableExtra)
	 */
	public void remove(ITableExtra extra) {
		remove(extra.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#remove(com.odcgroup.page.model.widgets.table.ITableGroup)
	 */
	public void remove(ITableGroup group) {
		remove(group.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#remove(com.odcgroup.page.model.widgets.table.ITableKeepFilter)
	 */
	public void remove(ITableKeepFilter keepFilter) {
		remove(keepFilter.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#remove(com.odcgroup.page.model.widgets.table.ITableSort)
	 */
	public void remove(ITableSort sort) {
		remove(sort.getWidget());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#removeColumn(int)
	 */
	public Widget removeColumn(final int index) {
		return doRemoveColumn(index);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#setColumnCount(int)
	 */
	public List<Widget> setColumnCount(int newColumnCount) {
		List<Widget> deletedColumns = new ArrayList<Widget>();
		int delta = newColumnCount - getColumnCount();
		if (delta > 0) {
			// add new columns
			for (int kx = 0; kx < delta; kx++) {
				insertColumn();
			}
		} else if (delta < 0) {
			// remove columns at columnIndex
			int columnIndex = getColumnCount() + delta;
			for (int kx = 0; kx < -delta; kx++) {
				deletedColumns.add(removeColumn(columnIndex));
			}
		}
		return deletedColumns;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#setColumnWidth(int, int)
	 */
	public void setColumnWidth(int columnIndex, int newWidth) {
		checkIndex(columnIndex, getColumnCount());

		Property prop = getWidget().findProperty(TABLE_COLUMNS_WIDTH_PROPERTY);
		if (prop != null) {
			String[] widths = StringUtils.split(prop.getValue(), TABLE_COLUMN_WIDTH_SEPARATOR);
			widths[columnIndex] = newWidth + "";
			prop.setValue(StringUtils.join(Arrays.asList(widths), TABLE_COLUMN_WIDTH_SEPARATOR));
		}

		Widget column = getWidget().getContents().get(columnIndex);
		column.setPropertyValue(TABLE_COLUMN_WIDTH_PROPERTY, newWidth + "");

	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#getFilterSetId()
	 */
	public String getFilterSetId() {
		if (StringUtils.isEmpty(getWidget().getPropertyValue(TABLE_FILTERSET_ID_PROPERTY))) {
			getWidget().setPropertyValue(TABLE_FILTERSET_ID_PROPERTY, SnippetUtil.getSnippetFactory().generateFilterSetID());
		}
		return getWidget().getPropertyValue(TABLE_FILTERSET_ID_PROPERTY);
	}

	/**
	 * Constructs a new Table Widget Adapter
	 * 
	 * @param widget
	 *            the adapted widget
	 */
	public Table(Widget widget) {
		super(widget);
	}

	@Override
	public boolean displayCheckboxOnTreeNodes() {
		Property property = getProperty(DISPLAY_CHECKBOX_PROPERTY);
		return property.getBooleanValue();
	}

	@Override
	public boolean makeCheckboxesExclusive() {
		Property property = getProperty(CHECKBOX_EXCLUSIVE_PROPERTY);
		return property.getBooleanValue();
	}

	/**
	 * is show number of items enable.
	 * 
	 * @return boolean default false.
	 */
	public boolean isNumItems() {
		Property property = getProperty(TABLE_NUM_PROPERTY);
		return property.getBooleanValue();
	}

	@Override
	public boolean isEditable() {
		List<ITableColumn> columns = getColumns();
		String[] editableWidgets = {"TableColumnCalendar", "TableColumnText", 
				"TableColumnTextArea", "TableColumnCheckbox", 
				"TableColumnCombobox", "TableColumnSearch"};
		List<String> ewidgets = new ArrayList<String>();
		CollectionUtils.addAll(ewidgets, editableWidgets);
		for (ITableColumn col : columns) {
			List<Widget> items = col.getWidget().getContents();
			for (Widget wid : items) {
				String type = wid.getTypeName();
				if (ewidgets.contains(type)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public ITableColumn getDisplayGroupingColumn() {
		List<ITableColumn> columns = getColumns();
		for (ITableColumn col : columns) {
			if (col.isDisplayGrouping()) {
				return col;
			}
		}
		return null;
	}

	@Override
	public boolean hasShowColumnSelector() {
	    Property property = getProperty(SHOW_COLUMNSELECTOR_PROPERTY);
		return property.getBooleanValue();
	}

	@Override
	public void moveGroups(int sourceIndex, int destIndex) {
		List<ITableGroup> groups = getColumn(sourceIndex).getGroups();
		for(ITableGroup grp : groups) {
			ITableColumn srcCol = getColumn(sourceIndex);
			srcCol.removeGroup(grp);
			srcCol.setDisplayGrouping(false);
		}
		ITableColumn destCol = getColumn(destIndex);
		destCol.addGroups(groups);
		destCol.setDisplayGrouping(true);
	}
}
