package com.tel.autosysframework.editpolicy;


import org.eclipse.gef.commands.Command;

import org.eclipse.gef.requests.GroupRequest;

import com.tel.autosysframework.commands.DeleteCommand;
import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysSubpart;

public class AutosysElementEditPolicy
	extends org.eclipse.gef.editpolicies.ComponentEditPolicy
{
	

protected Command createDeleteCommand(GroupRequest request) {
	Object parent = getHost().getParent().getModel();
	DeleteCommand deleteCmd = new DeleteCommand();
	deleteCmd.setParent((AutosysDiagram)parent);
	deleteCmd.setChild((AutosysSubpart)getHost().getModel());
	return deleteCmd;
}


}
