package com.odcgroup.page.model.snippets.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.SnippetUtil;

/**
 *
 * @author pkk
 *
 */
public class FilterSet extends SnippetAdapter implements IFilterSet {
	
	/**  */
	private static final String FILTER_TYPENAME = "Filter";
	/**  */
	private static final String LEVEL_PROPERTY = "level";
	/**  */
	private static final String TARGETDATASET_PROPERTY = "targetDataset";
	
	/** */
	private static final String LOGICALOPERATOR_ENABLED_PROPERTY = "logicalOperatorEnabled";
	/**  */
	private static final String CANCEL_PROPERTY = "cancel";
	
	/**
	 * @param snippet
	 */
	public FilterSet(Snippet snippet) {
		super(snippet);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#addFilter(com.odcgroup.page.model.snippets.IFilter)
	 */
	public void addFilter(IFilter filter) {
		if (filter != null) {
			getSnippet().getContents().add(filter.getSnippet());
		}
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#getFilters()
	 */
	public List<IFilter> getFilters() {
		List<IFilter> filters = new ArrayList<IFilter>();
		List<Snippet> snippets = getSnippet().getContents();
		for (Snippet snippet : snippets) {
			if (snippet.getTypeName().equals(FILTER_TYPENAME)) {
				IFilter filter = SnippetUtil.getSnippetFactory().adaptFilterSnippet(getSnippet(), snippet);
				filters.add(filter);
			}
		}
		return filters;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#getLevel()
	 */
	public int getLevel() {
		String val = getPropertyValue(LEVEL_PROPERTY);
		int level = -1;
		if (!StringUtils.isEmpty(val)) {
			try {
				Integer ii = new Integer(val);
				level = ii.intValue();
			} catch(Exception e) {
				
			}
		}
		return level;
	}

	

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#getTargetDatasetName()
	 */
	public String getTargetDatasetName() {
		return getPropertyValue(TARGETDATASET_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#removeFilter(com.odcgroup.page.model.snippets.IFilter)
	 */
	public void removeFilter(IFilter filter) {
		Snippet filterSnippet = filter.getSnippet();
		if (filterSnippet != null) {
			getSnippet().getContents().remove(filterSnippet);
		}

	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#setLevel(int)
	 */
	public void setLevel(int level) {
		setPropertyValue(LEVEL_PROPERTY, level);
	}
	
	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#isLogicalOperatorEnabled()
	 */
	public final boolean isLogicalOperatorEnabled() {
		return Boolean.valueOf(getPropertyValue(LOGICALOPERATOR_ENABLED_PROPERTY)).booleanValue();
	}


	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#setLogicalOperatorEnabled(boolean)
	 */
	public final void setLogicalOperatorEnabled(boolean enabled) {
		setPropertyValue(LOGICALOPERATOR_ENABLED_PROPERTY, enabled+"");
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#setTargetDataset(java.lang.String)
	 */
	public void setTargetDataset(String datasetName) {
		String previousValue = getTargetDatasetName();
		if (!StringUtils.isEmpty(previousValue)) {
			if (previousValue.equals(datasetName)) {
				return;
			}
			getSnippet().getContents().clear();
		}
		setPropertyValue(TARGETDATASET_PROPERTY, datasetName);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#getId()
	 */
	public String getId() {
		return getPropertyValue(PropertyTypeConstants.ID);
	}

	
	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#setId(java.lang.String)
	 */
	public void setId(String id) {
		setPropertyValue(PropertyTypeConstants.ID, id);			
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#isCancelled()
	 */
	public boolean isCancelled() {
		return Boolean.valueOf(getPropertyValue(CANCEL_PROPERTY)).booleanValue();
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilterSet#setCancelled(boolean)
	 */
	public void setCancelled(boolean cancel) {
		setPropertyValue(CANCEL_PROPERTY, ""+cancel);	
	}


}
