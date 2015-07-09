package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.util.AssertionUtils;

/**
 * TODO: Document me!
 *
 * @author Simon Hayes
 */
public enum ResultsTableQuestionStyleOption implements TableQuestionPresentationOption
{
	STANDARD(EnquiryStyleStrings.RESULTS_TABLE_QUESTION_COLUMN_HEADING);

	@Override
	public void applyTo(RichHTMLPresentationQuestionWrapper p_targetPresTableQuestion)
	{
		AssertionUtils.requireNonNull(p_targetPresTableQuestion, "p_targetPresTableQuestion");
		
		p_targetPresTableQuestion.setDesignToUseFromEntityKey("Custom");
		p_targetPresTableQuestion.setRowStyle(EnquiryStyleStrings.RESULTS_TABLE_QUESTION_ROW);
		p_targetPresTableQuestion.setQuestionStyle(m_questionStyle);
		p_targetPresTableQuestion.setMandatoryStyle(EnquiryStyleStrings.QUESTION_MANDATORY);
		p_targetPresTableQuestion.setPrefixStyle(EnquiryStyleStrings.QUESTION_PREFIX);
		p_targetPresTableQuestion.setAnswerStyle(EnquiryStyleStrings.QUESTION_ANSWER);
		
		/*
		 * Turns out we need to positively *refrain* from explicitly setting the 'Answer Control Style' since:
		 * 
		 * - This gets applied to the innermost <span> element...
		 * - whereas the style implied by the value of custom attribute: "EbAttr" value (sourced from Field.getDisplayType()) only gets applied that <span>'s parent <div>.
		 * 
		 * This means that any 'Answer Control Style' specified here 'overrules' any "EbAttr"-derived CSS classname injected by our ClassAttributeHook class (the
		 * symptoms being pretty much as if the whole "EbAttr"/ClassAttributeHook/EBAttributeLoader business hadn't been implemented at all !
		 * 
		 * 02/11/2014 Simon Hayes
		 */
		//p_targetPresTableQuestion.setAnswerControlStyle("FontDarkGrey"); // Nay, nay - and thrice nay !
		
		p_targetPresTableQuestion.setPostfixStyle("QPost");
		p_targetPresTableQuestion.setHelpStyle("QHelp");
		p_targetPresTableQuestion.setErrorStyle("ErrorColor SmallFont");
		p_targetPresTableQuestion.setInfoStyle("VSmallFont Italic");
		p_targetPresTableQuestion.setJustification(RichHTMLPresentationQuestionWrapper.EJustification.NONE);
		p_targetPresTableQuestion.setAnswerJustification(RichHTMLPresentationQuestionWrapper.EAnswerJustification.NONE);
		p_targetPresTableQuestion.setDisplayType("Text Input Field");
		p_targetPresTableQuestion.setReadOnlyFormat(RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.TEXT);
		p_targetPresTableQuestion.setDefaultButtonName("Use Default");
	}
	
	public void applyTo(RichHTMLPresentationQuestionWrapper p_targetPresTableQuestion, ResultsTableQuestionStyleRefinement p_refinement)
	{
        AssertionUtils.requireNonNull(p_targetPresTableQuestion, "p_targetPresTableQuestion");
        
        applyTo(p_targetPresTableQuestion);
        
        if (p_refinement != null)
            p_refinement.applyTo(p_targetPresTableQuestion);
	}
	
	private final String m_questionStyle;
	
	private ResultsTableQuestionStyleOption(String p_questionStyle)
	{
		m_questionStyle = AssertionUtils.requireNonNullAndNonEmpty(p_questionStyle, "p_questionStyle");
	}
}
