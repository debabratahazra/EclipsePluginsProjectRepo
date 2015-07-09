package com.odcgroup.page.ui.properties.table.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableExtra;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.InsertTableExtraCommand;
import com.odcgroup.page.ui.command.table.UpdateTableExtraCommand;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;
import com.odcgroup.page.ui.properties.table.controls.DomainAttributesCombo;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;

/**
 * TODO: Document me!
 * 
 * @author pkk
 * 
 */
public class TableExtraDefinitionDialog extends TableTransformDialog {

	/** table */
	private ITable table;
	
	/** table extra for edit*/
	private ITableExtra extraForEdit;

	/** */
	private ITypeCombo<MdfDatasetProperty> columnCombo;

	/**
	 * @param parentShell
	 * @param commandStack
	 * @param table
	 */
	public TableExtraDefinitionDialog(Shell parentShell, CommandStack commandStack, ITable table) {
		super(parentShell, commandStack);
		this.table = table;
	}
	
	/**
	 * @param parentShell
	 * @param commandStack
	 * @param table
	 * @param extraForEdit
	 */
	public TableExtraDefinitionDialog(Shell parentShell, CommandStack commandStack, ITable table, ITableExtra extraForEdit) {
		this(parentShell, commandStack, table);
		this.extraForEdit = extraForEdit;
		if (extraForEdit != null) {
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
		setTitle("Extra Data");
		setMessage("Table Extra data definitions");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Extra Data");
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

		new Label(body, SWT.NONE).setText("Data");		
		columnCombo = new DomainAttributesCombo(body, TableDomainObjectUtil.getDomainAttributes(table), getAddedAttributes(table), isEditMode());
		columnCombo.addSelectionListener(this);
		
		if (isEditMode()) {
			columnCombo.select(extraForEdit.getAttribute().getName());
		} 

		return composite;
	}	
	
	/**
	 * @param table
	 * @return List
	 */
	private List<MdfDatasetProperty> getAddedAttributes(ITable table) {
		List<MdfDatasetProperty> list = new ArrayList<MdfDatasetProperty>();
		for (ITableExtra sort : table.getExtras()) {
			if (!isEditMode() || (isEditMode() && !extraForEdit.getAttribute().equals(sort.getAttribute()))) {		
				list.add(sort.getAttribute());
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
			MdfDatasetProperty attribute = columnCombo.getSelectedValue();
			cmd = new UpdateTableExtraCommand(table, extraForEdit, attribute);
		} else {
			MdfDatasetProperty attribute = columnCombo.getSelectedValue();
			if (attribute == null) {
				return null;
			}
			ITableExtra extra = TableHelper.getTableUtilities().getFactory().createTableExtra();
			extra.setAttribute(attribute);
			cmd = new InsertTableExtraCommand(table, extra);
		}		
		return cmd;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#validChanges()
	 */
	@Override
	protected boolean validChanges() {
		if (columnCombo.getSelectedValue()== null) {
			setErrorMessage("Data is not specified");
			return false;
		} 
		setErrorMessage(null);
		return true;
	}

}
