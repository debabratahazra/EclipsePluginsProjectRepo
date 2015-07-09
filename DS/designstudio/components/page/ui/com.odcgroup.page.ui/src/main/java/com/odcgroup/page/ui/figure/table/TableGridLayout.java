package com.odcgroup.page.ui.figure.table;

import java.util.List;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.CalculatingLayout;
import com.odcgroup.page.ui.util.PointUtils;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class TableGridLayout extends GridLayout implements CalculatingLayout {

	/** */
	public TableGridLayout() {
	}

	/**
	 * @param numColumns
	 * @param makeColumnsEqualWidth
	 */
	public TableGridLayout(int numColumns, boolean makeColumnsEqualWidth) {
		super(numColumns, makeColumnsEqualWidth);
	}

	/*
	 * @see
	 * com.odcgroup.page.ui.figure.CalculatingLayout#calculateIndex(org.eclipse
	 * .draw2d.IFigure, org.eclipse.draw2d.geometry.Point)
	 */
	public int calculateIndex(IFigure container, Point location) {
		Point p = PointUtils.convertToLocalCoordinates(container, location);

		List children = container.getChildren();
		for (int i = 0; i < children.size(); ++i) {
			IFigure child = (IFigure) children.get(i);
			Rectangle r = child.getBounds();
			int xPos = r.x;

			if (xPos > p.x) {
				// The user has clicked before this Widget
				return i;
			}
		}

		// The Widget should be added to the end of the collection
		return children.size();
	}

}
