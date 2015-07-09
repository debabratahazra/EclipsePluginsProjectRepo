package com.odcgroup.page.ui.command.table;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableExtra;

/**
 * TODO: Document me!
 * 
 * @author pkk
 * 
 */
public class UpdateTableExtraCommand extends AbstractTableGroupCommand {
	/** */
	private MdfDatasetProperty oldValue;
	/** */
	private MdfDatasetProperty newValue;
	/** */
	private ITableExtra extra;

	/**
	 * @param table
	 * @param extra
	 * @param newValue
	 */
	public UpdateTableExtraCommand(ITable table, ITableExtra extra, MdfDatasetProperty newValue) {
		super(table);
		this.oldValue = extra.getAttribute();
		this.newValue = newValue;
		this.extra = extra;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		extra.setAttribute(newValue);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		extra.setAttribute(oldValue);
	}

}
