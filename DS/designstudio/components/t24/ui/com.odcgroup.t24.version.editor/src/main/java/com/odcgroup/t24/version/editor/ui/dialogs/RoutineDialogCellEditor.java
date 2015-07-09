package com.odcgroup.t24.version.editor.ui.dialogs;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
/**
 * DialogCell editor for the Routine selection
 * @author snn
 *
 */
public class RoutineDialogCellEditor extends DialogCellEditor {

    private Text routineText;

    public RoutineDialogCellEditor(Composite parent) {
	super(parent);
	doSetValue("");
    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
	RoutineSelectionDialog dialog = new RoutineSelectionDialog(cellEditorWindow.getShell(), false);
	if (dialog.open() == Dialog.OK) {
	    if(dialog.getResult().length>0) {
		Object firstResult = dialog.getFirstResult();
		if (firstResult !=null && firstResult instanceof IResource) {
		    IResource resource = (IResource) firstResult;
		    return resource.getName();
		}
	    }
	}
	return null;
    }

    @Override
    protected Control createContents(Composite cell) {
	routineText = new Text(cell, SWT.LEFT);
	routineText.setFont(cell.getFont());
	routineText.setBackground(cell.getBackground());
	return routineText;
    }

    @Override
    protected void updateContents(Object value) {
	if (routineText == null) {
	    return;
	}
	String textValue = "";
	if (value != null) {
	    textValue = value.toString();
	}
	routineText.setText(textValue);
    }
}
