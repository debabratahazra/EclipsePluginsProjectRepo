package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;

/**
 * <code>DisplayResultsTableWrapperPair</code> is the return type of <code>DisplayResultGroupSpec</code>'s {@link DisplayResultGroupSpec#generateResultsTable generateResultTable()} method.
 * 
 * It is an extended {@link TableWrapperPair} providing a couple of extra pieces of meta-information (modelled as <code>public final</code> fields for direct access) to save the caller
 * of the above <code>generateResultTable()</code> method from having to call another (necessarily state-dependent) method to retrieve that information.  
 *
 * @author shayes
 *
 */
public class DisplayResultsTableWrapperPair extends TableWrapperPair
{
    /** the {@link ResultsTableType type} of "display results table" implemented by this <code>DisplayResultsTableWrapperPair</code> instance */
    public final ResultsTableType resultsTableType;
    
    /** whether or not the generated "display results table" represented by this instance includes one or more table column questions that have been tagged as "collapsible" columns */ 
    public final boolean includesColumnQuestionsMarkedAsCollapsible;
    
    DisplayResultsTableWrapperPair(FormTableWrapper p_tableWrapper, RichHTMLPresentationTableWrapper p_presTableWrapper, ResultsTableType p_resultsTableType, boolean p_includesColumnQuestionsMarkedAsCollapsible)
    {
        super(
            AssertionUtils.requireNonNull(p_tableWrapper, "p_tableWrapper"),
            AssertionUtils.requireNonNull(p_presTableWrapper, "p_presTableWrapper")
        );
        
        resultsTableType = AssertionUtils.requireNonNull(p_resultsTableType, "p_resultsTableType");
        includesColumnQuestionsMarkedAsCollapsible = p_includesColumnQuestionsMarkedAsCollapsible;
    }
}
