package com.odcgroup.page.ui.figure.table;

import static com.odcgroup.page.metamodel.WidgetTypeConstants.COLUMN_BODY_INDEX;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.COLUMN_HEADER_INDEX;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.AbstractWidgetLayout;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.util.PointUtils;

/**
 * The layout for a Column. This assumes that the Column contains two figures:
 * A figure representing the header and a figure representing the body. The header
 * figure occupies a minimum height. (defined by getMinHeight). The body occupies the
 * rest of the available height. This class works in conjunction with TableBodyLayout
 * to ensure that each Column and each ColumnBody have the same amount of space. This
 * is important since a ColumnBody corresponds to a single Cell in a Row and each Cell
 * in the same Row need to have the same space.
 * 
 * @author Gary Hayes
 */
public class ColumnLayout extends AbstractWidgetLayout {
	
	/**
	 * Creates a new ColumnLayout.
	 * 
	 * @param figureContext The context in which the Layout is being used
	 */
	public ColumnLayout(FigureContext figureContext) {
		super(figureContext);
	}

	/**
	 * Lays out the figure.
	 * 
	 * @param container
	 *            The container to layout
	 */
	public void layout(IFigure container) {
		List children = container.getChildren();
		IWidgetFigure header = (IWidgetFigure) children.get(COLUMN_HEADER_INDEX);
		IWidgetFigure body = (IWidgetFigure) children.get(COLUMN_BODY_INDEX);
		
		// Calculate the size and position of each figure
		Rectangle cb = container.getBounds();
		
		calculateSizes(header, body, cb);
		calculatePositions(header, body);
	}
	
	/**
	 * Calculate the size of the header and the body.
	 * 
	 * @param header The header figure
	 * @param body The body figure
	 * @param cb The bounds of the container
	 */
	private void calculateSizes(IWidgetFigure header, IWidgetFigure body, Rectangle cb) {
		FigureConstants fc = getFigureConstants();

		// The width of each Widget is equal to its containers width (minus the spacing).
		int allocatedWidth = cb.width - 2 * fc.getWidgetSpacing();
		int minWidth = Math.max(header.getMinWidth(), body.getMinWidth());
		
		// Header
		int hw = allocatedWidth;
		if (hw < minWidth) {
			hw = minWidth;
		}
		Rectangle nb = new Rectangle(0, 0, hw, header.getMinHeight());
		header.setBounds(nb);
		
		// Body
		int bw = allocatedWidth;
		if (bw < minWidth) {
			bw = minWidth;
		}
		Rectangle bb = new Rectangle(0, 0, bw, cb.height - header.getMinHeight() - fc.getWidgetSpacing() * 2);
		body.setBounds(bb);	

	}

	/**
	 * Calculate the positions of the header and the body.
	 * 
	 * @param header The header Figure
	 * @param body The body Figure
	 */
	private void calculatePositions(IWidgetFigure header, IWidgetFigure body) {
		FigureConstants fc = getFigureConstants();

		// We begin at widgetSpacing in order to add an equal spacing between the Column
		// and the Widgets that it contains.
		// If we do not do this we will be unable to select the Column.
		int xPos = fc.getWidgetSpacing();
		int yPos = fc.getWidgetSpacing();

		// Header
		Rectangle hb = header.getBounds();
		hb.x = xPos; 
		hb.y = yPos; 
		header.setBounds(hb);
		
		// Body
		Rectangle bb = body.getBounds();
		bb.x = xPos; 
		bb.y = yPos + hb.height;
		body.setBounds(bb);
	}

	/**
	 * Calculates the index of the IFigure.
	 * 
	 * @param container
	 *            The container of the new Figure
	 * @param location
	 *            The location of the CreateRequest
	 * @return int The index of the new Widget
	 */
	public int calculateIndex(IFigure container, Point location) {
		Point p = PointUtils.convertToLocalCoordinates(container, location);

		List children = container.getChildren();
		for (int i = 0; i < children.size(); ++i) {
			IFigure child = (IFigure) children.get(i);
			Rectangle r = child.getBounds();
			int yPos = r.y;

			if (yPos > p.y) {
				// The user has clicked before this Widget
				return i;
			}
		}

		// The Widget should be added to the end of the collection
		return children.size();
	}	
}