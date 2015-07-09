package com.odcgroup.t24.enquiry.figure.tab;

import org.eclipse.draw2d.Clickable;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

/**
 *
 * @author phanikumark
 *
 */
public class TabFigure extends Clickable {
	
	private String text;
	
	public TabFigure(String text) {
		super();
		this.text = text;
	}
	
	protected void paintBorder(Graphics graphics) {
		setOpaque(false);
		Rectangle area = getClientArea();
		graphics.fillRectangle(area);
		Font font = new Font(graphics.getFont().getDevice(), "Tahoma", 9, SWT.BOLD);
        graphics.setFont(font);
		graphics.drawText(text, area.x + 12, 14);
		graphics.drawFocus(area.x, area.y, area.width, area.height);
		area.width = area.width - 1;
		graphics.drawRectangle(area);
	}
	
	protected void paintClientArea(Graphics graphics) {
		super.paintClientArea(graphics);
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	
}
