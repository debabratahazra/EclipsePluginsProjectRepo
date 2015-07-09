package com.odcgroup.page.ui.figure;

import java.util.List;

import org.eclipse.core.runtime.Assert;

/**
 * The base class for BoxTypes.
 *
 * @author Gary Hayes
 */
abstract public class AbstractBoxType implements BoxType {

	/** The BoxFigure. */
	private BoxFigure box;
	
	/**
	 * @return the wrapped box figure
	 */
	protected final BoxFigure getBoxFigure() {
		return this.box;
	}
	
	/**
	 * Creates a new HorizontalBoxType.
	 * 
	 * @param box The BoxFigure that this is the type for
	 */
	public AbstractBoxType(BoxFigure box) {
		Assert.isNotNull(box);
		this.box = box;
	}
	
	/**
	 * Gets the width of the box in pixels.
	 * 
	 * @return int The width of the box in pixels
	 */
	protected int getPixelWidth() {
		return box.getPixelWidth();
	}
	
	/**
	 * Gets the width of the box
	 * @return String The width of the box
	 */
	protected String getWidth() {
		return box.getWidth();
	}
	/**
	 * Gets the width of the box. Based on the percentage.
	 * @return int
	 */
	protected int getBoxBoundsWidth() {
		return box.getBoxBoundsWidth();
	}
	/**
	 * Gets the height of the box.
	 * 
	 * @return int The height of the box
	 */
	protected int getHeight() {
		return box.getHeight();
	}	
	
	/**
	 * Gets the children of the Box.
	 * 
	 * @return List The children of the box
	 */
	protected List getChildren() {
		return box.getChildren();
	}
	
	 /**
	  * Gets the FigureContext.
	  * 
	  * @return FigureContext
	  */
	 protected FigureContext getFigureContext() {
		 return box.getFigureContext();
	 }	
	
	 /**
	  * Gets the FigureConstants.
	  * 
	  * @return FigureConstants
	  */
	 protected FigureConstants getFigureConstants() {
		 return box.getFigureConstants();
	 }		
}
