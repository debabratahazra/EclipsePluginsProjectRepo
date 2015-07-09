package com.odcgroup.mdf.validation.validator;

import java.io.IOException;


public class JavaKeywordChecker extends KeywordChecker {

    private static JavaKeywordChecker INSTANCE = null;

    private JavaKeywordChecker() throws IOException {
        super("java.keywords", true);
    }

    public static synchronized JavaKeywordChecker getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new JavaKeywordChecker();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return INSTANCE;
    }

}
