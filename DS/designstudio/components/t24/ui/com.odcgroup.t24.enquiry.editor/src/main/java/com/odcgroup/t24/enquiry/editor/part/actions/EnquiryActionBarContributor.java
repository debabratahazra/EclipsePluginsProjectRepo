package com.odcgroup.t24.enquiry.editor.part.actions;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class EnquiryActionBarContributor extends ActionBarContributor {

	@Override
	protected void buildActions() {
		addRetargetAction(new DeleteRetargetAction());
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
	}

	@Override
	protected void declareGlobalActionKeys() {
		// currently none
	}

}
