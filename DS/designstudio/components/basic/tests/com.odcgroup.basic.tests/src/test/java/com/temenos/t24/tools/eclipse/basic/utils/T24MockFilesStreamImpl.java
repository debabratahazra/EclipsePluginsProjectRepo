package com.temenos.t24.tools.eclipse.basic.utils;

import com.temenos.t24.tools.eclipse.basic.editors.util.T24FilesStream;

public class T24MockFilesStreamImpl extends T24FilesStream {

    private static T24MockFilesStreamImpl TEST_FILE_STREAM = null;
    private static String sourcePath = "/testfiles/contentAssistTest_t24SourceItem.dat";
    private static String objectPath = "/testfiles/contentAssistTest_t24Objects.properties";
    private static String keywordPath = "/testfiles/contentAssistTest_keywords.dat";

    private T24MockFilesStreamImpl() {
        super(sourcePath, objectPath, keywordPath);
    }

    public static T24MockFilesStreamImpl getInstance() {
        if (TEST_FILE_STREAM == null) {
            TEST_FILE_STREAM = new T24MockFilesStreamImpl();
        }
        return TEST_FILE_STREAM;
    }
}
