package com.tel.autosysframework.figures.lte;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import com.tel.autosysframework.figures.FixedAnchor;
import com.tel.autosysframework.figures.OutputFigure;
import com.tel.autosysframework.model.SimpleOutput;




public class ModulatorFigure extends OutputFigure {
	/**
	 * @see org.eclipse.draw2d.Figure#paintFigure(Graphics)
	 * 
	 */
	
	public ModulatorFigure() {
		FixedAnchor inputConnectionAnchor = new FixedAnchor(this);
		inputConnectionAnchor.offsetH = 0;

		inputConnectionAnchor.place = new Point(1,1);
		inputConnectionAnchors.addElement(inputConnectionAnchor);
		connectionAnchors.put(SimpleOutput.TERMINAL_IP, inputConnectionAnchor);
		
		outputConnectionAnchor.topDown = false;
		outputConnectionAnchor.offsetV = 1;
		outputConnectionAnchor.leftToRight = true;
		outputConnectionAnchor.offsetH = -1;		
		
	}
	protected void paintFigure(Graphics g) {
		//g.setXORMode(true);
		g.setForegroundColor(ColorConstants.black);	
		g.setBackgroundColor(ColorConstants.lightGray);
		
		PointList p1 = new PointList(4);
		

		Rectangle r = getBounds().getCopy();
		r.translate(10, 0);
		//r.setSize(120, 100);				
		p1.addPoint(r.right() - 20, r.bottom() - 1);
		p1.addPoint(r.x, r.bottom() - 1);
		p1.addPoint(r.x, r.y);
		p1.addPoint(r.right() - 20, r.y);	
	
		
		
		g.drawPolygon(p1);
		g.fillPolygon(p1);
		
		p1 = new PointList(4);
		g.setForegroundColor(ColorConstants.black);
		p1.addPoint(r.right() - 30, r.bottom() - 6);
		p1.addPoint(r.x + 10, r.bottom() - 6);
		p1.addPoint(r.x+10, r.y+5);
		p1.addPoint(r.right() - 30, r.y +5);
		g.drawPolygon(p1);
		
		g.drawLine(r.x+6, r.y+9, r.x+10, r.y+9);
		g.drawLine(r.x+6, r.y+(r.height/4)+7, r.x+10,  r.y+(r.height/4)+7);
		g.drawLine(r.x+6, r.bottom() - (r.height/4)-9, r.x+10, r.bottom() - (r.height/4)-9);
		g.drawLine(r.x+6, r.bottom()- 11, r.x+10, r.bottom()- 11);
		
		g.drawLine(r.right() - 30, r.getLeft().y, r.right() - 24, r.getLeft().y);		
		
		g.setForegroundColor(ColorConstants.red);
		//Draw terminals, 1 at right side as output
		g.drawLine(r.getRight().x - 20, r.getRight().y - 1, r.getRight().x - 11, r.getRight().y - 1);
				
		//Draw terminals, 1 at left side as input
		g.drawLine(r.getLeft().x - 10,r.getLeft().y, r.getLeft().x, r.getLeft().y);
		
		g.setForegroundColor(ColorConstants.darkBlue);
		g.drawText("Modulator", r.getCenter().x - 35, r.getCenter().y - 8);
		
		g.setForegroundColor(ColorConstants.black);
		
		g.drawRectangle(r.getLeft().x - 10, r.getLeft().y - 1, 2, 2);
		g.drawRectangle(r.getRight().x - 13, r.getRight().y - 2, 2, 2);
		
		r.width--;
		r.height--;
		
	}
}
