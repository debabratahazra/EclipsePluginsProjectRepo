package com.temenos.t24.tools.eclipse.basic.t24unit;

import org.eclipse.core.resources.IFile;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;

/**
 * An instance which binds the remote site details to a local IFile.
 * 
 * @author ssethupathi
 * 
 */
public class TestFile {

    private IFile file;
    private String fileName;
    private RemoteSite remoteSite;
    private String remotePath;
    private boolean isLocal;

    public TestFile(IFile file, String fileName, RemoteSite remoteSite, String remotePath, boolean isLocal) {
        this.file = file;
        this.fileName = fileName;
        this.remoteSite = remoteSite;
        this.remotePath = remotePath;
        this.isLocal = isLocal;
    }

    /**
     * Returns the associated IFile
     * 
     * @return
     */
    public IFile getFile() {
        return file;
    }

    /**
     * Returns the associated {@link RemoteSite}
     * 
     * @return
     */
    public RemoteSite getRemoteSite() {
        return remoteSite;
    }

    /**
     * Returns the associated remote path
     * 
     * @return
     */
    public String getRemotePath() {
        return remotePath;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isLocal() {
        return isLocal;
    }
}
