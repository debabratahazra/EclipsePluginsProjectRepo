package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.util.EnumSet;

/**
 * Enum defines the available operation Type for a {@link RemoteSite}
 * 
 * @author yasar
 * 
 */
public enum OperationType{
    TRANSFER("transfer"), INSTALL("install");

    private String value;

    private OperationType(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return value;
    }

    /**
     * Returns the {@link operation Type} for the passed in string value.
     * 
     * @param value
     * @return type
     */
    public static Protocol getByValue(String value) {
        Protocol returnValue = null;
        for (final Protocol type : EnumSet.allOf(Protocol.class)) {
            if (type.toString().equals(value)) {
                returnValue = type;
            }
        }
        return returnValue;
    }
}
