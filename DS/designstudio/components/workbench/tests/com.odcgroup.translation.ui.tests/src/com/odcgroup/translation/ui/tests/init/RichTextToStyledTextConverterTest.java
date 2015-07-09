package com.odcgroup.translation.ui.tests.init;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.translation.ui.internal.views.converter.RichTextToStyledTextConverter;

/**
 * @author SSreekanth
 * 
 */
public class RichTextToStyledTextConverterTest {
	StyledText text = null;
	Display display = null;
	RichTextToStyledTextConverter converter = null;
	Shell shell = null;

	private final static String RICH_TEXT_1 = "Test for Single Line Rich Text with no Styles";

	private final static String RICH_TEXT_2 = "<rt>Test for Single Line Rich Text with RT Tags</rt>";
	private final static String EXPECTED_2 = "Test for Single Line Rich Text with RT Tags";

	private final static String RICH_TEXT_3 = "<rt><s ts=\"b\">Test for Single Line Rich Text with bold style</s></rt>";
	private final static String EXPECTED_3 = "Test for Single Line Rich Text with bold style";

	private final static String RICH_TEXT_4 = "<rt><s ts=\"i\">Test for Single Line Rich Text with italic style</s></rt>";
	private final static String EXPECTED_4 = "Test for Single Line Rich Text with italic style";

	private final static String RICH_TEXT_5 = "<rt><s ts=\"u\">Test for Single Line Rich Text with underline style</s></rt>";
	private final static String EXPECTED_5 = "Test for Single Line Rich Text with underline style";

	private final static String RICH_TEXT_6 = "<rt><s ts=\"bui\">Test for Single Line Rich Text with bold, italic and underline style</s></rt>";
	private final static String EXPECTED_6 = "Test for Single Line Rich Text with bold, italic and underline style";

	private final static String RICH_TEXT_7 = "<rt><s ts=\"b\">Test for Multi Line</s><s ts=\"b\">This is second Line</s></rt>";
	private final static String EXPECTED_7 = "Test for Multi LineThis is second Line";

	private final static String RICH_TEXT_8 = "<rt><s ts=\"bu\">Test for Multi Line</s><s ts=\"i\">This is second Line</s></rt>";

	private final static String RICH_TEXT_9 = "<rt><p align=\"right\">Test for Single Line Alignment</p></rt>";
	private final static String EXPECTED_9 = "Test for Single Line Alignment";

	private final static String RICH_TEXT_10 = "<rt><p align=\"right\">Test for Multi Line Different Alignment</p>"
			+ "<p align=\"center\">This is second line</p></rt>";
	private final static String EXPECTED_10 = "Test for Multi Line Different Alignment\nThis is second line";

	private final static String RICH_TEXT_11 = "<rt><p align=\"center\">Test for Multi Line Different Alignment</p>"
			+ "<p align=\"justify\">This is second line</p></rt>";

	private final static String RICH_TEXT_12 = "<rt><s fs=\"14\">Test for Multi Line Different Alignment</s>"
			+ "<s fs=\"14\">This is second line</s></rt>";
	private final static String EXPECTED_12 = "Test for Multi Line Different AlignmentThis is second line";

	private final static String RICH_TEXT_13 = "<rt><p><s fs=\"14\">Test for Multi Line Different Alignment</s></p>"
			+ "<p><s fs=\"16\">This is second line</s></p></rt>";
	private final static String EXPECTED_13 = "Test for Multi Line Different Alignment\nThis is second line";

	private final static String RICH_TEXT_14 = "<rt><p><s ts=\"ui\" fc=\"#804040\">Test for Multi Line Different Alignment</s></p>"
			+ "<p><s ts=\"b\" bc=\"#ff0080\">This is second line</s></p></rt>";

	private final static String RICH_TEXT_15 = "<rt><ul li=\"20\"><li>Test for Multi Line Different Alignment</li></ul>"
			+ "<ol type=\"1\" li=\"20\"><li>This is second line</li></ol></rt>";

	private final static String RICH_TEXT_16 = "<rt><p li=\"60\" wi=\"60\">Test for Multi Line Different Alignment</p>"
			+ "<p li=\"20\" wi=\"20\">This is second line</p></rt>";

	private final static String RICH_TEXT_17 = "<rt><p li=\"60\" wi=\"20\">Test for Multi Line Different Alignment</p>"
			+ "<p li=\"20\" wi=\"60\">This is second line</p></rt>";

	private Set<Color> colors;
	private Set<Font> fonts;
	private Font defaultFont;

	private void createStyledText() {
		shell = new Shell();
		shell.setLayout(new FillLayout());
		shell.setSize(200, 100);
		display = shell.getDisplay();
		text = new StyledText(shell, SWT.BORDER);
		FontData fontData = text.getFont().getFontData()[0];
		fontData.setHeight(11);
		defaultFont = new Font(text.getDisplay(), fontData);

	}

	@Before
	public void setUp() throws Exception {
		createStyledText();
		colors = new HashSet<Color>();
		fonts = new HashSet<Font>();
	}
	
	@After
	public void tearDown() throws Exception {
		for (Color c : colors) {
			c.dispose();
		}
		colors.clear();
		for (Font f : fonts) {
			f.dispose();
		}
		fonts.clear();
	}

	// Single Line - Rich Text with no Styling
	@Test
	public void testConvert1() {
		Assert.assertFalse(RichTextUtils.isRichRext(RICH_TEXT_1));
	}

	@Test
	public void testConvert2() throws Exception {
		Assert.assertTrue(RichTextUtils.isRichRext(RICH_TEXT_2));
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_2, converter);
		Assert.assertEquals(EXPECTED_2, text.getText());
		Assert.assertEquals(0, text.getStyleRanges().length);
	}

	@Test
	public void testConvert3() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
				defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_3, converter);
		Assert.assertEquals(EXPECTED_3, text.getText());
		Assert.assertEquals(1, text.getStyleRanges().length);

		StyleRange sr = text.getStyleRanges()[0];
		Assert.assertEquals(SWT.BOLD, sr.fontStyle);
		Assert.assertEquals(EXPECTED_3.length(), sr.length);
	}

	@Test
	public void testConvert4() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_4, converter);
		Assert.assertEquals(EXPECTED_4, text.getText());
		Assert.assertEquals(1, text.getStyleRanges().length);

		StyleRange sr = text.getStyleRanges()[0];
		Assert.assertEquals(SWT.ITALIC, sr.fontStyle);
		Assert.assertEquals(EXPECTED_4.length(), sr.length);
	}

	@Test
	public void testConvert5() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_5, converter);
		Assert.assertEquals(EXPECTED_5, text.getText());
		Assert.assertEquals(1, text.getStyleRanges().length);

		StyleRange sr = text.getStyleRanges()[0];
		Assert.assertTrue(sr.underline);
		Assert.assertEquals(EXPECTED_5.length(), sr.length);
	}

	@Test
	public void testConvert6() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_6, converter);
		Assert.assertEquals(EXPECTED_6, text.getText());
		Assert.assertEquals(1, text.getStyleRanges().length);

		StyleRange sr = text.getStyleRanges()[0];
		Assert.assertTrue(sr.underline);
		Assert.assertEquals(SWT.BOLD | SWT.ITALIC, sr.fontStyle);
		Assert.assertEquals(EXPECTED_6.length(), sr.length);
	}

	@Test
	public void testConvert7() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_7, converter);
		Assert.assertEquals(EXPECTED_7, text.getText());
		Assert.assertEquals(2, text.getStyleRanges().length);

		StyleRange sr = text.getStyleRanges()[0];
		Assert.assertEquals(SWT.BOLD, sr.fontStyle);

		sr = text.getStyleRanges()[1];
		Assert.assertEquals(SWT.BOLD, sr.fontStyle);
	}

	@Test
	public void testConvert8() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_8, converter);
		Assert.assertEquals(EXPECTED_7, text.getText());
		Assert.assertEquals(2, text.getStyleRanges().length);

		StyleRange sr = text.getStyleRanges()[0];
		Assert.assertEquals(SWT.BOLD, sr.fontStyle);
		Assert.assertTrue(sr.underline);

		sr = text.getStyleRanges()[1];
		Assert.assertEquals(SWT.ITALIC, sr.fontStyle);
	}

	@Test
	public void testConvert9() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_9, converter);
		Assert.assertEquals(EXPECTED_9, text.getText());
		Assert.assertEquals(0, text.getStyleRanges().length);
		Assert.assertEquals(1, text.getLineCount());
		Assert.assertEquals(SWT.RIGHT, text.getLineAlignment(0));
	}

	@Test
	public void testConvert10() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_10, converter);
		Assert.assertEquals(EXPECTED_10, text.getText());
		Assert.assertEquals(0, text.getStyleRanges().length);
		Assert.assertEquals(2, text.getLineCount());
		Assert.assertEquals(SWT.RIGHT, text.getLineAlignment(0));
		Assert.assertEquals(SWT.CENTER, text.getLineAlignment(1));
	}

	@Test
	public void testConvert11() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_11, converter);
		Assert.assertEquals(EXPECTED_10, text.getText());
		Assert.assertEquals(0, text.getStyleRanges().length);
		Assert.assertEquals(2, text.getLineCount());
		Assert.assertEquals(SWT.CENTER, text.getLineAlignment(0));
		Assert.assertTrue(text.getLineJustify(1));
	}

	@Test
	public void testConvert12() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_12, converter);
		Assert.assertEquals(EXPECTED_12, text.getText());
		Assert.assertEquals(2, text.getStyleRanges().length);
		Assert.assertEquals(1, text.getLineCount());
		StyleRange sr = text.getStyleRanges()[0];
		FontData data = sr.font.getFontData()[0];
		Assert.assertEquals(14, data.getHeight());
	}

	@Test
	public void testConvert13() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_13, converter);
		Assert.assertEquals(EXPECTED_13, text.getText());
		Assert.assertEquals(2, text.getStyleRanges().length);
		Assert.assertEquals(2, text.getLineCount());
		StyleRange sr = text.getStyleRanges()[0];
		FontData data = sr.font.getFontData()[0];
		Assert.assertEquals(14, data.getHeight());

		sr = text.getStyleRanges()[1];
		data = sr.font.getFontData()[0];
		Assert.assertEquals(16, data.getHeight());
	}

	@Test
	public void testConvert14() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_14, converter);
		Assert.assertEquals(EXPECTED_13, text.getText());
		Assert.assertEquals(2, text.getStyleRanges().length);
		Assert.assertEquals(2, text.getLineCount());

		StyleRange sr = text.getStyleRanges()[0];
		Assert.assertEquals(128, sr.foreground.getRed());
		Assert.assertEquals(64, sr.foreground.getGreen());
		Assert.assertEquals(64, sr.foreground.getBlue());
		Assert.assertTrue(sr.underline);
		Assert.assertEquals(SWT.ITALIC, sr.fontStyle);

		sr = text.getStyleRanges()[1];
		Assert.assertEquals(255, sr.background.getRed());
		Assert.assertEquals(0, sr.background.getGreen());
		Assert.assertEquals(128, sr.background.getBlue());
		Assert.assertEquals(SWT.BOLD, sr.fontStyle);

	}

	@Test
	public void testConvert15() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_15, converter);
		Assert.assertEquals(EXPECTED_13, text.getText());
		Assert.assertEquals(2, text.getLineCount());
		Assert.assertEquals(ST.BULLET_DOT, text.getLineBullet(0).type);
		Assert.assertEquals(ST.BULLET_NUMBER | ST.BULLET_TEXT,
				text.getLineBullet(1).type);
		Assert.assertEquals(".", text.getLineBullet(1).text);
	}
	
	@Test
	public void testConvert16() throws Exception {

		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_16, converter);
		Assert.assertEquals(EXPECTED_13, text.getText());
		Assert.assertEquals(2, text.getLineCount());
		Assert.assertEquals(60, text.getLineIndent(0));
		Assert.assertEquals(60, text.getLineWrapIndent(0));
		Assert.assertEquals(20, text.getLineIndent(1));
		Assert.assertEquals(20, text.getLineWrapIndent(1));
	}


	@Test
	public void testConvert17() throws Exception {
		converter = new RichTextToStyledTextConverter(text, display,
					defaultFont, fonts, colors);
		RichTextUtils.parseRichText(RICH_TEXT_17, converter);
		Assert.assertEquals(EXPECTED_13, text.getText());
		Assert.assertEquals(2, text.getLineCount());
		Assert.assertEquals(60, text.getLineIndent(0));
		Assert.assertEquals(20, text.getLineWrapIndent(0));
		Assert.assertEquals(20, text.getLineIndent(1));
		Assert.assertEquals(60, text.getLineWrapIndent(1));
	}

}
