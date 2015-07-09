package com.temenos.t24.tools.eclipse.basic.actions.remote;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.ViewLocksInServerDialog;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

public class ViewLocksInServerActionDelegate implements IWorkbenchWindowActionDelegate {
    
    private LogConsole log = LogConsole.getT24BasicConsole();
    
    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
    }
    
    /**
     * Method invoked by the framework. The main logic of the action goes here. 
     */
    public void run(IAction action) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        // Check whether user has signed on. If not, then display message and leave.
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        if(!sesMgr.isUserSignedOn()){
            String message = "USER HAS NOT SIGNED ON";
            String dialogTitle = "  View Locks Dialog"; 
            T24MessageDialog warningDialog = new T24MessageDialog(window.getShell(),
                        dialogTitle , message, MessageDialog.ERROR);
            warningDialog.open();   
            return;
        }
        
        // Get and display the locks held by the local user in the Server 
        ArrayList<String> lockedFiles = getLocksInServer();
        
        ViewLocksInServerDialog dialog = new ViewLocksInServerDialog(window.getShell(), lockedFiles);

        if (dialog.open() != InputDialog.OK){
            // The user clicked something else other than OK. The execution is finished.
            return;
        }

        // This line is reached if user clicked OK and input validation passed.
        unlockFilesServer(dialog.getFilesSelected());
        
     }  

    /**
     * Iterates through all the basic files within the project, and saves them in 
     * the remote server.
     * @param serverDir - remote server directory
     * @param filesSelected - map with the files selected by the user. Each entry in the map holds
     * a filename, and a boolean (true - selected or false - not selected) 
     */
    public void unlockFilesServer(final Map<String, Boolean>filesSelected) {
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        
        Iterator<String> it = filesSelected.keySet().iterator();
        while(it.hasNext()){
            String basicFilenameNoPrefix = (String)it.next();
            Boolean selected = (Boolean)filesSelected.get(basicFilenameNoPrefix);
            if(selected){
                    Response res = sesMgr.unlockFile(basicFilenameNoPrefix);
    
                    if (res.getPassed()) {
                        // UNLOCK SUCCESSFUL
                        log.logMessage("("+res.getActionTimeMillis()+"ms) - File " + basicFilenameNoPrefix+ 
                                       " was unlocked sucessfully.");
                        
                    } else {
                        // UNLOCK FAILED
                        log.logMessage("Error: " + basicFilenameNoPrefix + " failed to be unlocked.\n" 
                                       + res.getRespMessage());
                    }
            }
        }
    }  
    
    
    /**
     * @return an ArrayList with all the programs locked by current local user in the server
     */
    public ArrayList<String> getLocksInServer(){
        ArrayList<String> lockArray = new ArrayList<String>();
        RemoteSessionManager rsMgr = RemoteSessionManager .getInstance();
        IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
        String localUsername  = store.getString(PluginConstants.T24_LOCAL_USERNAME);
        Response response = rsMgr.getLocks(localUsername);
        if(response.getPassed()){
            String responseXmlContents = (String) response.getObject();
            String locklist = XmlUtil.getText(responseXmlContents, "//locklist");
            if(!"".equals(locklist)){
                String[] lockedFiles = locklist.split(":");
                for(int i=0; i<lockedFiles.length; i++){
                    lockArray.add(i, lockedFiles[i]);
                }
            }
            
        } else {
            // FAILED RETRIEVING LOCKS
            log.logMessage("Failed to retrieve locks for "+localUsername+".\n" + response.getRespMessage());
        }
        
        return lockArray;
    }
    
    
    /**
     * Notifies this action delegate that the selection in the
     * workbench has changed. for example if a project or file has
     * been clicked with the mouse.
     * @param action the action proxy that handles presentation portion
     *           of the action
     * @param selection the current selection, or <code>null</code>
     *           if there is no selection.
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(!selection.isEmpty() && RemoteSessionManager.getInstance().isUserSignedOn());        
     }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }
}
