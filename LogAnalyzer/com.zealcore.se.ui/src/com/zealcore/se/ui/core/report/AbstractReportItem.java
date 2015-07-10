package com.zealcore.se.ui.core.report;

/**
 * Extend this class to create concrete report item types
 * 
 * @see ImageReportItem
 * @author daze
 * 
 */
public abstract class AbstractReportItem {

    private String name;

    private String description;

    /**
     * Gets this item's name
     * 
     * @return The name of this item
     */
    String getName() {
        return this.name;
    }

    /**
     * Sets this item's name
     * 
     * @param name
     *                The new name for this item
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets this item's description
     * 
     * @return The description for this item
     */
    String getDescription() {
        return this.description;
    }

    /**
     * Sets this item's description
     * 
     * @param description
     *                The new description for this item
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
