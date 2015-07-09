package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.FormButtonWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationButtonWrapper;

import com.acquire.util.AssertionUtils;

/**
 * <code>ButtonWrapperPair</code> is a simple immutable aggregate representing a pairing of a {@link FormButtonWrapper} with its {@link RichHTMLPresentationButtonWrapper} presentation counterpart.
 *
 * @author Simon Hayes
 */
public class ButtonWrapperPair extends VisualWrapperObjectPair<FormButtonWrapper,RichHTMLPresentationButtonWrapper>
{
    /**
     * @param   p_formButtonWrapper         the {@link FormButtonWrapper} for this <code>ButtonWrapperPair</code> <i>(cannot be null)</i>
     * @param   p_presButtonWrapper         the matching {@link RichHTMLPresentationButtonWrapper} for this <code>ButtonWrapperPair</code> <i>(cannot be null)</i<>
     * 
     * @throws  IllegalArgumentException    if either argument is <code>null</code> or <code>p_presButtonWrapper</code> was not created for <code>p_formButtonWrapper</code>
     * 
     * @throws  IllegalStateException       if <code>p_formButtonWrapper</code> and <code>p_presButtonWrapper</code> have <u>different</u> "parentage" states (i.e. its fine for <u>both</u>
     *                                      to be parented or <u>both</u> to be unparented, but anything else is so likely to be a programming error that we throw an exception).
     */
    public ButtonWrapperPair(FormButtonWrapper p_formButtonWrapper, RichHTMLPresentationButtonWrapper p_presButtonWrapper)
    {
        super(
            AssertionUtils.requireNonNull(p_formButtonWrapper, "p_formButtonWrapper"),
            AssertionUtils.requireNonNull(p_presButtonWrapper, "p_presButtonWrapper")
        );
    }
}
