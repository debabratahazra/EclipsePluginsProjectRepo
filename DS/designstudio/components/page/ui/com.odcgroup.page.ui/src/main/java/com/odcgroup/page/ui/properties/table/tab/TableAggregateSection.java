package com.odcgroup.page.ui.properties.table.tab;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.DeleteTableAggregateCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableAggregateDefinitionDialog;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * The class {@code TableAggregateSection} shows the aggregated columns in the
 * table.
 * 
 * @author atr,pkk
 * @since DS.140.0
 */
public class TableAggregateSection extends ListControl<ITableAggregate, ITable>{

	/**
	 * @param parent
	 * @param style
	 * @param sortable
	 */
	public TableAggregateSection(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getDeleteCommand(ITable input, ITableAggregate element) {
		return new DeleteTableAggregateCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveDownCommand(ITable input, ITableAggregate element) {
		// NO FUNCTIONAL REQUIREMENT
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveUpCommand(ITable input, ITableAggregate element) {
		// NO FUNCTIONAL REQUIREMENT
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		List<ITableGroup> groups = getInput().getGroupsByRank();
		if (!groups.isEmpty()) {
			ITableGroup group = groups.iterator().next();
			if (group.isHierarchy() && !group.isAggregatedData()) {
				String msg = "It is not possible to define aggregates with a hierarchy using \"raw data\"";
				MessageDialog.openError(getShell(), "Constraint", msg);
				return null;
			}
		}
		if (edit) {
			return new TableAggregateDefinitionDialog(getShell(), getCommandStack(), getInput(), getSelection());
		} else {
			return new TableAggregateDefinitionDialog(getShell(), getCommandStack(), getInput());
		}
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Column", 30, 5, false));
		columns.add(new PropertyColumn("Computation", 30, 5, false));			
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object, int)
	 */
	@Override
	public String getPropertyTableColumnText(ITableAggregate element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getColumnName();
			}  else {
				DataType type = TableHelper.getTableUtilities().getAggregateComputations();
				return type.findDataValue(element.getComputation()).getDisplayName();
			}
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getSelection()
	 */
	@Override
	public ITableAggregate getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (ITableAggregate)selection.getFirstElement();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	@Override
	public ITableAggregate[] getTableElements(List<ITableAggregate> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new ITableAggregate[0]);
		}
		return null;
	}

	/**
	 * non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	@Override
	public List<ITableAggregate> getTableInput(ITable input) {
		return input.getAggregatedColumns();
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
	protected SpecialControl<ITable> createOtherControls(Composite parent) {
		return null;
	}

}
