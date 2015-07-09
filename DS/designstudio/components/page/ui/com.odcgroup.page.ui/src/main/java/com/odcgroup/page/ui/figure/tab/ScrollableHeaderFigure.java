package com.odcgroup.page.ui.figure.tab;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.AbstractHeaderFigure;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.TechnicalBoxFigure;

/**
 *
 * @author pkk
 *
 */
public class ScrollableHeaderFigure extends TechnicalBoxFigure  {
	
	/** */
	private AbstractHeaderFigure header = null;

	/**	 */
	private static Image PREVIOUS = createImage("/icons/obj16/resultset_previous.png"); //$NON-NLS-1$
	/** */
	private static Image NEXT = createImage("/icons/obj16/resultset_next.png"); //$NON-NLS-1$

	/**
	 * @param widget
	 * @param figureContext
	 */
	public ScrollableHeaderFigure(Widget widget, FigureContext figureContext) {		
		super(widget, figureContext);
		setBoxType(PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
		setBackgroundColor(ColorConstants.white);
	}	
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		setBoxType(new HeaderBoxType(this));
	}

	/**
	 * @param header
	 */
	public void addHeader(AbstractHeaderFigure header) {
		this.header = header;
		ImageFigure leftArrow = new ImageFigure();
		leftArrow.setImage(PREVIOUS);
		add(leftArrow, 0);
		
		final HorizontalScrollView scrollPane = new HorizontalScrollView(getWidget(), getFigureContext());
		scrollPane.setContents(header);
		add(scrollPane, 1);
		
		ImageFigure rightArrow = new ImageFigure();
		rightArrow.setImage(NEXT);
		add(rightArrow, 2);
		
		//scrollPane.validate();
		
		leftArrow.addMouseListener(new MouseListener.Stub() { 
			public void mousePressed(MouseEvent me) {
				if (me.getSource() instanceof ImageFigure && scrollEnabled()) {
					scrollPane.decrementHorizontalLocation();
					repaint();
				}
			}
		});
		
		rightArrow.addMouseListener(new MouseListener.Stub() { 
			public void mousePressed(MouseEvent me) {
				if (me.getSource() instanceof ImageFigure && scrollEnabled()) {
					scrollPane.incrementHorizontalLocation();
					repaint();
				}
			}
		});		
	}	
	
	/**
	 * @return boolean
	 */
	private boolean scrollEnabled() {
		if (header.getMinWidth() > getBounds().width-4) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void drawOutline(Graphics graphics) {
		// do nothing
	}

	/**
	 * @see org.eclipse.draw2d.Figure#paintChildren(org.eclipse.draw2d.Graphics)
	 */
	protected void paintChildren(Graphics graphics) {
		IFigure child;

		Rectangle clip = Rectangle.SINGLETON;
		for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure)getChildren().get(i);
			if (child.isVisible() && child.intersects(graphics.getClip(clip))) {
				graphics.clipRect(child.getBounds());
				if (!scrollEnabled() && child instanceof ImageFigure) {
					graphics.setForegroundColor(ColorConstants.white);
					graphics.drawRectangle(child.getBounds());
				} else {
					child.paint(graphics);
				}
				graphics.restoreState();
			}
		}
	}	
	
	/**
	 * @see com.odcgroup.page.ui.figure.TechnicalBoxFigure#drawBorder(org.eclipse.draw2d.Graphics)
	 */
	protected void drawBorder(Graphics graphics) {
		// no border
	}
	
	

}
