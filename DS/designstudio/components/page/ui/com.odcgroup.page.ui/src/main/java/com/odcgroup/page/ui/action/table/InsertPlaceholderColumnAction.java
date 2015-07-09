package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;

/**
 * @author pkk
 *
 */
public class InsertPlaceholderColumnAction extends TableInsertColumnAction {

	/**
	 * @param parameters
	 */
	public InsertPlaceholderColumnAction(ActionParameters parameters) {
		super(parameters);
	}

	
	/**
	 * @see com.odcgroup.page.ui.action.table.TableInsertColumnAction#getType()
	 */
	public String getType() {
		return "PlaceHolderColumn";
	}

}
