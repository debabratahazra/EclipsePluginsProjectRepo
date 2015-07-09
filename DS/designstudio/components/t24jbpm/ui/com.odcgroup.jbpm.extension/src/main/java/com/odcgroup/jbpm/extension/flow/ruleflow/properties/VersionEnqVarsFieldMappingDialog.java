package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
 * @author gasampong
 *
 */
public class VersionEnqVarsFieldMappingDialog extends Dialog {

    private String title;
    private TableItem value;
    
    private Combo valueCombo;
    private Combo keyCombo;
    private Table table;

    private List<String> processVariables;  
    private String taskAssignedGroupId;
    
	private static ArrayList<String> PREDEFINEDKEYS = new ArrayList<String>();    
    
	static {
		PREDEFINEDKEYS.add("GROUP.ID");
		PREDEFINEDKEYS.add("DURATION");	
		PREDEFINEDKEYS.add("CUSTOMER");			
	}      
	    
	/**
	 * @param parentShell
	 * @param title
	 * @param fieldNames
	 */
	protected VersionEnqVarsFieldMappingDialog(Shell parentShell, String title, 
			List<String> processVariables,String taskAssignedGroupId,Table table) {
        super(parentShell);
        this.title = title;
        this.processVariables = processVariables;
        this.taskAssignedGroupId = taskAssignedGroupId;
        this.table = table;
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
        return new Point(400, 200);
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
		flabel.setText("Key Name :");		
		keyCombo = new Combo(eBody, SWT.NONE);
		keyCombo.setTextLimit(100);		
		keyCombo.setItems(PREDEFINEDKEYS.toArray(new String[] {}));	
		keyCombo.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent e) {
				if(keyCombo.getText().equals("GROUP.ID")){
					 valueCombo.setText(taskAssignedGroupId);						
				} else if (keyCombo.getText().equals("DURATION")){
					valueCombo.setText("00:00:00:00");
				} else {
					valueCombo.setText("");					
				}
			}
		});			
		gridData = new GridData();
		gridData.widthHint = 200;
		keyCombo.setLayoutData(gridData);		

		Label vlabel = new Label(eBody, SWT.LEFT);
		vlabel.setText("Value :");		
		valueCombo = new Combo(eBody, SWT.NONE);
		valueCombo.setTextLimit(100);
		valueCombo.setItems(processVariables.toArray(new String[]{}));
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gridData.widthHint = 200;
		valueCombo.setLayoutData(gridData);
		
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
		if (keyCombo.getText().isEmpty()) {
			throw new IllegalArgumentException("Key cannot be empty");
		} else if (keyCombo.getText().equals("DURATION")){
			if(!JbpmDialogHelper.isDurationValid(valueCombo.getText()==null?"":valueCombo.getText())){
				throw new IllegalArgumentException(JbpmDialogHelper.INVALID_DURATION_ERROR_MSG);				
			}			
		} else if(JbpmDialogHelper.containsInValidCharacter(keyCombo.getText())){
			throw new IllegalArgumentException(JbpmDialogHelper.INVALID_CHAR_ERROR_MSG);		
		}
    			
		if (valueCombo.getText().isEmpty()) {
			throw new IllegalArgumentException("Value cannot be empty");
		} else if (JbpmDialogHelper.containsInValidCharacter(valueCombo.getText())){
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
			item.setText(0, keyCombo.getText());				
			item.setText(1, valueCombo.getText());
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
