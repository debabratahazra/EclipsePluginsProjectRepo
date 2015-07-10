package com.zealcore.se.core.ifw.assertions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.zealcore.se.core.ifw.AbstractQuery;
import com.zealcore.se.core.ifw.IDataSource;
import com.zealcore.se.core.ifw.IQuery;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;

public class AssertionRunner extends AbstractQuery implements IQuery {

    private static final String RUNNER_IS_NOT_RUNNING_NO_DATA_HAS_BEEN_REQUESTED = "Runner is not running, no data has been requested";

    private static final String ATLEAST_ONE_ASSERTION_SET_MUST_BE_SPECIFIED = "Atleast one assertion set must be specified";

    // Default assertion minimum limit.
    public static final int MIN_ASSERTION_LIMIT = 1000;

    public static final int ASSERTION_INFO_START_INDEX = 0;

    // Assertion minimum limit set by user in preferences. Default is MIN_ASSERTION_LIMIT.
    private static int currentAssertionLimit;

    /** The assertion sets. */
    private final Collection<IAssertionSet> assertionSets = new ArrayList<IAssertionSet>();

    private boolean runMode;

    private AssertionInfo info;

    private int hitCount;

    private int singleHitCount;

    private int skipCount;

    private int totalEvents;

    private int skipEvents;

    private boolean firstTimeAssertion;

    private boolean limitReached;

    private int firstLogEventIndex;

    private int lastLogEventIndex;

    private boolean skipEventsRequired;

    /**
     * The Constructor.
     * 
     * @param assertionSets
     *            the assertion sets
     */
    public AssertionRunner(final Iterable<IAssertionSet> assertionSets,
            final int setLimit, final AssertionInfo info) {

        if (assertionSets == null) {
            throw new NullPointerException(
                    AssertionRunner.ATLEAST_ONE_ASSERTION_SET_MUST_BE_SPECIFIED);
        }

        for (final IAssertionSet set : assertionSets) {
            this.assertionSets.add(set);
        }

        if (info == null) {
            currentAssertionLimit = (setLimit < MIN_ASSERTION_LIMIT) ? MIN_ASSERTION_LIMIT : setLimit;
            this.info = new AssertionInfo();
            this.info.setMaxNumberOfEvents(currentAssertionLimit);
            firstTimeAssertion = true;
        } else {
            this.info = info;
            currentAssertionLimit = this.info.getMaxNumberOfEvents();
        }
    }

    /**
     * The Constructor.
     * 
     * @param assertionSet
     *            the assertion set
     */
    public AssertionRunner(final IAssertionSet assertionSet) {
        if (assertionSet == null) {
            throw new NullPointerException(
                    AssertionRunner.ATLEAST_ONE_ASSERTION_SET_MUST_BE_SPECIFIED);
        }
        assertionSets.add(assertionSet);
    }

    public AssertionInfo getAssertionInfo() {
        return info;
    }

    public Iterable<IAssertionSetResult> run(final Logset logset,
            final IProgressMonitor monitor) {

        // Lock the Logset during running one Assertion.
        synchronized (logset) {

            final ArrayList<IAssertionSetResult> results = new ArrayList<IAssertionSetResult>();

            long syncKey = logset.getLock();
            if (syncKey == -1) {
                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        MessageDialog
                                .openWarning(
                                        Display.getDefault().getActiveShell(),
                                        "Warning",
                                        "Another Log Analyzer activity is currently running.To continue preempt the currently running task or wait for running task to finish its execution");
                    }
                });
                return Collections.EMPTY_LIST;
            } else {
                long currentTime = logset.getCurrentTime();
                try {
                    // Running in Non UI Thread
                    runMode = true;
                    
                    logset.setCurrentTime(0);

                    if (logset.canSkipEvents() && (info != null)) {
                        logset.setNoOfEventsToSkip(info.getCurrentHit()
                                .getFirstLogEventIndex());
                        this.setSkipEventsRequired(true);
                    }

                    logset.addQuery(this, monitor);
                } finally {
                    logset.releaseLock(syncKey);
                    logset.setNoOfEventsToSkip(0);
                    logset.removeQuery(this);
                    logset.setCurrentTime(currentTime);
                    runMode = false;
                }
            }

            for (final IAssertionSet set : assertionSets) {
                results.add(set.getResult());
            }
            return results;
        }
    }

    public void begin() {
        throw new UnsupportedOperationException();
    }

    public void end() {
        throw new UnsupportedOperationException();
    }

    public boolean visitBegin(final Reason reason) {
        for (final IAssertionSet set : assertionSets) {
            set.visitBegin(reason);
        }

        hitCount = 0;
        singleHitCount = 0;
        skipCount = 0;
        totalEvents = 0;
        firstLogEventIndex = 0;
        lastLogEventIndex = 0;
        if (isSkipEventsRequired()) {
            skipEvents = 0;
        } else {
            skipEvents = info.getCurrentHit().getSkipCount();
        }
        return true;
    }

    public boolean visit(final IObject abstractLogFileItem) {

        if (!runMode) {
            throw new IllegalStateException(
                    "Unexpected call to save - "
                            + AssertionRunner.RUNNER_IS_NOT_RUNNING_NO_DATA_HAS_BEEN_REQUESTED);
        }
        totalEvents++;
        if (skipEvents > 0) {
            skipEvents--;
            return true;
        }
        if (abstractLogFileItem instanceof ILogEvent) {
            lastLogEventIndex++;
        }
        for (final IAssertionSet assertionSet : assertionSets) {
            assertionSet.visit(abstractLogFileItem);
        }
        if (!limitReached) {
            singleHitCount = getHitCount();
            if (singleHitCount >= currentAssertionLimit) {
                setSaveResults(false);
                limitReached = true;
            }
        }
        if ((singleHitCount >= currentAssertionLimit) && !firstTimeAssertion) {
            return false;
        } else {
            hitCount = getHitCount();
            if (hitCount >= currentAssertionLimit) {
                info.addHit(new Hit(totalEvents, hitCount, skipCount,
                        firstLogEventIndex, lastLogEventIndex));
                info.setTotalEvents(totalEvents);
                skipCount = totalEvents;
                firstLogEventIndex = lastLogEventIndex;
                resetHitCount();
            }
        }
        return true;
    }

    public void visitEnd(final boolean atEnd) {
        for (final IAssertionSet set : assertionSets) {
            set.visitEnd(atEnd);
        }
        if (firstTimeAssertion) {
            info.addHit(new Hit(totalEvents, hitCount, skipCount,
                    firstLogEventIndex, lastLogEventIndex));
            info.setTotalEvents(totalEvents);
            info.setTotalLogEventsCount(lastLogEventIndex);
        }
        firstTimeAssertion = false;
    }

    public boolean isSkipEventsRequired() {
        return skipEventsRequired;
    }

    public void setSkipEventsRequired(boolean skipEventsRequired) {
        this.skipEventsRequired = skipEventsRequired;
    }

    public void initialize(final IDataSource data) {}

    public int getHitCount() {
        int hitCount = 0;
        for (final IAssertionSet assertionSet : assertionSets) {
            hitCount += assertionSet.getHitCount();
        }
        return hitCount;
    }

    public void resetHitCount() {
        for (final IAssertionSet assertionSet : assertionSets) {
            assertionSet.resetHitCount();
        }
    }

    public void setSaveResults(final boolean save) {
        for (final IAssertionSet assertionSet : assertionSets) {
            assertionSet.setSaveResults(save);
        }
    }

    public static class Hit {

        private final int totalEventCount;

        private final int hitsCount;

        private final int skipCount;

        private final int firstLogEventIndex;

        private final int lastLogEventIndex;

        Hit(final int totalEventCount, final int hitsCount,
                final int skipCount, final int firstLogEventIndex,
                final int lastLogEventIndex) {
            this.totalEventCount = totalEventCount;
            this.hitsCount = hitsCount;
            this.skipCount = skipCount;
            this.firstLogEventIndex = firstLogEventIndex;
            this.lastLogEventIndex = lastLogEventIndex;
        }

        public int getTotalEventCount() {
            return totalEventCount;
        }

        public int getHitsCount() {
            return hitsCount;
        }

        public int getSkipCount() {
            return skipCount;
        }

        public int getFirstLogEventIndex() {
            return firstLogEventIndex;
        }

        public int getLastLogEventIndex() {
            return lastLogEventIndex;
        }
    }

    public static class AssertionInfo {

        private static final long serialVersionUID = 1L;

        private final List<Hit> hitsInfo;

        private int currentHitIndex;

        private int totalEvents;

        private int maxNumberOfEvents;

        private int totalLogEventsCount;

        AssertionInfo() {
            hitsInfo = new ArrayList<Hit>();
            currentHitIndex = ASSERTION_INFO_START_INDEX;
        }

        public void addHit(final Hit hit) {
            hitsInfo.add(hit);
        }

        public int getHitsSize() {
            return hitsInfo.size();
        }

        public Hit getHitInfoAtIndex(final int index) {
            if (index > hitsInfo.size()) {
                return null;
            }
            return hitsInfo.get(index);
        }

        public Hit getCurrentHit() {
            if ((hitsInfo.size() == 0)
                    || ((currentHitIndex + 1) > hitsInfo.size())) {
                return new Hit(0, 0, 0, 0, 0);
            }
            return hitsInfo.get(currentHitIndex);
        }

        public int getCurrentHitIndex() {
            return currentHitIndex;
        }

        public void setCurrentHitIndex(int currentHitIndex) {
            this.currentHitIndex = currentHitIndex;
        }

        public int getTotalEvents() {
            return totalEvents;
        }

        public void setTotalEvents(final int totalEvents) {
            this.totalEvents = totalEvents;
        }

        public int getMaxNumberOfEvents() {
            return maxNumberOfEvents;
        }

        public void setMaxNumberOfEvents(final int maxNumberOfEvents) {
            this.maxNumberOfEvents = maxNumberOfEvents;
        }

        public int getTotalLogEventsCount() {
            return totalLogEventsCount;
        }

        public void setTotalLogEventsCount(final int totalLogEventsCount) {
            this.totalLogEventsCount = totalLogEventsCount;
        }
    }
}
