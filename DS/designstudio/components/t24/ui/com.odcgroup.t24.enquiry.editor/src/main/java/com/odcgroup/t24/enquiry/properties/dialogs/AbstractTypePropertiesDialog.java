package com.odcgroup.t24.enquiry.properties.dialogs;

import java.util.Arrays;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 *
 */
public abstract class AbstractTypePropertiesDialog extends Dialog {

	protected Combo typeCombo;
	protected TableViewer tableViewer;
	protected TableViewerColumn tableColumnViewerProperty;
	protected TableViewerColumn tableColumnViewerValue;
	private Table dsTable;
	private String propertyType;
	private String[] types;
	private String dialogName;
	private boolean summary;
	protected Label summaryLabel;

	public AbstractTypePropertiesDialog(Shell parentShell, String dialogName, String propertyType, String[] types, boolean summary) {
		super(parentShell);
		this.dialogName = dialogName;
		this.propertyType = propertyType;
		this.types = types;
		this.summary = summary;
		Arrays.sort(types);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		container.setLayout(gridLayout);
		GridData containerdata = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(containerdata);

		Group group = new Group(container, SWT.NONE);
		group.setLayout(gridLayout);
		GridData companiesGroupdata = new GridData(GridData.FILL_BOTH);
		group.setLayoutData(companiesGroupdata);
		group.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		

		createSelectionComposite(group);
	    createTable(group);
	    
	    if (summary) {
		    Group summary = new Group(group, SWT.NONE);
			summary.setText("Summary");
			summary.setLayout(gridLayout);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			summary.setLayoutData(gridData);
			summary.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
	
			summaryLabel = new Label(summary, SWT.WRAP);
			summaryLabel.setLayoutData(gridData);
			summaryLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			updateSummary();
	    }
		updateCombo();
		updateTable();
		return container;
	}

	/**
	 * @param group
	 * @return
	 */
	protected void createSelectionComposite(Group group) {
		GridLayout gridLayout;
		Composite firstComposite = new Composite(group, SWT.NONE);
		gridLayout = new GridLayout(2, false);
		firstComposite.setLayout(gridLayout);
		firstComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		Label operationTypeLabel = new Label(firstComposite,SWT.NONE);
		operationTypeLabel.setText(propertyType+" Type :");
		operationTypeLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		typeCombo = new Combo(firstComposite, SWT.SINGLE);
		typeCombo.setItems(types);
		
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModel();
			}
		});
	}
	
	/**
	 * @param companiesGroup
	 */
	private void createTable(Composite companiesGroup) {
		Composite tcomp = new Composite(companiesGroup, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		tcomp.setLayoutData(gd);
		tableViewer = new TableViewer(tcomp, SWT.BORDER | SWT.FULL_SELECTION);
		dsTable = tableViewer.getTable();
		dsTable.setLinesVisible(true);
		dsTable.setHeaderVisible(true);

		
		tableColumnViewerProperty = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn propertyColumn = tableColumnViewerProperty.getColumn();
		propertyColumn.setText("Property");

		tableColumnViewerValue = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn valueColumn = tableColumnViewerValue.getColumn();
		valueColumn.setText("Value");
		addLabelProviders();
		
		TableColumnLayout layout = new TableColumnLayout();
		tcomp.setLayout(layout);
		layout.setColumnData( tableColumnViewerProperty.getColumn(), new ColumnWeightData( 60 ) );
		layout.setColumnData( tableColumnViewerValue.getColumn(), new ColumnWeightData( 60 ) );

		tableViewer.setContentProvider(new ArrayContentProvider());		
	}
	
	/**
	 * 
	 */
	protected void addLabelProviders() {
		tableColumnViewerProperty.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof EAttribute){
					EAttribute attribute = (EAttribute)element;
					String key = attribute.getName();
					if (key.length() == 0) return key;
			        return key.substring(0, 1).toUpperCase() + key.substring(1).toLowerCase();
				}
				return super.getText(element);
			}
		});
		
		tableColumnViewerValue.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				updateSummary();
				return getCellEditorColumnText(element);
			}		
		});		
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(dialogName);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 500);
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}
	
	protected void updateTable() {
		tableViewer.setInput(null);	
	}

	protected abstract void updateCombo();
	protected abstract void updateModel();
	protected abstract void updateSummary();
	protected abstract String getCellEditorColumnText(Object element);
}

