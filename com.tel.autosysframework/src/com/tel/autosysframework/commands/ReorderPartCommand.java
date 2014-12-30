package com.tel.autosysframework.commands;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysSubpart;

public class ReorderPartCommand extends Command {

private int oldIndex, newIndex;
private AutosysSubpart child;
private AutosysDiagram parent;

public ReorderPartCommand(AutosysSubpart child, AutosysDiagram parent, int newIndex ) {
	super("ReorderPart Command");
	this.child = child;
	this.parent = parent;
	this.newIndex = newIndex;
}

public void execute() {
	oldIndex = parent.getChildren().indexOf(child);
	parent.removeChild(child);
	parent.addChild(child, newIndex);
}

public void undo() {
	parent.removeChild(child);
	parent.addChild(child, oldIndex);
}

}
