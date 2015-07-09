package com.temenos.t24.tools.eclipse.basic.actions;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;

public class ActionsUtils {

    private static IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");

    public static synchronized String getRemoteServerDir() {
        String remoteDir = store.getString(PluginConstants.T24_REMOTE_SERVER_DIR);
        return remoteDir;
    }

    /**
     * Saves in local store the remote dir passed.
     * 
     * @param serverDir
     */
    public static synchronized void saveRemoteServerDir(String serverDir) {
        store.setValue(PluginConstants.T24_REMOTE_SERVER_DIR, serverDir);
    }

    /**
     * Launches a dialog pop-up and locks execution until user clicks a button.
     * 
     * @param dialogHeader - header of the dialog box
     * @param message - message to display
     * @param type - e.g. MessageDialog.WARNING
     */
    public static synchronized void launchMessageDialogPopup(String dialogHeader, String message, int type) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        T24MessageDialog dialog = new T24MessageDialog(window.getShell(), dialogHeader, message, type);
        dialog.open();
    }

    /**
     * Stores locally login info, to be used in future logins.
     * 
     * @param rememberLogin
     */
    public static synchronized void saveLoginInfo(boolean rememberLogin, String username, String password) {
        // store login info, if required.
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        if (rememberLogin) {
            mu.updateProperty("local.user.login", username);
            mu.updateProperty("local.user.password", password);
            mu.updateProperty("local.user.rememberlogin.flag", "true");
        } else {
            mu.updateProperty("local.user.login", "");
            mu.updateProperty("local.user.password", "");
            mu.updateProperty("local.user.rememberlogin.flag", "false");
        }
    }

       /**
     * Returns true if 'Always prompt before overwriting' is selected in preference page. false otherwise.
     * @return true will prompt a dialog. false no dialog required.
     */
    public static boolean isPromptOverwriting() {
        return Boolean.parseBoolean(store.getString(PluginConstants.T24_PROMPT_BEFORE_OVERWRITING));
    }
}
