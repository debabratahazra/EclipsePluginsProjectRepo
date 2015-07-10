package com.zealcore.se.core.ifw.assertions;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.SearchCriteria;
import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.model.AbstractDuration;
import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ReflectiveType;

public class AssertionTest {

    private static final String DURATION = "Duration";

    public static class TestDuration extends AbstractDuration {
        private final long l;

        public TestDuration(final long l) {
            this.l = l;
        }

        @ZCProperty(name = AssertionTest.DURATION, searchable = true, plottable = true)
        @Override
        public long getDurationTime() {
            return this.l;
        }
    }

    private static final long MAX_DURATION_TIME = 100L;

    private static final String STR_TEST = "Test";

    private static final int SUCCEED = 1;

    private static final int FAIL = 0;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() {
        MockUtil.verifyAll();
    }
    
    @Test
    public void neverNull() {
        Assertion subject = new Assertion();
        assertNotNull(subject.getName());
        assertNotNull(subject.getDescription());
        subject.setName(null);
        subject.setDescription(null);
        assertNotNull(subject.getDescription());
    }

    @Test
    public void testVerify() {

        /*
         * Create a Assertion on TestType which test if getTest == 0
         * 
         */
        final SearchAdapter adapter = SearchAdapter
                .createSearchAdapter(ReflectiveType.valueOf(TestType.class));
        final SearchCriteria searchCriteria = adapter.getCritList().get(1);
		searchCriteria.setOperator1("=");
		searchCriteria.setOperand1(AssertionTest.FAIL);
		searchCriteria.setOperand2(AssertionTest.FAIL);
        Assert.assertEquals(AssertionTest.STR_TEST, searchCriteria
                .getAttributeName());

        final IAssertion subject = Assertion.valueOf(AssertionTest.STR_TEST,
                "Destc", AssertionTest.FAIL, adapter);

        subject.visitBegin(Reason.QUERY_ADDED);

        subject.visit(new TestType(AssertionTest.SUCCEED));
        Assert.assertFalse("The assertion should not on this test", subject
                .getResult().hasFailed());

    }

    @Test
    public void testVerifyWrongType() {

        final SearchAdapter adapter = SearchAdapter
                .createSearchAdapter(ReflectiveType.valueOf(TestType.class));
        final SearchCriteria searchCriteria = adapter.getCritList().get(1);
        Assert.assertEquals(AssertionTest.STR_TEST, searchCriteria
                .getAttributeName());

        final IAssertion subject = Assertion.valueOf(AssertionTest.STR_TEST,
                "Dest", 0, adapter);

        final TestDuration type = new TestDuration(1);

        subject.visitBegin(Reason.QUERY_ADDED);
        Assert.assertTrue("The assertion must not fail on wrong input type",
                subject.visit(type));

    }

    @Test
    public void testVisit2() {

        final SearchAdapter adapter = SearchAdapter
                .createSearchAdapter(ReflectiveType
                        .valueOf(IDuration.class));

        boolean hasSet = false;
        for (final SearchCriteria criteria : adapter.getCritList()) {
            if (criteria.getAttributeName().equalsIgnoreCase(
                    AssertionTest.DURATION)) {
				criteria.setOperand1(AssertionTest.MAX_DURATION_TIME);
				criteria.setOperator1(">=");
				// By default operator2 is < hence the criteria is changed
				criteria.setOperand2(AssertionTest.MAX_DURATION_TIME + 5);
                hasSet = true;
            }
        }
        Assert.assertTrue("Did not set Criteria Values", hasSet);

        final IAssertion subject = Assertion.valueOf("", "", 0, adapter);
        subject.visitBegin(Reason.QUERY_ADDED);
        subject.visit(new TestDuration(AssertionTest.MAX_DURATION_TIME - 1));
        subject.visit(new TestDuration(1));

        final IDuration failedDuration = new TestDuration(
                AssertionTest.MAX_DURATION_TIME + 1);
        subject.visit(failedDuration);

        int failCounter = 0;
        for (final IObject object : subject.getResult()) {
            Assert
                    .assertTrue(
                            "The assertion should only have failed on the failedDuration object",
                            object == failedDuration);
            failCounter++;
        }

        Assert.assertEquals("There should only be one failed object", 1,
                failCounter);

    }

    @Test(expected = IllegalStateException.class)
    public void testUninitializedAssertion() {
        final Assertion subject = new Assertion();
        subject.visit(new TestType(AssertionTest.SUCCEED));
    }

}
