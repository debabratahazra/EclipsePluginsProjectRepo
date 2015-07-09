package com.odcgroup.page.ui.figure.table;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.AbstractAlignableWidgetFigure;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * This class represent the TableGroup figure
 * 
 */
public class TableGroupFigure extends AbstractAlignableWidgetFigure {
	/** The minimum size of the Label. */
	private final static int MIN_SIZE = 60;
	
	String GROUP_HIERARCHY = "hierarchy";
	String GROUP_DATA_AGGR = "aggregateData";

	/**
	 * Creates a new Label.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the
	 *            page correctly
	 */
	public TableGroupFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	/**
	 * Method called when a Preference is changed in Eclipse. The default
	 * version does nothing.
	 */
	public void preferenceChange() {
		revalidate();
	}

	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		String text = getText();
		Property prop = getProperty(GROUP_HIERARCHY);
		if(prop.getBooleanValue()) {
			text += " (H";
			Property raw = getProperty(GROUP_DATA_AGGR);
			if(raw != null && raw.getValue().equals("false")) {
				text += "-Raw)";
			} else {
				text += ")";
			}
		}
		
		Double d = calculateTextSize(text).width * 1.08; //bold font
		int minWidth = d.intValue();
		if (text.length() == 0) {
			minWidth = MIN_SIZE;
		}
		int childWidth = 0;
		if(getChildren().size() > 0) {
			for(int i=0;i<getChildren().size();i++) {
				IWidgetFigure figure = (IWidgetFigure)getChildren().get(i);
				childWidth += figure.getMinWidth();
			}
		}
		minWidth += childWidth;
		return minWidth + 16 + PaintUtils.getWidth(getWidget());
	}

	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		return 16;
	}
	
	@Override
	protected void paintChildren(Graphics graphics) {
		IFigure child;
       	for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure) getChildren().get(i);
			if (child.isVisible()) {
				Rectangle bounds = child.getBounds();
				graphics.clipRect(bounds);
				child.paint(graphics);
				graphics.restoreState();
			}		
		}		
	}
	
	/**
	 * Paints the Label.
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		int offset = 0;
		if(getChildren().size() > 0) {
			IWidgetFigure figure = (IWidgetFigure)getChildren().get(0);
			offset += figure.getMinWidth() + 5;
		}
		int x=0;
		PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - PaintUtils.getWidth(getWidget()));
		x += PaintUtils.paintPlus(offset, getWidget(), graphics);
		String text = getText();
		graphics.setForegroundColor(getTextForegroundColor());
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		if(getChildren().size() > 0) {
			x += ((IWidgetFigure)getChildren().get(0)).getMinWidth() + 8;
		}
		
		Property prop = getProperty(GROUP_HIERARCHY);
		if(prop.getBooleanValue()) {
			text += " (H";
			Property raw = getProperty(GROUP_DATA_AGGR);
			if(raw != null &&  raw.getValue().equals("false")) {
				text += "-Raw)";
			} else {
				text += ")";
			}
		}
		graphics.drawText(text, x, 0);
	}	
	

	@Override
	protected String getText() {
		String val = getWidget().getPropertyValue(ITableGroup.GROUP_COLUMN_NAME_PROPERTY);
		if (StringUtils.isEmpty(val)) {
			val = "Table Group";
		}
		ITableGroup group = TableHelper.getTableGroup(getWidget());
		if (group.isUsedForDynamicColumn()) {
			val += "_(Dyn)_";
		}
		return val;
	}

	/**
	 * Reset the image to null if the icon has changed.
	 * 
	 * @param name
	 *            The property name
	 */
	public void afterPropertyChange(String name) {
		if (name.equals(PropertyTypeConstants.HORIZONTAL_ALIGNMENT)) {
			revalidate();
		}
	}
}