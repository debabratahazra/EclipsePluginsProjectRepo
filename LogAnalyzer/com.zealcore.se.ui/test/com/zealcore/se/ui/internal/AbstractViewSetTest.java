/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.resource.ImageDescriptor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.ui.ILogMark;
import com.zealcore.se.ui.ISynchable;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.util.ArtifactColorMap;

public class AbstractViewSetTest {

    private static final int TEN = 10;

    private static final String TWO_STR = "two";

    private static final String ONE_STR = "One";

    private static final int TWO = 2;

    private static final String DUPLICATION_MESSAGE = "Not allowed to trigger by the same instance twice";

    private Subject subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new Subject(0, 20);
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public final void testAddSynchable() {
        final ISynchable synchable = MockUtil.newMock(ISynchable.class);
        MockUtil.replayAll();
        Assert.assertFalse(this.subject.stateChangeOccured);

        this.subject.addSynchable(synchable);
        Assert.assertFalse(this.subject.stateChangeOccured);

        this.subject.stateChangeOccured = false;
        this.subject.addSynchable(synchable);
        Assert.assertFalse(AbstractViewSetTest.DUPLICATION_MESSAGE,
                this.subject.stateChangeOccured);

        MockUtil.verifyAll();

    }

    @Test
    public final void testLogmark() {
        final String string = "Something";
        final ILogMark mark = this.subject.logmark(string);
        Assert.assertNotNull(mark);
        Assert.assertEquals(this.subject.getCurrentTime(), mark.getLogmark());
        Assert.assertEquals(string, mark.getNote());
    }

    @Test
    public final void testChain() {

        final ILogSet logset = MockUtil.newMock(ILogSet.class);
        final ICaseFile2 casefile = MockUtil.newMock(ICaseFile2.class);
        org.easymock.EasyMock.expect(logset.getCaseFile()).andReturn(casefile);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        final IViewSetChain chain = MockUtil.niceMock(IViewSetChain.class);
        org.easymock.EasyMock.expect(casefile.getViewSetChain()).andReturn(
                chain);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();

        final Subject local = new Subject(0, 0) {
            @Override
            public ILogSet getParent() {
                return logset;
            }
        };

        MockUtil.replayAll();
        Assert.assertFalse("May not be chained by default", this.subject
                .isChained());
        Assert.assertFalse("No previous uncaught change may occur",
                this.subject.stateChangeOccured);

        local.chain();
        Assert.assertTrue("Must respond to chain command", local.isChained());
        Assert.assertTrue("No State Change occured", local.stateChangeOccured);

        local.stateChangeOccured = false;
        local.chain();
        Assert.assertTrue("Should still be chainged", local.isChained());
        Assert.assertFalse("Multiple chain calls should not trigger events",
                local.stateChangeOccured);

        MockUtil.verifyAll();
    }

    @Test
    public final void testGetLogmarks() {
        final ILogMark mark = this.subject.logmark(AbstractViewSetTest.ONE_STR);
        final ILogMark mark2 = this.subject
                .logmark(AbstractViewSetTest.TWO_STR);
        final List<ILogMark> marks = Arrays.asList(this.subject.getLogmarks());
        Assert.assertTrue(marks.contains(mark));
        Assert.assertTrue(marks.contains(mark2));
        Assert.assertEquals(AbstractViewSetTest.TWO, marks.size());

    }

    @Test
    public final void testRemoveLogmark() {
        final ILogMark mark = this.subject.logmark(AbstractViewSetTest.ONE_STR);
        final ILogMark mark2 = this.subject
                .logmark(AbstractViewSetTest.TWO_STR);
        final List<ILogMark> marks = Arrays.asList(this.subject.getLogmarks());
        Assert.assertTrue(marks.contains(mark));
        Assert.assertTrue(marks.contains(mark2));
        Assert.assertEquals(AbstractViewSetTest.TWO, marks.size());

        this.subject.stateChangeOccured = false;
        this.subject.removeLogmark(mark);
        Assert.assertTrue(this.subject.stateChangeOccured);

        this.subject.stateChangeOccured = false;
        this.subject.removeLogmark(mark);
        Assert.assertFalse(AbstractViewSetTest.DUPLICATION_MESSAGE,
                this.subject.stateChangeOccured);

        this.subject.stateChangeOccured = false;
        this.subject.removeLogmark(mark2);
        Assert.assertTrue(this.subject.stateChangeOccured);

    }

    @Test
    public final void testRemoveSynchable() {
        final ISynchable synchable = MockUtil.newMock(ISynchable.class);
        MockUtil.replayAll();
        Assert.assertFalse(this.subject.stateChangeOccured);

        this.subject.addSynchable(synchable);
        Assert.assertFalse(this.subject.stateChangeOccured);

        this.subject.stateChangeOccured = false;
        this.subject.addSynchable(synchable);
        Assert.assertFalse(AbstractViewSetTest.DUPLICATION_MESSAGE,
                this.subject.stateChangeOccured);

        this.subject.removeSynchable(synchable);
        Assert.assertFalse(this.subject.stateChangeOccured);

        this.subject.stateChangeOccured = false;
        this.subject.removeSynchable(synchable);
        Assert.assertFalse(this.subject.stateChangeOccured);

        MockUtil.verifyAll();

    }

    @Test
    public final void testUnChain() {

        final ILogSet logset = MockUtil.newMock(ILogSet.class);
        final ICaseFile2 casefile = MockUtil.newMock(ICaseFile2.class);

        org.easymock.EasyMock.expect(logset.getCaseFile()).andReturn(casefile);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();

        final IViewSetChain chain = MockUtil.niceMock(IViewSetChain.class);
        org.easymock.EasyMock.expect(casefile.getViewSetChain()).andReturn(
                chain);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();

        final Subject local = new Subject(0, 0) {
            @Override
            public ILogSet getParent() {
                return logset;
            }
        };

        MockUtil.replayAll();

        Assert.assertFalse(local.stateChangeOccured);
        local.unChain();
        Assert.assertFalse(local.stateChangeOccured);

        local.chain();
        Assert.assertTrue(local.stateChangeOccured);

        local.stateChangeOccured = false;
        local.unChain();
        Assert.assertTrue(local.stateChangeOccured);

        local.stateChangeOccured = false;
        local.unChain();
        Assert.assertFalse(local.stateChangeOccured);

        MockUtil.verifyAll();
    }

    @Test
    public void testSetCurrentTime() {

        this.subject.setCurrentTime(AbstractViewSetTest.TEN);
        final ISynchable synchable = MockUtil.newMock(ISynchable.class);

        synchable.synch(org.easymock.EasyMock.isA(TimeEvent.class));

        MockUtil.replayAll();

        Assert.assertTrue(AbstractViewSetTest.TEN > 0);
        final ISynchable verifier = new ISynchable() {

            public void synch(final TimeEvent source) {
                Assert.assertEquals((long) AbstractViewSetTest.TEN, source
                        .getPrevious());
                Assert
                        .assertEquals(
                                (long) (AbstractViewSetTest.TEN + AbstractViewSetTest.TEN),
                                source.getSource().getCurrentTime());

            }

        };

        this.subject.addSynchable(synchable);
        this.subject.addSynchable(verifier);
        this.subject.setCurrentTime(AbstractViewSetTest.TEN
                + AbstractViewSetTest.TEN);
        MockUtil.verifyAll();
    }

    /**
     * A concrete implementation of the subject {@link AbstractViewSet}. This
     * class use no implementation of the "left-over" methods, and does not
     * override anything from its parent.
     */
    private static class Subject extends AbstractViewSet {

        /**
         * The state change occured member is used to check if stateChangeed()
         * method was called. Reset this variable after assertion
         * 
         */
        private boolean stateChangeOccured;

        private final int min;

        private final int max;

        public Subject(final int min, final int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        protected void fireStateChanged(final boolean immediate) {
            this.stateChangeOccured = true;
        }

        public int getId() {
            return 0;
        }

        public long getMax() {
            return this.max;
        }

        public long getMin() {
            return this.min;
        }

        public ILogSet getParent() {
            return new ILogSet() {
				
				public Object getAdapter(Class adapter) {
					return null;
				}
				
				public Object getParent(Object o) {
					return null;
				}
				
				public String getLabel(Object o) {
					return null;
				}
				
				public ImageDescriptor getImageDescriptor(Object object) {
					return null;
				}
				
				public Object[] getChildren(Object o) {
					return null;
				}
				
				public void removeChangeListener(IChangeListener changeListener) {
				}
				
				public UUID getId() {
					return null;
				}
				
				public IFolder getFolder() {
					return null;
				}
				
				public ITimeCluster getDefaultViewSet() {
					return null;
				}
				
				public ArtifactColorMap getColorMap() {
					return null;
				}
				
				public void addChangeListener(IChangeListener changeListener) {
				}
				
				public void setCurrentTime(long time) {
				}
				
				public ICaseFile2 getCaseFile() {
					return null;
				}
			};
        }

        public String getUid() {
            return null;
        }
    }
}
