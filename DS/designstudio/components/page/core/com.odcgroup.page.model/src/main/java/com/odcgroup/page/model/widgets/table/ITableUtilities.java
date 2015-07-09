package com.odcgroup.page.model.widgets.table;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;

/**
 * This ITableUtilities exposes services that should help to build GUI specific to 
 * the following table elements (Group, Aggregate, Sort)<p>  
 * 
 * The DataType returned shall be used to fill comboboxes, Then you can use the specific
 * methods defines in interface {@link ITableGroup}, {@link ITableAggregate} and {@link ITableSort} to get/set the
 * value of the corresponding attribute. <p>
 * 
 * The method {@link DataType#getValues()} will help to retrieve all values of the type.<p>
 * The method {@link DataType#findDataValue(String)} will help to set the selection in the combo.<p>
 * 
 *
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableUtilities {

	/**
	 * @return the factory to create adapter for Table/Tree widgets
	 */
	ITableFactory getFactory();

	/**
	 * @return the AggregateComputation data type
	 */
	DataType getAggregateComputations();

	/**
	 * @return the KeepFilterLogic data type
	 */
	DataType getKeepFilterLogics();

	/**
	 * @return the KeepFilterOperator data type
	 */
	DataType getKeepFilterOperators();
	
	/**
	 * @return the column computation data type
	 */
	DataType getColumnComputations();	

	/**
	 * @return the SortingDirection data type
	 */
	DataType getSortingDirections();
	
	/**
	 * @return the event levels data type
	 */
	DataType getEventLevels();

	/**
	 * This method checks if the value of the property represents the summary rendering mode
	 * @param renderingMode the property value to be checked
	 * @return {@code true} if the property represents the summary rendering mode,
	 *         otherwise it return {@code false}
	 */
	boolean isSummaryRenderingMode(Property renderingMode);

	/**
	 * This method checks if the value of the property represents the detailed delegated rendering mode
	 * @param renderingMode the property value to be checked
	 * @return {@code true} if the property represents the detailed delegated rendering mode,
	 *         otherwise it return {@code false}
	 */
	boolean isDetailedDelegatedRenderingMode(Property renderingMode);

	/**
	 * This method helps to implements some logic required in the Table Aggregate dialog.
	 * @param value the value to be tested.
	 * @return {@code true} if the specified value represents the weighted mean,
	 *         otherwise it return {@code false}
	 */
	boolean isWeightedMean(DataValue value);
	

}
