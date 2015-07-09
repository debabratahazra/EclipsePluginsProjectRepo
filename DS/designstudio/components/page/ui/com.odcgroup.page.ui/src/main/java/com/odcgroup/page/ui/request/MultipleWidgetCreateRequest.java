package com.odcgroup.page.ui.request;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.DropRequest;

import com.odcgroup.page.model.Widget;

/**
 * Creates new Widgets. In reality this Request receives the already-created Widgets
 * in the constructor.
 * 
 * @author Gary Hayes
 */
public class MultipleWidgetCreateRequest extends Request implements DropRequest {
	
	/** The list of widget template */
	private List<Widget> widgets;
	
	/** The location of the Request. */
	private Point location;
	
	/**
	 * Creates a new MultipleWidgetCreateRequest.
	 */
	public MultipleWidgetCreateRequest() {
		setType(PageUIRequestConstants.REQ_MULTIPLE_CREATE);
	}

	/**
	 * Gets the new objects.
	 * 
	 * @return a list of new widgets
	 */
	public List getNewObjects() {
		return widgets;
	}
	
	/**
	 * Sets the new objects.
	 * 
	 * @param widgets The List of Widgets
	 */
	public void setNewObjects(List<Widget> widgets) {
		this.widgets = widgets;
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
	 * @param location the location
	 */
	public void setLocation(Point location) {
		this.location = location;
	}	
}
