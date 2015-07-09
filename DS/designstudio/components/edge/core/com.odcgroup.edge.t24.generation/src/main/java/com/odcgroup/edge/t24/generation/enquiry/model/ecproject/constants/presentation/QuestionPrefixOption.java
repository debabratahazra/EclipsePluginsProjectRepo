package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.util.AssertionUtils;

public enum QuestionPrefixOption implements QuestionPresentationOption
{
    HIDE_PREFIX(EnquiryInlineStyleBlocks.DISPLAY_NONE),
    SHOW_PREFIX(EnquiryStyleStrings.QUESTION_PREFIX);

    @Override
    public void applyTo(RichHTMLPresentationQuestionWrapper p_targetPresQuestion)
    {
        AssertionUtils.requireNonNull(p_targetPresQuestion, "p_targetPresQuestion");
        p_targetPresQuestion.setPrefixStyle(m_cssStyleText);
    }
    
    private final String m_cssStyleText;
    
    QuestionPrefixOption(String p_cssStyleText)
    {
        m_cssStyleText = AssertionUtils.requireNonNullAndNonEmpty(p_cssStyleText, "p_cssStyleText");
    }
}
