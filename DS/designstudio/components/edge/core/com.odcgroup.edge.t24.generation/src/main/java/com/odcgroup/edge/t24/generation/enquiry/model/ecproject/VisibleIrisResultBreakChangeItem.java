package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.util.AssertionUtils;


/**
 * <code>VisibleIrisResultBreakChangeItem</code> is the concrete {@link IrisResultBreakChangeItem} extension for {@link VisibleIrisResultItemSpec}(s).<p>
 * 
 * It adds a display-oriented {@link Comparable} implementation that produces natural left-to-right within top-to-bottom ordering based on the {@link DisplayableT24FieldPosition} amongst instances
 * added to sorted collections.<p>
 *
 * @author Simon Hayes
 */
public class VisibleIrisResultBreakChangeItem extends IrisResultBreakChangeItem implements Comparable<VisibleIrisResultBreakChangeItem>
{
    public VisibleIrisResultBreakChangeItem(VisibleIrisResultItemSpec p_irisResultItemSpec, PropertyWrapper p_breakChangeDataItem)
    {
        super(
            AssertionUtils.requireNonNull(p_irisResultItemSpec, "p_irisResultItemSpec"),
            AssertionUtils.requireNonNull(p_breakChangeDataItem, "p_breakChangeDataItem")
        );
    }
    
    public VisibleIrisResultItemSpec getVisibleIrisResultItemSpec()
    {
        return (VisibleIrisResultItemSpec) getIrisResultItemSpec();
    }
    
    @Override
    public int compareTo(VisibleIrisResultBreakChangeItem p_other)
    {
        AssertionUtils.requireNonNull(p_other, "p_other");
        return VisibleIrisResultItemSpec.LEFT_TO_RIGHT_WITHIN_TOP_TO_BOTTOM_COMPARATOR.compare(this.getVisibleIrisResultItemSpec(), p_other.getVisibleIrisResultItemSpec());
    }
}
