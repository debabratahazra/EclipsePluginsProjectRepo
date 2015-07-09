package com.odcgroup.edge.t24.generation.composite.singleifp.enquiry;

import gen.com.acquire.intelligentforms.entities.DataStructureWrapper;
import gen.com.acquire.intelligentforms.entities.FormButtonWrapper;
import gen.com.acquire.intelligentforms.entities.FormListWrapper;
import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.HeadingWrapper;
import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.ListItemWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationButtonWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;
import gen.com.acquire.intelligentforms.rules.EvaluateRuleWrapper;
import gen.com.acquire.intelligentforms.rules.IncrementorRuleWrapper;
import gen.com.acquire.intelligentforms.rules.ResetDataRuleWrapper;
import gen.com.acquire.intelligentforms.rules.SetValueRuleWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.slf4j.Logger;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.FormList;
import com.acquire.intelligentforms.entities.ItemGroup;
import com.acquire.intelligentforms.entities.meta.ErrorHandlingItem;
import com.acquire.util.AssertionUtils;
import com.acquire.util.IntegerFactory;
import com.acquire.util.StringUtils;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.odcgroup.edge.t24.generation.composite.singleifp.BasicSingleIFPCompositeTemplateConstants;
import com.odcgroup.edge.t24.generation.composite.singleifp.GlobalContext;
import com.odcgroup.edge.t24.generation.enquiry.EdgeConnectDataType;
import com.odcgroup.edge.t24.generation.enquiry.core.EnquiryFieldsDigest;
import com.odcgroup.edge.t24.generation.enquiry.core.EnquiryFilterParamDef;
import com.odcgroup.edge.t24.generation.enquiry.core.EnquiryResultsHeaderLabel;
import com.odcgroup.edge.t24.generation.enquiry.core.EnquiryResultsHeaderValue;
import com.odcgroup.edge.t24.generation.enquiry.core.EnquiryResultsTableField;
import com.odcgroup.edge.t24.generation.enquiry.core.EnquirySelectionDigest;
import com.odcgroup.edge.t24.generation.enquiry.core.ResultsHeaderFooterCellSpec;
import com.odcgroup.edge.t24.generation.enquiry.core.ResultsHeaderFooterRowSpec;
import com.odcgroup.edge.t24.generation.enquiry.core.SearchParamComparisonOp;
import com.odcgroup.edge.t24.generation.enquiry.core.SearchParamDropdownInfo;
import com.odcgroup.edge.t24.generation.enquiry.layout.DynamicHeaderValueElement;
import com.odcgroup.edge.t24.generation.enquiry.layout.RichHTMLEnquiryHeaderLayoutIterationListener;
import com.odcgroup.edge.t24.generation.enquiry.layout.RichHTMLEnquiryLayoutHelper;
import com.odcgroup.edge.t24.generation.enquiry.layout.StaticHeaderLabelElement;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.util.RichHelper;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.edge.t24ui.cos.bespoke.EnquiryPanel;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.temenos.connect.enquiry.EdgeListEntryDef;
import com.temenos.connect.enquiry.EdgeODataComparisonOpDef;
import com.temenos.connect.enquiry.EnquiryResultConstants;
import com.temenos.connect.enquiry.EnquirySearchUrlBuilderRule;
import com.temenos.connect.odata.IRISRequest;

/**
 * An <code>EnquiryPanelProcessor</code> is a single-use, throwaway "strategy" object for appending all of the the per-enquiry IFP elements for the {@link Enquiry} referenced by a
 * specified {@link EnquiryPanel} to an object model of a (non component-based) composite IFP.<p>
 * 
 * In outline the approach involves creating a {@link EnquiryProcessorContext} representing the top-level enquiry-specific IFP elements, creating a {@link EnquiryFieldsDigest} from
 * which per-enquiry-field information can be conveniently garnered, and then using the latter to add the field-dependent IFP sub-elements to the top-level elements established by the former. 
 *
 * @author Simon Hayes
 */
public class EnquiryPanelProcessor implements BasicSingleIFPCompositeTemplateConstants
{
    private static final Logger LOGGER = GenLogger.getLogger(EnquiryPanelProcessor.class);
    
    private final Enquiry m_enquiry;
    private final boolean m_isFirstPanel;
    private final GlobalContext m_globalContext;
    private final FormContext m_formContext;
    private final EnquiryGenerationOptions m_genOptions;
    
	private FormListWrapper m_allSearchParamComparisonOperatorsList; // managed by findOrCreateAllSearchParamComparisonOperatorsList()
	private TreeMap<Integer,QuestionWrapper> m_resultsTableQuestionByT24ColumnNumber; // created/populated by processEnquiryResultsTableFields()

	public EnquiryPanelProcessor(EnquiryPanel p_enquiryPanel, boolean p_isFirstPanel, GlobalContext p_globalContext)
    {
    	m_enquiry = AssertionUtils.requireNonNull(p_enquiryPanel, "p_enquiryPanel").getEnquiry();
    	m_isFirstPanel = p_isFirstPanel;
    	m_globalContext = AssertionUtils.requireNonNull(p_globalContext, "p_globalContext");
    	m_formContext = m_globalContext.getFormContext();
    	m_genOptions = new EnquiryGenerationOptions(SearchGenerationOption.NONE); //!! TEMP
    	// m_genOptions = new EnquiryGenerationOptions(SearchGenerationOption.BASIC); //!! TEMP
    	// m_genOptions = new EnquiryGenerationOptions(SearchGenerationOption.INCLUDE_FILTER_PARAMS); //!! TEMP
    	// m_genOptions = new EnquiryGenerationOptions(SearchGenerationOption.INCLUDE_FILTER_PARAMS_AND_SORT_OPTIONS); //!! TEMP
    	
    	AssertionUtils.requireConditionTrue((m_enquiry != null), "p_enquiryPanel.getEnquiry() != null");
    }

    public void go() throws Exception
	{
		LOGGER.info("Referenced enquiry: " + m_enquiry.getName());
		
		final EnquiryProcessorContext enquiryProcessorContext = new EnquiryProcessorContext(m_globalContext, m_enquiry, m_genOptions);
		final EnquiryFieldsDigest enquiryFieldsDigest = new EnquiryFieldsDigest(m_globalContext, m_enquiry);
		final EnquirySelectionDigest enquirySelectionDigest = new EnquirySelectionDigest(m_globalContext, m_enquiry, enquiryFieldsDigest);
		
		if (! m_isFirstPanel)
			enquiryProcessorContext.getSearchResultsPresItemGroup().addChild(makePresSpacingWithBankLines(2));
		
		addStartPhaseRuleToMakeAppropriateEnquirySubItemGroupInitiallyVisible(enquiryProcessorContext);

		processEnquiryResultsHeaders(enquiryFieldsDigest, enquiryProcessorContext);
		
		if (m_genOptions.searchGenerationOption.includeSearchItemGroup())
			addSelectionScreenButtonToSearchResultsItemGroup(enquiryProcessorContext);
		
		processEnquiryResultsHeaderRowFields(enquiryFieldsDigest, enquiryProcessorContext);
		processEnquiryResultsTableFields(enquiryFieldsDigest, enquiryProcessorContext);
		processEnquiryResultsFooterRowFields(enquiryFieldsDigest, enquiryProcessorContext);

		addApplicableSearchElements(enquirySelectionDigest, enquiryFieldsDigest, enquiryProcessorContext);
	}

    private void addStartPhaseRuleToMakeAppropriateEnquirySubItemGroupInitiallyVisible(EnquiryProcessorContext p_context)
    {
    	final PropertyWrapper displayedEnquiryItemGroupItem = p_context.getDisplayedEnquiryItemGroupItem();
    	final boolean showSearchItemGroup = m_isFirstPanel && m_genOptions.getSearchGenerationOption().includeSearchItemGroup();
    	final String listKeyValueToSet = showSearchItemGroup ? Lists.DisplayedEnquiryItemGroup.Keys.SEARCH : Lists.DisplayedEnquiryItemGroup.Keys.SEARCH_RESULTS;
    	final ItemGroupWrapper targetEnquirySubItemGroup = showSearchItemGroup ? p_context.getSearchItemGroup() : p_context.getSearchResultsItemGroup();
    	final String targetItemGroupName = (String) targetEnquirySubItemGroup.unwrap().getAttribute(ItemGroup.ATTR_ITEM_GROUP_NAME);
    	
    	final SetValueRuleWrapper setEnquirySubItemGroupVisibleRule = SetValueRuleWrapper.create(p_context.getFormContext());
    	setEnquirySubItemGroupVisibleRule.setName("Set " + targetItemGroupName + " item group initially visible for " + p_context.getIfpFriendlyEnquiryName() + " enquiry");
    	setEnquirySubItemGroupVisibleRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	setEnquirySubItemGroupVisibleRule.setPropertyName(displayedEnquiryItemGroupItem);
    	setEnquirySubItemGroupVisibleRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
    	setEnquirySubItemGroupVisibleRule.setFromValue(listKeyValueToSet);
    	setEnquirySubItemGroupVisibleRule.setFromValueList(listKeyValueToSet);
    	
    	p_context.getCompositeScreenStartPhase().addPostPhaseRule(setEnquirySubItemGroupVisibleRule.unwrap());
    }
    
    private void processEnquiryResultsHeaders(EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
		final RichHTMLEnquiryLayoutHelper layoutHelper = new RichHTMLEnquiryLayoutHelper();
		
		registerEnquiryResultsHeaderLabels(p_fieldsDigest.getEnqResultsHeaderLabels(), layoutHelper);
		registerEnquiryResultsHeaderValuesAndCreateAssocDataItems(p_fieldsDigest.getEnqResultsHeaderValues(), layoutHelper, p_context);
		
    	final RichHTMLEnquiryHeaderLayoutIterationListener layoutIterationListener = new RichHTMLEnquiryHeaderLayoutIterationListener(p_context.getSearchResultsItemGroup()); 
    	layoutHelper.iterateHeaderItems(layoutIterationListener, p_context.getSearchResultsPresItemGroup());
    	
    	p_context.getSearchResultsPresItemGroup().addChild(makePresSpacingWithBankLine());
    }
    
    private void addApplicableSearchElements(EnquirySelectionDigest p_selectionDigest, EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
    	final SearchGenerationOption searchGenOption = m_genOptions.searchGenerationOption;
    	
    	if (searchGenOption.includeSearchItemGroup())
    	{
    		addNoResultsFoundHeading(p_context.getSearchItemGroup(), p_context.getSearchPresItemGroup(), p_fieldsDigest, p_context);
    		
	    	if (m_genOptions.searchGenerationOption.includeSearchFilters())
	    		addAdvancedSearchElements(p_selectionDigest, p_fieldsDigest, p_context);
	    	
	    	else
	    		addSimpleParameterlessSearchElements(p_fieldsDigest, p_context);
    	}
    }
    
    private void addAdvancedSearchElements(EnquirySelectionDigest p_selectionDigest, EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
    	addAdvancedSearchDataElements(p_selectionDigest, p_context);
    	addAdvancedSearchScreenElements(p_selectionDigest, p_fieldsDigest, p_context);
    }
    
    private void addAdvancedSearchDataElements(EnquirySelectionDigest p_selectionDigest, EnquiryProcessorContext p_context)
    {
    	final DataStructureWrapper enquirySpecificSearchRequestStructure = p_context.getEnquirySpecificSearchRequestStructure();
    	final PropertyGroupWrapper filtersSubGroup = PropertyGroupWrapper.create(m_formContext, Structures.SearchRequestType.FiltersGroup.NAME);
    	enquirySpecificSearchRequestStructure.addChild(filtersSubGroup);
    	
    	final EnquiryFilterParamDef[] filterParamDefs = p_selectionDigest.getFilterParamDefs();
    	final int numFilterParamDefs = (filterParamDefs == null) ? 0 : filterParamDefs.length;
    	final FormListWrapper enqFilterFieldsList = m_genOptions.searchGenerationOption.includeSortOptions() ? FormListWrapper.create(m_formContext, p_context.getIfpFriendlyEnquiryName() + " - AllFilterFields") : null;
    	
    	for (int i = 0; i < numFilterParamDefs; ++i)
    	{
    		final EnquiryFilterParamDef filterParamDef = filterParamDefs[i];
    		final EdgeConnectDataType edgeConnectDataType = filterParamDef.getEdgeConnectDataType();
    		
    		final PropertyGroupWrapper filterParamSubGroup = PropertyGroupWrapper.create(m_formContext, filterParamDef.getODataFieldName());
    		filtersSubGroup.addChild(filterParamSubGroup);
    		
    		addSearchParamCompareOpItem(filterParamDef, filterParamSubGroup, p_context);
    		addSearchParamOperandItem(DataStore.EnquiryFilterParamGroupTemplate.OPERAND1_ITEM_NAME, edgeConnectDataType, filterParamSubGroup);
    		addSearchParamOperandItem(DataStore.EnquiryFilterParamGroupTemplate.OPERAND2_ITEM_NAME, edgeConnectDataType, filterParamSubGroup);
    		
    		if (enqFilterFieldsList != null)
    		{
    			final ListItemWrapper filterFieldListItem = ListItemWrapper.create(m_formContext, filterParamDef.getODataFieldName(), filterParamDef.getAppFieldName(), null);
    			enqFilterFieldsList.addChild(filterFieldListItem);
    		}
    	}
    	
    	if (enqFilterFieldsList != null)
    	{
    		m_globalContext.getListsRoot().addChild(enqFilterFieldsList.unwrap());

    		// Create the ColumnSortSpecType data structure...
    		
    		final DataStructureWrapper enqColumnSortSpecStructure = DataStructureWrapper.create(m_formContext, p_context.getIfpFriendlyEnquiryName() + " - ColumnSortSpecType");
    		m_globalContext.getStructuresRoot().addChild(enqColumnSortSpecStructure.unwrap());

    		final PropertyWrapper fieldNameItem = PropertyWrapper.create(m_formContext, Structures.ColumnSortSpecType.ItemNames.FIELD_NAME);
    		fieldNameItem.setType(enqFilterFieldsList.getEntityKeyName());
    		enqColumnSortSpecStructure.addChild(fieldNameItem);
    		
    		final PropertyWrapper sortOrderItem = PropertyWrapper.create(m_formContext, Structures.ColumnSortSpecType.ItemNames.SORT_ORDER);
    		sortOrderItem.setType(p_context.getSortOrderListType().getEntityKeyName());
    		enqColumnSortSpecStructure.addChild(sortOrderItem);

    		// Create the OrderBy group and add it to our enquiry-specific search request structure...
    		
    		final PropertyGroupWrapper orderByGroup = PropertyGroupWrapper.create(m_formContext, Structures.SearchRequestType.OrderByGroup.NAME);
    		enquirySpecificSearchRequestStructure.addChild(orderByGroup);
    		
    		// Add multi-instance ColumnSortSpec child group to orderByGroup (as link to enqColumnSortSpecStructure with max 3 instances)...
    		
    		final PropertyGroupWrapper columnSortSpecGroup = PropertyGroupWrapper.create(
    			m_formContext,
    			Structures.SearchRequestType.OrderByGroup.ColumnSortSpecGroup.NAME,
    			Boolean.TRUE, /* p_fixedSize */
    			new Integer(3), /* p_maxInstances */
    			Boolean.TRUE, /* p_linkToStructure */
    			enqColumnSortSpecStructure
    		);
    		
    		orderByGroup.addChild(columnSortSpecGroup);
    	}
    }
    
    private void addSimpleParameterlessSearchElements(EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
    	final RichHTMLPresentationItemGroupWrapper searchPresItemGroup = p_context.getSearchPresItemGroup();
    	
		searchPresItemGroup.addChild(makePresSpacingWithHorizontalLine());
		addEnquiryDescriptionElems(p_context.getSearchItemGroup(), p_context.getSearchPresItemGroup(), p_context);
		final FormButtonWrapper findButton = addFindButton(p_context.getSearchItemGroup(), p_context.getSearchPresItemGroup());
    	addBasicSearchButtonRules(findButton, p_fieldsDigest, p_context);
    	searchPresItemGroup.addChild(makePresSpacingWithHorizontalLine());
    }
    
    private void addAdvancedSearchScreenElements(EnquirySelectionDigest p_selectionDigest, EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
		final RichHTMLPresentationColumnBreakWrapper searchActionsAndParamsPresColumn = makePresentationColumnBreak("Enquiry Search Actions and Parameters Column");
		p_context.getSearchPresItemGroup().addChild(searchActionsAndParamsPresColumn);

		final FormButtonWrapper findButton = addAndPopulateSearchActionsItemGroup(searchActionsAndParamsPresColumn, p_selectionDigest, p_fieldsDigest, p_context);
		addAndPopulateSearchParamsItemGroup(p_selectionDigest, searchActionsAndParamsPresColumn, findButton, p_context);
    }
    
    private FormButtonWrapper addAndPopulateSearchActionsItemGroup(
    	RichHTMLPresentationColumnBreakWrapper p_searchActionsAndParamsColumn,
    	EnquirySelectionDigest p_selectionDigest,
    	EnquiryFieldsDigest p_fieldsDigest,
    	EnquiryProcessorContext p_context
    ) {
		final ItemGroupWrapper searchActionsItemGroup = ItemGroupWrapper.create(m_formContext, "Search Actions");
		p_context.getSearchItemGroup().addChild(searchActionsItemGroup);
    	
		final RichHTMLPresentationFormatBreakWrapper searchActionsPresSection = RichHTMLPresentationFormatBreakWrapper.create(m_formContext);
		searchActionsPresSection.setDesignToUseFromEntityKey("Enquiry Search Actions Section");
		p_searchActionsAndParamsColumn.addChild(searchActionsPresSection);
		
		final RichHTMLPresentationItemGroupWrapper searchActionsPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(m_formContext, searchActionsItemGroup.unwrap());
		searchActionsPresSection.addChild(searchActionsPresItemGroup.unwrap());
		
		searchActionsPresItemGroup.addChild(makePresSpacingWithHorizontalLine());

    	addEnquiryDescriptionElems(searchActionsItemGroup, searchActionsPresItemGroup, p_context);
		addEnquiryOptionsAndClearButtonElems(searchActionsItemGroup, searchActionsPresItemGroup, p_context);
		final FormButtonWrapper findButton = addFindButton(searchActionsItemGroup, searchActionsPresItemGroup);
		addAdvancedSearchFindButtonRules(findButton, p_selectionDigest, p_fieldsDigest, p_context);
		searchActionsPresItemGroup.addChild(makePresSpacingWithHorizontalLine());
		
		if (m_genOptions.searchGenerationOption.includeSortOptions())
			addAndPopulateSortOptionsItemGroup(searchActionsPresSection, p_context);
		
		return findButton;
    }

    private void addAndPopulateSortOptionsItemGroup(RichHTMLPresentationFormatBreakWrapper p_searchActionsPresSection, EnquiryProcessorContext p_context)
    {
		final ItemGroupWrapper sortOptionsItemGroup = ItemGroupWrapper.create(m_formContext, "Sort Options");
		p_context.getSearchItemGroup().addChild(sortOptionsItemGroup);
		
		final String enquirySpecificWorkingElementsSubGroupPath = p_context.getEnquirySpecificWorkingElementsSubGroup().getEntityKeyName(); 
		final String sortOptionsHideShowStateItemPath =	enquirySpecificWorkingElementsSubGroupPath + '.' + DataStore.WorkingElementsGroup.EnquirySpecificSubGroupTemplate.ItemNames.SORT_OPTIONS_HIDE_SHOW_STATE;
		
		final RichHTMLPresentationFormatBreakWrapper sortOptionsPresSection = RichHTMLPresentationFormatBreakWrapper.create(m_formContext);
		sortOptionsPresSection.setHideField(Boolean.TRUE);
		sortOptionsPresSection.setConditionExpression("$$" + sortOptionsHideShowStateItemPath + "$ == 'SHOW'");
		sortOptionsPresSection.setDesignToUseFromEntityKey("Enquiry Sort Options Section");
		p_searchActionsPresSection.addChild(sortOptionsPresSection);

		final RichHTMLPresentationItemGroupWrapper sortOptionsPresItemGroup =  RichHTMLPresentationItemGroupWrapper.create(m_formContext, sortOptionsItemGroup.unwrap());
		sortOptionsPresSection.addChild(sortOptionsPresItemGroup);
		
		final String baseColumnSortSpecGroupPath = (
			p_context.getEnquirySearchRequestGroup().getEntityKeyName() + '.' +
			DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.OrderByGroup.NAME + "[1]." +
			DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.NAME + "["
		);

		for (int i = 1; i <= 3; ++i)
		{
			final String
				columnSortByGroupPath = baseColumnSortSpecGroupPath + i + "]",
				fieldNameItemPath = columnSortByGroupPath + '.' + DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.ItemNames.FIELD_NAME,
				sortOrderItemPath = columnSortByGroupPath + '.' + DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.ItemNames.SORT_ORDER;
			
			// (a) Sort option process item group & questions
			
			final ItemGroupWrapper columnSortSpecItemGroup = ItemGroupWrapper.create(m_formContext, "Column Sort " + i);
			sortOptionsItemGroup.addChild(columnSortSpecItemGroup);
			
			final QuestionWrapper sortFieldNameQuestion = QuestionWrapper.create(m_formContext);
			sortFieldNameQuestion.setQuestionText((i == 1) ? "$%SLANG Enquiry.SortByLabel Sort By:$" : "$%SLANG Enquiry.ThenByLabel Then By:$");
			sortFieldNameQuestion.setMandatory(Boolean.FALSE);
			sortFieldNameQuestion.setPropertyKeyFromEntityKey(fieldNameItemPath);
			columnSortSpecItemGroup.addChild(sortFieldNameQuestion);
			
			final QuestionWrapper sortOrderQuestion = QuestionWrapper.create(m_formContext);
			sortOrderQuestion.setQuestionText("$%SLANG Enquiry.SortOrderLabel [Sort Order]$");
			sortOrderQuestion.setMandatory(Boolean.FALSE);
			sortOrderQuestion.setPropertyKeyFromEntityKey(sortOrderItemPath);
			columnSortSpecItemGroup.addChild(sortOrderQuestion);

			// (b) Sort option presentation item group & questions
			
			final RichHTMLPresentationItemGroupWrapper columnSortSpecPresItemGroup =  RichHTMLPresentationItemGroupWrapper.create(m_formContext, columnSortSpecItemGroup.unwrap());
			sortOptionsPresItemGroup.addChild(columnSortSpecPresItemGroup);
			
			final RichHTMLPresentationQuestionWrapper sortFieldNamePresQuestion = RichHTMLPresentationQuestionWrapper.create(m_formContext, sortFieldNameQuestion.unwrap());
			sortFieldNamePresQuestion.setDisplayType("Drop down list");
			sortFieldNamePresQuestion.setDesignToUseForDisplayTypeFromEntityKey("Enquiry Sort By Field Answer");
			columnSortSpecPresItemGroup.addChild(sortFieldNamePresQuestion);
			
			final RichHTMLPresentationQuestionWrapper sortOrderPresQuestion = RichHTMLPresentationQuestionWrapper.create(m_formContext, sortOrderQuestion.unwrap());
			sortOrderPresQuestion.setHideQuestion(Boolean.TRUE);
			sortOrderPresQuestion.setQuestionNewLine(Boolean.FALSE);
			sortOrderPresQuestion.setDisplayType("Radio Button");
			sortOrderPresQuestion.setDefaultListValue( "Ascending" );
			columnSortSpecPresItemGroup.addChild(sortOrderPresQuestion);
		}
		
		sortOptionsPresSection.addChild(makePresSpacingWithHorizontalLine("EnquirySearchScreenWidth"));
    }
    
    private void addAndPopulateSearchParamsItemGroup(
    	EnquirySelectionDigest p_selectionDigest,
    	RichHTMLPresentationColumnBreakWrapper p_searchActionsAndParamsColumn,
    	FormButtonWrapper p_findButton,
    	EnquiryProcessorContext p_context
    ) {
		final ItemGroupWrapper searchParamsItemGroup = ItemGroupWrapper.create(m_formContext, "Search Parameters");
		p_context.getSearchItemGroup().addChild(searchParamsItemGroup);
		
		final RichHTMLPresentationFormatBreakWrapper searchParamsPresSection = RichHTMLPresentationFormatBreakWrapper.create(m_formContext);
		searchParamsPresSection.setDesignToUseFromEntityKey("Enquiry Search Params Section");
		searchParamsPresSection.setDisplayType("Standard section");
		searchParamsPresSection.setDefaultButton(p_findButton.unwrap().getActionCommand());
		searchParamsPresSection.setDefaultButtonName(p_findButton.getEntityKeyName());
		p_searchActionsAndParamsColumn.addChild(searchParamsPresSection);

		final RichHTMLPresentationItemGroupWrapper searchParamsPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(m_formContext, searchParamsItemGroup.unwrap());
		searchParamsPresSection.addChild(searchParamsPresItemGroup);
		
    	final EnquiryFilterParamDef[] filterParamDefs = p_selectionDigest.getFilterParamDefs();
    	final int numFilterParamDefs = (filterParamDefs == null) ? 0 : filterParamDefs.length;
    	final String searchRequestFiltersGroupPath = p_context.getEnquirySearchRequestGroup().getEntityKeyName() + '.' + DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.FiltersGroup.NAME + "[1]";
    	
    	for (int i = 0; i < numFilterParamDefs; ++i)
    	{
    		final EnquiryFilterParamDef filterParamDef = filterParamDefs[i];
    		
    		final ItemGroupWrapper filterParamItemGroup = ItemGroupWrapper.create(m_formContext, filterParamDef.getODataFieldName());
    		searchParamsItemGroup.addChild(filterParamItemGroup);
    		
    		final RichHTMLPresentationItemGroupWrapper filterParamPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(m_formContext, filterParamItemGroup.unwrap());
    		searchParamsPresItemGroup.addChild(filterParamPresItemGroup.unwrap());
    		
    		final String filterParamGroupPath = searchRequestFiltersGroupPath + '.' + filterParamDef.getODataFieldName() + "[1]";
    		final String comparisonOperatorItemPath = filterParamGroupPath + '.' + DataStore.EnquiryFilterParamGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME;

    		final String[] operandItemPaths = new String[] {
    			filterParamGroupPath + '.' + DataStore.EnquiryFilterParamGroupTemplate.OPERAND1_ITEM_NAME,
    			filterParamGroupPath + '.' + DataStore.EnquiryFilterParamGroupTemplate.OPERAND2_ITEM_NAME
			};

			// Add comparison operator question (process & presentation)
			
			final QuestionWrapper paramCompareOpQuestion = QuestionWrapper.create(m_formContext);
			paramCompareOpQuestion.setPropertyKeyFromEntityKey(comparisonOperatorItemPath);
			paramCompareOpQuestion.setMandatory(Boolean.valueOf(filterParamDef.isParamMandatory()));
			m_globalContext.getLanguageMapHelper().registerT24TextTranslations(paramCompareOpQuestion, filterParamDef.getLabelTranslations());
	        
	        paramCompareOpQuestion.setQuestionText(filterParamDef.getLabelTranslations().getText());
	        filterParamItemGroup.addChild(paramCompareOpQuestion);

	        final RichHTMLPresentationQuestionWrapper paramCompareOpPresQuestion = RichHTMLPresentationQuestionWrapper.create(m_formContext, paramCompareOpQuestion.unwrap());
	        paramCompareOpPresQuestion.setDesignToUseFromEntityKey("Enquiry Search Comparison  Operator Question");
	        paramCompareOpPresQuestion.setDisplayType("Drop down list");
	        paramCompareOpPresQuestion.setListPrompt( RichHTMLPresentationQuestionWrapper.EListPrompt.NO_PROMPT );
	        paramCompareOpPresQuestion.setDefaultListValue("equals");
	        filterParamPresItemGroup.addChild(paramCompareOpPresQuestion);
	        
	        // Add the [Single Operand] question (process & presentation)
	        
	        final QuestionWrapper singleOperandQuestion = QuestionWrapper.create(m_formContext);
	        singleOperandQuestion.setQuestionText("$%SLANG Enquiry.SingleOperandLabel [Single Operand]$");
	        singleOperandQuestion.setNotApplicable(Boolean.TRUE);
	        singleOperandQuestion.setConditionExpression("$$" + comparisonOperatorItemPath + ".groupValue()$ != 'RANGE'");
	        singleOperandQuestion.setMandatory(Boolean.FALSE);
	        singleOperandQuestion.setPropertyKeyFromEntityKey(operandItemPaths[0]);
	        filterParamItemGroup.addChild(singleOperandQuestion);
	        
	        addSearchFilterValuePresQuestion(singleOperandQuestion, filterParamDef, filterParamPresItemGroup);
	        
	        // Add the [Range Bound #] questions (process and presentation)
	        
	        for (int j = 0; j < operandItemPaths.length; ++j)
	        {
		        final QuestionWrapper rangeBoundQuestion = QuestionWrapper.create(m_formContext);
		        rangeBoundQuestion.setQuestionText("[$%SLANG Enquiry.RangeBoundLabel Range Bound$ #" + (j + 1) + "]");
		        rangeBoundQuestion.setNotApplicable(Boolean.TRUE);
		        rangeBoundQuestion.setConditionExpression("$$" + comparisonOperatorItemPath + ".groupValue()$ == 'RANGE'");
		        rangeBoundQuestion.setMandatory(Boolean.FALSE);
		        rangeBoundQuestion.setPropertyKeyFromEntityKey(operandItemPaths[j]);
		        filterParamItemGroup.addChild(rangeBoundQuestion);

		        addSearchFilterValuePresQuestion(rangeBoundQuestion, filterParamDef, filterParamPresItemGroup);
	        }
    	}
    	
    	searchParamsPresSection.addChild(makePresSpacingWithHorizontalLine("EnquirySearchScreenWidth"));
    }
    
    private void addAdvancedSearchFindButtonRules(FormButtonWrapper p_findButton, EnquirySelectionDigest p_selectionDigest, EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
    	// Add rule to clear the target SearchResponse group.
    	
		final ResetDataRuleWrapper clearSearchResultsGroupRule = ResetDataRuleWrapper.create(m_formContext);
		clearSearchResultsGroupRule.setName("Clear the " + p_context.getEnquirySearchResponseGroup().getName() + " group");
		clearSearchResultsGroupRule.setResetPropertyGroup(p_context.getEnquirySearchResponseGroup());
		p_findButton.addChild(clearSearchResultsGroupRule.unwrap());

		// Add per-filter-param rules to check whether (a) a range comparison operator was chosen, and if so, (b) to ensure that Operand1 < Operand2 (swapping the 2 values if necessary)
		p_findButton.addChild(createFilterOperandsPreprocessingRules(p_selectionDigest, p_context));
		
		final String
			searchRequestGroupPath = p_context.getEnquirySearchRequestGroup().getEntityKeyName(),
			baseIrisResourceUrlForEnquiry = "$$!IRIS_URL$/" + p_context.getIrisEnquiryResourceName() + "()",
			filtersGroupPath = searchRequestGroupPath + '.' + DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.FiltersGroup.NAME + "[1]",
			repeatableColumnSortSpecGroupPath = searchRequestGroupPath + '.' + DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.OrderByGroup.NAME + "[1]." + DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.NAME + "[1]",
			fullIrisSearchUrlItemPath = p_context.getFullIrisSearchUrlItem().getEntityKeyName();
		
		final EnquirySearchUrlBuilderRule enquirySearchUrlBuilderRule = new EnquirySearchUrlBuilderRule(m_formContext);
		enquirySearchUrlBuilderRule.setName("Build full IRIS Search URL");
		enquirySearchUrlBuilderRule.setBaseIrisUrl(baseIrisResourceUrlForEnquiry);
		enquirySearchUrlBuilderRule.setFilterParamsGroupPath(filtersGroupPath);
		
		if (m_genOptions.searchGenerationOption.includeSortOptions())
			enquirySearchUrlBuilderRule.setRepeatableOrderByParamGroupPath(repeatableColumnSortSpecGroupPath);
		
		enquirySearchUrlBuilderRule.setFullIrisSearchUrlOutputItemPath(fullIrisSearchUrlItemPath);
		enquirySearchUrlBuilderRule.setErrorHandlingType(ErrorHandlingItem.ON_ERROR_PASS_UP);
		p_findButton.addChild(enquirySearchUrlBuilderRule);

		final String searchResultGroupFirstInstancePath = StringUtils.replaceOnce(p_context.getEnquirySearchResultGroup().getEntityKeyName(), "[C]", "[1]");

		final ResultsHeaderFooterRowSpec resultsHeaderRowSpec = p_fieldsDigest.getEnqResultsHeaderRowSpec();
		boolean headerRowInserted = false;
		
		// If we have a header row spec, then create a placeholder SearchResult as the first instance now (so that we won't have to *insert* it after the IRIS call, which would
		// be slow due to the need to shuffle all of the IRIS results up by one).
		
		if (resultsHeaderRowSpec != null)
		{
			final String isHeaderRowItemPathForFirstResult = searchResultGroupFirstInstancePath + '.' + DataStore.EnquiryRequestResponseGroupTemplate.SearchResponseGroup.SearchResultGroup.ItemNames.IS_HEADER_ROW;
			final SetValueRuleWrapper createPlaceHolderHeaderRowRule = SetValueRuleWrapper.create(m_formContext);
			createPlaceHolderHeaderRowRule.setName("Create placeholder header row");
			createPlaceHolderHeaderRowRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
			createPlaceHolderHeaderRowRule.setPropertyNameFromEntityKey(isHeaderRowItemPathForFirstResult);
			createPlaceHolderHeaderRowRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
			createPlaceHolderHeaderRowRule.setFromValue(Lists.TrueOrFalse.Keys.TRUE);
			createPlaceHolderHeaderRowRule.setFromValueList(Lists.TrueOrFalse.Keys.TRUE);
			p_findButton.addChild(createPlaceHolderHeaderRowRule.unwrap());
			
			headerRowInserted = true;
		}
		
		final IRISRequest irisRequestRule = new IRISRequest(m_formContext);
		irisRequestRule.setName("Call IRIS");
		irisRequestRule.setErrorHandlingType(ErrorHandlingItem.ON_ERROR_PASS_UP);
		irisRequestRule.setAttribute(IRISRequest.TARGET_URL, "$$" + fullIrisSearchUrlItemPath + "$");
		irisRequestRule.setAttribute(IRISRequest.TARGET_GROUP, searchResultGroupFirstInstancePath);
		irisRequestRule.setAttribute(IRISRequest.APPENDS_RESULT, headerRowInserted ? "Y" : "N");
		p_findButton.addChild(irisRequestRule);

		addCommonIrisRequestChildRules(irisRequestRule, p_fieldsDigest, p_context);
    }
    
    private ContainerRuleWrapper createFilterOperandsPreprocessingRules(EnquirySelectionDigest p_selectionDigest, EnquiryProcessorContext p_context)
    {
    	final ContainerRuleWrapper resultContainerRule = ContainerRuleWrapper.create(m_formContext, "Filter operands preprocessing rules");
    	
    	final EnquiryFilterParamDef[] filterParamDefs = p_selectionDigest.getFilterParamDefs();
    	final int numFilterParamDefs = (filterParamDefs == null) ? 0 : filterParamDefs.length;
    	
    	for (int i = 0; i < numFilterParamDefs; ++i)
    		resultContainerRule.addTrueRule(createFilterOperandPreprocessingRule(filterParamDefs[i], p_context).unwrap());
    	
    	return resultContainerRule;
    }
    
    private void addCommonIrisRequestChildRules(IRISRequest p_irisRequestRule, EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
    	final PropertyGroupWrapper searchResultGroup = p_context.getEnquirySearchResultGroup();
    	
    	final String
    		searchResultGroupCurrentInstancePath = searchResultGroup.getEntityKeyName(),
    	    searchResultGroupFirstInstancePath = StringUtils.replaceOnce(searchResultGroupCurrentInstancePath, "[C]", "[1]"),
    		searchResultGroupSecondInstancePath = StringUtils.replaceOnce(searchResultGroupCurrentInstancePath, "[C]", "[2]");
    	
    	final ResultsHeaderFooterRowSpec
			resultsHeaderRowSpec = p_fieldsDigest.getEnqResultsHeaderRowSpec(),
			resultsFooterRowSpec = p_fieldsDigest.getEnqResultsFooterRowSpec();

    	final boolean placeHolderHeaderRowInsertedPriorToIRISCall = (resultsHeaderRowSpec != null);
    	
    	final EvaluateRuleWrapper resultsReturnedEvalRule = EvaluateRuleWrapper.create(m_formContext);
		resultsReturnedEvalRule.setName("Test whether any results were returned" );
		resultsReturnedEvalRule.setExpression("$$" + searchResultGroupCurrentInstancePath + ".lastInstance()$ > '" + (placeHolderHeaderRowInsertedPriorToIRISCall ? 1 : 0) + "'");
		p_irisRequestRule.addTrueRule(resultsReturnedEvalRule.unwrap(), false /* p_linkedObject */);

		final ContainerRuleWrapper resultsFoundContainerRule = ContainerRuleWrapper.create(m_formContext, "Results found rules");
		resultsReturnedEvalRule.addTrueRule(resultsFoundContainerRule.unwrap());
		
		final ContainerRuleWrapper noResultsFoundContainerRule = ContainerRuleWrapper.create(m_formContext, "No results found rules");
		resultsReturnedEvalRule.addFalseRule(noResultsFoundContainerRule.unwrap());
		
		if (placeHolderHeaderRowInsertedPriorToIRISCall)
		{
			// Add rules to populate our placeholder header row with field values from the 2nd SearchResult instance (i.e. the 1st result returned by IRIS)
			
			Iterator<ResultsHeaderFooterCellSpec> headerCellSpecIter = resultsHeaderRowSpec.iterator();
			ContainerRuleWrapper populateResultsHeaderRowRule = null;
			
			while(headerCellSpecIter.hasNext())
			{
				final ResultsHeaderFooterCellSpec headerCellSpec = headerCellSpecIter.next();
				final QuestionWrapper resultsTableQuestion = m_resultsTableQuestionByT24ColumnNumber.get(headerCellSpec.getT24ColumnNumber());
				
				if (resultsTableQuestion == null)
					continue;

				final String tableQuestionItemPath = (String) resultsTableQuestion.unwrap().getAttribute("PropertyKey");
				
				final String headerCellValueSourceItemName = headerCellSpec.getEdgeConnectDataItemName();
				final String headerCellValueSourceItemPath = searchResultGroupSecondInstancePath + '.' + headerCellValueSourceItemName;
				
				final String headerCellValueTargetItemPath = StringUtils.replaceAll(tableQuestionItemPath, "[C]", "[1]");
				final String headerCellValueTargetItemName = headerCellValueTargetItemPath.substring(headerCellValueTargetItemPath.lastIndexOf('.') + 1);
				
				final SetValueRuleWrapper setHeaderCellValueRule = SetValueRuleWrapper.create(m_formContext);
				setHeaderCellValueRule.setName("Populate " + headerCellValueTargetItemName + " item in header row from " + headerCellValueSourceItemName + " item in 2nd result instance");
				setHeaderCellValueRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
				setHeaderCellValueRule.setPropertyNameFromEntityKey(headerCellValueTargetItemPath);
				setHeaderCellValueRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
				setHeaderCellValueRule.setFromPropertyNameFromEntityKey(headerCellValueSourceItemPath);
				
				if (populateResultsHeaderRowRule == null)
					populateResultsHeaderRowRule = ContainerRuleWrapper.create(m_formContext, "Populate our placeholder header row from fields in the 2nd result instance");
				
				populateResultsHeaderRowRule.addTrueRule(setHeaderCellValueRule.unwrap());
			} // headerCellSpecIter.hasNext()

			if (populateResultsHeaderRowRule != null)
			{
				resultsFoundContainerRule.addTrueRule(populateResultsHeaderRowRule.unwrap());
			}
		} // if (placeHolderHeaderRowInsertedPriorToIRISCall)

		if (resultsFooterRowSpec != null)
		{
			/*
			 * Add rules to populate an additional 'footer' results row with field values:
			 * - first from the 2nd SearchResult instance (i.e. the 1st result returned by IRIS)
			 * - then falling back to the final result returned by IRIS (i.e. the result immediately BEFORE the footer result that we'll be inserting)
			 * 
			 * This approach is based on findings re the STMT.ENT.BOOK enquiry, for example, where the closing balance value is given by a final <entry> in the IRIS response that contains ONLY
			 * that value (and none of the values required to populate the other footer row fields).
			 */
			
			final Iterator<ResultsHeaderFooterCellSpec> footerCellSpecIter = resultsFooterRowSpec.iterator();
			final PropertyWrapper finalResultInstanceItem = p_context.getFinalResultInstanceItem();
			
			ContainerRuleWrapper populateResultsFooterRowFrom2ndResultInstanceRule = null;
			StringBuilder anyFooterCellsLeftUnpopulatedExpn = null;
			ArrayList<EvaluateRuleWrapper> footerCellUnpopulatedEvalRules = null;
			
			while(footerCellSpecIter.hasNext())
			{
				final ResultsHeaderFooterCellSpec footerCellSpec = footerCellSpecIter.next();
				final QuestionWrapper resultsTableQuestion = m_resultsTableQuestionByT24ColumnNumber.get(footerCellSpec.getT24ColumnNumber());
				
				if (resultsTableQuestion == null)
					continue;

				if (populateResultsFooterRowFrom2ndResultInstanceRule == null) // lazy create the container rule that will contain the per-footer field rules for populating footer from 2nd result instance
				{
					populateResultsFooterRowFrom2ndResultInstanceRule = ContainerRuleWrapper.create(m_formContext, "First try populating our footer row from fields in the 2nd result instance");

					// Add a rule store the final instance of the repeating SearchResut group to WorkingElements[1].FinalResultInstance (so that we can refer back to this instance
					// as SearchResult[$$WorkingElements[1].FinalResultInstance] even after having created a new 'last instance' by populating item(s) for the footer row.
					final SetValueRuleWrapper storeFinalResultInstanceRule = SetValueRuleWrapper.create(m_formContext);
					storeFinalResultInstanceRule.setName("Initialize " + finalResultInstanceItem.getName() + " from final instance of repeating " + searchResultGroup.getName() + " group");
					storeFinalResultInstanceRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
					storeFinalResultInstanceRule.setPropertyName(finalResultInstanceItem);
					storeFinalResultInstanceRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
					storeFinalResultInstanceRule.setFromValue("$$" + searchResultGroupFirstInstancePath + ".lastInstance()$");
					populateResultsFooterRowFrom2ndResultInstanceRule.addTrueRule(storeFinalResultInstanceRule.unwrap());
					
					// Add a rule to set the current instance for the repeating SearchResult group point to the (current) final one
					final SetValueRuleWrapper setCurrentResultInstanceToFinal = SetValueRuleWrapper.create(m_formContext);
					setCurrentResultInstanceToFinal.setName("Set current instance for " + searchResultGroup.getName() + " group to last populated instance");
					setCurrentResultInstanceToFinal.setType(SetValueRuleWrapper.EType.DATA_GROUP_INSTANCE);
					setCurrentResultInstanceToFinal.setPropertyGroupInstanceNameFromEntityKey(searchResultGroupFirstInstancePath);
					setCurrentResultInstanceToFinal.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
					setCurrentResultInstanceToFinal.setFromPropertyName(finalResultInstanceItem);
					populateResultsFooterRowFrom2ndResultInstanceRule.addTrueRule(setCurrentResultInstanceToFinal.unwrap());
					
					// Add a rule to increment the current instance for the repeating SearchResult group to point to the next free instance (which will become our footer row instance).
					// Subsequent rules will therefore refer to the footer row instance as SearchResult[C].
					final IncrementorRuleWrapper incrementCurrentResultGroupInstanceRule = IncrementorRuleWrapper.create(m_formContext);
					incrementCurrentResultGroupInstanceRule.setName("Increment current instance for " + searchResultGroup.getName() + " to point to the first empty one");
					incrementCurrentResultGroupInstanceRule.setOperator(IncrementorRuleWrapper.EOperator.INCREMENT);
					incrementCurrentResultGroupInstanceRule.setType(IncrementorRuleWrapper.EType.DATA_GROUP_INSTANCE);
					incrementCurrentResultGroupInstanceRule.setPropertyGroupNameFromEntityKey(searchResultGroupFirstInstancePath);
					incrementCurrentResultGroupInstanceRule.setIncrementBy("1");
					populateResultsFooterRowFrom2ndResultInstanceRule.addTrueRule(incrementCurrentResultGroupInstanceRule.unwrap());
				}
				
				final String tableQuestionItemPath = (String) resultsTableQuestion.unwrap().getAttribute("PropertyKey");
				
				final String footerCellValueSourceItemName = footerCellSpec.getEdgeConnectDataItemName();
				final String footerCellValueSourceItemPath = searchResultGroupSecondInstancePath + '.' + footerCellValueSourceItemName;
				
				final String footerCellValueTargetItemPath = tableQuestionItemPath; // will include [C], so referring to the 'footer' result row we're about to insert values for
				final String footerCellValueTargetItemName = footerCellValueTargetItemPath.substring(footerCellValueTargetItemPath.lastIndexOf('.') + 1);
				
				final SetValueRuleWrapper setFooterCellValueRule = SetValueRuleWrapper.create(m_formContext);
				setFooterCellValueRule.setName("Populate " + footerCellValueTargetItemName + " item in footer row from " + footerCellValueSourceItemName + " item in 2nd result instance");
				setFooterCellValueRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
				setFooterCellValueRule.setPropertyNameFromEntityKey(footerCellValueTargetItemPath);
				setFooterCellValueRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
				setFooterCellValueRule.setFromPropertyNameFromEntityKey(footerCellValueSourceItemPath);
				
				populateResultsFooterRowFrom2ndResultInstanceRule.addTrueRule(setFooterCellValueRule.unwrap());
				
				/*
				 * Now collect up the bits and bobs necessary to try the SearchResult pointed to by WorkingElements[1].FinalResultInstance as a fallback for any footer values that are still
				 * empty (this is necessary in the case of STMT.ENT.BOOK, for example, where the closing balance value is given by a final <entry> in the IRIS response that contains ONLY
				 * that value).
				 */
				
				// 1. Add a clause to the expression we're building up to test whether ANY of the footer cells have been left unpopulated...
				
				if (anyFooterCellsLeftUnpopulatedExpn == null)
					anyFooterCellsLeftUnpopulatedExpn = new StringBuilder();
				
				if (anyFooterCellsLeftUnpopulatedExpn.length() > 0)
					anyFooterCellsLeftUnpopulatedExpn.append(" OR ");
				
				anyFooterCellsLeftUnpopulatedExpn.append("$$");
				anyFooterCellsLeftUnpopulatedExpn.append(footerCellValueTargetItemPath);
				anyFooterCellsLeftUnpopulatedExpn.append("$ == ''");
				
				// Add an evaluation rule to test whether the CURRENT footer cell is still unpopulated (and if so to try populating from last SearchResult instance) to our list...
				
				if (footerCellUnpopulatedEvalRules == null)
					footerCellUnpopulatedEvalRules = new ArrayList<EvaluateRuleWrapper>();
				
				final EvaluateRuleWrapper evalFooterCellStillUnpopulatedRule = EvaluateRuleWrapper.create(m_formContext);
				evalFooterCellStillUnpopulatedRule.setName("Test whether footer row item " + footerCellValueTargetItemName + " is still unpopulated");
				evalFooterCellStillUnpopulatedRule.setExpression("$$" + footerCellValueTargetItemPath + "$ == ''");
				footerCellUnpopulatedEvalRules.add(evalFooterCellStillUnpopulatedRule);
				
				final SetValueRuleWrapper fallbackSetFooterCellValueRule = SetValueRuleWrapper.create(m_formContext);
				fallbackSetFooterCellValueRule.setName("Populate " + footerCellValueTargetItemName + " item in footer row from " + footerCellValueSourceItemName + " item in the final result instance");
				fallbackSetFooterCellValueRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
				fallbackSetFooterCellValueRule.setPropertyNameFromEntityKey(footerCellValueTargetItemPath);
				fallbackSetFooterCellValueRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
				fallbackSetFooterCellValueRule.setFromPropertyNameFromEntityKey(StringUtils.replaceAll(footerCellValueSourceItemPath, "[2]", "[$$" + finalResultInstanceItem.getEntityKeyName() + "$]"));
				evalFooterCellStillUnpopulatedRule.addTrueRule(fallbackSetFooterCellValueRule.unwrap());
			} // while(footerCellSpecIter.hasNext()) 

			if (populateResultsFooterRowFrom2ndResultInstanceRule != null)
			{
				final ContainerRuleWrapper populateResultsFooterRowContainerRule = ContainerRuleWrapper.create(m_formContext, "Populate our results footer row");
				populateResultsFooterRowContainerRule.addTrueRule(populateResultsFooterRowFrom2ndResultInstanceRule.unwrap());
				resultsFoundContainerRule.addTrueRule(populateResultsFooterRowContainerRule.unwrap());

				final ContainerRuleWrapper fallbackToFinalRowForUnpopulatedFieldsRule = ContainerRuleWrapper.create(m_formContext, "Now fallback to final results row for any still-unpopulated footer fields");
				populateResultsFooterRowContainerRule.addTrueRule(fallbackToFinalRowForUnpopulatedFieldsRule.unwrap());
				
				// Add an EvaluateRule to test whether any of our displayable footer cell items are still unpopulated
				
				final EvaluateRuleWrapper anyFooterCellsLeftUnpopulatedEvalRule = EvaluateRuleWrapper.create(
					m_formContext,
					"Check whether any of our footer cells are still unpopulated",
					anyFooterCellsLeftUnpopulatedExpn.toString()
				);
				
				fallbackToFinalRowForUnpopulatedFieldsRule.addTrueRule(anyFooterCellsLeftUnpopulatedEvalRule.unwrap());
				
				// Now add the each of the footerCellUnpopulatedEvalRules collected above as 'true' children of the above EvaluateRule
				
				final Iterator<EvaluateRuleWrapper> checkForUnpopulatedFooterCellEvalRuleIter = footerCellUnpopulatedEvalRules.iterator();
				
				while(checkForUnpopulatedFooterCellEvalRuleIter.hasNext())
				{
					anyFooterCellsLeftUnpopulatedEvalRule.addTrueRule(checkForUnpopulatedFooterCellEvalRuleIter.next().unwrap());
				}

				/*
				 * If (as is the case with STMT.ENT.BOOK) the final entry in the IRIS response actually contains no values for any of our result table's column questions, then
				 * we want to delete the SearchResult instance corresponding to that final entry so that we don't see an empty row immediately above our footer row.
				 * 
				 * In outline, the approach taken below involves:
				 * a) concatenating up all of the directly displayable values from the final SearchResult instance populated by the IRISRequest rule into a single string
				 * b) checking whether this string ends up being empty
				 * c) if so, deleting that SearchResult instance.
				 * 
				 * The implementation of (a) is complicated by the fact that $$'d subscripts are NOT currently understood by (i) edgeConnect's EvaluateRule (within expression string)
				 * or (ii) within edgeConnect's SetValueRule (within 'From' Value string - and we have to use a from Value to do the concatenation).
				 * 
				 * We kludge our way around this by (for each display value item), first copying the value for the final SearchResult instance populated by the IRISRequest rule
				 * to a temporary 'scratch' item, and then using a separate SetValueRule to concatenate the contents of that scratch item onto the item containing ALL of the
				 * display values. 
				 */
				
				final ContainerRuleWrapper checkFinalResultRowAndDeleteIfNoDisplayableValuesRule = ContainerRuleWrapper.create(m_formContext, "If the final result row contains no directly displayable values then delete it");
				resultsFoundContainerRule.addTrueRule(checkFinalResultRowAndDeleteIfNoDisplayableValuesRule.unwrap());
				
				final PropertyWrapper allDisplayableFinalResultRowValuesItem = p_context.getAllDisplayableFinalResultRowValuesItem();
				final String allDisplayableFinalResultRowValuesItemPath = allDisplayableFinalResultRowValuesItem.getEntityKeyName();
				final String allDisplayableFinalResultRowValuesItemName = allDisplayableFinalResultRowValuesItem.getName();

				final ContainerRuleWrapper concatDisplayValuesFromFinalSearchResultContainer = ContainerRuleWrapper.create(
					m_formContext, "Concatenate up directly displayable values from final search result to " + allDisplayableFinalResultRowValuesItemName);
				
				checkFinalResultRowAndDeleteIfNoDisplayableValuesRule.addTrueRule(concatDisplayValuesFromFinalSearchResultContainer.unwrap());
				
				final Iterator<QuestionWrapper> resultsTableQuestionIter = m_resultsTableQuestionByT24ColumnNumber.values().iterator();
				
				final String allDisplayableFinalResultsRowValuesItemRef = "$$" + allDisplayableFinalResultRowValuesItemPath + "$";
				
				final PropertyWrapper scratchItem = p_context.getScratchItem();
				final String scratchItemPath = scratchItem.getEntityKeyName();
				final String scratchItemName = scratchItem.getName();
				final String scratchItemRef = "$$" + scratchItemPath + "$";
				
				final ResetDataRuleWrapper resetAllDisplayableFinalResultRowValuesItemRule = ResetDataRuleWrapper.create(m_formContext);
				resetAllDisplayableFinalResultRowValuesItemRule.setName("Reset " + allDisplayableFinalResultRowValuesItemName + " item");
				resetAllDisplayableFinalResultRowValuesItemRule.setResetPropertyFromEntityKey(allDisplayableFinalResultRowValuesItemPath);
				concatDisplayValuesFromFinalSearchResultContainer.addTrueRule(resetAllDisplayableFinalResultRowValuesItemRule.unwrap());
				
				while(resultsTableQuestionIter.hasNext())
				{
					final String tableQuestionItemPath = (String) resultsTableQuestionIter.next().unwrap().getAttribute("PropertyKey");
					final String tableQuestionItemName = tableQuestionItemPath.substring(tableQuestionItemPath.lastIndexOf('.') + 1);
					final String itemPathForFinalResult = StringUtils.replaceOnce(tableQuestionItemPath, "[C]", "[$$" + finalResultInstanceItem.getEntityKeyName() + "$]");

					final ContainerRuleWrapper itemCopyAndAppendContainer = ContainerRuleWrapper.create(m_formContext, tableQuestionItemName + " value");
					concatDisplayValuesFromFinalSearchResultContainer.addTrueRule(itemCopyAndAppendContainer.unwrap());
					
					final SetValueRuleWrapper copyFinalResultDisplayItemToScratchItemRule = SetValueRuleWrapper.create(m_formContext);
					copyFinalResultDisplayItemToScratchItemRule.setName("Copy " + tableQuestionItemName + " value from final result row to " + scratchItemName + " item");
					copyFinalResultDisplayItemToScratchItemRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
					copyFinalResultDisplayItemToScratchItemRule.setPropertyNameFromEntityKey(scratchItemPath);
					copyFinalResultDisplayItemToScratchItemRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
					copyFinalResultDisplayItemToScratchItemRule.setFromPropertyNameFromEntityKey(itemPathForFinalResult);
					itemCopyAndAppendContainer.addTrueRule(copyFinalResultDisplayItemToScratchItemRule.unwrap());
					
					final SetValueRuleWrapper appendScratchItemToAllDisplayableFinalResultRowValuesItemRule = SetValueRuleWrapper.create(m_formContext);
					appendScratchItemToAllDisplayableFinalResultRowValuesItemRule.setName("Append " + scratchItemName + " item value to " + allDisplayableFinalResultRowValuesItemName);
					appendScratchItemToAllDisplayableFinalResultRowValuesItemRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
					appendScratchItemToAllDisplayableFinalResultRowValuesItemRule.setPropertyNameFromEntityKey(allDisplayableFinalResultRowValuesItemPath);
					appendScratchItemToAllDisplayableFinalResultRowValuesItemRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
					appendScratchItemToAllDisplayableFinalResultRowValuesItemRule.setFromValue(allDisplayableFinalResultsRowValuesItemRef + scratchItemRef);
					itemCopyAndAppendContainer.addTrueRule(appendScratchItemToAllDisplayableFinalResultRowValuesItemRule.unwrap());
				}
				
				final EvaluateRuleWrapper evalFinalSearchResultInstanceDefinesNoDirectlyDisplayableValuesRule = EvaluateRuleWrapper.create(
					m_formContext,
					"Test whether the final SearchResult instance defines any directly displayable values",
					allDisplayableFinalResultsRowValuesItemRef + " == ''"
				);
				
				checkFinalResultRowAndDeleteIfNoDisplayableValuesRule.addTrueRule(evalFinalSearchResultInstanceDefinesNoDirectlyDisplayableValuesRule.unwrap());
				
				final ResetDataRuleWrapper resetFinalSearchResultInstanceRule = ResetDataRuleWrapper.create(m_formContext);
				final String searchResultGroupFinalInstancePath = StringUtils.replaceOnce(searchResultGroupCurrentInstancePath, "[C]", "[$$" + finalResultInstanceItem.getEntityKeyName() + "$]"); 
				resetFinalSearchResultInstanceRule.setResetPropertyGroupFromEntityKey(searchResultGroupFinalInstancePath);
				
				evalFinalSearchResultInstanceDefinesNoDirectlyDisplayableValuesRule.addTrueRule(resetFinalSearchResultInstanceRule.unwrap());
			} // if (populateResultsFooterRowFrom2ndResultInstanceRule != null)
		} // if (m_resultsFooterRowSpec != null)

		/*
		 * If a placeholder 'header' row was inserted prior to the IRIS call, then add a 'true' rule to set the current instance for the repeating SearchResult group to point to the first real data
		 * (i.e. non header) row.
		 * 
		 * This is necessary to ensure that the "[C]" references in any dynamic search results screen header elements we create point to an instance containing the referenced values
		 */

		if (placeHolderHeaderRowInsertedPriorToIRISCall)
		{
			final SetValueRuleWrapper setCurrentResultGroupInstanceRule = SetValueRuleWrapper.create(m_formContext);
			setCurrentResultGroupInstanceRule.setName("Set the current SearchResult group instance to be the first real data - vs header - row");
			setCurrentResultGroupInstanceRule.setType(SetValueRuleWrapper.EType.DATA_GROUP_INSTANCE);
			setCurrentResultGroupInstanceRule.setPropertyGroupInstanceNameFromEntityKey(searchResultGroupFirstInstancePath);
			setCurrentResultGroupInstanceRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
			setCurrentResultGroupInstanceRule.setFromValue(placeHolderHeaderRowInsertedPriorToIRISCall ? "2" : "1");
			resultsFoundContainerRule.addTrueRule(setCurrentResultGroupInstanceRule.unwrap());
		}

		final PropertyWrapper
			displayedEnquiryItemGroupItem = p_context.getDisplayedEnquiryItemGroupItem(),
			searchOutcomeItem = p_context.getSearchOutcomeItem();
		
		// Add a rule set DisplayedEnquiryItemGroup item to RESULTS to resultsFoundContainerRule
		final SetValueRuleWrapper setDisplayedEnquiryItemGroupToResultsRule = SetValueRuleWrapper.create(m_formContext);
		setDisplayedEnquiryItemGroupToResultsRule.setName("Set " + displayedEnquiryItemGroupItem.getName() + " to " + Lists.DisplayedEnquiryItemGroup.Keys.SEARCH_RESULTS);
		setDisplayedEnquiryItemGroupToResultsRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setDisplayedEnquiryItemGroupToResultsRule.setPropertyName(displayedEnquiryItemGroupItem);
		setDisplayedEnquiryItemGroupToResultsRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setDisplayedEnquiryItemGroupToResultsRule.setFromValue(Lists.DisplayedEnquiryItemGroup.Keys.SEARCH_RESULTS);
		setDisplayedEnquiryItemGroupToResultsRule.setFromValueList(Lists.DisplayedEnquiryItemGroup.Keys.SEARCH_RESULTS);
		resultsFoundContainerRule.addTrueRule(setDisplayedEnquiryItemGroupToResultsRule.unwrap());
		
		// Add a rule to set SearchOutcome to RESULTS_FOUND to resultsFoundContainerRule
		final SetValueRuleWrapper setSearchOutcomeToResultsFoundRule = SetValueRuleWrapper.create(m_formContext);
		setSearchOutcomeToResultsFoundRule.setName("Set " + searchOutcomeItem.getName() + " to " + Lists.SearchOutcome.Keys.RESULTS_FOUND);
		setSearchOutcomeToResultsFoundRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setSearchOutcomeToResultsFoundRule.setPropertyName(searchOutcomeItem);
		setSearchOutcomeToResultsFoundRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setSearchOutcomeToResultsFoundRule.setFromValue(Lists.SearchOutcome.Keys.RESULTS_FOUND);
		setSearchOutcomeToResultsFoundRule.setFromValueList(Lists.SearchOutcome.Keys.RESULTS_FOUND);
		resultsFoundContainerRule.addTrueRule(setSearchOutcomeToResultsFoundRule.unwrap());

		// If a placeholder 'header' row was inserted prior to the IRIS call, then add a rule to clear this to noResultsFoundContainerRule
		
		if (placeHolderHeaderRowInsertedPriorToIRISCall)
		{
			final ResetDataRuleWrapper clearSearchResultsGroupRule = ResetDataRuleWrapper.create(m_formContext);
			clearSearchResultsGroupRule.setName("Clear the " + p_context.getEnquirySearchResponseGroup().getName() + " group");
			clearSearchResultsGroupRule.setResetPropertyGroup(p_context.getEnquirySearchResponseGroup());
			noResultsFoundContainerRule.addTrueRule(clearSearchResultsGroupRule.unwrap());
		}

		// Add a rule to set SearchOutcome to NO_RESULTS_FOUND to noResultsFoundContainerRule
		final SetValueRuleWrapper setSearchOutcomeToNoResultsFoundRule = SetValueRuleWrapper.create(m_formContext);
		setSearchOutcomeToNoResultsFoundRule.setName("Set " + searchOutcomeItem.getName() + " to " + Lists.SearchOutcome.Keys.NO_RESULTS_FOUND);
		setSearchOutcomeToNoResultsFoundRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setSearchOutcomeToNoResultsFoundRule.setPropertyName(searchOutcomeItem);
		setSearchOutcomeToNoResultsFoundRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setSearchOutcomeToNoResultsFoundRule.setFromValue(Lists.SearchOutcome.Keys.NO_RESULTS_FOUND);
		setSearchOutcomeToNoResultsFoundRule.setFromValueList(Lists.SearchOutcome.Keys.NO_RESULTS_FOUND);
		noResultsFoundContainerRule.addTrueRule(setSearchOutcomeToNoResultsFoundRule.unwrap());
    }
    
    private ContainerRuleWrapper createFilterOperandPreprocessingRule(EnquiryFilterParamDef p_filterParamDef, EnquiryProcessorContext p_context)
    {
    	final String
    		odataFieldName = p_filterParamDef.getODataFieldName(),
    		filterParamGroupPath = p_context.getEnquirySearchRequestGroup().getEntityKeyName() + '.' +	DataStore.EnquiryRequestResponseGroupTemplate.SearchRequestGroup.FiltersGroup.NAME + "[1]." + odataFieldName + "[1]",
    		comparisonOperatorItemPath = filterParamGroupPath + '.' + DataStore.EnquiryFilterParamGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME,
    		operand1ItemPath = filterParamGroupPath + '.' + DataStore.EnquiryFilterParamGroupTemplate.OPERAND1_ITEM_NAME,
    	    operand2ItemPath = filterParamGroupPath + '.' + DataStore.EnquiryFilterParamGroupTemplate.OPERAND2_ITEM_NAME;
    	
    	final ContainerRuleWrapper topLevelContainerRule = ContainerRuleWrapper.create(m_formContext, odataFieldName);

    	final PropertyWrapper scratchItem = p_context.getScratchItem();
    	
    	final EvaluateRuleWrapper testForRangeComparisonOperatorSelectedRule = EvaluateRuleWrapper.create(
    		m_formContext,
    		"If a range-based operator was chosen for " + odataFieldName,
    		"$$" + comparisonOperatorItemPath + ".groupValue()$ == 'RANGE'" 
    	);

    	topLevelContainerRule.addTrueRule(testForRangeComparisonOperatorSelectedRule.unwrap());
    	
		final EvaluateRuleWrapper areRangeOperatorsMisorderedEvalRule = EvaluateRuleWrapper.create(m_formContext);
		areRangeOperatorsMisorderedEvalRule.setName("Are the range operands for " + odataFieldName + " mis-ordered");
		areRangeOperatorsMisorderedEvalRule.setExpression("$$" + operand2ItemPath + "$ < $$" + operand1ItemPath + "$");
		testForRangeComparisonOperatorSelectedRule.addTrueRule(areRangeOperatorsMisorderedEvalRule.unwrap());

		final ContainerRuleWrapper swapOperandsContainerRule = ContainerRuleWrapper.create(m_formContext, "Swap the range operands for " + odataFieldName);
		areRangeOperatorsMisorderedEvalRule.addTrueRule(swapOperandsContainerRule.unwrap());

		final SetValueRuleWrapper setScratchItemFromOperand1ItemRule = SetValueRuleWrapper.create(m_formContext);
		setScratchItemFromOperand1ItemRule.setName("Set " + scratchItem.getName() + " from " + DataStore.EnquiryFilterParamGroupTemplate.OPERAND1_ITEM_NAME);
		setScratchItemFromOperand1ItemRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setScratchItemFromOperand1ItemRule.setPropertyName(scratchItem);
		setScratchItemFromOperand1ItemRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
		setScratchItemFromOperand1ItemRule.setFromPropertyNameFromEntityKey(operand1ItemPath);
		swapOperandsContainerRule.addTrueRule(setScratchItemFromOperand1ItemRule.unwrap());

		final SetValueRuleWrapper setOperand1ItemFromOperand2Rule = SetValueRuleWrapper.create(m_formContext);
		setOperand1ItemFromOperand2Rule.setName("Set " + DataStore.EnquiryFilterParamGroupTemplate.OPERAND1_ITEM_NAME + " from " + DataStore.EnquiryFilterParamGroupTemplate.OPERAND2_ITEM_NAME);
		setOperand1ItemFromOperand2Rule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setOperand1ItemFromOperand2Rule.setPropertyNameFromEntityKey(operand1ItemPath);
		setOperand1ItemFromOperand2Rule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
		setOperand1ItemFromOperand2Rule.setFromPropertyNameFromEntityKey(operand2ItemPath);
		swapOperandsContainerRule.addTrueRule(setOperand1ItemFromOperand2Rule.unwrap());

		final SetValueRuleWrapper setOperand2ItemFromScratchItem = SetValueRuleWrapper.create(m_formContext);
		setOperand2ItemFromScratchItem.setName("Set " + DataStore.EnquiryFilterParamGroupTemplate.OPERAND2_ITEM_NAME + " from " + scratchItem.getName());
		setOperand2ItemFromScratchItem.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setOperand2ItemFromScratchItem.setPropertyNameFromEntityKey(operand2ItemPath);
		setOperand2ItemFromScratchItem.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
		setOperand2ItemFromScratchItem.setFromPropertyName(scratchItem);
		swapOperandsContainerRule.addTrueRule(setOperand2ItemFromScratchItem.unwrap());

    	return topLevelContainerRule;
    }
    
    private void addEnquiryOptionsAndClearButtonElems(ItemGroupWrapper p_searchActionsItemGroup, RichHTMLPresentationItemGroupWrapper p_searchActionsPresItemGroup, EnquiryProcessorContext p_context)
    {
		final RichHTMLPresentationColumnBreakWrapper enquiryOptionsPresColumn = makePresentationColumnBreak("Enquiry Options And Clear Buttons Column"); 
		p_searchActionsPresItemGroup.addChild(enquiryOptionsPresColumn);

    	if (m_genOptions.searchGenerationOption.includeSortOptions())
    		addMoreOptionsButtonDataItemAndRules(p_searchActionsItemGroup, enquiryOptionsPresColumn, p_context);
    	
    	addClearSelectionButton(p_searchActionsItemGroup, enquiryOptionsPresColumn, p_context);
    }
    
    private void addMoreOptionsButtonDataItemAndRules(ItemGroupWrapper p_searchActionsItemGroup, RichHTMLPresentationColumnBreakWrapper p_enquiryOptionsPresColumn, EnquiryProcessorContext p_context)
    {
    	final PropertyWrapper sortOptionsHideShowStateItem = PropertyWrapper.create(m_formContext, DataStore.WorkingElementsGroup.EnquirySpecificSubGroupTemplate.ItemNames.SORT_OPTIONS_HIDE_SHOW_STATE);
    	sortOptionsHideShowStateItem.setType(m_globalContext.getShowOrHideListType().getEntityKeyName());
    	p_context.getEnquirySpecificWorkingElementsSubGroup().addChild(sortOptionsHideShowStateItem);
    	
		final FormButtonWrapper moreOptionsButton = FormButtonWrapper.create(m_formContext);
		moreOptionsButton.setActionCommand("More Options");
		moreOptionsButton.setDependencyType(FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE);
		p_searchActionsItemGroup.addChild(moreOptionsButton);
		
		final EvaluateRuleWrapper evalCurrentSortOptionsHideShowStateRule = EvaluateRuleWrapper.create(
			m_formContext,
			"If sort options are currently hidden",
			"$$" + sortOptionsHideShowStateItem.getEntityKeyName() + "$ != '" + Lists.ShowOrHide.Keys.SHOW + "'"
		);
		moreOptionsButton.addChild(evalCurrentSortOptionsHideShowStateRule);
		
		final SetValueRuleWrapper setSortOptionsVisibleRule = SetValueRuleWrapper.create(m_formContext);
		setSortOptionsVisibleRule.setName("Show the sort options");
		setSortOptionsVisibleRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setSortOptionsVisibleRule.setPropertyName(sortOptionsHideShowStateItem);
		setSortOptionsVisibleRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setSortOptionsVisibleRule.setFromValue(Lists.ShowOrHide.Keys.SHOW);
		setSortOptionsVisibleRule.setFromValueList(Lists.ShowOrHide.Keys.SHOW);
		evalCurrentSortOptionsHideShowStateRule.addTrueRule(setSortOptionsVisibleRule.unwrap());
		
		final SetValueRuleWrapper setSortOptionsHiddenRule = SetValueRuleWrapper.create(m_formContext);
		setSortOptionsHiddenRule.setName("Hide the sort options");
		setSortOptionsHiddenRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setSortOptionsHiddenRule.setPropertyName(sortOptionsHideShowStateItem);
		setSortOptionsHiddenRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setSortOptionsHiddenRule.setFromValue(Lists.ShowOrHide.Keys.HIDE);
		setSortOptionsHiddenRule.setFromValueList(Lists.ShowOrHide.Keys.HIDE);
		evalCurrentSortOptionsHideShowStateRule.addFalseRule(setSortOptionsHiddenRule.unwrap());

		final RichHTMLPresentationButtonWrapper moreOptionsPresButton = RichHTMLPresentationButtonWrapper.create(m_formContext, moreOptionsButton.unwrap());
		moreOptionsPresButton.setDisplayInAnswerColumn(Boolean.FALSE);
		moreOptionsPresButton.setDesignToUseFromEntityKey("Enquiry Options Column Button");
		moreOptionsPresButton.setLinkText("$%if " + sortOptionsHideShowStateItem.getEntityKeyName() + " != '" + Lists.ShowOrHide.Keys.SHOW + "'$Show$%else$Hide$%endif$ Options");
		p_enquiryOptionsPresColumn.addChild(moreOptionsPresButton);
    }
    
    private void addClearSelectionButton(ItemGroupWrapper p_searchActionsItemGroup, RichHTMLPresentationColumnBreakWrapper p_enquiryOptionsPresColumn, EnquiryProcessorContext p_context)
    {
		final FormButtonWrapper clearSearchParamsButton = FormButtonWrapper.create(m_formContext);
		clearSearchParamsButton.setActionCommand("Clear Selection");
		clearSearchParamsButton.setDependencyType(FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE);
		p_searchActionsItemGroup.addChild(clearSearchParamsButton);
		
		final PropertyGroupWrapper searchRequestGroup = p_context.getEnquirySearchRequestGroup();
		final ResetDataRuleWrapper resetEnquirySearchRequestGroupRule = ResetDataRuleWrapper.create(m_formContext);
		resetEnquirySearchRequestGroupRule.setName("Reset " + p_context.getIfpFriendlyEnquiryName() + ' ' + searchRequestGroup.getName() + " group");
		resetEnquirySearchRequestGroupRule.setResetPropertyGroup(searchRequestGroup);
		clearSearchParamsButton.addChild(resetEnquirySearchRequestGroupRule);
		
		final RichHTMLPresentationButtonWrapper clearSearchParamsPresButton = RichHTMLPresentationButtonWrapper.create(m_formContext, clearSearchParamsButton.unwrap());
		clearSearchParamsPresButton.setDisplayInAnswerColumn(Boolean.FALSE);
		clearSearchParamsPresButton.setDesignToUseFromEntityKey("Enquiry Options Column Button");
		p_enquiryOptionsPresColumn.addChild(clearSearchParamsPresButton);
    }
    
    private FormButtonWrapper addFindButton(GenericNodeWrapper<?> p_parentNode, PresentationObjectWrapper<?> p_parentPresNode)
    {
		// Add the "Find" button...
		
		final FormButtonWrapper findButton = FormButtonWrapper.create(m_formContext);
    	findButton.setActionCommand("Find");
    	findButton.setDependencyType(FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE);
    	p_parentNode.addChild(findButton);
    	
    	// Now add the corresponding presentation presentation elements...
    	
		final RichHTMLPresentationColumnBreakWrapper enqFindButtonPresColumn = makePresentationColumnBreak("Enquiry Find Button Column");
		p_parentPresNode.addChild(enqFindButtonPresColumn);
				
		final RichHTMLPresentationButtonWrapper findPresButton = RichHTMLPresentationButtonWrapper.create(m_formContext, findButton.unwrap());
		findPresButton.setDisplayInAnswerColumn(Boolean.FALSE);
		findPresButton.setDesignToUseFromEntityKey("Enquiry Find Button");
		enqFindButtonPresColumn.addChild(findPresButton);
		
		return findButton;
    }
    
    private void addEnquiryDescriptionElems(GenericNodeWrapper<?> p_parentNode, PresentationObjectWrapper<?> p_parentPresNode, EnquiryProcessorContext p_context)
    {
    	// Add the enquiry description heading...
    	
		final HeadingWrapper enqDescriptionHeading = HeadingWrapper.create(m_formContext);
        final TextTranslations enqDescriptionTranslations = TextTranslations.getLocalTranslations( p_context.getEdgeMapper(),
                                                                                                   p_context.getEnquiry().getDescription(),
                                                                                                   p_context.getEnquiry().getName() );

        p_context.getLanguageMapHelper().registerT24TextTranslations(enqDescriptionHeading, enqDescriptionTranslations); // Q. Is there a difference between m_enquiry.getDescription() (used here) and p_context.getEnquiry().getDescription() 
		
        enqDescriptionHeading.setHeaderText( enqDescriptionTranslations.getText() );
        enqDescriptionHeading.setHeaderLevel( HeadingWrapper.EHeaderLevel.HEADER_LEVEL3 );
        p_parentNode.addChild( enqDescriptionHeading );

    	// Now add the corresponding presentation presentation elements...
    	
		final RichHTMLPresentationColumnBreakWrapper enqDescriptionPresColumn = makePresentationColumnBreak("Enquiry Description Column");
		p_parentPresNode.addChild(enqDescriptionPresColumn);
		
		final RichHTMLPresentationQuestionWrapper enqDescriptionPresHeading = RichHTMLPresentationQuestionWrapper.create(m_formContext, enqDescriptionHeading.unwrap());
		enqDescriptionPresHeading.setDesignToUseFromEntityKey("Enquiry Description Heading");
		enqDescriptionPresColumn.addChild(enqDescriptionPresHeading);
    }
    
    private void addBasicSearchButtonRules(FormButtonWrapper p_invokeSearchButton, EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
		final IRISRequest irisRequestRule = new IRISRequest(m_formContext);
		irisRequestRule.setName("Invoke IRIS " + p_context.getIrisEnquiryResourceName() + " enquiry resource");
		irisRequestRule.setErrorHandlingType(ErrorHandlingItem.ON_ERROR_PASS_UP);
		irisRequestRule.setAttribute(IRISRequest.TARGET_URL, "$$!IRIS_URL$/" + p_context.getIrisEnquiryResourceName() + "()?$top=100");
		irisRequestRule.setAttribute(IRISRequest.TARGET_GROUP, p_context.getEnquirySearchResultGroup().getEntityKeyName());
		p_invokeSearchButton.addChild(irisRequestRule);
		
		addCommonIrisRequestChildRules(irisRequestRule, p_fieldsDigest, p_context);
    }
    
    private void addSelectionScreenButtonToSearchResultsItemGroup(EnquiryProcessorContext p_context)
    {
		final FormButtonWrapper showEnquirySearchItemGroupButton = FormButtonWrapper.create(m_formContext);
		showEnquirySearchItemGroupButton.setActionCommand("Selection Screen");
		showEnquirySearchItemGroupButton.setDependencyType(FormButtonWrapper.EDependencyType.NONE);
		p_context.getSearchResultsItemGroup().addChild(showEnquirySearchItemGroupButton);
		
		final ResetDataRuleWrapper clearEnquirySearchResponseGroupRule = ResetDataRuleWrapper.create(m_formContext);
		clearEnquirySearchResponseGroupRule.setName("Reset the enquiry results for " + p_context.getIfpFriendlyEnquiryName());
		clearEnquirySearchResponseGroupRule.setResetPropertyGroup(p_context.getEnquirySearchResponseGroup());
		showEnquirySearchItemGroupButton.addChild(clearEnquirySearchResponseGroupRule);

		final SetValueRuleWrapper setSearchResultsItemGroupVisibleRule = SetValueRuleWrapper.create(m_formContext);
		final PropertyWrapper displayedEnquiryItemGroupItem = p_context.getDisplayedEnquiryItemGroupItem();
		setSearchResultsItemGroupVisibleRule.setName("Set " + displayedEnquiryItemGroupItem.getName() + " for " + p_context.getIfpFriendlyEnquiryName() + " to " + Lists.DisplayedEnquiryItemGroup.Keys.SEARCH);
		setSearchResultsItemGroupVisibleRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setSearchResultsItemGroupVisibleRule.setPropertyName(displayedEnquiryItemGroupItem);
		setSearchResultsItemGroupVisibleRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setSearchResultsItemGroupVisibleRule.setFromValue(Lists.DisplayedEnquiryItemGroup.Keys.SEARCH);
		setSearchResultsItemGroupVisibleRule.setFromValueList(Lists.DisplayedEnquiryItemGroup.Keys.SEARCH);
		showEnquirySearchItemGroupButton.addChild(setSearchResultsItemGroupVisibleRule);
		
        final RichHTMLPresentationButtonWrapper showSearchItemGroupPresButton = RichHTMLPresentationButtonWrapper.create(m_formContext, showEnquirySearchItemGroupButton.unwrap());
        showSearchItemGroupPresButton.setDesignToUseFromEntityKey("Enquiry Results Selection Screen Button");
        showSearchItemGroupPresButton.setDisplayInAnswerColumn(Boolean.FALSE);
        showSearchItemGroupPresButton.setHintText( "$%SLANG Enquiry.SelectionScreenHint Selection Screen$" );
        p_context.getSearchResultsPresItemGroup().addChild(showSearchItemGroupPresButton);
        p_context.getSearchResultsPresItemGroup().addChild(makePresSpacingWithBankLine());
    }
    
    private PropertyWrapper addSearchParamOperandItem(String p_dataItemName, EdgeConnectDataType p_dataType, PropertyGroupWrapper p_filterParamSubGroup)
    {
    	final PropertyWrapper result = PropertyWrapper.create(m_globalContext.getFormContext(), p_dataItemName);
    	result.setType(p_dataType.getDataTypeName());
    	p_filterParamSubGroup.addChild(result);
    	return result;
    }

    private void processEnquiryResultsHeaderRowFields(EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
    	addResultsHeaderFooterRowDataItems(p_fieldsDigest.getEnqResultsHeaderRowSpec(), p_context);
    }

    private void processEnquiryResultsFooterRowFields(EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
    	addResultsHeaderFooterRowDataItems(p_fieldsDigest.getEnqResultsFooterRowSpec(), p_context);
    }

    private void addResultsHeaderFooterRowDataItems(ResultsHeaderFooterRowSpec p_resultsHeaderFooterRowSpec, EnquiryProcessorContext p_context)
    {
    	if (p_resultsHeaderFooterRowSpec != null)
    	{
    		p_resultsHeaderFooterRowSpec.addDataItemsTo(p_context.getEnquirySearchResultGroup(), m_formContext);
    	}
    }
    
	private void processEnquiryResultsTableFields(EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
    {
    	final EnquiryResultsTableField[] resultsTableFields = p_fieldsDigest.getColumnOrderedResultsTableFields();
    	final int numResultsTableFields = resultsTableFields.length;
    	
		addNoResultsFoundHeading(p_context.getSearchResultsItemGroup(), p_context.getSearchResultsPresItemGroup(), p_fieldsDigest, p_context);

		final FormTableWrapper enqResultsTable = FormTableWrapper.create(m_formContext);
		enqResultsTable.setTableTitle("EnquiryResults");
		enqResultsTable.setSelectorMand(Boolean.FALSE);
		enqResultsTable.setSelector(p_context.getResultsTableSelectorItem());
		p_context.getSearchResultsItemGroup().addChild(enqResultsTable);
		
		final RichHTMLPresentationTableWrapper enqResultsPresTable = RichHTMLPresentationTableWrapper.create(m_formContext, enqResultsTable.unwrap());
		enqResultsPresTable.setDesignToUseFromEntityKey("Enquiry Results Table");
		enqResultsPresTable.setTableSummary( "$%SLANG Enquiry.EnquiryResultsLabel Enquiry Results$" );
		p_context.getSearchResultsPresItemGroup().addChild(enqResultsPresTable);

		m_resultsTableQuestionByT24ColumnNumber = new TreeMap<Integer,QuestionWrapper>();
		
    	for (int i = 0; i < numResultsTableFields; ++i)
    	{
    		final EnquiryResultsTableField tableQuestionField = resultsTableFields[i];
    		final PropertyWrapper dataItem = updateSearchResultGroupFor(tableQuestionField, i, p_context);
    		final String questionItemPath = StringUtils.replaceOnce(dataItem.getEntityKeyName(), ".element[C]", ".element[1]"); 
    		
			final QuestionWrapper resultsTableQuestion = QuestionWrapper.create(m_formContext);
			resultsTableQuestion.setPropertyKeyFromEntityKey(questionItemPath);
			resultsTableQuestion.setMandatory(Boolean.FALSE);
			resultsTableQuestion.setReadOnly(Boolean.TRUE);
			resultsTableQuestion.setQuestionText(tableQuestionField.getLabelText());
			enqResultsTable.addChild(resultsTableQuestion);
			
			m_resultsTableQuestionByT24ColumnNumber.put(tableQuestionField.getT24ColumnNumber(), resultsTableQuestion);
		
			final RichHTMLPresentationQuestionWrapper resultsTablePresQuestion = RichHTMLPresentationQuestionWrapper.create(m_formContext, resultsTableQuestion.unwrap());
			enqResultsPresTable.addChild(resultsTablePresQuestion);

			p_context.getLanguageMapHelper().registerT24TextTranslations(resultsTableQuestion, tableQuestionField.getLabelTranslations());
    	}
    	
    	// Add the per-results-row "Action" question
    	
    	final QuestionWrapper resultsRowActionQuestion = QuestionWrapper.create(m_formContext);
    	resultsRowActionQuestion.setQuestionText("$%SLANG Enquiry.ResultsRowActionLabel Action$");
    	resultsRowActionQuestion.setPropertyKey(p_context.getResultRowActionItem());
    	resultsRowActionQuestion.setMandatory(Boolean.FALSE);
    	enqResultsTable.addChild(resultsRowActionQuestion);
    	
    	final RichHTMLPresentationQuestionWrapper resultsRowActionPresQuestion = RichHTMLPresentationQuestionWrapper.create(m_formContext, resultsRowActionQuestion.unwrap());
    	resultsRowActionPresQuestion.setDisplayType( "Drop down list" );

    	enqResultsPresTable.addChild(resultsRowActionPresQuestion);
    	
    	// Add the per-results-row "Go" button
    	
    	final FormButtonWrapper resultsRowGoButton = FormButtonWrapper.create(m_formContext);
		resultsRowGoButton.setActionCommand("Go");
		resultsRowGoButton.setDisableInput(true);
		resultsRowGoButton.setDependencyType(FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE);
		addResultsRowGoButtonRules(resultsRowGoButton, p_context);
		enqResultsTable.addChild(resultsRowGoButton);
		
		final RichHTMLPresentationButtonWrapper resultsRowGoPresButton = RichHTMLPresentationButtonWrapper.create(m_formContext, resultsRowGoButton.unwrap());
		resultsRowGoPresButton.setDisplayType("Use Link");
		resultsRowGoPresButton.setButtonStyle("LinkButton");
		enqResultsPresTable.addChild(resultsRowGoPresButton);
    }
    
    private void addResultsRowGoButtonRules(FormButtonWrapper p_goButton, EnquiryProcessorContext p_context)
    {
    	final SetValueRuleWrapper setCurrentSearchResultInstanceRule = SetValueRuleWrapper.create(m_formContext);
    	setCurrentSearchResultInstanceRule.setName("Set the current SearchResult instance to be the one for the row containing this question");
    	setCurrentSearchResultInstanceRule.setType(SetValueRuleWrapper.EType.DATA_GROUP_INSTANCE);
    	setCurrentSearchResultInstanceRule.setPropertyGroupInstanceName(p_context.getEnquirySearchResultGroup());
    	setCurrentSearchResultInstanceRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
    	setCurrentSearchResultInstanceRule.setFromPropertyName(p_context.getResultsTableSelectorItem());
    	p_goButton.addChild(setCurrentSearchResultInstanceRule);
    	
    	final String dummyResultsRowActionListKey = Lists.EnquirySpecificDrilldownActionTemplate.PLACEHOLDER_LIST_ITEM.KEY;
    	
    	final EvaluateRuleWrapper testResultRowActionAgainstConstValueRule = EvaluateRuleWrapper.create(m_formContext);
		testResultRowActionAgainstConstValueRule.setName("Test whether the " + dummyResultsRowActionListKey + " action was selected");
		testResultRowActionAgainstConstValueRule.setExpression("$$" + p_context.getResultRowActionItem().getEntityKeyName() + "$ == '" + dummyResultsRowActionListKey + "'");
		p_goButton.addChild(testResultRowActionAgainstConstValueRule);
		
		final String searchResponseGroupName = p_context.getEnquirySearchResponseGroup().getName();
		final String displayedEnquiryItemGroupItemName = p_context.getDisplayedEnquiryItemGroupItem().getName();
		final String dummyIfpFriendlyTargetEnquiryName = "MyTargetEnquiry";
		final String dummySearchResponseGroupPath = dummyIfpFriendlyTargetEnquiryName + "[1]." + searchResponseGroupName + "[1]";
		final String dummyTargetSearchResultGroupPath = dummySearchResponseGroupPath + '.' + p_context.getEnquirySearchResultGroup().getName() + "[1]";
		final String dummyEnquirySpecificWorkingElementsSubGroupPath = p_context.getWorkingElementsGroup().getEntityKeyName() + "." + dummyIfpFriendlyTargetEnquiryName + "[1]";
		final String dummySearchOutcomeItemPath = dummyEnquirySpecificWorkingElementsSubGroupPath + '.' + p_context.getSearchOutcomeItem().getName();
		final String dummyDisplayedEnquiryItemGroupItemPath = dummyEnquirySpecificWorkingElementsSubGroupPath + '.' + displayedEnquiryItemGroupItemName;
		
		final ResetDataRuleWrapper resetTargetEnquirySearchResponseGroup = ResetDataRuleWrapper.create(m_formContext);
		resetTargetEnquirySearchResponseGroup.setName("Reset " + searchResponseGroupName + " group for " + dummyIfpFriendlyTargetEnquiryName + " enquiry");
		resetTargetEnquirySearchResponseGroup.setResetPropertyGroupFromEntityKey(dummySearchResponseGroupPath);
		testResultRowActionAgainstConstValueRule.addTrueRule(resetTargetEnquirySearchResponseGroup.unwrap());
		
		final IRISRequest irisRequestRule = new IRISRequest(m_formContext);
		irisRequestRule.setName("Call IRIS for " + dummyIfpFriendlyTargetEnquiryName + " enquiry");
		irisRequestRule.setErrorHandlingType(ErrorHandlingItem.ON_ERROR_PASS_UP);
		irisRequestRule.setAttribute(IRISRequest.TARGET_URL, "$$!IRIS_URL$/<irisResourceNameForTargetEnquiry>?$filter=<odataFilterParamsGoHere>");
		irisRequestRule.setAttribute(IRISRequest.TARGET_GROUP, dummyTargetSearchResultGroupPath);
		testResultRowActionAgainstConstValueRule.addTrueRule(irisRequestRule);
		
		final EvaluateRuleWrapper testWhetherAnyResultsWereReturned = EvaluateRuleWrapper.create(m_formContext);
		testWhetherAnyResultsWereReturned.setName("Test whether any results were returned");
		testWhetherAnyResultsWereReturned.setExpression("$$" + dummyTargetSearchResultGroupPath + ".lastInstance()$ > '0'");
		irisRequestRule.addTrueRule(testWhetherAnyResultsWereReturned.unwrap(), false /* p_linkedObject */);
		
		final SetValueRuleWrapper setSearchOutcomeForTargetEnquiryToResultsFound = SetValueRuleWrapper.create(m_formContext);
		setSearchOutcomeForTargetEnquiryToResultsFound.setName("Set SearchOutcome for " + dummyIfpFriendlyTargetEnquiryName + " Enquiry to " + Lists.SearchOutcome.Keys.RESULTS_FOUND);
		setSearchOutcomeForTargetEnquiryToResultsFound.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setSearchOutcomeForTargetEnquiryToResultsFound.setPropertyNameFromEntityKey(dummySearchOutcomeItemPath);
		setSearchOutcomeForTargetEnquiryToResultsFound.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setSearchOutcomeForTargetEnquiryToResultsFound.setFromValue(Lists.SearchOutcome.Keys.RESULTS_FOUND);
		setSearchOutcomeForTargetEnquiryToResultsFound.setFromValueList(Lists.SearchOutcome.Keys.RESULTS_FOUND);
		testWhetherAnyResultsWereReturned.addTrueRule(setSearchOutcomeForTargetEnquiryToResultsFound.unwrap());

		final SetValueRuleWrapper setSearchOutcomeForTargetEnquiryToNoResultsFound = SetValueRuleWrapper.create(m_formContext);
		setSearchOutcomeForTargetEnquiryToNoResultsFound.setName("Set SearchOutcome for " + dummyIfpFriendlyTargetEnquiryName + " Enquiry to " + Lists.SearchOutcome.Keys.NO_RESULTS_FOUND);
		setSearchOutcomeForTargetEnquiryToNoResultsFound.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setSearchOutcomeForTargetEnquiryToNoResultsFound.setPropertyNameFromEntityKey(dummySearchOutcomeItemPath);
		setSearchOutcomeForTargetEnquiryToNoResultsFound.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setSearchOutcomeForTargetEnquiryToNoResultsFound.setFromValue(Lists.SearchOutcome.Keys.NO_RESULTS_FOUND);
		setSearchOutcomeForTargetEnquiryToNoResultsFound.setFromValueList(Lists.SearchOutcome.Keys.NO_RESULTS_FOUND);
		testWhetherAnyResultsWereReturned.addFalseRule(setSearchOutcomeForTargetEnquiryToNoResultsFound.unwrap());
		
		final SetValueRuleWrapper setDisplayedItemGroupToSearchResultsForTargetEnquiry = SetValueRuleWrapper.create(m_formContext);
		setDisplayedItemGroupToSearchResultsForTargetEnquiry.setName("Set " + displayedEnquiryItemGroupItemName + " for " + dummyIfpFriendlyTargetEnquiryName + " to " + Lists.DisplayedEnquiryItemGroup.Keys.SEARCH_RESULTS);
		setDisplayedItemGroupToSearchResultsForTargetEnquiry.setType(SetValueRuleWrapper.EType.DATA_ITEM);
		setDisplayedItemGroupToSearchResultsForTargetEnquiry.setPropertyNameFromEntityKey(dummyDisplayedEnquiryItemGroupItemPath);
		setDisplayedItemGroupToSearchResultsForTargetEnquiry.setFromType(SetValueRuleWrapper.EFromType.VALUE);
		setDisplayedItemGroupToSearchResultsForTargetEnquiry.setFromValue(Lists.DisplayedEnquiryItemGroup.Keys.SEARCH_RESULTS);
		setDisplayedItemGroupToSearchResultsForTargetEnquiry.setFromValueList(Lists.DisplayedEnquiryItemGroup.Keys.SEARCH_RESULTS);
		testResultRowActionAgainstConstValueRule.addTrueRule(setDisplayedItemGroupToSearchResultsForTargetEnquiry.unwrap());
    }
    
	private void addSearchFilterValuePresQuestion(QuestionWrapper p_filterValueQuestion, EnquiryFilterParamDef p_filterParamDef, RichHTMLPresentationItemGroupWrapper p_questionPresItemGroup)
	{
        final RichHTMLPresentationQuestionWrapper filterValuePresQuestion = RichHTMLPresentationQuestionWrapper.create(m_globalContext.getFormContext(), p_filterValueQuestion.unwrap());
		filterValuePresQuestion.setHideQuestion(Boolean.TRUE);
		filterValuePresQuestion.setQuestionNewLine(Boolean.FALSE);
		filterValuePresQuestion.setFieldSize(IntegerFactory.getInteger(20));
		filterValuePresQuestion.setDesignToUseFromEntityKey("Standard Question");
        
		final SearchParamDropdownInfo searchParamDropdownInfo = p_filterParamDef.getSearchParamDropdownInfo();
		
		final String oDataFieldName = p_filterParamDef.getODataFieldName();
		
		//TODO:check if entity name or question name has to be set.(figure if enquiry name of app field name has to be set)
		final String irisNameForField = MapperUtility.processT24NameToIRISName((oDataFieldName==null)?p_filterParamDef.getAppFieldName():oDataFieldName);
		
		final EdgeConnectDataType edgeConnectDataType = p_filterParamDef.getEdgeConnectDataType();
		
		final MdfProperty appFieldDef = p_filterParamDef.getAppFieldDefn();
		
		final String typeModifier = RichHelper.getTypeModifiers(appFieldDef);		
        
        final String businessType = RichHelper.getBusinessType(appFieldDef);
        
		if (searchParamDropdownInfo == null)
		{
			if (edgeConnectDataType == EdgeConnectDataType.DATE)
			{
	        	// Setting the date format as YYYY/MM/DD and removing the default icon
				filterValuePresQuestion.setDateFormatType( RichHTMLPresentationQuestionWrapper.EDateFormatType.SPECIFY_DATE_FORMAT );
	        	filterValuePresQuestion.setDateFormat(" True, False, /, Day, Text, , , /, Month, Text, , , /, Year, Text, , , False, False, :, Hours, Text, , , :, Minutes, Text, , , :, Seconds, Text, , , Specify, dd, Specify, mm, Specify, yyyy, Specify, hh, Specify, mm, Specify, ss, True, T, False");
	        	
	        	// Adding the custom parameters for calendarUI widget
				filterValuePresQuestion.setAttribute("DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|calendarUI v2");
				filterValuePresQuestion.setAttribute("DisplayTypeImageUrl", "images/calendarUI.gif");
				filterValuePresQuestion.setAttribute( "DisplayTypeTextInputCSSClassNames", irisNameForField);// Not really required as this is applied only to the hidden element and not to the visible element
			        
			}
			else if (edgeConnectDataType == EdgeConnectDataType.RELATIVEDATE)
            {
                
                filterValuePresQuestion.setAttribute( "IsEnquiry","YES" );                
                RichHelper.setRelativeCalendarWidget( filterValuePresQuestion,typeModifier, irisNameForField, false, filterValuePresQuestion.unwrap().getName());
                                
            }
            else if (edgeConnectDataType == EdgeConnectDataType.FREQUENCY)
            {
                filterValuePresQuestion.setAttribute( "IsEnquiry","YES" );
                RichHelper.setFrequencyControlWidget( filterValuePresQuestion, businessType, typeModifier, irisNameForField , false, false, filterValuePresQuestion.unwrap().getName());
            }			
			
			else
			{
				filterValuePresQuestion.setDisplayType("Text Input Field");
			}
		}
		
		else
		{
			filterValuePresQuestion.setAttribute("DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Dropdown v2");
			filterValuePresQuestion.setAttribute("DisplayTypeResourceURL", searchParamDropdownInfo.irisResourceName);
			filterValuePresQuestion.setAttribute("DisplayTypePrimaryKey", searchParamDropdownInfo.irisPrimaryKeyName);
		}
		
		p_questionPresItemGroup.addChild(filterValuePresQuestion);
	}
	
    private void registerEnquiryResultsHeaderLabels(EnquiryResultsHeaderLabel[] p_headerLabelSpecs, RichHTMLEnquiryLayoutHelper p_layoutHelper)
    {
    	final int numLabelSpecs = p_headerLabelSpecs.length;
    	
    	for (int i = 0; i < numLabelSpecs; ++i)
    	{
    		final EnquiryResultsHeaderLabel headerLabelSpec = p_headerLabelSpecs[i];
    		p_layoutHelper.addHeaderElement(new StaticHeaderLabelElement(headerLabelSpec.getT24ColumnNumber(), headerLabelSpec.getT24LineNumber(), headerLabelSpec.getLabelText()));
    	}
    }
    
    private void registerEnquiryResultsHeaderValuesAndCreateAssocDataItems(EnquiryResultsHeaderValue[] p_headerValueSpecs, RichHTMLEnquiryLayoutHelper p_layoutHelper, EnquiryProcessorContext p_context)
    {
    	final int numValueSpecs = p_headerValueSpecs.length;
    	
    	for (int i = 0; i < numValueSpecs; ++i)
    	{
    		final EnquiryResultsHeaderValue headerValueSpec = p_headerValueSpecs[i];
    		final PropertyWrapper dataItem = updateSearchResultGroupFor(headerValueSpec, p_context);
    		final String itemPath = StringUtils.replaceOnce(dataItem.getEntityKeyName(), ".element[C]", ".element[1]");
    		
    		p_layoutHelper.addHeaderElement(new DynamicHeaderValueElement(headerValueSpec.getT24ColumnNumber(), headerValueSpec.getT24LineNumber(), itemPath));
    	}
    }
    
    private PropertyWrapper updateSearchResultGroupFor(EnquiryResultsTableField p_tableQuestionField, int p_columnIndexForExport, EnquiryProcessorContext p_context)
    {
    	AssertionUtils.requireNonNull(p_tableQuestionField, "p_tableQuestionField");
    	
    	return updateSearchResultGroup(
    		p_tableQuestionField.getDataItemName(),
    		true, /* p_isDataItemValueExportable */
    		p_tableQuestionField.getLabelText(),
    		p_columnIndexForExport,
    		p_tableQuestionField.requiresMultiValueGroup(),
    		p_context
    	);
    }
    
    private PropertyWrapper updateSearchResultGroupFor(EnquiryResultsHeaderValue p_resultsHeaderValue, EnquiryProcessorContext p_context)
    {
    	return updateSearchResultGroupForNonExportableValue(
    		p_resultsHeaderValue.getDataItemName(),
    		p_resultsHeaderValue.requiresMultiValueGroup(),
    		p_context
    	);
    }
    		
    private PropertyWrapper updateSearchResultGroupForNonExportableValue(
    	String p_dataItemNameToAdd,
    	boolean p_isMultiValueGroupRequired,
    	EnquiryProcessorContext p_context
    ) {
    	return updateSearchResultGroup(
    		p_dataItemNameToAdd,
    		false, /* p_isDataItemValueExportable */
    		null, /* p_columnTitleForExport */
    		null, /* p_columnIndexForExport */
    		p_isMultiValueGroupRequired,
    		p_context
    	);
    }
    
    private PropertyWrapper updateSearchResultGroup(
    	String p_dataItemNameToAdd,
    	boolean p_isDataItemValueExportable,
    	String p_columnTitleForExport,
    	Integer p_columnIndexForExport,
    	boolean p_isMultiValueGroupRequired,
    	EnquiryProcessorContext p_context
    ) {
    	final PropertyGroupWrapper searchResultGroup = p_context.getEnquirySearchResultGroup();
    	final PropertyWrapper dataItem = PropertyWrapper.create(m_formContext, p_dataItemNameToAdd);
    	
    	if (p_isMultiValueGroupRequired)
    	{
			final String multiValuedFieldGroupName = p_context.getIfpFriendlyEnquiryName() + '_' + p_dataItemNameToAdd + EnquiryResultConstants.MULTI_VALUE_FIELD_GROUPNAME_SUFFIX;
			final PropertyGroupWrapper multiValuedFieldGroup = PropertyGroupWrapper.create(m_formContext, multiValuedFieldGroupName);
			
			multiValuedFieldGroup.setAttribute(EnquiryResultConstants.CustomAttrNames.IS_DATA_FOR_EXPORT, String.valueOf(p_isDataItemValueExportable));
			searchResultGroup.addChild(multiValuedFieldGroup);
			
			if (p_isDataItemValueExportable)
			{
				multiValuedFieldGroup.setAttribute(EnquiryResultConstants.CustomAttrNames.RESULT_COLUMN_TITLE, p_columnTitleForExport);
				multiValuedFieldGroup.setAttribute(EnquiryResultConstants.CustomAttrNames.RESULT_COLUMN_INDEX, p_columnIndexForExport.toString());
			}
			
			final PropertyGroupWrapper repeatableElementChildGroup = PropertyGroupWrapper.create(p_context.getFormContext(), "element");
			repeatableElementChildGroup.setFixedSize(Boolean.FALSE);
			repeatableElementChildGroup.addChild(dataItem);
			multiValuedFieldGroup.addChild(repeatableElementChildGroup);
    	}
    	
    	else
    	{
			dataItem.setAttribute(EnquiryResultConstants.CustomAttrNames.IS_DATA_FOR_EXPORT, String.valueOf(p_isDataItemValueExportable));
			
			if (p_isDataItemValueExportable)
    			dataItem.setAttribute(EnquiryResultConstants.CustomAttrNames.RESULT_COLUMN_TITLE, p_columnTitleForExport);

			searchResultGroup.addChild(dataItem);
    	}
    	
    	return dataItem;
    }

	private PropertyWrapper addSearchParamCompareOpItem(EnquiryFilterParamDef p_filterParamDef, PropertyGroupWrapper p_filterParamSubGroup, EnquiryProcessorContext p_context)
	{
		final PropertyWrapper searchParamCompareOpItem = PropertyWrapper.create(m_globalContext.getFormContext(), DataStore.EnquiryFilterParamGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME);
		final SearchParamComparisonOp[] paramSpecificComparisonOps = p_filterParamDef.getParamSpecificComparisonOps();
		
		final FormListWrapper listDef = (paramSpecificComparisonOps == null) ?
			findOrCreateAllSearchParamComparisonOperatorsList() : addSearchParamSpecificCompareOpList(p_filterParamDef, p_context);
				
		searchParamCompareOpItem.setType(listDef.getFullName());
		p_filterParamSubGroup.addChild(searchParamCompareOpItem);
		
		return searchParamCompareOpItem;
	}
	
	private FormListWrapper addSearchParamSpecificCompareOpList(EnquiryFilterParamDef p_filterParamDef, EnquiryProcessorContext p_context)
	{
		final FormListWrapper result = FormListWrapper.create(m_formContext, p_context.getIfpFriendlyEnquiryName() + " - " + p_filterParamDef.getODataFieldName() + "ComparisonOperators");
		final SearchParamComparisonOp[] searchParamComparisonOps = p_filterParamDef.getParamSpecificComparisonOps();
		final int numSearchParamComparisonOps = (searchParamComparisonOps == null) ? 0 : searchParamComparisonOps.length;
		
		for (int i = 0; i < numSearchParamComparisonOps; ++i)
		{
			final SearchParamComparisonOp searchParamComparisonOp = searchParamComparisonOps[i];
			final EdgeODataComparisonOpDef edgeODataComparisonOpDef = searchParamComparisonOp.getEdgeODataComparisonOpDef();
			
			if (edgeODataComparisonOpDef == null)
				continue;
			
			final EdgeListEntryDef edgeListEntryDef = edgeODataComparisonOpDef.getEdgeListEntryDef();
			
			result.addChild(ListItemWrapper.create(m_formContext, edgeListEntryDef.getKey(), edgeListEntryDef.getValue(), null));
		}
		
		m_globalContext.getListsRoot().addChild(result.unwrap());
		
		return result;
	}
	
	private FormListWrapper findOrCreateAllSearchParamComparisonOperatorsList() {
		if (m_allSearchParamComparisonOperatorsList == null)
		{
			FormList allSearchParamComparisonOperatorsList = m_formContext.getEntityofType(Lists.AllEnquirySearchComparisonOperators.FULLNAME, FormList.class);
			
			if (allSearchParamComparisonOperatorsList != null)
				m_allSearchParamComparisonOperatorsList = FormListWrapper.wrap(allSearchParamComparisonOperatorsList);
			
			else
			{
				m_allSearchParamComparisonOperatorsList = FormListWrapper.create(m_formContext, Lists.AllEnquirySearchComparisonOperators.FULLNAME);

				final int numEntries = EdgeODataComparisonOpDef.NUM_VALUES;
				
				for (int i = 0; i < numEntries; ++i)
				{
					final EdgeListEntryDef edgeListEntryDef = EdgeODataComparisonOpDef.VALUES[i].getEdgeListEntryDef();
					m_allSearchParamComparisonOperatorsList.addChild(ListItemWrapper.create(m_formContext, edgeListEntryDef.getKey(), edgeListEntryDef.getValue(), edgeListEntryDef.getGroupValue()));
				}
				
				m_globalContext.getListsRoot().addChild(m_allSearchParamComparisonOperatorsList.unwrap());
			}
		}
		
		return m_allSearchParamComparisonOperatorsList;
	}
	
	private void addNoResultsFoundHeading(GenericNodeWrapper<?> p_parentNode, PresentationObjectWrapper<?> p_parentPresNode, EnquiryFieldsDigest p_fieldsDigest, EnquiryProcessorContext p_context)
	{
		final String customHeadingText = StringUtils.trimEmptyToNull(p_fieldsDigest.getCustomNoResultsMsg());
		
		final HeadingWrapper noResultsHeading = HeadingWrapper.create(m_formContext);
		noResultsHeading.setHeaderText((customHeadingText == null) ? "No records matched the selection criteria" : customHeadingText);
		noResultsHeading.setHeaderLevel( HeadingWrapper.EHeaderLevel.HEADER_LEVEL2 );
		noResultsHeading.setNotApplicable( Boolean.TRUE );
		noResultsHeading.setConditionExpression("$$" + p_context.getSearchOutcomeItem().getEntityKeyName() + "$ == '" + Lists.SearchOutcome.Keys.NO_RESULTS_FOUND + "'");
		p_parentNode.addChild(noResultsHeading);

		final RichHTMLPresentationQuestionWrapper noResultsPresHeading = RichHTMLPresentationQuestionWrapper.create(m_formContext, noResultsHeading.unwrap());
		noResultsPresHeading.setDesignToUseFromEntityKey("Enquiry No Results Found Message");
		p_parentPresNode.addChild(noResultsPresHeading);
	}
	
		
    private RichHTMLPresentationSpacingWrapper makePresSpacingWithBankLine()
    {
    	return makePresSpacingWithBankLines(1);
    }
    
    private RichHTMLPresentationSpacingWrapper makePresSpacingWithBankLines(int p_numLines)
    {
    	RichHTMLPresentationSpacingWrapper result = RichHTMLPresentationSpacingWrapper.create(m_formContext);
    	result.setNumberBlankLines(p_numLines);
    	return result;
    }
    
    private RichHTMLPresentationSpacingWrapper makePresSpacingWithHorizontalLine(String p_spacingStyle)
    {
    	final RichHTMLPresentationSpacingWrapper result = makePresSpacingWithHorizontalLine();
    	
    	if (p_spacingStyle != null)
    		result.setSpacingStyle(p_spacingStyle);
    	
    	return result;
    }
    
    private RichHTMLPresentationSpacingWrapper makePresSpacingWithHorizontalLine()
    {
		final RichHTMLPresentationSpacingWrapper result = RichHTMLPresentationSpacingWrapper.create(m_formContext);
		result.setSpacingType(RichHTMLPresentationSpacingWrapper.ESpacingType.LINE);
		return result;
    }

    private RichHTMLPresentationColumnBreakWrapper makePresentationColumnBreak(String p_columnDesignName)
    {
		final RichHTMLPresentationColumnBreakWrapper result = RichHTMLPresentationColumnBreakWrapper.create(m_formContext);
		result.setDesignToUseFromEntityKey(p_columnDesignName);
		return result;
    }
}
