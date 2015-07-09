package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;

import com.acquire.util.AssertionUtils;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum ResultsToolbarContentDesignOption implements SectionDesignOption
{
    STANDARD_RESULTS_TOOLBAR_CONTENT_SECTION(EnquiryDesignNames.ResultsScreenToolbar.ToolbarContentSection.STANDARD_TOOLBAR_CONTENT_SECTION),
    PSEUDO_RESULTS_TOOLBAR_CONTENT_SECTION(EnquiryDesignNames.ResultsScreenToolbar.ToolbarContentSection.PSEUDO_TOOLBAR_CONTENT_SECTION);
    
    @Override
    public void applyTo(RichHTMLPresentationFormatBreakWrapper p_sectionBreak)
    {
        AssertionUtils.requireNonNull(p_sectionBreak, "p_sectionBreak");
        p_sectionBreak.setDesignToUseFromEntityKey(m_designName);
    }
    
    private String m_designName;
    
    private ResultsToolbarContentDesignOption(String p_designName)
    {
        m_designName = AssertionUtils.requireNonNullAndNonEmpty(p_designName, "p_designName");
    }
}
