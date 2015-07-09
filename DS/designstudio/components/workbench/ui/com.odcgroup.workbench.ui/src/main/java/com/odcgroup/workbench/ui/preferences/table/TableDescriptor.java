/**
 * 
 */
package com.odcgroup.workbench.ui.preferences.table;

import java.util.ArrayList;

import org.eclipse.swt.SWT;

/**
 * Table descriptor
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableDescriptor implements ITableDescriptor {
	
	/** the column descriptors */
	private TableColumnDescriptor[] columns;

	/** */
	private boolean headerVisible = true;
	
	/** */
	private boolean linesVisible = true;
	
	/** */
	private int heightHint = 0;
	
	/** */
	private int style = SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
	
	/**
	 * @return the number of columns
	 */
	public final int getColumnCount() {
		return columns.length;
	}
	
	/**
	 * @return the table column descriptors
	 */
	public TableColumnDescriptor[] getColumns() {
		return this.columns;
	}
	
	/**
	 * @return the column names
	 */
	public String[] getColumnNames() {
		ArrayList<String> list = new ArrayList<String>();
		for (TableColumnDescriptor column : columns) {
			list.add(column.getName());
		}
		String[] names = new String[list.size()];
		list.toArray(names);
		return names;
	}
	
	/**
	 * @param name
	 * @return TableColumnDescriptor
	 */
	public TableColumnDescriptor getColumn(String name) {
		for (TableColumnDescriptor column : columns) {
			if (column.getName().equals(name)) {
				return column;
			}
		}
		return null;
	}
	
	/**
	 * @return headerVisible
	 */
	public final boolean isHeaderVisible() {
		return this.headerVisible;
	}

	/**
	 * @param visible
	 */
	public final void setHeaderVisible(boolean visible) {
		this.headerVisible = visible;
	}
	
	/**
	 * @return linesVisible
	 */
	public final boolean isLinesVisible() {
		return this.linesVisible;
	}

	/**
	 * @param visible
	 */
	public final void setLinesVisible(boolean visible) {
		this.linesVisible = visible;
	}

	/**
	 * @return heightHint
	 */
	public final int getHeightHint() {
		return this.heightHint;
	}

	/**
	 * @param heightHint
	 */
	public final void setHeightHint(int heightHint) {
		this.heightHint = heightHint;
	}

	/**
	 * @return the style
	 */
	public final int getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public final void setStyle(int style) {
		this.style = style;
	}

	/**
	 * @param columns
	 */
	public TableDescriptor(TableColumnDescriptor... columns) {
		this.columns = columns;
	}
}
