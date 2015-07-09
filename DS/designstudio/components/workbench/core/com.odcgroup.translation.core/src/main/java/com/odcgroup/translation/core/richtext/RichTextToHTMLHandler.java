package com.odcgroup.translation.core.richtext;

import org.apache.commons.lang.StringUtils;

public class RichTextToHTMLHandler implements IRichTextHandler {

	private StringBuilder buffer;
	private boolean insideList = false;

	public RichTextToHTMLHandler() {
	}

	@Override
	public void end() {
	}

	@Override
	public void endList(IListStyle style) {
		if (style.isOrderedList()) {
			buffer.append("</ol>");
		} else {
			buffer.append("</ul>");
		}
		insideList = false;
	}

	@Override
	public void endListItem() {
		buffer.append("</li>");
	}

	@Override
	public void endParagraph() {
		if (insideList) {
			buffer.append("</span>");
		} else {
			buffer.append("</p>");
		}
	}

	public String getResult() {
		String html = buffer.toString();
		//System.out.println(html);
		return html;
	}

	@Override
	public void newLine() {
		buffer.append("<br/>");
	}

	@Override
	public void start() {
		buffer = new StringBuilder();
		insideList = false;
	}

	@Override
	public void startList(IListStyle style, String prevTextChunk) {

		// do not output the last '\n' just before a list.
		if (prevTextChunk.endsWith("\n")) {
			prevTextChunk = prevTextChunk.substring(0, prevTextChunk.length() - 1);
		}
		if (prevTextChunk.length() > 0) {
			text(prevTextChunk);
		}
		
		StringBuilder styleBuidler = new StringBuilder();
		
		insideList = true;
		if (style.isOrderedList()) {
			buffer.append("<ol");
//			styleBuidler.append("list-style-type:decimal;");
		} else {
			buffer.append("<ul");
//			styleBuidler.append("list-style-type:disc;");
		}
		appendID(style.getID());
		appendCSS(style.getCSSClass());

		int fi = style.getIndent();
		if (fi > 0) {
			styleBuidler.append("padding-left:");
			styleBuidler.append(fi);
			styleBuidler.append("px;");
		}
		
		if (style.isOrderedList()) {
			String start = style.getStartNumber();
			if (StringUtils.isNotBlank(start)) {
				buffer.append(" start=\"");
				buffer.append(start);
				buffer.append("\"");
			}
		}
		
		if (styleBuidler.length() > 0) {
			buffer.append(" style=\"");
			buffer.append(styleBuidler);
			buffer.append("\"");
		}
		
		buffer.append(">");
	}

	@Override
	public void startListItem() {
		buffer.append("<li>");
	}

	@Override
	public void startParagraph(IParagraphStyle style) {

		if (insideList) {
			buffer.append("<span");
		} else {
			buffer.append("<p");
		}

		// html id + css class
		appendID(style.getID());
		appendCSS(style.getCSSClass());
		
		// text alignment
		if (style.isCenter()) {
			buffer.append(" align=\"center\"");
		} else if (style.isLeft()) {
			buffer.append(" align=\"left\"");
		} else if (style.isRight()) {
			buffer.append(" align=\"right\"");
		} else if (style.isJustify()) {
			buffer.append(" align=\"justify\"");
		}

		int fi = style.getFirstLineIndent();
		int wi = style.getWrappedLineIndent();
		boolean generateStyle = fi > 0 || wi > 0 ;

		if (generateStyle && !insideList) {
			
			buffer.append(" style=\"");
			if (fi != wi) {
				// first line is indented
				buffer.append("text-indent:");
				buffer.append(fi-wi);
				buffer.append("px;");
			}
			if (wi > 0) {
				buffer.append("padding-left:");
				buffer.append(wi);
				buffer.append("px;");
			}
		
			// end of style attribute
			buffer.append("\"");
		}

		buffer.append(">");
	}
	
//	private boolean isDefaultFontName(String fontName) {
//		return StringUtils.isBlank(fontName) || fontName.equals(RichTextUtils.DEFAULT_FONT_NAME);
//	}
	
	private boolean isDefaultFontSize(int fontSize) {
		return fontSize == 0 || fontSize == RichTextUtils.DEFAULT_FONT_HEIGHT;
	}

	@Override
	public void text(ITextStyle style, String text) {

		IColor fc = style.getForegroundColor();
		IColor bc = style.getBackgroundColor();
		//String fontName = style.getFontName();
		int fontSize = style.getFontSize();

		// check if a span element must be generated.
		boolean generateSpan = /*!isDefaultFontName(fontName) ||*/ !isDefaultFontSize(fontSize)
				|| fc != null || bc != null;
		if (generateSpan) {
			buffer.append("<span style=\"");
//			if (!isDefaultFontName(fontName)) {
//				buffer.append("font-family:");
//				buffer.append(fontName);
//				buffer.append(";");
//			}
			if (!isDefaultFontSize(fontSize)) {
				buffer.append("font-size:");
				buffer.append(fontSize);
				buffer.append("px;");
			}
			appendColor("color", fc);
			appendColor("background-color", bc);
			buffer.append("\">");
		}

		boolean isBold = style.isBold();
		boolean isItalic = style.isItalic();
		boolean isUnderline = style.isUnderline();

		if (isBold)
			buffer.append("<b>");
		if (isItalic)
			buffer.append("<i>");
		if (isUnderline)
			buffer.append("<u>");

		buffer.append(text);

		if (isUnderline)
			buffer.append("</u>");
		if (isItalic)
			buffer.append("</i>");
		if (isBold)
			buffer.append("</b>");

		if (generateSpan) {
			buffer.append("</span>");
		}
	}

	@Override
	public void text(String text) {
		int start = 0;
		int len = text.length();
		for (int kx = 0; kx < len; kx++) {
			if (text.charAt(kx) == '\n') {
				buffer.append(text.substring(start, kx));
				buffer.append("<br/>");
				start = kx+1;
			}
		}
		if (start < len) {
			buffer.append(text.substring(start));
		}
	}

	// ok
	private void appendID(String id) {
		if (StringUtils.isNotBlank(id)) {
			buffer.append(" id=\"");
			buffer.append(id);
			buffer.append("\"");
		}
	}

	private void appendCSS(String css) {
		if (StringUtils.isNotBlank(css)) {
			buffer.append(" class=\"");
			buffer.append(css);
			buffer.append("\"");
		}
	}

	// OK
	private void appendColor(String attribute, IColor color) {
		if (color != null) {
			buffer.append(attribute);
			buffer.append(":rgb(");
			buffer.append(color.getRed());
			buffer.append(",");
			buffer.append(color.getGreen());
			buffer.append(",");
			buffer.append(color.getBlue());
			buffer.append(")");
			buffer.append(";");
		}
	}

}
