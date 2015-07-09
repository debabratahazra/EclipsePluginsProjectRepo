package com.temenos.t24.tools.eclipse.basic.t24unit;

import java.util.ArrayList;

import com.temenos.t24.tools.eclipse.basic.actions.remote.T24UnitTestActionDelegate;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * Class responsible for running the current set of test again from the test
 * suit and build the view.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestRerunHandler {

    private static T24UnitTestRerunHandler rerunHandler = new T24UnitTestRerunHandler();
    /** The array of tests which are to be run again */
    private IT24TreeViewNode[] currentTests;

    private T24UnitTestRerunHandler() {
    }

    /**
     * Returns the singlton object of this class
     * 
     * @return rerunHandler
     */
    public static T24UnitTestRerunHandler getInstance() {
        return rerunHandler;
    }

    /**
     * Runs the tests from the test suit passed
     * 
     * @param testSuit
     */
    public void run(T24UnitTestSuite testSuit) {
        if (testSuit == null) {
            return;
        }
        currentTests = testSuit.getParentNodes();
        T24UnitTestActionDelegate runDelegate = new T24UnitTestActionDelegate();
        runDelegate.run(getTestFiles());
    }

    /**
     * Returns the corresponding files of the test to be run
     * 
     * @return files
     */
    private ArrayList<TestFile> getTestFiles() {
        ArrayList<TestFile> allFiles = new ArrayList<TestFile>();
        for (IT24TreeViewNode test : currentTests) {
            if (test instanceof T24UnitTest) {
                allFiles.add(((T24UnitTest) test).getTestFile());
            }
        }
        return allFiles;
    }
}
