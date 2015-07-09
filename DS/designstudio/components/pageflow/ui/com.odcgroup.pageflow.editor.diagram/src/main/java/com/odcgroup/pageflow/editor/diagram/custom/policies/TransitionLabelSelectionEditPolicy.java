package com.odcgroup.pageflow.editor.diagram.custom.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableLabelEditPolicy;

public class TransitionLabelSelectionEditPolicy extends
		NonResizableLabelEditPolicy {

	@Override
	protected void hideSelection() {
		super.hideSelection();
		getHost().getParent().setSelected(EditPart.SELECTED_NONE);
	}

	@Override
	protected void showSelection() {
		super.showSelection();
		getHost().getParent().setSelected(EditPart.SELECTED);
	}

}
