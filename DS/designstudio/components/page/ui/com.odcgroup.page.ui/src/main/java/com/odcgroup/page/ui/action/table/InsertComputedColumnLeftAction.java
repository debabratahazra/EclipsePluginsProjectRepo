package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;

/**
 * @author pkk
 *
 */
public class InsertComputedColumnLeftAction extends TableInsertColumnLeftAction {

	/**
	 * @param parameters
	 */
	public InsertComputedColumnLeftAction(ActionParameters parameters) {
		super(parameters);
	}
	
	
	/**
	 * @see com.odcgroup.page.ui.action.table.TableInsertColumnLeftAction#getType()
	 */
	public String getType() {
		return "TableColumn";
	}

}
