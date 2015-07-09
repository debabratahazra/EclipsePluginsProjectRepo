package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.uimodel.RendererInfo;

/**
 * The base class for the FigureConstants. This contains all the constants
 * which are independent of which mode we are in.
 * 
 * @author Gary Hayes
 * @author Alain Tripod, added dependency with RendererInfo
 * @version 1.1
 */
abstract public class AbstractFigureConstants implements FigureConstants {
	
	/** The Color used to draw the Border of Boxes. */
	private static final Color BOX_BORDER_COLOR = ColorConstants.gray;
	
	/** The background color of a FileChooser button. */
	private static final String FILE_CHOOSER_BUTTON_BACKGROUND_COLOR = "FILE_CHOOSER_BUTTON_BACKGROUND_COLOR";
	
	/** The background color of a Caption. */
	private static final String CAPTION_BACKGROUND_COLOR = "CAPTION_BACKGROUND_COLOR";
	
	/** The background color of a Rich Text Area. */
	private static final String RICH_TEXTAREA_BACKGROUND_COLOR ="RICH_TEXTAREA_BACKGROUND_COLOR" ;
	
	/** The Font of a Caption. */
	private static final Font CAPTION_FONT = new Font(null, "Arial", 8, SWT.BOLD);
	
	/** The Font of a Caption. */
	private static final Font CAPTION_FONT_ITALIC = new Font(null, "Arial", 8, SWT.BOLD|SWT.ITALIC);

	/** The background color of a Column heading in a Table. */
	private static final String COLUMN_BACKGROUND_COLOR ="COLUMN_BACKGROUND_COLOR";
	
	/** The background color of a Module Header. */
	private static final String MODULE_HEADER_BACKGROUND_COLOR = "MODULE_HEADER_BACKGROUND_COLOR";	
	
	/** The background color of a Tree heading in a Table. */
	private static final String TREE_BACKGROUND_COLOR = "TREE_BACKGROUND_COLOR"; 	
	
	/** The Color used to draw the Rectangle of Fields. */
	private static final Color FIELD_COLOR = ColorConstants.black;	
	
	/** The line style used to draw the Rectangle of Fields. */
	private static final int FIELD_LINE_STYLE = SWT.LINE_SOLID;
	
	/** The Color to use for hidden features (code etc.) */
	private static final Color HIDDEN_FEATURES_COLOR = ColorConstants.red;
	
	/** The color used to present the border of Widgets for development purposes. */
	private static final Color AGGREGATION_BORDER_FOREGROUND_COLOR = ColorConstants.darkGreen;
	
	/** The line style used to present the border of Widgets for development purposes. */
	private static final int AGGREGATION_BORDER_LINE_STYLE = SWT.LINE_DOT;
	
	/** The line width used to present the border of Widgets for development purposes. */
	private static final int AGGREGATION_BORDER_LINE_WIDTH = 1;	
	
	/** The color used to present the border of Widgets for development purposes. */
	private static final Color OUTLINE_BORDER_FOREGROUND_COLOR = ColorConstants.lightGray;
	
	/** The line style used to present the border of Widgets for development purposes. */
	private static final int OUTLINE_BORDER_LINE_STYLE = SWT.LINE_DOT;
	
	/** The line width used to present the border of Widgets for development purposes. */
	private static final int OUTLINE_BORDER_LINE_WIDTH = 1;	
	
	/** The scaling factor used to calculate width. */
	private static final double SCALING_FACTOR_COLUMNS = 5;	
	
	/** The scaling factor used to calculate width of the textarea. */
	private static final double TEXTAREA_COLUMN_SCALING_FACTOR = 4.8;
	
	/** The scaling factor used to calculate height*/
	private static final int SCALING_FACTOR_ROWS = 15;
	
	/** The background color of the ScrollBar. */
	private static final Color SCROLL_BAR_BACKGROUND_COLOR = ColorConstants.button;
	
	/** The background color of the ScrollBar's thumb. */
	private static final Color SCROLL_BAR_THUMB_BACKGROUND_COLOR = ColorConstants.gray;
	
	/** The default height of simple widgets. */
	private static final int SIMPLE_WIDGET_DEFAULT_HEIGHT = 16;
	
	/** The default width of simple widgets. */
	private static final int SIMPLE_WIDGET_DEFAULT_WIDTH = 16;	
	
	/** The line width of a Tabbed Pane. */
	private static final int TABBED_PANE_LINE_WIDTH = 2;
	
	/** The color used to present the border of Tables. */
	private static final Color TABLE_BORDER_FOREGROUND_COLOR = ColorConstants.lightGray;	
	
	/** The line style used to present the border of Tables. */
	private static final int TABLE_BORDER_LINE_STYLE = SWT.LINE_SOLID;
	
	/** The line width used to present the border of Tables. */
	private static final int TABLE_BORDER_LINE_WIDTH = 1;	
	
	/** The Color of even Rows in a Table. */
	private static final String TABLE_EVEN_ROW_BACKGROUND_COLOR = "TABLE_EVEN_ROW_BACKGROUND_COLOR";	
	
	/** The Color of odd Rows in a Table. */
	private static final String TABLE_ODD_ROW_BACKGROUND_COLOR = "TABLE_ODD_ROW_BACKGROUND_COLOR";

	/** The default spacing between Widgets. */
	private static final int WIDGET_DEFAULT_SPACING = 4;	
	
	/** The size of a feature that is not normally visible. */
	private static final int INVISIBLE_WIDGET_SIZE = 8;
	
	/** The default spacing between Widgets. */
	private int widgetSpacing = WIDGET_DEFAULT_SPACING;

	/** The background color of a Rendering figure. */
	private static final String RENDERING_BACKGROUND_COLOR = "RENDERING_BACKGROUND_COLOR"; 
	
	
	/** 
	 * @return <code>True</code> if this instance is used in designed mode, otherwise it
	 *         must returns <code>False</code> 
	 */
	protected abstract boolean isDesignMode();
	
	/** 
	 * @return <code>False</code> if this instance is used in preview mode, otherwise it
	 *         must returns <code>True</code> 
	 */
	private final boolean isPreviewMode() {
		return ! isDesignMode();
	}

	/**
	 * Gets the Color used to draw the border of boxes.
	 * 
	 * @return Color The Color used to draw the border of boxes
	 */
	public Color getBoxBorderColor() {
		return BOX_BORDER_COLOR;
	}	
	
	/**
	 * Gets the background color of the FileChooser button.
	 * 
	 * @return Color The background color of the FileChooser button.
	 */
	public Color getFileChooserButtonBackgroundColor() {
		return PageUIPlugin.getColor(FILE_CHOOSER_BUTTON_BACKGROUND_COLOR);
	}
	
	/**
	 * Gets the background color to use for the Caption.
	 * 
	 * @return Color The background color to use for the Caption
	 */
	public Color getCaptionBackgroundColor() {
		return PageUIPlugin.getColor(CAPTION_BACKGROUND_COLOR);
	}
	
	/**
	 * Gets the background color to use for the Rich Text Area.
	 * 
	 * @return Color The background color to use for the Rich Text Area
	 */
	public Color getRichTextAreaBackgroundColor() {
		return PageUIPlugin.getColor(RICH_TEXTAREA_BACKGROUND_COLOR);
	}
	
	/**
	 * Gets the Font to use for the Caption.
	 * @param italic true for the italic 
	 * @return Font The Font to use for the Caption
	 */
	public Font getCaptionFont(boolean italic) {
		return (italic) ? CAPTION_FONT_ITALIC : CAPTION_FONT;
	}
	
	/**
	 * Gets the background color to use for the Column heading.
	 * 
	 * @return Color The background color to use for the Column heading
	 */
	public Color getColumnBackgroundColor() {
		return PageUIPlugin.getColor(COLUMN_BACKGROUND_COLOR);
	}
	
	/**
	 * Gets the scaling factor used to calculate width.
	 * 
	 * @return double The scaling factor used to calculate width.
	 */
	public double getColumnScalingFactor() {
		return SCALING_FACTOR_COLUMNS;
	}
	/**¨
	 * Gets the scaling factor used to calculate the width
	 * of the textarea component.
	 * 
	 * @return double The scaling factor used to calculate width.
	 */
	public double getTextAreaColumnScalingFactor() {
		return TEXTAREA_COLUMN_SCALING_FACTOR;
	}
	
	/**
	 * Gets the Color used to draw fields.
	 * 
	 * @return Color The Color used to draw fields
	 */
	public Color getFieldColor() {
		return FIELD_COLOR;
	}	
	
	/**
	 * Gets the line style used to draw fields.
	 * 
	 * @return int The line style used to draw fields
	 */
	public int getFieldLineStyle() {
		return FIELD_LINE_STYLE;
	}
	
	/**
	 * Gets the hidden features color.
	 * 
	 * @return Color The hidden features color
	 */
	public Color getHiddenFeaturesColor() {
		return HIDDEN_FEATURES_COLOR;
	}	
	
	/**
	 * The color to use for drawing a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return Color The aggregation border foreground color
	 */
	public Color getAggregationBorderForegroundColor() {
		return AGGREGATION_BORDER_FOREGROUND_COLOR;
	}
	
	/**
	 * Gets the line style used to draw a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return int The aggregation border line style
	 */
	public int getAggregationBorderLineStyle() {
		return AGGREGATION_BORDER_LINE_STYLE;
	}
	
	/**
	 * Gets the line width used to draw a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return int The aggregation border line width
	 */
	public int getAggregationBorderLineWidth() {
		return AGGREGATION_BORDER_LINE_WIDTH;
	}
	
	/**
	 * The color to use for drawing a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return Color The outline border foreground color
	 */
	public Color getOutlineBorderForegroundColor() {
		return OUTLINE_BORDER_FOREGROUND_COLOR;
	}
	
	/**
	 * Gets the line style used to draw a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return int The outline border line style
	 */
	public int getOutlineBorderLineStyle() {
		return OUTLINE_BORDER_LINE_STYLE;
	}
	
	/**
	 * Gets the line width used to draw a border around an item where the border will NOT
	 * be part of the final HTML. In other words this border is used for development
	 * purposes.
	 * 
	 * @return int The outline border line width
	 */
	public int getOutlineBorderLineWidth() {
		return OUTLINE_BORDER_LINE_WIDTH;
	}
	/**
	 * Gets the scaling factor used to calculate height.
	 * @return int The scaling factor used to calculate height.
	 */
	public int getRowScalingFactor() {
		return SCALING_FACTOR_ROWS;
	}
	/**
	 * Gets the background color of the ScrollBar.
	 * 
	 * @return Color The background color of the ScrollBar.
	 */
	public Color getScrollBarBackgroundColor() {
		return SCROLL_BAR_BACKGROUND_COLOR;
	}
	
	/**
	 * Gets the background color of the ScrollBar's thumb.
	 * 
	 * @return Color The background color of the ScrollBar's thumb.
	 */
	public Color getScrollBarThumbBackgroundColor() {
		return SCROLL_BAR_THUMB_BACKGROUND_COLOR;
	}	
	
	/**
	 * The height of simple Widgets (those which do not contain other Widgets).
	 * Using the same value everywhere this makes the alignment of Widgets easier.
	 * 
	 * @return int The height of simple Widgets
	 */
	public int getSimpleWidgetDefaultHeight() {
		return SIMPLE_WIDGET_DEFAULT_HEIGHT;
	}	
	
	/**
	 * The width of simple Widgets (those which do not contain other Widgets).
	 * Using the same value everywhere this makes the alignment of Widgets easier.
	 * 
	 * @return int The width of simple Widgets
	 */
	public int getSimpleWidgetDefaultWidth() {
		return SIMPLE_WIDGET_DEFAULT_WIDTH;
	}	
	
	/**
	 * Gets the line width to use for the bottom of a TabbedPane.
	 * 
	 * @return int The line width to use for the bottom of a TabbedPane
	 */
	public int getTabbedPaneLineWidth() {
		return TABBED_PANE_LINE_WIDTH;
	}
	
	/**
	 * The color to use for drawing a border around a Table.
	 * 
	 * @return Color The table border foreground color
	 */
	public Color getTableBorderForegroundColor() {
		return TABLE_BORDER_FOREGROUND_COLOR;
	}
	
	/**
	 * The background color for even Rows in a table.
	 * 
	 * @return Color The background color for even Rows in a table
	 */
	public Color getTableEvenRowBackgroundColor() {
		return PageUIPlugin.getColor(TABLE_EVEN_ROW_BACKGROUND_COLOR);
	}
	
	/**
	 * The background color for odd Rows in a table.
	 * 
	 * @return Color The background color for odd Rows in a table
	 */
	public Color getTableOddRowBackgroundColor() {
		return PageUIPlugin.getColor(TABLE_ODD_ROW_BACKGROUND_COLOR);
	}	
	
	/**
	 * Gets the line style used to draw a border around a Table.
	 * 
	 * @return int The table border line style
	 */
	public int getTableBorderLineStyle() {
		return TABLE_BORDER_LINE_STYLE;
	}
	
	/**
	 * Gets the line width used to draw a border around a Table.
	 * 
	 * @return int The table border line width
	 */
	public int getTableBorderLineWidth() {
		return TABLE_BORDER_LINE_WIDTH;
	}
			
	/**
	 * Gets the spacing to use between each Widget.
	 * 
	 * @return int The spacing to use between each Widget
	 */
	public int getWidgetSpacing() {
		return widgetSpacing;
	}		
	
	/**
	 * Gets the size of a feature that is not normally visible.
	 * 
	 * @return int The size of a feature that is not normally visible
	 */
	public int getInvisibleWidgetSize() {
		return INVISIBLE_WIDGET_SIZE;
	}
	
	/**
	 * Initializes the constants given the rendering info descriptor
	 * @param ri the rendering info
	 */
	public AbstractFigureConstants(RendererInfo ri) {
		if (isPreviewMode()) {
			widgetSpacing = ri.getPreviewWidgetSpacing();
		} else {
			widgetSpacing = ri.getDesignWidgetSpacing();
		}
		
	}
	
	/**
	 * initialze the color in to the color registry.
	 */
	static{
	    PageUIPlugin.setColorInRegistry(FILE_CHOOSER_BUTTON_BACKGROUND_COLOR, new RGB(253, 243, 219));
	    PageUIPlugin.setColorInRegistry(CAPTION_BACKGROUND_COLOR, new RGB(210, 210, 210));
	    PageUIPlugin.setColorInRegistry(RICH_TEXTAREA_BACKGROUND_COLOR, new RGB(209, 209, 209));
	    PageUIPlugin.setColorInRegistry(COLUMN_BACKGROUND_COLOR, new RGB(192, 195, 222));
	    PageUIPlugin.setColorInRegistry(MODULE_HEADER_BACKGROUND_COLOR, new RGB(192, 195, 222));
	    PageUIPlugin.setColorInRegistry(TREE_BACKGROUND_COLOR,new RGB(157, 162, 204));
	    PageUIPlugin.setColorInRegistry(TABLE_EVEN_ROW_BACKGROUND_COLOR,new RGB(242, 242, 242));
	    PageUIPlugin.setColorInRegistry(TABLE_ODD_ROW_BACKGROUND_COLOR,new RGB(209, 209, 209));
	    PageUIPlugin.setColorInRegistry(RENDERING_BACKGROUND_COLOR,new RGB(242, 242, 242));	
	    
	    
	}
	/**
	 * Gets the background color to use for the Tree heading.
	 * 
	 * @return Color The background color to use for the Tree heading
	 */
	public Color getTreeBackgroundColor() {
		return PageUIPlugin.getColor(TREE_BACKGROUND_COLOR);
	}

	/**
	 * Gets the color of the module header.
	 * 
	 * @return Color
	 */
	public Color getModuleHeaderBackgroundColor() {
		return PageUIPlugin.getColor(MODULE_HEADER_BACKGROUND_COLOR);
	}
	
	/**
	 * Gets the background color of the Rendering figure.
	 * 
	 * @return Color
	 */
	public Color getRenderingBackgroundColor() {
		return PageUIPlugin.getColor(RENDERING_BACKGROUND_COLOR);
	}
}