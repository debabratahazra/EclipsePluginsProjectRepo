package com.odcgroup.page.domain.filter;

/**
 * @author atr
 */
public enum DomainFilterEnum {
	
	/** domain flag */
	DOMAIN, 

	/** class flag */
	CLASS,

	/** class flag */
	ABSTRACT_CLASS,

	/** dataset flag */
	DATASET,

	/** dataset property flag */
	DATASET_PROPERTY,
    
    /** dataset derived property flag when the property is a linked dataset */
    DATASET_PROPERTY_LINKED_DATASET,   
    
    /** dataset derived property flag when the property is an association */
    DATASET_PROPERTY_ASSOCIATION, 
    
    /** dataset mapped property flag when the property is an attribute */
    DATASET_PROPERTY_ATTRIBUTE,     

    /** dataset mapped property flag when the property is a derived attribute */
    DATASET_DERIVED_PROPERTY_ATTRIBUTE,     

    /** property flag */
	ATTRIBUTE,

	/** association flag */
	ASSOCIATION_ONE_BY_VALUE,

	/** association flag */
	ASSOCIATION_ONE_BY_REFERENCE,

	/** association flag */
	ASSOCIATION_MANY_BY_VALUE,

	/** association flag */
	ASSOCIATION_MANY_BY_REFERENCE,

	/** association flag */
	REVERSE_ASSOCIATION,
	
	/** */
	BOOLEAN_TYPE,
	
	/** all domain elements */
	ALL;
	
};
