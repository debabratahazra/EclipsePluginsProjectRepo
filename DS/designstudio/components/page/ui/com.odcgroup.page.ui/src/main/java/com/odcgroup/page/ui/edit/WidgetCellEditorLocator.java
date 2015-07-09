package com.odcgroup.page.ui.edit;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * The Cell Locator for Widgets.
 * 
 * @author atr
 */
public class WidgetCellEditorLocator implements CellEditorLocator {
	
	/** The Widget figure. */
	private IWidgetFigure figure;
	
	/** The request. */
	private LocationRequest request;	
		
	/**
	 * Creates a new WidgetCellEditorLocator.
	 * 
	 * @param figure The Widget Figure
	 * @param request The request
	 */
	public WidgetCellEditorLocator(IWidgetFigure figure, LocationRequest request) {
		Assert.isNotNull(figure);
		Assert.isNotNull(request);
		
		this.figure = figure;	
		this.request = request;
	}
	
	/**
	 * Relocates a CellEditor.
	 * 
	 * @param cellEditor the CellEditor
	 */
	public void relocate(CellEditor cellEditor) {
		Rectangle b = getSelectedBounds();
		
		// Create a new Rectangle so as not to modify the original one
		Rectangle r = new Rectangle(b.x+1, b.y+1, b.width-2, b.height-2);//EDITOR_WIDTH, EDITOR_HEIGHT);
		
//		// Don't allow the CellEditor to overflow the bounds of the root figure
//		IFigure root = getFigure().getRootFigure();
//		Rectangle rb = root.getBounds();
//		if (r.x + EDITOR_WIDTH > rb.x + rb.width) {
//			r.x = rb.x + rb.width - EDITOR_WIDTH - 1;
//		}
//		if (r.y + EDITOR_HEIGHT > rb.y + rb.height) {
//			r.y = rb.y + rb.height - EDITOR_HEIGHT - 1;
//		}
		
		Text t = (Text) cellEditor.getControl();
		t.setBounds(r.x, r.y, r.width, r.height);
	}
	
	/**
	 * Gets the selected bounds. By default this returns the Bounds of the main Widget
	 * translated to absolute coordinates.
	 * Subclasses can override this method to return other Bounds. The TextEditor will
	 * be located starting from the x, y position of these bounds unless it would fall
	 * outside the bounds of the EditorPart.
	 * 
	 * @return Rectangle
	 */
	protected Rectangle getSelectedBounds() {
		Rectangle r = new Rectangle(getFigure().getBounds());
		getFigure().translateToAbsolute(r);
		return r;
	}
	
	/**
	 * Gets the figure.
	 * 
	 * @return IWidgetFigure
	 */
	protected IWidgetFigure getFigure() {
		return figure;
	}
	
	/**
	 * Gets the request.
	 * 
	 * @return Request
	 */
	protected LocationRequest getRequest() {
		return request;
	}	
}