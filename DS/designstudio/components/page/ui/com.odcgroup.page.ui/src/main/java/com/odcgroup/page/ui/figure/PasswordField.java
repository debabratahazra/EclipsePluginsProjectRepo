package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;
/**
 * A PasswordField presents a password textfield in a user-interface.
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class PasswordField extends AbstractAlignableWidgetFigure {


	/**
	 * Creates a new PasswordField.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public PasswordField(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Gets the minimum width of the figure. 
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				return Math.max((int)(getColumns() * getFigureConstants().getColumnScalingFactor()), PaintUtils.getWidth(getWidget()));
			}
			root = root.getParent();
		}
		return (int)(getColumns() * getFigureConstants().getColumnScalingFactor()) + PaintUtils.getWidth(getWidget());
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		int result = getFigureConstants().getSimpleWidgetDefaultHeight();
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				result += PaintUtils.IMAGE_HEIGHT;
				break;
			}
			root = root.getParent();
		}
		return result;
	}	
	
	/**
	 * Paints the PasswordField.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		Widget root = getWidget().getParent();
		int y = 0;
		int x = 0;
		if(PaintUtils.isWidgethAlignLead(getWidget())){
		    PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - PaintUtils.getWidth(getWidget()));
		}else {
		    x=PaintUtils.paintIcons(getWidget(), graphics,0);
		}
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && x > 0) {
				y = PaintUtils.IMAGE_HEIGHT;
				x = 0;
				break;
			}
			root = root.getParent();
		}
		
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getPasswordLineColor());
		graphics.setLineStyle(fc.getFieldLineStyle());

		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		Rectangle b = getBounds();
		graphics.drawRectangle(new Rectangle(x, y, b.width - (x + 1) , b.height - 1));
		
		graphics.setForegroundColor(fc.getFieldColor());
		
		// The 3 serves to center the text in the rectangle
		String pv = getPreviewValue();
		String s = createStarString(pv.length());
		graphics.drawText(s, x+1, 3+y);	
	}
	
	/**
	 * Creates a String containing '*''s.
	 * 
	 * @param number The number of '*'^s to put
	 * @return String The String
	 */
	private String createStarString(int number) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < number;i++) {
			buffer.append('*');
		}
		return buffer.toString();
	}
	
	/**
	 * Gets the width of the element in visible characters.
	 * 
	 * @return int The number of columns.
	 */
	public int getColumns() {
		return getInt(PropertyTypeConstants.COLUMNS);
	}
}
