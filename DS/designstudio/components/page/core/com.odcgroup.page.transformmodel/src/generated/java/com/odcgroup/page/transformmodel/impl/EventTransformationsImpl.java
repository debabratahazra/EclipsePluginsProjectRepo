/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.transformmodel.EventTransformation;
import com.odcgroup.page.transformmodel.EventTransformations;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Transformations</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.EventTransformationsImpl#getTransformations <em>Transformations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventTransformationsImpl extends MinimalEObjectImpl.Container implements EventTransformations {
	/**
	 * The cached value of the '{@link #getTransformations() <em>Transformations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformations()
	 * @generated
	 * @ordered
	 */
	protected EList<EventTransformation> transformations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventTransformationsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.EVENT_TRANSFORMATIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EventTransformation> getTransformations() {
		if (transformations == null) {
			transformations = new EObjectContainmentEList<EventTransformation>(EventTransformation.class, this, TransformModelPackage.EVENT_TRANSFORMATIONS__TRANSFORMATIONS);
		}
		return transformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TransformModelPackage.EVENT_TRANSFORMATIONS__TRANSFORMATIONS:
				return ((InternalEList<?>)getTransformations()).basicRemove(otherEnd, msgs);
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
			case TransformModelPackage.EVENT_TRANSFORMATIONS__TRANSFORMATIONS:
				return getTransformations();
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
			case TransformModelPackage.EVENT_TRANSFORMATIONS__TRANSFORMATIONS:
				getTransformations().clear();
				getTransformations().addAll((Collection<? extends EventTransformation>)newValue);
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
			case TransformModelPackage.EVENT_TRANSFORMATIONS__TRANSFORMATIONS:
				getTransformations().clear();
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
			case TransformModelPackage.EVENT_TRANSFORMATIONS__TRANSFORMATIONS:
				return transformations != null && !transformations.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * Finds the EventTransformation given the eventName.
	 * 
	 * @param eventName The name of the event
	 * @return EventTransformation The EventTransformation or null if no event transformation can be found
	 */
	public EventTransformation findEventTransformation(String eventName) {
		for (EventTransformation et : getTransformations()) {
			if (et.getEventType().getName().equals(eventName)) {
				return et;
			}
		}
		
		// Not found
		return null;
		
	}	
	
	/**
	 * Finds the EventTransformation given the typeName.
	 * 
	 * @param typeName The type name
	 * @return EventTransformation The EventTransformation or null if no event transformation can be found
	 */
	public EventTransformation findEventTransformationFromTypeName(String typeName) {
		for (EventTransformation et : getTransformations()) {
			if (et.getTypeName().equals(typeName)) {
				return et;
			}
		}
		
		// Not found
		return null;
		
	}	

} //EventTransformationsImpl
