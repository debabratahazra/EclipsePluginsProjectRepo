package com.odcgroup.page.ui.edit.table;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.LocationRequest;

import com.odcgroup.page.ui.edit.WidgetCellEditorLocator;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.table.ColumnBody;
import com.odcgroup.page.ui.figure.table.Item;

/**
 * The CellEditorLocator for Column Bodies. This places the TextCellEditor above the
 * selected child Widget.
 * 
 * @author Gary Hayes
 */
public class ColumnBodyCellEditorLocator extends WidgetCellEditorLocator {

	/**
	 * Creates a new ColumnBodyCellEditorLocator.
	 * 
	 * @param figure The IWidgetFigure
	 * @param request The request
	 */
	public ColumnBodyCellEditorLocator(IWidgetFigure figure, LocationRequest request) {
		super(figure, request);
	}

	/**
	 * Gets the selected bounds. By default this returns the Bounds of the main Widget.
	 * Subclasses can override this method to return other Bounds. The TextEditor will
	 * be located starting from the x, y position of these bounds unless it would fall
	 * outside the bounds of the EditorPart.
	 * 
	 * @return Rectangle
	 */
	protected Rectangle getSelectedBounds() {
		ColumnBody cb = (ColumnBody) getFigure();

		LocationRequest sr = getRequest();
		Point l = sr.getLocation();
		
		// Calculate the relative position of the mouse-click
		Rectangle r = new Rectangle(cb.getBounds());
		cb.translateToAbsolute(r);
		Point rl = new Point(l.x - r.x, l.y - r.y);

		int pageSize = cb.getParentTable().getPageSize();
		float y;
		if (cb.isGrouped()) {
			y = ((float) cb.getBounds().height - ColumnBody.IMAGE_HEIGHT) / (float) pageSize;
		} else {
			y = ((float) cb.getBounds().height) / (float) pageSize;
		}
		
		for (int i = 0; i < cb.getChildren().size(); ++i) {
			IWidgetFigure awf = (IWidgetFigure) cb.getChildren().get(i);
			if (! (awf instanceof Item)) {
				continue;
			}
			
			Rectangle cr = awf.getBounds();

			for (int j = 0; j < pageSize; ++j) {
				Rectangle dr = new Rectangle(cr);
				dr.y = (int) (dr.y + y * j);
				
				if (dr.contains(rl)) {
					
					// Translate to absolute coordinates
					IFigure f = getFigure();
					Rectangle b = f.getBounds();
					Rectangle bb = new Rectangle(b);
					f.translateToAbsolute(bb);
					
					return new Rectangle(dr.x + bb.x, dr.y + bb.y, dr.width, dr.height);
				}
			}
			
		}

		// Not found
		return null;
	}
}