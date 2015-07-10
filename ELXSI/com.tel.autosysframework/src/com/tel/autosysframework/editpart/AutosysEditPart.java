package com.tel.autosysframework.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.DropRequest;

import com.tel.autosysframework.editpolicy.AutosysElementEditPolicy;
import com.tel.autosysframework.editpolicy.AutosysNodeEditPolicy;
import com.tel.autosysframework.editpolicy.AutosysXYLayoutEditPolicy;
import com.tel.autosysframework.figures.BaseFigure;
import com.tel.autosysframework.internal.Refresh;
import com.tel.autosysframework.model.AutosysSubpart;
import com.tel.autosysframework.model.Wire;
import com.tel.autosysframework.run.RunAutosysProject;
import com.tel.autosysframework.views.Configure;
import com.tel.autosysframework.workspace.ProjectInformation;


/**
 * Porvides support for 
 */
abstract public class AutosysEditPart
extends org.eclipse.gef.editparts.AbstractGraphicalEditPart
implements NodeEditPart, PropertyChangeListener
{

	private AccessibleEditPart acc;

	public void activate(){
		if (isActive())	{

			return;
		}
		super.activate();
		getAutosysSubpart().addPropertyChangeListener(this);
		new Refresh().start();
	}

	protected void createEditPolicies(){
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AutosysElementEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new AutosysXYLayoutEditPolicy(new XYLayout()));

		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new AutosysNodeEditPolicy());
	}

	abstract protected AccessibleEditPart createAccessible();

	/**
	 * Makes the EditPart insensible to changes in the model
	 * by removing itself from the model's list of listeners.
	 */
	public void deactivate(){
		if (!isActive())
			return;
		super.deactivate();
		/*String projectPath = new ProjectInformation().getProjectName(4);
		File file = new File(projectPath+"\\"+getAutosysSubpart().toString()+".dat");
		if(file.exists()){
			file.delete();
			new Refresh().start();
		}*/
		getAutosysSubpart().removePropertyChangeListener(this);
	}

	protected AccessibleEditPart getAccessibleEditPart() {
		if (acc == null)
			acc = createAccessible();
		return acc;
	}

	/**
	 * Returns the model associated with this as a AutosysSubPart.
	 *
	 * @return  The model of this as a AutosysSubPart.
	 */
	protected AutosysSubpart getAutosysSubpart(){
		return (AutosysSubpart)getModel();
	}

	/**
	 * Returns a list of connections for which this is the 
	 * source.
	 *
	 * @return List of connections.
	 */
	protected List getModelSourceConnections(){
		return getAutosysSubpart().getSourceConnections();
	}

	/**
	 * Returns a list of connections for which this is the 
	 * target.
	 *
	 * @return  List of connections.
	 */
	protected List getModelTargetConnections(){
		return getAutosysSubpart().getTargetConnections();
	}

	/** 
	 * Returns the Figure of this, as a node type figure.
	 *
	 * @return  Figure as a NodeFigure.
	 */
	protected BaseFigure getNodeFigure(){
		return (BaseFigure) getFigure();
	}

	/**
	 * Returns the connection anchor for the given
	 * ConnectionEditPart's source. 
	 *
	 * @return  ConnectionAnchor.
	 */
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connEditPart) {
		Wire wire = (Wire) connEditPart.getModel();
		return getNodeFigure().getConnectionAnchor(wire.getSourceTerminal());
	}

	/**
	 * Returns the connection anchor of a source connection which
	 * is at the given point.
	 * 
	 * @return  ConnectionAnchor.
	 */
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		Point pt = new Point(((DropRequest)request).getLocation());
		return getNodeFigure().getSourceConnectionAnchorAt(pt);
	}

	/**
	 * Returns the connection anchor for the given 
	 * ConnectionEditPart's target.
	 *
	 * @return  ConnectionAnchor.
	 */
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connEditPart) {
		Wire wire = (Wire) connEditPart.getModel();
		return getNodeFigure().getConnectionAnchor(wire.getTargetTerminal());
	}

	/**
	 * Returns the connection anchor of a terget connection which
	 * is at the given point.
	 *
	 * @return  ConnectionAnchor.
	 */
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		Point pt = new Point(((DropRequest)request).getLocation());
		return getNodeFigure().getTargetConnectionAnchorAt(pt);
	}

	/**
	 * Returns the name of the given connection anchor.
	 *
	 * @return  The name of the ConnectionAnchor as a String.
	 */
	public final String mapConnectionAnchorToTerminal(ConnectionAnchor c){
		return getNodeFigure().getConnectionAnchorName(c);
	}

	/**
	 * Handles changes in properties of this. It is 
	 * activated through the PropertyChangeListener.
	 * It updates children, source and target connections,
	 * and the visuals of this based on the property
	 * changed.
	 *
	 * @param evt  Event which details the property change.
	 */
	public void propertyChange(PropertyChangeEvent evt){
		String prop = evt.getPropertyName();
		if (AutosysSubpart.CHILDREN.equals(prop)) {
			if (evt.getOldValue() instanceof Integer)
				// new child
				addChild(createChild(evt.getNewValue()), ((Integer)evt
						.getOldValue()).intValue());
			else
				// remove child
				removeChild((EditPart)getViewer().getEditPartRegistry().get(
						evt.getOldValue()));
		}
		else if (AutosysSubpart.INPUTS.equals(prop))
			refreshTargetConnections();
		else if (AutosysSubpart.OUTPUTS.equals(prop))
			refreshSourceConnections();
		else if (prop.equals(AutosysSubpart.ID_SIZE) || prop.equals(AutosysSubpart.ID_LOCATION))
			refreshVisuals();
	}

	/**
	 * Updates the visual aspect of this. 
	 */
	protected void refreshVisuals() {
		Point loc = getAutosysSubpart().getLocation();
		Dimension size= getAutosysSubpart().getSize();
		Rectangle r = new Rectangle(loc ,size);

		((GraphicalEditPart) getParent()).setLayoutConstraint(
				this,
				getFigure(),
				r);
	}

}
