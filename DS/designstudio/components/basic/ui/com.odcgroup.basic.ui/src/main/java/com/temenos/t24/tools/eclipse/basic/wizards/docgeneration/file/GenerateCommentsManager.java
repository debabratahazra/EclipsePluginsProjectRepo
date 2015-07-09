package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file;

import java.io.File;

import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * entry point for comments generation after the user click the finish button in
 * generate comments wizard, managing the comments generation operation
 * 
 * @author sbharathraja
 * 
 */
public class GenerateCommentsManager {

    /** array of selected projects */
    private String[] selectedProjects;
    /** path to strip out the comments */
    private String targetFolderPath;
    /** input location */
    private String inputLocation;
    /** target folder path build step by step */
    private String stepByStepTargetPath = "";

    /**
     * constructor of generate comments manager
     * 
     * @param selectedProjects
     * @param targetPath
     */
    public GenerateCommentsManager(String[] selectedProjects, String inputPath, String targetPath) {
        this.selectedProjects = selectedProjects;
        this.inputLocation = inputPath;
        this.targetFolderPath = targetPath;
    }

    /**
     * initialize the comment strip out process
     */
    public void initCommentsStripOut() {
        FileCreator.getInstance().makeNewFolder(targetFolderPath);
        for (String project : selectedProjects) {
            stepByStepTargetPath = targetFolderPath + GenerateDocConstants.PATH_SEPARATOR + getProductName(project);
            FileCreator.getInstance().makeNewFolder(stepByStepTargetPath);
            listOutFolders(project);
        }
    }

    /**
     * list out the folder within the given project folder
     * 
     * @param project
     */
    private void listOutFolders(String project) {
        // path up to source folder
        String path = inputLocation + GenerateDocConstants.PATH_SEPARATOR + project + GenerateDocConstants.PATH_SEPARATOR
                + GenerateDocConstants.NAME_OF_SOURCE_FOLDER;
        File sourceFolder = new File(path);
        if (sourceFolder.exists()) {
            for (File withinSourceFolder : sourceFolder.listFiles()) {
                if (withinSourceFolder.isDirectory()) {
                    listOutComponentFolders(withinSourceFolder);
                }
                stepByStepTargetPath = targetFolderPath + GenerateDocConstants.PATH_SEPARATOR + getProductName(project);
            }
        }
    }

    /**
     * list out component files within the given source folder
     * 
     * @param withinSourceFolder
     */
    private void listOutComponentFolders(File withinSourceFolder) {
        stepByStepTargetPath += (GenerateDocConstants.PATH_SEPARATOR + withinSourceFolder.getName());
        FileCreator.getInstance().makeNewFolder(stepByStepTargetPath);
        for (File componentFolder : withinSourceFolder.listFiles()) {
            if (componentFolder.isDirectory()) {
                getBasicFiles(componentFolder);
            }
        }
    }

    /**
     * get the basic files from given component folder and initiate the process
     * of comments strip out
     * 
     * @param componentFolder
     */
    private void getBasicFiles(File componentFolder) {
        StringUtil stringUtil = new StringUtil();
        String tempTargetPath = "";
        tempTargetPath = stepByStepTargetPath + GenerateDocConstants.PATH_SEPARATOR + componentFolder.getName();
        for (File bFile : componentFolder.listFiles()) {
            if (stringUtil.isBasicFile(bFile.getName())) {
                FileCreator.getInstance().makeNewFolder(tempTargetPath);
                BasicFileReader.getInstance().stripCommentsFromSingleFile(bFile, tempTargetPath);
            }
            deleteEmptyFolder(tempTargetPath);
        }
    }

    /**
     * get the product name from the given project name.
     * 
     * @param projectName - name of the project
     * @return product name
     */
    private String getProductName(String projectName) {
        String[] splittedProjectName = projectName.split("_");
        // since the product name is in first part of project name return the
        // first index(e.g AA_BF here AA is product).
        return splittedProjectName[0];
    }

    /**
     * delete the empty folder (doesn't contains any files)
     * 
     * @param folderPath - path of the folder
     */
    private void deleteEmptyFolder(String folderPath) {
        File emptyFolder = new File(folderPath);
        if (emptyFolder.isDirectory() && emptyFolder.listFiles().length == 0) {
            emptyFolder.delete();
        }
    }
}
