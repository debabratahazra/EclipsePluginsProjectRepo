package com.odcgroup.page.pageflow.integration.ui.dialog.controls;

import com.odcgroup.page.model.Event;

/**
 * UI controls that are used as tab in the event definition dialog
 * 
 * @author pkk
 */
public interface IEventControl {
	
	/**
	 * validate the event 
	 * @return boolean
	 */
	public boolean isValid();
	
	/**
	 * return error msg incase if the event is not valid
	 * 
	 * @return msg
	 */
	public String getErrorMessage();
	
	/**
	 * @param listener
	 */
	public void addEventModelChangeListener(EventModelChangeListener listener);
	
	/**
	 * @param event
	 */
	public void notifyEventModelChange(Event event);

}
