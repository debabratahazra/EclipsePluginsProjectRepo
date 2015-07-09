package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.util.AssertionUtils;

/**
 * <code>ResultsTableStyleOption</code> enumerates the various "Table Style" options for Enquiry results presentation tables.
 * 
 * Each of the enumerated constants knows how to {@link #applyTo(RichHTMLPresentationTableWrapper) apply} itself to a specified <code>RichHTMLPresentationTableWrapper</code>.
 *
 * @author Simon Hayes
 */
public enum ResultsTableStyleOption implements TablePresentationOption 
{
	ENQUIRY_HEADER_RESULTS_TABLE(null, EnquiryStyleStrings.PRIMPABLE_HEADER_RESULTS_TABLE),
	
	PRIMPABLE_ENQUIRY_RESULTS_TABLE(EnquiryStyleStrings.RESULTS_TABLE, EnquiryStyleStrings.PRIMPABLE_ENQUIRY_RESULTS_TABLE),
	
	NON_PRIMPABLE_ENQUIRY_RESULTS_TABLE(EnquiryStyleStrings.RESULTS_TABLE, null),

	DISPLAY_END_RESULTS_AS_MAIN_RESULTS_FOOTER_ROW(EnquiryStyleStrings.RESULTS_TABLE, EnquiryStyleStrings.DISPLAY_END_RESULTS_AS_MAIN_RESULTS_FOOTER_ROW),
	
	HEADER_ENQUIRY_RESULTS_TABLE(EnquiryStyleStrings.HEADER_ENQUIRY_RESULTS_TABLE, null);
	
	@Override
	public void applyTo(RichHTMLPresentationTableWrapper p_presTable)
	{
		AssertionUtils.requireNonNull(p_presTable, "p_presTable");
		p_presTable.setTableStyle(m_cssStyleText);
	}

	private final String m_cssStyleText;
	
	private ResultsTableStyleOption(String p_baseTableStyle, String p_primpableTableStyleClassName)
	{
        m_cssStyleText = (
            ((p_baseTableStyle == null) ? "" : p_baseTableStyle + ' ') +
            ((p_primpableTableStyleClassName == null) ? "" : p_primpableTableStyleClassName + ' ') +
            EnquiryInlineStyleBlocks.WIDTH_AUTO
        );
	}
}
