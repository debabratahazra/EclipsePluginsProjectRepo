package com.odcgroup.pageflow.editor.diagram.custom.policies;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.pageflow.editor.diagram.custom.commands.ToggleIDCommand;
import com.odcgroup.pageflow.editor.diagram.custom.commands.TransitionLabelDisplayCommand;
import com.odcgroup.pageflow.editor.diagram.custom.parts.PageflowShapeEditPart;
import com.odcgroup.pageflow.editor.diagram.custom.request.ChangeTransitionLabelDisplayRequest;
import com.odcgroup.pageflow.editor.diagram.custom.request.ToggleIDRequest;
import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowRequestConstants;
import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.PageflowEditPart;

public class PageflowActionLabelsEditPolicy extends AbstractEditPolicy {

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	public Command getCommand(Request request) {
		if (PageflowRequestConstants.REQ_CHANGE_TRANSITION_LABEL_DISPLAY.equals(request.getType())){
			List connections = ((DiagramEditPart)getHost()).getConnections();
			List children = ((PageflowEditPart)getHost()).getChildren();
			for(Iterator iter = children.iterator();iter.hasNext();){
				EditPart editpart = (EditPart) iter.next();
				if (editpart instanceof DecisionStateEditPart){
					connections.add(editpart);
				}
			}
			TransitionLabelDisplayCommand command = new TransitionLabelDisplayCommand("selectTransitionLabel", Display.getCurrent().getActiveShell(), connections);
			return new ICommandProxy(command);
		} else if (PageflowRequestConstants.REQ_TOGGLE_ID.equals(request.getType())) {
			List connections = ((DiagramEditPart)getHost()).getConnections();
			List children = ((PageflowEditPart)getHost()).getChildren();
			for(Iterator iter = children.iterator();iter.hasNext();){
				EditPart editpart = (EditPart) iter.next();
				if (editpart instanceof PageflowShapeEditPart){
					connections.add(editpart);
				}
			}
			ToggleIDCommand command = new ToggleIDCommand("ToggleIDAction", connections);
			return new ICommandProxy(command);
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#getTargetEditPart(org.eclipse.gef.Request)
	 */
	public EditPart getTargetEditPart(Request request) {
		if (PageflowRequestConstants.REQ_CHANGE_TRANSITION_LABEL_DISPLAY.equals(request.getType())){
			Command command = getHost().getCommand(
				new ChangeTransitionLabelDisplayRequest());
			if (command != null && command.canExecute())
				return getHost();
		} else if (PageflowRequestConstants.REQ_TOGGLE_ID.equals(request
				.getType())) {
			Command command = getHost().getCommand(new ToggleIDRequest());
			if (command != null && command.canExecute())
				return getHost();
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#understandsRequest(org.eclipse.gef.Request)
	 */
	public boolean understandsRequest(Request request) {
		if(PageflowRequestConstants.REQ_CHANGE_TRANSITION_LABEL_DISPLAY.equals(request.getType())) {
			return true;
		} else if (PageflowRequestConstants.REQ_TOGGLE_ID.equals(request.getType())) {
			return true;
		}
		return false;
	}

}
