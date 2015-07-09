package com.odcgroup.edge.t24.generation.enquiry.model.dsl;

import org.eclipse.emf.common.util.EList;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.BasicEnquiryProject;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.DisplayResultsTableWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.IrisResultGroupWrapper;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 * <code>EnquiryAttrsDigest</code> is the "center of intelligence" for knowledge regarding the interpretation of the set of any <code>Enquiry</code>'s "true-valued"
 * {@link Enquiry#getAttributes() attributes} in combination with relevant "summary information" from the supplied {@link EnquiryFieldsDigest} (
 * {@link EnquiryFieldsDigest#noVisibleFieldsHaveLabels() noVisibleFieldsHaveLabels()},
 * {@link EnquiryFieldsDigest#allVisibleFieldsSpecifyNoHeader() allVisibleFieldsSpecifyNoHeader()}, and
 * {@link EnquiryFieldsDigest#includesStartTreeEndTreeFields() includesStartTreeEndTreeFields()}).<p><p>
 * 
 * The main aim of this class is to translate the <i>(sometimes distinctly non-obvious)</i> semantics of individual attributes / combinations of attributes into terms that will "make sense" to
 * a developer working on the main Enquiry IFP {@link BasicEnquiryProject generator} code, so that referencing code because easier to understand for non T24 experts <i>(such as myself !)</i>.<p>
 * 
 * It will acts as a "first port-of-call" for investigating generation problems where it seems likely that we've misunderstood the significance of a particular enquiry attribute, and
 * a central point for "fixes" to any such misinterpretations.
 * 
 * For these reasons, please try to use this class (adding to it if need be) rather than consulting the underlying low-level information from the DSL directly (it will make the code easier to
 * maintain :-)).<p>
 *
 * @author Simon Hayes
 */
public class EnquiryAttrsDigest
{
    public static final int DEFAULT_NO_OF_ROWS_PER_PAGE = 10;
    
    private final Enquiry m_enquiry;
    
    private final boolean
        m_areResultsTableColumnHeadersSuppresssed,
        m_isBreadCrumbTrailRequired,
        m_hasDrilldowns,
        m_isHeaderEnquiry,
        m_isResultsScreenToolbarRequired,
        m_isReiterationOfPopulatedSearchParamsBeneathResultsTableRequired,
        m_isSearchFavouritesFunctionalityRequired,
        m_isTreeEnquiry,
        m_isShowAllResultsOnOnePage,
        m_useSingleBackgroundColourForResultsTable;

    private final EnquiryFieldsDigest m_enquiryFieldsDigest;
        
    public EnquiryAttrsDigest(EnquiryFieldsDigest p_enquiryFieldsDigest)
    {
        m_enquiryFieldsDigest = AssertionUtils.requireNonNull(p_enquiryFieldsDigest, "p_enquiryFieldsDigest");
        
        m_enquiry = p_enquiryFieldsDigest.getEnquiry();
        
        final EList<String> enquiryAttrs = m_enquiry.getAttributes();
        
        final boolean
            isAllDataEnquiry = enquiryAttrs.contains(EnquiryAttributeNames.ALLDATA),
            isNoBreadCrumbs = enquiryAttrs.contains(EnquiryAttributeNames.NO_BREADCRUMBS),
            isNoColumnHeaders = enquiryAttrs.contains(EnquiryAttributeNames.NO_COLUMN_HEADERS),
            isNoFavouritesEnquiry = enquiryAttrs.contains(EnquiryAttributeNames.NO_FAVOURITES),
            isNoSelectionEnquiry = Boolean.TRUE.equals(m_enquiry.getNoSelection()),
            isNoToolbarEnquiry = enquiryAttrs.contains(EnquiryAttributeNames.NO_TOOLBAR),
            isSingleBackgroundColour = enquiryAttrs.contains(EnquiryAttributeNames.SINGLE_BACKGROUND_COLOUR);
        
        m_hasDrilldowns = ! ((m_enquiry.getDrillDowns() == null) || m_enquiry.getDrillDowns().isEmpty());
        m_isBreadCrumbTrailRequired = ! isNoBreadCrumbs;
        m_isHeaderEnquiry = (isNoToolbarEnquiry && p_enquiryFieldsDigest.noVisibleFieldsHaveLabels() && p_enquiryFieldsDigest.allVisibleFieldsSpecifyNoHeader());
        m_areResultsTableColumnHeadersSuppresssed = isNoColumnHeaders || m_isHeaderEnquiry || p_enquiryFieldsDigest.allVisibleResultsFieldsHaveRelativePositions();
        m_isReiterationOfPopulatedSearchParamsBeneathResultsTableRequired = ! isNoSelectionEnquiry;
        m_isResultsScreenToolbarRequired = ! (m_isHeaderEnquiry || isNoToolbarEnquiry);
        m_isSearchFavouritesFunctionalityRequired = ! (isHeaderEnquiry() || isNoFavouritesEnquiry);
        m_isTreeEnquiry = p_enquiryFieldsDigest.includesStartTreeEndTreeFields();
        m_isShowAllResultsOnOnePage = isAllDataEnquiry || m_isHeaderEnquiry || m_isTreeEnquiry;
        m_useSingleBackgroundColourForResultsTable = isSingleBackgroundColour;
    }

    public BasicEnquiryProject getEnquiryProject()
    {
        return m_enquiryFieldsDigest.getEnquiryProject();
    }
    
    public boolean areResultsTableColumnHeadersSuppressed()
    {
        return m_areResultsTableColumnHeadersSuppresssed;
    }
    
    public boolean isAutoRunEnquiryOnLoad()
    {
        return m_isHeaderEnquiry;
    }
    
    public boolean isEnquiryWithDrilldowns()
    {
        return m_hasDrilldowns;
    }
    
    public boolean isSearchFavouritesFunctionalityRequired()
    {
        return m_isSearchFavouritesFunctionalityRequired;
    }
    
    public boolean isBreadCrumbTrailRequired()
    {
        return m_isBreadCrumbTrailRequired;
    }
    
    public boolean isHeaderEnquiry()
    {
        return m_isHeaderEnquiry;
    }
    
    public boolean isTreeEnquiry()
    {
        return m_isTreeEnquiry;
    }
    
    public boolean isReiterationOfPopulatedSearchParamsBeneathResultsTableEnabled()
    {
        return m_isReiterationOfPopulatedSearchParamsBeneathResultsTableRequired;
    }
    
    public boolean isResultsScreenToolbarRequired()
    {
        return m_isResultsScreenToolbarRequired;
    }

    public boolean isResultsTablePaginationRequiredFor(DisplayResultsTableWrapperPair p_displayResultsTableWrapperPair, IrisResultGroupWrapper p_irisResultGroupWrapper)
    {
        AssertionUtils.requireNonNull(p_displayResultsTableWrapperPair, "p_displayResultsTableWrapperPair");
        AssertionUtils.requireNonNull(p_irisResultGroupWrapper, "p_irisResultGroupWrapper");
        
        return (
            p_displayResultsTableWrapperPair.resultsTableType.isMainEnquiryResults() &&
            ! (m_isShowAllResultsOnOnePage || p_irisResultGroupWrapper.includesIrisBreakChangeItems())
        );
    }
    
    public boolean useSingleBackgroundColourForResultsTable()
    {
        return m_useSingleBackgroundColourForResultsTable;
    }
    
    public Enquiry getEnquiry()
    {
        return m_enquiry;
    }
}
