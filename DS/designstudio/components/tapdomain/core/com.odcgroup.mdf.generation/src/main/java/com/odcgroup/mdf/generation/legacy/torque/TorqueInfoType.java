package com.odcgroup.mdf.generation.legacy.torque;

/**
 * Represents Torque type info
 *
 * @author <a href="mailto:dnemeshazyodyssey-group.com">David Nemeshazy</a>
 * @version 1.0
 */
public class TorqueInfoType {
    private final String size;
    private final String type;

    /**
     * Creates a new TorqueType object.
     *
     * @param t TODO: DOCUMENT ME!
     * @param s TODO: DOCUMENT ME!
     */
    public TorqueInfoType(String t, String s) {
        this.type = t;
        this.size = s;
    }

    /**
     * TODO: DOCUMENT ME!
     *
     * @return TODO: DOCUMENT ME!
     */
    public String getSize() {
        return size;
    }

    /**
     * TODO: DOCUMENT ME!
     *
     * @return TODO: DOCUMENT ME!
     */
    public String getType() {
        return type;
    }
}
