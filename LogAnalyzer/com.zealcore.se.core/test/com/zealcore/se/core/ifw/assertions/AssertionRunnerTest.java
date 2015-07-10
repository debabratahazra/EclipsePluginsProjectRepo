package com.zealcore.se.core.ifw.assertions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.easymock.EasyMock;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.ImportOperationCancelledException;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.ifw.TestAdapter;
import com.zealcore.se.core.ifw.TestTypeRegistry;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.AbstractObject;
import com.zealcore.se.core.model.ILogEvent;

public class AssertionRunnerTest {

    @Before
    public void setUp() throws Exception {
        TestTypeRegistry.register();

    }

    @After
    public void tearDown() throws Exception {}

    @Test(expected = IllegalStateException.class)
    public final void testSave() {
        final IAssertionSet set = MockUtil.newMock(IAssertionSet.class);
        final AssertionRunner subject = new AssertionRunner(set);

        subject.visit(new AbstractObject() {});

    }

    @Test
    public final void testRun() {

        final IAssertionSet set = MockUtil.newMock(IAssertionSet.class);
        final IAssertionSetResult mockResult = MockUtil
                .niceMock(IAssertionSetResult.class);
        final ILogEvent item1 = new AbstractLogEvent() {};
        final ILogEvent item2 = new AbstractLogEvent() {};

        Collection<IAssertionSet> assertionSets = new ArrayList<IAssertionSet>();
        assertionSets.add(set);
        final AssertionRunner subject = new AssertionRunner(assertionSets, AssertionRunner.MIN_ASSERTION_LIMIT, null);
        final Logset stubLogSet = Logset.valueOf(UUID.randomUUID());

        final java.util.List<ILogEvent> data = new ArrayList<ILogEvent>();
        data.add(item1);
        data.add(item2);
        try {
			stubLogSet.addLog(new TestAdapter(data));
		} catch (ImportOperationCancelledException e) {}

        final IAssertion assertion = MockUtil.newMock(IAssertion.class);

        final Collection<IAssertion> oneAssertion = new ArrayList<IAssertion>();
        oneAssertion.add(assertion);

        EasyMock.expect(set.visitBegin(Reason.QUERY_ADDED)).andReturn(true);
       // EasyMock.expect(set.visit(item1)).andReturn(true);
       // EasyMock.expect(set.visit(item2)).andReturn(true);

        set.visitEnd(true);

        EasyMock.expect(set.getResult()).andReturn(mockResult);
        MockUtil.replayAll();

        final Iterable<IAssertionSetResult> results = subject.run(stubLogSet, new NullProgressMonitor());

        Assert.assertNotNull(results);

        MockUtil.verifyAll();

    }
}
