package com.temenos.t24.tools.eclipse.basic.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;

public class StringUtilTest {

    protected final Logger log = LoggerFactory.getLogger(StringUtilTest.class);

    @Test
	public void testIsComment() {
        assertTrue(StringUtil.isComment("* TEST"));
        assertTrue(StringUtil.isComment("*TEST"));
        assertTrue(StringUtil.isComment(" *TEST"));
        assertFalse(StringUtil.isComment("a * TEST"));
        assertFalse(StringUtil.isComment(" A = B * C"));
        assertFalse(StringUtil.isComment("   "));
        assertTrue(StringUtil.isComment(";*TEST"));
        assertTrue(StringUtil.isComment(" ; * TEST"));
        assertFalse(StringUtil.isComment("a ; * TEST"));
        assertTrue(StringUtil.isComment("REM TEST"));
        assertTrue(StringUtil.isComment(" REM TEST  "));
        assertFalse(StringUtil.isComment("a REM TEST  "));
        assertFalse(StringUtil.isComment(" REMTEST "));
    }

    @Test
	public void testIsWithinQuotes() {
        StringUtil su = new StringUtil();
        assertTrue(su.isWithinQuotes("\"inside\"", 1));
        assertTrue(su.isWithinQuotes("\'inside\'", 1));
        assertTrue(su.isWithinQuotes("outside \"inside\"", 11));
        assertFalse(su.isWithinQuotes("outside", 1));
        assertFalse(su.isWithinQuotes("outside \"inside\"", 1));
    }

    @Test
	public void testIsBasicFile() {
        StringUtil su = new StringUtil();
        assertTrue(su.isBasicFile("TEST.b"));
        assertTrue(su.isBasicFile("TEST.B"));
        assertTrue(su.isBasicFile("TEST.MODULE.B.B"));
        assertFalse(su.isBasicFile("TEST.MODULE.B.x"));
        assertFalse(su.isBasicFile("TEST.BASIC"));
        assertFalse(su.isBasicFile("TEST"));
    }

    @Test
	public void testInputStream() {
        StringUtil su = new StringUtil();
        String test = "TEST STRING";
        assertTrue(test.equals(su.getStringFromInputStream(su.getInputStreamFromString(test))));
    }

    @Test
	public void testRemoveFileExtension() {
        assertEquals("test", StringUtil.removeBasicExtension("test" + PluginConstants.LOCAL_BASIC_DOT_PREFIX));
        assertEquals("test", StringUtil.removeBasicExtension("test"));
        assertEquals("test" + PluginConstants.LOCAL_BASIC_DOT_PREFIX, StringUtil.removeBasicExtension("test"
                + PluginConstants.LOCAL_BASIC_DOT_PREFIX + PluginConstants.LOCAL_BASIC_DOT_PREFIX));
    }

    @Test
	public void testIsNewThan() {
        StringUtil su = new StringUtil();
        assertFalse(su.isNewThan("1.1.1", "1.1.1"));
        assertTrue(su.isNewThan("2.1.1", "1.1.1"));
        assertFalse(su.isNewThan("1.1.1", "2.1.1"));
        assertTrue(su.isNewThan("1.2.1", "1.1.1"));
        assertFalse(su.isNewThan("1.1.1", "1.2.1"));
        assertTrue(su.isNewThan("1.1.2", "1.1.1"));
        assertFalse(su.isNewThan("1.1.1", "1.1.2"));
        /** more digits digits */
        assertTrue(su.isNewThan("11.11.11", "11.11.10"));
        assertTrue(su.isNewThan("11.11.11", "11.10.11"));
        assertTrue(su.isNewThan("11.11.11", "10.11.11"));
        // Using format X.Y.Z.vddMMyyHHmmss
        assertTrue(su.isNewThan("10.10.10.v191107093726", "10.10.10.v191107093725"));
        assertFalse(su.isNewThan("10.10.10.v191107093726", "10.10.10.v191107093726"));
        assertFalse(su.isNewThan("10.10.10.v191107093725", "10.10.10.v191107093726"));
        assertTrue(su.isNewThan("1.1.2.v191107093725", "1.1.1.v191107093726"));
        assertFalse(su.isNewThan("1.1.1.v191107093726", "1.1.2.v191107093725"));
        assertTrue(su.isNewThan("1.1.1.v191107093725", "1.1.1"));
        assertFalse(su.isNewThan("1.1.1", "1.1.1.v191107093725"));
        assertFalse(su.isNewThan("1.1.1.v191107093725", "1.1.2"));
        assertTrue(su.isNewThan("1.1.2", "1.1.1.v191107093725"));
        // Using format X.Y.Z.ddMMyyHHmmss
        assertTrue(su.isNewThan("1.2.1.100907151843", "1.2.1.100907101843"));
        assertTrue(su.isNewThan("1.2.2.100907151843", "1.2.1.100907101843"));
        assertFalse(su.isNewThan("1.2.1.100907151843", "1.2.2.100907151843"));
        assertTrue(su.isNewThan("2.2.2.100907151843", "1.2.1.100907101843"));
        // Using format X.Y.Z.ddMMyyHHmmss & X.Y.Z
        assertTrue(su.isNewThan("1.2.2", "1.2.1.100907101843"));
        assertFalse(su.isNewThan("1.2.2", "1.2.2.100907101843"));
        assertTrue(su.isNewThan("1.2.1.1", "1.2.1"));

    }

    @Test
	public void testPad() {
        StringUtil su = new StringUtil();
        assertTrue("TEST    ".equals(su.pad("TEST", 8, " ")));
        assertTrue("    ".equals(su.pad("", 4, " ")));
        assertTrue("TEST".equals(su.pad("TEST", 2, " ")));
        assertTrue(su.pad("Description:", 15, " ").length() == 15);
    }

    @Test
	public void testRemoveExtraSpaces() {
        StringUtil su = new StringUtil();
        assertTrue("TEST WORD".equals(su.removeExtraSpaces("TEST    WORD")));
        assertTrue(" TEST WORD ".equals(su.removeExtraSpaces("  TEST  \r\nWORD  \r\n")));
        assertTrue("".equals(su.removeExtraSpaces("")));
    }

    @Test
	public void testLeftTrim() {
        StringUtil su = new StringUtil();
        assertTrue("TEST  ".equals(su.trimLeft("  TEST  ")));
        assertTrue("".equals(su.trimLeft("")));
        assertTrue("".equals(su.trimLeft("   ")));
        assertTrue(".".equals(su.trimLeft(".")));
    }

    @Test
	public void testContainsWord() {
        StringUtil su = new StringUtil();
        assertTrue(su.containsWholeWord(" TEST ", "TEST"));
        assertTrue(su.containsWholeWord(" TEST", "TEST"));
        assertTrue(su.containsWholeWord("TEST ", "TEST"));
        assertTrue(su.containsWholeWord("TEST", "TEST"));
        assertFalse(su.containsWholeWord("MODULE.TEST", "TEST"));
        assertFalse(su.containsWholeWord("TEST.", "TEST"));
        assertTrue(su.containsWholeWord("$INSERT COMMON", "\\$INSERT"));
    }

    @Test
	public void testTrim() {
        StringUtil su = new StringUtil();
        assertTrue("TEST   WORD".equals(su.trim("  TEST   WORD  ")));
        assertTrue("".equals(su.trim("")));
    }

    @Test
	public void testAtLeastOneEmpty() {
        StringUtil su = new StringUtil();
        assertTrue(su.atLeastOneEmpty(new String[] { "test", "" }));
        assertTrue(su.atLeastOneEmpty(new String[] { "test", null, null }));
        assertFalse(su.atLeastOneEmpty(new String[] { "test", "test" }));
    }

    @Test
	public void testIsEmpty() {
        assertTrue(StringUtil.isEmpty(""));
        assertTrue(StringUtil.isEmpty("  "));
        assertTrue(StringUtil.isEmpty(" \r\n"));
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isEmpty(" \t "));
        assertFalse(StringUtil.isEmpty("TEST"));
    }

    @Test
	public void testFinishesWithNewLine() {
        StringUtil su = new StringUtil();
        assertTrue(su.finishesWithNewLine("TEST \r\n"));
        assertTrue(su.finishesWithNewLine("TEST \n"));
        assertFalse(su.finishesWithNewLine("TEST"));
        assertFalse(su.finishesWithNewLine(""));
        assertFalse(su.finishesWithNewLine("   "));
    }

    @Test
	public void testGetBeginningOfNextWord() {
        StringUtil su = new StringUtil();
        assertEquals(4, su.getBeginningOfNextWord("BLA BLU", 0));
        assertEquals(-1, su.getBeginningOfNextWord("BLA BLU", 5));
        assertEquals(0, su.getBeginningOfNextWord("BLA BLU", -2));
        assertEquals(-1, su.getBeginningOfNextWord("BLA BLU", 30));
        assertEquals(5, su.getBeginningOfNextWord(" BLA BLU", 1));
        assertEquals(1, su.getBeginningOfNextWord(" BLA BLU", 0));
        assertEquals(1, su.getBeginningOfNextWord(" BLA BLU", -1));
        assertEquals(5, su.getBeginningOfNextWord("BLA\r\nBLU", 0));
        assertEquals(-1, su.getBeginningOfNextWord("BLA\r\nBLU", 6));
        assertEquals(7, su.getBeginningOfNextWord("BLA \r\n BLU", 0));
        assertEquals(0, su.getBeginningOfNextWord("BLA \r\n BLU", -1));
        assertEquals(-1, su.getBeginningOfNextWord("", 0));
        assertEquals(-1, su.getBeginningOfNextWord("   ", 0));
        assertEquals(-1, su.getBeginningOfNextWord("   ", -1));
        assertEquals(-1, su.getBeginningOfNextWord("\r\n", 0));
        assertEquals(-1, su.getBeginningOfNextWord("\n", 0));
    }

    @Test
	public void testMin() {
        StringUtil su = new StringUtil();
        assertEquals(0, su.min());
        assertEquals(0, su.min(0, Integer.MAX_VALUE));
        assertEquals(-1, su.min(0, Integer.MAX_VALUE, -1));
        assertEquals(Integer.MIN_VALUE, su.min(0, Integer.MAX_VALUE, -1, Integer.MIN_VALUE));
    }

    @Test
	public void testMax() {
        StringUtil su = new StringUtil();
        assertEquals(0, su.max());
        assertEquals(-1, su.max(-1, Integer.MIN_VALUE));
        assertEquals(0, su.max(-1, Integer.MIN_VALUE, 0));
        assertEquals(Integer.MAX_VALUE, su.max(-1, Integer.MIN_VALUE, 0, Integer.MAX_VALUE));
    }

    @Test
	public void testIsFirstCharOfLine() {
        StringUtil su = new StringUtil();
        IDocument doc = new Document();
        int offset = 6;
        doc.set("GOTO GOTO ");
        assertTrue(su.isFirstCharOfLine(doc, offset, 'G'));
        doc.set("  GOTO GOTO ");
        assertTrue(su.isFirstCharOfLine(doc, offset, 'G'));
        doc.set("  LOTO GOTO ");
        assertFalse(su.isFirstCharOfLine(doc, offset, 'G'));
        doc.set(" . GOTO GOTO ");
        assertFalse(su.isFirstCharOfLine(doc, offset, 'G'));
    }

    @Test
	public void testGetFirstWordInLine() {
        StringUtil su = new StringUtil();
        IDocument doc = new Document();
        doc.set("WORD TEST");
        assertTrue("WORD".equals(su.getFirstWordInLine(doc, 0)));
        doc.set("  WORD TEST");
        assertTrue("WORD".equals(su.getFirstWordInLine(doc, 5)));
        doc.set(". WORD TEST");
        assertTrue(".".equals(su.getFirstWordInLine(doc, 5)));
        doc.set(" . WORD TEST");
        assertTrue(".".equals(su.getFirstWordInLine(doc, 5)));
        doc.set("  ");
        assertTrue("".equals(su.getFirstWordInLine(doc, 0)));
        doc.set("  TEST.LABEL:");
        assertTrue("TEST.LABEL:".equals(su.getFirstWordInLine(doc, 0)));
    }

    @Test
	public void testGetFirstWordInLine2() {
        StringUtil su = new StringUtil();
        assertTrue("WORD".equals(su.getFirstWordInLine("WORD TEST")));
        assertTrue("WORD".equals(su.getFirstWordInLine("  WORD TEST")));
        assertTrue(".".equals(su.getFirstWordInLine(". WORD TEST")));
        assertTrue(".".equals(su.getFirstWordInLine(" . WORD TEST")));
        assertTrue("".equals(su.getFirstWordInLine("  ")));
        assertTrue("TEST.LABEL:".equals(su.getFirstWordInLine("  TEST.LABEL:")));
    }

    @Test
	public void testGetWord() {
        StringUtil su = new StringUtil();
        String str = "WORD TEST";
        assertTrue("WORD".equals(su.getWord(str, 0)));
        assertTrue("WORD".equals(su.getWord(str, 1)));
        assertTrue("".equals(su.getWord(str, 4)));
        assertTrue("TEST".equals(su.getWord(str, 5)));
        assertTrue("".equals(su.getWord(str, -1)));
        assertTrue("".equals(su.getWord(str, 40)));
        str = " WORD TEST";
        assertTrue("".equals(su.getWord(str, 0)));
        assertTrue("WORD".equals(su.getWord(str, 1)));
        assertTrue("".equals(su.getWord(str, 5)));
        assertTrue("TEST".equals(su.getWord(str, 6)));
        str = "";
        assertTrue("".equals(su.getWord(str, 0)));
        str = "   ";
        assertTrue("".equals(su.getWord(str, 1)));
        str = " WORD:";
        assertTrue("WORD".equals(su.getWord(str, 1)));
        str = " WORD(";
        assertTrue("WORD".equals(su.getWord(str, 1)));
        str = " WORD\t";
        assertTrue("WORD".equals(su.getWord(str, 1)));
        str = " WORD\t";
        assertTrue("WORD".equals(su.getWord(str, 1)));
        str = " WORD\n";
        assertTrue("WORD".equals(su.getWord(str, 1)));
        str = "WORD[";
        assertTrue("WORD".equals(su.getWord(str, 1)));
        str = "WORD{";
        assertTrue("WORD".equals(su.getWord(str, 1)));
        str = ":WORD:TEST";
        assertTrue("WORD".equals(su.getWord(str, 1)));
    }

    @Test
	public void testGetWordRegion() {
        StringUtil su = new StringUtil();
        String str = "WORD  TESTXYZ";
        IRegion r = su.getWordRegion(str, 0);
        assertEquals(0, r.getOffset());
        assertEquals(4, r.getLength());
        r = su.getWordRegion(str, 2);
        assertEquals(0, r.getOffset());
        assertEquals(4, r.getLength());
        r = su.getWordRegion(str, 8);
        assertEquals(6, r.getOffset());
        assertEquals(7, r.getLength());
        r = su.getWordRegion(str, -1);
        assertTrue(r == null);
        r = su.getWordRegion(str, 4);
        assertTrue(r == null);
        r = su.getWordRegion(str, 20);
        assertTrue(r == null);
    }

    @Test
	public void testTransformToOONaming() {
        StringUtil oo = new StringUtil();
        assertEquals("test", oo.transformToOONaming("TEST"));
        assertEquals("testWord", oo.transformToOONaming("TEST.WORD"));
        assertEquals("testWordXyzModule", oo.transformToOONaming("TEST.WORD.XYZ.MODULE"));
        assertEquals("", oo.transformToOONaming(""));
        assertEquals("", oo.transformToOONaming(null));
    }

    @Test
	public void testReplaceWord() {
        StringUtil su = new StringUtil();
        IDocument document = new Document();
        document.set("WORD TEST");
        su.replaceWord(document, 8, "REPLACEMENT");
        assertTrue("REPLACEMENT".equals(su.getWord(document.get(), 8)));
        su.replaceWord(document, 0, "XYZ");
        assertTrue("XYZ".equals(su.getWord(document.get(), 0)));
        su.replaceWord(null, 0, "XYZ");
        assertTrue("XYZ".equals(su.getWord(document.get(), 0)));
    }

    @Test
	public void testRemoveJbcNonWordChar() {
        String text = "Word1 Word2,Word3(Word4 Wor_d5!Word6#Word7$Word8%Word9^Word10&Word11*Word12)Word13/Word14\\Word15  word16word17-Word18=Word19+Word20 \"word21\" \"word22\" \"\"\"word23\"\"\" wo;rd24 *;word25 wo* ;rd26";
        String expected = "Word1 Word2 Word3 Word4 Wor_d5 Word6 Word7$Word8 Word9 Word10 Word11 Word12 Word13 Word14 Word15  word16word17 Word18 Word19 Word20 \"word21\" \"word22\" \"\"\"word23\"\"\" wo rd24   word25 wo   rd26";
        String actuals = StringUtil.removeJbcNonwordChar(text);
        // System.out.println("Expected :" + expected + "\n" + "Actuals :" +
        // actuals);
        assertTrue(expected.equals(actuals));
    }

    @Test
	public void testIsJbcLiteral() {
        String[] texts = { null, "", " ", "A+", "100", "  ", "  0", "\"test", "\"test\"", "te\"st", "\" \"", "\" test   \"",
                "\"variable\"", " \"test\"", "200.1", "-345.32", "--234", "=234", "-234..5234", "-.345", ".23424242342349",
                "-13412313.123412341234", "0" };
        Boolean[] results = { false, true, false, false, true, false, false, true, true, false, true, true, true, true, true, true,
                false, false, false, true, true, true, true };
        boolean result = false;
        for (int i = 0; i < texts.length; i++) {
            result = StringUtil.isJbcLiteral(texts[i]);
            assertTrue(results[i] == result);
        }
    }

    @Test
	public void testRemoveJbcCommentPart() {
        // case 1
        String line = "IF A=\"100\" THEN ;* condition";
        String expected = "IF A=\"100\" THEN ";
        assertTrue(expected.equals(StringUtil.removeJbcCommentPart(line)));
        // case 2
        line = "IF A=\"100\" THEN ;  * condition";
        expected = "IF A=\"100\" THEN ";
        assertTrue(expected.equals(StringUtil.removeJbcCommentPart(line)));
        // case 3
        line = "IF A=\"100\" THEN;*condition";
        expected = "IF A=\"100\" THEN";
        assertTrue(expected.equals(StringUtil.removeJbcCommentPart(line)));
        // case 4
        line = "IF A=\"100\" THEN ;  *";
        expected = "IF A=\"100\" THEN ";
        assertTrue(expected.equals(StringUtil.removeJbcCommentPart(line)));
        // case 5
        line = "IF A=\"100\" THEN ;";
        expected = "IF A=\"100\" THEN ";
        assertTrue(expected.equals(StringUtil.removeJbcCommentPart(line)));
        // case 6
        line = " IF A=\"100\" THEN";
        expected = " IF A=\"100\" THEN";
        assertTrue(expected.equals(StringUtil.removeJbcCommentPart(line)));
        // case 7
        line = "IF A=\"100\" AND B * C THEN ";
        expected = "IF A=\"100\" AND B * C THEN ";
        assertTrue(expected.equals(StringUtil.removeJbcCommentPart(line)));
        // case 7
        line = " B * C ";
        expected = " B * C ";
        assertTrue(expected.equals(StringUtil.removeJbcCommentPart(line)));
    }

    @Test
	public void testRemoveJbcLiteral() {
        String line = "test";
        assertTrue(StringUtil.removeJbcLiteral(line).equals("test"));
        line = "test\"hello\"";
        assertTrue(StringUtil.removeJbcLiteral(line).equals("test"));
        line = "test\"hello test again\"";
        assertTrue(StringUtil.removeJbcLiteral(line).equals("test"));
        line = "test\"hello 'test' again\"";
        assertTrue(StringUtil.removeJbcLiteral(line).equals("test"));
        line = "\'test\"hello test again\"\'";
        assertTrue(StringUtil.removeJbcLiteral(line).equals(""));
        line = "'test\"hello test again\'";
        assertTrue(StringUtil.removeJbcLiteral(line).equals(""));
        line = "' '";
        assertTrue(StringUtil.removeJbcLiteral(line).equals(""));
        line = "abc:'test\':xyz";
        assertTrue(StringUtil.removeJbcLiteral(line).equals("abc::xyz"));
        line = "abc:\"test\":xyz";
        assertTrue(StringUtil.removeJbcLiteral(line).equals("abc::xyz"));
        line = "Variable \"test\"";
        assertTrue(StringUtil.removeJbcLiteral(line).equals("Variable "));
        line = "test 'a' and 'b' then";
        assertTrue(StringUtil.removeJbcLiteral(line).equals("test  and  then"));
    }

    @Test
	public void testIsLabel() {
        // Positive test cases
        String line = " Initialise:";
        assertTrue(StringUtil.isLabel(line));
        line = " Initi.alise:";
        assertTrue(StringUtil.isLabel(line));
        line = " Initialise:";
        assertTrue(StringUtil.isLabel(line));
        line = "Initialise: ";
        assertTrue(StringUtil.isLabel(line));
        line = " Initialise: ;*comment";
        assertTrue(StringUtil.isLabel(line));
        line = " Initialise:;*Comment";
        assertTrue(StringUtil.isLabel(line));
        line = "Initialise:";
        assertTrue(StringUtil.isLabel(line));
        line = " In$it123ialise:";
        assertTrue(StringUtil.isLabel(line));
        // Negative test cases
        line = " Initialise";
        assertFalse(StringUtil.isLabel(line));
        line = " Initialise:\"test\"";
        assertFalse(StringUtil.isLabel(line));
        line = " Initialise:*Not a comment";
        assertFalse(StringUtil.isLabel(line));
        line = "Initi alise:";
        assertFalse(StringUtil.isLabel(line));
        line = "Initialise:.";
        assertFalse(StringUtil.isLabel(line));
        line = "Initia:lise:";
        assertFalse(StringUtil.isLabel(line));
        line = " Initialise\":\"";
        assertFalse(StringUtil.isLabel(line));
        line = ":";
        assertFalse(StringUtil.isLabel(line));
    }

    @Test
	public void testRemoveJbcFunction() {
        // case 1
        String line = "temp= TIMESTAMP(test,test2)";
        line = StringUtil.removeJbcFunction(line);
        assertTrue(line.equals("temp test,test2)"));
        // case 2
        line = "temp = TIMESTAMP(test ,test2)";
        line = StringUtil.removeJbcFunction(line);
        assertTrue(line.equals("temp  test ,test2)"));
        // case 3
        line = "temp=TIMESTAMP(test10 ,test2)";
        line = StringUtil.removeJbcFunction(line);
        assertTrue(line.equals("temp test10 ,test2)"));
        // case 4
        line = "temp     =   TIMESTAMP()";
        line = StringUtil.removeJbcFunction(line);
        assertTrue(line.equals("temp      )"));
        // case 5
        line = "PROGRAM TIMESTAMP(test , test3)";
        line = StringUtil.removeJbcFunction(line);
        assertTrue(line.equals("PROGRAM TIMESTAMP(test , test3)"));
        // case 6
        line = "CALL TIMESTAMP(test)";
        line = StringUtil.removeJbcFunction(line);
        assertTrue(line.equals("CALL TIMESTAMP(test)"));
        // case 7
        line = "PERFORM TIMESTAMP()";
        line = StringUtil.removeJbcFunction(line);
        assertTrue(line.equals("PERFORM TIMESTAMP()"));
    }

    @Test
	public void testIsNonWordChar() {
        assertTrue(StringUtil.isNonWordChar(' '));
        assertTrue(StringUtil.isNonWordChar('`'));
        assertTrue(StringUtil.isNonWordChar('!'));
        assertTrue(StringUtil.isNonWordChar('@'));
        assertTrue(StringUtil.isNonWordChar('#'));
        assertTrue(StringUtil.isNonWordChar('%'));
        assertTrue(StringUtil.isNonWordChar('^'));
        assertTrue(StringUtil.isNonWordChar('&'));
        assertTrue(StringUtil.isNonWordChar('*'));
        assertTrue(StringUtil.isNonWordChar('('));
        assertTrue(StringUtil.isNonWordChar(')'));
        assertTrue(StringUtil.isNonWordChar('-'));
        assertTrue(StringUtil.isNonWordChar('='));
        assertTrue(StringUtil.isNonWordChar('+'));
        assertTrue(StringUtil.isNonWordChar(':'));
        assertTrue(StringUtil.isNonWordChar(';'));
        assertTrue(StringUtil.isNonWordChar('<'));
        assertTrue(StringUtil.isNonWordChar('>'));
        assertTrue(StringUtil.isNonWordChar('?'));
        assertTrue(StringUtil.isNonWordChar('/'));
        assertTrue(StringUtil.isNonWordChar('{'));
        assertTrue(StringUtil.isNonWordChar('['));
        assertTrue(StringUtil.isNonWordChar('}'));
        assertTrue(StringUtil.isNonWordChar(']'));
        assertTrue(StringUtil.isNonWordChar('|'));
        assertTrue(StringUtil.isNonWordChar('\\'));
        assertTrue(StringUtil.isNonWordChar('~'));
        assertTrue(StringUtil.isNonWordChar('"'));
        assertTrue(StringUtil.isNonWordChar('\''));
        assertFalse(StringUtil.isNonWordChar('_'));
        assertFalse(StringUtil.isNonWordChar('0'));
        assertFalse(StringUtil.isNonWordChar('9'));
        assertFalse(StringUtil.isNonWordChar('a'));
        assertFalse(StringUtil.isNonWordChar('z'));
        assertFalse(StringUtil.isNonWordChar('A'));
        assertFalse(StringUtil.isNonWordChar('Z'));
        assertFalse(StringUtil.isNonWordChar('$'));
        assertFalse(StringUtil.isNonWordChar('.'));
    }
}
