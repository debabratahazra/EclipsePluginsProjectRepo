package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.util.EnumSet;

/**
 * Enum defines the supported platforms for a {@link RemoteSite}
 * 
 * @author ssethupathi
 * 
 */
public enum RemoteSitePlatform {
    UNIX("UNIX"), NT("NT"), LINUX("LINUX");

    private String value;

    private RemoteSitePlatform(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return value;
    }

    /**
     * Returns the {@link RemoteSitePlatform} for the passed in string value.
     * 
     * @param value
     * @return platform
     */
    public static RemoteSitePlatform getByValue(String value) {
        RemoteSitePlatform returnValue = null;
        for (final RemoteSitePlatform platform : EnumSet.allOf(RemoteSitePlatform.class)) {
            if (platform.toString().equals(value)) {
                returnValue = platform;
            }
        }
        return returnValue;
    }
}
