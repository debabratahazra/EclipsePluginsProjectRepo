package com.odcgroup.page.ui.properties.table.tab;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.DeleteTableKeepFilterCommand;
import com.odcgroup.page.ui.properties.table.controls.KeepFilterLogicComposite;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableKeepFilterDefinitionDialog;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * The class {@code TableKeepFilterSection} shows the KeepFilters of the table.
 * 
 * @author atr,pkk
 * @since DS.140.0
 */
public class TableKeepFilterSection extends ListControl<ITableKeepFilter, ITable> {
	

	/**
	 * @param parent
	 * @param style
	 * @param sortable
	 */
	public TableKeepFilterSection(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable, true);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#createOtherControls(org.eclipse.swt.widgets.Composite)
	 */
	protected SpecialControl<ITable> createOtherControls(Composite parent) {
		return new KeepFilterLogicComposite(parent);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getDeleteCommand(ITable input, ITableKeepFilter element) {
		return new DeleteTableKeepFilterCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveDownCommand(ITable input, ITableKeepFilter element) {
		// NO FUNCTIONAL REQUIREMENT
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveUpCommand(ITable input, ITableKeepFilter element) {
		// NO FUNCTIONAL REQUIREMENT
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new TableKeepFilterDefinitionDialog(getShell(), getCommandStack(), getInput(), getSelection());
		} else {
			return new TableKeepFilterDefinitionDialog(getShell(), getCommandStack(), getInput());
		}	
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Column", 30, 4, false));
		columns.add(new PropertyColumn("Criteria", 30, 3, false));	
		columns.add(new PropertyColumn("Value", 40, 3, false));		
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object, int)
	 */
	@Override
	public String getPropertyTableColumnText(ITableKeepFilter element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getColumnName();
			} else if (columnIndex == 1){
				DataType type = TableHelper.getTableUtilities().getKeepFilterOperators();
				return type.findDataValue(element.getOperator()).getDisplayName();
			} else {
				return element.getOperand();
			}
		}
		return null;	
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getSelection()
	 */
	@Override
	public ITableKeepFilter getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (ITableKeepFilter)selection.getFirstElement();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	@Override
	public ITableKeepFilter[] getTableElements(List<ITableKeepFilter> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new ITableKeepFilter[0]);
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	@Override
	public List<ITableKeepFilter> getTableInput(ITable input) {
		return input.getKeepFilters();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableSorter()
	 */
	@Override
	public ViewerSorter getTableSorter() {
		return null;
	}

}
