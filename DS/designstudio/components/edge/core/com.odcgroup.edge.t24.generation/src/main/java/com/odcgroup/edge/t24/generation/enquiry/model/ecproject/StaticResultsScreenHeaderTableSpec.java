package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import java.util.SortedMap;
import java.util.TreeMap;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.StaticResultsScreenHeaderControlElems;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.EnquiryStyleStrings;


/**
 * A <code>StaticResultsScreenHeaderTableSpec</code> is a {@link #generateStaticHeaderTable "materializable"} specification for a single "static headers table" within an owning
 * {@link StaticResultsScreenHeadersModel}.<p>
 * 
 * Internally it is little more than a T24-column-number-indexed collection of {@link StaticResultsScreenHeaderTableQuestionSpec}(s).
 *
 * @author Simon Hayes
 */
public class StaticResultsScreenHeaderTableSpec
{
    private final int m_t24LineNumber;
    private final SortedMap<Integer,StaticResultsScreenHeaderTableQuestionSpec> m_headerTableQuestionSpecByT24ColumnNumber = new TreeMap<Integer,StaticResultsScreenHeaderTableQuestionSpec>();
    
    public void addTableColumnQuestionSpecFor(int p_t24ColumnNumber, String p_displayText)
    {
        AssertionUtils.requireNonNull(p_displayText, "p_displayText");
        m_headerTableQuestionSpecByT24ColumnNumber.put(p_t24ColumnNumber, new StaticResultsScreenHeaderTableQuestionSpec(m_t24LineNumber, p_t24ColumnNumber, p_displayText));
    }
    
    public TableWrapperPair generateStaticHeaderTable(
        int p_staticHeaderTableNumber,
        StaticResultsScreenHeaderControlElems p_controlElems,
        EnquiryAttrsDigest p_enquiryAttrsDigest,
        FormContext p_formContext
    ) {
        AssertionUtils.requireNonNull(p_controlElems, "p_controlElems");
        AssertionUtils.requireNonNull(p_formContext, "p_formContext");
        AssertionUtils.requireNonNull(p_enquiryAttrsDigest, "p_enquiryAttrsDigest");
        
        final FormTableWrapper formTable = FormTableWrapper.create(p_formContext);
        formTable.setTableTitle("Static Header Table " + p_staticHeaderTableNumber);
        
        final RichHTMLPresentationTableWrapper presTable = RichHTMLPresentationTableWrapper.create(p_formContext, formTable.unwrap());
        presTable.setTableSummary( "$%SLANG Enquiry.StaticHeaderTableHint Static Header Table$ " + p_staticHeaderTableNumber );
        ResultsTableType.ENQUIRY_HEADER_RESULTS.applyPresentationOptionsTo(presTable, p_enquiryAttrsDigest, false);

        boolean isLeftmostTableQuestion = true;
        
        for (StaticResultsScreenHeaderTableQuestionSpec tableQuestionSpec: m_headerTableQuestionSpecByT24ColumnNumber.values())
        {
            final QuestionWrapperPair tableQuestionWrapperPair = tableQuestionSpec.generateTableQuestionAndDataItem(p_controlElems, p_formContext);

            if (! isLeftmostTableQuestion)
                tableQuestionWrapperPair.presWrapperObject.setAnswerControlStyle(EnquiryStyleStrings.HEADER_ENQUIRY_RESULTS_TABLE_QUESTION_WITH_LEFT_PADDING);

            formTable.addChild(tableQuestionWrapperPair.wrapperObject);
            presTable.addChild(tableQuestionWrapperPair.presWrapperObject);
            
            isLeftmostTableQuestion = false;
        }
        
        return new TableWrapperPair(formTable, presTable);
    }
    
    StaticResultsScreenHeaderTableSpec(int p_t24LineNumber)
    {
        m_t24LineNumber = AssertionUtils.requireNonNegative(p_t24LineNumber, "p_t24LineNumber");
    }
}
