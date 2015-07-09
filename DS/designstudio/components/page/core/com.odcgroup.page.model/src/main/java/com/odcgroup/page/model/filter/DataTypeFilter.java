package com.odcgroup.page.model.filter;

import java.util.List;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;

/**
 * The DataTypeFilter filters datatype values for a given datatype.
 * 
 * @author pkk
 */
public interface DataTypeFilter {

	/**
	 * filters the datatype values
	 * 
	 * @param dataType
	 * @return list
	 */
	public List<DataValue> filter(DataType dataType);
}
