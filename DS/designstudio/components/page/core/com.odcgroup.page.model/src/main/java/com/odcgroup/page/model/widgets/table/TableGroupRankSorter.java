package com.odcgroup.page.model.widgets.table;

import java.util.Comparator;


/**
 * Compare the rank of two {@code ITableGroup} objects
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableGroupRankSorter implements Comparator<ITableGroup> {

	/*
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(ITableGroup left, ITableGroup right) {
		int leftRank = left.getRank();
		int rightRank = right.getRank();
		return (leftRank < rightRank ? -1 : (leftRank == rightRank ? 0 : 1));

	}
}
