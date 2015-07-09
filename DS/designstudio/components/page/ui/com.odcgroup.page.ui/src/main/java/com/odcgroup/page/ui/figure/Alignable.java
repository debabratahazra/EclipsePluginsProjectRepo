package com.odcgroup.page.ui.figure;

/**
 * Figures which are alignable can be aligned horizontally and vertically.
 * 
 * @author Gary Hayes
 */
public interface Alignable {
	
	/** A choice for both the horizontal and vertical alignments. */
	public static final String LEAD = "lead";
	
	/** A choice for both the horizontal and vertical alignments. */
	public static final String TRAIL = "trail";
	
	/** A choice for both the horizontal and vertical alignments. */
	public static final String CENTER = "center";
	
	/**
	 * Gets the horizontal alignment.
	 * 
	 * @return String The horizontal alignment
	 */
	public String getHorizontalAlignment();

	/**
	 * Gets the vertical alignment.
	 * 
	 * @return String The vertical alignment
	 */
	public String getVerticalAlignment();
}