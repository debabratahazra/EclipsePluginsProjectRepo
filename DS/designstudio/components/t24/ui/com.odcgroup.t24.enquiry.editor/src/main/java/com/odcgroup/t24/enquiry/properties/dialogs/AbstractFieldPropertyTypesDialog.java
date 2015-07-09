package com.odcgroup.t24.enquiry.properties.dialogs;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
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

import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.Operation;

/**
 * @author mumesh
 *
 */
public abstract class AbstractFieldPropertyTypesDialog extends Dialog{

		protected Combo typeCombo;
		protected TableViewer tableViewer;
		protected TableViewerColumn tableColumnViewerProperty;
		protected TableViewerColumn tableColumnViewerValue;
		private Table dsTable;
		protected Field fieldModel;
		protected EObject model;
		private String fieldPropertyType;
		protected String[] types;
		protected Label summaryLabel;
		protected EObject initialModel;

		public AbstractFieldPropertyTypesDialog(Shell parentShell,Field parentModel,EObject model,String propertyType,String[] types) {
			super(parentShell);
			this.fieldModel = parentModel;
			this.model = model;
			this.initialModel = model;
			this.fieldPropertyType = propertyType;
			this.types = types;
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite container = (Composite) super.createDialogArea(parent);
			GridLayout gridLayout = new GridLayout(1, false);
			container.setLayout(gridLayout);
			GridData containerdata = new GridData(GridData.FILL_BOTH);
			container.setLayoutData(containerdata);

			Group group = new Group(container, SWT.NONE);
			group.setText(fieldPropertyType);
			group.setLayout(gridLayout);
			GridData companiesGroupdata = new GridData(GridData.FILL_BOTH);
			group.setLayoutData(companiesGroupdata);
			group.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			

			createSelectionComposite(group);
		    createTable(group);
		    
		    Group summary = new Group(group, SWT.NONE);
			summary.setText("Summary");
			summary.setLayout(gridLayout);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			summary.setLayoutData(gridData);
			summary.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

			summaryLabel = new Label(summary, SWT.WRAP);
			summaryLabel.setLayoutData(gridData);
			summaryLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			
			updateCombo();
			updateTable();
			updateSummary();
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
			operationTypeLabel.setText(fieldPropertyType+" Type :");
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
		 * 
		 */
		protected void updateCombo() {
			if(model == null && typeCombo != null){
				typeCombo.setText("None");
			}			
		}

		abstract protected void updateModel();
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
			addLabelProvider();

			tableColumnViewerValue = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn valueColumn = tableColumnViewerValue.getColumn();
			valueColumn.setText("Value");
						
			tableColumnViewerValue.setLabelProvider(new ColumnLabelProvider(){
				@Override
				public String getText(Object element) {
					updateSummary();
					return getCellEditorColumnText(element);
				}

			});
			
			
			TableColumnLayout layout = new TableColumnLayout();
			tcomp.setLayout(layout);
			layout.setColumnData( tableColumnViewerProperty.getColumn(), new ColumnWeightData( 60 ) );
			layout.setColumnData( tableColumnViewerValue.getColumn(), new ColumnWeightData( 60 ) );

			tableViewer.setContentProvider(new ArrayContentProvider());
			
		}

		/**
		 * 
		 */
		protected void addLabelProvider() {
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
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText("Field Operation Selection Dialog");
		}

		@Override
		protected Point getInitialSize() {
			return new Point(500, 500);
		}

		@Override
		protected void okPressed() {
			super.okPressed();
		}
		
		public Operation getFieldOpertaion(){
			return (Operation) model;
		}


		protected void updateTable(){
			if(model != null){
				tableViewer.setInput(model.eClass().getEAllAttributes());
				tableColumnViewerValue.setEditingSupport(new ValueEditingSupport(tableViewer,model,fieldModel));
			} else {
				tableViewer.setInput(null);
			}

		}
		
		protected abstract void updateSummary();
			
		protected String getCellEditorColumnText(Object element) {
			if(element instanceof EAttribute){
				EAttribute attribute = (EAttribute)element;
				if(model.eGet(attribute) == null){
					return "";
				}
				else{
					return model.eGet(attribute).toString();
				}
			}
			return "";
		}
}
