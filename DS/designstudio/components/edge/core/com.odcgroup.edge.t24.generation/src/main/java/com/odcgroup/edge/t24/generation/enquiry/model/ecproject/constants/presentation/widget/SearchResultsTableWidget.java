package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.TableWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;

/**
 * The singleton <code>SearchResultsTableWidget</code> {@link #INSTANCE instance} aims to simplify the process of {@link #applyTo(RichHTMLPresentationTableWrapper, Params) configuring}
 * a {@link RichHTMLPresentationTableWrapper} to use Sudar's {@value #WIDGET_NAME} widget.<p>
 * 
 * To use it:<p>
 * 
 * <ol>
 *     <li>Create a new {@link SearchResultsTableWidget#Params} object</li>
 *     <li>Call setters on that to override defaults as required</li>
 *     <li>Call the {@link #applyTo(RichHTMLPresentationTableWrapper, Params) apply} method to configure your target {@link RichHTMLPresentationTableWrapper}</li>
 * </ol><p>
 *
 * @author Simon Hayes
 */
public class SearchResultsTableWidget extends AbstractWidgetWithParameters<RichHTMLPresentationTableWrapper,SearchResultsTableWidget.Params>
{
	public static final String WIDGET_NAME = "SearchResult";
	
	public static final SearchResultsTableWidget INSTANCE = new SearchResultsTableWidget();
	
	private static interface WidgetAttrNames
	{
		String DISPLAY_END_RESULTS_TABLE_RUNTIME_ID = "DisplayEndRuntimeId";
		
		//TODO: As soon as we stop supporting the old legacy stuff in our generated IFPs, we ought to rename the underlying attribute in widgets.xml as "IsTreeEnquiry".
		String IS_TREE_ENQUIRY = "IsPagebreakEnquiry";
		
		String HAS_DISPLAY_END_TABLE = "HasFooterTable";
		String HAS_COLLAPSIBLE_COLUMNS = "HasCollapsibleColumns";
		String GROUPING_KEY = "GroupingKey";
	}
	
	private enum YesNoValue
	{
		YES, NO;
		
		static YesNoValue forBoolean(boolean p_boolValue)
		{
			return p_boolValue ? YES : NO;
		}
	}
	
	/**
	 * <code>Params</code> represents the set of parameters name/value pairs that the widget accepts, providing type-safe setter(s) with logical names for clarity-of-intent.
	 */
	public static class Params extends WidgetParamValues<RichHTMLPresentationTableWrapper>
	{
		/**
		 * Initializes a new instance where all boolean-valued properties are initially set <code>false</code>, and any other properties are logically null.
		 */
		public Params()
		{
			setIsTreeEnquiry(false);
			setHasDisplayEndTable(false);
			setHasCollapsibleColumns(false);
		}
		
		public void setDisplayEndResultsTable(TableWrapperPair p_displayEndResultsTableWrapperPair)
		{
			String displayOnceResultsTableRuntimeId = null;
			boolean hasDisplayEndTable = false; 
			
			if (p_displayEndResultsTableWrapperPair != null)
			{
				final String expectedFormTableTitle = ResultsTableType.DISPLAY_END_RESULTS.getTableTitle();
				final String actualFormTableTitle = p_displayEndResultsTableWrapperPair.wrapperObject.unwrap().getTableTitle();
				
				if (! expectedFormTableTitle.equals(actualFormTableTitle))
					throw new IllegalArgumentException("p_displayEndResultsTableWrapperPair.formTable.getTableTitle() -> " + StringUtils.quoteIfString(actualFormTableTitle) + " (\"" + expectedFormTableTitle + "\" expected !)");
				
				displayOnceResultsTableRuntimeId = StringUtils.trimEmptyToNull((String) p_displayEndResultsTableWrapperPair.presWrapperObject.unwrap().getAttribute("RuntimeId"));
				
				if (displayOnceResultsTableRuntimeId == null)
					throw new IllegalStateException("Runtime ID not set on: p_displayEndResultsTableWrapperPair.presTableWrapper !");
				
				hasDisplayEndTable = true;
			}
			
			setWidgetParamValue(WidgetAttrNames.DISPLAY_END_RESULTS_TABLE_RUNTIME_ID, displayOnceResultsTableRuntimeId);
			setHasDisplayEndTable(hasDisplayEndTable);
		}
		
		public void setIsTreeEnquiry(boolean p_isTrue)
		{
			setWidgetParamValue(WidgetAttrNames.IS_TREE_ENQUIRY, YesNoValue.forBoolean(p_isTrue).toString());			
		}
		
		public void setHasCollapsibleColumns(boolean p_isTrue)
		{
			setWidgetParamValue(WidgetAttrNames.HAS_COLLAPSIBLE_COLUMNS, YesNoValue.forBoolean(p_isTrue).toString());
		}
		
		public void setGroupingKeyItemForJQueryDataTable(PropertyWrapper p_groupingKeyItem)
		{
			final String groupingItemPath = (p_groupingKeyItem == null) ? null : p_groupingKeyItem.getEntityKeyName();
				
			setWidgetParamValue(WidgetAttrNames.GROUPING_KEY, groupingItemPath);
		}
		
		private void setHasDisplayEndTable(boolean p_isTrue)
		{
			setWidgetParamValue(WidgetAttrNames.HAS_DISPLAY_END_TABLE, YesNoValue.forBoolean(p_isTrue).toString());
		}
	}

	private SearchResultsTableWidget()
	{
		super(WIDGET_NAME);
	}
}
