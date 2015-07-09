package com.odcgroup.pageflow.editor.diagram.custom.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * @author pkk
 * gradient
 */
public class RoundedRectangleFigure extends RoundedRectangle {

	
	/**
	 * @see Shape#fillShape(Graphics)
	 */
	protected void fillShape(Graphics graphics) {
		Rectangle f = Rectangle.SINGLETON;
		Rectangle r = getBounds();
		f.x = r.x + lineWidth / 2;
		f.y = r.y + lineWidth / 2;
		f.width = r.width - lineWidth-1;
		f.height = r.height - lineWidth-1;
		
		Rectangle g = Rectangle.SINGLETON;
		g.x = f.x+1;
		g.y = f.y+1;
		g.width = f.width -2;
		g.height = r.height - lineWidth -1;

        Color foreground = graphics.getForegroundColor();
        Color background = graphics.getBackgroundColor();	      

        graphics.setBackgroundColor(background);
        graphics.setForegroundColor(background);

    	graphics.fillRoundRectangle(f, corner.width, corner.height);
    	
        // Gradient
        graphics.setForegroundColor(darker(background));
        graphics.fillGradient(g, false);
        graphics.setForegroundColor(darker(background));
		/*
		Rectangle r = getBounds().getCopy();
		Point topLeft = r.getTopLeft();
		Point bottomRight = r.getBottomRight();
		Pattern pattern = new Pattern(Display.getCurrent(), topLeft.x,
		    topLeft.y, bottomRight.x, bottomRight.y, ColorConstants.white,
		    ColorConstants.darkBlue);
		graphics.setBackgroundPattern(pattern);
		graphics.fillRoundRectangle(r, corner.width, corner.height);
		graphics.setBackgroundPattern(null);
		pattern.dispose();
		*/

	}
	
	protected void outlineShape(Graphics graphics) {
		Rectangle f = Rectangle.SINGLETON;
		Rectangle r = getBounds();
		f.x = r.x + lineWidth / 2;
		f.y = r.y + lineWidth / 2;
		f.width = r.width - lineWidth;
		f.height = r.height - lineWidth;
		graphics.drawRoundRectangle(f, corner.width, corner.height);
	}
	
	private static final float RGB_VALUE_MULTIPLIER = 0.8f;

    public Color darker(Color color) {
        return getColor((int) (color.getRed() * RGB_VALUE_MULTIPLIER), (int) (color.getGreen() * RGB_VALUE_MULTIPLIER),
                (int) (color.getBlue() * RGB_VALUE_MULTIPLIER));
    }
    
    public Color getColor(int red, int green, int blue) {
        return new Color(null,new RGB(red, green, blue));
    }

}
