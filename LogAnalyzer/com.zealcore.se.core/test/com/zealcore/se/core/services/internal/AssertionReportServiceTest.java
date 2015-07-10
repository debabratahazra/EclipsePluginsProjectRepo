package com.zealcore.se.core.services.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.assertions.IAssertion;
import com.zealcore.se.core.ifw.assertions.IAssertionResult;
import com.zealcore.se.core.ifw.assertions.IAssertionSet;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.ifw.assertions.TestType;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.services.IAsserionReportListener;
import com.zealcore.se.core.services.IAssertionReport;
import com.zealcore.se.core.services.IAssertionReportEvent;
import com.zealcore.se.core.services.IAssertionReportService;

public class AssertionReportServiceTest {

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testAddAssertionReportListener() {
        final IAssertionReportService subject = new AssertionReportService();

        final IAsserionReportListener listener = MockUtil
                .newMock(IAsserionReportListener.class);

        MockUtil.replayAll();
        subject.addAssertionReportListener(listener);
        MockUtil.verifyAll();
    }

    @Test
    public void testRemoveAssertionReportListener() {
        final IAssertionReportService subject = new AssertionReportService();
        final IAsserionReportListener listener = MockUtil
                .newMock(IAsserionReportListener.class);

        MockUtil.replayAll();
        subject.removeAssertionReportListener(listener);
        MockUtil.verifyAll();
    }

    @Test
    public void testSetAssertionSetResults() {
        final IAssertionReportService subject = new AssertionReportService();

        final IAsserionReportListener listener = MockUtil
                .newMock(IAsserionReportListener.class);
        listener.reportEvent(EasyMock.isA(IAssertionReportEvent.class));

        final IAsserionReportListener concrete = new IAsserionReportListener() {

            public void reportEvent(final IAssertionReportEvent event) {
                Assert.assertNotNull(event.getReport());
                Assert.assertNotNull(event.getLogSession());
                listener.reportEvent(event);

            }

            public void clearEvent() {
                listener.clearEvent();
            }

        };
        MockUtil.replayAll();

        subject.addAssertionReportListener(concrete);
        final Logset session = Logset.valueOf(null);
        final Iterable<IAssertionSetResult> result = new ArrayList<IAssertionSetResult>();
        subject.setAssertionSetResults(session, result);

        MockUtil.verifyAll();
    }

    @Test
    public void testSetAssertionSetResultsBeforeAdd() {
        final IAssertionReportService subject = new AssertionReportService();

        final IAsserionReportListener listener = MockUtil
                .newMock(IAsserionReportListener.class);
        listener.reportEvent(EasyMock.isA(IAssertionReportEvent.class));
        MockUtil.replayAll();

        final Logset session = Logset.valueOf(null);
        final Iterable<IAssertionSetResult> result = new ArrayList<IAssertionSetResult>();
        subject.setAssertionSetResults(session, result);

        subject.addAssertionReportListener(listener);

        MockUtil.verifyAll();
    }

    @Test
    public void testGetFailures() {
        final Collection<IAssertionSetResult> results = new ArrayList<IAssertionSetResult>();
        final IObject failedObject = new TestType();
        final TestType succeeded = new TestType(1);

        final IAssertionResult assertionResult = new IAssertionResult() {

            public IAssertion getAssertion() {
                throw new UnsupportedOperationException();
            }

            public boolean hasFailed() {
                return true;
            }

            public Iterator<IObject> iterator() {
                Collection<IObject> collection = new ArrayList<IObject>();
                collection.add(failedObject);
                return collection.iterator();
            }
        };

        final IAssertionSetResult assertionSetResult = new IAssertionSetResult() {

            public IAssertionSet getAssertionSet() {
                throw new UnsupportedOperationException();
            }

            public boolean hasFailed() {
                return true;
            }

            public Iterator<IAssertionResult> iterator() {
                Collection<IAssertionResult> assertionResults = new ArrayList<IAssertionResult>();
                assertionResults.add(assertionResult);
                return assertionResults.iterator();
            }

        };

        results.add(assertionSetResult);

        final IAssertionReport subject = AssertionReportService.AssertionReport
                .valueOf(results);
        Assert.assertSame(results, subject.getAssertionSetResults());

        // Check for failed object
        Iterable<IAssertionResult> failures = subject.getFailures(failedObject);

        Assert.assertNotNull(failures);
        Iterator<IAssertionResult> iterator = failures.iterator();
        Assert.assertTrue(iterator.hasNext());

        final IAssertionResult nextResult = iterator.next();
        Assert.assertNotNull(nextResult);
        Assert.assertNotNull(nextResult.iterator());
        Assert.assertNotNull(nextResult.iterator().next());
        final Iterator<IObject> iter = nextResult.iterator();
        Assert.assertSame(iter.next(), failedObject);
        Assert.assertFalse(iter.hasNext());

        Assert.assertTrue(subject.hasFailed(failedObject));

        // Assert for the succeded object

        failures = subject.getFailures(succeeded);

        Assert.assertNotNull(failures);
        iterator = failures.iterator();
        Assert.assertFalse(
                "Succeded object should not have any failed assertions",
                iterator.hasNext());
        Assert.assertFalse("Succeded object should not have failed", subject
                .hasFailed(succeeded));

    }

}
