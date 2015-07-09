/**
 * 
 */
package com.odcgroup.workbench.ui.preferences.table;

import java.util.Comparator;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * A Generic Table Viewer
 * 
 * @author atr
 * @since DS 1.40.0
 */
public abstract class TableFieldViewer extends TableViewer {
	
	private class TableFieldSorter extends ViewerSorter {
		
		private int propertyIndex = 0;

		private static final int DESCENDING = 1;

		private int direction = DESCENDING;
		
		private Comparator comparator;

		@Override
		@SuppressWarnings("unchecked")
		public int compare(Viewer viewer, Object left, Object right) {
			int rc = 0;
			if (comparator != null) {
				rc = comparator.compare(left, right);
				// If descending order, flip the direction
				if (direction == DESCENDING) {
					rc = -rc;
				}
			}
			return rc;
		}

		public void setColumn(int column, Comparator c) {
			if (c == null) {
				throw new IllegalArgumentException();
			}
			this.comparator = c;
			if (column == this.propertyIndex) {
				// Same column as last sort; toggle the direction
				direction = 1 - direction;
			} else {
				// New column; do an ascending sort
				this.propertyIndex = column;
				direction = DESCENDING;
			}
		}

		public TableFieldSorter() {
		}

	}
	
	/** the generic table sorter */
	private TableFieldSorter tableFieldSorter;
	
	/**
	 * @return me
	 */
	private TableViewer getViewer() {
		return this;
	}
	
	/**
	 * @return
	 */
	protected TableFieldSorter getTableFieldSorter() {
		if (tableFieldSorter == null) {
			tableFieldSorter = new TableFieldSorter();
		}
		return tableFieldSorter;
	}

	/**
	 * @param table
	 *            the table
	 * @param descriptor
	 *            the column descriptor
	 */
	protected void initTableColumn(Table table, final TableColumnDescriptor descriptor, final int index) {
		
		final TableColumn tc = new TableColumn(table, descriptor.getAlignment());
		tc.setWidth(descriptor.getWidth());
		tc.setResizable(descriptor.isResizable());
		tc.setAlignment(descriptor.getAlignment());
		tc.setText(descriptor.getName());

		// install a sorting mechanism
		if (descriptor.isSortable()) {
			tc.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					TableViewer tv = getViewer();
					getTableFieldSorter().setColumn(index, descriptor.getComparator());
					int dir = tv.getTable().getSortDirection();
					if (tv.getTable().getSortColumn() == tc) {
						dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
					} else {

						dir = SWT.DOWN;
					}
					tv.getTable().setSortDirection(dir);
					tv.getTable().setSortColumn(tc);
					tv.refresh();
				}
			});
		}
		
	}
	
	/**
	 * @param tableDescriptor
	 *            the table descriptor
	 */
	protected void initTable(ITableFieldDescriptor tableDescriptor) {
		
		Table table = getTable();

		int nbColumns = tableDescriptor.getColumnCount();
		CellEditor[] editors = new CellEditor[nbColumns];
		String[] columnProperties = new String[nbColumns];

		int index = 0;
		for (TableColumnDescriptor descriptor : tableDescriptor.getColumns()) {
			columnProperties[index] = descriptor.getName();
			initTableColumn(table, descriptor, index);
			if (descriptor.isEditable()) {
				editors[index] = new TextCellEditor(table);
			}
			index++;
		}
		
		table.setHeaderVisible(tableDescriptor.isHeaderVisible());
		table.setLinesVisible(tableDescriptor.isLinesVisible());

		setContentProvider(getTableContentProvider());
        setLabelProvider(getTableLabelProvider());
        setCellModifier(getTableCellModifier());

        setColumnProperties(columnProperties);
		setCellEditors(editors);
		
	}

	/**
	 * @return ICellModifier
	 */
	abstract protected ICellModifier getTableCellModifier();

	/**
	 * @return IBaseLabelProvider
	 */
	abstract protected IBaseLabelProvider getTableLabelProvider();

	/**
	 * @return IContentProvider
	 */
	abstract protected IContentProvider getTableContentProvider();

	/**
	 * @param parent
	 * @param descriptor
	 */
	public TableFieldViewer(Composite parent, ITableFieldDescriptor descriptor) {
		super(parent, descriptor.getStyle());
		initTable(descriptor);
	}

}
