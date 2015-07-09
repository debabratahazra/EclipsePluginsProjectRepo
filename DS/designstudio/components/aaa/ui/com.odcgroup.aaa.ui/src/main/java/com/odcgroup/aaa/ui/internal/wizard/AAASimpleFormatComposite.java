package com.odcgroup.aaa.ui.internal.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;

/**
 * @author atr
 * @since 1.40.0
 */
class AAASimpleFormatComposite implements PropertyChangeListener {
	
	private AAAImportFacade importFacade;
	private String[] columnLabels;
	private CheckboxTableViewer tableViewer;
	private AAAFormatsTableSorter tableSorter;
	
	private List<AAAFormatCode> allFormatCodes;
	
	public void populateTable(List<AAAFormatCode> allFormatCodes) {
		this.allFormatCodes = allFormatCodes;
		tableViewer.setInput(this.allFormatCodes);
		// automatically resize columns
		Table table = tableViewer.getTable();
		int nbColumns = table.getColumnCount();
		for (int cx=0; cx < nbColumns; cx++) {
			table.getColumn(cx).pack();
		}
	}
	
	public void refreshTable() {
		if (allFormatCodes == null) {
			return;
		}
		
		// Filter the list of all codes
		List<AAAFormatCode> formatCodes = new ArrayList<AAAFormatCode>();
        Pattern p = Pattern.compile(getImportFacade().getRegExpForCurrentCodePattern());
        boolean allFunction = getImportFacade().getSelectedFunction().getProcName() == null;
        String function = getImportFacade().getSelectedFunction().getDisplayName();
        for (AAAFormatCode formatCode : this.allFormatCodes) {
        	if (!allFunction) {
        		if (!formatCode.getFunction().equals(function)) {
        			// Not the selected function, skip this item
        			continue;
        		}
        	}
        	
	        Matcher m = p.matcher(formatCode.getCode());
			if (!m.matches()) {
				// Not selected by the filter, skip this item
				continue;
			}
			
			formatCodes.add(formatCode);
        }
		tableViewer.setInput(formatCodes);
		
		Table table = tableViewer.getTable();
		int nbItems = table.getItemCount();
		for (int kx=0; kx < nbItems; kx++) {
			TableItem item = table.getItem(kx);
			AAAFormatCode formatCode = (AAAFormatCode) item.getData();
			// TODO: migrate this logic to this class
			if (getImportFacade().matchFormatCodePattern(formatCode)) {
				table.select(kx);
				break;
			}
		}
	}
	
	private void configureLabels() {
		 List<String> columns = new ArrayList<String>(); 
		 columns.add(Messages.getString("aaa.wizard.format.tablecolumn.code"));
		 columns.add(Messages.getString("aaa.wizard.format.tablecolumn.function"));
		 columns.add(Messages.getString("aaa.wizard.format.tablecolumn.description"));
		 columnLabels = columns.toArray(new String[0]);
	}
	
	private void createListeners(final TableViewer tableViewer) {
		tableViewer.getTable().addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				final AAAFormatCode formatCode = (AAAFormatCode) ((TableItem) e.item).getData();
				Display display = tableViewer.getControl().getDisplay();
				display.asyncExec(new Runnable() {
					public void run() {
						getImportFacade().setFormatCodePattern(formatCode.getCode());
					}
				});
				
			}
		});
	}
	
	private void createProviders(TableViewer tableViewer) {
		tableViewer.setContentProvider(new AAAFormatTableContentProvider());
		tableViewer.setLabelProvider(new AAAFormatTableLabelProvider());
	}

	/**
	 * @param tableViewer
	 */
	private void createColumns(final TableViewer tableViewer) {
		String[] columnLabels = getColumnLabels();
		tableViewer.setColumnProperties(columnLabels);
		for (int kx = 0; kx < columnLabels.length; kx++) {
			final int index = kx;
			TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.LEFT);
			final TableColumn column = viewerColumn.getColumn();
			column.setText(columnLabels[kx]);
			column.setWidth(50);
			column.setResizable(true);
			column.setMoveable(false);
			column.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					tableSorter.setColumn(index);
					int direction = tableViewer.getTable().getSortDirection();
					if (tableViewer.getTable().getSortColumn() == column) {
						direction = direction == SWT.UP ? SWT.DOWN : SWT.UP;
					} else {
						direction = SWT.DOWN;
					}
					tableViewer.getTable().setSortColumn(column);
					tableViewer.getTable().setSortDirection(direction);
					tableViewer.refresh();
				}
			});
		}
	}
	
	/**
	 * @param parent
	 */
	private void createTable(Composite parent) {
		
		int styleTableViewer = SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER;
		tableViewer = CheckboxTableViewer.newCheckList(parent, styleTableViewer);		
		tableViewer.setContentProvider(new AAAFormatTableContentProvider());
		tableViewer.setUseHashlookup(true);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(true);		

		tableSorter = new AAAFormatsTableSorter();
		tableViewer.setSorter(tableSorter);
		
		int styleTable = GridData.FILL_BOTH;
		GridData gd = new GridData(styleTable);
		gd.heightHint = 200;
		tableViewer.getTable().setLayoutData(gd);

		createColumns(tableViewer);
		createListeners(tableViewer);
		createProviders(tableViewer);
		
		getImportFacade().addPropertyChangeListener(this);
		tableViewer.getTable().addDisposeListener(new DisposeListener(){
			public void widgetDisposed(DisposeEvent e) {
				getImportFacade().removePropertyChangeListener(AAASimpleFormatComposite.this);
			}
		});
	}
	
	/**
	 * @return
	 */
	protected final AAAImportFacade getImportFacade() {
		return this.importFacade;
	}
	
	/**
	 * @return
	 */
	protected final String[] getColumnLabels() {
		return columnLabels;
	}
	
	/**
	 * @return
	 */
	protected final CheckboxTableViewer getTableViewer() {
		return tableViewer;
	}

	/* 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		refreshTable();
	}	

	/**
	 * @param parent
	 * @param importFacade
	 */
	public AAASimpleFormatComposite(Composite parent, AAAImportFacade importFacade) {
		this.importFacade = importFacade;
		configureLabels();
		createTable(parent);
	}

}
