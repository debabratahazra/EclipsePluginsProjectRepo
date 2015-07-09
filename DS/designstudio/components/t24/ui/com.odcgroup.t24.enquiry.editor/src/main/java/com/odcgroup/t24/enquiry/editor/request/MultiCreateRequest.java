package com.odcgroup.t24.enquiry.editor.request;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.DropRequest;

/**
 * This class represents a request that holds multiple objects that is passed 
 * to createCommand in the edit ploicy.
 *
 * @author mumesh
 *
 */
public class MultiCreateRequest extends Request implements DropRequest {

	private List<Object> children;
	public static final String MULTIPLE_CREATE = "multiCreate" ;
	
	/**
	 * Creates a new MultipleWidgetCreateRequest.
	 */
	public MultiCreateRequest() {
		setType(MULTIPLE_CREATE);
	}

	
	@Override
	public Point getLocation() {
		return null;
	}

	/**
	 * Gets the new objects.
	 * 
	 * @return a list of new widgets
	 */
	public List<Object> getNewObjects() {
		return children;
	}
	
	/**
	 * Sets the new objects.
	 * 
	 * @param widgets The List of Widgets
	 */
	public void setNewObjects(List<Object> children) {
		this.children = children;
	}

	
}
