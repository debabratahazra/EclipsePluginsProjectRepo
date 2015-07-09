package com.temenos.t24.tools.eclipse.basic.wizards.t24objects;

import java.util.ArrayList;
import java.util.List;

/** Class represents a T24 method */
class T24Method {

    /** Name of the method */
    private String t24MethodName = null;
    /** method description */
    private String description = null;
    /** Arguments list */
    private List<String> arguments = new ArrayList<String>();
    /** Value returned from the method */
    private String returnValue = null;
    /** Validation error */
    private String errorMessage = null;

    T24Method() {
    }

    public String getT24MethodName() {
        return t24MethodName;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setT24MethodName(String t24MethodName) {
        this.t24MethodName = t24MethodName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * Validates the method
     * 
     * @return true if method object is complete. false otherwise.
     */
    public boolean validate(boolean functionSelected) {
        if (functionSelected) {
            if (validateMethodName() && validateDescription() && validateReturnValue()) {
                return true;
            }
            return false;
        }
        if (validateMethodName() && validateDescription()) {
            return true;
        }
        return false;
    }

    private boolean validateDescription() {
        if (description == null || description.length() <= 0) {
            errorMessage = "Description must not be empty";
            return false;
        }
        return true;
    }

    private boolean validateMethodName() {
        if (t24MethodName == null || t24MethodName.length() <= 0) {
            errorMessage = "T24 Method name must not be empty";
            return false;
        }
        return true;
    }

    private boolean validateReturnValue() {
        if (returnValue == null || returnValue.length() <= 0) {
            errorMessage = "Return field must not be empty";
            return false;
        }
        return true;
    }
}
