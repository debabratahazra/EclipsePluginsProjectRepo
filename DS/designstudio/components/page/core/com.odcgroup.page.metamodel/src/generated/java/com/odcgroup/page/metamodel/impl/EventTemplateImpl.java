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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.EventTemplate;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.ParameterTemplate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.EventTemplateImpl#getParameterTemplates <em>Parameter Templates</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.EventTemplateImpl#getFunctionType <em>Function Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.EventTemplateImpl#getEventType <em>Event Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.EventTemplateImpl#getNature <em>Nature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventTemplateImpl extends MinimalEObjectImpl.Container implements EventTemplate {
	/**
	 * The cached value of the '{@link #getParameterTemplates() <em>Parameter Templates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterTemplates()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterTemplate> parameterTemplates;

	/**
	 * The cached value of the '{@link #getFunctionType() <em>Function Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionType()
	 * @generated
	 * @ordered
	 */
	protected FunctionType functionType;

	/**
	 * The default value of the '{@link #getEventType() <em>Event Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventType()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEventType() <em>Event Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventType()
	 * @generated
	 * @ordered
	 */
	protected String eventType = EVENT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNature() <em>Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNature()
	 * @generated
	 * @ordered
	 */
	protected static final String NATURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNature() <em>Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNature()
	 * @generated
	 * @ordered
	 */
	protected String nature = NATURE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.EVENT_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterTemplate> getParameterTemplates() {
		if (parameterTemplates == null) {
			parameterTemplates = new EObjectContainmentEList<ParameterTemplate>(ParameterTemplate.class, this, MetaModelPackage.EVENT_TEMPLATE__PARAMETER_TEMPLATES);
		}
		return parameterTemplates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return FunctionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionType getFunctionType() {
		if (functionType != null && functionType.eIsProxy()) {
			InternalEObject oldFunctionType = (InternalEObject)functionType;
			functionType = (FunctionType)eResolveProxy(oldFunctionType);
			if (functionType != oldFunctionType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.EVENT_TEMPLATE__FUNCTION_TYPE, oldFunctionType, functionType));
			}
		}
		return functionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return FunctionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionType basicGetFunctionType() {
		return functionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newFunctionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionType(FunctionType newFunctionType) {
		FunctionType oldFunctionType = functionType;
		functionType = newFunctionType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.EVENT_TEMPLATE__FUNCTION_TYPE, oldFunctionType, functionType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEventType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventType(String newEventType) {
		String oldEventType = eventType;
		eventType = newEventType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.EVENT_TEMPLATE__EVENT_TYPE, oldEventType, eventType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNature(String newNature) {
		String oldNature = nature;
		nature = newNature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.EVENT_TEMPLATE__NATURE, oldNature, nature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.EVENT_TEMPLATE__PARAMETER_TEMPLATES:
				return ((InternalEList<?>)getParameterTemplates()).basicRemove(otherEnd, msgs);
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
			case MetaModelPackage.EVENT_TEMPLATE__PARAMETER_TEMPLATES:
				return getParameterTemplates();
			case MetaModelPackage.EVENT_TEMPLATE__FUNCTION_TYPE:
				if (resolve) return getFunctionType();
				return basicGetFunctionType();
			case MetaModelPackage.EVENT_TEMPLATE__EVENT_TYPE:
				return getEventType();
			case MetaModelPackage.EVENT_TEMPLATE__NATURE:
				return getNature();
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
			case MetaModelPackage.EVENT_TEMPLATE__PARAMETER_TEMPLATES:
				getParameterTemplates().clear();
				getParameterTemplates().addAll((Collection<? extends ParameterTemplate>)newValue);
				return;
			case MetaModelPackage.EVENT_TEMPLATE__FUNCTION_TYPE:
				setFunctionType((FunctionType)newValue);
				return;
			case MetaModelPackage.EVENT_TEMPLATE__EVENT_TYPE:
				setEventType((String)newValue);
				return;
			case MetaModelPackage.EVENT_TEMPLATE__NATURE:
				setNature((String)newValue);
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
			case MetaModelPackage.EVENT_TEMPLATE__PARAMETER_TEMPLATES:
				getParameterTemplates().clear();
				return;
			case MetaModelPackage.EVENT_TEMPLATE__FUNCTION_TYPE:
				setFunctionType((FunctionType)null);
				return;
			case MetaModelPackage.EVENT_TEMPLATE__EVENT_TYPE:
				setEventType(EVENT_TYPE_EDEFAULT);
				return;
			case MetaModelPackage.EVENT_TEMPLATE__NATURE:
				setNature(NATURE_EDEFAULT);
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
			case MetaModelPackage.EVENT_TEMPLATE__PARAMETER_TEMPLATES:
				return parameterTemplates != null && !parameterTemplates.isEmpty();
			case MetaModelPackage.EVENT_TEMPLATE__FUNCTION_TYPE:
				return functionType != null;
			case MetaModelPackage.EVENT_TEMPLATE__EVENT_TYPE:
				return EVENT_TYPE_EDEFAULT == null ? eventType != null : !EVENT_TYPE_EDEFAULT.equals(eventType);
			case MetaModelPackage.EVENT_TEMPLATE__NATURE:
				return NATURE_EDEFAULT == null ? nature != null : !NATURE_EDEFAULT.equals(nature);
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
		result.append(" (eventType: ");
		result.append(eventType);
		result.append(", nature: ");
		result.append(nature);
		result.append(')');
		return result.toString();
	}

} //EventTemplateImpl
