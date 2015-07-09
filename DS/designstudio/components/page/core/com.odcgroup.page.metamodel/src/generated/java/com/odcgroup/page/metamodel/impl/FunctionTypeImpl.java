/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.util.NamedTypeUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.FunctionTypeImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.FunctionTypeImpl#getEventTypes <em>Event Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.FunctionTypeImpl#isAllowUserParameters <em>Allow User Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionTypeImpl extends NamedTypeImpl implements FunctionType {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterType> parameters;

	/**
	 * The cached value of the '{@link #getEventTypes() <em>Event Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<EventType> eventTypes;

	/**
	 * The default value of the '{@link #isAllowUserParameters() <em>Allow User Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowUserParameters()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALLOW_USER_PARAMETERS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAllowUserParameters() <em>Allow User Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowUserParameters()
	 * @generated
	 * @ordered
	 */
	protected boolean allowUserParameters = ALLOW_USER_PARAMETERS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.FUNCTION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterType> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<ParameterType>(ParameterType.class, this, MetaModelPackage.FUNCTION_TYPE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EventType> getEventTypes() {
		if (eventTypes == null) {
			eventTypes = new EObjectResolvingEList<EventType>(EventType.class, this, MetaModelPackage.FUNCTION_TYPE__EVENT_TYPES);
		}
		return eventTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAllowUserParameters() {
		return allowUserParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllowUserParameters(boolean newAllowUserParameters) {
		boolean oldAllowUserParameters = allowUserParameters;
		allowUserParameters = newAllowUserParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.FUNCTION_TYPE__ALLOW_USER_PARAMETERS, oldAllowUserParameters, allowUserParameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.FUNCTION_TYPE__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.FUNCTION_TYPE__PARAMETERS:
				return getParameters();
			case MetaModelPackage.FUNCTION_TYPE__EVENT_TYPES:
				return getEventTypes();
			case MetaModelPackage.FUNCTION_TYPE__ALLOW_USER_PARAMETERS:
				return isAllowUserParameters();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetaModelPackage.FUNCTION_TYPE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends ParameterType>)newValue);
				return;
			case MetaModelPackage.FUNCTION_TYPE__EVENT_TYPES:
				getEventTypes().clear();
				getEventTypes().addAll((Collection<? extends EventType>)newValue);
				return;
			case MetaModelPackage.FUNCTION_TYPE__ALLOW_USER_PARAMETERS:
				setAllowUserParameters((Boolean)newValue);
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
			case MetaModelPackage.FUNCTION_TYPE__PARAMETERS:
				getParameters().clear();
				return;
			case MetaModelPackage.FUNCTION_TYPE__EVENT_TYPES:
				getEventTypes().clear();
				return;
			case MetaModelPackage.FUNCTION_TYPE__ALLOW_USER_PARAMETERS:
				setAllowUserParameters(ALLOW_USER_PARAMETERS_EDEFAULT);
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
			case MetaModelPackage.FUNCTION_TYPE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case MetaModelPackage.FUNCTION_TYPE__EVENT_TYPES:
				return eventTypes != null && !eventTypes.isEmpty();
			case MetaModelPackage.FUNCTION_TYPE__ALLOW_USER_PARAMETERS:
				return allowUserParameters != ALLOW_USER_PARAMETERS_EDEFAULT;
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
		result.append(" (allowUserParameters: ");
		result.append(allowUserParameters);
		result.append(')');
		return result.toString();
	}

	/**
	 * Finds the ParameterType with the given name.
	 * 
	 * @param name The name of the ParameterType
	 * @return ParameterType or null if no ParameterType can be found with this name
	 */
	public ParameterType findParameterType(String name) {
		return (ParameterType) NamedTypeUtils.findByName(getParameters(), name);
	}

} //FunctionTypeImpl
