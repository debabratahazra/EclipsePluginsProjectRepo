package com.temenos.t24.tools.eclipse.basic.help;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * Helper class which helps to find out the basic file within current user
 * workspace
 * 
 * @author sbharathraja
 * 
 */
public final class T24BasicFileInvestigator implements IT24FileHelper {

    public String getPathUpToComponentFolder(String componentName, String pathUpToSourceFolder) {
        File sourceFolder = new File(pathUpToSourceFolder);
        if (!sourceFolder.exists())
            return "";
        for (File inSourceFolder : sourceFolder.listFiles()) {
            if (inSourceFolder.isDirectory()) {
                for (File componentFolder : inSourceFolder.listFiles()) {
                    if (componentFolder.getName().equalsIgnoreCase(componentName))
                        return componentFolder.getPath();
                }
            }
        }
        return "";
    }

    public String getPathUpToSourceFolder(IProject project) {
        String sourceFolderPath = project.getLocation().toOSString();
        sourceFolderPath += IT24FileHelper.PATH_SEPARATOR + IT24FileHelper.NAME_OF_SOURCE_FOLDER;
        // return the path only if the file exists
        if (new File(sourceFolderPath).exists())
            return sourceFolderPath;
        else
            return "";
    }

    public IProject[] getProjects() {
        // get the workspace
        IWorkspace workSpace = ResourcesPlugin.getWorkspace();
        // get the root of the workspace
        IWorkspaceRoot workRoot = workSpace.getRoot();
        // return array of projects which will be in user's current workspace
        return workRoot.getProjects();
    }

    public boolean isProductThere(IProject project, String productName) {
        return project.getName().contains(productName);
    }

    public boolean isRoutineFound(String subRoutineName, String pathUptoComponentFolder) {
        StringUtil stringUtil = new StringUtil();
        File componentFolder = new File(pathUptoComponentFolder);
        if (!componentFolder.exists())
            return false;
        for (File basicFile : componentFolder.listFiles()) {
            if (stringUtil.isBasicFile(basicFile.getName()) && basicFile.getName().equalsIgnoreCase(subRoutineName))
                return true;
        }
        return false;
    }

    public boolean isRoutineFoundInTempFolder(String pathOfTempFolder, String subRoutineName) {
        StringUtil stringUtil = new StringUtil();
        File tempFolder = new File(pathOfTempFolder);
        if (!tempFolder.exists())
            return false;
        for (File basicFile : tempFolder.listFiles()) {
            if (stringUtil.isBasicFile(basicFile.getName()) && basicFile.getName().equalsIgnoreCase(subRoutineName))
                return true;
        }
        return false;
    }
}
