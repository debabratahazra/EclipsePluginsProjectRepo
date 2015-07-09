package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;

/**
 * 
 * @author pkk
 */
public class InsertPlaceholderColumnRightAction extends
		TableInsertColumnRightAction {

	/**
	 * @param parameters
	 */
	public InsertPlaceholderColumnRightAction(ActionParameters parameters) {
		super(parameters);
	}

	
	/**
	 * @see com.odcgroup.page.ui.action.table.TableInsertColumnRightAction#getType()
	 */
	public String getType() {
		return "PlaceHolderColumn";
	}

}
