package com.odcgroup.page.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * Utility constants.
 * 
 * @author Gary Hayes
 */
public class PageUIConstants {
	/** The color String used to highlight Widgets. */
	public static final String HIGHLIGHT_COLOR = "HIGHLIGHT_COLOR"; 
	static {
	    PageUIPlugin.setColorInRegistry(HIGHLIGHT_COLOR, new RGB(250, 190, 190));
	}
	/** The default icon. */
	public static final String DEFAULT_ICON = "icons/obj16/default.png";
	
	/** The contributer id of the Tabbed Property Sheet. */
	public static final String TABBED_PROPERTY_SHEET_CONTRIBUTER_ID = "com.odcgroup.page.ui.pageDesignerEditor";
	
	/** the HIGHLIGHTED color **/
	public static Color getHighLightColor(){
	   return PageUIPlugin.getColor(HIGHLIGHT_COLOR);
	}
}
