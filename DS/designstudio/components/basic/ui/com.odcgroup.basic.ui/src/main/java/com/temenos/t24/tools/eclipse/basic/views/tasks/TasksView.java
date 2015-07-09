package com.temenos.t24.tools.eclipse.basic.views.tasks;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24View;

/**
 * View to display as a list Directories and Files from the server.
 */
public class TasksView extends ViewPart implements IT24View {

    // The following ID is the one defined in the plugin manifest for this View
    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.viewTask";
    private LogConsole log = LogConsole.getT24BasicConsole();
    private TableViewer tableViewerT;
    private final int COL_WIDTH = 200;

    public TasksView() {
    }

    @Override
    /**
     * Main class for creating the view
     */
    public void createPartControl(Composite parent) {
        createTableViewer(parent);
        initializeToolBar();
    }

    /**
     * A TableViewer is an OO wrapper around the a Table widget
     */
    private void createTableViewer(Composite parent) {
        // INSTANCIATE VIEWER
        tableViewerT = new TableViewer(parent, SWT.SINGLE);
        // SET TABLE ELEMENTS
        Table table = tableViewerT.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(false);
        String[] colNames = new String[] { "Description", "Message", "Line", "Program" };
        int[] colWidths = new int[] { COL_WIDTH, COL_WIDTH, COL_WIDTH / 3, COL_WIDTH };
        int[] colAlignments = { SWT.LEFT, SWT.LEFT, SWT.CENTER, SWT.LEFT };
        for (int i = 0; i < colNames.length; i++) {
            TableColumn tabCol = new TableColumn(table, colAlignments[i]);
            tabCol.setText(colNames[i]);
            tabCol.setWidth(colWidths[i]);
        }
        // SET LABEL, CONTENT AND INPUT PROVIDERS
        tableViewerT.setLabelProvider(new TaskLabelsProvider());
        tableViewerT.setContentProvider(new ArrayContentProvider());
        // Associate listeners to each element
        addListeners();
    }

    /**
     * Links a listener to changes on other active pages, so it can react to
     * them.
     */
    private void addListeners() {
        // ASOCIATE LISTENERS AND EVENTS TO VIEWER
        tableViewerT.addDoubleClickListener(new IDoubleClickListener() {

            public void doubleClick(DoubleClickEvent event) {
                ISelection selection = event.getSelection();
                TaskItem clickedItem = (TaskItem) ((IStructuredSelection) selection).getFirstElement();
                if (clickedItem != null) {
                    // CHECK IF THE CORRECT FILE IS OPENED
                    String fullPath = clickedItem.getFullPath();
                    String filename = clickedItem.getFilename();
                    TextEditor curTextEditor = EditorDocumentUtil.getActiveEditor();
                    if (curTextEditor != null && StringUtil.removeBasicExtension(curTextEditor.getTitle()).equals(filename)) {
                        highlightLine(clickedItem.getLine());
                    } else {
                        // The file is not currently opened with an editor. Open
                        // it if it has a local file associated.
                        if (clickedItem.isLocal()) {
                            String result = EditorDocumentUtil.openFileWithEditor(fullPath, filename, null, clickedItem.isLocal());
                            if (!"".equals(result)) {
                                log.logMessage(result);
                            }
                            highlightLine(clickedItem.getLine());
                        } else {
                            log.logMessage(filename
                                    + " could not be opened, it is not a local file. Please retrieve from the server first.");
                        }
                    }
                }
            }
        });
    }

    public void refresh() {
        ProtocolUtil pu = new ProtocolUtil();
        tableViewerT.setInput(pu.getCompileTasks());
    }

    /**
     * Highlights the passed line in the current active editor
     * 
     * @param itemLine
     */
    private void highlightLine(int itemLine) {
        // HIGHLIGHT THE LINE
        if (itemLine != -1) {
            try {
                // Highlight the item in the active editor
                TextEditor activeEditor = EditorDocumentUtil.getActiveEditor();
                IDocument document = EditorDocumentUtil.getActiveDocument();
                if (activeEditor != null) {
                    IRegion lineRegion = document.getLineInformation(itemLine - 1);
                    activeEditor.setHighlightRange(lineRegion.getOffset(), lineRegion.getLength(), true);
                }
            } catch (BadLocationException e) {
                log.logMessage("Attempting to highlight a line in an invalid location in "
                        + EditorDocumentUtil.getActiveEditorFilename() + ".\n");
            }
        } else {
            // NO LINE AVAILABLE, SO DO NOT HIGHLIGHT ANYTHING
        }
    }

    @Override
    public void setFocus() {
        tableViewerT.getControl().setFocus();
    }

    public String getViewID() {
        return VIEW_ID;
    }

    public TableViewer getT24Viewer() {
        return tableViewerT;
    }

    private void initializeToolBar() {
        getViewSite().getActionBars().getToolBarManager();
    }
}
