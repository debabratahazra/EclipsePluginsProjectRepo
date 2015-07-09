package com.temenos.t24.tools.eclipse.basic.document.capture;

public class DocumentCharConverter {

    private static final String BACK_SLASH = "\\";

    private DocumentCharConverter() {
    }

    /**
     * Creates a new converter instance.
     * 
     * @return converter
     */
    public static DocumentCharConverter newInstance() {
        return new DocumentCharConverter();
    }

    public String convert(String str) throws InvalidDocumentException {
        if (str == null) {
            throw new InvalidDocumentException("Document is null");
        }
        String convertedStr = convertStrWithUnsupportedChars(str);
        return convertedStr;
    }

    private String convertStrWithUnsupportedChars(String str) throws InvalidDocumentException {
        // chars in word - converted to supportable format
        // Char Word notepad/Dialog Area
        //
        // “ 8220 34
        // ” 8221 34
        //
        // ‘ 8216 39
        // ’ 8217 39
        // – 8211 45
        // character in ms word
        char openDblQuotes = (char) 8220;
        char closeDblQuotes = (char) 8221;
        char openSingleQuote = (char) 8216;
        char closeSingleQuote = (char) 8217;
        char hyphenChar = (char) 8211;
        // counterpart characters in notepad
        char dblQuotes = (char) 34;
        char singleQuote = (char) 39;
        char hyph = (char) 45;
        str = str.replaceAll(BACK_SLASH + openDblQuotes, Character.toString(dblQuotes));
        str = str.replaceAll(BACK_SLASH + closeDblQuotes, Character.toString(dblQuotes));
        str = str.replaceAll(BACK_SLASH + openSingleQuote, Character.toString(singleQuote));
        str = str.replaceAll(BACK_SLASH + closeSingleQuote, Character.toString(singleQuote));
        str = str.replaceAll(BACK_SLASH + hyphenChar, Character.toString(hyph));
        return str;
    }
}
