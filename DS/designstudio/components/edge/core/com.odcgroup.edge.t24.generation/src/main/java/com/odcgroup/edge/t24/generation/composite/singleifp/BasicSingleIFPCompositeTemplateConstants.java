package com.odcgroup.edge.t24.generation.composite.singleifp;

import com.temenos.connect.enquiry.EnquirySearchUrlBuilderRuleConstants;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public interface BasicSingleIFPCompositeTemplateConstants {
	public interface Lists
	{
		public interface AllEnquirySearchComparisonOperators
		{
			String FULLNAME = "AllEnquirySearchParamComparisonOperators";
		}
		

		public interface DisplayedEnquiryItemGroup
		{
			String FULLNAME = "DisplayedEnquiryItemGroup";
			
			public interface Keys
			{
				String SEARCH = "SEARCH";
				String SEARCH_RESULTS = "SEARCH_RESULTS";
			}
		}
		
		public interface EnquirySpecificDrilldownActionTemplate
		{
			public interface PLACEHOLDER_LIST_ITEM
			{
				String KEY = "DRILLDOWN_01";
				String VALUE = "Drilldown #1";
			}
		}
		
		public interface SearchOutcome
		{
			String FULLNAME = "SearchOutcome";
			
			public interface Keys
			{
				String RESULTS_FOUND = "RESULTS_FOUND";
				String NO_RESULTS_FOUND = "NO_RESULTS_FOUND";
			}
		}
		
		public interface ShowOrHide
		{
			String FULLNAME = "ShowOrHide";
			
			public interface Keys
			{
				String SHOW = "SHOW";
				String HIDE = "HIDE";
			}
		}
	
		public interface SortOrder
		{
			String FULLNAME = "SortOrder";
		}
		
		public interface TrueOrFalse
		{
			String FULLNAME = "TrueFalse";
			
			public interface Keys
			{
				String TRUE = "true";
				String FALSE = "false";
			}
		}
	}
	
	public interface Structures
	{
		public interface ColumnSortSpecType
		{
			String NAME = "ColumnSortSpecType";

			public interface ItemNames
			{
				String FIELD_NAME = EnquirySearchUrlBuilderRuleConstants.DataStore.ColumnSortSpecGroup.FIELD_NAME_ITEM_NAME;
				String SORT_ORDER = EnquirySearchUrlBuilderRuleConstants.DataStore.ColumnSortSpecGroup.SORT_ORDER_ITEM_NAME;
			}
		}

		public interface SearchRequestType
		{
			String NAME = "SearchRequestType";
			
			public interface FiltersGroup
			{
				String NAME = "Filters";
			}
			
			public interface OrderByGroup
			{
				String NAME = "OrderBy";
				
				public interface ColumnSortSpecGroup extends ColumnSortSpecType
				{
					String NAME = "ColumnSortSpec";
				}
			}
		}

		public interface VersionSetupParamsType
		{
			String NAME = "VersionSetupParamsType";
			
			public interface ItemNames
			{
				String VERSION_SETUP_REQUEST_NUMBER = "VersionSetupRequestNumber";
				String IFP_FRIENDLY_VERSION_NAME = "IfpFriendlyVersionName";
				String IRIS_INPUT_CONTEXT = "IRISInputContext";
			}
		}
	}
	
	public interface DataStore
	{
		public interface EnquiryFilterParamGroupTemplate extends com.temenos.connect.enquiry.EnquirySearchUrlBuilderRuleConstants.DataStore.FilterFieldGroup { }

		public interface EnquiryRequestResponseGroupTemplate
		{
			public interface SearchRequestGroup extends Structures.SearchRequestType
			{
				String NAME = "SearchRequest";
			}
			
			public interface SearchResponseGroup
			{
				String NAME = "SearchResponse";
				
				public interface SearchResultGroup
				{
					String NAME = "SearchResult";
					
					public interface ItemNames
					{
						String IS_HEADER_ROW = "IsHeaderRow";
					}
				}
			}
		}
		
		public interface WorkingElementsGroup
		{
			String NAME = "WorkingElements";
			String PATH = NAME + "[1]";
			
			public interface EnquirySpecificSubGroupTemplate
			{
				public interface ItemNames
				{
					String DISPLAYED_ENQUIRY_ITEM_GROUP = "DisplayedEnquiryItemGroup";
					String FULL_IRIS_SEARCH_URL = "FullIrisSearchUrl";
					String SCRATCH = "Scratch";
					String SORT_OPTIONS_HIDE_SHOW_STATE = "SortOptionsHideShowState";
					String SEARCH_OUTCOME = "SearchOutcome";
					String FINAL_RESULT_INSTANCE = "FinalResultInstance";
					String ALL_DISPLAYABLE_FINAL_RESULT_ROW_VALUES = "AllDisplayableFinalResultRowValues";
					String RESULTS_TABLE_SELECTOR = "SelectedSearchResultInstance";
				}
			}
		}
	}
	
	public interface Processes
	{
		public interface CompositeScreenProcess
		{
			String NAME = "CompositeScreen";
			
			public interface StartPhase
			{
				String NAME = "Start";
				String FULLNAME = CompositeScreenProcess.NAME + '.' + NAME;
			}
			
			public interface CompositeScreenPhase
			{
				String NAME = "Composite Screen";
				String FULLNAME = CompositeScreenProcess.NAME + '.' + NAME;
			}
		}
	}
}
