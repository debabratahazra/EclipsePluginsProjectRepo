package com.temenos.t24.tools.eclipse.basic.help;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import com.temenos.t24.tools.eclipse.basic.utils.DocumentViewerUtil;

/**
 * help cache class for providing help text for identifier of call keyword in
 * basic file
 * 
 * @author sbharathraja
 * 
 */
public class CallHoverHelpCache {

    /**
     * helper to find-out basic file
     */
    private AbstractT24FileHelper fileHelper;

    /**
     * get the information about subroutine identifier hovered.
     * 
     * @param routineName - subroutine name
     * @return help words about the subroutine
     */
    public String getHelpForCall(String routineName) {
        fileHelper = new T24BasicFileExplorer();
        File basicFile = fileHelper.getBasicFile(routineName);
        if (basicFile == null || !basicFile.exists())
            return "";
        IFile iSubRoutineFile = makeFileToIFile(basicFile);
        String comment = DocumentViewerUtil.getDocument(iSubRoutineFile);
        if (comment == null)
            return "";
        else
            return comment;
    }
    
    /**
     * converting normal file to IFile, but the normal file should be in local
     * os path, not on any remote path
     * 
     * @param normalFile - java's io file
     * @return IFile equivalent to java's io file
     */
    public static IFile makeFileToIFile(File normalFile) {
        return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(Path.fromOSString(normalFile.getAbsolutePath()));
    }
}
