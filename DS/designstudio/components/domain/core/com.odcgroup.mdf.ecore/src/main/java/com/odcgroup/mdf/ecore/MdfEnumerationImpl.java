/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfPrimitive;


/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Enumeration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfEnumerationImpl#getValues <em>Values</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfEnumerationImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfEnumerationImpl#isAcceptNullValue <em>Accept Null Value</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfEnumerationImpl extends MdfPrimitiveImpl implements
        MdfEnumeration {

    /**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getValues()

	 * @ordered
	 */
    protected EList values;

    /**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getType()

	 * @ordered
	 */
    protected MdfPrimitive type;

    /**
	 * The default value of the '{@link #isAcceptNullValue() <em>Accept Null Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAcceptNullValue()

	 * @ordered
	 */
	protected static final boolean ACCEPT_NULL_VALUE_EDEFAULT = false;

				/**
	 * The cached value of the '{@link #isAcceptNullValue() <em>Accept Null Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAcceptNullValue()

	 * @ordered
	 */
	protected boolean acceptNullValue = ACCEPT_NULL_VALUE_EDEFAULT;

				/**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     */
    protected MdfEnumerationImpl() {
        super();
        //setName("NewEnumeration");
        setType((MdfPrimitive) PrimitivesDomain.DOMAIN.getEntity("mml:string"));
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_ENUMERATION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public List getValues() {
		if (values == null) {
			values = new EObjectContainmentEList(MdfEnumValue.class, this, MdfPackage.MDF_ENUMERATION__VALUES);
		}
		return values;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     */
    public MdfPrimitive getType() {
        if ((type instanceof EObject) && ((EObject) type).eIsProxy()) {
            InternalEObject oldType = (InternalEObject) type;
            type = (MdfPrimitive) eResolveProxy(oldType);
            if (type != oldType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            MdfPackage.MDF_ENUMERATION__TYPE, oldType, type));
            }
        }
        return type;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public MdfPrimitive basicGetType() {
		return type;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void setType(MdfPrimitive newType) {
		MdfPrimitive oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ENUMERATION__TYPE, oldType, type));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean isAcceptNullValue() {
		return acceptNullValue;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setAcceptNullValue(boolean newAcceptNullValue) {
		boolean oldAcceptNullValue = acceptNullValue;
		acceptNullValue = newAcceptNullValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ENUMERATION__ACCEPT_NULL_VALUE, oldAcceptNullValue, acceptNullValue));
	}

				/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MdfPackage.MDF_ENUMERATION__VALUES:
				return ((InternalEList)getValues()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    public MdfEnumValue getValue(String name) {
        Iterator it = getValues().iterator();
        while (it.hasNext()) {
            MdfEnumValue value = (MdfEnumValue) it.next();
            if (value.getName().equals(name)) return value;
        }
        return null;
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfPrimitive#isValidValue(java.lang.String)
     */
    public boolean isValidValue(String value) {
        return (getValue(value) != null);
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_ENUMERATION__VALUES:
				return getValues();
			case MdfPackage.MDF_ENUMERATION__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case MdfPackage.MDF_ENUMERATION__ACCEPT_NULL_VALUE:
				return isAcceptNullValue() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_ENUMERATION__VALUES:
				getValues().clear();
				getValues().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_ENUMERATION__TYPE:
				setType((MdfPrimitive)newValue);
				return;
			case MdfPackage.MDF_ENUMERATION__ACCEPT_NULL_VALUE:
				setAcceptNullValue(((Boolean)newValue).booleanValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void eUnset(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_ENUMERATION__VALUES:
				getValues().clear();
				return;
			case MdfPackage.MDF_ENUMERATION__TYPE:
				setType((MdfPrimitive)null);
				return;
			case MdfPackage.MDF_ENUMERATION__ACCEPT_NULL_VALUE:
				setAcceptNullValue(ACCEPT_NULL_VALUE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_ENUMERATION__VALUES:
				return values != null && !values.isEmpty();
			case MdfPackage.MDF_ENUMERATION__TYPE:
				return type != null;
			case MdfPackage.MDF_ENUMERATION__ACCEPT_NULL_VALUE:
				return acceptNullValue != ACCEPT_NULL_VALUE_EDEFAULT;
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
		result.append(" (acceptNullValue: ");
		result.append(acceptNullValue);
		result.append(')');
		return result.toString();
	}

} // MdfEnumerationImpl
