package com.odcgroup.page.ui.figure.table;

import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.PaintIterator;
import com.odcgroup.page.ui.figure.TechnicalBoxFigure;

/**
 * Represents the body of a Column.
 * 
 * @author Gary Hayes
 * @author Alexandre Jaquet
 */
public class ColumnBody extends TechnicalBoxFigure implements PaintIterator {
	
	/** The Folder. */
	private static Image FOLDER = createImage("/icons/obj16/tree.png");	
	
	/** The height of the image. */
	public static final int IMAGE_HEIGHT = 16;	

	/** The current index is used by the paint algorithm to provide information to the child Widgets. */ 
	private int currentIndex;
	
	/**
	 * Creates a new ColumnBody.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public ColumnBody(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);		
		setLayoutManager(new ColumnBodyHorizontalLayout(figureContext));
	}	
	
	/**
	 * Gets the minimum height.
	 * 
	 * @return int The minimum height
	 */
	public int getMinHeight() {
		int previewSize = getParentTable().getPreviewSize();
		int h = super.getMinHeight();
		if (previewSize > 0) {
			h = super.getMinHeight() * previewSize;
		} 
		
		if (isGrouped()) {
			// Include the grouping bar 
			h += IMAGE_HEIGHT;
		}
		
		return h;
	}
	
	/**
	 * Correct a problem with the paint method. If isOpaque is true the paint
	 * method fills the bounds with the background color at the end. Therefore we lose
	 * our images....
	 * 
	 * @return boolean
	 */
	public boolean isOpaque() {
		return false;
	}
	
	/**
	 * Override the base class version to paint one line
	 * per row.
	 * 
	 * @param graphics The Graphics context
	 */
	public void paint(Graphics graphics) {		
		if (getFigureContext().isDesignMode()) {
			paintDesignMode(graphics);
		}else {
			paintPreviewMode(graphics);
		}
	}
	
	/**
	 * Paint the figure if we are in preview mode
	 * 
	 * @param graphics
	 * 			The graphics context
	 */
	private void paintPreviewMode(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		Rectangle b = getBounds();
		
		int prevewSize = getParentTable().getPreviewSize();
		if (prevewSize == 0) {
			// Paint the background Color
			Color c = fc.getTableEvenRowBackgroundColor();
			graphics.setBackgroundColor(c);
			graphics.fillRectangle(b);
			drawGroupingBar(graphics);
			// Paint the contents
			super.paint(graphics);
			return;
		}
		// The tree contains an image
		int imageHeight = 0;
		if (isGrouped()) {
			imageHeight = IMAGE_HEIGHT;
		}
		// Paint one line per row defined in the page size
		for (int i = 0; i < prevewSize; ++i) {
			currentIndex = i;
			float y = ((float) super.getBounds().height - imageHeight) / (float) prevewSize;
			if (i == 0) {
				y += imageHeight;
			}
			
			// Paint the background Color
			Color c = fc.getTableEvenRowBackgroundColor();
			if (i % 2 == 1) {
				c = fc.getTableOddRowBackgroundColor();
			}
			graphics.setBackgroundColor(c);
			
			graphics.fillRectangle(b.x, b.y, b.width, (int) y);
			
			// Paint the contents
			super.paint(graphics);
			
			// Translate the Graphics context for the next line
			graphics.translate(0, y);
		}
		
		// Retranslate the Graphics context back to its starting position
		graphics.translate(0, -super.getBounds().height );				
		// Draw the grouping bar		
		drawGroupingBar(graphics);
	}
	
	/** 
	 * Paint the Figure in design mode
	 * 
	 * @param graphics
	 * 			The graphics context
	 */
	private void paintDesignMode(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		Rectangle b = getBounds();
		IWidgetFigure columnHeader = getColumnHeader();	
		String v = columnHeader.getWidget().getPropertyValue(PropertyTypeConstants.VISIBLE);
		if ("true".equals(v)) {
			paintPreviewMode(graphics);		
			return;
		}
		
		// Paint a grey Rectangle since the Column is not visible
		Color c = fc.getTableOddRowBackgroundColor();
		graphics.setBackgroundColor(c);
		graphics.fillRectangle(b);			
		drawGroupingBar(graphics);			
		// Paint the contents
		super.paint(graphics);
		return;			
	}
	
	/**
	 * Gets the current index.
	 * 
	 * @return int The current index
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	/**
	 * Gets the parent Table.
	 * 
	 * @return Table The parent Table
	 */
	public Table getParentTable() {
		// The parent is the Column, its parent is the TableBody
		// and its parent is the Table
		return (Table) getParent().getParent().getParent();
	}
	
	/**
	 * Override the base class version. We have set the origin to IMAGE_HEIGHT in order
	 * to display the Tree folder. This causes all the children to be displaced. We need
	 * to correct this for all the children which do not have a folder (in other words 
	 * all the children except the first one)- 
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintChildren(Graphics graphics) {

		FigureConstants fc = getFigureConstants();
		IFigure child;
		
		// alternate background color 
		for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure) getChildren().get(i);
			Color c = fc.getTableEvenRowBackgroundColor();
			if (currentIndex % 2 == 1) {
				c = fc.getTableOddRowBackgroundColor();
			}
			child.setBackgroundColor(c);
		}
		
		if (! isGrouped()) {
			super.paintChildren(graphics);
			return;
		}
		
		Rectangle clip = Rectangle.SINGLETON;
		for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure) getChildren().get(i);
			if (currentIndex != 0) {
				graphics.translate(0, -IMAGE_HEIGHT);
			}
			
			if (child.isVisible() && child.intersects(graphics.getClip(clip))) {
				graphics.clipRect(child.getBounds());
				child.paint(graphics);
				graphics.restoreState();
			}
		}
	}	
	
	/**
	 * Draws the grouping bar + the image folder.
	 * 
	 * @param graphics The graphics context
	 */
	private void drawGroupingBar(Graphics graphics) {
		if (! isGrouped()) {
			return;
		}
		
		FigureConstants fc = getFigureConstants();
		Rectangle b = getBounds();
		
		// Draw the grouping bar
		Color bgColor = fc.getTreeBackgroundColor();
		graphics.setBackgroundColor(bgColor);
		graphics.fillRectangle(b.x, b.y, b.width, IMAGE_HEIGHT);
		
		// Draw the Folder image
		if (isFirstColumn()) {
			graphics.drawImage(FOLDER, new Point(b.x, b.y));			
		}	
	}	
	
	/**
	 * Returns true of the ColumnBody needs to be drawn as a Tree.
	 * 
	 * @return boolean True of the ColumnBody needs to be drawn as a Tree
	 */
	public boolean isGrouped() {
		return getParentTable().isGrouped();
	}
	
	/**
	 * Returns true if this is the first Column in the Table.
	 * 
	 * @return boolean True if this is the first Column in the Table
	 */
	private boolean isFirstColumn() {
		Widget column = getWidget().getParent();
		Widget tree = getParentTable().getWidget();
		if (tree.getContents().indexOf(column) == 0) {
			return true;
		}
		return false;
	}	
	
	/**
	 * Gets the ColumnHeader associated to the current Column
	 * 
	 * @return ColumnHeader returns the ColumnHeader that correspond to the current Column
	 */
	private IWidgetFigure getColumnHeader() {
		IWidgetFigure column = (IWidgetFigure)getParent();
		List<IWidgetFigure> figures = column.getChildren();
		IWidgetFigure columnHeader = (ColumnHeader) figures.get(WidgetTypeConstants.COLUMN_HEADER_INDEX);
		return columnHeader;
	}

}