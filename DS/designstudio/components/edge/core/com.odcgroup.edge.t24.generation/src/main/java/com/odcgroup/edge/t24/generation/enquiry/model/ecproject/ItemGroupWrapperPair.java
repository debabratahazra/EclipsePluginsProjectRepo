package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;

import com.acquire.util.AssertionUtils;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class ItemGroupWrapperPair extends VisualWrapperObjectPair<ItemGroupWrapper, RichHTMLPresentationItemGroupWrapper>
{
    public ItemGroupWrapperPair(ItemGroupWrapper p_itemGroupWrapper, RichHTMLPresentationItemGroupWrapper p_presItemGroupWrapper)
    {
        super(
            AssertionUtils.requireNonNull(p_itemGroupWrapper, "p_itemGroupWrapper"),
            AssertionUtils.requireNonNull(p_presItemGroupWrapper, "p_presItemGroupWrapper")
        );
    }
}
