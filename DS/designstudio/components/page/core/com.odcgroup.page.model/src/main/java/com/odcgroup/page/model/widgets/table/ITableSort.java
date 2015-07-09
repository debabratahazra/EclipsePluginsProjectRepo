package com.odcgroup.page.model.widgets.table;

/**
 * The interface ITableKeepFilter provides services to ease the management of a
 * Sort Element in a Table Widget. In practice it will be used to implements
 * specific UI & related commands, and also to implements specific PSM
 * transformers.
 * <p>
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableSort extends ITableFeature, IWidgetAdapter {

	/**
	 * @param newValue
	 */
	void setColumnName(String newValue);

	/**
	 * @param newValue
	 */
	void setSortingDirection(String newValue);

	/**
	 * @return the sort direction
	 */
	String getSortingDirection();

	/**
	 * @return {@code true} if the sorting direction is 'ascending', otherwise
	 *         it returns {@code false}
	 */
	boolean isAscending();

	/**
	 * @return {@code true} if the sorting direction is 'descending', otherwise
	 *         it returns {@code false}
	 */
	boolean isDescending();

	/**
	 * Changes the rank of this sort definition
	 * 
	 * @param newValue
	 */
	public void setRank(int newValue);

	/**
	 * @return the rank of this sort definition
	 */
	int getRank();

}
