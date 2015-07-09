package com.odcgroup.page.model.widgets.table.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.TableGroupRankSorter;

/**
 * This is the reference implementation of ITableColumn, as a Widget Adapter. It
 * is mainly used by property views, and transformers
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumn extends WidgetAdapter implements ITableColumn {

	/**
	 * the name of the property that stores the parameters of the computation
	 * function
	 */
	private static final String COLUMN_COMPUTATION_PARAMETERS_PROPERTY = "column-computation-parameters";

	/** the name of the property that stores the computation */
	private static final String COLUMN_COMPUTATION_PROPERTY = "column-computation";

	/** the name of the property that stores the type of data */
	private static final String COLUMN_DISPLAY_TYPE_PROPERTY = "type";

	/** the name of the property id */
	private static final String COLUMN_ID_PROPERTY = "id";

	/** the name of the property locked */
	private static final String COLUMN_LOCKED_PROPERTY = "column-locked";

	/** the name of the property max-characters */
	private static final String COLUMN_MAX_CHARACTERS_PROPERTY = "column-max-characters";

	/** the name of the property that store the name of the column */
	private static final String COLUMN_NAME_PROPERTY = "column-name";

	/** the name of the property that store the participation to the filter */
	private static final String COLUMN_IS_PART_OF_FILTER = "column-is-part-of-filter";

	/** the name of the property sortable */
	private static final String COLUMN_SORTABLE_PROPERTY = "column-sortable";

	/** the name of the property that store the localized column title */
	private static final String COLUMN_TEXT_PROPERTY = "text";

	/** the name of the property that store the localized toolTip */
	private static final String COLUMN_TOOLTIP_PROPERTY = "tooltip";

	/** the name of the property that stores the type of the column */
	private static final String COLUMN_TYPE_PROPERTY = "column-type";

	/** the name of the property that stores the width of the column */
	private static final String COLUMN_WIDTH_PROPERTY = "column-width";

	/** the name of the property wrapped */
	private static final String COLUMN_WRAP_PROPERTY = "column-wrapped";

	/** the name of the property visibility */
	private static final String COLUMN_VISIBILITY_PROPERTY = "column-visibility";

	/** the name of the property xwidth */
	private static final String COLUMN_XWIDTH_PROPERTY = "column-x-width";

	/** the name of the property displayGrouping */
	private static final String COLUMN_DISPLAYGROUPING_PROPERTY = "column-display-grouping";

	/** the name of the widget type that represents an item */
	private static final String TABLE_COLUMN_ITEM_TYPE = "TableColumnItem";

	/** the name of the template to create a table column item */
	private static final String TABLE_COLUMN_ITEM_TEMPLATE = "TableColumnItem";

	/** of the library that contains widget template */
	private static final String XGUI_LIBRARY_NAME = WidgetLibraryConstants.XGUI;

	/** dynamic table column type */
	private static final String TABLE_COLUMN_DYNAMIC = "dynamic";

	/** placeholder table column type */
	private static final String TABLE_COLUMN_PLACEHOLDER = "placeholder";

	/** placeholder table column type */
	private static final String TABLE_COLUMN_DOMAIN = "domain";

	/** column property visibility value for never-visible */
	private static final String COLUMN_VISIBILITY_NEVER_VALUE = "never-visible";
	
	/** The name of the widget type for table group */
	private static final String TABLE_GROUP_WIDGET_TYPE = "TableGroup";
	/** The name of the template to create a table group	 */
	private static final String TABLE_GROUP_WIDGET_TEMPLATE = "TableGroup";

	/**
	 * @return list of computation parameters
	 */
	private List<String> doGetParameters() {
		List<String> parameters = new ArrayList<String>();
		String values = getPropertyValue(COLUMN_COMPUTATION_PARAMETERS_PROPERTY);
		StringTokenizer tokenizer = new StringTokenizer(values, ",");
		while (tokenizer.hasMoreTokens()) {
			parameters.add(tokenizer.nextToken());
		}
		return parameters;
	}

	/**
	 * @param parameters
	 */
	private void doSetParameters(List<String> parameters) {
		String values = StringUtils.join(parameters.toArray(new String[] {}),
				',');
		setPropertyValue(COLUMN_COMPUTATION_PARAMETERS_PROPERTY, values);
	}

	/**
	 * @return tableColumnItem
	 */
	private ITableColumnItem createItem() {

		/** Uses a script to create a table-column-widget. */
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(XGUI_LIBRARY_NAME);
		WidgetTemplate template = library
				.findWidgetTemplate(TABLE_COLUMN_ITEM_TEMPLATE);
		WidgetFactory factory = new WidgetFactory();

		/** Creates the new column widget from its template. */
		Widget item = factory.create(template);

		return new TableColumnItem(item);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#canMoveLeft()
	 */
	public boolean canMoveLeft() {
		return !isFirst();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#canMoveRight()
	 */
	public boolean canMoveRight() {
		return !isLast();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isPartOfTheFilter()
	 */
	public boolean isPartOfTheFilter() {
		if (isBoundToDomain()) {
			Property prop = getProperty(COLUMN_IS_PART_OF_FILTER);
			if (prop != null) {
				return prop.getBooleanValue();
			}
		}
		return false;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isSortable()
	 */
	public boolean isSortable() {
		Property prop = getProperty(COLUMN_SORTABLE_PROPERTY);
		if (prop != null) {
			return prop.getBooleanValue();
		}
		return false;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getDatasetProperty(java.lang.String)
	 */
	public MdfDatasetProperty getDatasetProperty(String name) {
		MdfDatasetProperty property = null;
		MdfModelElement mdfElement = getWidget().findDomainObject(getDomainRepository(), name);
		if( mdfElement instanceof MdfDatasetProperty) {
			property = (MdfDatasetProperty) mdfElement;
		}
		return property;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getColumnIndex()
	 */
	public int getColumnIndex() {
		int index = -1;
		Widget tableWidget = getWidget().getParent();
		if (tableWidget != null) {
			index = tableWidget.getContents().indexOf(getWidget());
		}
		return index;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getBasedOnGroup()
	 */
	public String getBasedOnGroup() {
		String groupName = "";
		for (ITableGroup group : getTable().getGroups()) {
			if (group.isUsedForDynamicColumn()) {
				groupName = group.getColumnName();
				break;
			}
		}
		return groupName;

	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getColumnName()
	 */
	public String getColumnName() {
		return getPropertyValue(COLUMN_NAME_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getColumnType()
	 */
	public Property getColumnType() {
		return getProperty(COLUMN_TYPE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getComputation()
	 */
	public Property getComputation() {
		return getProperty(COLUMN_COMPUTATION_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getContents()
	 */
	public List<Widget> getContents() {
		return getWidget().getContents();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getDisplayType()
	 */
	public Property getDisplayType() {
		return getProperty(COLUMN_DISPLAY_TYPE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getId()
	 */
	public Property getId() {
		return getProperty(COLUMN_ID_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getLocked()
	 */
	public Property getLocked() {
		return getProperty(COLUMN_LOCKED_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getMaxCharacters()
	 */
	public Property getMaxCharacters() {
		return getProperty(COLUMN_MAX_CHARACTERS_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getParameters()
	 */
	public List<String> getParameters() {
		return doGetParameters();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getSortable()
	 */
	public Property getSortable() {
		return getProperty(COLUMN_SORTABLE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getTable()
	 */
	public ITable getTable() {
		ITable table = null;
		Widget parent = getWidget().getParent();
		if (parent != null) {
			return new Table(parent);
		}
		return table;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getText()
	 */
	public Property getText() {
		return getProperty(COLUMN_TEXT_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getToolTip()
	 */
	public Property getToolTip() {
		return getProperty(COLUMN_TOOLTIP_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getWidth()
	 */
	public Property getWidth() {
		return getProperty(COLUMN_WIDTH_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getWrap()
	 */
	public Property getWrap() {
		return getProperty(COLUMN_WRAP_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getXWidth()
	 */
	public Property getXWidth() {
		return getProperty(COLUMN_XWIDTH_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#hasItem()
	 */
	public boolean hasItem() {
		for (Widget w : getWidget().getContents()) {
			if (TABLE_COLUMN_ITEM_TYPE.equals(w.getTypeName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isBoundToDomain()
	 */
	public boolean isBoundToDomain() {
		return getWidget().isDomainWidget();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isComputed()
	 */
	public boolean isComputed() {
		return !isBoundToDomain() && !isPlaceHolder() && !isDynamic();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isDynamic()
	 */
	public boolean isDynamic() {
		return getColumnType().getValue().equals(TABLE_COLUMN_DYNAMIC);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isPlaceHolder()
	 */
	public boolean isPlaceHolder() {
		return getColumnType().getValue().equals(TABLE_COLUMN_PLACEHOLDER);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isDomain()
	 */
	public boolean isDomain() {
		return getColumnType().getValue().equals(TABLE_COLUMN_DOMAIN);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isFirst()
	 */
	public boolean isFirst() {
		return getColumnIndex() == 0;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isLast()
	 */
	public boolean isLast() {
		return getColumnIndex() == (getTable().getColumnCount() - 1);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#setColumnName(java.lang.String)
	 */
	public void setColumnName(String newValue) {
		setPropertyValue(COLUMN_NAME_PROPERTY, newValue);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#setColumnType(int)
	 */
	public void setColumnType(int newValue) {
		setPropertyValue(COLUMN_TYPE_PROPERTY, newValue);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#setParameters(java.util.List)
	 */
	public void setParameters(List<String> parameters) {
		doSetParameters(parameters);
	}

	/**
	 * Constructs a new TableSort Widget Adapter
	 * 
	 * @param widget
	 *            the adapted widget
	 */
	public TableColumn(Widget widget) {
		super(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isDisplayGrouping()
	 */
	public boolean isDisplayGrouping() {
		Property p = getProperty(COLUMN_DISPLAYGROUPING_PROPERTY);
		return (p != null) ? p.getBooleanValue() : false;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getVisibility()
	 */
	public Property getVisibility() {
		return getProperty(COLUMN_VISIBILITY_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isNeverVisible()
	 */
	public boolean isNeverVisible() {
		Property property = getVisibility();
		String value = property.getValue();
		if (COLUMN_VISIBILITY_NEVER_VALUE.equals(value)) {
			return true;
		}
		return false;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getItems()
	 */
	public List<ITableColumnItem> getItems() {
		
		WidgetType type = getWidget().getLibrary().findWidgetType(TABLE_COLUMN_ITEM_TYPE);
		
		// Algorithm: walk the subtree and collect all widgets having the type TABLE_COLUMN_ITEM_TYPE
		List<ITableColumnItem> items = new ArrayList<ITableColumnItem>();
		Stack<Widget> stack = new Stack<Widget>();
		stack.push(this.getWidget());
		while (!stack.isEmpty()) {
			Widget w = stack.pop();
			for (Widget child : w.getContents()) {
				//if (child.getTypeName().equals(TABLE_COLUMN_ITEM_TYPE)) {
				if (child.getType().isInstanceOf(type)) {
					items.add(new TableColumnItem(child));
				} else {
					stack.push(child);
				}
			}
		}
		return items;	
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#removeItem(com.odcgroup.page.model.widgets.table.ITableColumnItem)
	 */
	public void removeItem(ITableColumnItem item) {
		Widget widget = item.getWidget();
		List<Widget> children = getWidget().getContents();
		int index = children.indexOf(widget);
		if (index != -1) {
			children.remove(index);
		}

	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#addItem()
	 */
	public ITableColumnItem addItem() {
		ITableColumnItem item = createItem();
		String name = "";
		if (isBoundToDomain()) {
			name = getPropertyValue("domainAttribute");
		} else if (isDynamic()) {
			name = ""; // No default value for dynamic column
		} else {
			name = getColumnName();
		}
		item.setColumn(name);
		Widget widget = item.getWidget();
		List<Widget> children = getWidget().getContents();
		int index = children.indexOf(widget);
		if (index == -1) {
			children.add(widget);
		}
		return item;
	}

	@Override
	public Widget getCheckbox() {
		if (isPlaceHolder()) {
			Widget widget = getWidget();
			return findCheckBox(widget);
		}
		return null;
	}

	/**
	 * @param widget
	 * @return
	 */
	private Widget findCheckBox(Widget widget) {
		List<Widget> children = widget.getContents();
		for (Widget child : children) {
			if (WidgetTypeConstants.CHECKBOX.equals(child.getTypeName())) {
				return child;
			} else if (WidgetTypeConstants.CONDITIONAL.equals(child
					.getTypeName())) {
				List<Widget> cbodies = child.getContents();
				for (Widget cbody : cbodies) {
					Widget wid = findCheckBox(cbody);
					if (wid != null) {
						return wid;
					}
				}
			}
		}
		return null;
	}
	@Override
	public ITableGroup addGroup() {
		ITableGroup group = createGrouping();
		Widget widget = group.getWidget();
		getWidget().getContents().add(widget);
		return group;
	}
	
	@Override
	public void addGroups(List<ITableGroup> groups) {
		Collections.sort(groups, new TableGroupRankSorter());
		for(ITableGroup grp : groups) {
			Widget widget = grp.getWidget();
			getWidget().getContents().add(widget);
		}		
	}

	@Override
	public void removeGroup(ITableGroup group) {		
		getWidget().getContents().remove(group.getWidget());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ITableGroup> getGroups() {
		return (List<ITableGroup>) CollectionUtils.collect(CollectionUtils.select(getWidget().getContents(),
				new Predicate() {
					public boolean evaluate(Object object) {
						return ((Widget) object).getTypeName().equals(TABLE_GROUP_WIDGET_TYPE);
					}
				}), new Transformer() {
			public Object transform(Object object) {
				return new TableGroup((Widget) object);
			}
		});
	}
	
	/**
	 * @return tableColumnItem
	 */
	private ITableGroup createGrouping() {
		
		/** Uses a script to create a table-column-widget. */
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(XGUI_LIBRARY_NAME);
		WidgetTemplate template = library.findWidgetTemplate(TABLE_GROUP_WIDGET_TEMPLATE);
		WidgetFactory factory = new WidgetFactory();

		/** Creates the new column widget from its template. */
		Widget group = factory.create(template);
		TableGroup tableGroup = new TableGroup(group);
		tableGroup.setRank(TableHelper.getNextGroupRank(getTable()));
		return tableGroup;
	}

	@Override
	public ITableGroup getGroup(int groupIndex) {
		if(getGroups() != null && getGroups().get(groupIndex) != null)
			return getGroups().get(groupIndex);
		return null;
	}
	
	public void moveGroupUp(final int index) {
		checkIndex(index, getGroups().size());
		ITableGroup grp = getGroups().get(index);
		if((index - 1) > 0) {
			getGroups().add(index, getGroup(index - 1));
			getGroups().add(index - 1, grp);
		}
	}
	
	public void moveGroupDown(final int index) {
		checkIndex(index, getGroups().size());
		ITableGroup grp = getGroups().get(index);
		if((index + 1) <= getGroups().size()) {
			getGroups().add(index, getGroup(index + 1));
			getGroups().add(index + 1, grp);
		}
	}

	private void checkIndex(int index, int upperBound) {
		if ((index < 0) || (index >= upperBound)) {
			StringBuilder builder = new StringBuilder();
			builder.append("The group index: ");
			builder.append(index);
			builder.append(" is not in the range [0,");
			builder.append(upperBound);
			builder.append("]");
			throw new IndexOutOfBoundsException(builder.toString());
		}
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITable#moveColumnRight(int)
	 */
	public void moveColumnRight(final int index) {
		checkIndex(index, getGroups().size());
		getWidget().getContents().move(index+1, index);
	}

	@Override
	public void setDisplayGrouping(boolean dispValue) {
		if(dispValue)
			setPropertyValue(COLUMN_DISPLAYGROUPING_PROPERTY, "true");
		else
			setPropertyValue(COLUMN_DISPLAYGROUPING_PROPERTY, "false");
	}
}
