package com.temenos.t24.tools.eclipse.basic.document.util;

import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file.GenerateDocConstants;

public class DocumentTextConverter {

    public static String convertAsXml(String document) {
        return document.replaceAll("\r", "");
    }

    public static String convertAsText(String document) {
        return document;
    }

    /**
     * convert the line break symbol presented in xml to new line feed
     * 
     * @param document - content to be formatted
     * @return formatted content
     */
    public static String convertCarriageReturnToNewLineFeed(String document) {
        return document.replaceAll(GenerateDocConstants.LINE_BRK_SYMBOL, System.getProperty("line.separator"));
    }
}
