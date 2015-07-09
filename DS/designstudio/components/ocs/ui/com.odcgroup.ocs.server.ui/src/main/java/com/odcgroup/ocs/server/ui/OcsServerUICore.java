package com.odcgroup.ocs.server.ui;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.ui.logs.LogWatcherManager;
import com.odcgroup.ocs.server.ui.util.ServerManager;
import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.server.ui.views.ServerView;
import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class OcsServerUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.ocs.server.ui";

	// Log watcher manager
	LogWatcherManager logWatcherManager;

	/**
	 * @return
	 */
	public static OcsServerUICore getDefault() {
		return (OcsServerUICore) getDefault(OcsServerUICore.class);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		logWatcherManager = new LogWatcherManager();

		OCSPluginActivator.getDefault().getProjectPreferences().addPreferenceChangeListener(
				new IPreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				if (ServerManager.isPropertyChangeServerRelated(event.getKey())) {
					ServerUICore.getDefault().getContributions().refreshServers();
					ServerView serverView = (ServerView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ServerView.SERVER_VIEW_ID);
					if (serverView != null) {
						serverView.forceViewerRefresh(true);
					}
				}
			}
		});
		
		ServerCore.getDefault().getProjectPreferences().addPreferenceChangeListener(
				new IPreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				if (ServerManager.isPropertyChangeServerRelated(event.getKey())) {
					ServerView serverView = (ServerView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ServerView.SERVER_VIEW_ID);
					if (serverView != null) {
						serverView.forceViewerRefresh(false);
					}
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		if (logWatcherManager != null) {
			logWatcherManager.dispose();
			logWatcherManager = null;
		}
		super.stop(context);
	}

	/**
	 * @return the log watcher manager (to start and stop
	 * watching logs)
	 */
	public LogWatcherManager getLogWatcherManager() {
		return logWatcherManager;
	}

}
