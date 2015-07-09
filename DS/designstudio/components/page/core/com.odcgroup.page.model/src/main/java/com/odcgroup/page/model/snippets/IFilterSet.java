package com.odcgroup.page.model.snippets;

import java.util.List;

/**
 * 
 * @author pkk
 * 
 */
public interface IFilterSet extends ISnippetAdapter {

	/**
	 * @return the filter ID
	 */
	String getId();

	/**
	 * @param id
	 */
	void setId(String id);

	/**
	 * @return filterset level
	 */
	int getLevel();

	/**
	 * @param level
	 */
	void setLevel(int level);
	
	/**
	 * @return <code>true</code> if the logical operator is enabled, and
	 *         <code>false</code> otherwise.
	 */
	boolean isLogicalOperatorEnabled();

	/**
	 * @return the target dataset name
	 */
	String getTargetDatasetName();

	/**
	 * @param datasetName
	 */
	void setTargetDataset(String datasetName);

	/**
	 * @param filter
	 */
	void addFilter(IFilter filter);

	/**
	 * @param filter
	 */
	void removeFilter(IFilter filter);

	/**
	 * @return the filters
	 */
	List<IFilter> getFilters();

	/**
	 * @param cancel
	 */
	void setCancelled(boolean cancel);

	/**
	 * @return boolean
	 */
	boolean isCancelled();

}
