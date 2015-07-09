package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;

/**
 * 
 * @author pkk
 */
public class InsertPlaceholderColumnLeftAction extends
		TableInsertColumnLeftAction {

	/**
	 * @param parameters
	 */
	public InsertPlaceholderColumnLeftAction(ActionParameters parameters) {
		super(parameters);
	}	
	
	/**
	 * @see com.odcgroup.page.ui.action.table.TableInsertColumnLeftAction#getType()
	 */
	public String getType() {
		return "PlaceHolderColumn";
	}

}
