package com.odcgroup.page.ui.figure;

import java.util.Iterator;

import org.eclipse.draw2d.LayoutManager;

/**
 * The BoxType for VerticalBoxes.
 * 
 * @author Gary Hayes
 */
public class VerticalBoxType extends AbstractBoxType {
	
	/** The absolute box type character used to display. */
	private static String BOX_TYPE_TO_DISPLAY = "V";
	
	/**
	 * Creates a new VerticalBoxType.
	 * 
	 * @param box
	 *            The BoxFigure that this is the type for
	 */
	public VerticalBoxType(BoxFigure box) {
		super(box);
	}

	/**
	 * Creates a LayoutManager for this type of box.
	 * 
	 * @param figureContext
	 *            The FigureContext
	 * @return LayoutManager The newly created LayoutManager
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		return new VerticalLayout(figureContext);
	}

	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		// Users can define a fixed width for the VerticalBox. If this is
		// smaller
		// than the needed width then scrollbars are added to the Box.
		if (getPixelWidth() > 0) {
			return getPixelWidth();
		}

		int width = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			width = Math.max(width, child.getMinWidth());

		}

		if (getChildren().size() > 0) {
			// Allow a spacing before and after the widest Widget
			width += getFigureConstants().getWidgetSpacing() * 2;
		}

		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (width < fc.getSimpleWidgetDefaultWidth()) {
			width = fc.getSimpleWidgetDefaultWidth();
		}
		return width;
	}

	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		// Users can define a fixed height for the VerticalBox. If this is
		// smaller
		// than the needed height then scrollbars are added to the Box.
		if (getHeight() > 0) {
			return getHeight();
		}

		int height = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			height += child.getMinHeight();
		}

		if (getChildren().size() > 0) {
			// Allow a space above, between and below each Widget
			height += getFigureConstants().getWidgetSpacing()
					* (getChildren().size() + 1);
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
	 * Gets the maximum width of the figure. HorizontalBoxes do not have maximum
	 * widths. Instead they can expand to fill the entire width of the parent
	 * container.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		// Users can define a fixed width for the VerticalBox. If this is
		// smaller
		// than the needed width then scrollbars are added to the Box.
		if (getPixelWidth() > 0) {
			return getPixelWidth();
		}

		return -1;
	}

	/**
	 * Gets the maximum height of the figure.
	 * 
	 * @return int The maximum height of the figure
	 */
	public int getMaxHeight() {
		// Users can define a fixed height for the VerticalBox. If this is
		// smaller
		// than the needed height then scrollbars are added to the Box.
		if (getHeight() > 0) {
			return getHeight();
		}

		int height = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			height += child.getMaxHeight();
		}

		if (getChildren().size() > 0) {
			// Allow a space above, between and below each Widget
			height += getFigureConstants().getWidgetSpacing()
					* (getChildren().size() + 1);
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
	 * Gets the String used to display a difference between the boxes.
	 * 
	 * @return String
	 */
	public String getBoxType() {
		return BOX_TYPE_TO_DISPLAY;
	}
}