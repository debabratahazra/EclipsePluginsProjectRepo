package com.odcgroup.t24.server.external.ui.action;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.IStartup;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.odcgroup.workbench.core.OfsCore;

public class ResetPreferenceOnStartup implements IStartup {

	@Override
	public void earlyStartup() {
		
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
        
        Preferences servPref = InstanceScope.INSTANCE.getNode("com.ibm.team.foundation.rcp.ui");
        servPref.put("com.ibm.team.foundation.rcp.core.internal.events.EventManager", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<eventmanager>\r\n<trigger categoryId=\"com.ibm.team.build.eventcategory.Build\" enabled=\"false\" id=\"com.ibm.team.build.trigger.BuildFinishedAlert\" minPriority=\"1\" notifierId=\"com.ibm.team.jface.AlertNotifier\" showError=\"true\" showInfo=\"true\" showWarning=\"true\" typeId=\"com.ibm.team.build.eventtype.BuildFinished\"/>\r\n<trigger categoryId=\"com.ibm.team.filesystem.notificationCategory.deliver\" enabled=\"false\" id=\"com.ibm.team.filesystem.notification.trigger\" minPriority=\"3\" name=\"Deliver Trigger\" notifierId=\"com.ibm.team.jface.AlertNotifier\" showError=\"true\" showInfo=\"true\" showWarning=\"true\" typeId=\"com.ibm.team.scm.eventCategory.delivery\"/>\r\n</eventmanager>");
        try {
            // flush preferences
        	servPref.flush();
          } catch(BackingStoreException e) {
        	OfsCore.getDefault().logWarning("Could not disable BuildFinished and delivery notification", e);
          }

	}

}
