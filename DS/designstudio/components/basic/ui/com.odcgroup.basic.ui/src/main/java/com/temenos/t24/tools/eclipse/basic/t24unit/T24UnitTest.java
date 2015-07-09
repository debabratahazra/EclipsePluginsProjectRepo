package com.temenos.t24.tools.eclipse.basic.t24unit;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * Class represents a T24 Unit test. Implements {@link IT24TreeViewNode} since
 * it is a node in the T24 Unit test view.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTest implements IT24TreeViewNode {

    /** Object holds the test file */
    private TestFile testFile = null;
    /** String to be used as a lable in case of any error */
    private String errorLabel = null;
    /** Indicates whether this test is completed or not */
    private boolean isError = false;
    /** Indicates whether this test is failed or not */
    private boolean isFailed = false;
    /** status of this test */
    private T24UnitTestStatus status;
    /** test cases belong to this test */
    private ArrayList<T24UnitTestCase> testCases = new ArrayList<T24UnitTestCase>();

    /**
     * Creates a T24 Unit test object for the {@link TestFile} passed
     * 
     * @param test file
     */
    public T24UnitTest(TestFile testFile) {
        this.testFile = testFile;
        status = new T24UnitTestStatus(ResultTypes.PASSED);
    }

    /**
     * Sets the error message if this test did not complete
     * 
     * @param errMsg
     */
    public void setErrorMessage(String errMsg) {
        errorLabel = errMsg;
        isError = true;
        status = new T24UnitTestStatus(ResultTypes.ERROR);
    }

    /**
     * Adds a test case to this test
     * 
     * @param testCase
     */
    public void addTestCase(T24UnitTestCase testCase) {
        if (testCase == null) {
            return;
        }
        if (testCase.isFailedCase()) {
            isFailed = true;
            status = new T24UnitTestStatus(ResultTypes.FAILED);
        }
        if (testCase.isErrorCase()) {
            isError = true;
            status = new T24UnitTestStatus(ResultTypes.ERROR);
        }
        testCase.setParent(this);
        testCases.add(testCase);
    }

    /**
     * Returns the number of test cases
     * 
     * @return number of test cases
     */
    public int getCasesCount() {
        return testCases.size();
    }

    /**
     * Returns the {@link TestFile} which is represented by this test object
     * 
     * @return test file
     */
    public TestFile getTestFile() {
        return testFile;
    }

    /**
     * Returns if this test did not complete due to error
     * 
     * @return true if error false otherwise
     */
    public boolean isError() {
        return isError;
    }

    /**
     * Returns if this test is failed
     * 
     * @return true if the test is failed false otherwise
     */
    public boolean isFailed() {
        return isFailed;
    }

    /**
     * Returns if the test completed and passed
     * 
     * @return true if test passed false otherwise
     */
    public boolean isPassed() {
        return !(isError || isFailed);
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage() {
        if (status == null) {
            return null;
        }
        return status.getImage();
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel() {
        String label = null;
        if (testFile != null) {
            label = testFile.getFileName();
        }
        if (errorLabel != null && errorLabel.length() > 0) {
            label = label + " " + errorLabel;
        }
        return label;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasChildren() {
        if (testCases.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode[] getChildren() {
        if (hasChildren()) {
            int arraySize = testCases.size();
            IT24TreeViewNode[] children = new IT24TreeViewNode[arraySize];
            testCases.toArray(children);
            return children;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode getParent() {
        return null;
    }
}
