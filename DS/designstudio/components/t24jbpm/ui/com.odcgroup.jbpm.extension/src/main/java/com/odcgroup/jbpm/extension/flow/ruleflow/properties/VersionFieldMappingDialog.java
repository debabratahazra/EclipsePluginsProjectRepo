package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.odcgroup.jbpm.extension.Activator;

/**
 * @author phanikumark
 *
 */
public class VersionFieldMappingDialog extends Dialog {

    private String title;
    private TableItem value;
    private List<String> fieldNames;
    private List<String> processVariables;    
    
    private Combo fieldCombo;
    private Combo processVariablesCombo;
    private Button addToEnqVarsButton;    
    private Table table;
    private Table enqVarsTable;    

	/**
	 * @param parentShell
	 * @param title
	 * @param fieldNames
	 */
	protected VersionFieldMappingDialog(Shell parentShell, String title, List<String> fieldNames, 
			List<String> processVariables,Table table,Table enqVarsTable) {
        super(parentShell);
        this.title = title;
        this.fieldNames = fieldNames;
        this.processVariables = processVariables;
        this.table = table;
        this.enqVarsTable = enqVarsTable;        
        setShellStyle(getShellStyle() | SWT.RESIZE);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(title);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
     */
    protected Point getInitialSize() {
        return new Point(400, 210);
    }
    
    
    @Override
	protected Control createDialogArea(Composite parent) {
    	
    	Composite comp = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		comp.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(gridData);

		Group body = new Group(comp, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(1, false);
		body.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_BOTH);
		body.setLayoutData(gridData);

		Composite eBody = new Composite(body, SWT.FILL);
		gridLayout = new GridLayout(2, false);
		eBody.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.verticalAlignment = SWT.TOP;
		eBody.setLayoutData(gridData);

		// Events
		Label flabel = new Label(eBody, SWT.LEFT);
		flabel.setText("Field Name :");
		fieldCombo = new Combo(eBody, SWT.None);
		fieldCombo.setItems(fieldNames.toArray(new String[]{}));

		Label vlabel = new Label(eBody, SWT.LEFT);
		vlabel.setText("Value :");		
		processVariablesCombo = new Combo(eBody, SWT.NONE);
		processVariablesCombo.setTextLimit(100);
		processVariablesCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		processVariablesCombo.setItems(processVariables.toArray(new String[]{}));		
		
		Label addToEnqVarslabel = new Label(eBody, SWT.LEFT);
		addToEnqVarslabel.setText("Add to Enquiry Variables Map :");
		addToEnqVarsButton = new Button(eBody, SWT.CHECK);		
		
		return comp;
	}

	/**
	 * @return
	 */
	public TableItem getValue() {
        return value;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    protected void okPressed() {
        try {
        	if (isDataValid()) {
	            value = updateValue();
	            super.okPressed();
        	}
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }
    
    /**
     * @return
     * @throws IllegalArgumentException
     */
    private boolean isDataValid() throws IllegalArgumentException {
		if (fieldCombo.getText().isEmpty()) {
			throw new IllegalArgumentException("Field cannot be empty");
		} else if (JbpmDialogHelper.containsInValidCharacter(fieldCombo.getText())){
			throw new IllegalArgumentException(JbpmDialogHelper.INVALID_CHAR_ERROR_MSG);				
		}
		
		if (processVariablesCombo.getText().isEmpty()) {
			throw new IllegalArgumentException("Value cannot be empty");
		} else if(JbpmDialogHelper.containsInValidCharacter(processVariablesCombo.getText())){
			throw new IllegalArgumentException(JbpmDialogHelper.INVALID_CHAR_ERROR_MSG);				
		}
		return true;
	}

	/**
	 * @return
	 */
	protected TableItem updateValue() {
		if (value == null) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, fieldCombo.getText());
			item.setText(1, processVariablesCombo.getText());			
			
			if(addToEnqVarsButton.getSelection()){
				TableItem item2 = new TableItem(enqVarsTable, SWT.NONE);
				item2.setText(0, fieldCombo.getText());
				item2.setText(1, processVariablesCombo.getText());				
			}
			
			return item;
		}
    	return value;
    }
    
    /**
     * @param value
     */
    public void setValue(TableItem value) {
        this.value = value;
    }
    
    /**
     * @param error
     */
    protected void showError(String error) {
        ErrorDialog.openError(getShell(), "Error", error, new Status(
            IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(),
            IStatus.ERROR, error, null));
    }
}
