package com.temenos.t24.tools.eclipse.basic.actions.local;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.preferences.T24BasicPreferenceColorPage;
import com.temenos.t24.tools.eclipse.basic.preferences.T24BasicPreferenceCorePage;

/**
 * Display current plug-in options.
 * 
 * @author lfernandez
 *
 */
public class DisplayT24PluginOptionsAction implements IWorkbenchWindowActionDelegate {

   public void selectionChanged(IAction action, ISelection selection) {
   }

   public void run(IAction action) {
       /** New preference manager, to handle preference nodes */
       PreferenceManager manager = new PreferenceManager();
       
       /** Create pref. pages*/
       IPreferencePage corePrefPage = new T24BasicPreferenceCorePage();
       corePrefPage.setTitle(PluginConstants.PLUGIN_DESCRIPTION);
       
       IPreferencePage colorPrefPage = new T24BasicPreferenceColorPage();
       colorPrefPage.setTitle(T24BasicPreferenceColorPage.COLOR_PREFERENCE_PAGE_TITLE);
       
       /** Add the pages as Nodes*/
       IPreferenceNode targetNode = new PreferenceNode(T24BasicPreferenceCorePage.PREFERENCES_PAGE_ID, corePrefPage);
       targetNode.add(new PreferenceNode(T24BasicPreferenceColorPage.PREFERENCES_PAGE_ID, colorPrefPage));
       
       /** Add node structure to the manager*/
       manager.addToRoot(targetNode);
       
       IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
       final PreferenceDialog dialog = new PreferenceDialog(window.getShell(), manager);
       BusyIndicator.showWhile(window.getShell().getDisplay(), new Runnable() {
           public void run() {
               dialog.create();
               dialog.setMessage(PluginConstants.PLUGIN_DESCRIPTION);
               dialog.open();
           }
       });       
    }

    public void dispose() {
        // TODO Auto-generated method stub
    }

    public void init(IWorkbenchWindow window) {
        // TODO Auto-generated method stub
    }   
}
