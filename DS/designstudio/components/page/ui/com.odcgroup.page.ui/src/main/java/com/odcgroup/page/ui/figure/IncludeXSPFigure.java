package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import com.odcgroup.page.model.Widget;

/**
 * A special figure to show the inclusion of an external module.
 * 
 * @author atr
 */
public class IncludeXSPFigure extends BoxFigure {
	
    /** The background image. */
    private static final Image BACKGROUND_IMAGE = createImage("/icons/obj16/background.png");
	
    /** The background pattern. */
    private static final Pattern BACKGROUND_PATTERN = new Pattern(null, BACKGROUND_IMAGE);
	
	/**
	 * Creates the figure
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public IncludeXSPFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#drawCaption(org.eclipse.draw2d.Graphics)
	 */
	protected void drawCaption(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		graphics.setBackgroundColor(fc.getModuleHeaderBackgroundColor());
		Rectangle b = getBounds();
		graphics.fillRectangle(0, 0, b.width, getYOffset());
	}
	
    /**
     * @see com.odcgroup.page.ui.figure.BoxFigure#paintSpecificFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintSpecificFigure(Graphics graphics) {
        Rectangle b = getBounds();

        if (getFigureContext().isDesignMode()) {
            graphics.setBackgroundPattern(BACKGROUND_PATTERN);
        } else {
        	graphics.setBackgroundColor(ColorConstants.white);
        }
		graphics.fillRectangle(0, 0, b.width, b.height);

        graphics.setFont(getFigureConstants().getCaptionFont(false));
        graphics.drawText("Include XSP", 10, 20);
        
    }
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#drawBorder(org.eclipse.draw2d.Graphics)
	 */
	protected void drawBorder(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		
		Rectangle b = getBounds();
		
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.setForegroundColor(fc.getBoxBorderColor());	
		
		// Note that the -1 ensures that the right and bottom lines are drawn,
		// otherwise they would fall outside the bounds of the Module
		graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
	}	
	
	@Override
	public void preferenceChange() {
		revalidate();
	}	
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getMinHeight()
	 */
	public int getMinHeight() {
		return 80;
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getYOffset()
	 */
	public int getYOffset() {
		return getFigureConstants().getSimpleWidgetDefaultHeight();
	}	
}
