package com.temenos.t24.tools.eclipse.basic.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 */
public class ViewManagerPerformanceTest extends AbstractT24Basic {

    private static final String VIEW_PERFORMANCE_TEST_FILE = "testfiles\\viewMgrTest_performance.txt";
    /**
     * The object that is being tested.
     * 
     * @see com.temenos.t24.tools.eclipse.basic.views.calls.CallsView
     */
    private ViewManager viewMgr;

    /**
     * Perform pre-test initialization.
     * 
     * @throws Exception
     * @see TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
        viewMgr = new ViewManager();
    }

    /**
     * Performance test. The idea behind this test is to open a real life large
     * file (15000 lines of code) and read its contents. The test does not
     * intend to check the validity of the contents within the file. Other tests
     * with smaller and more controllable contents have already been designed to
     * this effect.
     * 
     * The test is intended so that tools like JUnitPerf can detect changes in
     * the performance of the code, should the code change.
     * 
     */
    @Test
	public void testPerformanceTest() {
        IDocument document = new Document();
        String contents = getFileContents(VIEW_PERFORMANCE_TEST_FILE);
        document.set(contents);
        viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.CALL_ITEM);
        IT24ViewItem[] insertItems = viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.INSERT_ITEM);
        viewMgr.getViewItems(document, T24_VIEW_ITEM_CATEGORY.LABEL_AND_T24REGION_ITEM);
        // only the very first insert is checked.
        Assert.assertTrue("I_COMMON".equals(insertItems[0].getLabel()));
        Assert.assertTrue(true);
    }

    /**
     * 
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
