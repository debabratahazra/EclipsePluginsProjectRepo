package com.temenos.t24.tools.eclipse.basic.views.inlineDocs.listener;

import java.io.File;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file.GenerateDocConstants;

/**
 * Helper class to get the comments xml file path with the help of interface
 * {@link IT24CommentsFileHelper}
 * 
 * @author sbharathraja
 * 
 */
public final class T24CommentsFileInvestigator implements IT24CommentsFileHelper {

    /** name of the subroutine */
    private String subRoutineName;

    /**
     * constructor of {@link T24CommentsFileInvestigator}
     * 
     * @param routineName - name of the subroutine without extension
     */
    public T24CommentsFileInvestigator(String routineName) {
        this.subRoutineName = routineName;
    }

    public File[] getProductFolders() {
        File commentsRootFolder = new File(getCommentsLocation());
        if (commentsRootFolder.exists())
            return commentsRootFolder.listFiles();
        return null;
    }

    public boolean isProductThere(File productFolder, String productName) {
        if (!productFolder.exists())
            return false;
        return productFolder.getName().equalsIgnoreCase(productName);
    }

    public String getPathUpToComponentFolder(File productFolder, String componentName) {
        for (File publicOrPrivateFolder : productFolder.listFiles()) {
            for (File componentFolder : publicOrPrivateFolder.listFiles()) {
                if (componentFolder.isDirectory() && componentFolder.getName().contains(componentName)
                        && isXMLFileFound(componentFolder.getPath(), subRoutineName))
                    return componentFolder.getPath();
            }
        }
        return "";
    }

    public boolean isXMLFileFound(String pathUpToComponentFolder, String routineName) {
        File componentFolder = new File(pathUpToComponentFolder);
        String neededXMLFileName = routineName + XML_EXTENSION;
        if (!componentFolder.exists())
            return false;
        for (File xmlFile : componentFolder.listFiles()) {
            if (xmlFile.isFile() && xmlFile.getName().equalsIgnoreCase(neededXMLFileName))
                return true;
        }
        return false;
    }

    /**
     * get the Stripped out comments path from preferences page where the user
     * has saved.
     * 
     * @return full path
     */
    private String getCommentsLocation() {
        String path = DocumentFileUtil.getRoot() + GenerateDocConstants.PATH_SEPARATOR
                + GenerateDocConstants.NAME_OF_STRIPPED_FOLDER;
        return path;
    }
}
