/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import java.util.Stack;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.utils.MultiplicityCalculator;
import com.odcgroup.mdf.utils.PropertiesStack;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dataset Mapped Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl#isUnique <em>Unique</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl#isSingleValued <em>Single Valued</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl#getLinkDataset <em>Link Dataset</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfDatasetMappedPropertyImpl extends MdfDatasetPropertyImpl implements MdfDatasetMappedProperty {
	private static final Logger logger = LoggerFactory.getLogger(MdfDatasetMappedPropertyImpl.class);	

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()

	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()

	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #isUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnique()

	 * @ordered
	 */
	protected static final boolean UNIQUE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnique()

	 * @ordered
	 */
	protected boolean unique = UNIQUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isSingleValued() <em>Single Valued</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSingleValued()

	 * @ordered
	 */
	protected static final boolean SINGLE_VALUED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSingleValued() <em>Single Valued</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSingleValued()

	 * @ordered
	 */
	protected boolean singleValued = SINGLE_VALUED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLinkDataset() <em>Link Dataset</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkDataset()

	 * @ordered
	 */
	protected MdfDataset linkDataset;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected MdfDatasetMappedPropertyImpl() {
		super();
        //setName("newClassAttribute");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET_MAPPED_PROPERTY__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setUnique(boolean newUnique) {
		boolean oldUnique = unique;
		unique = newUnique;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET_MAPPED_PROPERTY__UNIQUE, oldUnique, unique));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean isSingleValued() {
		return singleValued;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setSingleValued(boolean newSingleValued) {
		boolean oldSingleValued = singleValued;
		singleValued = newSingleValued;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED, oldSingleValued, singleValued));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfDataset getLinkDataset() {
		if (linkDataset != null && ((EObject)linkDataset).eIsProxy()) {
			InternalEObject oldLinkDataset = (InternalEObject)linkDataset;
			linkDataset = (MdfDataset)eResolveProxy(oldLinkDataset);
			if (linkDataset != oldLinkDataset) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MdfPackage.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET, oldLinkDataset, linkDataset));
			}
		}
		return linkDataset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfDataset basicGetLinkDataset() {
		return linkDataset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setLinkDataset(MdfDataset newLinkDataset) {
		MdfDataset oldLinkDataset = linkDataset;
		linkDataset = newLinkDataset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET, oldLinkDataset, linkDataset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__PATH:
				return getPath();
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__UNIQUE:
				return isUnique() ? Boolean.TRUE : Boolean.FALSE;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED:
				return isSingleValued() ? Boolean.TRUE : Boolean.FALSE;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET:
				if (resolve) return getLinkDataset();
				return basicGetLinkDataset();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__PATH:
				setPath((String)newValue);
				return;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__UNIQUE:
				setUnique(((Boolean)newValue).booleanValue());
				return;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED:
				setSingleValued(((Boolean)newValue).booleanValue());
				return;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET:
				setLinkDataset((MdfDataset)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__UNIQUE:
				setUnique(UNIQUE_EDEFAULT);
				return;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED:
				setSingleValued(SINGLE_VALUED_EDEFAULT);
				return;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET:
				setLinkDataset((MdfDataset)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__UNIQUE:
				return unique != UNIQUE_EDEFAULT;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED:
				return singleValued != SINGLE_VALUED_EDEFAULT;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET:
				return linkDataset != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (path: ");
		result.append(path);
		result.append(", unique: ");
		result.append(unique);
		result.append(", singleValued: ");
		result.append(singleValued);
		result.append(')');
		return result.toString();
	}
	
    public boolean isTypeDataset() {
        return (linkDataset != null);
    }
    
    @Override
    public MdfEntity basicGetType() {
    	throw new UnsupportedOperationException("DomainJvmDatasetPropertyTypeReferenceProvider replaces the need for this");
//    	// This is copy/pasted from getType(),
//    	// BUT uses basicGetType instead of p.getType();
//    	// AND uses basicGetClassProperty instead of getClassProperty()
//        MdfDataset linkDataset = getLinkDataset();
//
//        if (linkDataset == null) {
//        	MdfPropertyImpl p = basicGetClassProperty(); // sic!
//            if (p != null) return p.basicGetType(); // sic!
//        } else {
//            return linkDataset;
//        }
//
//        return null;
    }
	
	public MdfEntity getType() {
        MdfDataset linkDataset = getLinkDataset();

        if (linkDataset == null) {
        	MdfProperty p = getClassProperty();
            if (p != null) return p.getType();
        } else {
            return linkDataset;
        }

        return null;
    }
    
    public int getMultiplicity() {
        if (path != null) {
            if (isSingleValued()) {
                return MdfConstants.MULTIPLICITY_ONE;
            } else {
            	MdfDataset parentDataset = getParentDataset();
            	if (parentDataset != null) {
            		return MultiplicityCalculator.getMultiplicity(
            				parentDataset, path);
            	} else {
                    return MdfMultiplicity.ONE;
            	}
            }
        } else {
            return MdfMultiplicity.ONE;
        }
    }
    
    protected MdfProperty getClassProperty() {
    	MdfDataset parentDataset = getParentDataset(); 
        MdfClass baseClass = parentDataset != null ? parentDataset.getBaseClass() : null;

        if (baseClass != null && path != null) {
            // Must get the last property's type
            try {
            	Stack props = PropertiesStack.getStack(parentDataset, path);
                MdfProperty p = (MdfProperty) props.peek();
                if (p != null) return p;
            } catch(RuntimeException e) {
            	// DS-4048: If the property does not exist, the getStack() method throws a RE
            	logger.warn("Exception in basicGetClassProperty() for: " + this.toString(), e);
            	return null;
            }
        }    	
        return null;
    }

//	private MdfPropertyImpl basicGetClassProperty() {
//    	// This is copy/pasted from getClassProperty(),
//    	// BUT uses basicGetBaseClass instead of p.getBaseClass();
//
//    	MdfDatasetImpl parentDataset = getParentDataset(); 
//        MdfClass baseClass = parentDataset != null ? parentDataset.basicGetBaseClass() : null;
//
//        if (baseClass != null && path != null) {
//            // Must get the last property's type
//            try {
//            	Stack props = PropertiesStack2.getStack(parentDataset, path);
//                MdfProperty p = (MdfProperty) props.peek();
//                if (p != null) return (MdfPropertyImpl) p;
//            } catch(RuntimeException e) {
//            	// DS-4048: If the property does not exist, the getStack() method throws a RE
//            	logger.warn("Exception in basicGetClassProperty() for: " + this.toString(), e);
//            	return null;
//            }
//        }    	
//        return null;
//	}

    public boolean isPrimaryKey() {
    	MdfProperty p = getClassProperty();
    	if(p!=null && p.isPrimaryKey()) {
    		return true;
    	}
    	return false;
    }

    public boolean isBusinessKey() {
    	MdfProperty p = getClassProperty();
    	if(p!=null && p.isBusinessKey()) {
    		return true;
    	}
    	return false;
    }
    
    public boolean isRequired() {
    	MdfProperty p = getClassProperty();
    	if(p!=null && p.isRequired()) {
    		return true;
    	}
    	return false;
    }
    
} //MdfDatasetMappedPropertyImpl
