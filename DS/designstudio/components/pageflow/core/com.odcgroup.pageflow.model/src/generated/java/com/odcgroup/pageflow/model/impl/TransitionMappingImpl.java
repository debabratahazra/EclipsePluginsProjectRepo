/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionMapping;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionMappingImpl#getEndState <em>End State</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionMappingImpl#getTransition <em>Transition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionMappingImpl extends MinimalEObjectImpl.Container implements TransitionMapping {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The cached value of the '{@link #getEndState() <em>End State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndState()
	 * @generated
	 * @ordered
	 */
	protected EndState endState;

	/**
	 * The cached value of the '{@link #getTransition() <em>Transition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransition()
	 * @generated
	 * @ordered
	 */
	protected Transition transition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PageflowPackage.Literals.TRANSITION_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndState getEndState() {
		if (endState != null && endState.eIsProxy()) {
			InternalEObject oldEndState = (InternalEObject)endState;
			endState = (EndState)eResolveProxy(oldEndState);
			if (endState != oldEndState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PageflowPackage.TRANSITION_MAPPING__END_STATE, oldEndState, endState));
			}
		}
		return endState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndState basicGetEndState() {
		return endState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndState(EndState newEndState) {
		EndState oldEndState = endState;
		endState = newEndState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION_MAPPING__END_STATE, oldEndState, endState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition getTransition() {
		if (transition != null && transition.eIsProxy()) {
			InternalEObject oldTransition = (InternalEObject)transition;
			transition = (Transition)eResolveProxy(oldTransition);
			if (transition != oldTransition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PageflowPackage.TRANSITION_MAPPING__TRANSITION, oldTransition, transition));
			}
		}
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition basicGetTransition() {
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransition(Transition newTransition) {
		Transition oldTransition = transition;
		transition = newTransition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION_MAPPING__TRANSITION, oldTransition, transition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PageflowPackage.TRANSITION_MAPPING__END_STATE:
				if (resolve) return getEndState();
				return basicGetEndState();
			case PageflowPackage.TRANSITION_MAPPING__TRANSITION:
				if (resolve) return getTransition();
				return basicGetTransition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PageflowPackage.TRANSITION_MAPPING__END_STATE:
				setEndState((EndState)newValue);
				return;
			case PageflowPackage.TRANSITION_MAPPING__TRANSITION:
				setTransition((Transition)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case PageflowPackage.TRANSITION_MAPPING__END_STATE:
				setEndState((EndState)null);
				return;
			case PageflowPackage.TRANSITION_MAPPING__TRANSITION:
				setTransition((Transition)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PageflowPackage.TRANSITION_MAPPING__END_STATE:
				return endState != null;
			case PageflowPackage.TRANSITION_MAPPING__TRANSITION:
				return transition != null;
		}
		return super.eIsSet(featureID);
	}

} //TransitionMappingImpl
