package com.zealcore.se.core.ifw.assertions;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.easymock.EasyMock;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.ImportOperationCancelledException;
import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.SearchCriteria;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.ifw.TestAdapter;
import com.zealcore.se.core.ifw.TestTypeRegistry;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.core.services.internal.MementoService;

public class AssertionIntegrationTest {

    private static final String MUST_NOT_BE_NULL = "Must not be null after initializiation";

    @Before
    public void setUp() {
        TestTypeRegistry.register();
    }

    @After
    public void tearDown() {
        MockUtil.verifyAll();
    }

    @Test
    public void testIntegration() {
        /*
         * populateRegistry(reg); restart
         * 
         * get reg create runner run runner verify expected assertions
         */

        final Path tempFile = new Path("aregistry.xml");

        if (tempFile.toFile().exists()) {
            tempFile.toFile().delete();
        }
        Assert.assertFalse(tempFile.toFile().exists());

        final MementoService service = new MementoService();

        final ITypeRegistry types = MockUtil.niceMock(ITypeRegistry.class);
        final ReflectiveType testTypeType = ReflectiveType
                .valueOf(TestType.class);
        EasyMock.expect(types.getType(testTypeType.getId())).andReturn(
                testTypeType).anyTimes();
        MockUtil.replayAll();
        AssertionRegistry registry = new AssertionRegistry(service, tempFile,
                types);
        populateRegistry(registry);

        // Create a new registry from the temporary file
        registry = new AssertionRegistry(service, tempFile, types);

        final Iterator<IAssertionSet> setIter = registry.getAssertionSets()
                .iterator();
        final IAssertionSet set = setIter.next();
        Assert.assertFalse(setIter.hasNext());
        final Iterator<IAssertion> assertionIter = set.getAssertions()
                .iterator();
        final IAssertion assertion = assertionIter.next();

        Assert.assertNotNull(assertion);
        Assert.assertTrue(assertionIter.hasNext());

        Assert.assertNotNull(assertionIter.next());
        Assert.assertFalse(assertionIter.hasNext());

        assertion.visitBegin(Reason.QUERY_ADDED);
        final TestType element = new TestType();
        assertion.visit(element);

        // Run the runner

        final AssertionRunner runner = new AssertionRunner(set);
        final Logset stubLogSet = Logset.valueOf(UUID.randomUUID());
        try {
			stubLogSet.addLog(new TestAdapter(element));
		} catch (ImportOperationCancelledException e) {	}

        final Iterable<IAssertionSetResult> results = runner.run(stubLogSet, new NullProgressMonitor());
        Assert.assertNotNull(results);

        final Iterator<IAssertionSetResult> resultIter = results.iterator();
        final IAssertionSetResult setResult = resultIter.next();
        Assert.assertFalse(resultIter.hasNext());

        final Iterator<IAssertionResult> assertResultIter = setResult
                .iterator();

        final IAssertionResult ares = assertResultIter.next();
        final IAssertion assertion2 = ares.getAssertion();

        Assert.assertNotNull(AssertionIntegrationTest.MUST_NOT_BE_NULL,
                assertion2.getName());
        Assert.assertNotNull(AssertionIntegrationTest.MUST_NOT_BE_NULL,
                assertion2.getDescription());
        Assert.assertNotNull(AssertionIntegrationTest.MUST_NOT_BE_NULL,
                assertion2.getSeverity());

        // TODO TO FIX
        /*final Iterator<IObject> failedIter = ares.iterator();
        final IObject object = failedIter.next();
        Assert.assertNotNull(object);

        Assert.assertSame(element, object);

        Assert.assertFalse(failedIter.hasNext());

        MockUtil.verifyAll();*/

    }

    private void populateRegistry(final IAssertionRegistry reg) {
        final AssertionSet set = new AssertionSet();

        set.addAssertion(createAssertion(0, 0));
        set.addAssertion(createAssertion(0, 0));

        reg.addAssertionSet(set);
    }

    private IAssertion createAssertion(final int criteriaIndex,
            final int notEquals) {
        /*
         * Creates an adapter on a minimal test type, this type has one
         * searchable property besides the first (index 0) which is inherited
         * from AbstractLogfileItem. This property which always return 0. To
         * assure that the assertion will fail on this type, set data to != 0.
         */
        final SearchAdapter adapter = SearchAdapter
                .createSearchAdapter(ReflectiveType.valueOf(TestType.class));
        final List<SearchCriteria> critList = adapter.getCritList();
        final SearchCriteria searchCriteria = critList.get(criteriaIndex + 1);
        Assert.assertEquals("Test expected certain search criteria order: ",
                "Test", searchCriteria.getAttributeName());
		searchCriteria.setOperator1("=");
		searchCriteria.setOperand1(notEquals);
	//	searchCriteria.setOperator2("=");
		searchCriteria.setOperand2(notEquals);
        final IAssertion assertion = Assertion.valueOf("Assert",
                "Concrete Assertion", criteriaIndex, adapter);
        return assertion;
    }
}
