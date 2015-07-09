package com.temenos.t24.tools.eclipse.basic.views.macrotemplate;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24View;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;


/**
 * View to display as a list Directories and Files from the server.
 */
public class TemplatesView extends AbstractViewPartMacroTemplate implements IT24View {
    // The following ID is the one defined in the plugin manifest for this View
    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.viewTemplates";
    protected static final String COLUMN_NAME = "Templates";
    protected static final int COLUMN_WIDTH = 200;

    private TemplateUtil templateUtil = null;
    private IT24ViewItem templateSelected; 
    
    public TemplatesView() {
        templateUtil = new TemplateUtil();        
    }

    /**
     * Links a listener to changes on other active pages, so it can react to
     * them.
     */
    @Override
    protected void addListeners() {
        // SELECT A TEMPLATE WITH MOUSE
        tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                templateSelected = (IT24ViewItem) selection.getFirstElement();
            }
        });
        
        // DOUBLE CLICK AND RUN TEMPLATE
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                templateSelected = (IT24ViewItem) selection.getFirstElement();      
                TemplateActions ta = new TemplateActions();
                ta.openTemplate(templateSelected,EclipseUtil.getTemporaryProject().toOSString());
            }
        });
        
        // INSERT TEMPLATE BUTTON
        buttonRun.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                TemplateActions ta = new TemplateActions();
                ta.openTemplate(templateSelected,EclipseUtil.getTemporaryProject().toOSString());
            }
        });
        
        // CREATE TEMPLATE BUTTON
        buttonCreate.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                TemplateActions ta = new TemplateActions();
                ta.createTemplate();
                refresh();
            }
        });        
        
        // DELETE TEMPLATE BUTTON
        buttonDelete.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                // Deletes the previously selected (see templateSelected)
                TemplateActions ta = new TemplateActions();
                ta.deleteTemplate(templateSelected);
                refresh();
            }
        });
        
        // EDIT TEMPLATE BUTTON
        buttonEdit.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                // Edits the previously selected (see templateSelected)
                TemplateActions ta = new TemplateActions();
                ta.editTemplate(templateSelected,EclipseUtil.getTemporaryProject().toOSString() );
                refresh();
            }
        });          
    }

    public void refresh() {
        // Update the input contents
        tableViewer.setInput(templateUtil.getTemplatesStored());
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
        return templateUtil.getTemplatesStored();
    }

}

