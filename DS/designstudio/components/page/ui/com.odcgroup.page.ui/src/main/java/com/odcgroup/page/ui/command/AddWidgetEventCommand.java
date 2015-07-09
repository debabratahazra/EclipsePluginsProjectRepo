/**
 * 
 */
package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;

/**
 * Command to be executed when we want to add a new Event to a Widget. 
 * 
 * @author Alain Tripod
 * @version 1.0
 */
public class AddWidgetEventCommand extends BaseCommand {

	/** The widget to add the event to. */
	private Widget widget;
	
	/** The event to add. */
	private Event event;

	/**
	 * Constructor
	 * 
	 * @param widget
	 * 			The widget
	 * @param event
	 * 			The event
	 */
	public AddWidgetEventCommand(Widget widget, Event event) {
		Assert.isNotNull(widget);
		Assert.isNotNull(event);
		this.widget = widget;
		this.event = event;
		setLabel("Add "+event.getEventName()+" Event");
	}

	/**
	 * Executes the Command.
	 */
	public void execute() {
		widget.getEvents().add(event);
	}

	/**
	 * Undoes the Command.
	 */
	public void undo() {
		widget.getEvents().remove(event);
	}
	
	

}
