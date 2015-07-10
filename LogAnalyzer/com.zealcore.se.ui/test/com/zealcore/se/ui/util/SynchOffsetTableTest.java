package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ILogMark;
import com.zealcore.se.ui.ISynchable;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

/**
 * This class contains test cases which test update capabilities of offset table
 * 
 */
public class SynchOffsetTableTest {
    public static final String LOGSET_NAME_1 = "LOG1";

    public static final String LOGSET_NAME_2 = "LOG2";

    public static final String LOGSET_NAME_3 = "LOG3";

    public static final String LOGSET_NAME_4 = "LOG4";

    public static final long LOGSET_SYNCH_TIME_1 = Long
            .parseLong("3365900004000");

    public static final long LOGSET_SYNCH_TIME_2 = Long
            .parseLong("3427900004000");

    public static final long LOGSET_SYNCH_TIME_3 = Long
            .parseLong("3429900004000");

    public static final long LOGSET_SYNCH_TIME_4 = Long
            .parseLong("3431900004000");

    SynchOffsetTable offsetTable;

    private Display display;

    private ITimeCluster cluster_4;

    private ITimeCluster cluster_3;

    private ITimeCluster cluster_2;

    private ITimeCluster baseCluster_1;

    @Before
    public void setUp() throws Exception {
        baseCluster_1 = new TestViewSet(LOGSET_NAME_1, LOGSET_SYNCH_TIME_1);
        cluster_2 = new TestViewSet(LOGSET_NAME_2, LOGSET_SYNCH_TIME_2);
        cluster_3 = new TestViewSet(LOGSET_NAME_3, LOGSET_SYNCH_TIME_3);
        cluster_4 = new TestViewSet(LOGSET_NAME_4, LOGSET_SYNCH_TIME_4);
    }

    @After
    public void tearDown() throws Exception {
        if (!display.isDisposed()) {
            display.dispose();
        }
        MockUtil.verifyAll();
    }

    @Test
    public void testSynchOffsetTableUpdate() {
        IStructuredSelection selection = MockUtil
                .newMock(IStructuredSelection.class);

        IFolder logsetFolder_1 = MockUtil.newMock(IFolder.class);
        IFolder logsetFolder_2 = MockUtil.newMock(IFolder.class);
        IFolder logsetFolder_3 = MockUtil.newMock(IFolder.class);
        IFolder logsetFolder_4 = MockUtil.newMock(IFolder.class);

        List<ITimeCluster> clusters = new ArrayList<ITimeCluster>();
        clusters.add(baseCluster_1);
        clusters.add(cluster_2);
        clusters.add(cluster_3);
        clusters.add(cluster_4);

        ICaseFile casefile = MockUtil.newMock(ICaseFile.class);
        Iterator<ITimeCluster> iterator = MockUtil.newMock(Iterator.class);
        Map<String, Long> offsetMap = new HashMap<String, Long>();

        EasyMock.expect(selection.toList()).andReturn(clusters);
        EasyMock.expect(selection.size()).andReturn(4);
        EasyMock.expect(baseCluster_1.getParent().getFolder())
                .andReturn(logsetFolder_1).anyTimes();
        EasyMock.expect(logsetFolder_1.getName()).andReturn(LOGSET_NAME_1)
                .anyTimes();

        EasyMock.expect(cluster_2.getParent().getFolder())
                .andReturn(logsetFolder_2).anyTimes();
        EasyMock.expect(logsetFolder_2.getName()).andReturn(LOGSET_NAME_2)
                .anyTimes();

        EasyMock.expect(cluster_3.getParent().getFolder())
                .andReturn(logsetFolder_3).anyTimes();
        EasyMock.expect(logsetFolder_3.getName()).andReturn(LOGSET_NAME_3)
                .anyTimes();

        EasyMock.expect(cluster_4.getParent().getFolder())
                .andReturn(logsetFolder_4).anyTimes();
        EasyMock.expect(logsetFolder_4.getName()).andReturn(LOGSET_NAME_4)
                .anyTimes();

        EasyMock.expect(baseCluster_1.getParent().getCaseFile())
                .andReturn(casefile).anyTimes();
        EasyMock.expect(casefile.getLogs())
                .andReturn(new ILogSessionWrapper[0]).anyTimes();

        display = new Display();

        Shell shell = new Shell(display);

        Composite parent = new Composite(shell, SWT.NONE);

        MockUtil.replayAll();

        offsetTable = new SynchOffsetTable(baseCluster_1, selection, false);
        offsetTable.createContents(parent);
        // Assert for zero data
        Set<SynchOffset> modelData = (Set<SynchOffset>) offsetTable.viewer
                .getInput();

        Assert.assertEquals(modelData.size(), selection.size() - 1);

        offsetTable.viewer.setInput(createModelData());
        offsetTable.update();

        Assert.assertTrue(baseCluster_1.getSynchronizationOffset(LOGSET_NAME_2) == 10);
        Assert.assertTrue(baseCluster_1.getSynchronizationOffset(LOGSET_NAME_3) == 20);
        Assert.assertTrue(baseCluster_1.getSynchronizationOffset(LOGSET_NAME_4) == 30);

        Assert.assertTrue(cluster_2.getSynchronizationOffset(LOGSET_NAME_1) == -10);
        Assert.assertTrue(cluster_2.getSynchronizationOffset(LOGSET_NAME_3) == 10);
        Assert.assertTrue(cluster_2.getSynchronizationOffset(LOGSET_NAME_4) == 20);

        Assert.assertTrue(cluster_3.getSynchronizationOffset(LOGSET_NAME_1) == -20);
        Assert.assertTrue(cluster_3.getSynchronizationOffset(LOGSET_NAME_2) == -10);
        Assert.assertTrue(cluster_3.getSynchronizationOffset(LOGSET_NAME_4) == 10);

        Assert.assertTrue(cluster_4.getSynchronizationOffset(LOGSET_NAME_1) == -30);
        Assert.assertTrue(cluster_4.getSynchronizationOffset(LOGSET_NAME_2) == -20);
        Assert.assertTrue(cluster_4.getSynchronizationOffset(LOGSET_NAME_3) == -10);
    }

    /**
     * Create dummy model data for the table. Mimick the case of table updation
     * and feed in updated data.
     * 
     * @return
     */
    private Set<SynchOffset> createModelData() {
        Set<SynchOffset> offsets = new TreeSet<SynchOffset>();
        SynchOffset offset = new SynchOffset();
        offset.setLogsetName(LOGSET_NAME_2);
        offset.setBaseOffsetValue("10");
        offset.setOffsetValue("20");
        offsets.add(offset);

        offset = new SynchOffset();
        offset.setLogsetName(LOGSET_NAME_3);
        offset.setBaseOffsetValue("20");
        offset.setOffsetValue("40");
        offsets.add(offset);

        offset = new SynchOffset();
        offset.setLogsetName(LOGSET_NAME_4);
        offset.setBaseOffsetValue("30");
        offset.setOffsetValue("60");
        offsets.add(offset);

        return offsets;
    }

    /**
     * It performs a mockup and expectation of an interator over a timecluster
     * list
     * 
     * @param iterator
     */
    private void iterateClusterList(Iterator<ITimeCluster> iterator) {
        EasyMock.expect(iterator.hasNext()).andReturn(true).times(4);
        EasyMock.expect(iterator.next()).andReturn(baseCluster_1).times(1);
        EasyMock.expect(iterator.next()).andReturn(cluster_2).times(1);
        EasyMock.expect(iterator.next()).andReturn(cluster_3).times(1);
        EasyMock.expect(iterator.next()).andReturn(cluster_4).times(1);
        EasyMock.expect(iterator.hasNext()).andReturn(false).times(1);
    }

    /**
     * This is a stub class representing a logset or timecluster
     */
    public class TestViewSet implements ITimeCluster {

        private final ArrayList<ISynchable> syncs = new ArrayList<ISynchable>();

        private long time;

        private long max;

        private long min;

        private ILogSessionWrapper session;

        private String name;

        Map<String, Long> offsetMap = new HashMap<String, Long>();

        private long synchTime;

        public TestViewSet(String name, long synchTime) {
            this.name = name;
            this.session = MockUtil.newMock(ILogSessionWrapper.class);
            this.synchTime = synchTime;
        }

        public boolean addSynchable(final ISynchable synchable) {
            throw new NotImplementedException();
        }

        public ILogMark logmark(final String note) {
            throw new NotImplementedException();
        }

        public boolean chain() {
            throw new NotImplementedException();
        }

        public ILogMark[] getLogmarks() {
            throw new NotImplementedException();
        }

        public long getCurrentTime() {
            return time;
        }

        public int getId() {
            throw new NotImplementedException();
        }

        public long getMax() {
            throw new NotImplementedException();
        }

        public long getMin() {
            throw new NotImplementedException();
        }

        public void setMax(final long max) {
            throw new NotImplementedException();
        }

        public void setMin(final long min) {
            throw new NotImplementedException();
        }

        public String getName() {
            return this.getUid();
        }

        public ILogSessionWrapper getParent() {
            return session;
        }

        public String getUid() {
            return name;
        }

        public boolean isChained() {
            throw new NotImplementedException();
        }

        public void removeLogmark(final ILogMark bm) {
            throw new NotImplementedException();
        }

        public boolean removeSynchable(final ISynchable synchable) {
            throw new NotImplementedException();
        }

        public void setCurrentTime(final long time) {
            throw new NotImplementedException();
        }

        public boolean unChain() {
            throw new NotImplementedException();
        }

        public void addLogMark(final ILogMark mark) {
            throw new NotImplementedException();
        }

        public long getSynchronizationOffset() {
            return synchTime;
        }

        public void setSynchronizationOffset(long offset) {
            throw new NotImplementedException();
        }

        public void setSynchronizationOffset(final String offsetWith,
                final long offset) {
            if (offsetWith.equalsIgnoreCase(this.getName())) {
                return;
            }
            offsetMap.put(offsetWith.toLowerCase(), offset);
        }

        public long getSynchronizationOffset(final String offsetWith) {
            Long offset0 = offsetMap.get(offsetWith.toLowerCase());
            return (offset0 == null) ? 0 : offset0;
        }

        public Set<Entry<String, Long>> getSynchronizationOffsets() {
            return Collections.unmodifiableSet(offsetMap.entrySet());
        }

        public Object removeOffset(String name) {
            return offsetMap.remove(name);
        }

        public void removeAllOffsets() {
            offsetMap.clear();
        }

        public long getPrimarySynchronizedEventTime() {
            throw new NotImplementedException();
        }
    }
}
