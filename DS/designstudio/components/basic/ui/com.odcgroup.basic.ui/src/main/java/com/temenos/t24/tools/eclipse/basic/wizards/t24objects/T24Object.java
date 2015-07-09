package com.temenos.t24.tools.eclipse.basic.wizards.t24objects;

import java.util.ArrayList;
import java.util.List;

/** This class represents a T24object */
class T24Object {

    /** Name of the T24object */
    private String t24ObjectName = null;
    /** Package name */
    private String packageName = null;
    /** Author name */
    private String authorName = null;
    /** Description */
    private String comments = null;
    /** variable stores whether function is selected or not */
    private boolean functionSelected = true;
    /** List of T24 methods */
    private List<T24Method> t24Methods = new ArrayList<T24Method>();

    public String getName() {
        return t24ObjectName;
    }

    public void setT24ObjectName(String t24ObjectName) {
        this.t24ObjectName = t24ObjectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<T24Method> getT24Methods() {
        return t24Methods;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isFunctionSelected() {
        return functionSelected;
    }

    public void setFunctionSelected(boolean isFunctionSelected) {
        this.functionSelected = isFunctionSelected;
    }

    public void addT24Method(T24Method t24Method) {
        if (t24Method instanceof T24Method) {
            this.t24Methods.add(t24Method);
        }
    }

    public void removeT24Method(T24Method t24Method) {
        if (t24Methods.contains(t24Method)) {
            this.t24Methods.remove(t24Method);
        }
    }

    /**
     * Validates the T24object. Sets error message appropriately.
     * 
     * @return true if the T24object is valid. false otherwise.
     */
    public boolean validate() {
        if (t24ObjectName == null || t24ObjectName.length() <= 0) {
            return false;
        }
        if (packageName == null || packageName.length() <= 0) {
            return false;
        }
        if (authorName == null || authorName.length() <= 0) {
            return false;
        }
        return true;
    }
}
