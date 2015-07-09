package com.temenos.t24.tools.eclipse.basic.views.macrotemplate;

import java.util.regex.Matcher;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.macros.MacroCreateDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.macros.MacroDataPromptDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.macros.MacroEditDialog;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

public class MacroActions {

    private LogConsole log = LogConsole.getT24BasicConsole();
    private IWorkbenchWindow window;
    
    public MacroActions(){
        this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    }
    
    /**********************************************************************************
     * EDIT MACRO
     * Opens a dialog with the details of a macro selected, so they can be updated. 
     */    
    public void editMacro(IT24ViewItem macroSelected){

        if (macroSelected!= null) {
            // Get the relevant macro information.
            Macro macro = (Macro)macroSelected.getData();

            // OPEN DIALOG
            StringUtil su = new StringUtil();
            MacroEditDialog dialog = new MacroEditDialog(window.getShell(), macro);
            boolean atLeastOneInputEmpty = false;
            do{
                if (dialog.open() != InputDialog.OK){
                    // The user clicked something else other than OK. The execution is finished.
                    return;

                } else {
                    // The user clicked OK. Now validate if inputs are empty. If at least one is empty
                    // open the dialog again.            
                    if(su.atLeastOneEmpty(new String[]{dialog.getMacroName(), dialog.getMacroBody()})){
                        // at least one input is empty
                        atLeastOneInputEmpty = true;
                        log.logMessage("Error: empty inputs. Please ensure that at least the Name and Body are not empty.");

                    } else {
                        // The user clicked OK, and the inputs are ok.
                        // Reset flag
                        atLeastOneInputEmpty = false;
                    }
                }
            } while(atLeastOneInputEmpty);            
                        
            // This point is reached if the user clicked 'OK'
            // UPDATE MACRO IN STORAGE
            // macro have the following structure: "macro.item.key<<NR>>macro_name<<NR>>macro_prompt?[;macro_prompt?]<<NR>>macro_body"
            // e.g. "t24.macro.system.1<<NR>>CACHE.READ<<NR>>Filename<<NR>>CALL CACHE.READ('F.<<1>>',<<1>>.ID,R.<<1>>,YERR)"
            StringBuffer sb = new StringBuffer();
            // System macros are pre-defined and fixed. If a System macro needs to be edited, 
            // it'll be saved as a User macro.
            String macroKey = macro.getKey();
            macroKey = macroKey.replaceAll("t24.macro.system", "t24.macro.user");

            sb.append(macroKey+MacroConstants.MACRO_NEW_RECORD_SEPARATOR);
            sb.append(dialog.getMacroName()+MacroConstants.MACRO_NEW_RECORD_SEPARATOR);
            sb.append(dialog.getMacroQuestions()+MacroConstants.MACRO_NEW_RECORD_SEPARATOR);
            sb.append(dialog.getMacroBody());
            String macroValue = sb.toString();
            
            MementoUtil mu = MementoUtilFactory.getMementoUtil();
            mu.updateProperty(macroKey, macroValue);            
        }        
    }
    
    
    /**********************************************************************************
     * RUN MACRO 
     * Execute the macro selected with the mouse. This means opening a dialog to get
     * any parameters, if needed; and then inserting the macro into the editor at
     * the cursor's position.
     * @param selection - macro selected with the mouse. 
     */
    public void runMacro(IT24ViewItem macroSelected) {       
        if (macroSelected!= null) {
            // Get the relevant macro information.
            Macro macro = (Macro)macroSelected.getData();
            String macroName        = macro.getName();
            String[] macroQuestions = macro.getQuestions(); // Note: there may be more than one question.
            String macroBody        = macro.getBody();
       
            // Macro string that will be inserted in editor
            String macroToInsert = "";
            
            // Only open a dialog if the macro needs parameters (or has any question)
            if (macroQuestions.length > 0) {
                // OPEN DIALOG TO GET MACRO PARAMETERS.

                MacroDataPromptDialog promptDialog = new MacroDataPromptDialog(window.getShell(), macroName, macroQuestions);
                if (promptDialog.open() != InputDialog.OK) {
                    // The user clicked something else other than OK. The
                    // execution is finished.
                    return;
                }
                
                // The user has entered the answers. Now substitute the answers into the macro.
                macroToInsert = this.substituteMacroParameters(macroBody, promptDialog.getAnswer());
                    
            } else {
                // The macro doesn't have any parameters
                macroToInsert = macroBody;
            }

            // This point is reached if the user clicked 'OK'
            // INSERT MACRO IN EDITOR
            insertMacroIntoEditor(macroToInsert);
        }
    }
    
    /**********************************************************************************
     * DELETE MACRO
     * Deletes a macro previously selected. Only user created macros can be deleted. 
     * Pre-defined system macros cannot be deleted.
     */
    public void deleteMacro(IT24ViewItem macroSelected){
        if(macroSelected!=null){
            String message = "";
            if(T24_VIEW_ITEM_CATEGORY.MACRO_SYSTEM_ITEM.equals(macroSelected.getCategory())){
                // SYSTEM MACROS CAN NOT BE DELETED
                message = "Failed. "+macroSelected.getLabel()+" is a system macro and cannot be deleted.";
                
                String dialogTitle = "  "+macroSelected.getLabel(); 
                T24MessageDialog warningDialog = new T24MessageDialog(window.getShell(),
                        dialogTitle , message, MessageDialog.ERROR);
                
                if (warningDialog.open() != InputDialog.OK){
                    // The user clicked something else other than OK. The execution is finished.
                    return;
                }                

            } else {
                MementoUtil mu = MementoUtilFactory.getMementoUtil();
                Macro macro = (Macro)macroSelected.getData();
                mu.deleteProperty(macro.getKey());
                
                message = "Macro "+macroSelected.getLabel()+" has been successfully deleted.";

            }
            // Display message on console.
            log.logMessage(message);
        }
    }    
    
    /**********************************************************************************
     * CREATE MACRO
     * Opens a dialog that allows the user to create a "user defined macro". The macro
     * is then persisted within the plug-in.
     */
    public void createMacro(){
        StringUtil su = new StringUtil();
        MacroCreateDialog dialog = new MacroCreateDialog(window.getShell());
        boolean atLeastOneInputEmpty = false;
        do{
            if (dialog.open() != InputDialog.OK){
                // The user clicked something else other than OK. The execution is finished.
                return;

            } else {
                // The user clicked OK. Now validate if inputs are empty. If at least one is empty
                // open the dialog again.            
                if(su.atLeastOneEmpty(new String[]{dialog.getMacroName(), dialog.getMacroBody()})){
                    // At least one input is empty
                    atLeastOneInputEmpty = true;
                    log.logMessage("Error: empty inputs. Please ensure that at least the Name and Body are not empty.");

                } else {
                    // The user clicked OK, and the inputs are ok.
                    // Reset flag
                    atLeastOneInputEmpty = false;
                }
            }
        } while(atLeastOneInputEmpty);        
        
        // Build the Macro store string. It has the following structure:
        //t24.macro.user.MACRO.NAME=MACRO.NAME<<NR>>Prompt1?[;Prompt2?]<<NR>>macro body <<1>> <<2>> ...
        String macroKey = "t24.macro.user."+dialog.getMacroName();
        
        StringBuffer sb = new StringBuffer();
        sb.append(macroKey+MacroConstants.MACRO_NEW_RECORD_SEPARATOR);
        sb.append(dialog.getMacroName()+MacroConstants.MACRO_NEW_RECORD_SEPARATOR);
        sb.append(dialog.getMacroQuestions()+MacroConstants.MACRO_NEW_RECORD_SEPARATOR);
        sb.append(dialog.getMacroBody());
        String macroValue = sb.toString();
        
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        mu.createProperty(macroKey, macroValue);
        
        String message = dialog.getMacroName() +" has been successfully created.";
        // Display message on console.
        log.logMessage(message);
    }
    
    
    /**********************************************************************************
     * INSERT MACRO TEXT IN EDITOR
     * Inserts a macro in the active editor (if any) where the cursor is located.
     * @param macroString - body of the macro to be inserted.
     */
    public void insertMacroIntoEditor(String macroString){
        T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
        if (editor != null) {
            TextSelection selectedText = (TextSelection) editor.getEditorSite()
                .getSelectionProvider().getSelection();

            // Cursor position
            int cursorPos = selectedText.getOffset();
            IDocument doc = EditorDocumentUtil.getDocument(editor);
            try{
                int curLine = doc.getLineOfOffset(cursorPos);
                int lineOffset = doc.getLineOffset(curLine);
                doc.replace(lineOffset, 0, macroString);
            } catch(BadLocationException  e){
                e.printStackTrace();
            }
        }
    }    
    
    /**
     * @param macro - e.g. CALL F.RELEASE(FN.<<1>>,<<2>>.ID,F.<<3>>)
     * In this example there are three params that need repacing: <<1>>, <<2>> and <<3>>
     * @param params - an array with all the params to be replaced
     * @return String - macro with all the parameters replaced
     */
    public String substituteMacroParameters(String macro, String[] params){
        for(int i=0; i<params.length; i++){
            String param = "";
            if(params[i]!=null){
                param = params[i];
            }
            // quoteReplacement, adds a slash (\) in front of dollars ($),
            // this is needed, otherwise replaceAll fails with $.
            param = Matcher.quoteReplacement(param);
            macro = macro.replaceAll("<<"+(i+1)+">>", param);
        }
        return macro;
    }    
    
    /**
     * Utility method to transform String[] into a semicolon separated ";" 
     * string.
     * @param questions - String[] with macro questions.
     * @return
     */
    public String transformQuestions(String[] questions){
        String q = "";
        for(int i=0; i<questions.length; i++){
            q = q + questions[i];
            if(i != questions.length-1)
                q = q + ";";
        }
        return q;
    }
}
