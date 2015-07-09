package com.temenos.t24.tools.eclipse.basic.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
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

public class LocalUserDetailsDialog extends Dialog
{
    private final static String DIALOG_MESSAGE = "Please enter your details. Username and Email are \n"+
                                    "mandatory. Please ensure the email is correctly entered.";
    
    private final static String DIALOG_BOX_TEXT = "User Details Dialog";
    
   private String username  = "";
   private String email     = "";
   private String telephone = "";
   
   // Fields used to capture the values
   private Text usernameText;
   private Text emailText;
   private Text telephoneText;

   private final int DIALOG_WIDTH = 300;
   private final int DIALOG_HEIGHT = 175;
   
   /**
    * Creates a dialog instance. Note that the window will have no
    * visual representation (no widgets) until it is told to open. By
    * default, <code>open</code> blocks for dialogs.
    * 
    * @param parentShell the parent shell
    */
   public LocalUserDetailsDialog(Shell parentShell, String defaultUsername, String defaultEmail, String defaultTelephone) {
      super(parentShell);
      setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);      
      this.username = defaultUsername;
      this.email    = defaultEmail;
      this.telephone = defaultTelephone;
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
      container.setBounds(100, 100, DIALOG_WIDTH, DIALOG_HEIGHT);
   
      final Label dialogLabel = new Label(container, SWT.NONE | SWT.WRAP);
      dialogLabel.setLayoutData(new GridData(GridData.BEGINNING,
            GridData.CENTER, false, false, 2, 1));
      dialogLabel.setText(DIALOG_MESSAGE);
      
      // *************************************************************
      // Username
      final Label usernameLabel= new Label(container, SWT.NONE);
      usernameLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
            false, false));
      usernameLabel.setText("Username:");

      usernameText = new Text(container, SWT.BORDER);
      usernameText.setLayoutData(new GridData(GridData.FILL,
            GridData.CENTER, true, false));
      usernameText.setText(username);
      
      // *************************************************************
      // Email
      final Label emailLabel= new Label(container, SWT.NONE);
      emailLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
            false, false));
      emailLabel.setText("Email:");

      emailText = new Text(container, SWT.BORDER);
      emailText.setLayoutData(new GridData(GridData.FILL,
            GridData.CENTER, true, false));
      emailText.setText(email);
      
      // *************************************************************
      // Telephone
      final Label telephoneLabel= new Label(container, SWT.NONE);
      telephoneLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
            false, false));
      telephoneLabel.setText("Telephone:");

      telephoneText = new Text(container, SWT.BORDER);
      telephoneText.setLayoutData(new GridData(GridData.FILL,
            GridData.CENTER, true, false));      
      telephoneText.setText(telephone);
      
      // **************************************************************
      // Setup listeners for the fields
      setupListeners();
      
      return container;
   }
   
   /**
    * Overwritten.
    */
   protected void createButtonsForButtonBar(Composite parent) {
       // Get rid of the CANCEL button
       super.createButtonsForButtonBar(parent);
       getButton(IDialogConstants.CANCEL_ID).dispose();
   }
   
   
   /**
    * Sets up listeners for the text fields, so whenever they are modified
    * the relevant variables are updated.
    */
   private void setupListeners() {
       
       usernameText.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
             username = usernameText.getText();
         }
      });
   
       emailText.addModifyListener(new ModifyListener() {
           public void modifyText(ModifyEvent e) {
               email = emailText.getText();
           }
        });       

       telephoneText.addModifyListener(new ModifyListener() {
           public void modifyText(ModifyEvent e) {
               telephone = telephoneText.getText();
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
       Rectangle b2 = new Rectangle(0,0, DIALOG_WIDTH, DIALOG_HEIGHT);
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
   
   public String getUsername() {
       return this.username;
    }

    public String getEmail() {
       return this.email;
    }
    
    public String getTelephone() {
        return this.telephone;
     }    
}
