package com.tel.autosysframework.figures.ports;

import org.eclipse.draw2d.ColorConstants;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.tel.autosysframework.editor.AutosysFrameworkEditor;
import com.tel.autosysframework.figures.BaseFigure;
import com.tel.autosysframework.figures.FixedAnchor;
import com.tel.autosysframework.model.SimpleOutput;

public class OutputPortFigure extends BaseFigure {

	public OutputPortFigure() {
		FixedAnchor inputConnectionAnchor = new FixedAnchor(this);
		inputConnectionAnchor.offsetH = 2;
		inputConnectionAnchor.place = new Point(1,1);
		inputConnectionAnchors.addElement(inputConnectionAnchor);
		connectionAnchors.put(SimpleOutput.TERMINAL_IP, inputConnectionAnchor);
	}

	protected void paintFigure(Graphics g) {
		//g.setXORMode(true);


		g.setForegroundColor(ColorConstants.black);	
		g.setBackgroundColor(ColorConstants.cyan);

		Rectangle r = getBounds().getCopy();

		PointList pl = new PointList(4);
		pl.addPoint(r.x+10,r.y + r.height/2);
		pl.addPoint((r.x+10 + r.right())/2,r.bottom() - 1);
		pl.addPoint(r.right() - 1,r.y + r.height/2);
		pl.addPoint((r.x+10 + r.right())/2,r.y);
	    /*pl.addPoint(r.x+5,r.bottom()- ((r.height)/2));
	    pl.addPoint(r.x+5+(r.width)/4,r.bottom()- 1);
	    pl.addPoint(r.right()-1,r.bottom()-1);
	    pl.addPoint(r.x + (3*(r.width)/4), r.bottom() - ((r.height)/2));
	    pl.addPoint(r.right()-1,r.y);
	    pl.addPoint(r.x+5+(r.width)/4, r.y);*/
	    g.drawPolygon(pl);

	    //g.setForegroundColor(ColorConstants.darkBlue);
		g.fillPolygon(pl);


		/*g.drawLine(r.x+10,r.bottom()- ((r.height)/2),r.x+10+(r.width)/4,r.bottom()- 1);
		g.drawLine(r.x+10+(r.width)/4,r.bottom()- 1,r.right()-1,r.bottom()-1);
		g.drawLine(r.right()-1,r.bottom()-1,r.x + (3*(r.width)/4), r.bottom() - ((r.height)/2) );
		g.drawLine(r.x+(3*(r.width)/4), r.bottom() - ((r.height)/2) ,r.right()-1,r.y);
		g.drawLine(r.right()-1,r.y,r.x + ((r.width)/4)+4, r.y);
		g.drawLine(r.x + ((r.width)/4)+4, r.y,r.x+10,r.bottom()- ((r.height)/2));*/


		g.setForegroundColor(ColorConstants.red);

		//Draw terminals, 1 at right side as output
		//g.drawLine(r.getLeft().x - 1, r.getLeft().y, r.x+5,r.getLeft().y);
		g.drawLine(r.x, r.getLeft().y, r.x+10,r.getLeft().y);


		g.drawText("O/P", r.getCenter().x - 4 ,r.getCenter().y - 7);

		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.getLeft().x , r.getLeft().y - 1, 2, 2);
		r.width--;
		r.height--;
		 

		/*try{
			Image img = new Image(AutosysFrameworkEditor.display, 
					this.getClass().getResourceAsStream("output.bmp"));
			g.drawImage(img, r.x, r.y);}catch(Exception e){
			e.printStackTrace();
		}*/
	}
}
