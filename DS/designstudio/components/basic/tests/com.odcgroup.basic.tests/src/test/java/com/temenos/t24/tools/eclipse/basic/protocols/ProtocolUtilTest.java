package com.temenos.t24.tools.eclipse.basic.protocols;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

public class ProtocolUtilTest {

    @BeforeClass
    public static void beforeClass() {
        try {
            // Create and setup spring context
            String location = "spring/test.protocol.spring.xml";
            ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(location);
            T24BasicPlugin.setSpringApplicationContext(springContext);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
	public void testNewLines() {
        ProtocolUtil pu = new ProtocolUtil();
        String originalString = "line1\n\nline3";
        String encoded64 = pu.encode64(originalString);
        String decoded = pu.decodeComplete(encoded64);
        Assert.assertTrue("line1\r\n\r\nline3".equals(decoded));
    }

    @Test
	public void testTransformIsolated_LF_or_CR_intoWinNewLines() {
        ProtocolUtil pu = new ProtocolUtil();
        // TESTS TRANSFORM NEW LINE
        Assert.assertTrue("TEST\r\nWORD".equals(pu.transformInsolated_CR_LF_IntoWinNewLines("TEST\rWORD")));
        Assert.assertTrue("TEST\r\nWORD".equals(pu.transformInsolated_CR_LF_IntoWinNewLines("TEST\nWORD")));
        Assert.assertTrue("TEST\r\nWORD".equals(pu.transformInsolated_CR_LF_IntoWinNewLines("TEST\r\nWORD")));
        Assert.assertTrue("TEST\r\n\r\nWORD".equals(pu.transformInsolated_CR_LF_IntoWinNewLines("TEST\r\n\r\nWORD")));
        Assert.assertTrue("TEST\r\nWORD\r\n\r\n\r\n".equals(pu.transformInsolated_CR_LF_IntoWinNewLines("TEST\r\nWORD\n\n\n")));
        Assert.assertTrue("TEST\r\n\r\nWORD\r\n".equals(pu.transformInsolated_CR_LF_IntoWinNewLines("TEST\n\r\nWORD\r\n")));
    }

    @Test
	public void testTransformIsolatedCRintoWinNewLines() {
        ProtocolUtil pu = new ProtocolUtil();
        // TESTS TRANSFORM NEW LINE
        String test = pu.transformIsolated_CR_intoWinNewLines("TEST\rWORD");
        Assert.assertTrue("TEST\r\nWORD".equals(test));
        Assert.assertTrue("\"TEST\"\r\n WORD".equals(pu.transformIsolated_CR_intoWinNewLines("\"TEST\"\r WORD")));
    }

    @Test
	public void testTransformIsolated_LF_intoWinNewLines() {
        ProtocolUtil pu = new ProtocolUtil();
        // TESTS TRANSFORM NEW LINE
        String test = pu.transformIsolated_LF_intoNewWinLines("TEST\nWORD");
        Assert.assertTrue("TEST\r\nWORD".equals(test));
        Assert.assertTrue("\"TEST\"\r\n WORD".equals(pu.transformIsolated_LF_intoNewWinLines("\"TEST\"\n WORD")));
    }

    @Test
	public void testBasicTermination() {
        Assert.assertTrue("test".equals(StringUtil.removeBasicExtension("test.b")));
        Assert.assertTrue("TEST".equals(StringUtil.removeBasicExtension("TEST.b")));
        Assert.assertTrue("TEST".equals(StringUtil.removeBasicExtension("TEST.B")));
        Assert.assertTrue("test.basic".equals(StringUtil.removeBasicExtension("test.basic.b")));
        Assert.assertTrue("test.basicmodule".equals(StringUtil.removeBasicExtension("test.basicmodule.b")));
        Assert.assertTrue("test.module".equals(StringUtil.removeBasicExtension("test.module")));
        Assert.assertTrue("".equals(StringUtil.removeBasicExtension("")));
    }

    @Test
	public void testTransformNewLineIntoFM() {
        ProtocolUtil pu = new ProtocolUtil();
        // TESTS TRANSFORM NEW LINE
        Assert.assertTrue("TEST<FM>WORD".equals(pu.transformNewLinesIntoFM("TEST\r\nWORD")));
        Assert.assertTrue("TEST<FM>WORD".equals(pu.transformNewLinesIntoFM("TEST\nWORD")));
        // TESTS RESTORE NEW LINE
        Assert.assertTrue("TEST\r\nWORD".equals(pu.transformFMintoNewLines("TEST<FM>WORD")));
        Assert.assertTrue("TEST \r\n WORD".equals(pu.transformFMintoNewLines("TEST <FM> WORD")));
        Assert.assertTrue("TEST \r\n WORD".equals(pu.transformFMintoNewLines("TEST !FM! WORD")));
    }

    @Test
	public void testEncodeComplete() {
        ProtocolUtil pu = new ProtocolUtil();
        String originalString = "COND\r\n> 3";
        Assert.assertTrue("Q09ORDxGTT4%2BIDM=".equals(pu.encodeComplete(originalString)));
        originalString = "*-------------------\r\n" + "* <Rating>0</Rating>\r\n" + "*-------------------\r\n"
                + "PROGRAM PEPE2\r\nCALL BLA\r\nEND\r\n  ";
        String encoded = pu.encodeComplete(originalString);
        Assert
                .assertTrue(encoded
                        .equals("Ki0tLS0tLS0tLS0tLS0tLS0tLS08Rk0%2BKiA8UmF0aW5nPjA8L1JhdGluZz48Rk0%2BKi0tLS0tLS0tLS0tLS0tLS0tLS08Rk0%2BUFJPR1JBTSBQRVBFMjxGTT5DQUxMIEJMQTxGTT5FTkQ8Rk0%2BICA="));
    }

    @Test
	public void testDecodeComplete() {
        ProtocolUtil pu = new ProtocolUtil();
        String originalString = "Q09ORA0+IDM=";
        String decodedComplete = pu.decodeComplete(originalString);
        Assert.assertTrue("COND\r\n> 3".equals(decodedComplete));
    }

    @Test
	public void testDecodeComplete2() {
        ProtocolUtil pu = new ProtocolUtil();
        String textUndecoded = "A\r\n\t  XYZ";
        pu.encodeComplete(textUndecoded);
        //String encoded = "YQ0gICAgIFRBQiA=";
        String decodedComplete = pu.decodeComplete("TElOIDENTElOIDINTElOIDMNTElOIDQ=");
        System.out.println(">>> decoded: " + decodedComplete);
        Assert.assertTrue(true);
    }

    @Test
	public void testProcessCompileOutcome1() {
        ProtocolUtil pu = new ProtocolUtil();
        String outputXml = "<compileout>"
                + "<![CDATA[PEPE2.BASIC!FM!Product group : other!FM!Source directory : BP Single item : PEPE2.BASIC!FM!Score Checks Compile Catalog !FM!Score Checks !!! Problem invoking j24 compiler. Code is 128!FM!Compile Catalog !FM!Cataloging 1 other!FM!PEPE2.BASIC!FM! ** Unable to compile source PEPE2.BASIC **!FM!"
                + "<compileoutput><errors>"
                + "<error><msg>Error in TEST Found 'PROGRAM/SUBROUTINE'</msg><line>5</line><correct>Program name and SUBROUTINE/PROGRAM statement must be the same</correct></error>"
                + "<error><msg>Message Test 2</msg><line>33</line><correct>Correct test 2</correct></error>"
                + "<error><msg></msg><line></line></error>"
                + "<error></error>"
                + "</errors>"
                + "<warnings>"
                + "<warning><msg>Warning - found 'CRT'</msg><line>6</line><type>Incompatible with Browser, Desktop and OFS. Do not use</type></warning>"
                + "<warning><msg>Warning Mst test2</msg><line>26</line><type>Warning type test2</type></warning>"
                + "</warnings>"
                + "<codereviews></codereviews><standards></standards>"
                + "<RatingDetail><CodeLines>NoLines</CodeLines><ParaLines>NoParaLines</ParaLines><Nests>NoNests</Nests>"
                + "<Conditions>NoConditions</Conditions><Gotos>NoGotos</Gotos><SameLine>NoSameLin</SameLine><Labels>NoLabels</Labels><Comments>NoComm</Comments>"
                + "<Rating>NoRating</Rating></RatingDetail></compileoutput></compileout>";
        String oldRating = "-10";
        String displayString = pu.processCompileOutcome(outputXml, oldRating);
        //System.out.println("COMPILE OUTOCOME: "+displayString);
        Assert.assertTrue(-1 != displayString.indexOf("CodeLines: NoLines"));
        Assert.assertTrue(-1 != displayString.indexOf("Nests: NoNests"));
        Assert.assertTrue(-1 != displayString.indexOf("Conditions: NoConditions"));
        Assert.assertTrue(-1 != displayString.indexOf("Gotos: NoGotos"));
        Assert.assertTrue(-1 != displayString.indexOf("SameLine: NoSameLin"));
        Assert.assertTrue(-1 != displayString.indexOf("Labels: NoLabels"));
        Assert.assertTrue(-1 != displayString.indexOf("Comments: NoComm"));
        Assert.assertTrue(-1 != displayString.indexOf("Rating: NoRating"));
    }

    @Test
	public void testGetCompileRating() {
        ProtocolUtil pu = new ProtocolUtil();
        String compileOutputXml = "<compileoutput><errors>"
                + "<error><msg>Error in TEST Found 'PROGRAM/SUBROUTINE'</msg><line>5</line><correct>Program name and SUBROUTINE/PROGRAM statement must be the same</correct></error>"
                + "<error><msg>Message Test 2</msg><line>33</line><correct>Correct test 2</correct></error>"
                + "<error><msg></msg><line></line></error>"
                + "<error></error>"
                + "</errors>"
                + "<warnings>"
                + "<warning><msg>Warning - found 'CRT'</msg><line>6</line><type>Incompatible with Browser, Desktop and OFS. Do not use</type></warning>"
                + "<warning><msg>Warning Mst test2</msg><line>26</line><type>Warning type test2</type></warning>"
                + "</warnings>"
                + "<codereviews></codereviews><standards></standards>"
                + "<RatingDetail><CodeLines>NoLines</CodeLines><ParaLines>NoParaLines</ParaLines><Nests>NoNests</Nests>"
                + "<Conditions>NoConditions</Conditions><Gotos>NoGotos</Gotos><SameLine>NoSameLin</SameLine><Labels>NoLabels</Labels><Comments>NoComm</Comments>"
                + "<Rating>100</Rating></RatingDetail></compileoutput>";
        String rating = pu.getCompilationRating(compileOutputXml, true);
        Assert.assertTrue(100 == Integer.parseInt(rating));
        compileOutputXml = "<compileoutput><RatingDetail><CodeLines>NoLines</CodeLines><ParaLines>NoParaLines</ParaLines><Nests>NoNests</Nests>"
                + "<Conditions>NoConditions</Conditions><Gotos>NoGotos</Gotos><SameLine>NoSameLin</SameLine><Labels>NoLabels</Labels><Comments>NoComm</Comments>"
                + "<Rating>-100</Rating></RatingDetail></compileoutput>";
        rating = pu.getCompilationRating(compileOutputXml, true);
        Assert.assertTrue(-100 == Integer.parseInt(rating));
        compileOutputXml = "<compileoutput><RatingDetail><CodeLines>NoLines</CodeLines><ParaLines>NoParaLines</ParaLines><Nests>NoNests</Nests>"
                + "<Conditions>NoConditions</Conditions><Gotos>NoGotos</Gotos><SameLine>NoSameLin</SameLine><Labels>NoLabels</Labels><Comments>NoComm</Comments>"
                + "<Rating></Rating></RatingDetail></compileoutput>";
        rating = pu.getCompilationRating(compileOutputXml, true);
        Assert.assertTrue("".equals(rating));
    }
}
