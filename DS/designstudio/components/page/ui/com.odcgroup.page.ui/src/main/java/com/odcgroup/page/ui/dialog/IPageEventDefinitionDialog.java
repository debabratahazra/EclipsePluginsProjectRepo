package com.odcgroup.page.ui.dialog;

import com.odcgroup.page.model.Event;

/**
 * a dialog to define/update an event for a specific widget
 * 
 * @author pkk
 *
 */
public interface IPageEventDefinitionDialog {
	
	/**
	 * returns the created/modified Event model back
	 * @return Event
	 */
	public Event getDefinedEvent();
	
	/**
	 * @return status
	 */
	public boolean close();
	
	/**
	 * returns the Window status code OK or CANCEL
	 * @return status code
	 */
	public int open();

}
