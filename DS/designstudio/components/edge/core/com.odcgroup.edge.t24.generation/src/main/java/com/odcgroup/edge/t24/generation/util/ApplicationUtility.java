package com.odcgroup.edge.t24.generation.util;

import java.util.Iterator;
import java.util.List;

import com.acquire.util.AssertionUtils;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public final class ApplicationUtility
{
    private ApplicationUtility() {}

    /** Method will return the Primary Key of the parent domain
     * 
     * @param p_mdfProperty    Property whose domain will be searched for
     * @return                 Name of the primary key field of linked Application, null if not found
     */
    public static String getDomainPrimaryKey(MdfProperty p_mdfProperty)
    {
    	MdfEntity mdfp = p_mdfProperty.getType();
        if	( mdfp instanceof MdfClass )
        {
        	List<MdfAssociationImpl> properties = ((MdfClass) mdfp).getProperties();
        	Iterator<MdfAssociationImpl> pIt = properties.iterator();
        	MdfAssociationImpl mdfAs = null;
        	while(pIt.hasNext())
        	{
        		mdfAs =  pIt.next();
        		//AbstractVersionFieldOrGroupMapper
        		if(mdfAs.isPrimaryKey())
        		{
        			return mdfAs.getName();
        		}
        	}
        }
        
        return null;
    }
    
    /**
     * Checks if p_mdfProperty is a foreign key (excludes primary key)
     * 
     * @param p_mdfProperty
     * 
     * @return true if it is
     */
    public static boolean isForeignKey(MdfProperty p_mdfProperty)
    {
        if ( p_mdfProperty instanceof MdfAssociation )
        {
            MdfAssociation association = (MdfAssociation) p_mdfProperty;

            return ( !association.isPrimaryKey() && association.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE );
        }
            
        return false;
    }
    
    /**
     * Gets the name of the domain/application referenced by the supplied {@link MdfProperty} if it represents a foreign key association or returns <code>null</code> otherwise.<p>
     * 
     * @param	p_mdfProperty	the <code>MdfProperty</code> for which the query is made
     * 
     * @return	the name of the domain/application referenced by <code>p_mdfProperty</code>, or <code>null</code> if <code>p_mdfProperty</code> does not represent a foreign key association
     */
    public static String getDomainNameReferencedByForeignKey(MdfProperty p_mdfProperty)
    {
    	String result = null;
    	
    	if ( isForeignKey(p_mdfProperty) )
    	{
	    	final MdfEntity type = p_mdfProperty.getType();
	    	
	    	if (type instanceof MdfClass)
	    	{
	    		result = ((MdfClass) type).getName();
	    	}
    	}
    	
    	return result;
    }

    /**
     * Gets the maximum input length of a property.
     * 
     * @param p_mdfProperty
     */
    public static int getMaxInputLength(MdfProperty p_mdfProperty)
    {
        AssertionUtils.requireNonNull( p_mdfProperty, "p_mdfProperty" );
        Integer maxLength = T24Aspect.getMaxLength(p_mdfProperty);
        return (maxLength != null ? maxLength.intValue() : 0 );
    }
    
    /**
     * Checks if is mandatory.
     *
     * @param p_mdfProperty the property
     * @return true, if is mandatory
     */
    public static boolean isMandatory(MdfProperty p_mdfProperty)
    {
        AssertionUtils.requireNonNull( p_mdfProperty, "p_mdfProperty" );
        return p_mdfProperty.isRequired();
    }
}
