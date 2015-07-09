package com.odcgroup.ocs.support.preferences;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

import com.odcgroup.ocs.support.OCSPluginActivator;


public class OCSPreferenceManager {

    private String edshome = null;

    public OCSPreferenceManager() {
    }

    public String getInstallDirectory() {
        return OCSPluginActivator.getDefault().getProjectPreferences()
        		.get(OCSRuntimePreference.INSTALL_DIR, ""); //$NON-NLS-1$
    }

    public String getEarInstanceName() {
        return OCSPluginActivator.getDefault().getProjectPreferences()
        		.get(OCSRuntimePreference.EAR_DIR, ""); //$NON-NLS-1$
    }

    public String getEdsHome() {
        if (edshome == null) {
            try {
                edshome = new File(FileLocator.resolve(
                        new URL("platform:/base/")).getPath()).getAbsolutePath(); //$NON-NLS-1$
                if (edshome.endsWith("/")) { //$NON-NLS-1$
                    edshome = edshome.substring(0, edshome.length() - 1);
                }
            } catch (Exception e) {
            }
            edshome = edshome.replace('\\', '/');
        }
        return edshome;
    }

    public String getJavaHome() {
        return getEdsHome() + "/jre"; //$NON-NLS-1$
    }

    public String getWLHome() {
        return getEdsHome() + "/bea/weblogic81"; //$NON-NLS-1$
    }

    public String getBeaHome() {
        return getEdsHome() + "/bea"; //$NON-NLS-1$
    }

    public String getWLServerLibDir() {
        return getEdsHome() + "/bea/weblogic81/server/lib"; //$NON-NLS-1$
    }

    public String getEarDir() {
        return getEarInstanceName();
    }

    public String getWuiDir() {
        return getEarInstanceName() + "/wui.war"; //$NON-NLS-1$
    }

    public String getCustomDir() {
        return getInstallDirectory() + "/custom"; //$NON-NLS-1$
    }

    public String getWebDomainDir() {
        return getInstallDirectory() + "/webDomain"; //$NON-NLS-1$
    }

    public String getCustomWebDomainDir() {
        return getCustomDir() + "/webDomain"; //$NON-NLS-1$
    }

    public String getWuiBlocksDir() {
        return getWebDomainDir() + "/wuiblock"; //$NON-NLS-1$
    }

    public String getCustomWuiBlocksDir() {
        return getCustomWebDomainDir() + "/wuiblock"; //$NON-NLS-1$
    }

    public String getComponentsDir() {
        return getInstallDirectory() + "/install"; //$NON-NLS-1$
    }

    public String getSourcesJarPath() {
        return OCSPluginActivator.getDefault().getProjectPreferences()
        		.get(OCSRuntimePreference.OCS_SOURCES_JAR, ""); //$NON-NLS-1$
    }

}
