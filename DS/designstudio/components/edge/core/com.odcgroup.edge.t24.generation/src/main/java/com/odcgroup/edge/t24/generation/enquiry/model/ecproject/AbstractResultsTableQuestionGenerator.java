package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper.EAnswerJustification;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper.EJustification;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.constants.FieldDisplayType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.EnquiryWidgets;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableQuestionStyleOption;
import com.odcgroup.edge.t24.generation.util.HtmlUtils;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.temenos.connect.attributeHooks.ClassAttributeHook;

/**
 * <code>AbstractResultsTableQuestionGenerator</code> is an abstract base {@link #ResultsTableQuestionGenerator} implementation that "commons-up"
 * most of the legwork involved, sub-classes being required to contribute implementations of:
 * 
 * <ul>
 *     <li>{@link #getLogicalQuestionText(IrisResultItemSpec)}</li>
 *     <li>{@link #getQuestionDataItem()}</li>
 * </ul>
 *
 * @author Simon Hayes
 */
public abstract class AbstractResultsTableQuestionGenerator
{
	private final IrisResultItemSpec m_definingIrisResultItemSpec;
	private final ResultsTableType m_targetResultsTableType;
	
	AbstractResultsTableQuestionGenerator(IrisResultItemSpec p_definingIrisResultItemSpec, ResultsTableType p_targetResultsTableType)
	{
		m_definingIrisResultItemSpec = AssertionUtils.requireNonNull(p_definingIrisResultItemSpec, "p_definingIrisResultItemSpec");
		m_targetResultsTableType = AssertionUtils.requireNonNull(p_targetResultsTableType, "p_targetResultsTableType");
	}
	
	public QuestionWrapperPair generateResultsTableQuestion(FormContext p_formContext, EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
		AssertionUtils.requireNonNull(p_formContext, "p_formContext");
		AssertionUtils.requireNonNull(p_enquiryAttrsDigest, "p_enquiryAttrsDigest");
		
        final String t24FieldDisplayType = m_definingIrisResultItemSpec.getT24FieldDisplayType();
        
		final PropertyWrapper questionDataItem = getQuestionDataItem();
		questionDataItem.setAttribute(ClassAttributeHook.EB_CUSTOM_ATTRIBUTE, t24FieldDisplayType);
		
		final QuestionWrapper question = QuestionWrapper.create(p_formContext);
		question.setQuestionText(getLogicalQuestionText(m_definingIrisResultItemSpec));
		question.setPropertyKey(questionDataItem);
		question.setMandatory(Boolean.FALSE);
		question.setReadOnly(Boolean.TRUE);
		
		final RichHTMLPresentationQuestionWrapper presQuestion = RichHTMLPresentationQuestionWrapper.create(p_formContext, question.unwrap());
		ResultsTableQuestionStyleOption.STANDARD.applyTo(presQuestion);
		presQuestion.setJustification(getQuestionJustification());
		presQuestion.setAnswerJustification(getAnswerJustification());
		
		setDisplayTextOverride(presQuestion, p_enquiryAttrsDigest);
		
		presQuestion.setAttribute(ClassAttributeHook.EB_CUSTOM_ATTRIBUTE, t24FieldDisplayType);
		
		if (FieldDisplayType.IMAGE.isReferencedBy(m_definingIrisResultItemSpec.getEnquiryField()))
			EnquiryWidgets.RESULTS_FIELD_IMAGE.applyTo(presQuestion);
		
		if (! m_targetResultsTableType.isMainEnquiryResults())
			presQuestion.setHideQuestion(Boolean.TRUE);
		
		return new QuestionWrapperPair(question, presQuestion);
	}

	protected abstract String getLogicalQuestionText(IrisResultItemSpec p_definingIrisResultItemSpec);
	
	protected abstract PropertyWrapper getQuestionDataItem();
	
	protected EJustification getQuestionJustification()
	{
		return AlignmentKindMapper.toEJustification(m_definingIrisResultItemSpec.getEnquiryField().getAlignment(), EJustification.LEFT);
	}
	
	protected EAnswerJustification getAnswerJustification()
	{
		return AlignmentKindMapper.toEAnswerJustification(m_definingIrisResultItemSpec.getEnquiryField().getAlignment(), EAnswerJustification.LEFT);
	}

	private void setDisplayTextOverride(RichHTMLPresentationQuestionWrapper p_presQuestion, EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
	    // FIXME: How does this override work .. are there multiple pres questions?
	    
	    String presQuestionDisplayTextOverride = null;
	    
	    if (m_targetResultsTableType.areColumnHeadersDisplayable(p_enquiryAttrsDigest))
	    {
    		final Field enquiryField = m_definingIrisResultItemSpec.getEnquiryField();
            final TextTranslations englishFieldLabelTranslation = TextTranslations.getLabelTranslations( p_enquiryAttrsDigest.getEnquiryProject().getEdgeMapper(),
                                                                                                         null, // FIXME: Get Property
                                                                                                         enquiryField.getLabel(),
                                                                                                         enquiryField.getName() );
            
            p_enquiryAttrsDigest.getEnquiryProject().getLanguageMapHelper().registerT24TextTranslations( p_presQuestion, englishFieldLabelTranslation );
            
    		presQuestionDisplayTextOverride = HtmlUtils.convertSpacesToHtmlNonBreakingSpaces(englishFieldLabelTranslation.getText(), true, true);
	    }
	    
	    p_presQuestion.setDisplayText( presQuestionDisplayTextOverride );
	}
}
