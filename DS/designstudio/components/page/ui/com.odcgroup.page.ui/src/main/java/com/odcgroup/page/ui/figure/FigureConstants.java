package com.odcgroup.page.ui.figure;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/**
 * Accessor to constants related to figures. These can be externalised in a
 * Graphical Chart.
 * 
 * @author Gary Hayes
 */
public interface FigureConstants {
	
	/**
	 * Gets the Color used to draw the border of boxes.
	 * 
	 * @return Color The Color used to draw the border of boxes
	 */
	public Color getBoxBorderColor();	
	
	/**
	 * Gets the background color of the FileChooser button.
	 * 
	 * @return Color The background color of the FileChooser button.
	 */
	public Color getFileChooserButtonBackgroundColor();
	
	/**
	 * Gets the background color to use for the Caption.
	 * 
	 * @return Color The background color to use for the Caption
	 */
	public Color getCaptionBackgroundColor();	

	/**
	 * Gets the background color to use for the Rich Text Area.
	 * 
	 * @return Color The background color to use for the Rich Text Area
	 */
	public Color getRichTextAreaBackgroundColor();	
	
	/**
	 * Gets the Font to use for the Caption.
	 * 
	 * @return Font The Font to use for the Caption
	 */
	public Font getCaptionFont(boolean italic);	
	
	/**
	 * Gets the background color to use for the Column heading.
	 * 
	 * @return Color The background color to use for the Column heading
	 */
	public Color getColumnBackgroundColor();
	
	/**
	 * Gets the background color to use for the Tree heading.
	 * 
	 * @return Color The background color to use for the Tree heading
	 */
	public Color getTreeBackgroundColor();	
	
	/**
	 * Gets the scaling factor used to calculate width.
	 * 
	 * @return double The scaling factor used to calculate width.
	 */
	public double getColumnScalingFactor();
	
	/**
	 * Gets the scaling factor used to calculate width.
	 * 
	 * @return double The scaling factor used to calculate width.
	 */
	public double getTextAreaColumnScalingFactor();

	/**
	 * Gets the Color used to draw fields.
	 * 
	 * @return Color The Color used to draw fields
	 */
	public Color getFieldColor();	
	
	/**
	 * Gets the line style used to draw fields.
	 * 
	 * @return int The line style used to draw fields
	 */
	public int getFieldLineStyle();
	
	/**
	 * Gets the hidden features color.
	 * 
	 * @return Color The hidden features color
	 */
	public Color getHiddenFeaturesColor();
	
	/**
	 * The color to use for drawing a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return Color The aggregation border foreground color
	 */
	public Color getAggregationBorderForegroundColor();
	
	/**
	 * Gets the line style used to draw a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return int The aggregation border line style
	 */
	public int getAggregationBorderLineStyle();	
	
	/**
	 * Gets the line width used to draw a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return int The aggregation border line width
	 */
	public int getAggregationBorderLineWidth();	
	
	/**
	 * The color to use for drawing a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return Color The outline border foreground color
	 */
	public Color getOutlineBorderForegroundColor();
	
	/**
	 * Gets the line style used to draw a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return int The outline border line style
	 */
	public int getOutlineBorderLineStyle();	
	
	/**
	 * Gets the line width used to draw a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return int The outline border line width
	 */
	public int getOutlineBorderLineWidth();
	
	/**
	 * Gets the scaling factor used to calculate height.
	 * @return int The scaling factor used to calculate height.
	 */
	public int getRowScalingFactor();
	
	/**
	 * Gets the background color of the ScrollBar.
	 * 
	 * @return Color The background color of the ScrollBar.
	 */
	public Color getScrollBarBackgroundColor();
	
	/**
	 * Gets the background color of the ScrollBar's thumb.
	 * 
	 * @return Color The background color of the ScrollBar's thumb.
	 */
	public Color getScrollBarThumbBackgroundColor();	
	
	/**
	 * The height of simple Widgets (those which do not contain other Widgets).
	 * Using the same value everywhere this makes the alignment of Widgets easier.
	 * 
	 * @return int The height of simple Widgets
	 */
	public int getSimpleWidgetDefaultHeight();
	
	/**
	 * The width of simple Widgets (those which do not contain other Widgets).
	 * Using the same value everywhere this makes the alignment of Widgets easier.
	 * 
	 * @return int The width of simple Widgets
	 */
	public int getSimpleWidgetDefaultWidth();
	
	/**
	 * Gets the line width to use for the bottom of a TabbedPane.
	 * 
	 * @return int The line width to use for the bottom of a TabbedPane
	 */
	public int getTabbedPaneLineWidth();
	
	/**
	 * The color to use for drawing a border around a Table.
	 * 
	 * @return Color The table border foreground color
	 */
	public Color getTableBorderForegroundColor();
	
	/**
	 * The background color for even Rows in a table.
	 * 
	 * @return Color The background color for even Rows in a table
	 */
	public Color getTableEvenRowBackgroundColor();
	
	/**
	 * The background color for odd Rows in a table.
	 * 
	 * @return Color The background color for odd Rows in a table
	 */
	public Color getTableOddRowBackgroundColor();
	
	/**
	 * Gets the line style used to draw a border around a Table.
	 * 
	 * @return int The table border line style
	 */
	public int getTableBorderLineStyle();
	
	/**
	 * Gets the line width used to draw a border around a Table.
	 * 
	 * @return int The table border line width
	 */
	public int getTableBorderLineWidth();
	
	/**
	 * Gets the spacing to use between each Widget.
	 * 
	 * @return int The spacing to use between each Widget
	 */
	public int getWidgetSpacing();	
	
	/**
	 * Gets the size of a feature that is not normally visible.
	 * 
	 * @return int The size of a feature that is not normally visible
	 */
	public int getInvisibleWidgetSize();
	
	/**
	 * Gets the color of the module header.
	 * 
	 * @return Color
	 */
	public Color getModuleHeaderBackgroundColor();
	
	/**
	 * Gets the background color of the Rendering figure.
	 * 
	 * @return Color
	 */
	public Color getRenderingBackgroundColor();
	
	/**
	 * Gets the Color used to draw password fields.
	 * 
	 * @return Color The Color used to draw password fields
	 */
	public Color getPasswordLineColor();	
}
