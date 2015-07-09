package com.temenos.t24.tools.eclipse.basic.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.actions.ActionsUtils;

public class SetServerDirectoryDialog extends Dialog
{
   // Remote server directory, e.g. GLOBUS.BP 
   private String serverDir = "";
   
   // Fields used to capture the values
   private Text serverDirField;

   /**
    * Creates a dialog instance. Note that the window will have no
    * visual representation (no widgets) until it is told to open. By
    * default, <code>open</code> blocks for dialogs.
    * 
    * @param parentShell the parent shell
    */
   public SetServerDirectoryDialog(Shell parentShell) {
      super(parentShell);
      setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
   }

   /**
    * Creates and returns the contents of the upper part of this
    * dialog (above the button bar). 
    * @param parent the parent composite to contain the dialog area
    * @return the dialog area control
    */
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite) super.createDialogArea(parent);
      final GridLayout gridLayout = new GridLayout();
      gridLayout.numColumns = 2;
      container.setLayout(gridLayout);
   
      final Label dialogLabel = new Label(container, SWT.NONE);
      dialogLabel.setLayoutData(new GridData(GridData.BEGINNING,
            GridData.CENTER, false, false, 2, 1));
      dialogLabel.setText("Please, enter the remote directory (e.g. GLOBUS.BP)");
      
      // *************************************************************
      // Directory
      final Label dirLabel= new Label(container, SWT.NONE);
      dirLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
            false, false));
      dirLabel.setText("Directory:");
   
      serverDirField = new Text(container, SWT.BORDER);
      serverDirField .setLayoutData(new GridData(GridData.FILL,
            GridData.CENTER, true, false));
      // Set initial value
      serverDir = ActionsUtils.getRemoteServerDir();
      serverDirField.setText(serverDir);
   
      // **************************************************************
      // Setup listeners for the fields
      setupListeners();
      
      return container;
   }

   
   /**
    * Sets up listeners for the text fields, so whenever they are modified
    * the relevant variables are updated.
    */
   private void setupListeners() {
     
       serverDirField.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
             serverDir = serverDirField.getText();
         }
      });
   }
   
   /**
    * Configures the given shell in preparation for opening this window in it.
    * In this case, we set the dialog title.
    */
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText("Set Server Directory Dialog");
   }
   
   public String getServerDir() {
       return this.serverDir;
    }
}
