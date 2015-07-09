package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

/**
 * Class represents a file in a {@link RemoteSite}
 * 
 * @author ssethupathi
 * 
 */
public class SftpFile implements IRemoteFile {

    private String name;
    private String fullPath;
    private boolean isDirectory;

    public SftpFile(String filename, String fullPath, boolean isDirectory) {
        this.name = filename;
        this.fullPath = fullPath;
        this.isDirectory = isDirectory;
    }

    /**
     * {@inheritDoc}
     */
    public String getFullPath() {
        return fullPath;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isDirectory() {
        return isDirectory;
    }
}
