/**
 */
package com.odcgroup.edge.t24ui.cos.bespoke.impl;

import com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel;
import com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage;

import com.odcgroup.mdf.metamodel.MdfClass;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Panel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.ApplicationPanelImpl#getApplication <em>Application</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicationPanelImpl extends PanelImpl implements ApplicationPanel {
	/**
	 * The cached value of the '{@link #getApplication() <em>Application</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplication()
	 * @generated
	 * @ordered
	 */
	protected MdfClass application;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationPanelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BespokePackage.Literals.APPLICATION_PANEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass getApplication() {
		if (application != null && ((EObject)application).eIsProxy()) {
			InternalEObject oldApplication = (InternalEObject)application;
			application = (MdfClass)eResolveProxy(oldApplication);
			if (application != oldApplication) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BespokePackage.APPLICATION_PANEL__APPLICATION, oldApplication, application));
			}
		}
		return application;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass basicGetApplication() {
		return application;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setApplication(MdfClass newApplication) {
		MdfClass oldApplication = application;
		application = newApplication;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BespokePackage.APPLICATION_PANEL__APPLICATION, oldApplication, application));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BespokePackage.APPLICATION_PANEL__APPLICATION:
				if (resolve) return getApplication();
				return basicGetApplication();
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
			case BespokePackage.APPLICATION_PANEL__APPLICATION:
				setApplication((MdfClass)newValue);
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
			case BespokePackage.APPLICATION_PANEL__APPLICATION:
				setApplication((MdfClass)null);
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
			case BespokePackage.APPLICATION_PANEL__APPLICATION:
				return application != null;
		}
		return super.eIsSet(featureID);
	}

} //ApplicationPanelImpl
