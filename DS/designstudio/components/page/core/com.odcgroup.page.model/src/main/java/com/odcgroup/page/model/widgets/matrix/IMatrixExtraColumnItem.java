package com.odcgroup.page.model.widgets.matrix;

import com.odcgroup.page.model.Property;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixExtraColumnItem extends IMatrixCellItem {
	
	/**
	 * @return
	 */
	boolean displayLastRow();
	
	/**
	 * @return
	 */
	Property getHorizontalAlignment();
	
	/**
	 * @return
	 */
	Property getVerticalAlignment();
	
	/**
	 * @return
	 */
	Property getColumnComputation();

	/**
	 * @return
	 */
	Property getDisplayFormat();
	
	/**
	 * @return
	 */
	String getCSSClass();

}
