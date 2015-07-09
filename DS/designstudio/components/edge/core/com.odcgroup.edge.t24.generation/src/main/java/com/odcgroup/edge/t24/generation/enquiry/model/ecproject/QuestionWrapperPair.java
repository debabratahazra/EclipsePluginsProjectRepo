package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.util.AssertionUtils;

/**
 * <code>QuestionWrapperPair</code> is a simple immutable aggregate representing a pairing of a {@link QuestionWrapper} with its {@link RichHTMLPresentationQuestionWrapper} presentation counterpart.
 *
 * @author Simon Hayes
 *
 */
public class QuestionWrapperPair extends VisualWrapperObjectPair<QuestionWrapper,RichHTMLPresentationQuestionWrapper>
{
	public QuestionWrapperPair(QuestionWrapper p_questionWrapper, RichHTMLPresentationQuestionWrapper p_presQuestionWrapper)
	{
		super(
	        AssertionUtils.requireNonNull(p_questionWrapper, "p_questionWrapper"),
	        AssertionUtils.requireNonNull(p_presQuestionWrapper, "p_presQuestionWrapper")
		);
	}
}
