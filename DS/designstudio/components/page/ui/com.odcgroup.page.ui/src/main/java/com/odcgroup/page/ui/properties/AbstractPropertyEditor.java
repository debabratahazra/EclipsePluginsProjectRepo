package com.odcgroup.page.ui.properties;

import org.eclipse.jface.viewers.ICellEditorValidator;

import com.odcgroup.page.model.Property;


/**
 * Base class for PropertyEditor's.
 * 
 * @author Gary Hayes
 */
public abstract class AbstractPropertyEditor implements PropertyEditor {
	
	/** The Property.*/
	private Property property;

	/**
	 * Creates a new AbstractWidgetPropertyEditor.
	 * 
	 * @param property The property
	 */
	public AbstractPropertyEditor(Property property) {
		super();
		this.property = property;
	}

	/**
	 * Gets the name of the Editor.
	 * 
	 * @return String The name
	 */
	protected String getName() {
		return property.getEditorName();
	}
	
	/**
	 * Gets the Property.
	 * 
	 * @return Property The property
	 */
	protected Property getProperty() {
	    return property;
	}
	
	/**
	 * Loads the validator
	 * 
	 * @param name
	 * 			The validator to load
	 * @return ICellEditorValidator
	 * 			The validator loaded
	 * 			
	 */
	protected ICellEditorValidator makeCellValidator(String name) {
		CellEditorValidator cev = new CellEditorValidator(name, property);
		return cev;
	}	
}