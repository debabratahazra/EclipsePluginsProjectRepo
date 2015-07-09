package com.temenos.t24.tools.eclipse.basic.actions.remote;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.actions.ActionsUtils;
import com.temenos.t24.tools.eclipse.basic.dialogs.SetServerDirectoryDialog;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

public class SetServerDirectoryActionDelegate implements IWorkbenchWindowActionDelegate {

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
    public void run(IAction action){
        StringUtil su = new StringUtil();
        SetServerDirectoryDialog dialog = new SetServerDirectoryDialog (window.getShell());
        
        // OPEN DIALOG TO GET THE FILE NAME AND DIRECTORY TO GET IT FROM
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
                if(su.atLeastOneEmpty(new String[]{dialog.getServerDir()})){
                    // at least one input is empty
                    atLeastOneInputEmpty = true;
                    log.logMessage("Error: empty inputs. Please ensure that all the inputs have a value.");

                } else {
                    // The user clicked OK, and the inputs are successful.
                    // Store the serverDir. It is assumed that this will the default dir for the User.
                    ActionsUtils.saveRemoteServerDir(dialog.getServerDir());
                    
                    // Reset flag
                    atLeastOneInputEmpty = false;

                }
            }
        } while(atLeastOneInputEmpty);

        // This line is reached if user clicked OK and input validation passed.
        ActionsUtils.saveRemoteServerDir(dialog.getServerDir());
    }
    
    public void selectionChanged(IAction action, ISelection selection) {
    }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }
}
