package com.zealcore.se.ui.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ILogMark;
import com.zealcore.se.ui.ISynchable;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.dialogs.EditSynchOffsetDialog;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

/**
 * This class has test cases testing the SynchLogset action of System Navigator
 */
public class SynchLogsetTest {
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

    private ITimeCluster cluster_4;

    private ITimeCluster cluster_3;

    private ITimeCluster cluster_2;

    private ITimeCluster baseCluster_1;

    private SynchLogset synchronizeLogset;

    @Before
    public void setUp() throws Exception {
        baseCluster_1 = new TestViewSet(LOGSET_NAME_1, LOGSET_SYNCH_TIME_1);
        cluster_2 = new TestViewSet(LOGSET_NAME_2, LOGSET_SYNCH_TIME_2);
        cluster_3 = new TestViewSet(LOGSET_NAME_3, LOGSET_SYNCH_TIME_3);
        cluster_4 = new TestViewSet(LOGSET_NAME_4, LOGSET_SYNCH_TIME_4);

        synchronizeLogset = new SynchLogset();
    }

    @After
    public void tearDown() throws Exception {
        MockUtil.verifyAll();
    }

    @Test
    public void testLogsetActionOk() {
        Display display = new Display();
        Shell shell = new Shell(display);

        // Create mocks to be sent as method arguments

        IStructuredSelection selection = MockUtil
                .newMock(IStructuredSelection.class);

        IWorkbenchPart part = MockUtil.newMock(IWorkbenchPart.class);

        setUpDefaultMocksAndExpectations(shell, part, selection);

        synchronizeLogset.setPart(part);

        UIRunnable click = new UIRunnable(shell, false);
        display.asyncExec(click);

        Assert.assertFalse(synchronizeLogset.registerSynchOffsetsInformation(
                selection, false));

        if (shell.isDisposed()) {
            display.dispose();
        }
    }

    @Test
    public void testLogsetActionCancel() {
        Display display = new Display();
        Shell shell = new Shell(display);

        IStructuredSelection selection = MockUtil
                .newMock(IStructuredSelection.class);

        IWorkbenchPart part = MockUtil.newMock(IWorkbenchPart.class);

        setUpDefaultMocksAndExpectations(shell, part, selection);

        synchronizeLogset.setPart(part);

        UIRunnable click = new UIRunnable(shell, false);
        display.asyncExec(click);

        Assert.assertFalse(synchronizeLogset.registerSynchOffsetsInformation(
                selection, false));

        if (shell.isDisposed()) {
            display.dispose();
        }
    }

    @Test
    public void testSynchLogsetAction() {
        Display display = new Display();
        Shell shell = new Shell(display);

        IStructuredSelection selection = MockUtil
                .newMock(IStructuredSelection.class);

        IWorkbenchPart part = MockUtil.newMock(IWorkbenchPart.class);

        IAction synchronizeAction = MockUtil.newMock(IAction.class);

        Iterator<ITimeCluster> iterator1 = MockUtil.newMock(Iterator.class);

        Iterator<ITimeCluster> iterator2 = MockUtil.newMock(Iterator.class);

        EasyMock.expect(selection.iterator()).andReturn(iterator1).once();

        EasyMock.expect(selection.iterator()).andReturn(iterator2).once();

        iterateSelections(iterator1);

        iterateSelections(iterator2);

        setUpDefaultMocksAndExpectations(shell, part, selection);

        UIRunnable click = new UIRunnable(shell, true);
        display.asyncExec(click);

        Assert.assertFalse(baseCluster_1.isChained());
        Assert.assertFalse(cluster_2.isChained());
        Assert.assertFalse(cluster_3.isChained());
        Assert.assertFalse(cluster_4.isChained());

        baseCluster_1.setSynchronizationOffset(LOGSET_NAME_2, 10);
        baseCluster_1.setSynchronizationOffset(LOGSET_NAME_3, 20);
        baseCluster_1.setSynchronizationOffset(LOGSET_NAME_4, 30);

        synchronizeLogset.setSelection(selection);
        synchronizeLogset.setPart(part);
        synchronizeLogset.runSafe(synchronizeAction);

        Assert.assertTrue(baseCluster_1.isChained());
        Assert.assertTrue(cluster_2.isChained());
        Assert.assertTrue(cluster_3.isChained());
        Assert.assertTrue(cluster_4.isChained());

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

        if (shell.isDisposed()) {
            display.dispose();
        }
    }

    /**
     * It sets up the basic mock objects and also the expectations
     * 
     * @param shell
     */
    private void setUpDefaultMocksAndExpectations(Shell shell,
            IWorkbenchPart part, IStructuredSelection selection) {

        SynchLogset delegate = MockUtil.newMock(SynchLogset.class);

        IWorkbenchPartSite partSite = MockUtil
                .newMock(IWorkbenchPartSite.class);

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

        EasyMock.expect(delegate.getPart()).andReturn(part).anyTimes();
        EasyMock.expect(part.getSite()).andReturn(partSite).anyTimes();
        EasyMock.expect(partSite.getShell()).andReturn(shell).anyTimes();
        EasyMock.expect(selection.getFirstElement()).andReturn(baseCluster_1)
                .anyTimes();

        EasyMock.expect(selection.toList()).andReturn(clusters).anyTimes();
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

        MockUtil.replayAll();

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

        private boolean chained;

        Map<String, Long> offsetMap = new HashMap<String, Long>();

        private long synchTime;

        public TestViewSet(String name, long synchTime) {
            this.name = name;
            this.session = MockUtil.newMock(ILogSessionWrapper.class);
            this.chained = false;
            this.synchTime = synchTime;
        }

        public boolean addSynchable(final ISynchable synchable) {
            throw new NotImplementedException();
        }

        public ILogMark logmark(final String note) {
            throw new NotImplementedException();
        }

        public boolean chain() {
            chained = true;
            return chained;
        }

        public ILogMark[] getLogmarks() {
            throw new NotImplementedException();
        }

        public long getCurrentTime() {
            // throw new NotImplementedException();
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
            return chained;
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
            chained = false;
            return chained;
        }

        public void addLogMark(final ILogMark mark) {
            throw new NotImplementedException();
        }

        public long getSynchronizationOffset() {
            // TODO Auto-generated method stub
            throw new NotImplementedException();
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
            return synchTime;
        }
    }

    /**
     * A UI Runnable which performs UI operations on a non-UI thread.
     * 
     * @author emb-murakri
     * 
     */
    class UIRunnable implements Runnable {
        Shell shell;

        boolean okPressed;

        public UIRunnable(final Shell shell, boolean okPressed) {
            this.shell = shell;
            this.okPressed = okPressed;
        }

        public void run() {
            while (!shell.isDisposed()) {
                if (shell.getDisplay().getActiveShell().getData() instanceof EditSynchOffsetDialog) {
                    EditSynchOffsetDialog dialog = (EditSynchOffsetDialog) shell
                            .getDisplay().getActiveShell().getData();
                    if (okPressed) {
                        dialog.okPressed();
                    } else {
                        dialog.cancelPressed();
                    }
                    shell.close();
                }
            }
        }
    }

    /**
     * It performs a mockup and expectation of an interator over a timecluster
     * list
     * 
     * @param iterator
     */
    private void iterateSelections(Iterator<ITimeCluster> iterator) {
        EasyMock.expect(iterator.hasNext()).andReturn(true).times(4);
        EasyMock.expect(iterator.next()).andReturn(baseCluster_1).times(1);
        EasyMock.expect(iterator.next()).andReturn(cluster_2).times(1);
        EasyMock.expect(iterator.next()).andReturn(cluster_3).times(1);
        EasyMock.expect(iterator.next()).andReturn(cluster_4).times(1);
        EasyMock.expect(iterator.hasNext()).andReturn(false).times(1);
    }
}
