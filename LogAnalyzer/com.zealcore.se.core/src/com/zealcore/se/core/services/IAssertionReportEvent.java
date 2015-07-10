/*
 * 
 */
package com.zealcore.se.core.services;

import com.zealcore.se.core.ifw.Logset;

public interface IAssertionReportEvent {

    /**
     * Gets logset this report is valid for.
     * 
     * @return the log session
     */
    Logset getLogSession();

    /**
     * Gets the report.
     * 
     * @return the report
     */
    IAssertionReport getReport();
}
