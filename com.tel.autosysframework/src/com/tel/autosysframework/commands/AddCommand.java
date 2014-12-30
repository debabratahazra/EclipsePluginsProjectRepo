package com.tel.autosysframework.commands;

import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysSubpart;

public class AddCommand extends
	org.eclipse.gef.commands.Command
{

private AutosysSubpart child;
private AutosysDiagram parent;
private int index = -1;

public AddCommand() {
	super("Add Command");
}

public void execute() {
	if( index < 0 )
		parent.addChild(child);
	
	else
		parent.addChild(child,index);
}

public AutosysDiagram getParent() {
	return parent;
}

public void redo() {
	if( index < 0 )
		parent.addChild(child);
	else
		parent.addChild(child,index);
}

public void setChild(AutosysSubpart subpart) {
	child = subpart;
}

public void setIndex(int i){
	index = i;
}

public void setParent(AutosysDiagram newParent) {
	parent = newParent;
}

public void undo() {
	parent.removeChild(child);
}

}
