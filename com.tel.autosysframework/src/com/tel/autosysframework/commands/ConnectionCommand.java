package com.tel.autosysframework.commands;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.gef.commands.Command;

import com.tel.autosysframework.model.AutosysSubpart;
import com.tel.autosysframework.model.Wire;

public class ConnectionCommand
	extends Command
{

protected AutosysSubpart oldSource;
protected String oldSourceTerminal;
protected AutosysSubpart oldTarget;
protected String oldTargetTerminal;
protected AutosysSubpart source;
protected String sourceTerminal;
protected AutosysSubpart target; 
protected String targetTerminal; 
protected Wire wire;

public ConnectionCommand() {
	super("Connection Command");
}

public boolean canExecute(){
	if (target != null) {
		Vector conns = target.getConnections();
		Iterator i = conns.iterator();
		while (i.hasNext()) {
			Wire conn = (Wire)i.next();
			if (targetTerminal != null && conn.getTargetTerminal() != null)
				if (conn.getTargetTerminal().equals(targetTerminal) 
						&& conn.getTarget().equals(target))
					return false;
		}
	}
	return true;
}

public void execute() {
	if (source != null){
		wire.detachSource();
		wire.setSource(source);
		wire.setSourceTerminal(sourceTerminal);
		wire.attachSource();
	}
	if (target != null) {
		wire.detachTarget();
		wire.setTarget(target);
		wire.setTargetTerminal(targetTerminal);
		wire.attachTarget();
	}
	if (source == null && target == null){
		wire.detachSource();
		wire.detachTarget();
		wire.setTarget(null);
		wire.setSource(null);
	}
}

public String getLabel() {
	return "Connection Change";
}

public AutosysSubpart getSource() {
	return source;
}

public java.lang.String getSourceTerminal() {
	return sourceTerminal;
}

public AutosysSubpart getTarget() {
	return target;
}

public String getTargetTerminal() {
	return targetTerminal;
}

public Wire getWire() {
	return wire;
}

public void redo() { 
	execute(); 
}

public void setSource(AutosysSubpart newSource) {
	source = newSource;
}

public void setSourceTerminal(String newSourceTerminal) {
	sourceTerminal = newSourceTerminal;
}

public void setTarget(AutosysSubpart newTarget) {
	target = newTarget;
}

public void setTargetTerminal(String newTargetTerminal) {
	targetTerminal = newTargetTerminal;
}

public void setWire(Wire w) {
	wire = w;
	oldSource = w.getSource();
	oldTarget = w.getTarget();
	oldSourceTerminal = w.getSourceTerminal();
	oldTargetTerminal = w.getTargetTerminal();	
}

public void undo() {
	source = wire.getSource();
	target = wire.getTarget();
	sourceTerminal = wire.getSourceTerminal();
	targetTerminal = wire.getTargetTerminal();

	wire.detachSource();
	wire.detachTarget();

	wire.setSource(oldSource);
	wire.setTarget(oldTarget);
	wire.setSourceTerminal(oldSourceTerminal);
	wire.setTargetTerminal(oldTargetTerminal);

	wire.attachSource();
	wire.attachTarget();
}

}
