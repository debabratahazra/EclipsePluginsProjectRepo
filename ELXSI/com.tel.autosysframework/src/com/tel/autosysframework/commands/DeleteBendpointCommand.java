package com.tel.autosysframework.commands;

import org.eclipse.draw2d.Bendpoint;

public class DeleteBendpointCommand 
	extends BendpointCommand 
{

private Bendpoint bendpoint;

public void execute() {
	bendpoint = (Bendpoint)getWire().getBendpoints().get(getIndex());
	getWire().removeBendpoint(getIndex());
	super.execute();
}

public void undo() {
	super.undo();
	getWire().insertBendpoint(getIndex(), bendpoint);
}

}


