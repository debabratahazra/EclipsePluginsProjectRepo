package com.odcgroup.page.model.widgets.table;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;

/**
 * The interface ITableExtra provides services to ease the management of a Extra
 * element in a Table Widget. In practice it will be used to implements specific
 * UI & related commands, and also to implements specific PSM transformers.
 * <p>
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableExtra extends ITableFeature, IWidgetAdapter {

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

}
