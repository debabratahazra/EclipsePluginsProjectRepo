package com.tel.autosysframework.editpolicy;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.tel.autosysframework.commands.ConnectionCommand;
import com.tel.autosysframework.editpart.AutosysEditPart;
import com.tel.autosysframework.figures.BaseFigure;
import com.tel.autosysframework.figures.FigureFactory;
import com.tel.autosysframework.model.AutosysSubpart;
import com.tel.autosysframework.model.Wire;


public class AutosysNodeEditPolicy
extends org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy
{

	protected Connection createDummyConnection(Request req) {
		PolylineConnection conn = FigureFactory.createNewWire(null);
		return conn;
	}

	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {	
		ConnectionCommand command = (ConnectionCommand)request.getStartCommand();
		command.setTarget(getAutosysSubpart());
		ConnectionAnchor ctor = getAutosysEditPart().getTargetConnectionAnchor(request);
		if (ctor == null)
			return null;
		command.setTargetTerminal(getAutosysEditPart().mapConnectionAnchorToTerminal(ctor));
		return command;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		ConnectionCommand command = new ConnectionCommand();
		command.setWire(new Wire());
		command.setSource(getAutosysSubpart());
		ConnectionAnchor ctor = getAutosysEditPart().getSourceConnectionAnchor(request);
		command.setSourceTerminal(getAutosysEditPart().mapConnectionAnchorToTerminal(ctor));
		request.setStartCommand(command);
		return command;
	}

	/**
	 * Feedback should be added to the scaled feedback layer.
	 * @see org.eclipse.gef.editpolicies.GraphicalEditPolicy#getFeedbackLayer()
	 */
	protected IFigure getFeedbackLayer() {
		/*
		 * Fix for Bug# 66590
		 * Feedback needs to be added to the scaled feedback layer
		 */
		return getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
	}



	protected AutosysEditPart getAutosysEditPart() {
		return (AutosysEditPart) getHost();
	}

	protected AutosysSubpart getAutosysSubpart() {
		return (AutosysSubpart) getHost().getModel();
	}

	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		/*if (getAutosysSubpart() instanceof LiveOutput || 
		getAutosysSubpart() instanceof GroundOutput)
			return null;*/

		ConnectionCommand cmd = new ConnectionCommand();
		cmd.setWire((Wire)request.getConnectionEditPart().getModel());

		ConnectionAnchor ctor = getAutosysEditPart().getTargetConnectionAnchor(request);
		cmd.setTarget(getAutosysSubpart());
		cmd.setTargetTerminal(getAutosysEditPart().mapConnectionAnchorToTerminal(ctor));
		return cmd;
	}

	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ConnectionCommand cmd = new ConnectionCommand();
		cmd.setWire((Wire)request.getConnectionEditPart().getModel());

		ConnectionAnchor ctor = getAutosysEditPart().getSourceConnectionAnchor(request);
		cmd.setSource(getAutosysSubpart());
		cmd.setSourceTerminal(getAutosysEditPart().mapConnectionAnchorToTerminal(ctor));
		return cmd;
	}

	protected BaseFigure getNodeFigure() {
		return (BaseFigure)((GraphicalEditPart)getHost()).getFigure();
	}

}
