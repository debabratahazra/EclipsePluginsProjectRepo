package com.tel.autosysframework.commands;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysSubpart;

public class OrphanChildCommand
	extends Command
{

private Point oldLocation;
private AutosysDiagram diagram;
private AutosysSubpart child;
private int index;

public OrphanChildCommand () {
	super("OrphanChild Command");
}

public void execute() {
	List children = diagram.getChildren();
	index = children.indexOf(child);
	oldLocation = child.getLocation();
	diagram.removeChild(child);
}

public void redo() {
	diagram.removeChild(child);
}

public void setChild(AutosysSubpart child) {
	this.child = child;
}

public void setParent(AutosysDiagram parent) { 
	diagram = parent;
}

public void undo() {
	child.setLocation(oldLocation);
	diagram.addChild(child, index);
}

}
