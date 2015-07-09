package com.temenos.t24.tools.eclipse.basic.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.formatting.FormattingController;
import com.temenos.t24.tools.eclipse.basic.editors.formatting.T24FormattingStrategy;

/**
 */
public class FormattingTest {

    private static final int INDENTATION_SPACES = 4;
    private String indentation = FormattingController.getIndentation();
    private static final String DEFAULT_FORMATTING_TEST_FILE_LOCATE_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_LOCATE_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_IF_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_IF_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_LOOP_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_LOOP_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_LOOP_2 = "testfiles\\t24EditorTest_defaultFormattingStrategy_LOOP_2.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_MIXED_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_MIXED_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_CASE_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_CASE_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_FOR_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_FOR_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_COMMENT_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_COMMENT_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_LABEL_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_LABEL_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_REGION_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_REGION_1.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_REGION_2 = "testfiles\\t24EditorTest_defaultFormattingStrategy_REGION_2.txt";
    private static final String DEFAULT_FORMATTING_TEST_FILE_OPEN_1 = "testfiles\\t24EditorTest_defaultFormattingStrategy_OPEN_1.txt";

    @BeforeClass
    public static void beforeClass() {
        try {
            String[] locations = { "/spring/test.plugin.spring.xml", "/spring/test.protocol.spring.xml" };
            ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(locations);
            T24BasicPlugin.setSpringApplicationContext(springContext);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        String ind = "";
        int noSpaces = INDENTATION_SPACES;
        for (int i = 0; i < noSpaces; i++)
            ind = ind + " ";
        this.indentation = ind;
    }

    @Test
	public void testFormat_Open_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_OPEN_1);
        // printResults(results);
        Assert.assertTrue((indentation + "OPEN FN.FILE TO F.FILE ELSE").equals(results[0]));
        Assert.assertTrue((indentation + indentation + "CALL MODULE").equals(results[1]));
        Assert.assertTrue((indentation + "END").equals(results[2]));
        Assert.assertTrue((indentation + "OPENSEQ unixFolder:unixFile TO fUnixFolder ELSE").equals(results[3]));
        Assert.assertTrue((indentation + indentation + "CRT \"Unable to open \" :unixFolder:unixFile").equals(results[4]));
        Assert.assertTrue((indentation + indentation + "STOP").equals(results[5]));
        Assert.assertTrue((indentation + "END").equals(results[6]));
        Assert.assertTrue((indentation + "CALL TEST").equals(results[7]));
    }

    @Test
	public void testFormat_Region_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_REGION_1);
        // printResults(results);
        Assert.assertTrue((indentation + "IF A > 2 THEN").equals(results[0]));
        Assert.assertTrue(("*** <region name= TEST.GOSUB.LABEL>").equals(results[1]));
        Assert.assertTrue(("*** <desc>Line one").equals(results[2]));
        Assert.assertTrue(("*** line two").equals(results[3]));
        Assert.assertTrue(("*** </desc>").equals(results[4]));
        Assert.assertTrue(("TEST.GOSUB.LABEL:").equals(results[5]));
        Assert.assertTrue((indentation + indentation + "IF A > 3 THEN").equals(results[6]));
        Assert.assertTrue((indentation + indentation + indentation + "CALL MODULE").equals(results[7]));
        Assert.assertTrue((indentation + indentation + "END").equals(results[8]));
        Assert.assertTrue((indentation + indentation + "RETURN").equals(results[9]));
        Assert.assertTrue(("*** </region>").equals(results[10]));
    }

    @Test
	public void testFormat_Region_2() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_REGION_2);
        // printResults(results);
        Assert.assertTrue((indentation + "IF A > 2 THEN").equals(results[0]));
        Assert.assertTrue(("*** <region name= TEST.REGION>").equals(results[1]));
        Assert.assertTrue(("*** <desc>Description</desc>").equals(results[2]));
        Assert.assertTrue((indentation + indentation + "IF A > 3 THEN").equals(results[3]));
        Assert.assertTrue((indentation + indentation + indentation + "CALL MODULE").equals(results[4]));
        Assert.assertTrue((indentation + indentation + "END").equals(results[5]));
        Assert.assertTrue(("*** </region>").equals(results[6]));
        Assert.assertTrue((indentation + "END").equals(results[7]));
    }

    @Test
	public void testFormat_Labels_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_LABEL_1);
        // printResults(results);
        Assert.assertTrue(true);
        Assert.assertTrue(("MY.LABEL1:").equals(results[0]));
        Assert.assertTrue((indentation + "IF COND1 THEN").equals(results[1]));
        Assert.assertTrue((indentation + indentation + "CALL TEST1").equals(results[2]));
        Assert.assertTrue((indentation + "END").equals(results[3]));
        Assert.assertTrue(("MY.LABEL2:").equals(results[4]));
    }

    @Test
	public void testFormat_Comments_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_COMMENT_1);
        // printResults(results);
        Assert.assertTrue(true);
        Assert.assertTrue(("REM COMMENT1").equals(results[0]));
        Assert.assertTrue(("REM  COMMENT2").equals(results[1]));
        Assert.assertTrue(("*  COMMENT3").equals(results[2]));
        Assert.assertTrue((";*  COMMENT4").equals(results[3]));
        Assert.assertTrue((indentation + "CALL TEST    ; * COMMENT").equals(results[4]));
        Assert.assertTrue((indentation + "CALL \"TEST\"    ; * COMMENT").equals(results[5]));
    }

    @Test
	public void testFormat_LOCATE_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_LOCATE_1);
        // printResults(results);
        Assert.assertTrue((indentation + "LOCATE expression IN field<1> SETTING var THEN").equals(results[0]));
        Assert.assertTrue((indentation + indentation + "CALL TEST").equals(results[1]));
        Assert.assertTrue((indentation + "END").equals(results[2]));
        Assert.assertTrue((indentation + "LOCATE expression IN field<1> SETTING var THEN CALL TEST END").equals(results[4]));
    }

    @Test
	public void testFormat_LOOP_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_LOOP_1);
        // printResults(results);
        Assert.assertTrue((indentation + "LOOP").equals(results[0]));
        Assert.assertTrue((indentation + indentation + "CALL TEST1").equals(results[1]));
        Assert.assertTrue((indentation + "UNTIL COND1 DO").equals(results[2]));
        Assert.assertTrue((indentation + indentation + "CALL TEST2").equals(results[3]));
        Assert.assertTrue((indentation + "REPEAT").equals(results[4]));
    }

    @Test
	public void testFormat_LOOP_2() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_LOOP_2);
        // printResults(results);
        Assert.assertTrue((indentation + "LOOP CALL BLA UNTIL COND CALL BLU REPEAT").equals(results[0]));
        Assert.assertTrue((indentation + "LOOP CALL BLA UNTIL COND CALL BLU").equals(results[2]));
        Assert.assertTrue((indentation + "REPEAT").equals(results[3]));
        Assert.assertTrue((indentation + "LOOP CALL BLA").equals(results[5]));
        Assert.assertTrue((indentation + "UNTIL COND").equals(results[6]));
        Assert.assertTrue((indentation + indentation + "CALL BLU").equals(results[7]));
        Assert.assertTrue((indentation + "REPEAT").equals(results[8]));
    }

    @Test
	public void testFormat_MIXED_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_MIXED_1);
        // printResults(results);
        Assert.assertTrue((indentation + "LOOP WHILE COND DO  ; * TEST COMMENT").equals(results[0]));
        Assert.assertTrue((indentation + indentation + "IF COND == \"TEST\" THEN").equals(results[1]));
        Assert.assertTrue((indentation + indentation + indentation + "LOOP").equals(results[2]));
        Assert.assertTrue((indentation + indentation + indentation + indentation + "CALL TEST1").equals(results[3]));
        Assert.assertTrue((indentation + indentation + indentation + "UNTIL COND DO").equals(results[4]));
        Assert.assertTrue((indentation + indentation + indentation + indentation + "IF COND THEN").equals(results[5]));
        Assert.assertTrue((indentation + indentation + indentation + indentation + indentation + "CALL TEST2").equals(results[6]));
        Assert.assertTrue((indentation + indentation + indentation + indentation + "END").equals(results[7]));
        Assert.assertTrue((indentation + indentation + indentation + "REPEAT").equals(results[8]));
        Assert.assertTrue((indentation + indentation + indentation + "BEGIN CASE").equals(results[9]));
        Assert.assertTrue((indentation + indentation + indentation + indentation + "CASE A > 1").equals(results[10]));
        Assert.assertTrue((indentation + indentation + indentation + indentation + indentation + "FOR I > 1 TO COND")
                .equals(results[11]));
        Assert.assertTrue((indentation + indentation + indentation + indentation + indentation + indentation + "CALL TEST")
                .equals(results[12]));
        Assert.assertTrue((indentation + indentation + indentation + indentation + indentation + "NEXT").equals(results[13]));
        Assert.assertTrue((indentation + indentation + indentation + "END CASE").equals(results[14]));
        Assert.assertTrue((indentation + indentation + "END").equals(results[15]));
        Assert.assertTrue((indentation + "REPEAT").equals(results[16]));
    }

    @Test
	public void testFormat_CASE_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_CASE_1);
        // printResults(results);
        Assert.assertTrue((indentation + "BEGIN CASE").equals(results[0]));
        Assert.assertTrue((indentation + indentation + "CASE COND > 1").equals(results[1]));
        Assert.assertTrue((indentation + indentation + indentation + "CALL TEST").equals(results[2]));
        Assert.assertTrue((indentation + indentation + "CASE COND <= 1").equals(results[3]));
        Assert.assertTrue((indentation + indentation + indentation + "CALL TEST").equals(results[4]));
        Assert.assertTrue((indentation + "END CASE").equals(results[5]));
    }

    @Test
	public void testFormat_IF_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_IF_1);
        // printResults(results);
        Assert.assertTrue((indentation + "IF COND1 THEN CALL TEST1 END").equals(results[0]));
        Assert.assertTrue((indentation + "IF C = \"TEST\" THEN CALL BLA").equals(results[2]));
        Assert.assertTrue((indentation + "IF COND1 THEN").equals(results[4]));
        Assert.assertTrue((indentation + indentation + "CALL TEST1").equals(results[5]));
        Assert.assertTrue((indentation + indentation + "IF COND2").equals(results[6]));
        Assert.assertTrue((indentation + indentation + indentation + "CALL TEST2").equals(results[7]));
        Assert.assertTrue((indentation + indentation + "END").equals(results[8]));
        Assert.assertTrue((indentation + "END").equals(results[9]));
        /** test with a label */
        Assert.assertTrue((indentation + "IF COND1 THEN").equals(results[11]));
        Assert.assertTrue((indentation + indentation + "CALL TEST1 ; * a label is coming next").equals(results[12]));
        Assert.assertTrue(("LABEL.TEST:").equals(results[13]));
        Assert.assertTrue((indentation + indentation + "CALL TEST2").equals(results[14]));
        Assert.assertTrue((indentation + "END").equals(results[15]));
    }

    @Test
	public void testFormat_FOR_1() {
        String[] results = formatFile(DEFAULT_FORMATTING_TEST_FILE_FOR_1);
        // printResults(results);
        Assert.assertTrue((indentation + "FOR I > 1 TO COND CALL TEST1 NEXT").equals(results[0]));
        Assert.assertTrue((indentation + "FOR I > 1 TO COND").equals(results[2]));
        Assert.assertTrue((indentation + indentation + "CALL TEST1").equals(results[3]));
        Assert.assertTrue((indentation + "NEXT").equals(results[4]));
    }

    private String[] formatFile(String file) {
        T24FormattingStrategy df = new T24FormattingStrategy();
        String content = "";
        String indentedContent = "";
        content = this.getFileContents(file);
        indentedContent = df.format(content);
        String[] results = this.breakUpText(indentedContent);
        return results;
    }

    /**
     * Breaks up the String passed into lines
     * 
     * @param content - String to be broken up
     * @return String[] - array with lines broken up
     */
    private String[] breakUpText(String content) {
        String[] res = content.split("\n");
        String s;
        for (int i = 0; i < res.length; i++) {
            s = formatContent(res[i]);
            res[i] = s;
        }
        return res;
    }

    /**
     * Removes all extra whitespaces, \r and \n from a string. Eg. "TEST WORD
     * \r\n MAP" will be transformed into "TEST WORD MAP"
     * 
     * @param content
     * @return String - return the transformed string. If parameter passed is
     *         either null or "", it'll return ""
     */
    private String formatContent(String content) {
        if ((content != null) && (content.length() > 0)) {
            String[] contentParts = content.split("\r|\n");
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < contentParts.length; i++) {
                buffer.append(contentParts[i]).append(" ");
            }
            if (buffer.length() > 0)
                buffer.delete(buffer.length() - 1, buffer.length());
            String res = buffer.toString();
            return res;
        } else {
            return "";
        }
    }

    /**
     * @param filename
     * @return String - text
     */
    private String getFileContents(String filename) {
        StringBuffer sb = new StringBuffer();
        String contents;
        BufferedReader br = null;
        try {
            InputStream is = this.getClass().getResourceAsStream("/" + filename);
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
        }
        contents = sb.toString();
        if (contents == null)
            return "";
        else
            return contents;
    }
}
