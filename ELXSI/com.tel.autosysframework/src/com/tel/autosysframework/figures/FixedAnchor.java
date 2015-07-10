package com.tel.autosysframework.figures;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;

public class FixedAnchor
extends AbstractConnectionAnchor
{
	public Point place;
	public int offsetH;

	public FixedAnchor(IFigure owner)
	{
		super(owner);
	}

	public Point getLocation(Point loc)
	{
		Rectangle r = getOwner().getBounds();
		int x = r.getLeft().x - 1 + offsetH;
		int y = r.getLeft().y/* - 1*/;
		Point p = new PrecisionPoint(x,y);
		getOwner().translateToAbsolute(p);
		return p;
	}
}