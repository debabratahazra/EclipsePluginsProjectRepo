package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.EnquiryStyleStrings;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableQuestionStyleOption;


/**
 * <code>InflatableColumnFillerQuestionGenerator</code> is a {@link ResultsTableQuestionGenerator} implementation that {@link #generateResultsTableQuestion(FormContext, EnquiryAttrsDigest) generates}
 * a question acting as a "filler" column - i.e. a column that will to fill whatever width remains given the data content of other columns in the same table and the horizontal real estate available
 * for the table as a whole.<p>
 * 
 * This sounds fancier than it is ! Basically it's just a question referencing a specified dummy answer item (i.e. one that will never be populated with any value) with a presentation counterpart
 * that includes "{width: 100%}" as part of the "Answer Style".
 *
 * @author Simon Hayes
 */
public class InflatableColumnFillerQuestionGenerator implements ResultsTableQuestionGenerator {
    private final PropertyWrapper m_fillerDataItem;

    InflatableColumnFillerQuestionGenerator(PropertyWrapper p_fillerDataItem)
    {
        m_fillerDataItem = AssertionUtils.requireNonNull(p_fillerDataItem, "p_fillerDataItem");
    }
    
    @Override
    public boolean isForSingleImageTypedField()
    {
        return false;
    }

    @Override
    public boolean isForCollapsibleResultsTableColumnQuestion() {
        return false;
    }

    @Override
    public QuestionWrapperPair generateResultsTableQuestion(FormContext p_formContext, EnquiryAttrsDigest p_enquiryAttrsDigest) {
        AssertionUtils.requireNonNull(p_formContext, "p_formContext");
        
        final QuestionWrapper question = QuestionWrapper.create(p_formContext);
        question.setQuestionText("[FILLER]");
        question.setPropertyKey(m_fillerDataItem);
        question.setMandatory(Boolean.FALSE);
        question.setReadOnly(Boolean.TRUE);
        
        final RichHTMLPresentationQuestionWrapper presQuestion = RichHTMLPresentationQuestionWrapper.create(p_formContext, question.unwrap());
        ResultsTableQuestionStyleOption.STANDARD.applyTo(presQuestion);
        presQuestion.setAnswerStyle(EnquiryStyleStrings.QUESTION_ANSWER + " {width: 100%}");
        
        return new QuestionWrapperPair(question, presQuestion);
    }
}
