package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.util.AssertionUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum ResultsTableSelectedRowHighlightOption implements TablePresentationOption
{
	SELECTED_ROW_HIGHLIGHTING_ENABLED(EnquiryStyleStrings.RESULTS_TABLE_SELECTED_ROW),
	SELECTED_ROW_HIGHLIGHTING_DISABLED(null);
	
	@Override
	public void applyTo(RichHTMLPresentationTableWrapper p_presTable)
	{
		AssertionUtils.requireNonNull(p_presTable, "p_presTable");
		p_presTable.setSelectedRowStyle(m_cssStyleText);
	}

	private final String m_cssStyleText;
	
	private ResultsTableSelectedRowHighlightOption(String p_cssStyleText)
	{
		m_cssStyleText = p_cssStyleText;
	}
}