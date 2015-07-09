package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.util.AssertionUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum ResultsTableRowRolloverOption implements TablePresentationOption
{
	TABLE_ROW_ROLLOVER_ENABLED(EnquiryStyleStrings.RESULTS_TABLE_ROW_ROLLOVER),
	TABLE_ROW_ROLLOVER_DISABLED(null);
	
	@Override
	public void applyTo(RichHTMLPresentationTableWrapper p_presTable)
	{
		AssertionUtils.requireNonNull(p_presTable, "p_presTable");
		p_presTable.setRollOverStyle(m_cssStyleText);
	}

	private final String m_cssStyleText;
	
	private ResultsTableRowRolloverOption(String p_cssStyleText)
	{
		m_cssStyleText = p_cssStyleText;
	}
}
