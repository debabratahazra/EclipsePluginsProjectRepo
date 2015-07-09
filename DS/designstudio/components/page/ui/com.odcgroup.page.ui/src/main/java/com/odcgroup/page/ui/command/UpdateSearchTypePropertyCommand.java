package com.odcgroup.page.ui.command;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * @author pkk
 * 
 * update search type command, which also updates the properties related to auto-complete
 */
public class UpdateSearchTypePropertyCommand extends BaseCommand {

	/** The property to update*/
	private Property property;	
	
	/** The new value to set*/
	private String newValue;
	
	/** The old value of the property*/
	private String oldValue;
	
	private Widget widget;
	
	private Property autodes;
	private Property delay;
	private Property nbchars;
	private Property accessHistoryItems;
	private Property favorite;
	
	private Model autodesold;
	private int delayold;
	private int nbcharsold;
	
	/**
	 * Constructor
	 * 
	 * @param property
	 * 				The property to udpate
	 * @param newValue
	 * 				The new value of the property
	 */
	public UpdateSearchTypePropertyCommand(Property property, String newValue) {
		this.property = property;
		this.oldValue = property.getValue();
		this.newValue = newValue;
		this.widget = property.getWidget();
		autodes = widget.findProperty("outputDesign");
		delay = widget.findProperty("auto-delay");
		nbchars = widget.findProperty("nb-chars");	
		accessHistoryItems = widget.findProperty("accessHistoryItems");
		favorite = widget.findProperty("favorite");
		
	}
	
	/**
	 * Executes the update command
	 *
	 */
	public void execute() {
		property.setValue(newValue);
		if ("searchOnly".equals(newValue)) {
			if (autodes != null) {
				autodesold = autodes.getModel();
				autodes.setModel(null);
			}
			if (delay != null && !StringUtils.isEmpty(delay.getValue())) {
				delayold = delay.getIntValue();
				delay.setValue(0);
			}
			if (nbchars != null && !StringUtils.isEmpty(nbchars.getValue())) {
				nbcharsold = nbchars.getIntValue();
				nbchars.setValue(0);
			}
			if(accessHistoryItems != null) {
				accessHistoryItems.setReadonly(true);
			}
			if(favorite != null) {
				favorite.setReadonly(true);
				favorite.setValue(false);
			}
		} else {
			if(accessHistoryItems != null) {
				accessHistoryItems.setReadonly(false);
			}
			if(favorite != null) {
				favorite.setReadonly(false);
				
			}
		}
		
	}
	
	/**
	 * Undos the update command
	 */
	public void undo () {
		property.setValue(oldValue);
		if ("searchOnly".equals(newValue)) {
			if (autodes != null) {
				autodes.setModel(autodesold);
				autodes.setReadonly(false);
			}
			if (delay != null) {
				delay.setValue(delayold);
				delay.setReadonly(false);
			}
			if (nbchars != null) {
				nbchars.setValue(nbcharsold);
				nbchars.setReadonly(false);
			}
		}
	}
	
}
