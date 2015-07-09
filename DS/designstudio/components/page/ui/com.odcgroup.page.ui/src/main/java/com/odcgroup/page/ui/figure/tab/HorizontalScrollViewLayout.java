package com.odcgroup.page.ui.figure.tab;

import org.eclipse.draw2d.AbstractHintLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.CalculatingLayout;

/**
 * layout used by the HorizontalScrollView
 *
 */
public class HorizontalScrollViewLayout extends AbstractHintLayout implements CalculatingLayout {
	

	/**
	 * @see com.odcgroup.page.ui.figure.CalculatingLayout#calculateIndex(org.eclipse.draw2d.IFigure, org.eclipse.draw2d.geometry.Point)
	 */
	public int calculateIndex(IFigure container, Point location) {
		return 0;
	}

	/**
	 * @see org.eclipse.draw2d.AbstractHintLayout#calculateMinimumSize(org.eclipse.draw2d.IFigure, int, int)
	 */
	protected Dimension calculateMinimumSize(IFigure figure, int wHint, int hHint) {
		HorizontalScrollView header = (HorizontalScrollView) figure;
		Dimension min = new Dimension();
		Insets insets = header.getInsets();
		return min.getExpanded(insets.getWidth(), insets.getHeight());
	}

	
	/**
	 * @see org.eclipse.draw2d.AbstractLayout#calculatePreferredSize(org.eclipse.draw2d.IFigure, int, int)
	 */
	protected Dimension calculatePreferredSize(IFigure parent, int wHint, int hHint) {
		HorizontalScrollView header = (HorizontalScrollView) parent;
		Insets insets = header.getInsets();
		IFigure contents = header.getContents();
		if (header.getContentsTracksWidth() && wHint > -1)
			wHint = Math.max(0, wHint - insets.getWidth());
		else
			wHint = -1;
		
		Dimension minSize = contents.getMinimumSize(wHint, hHint);
		if (wHint > -1)
			wHint = Math.max(wHint, minSize.width);
		if (hHint > -1)
			hHint = Math.max(hHint, minSize.height);
		return contents
			.getPreferredSize(wHint, hHint)
			.getExpanded(insets.getWidth(), insets.getHeight());
	}	

	/**
	 * @see org.eclipse.draw2d.AbstractHintLayout#isSensitiveHorizontally(IFigure)
	 */
	protected boolean isSensitiveHorizontally(IFigure parent) {
		return true;
	}

	
	/**
	 * @see org.eclipse.draw2d.AbstractHintLayout#isSensitiveVertically(org.eclipse.draw2d.IFigure)
	 */
	protected boolean isSensitiveVertically(IFigure parent) {
		return false;
	}

	/**
	 * @see org.eclipse.draw2d.LayoutManager#layout(IFigure)
	 */
	public void layout(IFigure figure) {
		
		HorizontalScrollView viewport = (HorizontalScrollView)figure;
		IFigure contents = viewport.getContents();
		
		if (contents == null) return;
		Point p = viewport.getClientArea().getLocation();

		p.translate(viewport.getViewLocation().getNegated());
		
		// Calculate the hints
		Rectangle hints = viewport.getClientArea();
		int wHint = viewport.getContentsTracksWidth() ? hints.width : -1;
		int hHint =  -1;
		
		Dimension newSize = viewport.getClientArea().getSize();
		Dimension min = contents.getMinimumSize(wHint, hHint);
		Dimension pref = contents.getPreferredSize(wHint, hHint);		

		newSize.height = Math.max(newSize.height, pref.height);

		if (viewport.getContentsTracksWidth())
			newSize.width = Math.max(newSize.width, min.width);
		else
			newSize.width = Math.max(newSize.width, pref.width);
		
		contents.setBounds(new Rectangle(p, newSize));
	}	

}
