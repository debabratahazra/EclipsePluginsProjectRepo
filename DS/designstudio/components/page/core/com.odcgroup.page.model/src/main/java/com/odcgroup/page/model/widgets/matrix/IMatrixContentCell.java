package com.odcgroup.page.model.widgets.matrix;

import java.util.List;

import com.odcgroup.page.model.Widget;


/**
 *
 * @author pkk
 *
 */
public interface IMatrixContentCell extends IMatrixCell, IStyleProvider {
	
	/**
	 */
	IMatrixContentCellItem addItem(boolean computed);
	
	/**
	 * @param box
	 * @return
	 */
	IMatrixContentCellItem addToBox(Widget box, boolean computed);
	
	/**
	 * @param item
	 */
	void addItem(IMatrixContentCellItem item);
	
	/**
	 * @param item
	 * @param box
	 */
	void addItem(IMatrixContentCellItem item, Widget box);
	
	/**
	 * @return
	 */
	List<IMatrixContentCellItem> getItems();
	
	/**
	 * @return
	 */
	List<IMatrixContentCellItem> getComputedItems();
	
	/**
	 * @return
	 */
	List<IMatrixContentCellItem> getDomainItems();

}
