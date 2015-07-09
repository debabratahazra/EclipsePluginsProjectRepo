package com.odcgroup.page.ui.properties.sections.matrix;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.widgets.matrix.IMatrixComputationItem;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.matrix.DeleteMatrixItemComputationParameterCommand;
import com.odcgroup.page.ui.command.matrix.MoveDownMatrixItemCompParamCommand;
import com.odcgroup.page.ui.command.matrix.MoveUpMatrixItemCompParamCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 *
 * @author pkk
 *
 */
public class MatrixComputationParamsComposite extends ListControl<String, IMatrixComputationItem> {

	/**
	 * @param parent
	 * @param style
	 * @param sortable 
	 */
	public MatrixComputationParamsComposite(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable, true);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getDeleteCommand(IMatrixComputationItem input, String element) {
		return new DeleteMatrixItemComputationParameterCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveDownCommand(IMatrixComputationItem input, String element) {
		return new MoveDownMatrixItemCompParamCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveUpCommand(IMatrixComputationItem input, String element) {
		return new MoveUpMatrixItemCompParamCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new MatrixComputationParameterDefinitionDialog(getShell(), getCommandStack(), getInput(), getSelection());
		} else {
			return new MatrixComputationParameterDefinitionDialog(getShell(), getCommandStack(), getInput());
		}
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Parameter", 100, 10, false));
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object, int)
	 */
	@Override
	public String getPropertyTableColumnText(String element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element;
			} 
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getSelection()
	 */
	@Override
	public String getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (String)selection.getFirstElement();	
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	@Override
	public String[] getTableElements(List<String> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new String[0]);
		} 
		return null;		
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	@Override
	public List<String> getTableInput(IMatrixComputationItem input) {
		return input.getComputationParameters();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableSorter()
	 */
	@Override
	public ViewerSorter getTableSorter() {
		return null;			
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#createOtherControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected SpecialControl<IMatrixComputationItem> createOtherControls(Composite parent) {
		return new MatrixItemComputationComposite(parent) {
			protected void selectionChange(String value) {
				if (propertyTable != null) {
					propertyTable.getAddButton().setEnabled(StringUtils.isNotEmpty(value));
				}
			}
		};
	}
}
