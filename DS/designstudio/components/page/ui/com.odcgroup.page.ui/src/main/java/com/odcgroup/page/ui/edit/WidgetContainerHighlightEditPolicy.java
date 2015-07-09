/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.odcgroup.page.ui.edit;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.swt.graphics.Color;

import com.odcgroup.page.ui.PageUIConstants;

/**
 * EditPolicy which highlights the parent Widget into which a child Widget is going to be dropped.
 * 
 * @author Gary Hayes
 */
public class WidgetContainerHighlightEditPolicy extends GraphicalEditPolicy {

	/** The normal background color of the Widget (before it has been highlighted. */
	private Color normalBackgroundColor;

	/**
	 * Filters the request so that the highlight color is only activated for active requests (those which can have an
	 * effect.
	 * 
	 * @param request
	 *            The Request
	 */
	public void showTargetFeedback(Request request) {
		if (request.getType().equals(RequestConstants.REQ_MOVE) || request.getType().equals(RequestConstants.REQ_ADD)
				|| request.getType().equals(RequestConstants.REQ_CLONE)
				|| request.getType().equals(RequestConstants.REQ_CREATE))
			showHighlight();
	}
	
	/**
	 * Erases the highlighting.
	 * 
	 * @param request
	 *            The Request
	 */
	public void eraseTargetFeedback(Request request) {
		if (normalBackgroundColor != null) {
			setContainerBackground(normalBackgroundColor);
			normalBackgroundColor = null;
		}
	}
	
	/**
	 * Gets the target EditPart. This returns null unless the Request is
	 * a REQ_SELECTION_HOVER request.
	 * 
	 * @param request The Request
	 * @return EditPart The target EditPart
	 */
	public EditPart getTargetEditPart(Request request) {
		return request.getType().equals(RequestConstants.REQ_SELECTION_HOVER) ? getHost() : null;
	}	
	
	/**
	 * Shows the highlight. This is reversed by eraseTargetFeedback.
	 */
	private void showHighlight() {
		if (normalBackgroundColor == null) {
			normalBackgroundColor = getContainerBackground();
			setContainerBackground(PageUIConstants.getHighLightColor());
		}
	}	

	/**
	 * Gets the Color of the Container (host) figure.
	 * 
	 * @return Color The color of the Container (host) figure
	 */
	private Color getContainerBackground() {
		return getContainerFigure().getBackgroundColor();
	}
	
	/**
	 * Sets the background Color of the Container (host) figure.
	 * 
	 * @param color The color of the Container (host) figure to set
	 */
	private void setContainerBackground(Color color) {
		getContainerFigure().setBackgroundColor(color);
	}	

	/**
	 * Gets the Container (host) figure.
	 * 
	 * @return IFigure The Container (host) figure
	 */
	private IFigure getContainerFigure() {
		return ((GraphicalEditPart) getHost()).getFigure();
	}
}