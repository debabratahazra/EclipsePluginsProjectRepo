package com.temenos.t24.tools.eclipse.basic.dialogs.macros;

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
import com.temenos.t24.tools.eclipse.basic.views.macrotemplate.Macro;

public class MacroEditDialog extends Dialog
{
    private final int DIALOG_WIDTH   = 500;  // Dialog width pixels
    private final int DIALOG_HEIGHT  = 400;  // Dialog height pixels
    private final int SPACING        = 10;   // space between elements
    private final int LABEL_LENGTH   = 8;    // Labels = name, prompts, body
          
    private final int QUESTION_BOX_HEIGHT = 100; 
    private final int BODY_BOX_HEIGHT     = 200;
    
    
    private String macroName       = ""; // e.g. WRITE.RECORD
    private String macroQuestions  = ""; // e.g. Record name?;File name? ...
    private String macroBody       = ""; // e.g. CALL F.WRITE(FN.<<1>>,<<1>>.ID,R.<<1>>)
    
    // Fields used to capture the values
    private Text nameField;
    private Text promptsField;
    private Text bodyField;
   
   private Macro macro;
   
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
   public MacroEditDialog(Shell parentShell, Macro macro) {
      super(parentShell);
      setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);      
      this.macro = macro;
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
       dialogLabel.setText("Please, enter the Macro details:");      
       
       // *************************************************************
       // Macro name items
       final Label nameLabel= new Label(container, SWT.NONE);
       nameLabel.setText(su.pad("Name:",LABEL_LENGTH," "));
       formData = new FormData();
       formData.left = new FormAttachment(0, SPACING);
       formData.top  = new FormAttachment(dialogLabel, SPACING);
       nameLabel.setLayoutData(formData);      
       
       nameField = new Text(container, SWT.BORDER);
       formData = new FormData();
       formData.left = new FormAttachment(nameLabel, SPACING);
       formData.right = new FormAttachment(100, -SPACING);
       formData.top  = new FormAttachment(dialogLabel, SPACING);
       nameField.setLayoutData(formData);
       // pre-populate the value
       macroName = macro.getName();
       nameField.setText(macroName);

       // *************************************************************
       // Macro questions
       final Label promptsLabel = new Label(container, SWT.NONE);
       promptsLabel.setText(su.pad("Prompt:", LABEL_LENGTH," "));
       formData = new FormData();
       formData.height = this.QUESTION_BOX_HEIGHT;
       formData.left  = new FormAttachment(0, SPACING);
       formData.top   = new FormAttachment(nameLabel, SPACING);
       promptsLabel.setLayoutData(formData);
             
       promptsField = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
       formData = new FormData();
       formData.height = this.QUESTION_BOX_HEIGHT;
       formData.left   = new FormAttachment(promptsLabel, SPACING);
       formData.top    = new FormAttachment(nameField, SPACING);
       formData.right  = new FormAttachment(100, -SPACING);
       promptsField.setLayoutData(formData);
       
       // pre-populate the contents
       String[] questions = macro.getQuestions();
       String q = "";
       for(int i=0; i<questions.length; i++){
           q = q + questions[i];
           if(i != (questions.length-1))
               q = q + "\r\n";
       }
       macroQuestions = q;
       promptsField.setText(q); 
       
       
       // *************************************************************
       // Macro body items
       final Label bodyLabel= new Label(container, SWT.NONE);
       bodyLabel.setText(su.pad("Body:", LABEL_LENGTH," "));
       formData = new FormData();
       formData.height = this.BODY_BOX_HEIGHT;
       formData.left   = new FormAttachment(0, SPACING);
       formData.top    = new FormAttachment(promptsLabel, SPACING);
       bodyLabel.setLayoutData(formData);
    
       bodyField = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
       formData = new FormData();
       formData.height = this.BODY_BOX_HEIGHT;
       formData.left   = new FormAttachment(bodyLabel, SPACING);
       formData.top    = new FormAttachment(promptsField, SPACING);
       formData.right  = new FormAttachment(100, -SPACING);
       formData.bottom = new FormAttachment(100, -SPACING);
       bodyField.setLayoutData(formData);
       // pre-populate the value
       macroBody = macro.getBody();
       bodyField.setText(macroBody);
      
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
       
       nameField.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
             macroName = nameField.getText();
         }
      });
       promptsField.addModifyListener(new ModifyListener() {
           public void modifyText(ModifyEvent e) {
               macroQuestions = promptsField.getText();
           }
        });
       bodyField.addModifyListener(new ModifyListener() {
           public void modifyText(ModifyEvent e) {
               macroBody = bodyField.getText();
           }
        });       
   }
   
   /**
    * Configures the given shell in preparation for opening this window in it.
    * In this case, we set the dialog title.
    */
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText(macro.getName()+"  Edit Macro Dialog");
   }
   
   public String getMacroName() {
       return this.macroName;
    }

   /**
    * Getter method
    * @return String - a semicolon ";" separated string with all the macro questions.
    * e.g. "Filename?;Record name?;Table name?"
    * Note: if there are no questions, an empty "" string will be returned.
    */
   public String getMacroQuestions() {
       StringUtil su = new StringUtil();
       StringBuffer sb = new StringBuffer();
       String[] questions = macroQuestions.split("\\n");
       // Append all the questions in a semicolon separated string
       for(int i=0; i<questions.length; i++){
           // append the next question, but before, trim it off all white spaces (including "\r")
           sb.append(su.trim(questions[i]));
           if(i!=(questions.length-1))
               sb.append(";");
       }       

       return sb.toString();
    }

   public String getMacroBody() {
       return this.macroBody;
    }   
}
