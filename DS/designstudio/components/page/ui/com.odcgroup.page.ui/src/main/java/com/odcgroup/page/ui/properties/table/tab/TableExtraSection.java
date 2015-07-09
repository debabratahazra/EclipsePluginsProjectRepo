package com.odcgroup.page.ui.properties.table.tab;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableExtra;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.table.DeleteTableExtraCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableExtraDefinitionDialog;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * The class {@code TableExtraSection} shows the extra data of the table.
 * 
 * @author atr,pkk
 * @since DS.140.0
 */
public class TableExtraSection extends ListControl<ITableExtra, ITable> {

	/**
	 * @param parent
	 * @param style
	 * @param sortable 
	 */
	public TableExtraSection(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getDeleteCommand(ITable input, ITableExtra element) {
		return new DeleteTableExtraCommand(input, element);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveDownCommand(ITable input, ITableExtra element) {
		// not required
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveUpCommand(ITable input, ITableExtra element) {
		// not required
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new TableExtraDefinitionDialog(getShell(), getCommandStack(), getInput(), getSelection());
		} else {
			return new TableExtraDefinitionDialog(getShell(), getCommandStack(), getInput());
		}
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Extra Data", 100, 10, false));
		
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object, int)
	 */
	@Override
	public String getPropertyTableColumnText(ITableExtra element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getAttribute().getName();
			} 
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getSelection()
	 */
	@Override
	public ITableExtra getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (ITableExtra)selection.getFirstElement();	
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	@Override
	public ITableExtra[] getTableElements(List<ITableExtra> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new ITableExtra[0]);
		} 
		return null;		
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	@Override
	public List<ITableExtra> getTableInput(ITable input) {
		return input.getExtras();
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
