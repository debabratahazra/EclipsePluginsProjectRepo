package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.util.AssertionUtils;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum ResultsTableColumnHeaderDisplayOption implements TablePresentationOption
{
	HIDE_TABLE_COLUMN_HEADERS(EnquiryInlineStyleBlocks.DISPLAY_NONE),
	SHOW_TABLE_COLUMN_HEADERS(EnquiryStyleStrings.RESULTS_TABLE_HEADER);
	
	@Override
	public void applyTo(RichHTMLPresentationTableWrapper p_presTable) {
		AssertionUtils.requireNonNull(p_presTable, "p_presTable");
		
		p_presTable.setTableHeaderRowStyle(m_cssStyleText);
	}

	private final String m_cssStyleText;
	
	private ResultsTableColumnHeaderDisplayOption(String p_cssStyleText)
	{
		m_cssStyleText = AssertionUtils.requireNonNull(p_cssStyleText, "p_cssStyleText");
	}
}
