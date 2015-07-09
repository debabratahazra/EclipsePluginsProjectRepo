/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.EventModel;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.WidgetEvent;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.EventModelImpl#getFunctions <em>Functions</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.EventModelImpl#getEvents <em>Events</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.EventModelImpl#getEventTypes <em>Event Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventModelImpl extends MinimalEObjectImpl.Container implements EventModel {
	/**
	 * The cached value of the '{@link #getFunctions() <em>Functions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctions()
	 * @generated
	 * @ordered
	 */
	protected EList<FunctionType> functions;

	/**
	 * The cached value of the '{@link #getEvents() <em>Events</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvents()
	 * @generated
	 * @ordered
	 */
	protected EList<WidgetEvent> events;

	/**
	 * The cached value of the '{@link #getEventTypes() <em>Event Types</em>}' containment reference list.
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
	protected EventModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.EVENT_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FunctionType> getFunctions() {
		if (functions == null) {
			functions = new EObjectContainmentEList<FunctionType>(FunctionType.class, this, MetaModelPackage.EVENT_MODEL__FUNCTIONS);
		}
		return functions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WidgetEvent> getEvents() {
		if (events == null) {
			events = new EObjectContainmentEList<WidgetEvent>(WidgetEvent.class, this, MetaModelPackage.EVENT_MODEL__EVENTS);
		}
		return events;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EventType> getEventTypes() {
		if (eventTypes == null) {
			eventTypes = new EObjectContainmentEList<EventType>(EventType.class, this, MetaModelPackage.EVENT_MODEL__EVENT_TYPES);
		}
		return eventTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.EVENT_MODEL__FUNCTIONS:
				return ((InternalEList<?>)getFunctions()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.EVENT_MODEL__EVENTS:
				return ((InternalEList<?>)getEvents()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.EVENT_MODEL__EVENT_TYPES:
				return ((InternalEList<?>)getEventTypes()).basicRemove(otherEnd, msgs);
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
			case MetaModelPackage.EVENT_MODEL__FUNCTIONS:
				return getFunctions();
			case MetaModelPackage.EVENT_MODEL__EVENTS:
				return getEvents();
			case MetaModelPackage.EVENT_MODEL__EVENT_TYPES:
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
			case MetaModelPackage.EVENT_MODEL__FUNCTIONS:
				getFunctions().clear();
				getFunctions().addAll((Collection<? extends FunctionType>)newValue);
				return;
			case MetaModelPackage.EVENT_MODEL__EVENTS:
				getEvents().clear();
				getEvents().addAll((Collection<? extends WidgetEvent>)newValue);
				return;
			case MetaModelPackage.EVENT_MODEL__EVENT_TYPES:
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
			case MetaModelPackage.EVENT_MODEL__FUNCTIONS:
				getFunctions().clear();
				return;
			case MetaModelPackage.EVENT_MODEL__EVENTS:
				getEvents().clear();
				return;
			case MetaModelPackage.EVENT_MODEL__EVENT_TYPES:
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
			case MetaModelPackage.EVENT_MODEL__FUNCTIONS:
				return functions != null && !functions.isEmpty();
			case MetaModelPackage.EVENT_MODEL__EVENTS:
				return events != null && !events.isEmpty();
			case MetaModelPackage.EVENT_MODEL__EVENT_TYPES:
				return eventTypes != null && !eventTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //EventModelImpl
