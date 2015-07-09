package com.odcgroup.page.model.widgets.matrix;

import com.odcgroup.page.model.Widget;

/**
 *
 * @author pkk
 *
 */
public interface IStyleProvider {
	
	/**
	 * @return the css class
	 */
	ICssClass getCssClass();
	
	/**
	 * @return
	 */
	Widget getWidget();

}
