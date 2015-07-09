package com.odcgroup.page.ui.figure.grid;

import java.util.List;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LayoutManager;

import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.impl.GridAdapter;
import com.odcgroup.page.ui.figure.AbstractBoxType;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridBoxType extends AbstractBoxType {
	
	protected Grid getGrid() {
		return new GridAdapter(getBoxFigure().getWidget());
	}
	
	/** The absolute box type character used to display. */
	private static String BOX_TYPE_TO_DISPLAY = "";
	
	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#createLayoutManager(com.odcgroup.page.ui.figure.FigureContext)
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		GridLayout gd = new GridLayout(getGrid().getColumnCount(), false);
		if (figureContext.isPreviewMode()) {
			gd.marginHeight= 0;
			gd.marginWidth = 1;
			gd.horizontalSpacing = 1;
			gd.verticalSpacing = 0;
		} else {
			gd.marginHeight= 3;
			gd.marginWidth = 9;
			gd.horizontalSpacing = 1;
			gd.verticalSpacing = 1;
		}
		return gd;
	}

	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#getBoxType()
	 */
	public String getBoxType() {
		return BOX_TYPE_TO_DISPLAY;
	}

	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxHeight()
	 */
	@SuppressWarnings("rawtypes")
	public int getMaxHeight() {
		// Users can define a fixed height for the HorizontalBox. If this is smaller
		// than the needed height then scrollbars are added to the Box.
		if (getHeight() > 0) {
			return getHeight();
		}
		
		int height = 0;		
		
		Grid grid = getGrid();
		int nbRows = grid.getRowCount();
		int nbCols = grid.getColumnCount();
		
		List children = getChildren();
		IWidgetFigure child = null;
		for (int rx = 0; rx < nbRows; rx++) {
			int maxHeight = 0;
			for (int cx = 0; cx < nbCols; cx++) {
				int pos = grid.getPosition(rx,cx);
				child = (IWidgetFigure)children.get(pos);
				maxHeight = Math.max(maxHeight, child.getMaxHeight());
			}
			height += maxHeight;
		}
		
		if (children.size() > 0) {
			// Allow a space above and below the tallest Widget
			height += getFigureConstants().getWidgetSpacing() * 2;
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		
		return height;
	}

	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxWidth()
	 */
	public int getMaxWidth() {
		// Users can define a fixed width for the HorizontalBox. However if the width
		// needed to display all its components is larger than this width then the
		// defined width is ignored.
		if (getPixelWidth() > 0) {
			return getPixelWidth();
		}		
		
		return -1;
	}

	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinHeight()
	 */
	@SuppressWarnings("rawtypes")
	public int getMinHeight() {
		// Users can define a fixed height for the HorizontalBox. If this is smaller
		// than the needed height then scrollbars are added to the Box.
		if (getHeight() > 0) {
			return getHeight();
		}
		
		int height = 0;		
		
		Grid grid = getGrid();
		int nbRows = grid.getRowCount();
		int nbCols = grid.getColumnCount();
		
		List children = getChildren();
		IWidgetFigure child = null;
		for (int rx = 0; rx < nbRows; rx++) {
			int maxHeight = 0;
			for (int cx = 0; cx < nbCols; cx++) {
				int pos = grid.getPosition(rx,cx);
				child = (IWidgetFigure)children.get(pos);
				maxHeight = Math.max(maxHeight, child.getMinHeight());
			}
			height += maxHeight;
		}
		
		if (children.size() > 0) {
			// Allow a space above and below the tallest Widget
			height += getFigureConstants().getWidgetSpacing() * 2;
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		return height;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinWidth()
	 */
	public int getMinWidth() {
		//DS-3098 - make the grid uses the full width of its container
		//than based on the width of its children
		//as grid cells are percentage based
		int width = 0;
		
		Grid grid = getGrid();
		
		int nbRows = grid.getRowCount();
		int nbCols = grid.getColumnCount();
		
		@SuppressWarnings("rawtypes")
		List children = getChildren();
		
		IWidgetFigure child = null;
		for (int cx = 0; cx < nbCols; cx++) {
			int maxWidth = 0;
			for (int rx = 0; rx < nbRows; rx++) {
				int pos = grid.getPosition(rx,cx);
				child = (IWidgetFigure)children.get(pos);
				maxWidth = Math.max(maxWidth, child.getMinWidth());
			}
			width += maxWidth;
		}
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (width < fc.getSimpleWidgetDefaultWidth()) {
			width = fc.getSimpleWidgetDefaultWidth();
		}
		
		// Users can define a fixed width for the HorizontalBox. However if the width
		// needed to display all its components is larger than this width then the
		// defined width is ignored.
		if (width < getPixelWidth()) {
			width = getPixelWidth();
		}
		return width;
	}

	/**
	 * @param box
	 */
	public GridBoxType(BoxFigure box) {
		super(box);
	}

}
