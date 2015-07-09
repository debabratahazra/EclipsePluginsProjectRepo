package com.odcgroup.page.model.widgets.matrix;

import com.odcgroup.page.metamodel.DataType;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixUtilities {
	
	IMatrixFactory getFactory();
	
	/**
	 * @return
	 */
	public DataType getCssClassType();
	
	/**
	 * @return
	 */
	DataType getAggregationType();

}
