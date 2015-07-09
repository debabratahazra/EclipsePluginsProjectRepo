package com.odcgroup.ocs.support;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;

import com.odcgroup.ocs.support.preferences.OCSPreferenceManager;
import com.odcgroup.workbench.core.AbstractActivator;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * The activator class controls the plug-in life cycle
 */
public class OCSPluginActivator extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.ocs.support";
	
	private OCSPreferenceManager preferenceManager = null;
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static OCSPluginActivator getDefault() {
		return (OCSPluginActivator) getDefault(OCSPluginActivator.class);
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.AbstractActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
        deleteWuiClassesJarName();
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.AbstractActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		preferenceManager = null;
		super.stop(context);
	}
	
	@Override
	protected String getResourceBundleName() {
		return PLUGIN_ID + ".messages";
	};
	
	/**
	 * @return
	 */
	public synchronized OCSPreferenceManager getPreferenceManager() {
		if(preferenceManager==null) {
			preferenceManager = new OCSPreferenceManager();
		}
		return preferenceManager;
	}

    public File getWuiClassesJarName() {
        return Platform.getLocation().append("wuiclasses.jar").toFile(); //$NON-NLS-1$
    }

    public void deleteWuiClassesJarName() {
        getWuiClassesJarName().delete();
    }
    
    public ProjectPreferences getProjectPreferences() {
		return new ProjectPreferences(null, PLUGIN_ID);
    }
  
}
