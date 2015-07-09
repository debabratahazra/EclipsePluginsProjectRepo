package com.odcgroup.page.model.widgets.matrix;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixExtra extends IWidgetAdapter{

	/**
	 * Changes the domain attribute
	 * 
	 * @param attribute
	 */
	void setAttribute(MdfDatasetProperty attribute);

	/**
	 * @return the domain attribute
	 */
	MdfDatasetProperty getAttribute();
	
	/**
	 * @param type
	 */
	void setAggregationType(String type);
	
	/**
	 * @return
	 */
	String getAggregationType();
	
	/**
	 * @return
	 */
	String getColumnName();

}
