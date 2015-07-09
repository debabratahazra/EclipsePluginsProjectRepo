package com.temenos.t24.tools.eclipse.basic.help;

import java.io.File;

import org.eclipse.core.resources.IProject;

import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineInfo;

/**
 * Helper class to get the basic file from user's current workspace. This
 * implements the method getBasicFile from {@link AbstractT24FileHelper}. The
 * real logic for file fetching is done here with the help of interface
 * {@link IT24FileHelper}
 * 
 * @author sbharathraja
 * 
 */
public final class T24BasicFileExplorer extends AbstractT24FileHelper {

    /** instance of class {@link SubroutineInfo} */
    private SubroutineInfo subRoutineInfo;
    /** {@link IT24FileHelper} */
    private IT24FileHelper fileHelper;

    public File getBasicFile(String subRoutineName) {
        subRoutineInfo = getSubRoutineInformation(subRoutineName);
        if (subRoutineInfo == null || subRoutineInfo.getProduct().equalsIgnoreCase(""))
            return null;
        fileHelper = new T24BasicFileInvestigator();
        for (IProject project : fileHelper.getProjects()) {
            if (fileHelper.isProductThere(project, subRoutineInfo.getProduct())) {
                if (!getFullPath(project).equalsIgnoreCase(""))
                    return new File(getFullPath(project));
            }
        }
        return null;
    }

    /**
     * get the full path of subroutine
     * 
     * @param project
     * @return path if basic file found, empty otherwise
     */
    private String getFullPath(IProject project) {
        if (fileHelper == null || subRoutineInfo.getComponent().equalsIgnoreCase(""))
            return "";
        String pathUpToSourceFolder = fileHelper.getPathUpToSourceFolder(project);
        String pathUpToComponentFolder = fileHelper.getPathUpToComponentFolder(subRoutineInfo.getComponent(), pathUpToSourceFolder);
        String basicFileName = subRoutineInfo.getSubroutineName() + IT24FileHelper.BASIC_FILE_EXTN;
        if (fileHelper.isRoutineFound(basicFileName, pathUpToComponentFolder))
            return pathUpToComponentFolder + IT24FileHelper.PATH_SEPARATOR + basicFileName;
        return "";
    }

    @Override
    public String getCommentsXMLPath(String subRoutineName) {
        // since this method is using only while the comments xml file path
        // needed, it return empty now.
        return "";
    }
}
