package com.temenos.t24.tools.eclipse.basic.views.macrotemplate;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.T24LabelsProvider;


public abstract class AbstractViewPartMacroTemplate extends ViewPart {
    protected static final String COLUMN_NAME = ""; 
    protected static final int COLUMN_WIDTH = 200;
    
    /** Running: executes the macro, inserting it in active editor. */
    protected Button buttonRun;  
    /** Edit: opens dialog to edit-change the macro. */
    protected Button buttonEdit;
    /** Create: opens dialog to create new macro. */
    protected Button buttonCreate;
    /** Delete: removes selected macro. */
    protected Button buttonDelete;
    
    protected TableViewer tableViewer;
    
    @Override
    /**
     * Main class for creating the view
     */
    public void createPartControl(Composite parent) {

        GridLayout glayout = new GridLayout();
        glayout.numColumns = 1;
        parent.setLayout(glayout);
        GridData gdata = new GridData();
        gdata.horizontalAlignment = GridData.FILL;
        gdata.grabExcessHorizontalSpace = true;
        parent.setLayoutData(gdata);
        
        
        /* Table viewer
         * Comprises a table, label, contents
         */
        tableViewer = new TableViewer(parent,SWT.BORDER | SWT.SIMPLE | SWT.V_SCROLL | SWT.H_SCROLL);
        
        // Set Table within the viewer
        Table table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(false);
        gdata = new GridData();
        gdata.horizontalAlignment = GridData.FILL;
        gdata.verticalAlignment = GridData.FILL;
        gdata.grabExcessHorizontalSpace = true;
        gdata.grabExcessVerticalSpace = true;
        table.setLayoutData(gdata);

        int columnAlignment = SWT.CENTER;
        TableColumn column = new TableColumn(table, columnAlignment, 0);
        column.setText(COLUMN_NAME);
        column.setWidth(COLUMN_WIDTH);

        // Set label, content and input provider
        tableViewer.setLabelProvider(new T24LabelsProvider());
        tableViewer.setContentProvider(new ArrayContentProvider());
        
        // Add items to tableViewer
        tableViewer.setInput(getViewItems());

        /** Buttons group */
        Composite group = new Composite (parent, SWT.BORDER);
        gdata = new GridData();
        gdata.horizontalAlignment = GridData.FILL;
        gdata.verticalAlignment = GridData.FILL;
        group.setLayoutData(gdata);
        
        GridLayout gLayout = new GridLayout();
        gLayout.numColumns = 2;
        group.setLayout(gLayout);
        
        buttonRun = new Button(group, SWT.PUSH);
        buttonRun.setLayoutData(getButtonNewGridData());
        buttonRun.setText("Run");
        
        buttonEdit = new Button(group, SWT.PUSH);
        buttonEdit.setLayoutData(getButtonNewGridData());
        buttonEdit.setText("Edit");

        buttonCreate= new Button(group, SWT.PUSH);        
        buttonCreate.setLayoutData(getButtonNewGridData());
        buttonCreate.setText("Create");
        
        buttonDelete= new Button(group, SWT.PUSH);
        buttonDelete.setLayoutData(getButtonNewGridData());
        buttonDelete.setText("Delete");        
       
        // Associate listeners to each element
        addListeners();
    }

    private GridData getButtonNewGridData() {
        GridData gdata = new GridData();
        gdata.horizontalAlignment = GridData.FILL;
        gdata.verticalAlignment = GridData.FILL;
        gdata.grabExcessHorizontalSpace = true;
        gdata.grabExcessVerticalSpace = true;
        return gdata;
    }
    
    /**
     * Returns the items, e.g. Macros or Templates, to be display.
     * @return array of view items.
     */
    protected abstract IT24ViewItem[] getViewItems();
    
    protected abstract void addListeners();
    
}
