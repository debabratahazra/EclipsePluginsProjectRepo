package com.odcgroup.page.ui.snippet.controls;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;

/**
 * FilterSet control specific to an Event
 *
 * @author pkk
 *
 */
public class EventFilterSetControl extends FilterSetControl<Event> {
	
	/**  */
	private Event event = null;
	/** */
	private IFilterSet filterSet = null;
	/** */
	private int index = -1;

	/**
	 * @param parent
	 * @param style
	 * @param datasetrequired 
	 * @param index 
	 */
	public EventFilterSetControl(Composite parent, int style, boolean datasetrequired, int index) {
		super(parent, style, datasetrequired);
		this.index = index;
	}

	/**
	 * @see com.odcgroup.page.ui.snippet.controls.FilterSetControl#getFilterSet()
	 */
	public IFilterSet getFilterSet() {
		return filterSet;
	}

	/**
	 * @see com.odcgroup.page.ui.snippet.controls.FilterSetControl#setReflectiveInput(java.lang.Object)
	 */
	public void setReflectiveInput(Event parent) {
		this.event = parent;
		if (event != null) {
			ISnippetFactory factory = SnippetUtil.getSnippetFactory();
			filterSet = factory.adaptFilterSetSnippet(event.getSnippets(), index);
			if (filterSet == null) {
				filterSet = factory.createFilterSet();
				addFilterSet(event, filterSet);
			}
			updateDatasetControls();
		}
	}
	
	/**
	 * 
	 */
	public void updateDatasetControls() {
		String ds = getTargetDataset();
		if (StringUtils.isEmpty(ds)) {
			this.dsrequired = true;
			this.datasetText.setEnabled(true);
			this.browse.setEnabled(true);
		} else {
			this.dsrequired = false;
			this.datasetText.setText("");
			this.datasetText.setEnabled(false);
			this.browse.setEnabled(false);			
		}
		initFilterSetControls();
	}


	/**
	 * @see com.odcgroup.page.ui.snippet.controls.FilterSetControl#updateFilterSet(java.lang.Object, com.odcgroup.page.model.snippets.IFilterSet)
	 */
	public void updateFilterSet(Event parent, IFilterSet filterSet) {
	}

	/**
	 * @see com.odcgroup.page.ui.snippet.controls.FilterSetControl#addFilterSet(java.lang.Object, com.odcgroup.page.model.snippets.IFilterSet)
	 */
	public void addFilterSet(Event event, IFilterSet filterSet) {
		event.getSnippets().add(filterSet.getSnippet());		
	}

	/**
	 * @see com.odcgroup.page.ui.snippet.controls.AbstractSnippetControl#getContainer()
	 */
	public Event getContainer() {
		return event;
	}

	/**
	 * @see com.odcgroup.page.ui.snippet.controls.FilterSetControl#getTargetDataset()
	 */
	public String getTargetDataset() {
		IQuery query = SnippetUtil.getSnippetFactory().adaptQuerySnippet(event.getSnippets());
		if (query != null) {
			return query.getSearchModuleDomainEntity();
		}
		return null;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

}
