package com.temenos.t24.tools.eclipse.basic.views;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.editors.text.TextEditor;

import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * Utility class with common actions among Views.
 *  
 * @author lfernandez
 *
 */
public class ViewCommon {

    private ViewManager viewManager;
    
    public ViewCommon(){
        viewManager = new ViewManager();
    }
    
    /**
     * Creates a TableViewer configured with the passed parameters.
     * A TabelViewer is a concrete viewer based on a SWT <code>Table</code> control.
     * @param parent - Composite parent to which this viewer will belong to.
     * @param itemCategory - e.g. BASIC_CALL_ITEM 
     * @param columnNames
     * @param columnWidths
     * @return instance of tableViewer
     */
    public TableViewer createTableViewer(Composite parent, T24_VIEW_ITEM_CATEGORY itemCategory, String[] columnNames, int[] columnWidths) {
        // INSTANCIATE VIEWER
        TableViewer tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.FULL_SELECTION);
        
        // SET TABLE ELEMENTS
        Table table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(false);
        String columnName = columnNames[0];
        int columnAlignment = SWT.CENTER;
        TableColumn column = new TableColumn(table, columnAlignment, 0);
        column.setText(columnName);
        column.setWidth(columnWidths[0]);

        // SET LABEL, CONTENT AND INPUT PROVIDERS
        tableViewer.setLabelProvider(new T24LabelsProvider());
        tableViewer.setContentProvider(new ArrayContentProvider());
        
        // Get the document associated with the active editor
        IDocument document = EditorDocumentUtil.getDocument(EditorDocumentUtil.getActiveEditor());

        tableViewer.setInput(viewManager.getViewItemsGroups(document, itemCategory)); 
        
        // ASOCIATE LISTENERS AND EVENTS TO VIEWER
        tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                    highlightElementSelected(selection);
            }
        });

        return tableViewer;
    }
    
    /**
     * This method has been factored out. It is common for all the views
     * that have elements from an editor, e.g. CALLs, LABELs, etc. Whenever
     * an element is selected in the view, its counterpart within the editor
     * will be highlighted. 
     * @param selection - view item selected with the mouse. 
     */
    public void highlightElementSelected(IStructuredSelection selection) {
        // Transform the selected element into an IT24ViewItem
        T24ViewItemGroup selectedItem = (T24ViewItemGroup) selection.getFirstElement();
        if (selectedItem != null) {
            try {
                // Highlight the item in the active editor
                TextEditor activeEditor = EditorDocumentUtil.getActiveEditor();
                IDocument document = EditorDocumentUtil.getActiveDocument();
                if (activeEditor != null) {
                    int offset = selectedItem.getNextPosition().getOffset();
                    activeEditor.resetHighlightRange();
                    IRegion lineRegion = document.getLineInformationOfOffset(offset);
                    activeEditor.setHighlightRange(lineRegion.getOffset(), lineRegion.getLength(), true);
                 }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }      

    /**
     *
     */
    public void refresh(TableViewer tableViewer, T24_VIEW_ITEM_CATEGORY itemCategory) {
        // Get the document associated with the active editor
        IDocument document = EditorDocumentUtil.getDocument(EditorDocumentUtil.getActiveEditor());
        // Update the input contents
        tableViewer.setInput(viewManager.getViewItemsGroups(document, itemCategory));
    }         
    
      
}
