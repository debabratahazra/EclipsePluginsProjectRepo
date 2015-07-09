package com.odcgroup.page.ui.properties.sections.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.matrix.InsertMatrixKeepFilterCommand;
import com.odcgroup.page.ui.command.matrix.UpdateMatrixKeepFilterCommand;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;
import com.odcgroup.page.ui.properties.table.controls.StringValueCombo;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * TODO: Document me!
 *
 * @author pkk & scn
 *
 */
public class MatrixKeepFilterDefinitionDialog  extends TableTransformDialog {

	/** matrix */
	private IMatrix matrix;
	
	/** table sort for edit*/
	private ITableKeepFilter filterForEdit;
	
	private ITableKeepFilter oldFilterForEdit;

	/** */
	private ITypeCombo<String> columnCombo;
	
	/** */
	private ITypeCombo<DataValue> operatorCombo;
	
	/** */
	private Text valueText;
	
	private Label content;

	/**
	 * @param parentShell
	 * @param commandStack
	 * @param matrix
	 */
	public MatrixKeepFilterDefinitionDialog(Shell parentShell, CommandStack commandStack, IMatrix matrix) {
		super(parentShell, commandStack);
		this.matrix = matrix;
	}
	
	/**
	 * @param parentShell
	 * @param commandStack
	 * @param matrix
	 * @param filterForEdit 
	 */
	public MatrixKeepFilterDefinitionDialog(Shell parentShell, CommandStack commandStack, IMatrix matrix, ITableKeepFilter filterForEdit) {
		this(parentShell, commandStack, matrix);
		this.filterForEdit = filterForEdit;
		if (filterForEdit != null) {
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
		setTitle("Keep Filter");
		setMessage("Table Keep Filter definition");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Keep Filter");
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

		new Label(body, SWT.NONE).setText("Filter Column");
		columnCombo = new StringValueCombo(body, TableDomainObjectUtil.getDomainAttributeNames(matrix),
				getFilteredColumns(matrix), isEditMode(), true);
		
		new Label(body, SWT.NONE).setText("Criteria");
		operatorCombo = new DataTypeCombo(body, TableHelper.getTableUtilities().getKeepFilterOperators(), isEditMode());	

		new Label(body, SWT.NONE).setText("Value");
		valueText = new Text(body, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		valueText.setLayoutData(gridData);
		
		
		new Label(body, SWT.NONE);
		
		content = new Label(body, SWT.NONE);
		content.setText("Wildcard is *. Example of Value. C*");
		content.setVisible(false);
		
		if (isEditMode()) {
			columnCombo.select(filterForEdit.getColumnName());
			columnCombo.setEnabled(false);
			operatorCombo.select(filterForEdit.getOperator());
			DataValue value = operatorCombo.getSelectedValue();
			if(value.getDisplayName().equals("Like") || value.getDisplayName().equals("Not Like")) {
				content.setVisible(true);
			} else {
				content.setVisible(false);
			}
			valueText.setText(filterForEdit.getOperand());
		} else {
			// default selection
		}
		
		columnCombo.addSelectionListener(this);
		operatorCombo.addSelectionListener(this);
		valueText.addModifyListener(this);

		return composite;
	}
	
	/**
	 * @param table
	 * @return List
	 */
	private List<String> getFilteredColumns(IMatrix matrix) {
		List<String> list = new ArrayList<String>();
		for (ITableKeepFilter filter : matrix.getKeepFilters()) {
			if (!isEditMode() || (isEditMode() && !filterForEdit.getColumnName().equals(filter.getColumnName()))) {
				list.add(filter.getColumnName());
			}
		}
		Collections.sort(list);
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
			oldFilterForEdit = filterForEdit;
			String operator = operatorCombo.getSelectedValue().getValue();
			String value = valueText.getText();	
			filterForEdit.setOperand(value);
			filterForEdit.setOperator(operator);
			cmd = new UpdateMatrixKeepFilterCommand(matrix, filterForEdit, oldFilterForEdit);			
		} else {
			String columnName = columnCombo.getSelectedValue();
			String operator = operatorCombo.getSelectedValue().getValue();
			String value = valueText.getText();
			ITableKeepFilter filter = TableHelper.createTableKeepFilter(columnName, operator, value);
			cmd = new InsertMatrixKeepFilterCommand(matrix, filter);
		}		
		return cmd;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#validChanges()
	 */
	@Override
	protected boolean validChanges() {
		if (!isEditMode()) {			
			if(columnCombo.getSelectedValue() == null) {
				setErrorMessage("Filter Column is not specified");
			} else if (operatorCombo.getSelectedValue() == null ) {
				setErrorMessage("Criteria is not specified");				
			} else if (StringUtils.isEmpty(valueText.getText())) {
				setErrorMessage("Value is not specified");
			}
			else {
				setErrorMessage(null);
				return true;
			}
			return false;
		} else if (isEditMode()
				&& StringUtils.isEmpty(valueText.getText()) 
				&& valueText.getText().equals(filterForEdit.getOperator())
				&& operatorCombo.getSelectedValue().getValue().equals(filterForEdit.getOperand())) {
			return false;
		}
		return true;
	}
	
	@Override
	public void widgetSelected(SelectionEvent event) {
		super.widgetSelected(event);
		if(event.getSource() instanceof Combo) {
			Combo combo = (Combo)event.getSource();
			String val = combo.getItem(combo.getSelectionIndex());
			if(val.equals("Like") || val.equals("Not Like")) {
				content.setVisible(true);
				if(valueText.getText() == null || valueText.getText() == "") {
					valueText.setText("*");
				}
			} else {
				content.setVisible(false);
				if(valueText.getText().equals("*")) {
					valueText.setText("");
				}
			}
		}
		if (validChanges()) {
			enableOK(true);
		} else {
			enableOK(false);
		}
	}

}
