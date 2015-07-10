package com.zealcore.se.ui.views;

import java.util.Iterator;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;

import com.zealcore.se.core.ifw.ILogAdapter;
import com.zealcore.se.ui.ILogMark;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.editors.LogsetEditor;
import com.zealcore.se.ui.internal.LogMark;
import com.zealcore.se.ui.util.BitCheck;
import com.zealcore.se.ui.util.PartListenerAdapter;
import com.zealcore.se.ui.util.TimeFormat;

public class LogmarkView extends ViewPart implements IResourceChangeListener {

    private static final String DISTANCE_PREFIX = "Distance: ";
    
    private static final String WARNING = "Logmark unavailable!";
    private static final int MIN_COLUMN_WIDTH = 110;

    private TableViewer viewer;

    private static ILogViewInput cluster;

    private PartListenerAdapter partListenerAdapter;

    public static final String VIEW_ID = "com.zealcore.se.ui.views.LogmarkView";
    public LogmarkView() {}

    @Override
    public void createPartControl(final Composite parent) {
    	
        final Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FormLayout());
        viewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.MULTI
                | SWT.BORDER);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new LogmarkTableLabelProvider());
        getSite().setSelectionProvider(viewer);
        final Table table = viewer.getTable();
        final FormData fdTable = new FormData();
        fdTable.right = new FormAttachment(100, -5);
        fdTable.top = new FormAttachment(0, 0);
        fdTable.left = new FormAttachment(0, 0);
        table.setLayoutData(fdTable);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        final TableColumn iconHeader = new TableColumn(table, SWT.NULL);
        final TableColumn timeHeader = new TableColumn(table, SWT.NULL);
        final TableColumn noteHeader = new TableColumn(table, SWT.NULL);

        timeHeader.setWidth(LogmarkView.MIN_COLUMN_WIDTH);
        iconHeader.setWidth(20);
        iconHeader.setResizable(false);
        noteHeader.setWidth(LogmarkView.MIN_COLUMN_WIDTH);
        iconHeader.setText("!");
        timeHeader.setText("Time");
        noteHeader.setText("Note");
        viewer.getTable().redraw();
        
        final Label distanceLabel;
        distanceLabel = new Label(container, SWT.NONE);
        fdTable.bottom = new FormAttachment(distanceLabel, -22, SWT.BOTTOM);
        final FormData fdDistanceLabel = new FormData();
        fdDistanceLabel.bottom = new FormAttachment(100, -6);
        fdDistanceLabel.right = new FormAttachment(0, 400);
        fdDistanceLabel.left = new FormAttachment(0, 5);
        distanceLabel.setLayoutData(fdDistanceLabel);
        distanceLabel.setText(LogmarkView.DISTANCE_PREFIX);
        
        final CLabel warnLabel;
        warnLabel = new CLabel(container, SWT.NONE);
        fdTable.top = new FormAttachment(warnLabel, 20, SWT.TOP);
        
        final FormData fdWarnLabel = new FormData();
        fdWarnLabel.top = new FormAttachment(2, -4);
        fdWarnLabel.right = new FormAttachment(0, 250);
        fdWarnLabel.left = new FormAttachment(0, 3);
        warnLabel.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
        warnLabel.setText(LogmarkView.WARNING);
        warnLabel.setLayoutData(fdWarnLabel);
        
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
        partListenerAdapter = new PartListenerAdapter() {
            @Override
            public void partActivated(final IWorkbenchPartReference partRef) {
                final ILogViewInput clusterInput = LogmarkView.this
                        .getNewInput(partRef);

                if (clusterInput != null) {
                    cluster = clusterInput;
                    LogmarkView.this.touchInput();
                }
            }
        };
        getViewSite().getPage().addPartListener(partListenerAdapter);

        makeAcions();
        initializeToolBar();

        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @SuppressWarnings("unchecked")
            public void selectionChanged(final SelectionChangedEvent event) {
                final IStructuredSelection selection = (IStructuredSelection) viewer
                        .getSelection();
                if (selection.size() == 2) {
                    final Iterator iterator = selection.iterator();
                    final ILogMark first = (ILogMark) iterator.next();
                    final ILogMark second = (ILogMark) iterator.next();

                    final long delta = Math.abs(second.getLogmark()
                            - first.getLogmark());
                    distanceLabel.setText(LogmarkView.DISTANCE_PREFIX
                            + TimeFormat.format(delta));

                } else {
                    distanceLabel.setText(LogmarkView.DISTANCE_PREFIX);
                }
            }
        });

        final IEditorPart editor = getViewSite().getPage().getActiveEditor();
        if (editor instanceof LogsetEditor) {
            final LogsetEditor logEditor = (LogsetEditor) editor;
            cluster = (logEditor.getInput());
            touchInput();
        }
    }

    ILogViewInput getNewInput(final IWorkbenchPartReference partRef) {
        ILogViewInput ci = null;
        final IWorkbenchPart part = partRef.getPart(false);
        if (part instanceof LogsetEditor) {
            final LogsetEditor ed = (LogsetEditor) part;
            ci = ed.getInput();
        }
        final ILogViewInput clusterInput = ci;
        return clusterInput;
    }

    private void makeAcions() {
        viewer.addDoubleClickListener(new IDoubleClickListener() {

            public void doubleClick(final DoubleClickEvent event) {
                final IStructuredSelection struct = (IStructuredSelection) viewer
                        .getSelection();

                final Object o = struct.getFirstElement();
                if (o instanceof ILogMark) {
                    final ILogMark bm = (ILogMark) o;
                    if (cluster != null && cluster.getTimeCluster() != null) {
                        cluster.getTimeCluster()
                                .setCurrentTime(bm.getLogmark());
                    }
                }
            }

        });

        viewer.getTable().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                if (e.keyCode == SWT.DEL) {
                    final IStructuredSelection struct = (IStructuredSelection) viewer
                            .getSelection();

                    if (struct.size() < 1) {
                        return;
                    }

                    String dialogTitle = "";
                    String dialogMessage = "";
                    if (struct.size() == 1) {
                        dialogTitle = "Delete Logmark";
                        dialogMessage = "Are you sure you want to delete the Logmark?";
                    } else {
                        dialogTitle = "Delete Logmarks";
                        dialogMessage = "Are you sure you want to delete the Logmarks?";
                    }
                    final MessageDialog dialog = new MessageDialog(null,
                            dialogTitle, null, dialogMessage,
                            MessageDialog.QUESTION,
                            new String[] { "Yes", "No", }, 0);
                    // yes is the default

                    if (dialog.open() == 0) {
                        for (Iterator<?> iterator = struct.iterator(); iterator
                                .hasNext();) {
                            Object i = (Object) iterator.next();
                            if (i instanceof ILogMark) {
                                final ILogMark bm = (ILogMark) i;
                                if (cluster != null
                                        && cluster.getTimeCluster() != null) {
                                    cluster.getTimeCluster().removeLogmark(bm);
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    @Override
    public void setFocus() {}

    private void touchInput() {
        if (viewer == null) {
            return;
        }
        if (viewer.getTable().isDisposed()) {
            return;
        }
        if (cluster == null) {
            return;
        }

        if (cluster.exists()) {

            final String toolTip = cluster.getCaseFile()
                    + CommonConstants.DELIMITER + cluster.getLog()
                    + CommonConstants.DELIMITER + cluster.getTimeCluster();
            setTitleToolTip(toolTip);
            viewer.setInput(cluster.getTimeCluster().getLogmarks());
        } else if (!cluster.exists() && viewer != null
                && viewer.getContentProvider() != null) {
            viewer.setInput(new Object[0]);
        }
    }

    @Override
    public void dispose() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        getViewSite().getPage().removePartListener(partListenerAdapter);
        super.dispose();
    }

    public void resourceChanged(final IResourceChangeEvent event) {
        if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
            return;
        }
        final int kind = event.getDelta().getKind();
        // If bitmasks aint any of added, removed or changed
        if (!BitCheck.anyOf(kind, IResourceDelta.ADDED, IResourceDelta.REMOVED,
                IResourceDelta.CHANGED)) {

            return;
        }
        // s
        final UIJob job = new UIJob("Updating Logmarks") {
            @Override
            public IStatus runInUIThread(final IProgressMonitor monitor) {
                LogmarkView.this.touchInput();
                return Status.OK_STATUS;
            }
        };
        job.setSystem(true);
        job.schedule();

    }

    private static class LogmarkTableLabelProvider extends LabelProvider
            implements ITableLabelProvider {

        public Image getColumnImage(final Object element, final int columnIndex) {
            // For the first column (!) and if it's log mark.
            if (columnIndex == 0 && element instanceof ILogMark) {
                final ILogMark bm = (ILogMark) element;
                boolean check = false; // To check whether log mark is from correct configured file
                    for (ILogAdapter adpter: cluster.getLogset().getLogs()){
                        // Is the log mark from configured file?
                        if (adpter.getFile().getFullPath().toFile().getName().equalsIgnoreCase((((LogMark)bm).getLogfile())))
                        {
                            check = true;
                            break;
                        }
                    }
                    // If the log mark is from configured file then don't display warning icon.
                    if (check)
                    {
                        return null;
                    }
                return PlatformUI.getWorkbench().getSharedImages()
                     .getImage(ISharedImages.IMG_OBJS_WARN_TSK);
            }
            return null;
        }

        public String getColumnText(final Object element, final int columnIndex) {
            if (element instanceof ILogMark) {
                final ILogMark bm = (ILogMark) element;
                switch (columnIndex) {
                
                case 0:
                	return ""; // Will return the image for the first column from getColumnImage() method.
                                                     
                case 1:
                	 return TimeFormat.format(
                             CommonConstants.DEFAULT_FORMAT_STRING, bm
                                     .getLogmark());
                case 2:
                	return bm.getNote();
                default:
                    return "Unknown Column";
                }
            }
            return super.getText(element);
        }
    }

    private void initializeToolBar() {}
}
