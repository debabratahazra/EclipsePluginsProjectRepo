package com.temenos.t24.tools.eclipse.basic.dialogs.templates;

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

public class TemplateOpenDialog extends Dialog
{
   // Template's name
   private String modulename = "";
   // Prefix used to substitute 'XX' sequences inside the template.
   private String prefix = "";
   
   // Fields used to capture the values
   private Text modulenameField;
   private Text prefixField;   

   /**
    * Creates a dialog instance. Note that the window will have no
    * visual representation (no widgets) until it is told to open. By
    * default, <code>open</code> blocks for dialogs.
    * 
    * @param parentShell the parent shell
    */
   public TemplateOpenDialog(Shell parentShell) {
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
      dialogLabel.setText("Please, enter the Application's name:");
      
      // *************************************************************
      // Filename
      final Label filenameLabel= new Label(container, SWT.NONE);
      filenameLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
            false, false));
      filenameLabel.setText("Name:");
   
      modulenameField = new Text(container, SWT.BORDER);
      modulenameField.setLayoutData(new GridData(GridData.FILL,
            GridData.CENTER, true, false));
      
      // *************************************************************
      // Prefix. This will be used to replace the '<<PREFIX>>' sequences in the 
      // chosen Template
      final Label prefixLabel= new Label(container, SWT.NONE);
      prefixLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
            false, false));
      prefixLabel.setText("Prefix:");
   
      prefixField = new Text(container, SWT.BORDER);
      prefixField .setLayoutData(new GridData(GridData.FILL,
            GridData.CENTER, true, false));
      
      final Label prefixNoteLabel = new Label(container, SWT.NONE);
      prefixNoteLabel.setLayoutData(new GridData(GridData.BEGINNING,
            GridData.CENTER, false, false, 2, 1));
      prefixNoteLabel.setText("The prefix will be used to replace '<<PREFIX>>' \nsequences in the chosen Template.");
   
      
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
     
       modulenameField.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
             modulename= modulenameField.getText();
         }
      });
       prefixField.addModifyListener(new ModifyListener() {
           public void modifyText(ModifyEvent e) {
               prefix = prefixField.getText();
           }
        });       
   }
   
   /**
    * Configures the given shell in preparation for opening this window in it.
    * In this case, we set the dialog title.
    */
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText("Open Template Dialog");
   }
   
   public String getModulename() {
       return this.modulename;
    }
   public String getPrefix() {
       return this.prefix;
    }   
}
