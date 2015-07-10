package com.zealcore.se.ui.core.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Abstract class that represents a report
 * <p>
 * Should be extended by report generators
 * <p>
 * Used by report contributors to submit report items
 * 
 * @see AbstractReportGenerator
 * @see IReportContributor
 * @see HtmlReportGenerator
 * @author daze
 * 
 */
public abstract class AbstractReport {

    private Collection<AbstractReportItem> items;

    /**
     * Gets an unmodifiable collection of report items
     * 
     * @return The collection of report items
     */
    Collection<AbstractReportItem> getReportData() {
        return Collections.unmodifiableCollection(this.items);
    }

    /**
     * Adds an report item to this report
     * 
     * @param reportItem
     *                The item to add
     */
    public void addReportData(final AbstractReportItem reportItem) {
        if (this.items == null) {
            this.items = new ArrayList<AbstractReportItem>();
        }
        this.items.add(reportItem);
    }

    /**
     * Clears all report data
     * 
     */
    void clearReportData() {
        if (this.items == null) {
            return;
        }
        this.items.clear();
    }
}
