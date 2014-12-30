package com.tel.autosysframework.commands;

import org.eclipse.draw2d.Bendpoint;

import com.tel.autosysframework.model.WireBendpoint;

public class MoveBendpointCommand
	extends BendpointCommand 
{

private Bendpoint oldBendpoint;

public void execute() {
	WireBendpoint bp = new WireBendpoint();
	bp.setRelativeDimensions(getFirstRelativeDimension(), 
					getSecondRelativeDimension());
	setOldBendpoint((Bendpoint)getWire().getBendpoints().get(getIndex()));
	getWire().setBendpoint(getIndex(), bp);
	super.execute();
}

protected Bendpoint getOldBendpoint() {
	return oldBendpoint;
}

public void setOldBendpoint(Bendpoint bp) {
	oldBendpoint = bp;
}

public void undo() {
	super.undo();
	getWire().setBendpoint(getIndex(), getOldBendpoint());
}

}


