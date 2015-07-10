package com.tel.autosysframework.commands;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysSubpart;

public class CreateCommand
	extends org.eclipse.gef.commands.Command
{

private AutosysSubpart child;
private Rectangle rect;
private AutosysDiagram parent;
private int index = -1;

public CreateCommand() {
	super("Create Command");
}

public boolean canExecute() {
	return child != null && parent != null;
}

public void execute() {
	if (rect != null) {
		Insets expansion = getInsets();
		if (!rect.isEmpty())
			rect.expand(expansion);
		else {
			rect.x -= expansion.left;
			rect.y -= expansion.top;
		}
		child.setLocation(rect.getLocation());
		if (!rect.isEmpty())
			child.setSize(rect.getSize());
	}
	redo();
}

private Insets getInsets() {
	/*if (child instanceof LED || child instanceof Circuit)
		return new Insets(2, 0, 2, 0);*/
	return new Insets();
}

public void redo() {
	parent.addChild(child,index);
}

public void setChild(AutosysSubpart subpart) {
	child = subpart;
}

public void setIndex( int index ){
	this.index = index;
}

public void setLocation (Rectangle r) {
	rect = r;
}

public void setParent(AutosysDiagram newParent) {
	parent = newParent;
}

public void undo() {
	parent.removeChild(child);
}

public void setConstraint(Rectangle bounds) {
	// TODO Auto-generated method stub
	rect = bounds;
}

}
