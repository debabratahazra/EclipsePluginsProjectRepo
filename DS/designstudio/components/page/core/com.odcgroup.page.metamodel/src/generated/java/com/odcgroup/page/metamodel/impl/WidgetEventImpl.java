/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.WidgetEvent;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Widget Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetEventImpl#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetEventImpl#getEventTypes <em>Event Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WidgetEventImpl extends MinimalEObjectImpl.Container implements WidgetEvent {
	/**
	 * The cached value of the '{@link #getWidgetType() <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetType()
	 * @generated
	 * @ordered
	 */
	protected WidgetType widgetType;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WidgetEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.WIDGET_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getWidgetType() {
		if (widgetType != null && widgetType.eIsProxy()) {
			InternalEObject oldWidgetType = (InternalEObject)widgetType;
			widgetType = (WidgetType)eResolveProxy(oldWidgetType);
			if (widgetType != oldWidgetType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.WIDGET_EVENT__WIDGET_TYPE, oldWidgetType, widgetType));
			}
		}
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetWidgetType() {
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidgetType(WidgetType newWidgetType) {
		WidgetType oldWidgetType = widgetType;
		widgetType = newWidgetType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_EVENT__WIDGET_TYPE, oldWidgetType, widgetType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EventType> getEventTypes() {
		if (eventTypes == null) {
			eventTypes = new EObjectResolvingEList<EventType>(EventType.class, this, MetaModelPackage.WIDGET_EVENT__EVENT_TYPES);
		}
		return eventTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.WIDGET_EVENT__WIDGET_TYPE:
				if (resolve) return getWidgetType();
				return basicGetWidgetType();
			case MetaModelPackage.WIDGET_EVENT__EVENT_TYPES:
				return getEventTypes();
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
			case MetaModelPackage.WIDGET_EVENT__WIDGET_TYPE:
				setWidgetType((WidgetType)newValue);
				return;
			case MetaModelPackage.WIDGET_EVENT__EVENT_TYPES:
				getEventTypes().clear();
				getEventTypes().addAll((Collection<? extends EventType>)newValue);
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
			case MetaModelPackage.WIDGET_EVENT__WIDGET_TYPE:
				setWidgetType((WidgetType)null);
				return;
			case MetaModelPackage.WIDGET_EVENT__EVENT_TYPES:
				getEventTypes().clear();
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
			case MetaModelPackage.WIDGET_EVENT__WIDGET_TYPE:
				return widgetType != null;
			case MetaModelPackage.WIDGET_EVENT__EVENT_TYPES:
				return eventTypes != null && !eventTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //WidgetEventImpl
