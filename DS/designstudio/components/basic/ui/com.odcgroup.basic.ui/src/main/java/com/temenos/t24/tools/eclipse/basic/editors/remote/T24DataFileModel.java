package com.temenos.t24.tools.eclipse.basic.editors.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteFileTransferUtil;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24DataFileTransfer;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24DataTransferHelper;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FileUtil;

/**
 * Model class for {@link T24DataFileEditor}.
 * 
 * @author ssethupathi
 * 
 */
public class T24DataFileModel {

    private IStorage storage;
    private String t24FileName;
    private String t24RecordKey;
    private boolean isLocal;

    /**
     * Constructor with a file object.
     * 
     * @param file
     */
    public T24DataFileModel(IStorage storage, boolean isLocal) {
        this.storage = storage;
        this.isLocal = isLocal;
        constructT24FileDetails();
    }

    /**
     * Returns the {@link IFile} object associated with this.
     * 
     * @return {@link IFile} file.
     */
    public IStorage getFile() {
        return storage;
    }

    /**
     * Returns the name of the associated {@link IFile}. Eg., F.SPF!SYSTEM.d
     * 
     * @return file name.
     */
    public String getFileName() {
        return storage.getName();
    }

    /**
     * Returns the T24 file name. Eg., F.SPF
     * 
     * @return T24 file name.
     */
    public String getT24FileName() {
        return t24FileName;
    }

    /**
     * Returns the T24 record key. Eg., SYSTEM
     * 
     * @return T24 record key.
     */
    public String getT24RecordKey() {
        return t24RecordKey;
    }

    /**
     * Returns the local absolute path of the associated {@link IFile}.
     * 
     * @return local path.
     */
    public String getLocalPath() {
        if (isLocal && storage instanceof IFile) {
            return ((IFile) storage).getLocation().toOSString();
        }
        return storage.getFullPath().toOSString();
    }

    /**
     * Returns the content of associated {@link IFile} in String.
     * 
     * @return file content.
     * @throws CoreException
     */
    public String getFileContent() {
        FileUtil fu = new FileUtil();
        String content = "";
        try {
            content = fu.getFromFile(storage.getContents());
        } catch (CoreException e) {
            return "";
        }
        return content;
    }

    /**
     * Returns the description for this file.
     * 
     * @return description.
     */
    public String getDescription() {
        return "File: " + t24FileName + " Record: " + t24RecordKey;
    }

    /**
     * Returns true if this file is opened from workspace, false otherwise(eg.,
     * from RTC source control)
     * 
     * @return true if locally stored, false otherwise
     */
    public boolean isLocalFile() {
        return isLocal;
    }

    /**
     * Retrieves the associated {@link IFile} from T24 server through
     * {@link T24DataFileTransfer}
     * 
     * @return true if retrieved successfully, false otherwise.
     */
    public boolean getFromT24() {
    	boolean received = T24DataFileTransfer.getInstance().getFromT24(getLocalPath(), getFileName());
    	if(received){
        storage = EditorDocumentUtil.getIFile(getLocalPath());
    	}
        return received;
    }

    /**
     * Sends the associated {@link IFile} to T24 server through
     * {@link T24DataFileTransfer}
     * 
     * @return
     */
    public boolean sendToT24() {
        return T24DataFileTransfer.getInstance().sendToT24(getLocalPath(), getFileName());
    }

    private void constructT24FileDetails() {
        String fileName = RemoteFileTransferUtil.removeDataFileExtension(getFileName());
        T24DataTransferHelper helper = T24DataTransferHelper.getInstance();
        t24FileName = helper.getT24FileName(fileName);
        t24RecordKey = helper.getT24RecordKey(fileName);
    }
}
