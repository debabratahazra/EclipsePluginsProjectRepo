package com.odcgroup.page.model.widgets.table.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITableAggregate;

/**
 * This is the reference implementation of ITableAggregate, as a Widget Adapter.
 *
 * @author atr
 * @since DS 1.40.0
 */
public class TableAggregate extends TableFeature implements ITableAggregate {
	
	/** Name of the property that contains the column name */
	private static final String COLUMN_NAME_PROPERTY = "aggregate-column-name";
	/** Name of the property that contains the kind of computation */
	private static final String COMPUTATION_PROPERTY = "aggregate-computation";
	/** Name of the property that contains related existing group */
	private static final String GROUP_NAMES_PROPERTY = "aggregate-group-names";
	/** Name of the property that contains an extra column name when computation is equals to a specific value */
	private static final String OTHER_COLUMN_NAME_PROPERTY = "aggregate-other-column-name";
	
	/**
	 * @return the names of groups this aggregate is bound to
	 */
	private Set<String> doGetGroupNames() {
		Set<String> names = new LinkedHashSet<String>();
		String values = getPropertyValue(GROUP_NAMES_PROPERTY);
		StringTokenizer tokenizer = new StringTokenizer(values, ",");
		while (tokenizer.hasMoreTokens()) {
			names.add(tokenizer.nextToken());
		}
		return names;
	}
	
	/**
	 * @param names
	 */
	private void doSetGroupNames(Set<String> names) {
		String values = StringUtils.join(names.toArray(new String[]{}),',');
		setPropertyValue(GROUP_NAMES_PROPERTY, values);
	}

	/**
	 * Constructs a new Aggregate Widget Adapter
	 * @param widget the adapted widget
	 */
	public TableAggregate(Widget widget) {
		super(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#addGroupNames(java.lang.String[])
	 */
	public void addGroupNames(String... names) {
		Set<String> groupNames = doGetGroupNames();
		for (String name : names) {
			groupNames.add(name);
		}
		doSetGroupNames(groupNames);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#getColumnName()
	 */
	public String getColumnName() {
		return getPropertyValue(COLUMN_NAME_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#getComputation()
	 */
	public String getComputation() {
		return getPropertyValue(COMPUTATION_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#getGroupNames()
	 */
	public String[] getGroupNames() {
		return doGetGroupNames().toArray(new String[]{});
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#getOtherColumnName()
	 */
	public String getOtherColumnName() {
		return getPropertyValue(OTHER_COLUMN_NAME_PROPERTY);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#hasGroupName(java.lang.String)
	 */
	public boolean hasGroupName(String name) {
		return doGetGroupNames().contains(name);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#removeGroupNames(java.lang.String[])
	 */
	public void removeGroupNames(String... names) {
		Set<String> groupNames = doGetGroupNames();
		for (String name : names) {
			groupNames.remove(name);
		}
		doSetGroupNames(groupNames);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#setColumnName(java.lang.String)
	 */
	public void setColumnName(String name) {
		setPropertyValue(COLUMN_NAME_PROPERTY, name);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#setComputation(java.lang.String)
	 */
	public void setComputation(String value) {
		setPropertyValue(COMPUTATION_PROPERTY, value);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableAggregate#setOtherColumn(java.lang.String)
	 */
	public void setOtherColumn(String name) {
		setPropertyValue(OTHER_COLUMN_NAME_PROPERTY, name);
	}
	
}
