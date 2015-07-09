/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.ParameterTemplate;
import com.odcgroup.page.metamodel.ParameterType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.ParameterTemplateImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.ParameterTemplateImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.ParameterTemplateImpl#isUserDefined <em>User Defined</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.ParameterTemplateImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParameterTemplateImpl extends MinimalEObjectImpl.Container implements ParameterTemplate {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ParameterType type;

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
	 * The default value of the '{@link #isUserDefined() <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUserDefined()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USER_DEFINED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUserDefined() <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUserDefined()
	 * @generated
	 * @ordered
	 */
	protected boolean userDefined = USER_DEFINED_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.PARAMETER_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ParameterType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (ParameterType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.PARAMETER_TEMPLATE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ParameterType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ParameterType newType) {
		ParameterType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PARAMETER_TEMPLATE__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PARAMETER_TEMPLATE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUserDefined() {
		return userDefined;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserDefined(boolean newUserDefined) {
		boolean oldUserDefined = userDefined;
		userDefined = newUserDefined;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PARAMETER_TEMPLATE__USER_DEFINED, oldUserDefined, userDefined));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PARAMETER_TEMPLATE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.PARAMETER_TEMPLATE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case MetaModelPackage.PARAMETER_TEMPLATE__VALUE:
				return getValue();
			case MetaModelPackage.PARAMETER_TEMPLATE__USER_DEFINED:
				return isUserDefined();
			case MetaModelPackage.PARAMETER_TEMPLATE__NAME:
				return getName();
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
			case MetaModelPackage.PARAMETER_TEMPLATE__TYPE:
				setType((ParameterType)newValue);
				return;
			case MetaModelPackage.PARAMETER_TEMPLATE__VALUE:
				setValue((String)newValue);
				return;
			case MetaModelPackage.PARAMETER_TEMPLATE__USER_DEFINED:
				setUserDefined((Boolean)newValue);
				return;
			case MetaModelPackage.PARAMETER_TEMPLATE__NAME:
				setName((String)newValue);
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
			case MetaModelPackage.PARAMETER_TEMPLATE__TYPE:
				setType((ParameterType)null);
				return;
			case MetaModelPackage.PARAMETER_TEMPLATE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case MetaModelPackage.PARAMETER_TEMPLATE__USER_DEFINED:
				setUserDefined(USER_DEFINED_EDEFAULT);
				return;
			case MetaModelPackage.PARAMETER_TEMPLATE__NAME:
				setName(NAME_EDEFAULT);
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
			case MetaModelPackage.PARAMETER_TEMPLATE__TYPE:
				return type != null;
			case MetaModelPackage.PARAMETER_TEMPLATE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case MetaModelPackage.PARAMETER_TEMPLATE__USER_DEFINED:
				return userDefined != USER_DEFINED_EDEFAULT;
			case MetaModelPackage.PARAMETER_TEMPLATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(", userDefined: ");
		result.append(userDefined);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ParameterTemplateImpl
