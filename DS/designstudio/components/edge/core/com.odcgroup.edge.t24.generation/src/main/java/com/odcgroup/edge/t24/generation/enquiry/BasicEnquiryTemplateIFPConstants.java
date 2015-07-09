package com.odcgroup.edge.t24.generation.enquiry;

import com.temenos.connect.enquiry.EnquirySearchUrlBuilderRuleConstants;

/**
 * The <code>BasicEnquiryTemplateIFPConstants</code> defines constants for the names / paths of key elements assumed / required to be present in <code>templates/BasicEnquiryTemplate.ifp</code>
 * by {@link BasicEnquiryProject}.<p>
 * 
 * To take advantage of the auto-complete feature in eclipse, etc, the names are organised into sub-interfaces: {@link #Lists}, {@link #Structures}, {@link #DataStore}, {@link #Processes} and
 * {@link #TopLevelRuleNames}, each acting as a name-space and containing further sub-interfaces as required.
 *
 * @author Simon Hayes
 *
 */
public interface BasicEnquiryTemplateIFPConstants
{
	public interface Lists
	{
		public interface AllFilterFields
		{
			String FULLNAME = "AllFilterFields";
		}
		
		public interface AllSearchComparisonOperators
		{
			String FULLNAME = "AllSearchParamComparisonOperators";
		}
		
		public interface AutoRefreshButtonOperations
		{
		    String FULLNAME = "AutoRefreshButtonOperations";
		    
		    public interface Keys
		    {
		        String START = "START";
		        String STOP = "STOP";
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
		public interface LinkType
		{
			String NAME = "LinkType";
			String PATH = NAME + "[1]";
		}
		
		public interface SearchRequestType
		{
			String NAME = "SearchRequestType";
			String PATH = NAME + "[1]";
			
			public interface AutoRefreshIntervalSecondsItem
			{
			    String NAME = "AutoRefreshIntervalSeconds";
			    String PATH = SearchRequestType.PATH + '.' + NAME;
			}
			
			public interface FiltersGroup
			{
				String NAME = "Filters";
				String PATH = SearchRequestType.PATH + '.' + NAME + "[1]";
			}
			
			public interface OrderByGroup
			{
				String NAME = "OrderBy";
				String PATH = SearchRequestType.PATH + '.' + NAME + "[1]";
				
				public interface ColumnSortSpecGroup
				{
					String NAME = "ColumnSortSpec";
					String PATH = OrderByGroup.PATH + '.' + NAME + "[1]";
					
					String FIELD_NAME_ITEM_NAME = EnquirySearchUrlBuilderRuleConstants.DataStore.ColumnSortSpecGroup.FIELD_NAME_ITEM_NAME;
					String SORT_ORDER_ITEM_NAME = EnquirySearchUrlBuilderRuleConstants.DataStore.ColumnSortSpecGroup.SORT_ORDER_ITEM_NAME;
				}
			}
		}
	}
	
	public interface DataStore
	{
		public interface SearchParamFilterGroupTemplate extends com.temenos.connect.enquiry.EnquirySearchUrlBuilderRuleConstants.DataStore.FilterFieldGroup { }
		
		public interface LogicalScreenModel
		{
			String NAME = "LogicalScreenModel";
			String PATH = NAME + "[1]";
			
			public interface ItemPaths
			{
				String IRIS_INPUT_CONTEXT = LogicalScreenModel.PATH + ".IRISInputContext";
				String IRIS_OUTPUT_CONTEXT = LogicalScreenModel.PATH + ".IRISOutputContext";
				String IRIS_RELATION = LogicalScreenModel.PATH + ".IRISRelation";
			}
		}
		
		public interface EnquiryGroup
		{
			String NAME = "BasicEnquiry";
			String PATH = NAME + "[1]";
			
			public interface SearchRequestGroup
			{
				String NAME = "SearchRequest";
				String PATH = EnquiryGroup.PATH + '.' + NAME + "[1]";
				
				public interface AutoRefreshIntervalSecondsItem
				{
				    String NAME = Structures.SearchRequestType.AutoRefreshIntervalSecondsItem.NAME;
				    String PATH = SearchRequestGroup.PATH + '.' + NAME;
				}
				
				public interface FiltersGroup
				{
					String NAME = Structures.SearchRequestType.FiltersGroup.NAME;
					String PATH = SearchRequestGroup.PATH + '.' + NAME + "[1]";
				}
				
				public interface OrderByGroup
				{
					String NAME = Structures.SearchRequestType.OrderByGroup.NAME;
					String PATH = SearchRequestGroup.PATH + '.' + NAME + "[1]";
					
					public interface ColumnSortSpecGroup
					{
						String NAME = Structures.SearchRequestType.OrderByGroup.ColumnSortSpecGroup.NAME;
						String PATH = OrderByGroup.PATH + '.' + NAME + "[1]";
						
						String FIELD_NAME_ITEM_NAME = Structures.SearchRequestType.OrderByGroup.ColumnSortSpecGroup.FIELD_NAME_ITEM_NAME;
						String SORT_ORDER_ITEM_NAME = Structures.SearchRequestType.OrderByGroup.ColumnSortSpecGroup.SORT_ORDER_ITEM_NAME;
					}
				}
			}

			public interface SearchResponseGroup
			{
				String PATH = EnquiryGroup.PATH + ".SearchResponse[1]";
				
				public interface SearchResultGroup
				{
					String NAME = "SearchResult";
					String PATH = SearchResponseGroup.PATH + '.' + NAME + "[1]";
				}
				
				public interface ItemNames
				{
					String IS_HEADER_ROW = "IsHeaderRow";
				}
			}
		}
		
		public interface SavedSearchesGroup
		{
			String NAME = "SavedSearches";
			String PATH = NAME + "[1]";
			
			public interface SavedSearchGroup
			{
				String NAME = "SavedSearch";
				String PATH = SavedSearchesGroup.PATH + '.' + NAME + "[1]";
				String CURRENT_INSTANCE_PATH = SavedSearchesGroup.PATH + '.' + NAME + "[C]";
				
				public interface SearchNameItem
				{
					String NAME = "SearchName";
					String PATH = SavedSearchGroup.PATH + '.' + NAME;
					String CURRENT_INSTANCE_PATH = SavedSearchGroup.CURRENT_INSTANCE_PATH + '.' + NAME;
				}

				public interface SearchRequestGroup
				{
					String NAME = "SearchRequest";
					String PATH = SavedSearchGroup.PATH + '.' + NAME + "[1]";
					String CURRENT_INSTANCE_PATH = SavedSearchGroup.CURRENT_INSTANCE_PATH + '.' + NAME + "[1]";

					public interface AutoRefreshIntervalSecondsItem
					{
					    String NAME = "AutoRefreshIntervalSeconds";
					    String PATH = SearchRequestGroup.PATH + '.' + NAME;
					    String CURRENT_INSTANCE_PATH = SearchRequestGroup.CURRENT_INSTANCE_PATH + '.' + NAME;
					}
					
					public interface FiltersGroup
					{
						String NAME = Structures.SearchRequestType.FiltersGroup.NAME;
						String PATH = SearchRequestGroup.PATH + '.' + NAME + "[1]";
						String CURRENT_INSTANCE_PATH = SearchRequestGroup.CURRENT_INSTANCE_PATH + '.' + NAME + "[1]";
					}
					
					public interface OrderByGroup
					{
						String NAME = Structures.SearchRequestType.OrderByGroup.NAME;
						String PATH = SearchRequestGroup.PATH + '.' + NAME + "[1]";
						String CURRENT_INSTANCE_PATH = SearchRequestGroup.CURRENT_INSTANCE_PATH + '.' + NAME + "[1]";
						
						public interface ColumnSortSpecGroup
						{
							String NAME = Structures.SearchRequestType.OrderByGroup.ColumnSortSpecGroup.NAME;
							String PATH = OrderByGroup.PATH + '.' + NAME + "[1]";
							String CURRENT_INSTANCE_PATH = OrderByGroup.CURRENT_INSTANCE_PATH + '.' + NAME + "[C]";
							
							public interface FieldNameItem
							{
								String NAME = Structures.SearchRequestType.OrderByGroup.ColumnSortSpecGroup.FIELD_NAME_ITEM_NAME;
								String PATH = ColumnSortSpecGroup.PATH + '.' + NAME;
								String CURRENT_INSTANCE_PATH = ColumnSortSpecGroup.CURRENT_INSTANCE_PATH + '.' + NAME;
							}
							
							public interface SortOrderItem
							{
								String NAME = Structures.SearchRequestType.OrderByGroup.ColumnSortSpecGroup.SORT_ORDER_ITEM_NAME;
								String PATH = ColumnSortSpecGroup.PATH + '.' + NAME;
								String CURRENT_INSTANCE_PATH = ColumnSortSpecGroup.CURRENT_INSTANCE_PATH + '.' + NAME;
							}
						}
					}
				}
			}
		}
		
		public interface WorkingElementsGroup
		{
			String NAME = "WorkingElements";
			String PATH = NAME + "[1]";
			
			public interface ItemPaths
			{
			    String BASE_IRIS_SEARCH_URL = WorkingElementsGroup.PATH + ".BaseIrisSearchUrl";
				String DOWNLOAD_FILE_PATH = WorkingElementsGroup.PATH + ".DownloadFilePath";
				String FILE_DOWNLOAD_PARAMS_SESSION_ATTR_NAME = WorkingElementsGroup.PATH + ".FileDownloadParamsSessionAttrName";
				String FINAL_RESULT_INSTANCE = WorkingElementsGroup.PATH + ".FinalResultInstance"; 
				String FULL_IRIS_SEARCH_URL = WorkingElementsGroup.PATH + ".FullIrisSearchUrl";
                String GENERIC_RESULTS_SCREEN_ACTION = WorkingElementsGroup.PATH + ".ResultScreenAction";
				String IFP_FRIENDLY_ENQUIRY_NAME = WorkingElementsGroup.PATH + ".IfpFriendlyEnquiryName";
				String INVOKED_IN_DRILLDOWN_CONTEXT = WorkingElementsGroup.PATH + ".InvokedInDrilldownContext";
				String IRIS_RELATION_ON_ENTRY = WorkingElementsGroup.PATH + ".IRISRelationOnEntry";
				String LAST_USED_SEARCH_NAME = WorkingElementsGroup.PATH + ".LastUsedSearchName";
				String NEW_SEARCH_NAME = WorkingElementsGroup.PATH + ".NewSearchName";
                String NEXT_PAGE_INSTANCE = WorkingElementsGroup.PATH + ".NextPageInstance";
				String RESULTS_SCREEN_SEARCH_ELEMS_HIDE_SHOW_STATE = WorkingElementsGroup.PATH + ".ResultScreenSearchElemsHideShowState";
				String RESULTS_TABLE_SELECTOR = WorkingElementsGroup.PATH + ".SelectedSearchResultInstance";
                String SAVED_SEARCH_NAME_TO_UPDATE = WorkingElementsGroup.PATH + ".SavedSearchNameToUpdate";
				String SAVE_SEARCH_DIALOG_HIDE_SHOW_STATE = WorkingElementsGroup.PATH + ".SaveSearchDialogHideShowState";
				String SAVE_SEARCH_MODE = WorkingElementsGroup.PATH + ".SaveSearchMode";
				String SCRATCH = WorkingElementsGroup.PATH + ".Scratch";
				String SCRATCH_SEARCH_NAME_LIST = WorkingElementsGroup.PATH + ".ScratchSearchNameListItem";
                String SEARCH_OUTCOME = WorkingElementsGroup.PATH + ".SearchOutcome";
				String SELECTED_SAVED_SEARCH_INSTANCE = WorkingElementsGroup.PATH + ".SelectedSavedSearchInstance";
				String SELECTION_ELEMS_HIDE_SHOW_STATE_ITEM = WorkingElementsGroup.PATH + ".SelectionElemsHideShowState";
				String SORT_OPTIONS_HIDE_SHOW_STATE = WorkingElementsGroup.PATH + ".SortOptionsHideShowState";
                String TOP_LEVEL_LINKS_ACTION = WorkingElementsGroup.PATH + ".Action";
			}
			
			public interface TopLevelLinksGroup
			{
				String NAME = "TopLevelLinks";
				String PATH = WorkingElementsGroup.PATH + '.' + NAME + "[1]";
				
				public interface LinksGroup
				{
					String NAME = "Links";
					String PATH = TopLevelLinksGroup.PATH + '.' + NAME + "[1]";
					String CURRENT_INSTANCE_PATH = TopLevelLinksGroup.PATH + '.' + NAME + "[C]";
				}
			}
			
			public interface VersionSetupParamsGroup
			{
				String NAME = "VersionSetupParams";
				String PATH = WorkingElementsGroup.PATH + '.' + NAME + "[1]";
			}
		}
		
		public interface DrilldownMapGroup
		{
			String NAME = "DrilldownMap";
			String PATH = NAME + "[1]";
			String CURRENT_INSTANCE_PATH =  NAME + "[C]";
			
			public interface ItemPaths
			{
				String ID = DrilldownMapGroup.PATH + ".Id";
				String CURRENT_INSTANCE_ID= DrilldownMapGroup.CURRENT_INSTANCE_PATH + ".Id";
				String IMAGE_PATH = DrilldownMapGroup.PATH + ".ImagePath";
				String CURRENT_INSTANCE_IMAGE_PATH= DrilldownMapGroup.CURRENT_INSTANCE_PATH + ".ImagePath";

				String REFCOL_PATH = DrilldownMapGroup.PATH + ".RefCol";
				String CURRENT_INSTANCE_REFCOL_PATH= DrilldownMapGroup.CURRENT_INSTANCE_PATH + ".RefCol";
			}
		}
		
		public interface AutoRefreshGroup
		{
		    String NAME = "AutoRefresh";
		    String PATH = NAME + "[1]";
		    
		    public interface ItemPaths
		    {
		        String BUTTON_OPERATION = AutoRefreshGroup.PATH + ".ButtonOperation";
		        String BUTTON_IMAGE_FILENAME = AutoRefreshGroup.PATH + ".ButtonImageFilename";
		        String INTERVAL_INPUT_AND_COUNTDOWN_DISPLAY_ITEM = AutoRefreshGroup.PATH + ".IntervalInputAndCountdownDisplayItem";
		    }
		}
	}
	
	public interface Processes
	{
		public interface EnquiryProcess
		{
			String NAME = "BasicEnquiry";
		
			public interface StartPhase
			{
				String NAME = "Start";
				String FULLNAME = EnquiryProcess.NAME + '.' + NAME;
			}
			
			public interface SearchPhase
			{
				String NAME = "Search";
				String FULLNAME = EnquiryProcess.NAME + '.' + NAME;
			}
			
			public interface SearchResultsPhase
			{
				String NAME = "Search Results";
				String FULLNAME = EnquiryProcess.NAME + '.' + NAME;
			}
		}
	}

	public interface Integration
	{
		public interface XML
		{
			public interface SavedSearches
			{
				String SOURCE_NAME = "Favourites XML File";
				String INTERFACE_NAME = "Saved Searches";
			}
			
			public interface SearchResults
			{
				String SOURCE_NAME = "Enquiry Results XML File";
				String INTERFACE_NAME = "Search Results";
			}
		}
	}
	public interface TopLevelRuleNames
	{
		String ADD_FAVOURITE_BUTTON_CONTAINER_RULE = "Add Favourite button rules";
		String ADJUST_INPUT_STATES_FOR_AUTO_REFRESH_ACTIVATED = "Adjust input states for Auto Refresh ACTIVATED";
		String ADJUST_INPUT_STATES_FOR_AUTO_REFRESH_DEACTIVATED = "Adjust input states for Auto Refresh DEACTIVATED";
		String AUTO_REFRESH_STATE_ENTRY_CONTAINER_RULE = "Auto Refresh state ENTRY rules";
        String AUTO_REFRESH_STATE_EXIT_CONTAINER_RULE = "Auto Refresh state EXIT rules";
		String AUTO_REFRESH_TOGGLE_BUTTON_CONTAINER_RULE = "Auto Refresh Toggle Button Rules";
		String CLEAR_BUTTON_CONTAINER_RULE = "Clear button rules";
		String COPY_SEARCH_PARAMS_TO_CURRENT_SAVED_SEARCH_INSTANCE_CONTAINER_RULE = "Copy search params to current SavedSearch instance container";
		String CSV_FILE_GENERATION_CONTAINER_RULE = "CSV File Generation rule container";
		String DELETE_SAVED_SEARCH_CONTAINER_RULE = "Delete saved search rules";
		String FIND_BUTTON_CONTAINER_RULE = "Find button rules";
		String LOAD_AND_EXEC_SAVED_SEARCH_CONTAINER_RULE = "Load and exec saved search rules";
		String LOAD_RESULTS_SCREEN_AUTO_REFRESH_VALUE_FROM_SEARCH_PARAM = "Load IntervalInputAndCountdownDisplayItem from search parameter AutoRefreshIntervalSeconds";
		String MORE_OPTIONS_BUTTON_CONTAINER_RULE = "More options button rules";
		String PREPROCESS_FILTER_OPERANDS_CONTAINER_RULE = "Filter operands preprocessing rules";
		String REPORT_DUPLICATE_SEARCH_NAME_ERROR_RULE = "Report duplicate search name error container";
		String RESULTS_SCREEN_ACTION_QLR_CONTAINER_RULE = "Result screen Action QLR container";
		String SAVE_RESULTS_SCREEN_AUTO_REFRESH_VALUE_TO_SEARCH_PARAM = "Store IntervalInputAndCountdownDisplayItem value as search parameter AutoRefreshIntervalSeconds";
		String SAVE_SEARCH_AS_FAVOURITE_CONTAINER_RULE = "Save search as favourite rules";
		String STARTUP_RULES_CONTAINER_RULE = "Startup Rules";
		String REFRESH_PAGE_CONTAINER_RULE = "Refresh Enquiry";
	}
}
