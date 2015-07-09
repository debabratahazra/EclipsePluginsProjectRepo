package com.temenos.t24.tools.eclipse.basic.views;

import junit.framework.AssertionFailedError;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.junit.Assert;


/**
 * The abstract superclass for all T24BasicView tests
 */
public abstract class AbstractT24Basic {

    /**
     * Assert that the two arrays are equal. Throw an AssertionException if they
     * are not.
     * 
     * @param expected first array
     * @param actual second array
     */
    public void assertEquals(Object[] expected, Object[] actual) {
        if (expected == null) {
            if (actual == null)
                return;
            throw new AssertionFailedError("expected is null, but actual is not");
        } else {
            if (actual == null)
                throw new AssertionFailedError("actual is null, but expected is not");
        }
        Assert.assertEquals("expected.length " + expected.length + ", but actual.length " + actual.length, expected.length, actual.length);
        for (int i = 0; i < actual.length; i++)
        	Assert.assertEquals("expected[" + i + "] is not equal to actual[" + i + "]", expected[i], actual[i]);
    }

    /**
     * Process UI input but do not return for the specified time interval.
     * 
     * @param waitTimeMillis the number of milliseconds
     */
    public void delay(long waitTimeMillis) {
        Display display = Display.getCurrent();
        // If this is the UI thread,
        // then process input.
        if (display != null) {
            long endTimeMillis = System.currentTimeMillis() + waitTimeMillis;
            while (System.currentTimeMillis() < endTimeMillis) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            display.update();
        }
        // Otherwise, perform a simple sleep.
        else {
            try {
                Thread.sleep(waitTimeMillis);
            } catch (InterruptedException e) {
                // Ignored.
            }
        }
    }

    /**
     * Wait until all background tasks are complete.
     */
    // public void waitForJobs() {
    // while (Platform.getJobManager().currentJob() != null)
    // delay(1000);
    // }
    /**
     * Assert the content of the T24 view
     * 
     * @param t24View the view under test
     * @param expectedContent the expected content
     * @param expectedLabels the expected labels for the content
     */
    protected void assertCallsViewContent(IT24View t24View, Object[] expectedContent, Object[] expectedLabels) {
        TableViewer viewer = t24View.getT24Viewer();
        // Assert valid content.
        IStructuredContentProvider contentProvider = (IStructuredContentProvider) viewer.getContentProvider();
        assertEquals(expectedContent, contentProvider.getElements(viewer.getInput()));
        // Assert valid labels.
        ITableLabelProvider labelProvider = (ITableLabelProvider) viewer.getLabelProvider();
        for (int i = 0; i < expectedLabels.length; i++)
            Assert.assertEquals(expectedLabels[i], labelProvider.getColumnText(expectedContent[i], 1));
    }
}
