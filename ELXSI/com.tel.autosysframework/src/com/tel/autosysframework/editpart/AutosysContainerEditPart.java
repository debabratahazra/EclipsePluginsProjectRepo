package com.tel.autosysframework.editpart;

import java.util.List;

import org.eclipse.swt.accessibility.AccessibleEvent;

import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;

import com.tel.autosysframework.editpolicy.AutosysContainerEditPolicy;
import com.tel.autosysframework.model.AutosysDiagram;


/**
 * Provides support for Container EditParts.
 */
abstract public class AutosysContainerEditPart
	extends AutosysEditPart
{
protected AccessibleEditPart createAccessible() {
	return new AccessibleGraphicalEditPart(){
		public void getName(AccessibleEvent e) {
			e.result = getAutosysDiagram().toString();
		}
	};
}

/**
 * Installs the desired EditPolicies for this.
 */
protected void createEditPolicies() {
	super.createEditPolicies();
	installEditPolicy(EditPolicy.CONTAINER_ROLE, new AutosysContainerEditPolicy());
}

/**
 * Returns the model of this as a AutosysDiagram.
 *
 * @return  AutosysDiagram of this.
 */
protected AutosysDiagram getAutosysDiagram() {
	return (AutosysDiagram)getModel();
}

/**
 * Returns the children of this through the model.
 *
 * @return  Children of this as a List.
 */
protected List getModelChildren() {
	return getAutosysDiagram().getChildren();
}

}
