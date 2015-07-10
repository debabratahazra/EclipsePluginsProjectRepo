package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.GC;

import com.zealcore.se.ui.graphics.figures.ObjectFigure;

public class GanttOverviewDraw extends Figure {
	private final SWTGraphics draw2d;

	private final GC gc;

	GanttOverviewDraw(final GC gc) {
		this.gc = gc;
		setOpaque(true);
		if (gc == null) {
			draw2d = null;
			return;
		}
		draw2d = new SWTGraphics(gc);
	}

	Graphics getGraphics() {
		return draw2d;
	}

	void paint() {
		paintFigure(draw2d);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void paintFigure(final Graphics graphics) {
		if (gc == null || gc.isDisposed()) {
			return;
		}

		Rectangle clip = graphics.getClip(new Rectangle());
		graphics.setClip(getBounds());
		ArrayList<IFigure> children = new ArrayList<IFigure>(getChildren());
		Collections.sort(children, new Comparator<IFigure>() {
			public int compare(final IFigure o1, final IFigure o2) {
				Integer z1 = ObjectFigure.Z0;
				Integer z2 = ObjectFigure.Z0;
				if (o1 instanceof ObjectFigure) {
					z1 = ((ObjectFigure) o1).getZ();

				}
				if (o2 instanceof ObjectFigure) {
					z2 = ((ObjectFigure) o2).getZ();

				}
				return z1.compareTo(z2);
			}
		});

		for (final Object object : children) {
			((IFigure) object).paint(graphics);
		}
		graphics.setClip(clip);
	}

	public GC getGc() {
		return gc;
	}

}
