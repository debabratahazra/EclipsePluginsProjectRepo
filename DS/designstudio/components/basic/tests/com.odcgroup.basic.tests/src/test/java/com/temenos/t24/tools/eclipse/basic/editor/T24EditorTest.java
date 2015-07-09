package com.temenos.t24.tools.eclipse.basic.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.junit.Assert;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.editors.scanners.T24BasicPartitionScanner;
import com.temenos.t24.tools.eclipse.basic.views.AbstractT24Basic;

/**
 * The class <code>ViewManagerTest</code> contains tests for the class
 * {@link com.temenos.t24.tools.eclipse.basic.views.ViewManager}.
 */
public class T24EditorTest extends AbstractT24Basic {

    private static final String EDITOR_PARTITIONS_TEST_FILE = "testfiles\\t24EditorTest_partitions1.txt";

    @Test
	public void testT24Partitions() {
        IDocument document = new Document();
        String contents = getFileContents(EDITOR_PARTITIONS_TEST_FILE);
        // A partitioner is created 
        IDocumentPartitioner partitioner = new FastPartitioner(
                new T24BasicPartitionScanner(), 
                new String[] {
                    T24BasicPartitionScanner.BASIC_COMMENT, 
                    T24BasicPartitionScanner.BASIC_LITERAL });
        // Associate partitioner with document
        partitioner.connect(document);
        // And document with partitioner
        document.setDocumentPartitioner(partitioner);
        document.set(contents);
        try {
            ITypedRegion[] trs = document.computePartitioning(0, document.getLength());
//            for (int i = 0; i < trs.length; i++) {
//                System.out.println("line: " + document.getLineOfOffset(trs[i].getOffset()) + " " + trs[i].getType()+
//                        " "+document.get(trs[i].getOffset(), trs[i].getLength()));
//            }
            
            Assert.assertTrue("__basic_comment".equals(trs[0].getType()));
            String text = document.get(trs[0].getOffset(), trs[0].getLength());
            Assert.assertTrue("REM COMMENT_1\r\n".equals(text));
            
            Assert.assertTrue("__dftl_partition_content_type".equals(trs[1].getType()));
            text = document.get(trs[1].getOffset(), trs[1].getLength());
            Assert.assertTrue("PROGRAM\r\n".equals(text));
            
            Assert.assertTrue("__basic_comment".equals(trs[2].getType()));
            text = document.get(trs[2].getOffset(), trs[2].getLength());
            Assert.assertTrue("* THIS IS ANOTHER COMMENT\r\n".equals(text));
            
            Assert.assertTrue("__basic_literal".equals(trs[3].getType()));
            text = document.get(trs[3].getOffset(), trs[3].getLength());
            Assert.assertTrue("\"THIS IS A LITERAL\"".equals(text));
            
            Assert.assertTrue("__dftl_partition_content_type".equals(trs[4].getType()));
            text = document.get(trs[4].getOffset(), trs[4].getLength());
            Assert.assertTrue("\r\nCALL LABEL1\r\nEND\r\n".equals(text));
                        
        } catch (BadLocationException e) {
            e.printStackTrace();
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
    
    @Test
    public void testT24RTCToolsMenuActivities(){
    	IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint("org.eclipse.ui.activities");
        IExtension extension = point.getExtension("com.odcgroup.tafj.activities");
        Assert.assertNotNull(extension);
        IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI.getWorkbench().getActivitySupport();
        Set enableIds = workbenchActivitySupport.getActivityManager().getEnabledActivityIds();
        Assert.assertNotNull(enableIds);
        Assert.assertEquals(1, enableIds.size());
    }
}
