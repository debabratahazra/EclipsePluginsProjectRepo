package com.odcgroup.pageflow.editor.diagram.custom.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;

/**
 * @author pkk
 *
 */
public class CompartmentFigure extends Figure {

	/**
	 * default constructor
	 */
	public CompartmentFigure() {
	    ToolbarLayout layout = new ToolbarLayout();
	    layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
	    layout.setStretchMinorAxis(false);
	    layout.setSpacing(2);
	    setLayoutManager(layout);
	    setBorder(new CompartmentFigureBorder());
	  }
	    
	  
	public class CompartmentFigureBorder extends AbstractBorder {
	    /* (non-Javadoc)
	     * @see org.eclipse.draw2d.Border#getInsets(org.eclipse.draw2d.IFigure)
	     */
	    public Insets getInsets(IFigure figure) {
	      return new Insets(3,0,0,0);
	    }
	    
	    /* (non-Javadoc)
	     * @see org.eclipse.draw2d.Border#paint(org.eclipse.draw2d.IFigure, org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
	     */
	    public void paint(IFigure figure, Graphics graphics, Insets insets) {
	      graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(), tempRect.getTopRight());
	    }
	}
	
} 