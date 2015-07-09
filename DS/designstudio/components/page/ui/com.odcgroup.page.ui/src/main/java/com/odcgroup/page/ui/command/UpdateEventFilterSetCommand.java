package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;

/**
 *
 * @author pkk
 *
 */
public class UpdateEventFilterSetCommand extends BaseCommand {

	/** The event to which filterSet is added. */
	private Event event;
	
	/** The filterSet to add. */
	private IFilterSet filterSet;
	/** The filterSet old value. */
	private IFilterSet oldFilterSet;
	/** */
	private int index;

	/**
	 * Constructor
	 * 
	 * @param event
	 * 			The event
	 * @param filterSet
	 * 			The filterSet
	 * @param index 
	 */
	public UpdateEventFilterSetCommand(Event event, IFilterSet filterSet, int index) {
		Assert.isNotNull(event);
		Assert.isNotNull(filterSet);
		this.event = event;
		this.filterSet = filterSet;
		this.index = index;
		setLabel("Add FilterSet");
	}

	/**
	 * Executes the Command.
	 */
	public void execute() {
		ISnippetFactory factory = SnippetUtil.getSnippetFactory();
		oldFilterSet = factory.adaptFilterSetSnippet(event.getSnippets(), index);
		if (oldFilterSet == null) {
			event.getSnippets().add(filterSet.getSnippet());
		} else {
			event.getSnippets().remove(oldFilterSet.getSnippet());
			event.getSnippets().add(filterSet.getSnippet());
		}
	}

	/**
	 * Undoes the Command.
	 */
	public void undo() {
		event.getSnippets().remove(filterSet.getSnippet());
		if (oldFilterSet != null) {
			event.getSnippets().add(oldFilterSet.getSnippet());
		} 
	}
	
	

}