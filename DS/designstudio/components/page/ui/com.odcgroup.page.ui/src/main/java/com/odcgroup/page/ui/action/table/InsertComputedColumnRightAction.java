package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;

/**
 * @author pkk
 *
 */
public class InsertComputedColumnRightAction extends TableInsertColumnRightAction {

	/**
	 * @param parameters
	 */
	public InsertComputedColumnRightAction(ActionParameters parameters) {
		super(parameters);
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.table.TableInsertColumnRightAction#getType()
	 */
	public String getType() {
		return "TableColumn";
	}

}
