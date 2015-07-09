package com.odcgroup.page.model.widgets.matrix;

import java.util.List;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixExtraColumn extends IMatrixCell {
	
	/**
	 */
	IMatrixExtraColumnItem addItem();
	
	/**
	 * @param item
	 */
	void addItem(IMatrixExtraColumnItem item);
	
	/**
	 * @return
	 */
	List<IMatrixExtraColumnItem> getItems();

}
