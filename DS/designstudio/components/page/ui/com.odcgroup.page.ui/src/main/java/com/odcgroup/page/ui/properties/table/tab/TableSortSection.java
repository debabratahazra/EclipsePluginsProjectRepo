package com.odcgroup.page.ui.properties.table.tab;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.DeleteTableSortCommand;
import com.odcgroup.page.ui.command.table.TableSortMoveDownCommand;
import com.odcgroup.page.ui.command.table.TableSortMoveUpCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableSortDefinitionDialog;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * The class {@code TableSortSection} shows the sorts defined in the table.
 * 
 * @author atr,pkk
 * @since DS.140.0
 */
public class TableSortSection extends ListControl<ITableSort, ITable> {

	/**
	 * @param parent
	 * @param style
	 * @param sortable 
	 */
	public TableSortSection(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getDeleteCommand(ITable input, ITableSort element) {
		return new DeleteTableSortCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveDownCommand(ITable input, ITableSort element) {
		return new TableSortMoveDownCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveUpCommand(ITable input, ITableSort element) {
		return new TableSortMoveUpCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new TableSortDefinitionDialog(getShell(), getCommandStack(), getInput(), getSelection());
		} else {
			return new TableSortDefinitionDialog(getShell(), getCommandStack(), getInput());
		}
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Column", 0, 1, false));
		columns.add(new PropertyColumn("Direction", 0, 1, false));		
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object, int)
	 */
	@Override
	public String getPropertyTableColumnText(ITableSort element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getColumnName();
			} else {
				DataType type = TableHelper.getTableUtilities().getSortingDirections();
				return type.findDataValue(element.getSortingDirection()).getDisplayName();
			}
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getSelection()
	 */
	@Override
	public ITableSort getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (ITableSort)selection.getFirstElement();	
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	@Override
	public ITableSort[] getTableElements(List<ITableSort> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new ITableSort[0]);
		} 
		return null;		
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	@Override
	public List<ITableSort> getTableInput(ITable input) {
		return input.getSorts();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableSorter()
	 */
	@Override
	public ViewerSorter getTableSorter() {
		ViewerSorter sorter = new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				ITableSort sort1 = (ITableSort) e1;
				ITableSort sort2 = (ITableSort) e2;
				return sort1.getRank() - sort2.getRank();
			}
		};
		return sorter;	
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
