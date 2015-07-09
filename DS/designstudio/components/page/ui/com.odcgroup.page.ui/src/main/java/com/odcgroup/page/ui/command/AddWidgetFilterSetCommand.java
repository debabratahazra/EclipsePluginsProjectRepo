package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;

/**
 *
 * @author pkk
 *
 */
public class AddWidgetFilterSetCommand extends BaseCommand {

	/** The widget to which filterSet is added. */
	private Widget widget;
	
	/** The filterSet to add. */
	private IFilterSet filterSet;

	/**
	 * Constructor
	 * 
	 * @param widget
	 * 			The widget
	 * @param filterSet
	 * 			The filterSet
	 */
	public AddWidgetFilterSetCommand(Widget widget, IFilterSet filterSet) {
		Assert.isNotNull(widget);
		Assert.isNotNull(filterSet);
		this.widget = widget;
		this.filterSet = filterSet;
		setLabel("Add FilterSet");
	}

	/**
	 * Executes the Command.
	 */
	public void execute() {
		ISnippetFactory factory = SnippetUtil.getSnippetFactory();
		IFilterSet oldFilterSet = factory.adaptFilterSetSnippet(widget.getSnippets());
		if (oldFilterSet == null) {
			widget.getSnippets().add(filterSet.getSnippet());
		}
	}

	/**
	 * Undoes the Command.
	 */
	public void undo() {
		widget.getSnippets().remove(filterSet.getSnippet());
	}
	
	

}