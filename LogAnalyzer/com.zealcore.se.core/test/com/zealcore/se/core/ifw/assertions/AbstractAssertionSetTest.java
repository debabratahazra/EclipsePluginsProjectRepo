package com.zealcore.se.core.ifw.assertions;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.ifw.Reason;

public abstract class AbstractAssertionSetTest {

    private IMocksControl control;

    private IAssertion mockAssertion;

    public AbstractAssertionSetTest() {}

    @Before
    public void setUp() throws Exception {
        this.control = EasyMock.createControl();
        this.mockAssertion = this.control.createMock(IAssertion.class);
    }

    @Test
    public void testBegin() {
        EasyMock.expect(this.mockAssertion.visitBegin(Reason.QUERY_ADDED)).andReturn(true);
        this.control.replay();

        final IAssertionSet subject = getSubject();
        subject.addAssertion(this.mockAssertion);
        subject.visitBegin(Reason.QUERY_ADDED);

        this.control.verify();
    }

    @Test
    public void testEndVerify() {
        this.mockAssertion.visitEnd(true);

        this.control.replay();
        final IAssertionSet subject = getSubject();
        subject.addAssertion(this.mockAssertion);
        subject.visitEnd(true);

        this.control.verify();
    }

    protected abstract IAssertionSet getSubject();
}
