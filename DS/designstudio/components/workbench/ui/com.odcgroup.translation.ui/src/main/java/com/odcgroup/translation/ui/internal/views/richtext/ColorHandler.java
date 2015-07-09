package com.odcgroup.translation.ui.internal.views.richtext;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;


abstract class ColorHandler extends StyledTextHandler {
	
	private Set<Color> colors;
	
	protected synchronized Color createColor(RGB rgb) {
		// check if this color has already been created
		for (Color c : colors) {
			if (c.getRed() == rgb.red && c.getGreen() == rgb.green && c.getBlue() == rgb.blue) {
				return c;
			}
		}
		
		// create a new color
		Color c = new Color(getText().getShell().getDisplay(), rgb);
		colors.add(c);
		return c;
	}
	
	/*
	 * Set the foreground color for the selected text.
	 */
	protected void setColor(Color color, boolean foreground) {
		Point sel = getText().getSelectionRange();
		if ((sel == null) || (sel.y == 0))
			return;
		StyleRange style, range;
		for (int i = sel.x; i < sel.x + sel.y; i++) {
			range = getText().getStyleRangeAtOffset(i);
			if (range != null) {
				style = (StyleRange) range.clone();
				style.start = i;
				style.length = 1;
				if (foreground) {
					style.foreground = color;
				} else {
					style.background = color;
				}
			} else if (foreground) {
				style = new StyleRange(i, 1, color, null, SWT.NORMAL);
			} else {
				style = new StyleRange(i, 1, null, color, SWT.NORMAL);
			}
			getText().setStyleRange(style);
		}
		getText().setSelectionRange(sel.x + sel.y, 0);
	}
	
	@Override
	public boolean isStyleSelected(boolean selected) {
		return false;
	}

	public ColorHandler(StyledText text, Set<Color> colors) {
		super(text);
		this.colors = colors;
	}

}
