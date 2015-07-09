/**
 * 
 */
package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;

/**
 * Command to be executed when we want to remove an Event from a Widget. 
 * 
 * @author Alain Tripod
 * @version 1.0
 */
public class DeleteWidgetEventCommand extends BaseCommand {

	/** The widget to delete the event from. */
	private Widget widget;
	
	/** The event to delete. */
	private Event event;
	
	/**
	 * Constructor
	 * 
	 * @param widget
	 * 			The widget
	 * @param event
	 * 			The event
	 */
	public DeleteWidgetEventCommand(Widget widget, Event event) {
		Assert.isNotNull(widget);
		Assert.isNotNull(event);
		this.widget = widget;
		this.event = event;
		
		setLabel("Remove "+event.getEventName()+" Event");
	}

	/**
	 * Executes the Command.
	 */
	public void execute() {
		widget.getEvents().remove(event);
	}

	/**
	 * Undoes the Command.
	 */
	public void undo() {
		widget.getEvents().add(event);
	}


}
