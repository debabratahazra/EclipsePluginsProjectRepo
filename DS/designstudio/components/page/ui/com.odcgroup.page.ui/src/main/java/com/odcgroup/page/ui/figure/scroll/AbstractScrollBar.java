package com.odcgroup.page.ui.figure.scroll;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 * Base class for the Horizontal and Vertical ScrollBars.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractScrollBar extends Figure {

	/** The Figure Context. */
	private FigureContext figureContext;
	
	/** The ScrollableWidgetFigure which this ScrollBar is for. */
	private ScrollableWidgetFigure scrollableFigure;
	
	/** The part of the Figure which can be dragged. */
	private IFigure thumb;	

	/**
	 * Creates a new AbstractScrollBar.
	 * 
	 * @param figureContext The FigureContext
	 * @param scrollableFigure The ScrollableWidgetFigure that the ScrollBar is for
	 */
	public AbstractScrollBar(FigureContext figureContext, ScrollableWidgetFigure scrollableFigure) {
		this.figureContext = figureContext;
		this.scrollableFigure = scrollableFigure;
		
		FigureConstants fc = getFigureConstants();
		setOpaque(true);
		setBackgroundColor(fc.getScrollBarBackgroundColor());

		addThumb();
	}
	
	/**
	 * Gets the parent ScrollableWidgetFigure.
	 * 
	 * @return ScrollableWidgetFigure The parent ScrollableWidgetFigure
	 */
	public ScrollableWidgetFigure getScrollableFigure() {
		return scrollableFigure;
	}	
	
	/**
	 * Gets the thumb.
	 * 
	 * @return IFigure The thumb
	 */
	public IFigure getThumb() {
		return thumb;
	}
	
	/**
	 * Overridden to recalculate the Bounds of the ScrollBar's Figures.
	 * 
	 * @param rectangle
	 *            The new Bounds
	 */
	public void setBounds(Rectangle rectangle) {
		super.setBounds(rectangle);
		recalculateBounds();
	}

	/**
	 * Recalculates the Bounds of the ScrollBar's Figures.
	 */
	abstract protected void recalculateBounds();
	
	/**
	 * Gets the FigureContext.
	 * 
	 * @return FigureContext The FigureContext
	 */
	protected FigureContext getFigureContext() {
		return figureContext;
	}	
	
	 /**
	  * Gets the FigureConstants.
	  * 
	  * @return FigureConstants
	  */
	 protected FigureConstants getFigureConstants() {
		 return getFigureContext().getFigureConstants();
	 }	

	/**
	 * Creates and adds the Scrollbar's "thumb", the draggable Figure that indicates the Scrollbar's position.
	 */
	private void addThumb() {
		FigureConstants fc = getFigureConstants();
		thumb = new Panel();
		thumb.setMinimumSize(new Dimension(6, 6));
		thumb.setBackgroundColor(fc.getScrollBarThumbBackgroundColor());

		add(thumb);
	}
}