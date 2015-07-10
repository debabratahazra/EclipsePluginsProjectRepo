package com.tel.autosysframework.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;

//import com.tel.autosysframwork.model.LED;
import com.tel.autosysframework.editpolicy.AutosysElementEditPolicy;
import com.tel.autosysframework.editpolicy.AutosysTreeEditPolicy;
import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.AutosysSubpart;

/**
 * EditPart for Autosys components in the Tree.
 */
public class AutosysTreeEditPart
	extends org.eclipse.gef.editparts.AbstractTreeEditPart
	implements PropertyChangeListener
{

/**
 * Constructor initializes this with the given model.
 *
 * @param model  Model for this.
 */
public AutosysTreeEditPart(Object model) {
	super (model);
}

public void activate(){
	super.activate();
	getAutosysSubpart().addPropertyChangeListener(this);
}

/**
 * Creates and installs pertinent EditPolicies
 * for this.
 */
protected void createEditPolicies() {
	EditPolicy component;
	/*if (getModel() instanceof LED)
		component = new LEDEditPolicy();
	else*/
		component = new AutosysElementEditPolicy();
	installEditPolicy(EditPolicy.COMPONENT_ROLE, component);
	installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new AutosysTreeEditPolicy());
}

public void deactivate(){
	getAutosysSubpart().removePropertyChangeListener(this);
	super.deactivate();
}

/**
 * Returns the model of this as a AutosysSubPart.
 *
 * @return Model of this.
 */
protected AutosysSubpart getAutosysSubpart() {
	return (AutosysSubpart)getModel();
}

/**
 * Returns <code>null</code> as a Tree EditPart holds
 * no children under it.
 *
 * @return <code>null</code>
 */
protected List getModelChildren() {
	return Collections.EMPTY_LIST;
}

public void propertyChange(PropertyChangeEvent change){
	if (change.getPropertyName().equals(AutosysDiagram.CHILDREN)) {
		if (change.getOldValue() instanceof Integer)
			// new child
			addChild(createChild(change.getNewValue()), ((Integer)change.getOldValue()).intValue());	
		else
			// remove child
			removeChild((EditPart)getViewer().getEditPartRegistry().get(change.getOldValue()));
	} else
		refreshVisuals();
}

/**
 * Refreshes the visual properties of the TreeItem for this part.
 */
protected void refreshVisuals(){
	if (getWidget() instanceof Tree)
		return;
	Image image = getAutosysSubpart().getIcon();
	TreeItem item = (TreeItem)getWidget();
	if (image != null)
		image.setBackground(item.getParent().getBackground());
	setWidgetImage(image);
	setWidgetText(getAutosysSubpart().toString());
}

}
