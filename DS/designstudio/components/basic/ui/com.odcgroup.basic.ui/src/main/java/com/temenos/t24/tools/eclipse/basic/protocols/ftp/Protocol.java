package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.util.EnumSet;

/**
 * Enum defines the available protocols for a {@link RemoteSite}
 * 
 * @author yasar
 * 
 */
public enum Protocol{
    FTP("ftp"), SFTP("sftp"), LOCAL("local");

    private String value;

    private Protocol(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return value;
    }

    /**
     * Returns the {@link Protocol} for the passed in string value.
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
