package com.odcgroup.page.ui.figure;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;

public class ExternalIncludeFigure extends BoxFigure {
	
	private static final int BORDER_SPACE = 8;
	private static final int MIN_HEIGHT = 24;

	/** The background image. */
    private static final Image BACKGROUND_IMAGE = createImage("/icons/obj16/background.png");
	
    /** The background pattern. */
    private static final Pattern BACKGROUND_PATTERN = new Pattern(null, BACKGROUND_IMAGE);
	
	private static final String EXTERNAL_CONTENT = "External Content";
	private static final String EXTERNAL_IMAGE = "External Image";

	public ExternalIncludeFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.AbstractAlignableWidgetFigure#getHorizontalAlignment()
	 */
	public String getHorizontalAlignment() {
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.AbstractAlignableWidgetFigure#getVerticalAlignment()
	 */
	public String getVerticalAlignment() {
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#paintSpecificFigure(org.eclipse.draw2d.Graphics)
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		Rectangle widgetBoundary = getBounds();
		Rectangle parentBoundary = getParent().getBounds();
		
        graphics.setBackgroundPattern(BACKGROUND_PATTERN);
        
        if( widgetBoundary.height > parentBoundary.height) {
        	graphics.fillRectangle(0, 0, widgetBoundary.width, parentBoundary.height);
        } else {
        	graphics.fillRectangle(0, 0, widgetBoundary.width, widgetBoundary.height);
        }
        
        graphics.setFont(getFigureConstants().getCaptionFont(false));
        graphics.drawText(getDisplayText(), BORDER_SPACE, BORDER_SPACE);
	}

	/**
	 * @return
	 */
	private String getDisplayText() {
		String propertyValue = getWidget().getPropertyValue(PropertyTypeConstants.URL_TYPE);
		
		if (propertyValue.equalsIgnoreCase("page")) {
			return EXTERNAL_CONTENT;
		}
		else {
			return EXTERNAL_IMAGE;
		}
	}


	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#drawCaption(org.eclipse.draw2d.Graphics)
	 */
	protected void drawCaption(Graphics graphics) {
		FigureConstants figureConstants = getFigureConstants();
		graphics.setBackgroundColor(figureConstants.getModuleHeaderBackgroundColor());
		Rectangle boundaryRectangle = getBounds();
		graphics.fillRectangle(0, 0, boundaryRectangle.width, getYOffset());
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#drawBorder(org.eclipse.draw2d.Graphics)
	 */
	protected void drawBorder(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getBoxBorderColor());	
		
		// Note that the -1 ensures that the right and bottom lines are drawn,
		// otherwise they would fall outside the bounds of the Module
		Rectangle boundaryrectangle = getBounds();
		
		int insideBoundaryHeight = boundaryrectangle.height - 1;
		
		Rectangle parentBoundary = getParent().getBounds();
		if( boundaryrectangle.height > parentBoundary.height) {
			insideBoundaryHeight = parentBoundary.height - 1;
	    } else {
	       	insideBoundaryHeight = boundaryrectangle.height - 1;
	    }
		
		int insideBoundaryWidth = boundaryrectangle.width - 1;
		
		
		graphics.setLineStyle(SWT.LINE_SOLID);
		graphics.drawRectangle(0, 0, insideBoundaryWidth, insideBoundaryHeight);
	}	
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getMinWidth()
	 */
	public int getMinWidth() {
		return -1;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getMinHeight()
	 */
	public int getMinHeight() {
		String setheight = getWidget().getPropertyValue(PropertyTypeConstants.HEIGHT);
		int height = 0;
		if(!StringUtils.isEmpty(setheight)) {
			try {
				height = Integer.parseInt(setheight);
			} catch (Exception e) {
			}
		}
		if (height > MIN_HEIGHT) {
			return height;
		} else {		
			return MIN_HEIGHT;
		}
	}	
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.AbstractWidgetFigure#preferenceChange()
	 */
	public void preferenceChange() {
		revalidate();
	}	
}
