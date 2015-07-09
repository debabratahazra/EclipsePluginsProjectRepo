/**
 * 
 */
package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;

/**
 * @author atr
 *
 */
public class InsertDynamicColumnAction extends TableInsertColumnAction {

	/**
	 * @param parameters
	 */
	public InsertDynamicColumnAction(ActionParameters parameters) {
		super(parameters);
	}

	/**
	 * @see com.odcgroup.page.ui.action.table.TableInsertColumnAction#getType()
	 */
	public String getType() {
		return "DynamicColumn";
	}

}
