package com.odcgroup.page.ui.properties;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.util.MultiLineDialog;

/**
 * A multi lines cell editor to edit the code property.
 * 
 * @author atr
 *
 */
public class MultiLineCellEditor extends DialogCellEditor {
	
	/** The selected property*/
	private Property property;

	/**
	 * Constructor
	 * 
	 * @param parent 
	 * 			The parent component
	 * @param property
	 * 			The selected property
	 */
    public MultiLineCellEditor(Composite parent, Property property) {
        super(parent);
        this.property = property;
    }

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		String result = property.getValue();
		MultiLineDialog dialog = new MultiLineDialog(cellEditorWindow.getShell(), property);
		if (Dialog.OK == dialog.open()) {
			result = dialog.getText();
		}
		return result;
	}
}
