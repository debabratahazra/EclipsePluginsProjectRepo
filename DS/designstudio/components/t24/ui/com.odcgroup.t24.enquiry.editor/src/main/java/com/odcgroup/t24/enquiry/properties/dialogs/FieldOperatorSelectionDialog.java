package com.odcgroup.t24.enquiry.properties.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.t24.enquiry.enquiry.CalcOperation;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldOperatorSelectionDialog extends Dialog {

	private TableViewer tableViewer;
	private Table dsTable;
	private TableViewerColumn tableColumnViewerProperty;
	private CalcOperation model;
	private TableViewerColumn tableColumnViewerValue;
	private EObject operationModel;
	private EObject fieldModel;
	private String[] operators = new String[] { "+", "-", "*", "/", ":" };
	private Map<String, String> fieldOperator = new HashMap<String, String>();
	private final String FIRST_OPERATOR = "_Operator";
	private final String SECOND_FIELD = "Field";
	
	/**
	 * @param parentShell
	 */
	protected FieldOperatorSelectionDialog(Shell parentShell,EObject operationModel, EObject fieldModel) {
		super(parentShell);
		this.operationModel = operationModel;
		this.fieldModel = fieldModel;
		fieldOperator.put(FIRST_OPERATOR,"");
		fieldOperator.put(SECOND_FIELD,"");
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 500);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		container.setLayout(gridLayout);
		GridData containerdata = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(containerdata);

		Group group = new Group(container, SWT.NONE);
		group.setText("Operator and Field");
		group.setLayout(gridLayout);
		GridData companiesGroupdata = new GridData(GridData.FILL_BOTH);
		group.setLayoutData(companiesGroupdata);
		group.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		

		Composite firstComposite = new Composite(group, SWT.NONE);
		gridLayout = new GridLayout(2, false);
		firstComposite.setLayout(gridLayout);
		firstComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
	    createTable(group);
	    
		return container;
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

		tableViewer.setContentProvider(new ArrayContentProvider());

		tableColumnViewerProperty = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn propertyColumn = tableColumnViewerProperty.getColumn();
		propertyColumn.setText("Property");
		tableColumnViewerProperty.getViewer().setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element) {
				if(element.equals(FIRST_OPERATOR)){
					return "Operator";
				}
				return super.getText(element);
			}
		});
		tableColumnViewerValue = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn valueColumn = tableColumnViewerValue.getColumn();
		valueColumn.setText("Value");
					
		tableColumnViewerValue.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				return getCellEditorColumnText(element);
			}

		});
		
		
		TableColumnLayout layout = new TableColumnLayout();
		tcomp.setLayout(layout);
		layout.setColumnData( tableColumnViewerProperty.getColumn(), new ColumnWeightData( 60 ) );
		layout.setColumnData( tableColumnViewerValue.getColumn(), new ColumnWeightData( 60 ) );
		
		tableViewer.setInput(fieldOperator.keySet());
		tableColumnViewerValue.setEditingSupport(new OperatorExperssionValueEditing(tableViewer));
	}
	protected String getCellEditorColumnText(Object element) {
		return fieldOperator.get(element);
	}

	public String getOperatorField(){
		String field = "";
		String operator = "";
		for(String key:fieldOperator.keySet()){
			if(fieldOperator.get(key).isEmpty()){
				return null;
			}
			if(key.equals(SECOND_FIELD)){
				field=fieldOperator.get(key);
			}
			else if(key.equals(FIRST_OPERATOR)){
				operator=fieldOperator.get(key);
			}
		}
		return operator+" "+field;
	}
	private class OperatorExperssionValueEditing extends EditingSupport{

		/**
		 * @param viewer
		 */
		public OperatorExperssionValueEditing(ColumnViewer viewer) {
			super(viewer);
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			if(element.equals(SECOND_FIELD)){
				ExtendedTextDialogCellEditor cellEditor = new ExtendedTextDialogCellEditor(tableViewer.getTable(), new LabelProvider()) {
					@Override
					protected Object openDialogBox(Control cellEditorWindow) {

						List<String> list = new ArrayList<String>();
						Enquiry enquiry = (Enquiry) fieldModel.eContainer();
						return EnquiryFieldSelectionDialog.openEnquiryFieldsDialog(cellEditorWindow.getShell(), enquiry,
								"Enquiry Field Selection", "Select an Enquiry Field as Break Field", list, false);					
					}
				};
				return cellEditor;
			
			}
			else if(element.equals(FIRST_OPERATOR)){
				return new ExtendedComboBoxCellEditor(tableViewer.getTable(), Arrays.asList(operators), new LabelProvider());
			}
			return null;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			return fieldOperator.get(element);
		
		}

		@Override
		protected void setValue(Object element, Object value) {
			fieldOperator.put((String)element, (String)value);
			tableViewer.update(element, null);
		}
		
	}

}
