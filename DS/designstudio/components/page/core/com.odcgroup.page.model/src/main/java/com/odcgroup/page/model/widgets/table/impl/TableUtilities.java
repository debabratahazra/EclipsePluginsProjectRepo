package com.odcgroup.page.model.widgets.table.impl;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.widgets.table.ITableFactory;
import com.odcgroup.page.model.widgets.table.ITableUtilities;

/**
 * This is the reference implementation of the interface {@link ITableUtilities}
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableUtilities implements ITableUtilities {

	/** The name of the Aggregated-Computation data type */
	private static final String AGGREGATED_COMPUTATION_DATA_TYPE = "AggregateComputation";

	/** The name of the Column Computation data type */
	private static final String COLUMN_COMPUTATION_DATA_TYPE = "ColumnComputation";

	/** The name of the Keep-Filter-Logic data type */
	private static final String KEEP_FILTER_LOGIC_DATA_TYPE = "KeepFilterLogic";

	/** The name of the Keep-Filter-Operator data type */
	private static final String KEEP_FILTER_OPERATOR_DATA_TYPE = "KeepFilterOperator";
	
	/** The internal string representation of the summary rendering mode (see data type TableRenderingMode) */
	private static final String SUMMARY_RENDERING_MODE = "summary-delegated";

	/** The internal string representation of the detailed-delegated rendering mode (see data type TableRenderingMode) */
	private static final String DETAILED_DELEGATED_RENDERING_MODE = "detailed-delegated";

	/** The name of the Sorting data type */
	private static final String SORTING_DATA_TYPE = "Sorting";
	
	/** the name of the event level type */
	private static final String EVENT_LEVEL = "EventLevel";

	/**
	 * The internal string representation of the weighted-mean value (see data
	 * type AggregatedComputation)
	 */
	private static final String WEIGHTED_MEAN_VALUE = "weighted-mean";

	/** the factory for all table*** adapters */
	private ITableFactory tableFactory = new TableFactory();

	/**
	 * Initializes this instance
	 */
	private void init() {
	}

	/**
	 * Find the DataType given its  name
	 * @param name
	 * @return DataType
	 */
	private DataType findDataType(String name) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		for (DataType dataType : metamodel.getDataTypes().getTypes()) {
			if (name.equals(dataType.getName())) {
				return dataType;
			}
		}
		return null;
	}

	/**
	 * Constructors
	 */
	public TableUtilities() {
		init();
	}

	/** 
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#getFactory()
	 */
	public final ITableFactory getFactory() {
		return tableFactory;
	}

	/** 
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#getAggregateComputations()
	 */
	public DataType getAggregateComputations() {
		return findDataType(TableUtilities.AGGREGATED_COMPUTATION_DATA_TYPE);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#getColumnComputations()
	 */
	public DataType getColumnComputations() {
		return findDataType(TableUtilities.COLUMN_COMPUTATION_DATA_TYPE);
	}

	/** 
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#getKeepFilterLogics()
	 */
	public DataType getKeepFilterLogics() {
		return findDataType(TableUtilities.KEEP_FILTER_LOGIC_DATA_TYPE);
	}

	/** 
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#getKeepFilterOperators()
	 */
	public DataType getKeepFilterOperators() {
		return findDataType(TableUtilities.KEEP_FILTER_OPERATOR_DATA_TYPE);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#getSortingDirections()
	 */
	public DataType getSortingDirections() {
		return findDataType(TableUtilities.SORTING_DATA_TYPE);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#getEventLevels()
	 */
	public DataType getEventLevels() {
		return findDataType(TableUtilities.EVENT_LEVEL);
	}

	/** 
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#isSummaryRenderingMode(com.odcgroup.page.model.Property)
	 */
	public boolean isSummaryRenderingMode(Property renderingMode) {
		String value = renderingMode.getValue();
		return TableUtilities.SUMMARY_RENDERING_MODE.equals(value);
	}
	
	/** 
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#isSummaryRenderingMode(com.odcgroup.page.model.Property)
	 */
	public boolean isDetailedDelegatedRenderingMode(Property renderingMode) {
		String value = renderingMode.getValue();
		return TableUtilities.DETAILED_DELEGATED_RENDERING_MODE.equals(value);
	}	

	/** 
	 * @see com.odcgroup.page.model.widgets.table.ITableUtilities#isWeightedMean(com.odcgroup.page.metamodel.DataValue)
	 */
	public boolean isWeightedMean(DataValue value) {
		return TableUtilities.WEIGHTED_MEAN_VALUE.equals(value.getValue());
	}

}
