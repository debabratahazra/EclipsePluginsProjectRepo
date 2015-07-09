package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;

import java.util.Collections;
import java.util.List;

import com.acquire.intelligentforms.entities.PropertyGroup;
import com.acquire.util.AssertionUtils;

/**
 * <code>IrisResultGroupWrapper</code> is the return type of <code>IrisResultGroupSpec</code>'s {@link IrisResultGroupSpec#generateDataGroup generateDataGroup}
 * method.<p>
 * 
 * It is an extended {@link PropertyGroupWrapper} that adds information about descendent data items that capture break-change values (to save the caller of the <code>generateDataGroup()</code>
 * method above from having to call a second [state-dependent] method to retrieve this).<p>
 *
 * @author Simon Hayes
 */
public class IrisResultGroupWrapper extends PropertyGroupWrapper
{
    public final List<IrisResultBreakChangeItem> breakChangeItemList;
    
    public boolean includesIrisBreakChangeItems() {
        return ! breakChangeItemList.isEmpty();
    }
    
    IrisResultGroupWrapper(PropertyGroup p_propertyGroup, List<IrisResultBreakChangeItem> m_irisBreakChangeItemList)
    {
        super(AssertionUtils.requireNonNull(p_propertyGroup, "p_propertyGroup"));
        AssertionUtils.requireNonNull(m_irisBreakChangeItemList, "m_irisBreakChangeItemList");
        
        breakChangeItemList = Collections.unmodifiableList(m_irisBreakChangeItemList);
    }
}
