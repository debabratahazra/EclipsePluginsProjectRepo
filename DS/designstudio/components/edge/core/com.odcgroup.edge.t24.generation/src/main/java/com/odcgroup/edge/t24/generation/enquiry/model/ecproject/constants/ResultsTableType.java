package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import java.math.BigDecimal;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableColumnHeaderDisplayOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableRowColouringOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableRowRolloverOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableSelectedRowHighlightOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableSortableColumnsOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableStyleOption;

/**
 * <code>ResultsTableType</code> enumerates the types of enquiry results table recognised by the {@link com.odcgroup.edge.t24.generation.enquiry.BasicEnquiryProject BasicEnquiryProject} Enquiry IFP generator.<p><p>
 *
 * Each constant carries key information about the results table type it denotes.<p>
 * 
 * @author shayes
 */
public enum ResultsTableType
{
	ENQUIRY_HEADER_RESULTS("Enquiry Header Result", "DisplayEnquiryHeader"),
	DISPLAY_ONCE_RESULTS("Display Once Enquiry Results", "DisplayOnceResult"),
	MAIN_ENQUIRY_RESULTS("Main Enquiry Results", "DisplayMainResult"),
	/*
	 * 27/10/2014: SF reckons this might be a fossil - i.e. there's actually NO SUCH ANIMAL ! (and that DISPLAY_END_RESULTS should really be called FOOTER_RESULTS)
	 * ? Could maybe a fossil of my old header/footer row insertion into the single Enquiry results table
	 * TODO: confirm with Sudar
	 */
	FOOTER_RESULTS("Enquiry Footer Results", "DisplayFooterResult"),
	PAGE_THROW_RESULTS("Enquiry Page Throw Results", "DisplayPageThrowResult"),
	DISPLAY_END_RESULTS("Display End Results", "DisplayEndResult");
	
	private final String m_tableTitle;
	private final String m_displayResultGroupName;
	
	/** @return <code>true</code> if the column headers for a results table of <code>this</code> type should be displayed or <code>false</code> otherwise */
	public boolean areColumnHeadersDisplayable(EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
	    AssertionUtils.requireNonNull(p_enquiryAttrsDigest, "p_enquiryAttrsDigest");
	    return isMainEnquiryResults() && ! p_enquiryAttrsDigest.areResultsTableColumnHeadersSuppressed(); 
	}

	/** @return <code>true</code> if this is the {@link #ENQUIRY_HEADER_RESULTS} instance, or <code>false</code> otherwise */ 
	public boolean isEnquiryHeaderResults()
	{
		return (this == ENQUIRY_HEADER_RESULTS);
	}
	
	/** @return <code>true</code> if this is the {@link #DISPLAY_ONCE_RESULTS} instance, or <code>false</code> otherwise */ 
	public boolean isDisplayOnceResults()
	{
		return (this == DISPLAY_ONCE_RESULTS);
	}
	
	/** @return <code>true</code> if this is the {@link #FOOTER_RESULTS} instance, or <code>false</code> otherwise */ 
	public boolean isFooterResults()
	{
		return (this == FOOTER_RESULTS);
	}
	
	/** @return <code>true</code> if this is the {@link #PAGE_THROW_RESULTS} instance, or <code>false</code> otherwise */ 
	public boolean isPageThrowResults()
	{
		return (this == PAGE_THROW_RESULTS);
	}
	
	/** @return <code>true</code> if this is the {@link #DISPLAY_END_RESULTS} instance, or <code>false</code> otherwise */ 
	public boolean isDisplayEndResults()
	{
		return (this == DISPLAY_END_RESULTS);
	}
	
	/** @return <code>true</code> if this is the {@link #MAIN_ENQUIRY_RESULTS} instance, or <code>false</code> otherwise */ 
	public boolean isMainEnquiryResults()
	{
		return (this == MAIN_ENQUIRY_RESULTS);
	}
	
	public boolean offersEnquiryDrilldowns()
	{
		return isMainEnquiryResults();
	}
	
	public boolean supportsCollapsibleColumns()
	{
		return isClubbableColumnSetMember();
	}
	
	public boolean requiresTaggingOfCollapsibleTableColumnQuestions()
	{
	    return isMainEnquiryResults();
	}
	
	public boolean isClubbableColumnSetMember()
	{
		switch(this)
		{
			case DISPLAY_ONCE_RESULTS:
			case MAIN_ENQUIRY_RESULTS:
			case FOOTER_RESULTS:
			case PAGE_THROW_RESULTS:
				return true;
		}
		
		return false;
	}
	
	/** @return the title for the table denoted by this instance */
    public String getTableTitle()
    {
        return m_tableTitle;
    }
    
    /** @return the name to be used for the data store group containing the "displayable" values for a results table of the type denoted by this instance */
    public String getDisplayResultGroupName()
    {
        return m_displayResultGroupName;
    }
    
	public void applyPresentationOptionsTo(RichHTMLPresentationTableWrapper p_presTable, EnquiryAttrsDigest p_enquiryAttrsDigest, boolean p_areAllTableQuestionForSingleFooterSectionFields)
	{
		AssertionUtils.requireNonNull(p_presTable, "p_presTable");
        AssertionUtils.requireNonNull(p_enquiryAttrsDigest, "p_enquiryAttrsDigest");
		
		p_presTable.setDesignToUseFromEntityKey("Custom");
		p_presTable.setHideSelector(Boolean.TRUE);
		
		getEnquiryResultsTableStyle(p_enquiryAttrsDigest, p_areAllTableQuestionForSingleFooterSectionFields).applyTo(p_presTable);
		getTableColumnHeaderDisplayOption(p_enquiryAttrsDigest).applyTo(p_presTable);
		getResultsTableRowColouringOption(p_enquiryAttrsDigest).applyTo(p_presTable);
		getResultsTableSelectedRowHighlightOption(p_enquiryAttrsDigest).applyTo(p_presTable);
		getResultsTableRowRolloverOption(p_enquiryAttrsDigest).applyTo(p_presTable);
		getResultsTableSortableColumnsOption().applyTo(p_presTable);
		
		if (isEnquiryHeaderResults())
		    p_presTable.setCellPadding(BigDecimal.valueOf(0));
	}
	
	/** @return <code>true</code> if multiple instances of this table may be required (each holding display items for Enquiry fields having a common T24 line number), or <code>false</code> otherwise */
	public boolean requiresSeparateTablePerT24LineNumber()
	{
		return (this == ENQUIRY_HEADER_RESULTS);
	}
	
	private ResultsTableType(String p_tableTitle, String p_displayResultGroupName)
	{
		m_tableTitle = AssertionUtils.requireNonNullAndNonEmpty(p_tableTitle, "p_tableTitle");
		m_displayResultGroupName = AssertionUtils.requireNonNull(p_displayResultGroupName, "p_displayResultGroupName");
	}

	private ResultsTableStyleOption getEnquiryResultsTableStyle(EnquiryAttrsDigest p_enquiryAttrsDigest, boolean p_areAllTableQuestionForSingleFooterSectionFields)
	{
	    if (isEnquiryHeaderResults())
	        return ResultsTableStyleOption.ENQUIRY_HEADER_RESULTS_TABLE;
	    
	    if (p_enquiryAttrsDigest.isHeaderEnquiry())
	        return ResultsTableStyleOption.HEADER_ENQUIRY_RESULTS_TABLE;
	    
	    if (isDisplayEndResults())
	        return p_areAllTableQuestionForSingleFooterSectionFields ? ResultsTableStyleOption.DISPLAY_END_RESULTS_AS_MAIN_RESULTS_FOOTER_ROW : ResultsTableStyleOption.NON_PRIMPABLE_ENQUIRY_RESULTS_TABLE;
	    
	    return ResultsTableStyleOption.PRIMPABLE_ENQUIRY_RESULTS_TABLE;
	}
	
	private ResultsTableColumnHeaderDisplayOption getTableColumnHeaderDisplayOption(EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
		return areColumnHeadersDisplayable(p_enquiryAttrsDigest) ?
		    ResultsTableColumnHeaderDisplayOption.SHOW_TABLE_COLUMN_HEADERS : ResultsTableColumnHeaderDisplayOption.HIDE_TABLE_COLUMN_HEADERS;
	}
	
	private ResultsTableRowColouringOption getResultsTableRowColouringOption(EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
		return (p_enquiryAttrsDigest.useSingleBackgroundColourForResultsTable() && ! isEnquiryHeaderResults()) ? ResultsTableRowColouringOption.SINGLE_BACKGROUND_COLOUR :
		    (isMainEnquiryResults() || isDisplayEndResults()) ? ResultsTableRowColouringOption.HUMBUG_ROW_COLOURING : ResultsTableRowColouringOption.NO_ROW_COLOURING;
	}
	
	private ResultsTableSelectedRowHighlightOption getResultsTableSelectedRowHighlightOption(EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
		return (isMainEnquiryResults() && ! p_enquiryAttrsDigest.isHeaderEnquiry()) ? ResultsTableSelectedRowHighlightOption.SELECTED_ROW_HIGHLIGHTING_ENABLED : ResultsTableSelectedRowHighlightOption.SELECTED_ROW_HIGHLIGHTING_DISABLED;
	}
	
	private ResultsTableRowRolloverOption getResultsTableRowRolloverOption(EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
		return (isMainEnquiryResults() && ! p_enquiryAttrsDigest.isHeaderEnquiry()) ? ResultsTableRowRolloverOption.TABLE_ROW_ROLLOVER_ENABLED : ResultsTableRowRolloverOption.TABLE_ROW_ROLLOVER_DISABLED;
	}
	
	private ResultsTableSortableColumnsOption getResultsTableSortableColumnsOption()
	{
		return ResultsTableSortableColumnsOption.SORTABLE_COLUMNS_DISABLED;
	}
}
