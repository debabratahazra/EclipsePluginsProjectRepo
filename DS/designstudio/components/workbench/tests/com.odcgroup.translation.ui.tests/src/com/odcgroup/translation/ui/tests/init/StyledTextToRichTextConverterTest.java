package com.odcgroup.translation.ui.tests.init;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.richtext.IColor;
import com.odcgroup.translation.core.richtext.ITextStyle;
import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.translation.ui.internal.views.converter.StyledTextToRichTextConverter;

/**
 * @author SSreekanth
 * 
 */
public class StyledTextToRichTextConverterTest {

	StyledText text = null;
	Display display = null;
	StyledTextToRichTextConverter converter = null;
	Shell shell = null;
	String result = null;

	private final static String SINGLE_LINE_NO_RT = "This is test for one line without rich text";
	private final static String MULTI_LINE_RT = "This is test for two line rich text\nThe second line for test";
	private final static String SINGLE_LINE_WITH_RT = "This is test for one line with rich text";

	/**
	 *
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
	 *
	 */
	private class TextStyle implements ITextStyle {

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
			return fs == 0 || fs == RichTextUtils.DEFAULT_FONT_HEIGHT;
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
				if (!isDefaultFontSize()) {
					return true;
				}
			}
			return decorated;
		}

		public TextStyle(StyleRange range) {
			this.range = range;
		}

		@Override
		public String getID() {
			return null;
		}

		@Override
		public String getCSSClass() {
			return null;
		}
	}

	@Before
	public void setUp() throws Exception {
		createStyledText();
	}

	@After
	public void tearDown() throws Exception {
		shell.close();
		display = null;
		text = null;
	}

	private void createStyledText() {
		shell = new Shell();
		shell.setLayout(new FillLayout());
		shell.setSize(200, 100);
		display = shell.getDisplay();
		// create the styled text widget
		text = new StyledText(shell, SWT.BORDER);
		shell.open();
	}

	/**
	 * Single line with our Rich Text content
	 */
	@Test
	public void testConvert1() {
		// Single Line no rich text
		text.setText(SINGLE_LINE_NO_RT);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();
		Assert.assertEquals(SINGLE_LINE_NO_RT, result);
		Assert.assertTrue(!result.startsWith("<rt>"));
		Assert.assertTrue(!result.endsWith("</rt>"));
		result = null;
		converter = null;
	}

	/**
	 * Multiple line with out Rich Text content
	 */
	@Test
	public void testConvert2() {
		text.setText(MULTI_LINE_RT);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();
		Assert.assertEquals(MULTI_LINE_RT, result);
		Assert.assertTrue(!result.startsWith("<rt>"));
		Assert.assertTrue(!result.endsWith("</rt>"));
	}

	/**
	 * Single line with Rich Text for Bold
	 */
	@Test
	public void testConvert3() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.fontStyle = SWT.BOLD;

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s ts=\"b\">" + SINGLE_LINE_WITH_RT + "</s></rt>",
				result);
		Assert.assertTrue(result.startsWith("<rt>"));
		Assert.assertTrue(result.endsWith("</rt>"));
		Assert.assertTrue(ts.isBold());
	}

	/**
	 * Single line with Rich Text for Italic
	 */
	@Test
	public void testConvert4() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.fontStyle = SWT.ITALIC;

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s ts=\"i\">" + SINGLE_LINE_WITH_RT + "</s></rt>",
				result);
		Assert.assertTrue(result.startsWith("<rt>"));
		Assert.assertTrue(result.endsWith("</rt>"));
		Assert.assertTrue(ts.isItalic());
	}

	/**
	 * Single line with Rich Text for underline
	 */
	@Test
	public void testConvert5() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.underline = true;

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s ts=\"u\">" + SINGLE_LINE_WITH_RT + "</s></rt>",
				result);
		Assert.assertTrue(result.startsWith("<rt>"));
		Assert.assertTrue(result.endsWith("</rt>"));
		Assert.assertTrue(ts.isUnderline());
	}

	/**
	 * Single line with Rich Text with Bold, Underline and Italic
	 */
	@Test
	public void testConvert6() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.underline = true;
		styleRange.fontStyle |= SWT.BOLD;
		styleRange.fontStyle |= SWT.ITALIC;

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s ts=\"bui\">" + SINGLE_LINE_WITH_RT + "</s></rt>",
				result);
		Assert.assertTrue(result.startsWith("<rt>"));
		Assert.assertTrue(result.endsWith("</rt>"));
		Assert.assertTrue(ts.isBold());
		Assert.assertTrue(ts.isUnderline());
		Assert.assertTrue(ts.isItalic());
	}

	/**
	 * Single line with Rich Text with Bold and Underline
	 */
	@Test
	public void testConvert7() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.underline = true;
		styleRange.fontStyle |= SWT.BOLD;

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s ts=\"bu\">" + SINGLE_LINE_WITH_RT + "</s></rt>",
				result);
		Assert.assertTrue(result.startsWith("<rt>"));
		Assert.assertTrue(result.endsWith("</rt>"));
		Assert.assertTrue(ts.isBold());
		Assert.assertTrue(ts.isUnderline());
	}

	/**
	 * Single line with Rich Text with Underline and Italic
	 */
	@Test
	public void testConvert8() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.underline = true;
		styleRange.fontStyle |= SWT.ITALIC;

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s ts=\"ui\">" + SINGLE_LINE_WITH_RT + "</s></rt>",
				result);
		Assert.assertTrue(result.startsWith("<rt>"));
		Assert.assertTrue(result.endsWith("</rt>"));
		Assert.assertTrue(ts.isUnderline());
		Assert.assertTrue(ts.isItalic());
	}

	/**
	 * Single line with Rich Text with Bold and Italic
	 */
	@Test
	public void testConvert9() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.fontStyle |= SWT.BOLD;
		styleRange.fontStyle |= SWT.ITALIC;

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s ts=\"bi\">" + SINGLE_LINE_WITH_RT + "</s></rt>",
				result);
		Assert.assertTrue(result.startsWith("<rt>"));
		Assert.assertTrue(result.endsWith("</rt>"));
		Assert.assertTrue(ts.isBold());
		Assert.assertTrue(ts.isItalic());
	}

	/**
	 * Single line with Rich Text to check for Default Font and Default font
	 * size.
	 */
	@Test
	public void testConvert10() {

		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.font = new Font(display, "Arial", RichTextUtils.DEFAULT_FONT_HEIGHT, SWT.NORMAL);
		styleRange.length = SINGLE_LINE_WITH_RT.length();

		text.setStyleRange(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();
		Assert.assertEquals(SINGLE_LINE_WITH_RT, result);
		styleRange.font.dispose();
	}

	/**
	 * Single line with Rich Text to check for different Font and different font
	 * size.
	 */
	@Test
	public void testConvert11() {

		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.font = new Font(display, "Helvetica", 16, SWT.NORMAL);
		styleRange.length = SINGLE_LINE_WITH_RT.length();

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();
		Assert.assertEquals(16, ts.getFontSize());
		Assert.assertEquals("<rt><s fs=\"16\">" + SINGLE_LINE_WITH_RT
				+ "</s></rt>", result);
		styleRange.font.dispose();
	}

	/**
	 * Single line with Rich Text to check for Foreground Color.
	 */
	@Test
	public void testConvert12() {

		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.foreground = new org.eclipse.swt.graphics.Color(display,
				128, 64, 64);

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s fc=\"#804040\">" + SINGLE_LINE_WITH_RT
				+ "</s></rt>", result);
		Assert.assertEquals(128, ts.getForegroundColor().getRed());
		Assert.assertEquals(64, ts.getForegroundColor().getGreen());
		Assert.assertEquals(64, ts.getForegroundColor().getBlue());
		styleRange.foreground.dispose();

	}

	/**
	 * Single line with Rich Text to check for Background Color.
	 */
	@Test
	public void testConvert13() {

		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.background = new org.eclipse.swt.graphics.Color(display,
				128, 128, 192);

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><s bc=\"#8080c0\">" + SINGLE_LINE_WITH_RT
				+ "</s></rt>", result);
		Assert.assertEquals(128, ts.getBackgroundColor().getRed());
		Assert.assertEquals(128, ts.getBackgroundColor().getGreen());
		Assert.assertEquals(192, ts.getBackgroundColor().getBlue());
		styleRange.background.dispose();

	}

	/**
	 * Single line with Rich Text to check combination of styling.
	 */
	@Test
	public void testConvert14() {

		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();
		styleRange.fontStyle |= SWT.BOLD;
		styleRange.fontStyle |= SWT.ITALIC;
		styleRange.font = new Font(display, "Lucida Sans", 15, SWT.NORMAL);
		styleRange.background = new org.eclipse.swt.graphics.Color(display,
				128, 128, 192);

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals(
				"<rt><s ts=\"bi\" fs=\"15\" bc=\"#8080c0\">"
						+ SINGLE_LINE_WITH_RT + "</s></rt>", result);
		Assert.assertEquals(128, ts.getBackgroundColor().getRed());
		Assert.assertEquals(128, ts.getBackgroundColor().getGreen());
		Assert.assertEquals(192, ts.getBackgroundColor().getBlue());
		Assert.assertEquals(15, ts.getFontSize());
		Assert.assertTrue(ts.isBold());
		Assert.assertTrue(ts.isItalic());
		styleRange.background.dispose();
	}

	/**
	 * Multi line with Rich Text to check combination of styling.
	 */
	@Test
	public void testConvert15() {

		text.setText(MULTI_LINE_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = MULTI_LINE_RT.length();
		styleRange.fontStyle |= SWT.BOLD;
		styleRange.fontStyle |= SWT.ITALIC;
		styleRange.font = new Font(display, "Verdana", 15, SWT.NORMAL);
		styleRange.background = new org.eclipse.swt.graphics.Color(display,
				128, 128, 192);

		text.setStyleRange(styleRange);
		TextStyle ts = new TextStyle(styleRange);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals(
				"<rt><s ts=\"bi\" fs=\"15\" bc=\"#8080c0\">"
						+ "This is test for two line rich text"
						+ "</s>"
						+ "\n"
						+ "<s ts=\"bi\" fs=\"15\" bc=\"#8080c0\">"
						+ "The second line for test" 
						+ "</s></rt>",
				result);
		Assert.assertEquals(128, ts.getBackgroundColor().getRed());
		Assert.assertEquals(128, ts.getBackgroundColor().getGreen());
		Assert.assertEquals(192, ts.getBackgroundColor().getBlue());
		Assert.assertEquals(15, ts.getFontSize());
		Assert.assertTrue(ts.isBold());
		Assert.assertTrue(ts.isItalic());
		styleRange.background.dispose();
	}

	/**
	 * Single line with Right, Center and Justify Alignment
	 */
	@Test
	public void testConvert16() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();

		text.setStyleRange(styleRange);
		text.setLineAlignment(0, 1, SWT.RIGHT);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><p align=\"right\">" + SINGLE_LINE_WITH_RT
				+ "</p></rt>", result);
		Assert.assertEquals(SWT.RIGHT, text.getLineAlignment(0));

		text.setLineAlignment(0, 1, SWT.CENTER);
		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();
		Assert.assertEquals("<rt><p align=\"center\">" + SINGLE_LINE_WITH_RT
				+ "</p></rt>", result);
		Assert.assertEquals(SWT.CENTER, text.getLineAlignment(0));

		text.setLineJustify(0, 1, true);
		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();
		Assert.assertEquals("<rt><p align=\"justify\">" + SINGLE_LINE_WITH_RT
				+ "</p></rt>", result);
		Assert.assertTrue(text.getLineJustify(0));
	}

	/**
	 * Multi line with Right, Center and Justify Alignment
	 */
	@Test
	public void testConvert17() {
		text.setText(MULTI_LINE_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = MULTI_LINE_RT.length();

		text.setStyleRange(styleRange);
		text.setLineAlignment(0, 1, SWT.RIGHT);
		text.setLineAlignment(1, 1, SWT.CENTER);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><p align=\"right\">"
				+ "This is test for two line rich text" + "</p>"
				+ "<p align=\"center\">" + "The second line for test"
				+ "</p></rt>", result);
		Assert.assertEquals(SWT.RIGHT, text.getLineAlignment(0));
		Assert.assertEquals(SWT.CENTER, text.getLineAlignment(1));
	}

	/**
	 * Single line Indentation
	 */
	@Test
	public void testConvert18() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();

		text.setStyleRange(styleRange);

		text.setLineIndent(0, 1, 20);

		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><p li=\"20\">" + SINGLE_LINE_WITH_RT + "</p></rt>",
				result);
		Assert.assertEquals(20, text.getLineIndent(0));
	}

	/**
	 * Multi line Indentation
	 */
	@Test
	public void testConvert19() {
		text.setText(MULTI_LINE_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = MULTI_LINE_RT.length();

		text.setStyleRange(styleRange);

		text.setLineIndent(0, 1, 20);
		text.setLineIndent(1, 1, 80);
		
		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();

		Assert.assertEquals("<rt><p li=\"20\">"+ "This is test for two line rich text" +"</p>"+
						 "<p li=\"80\">"+ "The second line for test"+"</p></rt>", result);
		Assert.assertEquals(20, text.getLineIndent(0));
		Assert.assertEquals(80, text.getLineIndent(1));
	}
	
	/**
	 * Single line Random
	 */
	@Test
	public void testConvert20() {
		text.setText(SINGLE_LINE_WITH_RT);

		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = SINGLE_LINE_WITH_RT.length();

		styleRange.font = new Font(display, "Arial", 14, SWT.NORMAL);
		styleRange.fontStyle = SWT.ITALIC;
		text.setLineAlignment(0, 1, SWT.CENTER);
		text.setLineIndent(0, 1, 40);
		styleRange.foreground = new org.eclipse.swt.graphics.Color(display, 101, 200, 250);
		styleRange.background = new org.eclipse.swt.graphics.Color(display, 200, 100, 225);
		text.setStyleRange(styleRange);
		TextStyle style = new TextStyle(styleRange);
		
		
		converter = new StyledTextToRichTextConverter(text);
		result = converter.convert();
		Assert.assertEquals("<rt><p li=\"40\" align=\"center\"><s ts=\"i\" fs=\"14\" bc=\"#c864e1\" fc=\"#65c8fa\">" + SINGLE_LINE_WITH_RT + "</s></p></rt>", result);
		Assert.assertEquals(101, style.getForegroundColor().getRed());
		Assert.assertEquals(225, style.getBackgroundColor().getBlue());
		Assert.assertEquals(40, text.getLineIndent(0));
		
		styleRange.font.dispose();
		styleRange.foreground.dispose();
		styleRange.background.dispose();
	}

}
