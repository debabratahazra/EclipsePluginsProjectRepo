package com.odcgroup.pageflow.editor.diagram.custom.policies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;

import com.odcgroup.pageflow.editor.diagram.custom.commands.ShowHideDescCommand;
import com.odcgroup.pageflow.editor.diagram.custom.request.ToggleDescriptionLabelRequest;
import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowRequestConstants;
import com.odcgroup.pageflow.editor.diagram.edit.parts.PageflowEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateEditPart;

public class DescLabelDisplayEditPolicy extends AbstractEditPolicy {

	@Override
	public Command getCommand(Request request) {
		if (PageflowRequestConstants.REQ_TOGGLE_DESCRIPTION.equals(request
				.getType())) {
			List children = ((PageflowEditPart) getHost()).getChildren();
			List content = new ArrayList();
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				EditPart editpart = (EditPart) iter.next();
				if (editpart instanceof ViewStateEditPart
						|| editpart instanceof SubPageflowStateEditPart) {
					content.add(editpart);
				}
			}
			ShowHideDescCommand command = new ShowHideDescCommand("ToggleDescAction", content);
			return new ICommandProxy(command);
		} 
		return null;
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		if (understandsRequest(request)) {
			Command command = getHost().getCommand(
					new ToggleDescriptionLabelRequest());
			if (command != null && command.canExecute())
				return getHost();
		}
		return null;
	}

	@Override
	public boolean understandsRequest(Request request) {
		return PageflowRequestConstants.REQ_TOGGLE_DESCRIPTION.equals(request.getType());
	}

}
