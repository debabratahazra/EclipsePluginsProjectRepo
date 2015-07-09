package com.odcgroup.page.ui.util;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;

/**
 * Utility methods for interacting with SWT.
 * 
 * @author Gary Hayes
 */
public class SWTUtils {

	/**
	 * Calculates the size that a String is going to take up using the default font.
	 * 
	 * @param font
	 *            The Font
	 * @param str
	 *            The String to calculate the size for
	 * @return Rectangle The Rectangle. Note that we return a draw2D rectangle since
	 */
	public static Rectangle calculateSize(Font font, String str) {
		Rectangle b = new Rectangle(0, 0, 0, 0);
		if (StringUtils.isEmpty(str)) {
			return b;
		}

		TextLayout tl = null;
		try {
			tl = new TextLayout(font.getDevice());
			tl.setText(str);
			tl.setFont(font);
			return tl.getBounds();
		} finally {
			if (tl != null) {
				tl.dispose();
			}
		}
	}
}
