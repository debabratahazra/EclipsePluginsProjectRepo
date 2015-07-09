package com.odcgroup.page.ui.properties.table.controls;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.DeleteTableColumnComputationParameterCommand;
import com.odcgroup.page.ui.command.table.MoveDownTableColumnComputationParameterCommand;
import com.odcgroup.page.ui.command.table.MoveUpTableColumnComputationParameterCommand;
import com.odcgroup.page.ui.properties.table.dialog.TableColumnComputationParameterDefinitionDialog;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * The class {@code TableExtraSection} shows the extra data of the table.
 * 
 * @author atr
 * @since DS.140.0
 */
public class TableColumnComputationParamsComposite extends ListControl<String, ITableColumn> {

	/**
	 * @param parent
	 * @param style
	 * @param sortable 
	 */
	public TableColumnComputationParamsComposite(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable, true);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getDeleteCommand(ITableColumn input, String element) {
		return new DeleteTableColumnComputationParameterCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveDownCommand(ITableColumn input, String element) {
		return new MoveDownTableColumnComputationParameterCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveUpCommand(ITableColumn input, String element) {
		return new MoveUpTableColumnComputationParameterCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new TableColumnComputationParameterDefinitionDialog(getShell(), getCommandStack(), getInput(), getSelection());
		} else {
			return new TableColumnComputationParameterDefinitionDialog(getShell(), getCommandStack(), getInput());
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
	public List<String> getTableInput(ITableColumn input) {
		return input.getParameters();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableSorter()
	 */
	@Override
	public ViewerSorter getTableSorter() {
		ViewerSorter sorter = new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				List<String> parameters = getInput().getParameters();
				int rank1 = parameters.indexOf(e1);
				int rank2 = parameters.indexOf(e2);
				return rank1-rank2;
			}
		};
		return sorter;			
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#createOtherControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected SpecialControl<ITableColumn> createOtherControls(Composite parent) {
		return new TableColumnComputationComposite(parent) {
			protected void selectionChange(String value) {
				if (propertyTable != null) {
					propertyTable.getAddButton().setEnabled(StringUtils.isNotEmpty(value));
				}
			}
		};
	}
}
