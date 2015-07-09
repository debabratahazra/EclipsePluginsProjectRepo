package com.odcgroup.page.ui.figure;

import java.util.Iterator;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.scroll.ScrollableWidgetFigure;
import com.odcgroup.page.ui.figure.scroll.XScrollBar;
import com.odcgroup.page.ui.figure.scroll.YScrollBar;

/**
 * This figure adds a border to the box.
 * 
 * @author Gary Hayes
 */
public class BoxFigure extends AbstractAlignableWidgetFigure implements Constrainable, ScrollableWidgetFigure {
	
	/** The percentage character. */
	private static final String CHARACTER_PERCENT = PropertyTypeConstants.CHARACTER_PERCENT;
	
	/** The flag indicating that the border should be horizontal. */
	private static final String BORDER_HORIZONTAL = "horizontal";
	
	/** The flag indicating that the border should be both horizontal and vertical. */
	private static final String BORDER_BOX = "box";
	
	/** The origin of the figure. */
	private Point origin = new Point(0, 0);
	
	/** The offset of the figure. This is zero unless the Box has a caption. */
	protected int yOffset = 0;
	
	/** The X-ScrollBar. */
	private XScrollBar xScrollBar;	
	
	/** The Y-ScrollBar. */
	private YScrollBar yScrollBar;
	
	/** The type of box. */
	private BoxType boxType;	
	
	/** The type of box as a String. */
	private String boxTypeStr;
	
	/** */
	private String widgetBorder = "";
	
	/**
	 * Creates a new BoxFigure.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public BoxFigure(Widget widget, FigureContext figureContext) {
	    super(widget, figureContext);
	    setOpaque(true);
	    String widgetName=widget.getType().getName();
	    //set the box figure type xtooltip 
	    if(widgetName!=null&&widgetName.equals("Xtooltip")){
		setBoxType(PropertyTypeConstants.BOX_TYPE_XTOOLTIP);
	    }
	    else{
		Property p = widget.findProperty(PropertyTypeConstants.BOX_TYPE);
		if (p != null) {
		    if (! StringUtils.isEmpty(p.getValue())) {
			setBoxType(p.getValue());
		    } else {
			setBoxType(PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
		    }
		} else {
		    setBoxType(PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
		}
	    }

	    setLayoutManager(getBoxType().createLayoutManager(getFigureContext()));

	    // force to calculate yOffset;
	    setYOffset(0);

	    widgetBorder = getString(PropertyTypeConstants.WIDGET_BORDER);

	}	
	
	@Override
	public void setText(String text) {
		if (StringUtils.isEmpty(text)) {
			setYOffset(0);
		} else {
			setYOffset(getFigureConstants().getSimpleWidgetDefaultHeight());
		}
		super.setText(text);
	}
	
	/**
	 * Override the base class version to ensure that the ScrollBars are chosen in priority
	 * to the contained WidgetFigure's.
	 * 
	 * @param x The x-position
	 * @param y The y-position
	 * @param search The search
	 * @return IFigure
	 */
	public IFigure findFigureAt(int x, int y, TreeSearch search) {
		if (isPointInScrollBar(x, y)) {
			return this;
		}
		return super.findFigureAt(x, y, search);
	}	
	
	/**
	 * Returns true since we wish to use local coordinates.
	 * 
	 * @return boolean True since we wish to use local coordinates
	 */
	protected boolean useLocalCoordinates() {
		return true;
	}	
	
	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		return getBoxType().getMinWidth();
	}	
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		if (!isVisible()) {
			return 0;
		}
		/*if (isCollapsed()) {
			return 16;
		}*/
		return getBoxType().getMinHeight() + getYOffset();
	}	

	/**
	 * Gets the maximum width of the figure. HorizontalBoxes do not have
	 * maximum widths. Instead they can expand to fill the entire width
	 * of the parent container.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		return getBoxType().getMaxWidth();
	}
	
	/**
	 * Gets the maximum height of the figure.
	 * 
	 * @return int The maximum height of the figure
	 */
	public int getMaxHeight() {
		return getBoxType().getMaxHeight();
	}
	
	/**
	 * Gets the y-offset of the scrollable part of the WidgetFigure.
	 * 
	 * @return int The y-offset of the scrollable part of the WidgetFigure
	 */
	public int getYOffset() {
		return yOffset;
	}
	
	/**
	 * Changes the y-offset of the scrollable part of the WidgetFigure
	 * @param newValue the new value.
	 */
	public void setYOffset(int newValue) {
		yOffset = newValue;
	}

	/**
	 * Gets the Scrollable bounds. This is the part of the WidgetFigure
	 * which is scrollable.
	 * 
	 * @return Rectangle The scrollable bounds
	 */
	public Rectangle getScrollableBounds() {
		Rectangle b = getBounds();
		int yo = getYOffset();
		Rectangle sb = new Rectangle(b.x, b.y + yo, b.width, b.height - yo);
		return sb;
	}
	
	/**
	 * Gets the scale factor to be used for the X-axis. This is
	 * (ChildrensWidth - FigureWidth) / FigureWidth.
	 * 
	 * @return double The scale factor to be used for the X-axis
	 */
	public double getXScaleFactor() {
		Rectangle b = getScrollableBounds();
		int clientWidth = b.width;
		if (hasYScrollBar()) {
			clientWidth -= SCROLL_BAR_SIZE;
		}		
		return ((double) (getChildrensWidth() - clientWidth)) / clientWidth;
	}
	
	/**
	 * Gets the scale factor to be used for the Y-axis. This is
	 * (ChildrensHeight - FigureHeight) / FigureHeight.
	 * 
	 * @return double The scale factor to be used for the Y-axis
	 */
	public double getYScaleFactor() {
		Rectangle b = getScrollableBounds();
		int clientHeight = b.height;
		if (hasXScrollBar()) {
			clientHeight -= SCROLL_BAR_SIZE;
		}
		return ((double) (getChildrensHeight() - clientHeight)) / clientHeight;		
	}
	
	/**
	 * Gets the width of the child figures. This is the difference between the
	 * left-hand side of the left-most figure and the right-hand side of the
	 * right-most figure.
	 * 
	 * @return int The width taken up by the child figures.
	 */
	private int getChildrensWidth() {
		int minChildX = 0;
		int maxChildX = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			Rectangle cb = child.getBounds();
			minChildX = Math.min(minChildX, cb.x);
			maxChildX = Math.max(maxChildX, cb.x + cb.width);
		}
		// We also need to take into account the Figure spacing
		return maxChildX - minChildX + 2 * getFigureConstants().getWidgetSpacing();		
	}
	
	/**
	 * Gets the height of the child figures. This is the difference between the
	 * top of of the top-most figure and the bottom of the
	 * bottom-most figure.
	 * 
	 * @return int The height taken up by the child figures.
	 */
	private int getChildrensHeight() {
		int minChildY = 0;
		int maxChildY = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			Rectangle cb = child.getBounds();
			minChildY = Math.min(minChildY, cb.y);
			maxChildY = Math.max(maxChildY, cb.y + cb.height);
		}
		// We also need to take into account the Figure spacing
		return maxChildY - minChildY + 2 * getFigureConstants().getWidgetSpacing();		
	}	
	
	/**
	 * Paints the specific figure. The Graphics context has already been
	 * translated to the origin of the figure.
	 * Subclasses need to override this method.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {	
		// All the painting is done in paintClientArea to ensure that
		// the ScrollBars and Border are painted on top of the children		
		drawBoxTypeIndicator(graphics);
	}
	
	/**
	 * Paints the client area. We paint the ScrollBar here since it needs
	 * to be painted on top of the child WidgetFigures.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintClientArea(Graphics graphics) {
		super.paintClientArea(graphics);
		
		Rectangle b = getBounds();
		
		graphics.translate(b.x, b.y);		

		drawCaption(graphics);
//		drawScrollBars(graphics);
		drawAggregationBorder(graphics);
		drawBorder(graphics);
		
		graphics.translate(-b.x, -b.y);	
	}
	
	/**
	 * Draws an visual indicator for the box type.
	 * 
	 * @param graphics
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {
		
		if (getFigureContext().isPreviewMode()) {
			return;
		}

		if (! isRootFigure()) {
			graphics.setClip(new Rectangle(5, -6, 20, 20));
			graphics.drawText(getBoxType().getBoxType(), 5, -6);
		}
	}
	
	/**
	 * Draw the caption.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawCaption(Graphics graphics) {
		if (StringUtils.isEmpty(getText())) {
			return;
		}
		
		FigureConstants fc = getFigureConstants();
		graphics.setBackgroundColor(fc.getCaptionBackgroundColor());
		
		Rectangle b = getBounds();
		graphics.fillRectangle(0, 0, b.width, getYOffset());
		
		graphics.setFont(fc.getCaptionFont(false));
		graphics.drawText(getText(), 2, 0);
	}
	
	/**
	 * Draw the X and Y ScrollBars if they are required.
	 * 
	 * @param graphics The Graphics context
	 */
	private void drawScrollBars(Graphics graphics) {
		if (hasXScrollBar()) {
			if (xScrollBar == null) {
				xScrollBar = new XScrollBar(getFigureContext(), this);					
			}	
			drawXScrollBar(graphics);
		} else {
			xScrollBar = null;
		}
		
		if (hasYScrollBar()) {
			if (yScrollBar == null) {
				yScrollBar = new YScrollBar(getFigureContext(), this);
			}
			drawYScrollBar(graphics);
		} else {
			yScrollBar = null;
		}	
		
		// Draw the little space in the bottom right-hand corner
		if (hasXScrollBar() && hasYScrollBar()) {
			Rectangle b = getBounds();
			Rectangle sb = new Rectangle(b.width - SCROLL_BAR_SIZE, b.height - SCROLL_BAR_SIZE, SCROLL_BAR_SIZE, SCROLL_BAR_SIZE);
			graphics.setBackgroundColor(getFigureConstants().getScrollBarBackgroundColor());
			graphics.fillRectangle(sb);
		}
	}
	
	/**
	 * Returns true if the Box needs to have an X-ScrollBar. This is true
	 * if the difference between x-position of the left-most Widget 
	 * and the x-position of the right-most Widget is greater
	 * than the width of this Box. Note the minimum x-position can
	 * be less than zero! This occurs when the scrollbar has been used.
	 * 
	 * @return boolean True if the Box needs to have X-ScrollBar.
	 */
	protected boolean hasXScrollBar() {
		if (getPixelWidth() == 0 || getPercentageWidth() < 100) {
			// Only fixed-size figures can have ScrollBars
			return false;
		}
		
		int minChildX = 0;
		int maxChildX = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			Rectangle cb = child.getBounds();
			minChildX = Math.min(minChildX, cb.x);
			maxChildX = Math.max(maxChildX, cb.x + cb.width);
		}
		Rectangle b = getBounds();
		return (maxChildX - minChildX) > b.width;
	}	
	
	/**
	 * Returns true if the Box needs to have an Y-ScrollBar. This is true
	 * if the difference between y-position of the top-most Widget 
	 * and the y-position of the bottom-most Widget is greater
	 * than the height of this Box. Note the minimum y-position can
	 * be less than zero! This occurs when the scrollbar has been used.
	 * 
	 * @return boolean True if the Box needs to have Y-ScrollBar.
	 */
	protected boolean hasYScrollBar() {
		if (getHeight() == 0) {
			// Only fixed-size figures can have ScrollBars
			return false;
		}
		
		int minChildY = 0;
		int maxChildY = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			Rectangle cb = child.getBounds();
			minChildY = Math.min(minChildY, cb.y);
			maxChildY = Math.max(maxChildY, cb.y + cb.height);
		}
		Rectangle b = getBounds();
		return (maxChildY - minChildY) > b.height;
	}
	
	/**
	 * Draws the X-ScrollBar.
	 * 
	 * @param graphics The Graphics context
	 */
	private void drawXScrollBar(Graphics graphics) {
		Rectangle b = getBounds();
		
		// Start from the bottom (b.height) - the size of the ScrollBar (SCROLL_BAR_SIZE)		
		// Note that we are using local coordinates
		int yScrollBarSize = 0;
		if (hasYScrollBar()) {
			yScrollBarSize = SCROLL_BAR_SIZE;
		}
		Rectangle sb = new Rectangle(0, b.height - SCROLL_BAR_SIZE, b.width - yScrollBarSize, b.height);
		xScrollBar.setBounds(sb);

		xScrollBar.paint(graphics);
	}	

	/**
	 * Draws the Y-ScrollBar.
	 * 
	 * @param graphics The Graphics context
	 */
	private void drawYScrollBar(Graphics graphics) {
		Rectangle b = getBounds();
		int yo = getYOffset();
		
		// Start from the right-hand side (b.width) - the size of the ScrollBar (SCROLL_BAR_SIZE)
		// Note that we are using local coordinates
		int xScrollBarSize = 0;
		if (hasXScrollBar()) {
			xScrollBarSize = SCROLL_BAR_SIZE;
		}		
		
		Rectangle sb = new Rectangle(b.width - SCROLL_BAR_SIZE, yo, b.width, b.height - xScrollBarSize - yo);
		yScrollBar.setBounds(sb);
			
		yScrollBar.paint(graphics);
	}
	
	/**
	 * Draws the border for aggregated figure.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawAggregationBorder(Graphics graphics) {
		if (getAggregation()) {
			drawAggregation(graphics);
		} else {
			drawOutline(graphics);
		}
	}
	
	/**
	 * Draws the border for this figure.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawBorder(Graphics graphics) {
		
		String widgetBorder = getWidgetBorder();
		
		if (BORDER_BOX.equalsIgnoreCase(widgetBorder) || BORDER_HORIZONTAL.equalsIgnoreCase(widgetBorder)) {
			drawTopAndBottom(graphics);
		}

		if (BORDER_BOX.equalsIgnoreCase(widgetBorder)) {			
			drawLeftAndRight(graphics);
		}		
	}
	
	/**
	 * Draws an outline around the figure when the figure is
	 * an aggregation. This is only drawn if we are in
	 * design mode.
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	private void drawAggregation(Graphics graphics) {
		if (getFigureContext().isPreviewMode()) {
			// Nothing to display
			return;
		}

		FigureConstants fc = getFigureConstants();

		graphics.setForegroundColor(fc.getAggregationBorderForegroundColor());
		graphics.setLineStyle(fc.getAggregationBorderLineStyle());
		graphics.setLineWidth(fc.getAggregationBorderLineWidth());

		// The -1 ensures that the right and bottom lines are drawn, otherwise
		// they would fall outside the bounds of the TextField
		Rectangle b = getBounds();
		graphics.drawRectangle(new Rectangle(0, 0, getBoxBoundsWidth(), b.height - 1));
	}	
	
	/**
	 * Draws the top and bottom lines of the border.
	 * 
	 * @param graphics The Graphics context
	 */
	private void drawTopAndBottom(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		
		// Note that the -1 ensures that the right and bottom lines are drawn,
		// otherwise they would fall outside the bounds of the TextField
		Rectangle b = getBounds();
		
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.setForegroundColor(fc.getBoxBorderColor());
		
		int lineWidth = getBoxBoundsWidth();
		
		// Top
		graphics.drawLine(0, 0, lineWidth, 0);
		// Bottom
		graphics.drawLine(0, b.height - 1, lineWidth, b.height - 1);
	}
	
	/**
	 * Draws the left and right lines of the border.
	 * 
	 * @param graphics The Graphics context
	 */
	private void drawLeftAndRight(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		
		// Note that the -1 ensures that the right and bottom lines are drawn,
		// otherwise they would fall outside the bounds of the TextField
		Rectangle b = getBounds();
		
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.setForegroundColor(fc.getBoxBorderColor());
		
		int lineWidth = getBoxBoundsWidth();
		
		// Left
		graphics.drawLine(0, 0, 0, b.height - 1);
		
		// Right
		graphics.drawLine(lineWidth, 0, lineWidth, b.height - 1);		

	}	
		
	/**
	 * Gets the XScrollBar.
	 * 
	 * @return XScrollBar The XScrollBar
	 */
	public XScrollBar getXScrollBar() {
		return xScrollBar;
	}
	
	/**
	 * Gets the YScrollBar.
	 * 
	 * @return YScrollBar The YScrollBar
	 */
	public YScrollBar getYScrollBar() {
		return yScrollBar;
	}
	
	/**
	 * Gets the origin of the figure.
	 * 
	 * @return Point The origin of the figure
	 */
	public Point getOrigin() {
		return origin;
	}
	
	/**
	 * Sets the origin of the figure.
	 * 
	 * @param origin The origin to set
	 */
	public void setOrigin(Point origin) {
		this.origin = origin;
	}
	
	/**
	 * Gets the BoxType.
	 * 
	 * @return BoxType The BoxType
	 */
	public BoxType getBoxType() {
		return boxType;
	}
	
	/**
	 * @param boxType the box Type
	 */
	protected final void setBoxType(BoxType boxType) {
		this.boxType = boxType;
	}
	
	protected final void setBoxTypeName(String name) {
		this.boxTypeStr = name;
	}
	
	/**
	 * Sets the BoxType.
	 * 
	 * @param newBoxType The boxType
	 */	
	public void setBoxType(String newBoxType) {
		if (! ObjectUtils.equals(boxTypeStr, newBoxType)) {
			if (PropertyTypeConstants.BOX_TYPE_HORIZONTAL.equals(newBoxType)) {
				setBoxType(new HorizontalBoxType(this));
			} else if (PropertyTypeConstants.BOX_TYPE_VERTICAL.equals(newBoxType)) {
				setBoxType(new VerticalBoxType(this));
			}else if(PropertyTypeConstants.BOX_TYPE_XTOOLTIP.equals(newBoxType)){
				setBoxType(new XtooltipBoxType(this));
			}
			setLayoutManager(getBoxType().createLayoutManager(getFigureContext()));
		}
		
		setBoxTypeName(newBoxType);
	}
	
	/**
	 * Gets the constraints. This is a percentage.
	 * 
	 * @return int The constraints
	 */
	public int getConstraints() {
		return getInt(PropertyTypeConstants.CONSTRAINTS);
	}


	/**
	 * Gets the widgetBorder.
	 * 
	 * @return String The widgetBorder
	 */
	public String getWidgetBorder() {
		return widgetBorder;
	}

	
	/**
	 * Return the fixed height of the HorizontalBox.
	 * 
	 * @return int
	 */
	public int getHeight() {
		/*if (isCollapsed()) {
			return 0;
		}*/
		return getInt(PropertyTypeConstants.HEIGHT);
	}
	
	/**
	 * Return the width of the HorizontalBox.
	 * 
	 * @return String
	 */
	public String getWidth() {
		return getString(PropertyTypeConstants.WIDTH);
	}

	/**
	 * Get the id of the HorizontalBox.
	 * 
	 * @return String
	 */
	public String getId() {
		return getWidget().getID();
	}

	/**
	 * Gets the flag indicating that the Box is an aggregation.
	 *
	 * @return boolean The flag indicating that the Box is an aggregation
	 */
	public boolean getAggregation() {
		return getBoolean(PropertyTypeConstants.AGGREGATION);
	}

	/**
	 * Code who must be executed after settings the properties
	 * @param name
	 * 			The name of the property
	 */
	public void afterPropertyChange(String name) {		
		if (name.equals(PropertyTypeConstants.BOX_TYPE)) {
			String value = getString(PropertyTypeConstants.BOX_TYPE);
			setBoxType(value);
		} else if (name.equals(PropertyTypeConstants.WIDGET_BORDER)) {
			widgetBorder = getString(PropertyTypeConstants.WIDGET_BORDER);
		}
	}
	
	/**
	 * Returns true if the point is touching a ScrollBar.
	 * 
	 * @param x The x-position
	 * @param y The y-position
	 * @return boolean
	 */
	private boolean isPointInScrollBar(int x, int y) {
		if (xScrollBar != null) {
			if (xScrollBar.getBounds().contains(x, y)) {
				return true;
			}
		}
		if (yScrollBar != null) {
			if (yScrollBar.getBounds().contains(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the width of the box in pixels. If the width is expressed
	 * in percentage this returns 0.
	 * 
	 * @return int The width of the box in pixels
	 */
	public int getPixelWidth() {
		String w = getWidth();
		if (w.contains(CHARACTER_PERCENT)) {
			return 0;
		}
		try {
			return Integer.parseInt(w);
		} catch(NumberFormatException nfe) {
			return 0;
		}
	}	
	
	/**
	 * Gets the width of the box as a percentage. If the width is expressed
	 * in pixels this returns 100.
	 * 
	 * @return int The width of the box as a percentage
	 */
	public int getPercentageWidth() {
		String w = getWidth();
		if (! w.contains(CHARACTER_PERCENT)) {
			return 100;
		}
		try {
			w = w.replaceAll(CHARACTER_PERCENT, "");
			return Integer.parseInt(w);
		} catch(NumberFormatException nfe) {
			return 0;
		}
	}	
	
	/**
	 * Calculates the width to draw the box taking into account the percentage
	 * width.
	 * 
	 * @return int The width to draw the box
	 */
	public int getBoxBoundsWidth() {
		return (int) ((getBounds().width - 1) * getPercentageWidth() / 100);
	}
	
	@Override
	protected void setGraphicsForOutline(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getOutlineBorderForegroundColor());
		graphics.setLineStyle(fc.getOutlineBorderLineStyle());
	}

}
