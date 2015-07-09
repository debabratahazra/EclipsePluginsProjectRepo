package com.odcgroup.edge.t24.generation.enquiry.model.dsl;

/**
 * <code>EnquiryAttributeNames</code> is the central point-of-definition for the names of the various "true-valued" {@link Enquiry#getAttributes() attributes} that can be
 * defined in the DSL description of an Enquiry.<p>
 *
 * Note that the sole intended user of the constants defined in this interface is {@link EnquiryAttrsDigest}
 * 
 * @author Simon Hayes
 */
public interface EnquiryAttributeNames
{
    String ALLDATA = "ALLDATA";
    String NO_BREADCRUMBS = "NO.BREADCRUMBS";
    String NO_COLUMN_HEADERS = "NO.COLUMN.HEADER";
    String NO_FAVOURITES = "NO.ENQUIRY.FAVOURITES";
    String NO_MORE_OPTIONS = "NO.MORE.OPTIONS";
    String NO_TOOLBAR = "NO.TOOLBAR";
    String SINGLE_BACKGROUND_COLOUR = "SINGLE.BACKGROUND.COLOUR";
}
