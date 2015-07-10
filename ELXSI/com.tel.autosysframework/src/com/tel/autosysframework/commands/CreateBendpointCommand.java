package com.tel.autosysframework.commands;

import com.tel.autosysframework.model.WireBendpoint;




public class CreateBendpointCommand 
	extends BendpointCommand 
{

public void execute() {
	WireBendpoint wbp = new WireBendpoint();
	wbp.setRelativeDimensions(getFirstRelativeDimension(), 
					getSecondRelativeDimension());
	getWire().insertBendpoint(getIndex(), wbp);
	super.execute();
}

public void undo() {
	super.undo();
	getWire().removeBendpoint(getIndex());
}

}


