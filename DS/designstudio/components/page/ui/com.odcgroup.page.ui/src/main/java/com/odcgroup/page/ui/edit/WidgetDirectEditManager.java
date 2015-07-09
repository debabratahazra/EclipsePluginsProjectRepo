package com.odcgroup.page.ui.edit;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * Adds the LocationRequest to the constructor.
 * 
 * @author Gary Hayes
 */
public class WidgetDirectEditManager extends DirectEditManager {
	
	/** The request. */
	private LocationRequest request;
	
	/**
	 * Constructs a new WidgetDirectEditManager for the given source edit part. 
	 * 
	 * @param source the source edit part
	 * @param request The LocationRequest
	 */
	public WidgetDirectEditManager(GraphicalEditPart source, LocationRequest request) {		
		super(source, TextCellEditor.class, new WidgetCellEditorLocator(
				(IWidgetFigure) source.getFigure(), request));
		
		this.request = request;
	}
	
	/**
	 * Initialises the CellEditor.
	 */
	protected void initCellEditor() {
		Widget w = (Widget) getEditPart().getModel();
		String s = w.getPropertyValue(PropertyTypeConstants.PREVIEW_VALUE);
		getCellEditor().setValue(s);
	}	
	
	/**
	 * Gets the LocationRequest.
	 * 
	 * @return LocationRequest
	 */
	protected LocationRequest getRequest() {
		return request;
	}
}