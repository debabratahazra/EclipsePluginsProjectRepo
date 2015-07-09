/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Association</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAssociationImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAssociationImpl#getContainment <em>Containment</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAssociationImpl#getReverse <em>Reverse</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfAssociationImpl extends MdfPropertyImpl implements MdfAssociation {
    /**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getType()

	 * @ordered
	 */
    protected MdfClass type;

    /**
     * The default value of the '{@link #getContainment() <em>Containment</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getContainment()
     * @ordered
     */
    protected static final int CONTAINMENT_EDEFAULT = MdfContainment.BY_REFERENCE;

    /**
     * The cached value of the '{@link #getContainment() <em>Containment</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getContainment()
     * @ordered
     */
    protected int containment = CONTAINMENT_EDEFAULT;

    /**
	 * The cached value of the '{@link #getReverse() <em>Reverse</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getReverse()

	 * @ordered
	 */
    protected MdfReverseAssociation reverse;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected MdfAssociationImpl() {
        super();
        //setName("newAssociation");
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_ASSOCIATION;
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public MdfEntity getType() {
        if (type != null && ((EObject)type).eIsProxy()) {
            InternalEObject oldType = (InternalEObject)type;
            MdfClass resolvedType = (MdfClass) eResolveProxy(oldType);
            if(resolvedType!=null) type = resolvedType;
            if (type != oldType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, MdfPackage.MDF_ASSOCIATION__TYPE, oldType, type));
            }
        }
// DS-7188 removed - this should normally not be needed anymore, since we have the Xtext-index based EMF Proxies:        
//       if(type!=null && type.getParentDomain() ==null && !((EObject)type).eIsProxy()){
//    	 if(getParentClass()!=null && getParentClass().getParentDomain()!=null){  
//    	 return MdfEcoreUtil.getMdfClassProxy(MdfNameFactory.createMdfName(getParentClass().getParentDomain().getName(), type.getName()));
//    	 }
//       }
        return type;
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfClass basicGetType() {
		return type;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setType(MdfClass newType) {
		MdfClass oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ASSOCIATION__TYPE, oldType, type));
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public int getContainment() {
        return containment;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public void setContainment(com.odcgroup.mdf.metamodel.MdfContainment newContainment) {
        MdfContainment oldContainment = MdfContainment.get(containment);
        containment = newContainment == null ? CONTAINMENT_EDEFAULT : newContainment.getValue();
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ASSOCIATION__CONTAINMENT, oldContainment, newContainment));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public void setContainment(int newContainment) {
        MdfContainment oldContainment = MdfContainment.get(containment);
        containment = newContainment;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ASSOCIATION__CONTAINMENT, oldContainment, MdfContainment.get(containment)));
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfReverseAssociation getReverse() {
		return reverse;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public NotificationChain basicSetReverse(MdfReverseAssociation newReverse, NotificationChain msgs) {
		MdfReverseAssociation oldReverse = reverse;
		reverse = newReverse;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ASSOCIATION__REVERSE, oldReverse, newReverse);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setReverse(MdfReverseAssociation newReverse) {
		if (newReverse != reverse) {
			NotificationChain msgs = null;
			if (reverse != null)
				msgs = ((InternalEObject)reverse).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MdfPackage.MDF_ASSOCIATION__REVERSE, null, msgs);
			if (newReverse != null)
				msgs = ((InternalEObject)newReverse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MdfPackage.MDF_ASSOCIATION__REVERSE, null, msgs);
			msgs = basicSetReverse(newReverse, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ASSOCIATION__REVERSE, newReverse, newReverse));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MdfPackage.MDF_ASSOCIATION__REVERSE:
				return basicSetReverse(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MdfPackage.MDF_ASSOCIATION__TYPE:
                if (resolve) return getType();
                return basicGetType();
            case MdfPackage.MDF_ASSOCIATION__CONTAINMENT:
                return MdfContainment.get(getContainment());
            case MdfPackage.MDF_ASSOCIATION__REVERSE:
                return getReverse();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case MdfPackage.MDF_ASSOCIATION__TYPE:
                setType((MdfClass)newValue);
                return;
            case MdfPackage.MDF_ASSOCIATION__CONTAINMENT:
                setContainment(((MdfContainment)newValue).getValue());
                return;
            case MdfPackage.MDF_ASSOCIATION__REVERSE:
                setReverse((MdfReverseAssociation)newValue);
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
			case MdfPackage.MDF_ASSOCIATION__TYPE:
				setType((MdfClass)null);
				return;
			case MdfPackage.MDF_ASSOCIATION__CONTAINMENT:
				setContainment(CONTAINMENT_EDEFAULT);
				return;
			case MdfPackage.MDF_ASSOCIATION__REVERSE:
				setReverse((MdfReverseAssociation)null);
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
			case MdfPackage.MDF_ASSOCIATION__TYPE:
				return type != null;
			case MdfPackage.MDF_ASSOCIATION__CONTAINMENT:
				return containment != CONTAINMENT_EDEFAULT;
			case MdfPackage.MDF_ASSOCIATION__REVERSE:
				return reverse != null;
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
		result.append(" (containment: ");
		result.append(containment);
		result.append(')');
		return result.toString();
	}

} //MdfAssociationImpl