package com.odcgroup.t24.enquiry.properties.dialogs;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class OperatorExpressionSelectionDialog extends MultiValueSelectionDialog {
	
	private EObject operationModel;
	private EObject fieldModel;
	/**
	 * @param parentShell
	 * @param dialogText
	 * @param slectedValues
	 * @param isSequential
	 */
	public OperatorExpressionSelectionDialog(Shell parentShell, String dialogText, List<String> slectedValues,
			boolean isSequential,EObject fieldModel, EObject operationModel,String label) {
		super(parentShell, dialogText, slectedValues, isSequential,label);
		this.operationModel = operationModel;
		this.fieldModel = fieldModel;
	}

	@Override
	protected void addButtonSelection() {
		FieldOperatorSelectionDialog codeDialog = new FieldOperatorSelectionDialog(getShell(),operationModel,fieldModel);
		int i = codeDialog.open();
		if (i == Dialog.OK) {
			if (codeDialog.getOperatorField() != null && (!codeDialog.getOperatorField().isEmpty())) {
				valueList.add(codeDialog.getOperatorField());
				if (!removeButton.isEnabled()) {
					removeButton.setEnabled(true);
				}
				enableSequenceButton();
			}
		}
	}
}
