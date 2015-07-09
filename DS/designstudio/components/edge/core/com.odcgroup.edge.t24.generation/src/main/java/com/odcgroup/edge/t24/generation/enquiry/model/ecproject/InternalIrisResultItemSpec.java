package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import com.acquire.util.AssertionUtils;
import com.odcgroup.t24.enquiry.enquiry.Field;

/**
 * <code>InternalIrisResultItemSpec</code> is a concrete {@link IrisResultItemSpec} sub-type representing the specification of a data item within the "IRISResult" data group corresponding
 * to a a position-less (and therefore implicitly non displayable) field whose values we nevertheless need to capture from the IRIS odata response for one reason or another.  
 *
 * @author Simon Hayes
 *
 */
public class InternalIrisResultItemSpec extends IrisResultItemSpec
{
	public InternalIrisResultItemSpec(Field p_field)
	{
		super(AssertionUtils.requireNonNull(p_field, "p_field"));
	}
}
