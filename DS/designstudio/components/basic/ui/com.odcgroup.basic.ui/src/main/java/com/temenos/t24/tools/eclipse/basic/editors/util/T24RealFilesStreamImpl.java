package com.temenos.t24.tools.eclipse.basic.editors.util;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;

/**
 * Class which provides the actual data paths to the T24FilesStream class
 * 
 * @author ssethupathi
 * 
 */
public class T24RealFilesStreamImpl extends T24FilesStream {

    private static T24RealFilesStreamImpl REAL_FILE_STREAM = null;
    private static String sourcePath = PluginConstants.config_dir + PluginConstants.t24SourceItemsFile;
    private static String objectPath = PluginConstants.config_dir + PluginConstants.t24ObjectsFile;
    private static String keywordPath = PluginConstants.config_dir + PluginConstants.keywordsFilename;

    private T24RealFilesStreamImpl() {
        super(sourcePath, objectPath, keywordPath);
    }

    public static T24RealFilesStreamImpl getInstance() {
        if (REAL_FILE_STREAM == null) {
            REAL_FILE_STREAM = new T24RealFilesStreamImpl();
        }
        return REAL_FILE_STREAM;
    }
}
