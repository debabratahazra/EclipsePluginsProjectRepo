package com.odcgroup.edge.t24.generation.enquiry.model;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public interface AppliableRefinement<ApplicandType> extends Appliable<ApplicandType>
{
    void addTo(ApplicandType p_applicandType);
    
    void removeFrom(ApplicandType p_applicandType);
}
