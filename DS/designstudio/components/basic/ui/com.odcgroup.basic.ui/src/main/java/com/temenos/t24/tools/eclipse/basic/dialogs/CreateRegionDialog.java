package com.temenos.t24.tools.eclipse.basic.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

public class CreateRegionDialog extends Dialog
{
   private final int SPACING        = 5;   // space between elements
   private final int DIALOG_WIDTH   = 300;  // Dialog width pixels
   private final int DIALOG_HEIGHT  = 200;  // Dialog height pixels    
   private final int DESCRIPTION_BOX_HEIGHT = 100;
   private final int LABEL_LENGTH   = 8;    // Labels = Name, Description
 
   private String regName = "";
   private String regDesc = "";
   private String dialogTitle = "";
   
   // Fields used to capture the values
   private Text regNameField;
   private Text regDescField;

   /**
    * Returns the initial size to use for the shell.
    * @return the initial size of the shell
    */
   protected Point getInitialSize() {
       // Answer the size from the previous incarnation.
       Rectangle b1 = getShell().getDisplay().getBounds();
       Rectangle b2 = new Rectangle(0,0,DIALOG_WIDTH,DIALOG_HEIGHT);
       return new Point(
         b1.width < b2.width ? b1.width : b2.width,
         b1.height < b1.height ? b2.height : b2.height);
    }      
   
   /**
    * Creates a dialog instance. Note that the window will have no
    * visual representation (no widgets) until it is told to open. By
    * default, <code>open</code> blocks for dialogs.
    * 
    * @param parentShell the parent shell
    */
   public CreateRegionDialog(Shell parentShell, String dialogTitle) {
      super(parentShell);
      setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
      this.dialogTitle = dialogTitle;
   }

   /**
    * Creates and returns the contents of the upper part of this
    * dialog (above the button bar). 
    * @param parent the parent composite to contain the dialog area
    * @return the dialog area control
    */
   protected Control createDialogArea(Composite parent) {
      StringUtil su = new StringUtil();
      Composite container = (Composite) super.createDialogArea(parent);
      FormLayout formLayout = new FormLayout();
      formLayout.marginWidth = SPACING;
      formLayout.marginHeight = SPACING;
      container.setLayout(formLayout);
   
      final Label dialogLabel = new Label(container, SWT.NONE);
      FormData formData = new FormData();
      formData.left = new FormAttachment(0, SPACING);
      formData.top  = new FormAttachment(0, SPACING);
      dialogLabel.setText("Please, enter the following details");
      
      // *************************************************************
      // Region Name
      final Label regNameLabel= new Label(container, SWT.NONE);
      regNameLabel.setText(su.pad("Name:", LABEL_LENGTH," "));
      formData = new FormData();
      formData.left = new FormAttachment(0, SPACING);
      formData.top  = new FormAttachment(dialogLabel, SPACING);
      regNameLabel.setLayoutData(formData);
   
      regNameField = new Text(container, SWT.BORDER);
      formData = new FormData();
      formData.left = new FormAttachment(regNameLabel, SPACING);
      formData.right = new FormAttachment(100, -SPACING);
      formData.top  = new FormAttachment(dialogLabel, SPACING);
      regNameField.setLayoutData(formData);
      
      // *************************************************************
      // Region Description
      final Label regDescLabel= new Label(container, SWT.NONE);
      regDescLabel.setText(su.pad("Desc:", LABEL_LENGTH," "));
      formData = new FormData();
      formData.height = this.DESCRIPTION_BOX_HEIGHT;
      formData.left  = new FormAttachment(0, SPACING);
      formData.top   = new FormAttachment(regNameField, SPACING);
      formData.bottom = new FormAttachment(100, -SPACING);
      regDescLabel.setLayoutData(formData);
      
   
      regDescField = new Text(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
      formData = new FormData();
      formData.height = this.DESCRIPTION_BOX_HEIGHT;
      formData.left   = new FormAttachment(regDescLabel, SPACING);
      formData.top    = new FormAttachment(regNameField, SPACING);
      formData.right  = new FormAttachment(100, -SPACING);
      formData.bottom = new FormAttachment(100, -SPACING);
      regDescField.setLayoutData(formData);      
      
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
     
       regNameField.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
             regName = regNameField.getText();
         }
      });
   
       regDescField.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
             regDesc = regDescField.getText();
         }
      });
   }
   
   /**
    * Configures the given shell in preparation for opening this window in it.
    * In this case, we set the dialog title.
    */
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText(dialogTitle);
   }
   
   public String getRegionName() {
       return this.regName;
    }

    public String getRegionDescription() {
       return this.regDesc;
    }
}
