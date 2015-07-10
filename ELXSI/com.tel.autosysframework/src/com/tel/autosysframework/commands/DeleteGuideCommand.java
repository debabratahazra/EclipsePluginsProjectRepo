package com.tel.autosysframework.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysGuide;
import com.tel.autosysframework.model.AutosysRuler;
import com.tel.autosysframework.model.AutosysSubpart;

/**
 * @author Pratik Shah
 */
public class DeleteGuideCommand 
	extends Command 
{

private AutosysRuler parent;
private AutosysGuide guide;
private Map oldParts;

public DeleteGuideCommand(AutosysGuide guide, AutosysRuler parent) {
	super("DeleteGuide Command");
	this.guide = guide;
	this.parent = parent;
}

public boolean canUndo() {
	return true;
}

public void execute() {
	oldParts = new HashMap(guide.getMap());
	Iterator iter = oldParts.keySet().iterator();
	while (iter.hasNext()) {
		guide.detachPart((AutosysSubpart)iter.next());
	}
	parent.removeGuide(guide);
}
public void undo() {
	parent.addGuide(guide);
	Iterator iter = oldParts.keySet().iterator();
	while (iter.hasNext()) {
		AutosysSubpart part = (AutosysSubpart)iter.next();
		guide.attachPart(part, ((Integer)oldParts.get(part)).intValue());
	}
}
}
