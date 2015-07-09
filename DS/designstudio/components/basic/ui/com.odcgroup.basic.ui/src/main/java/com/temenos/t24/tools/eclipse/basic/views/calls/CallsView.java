package com.temenos.t24.tools.eclipse.basic.views.calls;

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
 * View to show the CALLs made within the active editor.
 */
public class CallsView extends ViewPart implements IT24View {
    // The following ID is the one defined in the plugin manifest for this View
    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.viewCalls";
    private TableViewer tableViewer;
    private ISelectionListener pageSelectionListener;
    private IEditorPart curActiveEditor;
    private ViewCommon viewCommon;
    private T24_VIEW_ITEM_CATEGORY itemCategory  = T24_VIEW_ITEM_CATEGORY.CALL_ITEM;

    public CallsView() {
        curActiveEditor = EditorDocumentUtil.getActiveEditor();
        viewCommon      = new ViewCommon();
    }

    @Override
    /**
     * Main class for creating the view
     */
    public void createPartControl(Composite parent) {
        createTableViewer(parent);
        hookPageSelection();
    }
   
    /**
     * A TableViewer is a OO wrapper around the a Table widget
     */
    private void createTableViewer(Composite parent) {
        // INSTANCIATE VIEWER
        String[] columnNames = {"Function Calls"};
        int[] columnWidths   = {200};
        tableViewer = viewCommon.createTableViewer(parent, itemCategory, columnNames, columnWidths);
        
        
    }
    
    /**
     * Links a listener to changes on other active pages, so it can react to
     * them.
     */
    private void hookPageSelection() {
        // Create a listener for page selection changes
        pageSelectionListener = new ISelectionListener() {
           public void selectionChanged(IWorkbenchPart part,ISelection selection) {
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
        if ((newEditor!=null) && (newEditor!=curActiveEditor)){
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
    
    public String getViewID(){
        return VIEW_ID;
    }

    public TableViewer getT24Viewer() {
        return tableViewer;
    }
}
