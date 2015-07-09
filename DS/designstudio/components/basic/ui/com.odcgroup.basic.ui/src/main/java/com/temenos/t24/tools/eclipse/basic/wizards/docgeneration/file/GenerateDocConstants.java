package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file;

/**
 * holds the constants which used in doc generation operation
 * 
 * @author sbharathraja
 * 
 */
public final class GenerateDocConstants {

    /**
     * name of the root folder which going to have all stripped out comments xml
     * file, which actually belonging to T24 doc location
     */
    public static final String NAME_OF_STRIPPED_FOLDER = "InLineDoc";
    /** name of the t24 doc directory */
    public static final String NAME_OF_T24DOC_DIR = "T24CompDoc";
    /** used to path separating */
    public static final String PATH_SEPARATOR = "/";
    /** name of the folder under basic file resides */
    public static final String NAME_OF_SOURCE_FOLDER = "Source";
    /** name of the element which will stored as xml */
    public static final String NAME_OF_ELEMENT = "name";
    /** identifier of the element */
    public static final String IDENTIFIER_OF__ELEMENT = "identifier";
    /** short comment */
    public static final String NAME_OF_SMALL_COMMENT = "info";
    /** large comment */
    public static final String NAME_OF_LARGE_COMMENT = "moreInfo";
    /** extension of the xml file */
    public static final String XML_EXTN = ".XML";
    /**
     * line break symbol which add in xml after each line for recognize that the
     * new line feed, which use for read it back and display it in pop-up window
     */
    public static final String LINE_BRK_SYMBOL = "@LB";
}
