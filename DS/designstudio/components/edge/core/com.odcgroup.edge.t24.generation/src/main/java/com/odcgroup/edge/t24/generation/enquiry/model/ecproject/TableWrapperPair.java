package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.util.AssertionUtils;

/**
 * <code>TableWrapperPair</code> is a simple immutable aggregate representing a pairing of a {@link FormTableWrapper} with its {@link RichHTMLPresentationTableWrapper} presentation counterpart.
 *
 * @author Simon Hayes
 */
public class TableWrapperPair extends VisualWrapperObjectPair<FormTableWrapper,RichHTMLPresentationTableWrapper>
{
    /**
     * @param   p_tableWrapper              the {@link FormTableWrapper} for this <code>TableWrapperPair</code> <i>(cannot be null)</i>
     * @param   p_presTableWrapper          the matching {@link RichHTMLPresentationTableWrapper} for this <code>TableWrapperPair</code> <i>(cannot be null)</i<>
     * 
     * @throws  IllegalArgumentException    if either argument is <code>null</code> or <code>p_presTableWrapper</code> was not created for <code>p_tableWrapper</code>
     * 
     * @throws  IllegalStateException       if <code>p_tableWrapper</code> and <code>p_presTableWrapper</code> have <u>different</u> "parentage" states (i.e. its fine for <u>both</u>
     *                                      to be parented or <u>both</u> to be unparented, but anything else is so likely to be a programming error that we throw an exception).
     */
	public TableWrapperPair(FormTableWrapper p_tableWrapper, RichHTMLPresentationTableWrapper p_presTableWrapper)
	{
	    super(
            AssertionUtils.requireNonNull(p_tableWrapper, "p_tableWrapper"),
            AssertionUtils.requireNonNull(p_presTableWrapper, "p_presTableWrapper")     
	    );
	}
}
