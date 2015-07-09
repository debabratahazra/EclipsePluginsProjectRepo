package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.util.AssertionUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum ResultsTableSortableColumnsOption implements TablePresentationOption
{
	SORTABLE_COLUMNS_ENABLED,
	SORTABLE_COLUMNS_DISABLED;

	@Override
	public void applyTo(RichHTMLPresentationTableWrapper p_presTable)
	{
		AssertionUtils.requireNonNull(p_presTable, "p_presTable");

		p_presTable.setSortable(Boolean.valueOf(this == SORTABLE_COLUMNS_ENABLED));
	}
}
