package com.odcgroup.page.model.widgets.table;

import java.util.List;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * The interface ITableColumn provides services to ease the management of a
 * Column in a Table Widget. In practice it will be used to implements specific
 * UI & related commands, and also to implements specific PSM transformers.
 * <p>
 * 
 * A computed column cannot be bound to an attribute of the domain. In other
 * words, whenever the method {@code isComputed()} returns the value {@code
 * true} then the method {@code isBoundToDomain()} returns the value {@code
 * false}. The same applies for all the columns bound to an attribute of the
 * domain, they cannot be computed.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableColumn extends IWidgetAdapter {
	
	/**
	 * Add an item widget in the content of the column
	 * @return item
	 */
	ITableColumnItem addItem();
		
	/**
	 * @return the list of all column items contained in the column.
	 */
	List<ITableColumnItem> getItems();

	/**
	 * @return {@code true} can be move to the left, otherwise it returns
	 *         {@code false}
	 */
	boolean canMoveLeft();

	/**
	 * @return {@code true} can be move to the right, otherwise it returns
	 *         {@code false}
	 */
	boolean canMoveRight();

	/**
	 * @param parameter
	 *            parameter
	 * @return the domain attribute bound to this column or {@code null}
	 */
	MdfDatasetProperty getDatasetProperty(String parameter);

	/**
	 * Returns the name of the group a dynamic column is based on. An empty
	 * string is returned if this column is not dynamic.
	 * 
	 * @return the name of the group a dynamic column is based on. 
	 */
	String getBasedOnGroup();
	
	/**
	 * @return the index of the column in the table
	 */
	int getColumnIndex();
	
	/**
	 * @return the name of this column
	 */
	String getColumnName();

	/**
	 * @return the type of this column
	 */
	Property getColumnType();

	/**
	 * @return the property computation
	 */
	Property getComputation();

	/**
	 * @return the widgets owned by this column
	 */
	List<Widget> getContents();


	/**
	 * @return the value of the property display-type
	 */
	Property getDisplayType();
	
	/**
	 * @return the property id
	 */
	Property getId();

	/**
	 * @return the property Locked
	 */
	Property getLocked();

	/**
	 * @return the value of the max-characters property
	 */
	Property getMaxCharacters();

	/**
	 * @return the list of parameters of the computation function
	 */
	List<String> getParameters();

	/**
	 * @return the property sortable
	 */
	Property getSortable();
	
	/**
	 * @return the property visibility
	 */
	Property getVisibility();

	/**
	 * @return the table that owns this column, or {@code null} if this column
	 *         is not yet attached to a table.
	 */
	ITable getTable();

	/**
	 * @return the value of the text property
	 */
//	Property getText();

	/**
	 * @return the value of the toolTip property
	 */
//	Property getToolTip();
	
	/**
	 * @return the property width
	 */
	Property getWidth();

	/**
	 * @return the property wrap
	 */
	Property getWrap();

	/**
	 * @return the property x-width
	 */
	Property getXWidth();

	/**
	 * @return {@code true} if the column has an item widget inside, otherwise
	 *         it return {@code false}
	 */
	boolean hasItem();

	/**
	 * @return {@code true} if this column is bound to an attribute of the
	 *         domain, otherwise it return {@code false}
	 */
	boolean isBoundToDomain();

	/**
	 * @return {@code true} if this column is computed, otherwise it returns
	 *         {@code false}
	 */
	boolean isComputed();
	
	/**
	 * @return {@code true} if this column is dynamic, otherwise it returns
	 *         {@code false}
	 */
	boolean isDynamic();
	
	/**
	 * @return {@code true} if this column is a placeholder, otherwise it returns
	 *         {@code false}
	 */
	boolean isPlaceHolder();
	
	/**
	 * @return {@code true} if this column is a domain, otherwise it returns
	 *         {@code false}
	 */
	public boolean isDomain();

	/**
	 * @return {@code true} if the value of its column index is zero, otherwise
	 *         it returns {@code false}
	 */
	boolean isFirst();

	/**
	 * @return {@code true} if the value of its column index is equals to the
	 *         number of columns (in the table) minus 1, otherwise it returns
	 *         {@code false}
	 */
	boolean isLast();
	
	/**
	 * @return {@code true} if the column is part of the filter, otherwise it
	 *         returns {@code false}.
	 */
	public boolean isPartOfTheFilter();
	
	/**
	 * @return {@code true} if the user can click on the column to change the sort order, 
	 *         otherwise it returns {@code false}
	 */
	boolean isSortable();
	
	/**
	 * @return {@code true} if the column is to display grouping, 
	 *         otherwise it returns {@code false}
	 */
	boolean isDisplayGrouping();
	
	/**
	 * @return {@code true} if the column is never visible, 
	 *         otherwise it returns {@code false}
	 */
	boolean isNeverVisible();	

	/**
	 *  Remove the item widget from the content of the column
	 * @param item 
	 */
	void removeItem(ITableColumnItem item);

	/**
	 * @param newValue
	 */
	void setColumnName(String newValue);

	/**
	 * @param newValue
	 */
	void setColumnType(int newValue);

	/**
	 * @param parameters
	 */
	void setParameters(List<String> parameters);
	
	/**
	 * @return
	 */
	Widget getCheckbox();
	

	/**
	 * @return
	 */
	ITableGroup addGroup();
	
	/**
	 * @param group
	 */
	void removeGroup(ITableGroup group);
	
	
	/**
	 * @return a list of defined groups, or an empty list if none have been
	 *         defined.
	 */
	List<ITableGroup> getGroups();

	ITableGroup getGroup(int groupIndex);

	void moveGroupDown(int groupIndex);

	void moveGroupUp(int groupIndex);

	void addGroups(List<ITableGroup> grp);

	void setDisplayGrouping(boolean b);
}
