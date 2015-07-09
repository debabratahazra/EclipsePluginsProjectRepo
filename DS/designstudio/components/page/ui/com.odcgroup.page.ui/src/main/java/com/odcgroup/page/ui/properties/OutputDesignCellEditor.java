package com.odcgroup.page.ui.properties;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.dialog.PageModelSelectionDialog;

/**
 * Cell editor for editing the property outputDesign.
 * 
 * @author pkk
 */
public class OutputDesignCellEditor extends DialogCellEditor {
	
	/**
	 * Constructor
	 * 
	 * @param parent 
	 * 			The parent component
	 * @param property The property being edited
	 */
    public OutputDesignCellEditor(Composite parent, Property property) {
        super(parent);
    }

    /**
     * Open dialog window
     * 
     * @param cellEditorWindow 
     * 			The cell editor window
     * @return Object The path
     */
    protected Object openDialogBox(Control cellEditorWindow) { 
    	Set<String> extns = new HashSet<String>();
		extns.add("module");
		int filter = PageModelSelectionDialog.AUTOCOMPLETE_DESIGN_FILTER;
		PageModelSelectionDialog dialog = PageModelSelectionDialog.createDialog(extns, filter);
    	dialog.open();
    	Object[] result = dialog.getResult();
    	
    	if (result == null || result.length == 0) {
    		return getValue();
    	}
    	
    	return result[0];
    }
}
