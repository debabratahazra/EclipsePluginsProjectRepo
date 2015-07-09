package com.odcgroup.page.ui.snippet.controls;

import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;

/**
 * FilterSet Control specific to a Widget
 *
 * @author pkk
 *
 */
public abstract class WidgetFilterSetControl extends FilterSetControl<Widget> {
	
	/** */
	private Widget widget = null;
	/** */
	private IFilterSet filterSet = null;

	/**
	 * @param parent
	 * @param style
	 * @param dsrequired 
	 */
	public WidgetFilterSetControl(Composite parent, int style, boolean dsrequired) {
		super(parent, style, dsrequired);
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
	public void setReflectiveInput(Widget parent) {
		this.widget = parent;
		if (widget != null) {
			ISnippetFactory factory = SnippetUtil.getSnippetFactory();
			filterSet = factory.adaptFilterSetSnippet(widget.getSnippets());
			if (filterSet == null) {
				filterSet = factory.createFilterSet();
				addFilterSet(parent, filterSet);
			}
		}
	}

	/**
	 * @see com.odcgroup.page.ui.snippet.controls.AbstractSnippetControl#getContainer()
	 */
	public Widget getContainer() {
		return widget;
	}	

}
