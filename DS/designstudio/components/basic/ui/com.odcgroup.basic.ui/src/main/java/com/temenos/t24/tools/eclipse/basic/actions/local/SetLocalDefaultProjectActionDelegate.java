package com.temenos.t24.tools.eclipse.basic.actions.local; 

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;

public class SetLocalDefaultProjectActionDelegate implements IWorkbenchWindowActionDelegate {
    
    private LogConsole log = LogConsole.getT24BasicConsole();
    /** The currently selected object in the active view */
    private ISelection selection;
    
    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
    }
    
    /**
     * Notifies this action delegate that the selection in the
     * workbench has changed.
     * 
     * @param action the action proxy that handles presentation portion
     *           of the action
     * @param selection the current selection, or <code>null</code>
     *           if there is no selection.
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
       this.selection = selection;
       action.setEnabled(!selection.isEmpty());
    }

    /**
     * Called when the user has selected this action to be executed.
     * It is assumed that an item has been selected.
     */
    public void run(IAction action) {
        setLocalWokspace(selection, true);
    }
    
    public void setLocalWokspace(ISelection pSelection, boolean printMessage) {
        if (pSelection instanceof IStructuredSelection) {
            Object obj = (Object) ((IStructuredSelection) pSelection).getFirstElement();
            String projFullPath = "";
            String projName = "";
            
            if ((obj instanceof IProject)) {
                IProject proj = (IProject) obj;
                projName = proj.getName();
                projFullPath = proj.getLocation().toOSString();

            } else {
                if (printMessage) {
                    IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                    String message = "Item selected is not a Project. Please ensure a Project is selected.";
                    String headerTitle = "Set Local Project";
                    log.logMessage(message);
                    T24MessageDialog errorDialog = new T24MessageDialog(window.getShell(),
                            headerTitle, message, MessageDialog.ERROR);
                    
                    if (errorDialog.open() != InputDialog.OK){
                        // The user clicked something else other than OK. The execution is finished.
                        return;
                    }
                }
            }
            
            if(!"".equals(projFullPath)){
                // Persist the path in the local store
                IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
                store.setValue(PluginConstants.T24_LOCAL_DEFAULT_PROJECT_FULLPATH, projFullPath);
                
                if (printMessage) {
                    log.logMessage(projName + " has been set as the default local Project. You can check this in the Preferences Page");
                }                
            }
        }
    }
}
