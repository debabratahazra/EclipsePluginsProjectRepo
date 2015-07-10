package com.zealcore.se.ui.core.report;

/**
 * Implement this interface to be able to contribute to reports
 * 
 * @see AbstractReport
 * @author daze
 * 
 */
public interface IReportContributor {

    /**
     * Called when the caller want's the contributor to contribute to a report
     * The contributor should populate the report objet with report items.
     * 
     * @see AbstractReportItem
     * @see AbstractReport
     * @param report
     *                The report object used to submit report items to
     */
    void fillReport(final AbstractReport report);
}
