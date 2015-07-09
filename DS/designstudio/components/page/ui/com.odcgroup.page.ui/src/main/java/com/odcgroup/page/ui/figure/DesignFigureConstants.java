package com.odcgroup.page.ui.figure;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.uimodel.RendererInfo;

/**
 * The FigureConstants when we are in design mode.
 * 
 * @author Gary Hayes
 */
public class DesignFigureConstants extends AbstractFigureConstants {
	
	/** The Color used to draw password fields. */
	private static final String PASSWORD_LINE_COLOR = "PASSWORD_LINE_COLOR";	
	
	/** 
	 * @return <code>True</code> if this instance is used in designed mode, otherwise it
	 *         must returns <code>False</code> 
	 */
	protected final boolean isDesignMode() {
		return true;
	}
	
	/**
	 * Initializes the constants given the rendering info descriptor.
	 * 
	 * @param ri the rendering info
	 */
	public DesignFigureConstants(RendererInfo ri) {
		super(ri);
	}	
	/**
	 * initialize the colors.
	 */
	static{
	    PageUIPlugin.setColorInRegistry(PASSWORD_LINE_COLOR, new RGB(0,0,188));
	}
	/**
	 * Gets the Color used to draw password fields.
	 * 
	 * @return Color The Color used to draw password fields
	 */
	public Color getPasswordLineColor() {
		return PageUIPlugin.getColor(PASSWORD_LINE_COLOR);
	}		
}
