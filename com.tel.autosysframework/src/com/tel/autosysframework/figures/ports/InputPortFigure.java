package com.tel.autosysframework.figures.ports;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tel.autosysframework.figures.OutputFigure;


public class InputPortFigure extends OutputFigure {

	public InputPortFigure() {
		outputConnectionAnchor.topDown = false;
		outputConnectionAnchor.offsetV = 1;
		outputConnectionAnchor.leftToRight = true;
		outputConnectionAnchor.offsetH = -1;
				
	}
	protected void paintFigure(Graphics g) {
		//g.setXORMode(true);
		g.setForegroundColor(ColorConstants.black);	
		g.setBackgroundColor(ColorConstants.cyan);

		Rectangle r = getBounds().getCopy();
		PointList pl = new PointList(4);
	    pl.addPoint((r.right() - 10 + r.x)/2, r.y);
	    pl.addPoint(r.x, r.y + r.height/2);
	    pl.addPoint((r.right() - 10 + r.x)/2, r.bottom() - 1);
	    pl.addPoint(r.right() - 10, r.y + r.height/2);
	    
	    /*pl.addPoint(r.x + (r.width)/4, r.bottom()-(r.height)/2);
	    pl.addPoint(r.x, r.bottom() - 1);
	    pl.addPoint(r.x + ((r.width)/2)+4, r.bottom() - 1);
	    pl.addPoint(r.x + (3*((r.width)/4))+4,r.bottom() - (r.height)/2);
	    pl.addPoint(r.x + ((r.width)/2)+4, r.y);*/
	    g.drawPolygon(pl);
		
	    //g.setForegroundColor(ColorConstants.darkBlue);
		g.fillPolygon(pl);
		/*g.drawLine(r.x, r.y,r.x + (r.width)/4, r.bottom()-(r.height)/2);
		g.drawLine(r.x + (r.width)/4, r.bottom()-(r.height)/2,r.x, r.bottom() -1);
		g.drawLine(r.x, r.bottom() - 1,r.x + ((r.width)/2)+4, r.bottom() -1 );
		g.drawLine(r.x + ((r.width)/2)+4, r.bottom() -1 ,r.x + (3*((r.width)/4))+4,r.bottom() - (r.height)/2);
		g.drawLine(r.x + (3*((r.width)/4))+4,r.bottom() -  (r.height)/2,r.x + ((r.width)/2)+4, r.y);
		g.drawLine(r.x, r.y,r.x + ((r.width)/2)+4, r.y);*/	
		
		
		g.setForegroundColor(ColorConstants.red);
		
		//Draw terminals, 1 at right side as output
		//g.drawLine(r.x + (3*((r.width)/4))+4,r.getRight().y, r.getRight().x - 1, r.getRight().y);
		g.drawLine(r.right() - 10,r.getRight().y, r.getRight().x - 1, r.getRight().y);
		
		g.drawText("I/P", r.getCenter().x - 10,r.getCenter().y - 7);
		
		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.getRight().x - 3, r.getRight().y - 1, 2, 2);
		
		r.width--;
		r.height--;
		
	}
	
}
