package com.odcgroup.page.ui.figure.scroll;

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.tools.TargetingTool;

/**
 * Base class for the X and Y ScrollBarDragTrackers.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractScrollBarDragTracker extends TargetingTool implements DragTracker {

	/** The name of this Command. */
	private static final String COMMAND_NAME = "scroll";

	/** The Figure being scrolled. */
	private ScrollableWidgetFigure scrollableFigure;

	/**
	 * Creates a new AbstractScrollBarDragTracker.
	 * 
	 * @param scrollableFigure
	 *            The Figure being scrolled
	 */
	public AbstractScrollBarDragTracker(ScrollableWidgetFigure scrollableFigure) {
		this.scrollableFigure = scrollableFigure;
	}

	/**
	 * Gets the name of this Command.
	 * 
	 * @return String The name of this Commans
	 */
	public String getCommandName() {
		return COMMAND_NAME;
	}
	
	/**
	 * Gets the ScrollableWidgetFigure which this DragTracker is for.
	 * 
	 * @return ScrollableWidgetFigure The ScrollableWidgetFigure which this DragTracker is for
	 */
	protected ScrollableWidgetFigure getScrollableWidgetFigure() {
		return scrollableFigure;
	}
	
	/**
	 * Deselects the previously selected EditParts.
	 * 
	 * @param button
	 *            The button which has been clicked
	 * @return boolean True if the click has been treated
	 */
	protected boolean handleButtonDown(int button) {
		if (button == 1) {
			// Deselect all the other elements
			getCurrentViewer().deselectAll();
		}
		return true;
	}	
}