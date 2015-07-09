/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.CompositeScreen;

import com.odcgroup.edge.t24ui.cos.pattern.InitialCOS;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Initial COS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialCOSImpl#getCompositeScreen <em>Composite Screen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InitialCOSImpl extends 
ChildResourceSpecImpl implements InitialCOS {
	/**
	 * The cached value of the '{@link #getCompositeScreen() <em>Composite Screen</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeScreen()
	 * @generated
	 * @ordered
	 */
	protected CompositeScreen compositeScreen;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InitialCOSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.INITIAL_COS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeScreen getCompositeScreen() {
		if (compositeScreen != null && compositeScreen.eIsProxy()) {
			InternalEObject oldCompositeScreen = (InternalEObject)compositeScreen;
			compositeScreen = (CompositeScreen)eResolveProxy(oldCompositeScreen);
			if (compositeScreen != oldCompositeScreen) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PatternPackage.INITIAL_COS__COMPOSITE_SCREEN, oldCompositeScreen, compositeScreen));
			}
		}
		return compositeScreen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeScreen basicGetCompositeScreen() {
		return compositeScreen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositeScreen(CompositeScreen newCompositeScreen) {
		CompositeScreen oldCompositeScreen = compositeScreen;
		compositeScreen = newCompositeScreen;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.INITIAL_COS__COMPOSITE_SCREEN, oldCompositeScreen, compositeScreen));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PatternPackage.INITIAL_COS__COMPOSITE_SCREEN:
				if (resolve) return getCompositeScreen();
				return basicGetCompositeScreen();
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
			case PatternPackage.INITIAL_COS__COMPOSITE_SCREEN:
				setCompositeScreen((CompositeScreen)newValue);
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
			case PatternPackage.INITIAL_COS__COMPOSITE_SCREEN:
				setCompositeScreen((CompositeScreen)null);
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
			case PatternPackage.INITIAL_COS__COMPOSITE_SCREEN:
				return compositeScreen != null;
		}
		return super.eIsSet(featureID);
	}

} //InitialCOSImpl
