package com.temenos.t24.tools.eclipse.basic.t24unit;

/**
 * Enumerates the possible results of any T24 Unit test/testcase
 * 
 * @author ssethupathi
 * 
 */
public enum ResultTypes {
    /** Indicates the test is completed and successful */
    PASSED,
    /** Indicates the test is completed but failed */
    FAILED,
    /** Indicates the test has not completed due to error */
    ERROR
}
