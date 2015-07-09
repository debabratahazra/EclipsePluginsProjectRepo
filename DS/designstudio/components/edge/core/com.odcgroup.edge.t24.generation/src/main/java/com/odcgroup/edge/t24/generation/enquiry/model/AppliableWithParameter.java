package com.odcgroup.edge.t24.generation.enquiry.model;

/**
 * An object implementing the <code>AppliableWithParameter</code> interface knows how to {@link #applyTo(Object, Object) apply} itself to a specified "target" object of the appropriate
 * type when provided with an additional parameter of the appropriate type.<p>
 *
 * @param <ApplicandType>	The type of "target" object that the implementing class knows how to "apply" itself to
 * @param <ParameterType>	The type of parameter that must be supplied by the caller in order for the implementing class to be able to "apply" itself to the target object
 * 
 * @author Simon Hayes
 */
public interface AppliableWithParameter<ApplicandType,ParameterType>
{
	void applyTo(ApplicandType p_applicand, ParameterType p_paramValue);
}
