package com.temenos.t24.tools.eclipse.basic.views.t24unit;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;

import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTest;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestCase;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestRerunHandler;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestResponse;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestResult;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestSuite;
import com.temenos.t24.tools.eclipse.basic.t24unit.TestFile;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * This class acts a data provider for the T24 Unit test view. It holds the
 * {@link T24UnitTestResponse} object and supplies required data to the view on
 * demand.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestViewController {

    /** Singleton object */
    private static T24UnitTestViewController testViewController = new T24UnitTestViewController();
    /** T24 unit test suit */
    private T24UnitTestSuite tUnitSuite = null;
    /** T24 unit test response */
    private T24UnitTestResponse testResponse = null;
    /** View mode - all tests are only failed tests are to be displayed */
    private boolean failedTestsOnly = false;
    private T24UnitTestRerunHandler rerunHandler = T24UnitTestRerunHandler.getInstance();

    private T24UnitTestViewController() {
    }

    /**
     * Returns the instance of this class
     * 
     * @return Object of {@link T24UnitTestViewController}
     */
    public static T24UnitTestViewController getInstance() {
        return testViewController;
    }

    /**
     * Sets the T24 unit test response obtained after executing the test
     * 
     * @param testResponse response of the executed test
     */
    public void setTestResponse(T24UnitTestResponse testResponse) {
        this.testResponse = testResponse;
    }

    /**
     * Retruns the T24 unit test suit which comprises of T24 unit tests to be
     * displayed in the view
     * 
     * @param failedTestsOnly Decides whether all the tests required are only
     *            failed tests are required by the view
     * @return T24 Unit test suit. Object of {@link T24UnitTestSuite}
     */
    public T24UnitTestSuite getTestSuite(boolean failedTestsOnly) {
        this.failedTestsOnly = failedTestsOnly;
        tUnitSuite = new T24UnitTestSuite();
        if (testResponse != null) {
            ArrayList<T24UnitTest> allTests = testResponse.getAllTests();
            if (this.failedTestsOnly) {
                buildFailedTestSuite(allTests);
            } else {
                buildFullTestSuite(allTests);
            }
        }
        return tUnitSuite;
    }

    /**
     * Clears the T24Unit test view. Called when a clear view action from view
     * tool bar is called.
     */
    public void clearView() {
        testResponse = new T24UnitTestResponse();
    }

    /**
     * Returns the time taken by the system to complete this test in
     * milliseconds
     * 
     * @return executionTime
     */
    public Double getExecutionTime() {
        if (testResponse == null) {
            return 0d;
        }
        return testResponse.getExecutionTime();
    }

    /**
     * Returns the total number of tests being executed
     * 
     * @return total number of tests
     */
    public int getAllTestsCount() {
        if (testResponse == null) {
            return 0;
        }
        return testResponse.getTotalTests();
    }

    /**
     * Returns the total number of test passed
     * 
     * @return passed tests
     */
    public int getPassedTestsCount() {
        if (testResponse == null) {
            return 0;
        }
        return testResponse.getPassedTests();
    }

    /**
     * Returns the total number of tests failed
     * 
     * @return failed tests
     */
    public int getFailedTestsCount() {
        if (testResponse == null) {
            return 0;
        }
        return testResponse.getFailedTests();
    }

    /**
     * Returns the total number of tests that were not executed due to error
     * 
     * @return error tests
     */
    public int getErrorTestsCount() {
        if (testResponse == null) {
            return 0;
        }
        return testResponse.getErrorTests();
    }

    /**
     * Runs the current tests again
     */
    public void reRun() {
        rerunHandler.run(getTestSuite(false));
    }

    /**
     * Runs selected test
     * 
     * @param selectedNode
     */
    public void run(IT24TreeViewNode selectedNode) {
        T24UnitTest test = getTest(selectedNode);
        if (test == null) {
            return;
        }
        T24UnitTestSuite testSuit = new T24UnitTestSuite();
        testSuit.addParentNode(test);
        rerunHandler.run(testSuit);
    }

    /**
     * Builds the T24 unit test suit with only failed tests from the response
     * 
     * @param failedTests
     */
    private void buildFailedTestSuite(ArrayList<T24UnitTest> failedTests) {
        T24UnitTest test = null;
        Iterator<T24UnitTest> testIterator = failedTests.iterator();
        while (testIterator.hasNext()) {
            test = (T24UnitTest) testIterator.next();
            if (!test.isPassed()) {
                tUnitSuite.addParentNode(test);
            }
        }
    }

    /**
     * Builds the T24 unit test suit with all selected tests
     * 
     * @param allTests
     */
    private void buildFullTestSuite(ArrayList<T24UnitTest> allTests) {
        Iterator<T24UnitTest> testIterator = allTests.iterator();
        while (testIterator.hasNext()) {
            tUnitSuite.addParentNode((T24UnitTest) testIterator.next());
        }
    }

    public Object clone() {
        return new CloneNotSupportedException();
    }

    /**
     * Returns the total number of tests earmarked for execution
     * 
     * @return number of tests
     */
    public int getTestsToRun() {
        if (testResponse == null) {
            return 0;
        }
        return testResponse.getTestsToRun();
    }

    /**
     * Returns true if atlease one of the tests did not pass
     * 
     * @return true if atlease one test did not pass. false otherwise.
     */
    public boolean atleastOneFailed() {
        if (testResponse.getFailedTests() > 0 || testResponse.getErrorTests() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Opens the file in the editor which is represented by the selected node in
     * the tree.
     * 
     * @param node
     */
    public void openSelectedNode(IT24TreeViewNode node) {
        if ((node instanceof IT24TreeViewNode)) {
            TestFile testFile = getTestFile(node);
            if (testFile != null) {
                if (testFile.isLocal()) {
                    IFile file = testFile.getFile();
                    EditorDocumentUtil.openFileWithEditor(file, "", true);
                } else {
                    openRemoteFile(testFile);
                }
            }
        }
    }

    /**
     * Returns the corresponding IFile for the given node from the tree.
     * 
     * @param node
     * @return
     */
    private TestFile getTestFile(IT24TreeViewNode node) {
        T24UnitTest test = getTest(node);
        TestFile testFile = null;
        if (test != null) {
            testFile = test.getTestFile();
        }
        return testFile;
    }

    /**
     * Returns the test object of the node passed. null returned if the passed
     * node does belong to any test.
     * 
     * @param node
     * @return
     */
    private T24UnitTest getTest(IT24TreeViewNode node) {
        if (node == null) {
            return null;
        }
        if (node instanceof T24UnitTestResult) {
            node = ((T24UnitTestResult) node).getParent();
        }
        if (node instanceof T24UnitTestCase) {
            node = ((T24UnitTestCase) node).getParent();
        }
        if (node instanceof T24UnitTest) {
            return (T24UnitTest) node;
        }
        return null;
    }

    private void openRemoteFile(TestFile testFile) {
        RemoteSite remoteSite = testFile.getRemoteSite();
        String fileName = testFile.getFileName();
        String remotePath = testFile.getRemotePath() + "/" + fileName;
        String localPath = RemoteOperationsManager.getInstance().retrieveFile(remoteSite, remotePath, fileName);
        if (localPath == null || localPath.length() <= 0) {
            return;
        }
        RemoteFileEditorUtil.openFileWithEditor(remoteSite, testFile.getRemotePath(), localPath);
    }
}
