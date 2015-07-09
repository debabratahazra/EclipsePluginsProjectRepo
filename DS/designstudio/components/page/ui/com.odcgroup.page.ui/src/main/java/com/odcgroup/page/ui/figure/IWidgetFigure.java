package com.odcgroup.page.ui.figure;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Color;

import com.odcgroup.page.model.Widget;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public interface IWidgetFigure extends IFigure {

	/**
	 * Set the figure's text.
	 * <p>
	 * The value <code>null</code> clears it.
	 * 
	 * @param text
	 *            text the text to be displayed in the figure or null
	 */
	void setText(String text);
	
	/**
	 * @param color
	 */
	void setTextForegroundColor(Color color);

	/**
	 * Set the figure's toolTip.
	 * <p>
	 * The value <code>null</code> clears it.
	 * 
	 * @param toolTip
	 *            toolTip the toolTip to be displayed in the figure or null
	 */
	void setToolTip(String toolTip);
	
	/**
	 * 
	 */
	void dispose();

	/**
	 * 
	 */
	void resourceChange();

	/**
	 * 
	 */
	void preferenceChange();

	/**
	 * @param propName
	 */
	void notifyPropertyChange(String propName);
	
	/**
	 * @return Widget
	 */
	Widget getWidget();

	/**
	 * @return the minimum width of this figure
	 */
	int getMinWidth();

	/**
	 * @return the maximum width of this figure
	 */
	int getMaxWidth();


	/**
	 * @return the minimum height of this figure
	 */
	int getMinHeight();

	/**
	 * @return the maximum height of this figure
	 */
	int getMaxHeight();
	
	/**
	 * @return FigureContext
	 */
	FigureContext getFigureContext();

	/**
	 * @return
	 */
	List<WidgetFigureDecorator> getDecorators();

	/**
	 * @return
	 */
	boolean isVisualElement();

	/**
	 * @return
	 */
	List<IWidgetFigure> getAllChildren();

	/**
	 * @param name
	 */
	void afterPropertyChange(String name);
	
//	void notifyChange();
}
