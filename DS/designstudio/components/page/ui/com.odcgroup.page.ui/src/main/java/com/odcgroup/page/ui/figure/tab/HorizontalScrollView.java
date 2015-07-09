package com.odcgroup.page.ui.figure.tab;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.DefaultRangeModel;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.AbstractHeaderFigure;
import com.odcgroup.page.ui.figure.AbstractWidgetFigure;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 * A view figure whose contents can be scrolled horizontally, when the content width exceeds
 * that of parent
 * 
 */
public class HorizontalScrollView extends AbstractWidgetFigure implements PropertyChangeListener {
	
	/** */
	public static final String PROPERTY_VIEW_LOCATION = "viewLocation"; //$NON-NLS-1$
	/** */
	private RangeModel horizontalRangeModel = null;
	/** */
	private RangeModel verticalRangeModel = null;
	/** */
	private boolean ignoreScroll = false;
	/** */
	private IFigure view = null;

	/**
	 * @param widget
	 * @param figureContext
	 */
	public HorizontalScrollView(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		setLayoutManager(new HorizontalScrollViewLayout());
		horizontalRangeModel = new DefaultRangeModel();
		verticalRangeModel = new DefaultRangeModel();
	}
	
	/**
	 * @return point
	 */
	public Point getViewLocation() {
		return new Point(getHorizontalRangeModel().getValue(), getVerticalRangeModel().getValue());
	}
	
	/**
	 * @see Figure#paintClientArea(Graphics)
	 */
	protected void paintClientArea(Graphics g) {
		Point p = getViewLocation();		
		try {
			g.translate(-p.x, -p.y);
			g.pushState();
			super.paintClientArea(g);
			g.popState();
		} finally {
			g.translate(p.x, p.y);
		}
	}
	
	/**
	 * @see org.eclipse.draw2d.Figure#isCoordinateSystem()
	 */
	public boolean isCoordinateSystem() {
		return true;
	}
	
	/**
	 * @return boolean
	 */
	public boolean getContentsTracksWidth() {
		return true;
	}

	@Override
	public int getMinHeight() {
		return 16;
	}

	@Override
	public int getMinWidth() {
		return getParent().getBounds().width - 18;
	}
	
	/**
	 * @see org.eclipse.draw2d.Figure#getClientArea(org.eclipse.draw2d.geometry.Rectangle)
	 */
	public Rectangle getClientArea(Rectangle rect) {
		super.getClientArea(rect);
		rect.translate(getViewLocation());
		return rect;
	}

	/**
	 * @return the horizontalRangeModel
	 */
	public RangeModel getHorizontalRangeModel() {
		return horizontalRangeModel;
	}

	/**
	 * @param rangeModel the horizontalRangeModel to set
	 */
	public void setHorizontalRangeModel(RangeModel rangeModel) {
		if (horizontalRangeModel != null)
			horizontalRangeModel.removePropertyChangeListener(this);
		horizontalRangeModel = rangeModel;
		horizontalRangeModel.addPropertyChangeListener(this);
	}

	/**
	 * @return the verticalRangeModel
	 */
	public RangeModel getVerticalRangeModel() {
		return verticalRangeModel;
	}

	/**
	 * @param rangeModel the verticalRangeModel to set
	 */
	public void setVerticalRangeModel(RangeModel rangeModel) {
		if (verticalRangeModel != null)
			verticalRangeModel.removePropertyChangeListener(this);
		verticalRangeModel = rangeModel;
		verticalRangeModel.addPropertyChangeListener(this);
	}	
	
	/**
	 * 
	 */
	public void incrementHorizontalLocation() {
		setViewLocation(getHorizontalRangeModel().getValue()+30, getVerticalRangeModel().getValue());
	}	
	
	/**
	 * 
	 */
	public void decrementHorizontalLocation() {
		setViewLocation(getHorizontalRangeModel().getValue()-30, getVerticalRangeModel().getValue());
	}
	
	/**
	 * @param event
	 */
	@SuppressWarnings("deprecation")
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getSource() instanceof RangeModel) {
			if (RangeModel.PROPERTY_VALUE.equals(event.getPropertyName())) {
				if (!ignoreScroll) {
					localRevalidate();
					repaint();
					fireMoved(); 
				}
				firePropertyChange(PROPERTY_VIEW_LOCATION, event.getOldValue(), 
									event.getNewValue());
			}
		}
	}
	
	/**
	 * 
	 */
	private void localRevalidate() {
		invalidate();
		if (getLayoutManager() != null)
			getLayoutManager().invalidate();
		getUpdateManager().addInvalidFigure(this);
	}
	
	/**
	 * 
	 */
	protected void readjustScrollBars() {
		if (getContents() == null)
			return;		
		AbstractHeaderFigure header = (AbstractHeaderFigure)getContents();
		int selIndex = header.getSelectedItemIndex();
		if (selIndex == 0) {
			setViewLocation(0, getVerticalRangeModel().getValue());
		} else {
			getVerticalRangeModel().setAll(0, getClientArea().height, getContents().getBounds().height);		
			getHorizontalRangeModel().setAll(0, getClientArea().width, getContents().getBounds().width+10);
			int selX = header.getSelectedItemX()-18;
			int width = getMinWidth();
			int x = getHorizontalRangeModel().getValue();
			if (selX > 0 && selX > width) {
				x = selX-width;
			}
			setViewLocation(x, getVerticalRangeModel().getValue());
		}
	}
	
	/**
	 * @param figure
	 */
	public void setContents(IFigure figure) {
		if (view == figure)
			return;
		if (view != null)
			remove(view);
		view = figure;
		if (view != null)
			add(figure);
	}
	
	/**
	 * @return figure
	 */
	public IFigure getContents() {
		return view;
	}
	
	/**
	 * @see org.eclipse.draw2d.Figure#validate()
	 */
	public void validate() {
		super.validate();
		readjustScrollBars();
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void setViewLocation(int x, int y) {		
		if (getHorizontalRangeModel().getValue() != x)
			getHorizontalRangeModel().setValue(x);
		if (getVerticalRangeModel().getValue() != y)
			getVerticalRangeModel().setValue(y);
	}
	
	/**
	 * @param p
	 */
	public void setViewLocation(Point p) {
		setViewLocation(p.x, p.y);
	}
	
	/**
	 * @see IFigure#translateFromParent(Translatable)
	 */
	public void translateFromParent(Translatable t) {
		t.performTranslate(
				getHorizontalRangeModel().getValue(),
				getVerticalRangeModel().getValue()
			);			
		super.translateFromParent(t);
	}

	/**
	 * @see IFigure#translateToParent(Translatable)
	 */
	public void translateToParent(Translatable t) {
		t.performTranslate(
				-getHorizontalRangeModel().getValue(),
				-getVerticalRangeModel().getValue()
			);
		super.translateToParent(t);
	}


}
