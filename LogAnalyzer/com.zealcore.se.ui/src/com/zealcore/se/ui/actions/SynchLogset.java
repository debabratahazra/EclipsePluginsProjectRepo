package com.zealcore.se.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;

import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.dialogs.EditSynchOffsetDialog;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

public class SynchLogset extends AbstractObjectDelegate {
    public SynchLogset() {}

    @SuppressWarnings("unchecked")
    @Override
    public void runSafe(final IAction action) {
        ISelection selections = getSelection();
        if (!(selections instanceof IStructuredSelection)) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) selections;

        /*
         * Check if all selected logsets already are synchronized, if true we
         * will desync
         */
        boolean allIsSync = true;
        for (Iterator i = struct.iterator(); i.hasNext();) {
            Object obj = (Object) i.next();
            if ((obj instanceof ITimeCluster)) {
                final ITimeCluster cluster = (ITimeCluster) obj;
                if (!cluster.isChained()) {
                    allIsSync = false;
                }
            }
        }

        /**
         * check to see if synchronization factor is applied 
         */
        if (!registerSynchOffsetsInformation(struct, allIsSync)) {
            return;
        }

        if (allIsSync) {
            /*
             * Desynchronize logsets
             */
            ITimeCluster cluster = null;
            for (Iterator i = struct.iterator(); i.hasNext();) {
                Object obj = (Object) i.next();
                if ((obj instanceof ITimeCluster)) {
                    cluster = (ITimeCluster) obj;
                    cluster.unChain();
                }
            }

            /*
             * If we only have one timecluster left in the sync-chain, desync it
             */
            List<ITimeCluster> syncTcs = CaseFileManager
                    .getSynchedTimeClusters();
            if (syncTcs.size() == 1) {
                syncTcs.get(0).unChain();
            }
        } else {
            /*
             * Add logsets/clusters to synch chain
             */
            for (Iterator i = struct.iterator(); i.hasNext();) {
                Object obj = (Object) i.next();
                if ((obj instanceof ITimeCluster)) {
                    final ITimeCluster cluster = (ITimeCluster) obj;
                    if (!cluster.isChained()) {
                        cluster.chain();
                    }
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        super.selectionChanged(action, selection);

        if (selection instanceof IStructuredSelection) {
            final IStructuredSelection struct = (IStructuredSelection) selection;

            if (struct.size() < 2) {
                action.setEnabled(false);
            } else {
                action.setEnabled(true);
            }

            /*
             * Check if all selected logsets already are synchronized, if true
             * we will desynch
             */
            boolean allIsSync = true;
            for (Iterator i = struct.iterator(); i.hasNext();) {
                Object obj = (Object) i.next();
                if ((obj instanceof ITimeCluster)) {
                    final ITimeCluster cluster = (ITimeCluster) obj;
                    if (!cluster.isChained()) {
                        allIsSync = false;
                    }
                }
            }
            if (allIsSync) {
                action.setText("Desynchronize");
            } else {
                action.setText("Synchronize");
            }
        }
    }

    /**
     * Set/resets the offsets information to all participating logsets depending
     * on allSynched is true or false.
     * 
     * @param selection
     * @param allSynched
     * @return
     */
    boolean registerSynchOffsetsInformation(IStructuredSelection selection,
            boolean allSynched) {
        if (selection == null) {
            throw new IllegalArgumentException();
        }
        boolean success = true;

        if (!allSynched) {
            EditSynchOffsetDialog synchOffsetDialog = new EditSynchOffsetDialog(
                    getShell(), (ITimeCluster) selection.getFirstElement(),
                    selection);

            if (synchOffsetDialog.open() == Window.CANCEL) {
                success = false;
            }
        } else {
            // pre-desynchronization....reset offset values

            if (getSynchedTimeClusters(selection).size() != selection.toList()
                    .size()) {

                for (ITimeCluster baseCluster : (List<ITimeCluster>) selection
                        .toList()) {

                    String baseClusterName = baseCluster.getParent()
                            .getFolder().getName();
                    Set<Entry<String, Long>> offsetsMap = baseCluster
                            .getSynchronizationOffsets();

                    ITimeCluster iCluster = null;

                    for (Entry<String, Long> entry : offsetsMap) {
                        iCluster = getSynchedTimeCluster(selection,
                                entry.getKey());
                        if (iCluster != null) {
                            iCluster.removeOffset(baseClusterName);
                        }
                    }
                }
            }
        }
        return success;
    }

    /**
     * It returns list of synchronized time clusters(aka logsets) based on a
     * given selection on the resource navigator.
     * 
     * @param selection
     * @return
     */
    private List<ITimeCluster> getSynchedTimeClusters(
            IStructuredSelection selection) {
        if (selection == null) {
            throw new IllegalArgumentException();
        }
        List<ITimeCluster> clusterList = new ArrayList<ITimeCluster>();
        ICaseFile casefile = null;
        if (selection.size() != 0) {
            if (selection.getFirstElement() instanceof ITimeCluster) {
                casefile = ((ITimeCluster) selection.getFirstElement())
                        .getParent().getCaseFile();
            }
        }

        if (casefile != null) {
            for (ILogSessionWrapper session : casefile.getLogs()) {
                ITimeCluster cluster = session.getDefaultViewSet();
                if (cluster.isChained()) {
                    clusterList.add(cluster);
                }
            }
        }
        return clusterList;
    }

    /**
     * Helper method to retrieve a specific synchronized cluster from a
     * selection
     * 
     * @param selection
     * @param logsetName
     * @return
     */
    private ITimeCluster getSynchedTimeCluster(
            final IStructuredSelection selection, String logsetName) {
        List<ITimeCluster> clusters = CaseFileManager.getSynchedTimeClusters();

        Set tClusters = new LinkedHashSet<ITimeCluster>(clusters);
        tClusters.addAll(selection.toList());
        clusters = new ArrayList<ITimeCluster>(tClusters);
        String name = null;
        for (ITimeCluster cluster : clusters) {
            name = cluster.getParent().getFolder().getName();
            if (name.equalsIgnoreCase(logsetName)) {
                return cluster;
            }
        }
        return null;
    }
}
