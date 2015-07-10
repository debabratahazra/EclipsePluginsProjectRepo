package com.zealcore.se.core.services.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.assertions.IAssertionResult;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.services.IAsserionReportListener;
import com.zealcore.se.core.services.IAssertionReport;
import com.zealcore.se.core.services.IAssertionReportEvent;
import com.zealcore.se.core.services.IAssertionReportService;

/**
 * The Class AssertionReportService.
 */
public class AssertionReportService implements IAssertionReportService {

    /** The listeners. */
    private final Set<IAsserionReportListener> listeners = new HashSet<IAsserionReportListener>();

    private final Map<Logset, IAssertionReport> reportBySession = new HashMap<Logset, IAssertionReport>();

    /**
     * {@inheritDoc}
     */
    public void addAssertionReportListener(
            final IAsserionReportListener listener) {
        if (!listeners.add(listener)) {
            return;
        }

        for (final Entry<Logset, IAssertionReport> entry : reportBySession
                .entrySet()) {
            final IAssertionReportEvent event = AssertionReportEvent.valueOf(
                    entry.getKey(), entry.getValue());
            listener.reportEvent(event);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeAssertionReportListener(
            final IAsserionReportListener listener) {
        listeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void setAssertionSetResults(final Logset session,
            final Iterable<IAssertionSetResult> result) {

        final IAssertionReport report = AssertionReport.valueOf(result);
        notifyListeners(session, report);

        reportBySession.put(session, report);
    }

    public void clearAssertionSetResults() {
        for (final IAsserionReportListener listener : listeners) {
            listener.clearEvent();
        }
    }

    /**
     * Notifies the listeners.
     * 
     * @param report
     *            the report
     */
    private void notifyListeners(final Logset logset,
            final IAssertionReport report) {

        final IAssertionReportEvent event = AssertionReportEvent.valueOf(
                logset, report);
        for (final IAsserionReportListener listener : listeners) {
            listener.reportEvent(event);
        }
    }

    static class AssertionReport implements IAssertionReport {

        private Iterable<IAssertionSetResult> results;

        /**
         * Creates a new IAssertionReport representing the specified results
         * value.
         * 
         * @param result
         *            the results
         * 
         * @return the I assertion report
         */
        public static IAssertionReport valueOf(
                final Iterable<IAssertionSetResult> results) {
            if (results == null) {
                throw new NullPointerException();
            }
            final AssertionReport report = new AssertionReport();

            report.results = results;
            return report;
        }

        public Iterable<IAssertionSetResult> getAssertionSetResults() {
            return results;
        }

        public Iterable<IAssertionResult> getFailures(final IObject object) {

            final Collection<IAssertionResult> assertionResults = new ArrayList<IAssertionResult>();

            for (final IAssertionSetResult result : results) {
                for (final IAssertionResult assertionResult : result) {
                    // If this assertionResult has failed object
                    // it has failed
                    for (final IObject failed : assertionResult) {
                        if (object.equals(failed)) {
                            assertionResults.add(assertionResult);
                            break;
                        }
                    }
                }
            }
            return assertionResults;
        }

        public boolean hasFailed(final IObject object) {
            return getFailures(object).iterator().hasNext();
        }
    }

    /**
     * The Class AssertionReportEvent.
     */
    static final class AssertionReportEvent implements IAssertionReportEvent {

        /** The logset. */
        private Logset logset;

        /** The report. */
        private IAssertionReport report;

        /**
         * The private constructor.
         */
        private AssertionReportEvent() {

        }

        /**
         * {@inheritDoc}
         */
        public Logset getLogSession() {
            return logset;
        }

        /**
         * Creates a new IAssertionReportEvent representing the specified logset
         * and report values.
         * 
         * @param report
         *            the report
         * @param logset
         *            the logset
         * 
         * @return the I assertion report event
         */
        public static IAssertionReportEvent valueOf(final Logset logset,
                final IAssertionReport report) {
            final AssertionReportEvent event = new AssertionReportEvent();
            event.logset = logset;
            event.report = report;
            return event;
        }

        /**
         * {@inheritDoc}
         */
        public IAssertionReport getReport() {
            return report;
        }
    }
}
