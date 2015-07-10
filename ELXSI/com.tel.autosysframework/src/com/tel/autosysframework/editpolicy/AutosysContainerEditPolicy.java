package com.tel.autosysframework.editpolicy;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;


import com.tel.autosysframework.commands.OrphanChildCommand;
import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysSubpart;

public class AutosysContainerEditPolicy
	extends ContainerEditPolicy
{
	

protected Command getCreateCommand(CreateRequest request) {
	return null;
}

public Command getOrphanChildrenCommand(GroupRequest request) {
	List parts = request.getEditParts();
	CompoundCommand result = 
		new CompoundCommand("Orphan Command");
	for (int i = 0; i < parts.size(); i++) {
		OrphanChildCommand orphan = new OrphanChildCommand();
		orphan.setChild((AutosysSubpart)((EditPart)parts.get(i)).getModel());
		orphan.setParent((AutosysDiagram)getHost().getModel());
		orphan.setLabel("Orphan Command");
		result.add(orphan);
	}
	return result.unwrap();
}

}
