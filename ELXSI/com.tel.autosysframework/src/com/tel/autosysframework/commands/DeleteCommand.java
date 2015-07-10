package com.tel.autosysframework.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysGuide;
import com.tel.autosysframework.model.AutosysSubpart;
import com.tel.autosysframework.model.Wire;

public class DeleteCommand
	extends Command
{

private AutosysSubpart child;
private AutosysDiagram parent;
private AutosysGuide vGuide, hGuide;
private int vAlign, hAlign;
private int index = -1;
private List sourceConnections = new ArrayList();
private List targetConnections = new ArrayList();

public DeleteCommand() {
	super("Delete Command");
}

private void deleteConnections(AutosysSubpart part) {
	if (part instanceof AutosysDiagram) {		
		List children = ((AutosysDiagram)part).getChildren();
		for (int i = 0; i < children.size(); i++)
			deleteConnections((AutosysSubpart)children.get(i));
	} 
	sourceConnections.addAll(part.getSourceConnections());
	for (int i = 0; i < sourceConnections.size(); i++) {
		Wire wire = (Wire)sourceConnections.get(i);
		wire.detachSource();
		wire.detachTarget();
	}
	targetConnections.addAll(part.getTargetConnections());
	for (int i = 0; i < targetConnections.size(); i++) {
		Wire wire = (Wire)targetConnections.get(i);
		wire.detachSource();
		wire.detachTarget();
	}
}

private void detachFromGuides(AutosysSubpart part) {
	if (part.getVerticalGuide() != null) {
		vGuide = part.getVerticalGuide();
		vAlign = vGuide.getAlignment(part);
		vGuide.detachPart(part);
	}
	if (part.getHorizontalGuide() != null) {
		hGuide = part.getHorizontalGuide();
		hAlign = hGuide.getAlignment(part);
		hGuide.detachPart(part);
	}
		
}
public void execute() {
	primExecute(); 
}

protected void primExecute() {
	deleteConnections(child);
	detachFromGuides(child);
	index = parent.getChildren().indexOf(child);
	parent.removeChild(child);
}

private void reattachToGuides(AutosysSubpart part) {
	if (vGuide != null)
		vGuide.attachPart(part, vAlign);
	if (hGuide != null)
		hGuide.attachPart(part, hAlign);
}

public void redo() {
	primExecute();
}

private void restoreConnections() {
	for (int i = 0; i < sourceConnections.size(); i++) {
		Wire wire = (Wire)sourceConnections.get(i);
		wire.attachSource();
		wire.attachTarget();
	}
	sourceConnections.clear();
	for (int i = 0; i < targetConnections.size(); i++) {
		Wire wire = (Wire)targetConnections.get(i);
		wire.attachSource();
		wire.attachTarget();
	}
	targetConnections.clear();
}

public void setChild (AutosysSubpart c) {
	child = c;
}

public void setParent(AutosysDiagram p) {
	parent = p;
}

public void undo() {
	parent.addChild(child, index);
	restoreConnections();
	reattachToGuides(child);
}

}
