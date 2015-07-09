package com.odcgroup.translation.ui.internal.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.translation.ui.views.ITranslationTableCellModifier;
import com.odcgroup.translation.ui.views.ITranslationTableContentProvider;
import com.odcgroup.translation.ui.views.ITranslationTableLabelProvider;
import com.odcgroup.translation.ui.views.table.TranslationTable;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class StandardTranslationTable extends TranslationTable {
	
	private static final int COLUMN_LANGUAGE = 0;
	private static final int COLUMN_LANGUAGE_WEIGHT = 20;
	private static final int COLUMN_LANGUAGE_WIDTH = 80;
	private static final int COLUMN_ORIGIN = 1;
	private static final int COLUMN_ORIGIN_WEIGHT = 20;
	private static final int COLUMN_ORIGIN_WIDTH = 80;
	private static final int COLUMN_MESSAGE = 2;
	private static final int COLUMN_MESSAGE_WEIGHT = 100;
	private static final int COLUMN_MESSAGE_WIDTH = 100;
	
	private void createColumn(final TableViewer tv, TableColumnLayout layout, String header, int weight, int width) {
		TableColumn tc = new TableColumn(tv.getTable(), SWT.LEFT);
		layout.setColumnData(tc, new ColumnWeightData(weight, width));
		tc.setText(header);
	}

	/**
	 * @return ITranslationTableContentProvider
	 */
	protected ITranslationTableContentProvider createTranslationTableContentProvider() {
		return new TranslationTableContentProvider();
	}

	/**
	 * @return ITranslationTableLabelProvider
	 */
	protected ITranslationTableLabelProvider createTranslationTableLabelProvider() {
		return new TranslationTableLabelProvider();
	}

	/**
	 * @return ITranslationTableCellModifier
	 */
	protected ITranslationTableCellModifier createTranslationTableCellModifier() {
		return new TranslationTableCellModifier();
	}

	@Override
	protected void hookTable(final TableViewer viewer) {

		Table table = viewer.getTable();

		/**
		 * Create the columns The mechanism shall support customization
		 */
		String[] columnProperties = { "Language", "Origin", "Translation" };
		
		TableColumnLayout layout = new TableColumnLayout();
		table.getParent().setLayout(layout);
		createColumn(viewer, layout, columnProperties[COLUMN_LANGUAGE], COLUMN_LANGUAGE_WEIGHT, COLUMN_LANGUAGE_WIDTH);
		createColumn(viewer, layout, columnProperties[COLUMN_ORIGIN], COLUMN_ORIGIN_WEIGHT, COLUMN_ORIGIN_WIDTH);
		createColumn(viewer, layout, columnProperties[COLUMN_MESSAGE], COLUMN_MESSAGE_WEIGHT, COLUMN_MESSAGE_WIDTH);

		// Turn on the header and the lines
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(getPreferredFont());

		/**
		 * Create the columns The mechanism shall support customization
		 */

		// create cell editors
		int nbCols = table.getColumnCount();
		CellEditor[] editors = new CellEditor[nbCols];
		for (int kx = 0; kx < nbCols; kx++) {
			editors[kx] = null;
		}

		if(isRichText()) {
			editors[nbCols - 1] = new TextCellEditor(table, SWT.READ_ONLY);
		} else {
			editors[nbCols - 1] = new TextCellEditor(table);
		}
		viewer.setColumnProperties(columnProperties);
		viewer.setCellModifier(getTranslationTableCellModifier());
		viewer.setCellEditors(editors);

	}
	
	@Override
	public void hideOrigin() {
		TableViewer tv = getViewer();
		if (tv != null) {
			Table table = tv.getTable();
			if (table != null && !table.isDisposed()) {
				TableColumn column = table.getColumn(COLUMN_ORIGIN);
				column.setResizable(false);
				TableColumnLayout layout = (TableColumnLayout)table.getParent().getLayout();
				layout.setColumnData(column, new ColumnWeightData(0, 0));
				table.getParent().layout(true);
			}
		}
	}

	@Override
	public void showOrigin() {
		TableViewer tv = getViewer();
		if (tv != null) {
			Table table = tv.getTable();
			if (table != null && !table.isDisposed()) {
				TableColumn column = table.getColumn(COLUMN_ORIGIN);
				column.setResizable(true);
				TableColumnLayout layout = (TableColumnLayout)table.getParent().getLayout();
				layout.setColumnData(column, new ColumnWeightData(COLUMN_ORIGIN_WIDTH));
				table.getParent().layout(true);
				
			}
		}
	}	

	/**
	 * @param parent
	 */
	public StandardTranslationTable(IProject project, Composite parent) {
		super(project, parent);
	}

	@Override
	public void showTable() {
		TableViewer tv = getViewer();
		Composite tableParentComposite = tv.getControl().getParent();
		GridData tableLayoutData = (GridData)tableParentComposite.getLayoutData();
		tableLayoutData.exclude = false;
		tableParentComposite.setVisible(true);		
	}

	@Override
	public void hideTable() {
		TableViewer tv = getViewer();
		Composite tableParentComposite = tv.getControl().getParent();
		GridData tableLayoutData = (GridData)tableParentComposite.getLayoutData();
		tableLayoutData.exclude = true;
		tableParentComposite.setVisible(false);
	}

}
