package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;

public class RemoteOperationsDialogHelper {

    /**
     * Stores the default server directory details to xml through MementoUtil
     * 
     * @param serverDir
     */
    public synchronized static void updateDefaultServerDirectory(String serverDir) {
        if (canUpdate(serverDir)) {
            String macroKey = PluginConstants.T24_SERVER_DIRECTORY;
            String macroValue = serverDir;
            MementoUtil mu = MementoUtilFactory.getMementoUtil();
            mu.updateProperty(macroKey, macroValue);
        }
    }

    /**
     * 
     * @param serverDir
     * @return true if the serverDir needs to be saved to xml else return false
     */
    private static boolean canUpdate(String serverDir) {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        String oldServerDir = mu.getProperty(PluginConstants.T24_SERVER_DIRECTORY);
        if (oldServerDir == null || !oldServerDir.equals(serverDir)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @return server directory name
     */
    public synchronized static String getServerDirectory() {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        String serverDir = mu.getProperty(PluginConstants.T24_SERVER_DIRECTORY);
        return serverDir;
    }
}
