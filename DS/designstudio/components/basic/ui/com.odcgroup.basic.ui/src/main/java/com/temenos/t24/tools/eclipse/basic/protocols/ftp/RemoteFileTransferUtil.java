package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

/**
 * Utility class for remote file transfer.
 * 
 * @author ssethupathi
 * 
 */
public final class RemoteFileTransferUtil {

    /**
     * Returns the default local path of the file name passed.
     * 
     * @param fileName
     * @return default local path
     */
    public static String getDefaultLocalPath(String fileName) {
        String filePath = "";
        filePath = EclipseUtil.getTemporaryProject().toOSString() + "//" + T24FTPClientHelper.getLocalFileName(fileName);
        return filePath;
    }

    /**
     * Removes the .d extension from the data file.
     * 
     * @param fileName file name.
     * @return file name extension removed.
     */
    public static String removeDataFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String extn = ".d";
        if (fileName.endsWith(extn)) {
            int length = fileName.length();
            fileName = fileName.substring(0, length - 2);
            return fileName;
        }
        return fileName;
    }

    /**
     * Returns the remote path in respect to the local path and remote root.
     * 
     * @param localRoot
     * @param localFullPath
     * @param remoteRoot
     * @return
     */
    public static String getExtendedeRmotePath(String localRoot, String localFullPath, String remoteRoot) {
        String extendedRemotePath = remoteRoot;
        if (localFullPath != null && localFullPath.startsWith(localRoot)) {
            String tempPath = localFullPath.replace(localRoot, "");
            extendedRemotePath = remoteRoot + tempPath;
            extendedRemotePath = extendedRemotePath.replace('\\', '/');
            int endIndex = extendedRemotePath.lastIndexOf('/');
            if (endIndex > 0) {
                extendedRemotePath = extendedRemotePath.substring(0, endIndex);
            }
        }
        return extendedRemotePath;
    }

    /**
     * Returns the list of sub directories to be created from the remote root to
     * the full remote path.
     * 
     * @param base
     * @param extended
     * @return
     */
    public static String[] getSubDirectories(String base, String extended) {
        if (base == null || extended == null) {
            return null;
            // TODO: Handle it!
        }
        if (extended.startsWith(base)) {
            String tempPath = extended.replace(base + "/", "");
            return tempPath.split("/");
        }
        return null;
    }
    
    /**
     * returns the hyperlink path to look as configured in preference page. If
     * no value is specified GLOBUS.BP will be returned
     * 
     * @return directories
     */
    public static synchronized String[] getHyperLinkDirectories() {
        String remoteDir = EclipseUtil.getPreferenceStore().getString(PluginConstants.T24_HYPERLINK_DIR).trim();
        String[] remoteDirList = null;
        if (remoteDir.length() == 0) {
            remoteDirList = new String[] { PluginConstants.SERVER_PRIMARY_SOURCE_ALTERNATE, PluginConstants.SERVER_PRIMARY_SOURCE };
        } else {
            remoteDirList = remoteDir.split(",");
        }
        return remoteDirList;
    }
}
