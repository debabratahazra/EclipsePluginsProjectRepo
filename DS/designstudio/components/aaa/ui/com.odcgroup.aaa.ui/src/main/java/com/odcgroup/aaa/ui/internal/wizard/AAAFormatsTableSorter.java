package com.odcgroup.aaa.ui.internal.wizard;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;

/**
 *
 * @author pkk
 *
 */
class AAAFormatsTableSorter extends ViewerSorter {
	
	private int columnIndex;
	// private static final int ASCENDING = 0;
	private static final int DESCENDING = 1;

	private int sortDir = 1;

	/**
	 * 
	 */
	public AAAFormatsTableSorter() {
		this.columnIndex = 0;
		sortDir = DESCENDING;
	}

	/**
	 * @param column
	 */
	public void setColumn(int column) {
		if (column == this.columnIndex) {
			// Same column as last sort; toggle the direction
			sortDir = 1 - sortDir;
		} else {
			// New column; do an ascending sort
			this.columnIndex = column;
			sortDir = DESCENDING;
		}
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public int compare(Viewer viewer, Object e1, Object e2) {
		
		AAAFormatCode formatOne = (AAAFormatCode) e1;
		AAAFormatCode formatTwo = (AAAFormatCode) e2;
		int rc = 0;
		
		switch (columnIndex) {
			case 0:
				rc = formatOne.getCode().compareTo(formatTwo.getCode());
				break;
			case 1:
				rc = formatOne.getFunction().compareTo(formatTwo.getFunction());
				break;
			case 2:
				String denom1 = formatOne.getDenom();
				String denom2 = formatTwo.getDenom();
				denom1 = (denom1 == null) ? "" : denom1;
				denom2 = (denom2 == null) ? "" : denom2;
				rc = denom1.compareTo(denom2);
				break;
			default:
				rc = 0;
		}
		// If descending order, flip the direction
		if (sortDir == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}
}
