package com.odcgroup.page.model.widgets.matrix;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixCellItem extends IWidgetAdapter, IStyleProvider {
	
	String ITEMTYPE_COMPUTED = "computed";
	String ITEMTYPE_DOMAIN = "domain";
	
	/**
	 * @return the domain attribute for the cell item
	 */
	Property getDomainAttribute();
	
	/**
	 * @return the aggregation type for the cell item
	 */
	Property getAggregationType();	
	
	/**
	 * @return the type of cell item
	 */
	Property getType();
	
	
	/**
	 * @return the unique identifier
	 */
	String getID();
	
	/**
	 * @param columnName
	 */
	void setColumnName(String columnName);
	
	/**
	 * @return
	 */
	String getColumnName();
	
	/**
	 * @return {@code true} if this item is computed, otherwise it returns
	 *         {@code false}
	 */
	boolean isComputed();

	
	/**
	 * @return {@code true} if this item is bound to a domain, otherwise it returns
	 *         {@code false}
	 */
	boolean isDomainBound();

}
