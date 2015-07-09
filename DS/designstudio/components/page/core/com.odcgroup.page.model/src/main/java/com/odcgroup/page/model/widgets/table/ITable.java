package com.odcgroup.page.model.widgets.table;

import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * The interface ITable provides services to ease the management of a Table
 * Widget. In practice it will be used to implements specific UI & related
 * commands, and also to implements specific PSM transformers.
 * 
 * @author atr
 */
public interface ITable extends IWidgetAdapter {

	/**
	 * Adds a new Aggregate object in the table
	 * 
	 * @param aggregate
	 *            the aggregate object to be added
	 */
	void add(ITableAggregate aggregate);

	/**
	 * Adds a new TableExtra object in the table
	 * 
	 * @param extra
	 *            the "extra" object to be added
	 */
	void add(ITableExtra extra);

	/**
	 * Adds a new KeepFilter object in the table
	 * 
	 * @param keepFilter
	 *            the keepfilter object to be added
	 */
	void add(ITableKeepFilter keepFilter);

	/**
	 * Adds a new Sort object in the table
	 * 
	 * @param sort
	 *            the sort object to be added
	 */
	void add(ITableSort sort);

	/**
	 * @return a list of aggregated columns, or an empty list if none have been
	 *         defined.
	 */
	List<ITableAggregate> getAggregatedColumns();
	
	/**
	 * @return the column of type 'Placeholder' having its attribute 
	 * 'checkbox' set to true, or {@code null} if this column is not defined.
	 */
	ITableColumn getMultiSelectionColumn();

	/**
	 * @param index
	 *            the position of the column (zero based)
	 * @return return the column given its index (zero based)
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	ITableColumn getColumn(int index);
	
	
	/**
	 * Returns the column given a column name, or {@code null} if the designated 
	 * column does not exists in the table.
	 * 
	 * @param columnName the name of a column
	 *  
	 * @return ITableColumn or {@code null}
	 */
	ITableColumn getColumn(String columnName);

	/**
	 * @return the number of columns in the table
	 */
	int getColumnCount();

	/**
	 * @return the list of all columns in the table
	 */
	List<ITableColumn> getColumns();

	/**
	 * @param index
	 *            the position of the column (zero based)
	 * @return the column widget at the specified position
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	Widget getColumnWidget(int index);

	/**
	 * @return a list of widget that represents the columns in the table, or an
	 *         empty list if the table contains no columns
	 */
	List<Widget> getColumnWidgets();

	/**
	 * @param columnIndex
	 *            the index of the column in the table
	 * @return the width of the designated column
	 */
	int getColumnWidth(int columnIndex);

	/**
	 * @return the property css class
	 */
	Property getCssClass();
	
	/**
	 * @return the unique table identifier
	 */
	String getID();

	/**
	 * @return the expand level property
	 */
	Property getExpandLevel();

	/**
	 * @return a list of defined Extra, or an empty list if none have been
	 *         defined.
	 */
	List<ITableExtra> getExtras();

	/**
	 * @return fixed-page-size property
	 */
	Property getFixedPageSize();

	/**
	 * @return a list of defined groups, or an empty list if none have been
	 *         defined.
	 */
	List<ITableGroup> getGroups();

	/**
	 * @return a list of defined groups ordered by rank, or an empty list if none have been
	 *         defined.
	 */
	List<ITableGroup> getGroupsByRank();
	
	/**
	 * @return the height property
	 */
	Property getHeight();

	/**
	 * This property reflects how multiple filters are connected
	 * 
	 * @return the property keep-filter-logic
	 */
	Property getKeepFilterLogic();

	/**
	 * @return a list of defined keep filters, or an empty list if none have
	 *         been defined.
	 */
	List<ITableKeepFilter> getKeepFilters();

	/**
	 * @return the the value of the property model-reference
	 */
	String getModelReference();

	/**
	 * @return the value of the attribute page-size
	 */
	Property getPageSize();

	/**
	 * @return the preview-size property
	 */
	Property getPreviewSize();

	/**
	 * @return the table rendering mode
	 */
	Property getRenderingMode();

	/**
	 * @return the list of defined Sort objects, or an empty list if none have
	 *         been defined.
	 */
	List<ITableSort> getSorts();

	/**
	 * @return the summary-show-count property
	 */
	Property getSummaryShowCount();
	
	/**
	 * @return the table-count-tree-elements property
	 */
	Property getCountTreeElements();

	/**
	 * @return the vertical alignement property
	 */
	Property getVerticalAlignment();

	/**
	 * @return the the value of the property view-reference
	 */
	String getViewReference();

	/**
	 * @return the width property
	 */
	Property getWidth();
	
	/**
	 * @return {@code true} if the table has at least one Aggregate defined, otherwise
	 *         it returns {@code false}
	 */
	boolean hasAggregatedColumns();
	
	/**
	 * @return {@code true} if the table has at least one Extra defined, otherwise
	 *         it returns {@code false}
	 */
	boolean hasExtras();

	/**
	 * @return {@code true} if the table has at least one Group defined, otherwise
	 *         it returns {@code false}
	 */
	boolean hasGroups();

	/**
	 * @return {@code true} if the table has at least one KeepFilter defined, otherwise
	 *         it returns {@code false}
	 */
	boolean hasKeepFilters();
	
	/**
	 * @return {@code true} if the table has its property 'expand/collapse all' set to true
	 *         otherwise it returns {@code false}
	 */
	boolean hasExpandCollapseAll();

	/**
	 * @return {@code true} if the table has its propert 'filter' set to true, otherwise 
	 *         it returns {@code false} 
	 */
	boolean hasFilter();

	/**
	 * @return {@code true} if the table has its property 'select/ deselect all' set to true
	 *         otherwise it returns {@code false}
	 */
	boolean hasSelectDeselectAll();

	/**
	 * @return {@code true} if the table has a placeholder column with its attribute 'checkbox'
	 *         set to true, otherwise it returns {@code false}
	 */
	boolean hasMultiSelection();
	
	/**
	 * @return {@code true} if the table has at least one Sort defined, otherwise
	 *         it returns {@code false}
	 */
	boolean hasSorts();
	
	/**
	 * @return {@code true} tells if the checkbox is shown at group/hierarchy level, else
	 *         it returns {@code false}
	 */
	boolean displayCheckboxOnTreeNodes();
	
	/**
	 * @return {@code true} tells if only one checkbox can be selected for multiple checkboxes
	 * designed in a row, else it returns {@code false}
	 */
	boolean makeCheckboxesExclusive();
	
	/**
	 * Insert a new column to the right in the table
	 * 
	 * @return the position of the new column in the table
	 */
	int insertColumn();
	
	/**
	 * Insert the given column to the right in the table
	 * 
	 * @param column
	 * @return the position of the inserted column in the table
	 */
	int insertColumn(ITableColumn column);
	
	/**
	 * Inserts the given column in the table at the specified position.
	 * Shifts the current column and the subsequent columns (if any) to the right
	 * 
	 * @param column
	 * @param index
	 */
	void insertColumnAt(ITableColumn column, int index);

	/**
	 * Inserts a new column in the table at the specified position in the table.
	 * Shifts to the right the column currently at that position (if any) and
	 * any subsequent columns to the right (adds one to their indices).
	 * 
	 * @param index
	 *            the position of the new column
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index > size())
	 */
	void insertColumnAt(int index);

	/**
	 * Inserts a new column with the designated widget in the table just at the
	 * right of the specified position (index+1) in the table. Shifts to the
	 * right the column currently at the (index+1) (if any) and any subsequent
	 * columns to the right (adds one to their indices).
	 * 
	 * @param index
	 *            the position of the new column
	 * @param columnWidget
	 *            the widget
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index > size())
	 */
	void insertColumnAt(int index, Widget columnWidget);

	/**
	 * Inserts a new column in the table at the left of specified position (-1)
	 * in the table. Shifts to the right the column currently at the position
	 * (if any) and any subsequent columns to the right (adds one to their
	 * indices).
	 * 
	 * @param index
	 *            the insertion point
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	void insertColumnLeft(int index);
	
	/**
	 * Inserts the given column in the table at the left of specified position (-1)
	 * in the table. Shifts to the right the column currently at the position
	 * (if any) and any subsequent columns to the right (adds one to their
	 * indices).
	 * 
	 * @param column 	 * 
	 * @param index
	 *            the insertion point
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	void insertColumnLeft(ITableColumn column, int index);

	/**
	 * Inserts a new column in the table just at the right of the specified
	 * position (+1) in the table. Shifts to the right the column currently at
	 * the (position+1) (if any) and any subsequent columns to the right (adds
	 * one to their indices).
	 * 
	 * @param index
	 *            the insertion point
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	void insertColumnRight(int index);
	
	/**
	 * Inserts the given column in the table just at the right of the specified
	 * position (+1) in the table. Shifts to the right the column currently at
	 * the (position+1) (if any) and any subsequent columns to the right (adds
	 * one to their indices).
	 * 
	 * @param column  
	 * @param index
	 *            the insertion point
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	void insertColumnRight(ITableColumn column, int index);

//	/**
//	 * @return {@code true} if the columns header is visible, otherwise it
//	 *         returns {@code false}.
//	 */
//	boolean isColumnsHeaderVisible();

	/**
	 * @return the value of the sticky flag
	 */
	boolean isDumpModelEnabled();
	
	/**
	 * @return is the row highlight enabled
	 */
	boolean isRowHighLighted();

	/**
	 * Moves the column with the given index to the left. Do nothing if the
	 * specified position correspond to the first column
	 * 
	 * @param index
	 *            position of the column to move
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	void moveColumnLeft(int index);

	/**
	 * Moves the column with the given index to the right. Do nothing if the
	 * specified position correspond to the last column
	 * 
	 * 
	 * @param index
	 *            position of the column to move
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	void moveColumnRight(int index);

	/**
	 * Removes the specified Aggregate object from the table. Do nothing if it
	 * is not found in the table
	 * 
	 * @param aggregate
	 *            the Aggregate object to be removed
	 */
	void remove(ITableAggregate aggregate);

	/**
	 * Removes the specified TableExtra object from the table. Do nothing if it
	 * is not found in the table
	 * 
	 * @param extra
	 *            the TableExtra object to be removed
	 */
	void remove(ITableExtra extra);

	/**
	 * Removes the specified Group object from the table. Do nothing if it is
	 * not found in the table
	 * 
	 * @param group
	 *            the Group object to be removed
	 */
	void remove(ITableGroup group);

	/**
	 * Removes the specified KeepFilter object from the table. Do nothing if it
	 * is not found in the table
	 * 
	 * @param keepFilter
	 *            the KeepFilter object to be removed
	 */
	void remove(ITableKeepFilter keepFilter);

	/**
	 * Removes the specified Sort object from the table. Do nothing if it is not
	 * found in the table
	 * 
	 * @param sort
	 *            the Sort object to be removed
	 */
	void remove(ITableSort sort);

	/**
	 * Removes from the table the column at the specified position and returns
	 * the underlying column widget. Shifts to the left any subsequent columns
	 * (position+1) to the right (removes one to their indices).
	 * 
	 * @param index
	 *            position of the column to remove
	 * @return the column widget
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	Widget removeColumn(int index);

	/**
	 * @param newColumnCount
	 * @return the list of column widgets
	 */
	List<Widget> setColumnCount(int newColumnCount);

	/**
	 * Changes the width of the designated column
	 * 
	 * @param columnIndex
	 *            the index of the column to be updated
	 * @param newWidth
	 *            the new value of the width
	 */
	void setColumnWidth(int columnIndex, int newWidth);

	/**
	 * @return the filter set id
	 */
	String getFilterSetId();
	
	/**
	 * IS show Number of Items 
	 */
	boolean isNumItems();
	
	/**
	 * @return
	 */
	boolean isEditable();
	
	/**
	 * @return the column with display-grouping set to true
	 */
	ITableColumn getDisplayGroupingColumn();
	/**
	 * 
	 * @return true if show column selector set to true.
	 * 
	 */
	
	boolean hasShowColumnSelector();

	void moveGroups(int sourceIndex, int destIndex);
}
