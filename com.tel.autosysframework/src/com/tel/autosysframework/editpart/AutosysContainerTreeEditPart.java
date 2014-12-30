package com.tel.autosysframework.editpart;

import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

import com.tel.autosysframework.editpolicy.AutosysContainerEditPolicy;
import com.tel.autosysframework.editpolicy.AutosysTreeContainerEditPolicy;
import com.tel.autosysframework.model.AutosysDiagram;

/**
 * Tree EditPart for the Container.
 */
public class AutosysContainerTreeEditPart
	extends AutosysTreeEditPart
{

/**
 * Constructor, which initializes this using the
 * model given as input.
 */
public AutosysContainerTreeEditPart(Object model) {
	super(model);
}

/**
 * Creates and installs pertinent EditPolicies.
 */
protected void createEditPolicies() {
	super.createEditPolicies();
	//installEditPolicy(EditPolicy.CONTAINER_ROLE, new AutosysContainerEditPolicy());
	installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE, new AutosysTreeContainerEditPolicy());
	//If this editpart is the contents of the viewer, then it is not deletable!
	if (getParent() instanceof RootEditPart)
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
}

/**
 * Returns the model of this as a AutosysDiagram.
 *
 * @return  Model of this.
 */
protected AutosysDiagram getAutosysDiagram() {
	return (AutosysDiagram)getModel();
}

/**
 * Returns the children of this from the model,
 * as this is capable enough of holding EditParts.
 *
 * @return  List of children.
 */
protected List getModelChildren() {
	return getAutosysDiagram().getChildren();
}

}
