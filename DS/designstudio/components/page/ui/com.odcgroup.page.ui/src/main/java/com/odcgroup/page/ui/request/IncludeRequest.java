package com.odcgroup.page.ui.request;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.DropRequest;

import com.odcgroup.page.model.Model;

/**
 * A Request to include an existing Model in another one.
 * 
 * @author Gary Hayes
 * @author atr, replaced Widget by Model
 */
public class IncludeRequest extends Request implements DropRequest {
	
	/** The location of the Request. */
	private Point location;
	
	/** The Model to be included. */
	private Model model;
	
	/**
	 * Creates a new IncludeRequest.
	 */
	public IncludeRequest() {
		setType(PageUIRequestConstants.REQ_INCLUDE);
	}
	
	/**
	 * Returns the location of the object to be created.
	 * 
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Sets the location where the new object will be placed.
	 * 
	 * @param location
	 *            the location
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	/**
	 * Gets the model to be included.
	 * 
	 * @return Model The Model to be included
	 */
	public Model getModel() {
		return model;
	}
	
	/**
	 * Sets the Model to be included.
	 * 
	 * @param model The Model to be included
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	
}