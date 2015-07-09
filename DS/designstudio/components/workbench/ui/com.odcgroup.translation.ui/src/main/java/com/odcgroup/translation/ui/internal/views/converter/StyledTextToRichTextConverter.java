package com.odcgroup.translation.ui.internal.views.converter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.FontData;

import com.odcgroup.translation.core.richtext.IColor;
import com.odcgroup.translation.core.richtext.IParagraphStyle;
import com.odcgroup.translation.core.richtext.IStyle;
import com.odcgroup.translation.core.richtext.ITextStyle;

/**
 * This class performs the transformation of a StyledText data structure to the
 * rich text expression language.
 * 
 * @see XML Schema for Rich Text Expression Language for Translation
 * 
 * @author Alain Tripod
 * @version 1.0
 */
public class StyledTextToRichTextConverter {

	/**
	 * @author alain
	 */
	private class Color implements IColor {

		private org.eclipse.swt.graphics.Color swtColor;

		@Override
		public int getBlue() {
			return swtColor.getBlue();
		}

		@Override
		public int getGreen() {
			return swtColor.getGreen();
		}

		@Override
		public int getRed() {
			return swtColor.getRed();
		}

		public Color(org.eclipse.swt.graphics.Color swtColor) {
			this.swtColor = swtColor;
		}
	}

	/**
	 */
	private class ParagraphStyle extends Style implements IParagraphStyle {

		private int line;
		
		@Override
		public int getFirstLineIndent() {
			return text.getLineIndent(line);
		}

		@Override
		public int getWrappedLineIndent() {
			return text.getLineWrapIndent(line);
		}

		public ParagraphStyle(int line) {
			this.line = line;
		}

		@Override
		public boolean isJustify() {
			return text.getLineJustify(line);
		}

		@Override
		public boolean isCenter() {
			return ((text.getLineAlignment(line) & SWT.CENTER) != 0);
		}

		@Override
		public boolean isLeft() {
			return ((text.getLineAlignment(line) & SWT.LEFT) != 0);
		}

		@Override
		public boolean isRight() {
			return ((text.getLineAlignment(line) & SWT.RIGHT) != 0);
		}

		@Override
		public boolean isDecorated() {
			return StringUtils.isNotBlank(getID()) 
				|| StringUtils.isNotBlank(getCSSClass())
				|| getFirstLineIndent() > 0
				|| getWrappedLineIndent() > 0
				|| isRight()
				|| isJustify()
				|| isCenter();
		}

	}

	/**
	 *
	 */
	private class Style implements IStyle {

		@Override
		public String getCSSClass() {
			return "";
		}

		@Override
		public String getID() {
			return "";
		}

	}

	/**
	 *
	 */
	private class TextStyle extends Style implements ITextStyle {

		private StyleRange range;

		@Override
		public IColor getBackgroundColor() {
			return (range.background != null) ? new Color(range.background)
					: null;
		}

		@Override
		public int getFontSize() {
			if (range.font != null) {
				FontData data = range.font.getFontData()[0];
				return data.getHeight();
			}
			return 0;
		}

		@Override
		public IColor getForegroundColor() {
			return (range.foreground != null) ? new Color(range.foreground)
					: null;
		}

		@Override
		public boolean isBold() {
			return (range.fontStyle & SWT.BOLD) != 0;
		}

		@Override
		public boolean isItalic() {
			return (range.fontStyle & SWT.ITALIC) != 0;
		}

		@Override
		public boolean isUnderline() {
			return range.underline;
		}
		
		@Override
		public boolean isDefaultFontSize() {
			int fs = getFontSize();
			return fs == 0 || fs == 10;
		}

		@Override
		public boolean isDecorated() {
			boolean decorated = StringUtils.isNotBlank(getID()) 
				|| StringUtils.isNotBlank(getCSSClass())
				|| getBackgroundColor() != null
				|| getForegroundColor() != null
				|| isBold()
				|| isItalic()
				|| isUnderline();
			if (!decorated) {
				// check that is not default font size
				if(!isDefaultFontSize()) {
					return true;
				}
			}
			return decorated;
		}

		public TextStyle(StyleRange range) {
			this.range = range;
		}
	}
	
	

	private StyledTextToRichTextHandler handler;

	private StyledText text;

	private boolean convertLine(int line) {
		boolean noRT = true;
		int offset = text.getOffsetAtLine(line);
		int end = offset;
		//System.out.println(text.getLine(line));
		// while (end < text.getCharCount() && line ==
		// text.getLineAtOffset(end++));
		end += text.getLine(line).length();
		// le while ne traite pas correctement la fin de la ligne.
		StyleRange[] ranges = text.getStyleRanges(offset, end - offset, true);
		for (StyleRange range : ranges) {
			if (offset < range.start) {
				// there are no styled text
				handler.text(text.getText(offset, range.start - 1));
				offset = range.start;
			}
			// output styled text
			String chunk = text.getText(range.start, range.start + range.length	- 1);
			TextStyle textStyle = new TextStyle(range);
			noRT &= !textStyle.isDecorated();
			handler.text(textStyle, chunk);
			offset += range.length;
		}
		if (offset < end) {
			handler.text(text.getText(offset, end - 1));
		}
		return noRT;
	}
	
	/**
	 * @return
	 */
	public String convert() {
		
		handler = new StyledTextToRichTextHandler();

		int lineCount = text.getLineCount();
		int lastLine = lineCount - 1;
		
		// true if the text does not contains any  rich-text decorators
		boolean noRT = true;
		
		handler.start();
		
		int line = 0;
		while (line < lineCount) {

			Bullet bullet = text.getLineBullet(line);
			
			if (bullet != null) {
				
				noRT = false;
				
				boolean unordered = bullet.type == ST.BULLET_DOT;
				ListStyle listStyle = new ListStyle(!unordered);
				if (listStyle.isOrderedList()) {
					listStyle.setStartNumber(bullet.text);
				}
				if (bullet.style.metrics.width > 0) {
					listStyle.setIndent(bullet.style.metrics.width);
				}
				//listStyle.setWrapIndent(text.getLineWrapIndent(line));

				Bullet tmp = bullet;
				handler.startList(listStyle, "");

				do {

					handler.startListItem();
					
					// convert paragraph
					ParagraphStyle style = new ParagraphStyle(line);
					boolean isDecorated = style.isDecorated();
					String lineContent = text.getContent().getLine(line); 
					int lineStartOffset = text.getOffsetAtLine(line);
					int lineLength = lineContent.length();
					isDecorated |= text.getRanges(lineStartOffset, lineLength).length != 0;
						
					if (isDecorated) {
						if (style.isDecorated()) {
							handler.startParagraph(style);
							convertLine(line);
							handler.endParagraph();
						} else {
							convertLine(line);
						}
					} else { 
						handler.text(lineContent);
					}					
										
					line++; 
					tmp = null;
					if (line < lineCount) {
						tmp = text.getLineBullet(line);
					} 
					handler.endListItem();

				} while (tmp == bullet);

				handler.endList(listStyle);				
				
			} else {
				
				ParagraphStyle style = new ParagraphStyle(line);
				boolean isDecorated = style.isDecorated();
				String lineContent = text.getContent().getLine(line); 
				int lineStartOffset = text.getOffsetAtLine(line);
				int lineLength = lineContent.length();
				isDecorated |= text.getRanges(lineStartOffset, lineLength).length != 0;
					
				if (isDecorated) {
					
					if (style.isDecorated()) {
						noRT = false;
						handler.startParagraph(style);
						convertLine(line);
						handler.endParagraph();
					} else {
						noRT &= convertLine(line);
						if (line < lastLine) {
							handler.newLine();
						}
					}
				} else { 
					handler.text(lineContent);
					if (line < lastLine) {
						handler.newLine();
					}
				}
				line++;
			}
		}
		
		line = 0;
		
		handler.end();
		
		String result = handler.getResult();
		
		if (noRT) {
			// remove enclosing tag <rt>
			if (result.startsWith("<rt>")) {
				result = result.substring(4);
			}
			if (result.endsWith("</rt>")) {
				result = result.substring(0, result.length()-5);
			}
			result = StringEscapeUtils.unescapeXml(result);
		}
		
		//System.out.println(result);
		return result;

	}

	public StyledTextToRichTextConverter(StyledText text) {
		this.text = text;
	}

}
