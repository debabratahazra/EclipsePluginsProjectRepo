package com.tel.autosysframework.editpolicy;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class AutosysTreeEditPolicy
	extends AbstractEditPolicy
{

/*public Command getCommand(Request req){
	if (REQ_MOVE.equals(req.getType()))
		return getMoveCommand((ChangeBoundsRequest)req);
	return null;	
}*/

protected Command getMoveCommand(ChangeBoundsRequest req){
	EditPart parent = getHost().getParent();
	if(parent != null){
		ChangeBoundsRequest request = new ChangeBoundsRequest(REQ_MOVE_CHILDREN);
		request.setEditParts(getHost());
		request.setLocation(req.getLocation());
		return parent.getCommand(request);
	}
	return UnexecutableCommand.INSTANCE;
}

}
