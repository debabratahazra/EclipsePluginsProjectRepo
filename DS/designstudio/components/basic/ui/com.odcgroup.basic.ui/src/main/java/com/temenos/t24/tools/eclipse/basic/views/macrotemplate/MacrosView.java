package com.temenos.t24.tools.eclipse.basic.views.macrotemplate;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.temenos.t24.tools.eclipse.basic.views.IT24View;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;

public class MacrosView extends AbstractViewPartMacroTemplate implements IT24View {
    // The following ID is the one defined in the plugin manifest for this View
    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.viewMacros";
    protected static final String COLUMN_NAME = "Macros"; 
    protected static final int COLUMN_WIDTH = 200;

    private MacroUtil macroUtil = null;
    private IT24ViewItem macroSelected; 
    
    public MacrosView() {
        macroUtil = new MacroUtil();        
    }
    
    /**
     * Links a listener to changes on other active pages, so it can react to
     * them.
     */
    @Override
    protected void addListeners() {
        // SELECT A MACRO WITH MOUSE
        tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                macroSelected = (IT24ViewItem) selection.getFirstElement();
            }
        });
        
        // DOUBLE CLICK AND RUN MACRO
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                macroSelected = (IT24ViewItem) selection.getFirstElement();      
                MacroActions ma = new MacroActions();
                ma.runMacro(macroSelected);
            }
        });
        
        // RUN MACRO BUTTON
        buttonRun.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                MacroActions ma = new MacroActions();
                ma.runMacro(macroSelected);
            }
        });
        
        // EDIT MACRO BUTTON
        buttonEdit.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                MacroActions ma = new MacroActions();
                ma.editMacro(macroSelected);
                refresh();
            }
        });
        
        // CREATE MACRO BUTTON
        buttonCreate.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                MacroActions ma = new MacroActions();
                ma.createMacro();
                refresh();
            }
        });        
        
        // DELETE MACRO BUTTON
        buttonDelete.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                // Deletes the previously selected (see macroSelected)
                MacroActions ma = new MacroActions();
                ma.deleteMacro(macroSelected);
                refresh();
            }
        });        
    }

    public void refresh() {
        // Update the input contents
        tableViewer.setInput(macroUtil.getMacrosStored());
        setFocus();
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

    /**
     * Used by CreateControl in a template pattern fashion.
     */
    @Override
    protected IT24ViewItem[] getViewItems() {
        return macroUtil.getMacrosStored();
    }

}

