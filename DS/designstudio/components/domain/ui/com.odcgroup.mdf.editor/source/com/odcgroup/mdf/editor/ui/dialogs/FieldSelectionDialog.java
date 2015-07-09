package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.odcgroup.mdf.metamodel.MdfProperty;

public class FieldSelectionDialog extends SelectionDialog {
	
	
	private ModelElementTreeSelectionUI elementUI;
   
	/**
	 * @param parent
	 * @param elementRenderer
	 * @param tableLableProvider
	 * @param decorator
	 * @param multi
	 */
	public FieldSelectionDialog(Shell parent, 
			ModelElementTreeSelectionUI elementUI) {
		super(parent);
		this.elementUI =elementUI;
		elementUI.createUI(parent);
		setTitle("Field Selection Dialog");
		setMessage("Choose a Field:");
	}
	
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(gridData);
		createMessageArea(composite);
		elementUI.createUI(composite);
		return composite;
	}
	
	
    private IStructuredSelection getSelection(){
    	return elementUI.getSelection();
    }
	
	protected Object[] getSelectedItem() {
		IStructuredSelection selection = getSelection();
		return selection.toArray();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void okPressed() {
		IStructuredSelection selection = elementUI.getSelection();
		if (selection.size() == 1) {
			Object input = getSelection().getFirstElement();
			if (input instanceof MdfProperty) {
				Object[] arr = {input};
				setResult(Arrays.asList(arr));
			}
		} else if (selection.size() > 1) {
			Iterator iter = selection.iterator();
			List<Object> fields = new ArrayList<Object>();
			while (iter.hasNext()) {
				Object input = iter.next();
				if (input instanceof MdfProperty) {
					fields.add(input);
				}
			}
			setResult(fields);
		}
		setReturnCode(OK);
		close();
	}

		
	
	

	public void setInput(Object inputElement){
		elementUI.setInput(inputElement);
	}
	
	protected Point getInitialSize() {
		return new Point(538, 785);
	}
}
