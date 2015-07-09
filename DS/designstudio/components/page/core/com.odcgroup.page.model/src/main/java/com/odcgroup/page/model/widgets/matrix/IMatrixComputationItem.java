package com.odcgroup.page.model.widgets.matrix;

import java.util.List;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.model.Property;

/**
 * @author pkk
 *
 */
public interface IMatrixComputationItem {
	
	/**
	 * @return
	 */
	Property getComputation();
	
	/**
	 * @return
	 */
	List<String> getComputationParameters();
	
	/**
	 * @param parameters
	 */
	void setComputationParameters(List<String> parameters);
	
	/**
	 * @return
	 */
	IMatrix getMatrix();
	
	
	/**
	 * @param name
	 * @return
	 */
	MdfDatasetProperty getDatasetProperty(String name);

}
