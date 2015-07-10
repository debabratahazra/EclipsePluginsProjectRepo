/*
 * 
 */
package com.zealcore.se.ui.util;

/**
 * Utility for BitChecking.
 */
public final class BitCheck {

    private BitCheck() {

    }

    /**
     * Checks if the specified value has any of the specified masks. For
     * instance 3 would have bitmasks 1 and 2, as such returning true for
     * anyOf(3,1,4,5).
     * 
     * @param value
     *                the value
     * @param masks
     *                the masks
     * 
     * @return true, if any of the masks are represented in the value
     */
    public static boolean anyOf(final int value, final int... masks) {
        for (final int mask : masks) {
            if ((value & mask) != 0) {
                return true;
            }
        }
        return false;
    }
}
