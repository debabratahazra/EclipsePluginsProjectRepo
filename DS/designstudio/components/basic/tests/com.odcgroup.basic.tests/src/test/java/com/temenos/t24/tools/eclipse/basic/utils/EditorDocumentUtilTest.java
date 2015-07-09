package com.temenos.t24.tools.eclipse.basic.utils;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditorDocumentUtilTest {
    protected final Logger log = LoggerFactory.getLogger(EditorDocumentUtilTest.class);
    
    @Test
	public void testGetRating(){
        String contents = "*-----------------------------------------------------------------------------"+
                          "* <Rating>-150</Rating>"+
                          "*-----------------------------------------------------------------------------";
        Assert.assertTrue("-150".equals(EditorDocumentUtil.getFileRating(contents)));    
        contents = "*-----------------------------------------------------------------------------"+
        "* <Rating></Rating>"+
        "*-----------------------------------------------------------------------------";
        Assert.assertTrue("".equals(EditorDocumentUtil.getFileRating(contents)));        
        contents = "*-----------------------------------------------------------------------------"+
        "*-----------------------------------------------------------------------------";
        Assert.assertTrue("".equals(EditorDocumentUtil.getFileRating(contents)));        
        
    }
    
    @Test
	public void testGetCodeOnly(){
        String contents = "REM COMMENT1\r\nPROGRAM TEST ;* COMMENT2 \r\n\r\n* COMMENT3\r\nEND ;* COMMENT3";
        String result = EditorDocumentUtil.getCodeOnly(contents);
        Assert.assertTrue("PROGRAM TEST \r\n\r\nEND \r\n".equals(result)); 

    }
    
    @Test
	public void testIsWithinComment(){
        IDocument doc = new Document();
        doc.set("   ");
        Assert.assertFalse(EditorDocumentUtil.isWithinComment(doc, 0));
        doc.set("*  ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,1));
        doc.set("* TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,1));
        doc.set("  * TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,4));
        doc.set(".* TEXT ");
        Assert.assertFalse(EditorDocumentUtil.isWithinComment(doc,5));
        doc.set(" .* TEXT  ");
        Assert.assertFalse(EditorDocumentUtil.isWithinComment(doc,6));
        doc.set(" . * TEXT ");
        Assert.assertFalse(EditorDocumentUtil.isWithinComment(doc,6));
        doc.set("*------ TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,6));        

        doc.set("REM     ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,4));
        doc.set("REM TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,6));
        doc.set("  REM TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,8));
        doc.set(". REM TEXT   ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,8));
        doc.set(" . REM TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,8));
        
        doc.set(";*     ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,4));
        doc.set(";* TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,6));
        doc.set("  ;* TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,8));
        doc.set(". ;* TEXT   ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,8));
        doc.set(" . ;* TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,8));        

        doc.set("CALL ;* TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,10));
        doc.set("CALL REM TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,10));
        doc.set("CALL * TEXT ");
        Assert.assertFalse(EditorDocumentUtil.isWithinComment(doc,10));
        
        doc.set("CALL ; * TEXT ");
        Assert.assertTrue(EditorDocumentUtil.isWithinComment(doc,10));        
        
    }
    
    @Test
	public void testIsLabel(){
        Assert.assertTrue(EditorDocumentUtil.isLabel("LABEL:"));
        Assert.assertTrue(EditorDocumentUtil.isLabel(" LABEL: "));
        Assert.assertTrue(EditorDocumentUtil.isLabel(" LABEL.TEST: "));
        Assert.assertTrue(EditorDocumentUtil.isLabel(" LABEL.TEST: ; * comment"));
        Assert.assertTrue(EditorDocumentUtil.isLabel(" LABEL.TEST: ;*comment"));
        Assert.assertTrue(EditorDocumentUtil.isLabel(" LABEL.TEST: *comment"));
        
        Assert.assertFalse(EditorDocumentUtil.isLabel("  "));
        Assert.assertFalse(EditorDocumentUtil.isLabel("NOT.LABEL"));
        Assert.assertFalse(EditorDocumentUtil.isLabel("NOT.LABEL :"));
        Assert.assertFalse(EditorDocumentUtil.isLabel("NOT.LABEL:NOT"));
        Assert.assertFalse(EditorDocumentUtil.isLabel("NOT LABEL: "));
        /** Text after semicolon, but not a comment*/
        Assert.assertFalse(EditorDocumentUtil.isLabel("NOT.LABEL: NOT"));      
        Assert.assertFalse(EditorDocumentUtil.isLabel(" NOT.LABEL:;*comment"));
    }
    
}
