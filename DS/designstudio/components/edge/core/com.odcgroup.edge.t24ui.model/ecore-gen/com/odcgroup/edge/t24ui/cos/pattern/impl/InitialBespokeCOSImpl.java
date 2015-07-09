/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.BespokeCompositeScreen;

import com.odcgroup.edge.t24ui.cos.pattern.InitialBespokeCOS;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Initial Bespoke COS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialBespokeCOSImpl#getCompositeScreen <em>Composite Screen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InitialBespokeCOSImpl extends 
ChildResourceSpecImpl implements InitialBespokeCOS {
	/**
	 * The cached value of the '{@link #getCompositeScreen() <em>Composite Screen</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeScreen()
	 * @generated
	 * @ordered
	 */
	protected BespokeCompositeScreen compositeScreen;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InitialBespokeCOSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.INITIAL_BESPOKE_COS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BespokeCompositeScreen getCompositeScreen() {
		if (compositeScreen != null && compositeScreen.eIsProxy()) {
			InternalEObject oldCompositeScreen = (InternalEObject)compositeScreen;
			compositeScreen = (BespokeCompositeScreen)eResolveProxy(oldCompositeScreen);
			if (compositeScreen != oldCompositeScreen) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PatternPackage.INITIAL_BESPOKE_COS__COMPOSITE_SCREEN, oldCompositeScreen, compositeScreen));
			}
		}
		return compositeScreen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BespokeCompositeScreen basicGetCompositeScreen() {
		return compositeScreen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositeScreen(BespokeCompositeScreen newCompositeScreen) {
		BespokeCompositeScreen oldCompositeScreen = compositeScreen;
		compositeScreen = newCompositeScreen;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.INITIAL_BESPOKE_COS__COMPOSITE_SCREEN, oldCompositeScreen, compositeScreen));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PatternPackage.INITIAL_BESPOKE_COS__COMPOSITE_SCREEN:
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
			case PatternPackage.INITIAL_BESPOKE_COS__COMPOSITE_SCREEN:
				setCompositeScreen((BespokeCompositeScreen)newValue);
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
			case PatternPackage.INITIAL_BESPOKE_COS__COMPOSITE_SCREEN:
				setCompositeScreen((BespokeCompositeScreen)null);
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
			case PatternPackage.INITIAL_BESPOKE_COS__COMPOSITE_SCREEN:
				return compositeScreen != null;
		}
		return super.eIsSet(featureID);
	}

} //InitialBespokeCOSImpl
