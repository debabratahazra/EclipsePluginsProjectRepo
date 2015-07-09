package com.temenos.t24.tools.eclipse.basic.help;

import org.eclipse.core.resources.IProject;

/**
 * interface contains methods which helps to find out the given subroutine in
 * local disk with the help of user current workspace
 * 
 * @author sbharathraja
 * 
 */
public interface IT24FileHelper {

    /** name of the folder under basic file resides */
    public static final String NAME_OF_SOURCE_FOLDER = "Source";
    /** extension of the basic file */
    public static final String BASIC_FILE_EXTN = ".b";
    /** name of the temporary folder where the subroutine can be located */
    public static final String NAME_OF_TEMP_FOLDER = "TempT24BasicIDEProj";
    /**
     * path separator which have to changed if the platform will change from
     * MS-Windows
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * get the projects from user current workspace
     * 
     * @return - array of projects as instance of {@link IProject}
     */
    public IProject[] getProjects();

    /**
     * checking whether the given subroutine's respective product is available
     * in local disk or not.
     * 
     * @param project - project available in user current workspace
     * @param productName - name of the product respective to subroutine in
     *            hands
     * @return true if respective product found, false otherwise
     */
    public boolean isProductThere(IProject project, String productName);

    /**
     * get the full path up-to the source folder where the subroutine can lies.
     * 
     * @param project - project available in user current workspace
     */
    public String getPathUpToSourceFolder(IProject project);

    /**
     * get the full path up-to the component folder have the name as given
     * component name within given source folder path, where the basic files can
     * be located
     * 
     * @param componentName - name of the component respective to subroutine
     * @param pathUpToSourceFolder - full path up-to the source folder where the
     *            basic files can be lies
     */
    public String getPathUpToComponentFolder(String componentName, String pathUpToSourceFolder);

    /**
     * checking whether the given subroutine file with subroutine name is
     * presented in given component folder path as basic file file
     * 
     * @param subRoutineName - name of the basic file with extension
     * @param pathUptoComponentFolder - path up-to the component folder where
     *            the basic files can be located
     * @return true if given subRoutineName's respective basic file is found,
     *         false otherwise
     */
    public boolean isRoutineFound(String subRoutineName, String pathUptoComponentFolder);

    /**
     * checking whether the given subroutine is presented in given temporary
     * folder path as basic file
     * 
     * @param pathOfTempFolder - path of the temporary folder
     * @param subRoutineName - routine name with extensions
     * @return true if the given subroutine is found in temp folder as basic
     *         file, false otherwise
     */
    public boolean isRoutineFoundInTempFolder(String pathOfTempFolder, String subRoutineName);
}
