package com.temenos.t24.tools.eclipse.basic.t24unit;

import java.util.ArrayList;

/**
 * Class represents the T24 Unit test response to be used to build up the view.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestResponse {

    /** Number of passed tests */
    private int passedTests = 0;
    /** Number of failed tests */
    private int failedTests = 0;
    /** Number of tests did not complete */
    private int errorTests = 0;
    /** Total time taken to execute the test */
    private Double totalExecutionTime = 0D;
    /** Tests that are run */
    private ArrayList<T24UnitTest> tests = new ArrayList<T24UnitTest>();
    /** Number of tests earmarked for running */
    private int testsToRun = 0;

    /**
     * Returns the total number of tests.
     * 
     * @return
     */
    public int getTotalTests() {
        return tests.size();
    }

    /**
     * Returns the total number of passed tests.
     * 
     * @return
     */
    public int getPassedTests() {
        return passedTests;
    }

    /**
     * Returns the total number of failed tests
     * 
     * @return
     */
    public int getFailedTests() {
        return failedTests;
    }

    /**
     * Returns the total number of tests that did not complete
     * 
     * @return
     */
    public int getErrorTests() {
        return errorTests;
    }

    /**
     * Returns the execution time
     * 
     * @return
     */
    public Double getExecutionTime() {
        return totalExecutionTime;
    }

    /**
     * Adds the execution time for every individual test
     * 
     * @param executionTime
     */
    public void addExecutionTime(Double executionTime) {
        this.totalExecutionTime += executionTime;
    }

    /**
     * Sets the total number of test earmarked for running
     * 
     * @param count
     */
    public void setTestsToRun(int count) {
        testsToRun = count;
    }

    /**
     * Returns the total number of tests earmarked for running
     * 
     * @return
     */
    public int getTestsToRun() {
        return testsToRun;
    }

    /**
     * Adds test to the response
     * 
     * @param newTest
     */
    public void addTest(T24UnitTest newTest) {
        if (newTest == null || tests.contains(newTest)) {
            return;
        }
        if (newTest.isError()) {
            errorTests++;
        } else if (newTest.isFailed()) {
            failedTests++;
        } else if (newTest.isPassed()) {
            passedTests++;
        }
        tests.add(newTest);
    }

    /**
     * Returns all the tests part of this response
     * 
     * @return
     */
    public ArrayList<T24UnitTest> getAllTests() {
        return tests;
    }
}
