package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;

import com.odcgroup.page.uimodel.RendererInfo;

/**
 * The FigureConstants when we are in preview mode.
 * 
 * @author Gary Hayes
 */
public class PreviewFigureConstants extends AbstractFigureConstants {

	/** The Color used to draw password fields. */
	private static final Color PASSWORD_LINE_COLOR = ColorConstants.black;	
	
	/** 
	 * @return <code>True</code> if this instance is used in designed mode, otherwise it
	 *         must returns <code>False</code> 
	 */
	protected final boolean isDesignMode() {
		return false;
	}
	
	/**
	 * Initializes the constants given the rendering info descriptor
	 * @param ri the rendering info
	 */
	public PreviewFigureConstants(RendererInfo ri) {
		super(ri);
	}

	/**
	 * Gets the Color used to draw password fields.
	 * 
	 * @return Color The Color used to draw password fields
	 */
	public Color getPasswordLineColor() {
		return PASSWORD_LINE_COLOR;
	}
}
