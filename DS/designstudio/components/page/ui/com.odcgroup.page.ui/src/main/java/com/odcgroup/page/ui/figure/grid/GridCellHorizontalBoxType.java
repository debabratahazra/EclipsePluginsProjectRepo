package com.odcgroup.page.ui.figure.grid;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.LayoutManager;

import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.impl.GridCellAdapter;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.HorizontalBoxType;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridCellHorizontalBoxType extends HorizontalBoxType {

	/**
	 * Creates a LayoutManager for this type of box.
	 * 
	 * @param figureContext The FigureContext
	 * @return LayoutManager The newly created LayoutManager
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		final BoxFigure boxFig = getBoxFigure();
		return new GridCellHorizontalLayout(figureContext){
			protected BoxFigure getBoxFigure() {
				return boxFig;
			}

		};
	}
	
	/**
	 * @param box
	 */
	public GridCellHorizontalBoxType(BoxFigure box) {
		super(box);
	}
	


	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	@SuppressWarnings("rawtypes")
	public int getMinWidth() {
		List children = getChildren();
		if (children.size() == 0) {
			return 50;
		}
		int width = 0;
		int wspace = getFigureConstants().getWidgetSpacing() * 2;
		for (Iterator it = children.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			width += child.getMinWidth()+wspace;
		}
		
		if (children.size() > 0) {	
			// Allow a spacing before the first Widget, after the last Widget and between each Widget
			width += wspace * (children.size() + 1);
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (width < fc.getSimpleWidgetDefaultWidth()) {
			width = fc.getSimpleWidgetDefaultWidth();
		}
		
		String wid = getBoxFigure().getWidth();
		int swidth = 0;
		try {
			swidth = Integer.parseInt(wid);
		} catch (NumberFormatException e) {
			swidth = 100;
		}
		if (width < swidth) {
			width = swidth;
		}
		// DS-5629
		if (children.size() == 0 && getPercentageWidth() <= 100) {
			int pwidth = getPixelWidth();
			if (width < pwidth) {
				return pwidth;
			}
		}
		return width;
	}      

	/**
	 * 
	 * @return
	 */
	private int getPercentageWidth() {
		int width = 0;
		GridCellAdapter adapter = new GridCellAdapter(getBoxFigure()
				.getWidget());
		Grid grid = adapter.getGrid();
		int count = grid.getColumnCount();
		for (int i = 0; i < count; i++) {
			width += grid.getColumnWidth(i);
		}
		return width;
	}

}
