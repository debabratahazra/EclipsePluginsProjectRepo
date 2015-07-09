package com.odcgroup.page.ui.properties.table.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.filter.DataTypeFilter;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.InsertTableAggregateCommand;
import com.odcgroup.page.ui.command.table.UpdateTableAggregateCommand;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;
import com.odcgroup.page.ui.properties.table.controls.StringValueCombo;
import com.odcgroup.page.ui.properties.table.controls.TableColumnCombo;
import com.odcgroup.page.ui.properties.table.controls.TableGroupListControl;

/**
 * Dialog to define/edit Table Aggregate
 * 
 * @author pkk
 * 
 */
public class TableAggregateDefinitionDialog extends TableTransformDialog {

	/** table */
	private ITable table;
	
	/** table aggregate for edit*/
	private ITableAggregate aggregateforEdit;

	/** */
	private ITypeCombo<String> columnCombo;
	/** */
	private ITypeCombo<String> weightedColumnCombo;	
	/** */
	private ITypeCombo<DataValue> computationCombo;
	/** */
	private TableGroupListControl groupControl;

	/**
	 * @param parentShell
	 * @param commandStack
	 * @param table
	 */
	public TableAggregateDefinitionDialog(Shell parentShell, CommandStack commandStack, ITable table) {
		super(parentShell, commandStack);
		this.table = table;
	}
	
	/**
	 * @param parentShell
	 * @param commandStack
	 * @param table
	 * @param aggregateforEdit
	 */
	public TableAggregateDefinitionDialog(Shell parentShell, CommandStack commandStack, ITable table, ITableAggregate aggregateforEdit) {
		this(parentShell, commandStack, table);
		this.aggregateforEdit = aggregateforEdit;
		if (aggregateforEdit != null) {
			setEditMode(true);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		setTitle("Aggregate");
		setMessage("Table Aggregate definition");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Aggregate");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 5;
		composite.setLayout(gridLayout);

		Composite body = new Group(composite, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(2, false);
		body.setLayout(gridLayout);
		body.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

		new Label(body, SWT.NONE).setText("Aggregated Column");
		columnCombo = new TableColumnCombo(body, table, isEditMode(), getAggregatedColumns(table));
		
		new Label(body, SWT.NONE).setText("Computation");
		computationCombo = new DataTypeCombo(body, TableHelper.getTableUtilities().getAggregateComputations(), new AggregationComputationFilter(), isEditMode());
		
		new Label(body, SWT.NONE).setText("Weighted mean's Weight");
		Set<String> columns = new TreeSet<String>();
		columns.addAll(TableDomainObjectUtil.getDomainAttributeNames(table));
		columns.addAll(TableHelper.getComputedColumnNames(table));
		weightedColumnCombo = new StringValueCombo(body, columns, new ArrayList<String>(), isEditMode());
		weightedColumnCombo.setEnabled(false);
		
		Label groupLabel = new Label(body, SWT.NONE);
		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		groupLabel.setLayoutData(gridData);
		groupLabel.setText("On which group to appear");
		groupControl = new TableGroupListControl(body);
		groupControl.setInput(table);	    
		
		if (isEditMode()) {
			columnCombo.select(aggregateforEdit.getColumnName());
			columnCombo.setEnabled(false);
			computationCombo.select(aggregateforEdit.getComputation());
			if (!StringUtils.isEmpty(aggregateforEdit.getOtherColumnName())) {
				weightedColumnCombo.select(aggregateforEdit.getOtherColumnName());
				weightedColumnCombo.setEnabled(true);
			}
			groupControl.setSelection(aggregateforEdit.getGroupNames());
			if(isPlaceHolderColumn()) {
				computationCombo.setEnabled(false);
			}
		} 
		
		columnCombo.addSelectionListener(this);
		computationCombo.addSelectionListener(this);
		weightedColumnCombo.addSelectionListener(this);
		groupControl.addSelectionListener(this);

		return composite;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent event) {
		if (event.widget == columnCombo.getCombo()) {
			if (isPlaceHolderColumn()) {
				computationCombo.setEnabled(false);
			} else {
				computationCombo.setEnabled(true);
			}
		}
		if (event.widget==computationCombo.getCombo()) {
			if(TableHelper.getTableUtilities().isWeightedMean(computationCombo.getSelectedValue())){
				weightedColumnCombo.setEnabled(true);
			} else {
				weightedColumnCombo.select(null);
				weightedColumnCombo.setEnabled(false);
			}
		}
		super.widgetSelected(event);
	}

	/**
	 * @param table
	 * @return List
	 */
	private List<String> getAggregatedColumns(ITable table) {
		List<String> list = new ArrayList<String>();
		for (ITableAggregate aggregate : table.getAggregatedColumns()) {
			if (!isEditMode() || (isEditMode() && !aggregateforEdit.getColumnName().equals(aggregate.getColumnName()))) {
				list.add(aggregate.getColumnName());
			}
		}
		return list;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#getCommand()
	 */
	@Override
	protected BaseCommand getCommand() {
		BaseCommand cmd = null;
		if (isEditMode()) {		
			if (!isPlaceHolderColumn()) {
				String computation = computationCombo.getSelectedValue().getValue();	
				aggregateforEdit.setComputation(computation);
			}
			if(isWeightedMeanComputation()){
				aggregateforEdit.setOtherColumn(weightedColumnCombo.getSelectedValue());
			} else {
				aggregateforEdit.setOtherColumn("");
			}
			aggregateforEdit.removeGroupNames(aggregateforEdit.getGroupNames());
			aggregateforEdit.addGroupNames(groupControl.getSelectedGroupNames());
			cmd = new UpdateTableAggregateCommand(table, aggregateforEdit);			
		} else {
			ITableAggregate aggr = TableHelper.getTableUtilities().getFactory().createTableAggregate();
			String selectedColumn = columnCombo.getSelectedValue();
			aggr.setColumnName(selectedColumn);
			if (!isPlaceHolderColumn()) {
				String computation = computationCombo.getSelectedValue().getValue();
				aggr.setComputation(computation);
			}
			if(isWeightedMeanComputation()){
				aggr.setOtherColumn(weightedColumnCombo.getSelectedValue());
			} 
			aggr.addGroupNames(groupControl.getSelectedGroupNames());
			cmd = new InsertTableAggregateCommand(table, aggr);
		}		
		return cmd;
	}
	
	/**
	 * @return boolean
	 */
	private boolean isWeightedMeanComputation() {
		if (isPlaceHolderColumn()) {
			return false;
		}
		if(TableHelper.getTableUtilities().isWeightedMean(computationCombo.getSelectedValue())){
			return true;
		}
		return false;
	}
	
	/**
	 * @return boolean
	 */
	private boolean isPlaceHolderColumn() {
		String selection = columnCombo.getSelectedValue();
		for(ITableColumn column : table.getColumns()) {
			if (selection.equals(column.getColumnName()) && column.isPlaceHolder()) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#validChanges()
	 */
	@Override
	protected boolean validChanges() {
		if (!isEditMode()) {
			if (columnCombo.getSelectedValue()== null) {
				setErrorMessage("Aggregated Column is not specified");
			} else if(!isPlaceHolderColumn() && computationCombo.getSelectedValue() == null) {
				setErrorMessage("Computation is not specified");
			} else if (isWeightedMeanComputation() && weightedColumnCombo.getSelectedValue() == null) {
				setErrorMessage("Weighted mean's Weight is not specified");
			} else {
				setErrorMessage(null);
				return true;
			}		
			return false;
		} else if (isEditMode()) {
			if(!isPlaceHolderColumn() && computationCombo.getSelectedValue() == null) {
				setErrorMessage("Computation is not specified");
			} else if (isWeightedMeanComputation() && weightedColumnCombo.getSelectedValue() == null) {
				setErrorMessage("Weighted mean's Weight is not specified");
			} else {
				setErrorMessage(null);
				return true;
			}		
			return false;
		}
		setErrorMessage(null);
		return true;
	}
	
	/**
	 *
	 */
	class AggregationComputationFilter implements DataTypeFilter {
		/** */
		private List<DataValue> values = null;
		/** */
		private DataType type = null;

		/**
		 * @see com.odcgroup.page.model.filter.DataTypeFilter#filter(com.odcgroup.page.metamodel.DataType)
		 */
		public List<DataValue> filter(DataType dataType) {
			if (table != null) {
				type = dataType;
				Property renderingMode = table.getRenderingMode();
				String value = renderingMode.getValue();
				values = new ArrayList<DataValue>();
				values.addAll(dataType.getValues());
				if (value.equals("detailed-delegated") || value.equals("summary-delegated") ) {
					removeDataValue("count");
					removeDataValue("first");
					removeDataValue("last");
					removeDataValue("mean");
					removeDataValue("or");
					removeDataValue("same");
				}
				/*
				if (value.equals("summary-delegated")) {
					removeDataValue("weighted-mean");					
				}
				*/
				return values;
			}
			return dataType.getValues();
		}
		
		/**
		 * @param value
		 */
		private void removeDataValue(String value) {
			for (DataValue dataValue : type.getValues()) {
				if (dataValue.getValue().equals(value)) {
					values.remove(dataValue);
				}
			}
		}
		
	}

}
