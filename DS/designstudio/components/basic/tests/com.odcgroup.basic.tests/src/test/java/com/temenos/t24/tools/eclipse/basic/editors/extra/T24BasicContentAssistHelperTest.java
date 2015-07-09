package com.temenos.t24.tools.eclipse.basic.editors.extra;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

public class T24BasicContentAssistHelperTest {

    private static T24BasicContentAssistHelper helper;
    
    
    @Before
    public void setUp() throws Exception {
        String location = "spring/test.plugin.spring.xml";
        ClassPathXmlApplicationContext springApplicationContext = new ClassPathXmlApplicationContext(location);
        T24BasicPlugin.setSpringApplicationContext(springApplicationContext);
    }

    @Test
	public void testGetInstance() {
        helper = T24BasicContentAssistHelper.getInstance();
        assertTrue(helper != null);
    }

    @Test
	public void testGetSubroutineList() {
        List<String> subroutineList = new ArrayList<String>();
        // Case 1
        subroutineList = helper.getSubroutineList("AB");
        assertTrue(subroutineList.size() == 1);
        assertTrue(subroutineList.get(0).equals("ABBREVIATION"));
        // Case 2
        subroutineList = helper.getSubroutineList("ACC");
        assertTrue(subroutineList.size() == 1);
        assertTrue(subroutineList.get(0).equals("ACCOUNT"));
        // Case 3
        subroutineList = helper.getSubroutineList("A");
        assertTrue(subroutineList.size() == 7);
        assertTrue(subroutineList.get(0).equals("AA.ACCOUNT.DETAILS"));
        assertTrue(subroutineList.get(1).equals("AA.ACTION.LIST.MANAGER(mode, messageSyntax, screenTitle, theCommand)"));
        assertTrue(subroutineList.get(2).equals("AA.ACTION.LOAD.CONTEXT"));
        assertTrue(subroutineList.get(3).equals(
                "AA.ACTION.MANAGER(PROCESS.TYPE, PROPERTY.CLASS, PROPERTY, ACTIVITY.ID, PC.ACTIONS, RET.ERROR)"));
        assertTrue(subroutineList
                .get(4)
                .equals(
                        "AA.BUILD.ACTIVITY.RECORD(ARR.ID, ARR.RECORD, ACTIVITY.DATE , PRODUCT.ID, ACTIVITY.ID, TYPE.STATE , PROCESS.DETAIL, BULK.MODE, CURRENT.STATE, OUT.PROPERTY.CLASS, OUT.PROPERTY, OUT.PRODUCT.ONLY, OUT.FUNCTION, OUT.ACTION, OUT.ID, RET.ERROR)"));
        assertTrue(subroutineList.get(5).equals("ABBREVIATION"));
        assertTrue(subroutineList.get(6).equals("ACCOUNT"));
        // Case 4
        subroutineList = helper.getSubroutineList("AA.ACTION.LI");
        assertTrue(subroutineList.size() == 1);
        assertTrue(subroutineList.get(0).equals("AA.ACTION.LIST.MANAGER(mode, messageSyntax, screenTitle, theCommand)"));
        // Case 5
        subroutineList = helper.getSubroutineList("AA.BUILD.ACTIVITY.RECORD(");
        assertTrue(subroutineList.size() == 1);
        assertTrue(subroutineList
                .get(0)
                .equals(
                        "AA.BUILD.ACTIVITY.RECORD(ARR.ID, ARR.RECORD, ACTIVITY.DATE , PRODUCT.ID, ACTIVITY.ID, TYPE.STATE , PROCESS.DETAIL, BULK.MODE, CURRENT.STATE, OUT.PROPERTY.CLASS, OUT.PROPERTY, OUT.PRODUCT.ONLY, OUT.FUNCTION, OUT.ACTION, OUT.ID, RET.ERROR)"));
    }

    @Test
	public void testGetProgramList() {
        List<String> programList = new ArrayList<String>();
        // Case 1
        programList = helper.getProgramList("T");
        assertTrue(programList.size() == 2);
        assertTrue(programList.get(0).equals("T.REMOTE.TRACE"));
        assertTrue(programList.get(1).equals("T24.PRE.RELEASE"));
        // Case 2
        programList = helper.getProgramList("T2");
        assertTrue(programList.size() == 1);
        assertTrue(programList.get(0).equals("T24.PRE.RELEASE"));
    }

    @Test
	public void testGetInsertList() {
        List<String> insertList = new ArrayList<String>();
        // Case 1
        insertList = helper.getInsertList("I_");
        assertTrue(insertList.size() == 1);
        assertTrue(insertList.get(0).equals("I_F.ACCOUNT"));
        // Case 3
        insertList = helper.getInsertList("j");
        assertTrue(insertList.size() == 1);
        assertTrue(insertList.get(0).equals("jBC.h"));
    }

    @Test
	public void testGetKeywordList() {
        List<String> keywordList = new ArrayList<String>();
        // Case 1
        keywordList = helper.getKeywordList("A");
        assertTrue(keywordList.size() == 1);
        assertTrue(keywordList.get(0).equals("ABS"));
        // Case 2
        keywordList = helper.getKeywordList("DEF");
        assertTrue(keywordList.size() == 1);
        assertTrue(keywordList.get(0).equals("DEFFUN"));
        // Case 3
        keywordList = helper.getKeywordList("DE");
        assertTrue(keywordList.size() == 3);
        assertTrue(keywordList.get(0).equals("DEBUG"));
        assertTrue(keywordList.get(1).equals("DEFFUN"));
        assertTrue(keywordList.get(2).equals("DELETE"));
        // Case 4
        keywordList = helper.getKeywordList("LOCATE");
        assertTrue(keywordList.size() == 1);
        assertTrue(keywordList.get(0).equals("LOCATE"));
    }

    @Test
	public void testGetObjectMethods() {
        String[] methods;
        // Case 1
        methods = helper.getObjectMethods("NoObject");
        assertTrue(methods.length == 0);
        // Case 2
        methods = helper.getObjectMethods("Object3");
        assertTrue(methods.length == 2);
        assertTrue(methods[0].equals("method1#method2(arg0),method3()&method4"));
        assertTrue(methods[1].equals("method5"));
        // Case 2
        methods = helper.getObjectMethods("Object2");
        assertTrue(methods.length == 2);
        assertTrue(methods[0].equals("method1(arg0,arg1)"));
        assertTrue(methods[1].equals("method2(arg0,arg1)"));
    }

    @Test
	public void testGetObjectNames() {
        List<String> objectList = new ArrayList<String>();
        // Case 1
        objectList = helper.getObjectNames("NoObject");
        assertTrue(objectList.size() == 0);
        // Case 2
        objectList = helper.getObjectNames("Obj");
        assertTrue(objectList.size() == 3);
        assertTrue(objectList.get(0).equals("Object1"));
        assertTrue(objectList.get(1).equals("Object2"));
        assertTrue(objectList.get(2).equals("Object3"));
        // Case 3
        objectList = helper.getObjectNames("Object1.me");
        assertTrue(objectList.size() == 3);
        assertTrue(objectList.get(0).equals("Object1.method1(arg0,arg1)"));
        assertTrue(objectList.get(1).equals("Object1.method2(arg0,arg1)"));
        assertTrue(objectList.get(2).equals("Object1.method3()"));
    }

    @Test
	public void testIsT24BranchingWord() {
        assertTrue(helper.isT24BranchingWord("CALL"));
        assertTrue(!helper.isT24BranchingWord("CALL$"));
        assertTrue(helper.isT24BranchingWord("$INSERT"));
        assertTrue(!helper.isT24BranchingWord(""));
    }
}
