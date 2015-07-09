package com.temenos.t24.tools.eclipse.basic.editors.util;

import java.io.InputStream;

/**
 * Abstract class which provides InputStream instances for different files used
 * in the content assist process. Using Spring, the implementation instance is
 * created based on the application context.
 * 
 * @author ssethupathi
 * 
 */
public abstract class T24FilesStream {

    protected static String sourcePath;
    protected static String objectPath;
    protected static String keywordPath;

    public T24FilesStream(String srcPath, String objPath, String keyPath) {
        sourcePath = srcPath;
        objectPath = objPath;
        keywordPath = keyPath;
    }

    /**
     * Returns the InputStream instance for T24 source items file
     * 
     * @return InputStream
     */
    public InputStream getItemsFileStream() {
        return T24FilesStream.class.getResourceAsStream(sourcePath);
    }

    /**
     * Returns the InputStream instance for T24 Objects properties file
     * 
     * @return InputStream
     */
    public InputStream getObjectsFileStream() {
        return T24FilesStream.class.getResourceAsStream(objectPath);
    }

    /**
     * Returns the InputStream instance for keywords file
     * 
     * @return InputStream
     */
    public InputStream getKeywordsFileStream() {
        return T24FilesStream.class.getResourceAsStream(keywordPath);
    }
}
