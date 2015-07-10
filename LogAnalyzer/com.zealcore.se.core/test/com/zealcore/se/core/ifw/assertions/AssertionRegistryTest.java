/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import java.io.IOException;
import java.util.Iterator;

import org.easymock.EasyMock;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IMemento;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.core.services.IMementoService;

public class AssertionRegistryTest {

    private static final int TWO_TIMES = 2;

    private static final String TEMP_FILE_DOESNOTEXIST = "/temp/doesnotexist";

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    /**
     * Test add and remove assertion set. This test case tests both add and
     * remove, this is due to the similarity in mocked objects which is
     * cumbersome.
     * 
     * @throws IOException
     *                 the IO exception
     */
    @Test
    public final void testAddAndRemoveAssertionSet() throws IOException {

        final IAssertionSet set = MockUtil.newMock(IAssertionSet.class);

        final ITypeRegistry registry = MockUtil.newMock(ITypeRegistry.class);
        final IMementoService service = MockUtil.newMock(IMementoService.class);
        final IMemento2 memento2 = MockUtil.newMock(IMemento2.class);

        final Path noPath = new Path(
                AssertionRegistryTest.TEMP_FILE_DOESNOTEXIST);
        EasyMock.expect(
                service.createWriteRoot(
                        AssertionRegistry.ASSERTION_REGISTRY_NODE, noPath))
                .andReturn(memento2);
        EasyMock.expectLastCall().times(AssertionRegistryTest.TWO_TIMES);

        final IMemento child = MockUtil.newMock(IMemento.class);

        EasyMock.expect(
                memento2.createChild(AssertionRegistry.ASSERTION_SET_NODE))
                .andReturn(child);

        // Expect a saveSate
        set.saveSate(child);

        child.putString(AssertionRegistry.IMPL_NODE, set.getClass().getName());

        memento2.save();
        EasyMock.expectLastCall().times(AssertionRegistryTest.TWO_TIMES);

        final AssertionRegistry subject = new AssertionRegistry(service,
                noPath, registry);
        set.addChangeListener(subject);
        set.removeChangeListener(subject);

        // Start replay
        MockUtil.replayAll();

        subject.addAssertionSet(set);
        subject.addAssertionSet(set);

        subject.removeAssertionSet(set);
        subject.removeAssertionSet(set);

        MockUtil.verifyAll();
    }

    @Test
    public final void testGetAssertionSets() {

        final IAssertionSet set = MockUtil.niceMock(IAssertionSet.class);
        final ITypeRegistry registry = MockUtil.newMock(ITypeRegistry.class);
        final IMementoService service = MockUtil
                .niceMock(IMementoService.class);
        final Path noPath = new Path(
                AssertionRegistryTest.TEMP_FILE_DOESNOTEXIST);

        // Start replay
        MockUtil.replayAll();

        final AssertionRegistry subject = new AssertionRegistry(service,
                noPath, registry);
        try {
            subject.addAssertionSet(set);
            Assert
                    .fail("Expected Nullpointer expected because of untrained mock");
        } catch (final NullPointerException expected) {

        }

        final Iterable<IAssertionSet> assertionSets = subject
                .getAssertionSets();
        final Iterator<IAssertionSet> iterator = assertionSets.iterator();
        Assert.assertTrue(iterator.hasNext());
        final IAssertionSet set2 = iterator.next();
        Assert.assertSame(set2, set);

        Assert.assertFalse("Should have no more sets", iterator.hasNext());

        try {
            iterator.remove();
            Assert.fail("Should not be allowed to remove");
        } catch (final UnsupportedOperationException expected) {

        }

        MockUtil.verifyAll();

    }

}
