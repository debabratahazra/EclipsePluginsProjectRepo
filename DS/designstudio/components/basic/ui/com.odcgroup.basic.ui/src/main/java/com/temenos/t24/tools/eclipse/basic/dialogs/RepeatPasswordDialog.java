package com.temenos.t24.tools.eclipse.basic.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.actions.remote.SignOnData;

public class RepeatPasswordDialog extends Dialog {

    private static final String DIALOG_BOX_TEXT = "Repeat Password Dialog";
    
    private String message = "";
    private String firstPswd = "";
    private String secondPswd = "";
    
    private Text firstPswdText   = null;
    private Text passwordText   = null;

    private final int DIALOG_WIDTH = 300;
    private final int DIALOG_HEIGHT = 150;
    
    /**
     * Creates a dialog instance. Note that the window will have no visual
     * representation (no widgets) until it is told to open. By default,
     * <code>open</code> blocks for dialogs.
     * 
     * @param parentShell the parent shell, or <code>null</code> to create a
     *            top-level shell.
     * @param password: this is the original password entered by the user when
     * he/she tried to log in for first time.          
     */
    public RepeatPasswordDialog(Shell parentShell, String password) {
        super(parentShell);
        setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
        this.firstPswd = password; 
    }

    /**
     * Creates and returns the contents of the upper part of this dialog (above
     * the button bar).
     * 
     * @param parent the parent composite to contain the dialog area
     * @return the dialog area control
     */
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);
        container.setBounds(100, 100, DIALOG_WIDTH, DIALOG_HEIGHT);
        
        // *******************************************************
        Label firstPswdLabel = new Label(container, SWT.NONE);
        firstPswdLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        firstPswdLabel.setText("Password:");
        firstPswdText= new Text(container, SWT.BORDER | SWT.PASSWORD);
        firstPswdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        firstPswdText.setText(firstPswd);
        
        // *******************************************************
        Label secondPswdLabel = new Label(container, SWT.NONE);
        secondPswdLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        secondPswdLabel.setText("Re-endter Password:");
        passwordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
        passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        // **************************************************************
        final Label dialogLabel = new Label(container, SWT.NONE | SWT.WRAP);
        dialogLabel.setLayoutData(new GridData(GridData.BEGINNING,
              GridData.CENTER, false, false, 2, 1));
        dialogLabel.setText(message);        
                
        // Setup listeners for the fields
        setupListeners();
        
        return container;
    }

    /**
     * Sets up listeners for the text fields, so whenever they are modified the
     * relevant variables are updated.
     */
    private void setupListeners() {
     
       firstPswdText.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
            firstPswd= firstPswdText.getText();
         }
      });
   
       passwordText.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
            secondPswd = passwordText.getText();
         }
      });       
       
   }
    
    /**
     * Returns the initial size to use for the shell.
     * @return the initial size of the shell
     */
    protected Point getInitialSize() {
        // Answer the size from the previous incarnation.
        Rectangle b1 = getShell().getDisplay().getBounds();
        Rectangle b2 = new Rectangle(0, 0, DIALOG_WIDTH, DIALOG_HEIGHT);
        return new Point(
              b1.width < b2.width ? b1.width : b2.width,
              b1.height < b1.height ? b2.height : b2.height);
     }    
    
    /**
     * Configures the given shell in preparation for opening this window in it.
     * In this case, we set the dialog title.
     */
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DIALOG_BOX_TEXT);
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public void resetPasswords() {
        firstPswd = SignOnData.firstPswd;
        secondPswd = "";
    }
    
    public String getFirstPassword() {
        return firstPswd;
    }

    public String getSecondPassword() {
        return secondPswd;
    }
}
