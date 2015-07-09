package com.odcgroup.page.ui.figure;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.util.SWTUtils;

/**
 * Base class for Header Figures.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractHeaderFigure extends AbstractTranslatedWidgetFigure {

	/** This value means no body is selected. */
	private static final int NO_SELECTION = -1;

	/** The height of a header (column, tab). */
	private static final int HEADER_HEIGHT = 16;

	/** The width between two headers. */
	private static final int SPACING_BETWEEN_HEADER = 0;

	/** index of current selected item. */
	private int selectedItem = NO_SELECTION;
	
	/**
	 * Creates a new AbstractHeaderFigure.
	 * 
	 * @param parentFigure
	 *            the parent figure
	 */
	public AbstractHeaderFigure(IWidgetFigure parentFigure) {
		super(parentFigure.getWidget(), parentFigure.getFigureContext());
		
		init();
	}
	
	/**
	 * 
	 * @param newIndex
	 */
	private void changeSelection(int newIndex) {
		if (newIndex == selectedItem) {
			// no selection change, nothing to do
			return;
		}

		List<Widget> items = getItems();
		if (items.size() == 0) {
			return;
		}

		// perform the selection
		if (newIndex == NO_SELECTION) {
		    return;
		}

		// select new child (column)
		Widget child = items.get(newIndex);
		child.setVisible(true);
		child.setSelected(true);
		getWidget().setIndexOfSelectedChild(getWidgetIndex(child));
		
		// store index of selected child
		selectedItem = newIndex;
	}

	/**
	 * Called when the mouse is clicked upon the HeaderFigure.
	 * 
	 * @param mouseLocation
	 *            The location of the mouse-click
	 */
	public void changeSelection(Point mouseLocation) {
		changeSelection(getSelectedHeader(mouseLocation));
	}

	/**
	 * Gets the index of the select Item.
	 * 
	 * @param items
	 * @return int The index of the selected Item
	 */
	protected int getIndexOfSelectedItem(List<Widget> items) {
		int position = getWidget().getIndexOfSelectedChild();
		return position;
	}

	/**
	 * Gets the maximum width of the figure. By default this is equal to the minimum width. Returning -1 indicates that
	 * the Figure does not have a maximum width and can be resized.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		return -1;
	}
	
	/**
	 * Gets the minimum height of the figure. The layouts always set the height to be greater than or equal to the
	 * minimum height.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		return HEADER_HEIGHT;
	}
	
	/**
	 * Gets the minimum width of the figure. The layouts always set the width to be greater than or equal to the minimum
	 * width.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {

		List<Widget> items = getItems();
		int index = 0;
		int width = 0;
		int nbItems = items.size();
		for (Widget item : items) {
			width += getItemWidth(item, index, nbItems, getCaptionFont());
			index++;
		}			
		return width;
	}
	
	/**
	 * @return i
	 */
	public int getSelectedItemX() {
		List<Widget> items = getItems();
		int nbItems =items.size();
		int index = 0;
		int width = 0;
		int selIndex = getIndexOfSelectedItem(items);
		for (Widget item : getItems()) {
			width += getItemWidth(item, index, nbItems, getCaptionFont());		
			if (index == selIndex) {
				break;
			}				
			index++;
		}			
		return width;
	}
	
	/**
	 * @return width
	 */
	public int getSelectedItemWidth() {
		List<Widget> items = getItems();
		int nbItems = items.size();
		int index = 0;
		int selIndex = getIndexOfSelectedItem(items);
		for (Widget item : items) {	
			if (index == selIndex) {
				return getItemWidth(item, index, nbItems, getCaptionFont());
			}				
			index++;
		}		
		return -1;		
	}
	
	/**
	 * @return
	 */
	public int getSelectedItemIndex() {
		return getIndexOfSelectedItem(getItems());
	}
	
	/**
	 * @return the font to draw text in the header
	 */
	protected abstract Font getCaptionFont();
	
	/**
	 * Gets the selected header.
	 * 
	 * @param mouseLocation
	 *            The mouse location
	 * @return int The index of the selected header
	 */
	public int getSelectedHeader(Point mouseLocation) {
		// Translate the position to that of the HeaderFigure
		Rectangle figBounds = this.getBounds();		
		Point pt = new Point(mouseLocation.x - figBounds.x, mouseLocation.y - figBounds.y);

		Font itemFont = getCaptionFont();

		// This is the Rectangle of a single Header
		Rectangle itemRect = new Rectangle();
		itemRect.x = 0;
		itemRect.y = 0;
		itemRect.height = HEADER_HEIGHT;

		List<Widget> items = getItems();
		int nbItems = items.size();
		int index = 0;
		for (Widget item : items) {
			itemRect.width = getItemWidth(item, index, nbItems, itemFont);
			if (itemRect.contains(pt)) {
				// We've found the selected item header !!
				itemRect.x = 0;
				return index;
			}
			// try next header
			itemRect.x += itemRect.width + SPACING_BETWEEN_HEADER;
			index++;
		}

		// no header was selected
		return -1;
	}
	
	/**
	 * Gets the index of the child widget.
	 * 
	 * @param childWidget
	 * @return int The index of the Child Widget
	 */
	private int getWidgetIndex(Widget childWidget) {
		return getWidget().getContents().indexOf(childWidget);
	}	
	
	/**
	 * Initializes the HeaderFigure.
	 */
	private void init() {
		List<Widget> items = getItems();
		for (Widget w : items) {
			w.setVisible(false);
		}
		changeSelection(0);
	}
	
	/**
	 * Gets the width of an Item. This depends upon whether the Item is compacted or not.
	 * 
	 * @param item
	 *            The item to get the width for
	 * @param index
	 *            The index of the Item
	 * @param nbItems
	 *            The number of items
	 * @param itemFont
	 *            The Font of the item
	 * @return int The Width of the Item
	 */
	abstract protected int getItemWidth(Widget item, int index, int nbItems, Font itemFont);
	
	/**
	 * Gets the items which need to have a Header.
	 * 
	 * @return List The List of items which should have a Header
	 */
	abstract protected List<Widget> getItems();

	/**
	 * Gets the text associated with a specific item. This is used in the Header.
	 * 
	 * @param widget
	 *            The Widget to get the text for
	 * @param index
	 *            The index
	 * @return String The text
	 */
	abstract protected String getItemText(Widget widget, int index);	
	
	/**
	 * Paints the Code.
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		Font itemFont = getCaptionFont();
		Color foregroundColor = fc.getCaptionBackgroundColor();
		Color backgroundColor = ColorConstants.white;

		Rectangle header = new Rectangle();
		header.x = 0;
		header.y = 0;
		header.height = HEADER_HEIGHT;

		List<Widget> items = getItems();
		int nbItems = items.size();
		selectedItem = getIndexOfSelectedItem(items);

		int index = 0;
		for (Widget t : items) {
			header.width = getItemWidth(t, index, nbItems, itemFont);

			if (index == selectedItem) {
				graphics.setBackgroundColor(foregroundColor);
			} else {
				graphics.setBackgroundColor(backgroundColor);
			}

			graphics.fillRoundRectangle(header, 6, 6);
			graphics.setFont(itemFont);

			String text = " "+getItemText(t, index)+" ";
			org.eclipse.swt.graphics.Rectangle sr = SWTUtils.calculateSize(getFont(), text);
			graphics.drawText(text, header.x + 4, 1);
			PaintUtils.paintIcons(t, graphics,sr.width+header.x+2);
			graphics.drawRoundRectangle(header, 6, 6);
			header.x += header.width + SPACING_BETWEEN_HEADER;
			index++;		
		}
		
	}
}
