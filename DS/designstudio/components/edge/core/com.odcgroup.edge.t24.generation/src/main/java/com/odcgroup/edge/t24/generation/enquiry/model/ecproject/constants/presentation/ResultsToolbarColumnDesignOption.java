package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;

import com.acquire.util.AssertionUtils;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum ResultsToolbarColumnDesignOption implements ColumnDesignOption
{
    FIRST_TOOLBAR_COLUMN(EnquiryDesignNames.ResultsScreenToolbar.Column.COLUMN_WITH_NO_LEFT_PADDING),
    SUBSEQUENT_STANDARD_TOOLBAR_COLUMN(EnquiryDesignNames.ResultsScreenToolbar.Column.LEFT_PADDED_STANDARD_TOOLBAR_COLUMN),
    SUBSEQUENT_PSEUDO_TOOLBAR_COLUMN(EnquiryDesignNames.ResultsScreenToolbar.Column.LEFT_PADDED_PSEUDO_TOOLBAR_COLUMN),
    CUSTOM("Custom");

    @Override
    public void applyTo(RichHTMLPresentationColumnBreakWrapper p_columnBreak)
    {
        AssertionUtils.requireNonNull(p_columnBreak, "p_columnBreak");
        p_columnBreak.setDesignToUseFromEntityKey(m_designName);
    }
    
    private String m_designName;
    
    private ResultsToolbarColumnDesignOption(String p_designName)
    {
        m_designName = AssertionUtils.requireNonNullAndNonEmpty(p_designName, "p_designName");
    }
}
