package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

/**
 * The {@link FtpFile} class represents a file in a remote site.
 * 
 * @author ssethupathi
 * 
 */
public class FtpFile implements IRemoteFile {

    private String fileName;
    private String remotePath;
    private boolean isDirectory;

    public FtpFile(String fileName, String remotePath, boolean isDirectory) {
        this.fileName = fileName;
        this.remotePath = remotePath;
        this.isDirectory = isDirectory;
    }

    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.protocols.ftp.IRemoteFile#isDirectory()
     */
    public boolean isDirectory() {
        return isDirectory;
    }

    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.protocols.ftp.IRemoteFile#getFullPath()
     */
    public String getFullPath() {
        return remotePath;
    }

    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.protocols.ftp.IRemoteFile#getName()
     */
    public String getName() {
        return fileName;
    }
}
