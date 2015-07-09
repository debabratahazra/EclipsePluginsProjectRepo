/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;


/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Reverse Association</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfReverseAssociationImpl#getReversedAssociation <em>Reversed Association</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfReverseAssociationImpl extends MdfPropertyImpl implements
        MdfReverseAssociation {

    /**
	 * The cached value of the '{@link #getReversedAssociation() <em>Reversed Association</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReversedAssociation()

	 * @ordered
	 */
	protected MdfAssociation reversedAssociation;

	/**
	 * The cached value of the '{@link #getReversedAssociationType() <em>Reversed Association Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReversedAssociationType()
	 * @generated
	 * @ordered
	 */
	protected MdfClass reversedAssociationType;

				/**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     */
    protected MdfReverseAssociationImpl() {
        super();
        setName("newAssociation");
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfModelElement#getQualifiedName()
     */
    public MdfName getQualifiedName() {
        MdfAssociation parent = getAssociation();

        if (parent != null) {
            MdfName parentqn = parent.getQualifiedName();

            if (parentqn != null) {
                StringBuffer localName = new StringBuffer();
                localName.append(parentqn.getLocalName()).append('#').append(getName());
                return MdfNameFactory.createMdfName(parentqn.getDomain(), localName.toString());
            }
        }

        return null;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_REVERSE_ASSOCIATION;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfAssociation getReversedAssociation() {
		if (reversedAssociation != null && ((EObject)reversedAssociation).eIsProxy()) {
			MdfAssociation oldReversedAssociation = (MdfAssociation)reversedAssociation;
			reversedAssociation = (MdfAssociation)eResolveProxy((InternalEObject)oldReversedAssociation);
			if (reversedAssociation != oldReversedAssociation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION, oldReversedAssociation, reversedAssociation));
			}
		}
		return reversedAssociation;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfAssociation basicGetReversedAssociation() {
		return reversedAssociation;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setReversedAssociation(MdfAssociation newReversedAssociation) {
		MdfAssociation oldReversedAssociation = reversedAssociation;
		reversedAssociation = newReversedAssociation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION, oldReversedAssociation, reversedAssociation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass getReversedAssociationType() {
		if (reversedAssociationType != null && ((EObject)reversedAssociationType).eIsProxy()) {
			InternalEObject oldReversedAssociationType = (InternalEObject)reversedAssociationType;
			reversedAssociationType = (MdfClass)eResolveProxy(oldReversedAssociationType);
			if (reversedAssociationType != oldReversedAssociationType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE, oldReversedAssociationType, reversedAssociationType));
			}
		}
		return reversedAssociationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass basicGetReversedAssociationType() {
		return reversedAssociationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReversedAssociationType(MdfClass newReversedAssociationType) {
		MdfClass oldReversedAssociationType = reversedAssociationType;
		reversedAssociationType = newReversedAssociationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE, oldReversedAssociationType, reversedAssociationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION:
				if (resolve) return getReversedAssociation();
				return basicGetReversedAssociation();
			case MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE:
				if (resolve) return getReversedAssociationType();
				return basicGetReversedAssociationType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION:
				setReversedAssociation((MdfAssociation)newValue);
				return;
			case MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE:
				setReversedAssociationType((MdfClass)newValue);
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
			case MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION:
				setReversedAssociation((MdfAssociation)null);
				return;
			case MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE:
				setReversedAssociationType((MdfClass)null);
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
			case MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION:
				return reversedAssociation != null;
			case MdfPackage.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE:
				return reversedAssociationType != null;
		}
		return super.eIsSet(featureID);
	}
	
	@Override
	public MdfEntity basicGetType() {
		return getType();
	}

	public MdfEntity getType() {
    	if (null!=getAssociation()) {
    		return getAssociation().getParentClass();
    	}
    	return null;
    }
    
    public MdfAssociation getAssociation() {
    	MdfAssociation association = getReversedAssociation();
    	if (association == null && eContainer() instanceof MdfAssociation) {
    		setReversedAssociation((MdfAssociation)eContainer());
    		association = getReversedAssociation();
    	}
    	return association;
    }

    public MdfClass getParentClass() {
        final MdfAssociation association = getAssociation();
        if (association != null)
        	return (MdfClass) association.getType();
        else
        	return null;
    }

} // MdfReverseAssociationImpl
