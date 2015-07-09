package com.odcgroup.translation.ui.tests.init;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.translation.core.richtext.RichTextUtils;

/**
 * @author SSreekanth
 *
 */
public class RichTextToHTMLConverterTest {

	private static String RICH_TEXT_1 = "Test for Single Line Rich Text with no Styles";
	private static String EXPECTED_1 = "Test for Single Line Rich Text with no Styles";
	
	private static String RICH_TEXT_2 = "<rt>Test for Single Line Rich Text with RT Tags</rt>";
	private static String EXPECTED_2 = "Test for Single Line Rich Text with RT Tags";
	
	private static String RICH_TEXT_3 = "<rt><s ts=\"b\">Test for Single Line Rich Text with bold style</s></rt>";
	private static String EXPECTED_3 = "<b>Test for Single Line Rich Text with bold style</b>";
	
	private static String RICH_TEXT_4 = "<rt><s ts=\"i\">Test for Single Line Rich Text with italic style</s></rt>";
	private static String EXPECTED_4 = "<i>Test for Single Line Rich Text with italic style</i>";
	
	private static String RICH_TEXT_5 = "<rt><s ts=\"u\">Test for Single Line Rich Text with underline style</s></rt>";
	private static String EXPECTED_5 = "<u>Test for Single Line Rich Text with underline style</u>";
	
	private static String RICH_TEXT_6 = "<rt><s ts=\"bui\">Test for Single Line Rich Text with bold, italic and underline style</s></rt>";
	private static String EXPECTED_6 = "<b><i><u>Test for Single Line Rich Text with bold, italic and underline style</u></i></b>"; 
			
	private static String RICH_TEXT_7 = "<rt><s ts=\"b\">Test for Multi Line</s><s ts=\"b\">This is second Line</s></rt>";
	private static String EXPECTED_7 = "<b>Test for Multi Line</b><b>This is second Line</b>";
	
	private static String RICH_TEXT_8 = "<rt><s ts=\"bu\">Test for Multi Line</s><s ts=\"i\">This is second Line</s></rt>";
	private static String EXPECTED_8 = "<b><u>Test for Multi Line</u></b><i>This is second Line</i>";
	
	private static String RICH_TEXT_9 = "<rt><p align=\"right\">Test for Single Line Alignment</p></rt>";
	private static String EXPECTED_9 = "<p align=\"right\">Test for Single Line Alignment</p>";
	
	private static String RICH_TEXT_10 = "<rt><p align=\"right\">Test for Multi Line Different Alignment</p>"+
											 "<p align=\"center\">This is second line</p></rt>";
	private static String EXPECTED_10 = "<p align=\"right\">Test for Multi Line Different Alignment</p>" + 
											 "<p align=\"center\">This is second line</p>";
	
	private static String RICH_TEXT_11 = "<rt><p align=\"center\">Test for Multi Line Different Alignment</p>" +
											  "<p align=\"justify\">This is second line</p></rt>";
	private static String EXPECTED_11 = "<p align=\"center\">Test for Multi Line Different Alignment</p>"+
											  "<p align=\"justify\">This is second line</p>";
	
	private static String RICH_TEXT_12 = "<rt><ol type=\"1\" li=\"20\"><li><p align=\"center\">Test for Multi Line Different Alignment</p>" + 
											 "</li></ol><ul li=\"20\"><li><p align=\"justify\">This is second line</p></li></ul></rt>";
	private static String EXPECTED_12 = "<ol style=\"padding-left:20px;\"><li><span align=\"center\">Test for Multi Line Different Alignment</span>" + 
											 "</li></ol><ul style=\"padding-left:20px;\"><li><span align=\"justify\">This is second line</span></li></ul>";
	
	private static String RICH_TEXT_13 = "<rt><s ts=\"b\" bc=\"#ff0080\">There are two ways to use this widget when specifying</s>" + 
											 "<s ts=\"b\"> text style information. You may use the API that is defined for StyledText or " +
											 "you may define your own LineStyleListener.</s>" + 
											 "<p li=\"20\" wi=\"20\"><s ts=\"ui\" fs=\"14\">If you define your own listener, you will be responsible" + 
											 " for maintaining the text style information for the</s> </p><p align=\"center\">widget. " + 
											 "IMPORTANT: You may not define your own listener and use the StyledText API. </p><ul li=\"20\"><li>" + 
											 "<s fc=\"#0080c0\">The following StyledText API is not supported if you have defined a LineStyleListener:</s>" + 
											 "</li></ul></rt>";
	private static String EXPECTED_13 = "<span style=\"background-color:rgb(255,0,128);\"><b>There are two ways to use this widget when specifying</b>" + 
										"</span><b> text style information. You may use the API that is defined for StyledText or you may define your" + 
										" own LineStyleListener.</b><p style=\"padding-left:20px;\"><span style=\"font-size:14px;\"><i><u>" + 
										"If you define your own listener, you will be responsible for maintaining the text style information for the</u></i></span>" + 
										" </p><p align=\"center\">widget. IMPORTANT: You may not define your own listener and use the StyledText API. " + 
										"</p><ul style=\"padding-left:20px;\"><li><span style=\"color:rgb(0,128,192);\">The following StyledText API is not " + 
										"supported if you have defined a LineStyleListener:</span></li></ul>";

	private static String RICH_TEXT_14 = "<rt><s ts=\"b\" bc=\"#ff0080\">There are two ways to use this widget when specifying</s>" + 
			 "<s ts=\"b\"> text style information. You may use the API that is defined for StyledText or " +
			 "you may define your own LineStyleListener.</s>" + 
			 "<p li=\"60\" wi=\"20\"><s ts=\"ui\" fs=\"14\">If you define your own listener, you will be responsible" + 
			 " for maintaining the text style information for the</s> </p><p align=\"center\">widget. " + 
			 "IMPORTANT: You may not define your own listener and use the StyledText API. </p><ul li=\"20\"><li>" + 
			 "<s fc=\"#0080c0\">The following StyledText API is not supported if you have defined a LineStyleListener:</s>" + 
			 "</li></ul></rt>";
	private static String EXPECTED_14 = "<span style=\"background-color:rgb(255,0,128);\"><b>There are two ways to use this widget when specifying</b>" + 
		"</span><b> text style information. You may use the API that is defined for StyledText or you may define your" + 
		" own LineStyleListener.</b><p style=\"text-indent:40px;padding-left:20px;\"><span style=\"font-size:14px;\"><i><u>" + 
		"If you define your own listener, you will be responsible for maintaining the text style information for the</u></i></span>" + 
		" </p><p align=\"center\">widget. IMPORTANT: You may not define your own listener and use the StyledText API. " + 
		"</p><ul style=\"padding-left:20px;\"><li><span style=\"color:rgb(0,128,192);\">The following StyledText API is not " + 
		"supported if you have defined a LineStyleListener:</span></li></ul>";
	
	private static String RICH_TEXT_15 = "<rt><s ts=\"b\" bc=\"#ff0080\">There are two ways to use this widget when specifying</s>" + 
			 "<s ts=\"b\"> text style information. You may use the API that is defined for StyledText or " +
			 "you may define your own LineStyleListener.</s>" + 
			 "<p li=\"20\" wi=\"60\"><s ts=\"ui\" fs=\"14\">If you define your own listener, you will be responsible" + 
			 " for maintaining the text style information for the</s> </p><p align=\"center\">widget. " + 
			 "IMPORTANT: You may not define your own listener and use the StyledText API. </p><ul li=\"20\"><li>" + 
			 "<s fc=\"#0080c0\">The following StyledText API is not supported if you have defined a LineStyleListener:</s>" + 
			 "</li></ul></rt>";
	private static String EXPECTED_15 = "<span style=\"background-color:rgb(255,0,128);\"><b>There are two ways to use this widget when specifying</b>" + 
		"</span><b> text style information. You may use the API that is defined for StyledText or you may define your" + 
		" own LineStyleListener.</b><p style=\"text-indent:-40px;padding-left:60px;\"><span style=\"font-size:14px;\"><i><u>" + 
		"If you define your own listener, you will be responsible for maintaining the text style information for the</u></i></span>" + 
		" </p><p align=\"center\">widget. IMPORTANT: You may not define your own listener and use the StyledText API. " + 
		"</p><ul style=\"padding-left:20px;\"><li><span style=\"color:rgb(0,128,192);\">The following StyledText API is not " + 
		"supported if you have defined a LineStyleListener:</span></li></ul>";

	/**
	 * Rich Text To HTML Conversion for text with no Rich Text Tags 
	 */
	@Test
	public void testConvert1() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_1);
			Assert.assertEquals(EXPECTED_1, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text with only Rich Text Tags
	 */
	@Test
	public void testConvert2() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_2);
			Assert.assertEquals(EXPECTED_2, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 *	Rich Text To HTML Conversion for text with BOLD Content
	 */
	@Test
	public void testConvert3() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_3);
			Assert.assertEquals(EXPECTED_3, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text with ITALIC Content
	 */
	@Test
	public void testConvert4() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_4);
			Assert.assertEquals(EXPECTED_4, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text with UNDERLINE Content
	 */
	@Test
	public void testConvert5() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_5);
			Assert.assertEquals(EXPECTED_5, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text with BOLD, ITALIC and UNDERLINE Content
	 */
	@Test
	public void testConvert6() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_6);
			Assert.assertEquals(EXPECTED_6, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text of multiple lines with BOLD Content
	 */
	@Test
	public void testConvert7() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_7);
			Assert.assertEquals(EXPECTED_7, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text of multiple lines with varied BOLD, ITALIC and UNDERLINE content
	 */
	@Test
	public void testConvert8() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_8);
			Assert.assertEquals(EXPECTED_8, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text with changed Alignment to RIGHT
	 */
	@Test
	public void testConvert9() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_9);
			Assert.assertEquals(EXPECTED_9, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text with varied Alignment of RIGHT and CENTER
	 */
	@Test
	public void testConvert10() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_10);
			Assert.assertEquals(EXPECTED_10, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Rich Text To HTML Conversion for text with varied Alignment of CENTER and JUSTIFY
	 */
	@Test
	public void testConvert11() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_11);
			Assert.assertEquals(EXPECTED_11, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Rich Text To HTML Conversion for text with varied styling of Alignment, Bullet and Numbering
	 */
	@Test
	public void testConvert12() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_12);
			Assert.assertEquals(EXPECTED_12, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Rich Text To HTML Conversion for text with varied styling of Coloring, Font, Indent, Alignment, Bullet and Numbering
	 */
	@Test
	public void testConvert13() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_13);
			Assert.assertEquals(EXPECTED_13, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rich Text To HTML Conversion for text with varied styling of Coloring, Font, Indent, Alignment, Bullet and Numbering
	 */
	@Test
	public void testConvert14() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_14);
			Assert.assertEquals(EXPECTED_14, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Rich Text To HTML Conversion for text with varied styling of Coloring, Font, Indent, Alignment, Bullet and Numbering
	 */
	@Test
	public void testConvert15() {
		try {
			String result = RichTextUtils.convertRichTextToHTML(RICH_TEXT_15);
			Assert.assertEquals(EXPECTED_15, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
