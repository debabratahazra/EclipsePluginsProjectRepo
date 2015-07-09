package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file;

import java.io.File;
import java.io.IOException;

/**
 * helper class of Comments Strip out mainly used for create new File
 * 
 * @author sbharathraja
 * 
 */
class FileCreator {

    /** instance of {@link FileCreator} */
    private static FileCreator fileMaker = new FileCreator();

    /**
     * get the instance
     * 
     * @return instance of {@link FileCreator}
     */
    protected static FileCreator getInstance() {
        return fileMaker;
    }

    private FileCreator() {
        // block instance creation
    }

    /**
     * making new file with given path
     * 
     * @param path
     * @return true if file created newly, false if the file already presented
     */
    protected boolean makeNewFile(String path) {
        try {
            File newFile = new File(path);
            if (!newFile.exists())
                return newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * making new Folder with given path
     * 
     * @param path
     * @return true if folder created newly, false if the folder already
     *         presented
     */
    protected boolean makeNewFolder(String path) {
        File newFolder = new File(path);
        if (!newFolder.exists())
            return newFolder.mkdir();
        return false;
    }
}
