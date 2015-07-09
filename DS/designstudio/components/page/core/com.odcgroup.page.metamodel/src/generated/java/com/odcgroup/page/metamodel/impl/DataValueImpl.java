/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.DataValueImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.DataValueImpl#getOrdinal <em>Ordinal</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.DataValueImpl#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataValueImpl extends MinimalEObjectImpl.Container implements DataValue {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOrdinal() <em>Ordinal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrdinal()
	 * @generated
	 * @ordered
	 */
	protected static final int ORDINAL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOrdinal() <em>Ordinal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrdinal()
	 * @generated
	 * @ordered
	 */
	protected int ordinal = ORDINAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
    protected static final String DISPLAY_NAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
    protected String displayName = DISPLAY_NAME_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.DATA_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newValue
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.DATA_VALUE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return int
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newOrdinal
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrdinal(int newOrdinal) {
		int oldOrdinal = ordinal;
		ordinal = newOrdinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.DATA_VALUE__ORDINAL, oldOrdinal, ordinal));
	}

	/**
	 * <!-- begin-user-doc -->
     * @return String
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDisplayName() {
		return displayName;
	}

    /**
	 * <!-- begin-user-doc -->
     * @param newDisplayName
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.DATA_VALUE__DISPLAY_NAME, oldDisplayName, displayName));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.DATA_VALUE__VALUE:
				return getValue();
			case MetaModelPackage.DATA_VALUE__ORDINAL:
				return getOrdinal();
			case MetaModelPackage.DATA_VALUE__DISPLAY_NAME:
				return getDisplayName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetaModelPackage.DATA_VALUE__VALUE:
				setValue((String)newValue);
				return;
			case MetaModelPackage.DATA_VALUE__ORDINAL:
				setOrdinal((Integer)newValue);
				return;
			case MetaModelPackage.DATA_VALUE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MetaModelPackage.DATA_VALUE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case MetaModelPackage.DATA_VALUE__ORDINAL:
				setOrdinal(ORDINAL_EDEFAULT);
				return;
			case MetaModelPackage.DATA_VALUE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MetaModelPackage.DATA_VALUE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case MetaModelPackage.DATA_VALUE__ORDINAL:
				return ordinal != ORDINAL_EDEFAULT;
			case MetaModelPackage.DATA_VALUE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (value: ");
		result.append(value);
		result.append(", ordinal: ");
		result.append(ordinal);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(')');
		return result.toString();
	}

} //DataValueImpl