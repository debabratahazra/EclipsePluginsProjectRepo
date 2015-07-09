package com.odcgroup.edge.t24.generation.composite.singleifp.enquiry;

import gen.com.acquire.intelligentforms.entities.DataStructureWrapper;
import gen.com.acquire.intelligentforms.entities.FormListWrapper;
import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.ListItemWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.types.NumberType;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.composite.singleifp.BasicSingleIFPCompositeTemplateConstants;
import com.odcgroup.edge.t24.generation.composite.singleifp.GlobalContext;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.temenos.connect.enquiry.EnquiryResultConstants;

/**
 * An <code>EnquiryProcessorContext</code> is an immutable, self-populating aggregate acting as creator and repository for the subset of top-level elements within a composite IFP pertaining to
 * a specified {@link Enquiry}.<p>
 * 
 * Being derived from {@link GlobalContext}, it inherits knowledge of all of the non-enquiry specific IFP elements represented by the {@link GlobalContext} object supplied on
 * {@link #EnquiryProcessorContext(GlobalContext, Enquiry) construction}.<p>
 * 
 * Each instance is created by (and act as the "base context" for) an owning {@link EnquiryPanelProcessor} instance.<p>
 * 
 * This class has no expertise in traversing / interpreting the detail of the {@link Enquiry} object (that expertise is assumed to reside in the owning <code>EnquiryPanelProcessor</code>).<p>
 * 
 * The IFP elements created by an <code>EnquiryProcessorContext</code> on construction are therefore (deliberately) limited to those that can be created knowing only the {@link Enquiry#getName() name} of the
 * <code>Enquiry</code> for which the instance is created.<p>
 *
 * @author Simon Hayes
 */
public class EnquiryProcessorContext extends GlobalContext implements BasicSingleIFPCompositeTemplateConstants
{
	private final Enquiry m_enquiry;
	private final String m_ifpFriendlyEnquiryName;
	
	// Search request data structure
	
	private final DataStructureWrapper m_enquirySpecificSearchRequestStructure;
	
	// WorkingElements[1].<IfpFriendlyEnquiryName> and child items
	
	private final PropertyGroupWrapper m_enquirySpecificWorkingElementsSubGroup;
	private final PropertyWrapper m_displayedEnquiryItemGroupItem;
	private final PropertyWrapper m_fullIrisSearchUrlItem;
	private final PropertyWrapper m_searchOutcomeItem;
	private final PropertyWrapper m_finalResultInstanceItem;
	private final PropertyWrapper m_allDisplayableFinalResultRowValuesItem;
	private final PropertyWrapper m_resultsTableSelectorItem;
	
	// <IfpFriendlyEnquiryName>[1] and child SearchRequest & SearchResponse groups

	private final PropertyGroupWrapper m_enquirySpecificRequestResponseGroup;
	private final PropertyGroupWrapper m_enquirySearchRequestGroup;
	private final PropertyGroupWrapper m_enquirySearchResponseGroup;
	private final PropertyGroupWrapper m_enquirySearchResultGroup;
	private final PropertyWrapper m_isHeaderRowResultItem;
	private final PropertyWrapper m_enquiryResultRowActionItem;
	
	// CompositeScreen.Composite Screen.<IfpFriendlyEnquiryName> item group
	
	private final ItemGroupWrapper m_enquiryItemGroup;
	private final RichHTMLPresentationItemGroupWrapper m_enquiryPresItemGroup;
	
	// CompositeScreen.Composite Screen.<IfpFriendlyEnquiryName>.Search item group
	
	private final ItemGroupWrapper m_searchItemGroup;
	private final RichHTMLPresentationItemGroupWrapper m_searchPresItemGroup;
	
	// CompositeScreen.Composite Screen.<IfpFriendlyEnquiryName>.Search Results item group
	
	private final ItemGroupWrapper m_searchResultsItemGroup;
	private final RichHTMLPresentationItemGroupWrapper m_searchResultsPresItemGroup;
	
	/**
	 * Initializes a new instance, triggering the generation of all enquiry-specific elements made available via the query methods on this instance.
	 * 
	 * @param	p_globalContext		the {@link GlobalContext} representing the non enquiry / version specific parts of the composite IFP that we're adding to
	 * @param	p_enquiry			the {@link Enquiry} for which we're to add the elements to the composite IFP represented by <code>p_globalContext</code>
	 */
	public EnquiryProcessorContext(GlobalContext p_globalContext, Enquiry p_enquiry, EnquiryGenerationOptions p_genOptions)
	{
		super(AssertionUtils.requireNonNull(p_globalContext, "p_globalContext"));
	
		
		m_enquiry = AssertionUtils.requireNonNull(p_enquiry, "p_enquiry");
        m_ifpFriendlyEnquiryName = MapperUtility.processT24NameToIRISName(p_enquiry.getName());
        
        final FormContext formContext = getFormContext();

        // Create placeholder per-result actions list type
        
        final FormListWrapper perEnquiryResultActionsList = FormListWrapper.create(formContext, m_ifpFriendlyEnquiryName + " - ResultActionList");
        
        perEnquiryResultActionsList.addChild(
    		ListItemWrapper.create(
	        	formContext,
	        	Lists.EnquirySpecificDrilldownActionTemplate.PLACEHOLDER_LIST_ITEM.KEY,
	        	Lists.EnquirySpecificDrilldownActionTemplate.PLACEHOLDER_LIST_ITEM.VALUE,
	        	null
	        )
	    );
        
        getListsRoot().addChild(perEnquiryResultActionsList.unwrap());
        
        // Create placeholder enquiry-specific search request structure
        
        m_enquirySpecificSearchRequestStructure = DataStructureWrapper.create(formContext, m_ifpFriendlyEnquiryName + " - SearchRequestType");
        getStructuresRoot().addChild(m_enquirySpecificSearchRequestStructure.unwrap());
        
        // Create the enquiry-specific root group and its child Search and SearchResponse groups...
        
        m_enquirySpecificRequestResponseGroup = PropertyGroupWrapper.create(formContext, m_ifpFriendlyEnquiryName);
        getDictionaryRoot().addChild(m_enquirySpecificRequestResponseGroup);
        
        m_enquirySearchRequestGroup = PropertyGroupWrapper.create(formContext, DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.NAME, Boolean.TRUE, null, Boolean.TRUE, m_enquirySpecificSearchRequestStructure);
        m_enquirySpecificRequestResponseGroup.addChild(m_enquirySearchRequestGroup);
        
        m_enquirySearchResponseGroup = PropertyGroupWrapper.create(formContext, DataStore.EnquiryRequestResponseGroupTemplate.SearchResponseGroup.NAME);
        m_enquirySpecificRequestResponseGroup.addChild(m_enquirySearchResponseGroup);
        
        m_enquirySearchResultGroup = PropertyGroupWrapper.create(formContext, DataStore.EnquiryRequestResponseGroupTemplate.SearchResponseGroup.SearchResultGroup.NAME, Boolean.FALSE, null, Boolean.FALSE, null);
        m_enquirySearchResponseGroup.addChild(m_enquirySearchResultGroup);
        
        m_isHeaderRowResultItem = PropertyWrapper.create(formContext, "IsHeaderRow");
        m_isHeaderRowResultItem.setType(getTrueFalseListType().getFullName());
        m_isHeaderRowResultItem.setAttribute(EnquiryResultConstants.CustomAttrNames.IS_DATA_FOR_EXPORT, "false"); //!!TODO: Don't think this is done in BasicEnquiryProject !
        m_enquirySearchResultGroup.addChild(m_isHeaderRowResultItem);
        
        m_enquiryResultRowActionItem = PropertyWrapper.create(formContext, "Action");
        m_enquiryResultRowActionItem.setType(perEnquiryResultActionsList.getFullName());
        m_enquiryResultRowActionItem.setAttribute(EnquiryResultConstants.CustomAttrNames.IS_DATA_FOR_EXPORT, "false");
        m_enquirySearchResultGroup.addChild(m_enquiryResultRowActionItem);
        
        // Create and populate the WorkingElements[1].<IfpFriendlyEnquiryName> group
        
        m_enquirySpecificWorkingElementsSubGroup = PropertyGroupWrapper.create(formContext, m_ifpFriendlyEnquiryName);
        getWorkingElementsGroup().addChild(m_enquirySpecificWorkingElementsSubGroup);

        m_displayedEnquiryItemGroupItem = PropertyWrapper.create(formContext, DataStore.WorkingElementsGroup.EnquirySpecificSubGroupTemplate.ItemNames.DISPLAYED_ENQUIRY_ITEM_GROUP);
        m_displayedEnquiryItemGroupItem.setType(getDisplayedEnquiryItemGroupListType().getFullName());
        m_enquirySpecificWorkingElementsSubGroup.addChild(m_displayedEnquiryItemGroupItem);
        
        m_fullIrisSearchUrlItem = PropertyWrapper.create(formContext, DataStore.WorkingElementsGroup.EnquirySpecificSubGroupTemplate.ItemNames.FULL_IRIS_SEARCH_URL);
        m_enquirySpecificWorkingElementsSubGroup.addChild(m_fullIrisSearchUrlItem);
        
        m_searchOutcomeItem = PropertyWrapper.create(formContext, DataStore.WorkingElementsGroup.EnquirySpecificSubGroupTemplate.ItemNames.SEARCH_OUTCOME);
        m_searchOutcomeItem.setType(getSearchOutcomeListType().getFullName());
        m_enquirySpecificWorkingElementsSubGroup.addChild(m_searchOutcomeItem);
        
        m_finalResultInstanceItem = PropertyWrapper.create(formContext, DataStore.WorkingElementsGroup.EnquirySpecificSubGroupTemplate.ItemNames.FINAL_RESULT_INSTANCE);
        m_finalResultInstanceItem.setType(NumberType.TYPE);
        m_enquirySpecificWorkingElementsSubGroup.addChild(m_finalResultInstanceItem);
        
        m_allDisplayableFinalResultRowValuesItem = PropertyWrapper.create(formContext, DataStore.WorkingElementsGroup.EnquirySpecificSubGroupTemplate.ItemNames.ALL_DISPLAYABLE_FINAL_RESULT_ROW_VALUES);
        m_enquirySpecificWorkingElementsSubGroup.addChild(m_allDisplayableFinalResultRowValuesItem);
        
        m_resultsTableSelectorItem = PropertyWrapper.create(formContext, DataStore.WorkingElementsGroup.EnquirySpecificSubGroupTemplate.ItemNames.RESULTS_TABLE_SELECTOR);
        m_resultsTableSelectorItem.setType(NumberType.TYPE);
        m_enquirySpecificWorkingElementsSubGroup.addChild(m_resultsTableSelectorItem);
        
        // Create the <IfpFriendlyEnquiryName> item group under the CompositeScreen phase (process and presentation)
        
        m_enquiryItemGroup = ItemGroupWrapper.create(formContext, m_ifpFriendlyEnquiryName);
        p_globalContext.getCompositeScreenPhase().addChild(m_enquiryItemGroup);
        
        m_enquiryPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(formContext, m_enquiryItemGroup.unwrap());
        p_globalContext.getCompositeScreenPresPhase().addChild(m_enquiryPresItemGroup);
        
        m_searchItemGroup = ItemGroupWrapper.create(formContext, "Search");
        m_enquiryItemGroup.addChild(m_searchItemGroup);
        m_searchItemGroup.setNotApplicable(Boolean.TRUE);
        m_searchItemGroup.setConditionExpression("$$" + m_displayedEnquiryItemGroupItem.getEntityKeyName() + "$ == '" + Lists.DisplayedEnquiryItemGroup.Keys.SEARCH + "'");
        
        m_searchPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(formContext, m_searchItemGroup.unwrap());
        m_enquiryPresItemGroup.addChild(m_searchPresItemGroup);

        m_searchResultsItemGroup = ItemGroupWrapper.create(formContext, "Search Results");
        m_enquiryItemGroup.addChild(m_searchResultsItemGroup.unwrap());
        m_searchResultsItemGroup.setNotApplicable(Boolean.TRUE);
        m_searchResultsItemGroup.setConditionExpression("$$" + m_displayedEnquiryItemGroupItem.getEntityKeyName() + "$ == '" + Lists.DisplayedEnquiryItemGroup.Keys.SEARCH_RESULTS + "'");
        
        m_searchResultsPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(formContext, m_searchResultsItemGroup.unwrap());
        m_enquiryPresItemGroup.addChild(m_searchResultsPresItemGroup);
	}
	
	/**
	 * @return the {@link Enquiry} supplied on {@link #EnquiryProcessorContext(GlobalContext, Enquiry) construction}
	 */
	public Enquiry getEnquiry()
	{
		return m_enquiry;
	}
	
	/**
	 * @return the IRIS-style (CamelCase) equivalent of the T24 enquiry {@link Enquiry#getName() name}<p>
	 * @see	   #getIrisEnquiryResourceName()
	 */
	public String getIfpFriendlyEnquiryName()
	{
		return m_ifpFriendlyEnquiryName;
	}

	/**
	 * @return the IRIS resource name for {@link #getEnquiry()} - i.e. <code>{@link #getIfpFriendlyEnquiryName()} + 's'</code>
	 */
	public String getIrisEnquiryResourceName()
	{
		return m_ifpFriendlyEnquiryName + 's';
	}

	/**
	 * @return the per-enquiry sub-group of <code>WorkingElements[1]</code> created for {@link #getEnquiry()}
	 */
	public PropertyGroupWrapper getEnquirySpecificWorkingElementsSubGroup()
	{
		return m_enquirySpecificWorkingElementsSubGroup;
	}
	
	/**
	 * @return the data item used to hold the final result instance as part of the enquiry results post-processing to deal with header/footer results rows
	 */
	public PropertyWrapper getFinalResultInstanceItem()
	{
		return m_finalResultInstanceItem;
	}
	
	/**
	 * @return	the data item used to hold the concatenated results-table-displayable values for the final enquiry result returned by IRIS as part of the enquiry
	 * 			results post-processing to deal with header/footer results rows
	 */
	public PropertyWrapper getAllDisplayableFinalResultRowValuesItem()
	{
		return m_allDisplayableFinalResultRowValuesItem;
	}
	
	/**
	 * @return the "selector" data item for the "Enquiry Results" table <i>(created externally to this class)</i> for {@link #getEnquiry()}  
	 */
	public PropertyWrapper getResultsTableSelectorItem()
	{
		return m_resultsTableSelectorItem;
	}
	
	/**
	 * @return the list-typed data item (within the multi-instance {@link #getEnquirySearchResultGroup() search-result group}) for the per-results-row "Action" list question for {@link #getEnquiry()}
	 */
	public PropertyWrapper getResultRowActionItem()
	{
		return m_enquiryResultRowActionItem;
	}
	
	/**
	 * @return the root-level group containing the single-instance enquiry-specific search-request and search-response groups for {@link #getEnquiry()} 
	 */
	public PropertyGroupWrapper getEnquirySpecificRequestResponseGroup()
	{
		return m_enquirySpecificRequestResponseGroup;
	}

	/**
	 * @return the (initially empty) data structure that will define the search request structure for {@link #getEnquiry()}
	 */
	public DataStructureWrapper getEnquirySpecificSearchRequestStructure()
	{
		return m_enquirySpecificSearchRequestStructure;
	}
	
	/**
	 * @return the single-instance <code>SearchRequest</code> child group within {@link #getEnquirySpecificRequestResponseGroup()}
	 */
	public PropertyGroupWrapper getEnquirySearchRequestGroup()
	{
		return m_enquirySearchRequestGroup;
	}
	
	/**
	 * @return the single-instance <code>SearchResponse</code> child group within {@link #getEnquirySpecificRequestResponseGroup()}
	 */
	public PropertyGroupWrapper getEnquirySearchResponseGroup()
	{
		return m_enquirySearchResponseGroup;
	}
	
	/**
	 * @return the multi-instance <code>SearchResult</code> child group within {@link #getEnquirySearchResponseGroup()}
	 */
	public PropertyGroupWrapper getEnquirySearchResultGroup()
	{
		return m_enquirySearchResultGroup;
	}
	
	/**
	 * @return the <code>SearchOutcome</code> item under {@link #getEnquirySpecificWorkingElementsSubGroup()}
	 */
	public PropertyWrapper getSearchOutcomeItem()
	{
		return m_searchOutcomeItem;
	}
	
	/**
	 * @return the <code>DisplayedEnquiryItemGroup</code> item under {@link #getEnquirySpecificWorkingElementsSubGroup()}
	 */
	public PropertyWrapper getDisplayedEnquiryItemGroupItem()
	{
		return m_displayedEnquiryItemGroupItem;
	}
	
	public PropertyWrapper getFullIrisSearchUrlItem()
	{
		return m_fullIrisSearchUrlItem;
	}
	
	public ItemGroupWrapper getEnquiryItemGroup()
	{
		return m_enquiryItemGroup;
	}
	
	public RichHTMLPresentationItemGroupWrapper getEnquiryPresItemGroup()
	{
		return m_enquiryPresItemGroup;
	}
	
	/**
	 * @return the "Search" process item group for {@link #getEnquiry()}
	 */
	public ItemGroupWrapper getSearchItemGroup()
	{
		return m_searchItemGroup;
	}
	
	/**
	 * @return the presentation analog for {@link #getSearchItemGroup()}
	 */
	public RichHTMLPresentationItemGroupWrapper getSearchPresItemGroup()
	{
		return m_searchPresItemGroup;
	}

	/**
	 * @return the "Search Results" process item group for {@link #getEnquiry()}
	 */
	public ItemGroupWrapper getSearchResultsItemGroup()
	{
		return m_searchResultsItemGroup;
	}
	
	/**
	 * @return the presentation analog for {@link #getSearchResultsItemGroup()}
	 */
	public RichHTMLPresentationItemGroupWrapper getSearchResultsPresItemGroup()
	{
		return m_searchResultsPresItemGroup;
	}
}
