package com.odcgroup.page.model.widgets.matrix;

import java.util.List;

import com.odcgroup.page.model.Property;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixContentCellItem extends IMatrixCellItem, IMatrixComputationItem {
	
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
	Property getAggregation();
	
	/**
	 * @return
	 */
	Property getAggregationItemsProperty();
	
	
	/**
	 * @return list of ids of corresponding items in aggregation cells of matrix
	 */
	List<String> getAggregationItems();
	
	/**
	 * @param id
	 */
	void removeAggregationItem(String id);
	
	/**
	 * @return
	 */
	Property getEnabledIsBasedOn();
	
	/**
	 * @return
	 */
	Property getEnabled();

	/**
	 * @return
	 */
	Property getDisplayFormat();
	
	/**
	 * @return
	 */
	Property getMeanWeight();
	
	/**
	 * @return
	 */
	boolean isWeightedMeanAggregation();
	
	void setAggregationItems(List<String> items);
	

}
