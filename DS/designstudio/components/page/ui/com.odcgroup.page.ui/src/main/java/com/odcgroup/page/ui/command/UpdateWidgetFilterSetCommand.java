package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;

/**
 *
 * @author pkk
 *
 */
public class UpdateWidgetFilterSetCommand extends BaseCommand {

	/** The widget to which filterSet is added. */
	private Widget widget;
	
	/** The filterSet to add. */
	private IFilterSet filterSet;
	/** The filterSet to add. */
	private IFilterSet oldFilterSet;

	/**
	 * Constructor
	 * 
	 * @param widget
	 * 			The widget
	 * @param filterSet
	 * 			The filterSet
	 */
	public UpdateWidgetFilterSetCommand(Widget widget, IFilterSet filterSet) {
		Assert.isNotNull(widget);
		Assert.isNotNull(filterSet);
		this.widget = widget;
		this.filterSet = filterSet;
		setLabel("Update FilterSet");
	}

	/**
	 * Executes the Command.
	 */
	public void execute() {
		ISnippetFactory factory = SnippetUtil.getSnippetFactory();
		oldFilterSet = factory.adaptFilterSetSnippet(widget.getSnippets());
		if (oldFilterSet == null) {
			widget.getSnippets().add(filterSet.getSnippet());
		} else {
			EcoreUtil.replace(oldFilterSet.getSnippet(), filterSet.getSnippet());
		}
	}

	/**
	 * Undoes the Command.
	 */
	public void undo() {
		if (oldFilterSet != null) {
			EcoreUtil.replace(filterSet.getSnippet(), oldFilterSet.getSnippet());
		}
	}	

}