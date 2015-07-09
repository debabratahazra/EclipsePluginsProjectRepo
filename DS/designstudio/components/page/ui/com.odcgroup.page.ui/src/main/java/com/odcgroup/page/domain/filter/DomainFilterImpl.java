package com.odcgroup.page.domain.filter;

import java.util.BitSet;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

/**
 * This class implements a simple MDF model filter
 * 
 * @author atr
 * @version 1.0
 */
class DomainFilterImpl implements DomainFilter {

	/** The filter */
	public BitSet filter = new BitSet(DomainFilterEnum.values().length);
	
	/**
	 * Returns <code>true</code> if the designated item is accepted, 
	 * otherwise it returns <code>false</code>.
	 * 
	 * @param item 
	 * @return <code>true</code> if the designated item is accepted
	 */
	protected final boolean isSet(DomainFilterEnum item) {
		return filter.get(item.ordinal());
	}

	/**
	 * @see com.odcgroup.page.domain.filter.DomainFilter#acceptAll()
	 */
	public void acceptAll() {
		accept(DomainFilterEnum.values());
	}

	/**
	 * @see com.odcgroup.page.domain.filter.DomainFilter#accept(com.odcgroup.page.domain.filter.DomainFilterEnum)
	 */
	public final void accept(DomainFilterEnum item) {
		filter.set(item.ordinal());
	}

	/**
	 * @see com.odcgroup.page.domain.filter.DomainFilter#accept(com.odcgroup.page.domain.filter.DomainFilterEnum[])
	 */
	public void accept(DomainFilterEnum[] items) {
		if (items != null) {
			for (int kx=0; kx < items.length; kx++) {
				accept(items[kx]);
			}
		}
	}

	/**
	 * @see com.odcgroup.page.domain.filter.DomainFilter#excludeAll()
	 */
	public void excludeAll() {
		exclude(DomainFilterEnum.values());
	}

	/**
	 * @see com.odcgroup.page.domain.filter.DomainFilter#exclude(com.odcgroup.page.domain.filter.DomainFilterEnum)
	 */
	public final void exclude(DomainFilterEnum item) {
		filter.clear(item.ordinal());
	}

	/**
	 * @see com.odcgroup.page.domain.filter.DomainFilter#exclude(com.odcgroup.page.domain.filter.DomainFilterEnum[])
	 */
	public void exclude(DomainFilterEnum[] items) {
		if (items != null) {
			for (int kx=0; kx < items.length; kx++) {
				exclude(items[kx]);
			}
		}
	}

	/**
	 * @see com.odcgroup.page.domain.filter.DomainFilter#accept(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public boolean accept(MdfModelElement modelElement) {

		// pessimistic
		boolean ret = false;

		// check domain
		if (modelElement instanceof MdfDomain) {
			ret = isSet(DomainFilterEnum.DOMAIN);
		}
		
        // check mdf class
        else if (modelElement instanceof MdfClass) {
            ret = isSet(DomainFilterEnum.CLASS);
            if (ret) {
                MdfClass cls = (MdfClass) modelElement;
                if (cls.isAbstract()) {
                    ret = isSet(DomainFilterEnum.ABSTRACT_CLASS);
                }
            }
        }	

		// check attribute
		else if (modelElement instanceof MdfAttribute) {
			ret = isSet(DomainFilterEnum.ATTRIBUTE);
		}

		// check mdf association
		else if (modelElement instanceof MdfAssociation) {
			MdfAssociation assoc = (MdfAssociation) modelElement;
			boolean isMany = assoc.getMultiplicity() > 0;
			boolean isOne = ! isMany;
			boolean byRef = assoc.getContainment() > 0;
			boolean byVal = ! byRef;
			// filter association 0..1 by reference
			if (isOne && byVal) {
				ret = isSet(DomainFilterEnum.ASSOCIATION_ONE_BY_VALUE);
			} else if (isOne && byRef) {
				ret = isSet(DomainFilterEnum.ASSOCIATION_ONE_BY_REFERENCE);
			} else if (isMany && byVal) {
				ret = isSet(DomainFilterEnum.ASSOCIATION_MANY_BY_VALUE);
			} else if (isMany && byRef) {
				ret = isSet(DomainFilterEnum.ASSOCIATION_MANY_BY_REFERENCE);
			}
		}
		
		// check reverse association
		else if (modelElement instanceof MdfReverseAssociation) {
			ret = isSet(DomainFilterEnum.ATTRIBUTE);
		}
        
        // check dataset
        else if (modelElement instanceof MdfDataset) {
            ret = isSet(DomainFilterEnum.DATASET);
        }		
		
        // check dataset property
        else if (modelElement instanceof MdfDatasetProperty) {
            ret = isSet(DomainFilterEnum.DATASET_PROPERTY);
            if (! ret) {
                // Look in more detail
                MdfDatasetProperty dp = (MdfDatasetProperty) modelElement;
                if (dp instanceof MdfDatasetMappedProperty) {
                    MdfDatasetMappedProperty dmp = (MdfDatasetMappedProperty) dp;
                    if (dmp.isTypeDataset()) {
                        ret = isSet(DomainFilterEnum.DATASET_PROPERTY_LINKED_DATASET);
                    } else if (dmp.getType() instanceof MdfClass){
                        ret = isSet(DomainFilterEnum.DATASET_PROPERTY_ASSOCIATION);
                    } else {
                        // primitive
                        ret = isSet(DomainFilterEnum.DATASET_PROPERTY_ATTRIBUTE);
                    }
                } else if (dp instanceof MdfDatasetDerivedProperty) {
                	MdfDatasetDerivedProperty ddp = (MdfDatasetDerivedProperty) dp;
                	if (! (ddp.getType() instanceof MdfClass)){
                        // primitive
                        ret = isSet(DomainFilterEnum.DATASET_DERIVED_PROPERTY_ATTRIBUTE);
                        // TODO should be DATASET_DERIVED_PROPERTY_ATTRIBUTE
                    }
                }
            }
        }		
		
		// default
		else {
			ret = false;
		}

		return ret;
	}

	/**
	 * Default constructor
	 */
	public DomainFilterImpl() {
	}

}
