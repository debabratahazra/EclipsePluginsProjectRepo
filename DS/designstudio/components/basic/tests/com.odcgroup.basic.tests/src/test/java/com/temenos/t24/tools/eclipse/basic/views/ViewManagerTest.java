package com.temenos.t24.tools.eclipse.basic.views;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * The class <code>ViewManagerTest</code> contains tests for the class
 * {@link com.temenos.t24.tools.eclipse.basic.views.ViewManager}.
 */
public class ViewManagerTest { // extends AbstractT24Basic {

    private static final String VIEW_CALLS_TEST_FILE = "testfiles\\viewMgrTest_callsView.txt";
    private static final String VIEW_INSERTS_TEST_FILE = "testfiles\\viewMgrTest_insertsView.txt";
    private static final String VIEW_LABELS_TEST_FILE = "testfiles\\viewMgrTest_labelsView.txt";
    private static final String VIEW_REGIONS_TEST_FILE = "testfiles\\viewMgrTest_regionsView.txt";
    private static final String VIEW_LABELS_REGIONS_TEST_FILE = "testfiles\\viewMgrTest_labelsAndregionsView.txt";
    private static final String VIEW_TODO_TEST_FILE = "testfiles\\viewMgrTest_TODOView.txt";
    private static final String VIEW_GOSUB_TEST_FILE = "testfiles\\viewMgrTest_GosubView.txt";
    private static final String VIEW_GROUP_TEST_FILE = "testfiles\\viewMgrTest_GroupView.txt";
    private static final String VIEW_VARIABLE_TEST_FILE = "testfiles\\viewMgrTest_Variables.txt";

    /**
     * The object that is being tested.
     * 
     * @see com.temenos.t24.tools.eclipse.basic.views.calls.CallsView
     */
    private ViewManager viewMgr;

    @Before
    public void setup() {
        String location = "spring/test.plugin.spring.xml";
        ClassPathXmlApplicationContext springApplicationContext = new ClassPathXmlApplicationContext(location);
        T24BasicPlugin.setSpringApplicationContext(springApplicationContext);
        viewMgr = new ViewManager();
    }

    @Test
	public void testViewItemsGroup() {
        IDocument document = new Document();
        String contents = getFileContents(VIEW_GROUP_TEST_FILE);
        document.set(contents);
        T24ViewItemGroup[] groups = viewMgr.getViewItemsGroups(document, T24_VIEW_ITEM_CATEGORY.CALL_ITEM);
        // for(int i=0; i<groups.length; i++){
        // System.out.println(groups[i].getLabel());
        // T24ViewItemGroup group = groups[i];
        // for(int j=0; j<group.getNoItems(); j++){
        // System.out.println("item"+j+" - offset:
        // "+group.getNextPosition().getOffset());
        // }
        // }
        assertTrue("TEST1.MODULE".equals(groups[0].getLabel()));
        assertTrue("TEST2.MODULE".equals(groups[1].getLabel()));
        assertTrue("TEST3.MODULE".equals(groups[2].getLabel()));
        assertTrue(2 == groups[0].getNoItems());
        assertTrue(3 == groups[1].getNoItems());
        assertTrue(1 == groups[2].getNoItems());
        // Test that nextPossition is rotating within a group.
        assertTrue(5 == groups[0].getNextPosition().getOffset());
        assertTrue(100 == groups[0].getNextPosition().getOffset());
        assertTrue(5 == groups[0].getNextPosition().getOffset());
    }

    @Test
	public void testCallsView() {
        IDocument document = new Document();
        String contents = getFileContents(VIEW_CALLS_TEST_FILE);
        document.set(contents);
        IT24ViewItem[] items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.CALL_ITEM);
        assertEquals("TEST_A.MODULE", items[0].getLabel());
        assertEquals("TEST_E.MODULE", items[1].getLabel());
        assertEquals("TEST_F.MODULE", items[2].getLabel());
        assertEquals("TEST_K.CALL", items[3].getLabel());
        assertEquals("TEST_L.MODULE", items[4].getLabel());
        assertEquals("TEST_M.MODULE", items[5].getLabel());
        assertEquals("TEST_O.MODULE", items[6].getLabel());
        assertEquals("TEST_P.MODULE", items[7].getLabel());
        assertEquals("TEST_R", items[8].getLabel());
        assertEquals("TEST_S", items[9].getLabel());
        assertEquals("TEST_T", items[10].getLabel());
        assertEquals("TEST_U", items[11].getLabel());
        document.set("");
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.CALL_ITEM);
        assertEquals(0, items.length);
        document = null;
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.CALL_ITEM);
        assertEquals(0, items.length);
    }

    @Test
	public void testInsertsView() {
        // Create Document that will be used for testing
        IDocument document = new Document();
        String contents = getFileContents(VIEW_INSERTS_TEST_FILE);
        document.set(contents);
        // Get the Inserts items from file
        IT24ViewItem[] items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.INSERT_ITEM);
        // for(int i=0; i<items.length; i++){
        // System.out.println(items[i].getLabel());
        // }
        assertEquals("I_COMMON", items[0].getLabel());
        assertEquals("I_F.ACCT.STATEMENT.CHARGE", items[1].getLabel());
        assertEquals("TEST1.INSERT", items[2].getLabel());
        assertEquals("TEST4.INSERT", items[3].getLabel());
        document = null;
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.INSERT_ITEM);
        assertEquals(0, items.length);
    }

    @Test
	public void testGosubsView() {
        // Create Document that will be used for testing
        IDocument document = new Document();
        String contents = getFileContents(VIEW_GOSUB_TEST_FILE);
        document.set(contents);
        // Get the items from file
        IT24ViewItem[] items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.GOSUB_ITEM);
        // for (int i = 0; i < items.length; i++) {
        // System.out.println(items[i].getLabel());
        // }
        assertEquals("TEST1.BLA", items[0].getLabel());
        assertEquals("TEST2.BLA", items[1].getLabel());
        document = null;
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.INSERT_ITEM);
        assertEquals(0, items.length);
    }

    @Test
	public void testLabelsView() {
        IDocument document = new Document();
        String contents = getFileContents(VIEW_LABELS_TEST_FILE);
        document.set(contents);
        // Get the items from file
        IT24ViewItem[] items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        // for(int i=0; i<items.length; i++){
        // System.out.println(items[i].getLabel());
        // }
        assertEquals("FIELD.DISPLAY.OR.INPUT", items[0].getLabel());
        assertEquals("TEST1.LABEL", items[1].getLabel());
        assertEquals("TEST2.LABEL", items[2].getLabel());
        assertEquals("TEST3.LABEL", items[3].getLabel());
        assertEquals("TEST4.SUBRTN", items[4].getLabel());
        assertEquals("TEST5.SUBRTN", items[5].getLabel());
        document = null;
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        assertEquals(0, items.length);
    }

    @Test
	public void testT24RegionsView() {
        // Create Document that will be used for testing
        IDocument document = new Document();
        String contents = getFileContents(VIEW_REGIONS_TEST_FILE);
        document.set(contents);
        // Get the items from file
        IT24ViewItem[] items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.T24REGION_ITEM);
        // for(int i=0; i<items.length; i++){
        // System.out.println(items[i].getLabel());
        // }
        assertEquals("TEST1.REGION", items[0].getLabel());
        assertEquals("TEST2.REGION", items[1].getLabel());
        document = null;
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.T24REGION_ITEM);
        assertEquals(0, items.length);
    }

    @Test
	public void testLabelsT24RegionsView() {
        // Create Document that will be used for testing
        IDocument document = new Document();
        String contents = getFileContents(VIEW_LABELS_REGIONS_TEST_FILE);
        document.set(contents);
        // Get the items from file
        IT24ViewItem[] items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.LABEL_AND_T24REGION_ITEM);
        // for(int i=0; i<items.length; i++){
        // System.out.println(items[i].getLabel());
        // }
        // Note: labels are retrieved first, then come regions
        assertEquals("FIELD.DISPLAY.OR.INPUT", items[0].getLabel());
        assertEquals("TEST1.LABEL", items[1].getLabel());
        assertEquals("TEST2.LABEL", items[2].getLabel());
        assertEquals("TEST3.LABEL", items[3].getLabel());
        assertEquals("TEST1.REGION", items[4].getLabel());
        assertEquals("TEST2.REGION", items[5].getLabel());
        document = null;
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.LABEL_AND_T24REGION_ITEM);
        assertEquals(0, items.length);
    }

    @Test
	public void testT24TODOView() {
        // Create Document that will be used for testing
        IDocument document = new Document();
        String contents = getFileContents(VIEW_TODO_TEST_FILE);
        document.set(contents);
        // Get the items from file
        IT24ViewItem[] items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.TODO_ITEM);
        // for(int i=0; i<items.length; i++){
        // System.out.println(items[i].getLabel());
        // }
        // Note: labels are retrieved first, then come regions
        assertEquals("test1 - comment", items[0].getLabel());
        assertEquals("test2 - comment", items[1].getLabel());
        assertEquals("test3 - comment  ", items[2].getLabel());
        assertEquals("test4 - comment", items[3].getLabel());
        assertEquals("test5 - comment", items[4].getLabel());
        document = null;
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.TODO_ITEM);
        assertEquals(0, items.length);
    }

    @Test
	public void testT24VariableView() {
        // Create Document that will be used for testing
        IDocument document = new Document();
        String contents = getFileContents(VIEW_VARIABLE_TEST_FILE);
        document.set(contents);
        // Get the items from file
        IT24ViewItem[] items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.VARIABLE_ITEM);
        assertEquals(26, items.length);
        assertEquals("average", items[0].getLabel());
        assertEquals("count", items[3].getLabel());
        assertEquals("key", items[6].getLabel());
        assertEquals("record", items[9].getLabel());
        assertEquals("temperature", items[12].getLabel());
        assertEquals("tempKey", items[15].getLabel());
        assertEquals("tempRecord", items[17].getLabel());
        assertEquals("time", items[18].getLabel());
        assertEquals("VM", items[20].getLabel());
        document = null;
        items = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.VARIABLE_ITEM);
        assertEquals(0, items.length);
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
