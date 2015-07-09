package com.odcgroup.translation.ui.editor.controls;

import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.ui.editor.model.IMultiTranslationTable;
import com.odcgroup.translation.ui.editor.model.ITranslationTableColumn;
import com.odcgroup.translation.ui.editor.model.ITranslationTableModel;

/**
 *
 * @author pkk
 *
 */
public abstract class MultiTranslationTable implements IMultiTranslationTable {
	
	/** set of commands descriptors */
	private TableViewer tableViewer;
	private ITranslationPreferences preferences;
	private CLabel matchCountLabel;
	
	private LabelProvider modelLabelProvider = null;

	/**
	 * @param body
	 */
	public final void createControls(Composite body, ITranslationPreferences preferences) {

        this.preferences = preferences;
        
        Composite tab = new Composite(body, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		tab.setLayout(layout);
		tab.setLayoutData(new GridData(GridData.FILL_BOTH));	
        
		TableLayoutComposite tableComposite = new TableLayoutComposite(tab, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
        tableComposite.setLayoutData(gd);

        createTableControl(tableComposite, getTableColumns());
        
        Composite comp = new Composite(tab, SWT.NONE);
		layout = new GridLayout(2, false);
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        CLabel label = new CLabel(comp, SWT.LEFT | SWT.TOP);
		label.setBackground(comp.getBackground());
		label.setFont(comp.getFont());
		label.setText("No. of matches found :");
		
		matchCountLabel = new CLabel(comp, SWT.TOP);
		matchCountLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		matchCountLabel.setBackground(comp.getBackground());
		matchCountLabel.setFont(comp.getFont());
		matchCountLabel.setText("0");
	}
	
	/**
	 * @param body
	 */
	protected void createTableControl(TableLayoutComposite tableComposite, List<ITranslationTableColumn> columns) {
		
		Table table = new Table(tableComposite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION
				| SWT.LINE_SOLID | SWT.VIRTUAL | SWT.SELECTED);
		GridData gd = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(gd);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				Double dou = new Double(event.gc.getFontMetrics().getHeight() * 1.12);
				event.height = dou.intValue();
			}
		});

		TableColumn tColumn = null;
		for (final ITranslationTableColumn column : columns) {
			tColumn = new TableColumn(table, column.getStyle());
			tColumn.setText(column.getText());
			tColumn.setResizable(column.isResizeable());
			tableComposite.addColumnData(new ColumnWeightData(column.getWeight()));
			Locale locale = column.getLocale();
			if (locale != null) {
				tColumn.addSelectionListener(new ColumnSelectionListener(new TranslationComparator(locale)));
			}
		}		
		// sort on the translation owner
		TranslationComparator comp = new TranslationComparator(null);
		comp.toggleDirection();
		table.getColumns()[0].addSelectionListener(new ColumnSelectionListener(comp));
		
		tableViewer = new TableViewer(table) {
			@Override
			public void setItemCount(int count) {
				super.setItemCount(count);
				if (matchCountLabel != null)
					matchCountLabel.setText(count+"");
			}
			
		};	
		
		tableViewer.setContentProvider(getTranslationTableContentProvider());
		MultiTranslationTableLabelProvider labelProvider = (MultiTranslationTableLabelProvider)getTranslationTableLabelProvider(preferences, table);
		labelProvider.setModelLabelProvider(modelLabelProvider);
    	tableViewer.setLabelProvider(labelProvider);
    	tableViewer.setUseHashlookup(true);
    	
	}
	
	/**
	 * @return
	 */
	protected abstract MultiTranslationTableContentProvider getTranslationTableContentProvider();
	
	/**
	 * @return
	 */
	protected abstract ITableLabelProvider getTranslationTableLabelProvider(ITranslationPreferences preferences, Table table);
	

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.IMultiTranslationTable#dispose()
	 */
	@Override
	public void dispose() {
		tableViewer.getContentProvider().dispose();
		tableViewer.getLabelProvider().dispose();
		tableViewer.getTable().dispose();
		tableViewer = null;
	}
	
	/**
	 * @return the preferences
	 */
	public ITranslationPreferences getPreferences() {
		return preferences;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.IMultiTranslationTable#setTranslations(com.odcgroup.translation.ui.editor.model.ITranslationTableModel)
	 */
	public void setTranslations(ITranslationTableModel tableModel) {		
		tableViewer.setInput(tableModel);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.IMultiTranslationTable#getTableViewer()
	 */
	@Override
	public TableViewer getTableViewer() {
		return tableViewer;
	}	

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.IMultiTranslationTable#setModelLabelProvider(org.eclipse.jface.viewers.LabelProvider)
	 */
	@Override
	public void setModelLabelProvider(LabelProvider labelProvider) {
		this.modelLabelProvider = labelProvider;		
	}
	
	class ColumnSelectionListener extends SelectionAdapter {
		
		private TranslationComparator comparator;
		private boolean firsttime = true;
		
		public ColumnSelectionListener(TranslationComparator comparator) {
			this.comparator = comparator;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			TableColumn col = (TableColumn) e.widget;
			tableViewer.getTable().setSortColumn(col);
			if (!firsttime) {
				comparator.toggleDirection();
			} else {
				firsttime = false;
			}
			int dir = comparator.getSortDirection();
			if (dir == 1) {
				tableViewer.getTable().setSortDirection(SWT.UP);
			} else {
				tableViewer.getTable().setSortDirection(SWT.DOWN);
			}
			((MultiTranslationTableContentProvider)tableViewer.getContentProvider()).setSortOrder(comparator);
		}
		
	}
	
}
