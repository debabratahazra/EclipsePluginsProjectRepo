package com.tel.autosysframework.commands;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysGuide;
import com.tel.autosysframework.model.AutosysSubpart;

/**
 * @author Pratik Shah
 */
public class MoveGuideCommand 
	extends Command 
{

private int pDelta;
private AutosysGuide guide;
	
public MoveGuideCommand(AutosysGuide guide, int positionDelta) {
	super("MoveGuide Command");
	this.guide = guide;
	pDelta = positionDelta;
}

public void execute() {
	guide.setPosition(guide.getPosition() + pDelta);
	Iterator iter = guide.getParts().iterator();
	while (iter.hasNext()) {
		AutosysSubpart part = (AutosysSubpart)iter.next();
		Point location = part.getLocation().getCopy();
		if (guide.isHorizontal()) {
			location.y += pDelta;
		} else {
			location.x += pDelta;
		}
		part.setLocation(location);
	}
}

public void undo() {
	guide.setPosition(guide.getPosition() - pDelta);
	Iterator iter = guide.getParts().iterator();
	while (iter.hasNext()) {
		AutosysSubpart part = (AutosysSubpart)iter.next();
		Point location = part.getLocation().getCopy();
		if (guide.isHorizontal()) {
			location.y -= pDelta;
		} else {
			location.x -= pDelta;
		}
		part.setLocation(location);
	}
}

}
