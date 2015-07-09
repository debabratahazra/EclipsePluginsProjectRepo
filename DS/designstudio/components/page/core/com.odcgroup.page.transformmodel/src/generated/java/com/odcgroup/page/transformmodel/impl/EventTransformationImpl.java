/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.transformmodel.EventTransformation;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Transformation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.EventTransformationImpl#getTypeName <em>Type Name</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.EventTransformationImpl#getEventType <em>Event Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventTransformationImpl extends MinimalEObjectImpl.Container implements EventTransformation {
	/**
	 * The default value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected String typeName = TYPE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEventType() <em>Event Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventType()
	 * @generated
	 * @ordered
	 */
	protected EventType eventType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventTransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.EVENT_TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType getEventType() {
		if (eventType != null && eventType.eIsProxy()) {
			InternalEObject oldEventType = (InternalEObject)eventType;
			eventType = (EventType)eResolveProxy(oldEventType);
			if (eventType != oldEventType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.EVENT_TRANSFORMATION__EVENT_TYPE, oldEventType, eventType));
			}
		}
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType basicGetEventType() {
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEventType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventType(EventType newEventType) {
		EventType oldEventType = eventType;
		eventType = newEventType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.EVENT_TRANSFORMATION__EVENT_TYPE, oldEventType, eventType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newTypeName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeName(String newTypeName) {
		String oldTypeName = typeName;
		typeName = newTypeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.EVENT_TRANSFORMATION__TYPE_NAME, oldTypeName, typeName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.EVENT_TRANSFORMATION__TYPE_NAME:
				return getTypeName();
			case TransformModelPackage.EVENT_TRANSFORMATION__EVENT_TYPE:
				if (resolve) return getEventType();
				return basicGetEventType();
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
			case TransformModelPackage.EVENT_TRANSFORMATION__TYPE_NAME:
				setTypeName((String)newValue);
				return;
			case TransformModelPackage.EVENT_TRANSFORMATION__EVENT_TYPE:
				setEventType((EventType)newValue);
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
			case TransformModelPackage.EVENT_TRANSFORMATION__TYPE_NAME:
				setTypeName(TYPE_NAME_EDEFAULT);
				return;
			case TransformModelPackage.EVENT_TRANSFORMATION__EVENT_TYPE:
				setEventType((EventType)null);
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
			case TransformModelPackage.EVENT_TRANSFORMATION__TYPE_NAME:
				return TYPE_NAME_EDEFAULT == null ? typeName != null : !TYPE_NAME_EDEFAULT.equals(typeName);
			case TransformModelPackage.EVENT_TRANSFORMATION__EVENT_TYPE:
				return eventType != null;
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
		result.append(" (typeName: ");
		result.append(typeName);
		result.append(')');
		return result.toString();
	}

} //EventTransformationImpl
