package com.odcgroup.edge.t24.generation.enquiry.model;

/**
 * An object implementing the <code>Appliable</code> interface knows how to {@link #applyTo(Object) apply} itself to a "target" object of a particular type. 
 *
 * @author Simon T Hayes
 */
public interface Appliable<ApplicandType>
{
	void applyTo(ApplicandType p_applicand);
}
