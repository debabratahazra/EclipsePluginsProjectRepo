package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.intelligentforms.entities.theme.design.TableDesign;
import com.acquire.util.AssertionUtils;
import com.acquire.util.StringUtils;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum ResultsTableStyleRefinement implements TablePresentationRefinement
{
    COLLAPSIBLE_TABLE(EnquiryStyleStrings.COLLAPSIBLE_TABLE);

    @Override
    public void applyTo(RichHTMLPresentationTableWrapper p_targetPresTable)
    {
        AssertionUtils.requireNonNull(p_targetPresTable, "p_targetPresTable");
        addTo(p_targetPresTable);
    }

    @Override
    public void addTo(RichHTMLPresentationTableWrapper p_targetPresTable)
    {
        AssertionUtils.requireNonNull(p_targetPresTable, "p_targetPresTable");
        
        final String existingCssStyleText = getExistingQuestionStyleCssTextOrEmptyString(p_targetPresTable);
        
        if (! existingCssStyleText.toUpperCase().contains(m_cssStyleTextUpperCased))
            applyUpdatedQuestionCssStyleTextTo(existingCssStyleText + ' ' + m_cssStyleText, p_targetPresTable);
    }

    @Override
    public void removeFrom(RichHTMLPresentationTableWrapper p_targetPresTable)
    {
        AssertionUtils.requireNonNull(p_targetPresTable, "p_targetPresTable");
        
        final String existingCssStyleText = getExistingQuestionStyleCssTextOrEmptyString(p_targetPresTable);

        if (existingCssStyleText.toUpperCase().contains(m_cssStyleTextUpperCased))
            applyUpdatedQuestionCssStyleTextTo(StringUtils.removeAll(existingCssStyleText, m_cssStyleText, true /* ignoreCase */), p_targetPresTable);
    }
    
    private static String getExistingQuestionStyleCssTextOrEmptyString(RichHTMLPresentationTableWrapper p_targetPresTable)
    {
        return StringUtils.nullToBlank((String) p_targetPresTable.unwrap().getAttribute(TableDesign.ATT_TABLE_STYLE));
    }

    private static void applyUpdatedQuestionCssStyleTextTo(String p_updatedCssStyleText, RichHTMLPresentationTableWrapper p_targetPresTable)
    {
        p_targetPresTable.unwrap().setAttribute(TableDesign.ATT_TABLE_STYLE, StringUtils.trimEmptyToNull(p_updatedCssStyleText));
    }
    
    private final String m_cssStyleText;
    private final String m_cssStyleTextUpperCased;
    
    private ResultsTableStyleRefinement(String p_cssStyleText)
    {
        m_cssStyleText = AssertionUtils.requireNonNullAndNonEmpty(p_cssStyleText, "p_cssStyleText");
        m_cssStyleTextUpperCased = m_cssStyleText.toUpperCase();
    }
}
