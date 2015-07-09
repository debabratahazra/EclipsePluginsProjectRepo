package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;

/**
 * @author atr
 *
 */
public class InsertDynamicColumnLeftAction extends TableInsertColumnLeftAction {

	/**
	 * @param parameters
	 */
	public InsertDynamicColumnLeftAction(ActionParameters parameters) {
		super(parameters);
	}
	
	
	/**
	 * @see com.odcgroup.page.ui.action.table.TableInsertColumnLeftAction#getType()
	 */
	public String getType() {
		return "DynamicColumn";
	}

}
