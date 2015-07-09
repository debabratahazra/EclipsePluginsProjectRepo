package com.odcgroup.page.model.widgets.table;

import java.util.Comparator;


/**
 * Compare the rank of two {@code ITableGroup} objects
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableSortSorter implements Comparator<ITableSort> {

	/*
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(ITableSort left, ITableSort right) {
		int leftRank = left.getRank();
		int rightRank = right.getRank();
		return (leftRank < rightRank ? -1 : (leftRank == rightRank ? 0 : 1));

	}
}
