package com.temenos.t24.tools.eclipse.basic.views.inlineDocs.listener;

import java.io.File;

import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineInfo;
import com.temenos.t24.tools.eclipse.basic.help.AbstractT24FileHelper;
import com.temenos.t24.tools.eclipse.basic.help.IT24FileHelper;

/**
 * Helper class which helps to get the comments xml file path from where that
 * files is stored which generated through doc generation operation. This class
 * define the method getCommentsXMLPath which is declared in
 * {@link AbstractT24FileHelper}
 * 
 * @author sbharathraja
 * 
 */
public final class T24CommentsFileExplorer extends AbstractT24FileHelper {

    /** instance of class {@link SubroutineInfo} */
    private SubroutineInfo subRoutineInfo;
    /** instance of {@link IT24CommentsFileHelper} */
    private IT24CommentsFileHelper fileHelper;

    @Override
    public File getBasicFile(String subRoutineName) {
        // since this method is needed only when the treasure of basic file from
        // user workspace, now it return null.
        return null;
    }

    @Override
    public String getCommentsXMLPath(String subRoutineName) {
        subRoutineInfo = getSubRoutineInformation(subRoutineName);
        fileHelper = new T24CommentsFileInvestigator(subRoutineName);
        if (fileHelper.getProductFolders() == null)
            return "";
        for (File productFolder : fileHelper.getProductFolders()) {
            if (fileHelper.isProductThere(productFolder, subRoutineInfo.getProduct())) {
                String pathUpToCompFolder = fileHelper.getPathUpToComponentFolder(productFolder, subRoutineInfo.getComponent());
                // if (fileHelper.isXMLFileFound(pathUpToCompFolder,
                // subRoutineName))
                return pathUpToCompFolder + IT24FileHelper.PATH_SEPARATOR + subRoutineName + IT24CommentsFileHelper.XML_EXTENSION;
            }
        }
        return "";
    }
}
