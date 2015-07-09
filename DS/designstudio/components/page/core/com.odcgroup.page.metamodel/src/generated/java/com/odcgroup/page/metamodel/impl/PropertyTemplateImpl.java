/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.PropertyTemplate;
import com.odcgroup.page.metamodel.PropertyType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTemplateImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTemplateImpl#isReadonly <em>Readonly</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTemplateImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyTemplateImpl extends MinimalEObjectImpl.Container implements PropertyTemplate {
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
	 * The default value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected boolean readonly = READONLY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected PropertyType type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.PROPERTY_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Returns the default value if not already initialized.
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getValue() {
		if (value == null) {
			if (getType() == null) {
				value = "";
			} else {
				value = getType().getDefaultValue();
			}
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Added an assert, param cannot be null
	 * @param newValue
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setValue(String newValue) {
		Assert.isNotNull(newValue);
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TEMPLATE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newReadonly
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadonly(boolean newReadonly) {
		boolean oldReadonly = readonly;
		readonly = newReadonly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TEMPLATE__READONLY, oldReadonly, readonly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (PropertyType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.PROPERTY_TEMPLATE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(PropertyType newType) {
		PropertyType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TEMPLATE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.PROPERTY_TEMPLATE__VALUE:
				return getValue();
			case MetaModelPackage.PROPERTY_TEMPLATE__READONLY:
				return isReadonly();
			case MetaModelPackage.PROPERTY_TEMPLATE__TYPE:
				if (resolve) return getType();
				return basicGetType();
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
			case MetaModelPackage.PROPERTY_TEMPLATE__VALUE:
				setValue((String)newValue);
				return;
			case MetaModelPackage.PROPERTY_TEMPLATE__READONLY:
				setReadonly((Boolean)newValue);
				return;
			case MetaModelPackage.PROPERTY_TEMPLATE__TYPE:
				setType((PropertyType)newValue);
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
			case MetaModelPackage.PROPERTY_TEMPLATE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case MetaModelPackage.PROPERTY_TEMPLATE__READONLY:
				setReadonly(READONLY_EDEFAULT);
				return;
			case MetaModelPackage.PROPERTY_TEMPLATE__TYPE:
				setType((PropertyType)null);
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
			case MetaModelPackage.PROPERTY_TEMPLATE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case MetaModelPackage.PROPERTY_TEMPLATE__READONLY:
				return readonly != READONLY_EDEFAULT;
			case MetaModelPackage.PROPERTY_TEMPLATE__TYPE:
				return type != null;
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
		result.append(", readonly: ");
		result.append(readonly);
		result.append(')');
		return result.toString();
	}

} //PropertyTemplateImpl