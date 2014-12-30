package com.tel.autosysframework.model;

import org.eclipse.jface.viewers.ICellEditorValidator;



public class AutosysNumberCellEditorValidator
	implements ICellEditorValidator {

private static AutosysNumberCellEditorValidator instance;

public static AutosysNumberCellEditorValidator instance() {
	if (instance == null) 
		instance = new AutosysNumberCellEditorValidator();
	return instance;
}

public String isValid(Object value) {
	try {
		new Integer((String)value);
		return null;
	} catch (NumberFormatException exc) {
		return "Not a number";
	}
}

}
