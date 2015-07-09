package com.odcgroup.page.ui.properties.table.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.InsertTableSortCommand;
import com.odcgroup.page.ui.command.table.UpdateTableSortCommand;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;
import com.odcgroup.page.ui.properties.table.controls.StringValueCombo;

/**
 * TODO: Document me!
 * 
 * @author pkk
 * 
 */
public class TableSortDefinitionDialog extends TableTransformDialog {

	/** table */
	private ITable table;
	
	/** table sort for edit*/
	private ITableSort sortForEdit;

	/** */
	private ITypeCombo<String> columnCombo;
	
	/** */
	private ITypeCombo<DataValue> directionCombo;

	/**
	 * @param parentShell
	 * @param commandStack
	 * @param table
	 */
	public TableSortDefinitionDialog(Shell parentShell, CommandStack commandStack, ITable table) {
		super(parentShell, commandStack);
		this.table = table;
	}
	
	/**
	 * @param parentShell
	 * @param commandStack
	 * @param table
	 * @param sortForEdit
	 */
	public TableSortDefinitionDialog(Shell parentShell, CommandStack commandStack, ITable table, ITableSort sortForEdit) {
		this(parentShell, commandStack, table);
		this.sortForEdit = sortForEdit;
		if (sortForEdit != null) {
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
		setTitle("Sort");
		setMessage("Table Column sorting definitions");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Sort");
	}
	

	/**
	 * (non-Javadoc)
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

		new Label(body, SWT.NONE).setText("Sorting Column");
		Set<String> columns = new TreeSet<String>();
		columns.addAll(TableDomainObjectUtil.getDomainAttributeNames(table));
		columns.addAll(TableHelper.getComputedColumnNames(table));
		
		columnCombo = new StringValueCombo(body, columns, getSortedColumns(table), isEditMode(), true);
		columnCombo.addSelectionListener(this);
		
		new Label(body, SWT.NONE).setText("Sorting Direction");
		directionCombo = new DataTypeCombo(body, TableHelper.getTableUtilities().getSortingDirections(), isEditMode());
		directionCombo.addSelectionListener(this);
		
		if (isEditMode()) {
			columnCombo.select(sortForEdit.getColumnName());
			columnCombo.setEnabled(false);
			directionCombo.select(sortForEdit.getSortingDirection());
		} else {
			// default selection
			directionCombo.select(PropertyTypeConstants.SORT_ASCENDING);
		}

		return composite;
	}
	
	/**
	 * @param table
	 * @return List
	 */
	private List<String> getSortedColumns(ITable table) {
		List<String> list = new ArrayList<String>();
		for (ITableSort sort : table.getSorts()) {
			if (!isEditMode() || (isEditMode() && !sortForEdit.getColumnName().equals(sort.getColumnName()))) {
				list.add(sort.getColumnName());
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
			String direction = directionCombo.getSelectedValue().getValue();	
			cmd = new UpdateTableSortCommand(table, sortForEdit, direction);			
		} else {
			String selectedColumn = columnCombo.getSelectedValue();
			String direction = directionCombo.getSelectedValue().getValue();
			int rank = TableHelper.getNextSortRank(table);
			ITableSort tableSort = TableHelper.createTableSort(selectedColumn, direction, rank);
			cmd = new InsertTableSortCommand(table, tableSort);
		}		
		return cmd;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#validChanges()
	 */
	@Override
	protected boolean validChanges() {
		if (!isEditMode() && columnCombo.getSelectedValue()== null) {
			setErrorMessage("Please select a Column to sort");
			return false;
		} else if (isEditMode() && directionCombo.getSelectedValue().getValue().equals(sortForEdit.getSortingDirection())) {
			setErrorMessage("Please select sorting direction");
			return false;
		}
		setErrorMessage(null);
		return true;
	}

}
