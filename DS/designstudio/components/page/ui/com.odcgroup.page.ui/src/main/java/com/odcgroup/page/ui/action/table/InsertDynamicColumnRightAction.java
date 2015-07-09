package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;

/**
 * @author atr
 *
 */
public class InsertDynamicColumnRightAction extends TableInsertColumnRightAction {

	/**
	 * @param parameters
	 */
	public InsertDynamicColumnRightAction(ActionParameters parameters) {
		super(parameters);
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.table.TableInsertColumnRightAction#getType()
	 */
	public String getType() {
		return "DynamicColumn";
	}

}
