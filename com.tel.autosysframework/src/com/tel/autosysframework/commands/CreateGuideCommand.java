package com.tel.autosysframework.commands;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysGuide;
import com.tel.autosysframework.model.AutosysRuler;

/**
 * @author Pratik Shah
 */
public class CreateGuideCommand extends Command {

private AutosysGuide guide;
private AutosysRuler parent;
private int position;

public CreateGuideCommand(AutosysRuler parent, int position) {
	super("CreateGuide Command");
	this.parent = parent;
	this.position = position;
}

public boolean canUndo() {
	return true;
}

public void execute() {
	if (guide == null)
		guide = new AutosysGuide(!parent.isHorizontal());
	guide.setPosition(position);
	parent.addGuide(guide);
}

public void undo() {
	parent.removeGuide(guide);
}

}
