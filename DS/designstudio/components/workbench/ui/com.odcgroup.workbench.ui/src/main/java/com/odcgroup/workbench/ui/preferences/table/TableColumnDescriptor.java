/**
 * 
 */
package com.odcgroup.workbench.ui.preferences.table;

import java.util.Comparator;

import org.eclipse.swt.SWT;

/**
 * A table column descriptor
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnDescriptor {

	/** the alignment flag */
	private int alignment = SWT.LEFT;
	
	/** the editable flag */
	private boolean editable = false;
	
	/** The name of the column */
	private String name;
	
	/** The resizable flag */
	private boolean resizable = true;

	/** the sortable flag */
	private boolean sortable = false;
	
	/** the comparator for sorting */
	private Comparator comparator;
	
	/** the style */
	private int style = SWT.NONE;
	
	/** the with of the column */
	private int width = 100;

	/**
	 * @return the alignement
	 */
	public final int getAlignment() {
		return this.alignment;
	}
	
	/**
	 * @param alignment
	 */
	public final void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	/**
	 * @return the name of the column
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @return {@code true} if the column is editable, otherwise it returns
	 *         {@code false}
	 */
	public final boolean isEditable() {
		return this.editable;
	}
	
	/**
	 * @param editable
	 */
	public final void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	/**
	 * @return {@code true} if the column is resizable, otherwise it returns
	 *         {@code false}
	 */
	public final boolean isResizable() {
		return this.resizable;
	}
	
	/**
	 * @param resizable
	 */
	public final void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	/**
	 * @return {@code true} if the column is sortable, otherwise it returns
	 *         {@code false}
	 */
	public final boolean isSortable() {
		return this.sortable;
	}
	
	/**
	 * @param sortable
	 */
	public final void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	
	/**
	 * @return the comparator
	 */
	public final Comparator getComparator() {
		return this.comparator;
	}

	/**
	 * @return the style
	 */
	public final int getStyle() {
		return this.style;
	}
	
	/**
	 * @param style
	 */
	public final void setStyle(int style) {
		this.style = style;
	}

	/**
	 * @return the width
	 */
	public final int getWidth() {
		return this.width;
	}
	
	/**
	 * @param width
	 */
	public final void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @param name
	 */
	public TableColumnDescriptor(String name) {
		this.name = name;
	}
	
	/**
	 * @param name
	 * @param width
	 */
	public TableColumnDescriptor(String name, int width) {
		this.name = name;
		this.width = width;
	}

	/**
	 * @param name
	 * @param width
	 * @param editable
	 */
	public TableColumnDescriptor(String name, int width, boolean editable) {
		this.name = name;
		this.width = width;
		this.editable = editable;
	}
	
	/**
	 * @param name
	 * @param width
	 * @param editable
	 * @param sortable
	 */
	public TableColumnDescriptor(String name, int width, Comparator c) {
		this.name = name;
		this.width = width;
		this.comparator = c;
		this.sortable = c != null;
	}
	
}
