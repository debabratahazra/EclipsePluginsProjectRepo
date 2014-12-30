package com.tel.autosysframework.commands;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysGuide;
import com.tel.autosysframework.model.AutosysSubpart;

/**
 * @author Pratik Shah
 */
public class ChangeGuideCommand 
	extends Command 
{

private AutosysSubpart part;
private AutosysGuide oldGuide, newGuide;
private int oldAlign, newAlign;
private boolean horizontal;

public ChangeGuideCommand(AutosysSubpart part, boolean horizontalGuide) {
	super();
	this.part = part;
	horizontal = horizontalGuide;
}

protected void changeGuide(AutosysGuide oldGuide, AutosysGuide newGuide, int newAlignment) {
	if (oldGuide != null && oldGuide != newGuide) {
		oldGuide.detachPart(part);
	}
	// You need to re-attach the part even if the oldGuide and the newGuide are the same
	// because the alignment could have changed
	if (newGuide != null) {
		newGuide.attachPart(part, newAlignment);
	}
}

public void execute() {
	// Cache the old values
	oldGuide = horizontal ? part.getHorizontalGuide() : part.getVerticalGuide();		
	if (oldGuide != null)
		oldAlign = oldGuide.getAlignment(part);
	
	redo();
}

public void redo() {
	changeGuide(oldGuide, newGuide, newAlign);
}

public void setNewGuide(AutosysGuide guide, int alignment) {
	newGuide = guide;
	newAlign = alignment;
}

public void undo() {
	changeGuide(newGuide, oldGuide, oldAlign);
}

}
