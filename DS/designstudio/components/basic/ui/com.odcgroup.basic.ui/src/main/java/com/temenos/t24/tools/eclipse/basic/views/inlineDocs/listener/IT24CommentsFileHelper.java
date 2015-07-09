package com.temenos.t24.tools.eclipse.basic.views.inlineDocs.listener;

import java.io.File;

/**
 * interface contains methods which helps to retrieve the stripped out comments
 * xml file which generated through the Generate Doc action.
 * 
 * @author sbharathraja
 * 
 */
public interface IT24CommentsFileHelper {

    /**
     * extension for the xml file
     */
    public static final String XML_EXTENSION = ".XML";

    /**
     * get the product folders from the t24 comments location given by the user
     * at preferences page
     * 
     * @return array of folders as files
     */
    public File[] getProductFolders();

    /**
     * checking availability of the stripped-out comments available in
     * respective product folder in user given path at preferences page,
     * 
     * @param productFolder - product Folder as File
     * @param productName - name of the product corresponding to user accessed
     *            subroutine
     * @return true if product folder is there, false otherwise
     */
    public boolean isProductThere(File productFolder, String productName);

    /**
     * get the path up to the component folder
     * 
     * @param productFolder - product folder as File
     * @param componentName - name of the component respective to sub-routine
     * @return full path up-to the component folder
     */
    public String getPathUpToComponentFolder(File productFolder, String componentName);

    /**
     * checking whether the given subroutine name's corresponding xml file
     * presented in given component folder path or not.
     * 
     * @param pathUpToComponentFolder
     * @param routineName - name of the subroutine where the xml file store with
     *            as it is.
     * @return true if the respective xml file found, false otherwise
     */
    public boolean isXMLFileFound(String pathUpToComponentFolder, String routineName);
}
