package com.odcgroup.page.ui.properties.table.tab;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.TableGroupMoveDownCommand;
import com.odcgroup.page.ui.command.table.TableGroupMoveUpCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableGroupDefinitionDialog;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;


/**
 * The class {@code TableGroupSection} shows the grouping columns of the table.
 * 
 * @author atr,pkk
 * @since DS.140.0
 */
public class TableGroupSection extends ListControl<ITableGroup, ITable> {

	/**
	 * @param parent
	 * @param style
	 * @param sortable
	 */
	public TableGroupSection(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getDeleteCommand(ITable input, ITableGroup element) {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveDownCommand(ITable input, ITableGroup element) {
		return new TableGroupMoveDownCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveUpCommand(ITable input, ITableGroup element) {
		return new TableGroupMoveUpCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new TableGroupDefinitionDialog(getShell(), getCommandStack(), getInput(), getSelection());
		} else {
			return new TableGroupDefinitionDialog(getShell(), getCommandStack(), getInput());
		}
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Column", 35, 4, false));
		columns.add(new PropertyColumn("Sort by", 35, 3, false));	
		columns.add(new PropertyColumn("Direction", 30, 3, false));			
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object, int)
	 */
	@Override
	public String getPropertyTableColumnText(ITableGroup element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getColumnName();
			} else if(columnIndex == 1) {
				return element.getSortingColumnName();
			}else {
				DataType type = TableHelper.getTableUtilities().getSortingDirections();
				if (StringUtils.isEmpty(element.getSortingDirection())) {
					return null;
				}
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
	public ITableGroup getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (ITableGroup)selection.getFirstElement();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	@Override
	public ITableGroup[] getTableElements(List<ITableGroup> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new ITableGroup[0]);
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	@Override
	public List<ITableGroup> getTableInput(ITable input) {
		return input.getGroups();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableSorter()
	 */
	@Override
	public ViewerSorter getTableSorter() {
		ViewerSorter sorter = new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				ITableGroup group1 = (ITableGroup) e1;
				ITableGroup group2 = (ITableGroup) e2;
				return group1.getRank() - group2.getRank();
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
