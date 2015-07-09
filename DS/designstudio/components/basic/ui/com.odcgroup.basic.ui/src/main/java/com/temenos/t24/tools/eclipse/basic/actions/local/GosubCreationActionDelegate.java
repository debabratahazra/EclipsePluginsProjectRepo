package com.temenos.t24.tools.eclipse.basic.actions.local; 

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.dialogs.CreateRegionDialog;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.editors.regions.RegionUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

public class GosubCreationActionDelegate implements IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow window;
    private LogConsole log = LogConsole.getT24BasicConsole();
    
    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    /**
     * Method invoked by the framework. The main logic of the action goes here. 
     */
    public void run(IAction action) {
        StringUtil su = new StringUtil();
        CreateRegionDialog dialog = new CreateRegionDialog(window.getShell(), "GOSUB dialog");
        
        /* dialog.open() opens this window, creating it first if it has not yet been created. 
         * This method waits (blocks) until the window is closed by the end user, and then it 
         * returns the window's return code. A window's return codes are window-specific, 
         * although two standard return codes are predefined: OK and CANCEL.  
         */        
        boolean atLeastOneInputEmpty = false;
        do{
            if (dialog.open() != InputDialog.OK){
                // The user clicked something else other than OK. The execution is finished.
                return;

            } else {
                // The user clicked OK. Now validate whether inputs are empty. If at least one is empty
                // open the dialog again.            
                if(su.atLeastOneEmpty(new String[]{dialog.getRegionName()})){
                    // at least one input is empty
                    atLeastOneInputEmpty = true;
                    log.logMessage("Error: empty inputs. Please ensure that all the inputs have a value.");

                } else {
                    atLeastOneInputEmpty = false;
                    
                }
            }
        } while(atLeastOneInputEmpty);

        // This line is reached if user clicked OK and input validation passed.
        createGosubRegion(dialog);
        
     }  

    /**
     * With the filename and remote directory, invokes the appropriate method
     * of SessionManager to save the file in the remote server.
     * Note: Remote filenames don't end with the .b extension, as local files do. 
     * @param dialog - holds all the details from the dialog; e.g. filename
     */
    private void createGosubRegion(CreateRegionDialog dialog) {
        RegionUtil cr = new RegionUtil();
        T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
        IDocument doc = EditorDocumentUtil.getDocument(editor);
        cr.createGosub(editor,doc,dialog.getRegionName(), dialog.getRegionDescription());        
    }  
    
    public void selectionChanged(IAction action, ISelection selection) {
    }
}
