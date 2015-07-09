package com.odcgroup.translation.core.tests.richtext;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.translation.core.richtext.RichTextUtils;

/**
 * @author atr
 */
public class TranslationRichTextUtilsTest {

	private IProject project;

	@Before
	public void setUp() throws Exception {

		IProjectDescription description = null;

		// create a normal project
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("project");
		project.create(null);
		project.open(null);
		description = project.getDescription();
		description.setNatureIds(new String[] { "org.eclipse.jdt.core.javanature" });
		project.setDescription(description, null);

	}

	@After
	public void tearDown() throws Exception {
		if (project != null) {
			project.delete(true, null);
		}
	}

	/**
	 * Tests the capability to recognize a string that can contains rich text content. 
	 */
	@Test
	public void testIsRichText() {
		
		final String MESSAGE_RICHTEXT = "The string must be recognized as rich text";
		final String MESSAGE_NON_RICHTEXT = "The string must not be recognized as rich text";

		// non rich texts
		Assert.assertFalse(MESSAGE_NON_RICHTEXT, RichTextUtils.isRichRext("Hello world"));
		Assert.assertFalse(MESSAGE_NON_RICHTEXT, RichTextUtils.isRichRext("Hello & world"));
		Assert.assertFalse(MESSAGE_NON_RICHTEXT, RichTextUtils.isRichRext("+ \" * ç % & / ( ) = ?  ` ^ ~ ' ´ è é à < > \\ "));
		Assert.assertFalse(MESSAGE_NON_RICHTEXT, RichTextUtils.isRichRext("Hello >> world"));
		Assert.assertFalse(MESSAGE_NON_RICHTEXT, RichTextUtils.isRichRext("<rt>Hello world"));
		Assert.assertFalse(MESSAGE_NON_RICHTEXT, RichTextUtils.isRichRext("Hello world</rt>"));
		
		// non rich texts
		Assert.assertTrue(MESSAGE_RICHTEXT, RichTextUtils.isRichRext("<rt>Hello world</rt>"));
		Assert.assertTrue(MESSAGE_RICHTEXT, RichTextUtils.isRichRext("<rt>Hello &amp; world</rt>"));
		Assert.assertTrue(MESSAGE_RICHTEXT, RichTextUtils.isRichRext("<rt>Hello &gt;&gt; world</rt>"));

	}
	
	@Test
	public void testEscapeNonRichText() {
		final String NON_RICH_TEXT = "<rt>Hello</rt>";
		final String EXPECTED_TEXT = "&lt;rt&gt;Hello&lt;/rt&gt;";
		Assert.assertTrue("Non richtext not properly escaped", EXPECTED_TEXT.equals(RichTextUtils.escapeRichTextTag(NON_RICH_TEXT)));
	}

	/**
	 * Tests the capability to validate the syntax of a rich text string.
	 */
	@Test
	public void testUnescapeNonRichText() {
		final String NON_RICH_TEXT = "&lt;rt&gt;Hello&lt;/rt&gt;";
		final String EXPECTED_TEXT = "<rt>Hello</rt>";
		Assert.assertTrue("Non richtext not properly unescaped", EXPECTED_TEXT.equals(RichTextUtils.unescapeRichTextTags(NON_RICH_TEXT)));
	}
	
	@Test
	public void testGrammarWithEmptyContent() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt></rt>");
	}
	
	@Test
	public void testGrammarWithTextStyles() throws SAXException, IOException {

		RichTextUtils.validateRichText("<rt><s ts=\"b\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"i\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"u\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bi\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bu\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ib\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"iu\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ub\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ui\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"biu\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bui\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ibu\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ubi\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"iub\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"uib\"/></rt>");

		// empty content - normal form
		RichTextUtils.validateRichText("<rt><s ts=\"b\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"i\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"u\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bi\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bu\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ib\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"iu\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ub\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ui\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"biu\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bui\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ibu\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ubi\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"iub\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"uib\"></s></rt>");

		
		// non empty content
		RichTextUtils.validateRichText("<rt><s ts=\"b\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"i\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"u\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bi\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bu\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ib\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"iu\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ub\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ui\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"biu\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"bui\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ibu\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"ubi\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"iub\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s ts=\"uib\">xxx</s></rt>");
	}
	

	@Test
	public void testGrammarWithColors() throws SAXException, IOException {
		
		// empty content short form
		RichTextUtils.validateRichText("<rt><s fc=\"#ff0000\"/></rt>");
		RichTextUtils.validateRichText("<rt><s fc=\"#00ff00\"/></rt>");
		RichTextUtils.validateRichText("<rt><s fc=\"#0000ff\"/></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#ff0000\"/></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#00ff00\"/></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#0000ff\"/></rt>");

		// empty content - normal form
		RichTextUtils.validateRichText("<rt><s fc=\"#ff0000\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s fc=\"#00ff00\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s fc=\"#0000ff\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#ff0000\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#00ff00\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#0000ff\"></s></rt>");

		// non empty content
		RichTextUtils.validateRichText("<rt><s fc=\"#ff0000\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s fc=\"#00ff00\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s fc=\"#0000ff\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#ff0000\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#00ff00\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s bc=\"#0000ff\">xxx</s></rt>");
	}

	@Test
	public void testGrammarWithFontName() throws SAXException, IOException {
		
		// empty content short form
		RichTextUtils.validateRichText("<rt><s ff=\"Arial\"/></rt>");
		RichTextUtils.validateRichText("<rt><s ff=\"Arial\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s ff=\"Arial\">xxx</s></rt>");
	}

	@Test
	public void testGrammarWithFontSize() throws SAXException, IOException {
		
		RichTextUtils.validateRichText("<rt><s fs=\"12\"/></rt>");
		RichTextUtils.validateRichText("<rt><s fs=\"12\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s fs=\"12\">xxx</s></rt>");
	}

	@Test
	public void testGrammarWithFontNameAndFontSize() throws SAXException, IOException {
		
		// empty content short form
		RichTextUtils.validateRichText("<rt><s ff=\"Arial\" fs=\"12\"/></rt>");
		RichTextUtils.validateRichText("<rt><s fs=\"12\"    ff=\"Arial\"/></rt>");

		// empty content - normal form
		RichTextUtils.validateRichText("<rt><s ff=\"Arial\" fs=\"12\"></s></rt>");
		RichTextUtils.validateRichText("<rt><s fs=\"12\"    ff=\"Arial\"></s></rt>");

		// non empty content
		RichTextUtils.validateRichText("<rt><s ff=\"Arial\" fs=\"12\">xxx</s></rt>");
		RichTextUtils.validateRichText("<rt><s fs=\"12\"    ff=\"Arial\">xxx</s></rt>");
		
	}

	@Test
	public void testGrammarWithLineAlignment() throws SAXException, IOException {
		
		// empty paragraphs - short form
		RichTextUtils.validateRichText("<rt><p align=\"left\"/></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"right\"/></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"center\"/></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"justify\"/></rt>");
		
		// empty paragraphs - normal form
		RichTextUtils.validateRichText("<rt><p align=\"left\"></p></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"right\"></p></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"center\"></p></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"justify\"></p></rt>");

		// non empty paragraphs
		RichTextUtils.validateRichText("<rt><p align=\"left\">xxx</p></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"right\">xxx</p></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"center\">xxx</p></rt>");
		RichTextUtils.validateRichText("<rt><p align=\"justify\">xxx</p></rt>");
	}
	
	@Test
	public void testGrammarWithLineIndentationt() throws SAXException, IOException {
		
		// empty paragraphs - short form
		RichTextUtils.validateRichText("<rt><p li=\"20\"/></rt>");
		RichTextUtils.validateRichText("<rt><p wi=\"20\"/></rt>");
		RichTextUtils.validateRichText("<rt><p li=\"20\" wi=\"20\"/></rt>");
		
		// empty paragraphs - normal form
		RichTextUtils.validateRichText("<rt><p li=\"20\"></p></rt>");
		RichTextUtils.validateRichText("<rt><p wi=\"20\"></p></rt>");
		RichTextUtils.validateRichText("<rt><p li=\"20\" wi=\"20\"></p></rt>");

		// non empty paragraphs
		RichTextUtils.validateRichText("<rt><p li=\"20\">xxx</p></rt>");
		RichTextUtils.validateRichText("<rt><p wi=\"20\">xxx</p></rt>");
		RichTextUtils.validateRichText("<rt><p li=\"20\" wi=\"20\">xxx</p></rt>");
	}

	@Test
	public void testGrammarWithLineAndNestedStyles() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><p><s ts=\"bu\" fc=\"#ff0000\" bc=\"#ff0000\">xxx</s></p></rt>");
	}

	@Test
	public void testGrammarWithMultipleLines() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><p>line 1</p><p>line 2</p></rt>");
	}
	
	@Test
	public void testGrammarWithMultipleLinesAndNestedStyles() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><p>the <s ts=\"bui\">big</s> dog</p><p>the <s fc=\"#ff0000\">big</s> dog</p></rt>");
	}	
	
	@Test
	public void testGrammarWithUnorderedList() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ul><li>line 1</li><li>line 2</li><li>line 3</li></ul></rt>");
	}	

	@Test
	public void testGrammarWithIndentedUnorderedList() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ul li=\"20\"><li>line 1</li><li>line 2</li><li>line 3</li></ul></rt>");
	}	

	@Test
	public void testGrammarWithUnorderedListAndBoldLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ul><li><s ts=\"b\">line 1</s></li></ul></rt>");
	}	

	@Test
	public void testGrammarWithUnorderedListAndItalicLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ul><li><s ts=\"i\">line 1</s></li></ul></rt>");
	}	

	@Test
	public void testGrammarWithUnorderedListAndUnderlineLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ul><li><s ts=\"u\">line 1</s></li></ul></rt>");
	}	

	@Test
	public void testGrammarWithUnorderedListAndBoldItalicUnderlineLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ul><li><s ts=\"biu\">line 1</s></li></ul></rt>");
	}	

	@Test
	public void testGrammarWithUnorderedListAndColoredLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ul><li><s fc=\"#ff0000\" bc=\"#ff0000\">line 1</s></li></ul></rt>");
	}	

	@Test
	public void testGrammarWithOrderedList() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ol><li>line 1</li><li>line 2</li><li>line 3</li></ol></rt>");
	}	

	@Test
	public void testGrammarWithOrderedListAndStartNumber() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ol start=\"4\"><li>line 1</li><li>line 2</li><li>line 3</li></ol></rt>");
	}	

	@Test
	public void testGrammarWithIndentedOrderedList() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ol li=\"20\"><li>line 1</li><li>line 2</li><li>line 3</li></ol></rt>");
	}	
	
	@Test
	public void testGrammarWithOrderedListAndBoldLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ol><li><s ts=\"b\">line 1</s></li></ol></rt>");
	}	

	@Test
	public void testGrammarWithOrderedListAndItalicLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ol><li><s ts=\"i\">line 1</s></li></ol></rt>");
	}	

	@Test
	public void testGrammarWithOrderedListAndUnderlineLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ol><li><s ts=\"u\">line 1</s></li></ol></rt>");
	}	

	@Test
	public void testGrammarWithOrderedListAndBoldItalicUnderlineLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ol><li><s ts=\"biu\">line 1</s></li></ol></rt>");
	}	

	@Test
	public void testGrammarWithOrderedListAndColoredLine() throws SAXException, IOException {
		RichTextUtils.validateRichText("<rt><ol><li><s fc=\"#ff0000\" bc=\"#ff0000\">line 1</s></li></ol></rt>");
	}	

}
