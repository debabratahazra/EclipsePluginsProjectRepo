package com.odcgroup.translation.core.richtext;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class implements receives notification of a SAX parser.
 * 
 * @author atr & scn
 * 
 */
public class SAXRichTextHandler extends DefaultHandler {

	private static class ParagraphStyle implements IParagraphStyle {

		private Attributes attributes;

		@Override
		public String getCSSClass() {
			String value = attributes.getValue(ATTRIBUTE_CLASS);
			return value != null ? value : "";
		}

		@Override
		public int getFirstLineIndent() {
			int indent = 0;
			String image = attributes.getValue(PARAGRAPH_FIRST_LINE_INDENT);
			if (image != null) {
				indent = Integer.valueOf(image);
			}
			return indent;
		}

		@Override
		public String getID() {
			String value = attributes.getValue(ATTRIBUTE_ID);
			return value != null ? value : "";
		}

		@Override
		public int getWrappedLineIndent() {
			int indent = 0;
			String image = attributes.getValue(PARAGRAPH_WRAPPED_LINE_INDENT);
			if (image != null) {
				indent = Integer.valueOf(image);
			}
			return indent;
		}

		@Override
		public boolean isCenter() {
			String alignment = attributes.getValue(PARAGRAPH_ALIGNMENT);
			return "center".equals(alignment);
		}

		@Override
		public boolean isJustify() {
			String alignment = attributes.getValue(PARAGRAPH_ALIGNMENT);
			return "justify".equals(alignment);
		}

		@Override
		public boolean isLeft() {
			String alignment = attributes.getValue(PARAGRAPH_ALIGNMENT);
			return "left".equals(alignment);
		}

		@Override
		public boolean isRight() {
			String alignment = attributes.getValue(PARAGRAPH_ALIGNMENT);
			return "right".equals(alignment);
		}

		public ParagraphStyle(Attributes attributes) {
			this.attributes = attributes;
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

	private static class Color implements IColor {

		int rr = 0, gg = 0, bb = 0;

		public Color(int r, int g, int b) {
			this.rr = r;
			this.gg = g;
			this.bb = b;
		}

		@Override
		public int getRed() {
			return this.rr;
		}

		@Override
		public int getGreen() {
			return this.gg;
		}

		@Override
		public int getBlue() {
			return this.bb;
		}
	}

	private int[] getRGB(String backColor) {
		int[] colors = new int[3];
		if (backColor.startsWith("#")) {
			backColor = backColor.substring(1);
		}
		switch (backColor.length()) {
		case 3:
			colors[0] = Integer.parseInt(backColor.substring(0, 1), 16);
			colors[1] = Integer.parseInt(backColor.substring(1, 2), 16);
			colors[2] = Integer.parseInt(backColor.substring(2), 16);
			break;
		case 4:
			colors[0] = Integer.parseInt(backColor.substring(0, 2), 16);
			colors[1] = Integer.parseInt(backColor.substring(2, 3), 16);
			colors[2] = Integer.parseInt(backColor.substring(3), 16);
			break;
		case 5:
			colors[0] = Integer.parseInt(backColor.substring(0, 2), 16);
			colors[1] = Integer.parseInt(backColor.substring(2, 4), 16);
			colors[2] = Integer.parseInt(backColor.substring(4), 16);
			break;
		case 6:
			colors[0] = Integer.parseInt(backColor.substring(0, 2), 16);
			colors[1] = Integer.parseInt(backColor.substring(2, 4), 16);
			colors[2] = Integer.parseInt(backColor.substring(4), 16);
			break;
		}
		return colors;
	}

	class TextStyle implements ITextStyle {

		private Attributes attributes;

		@Override
		public IColor getBackgroundColor() {
			String backColor = attributes.getValue("bc");
			if (backColor != null) {
				int[] vals = getRGB(backColor);
				return new Color(vals[0], vals[1], vals[2]);
			}
			return null;
		}

		@Override
		public String getCSSClass() {
			String value = attributes.getValue(ATTRIBUTE_CLASS);
			return value != null ? value : "";
		}

		@Override
		public int getFontSize() {
			String fontSize = attributes.getValue("fs");
			return fontSize != null ? Integer.parseInt(fontSize) : RichTextUtils.DEFAULT_FONT_HEIGHT;
		}

		@Override
		public IColor getForegroundColor() {
			String foreColor = attributes.getValue("fc");
			if (foreColor != null) {
				int[] vals = getRGB(foreColor);
				return new Color(vals[0], vals[1], vals[2]);
			}
			return null;
		}

		@Override
		public String getID() {
			String value = attributes.getValue(ATTRIBUTE_ID);
			return value != null ? value : "";
		}

		@Override
		public boolean isBold() {
			String textStyle = attributes.getValue("ts");
			if (textStyle != null && textStyle.contains("b"))
				return true;
			else
				return false;
		}

		@Override
		public boolean isItalic() {
			String textStyle = attributes.getValue("ts");
			if (textStyle != null && textStyle.contains("i"))
				return true;
			else
				return false;
		}

		@Override
		public boolean isUnderline() {
			String textStyle = attributes.getValue("ts");
			if (textStyle != null && textStyle.contains("u"))
				return true;
			else
				return false;
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
					decorated = ! isDefaultFontSize();
				}
				return decorated;
		}

		public TextStyle(Attributes attributes) {
			this.attributes = attributes;
		}
	}
	
	
	static class ListStyle implements IListStyle {
		
		private Attributes attributes;
		private boolean orderedList;

		@Override
		public String getID() {
			String value = attributes.getValue(ATTRIBUTE_ID);
			return value != null ? value : "";
		}

		@Override
		public String getCSSClass() {
			String value = attributes.getValue(ATTRIBUTE_CLASS);
			return value != null ? value : "";
		}

		@Override
		public final boolean isOrderedList() {
			return orderedList;
		}

		@Override
		public String getStartNumber() {
			String value = attributes.getValue(ATTRIBUTE_CLASS);
			return value != null ? value : "";
		}
		
		@Override
		public final int getIndent() {
			String value = attributes.getValue(LIST_INDENT);
			return value != null ? Integer.parseInt(value) : 0;
		}

		@Override
		public final int getWrapIndent() {
			String value = attributes.getValue(LIST_WRAP_INDENT);
			return value != null ? Integer.parseInt(value) : 0;
		}

		public ListStyle(Attributes attributes, boolean orderedList) {
			this.attributes = attributes;
			this.orderedList = orderedList;
		}
		
	}

	private static final String ATTRIBUTE_CLASS = "class";
	private static final String ATTRIBUTE_ID = "id";
	private static final String LI_ELEMENT = "li";
	private static final String OL_ELEMENT = "ol";
	private static final String LIST_INDENT = "li";
	private static final String LIST_WRAP_INDENT = "wi";

	private static final String P_ELEMENT = "p";
	private static final String PARAGRAPH_ALIGNMENT = "align";

	private static final String PARAGRAPH_FIRST_LINE_INDENT = "li";
	private static final String PARAGRAPH_WRAPPED_LINE_INDENT = "wi";
	private static final String RT_ELEMENT = "rt";

	private static final String S_ELEMENT = "s";
	private static final String UL_ELEMENT = "ul";

	private IRichTextHandler handler;

	private String textChunk = "";
	private ITextStyle textStyle = null;
	private IListStyle listStyle = null;

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		textChunk += String.valueOf(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals(RT_ELEMENT)) {
			if (textChunk.length() > 0) {
				handler.text(textChunk);
				textChunk = "";
			}
			handler.end();
		} else if (qName.equals(P_ELEMENT)) {
			if (textChunk.length() > 0) {
				handler.text(textChunk);
				textChunk = "";
			}
			handler.endParagraph();
		} else if (qName.equals(S_ELEMENT)) {
			if (textChunk.length() > 0) {
				handler.text(textStyle, textChunk);
				textChunk = "";
			}
			textStyle = null;
		} else if (qName.equals(OL_ELEMENT)) { 
			handler.endList(listStyle);
			listStyle = null;
		} else if (qName.equals(UL_ELEMENT)) {
			handler.endList(listStyle);
			listStyle = null;
		} else if (qName.equals(LI_ELEMENT)) { 
			if (textChunk.length() > 0) {
				handler.text(textChunk);
				textChunk = "";
			}
			handler.endListItem();
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals(RT_ELEMENT)) {
			handler.start();
		} else if (qName.equals(P_ELEMENT)) {
			if (textChunk.length() > 0) {
				handler.text(textChunk);
				textChunk = "";
			}
			handler.startParagraph(new ParagraphStyle(attributes));
		} else if (qName.equals(S_ELEMENT)) {
			if (textChunk.length() > 0) {
				handler.text(textChunk);
				textChunk = "";
			}
			textStyle = new TextStyle(attributes);
		} else if (qName.equals(OL_ELEMENT)) { 
			listStyle = new ListStyle(attributes, true);
			handler.startList(listStyle, textChunk);
			textChunk = "";
		} else if (qName.equals(UL_ELEMENT)) {
			listStyle = new ListStyle(attributes, false);
			handler.startList(listStyle, textChunk);
			textChunk = "";
		} else if (qName.equals(LI_ELEMENT)) {
			handler.startListItem();
		}
	}

	/**
	 * Constructor
	 */
	public SAXRichTextHandler(IRichTextHandler handler) {
		this.handler = handler;
	}

}
