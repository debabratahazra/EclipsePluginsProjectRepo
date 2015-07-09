package com.odcgroup.ocs.support.ui.preferences;
/*
import java.io.File;
import java.net.URL;

import org.eclipse.ant.core.AntCorePlugin;
import org.eclipse.core.runtime.Platform;
*/
import org.eclipse.ant.core.IAntPropertyValueProvider;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.preferences.OCSPreferenceManager;
import com.odcgroup.ocs.support.ui.OCSSupportUICore;

/**
 * Dynamic provider for Ant properties.
 *
 * Provides the dynamic values for the following Ant properties:
 *
 * <ul>
 * <li><code>ocs_ws_mgt.running</code> - set (to "true") when this plugin is running</li>
 * </ul>
 * @since 1.0
 */
public class AntPrefsValueProvider implements IAntPropertyValueProvider {
	/**
	 * Returns the dynamic property values for Ant properties.
	 *
	 * @param propertyName The name of the property to resolve the value for
	 * @return The resolved value for the property
	 * @see org.eclipse.ant.core.IAntPropertyValueProvider#getAntPropertyValue(String)
	 */
	public String getAntPropertyValue(String propertyName) {
		String value = null;
		if(OCSSupportUICore.getDefault()==null) return null; // DS-2195, in case the plugin has not been started
		OCSPreferenceManager prefs = OCSPluginActivator.getDefault().getPreferenceManager();

		if ("eds.home".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getEdsHome();
		} else

		if ("OCS_WEBDOMAIN_DIR".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getWebDomainDir();
		} else
		if ("OCS_EAR_DIR".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getEarDir();
		} else
		if ("OCS_WUI_DIR".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getWuiDir();
		} else

		if ("OCS_DELIVERY_DIR".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getComponentsDir();
		} else

		if ("WL_LIB_DIR".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getWLServerLibDir();
		} else

	// --- For the install scripts
		if ("oams.home".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getInstallDirectory();
		} else
		if ("was.type".equals(propertyName)){ //$NON-NLS-1$
			value = "wls"; //$NON-NLS-1$
		} else
		if ("was.host".equals(propertyName)){ //$NON-NLS-1$
			value = "localhost"; //$NON-NLS-1$
		} else
		if ("bea.home".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getBeaHome();
		} else
		if ("was.home".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getWLHome();
		} else
		if ("java.home".equals(propertyName)){ //$NON-NLS-1$
			value = prefs.getJavaHome();
		} else
		if ("oams.opt".equals(propertyName) && !prefs.getEdsHome().isEmpty()){ //$NON-NLS-1$
			value = prefs.getEdsHome()+"/opt"; //$NON-NLS-1$
		} else
		if ("oams.ant.home".equals(propertyName)&& !prefs.getEdsHome().isEmpty()){ //$NON-NLS-1$
			value = prefs.getEdsHome()+"/opt/ant"; //$NON-NLS-1$
		}
		if(value.isEmpty()) value = null;
		return value;
	}
}