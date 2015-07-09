package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.util.AssertionUtils;


/**
 * <code>InternalIrisResultBreakChangeItem</code> is the concrete extension of {@link IrisResultBreakChangeItem} for {@link InternalIrisResultItemSpec}(s), which -
 * unlike {@link VisibleIrisResultBreakChangeItem}(s) - can't be sorted by T24 field position, since they have none.<p>
 *
 * @author Simon Hayes
 */
public class InternalIrisResultBreakChangeItem extends IrisResultBreakChangeItem
{
    public InternalIrisResultBreakChangeItem(InternalIrisResultItemSpec p_irisResultItemSpec, PropertyWrapper p_breakChangeDataItem)
    {
        super(
            AssertionUtils.requireNonNull(p_irisResultItemSpec, "p_irisResultItemSpec"),
            AssertionUtils.requireNonNull(p_breakChangeDataItem, "p_breakChangeDataItem")
        );
    }
}
