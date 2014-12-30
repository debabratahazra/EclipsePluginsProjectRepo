package com.tel.autosysframework.editpolicy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import com.tel.autosysframework.commands.ConnectionCommand;
import com.tel.autosysframework.model.Wire;

public class WireEditPolicy
	extends org.eclipse.gef.editpolicies.ConnectionEditPolicy
{

protected Command getDeleteCommand(GroupRequest request) {
	ConnectionCommand c = new ConnectionCommand();
	c.setWire((Wire)getHost().getModel());
	return c;
}

}
