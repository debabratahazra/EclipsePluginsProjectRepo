package com.tel.autosysframework.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysGuide;
import com.tel.autosysframework.model.AutosysSubpart;
import com.tel.autosysframework.model.Modulator;
import com.tel.autosysframework.model.Wire;
import com.tel.autosysframework.model.WireBendpoint;



public class CloneCommand
	extends Command
{

private List parts, newTopLevelParts, newConnections;
private AutosysDiagram parent;
private Map bounds, indices, connectionPartMap;
private ChangeGuideCommand vGuideCommand, hGuideCommand;
private AutosysGuide hGuide, vGuide;
private int hAlignment, vAlignment;

public CloneCommand() {
	super("Clone Command");
	parts = new LinkedList();
}

public void addPart(AutosysSubpart part, Rectangle newBounds) {
	parts.add(part);
	if (bounds == null) {
		bounds = new HashMap();
	}
	bounds.put(part, newBounds);
}

public void addPart(AutosysSubpart part, int index) {
	parts.add(part);
	if (indices == null) {
		indices = new HashMap();
	}
	indices.put(part, new Integer(index));
}

protected void clonePart(AutosysSubpart oldPart, AutosysDiagram newParent, Rectangle newBounds,
					  List newConnections, Map connectionPartMap, int index) {
	AutosysSubpart newPart = null;
	
	/*if (oldPart instanceof AutosysLabel) {
		newPart = new AutosysLabel();
		((AutosysLabel)newPart).setLabelContents(((AutosysLabel)oldPart).getLabelContents());
	} else if (oldPart instanceof AutosysFlowContainer) {
		newPart = new AutosysFlowContainer();
	} else*/ if (oldPart instanceof Modulator) {
		newPart = new Modulator();
	} 
		
	if (oldPart instanceof AutosysDiagram) {
		Iterator i = ((AutosysDiagram)oldPart).getChildren().iterator();
		while (i.hasNext()) {
			// for children they will not need new bounds
			clonePart((AutosysSubpart)i.next(), (AutosysDiagram)newPart, null, 
					newConnections, connectionPartMap, -1);
		}
	}
	
	Iterator i = oldPart.getTargetConnections().iterator();
	while (i.hasNext()) {
		Wire connection = (Wire)i.next();
		Wire newConnection = new Wire();
		newConnection.setValue(connection.getValue());
		newConnection.setTarget(newPart);
		newConnection.setTargetTerminal(connection.getTargetTerminal());
		newConnection.setSourceTerminal(connection.getSourceTerminal());
		newConnection.setSource(connection.getSource());
	
		Iterator b = connection.getBendpoints().iterator();
		Vector newBendPoints = new Vector();
		
		while (b.hasNext()) {
			WireBendpoint bendPoint = (WireBendpoint)b.next();
			WireBendpoint newBendPoint = new WireBendpoint();
			newBendPoint.setRelativeDimensions(bendPoint.getFirstRelativeDimension(), 
					bendPoint.getSecondRelativeDimension());
			newBendPoint.setWeight(bendPoint.getWeight());
			newBendPoints.add(newBendPoint);
		}
		
		newConnection.setBendpoints(newBendPoints);
		newConnections.add(newConnection);
	}
	
	
	if (index < 0) {
		newParent.addChild(newPart);
	} else {
		newParent.addChild(newPart, index);
	}
	
	newPart.setSize(oldPart.getSize());

	
	if (newBounds != null) {
		newPart.setLocation(newBounds.getTopLeft());
	} else {
		newPart.setLocation(oldPart.getLocation());
	}
	
	// keep track of the new parts so we can delete them in undo
	// keep track of the oldpart -> newpart map so that we can properly attach
	// all connections.
	if (newParent == parent)
		newTopLevelParts.add(newPart);
	connectionPartMap.put(oldPart, newPart);
}

public void execute() {
	connectionPartMap = new HashMap();
	newConnections = new LinkedList();
	newTopLevelParts = new LinkedList();

	Iterator i = parts.iterator();
	
	AutosysSubpart part = null;
	while (i.hasNext()) {
		part = (AutosysSubpart)i.next();
		if (bounds != null && bounds.containsKey(part)) {
			clonePart(part, parent, (Rectangle)bounds.get(part), 
					newConnections, connectionPartMap, -1);	
		} else if (indices != null && indices.containsKey(part)) {
			clonePart(part, parent, null, newConnections, 
					connectionPartMap, ((Integer)indices.get(part)).intValue());
		} else {
			clonePart(part, parent, null, newConnections, connectionPartMap, -1);
		}
	}
	
	// go through and set the source of each connection to the proper source.
	Iterator c = newConnections.iterator();
	
	while (c.hasNext()) {
		Wire conn = (Wire)c.next();
		AutosysSubpart source = conn.getSource();
		if (connectionPartMap.containsKey(source)) {
			conn.setSource((AutosysSubpart)connectionPartMap.get(source));
			conn.attachSource();
			conn.attachTarget();
		}
	}
	
	if (hGuide != null) {
		hGuideCommand = new ChangeGuideCommand(
				(AutosysSubpart)connectionPartMap.get(parts.get(0)), true);
		hGuideCommand.setNewGuide(hGuide, hAlignment);
		hGuideCommand.execute();
	}
		
	if (vGuide != null) {
		vGuideCommand = new ChangeGuideCommand(
				(AutosysSubpart)connectionPartMap.get(parts.get(0)), false);
		vGuideCommand.setNewGuide(vGuide, vAlignment);
		vGuideCommand.execute();
	}
}

public void setParent(AutosysDiagram parent) {
	this.parent = parent;
}

public void redo() {
	for (Iterator iter = newTopLevelParts.iterator(); iter.hasNext();)
		parent.addChild((AutosysSubpart)iter.next());
	for (Iterator iter = newConnections.iterator(); iter.hasNext();) {
		Wire conn = (Wire) iter.next();
		AutosysSubpart source = conn.getSource();
		if (connectionPartMap.containsKey(source)) {
			conn.setSource((AutosysSubpart)connectionPartMap.get(source));
			conn.attachSource();
			conn.attachTarget();
		}
	}
	if (hGuideCommand != null)
		hGuideCommand.redo();
	if (vGuideCommand != null)
		vGuideCommand.redo();
}

public void setGuide(AutosysGuide guide, int alignment, boolean isHorizontal) {
	if (isHorizontal) {
		hGuide = guide;
		hAlignment = alignment;
	} else {
		vGuide = guide;
		vAlignment = alignment;
	}
}

public void undo() {
	if (hGuideCommand != null)
		hGuideCommand.undo();
	if (vGuideCommand != null)
		vGuideCommand.undo();
	for (Iterator iter = newTopLevelParts.iterator(); iter.hasNext();)
		parent.removeChild((AutosysSubpart)iter.next());
}

}
