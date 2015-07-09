package com.odcgroup.page.ui.command;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;

/**
 * Define a command to update an include property.
 * 
 * @author atr
 */
public class UpdateIncludePropertyCommand extends BaseCommand {

	/** The property to update*/
	private Property property;	
	
	/** The new model to set*/
	private Model newModel;
	
	/** The old model of the property*/
	private Model oldModel;
	
	/**
	 * Constructor
	 * 
	 * @param property
	 * 				The property to update
	 * @param newModel
	 * 				The new model of the property
	 */
	public UpdateIncludePropertyCommand(Property property, Model newModel) {
		this.property = property;
		this.oldModel = property.getModel();
		this.newModel = newModel;
	}
	
	/**
	 * Executes the update command
	 *
	 */
	public void execute() {
		property.setModel(newModel);
	}
	
	/**
	 * Undo the update command
	 */
	public void undo () {
		property.setModel(oldModel);
	}
}
