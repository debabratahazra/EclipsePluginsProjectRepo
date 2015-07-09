package com.temenos.t24.tools.eclipse.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class FormatUtilTest {
    protected final Logger log = LoggerFactory.getLogger(StringUtilTest.class);
    
    /**
     */
    @Test
	public void testBreakUpText() {
        FormatUtil fu = new FormatUtil();
        String[] res = fu.breakUpText("WORD TEST1 \n WORD   TEST2 \r\n    WORD TEST3\r\n");
        //for (int i = 0; i < res.length; i++) System.out.println(res[i]+"- length: "+res[i].length());
        Assert.assertTrue("WORD TEST1".equals(res[0]));
        Assert.assertTrue("WORD   TEST2".equals(res[1]));
        Assert.assertTrue("WORD TEST3".equals(res[2]));
        
        res = fu.breakUpText("");
        //for (int i = 0; i < res.length; i++) System.out.println(res[i]);
        Assert.assertTrue("".equals(res[0]));        
    }

    
    @Test
	public void testBreakUpComments() {
        FormatUtil fu = new FormatUtil();
        String[] res = fu.breakUpComments("  REM  BLA1  \r\n  REM  BLA2  ");
        //for (int i = 0; i < res.length; i++) System.out.println(res[i]);
        Assert.assertTrue("REM  BLA1".equals(res[0]));
        Assert.assertTrue("REM  BLA2".equals(res[1]));
    }    
    

    /**
     * Tests removing extra whitespaces, \r \n from a line
     */ 
    @Test
	public void testFormatText() {
        FormatUtil fu = new FormatUtil();
        String result = fu.formatText("TEST\r\n");
        //System.out.println(result+"-");
        Assert.assertTrue("TEST".equals(result));
        Assert.assertTrue("TEST ".equals(fu.formatText("TEST \r\n")));
        //Assert.assertTrue("".equals(fu.formatContent("")));
    }

    
    /**
     * Tests removing leading whitespaces, and trailing \r \n from a line
     */ 
    @Test
	public void testFormatConmment() {
        FormatUtil fu = new FormatUtil();
        String result;
        
        result = fu.formatComment("  REM TEST");
        Assert.assertTrue("REM TEST".equals(result));
        result = fu.formatComment("  REM TEST1  \r\n");
        Assert.assertTrue("REM TEST1".equals(result));
    }    
    
    
    @Test
	public void testActionAfterThen() {
        FormatUtil fu = new FormatUtil();
        Assert.assertTrue(fu.thereIsActionAfterThen("IF COND THEN CALL BLA"));    
        Assert.assertTrue(fu.thereIsActionAfterThen("IF COND1 THEN IF COND2 THEN CALL BLA"));
        
        Assert.assertFalse(fu.thereIsActionAfterThen("IF COND1 THEN"));
        Assert.assertFalse(fu.thereIsActionAfterThen("IF COND1 THEN * ; comment"));
        Assert.assertFalse(fu.thereIsActionAfterThen("IF COND1 THEN IF COND2 THEN"));
        Assert.assertFalse(fu.thereIsActionAfterThen("IF COND1 THEN IF COND2 THEN IF COND3 THEN"));
    }
    
    /**
     * Calculates the total indentation string based on the indentation level 
     */
    @Test
	public void testGetTotalIndentation(){
        FormatUtil fu = new FormatUtil();
        String localIndentation = "   ";
        Assert.assertTrue("".equals(fu.getTotalIndentation(localIndentation, -1, false, false)));       
        Assert.assertTrue("   ".equals(fu.getTotalIndentation(localIndentation, -1, true, false)));
        Assert.assertTrue("   ".equals(fu.getTotalIndentation(localIndentation, 0, true, false)));
        Assert.assertTrue("      ".equals(fu.getTotalIndentation(localIndentation, 1, true, false)));
        Assert.assertTrue("   ".equals(fu.getTotalIndentation(localIndentation, 1, false, false)));
        
        Assert.assertTrue("".equals(fu.getTotalIndentation(localIndentation, 2, false, true)));
        Assert.assertTrue("   ".equals(fu.getTotalIndentation(localIndentation, 2, true, true)));
    }      
}
