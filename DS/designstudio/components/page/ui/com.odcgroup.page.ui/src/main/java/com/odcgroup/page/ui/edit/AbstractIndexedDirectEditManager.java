package com.odcgroup.page.ui.edit;

import java.util.List;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;

/**
 * This is the DirectEditManager when the preview values are stored as comma-separated
 * values.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractIndexedDirectEditManager extends DirectEditManager {
	
	/** The request. */
	private LocationRequest request;	
	
	/**
	 * Constructs a new AbstractIndexedDirectEditManager for the given source edit part. 
	 * 
	 * @param source the source edit part
	 * @param locator The CellEditorLocator
	 * @param request The LocationRequest
	 */
	public AbstractIndexedDirectEditManager(GraphicalEditPart source, CellEditorLocator locator, LocationRequest request) {		
		super(source, TextCellEditor.class, locator);
		
		this.request = request;
	}
	
	/**
	 * Initialises the CellEditor.
	 */
	protected void initCellEditor() {
		int index = getSelectedIndex();
		
		Widget w = (Widget) getEditPart().getModel();
		
		List<String> pvs = WidgetUtils.getPreviewValues(w);
		String s = "";
		if (pvs.size() > index) {
			s = pvs.get(index);
		}
		
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
	
	/**
	 * Gets the selected Index.
	 * 
	 * @return int The selected Index
	 */
	abstract protected int getSelectedIndex();	
}
