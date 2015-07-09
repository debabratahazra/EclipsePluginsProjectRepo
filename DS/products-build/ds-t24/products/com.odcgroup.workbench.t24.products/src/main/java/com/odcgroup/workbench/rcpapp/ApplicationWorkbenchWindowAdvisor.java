package com.odcgroup.workbench.rcpapp;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.odcgroup.workbench.core.OfsCore;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(1000, 800));
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(false);
        String[] version = OfsCore.getVersionNumber().split("\\.");
        String newTitle = configurer.getTitle() + " " + version[0] + "." + version[1] + "." + version[2];
        configurer.setTitle(newTitle);
        
        // set layout of java packages to "hierarchical" (otherwise default is "flat")
        IPreferenceStore store = JavaPlugin.getDefault().getPreferenceStore();
        store.setValue("org.eclipse.jdt.internal.ui.navigator.layout", 1);
       
        // saves plugin preferences at the workspace level
        Preferences buildPref = InstanceScope.INSTANCE.getNode("com.ibm.team.build.ui"); 
        buildPref.put("com.ibm.team.build.eventtype.BuildFinished", "false");
        try {
            // flush preferences
        	buildPref.flush();
          } catch(BackingStoreException e) {
        	OfsCore.getDefault().logWarning("Could not disable BuildFinished notification", e);
          }
        Preferences deliverPref = InstanceScope.INSTANCE.getNode("com.ibm.team.filesystem.ide.ui"); 
        deliverPref.put("com.ibm.team.scm.eventCategory.delivery", "false");
        try {
          // flush preferences
        	deliverPref.flush();
        } catch(BackingStoreException e) {
        	OfsCore.getDefault().logWarning("Could not disable delivery notification", e);
        }
        
		// add perspective listener for reset (DS-425)
		configurer.getWindow().addPerspectiveListener(new PerspectiveAdapter() {
			@Override
			public void perspectiveActivated(IWorkbenchPage page,
					IPerspectiveDescriptor perspective) {
				super.perspectiveActivated(page, perspective);
				hideActionsforPage(page);
			}
			
		});
    }
    
    public void postWindowOpen() {
        super.postWindowOpen();
        IWorkbenchWindowConfigurer wc = getWindowConfigurer();
        IWorkbenchPage[] pages = wc.getWindow().getPages();
        for (int i=0; i < pages.length; i++) {
        	hideActionsforPage(pages[i]);
        }
    }
    
    private void hideActionsforPage(IWorkbenchPage page) {
    	page.hideActionSet("org.eclipse.ui.edit.text.actionSet.convertLineDelimitersTo");
    	page.hideActionSet("org.eclipse.ui.edit.text.actionSet.navigation");
    	page.hideActionSet("org.eclipse.search.searchActionSet");
    	page.hideActionSet("org.eclipse.ui.edit.text.actionSet.openExternalFile");
    	page.hideActionSet("org.eclipse.ui.actionSet.keyBindings");
    	page.hideActionSet("org.eclipse.ui.actionSet.openFiles");
    	page.hideActionSet("org.eclipse.ui.cheatsheets.actionSet");
    	page.hideActionSet("org.eclipse.ui.WorkingSetActionSet");
    	page.hideActionSet("org.eclipse.ui.edit.text.actionSet.annotationNavigation");
    	page.hideActionSet("org.eclipse.debug.ui.contextialLaunch.run.submenu");
    	page.hideActionSet("org.eclipse.debug.ui.launchActionSet");
    	page.hideActionSet("org.eclipse.ui.externaltools.ExternalToolsSet");
    	page.hideActionSet("org.eclipse.update.ui.softwareUpdates");
    	page.hideActionSet("team.main");        
    	page.hideActionSet("compareWithMenu");        
    	page.hideActionSet("replaceWithMenu");
    	page.hideActionSet("addFromHistoryAction");
    	if(Platform.getProduct().getId().equals("designstudioT24")){
    		page.hideActionSet("com.temenos.tafj.eclipse.actionSet");
    	}
    }
}
