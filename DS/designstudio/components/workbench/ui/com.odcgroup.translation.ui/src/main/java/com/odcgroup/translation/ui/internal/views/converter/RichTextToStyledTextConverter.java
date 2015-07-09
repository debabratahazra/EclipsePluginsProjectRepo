package com.odcgroup.translation.ui.internal.views.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.translation.core.richtext.IColor;
import com.odcgroup.translation.core.richtext.IListStyle;
import com.odcgroup.translation.core.richtext.IParagraphStyle;
import com.odcgroup.translation.core.richtext.IRichTextHandler;
import com.odcgroup.translation.core.richtext.ITextStyle;
import com.odcgroup.translation.core.richtext.RichTextUtils;

/**
 * This class performs the transformation of a RichText to the styled text
 * format.
 * 
 * @author Sreekanth S C
 * @version 1.0
 */
public class RichTextToStyledTextConverter implements IRichTextHandler {
	
	
	private class BulletRange {
		int startLine = 0;
		int lineCount = 0;
	}

	/**
	 *  Conventional constant to identify justification alignment.
	 */
	private static final int JUSTIFY = 999999;
	
	//
	/**
	 * StyledText passed by the converter. 
	 */
	private StyledText richText = null;
	
	/**
	 * Display to create fonts
	 */
	private Display device = null;
	
	/**
	 * Buffer to store the converted text
	 */
	private StringBuffer finaltext = new StringBuffer();
	
	/**
	 * List of StyleRanges
	 */
	private ArrayList<StyleRange> ranges = new ArrayList<StyleRange>();
	
	/**
	 * Text Offset
	 */
	private int offset = 0;
	
	/**
	 * Table to store the line number and the corresponding alignments
	 */
	private Map<Integer, Integer[]> textToLine = new HashMap<Integer, Integer[]>();
	
	/**
	 * Table to store the line number and the bullet formats
	 */
	private Map<Bullet, BulletRange> bulletToLine = new HashMap<Bullet, BulletRange>();
	
	/**
	 * The Bullet stack is used to bind the correct bullet definition to a line.
	 * The stack is necessary also to support nested ordered/unordered lists.
	 */
	private Stack<Bullet> bulletStack = new Stack<Bullet>();
	
	/**
	 * booleans to check font styles
	 */
	private boolean isBold = false;
	private boolean isItalic = false;
	
	/**
	 * Default font size
	 */
	private int fontSize = RichTextUtils.DEFAULT_FONT_HEIGHT;
	
	/**
	 * Variable to store number of lines
	 */
	private static int lineIndex = 0;
	
	/**
	 * is true if a newline was added just after a paragraph.
	 */
	private boolean extraEOL = false;
	
	/**
	 * if true a list is in construction.
	 */
	private boolean insideList = false;
	
	/**
	 * The sets of recognized colors
	 */
	private Set<Color> colors;
	
	/**
	 * The sets of recognized fonts
	 */
	private Set<Font> fonts;
	
	private Font defaultFont;

	private Color createColor(IColor color) {
		int rr = color.getRed();
		int gg = color.getGreen();
		int bb = color.getBlue();
		
		// check if this color has already been created
		for (Color c : colors) {
			if (c.getRed() == rr && c.getGreen() == gg && c.getBlue() == bb) {
				return c;
			}
		}
		
		// create a new color
		Color c = new Color(device, new RGB(rr, gg, bb));
		colors.add(c);
		return c;
	}

	/**
	 * @return null if the height is equals to the height of the default font.
	 */
	private Font createFont(int height) {

		// check if it map the default font
		FontData data = defaultFont.getFontData()[0];
		if (data.getHeight() == height) {
			return null;
		}
		
		// check if this color has already been created
		for (Font f : fonts) {
			data = f.getFontData()[0]; 
			if (data.getHeight() == height) {
				return f;
			}
		}
		
		// create a new font
		FontData[] fontData = defaultFont.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(height);
		}
		Font f = new Font(device, fontData);
		fonts.add(f);
		
		return f;
	}

	/**
	 * @param richText
	 * @param display
	 */
	public RichTextToStyledTextConverter(StyledText richText, Display display, Font defaultFont, Set<Font> fonts, Set<Color> colors) {
		// get the styletext and display
		this.richText = richText;
		this.device = display;
		this.colors = colors;
		this.fonts = fonts;
		this.defaultFont = defaultFont;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#start()
	 */
	@Override
	public void start() {
		// reset the offset - parsing starts here
		offset = 0;
		lineIndex = 0;
		extraEOL = false;
		insideList = false;
		bulletStack.clear();
		bulletToLine.clear();
		textToLine.clear();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#end()
	 */
	@Override
	public void end() {
		String text = finaltext.toString();

		if (extraEOL || text.endsWith("\n")) {
			// remove the last new line appended as it encounters end of paragraph.
			text = text.substring(0, text.length() - 1);
			extraEOL = false;
		}
		richText.setText(text);
		StyleRange[] sRanges = new StyleRange[ranges.size()];
		ranges.toArray(sRanges);

		// set line decorators
		for (int i = 0; i < lineIndex; i++) {
			Integer[] temp = textToLine.get(i);
			if (temp != null) {
				if (temp[0] != JUSTIFY) {
					if (richText.getLineAlignment(i) != temp[0]) {
						richText.setLineAlignment(i, 1, temp[0]);
					}
				} else {
					richText.setLineJustify(i, 1, true);
				}
				if (temp[1] != 0) {
					richText.setLineIndent(i, 1, temp[1]);
					
				}
				if (temp[2] != 0) {
					richText.setLineWrapIndent(i, 1, temp[2]);
				}
			}
		}
		
		// defines bullet lists
		for (Bullet bullet : bulletToLine.keySet()) {
			BulletRange br = bulletToLine.get(bullet);
			richText.setLineBullet(br.startLine, br.lineCount, bullet);
			richText.setLineWrapIndent(br.startLine, br.lineCount, bullet.style.metrics.width);
		}
		
		richText.setStyleRanges(sRanges);

		// clear
		bulletStack.clear();
		bulletToLine.clear();
		textToLine.clear();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#startParagraph(com.odcgroup.translation.core.richtext.IParagraphStyle)
	 */
	@Override
	public void startParagraph(IParagraphStyle style) {
		// MAP FOR LINE AND ALIGNMENT
		// Default alignment, LEFT and NO indentation:
		
		extraEOL = false;

		Integer[] indent = new Integer[] { SWT.LEFT, 0, 0 };

		if (style.isCenter()) {
			indent[0] = SWT.CENTER;
		} else if (style.isRight()) {
			indent[0] = SWT.RIGHT;
		}
		if (style.isJustify()) {
			indent[0] = JUSTIFY;
		}

		if (style.getFirstLineIndent() != 0) {
			indent[1] = style.getFirstLineIndent();
		}

		if (style.getWrappedLineIndent() != 0) {
			indent[2] = style.getWrappedLineIndent();
		}

		textToLine.put(lineIndex, indent);

	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#endParagraph()
	 */
	@Override
	public void endParagraph() {
		if (offset > 0) {
			if (! insideList) {
				newLine();
			}
			extraEOL = true;
		}

	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#startList(com.odcgroup.translation.core.richtext.IListStyle)
	 */
	@Override
	public void startList(IListStyle style, String prevTextChunk) {
		
		if (prevTextChunk.length() > 0) {
			text(prevTextChunk);
		}
		
		extraEOL = false;
		insideList = true;
		Bullet bullet = null;
		if (style.isOrderedList()) {
			StyleRange range = new StyleRange();
			range.metrics = new GlyphMetrics(0, 0, style.getIndent());
			bullet = new Bullet(ST.BULLET_NUMBER | ST.BULLET_TEXT, range);
			bullet.text = ".";
		} else {
			StyleRange range = new StyleRange();
			range.metrics = new GlyphMetrics(0, 0, style.getIndent());
			bullet = new Bullet(ST.BULLET_DOT, range);
		}
		BulletRange br = new BulletRange();
		br.startLine = lineIndex;
		br.lineCount = 0;
		bulletToLine.put(bullet, br);
		bulletStack.push(bullet);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#endList(com.odcgroup.translation.core.richtext.IListStyle)
	 */
	@Override
	public void endList(IListStyle style) {
		extraEOL = false;
		insideList = false;
		bulletStack.pop();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#startListItem()
	 */
	@Override
	public void startListItem() {
		extraEOL = false;
		//lineIndex++;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#endListItem()
	 */
	@Override
	public void endListItem() {
		
		// bind the current bullet to the line
		Bullet bullet = bulletStack.peek();
		bulletToLine.get(bullet).lineCount++;
		
		extraEOL = false;
		if(offset > 0) { 
			newLine();
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#text(com.odcgroup.translation.core.richtext.ITextStyle, java.lang.String)
	 */
	@Override
	public void text(ITextStyle style, String text) {
		extraEOL = false;
		StyleRange sr = new StyleRange();

		// START and LENGTH
		sr.start = offset;
		offset += text.length();
		sr.length = text.length();

		// BACKGROUND COLOR
		IColor bColor = style.getBackgroundColor();
		if (bColor != null) {
			sr.background = createColor(bColor);
		}

		// FOREGROUND COLOR
		IColor fColor = style.getForegroundColor();
		if (fColor != null) {
			sr.foreground = createColor(fColor);
		}

		// BOLD
		if (style.isBold()) {
			isBold = true;
		}

		// ITALIC
		if (style.isItalic()) {
			isItalic = true;
		}

		// UNDERLINE
		if (style.isUnderline()) {
			sr.underline = true;
		}

//		if (style.getFontName() != null) {
//			fontName = style.getFontName();
//		}

//		if (style.getFontName() != null) {
//			fontSize = style.getFontSize();
//		}

		// FONT DATA - IF NOT YET DEFAULT FONT AND SIZE WILL BE SET
		if (isBold && isItalic) {
			sr.fontStyle = SWT.BOLD | SWT.ITALIC;
			isBold = false;
			isItalic = false;
		} else if (isBold && !isItalic) {
			sr.fontStyle = SWT.BOLD;
			isBold = false;
		} else if (isItalic && !isBold) {
			sr.fontStyle = SWT.ITALIC;
			isItalic = false;
		} else {
			sr.fontStyle = SWT.NORMAL;
		}

		fontSize = style.getFontSize();
		sr.font = createFont(fontSize);

		// APPENDING THE STRING
		finaltext.append(text);

		// ADDING RANGE TO LIST
		ranges.add(sr);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#text(java.lang.String)
	 */
	@Override
	public void text(String text) {
		extraEOL = false;
		int len = text.length();
		for (int kx = 0; kx < len; kx++) {
			if (text.charAt(kx) == '\n') {
				lineIndex++;
			}
		}
		finaltext.append(text);
		offset += text.length(); 
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.richtext.IRichTextHandler#newLine()
	 */
	@Override
	public void newLine() {
		finaltext.append("\n");
		offset++;
		lineIndex++;
	}
}
