package com.temenos.t24.tools.eclipse.basic.jremote;

import java.util.EnumSet;

/**
 * Enum defines the available connections for a {@link RemoteSite}
 * 
 * @author ssethupathi
 * 
 */
public enum RemoteConnectionType {
    JCA("jca");
    private String value;

    private RemoteConnectionType(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return value;
    }

    /**
     * Returns the {@link RemoteSiteType} for the passed in string value.
     * 
     * @param value
     * @return type
     */
    public static RemoteConnectionType getByValue(String value) {
        RemoteConnectionType returnValue = null;
        for (final RemoteConnectionType type : EnumSet.allOf(RemoteConnectionType.class)) {
            if (type.toString().equals(value)) {
                returnValue = type;
            }
        }
        return returnValue;
    }
}
