package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.intelligentforms.entities.theme.design.QuestionDesign;
import com.acquire.util.AssertionUtils;
import com.acquire.util.StringUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum ResultsTableQuestionStyleRefinement implements TableQuestionPresentationRefinement
{
    COLLAPSIBLE_COLUMN(EnquiryStyleStrings.COLLAPSIBLE_RESULTS_TABLE_QUESTION),
    NO_COLUMN_HEADING(EnquiryInlineStyleBlocks.DISPLAY_NONE);

    @Override
    public void applyTo(RichHTMLPresentationQuestionWrapper p_targetPresTableQuestion)
    {
        AssertionUtils.requireNonNull(p_targetPresTableQuestion, "p_targetPresTableQuestion");
        addTo(p_targetPresTableQuestion);
    }

    @Override
    public void addTo(RichHTMLPresentationQuestionWrapper p_targetPresTableQuestion)
    {
        AssertionUtils.requireNonNull(p_targetPresTableQuestion, "p_targetPresTableQuestion");
        
        final String existingCssStyleText = getExistingQuestionStyleCssTextOrEmptyString(p_targetPresTableQuestion);
        
        if (! existingCssStyleText.toUpperCase().contains(m_cssStyleTextUpperCased))
            applyUpdatedQuestionCssStyleTextTo(existingCssStyleText + ' ' + m_cssStyleText, p_targetPresTableQuestion);
    }

    @Override
    public void removeFrom(RichHTMLPresentationQuestionWrapper p_targetPresTableQuestion)
    {
        AssertionUtils.requireNonNull(p_targetPresTableQuestion, "p_targetPresTableQuestion");
        
        final String existingCssStyleText = getExistingQuestionStyleCssTextOrEmptyString(p_targetPresTableQuestion);

        if (existingCssStyleText.toUpperCase().contains(m_cssStyleTextUpperCased))
            applyUpdatedQuestionCssStyleTextTo(StringUtils.removeAll(existingCssStyleText, m_cssStyleText, true /* ignoreCase */), p_targetPresTableQuestion);
    }
    
    private static String getExistingQuestionStyleCssTextOrEmptyString(RichHTMLPresentationQuestionWrapper p_targetPresTableQuestion)
    {
        return StringUtils.nullToBlank((String) p_targetPresTableQuestion.unwrap().getAttribute(QuestionDesign.ATT_QUESTIONSTYLE));
    }

    private static void applyUpdatedQuestionCssStyleTextTo(String p_updatedCssStyleText, RichHTMLPresentationQuestionWrapper p_targetPresTableQuestion)
    {
        p_targetPresTableQuestion.unwrap().setAttribute(QuestionDesign.ATT_QUESTIONSTYLE, StringUtils.trimEmptyToNull(p_updatedCssStyleText));
    }
    
    private final String m_cssStyleText;
    private final String m_cssStyleTextUpperCased;
    
    private ResultsTableQuestionStyleRefinement(String p_cssStyleText)
    {
        m_cssStyleText = AssertionUtils.requireNonNullAndNonEmpty(p_cssStyleText, "p_cssStyleText");
        m_cssStyleTextUpperCased = m_cssStyleText.toUpperCase();
    }
}
