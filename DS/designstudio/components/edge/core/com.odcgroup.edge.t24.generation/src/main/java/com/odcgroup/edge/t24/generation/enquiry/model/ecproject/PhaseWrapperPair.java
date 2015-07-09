package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationPhaseWrapper;

import com.acquire.util.AssertionUtils;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class PhaseWrapperPair extends VisualWrapperObjectPair<PhaseWrapper, RichHTMLPresentationPhaseWrapper>
{
    public PhaseWrapperPair(PhaseWrapper p_phaseWrapper, RichHTMLPresentationPhaseWrapper p_presPhaseWrapper)
    {
        super(
            AssertionUtils.requireNonNull(p_phaseWrapper, "p_phaseWrapper"),
            AssertionUtils.requireNonNull(p_presPhaseWrapper, "p_presPhaseWrapper")
        );
    }
}
