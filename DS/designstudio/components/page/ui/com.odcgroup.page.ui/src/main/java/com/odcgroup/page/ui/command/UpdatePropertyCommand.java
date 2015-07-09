package com.odcgroup.page.ui.command;

import com.odcgroup.page.model.Property;

/**
 * Define a command to update a property.
 * 
 * @author Alexandre Jaquet
 */
public class UpdatePropertyCommand extends BaseCommand {

	/** The property to update*/
	private Property property;	
	
	/** The new value to set*/
	private String newValue;
	
	/** The old value of the property*/
	private String oldValue;
	
	/**
	 * Constructor
	 * 
	 * @param property
	 * 				The property to udpate
	 * @param newValue
	 * 				The new value of the property
	 */
	public UpdatePropertyCommand(Property property, String newValue) {
		this.property = property;
		this.oldValue = property.getValue();
		this.newValue = newValue;
	}
	
	/**
	 * Executes the update command
	 *
	 */
	public void execute() {
		property.setValue(newValue);
	}
	
	/**
	 * Undos the update command
	 */
	public void undo () {
		property.setValue(oldValue);
	}
}
