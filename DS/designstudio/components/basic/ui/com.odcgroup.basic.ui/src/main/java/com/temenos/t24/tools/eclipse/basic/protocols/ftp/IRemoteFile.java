package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

/**
 * Defines a file in a {@link RemoteSite}.
 * 
 * @author ssethupathi
 * 
 */
public interface IRemoteFile {

    /**
     * Returns true if this file is a directory and false otherwise
     * 
     * @return true/false
     */
    public abstract boolean isDirectory();

    /**
     * Returns the full path of this file at the remote site
     * 
     * @return remote path
     */
    public abstract String getFullPath();

    /**
     * Returns the name of this file
     * 
     * @return file name
     */
    public abstract String getName();
}
