package com.tel.autosysframework.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.Wire;

public class BendpointCommand 
	extends Command 
{

protected int index;
protected Point location;
protected Wire wire;
private Dimension d1, d2;

protected Dimension getFirstRelativeDimension() {
	return d1;
}

protected Dimension getSecondRelativeDimension() {
	return d2;
}

protected int getIndex() {
	return index;
}

protected Point getLocation() {
	return location;
}

protected Wire getWire() {
	return wire;
}

public void redo() {
	execute();
}

public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
	d1 = dim1;
	d2 = dim2;
}

public void setIndex(int i) {
	index = i;
}

public void setLocation(Point p) {
	location = p;
}

public void setWire(Wire w) {
	wire = w;
}

}


