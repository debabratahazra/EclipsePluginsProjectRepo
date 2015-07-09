package com.odcgroup.page.model.widgets.matrix;

import com.odcgroup.page.model.Widget;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixFactory {
	
	/**
	 * @param widget
	 * @return
	 */
	IMatrix adaptMatrixWidget(Widget widget);
	
	/**
	 * @param widget
	 * @return
	 */
	IMatrixAxis adaptMatrixAxisWidget(Widget widget);
	
	/**
	 * @param widget
	 * @return
	 */
	IMatrixCell adaptMatrixCellWidget(Widget widget);
	
	/**
	 * @param widget
	 * @return
	 */
	IMatrixContentCell adaptMatrixContentCellWidget(Widget widget);
	
	/**
	 * @param widget
	 * @return
	 */
	IMatrixCellItem adaptMatrixCellItemWidget(Widget widget);
	
	/**
	 * @param widget
	 * @return
	 */
	IMatrixContentCellItem adaptMatrixContentCellItemWidget(Widget widget);
	
	/**
	 * @param widget
	 * @return
	 */
	IMatrixExtraColumn adapatMatrixExtraColumnWidget(Widget widget);
	
	/**
	 * @param widget
	 * @return
	 */
	IMatrixExtraColumnItem adapatMatrixExtraColumnItemWidget(Widget widget);
	
	/**
	 * @return
	 */
	IMatrixExtra createTableExtra();

}
