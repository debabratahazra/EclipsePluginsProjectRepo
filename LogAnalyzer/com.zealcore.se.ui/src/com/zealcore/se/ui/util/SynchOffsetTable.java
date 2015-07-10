package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

public class SynchOffsetTable {

    public static final String COLUMN_LOGSET = "Logset Name";

    public static final String COLUMN_OFFSET = "Offset (ns)";

    public static final String[] PROPS = { COLUMN_LOGSET, COLUMN_OFFSET };

    TableViewer viewer;

    List<ITimeCluster> clusters;

    ITimeCluster baseCluster;

    boolean readonly;

    public SynchOffsetTable(final ITimeCluster baseCluster,
            final IStructuredSelection selection, final boolean readonly) {
        this.baseCluster = baseCluster;
        this.readonly = readonly;
        this.clusters = new ArrayList<ITimeCluster>();
        if (selection != null) {
            clusters = selection.toList();
        }
    }

    public void createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.None);
        composite.setLayout(new GridLayout(2, false));

        viewer = new TableViewer(composite, SWT.BORDER | SWT.MULTI
                | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
        viewer.setContentProvider(new ArrayContentProvider());

        // Set up the table
        Table table = viewer.getTable();

        createColumns();

        table.setHeaderVisible(true);

        viewer.setInput(createOffsetModelData());
    }

    private void createColumns() {
        // First column is for the first name
        int col_width1 = 100;
        int col_width2 = 150;
        if (readonly) {
            col_width1 = 100;
            col_width2 = 120;
        }
        TableViewerColumn column = createTableViewerColumn(COLUMN_LOGSET,
                col_width1, 0);
        column.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                SynchOffset offset = (SynchOffset) element;
                return offset.getLogsetName();
            }
        });

        column = createTableViewerColumn(COLUMN_OFFSET, col_width2, 1);
        column.setLabelProvider(new CellLabelProvider() {
            @Override
            public void update(ViewerCell cell) {
                cell.setText(String.valueOf(((SynchOffset) cell.getElement())
                        .getOffsetValue()));
            }
        });
        column.setEditingSupport(new OffsetEditingSupport(viewer));
    }

    public TableViewer getViewer() {
        return viewer;
    }

    private TableViewerColumn createTableViewerColumn(String title, int bound,
            final int colNumber) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
                SWT.CENTER);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        return viewerColumn;
    }

    public void update() {
        // Update the base timecluster with new synch offsets
        Set<SynchOffset> offsets = (Set<SynchOffset>) viewer.getInput();
        String baseOffsetVal = "";
        String offsetVal = "";

        for (SynchOffset offset : offsets) {
            baseOffsetVal = offset.getBaseOffsetValue();
            offsetVal = offset.getOffsetValue();
            if (baseOffsetVal.equalsIgnoreCase(offsetVal)) {
                baseCluster.setSynchronizationOffset(offset.getLogsetName(), 0);
            } else {
                baseCluster.setSynchronizationOffset(
                        offset.getLogsetName(),
                        Long.parseLong(offsetVal)
                                - Long.parseLong(baseOffsetVal));
            }
        }

        updateOffsetsCyclically();

        viewer.refresh();
    }

    /**
     * Update the offsets of all logsets cyclically
     */
    private void updateOffsetsCyclically() {

        long oClusterOffset = 0;
        long iClusterOffset = 0;
        int size = 0;

        ITimeCluster oCluster = null;
        ITimeCluster iCluster = null;
        String iClusterName = "";
        String oClusterName = "";
        String oClusterOffsetVal = "";
        String oClusterBaseOffsetVal = "";
        String iClusterOffsetVal = "";
        String iClusterBaseOffsetVal = "";

        // Also update offsets for other logsets cyclically
        Set<SynchOffset> offsets = (Set<SynchOffset>) viewer.getInput();

        SynchOffset[] synchOffsets = offsets.toArray(new SynchOffset[offsets
                .size()]);

        size = synchOffsets.length;

        for (int i = 0; i < size; ++i) {

            oClusterName = synchOffsets[i].getLogsetName();
            oCluster = getSynchedTimeCluster(oClusterName);

            oClusterBaseOffsetVal = synchOffsets[i].getBaseOffsetValue();
            oClusterOffsetVal = synchOffsets[i].getOffsetValue();

            if (oClusterBaseOffsetVal.equalsIgnoreCase(oClusterOffsetVal)) {
                oClusterOffset = 0;
                //oClusterOffset = Long.parseLong(oClusterOffsetVal);
            } else {
                oClusterOffset = Long.parseLong(oClusterOffsetVal)
                        - Long.parseLong(oClusterBaseOffsetVal);
            }

            oCluster.setSynchronizationOffset(baseCluster.getParent()
                    .getFolder().getName(), -oClusterOffset);

            for (int j = i + 1; j < synchOffsets.length; ++j) {
                iClusterName = synchOffsets[j].getLogsetName();
                iCluster = getSynchedTimeCluster(iClusterName);

                iClusterBaseOffsetVal = synchOffsets[j].getBaseOffsetValue();
                iClusterOffsetVal = synchOffsets[j].getOffsetValue();

                if (iClusterBaseOffsetVal.equalsIgnoreCase(iClusterOffsetVal)) {
                    iClusterOffset = 0;
                    //iClusterOffset = Long.parseLong(iClusterOffsetVal);
                } else {
                    iClusterOffset = Long.parseLong(iClusterOffsetVal)
                            - Long.parseLong(iClusterBaseOffsetVal);
                }

                oCluster.setSynchronizationOffset(iClusterName, iClusterOffset
                        - oClusterOffset);

                iCluster.setSynchronizationOffset(oClusterName,
                        -(iClusterOffset - oClusterOffset));
            }
        }
    }

    /**
     * Editing support to the offset table
     * 
     */
    class OffsetEditingSupport extends EditingSupport {

        private final TableViewer viewer;

        public OffsetEditingSupport(TableViewer viewer) {
            super(viewer);
            this.viewer = viewer;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return new TextCellEditor(viewer.getTable());
        }

        @Override
        protected boolean canEdit(Object element) {
            return !readonly;
        }

        @Override
        protected Object getValue(Object element) {
            return String.valueOf(((SynchOffset) element).getOffsetValue());
        }

        @Override
        protected void setValue(Object element, Object value) {

            String offsetString = String.valueOf(value).trim();
            long offset = 0;

            if (offsetString.equals("")) {
                MessageDialog.openError(viewer.getTable().getShell(),
                        "Invalid Offset",
                        "Synchronization Offset value cannot be empty.");
                return;
            }

            try {
                if (offsetString.startsWith("+")) {
                    offset = Long.parseLong(offsetString.substring(1));
                } else {
                    offset = Long.parseLong(offsetString);
                }

            } catch (NumberFormatException nfe) {
                MessageDialog
                        .openError(viewer.getTable().getShell(),
                                "Invalid Offset",
                                "Synchronization Offset value has to be a valid number.");
                return;
            }

            ((SynchOffset) element).setOffsetValue(String.valueOf(offset));
            viewer.refresh();
        }
    }

    /**
     * Create model data to the offset table
     * 
     * @return
     */
    private Set<SynchOffset> createOffsetModelData() {
        Set<SynchOffset> offsets = new TreeSet<SynchOffset>();
        SynchOffset offset = null;
        if (readonly || clusters.size() == 0) {
            populateOffsets(offsets);
        } else {
            String name = null;
            for (ITimeCluster cluster : getClusters()) {
                if (this.baseCluster == cluster) {
                    populateOffsets(offsets);
                    continue;
                }
                offset = new SynchOffset();
                name = cluster.getParent().getFolder().getName();
                offset.setLogsetName(name);
                offset.setBaseOffsetValue(String.valueOf(baseCluster
                        .getCurrentTime() - cluster.getCurrentTime()));
                offsets.add(offset);
            }
        }
        return offsets;
    }

    private void populateOffsets(final Set<SynchOffset> offsets) {
        Set<Entry<String, Long>> offsetsMap = baseCluster
                .getSynchronizationOffsets();
        SynchOffset offset = null;
        ITimeCluster cluster = null;
        for (Entry<String, Long> entry : offsetsMap) {
            offset = new SynchOffset();
            offset.setLogsetName(entry.getKey());

            cluster = getSynchedTimeCluster(entry.getKey());
            // Add baseoffset and offset to be shown on table

            offset.setBaseOffsetValue(String.valueOf((baseCluster
                    .getPrimarySynchronizedEventTime() - cluster
                    .getPrimarySynchronizedEventTime())));
            offset.setOffsetValue(String.valueOf((baseCluster
                    .getPrimarySynchronizedEventTime() - cluster
                    .getPrimarySynchronizedEventTime())
                    + entry.getValue()));
            offsets.add(offset);
        }
    }

    private ITimeCluster getSynchedTimeCluster(String logsetName) {
        String name = null;
        List<ITimeCluster> clusters = getClusters();

        for (ITimeCluster cluster : clusters) {
            name = cluster.getParent().getFolder().getName();
            if (name.equalsIgnoreCase(logsetName)) {
                return cluster;
            }
        }
        return null;
    }

    private List<ITimeCluster> getClusters() {
        List<ITimeCluster> clusters = getSynchedTimeClusters();

        if (!readonly) {
            Set<ITimeCluster> selectedClusters = new LinkedHashSet<ITimeCluster>(
                    clusters);
            selectedClusters.addAll(this.clusters);
            clusters = new ArrayList<ITimeCluster>(selectedClusters);
        }
        return clusters;
    }

    private List<ITimeCluster> getSynchedTimeClusters() {
        List<ITimeCluster> clusterList = new ArrayList<ITimeCluster>();
        ICaseFile casefile = null;
        casefile = baseCluster.getParent().getCaseFile();

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
}
