package com.temenos.t24.tools.eclipse.basic.dialogs.macros;

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

public class MacroDataPromptDialog extends Dialog
{
   // Answer provided to the macro question 
   private String[] answers;
   private String[] questions;
   
   private String macroName;
   
   // Fields used to capture the values
   private Text[] macroQuestionField;

   /**
    * Creates a dialog instance. Note that the window will have no
    * visual representation (no widgets) until it is told to open. By
    * default, <code>open</code> blocks for dialogs.
    * 
    * @param parentShell the parent shell
    * @param questionsString - semicolon ";" separated set of questions to get the Macro parameters:
    * e.g. "File name?;Record name?;User name?"
    */
   public MacroDataPromptDialog(Shell parentShell, String macroName, String[] macroQuestions) {
      super(parentShell);
      setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);      
      this.macroName = macroName;
      this.questions = macroQuestions;
      macroQuestionField = new Text[questions.length];
      answers = new String[questions.length];
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
      final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
      gridLayout.numColumns = 2;
      container.setLayout(gridLayout);
      container.setLayoutData(gridData);
   
      final Label dialogLabel = new Label(container, SWT.NONE);
      dialogLabel.setLayoutData(new GridData(GridData.BEGINNING,
            GridData.CENTER, false, false, 2, 1));
      dialogLabel.setText("Please, enter the required information:                                       ");
      
      // *************************************************************
      // Macro Questions
      for(int i=0; i<questions.length; i++){
      
          final Label macroQuestionLabel= new Label(container, SWT.RESIZE | SWT.NONE);
          macroQuestionLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,false, false));
          macroQuestionLabel.setText(questions[i]);
   
          Text fieldText = new Text(container, SWT.BORDER);
          macroQuestionField[i] = fieldText;
          macroQuestionField[i].setLayoutData(new GridData(GridData.FILL,
                  GridData.CENTER, true, false));
      }
   
      // **************************************************************
      // Setup listeners for the fields
      setupListeners();
      
      return container;
   }

   
   /**
     * Sets up listeners for the text fields, so whenever they are modified the
     * relevant variables are updated.
     */
    private void setupListeners() {
        for (int i = 0; i < macroQuestionField.length; i++) {
            final int j = i;
            Text fieldText = macroQuestionField[i];
            fieldText.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    answers[j] = macroQuestionField[j].getText();
                }
            });
        }
    }
   
   /**
    * Configures the given shell in preparation for opening this window in it.
    * In this case, we set the dialog title.
    */
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText(macroName+" Macro input(s):");
   }
   
   public String[] getAnswer() {
       return this.answers;
    }
}
