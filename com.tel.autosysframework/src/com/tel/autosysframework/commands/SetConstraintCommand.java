package com.tel.autosysframework.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tel.autosysframework.model.AutosysSubpart;

public class SetConstraintCommand
	extends org.eclipse.gef.commands.Command
{

private Point newPos;
private Dimension newSize;
private Point oldPos;
private Dimension oldSize;
private AutosysSubpart part;

public void execute() {
	oldSize = part.getSize();
	oldPos  = part.getLocation();
	redo();
}

public String getLabel() {
	if (oldSize.equals(newSize))
		return "Move Oject";
	return "Resize Object";
}

public void redo() {
	part.setSize(newSize);
	part.setLocation(newPos);
}

public void setLocation(Rectangle r) {
	setLocation(r.getLocation());
	setSize(r.getSize());
}

public void setLocation(Point p) {
	newPos = p;
	
}

public void setPart(AutosysSubpart part) {
	this.part = part;
}

public void setSize(Dimension p) {
	newSize = p;
}

public void undo() {
	part.setSize(oldSize);
	part.setLocation(oldPos);
}

}
