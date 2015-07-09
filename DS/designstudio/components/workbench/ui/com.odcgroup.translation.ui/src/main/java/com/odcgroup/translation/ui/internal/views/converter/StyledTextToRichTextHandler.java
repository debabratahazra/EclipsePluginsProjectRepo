package com.odcgroup.translation.ui.internal.views.converter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.odcgroup.translation.core.richtext.IColor;
import com.odcgroup.translation.core.richtext.IListStyle;
import com.odcgroup.translation.core.richtext.IParagraphStyle;
import com.odcgroup.translation.core.richtext.IRichTextHandler;
import com.odcgroup.translation.core.richtext.IStyle;
import com.odcgroup.translation.core.richtext.ITextStyle;

/**
 * @author sri, atr
 *
 */
public class StyledTextToRichTextHandler implements IRichTextHandler {

	private static final int INITIAL_CAPACITY = 1024;
	private StringBuilder buffer = new StringBuilder(INITIAL_CAPACITY);
	
	
	boolean isParagraphDecorated = false;
	boolean insideList = false;

	@Override
	public void end() {
		write("</rt>");
	}
	
	@Override
	public void endList(IListStyle style) {
		if (style.isOrderedList()) {
			write("</ol>");
		} else {
			write("</ul>");
		}
		insideList = false;
	}
	
	@Override
	public void endListItem() {
		write("</li>");
	}

	@Override
	public void endParagraph() {
		if (!insideList) {
			if (isParagraphDecorated) {
				write("</p>");
				isParagraphDecorated = false;
			}
		}
	}

	private final String escape(String text) {
		return StringEscapeUtils.escapeXml(text);
	}

	public String getResult() {
		return buffer.toString();
	}
	
	@Override
	public void newLine() {
		write("\n");
	}
	
	@Override
	public void start() {
		isParagraphDecorated = false;
		buffer.delete(0, buffer.length());
		write("<rt>");
	}

	@Override
	public void startList(IListStyle style, String prevTextChunk) {
		insideList = true;
		if (style.isOrderedList()) {
			write("<ol type=\"1\"");
			String start = style.getStartNumber();
			if (StringUtils.isNotBlank(start)) {
				start = start.trim();
			}
		} else {
			write("<ul");
		}
		int li= style.getIndent();
		int wi = style.getWrapIndent();
		if (li > 0) {
			write(" li=\"");
			write(style.getIndent());
			write("\"");
		}
		if (wi > 0 && wi !=li) {
			write(" wi=\"");
			write(style.getWrapIndent());
			write("\"");
		}
		write(">");
	}

	@Override
	public void startListItem() {
		write("<li>");
	}

	@Override
	public void startParagraph(IParagraphStyle style) {
		if (!insideList) {
			isParagraphDecorated = style.isDecorated();
			if (isParagraphDecorated) {
				write("<p");
				write(style);
				write(">");
			}
		}
	}

	@Override
	public void text(ITextStyle style, String text) {
		boolean isDecorated = style.isDecorated();
		if (isDecorated) {
			write("<s");
			write(style);
			write(">");
			write(escape(text));
			write("</s>");
		} else {
			text(text);
		}
	}

	@Override
	public void text(String text) {
		write(escape(text));
	}

	private String toHexString(int value) {
		String hex = Integer.toHexString(value);
		if (hex.length() == 1) {
			return '0'+hex;
		}
		return hex;
	}

	private void write(IColor color, String attribute) {
		if (color != null) {
			write(" ");
			write(attribute);
			write("=\"#");
			write(toHexString(color.getRed()));
			write(toHexString(color.getGreen()));
			write(toHexString(color.getBlue()));
			write("\"");
		}
	}

	private final void write(int value) {
		buffer.append(value);
	}

	private void write(IParagraphStyle style) {
		write((IStyle) style);
		int li = style.getFirstLineIndent();
		if (li > 0) {
			write(" li=\"");
			write(li);
			write("\"");
		}
		int wi = style.getWrappedLineIndent();
		if (wi > 0) {
			write(" wi=\"");
			write(wi);
			write("\"");
		}
		if (style.isJustify()) {
			write(" align=\"justify\"");
// do not output align=left : it is the default alignment
//		} else if (style.isLeft()) {
//			write(" align=\"left\"");
		} else if (style.isCenter()) {
			write(" align=\"center\"");
		} else if (style.isRight()) {
			write(" align=\"right\"");
		}
	}

	private void write(IStyle style) {
		if (style.getID().length() > 0) {
			write(" id=\"");
			write(style.getID());
			write("\"");
		}
		if (style.getCSSClass().length() > 0) {
			write(" class=\"");
			write(style.getCSSClass());
			write("\"");
		}
	}

	private void write(ITextStyle style) {
		write((IStyle) style);
		StringBuilder buf = new StringBuilder(3);
		if (style.isBold()) buf.append('b');
		if (style.isUnderline()) buf.append('u');
		if (style.isItalic()) buf.append('i');
		String ts = buf.toString();
		if (ts.length() > 0) {
			write(" ts=\"");
			write(ts);
			write("\"");
		}
		if (!style.isDefaultFontSize()) {
			write(" fs=\"");
			write(style.getFontSize()+"");
			write("\"");
		}
		write(style.getBackgroundColor(), "bc");
		write(style.getForegroundColor(), "fc");
	}

	private final void write(String text) {
		buffer.append(text);
	}

}
