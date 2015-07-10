package com.zealcore.se.core.ifw.assertions;

import java.util.Iterator;

import org.easymock.EasyMock;
import org.eclipse.ui.IMemento;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.SearchCriteria;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.AbstractQuery;
import com.zealcore.se.core.ifw.IDataSource;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;

public class AssertionSetTest extends AbstractAssertionSetTest {

    private static final int FAILED = 0;

    private static final String STR_TEST = "Test";

    static final String SHOULD_NOT_FAIL_ON_THIS_ITEM = "Should not fail on this item";

    private static final int SUCCEED = AssertionSetTest.FAILED + 1;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() {
        MockUtil.verifyAll();
    }

    @Test
    public void testVisitFailure() {

        final IAssertionSet subject = getSubject();

        final IAssertion assertion = createTestAssertion();

        subject.addAssertion(assertion);

        final TestType type = new TestType(AssertionSetTest.FAILED);

        subject.visitBegin(Reason.QUERY_ADDED);
        subject.visit(type);

        subject.visitEnd(true);
        Assert.assertTrue(
                "The assertion set should contain atleast one failed object",
                subject.getResult().hasFailed());

    }

    @Override
    protected IAssertionSet getSubject() {
        return AssertionSet.valueOf("subject", "tested");
    }

    @Test
    public void testVisitNonFailure() {

        final IAssertionSet subject = getSubject();

        final IAssertion assertion = createTestAssertion();

        subject.addAssertion(assertion);

        final TestType type = new TestType(AssertionSetTest.SUCCEED);

        subject.visitBegin(Reason.QUERY_ADDED);
        subject.visit(type);

        subject.visitEnd(true);
        Assert.assertFalse("The assertion set should not have failed objects",
                subject.getResult().hasFailed());

    }

    private IAssertion createTestAssertion() {
        final SearchAdapter adapter = SearchAdapter
                .createSearchAdapter(ReflectiveType.valueOf(TestType.class));
        final SearchCriteria searchCriteria = adapter.getCritList().get(1);
        Assert.assertEquals(AssertionSetTest.STR_TEST, searchCriteria
                .getAttributeName());
		searchCriteria.setOperator1("=");
		searchCriteria.setOperand1(AssertionSetTest.FAILED);
		searchCriteria.setOperand2(AssertionSetTest.SUCCEED);
        final IAssertion assertion = Assertion.valueOf(
                AssertionSetTest.STR_TEST, "Dest", 0, adapter);
        return assertion;
    }

    @Test
    public void testInit() {

        final AssertionSet subject = new AssertionSet();

        final IMemento memento = MockUtil.newMock(IMemento.class);
        final ITypeRegistry registry = MockUtil.newMock(ITypeRegistry.class);

        final IMemento assertionOneMemento = MockUtil.newMock(IMemento.class);
        final IMemento assertionTwoMemento = MockUtil.newMock(IMemento.class);

        EasyMock.expect(memento.getString(AssertionSet.NAME_NODE)).andReturn(
                "Mock 1");

        EasyMock.expect(memento.getString(AssertionSet.DESCRIPTION_NODE))
                .andReturn("Desc 1");

        EasyMock.expect(memento.getChildren(AssertionSet.ASSERTIONS_NODE))
                .andReturn(
                        new IMemento[] { assertionOneMemento,
                                assertionTwoMemento, });

        final String impl = DummyImpl.class.getName();
        EasyMock.expect(assertionOneMemento.getString(AssertionSet.IMPL_NODE))
                .andReturn(impl);
        EasyMock.expect(assertionTwoMemento.getString(AssertionSet.IMPL_NODE))
                .andReturn(impl);

        MockUtil.replayAll();

        subject.init(memento, registry);

        final Iterator<IAssertion> iter = subject.getAssertions().iterator();
        Assert.assertTrue(iter.hasNext());
        iter.next();
        Assert.assertTrue(iter.hasNext());
        iter.next();
        Assert.assertFalse(iter.hasNext());

        MockUtil.verifyAll();

    }

    public static class DummyImpl extends AbstractQuery implements IAssertion {

        public String getDescription() {
            return null;
        }

        public String getName() {
            return null;
        }

        public int getSeverity() {
            return 0;
        }

        public IType getTestedType() {
            return null;
        }

        public void init(final IMemento memento, final ITypeRegistry typeService) {

        }

        public void saveState(final IMemento memento) {

        }

        public void setDescription(final String description) {
            throw new NotImplementedException();

        }

        public void setName(final String name) {
            throw new NotImplementedException();

        }

        public void setSeverity(final int severity) {
            throw new NotImplementedException();
        }

        public String getCondition() {
            throw new NotImplementedException();
        }

        public boolean visitBegin(final Reason r) {
            throw new UnsupportedOperationException();
        }

        public void visitEnd(final boolean a) {
            throw new UnsupportedOperationException();
        }

        public boolean visit(final IObject item) {
            throw new UnsupportedOperationException();
        }

        public IAssertionResult getResult() {
            throw new UnsupportedOperationException();
        }

        public void initialize(final IDataSource data) {
        }

		public int getHitCount() {
			return 0;
		}

		public void resetHitCount() {
		
		}

		public void setSaveResults(boolean save) {
		
		}
    }

    @Test
    public void testSaveSate() {
        final AssertionSet subject = new AssertionSet();
        final IMemento memento = MockUtil.newMock(IMemento.class);

        final IAssertion assertion = MockUtil.newMock(IAssertion.class);
        final IMemento aMem1 = MockUtil.newMock(IMemento.class);

        final IAssertion assertion2 = MockUtil.newMock(IAssertion.class);
        final IMemento aMem2 = MockUtil.newMock(IMemento.class);

        assertion.saveState(aMem1);
        assertion2.saveState(aMem2);

        memento.putString(AssertionSet.NAME_NODE, null);
        memento.putString(AssertionSet.DESCRIPTION_NODE, null);

        EasyMock.expect(memento.createChild(AssertionSet.ASSERTIONS_NODE))
                .andReturn(aMem1);
        EasyMock.expect(memento.createChild(AssertionSet.ASSERTIONS_NODE))
                .andReturn(aMem2);

        aMem1.putString(AssertionSet.IMPL_NODE, assertion.getClass().getName());
        aMem2
                .putString(AssertionSet.IMPL_NODE, assertion2.getClass()
                        .getName());

        MockUtil.replayAll();
        subject.addAssertion(assertion);
        subject.addAssertion(assertion2);

        subject.saveSate(memento);

        MockUtil.verifyAll();
    }

}
