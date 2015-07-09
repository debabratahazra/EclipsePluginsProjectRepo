package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file;

/**
 * container class which contains comments, keywords and identifiers.
 * 
 * @author sbharathraja
 * 
 */
class CommentsContainer {

    /** short information comment */
    private String info = "";
    /** more information comment */
    private String moreInfo = "";
    /** keyword in the basic file */
    private String keyword = "";
    /** identifier of the keyword */
    private String identifier = "";

    /**
     * set the short comment
     * 
     * @param info - short comment
     */
    protected void setInfo(String info) {
        this.info = info;
    }

    /**
     * get the short comment
     * 
     * @return short info comment
     */
    protected String getInfo() {
        return info;
    }

    /**
     * set the more information comment
     * 
     * @param moreInfo - more information comment
     */
    protected void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    /**
     * get the more information comment
     * 
     * @return more information comment
     */
    protected String getMoreInfo() {
        return moreInfo;
    }

    /**
     * set the keyword presented in basic file
     * 
     * @param keyword
     */
    protected void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * get the keyword
     * 
     * @return keyword
     */
    protected String getKeyword() {
        return keyword;
    }

    /**
     * set the identifier equivalence to keyword
     * 
     * @param identifier
     */
    protected void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * get the identifier equivalence to keyword
     * 
     * @return identifier
     */
    protected String getIdentifier() {
        return identifier;
    }
}
