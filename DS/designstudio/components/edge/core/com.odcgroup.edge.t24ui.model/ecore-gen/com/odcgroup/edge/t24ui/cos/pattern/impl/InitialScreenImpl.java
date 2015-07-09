/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.cos.pattern.InitialScreen;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;

import com.odcgroup.t24.version.versionDSL.Version;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Initial Screen</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialScreenImpl#getScreen <em>Screen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InitialScreenImpl extends 
ChildResourceSpecImpl implements InitialScreen {
	/**
	 * The cached value of the '{@link #getScreen() <em>Screen</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScreen()
	 * @generated
	 * @ordered
	 */
	protected Version screen;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InitialScreenImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.INITIAL_SCREEN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version getScreen() {
		if (screen != null && screen.eIsProxy()) {
			InternalEObject oldScreen = (InternalEObject)screen;
			screen = (Version)eResolveProxy(oldScreen);
			if (screen != oldScreen) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PatternPackage.INITIAL_SCREEN__SCREEN, oldScreen, screen));
			}
		}
		return screen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version basicGetScreen() {
		return screen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScreen(Version newScreen) {
		Version oldScreen = screen;
		screen = newScreen;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.INITIAL_SCREEN__SCREEN, oldScreen, screen));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PatternPackage.INITIAL_SCREEN__SCREEN:
				if (resolve) return getScreen();
				return basicGetScreen();
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
			case PatternPackage.INITIAL_SCREEN__SCREEN:
				setScreen((Version)newValue);
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
			case PatternPackage.INITIAL_SCREEN__SCREEN:
				setScreen((Version)null);
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
			case PatternPackage.INITIAL_SCREEN__SCREEN:
				return screen != null;
		}
		return super.eIsSet(featureID);
	}

} //InitialScreenImpl
