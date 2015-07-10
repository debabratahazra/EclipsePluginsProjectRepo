package com.zealcore.se.ui.core.report;

import org.eclipse.swt.graphics.ImageData;

/**
 * Class for using ImageData as a report item in reports
 * <p>
 * Used by report contributors to populate a report
 * 
 * @see AbstractReport
 * @see AbstractReportItem
 * @see IReportContributor
 * @author daze
 * 
 */
public class ImageReportItem extends AbstractReportItem {

    private static final String S_NOT_AVAILABLE = "n/a";

    private final ImageData image;

    /**
     * Constructs a new ImageReportItem and sets the name and description to its
     * default values
     * 
     * @param image
     *                The ImageData object which will be contained in the
     *                constructed ImageReportItem
     * @see AbstractReportItem#setName(String)
     * @see AbstractReportItem#setDescription(String)
     */
    public ImageReportItem(final ImageData image) {
        this.image = image;
        setName(ImageReportItem.S_NOT_AVAILABLE);
        setDescription(ImageReportItem.S_NOT_AVAILABLE);
    }

    /**
     * Gets the image that is contained in this ImageReportItem
     * 
     * @return The contained image
     */
    public ImageData getImage() {
        return this.image;
    }
}
