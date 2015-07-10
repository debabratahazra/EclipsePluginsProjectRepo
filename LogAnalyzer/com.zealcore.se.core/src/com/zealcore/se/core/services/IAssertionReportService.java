/*
 * 
 */
package com.zealcore.se.core.services;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;

public interface IAssertionReportService {

    /**
     * Adds the listener to the collection of listeners who will be notified
     * when new reports are available, by sending it one of the messages defined
     * in the <code>IAsserionReportListener</code> interface. The first time a
     * listener is added the listener will receive as many reportChange events
     * as there are reports in the service.
     * 
     * A listener will be notified with reports from all logsets.
     * 
     * @param listener
     *                the listener which should be notified
     */
    void addAssertionReportListener(IAsserionReportListener listener);

    /**
     * Removes the listener from the collection of listeners who will be
     * notified when new reports are available
     * 
     * @param listener
     *                the listener which should no longer be notified
     */
    void removeAssertionReportListener(IAsserionReportListener listener);

    /**
     * Sets the assertion results for a specific session. This will produce
     * events to all listeners previously registered with the
     * addAssertionReportListener method. The event will contain a report
     * computed from the passed result parameter. If the result is null, an
     * empty report will be generated
     * 
     * @param result
     *                the result to set, null will clear the result
     * @param logsession
     *                the session
     */
    void setAssertionSetResults(Logset logsession,
            Iterable<IAssertionSetResult> result);

    void clearAssertionSetResults();

}
