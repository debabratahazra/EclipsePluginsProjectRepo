/**
 */
package com.odcgroup.t24.localReferenceApplication.impl;

import com.odcgroup.mdf.metamodel.MdfClass;

import com.odcgroup.t24.localReferenceApplication.LocalField;
import com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication;
import com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Reference Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationImpl#getLocalField <em>Local Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationImpl#getForApplication <em>For Application</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalReferenceApplicationImpl extends EObjectImpl implements LocalReferenceApplication {
	/**
	 * The cached value of the '{@link #getLocalField() <em>Local Field</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalField()
	 * @generated
	 * @ordered
	 */
	protected EList<LocalField> localField;

	/**
	 * The cached value of the '{@link #getForApplication() <em>For Application</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForApplication()
	 * @generated
	 * @ordered
	 */
	protected MdfClass forApplication;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocalReferenceApplicationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LocalReferenceApplicationPackage.Literals.LOCAL_REFERENCE_APPLICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LocalField> getLocalField() {
		if (localField == null) {
			localField = new EObjectContainmentEList<LocalField>(LocalField.class, this, LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD);
		}
		return localField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass getForApplication() {
		if (forApplication != null && ((EObject)forApplication).eIsProxy()) {
			InternalEObject oldForApplication = (InternalEObject)forApplication;
			forApplication = (MdfClass)eResolveProxy(oldForApplication);
			if (forApplication != oldForApplication) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION, oldForApplication, forApplication));
			}
		}
		return forApplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass basicGetForApplication() {
		return forApplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForApplication(MdfClass newForApplication) {
		MdfClass oldForApplication = forApplication;
		forApplication = newForApplication;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION, oldForApplication, forApplication));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
				return ((InternalEList<?>)getLocalField()).basicRemove(otherEnd, msgs);
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
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
				return getLocalField();
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION:
				if (resolve) return getForApplication();
				return basicGetForApplication();
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
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
				getLocalField().clear();
				getLocalField().addAll((Collection<? extends LocalField>)newValue);
				return;
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION:
				setForApplication((MdfClass)newValue);
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
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
				getLocalField().clear();
				return;
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION:
				setForApplication((MdfClass)null);
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
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
				return localField != null && !localField.isEmpty();
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION:
				return forApplication != null;
		}
		return super.eIsSet(featureID);
	}

} //LocalReferenceApplicationImpl
