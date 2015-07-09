package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * TODO: Document me!
 *
 * @author atr
 * @since DS 1.40.0
 */
public class UpdateTableColumnComputationCommand extends AbstractTableColumnCommand {
	/** */
	private String oldProperty;
	/** */
	private String newValue;

	/**
	 * @param column
	 * @param newValue 
	 */
	public UpdateTableColumnComputationCommand(ITableColumn column, String newValue) {
		super(column);
		this.newValue = newValue;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		ITableColumn column = getTableColumn();
		Property p = column.getComputation();
		if (p != null) {
			oldProperty = p.getValue();
			p.setValue(newValue);
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (oldProperty != null) {
			getTableColumn().getComputation().setValue(oldProperty);
		}
	}

}
