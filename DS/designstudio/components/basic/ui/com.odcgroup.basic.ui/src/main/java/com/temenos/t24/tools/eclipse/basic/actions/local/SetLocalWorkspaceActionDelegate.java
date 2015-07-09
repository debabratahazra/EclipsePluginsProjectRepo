package com.temenos.t24.tools.eclipse.basic.actions.local; 

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;

public class SetLocalWorkspaceActionDelegate implements IWorkbenchWindowActionDelegate {
    private LogConsole log = LogConsole.getT24BasicConsole();
    
    /** The currently selected object in the active view */
    private ISelection selection;
    
    /**
     * Notifies this action delegate that the selection in the workbench has changed.
     * <p>
     * Implementers can use this opportunity to change the availability of the
     * action or to modify other presentation properties.
     * </p><p>
     * When the selection changes, the action enablement state is updated based on
     * the criteria specified in the plugin.xml file. Then the delegate is notified
     * of the selection change regardless of whether the enablement criteria in the
     * plugin.xml file is met.
     * </p>
     *
     * @param action the action proxy that handles presentation portion of 
     *      the action
     * @param selection the current selection, or <code>null</code> if there
     *      is no selection.
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
        /** Delegate to another method. */
        setLocalDirectory(selection, true);
    }
    
    /**
     * Checks whether the passed selection is either a IProject or a IFolder.
     * If it is, then it's fullPath will be regarded as the local path where
     * basicFiles will be located when retrieved from the remote server.
     * 
     * @param pSelection; selection made by user.
     * @param logMessageInConsole; true => a message indicating what happened (success of failure)
     * will be displayed on console.
     */
    public void setLocalDirectory(ISelection pSelection, boolean logMessageInConsole) {

        /** Is it a selection containing elements? */
        if (pSelection instanceof IStructuredSelection) {
            Object obj = (Object) ((IStructuredSelection) pSelection).getFirstElement();
            String objSelectedFullPath = "";
            String objSelectedName = "";

            if ((obj instanceof IProject)) {
                IProject proj = (IProject) obj;
                objSelectedName = proj.getName();
                objSelectedFullPath = proj.getLocation().toOSString();

            } else if ((obj instanceof IFolder)) {
                IFolder fold = (IFolder) obj;
                objSelectedName = fold.getName();
                objSelectedFullPath = fold.getLocation().toOSString();

            } else {
                if (logMessageInConsole) {
                    log.logMessage("Failed: Item selected is not a Project or Folder. Please ensure a Project or Folder is selected.");
                }
            }

            if(!"".equals(objSelectedFullPath)){
                // Persist the path
                IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
                store.setValue(PluginConstants.T24_LOCAL_DIRECTORY, objSelectedFullPath);

                if (logMessageInConsole) {
                    log.logMessage(objSelectedName + " has been set as the default local Workspace. You can check this in the Preferences Page");
                }                
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void dispose() {
    }

    /**
     * {@inheritDoc}
     */
    public void init(IWorkbenchWindow window) {
    }
    
}
