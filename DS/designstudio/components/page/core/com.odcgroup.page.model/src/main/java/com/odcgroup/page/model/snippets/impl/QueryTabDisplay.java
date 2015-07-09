package com.odcgroup.page.model.snippets.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.IQueryTabDisplay;
import com.odcgroup.page.model.snippets.SnippetUtil;

/**
 *
 * @author pkk
 *
 */
public class QueryTabDisplay extends SnippetAdapter implements IQueryTabDisplay {
	
	private IQuery query;

	/** module tab - id & name map */
	private Map<String, String> tabMap;

	/**
	 * @param snippet
	 * @param query 
	 */
	public QueryTabDisplay(Snippet snippet, IQuery query) {
		super(snippet);
		this.query = query;
		tabMap = SnippetUtil.parseModuleForTabs(query.getSearchCriteriaModuleModel());
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQueryTabDisplay#getEnabledTabs()
	 */
	public String[] getEnabledTabs() {
		String value = getPropertyValue(PropertyTypeConstants.ENABLED_TABS);
		List<String> tabNames = new ArrayList<String>();
		if (!StringUtils.isEmpty(value)) {
			String[]  ids = value.split(",");
			for (String id : ids) {
				tabNames.add(getTabName(id));
			}
		}
		return tabNames.toArray(new String[0]);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQueryTabDisplay#getSelection()
	 */
	public String getSelection() {
		return getPropertyValue(PropertyTypeConstants.SELECTED_TAB);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQueryTabDisplay#setEnabledTabs(java.lang.String[])
	 */
	public void setEnabledTabs(String[] tabs) {
		if (tabs != null) {
			String value = joinByDelimiter(tabs, ",");
			setPropertyValue(PropertyTypeConstants.ENABLED_TABS, value);
		}	
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQueryTabDisplay#setSelection(java.lang.String)
	 */
	public void setSelection(String selection) {
		if (!StringUtils.isEmpty(selection)) {
			setPropertyValue(PropertyTypeConstants.SELECTED_TAB, selection);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(getSelection())) {
			sb.append("Displayed ["+getTabName(getSelection())+"]");
		}
		if (getEnabledTabs() != null) {
			int tabs = getEnabledTabs().length;
			if (tabs > 0) {
				sb.append(", Available [");
			}
			sb.append(joinByDelimiter(getEnabledTabs(), ","));
			if (tabs > 0) {
				sb.append("]");
			}
		}
		return sb.toString();
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQueryTabDisplay#getEnabledTabsAsString()
	 */
	public String getEnabledTabsAsString() {		
		return joinByDelimiter(getEnabledTabs(), ",");
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQueryTabDisplay#getQuery()
	 */
	public IQuery getQuery() {
		return query;
	}
	
	
	/**
	 * @param id
	 * @return name
	 */
	private String getTabName(String id) {
		String name = null;
		if (tabMap.containsKey(id)) {
			name = tabMap.get(id);
		}
		return name;
	}	

}
