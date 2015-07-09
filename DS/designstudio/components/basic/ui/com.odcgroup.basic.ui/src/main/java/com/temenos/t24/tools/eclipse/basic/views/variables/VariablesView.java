package com.temenos.t24.tools.eclipse.basic.views.variables;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24View;
import com.temenos.t24.tools.eclipse.basic.views.ViewCommon;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * View to show the Variables made within the active editor.
 */
public class VariablesView extends ViewPart implements IT24View {

    /**
     * The following ID is the one defined in the plugin manifest for this View
     */
    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.viewVariables";
    private TableViewer tableViewer;
    private ISelectionListener pageSelectionListener;
    private IEditorPart curActiveEditor;
    private ViewCommon viewCommon;
    private T24_VIEW_ITEM_CATEGORY itemCategory = T24_VIEW_ITEM_CATEGORY.VARIABLE_ITEM;

    public VariablesView() {
        curActiveEditor = EditorDocumentUtil.getActiveEditor();
        viewCommon = new ViewCommon();
    }

    @Override
    /**
     * Main method for creating the view
     */
    public void createPartControl(Composite parent) {
        createTableViewer(parent);
        hookPageSelection();
    }

    /**
     * A TableViewer is a OO wrapper around the a Table widget
     */
    private void createTableViewer(Composite parent) {
        // INSTANTIATE VIEWER
        String[] columnNames = { "Variables" };
        int[] columnWidths = { 200 };
        tableViewer = viewCommon.createTableViewer(parent, itemCategory, columnNames, columnWidths);
    }

    /**
     * Links a listener to changes on other active pages, so it can react to
     * them.
     */
    private void hookPageSelection() {
        // Create a listener for page selection changes
        pageSelectionListener = new ISelectionListener() {

            public void selectionChanged(IWorkbenchPart part, ISelection selection) {
                pageSelectionChanged(part, selection);
            }
        };
        // Link the listener to changes in page 
        getSite().getPage().addPostSelectionListener(pageSelectionListener);
    }

    /**
     * Actions whenever there is a change in the page selection
     * @param part
     * @param selection
     */
    private void pageSelectionChanged(IWorkbenchPart part, ISelection selection) {
        TextEditor newEditor = EditorDocumentUtil.getActiveEditor();
        if ((newEditor != null) && (newEditor != curActiveEditor)) {
            curActiveEditor = newEditor;
            refresh();
        }
    }

    // Refreshes the view's contents.
    public void refresh() {
        viewCommon.refresh(tableViewer, itemCategory);
    }

    @Override
    public void setFocus() {
        tableViewer.getControl().setFocus();
    }

    /**
     * returns the View Id for variables view
     * @return viewId
     */
    public String getViewID() {
        return VIEW_ID;
    }

    /**
     * returns the table viewer
     * @return tableViewer
     */
    public TableViewer getT24Viewer() {
        return tableViewer;
    }
}
