package com.odcgroup.page.ui.figure.grid;

import org.eclipse.draw2d.IFigure;

import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;

/**
 * @author pkk
 *
 */
public class GridCellLayoutUtil {
	
	/**
	 * returns the gridcellfigure if the given figure is contained in it
	 * 
	 * @param figure
	 * @return figure
	 */
	public static GridCellFigure fetchParentGridCell(IFigure figure) {
		IFigure parent = figure.getParent();
		GridCellFigure gridCell = null;
		if (parent != null) {
			if(parent instanceof GridCellFigure) {
				gridCell = (GridCellFigure) parent;
			} else {
				gridCell = fetchParentGridCell(parent);
			}
		} 
		return gridCell;
	}
	

	/**
	 * @param gridCell
	 * @param parent
	 * @param gridCellWidth
	 * @return width
	 */
	public static int getPossibleWidth(FigureConstants figureConstants, IFigure gridCell, IFigure parent, int gridCellWidth, int textwidth) {
		if (parent.equals(gridCell)) {
			int children = gridCell.getChildren().size();
			int spacing = figureConstants.getWidgetSpacing() * ( children + 1);
			int ret = gridCellWidth - spacing;
			return (ret < textwidth) ? ret : textwidth;
		} else  {
			int parentWidth = getPossibleWidth(figureConstants, gridCell, parent.getParent(), gridCellWidth, textwidth);
			BoxFigure parentBox = (BoxFigure) parent;
			int ret = 0;
			if(parentBox.getBoxType().getBoxType().equals("V")) {
				ret = parentWidth;
			} else {
				int parentchildren = parent.getChildren().size();
				int spacing = figureConstants.getWidgetSpacing() * ( parentchildren + 1);
				ret = parentWidth - spacing;
			}
			return (ret < textwidth) ? ret : textwidth;
		}
	}

}
