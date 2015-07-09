/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Initial Enquiry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialEnquiryImpl#getEnquiry <em>Enquiry</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InitialEnquiryImpl extends 
ChildResourceSpecImpl implements InitialEnquiry {
	/**
	 * The cached value of the '{@link #getEnquiry() <em>Enquiry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnquiry()
	 * @generated
	 * @ordered
	 */
	protected Enquiry enquiry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InitialEnquiryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.INITIAL_ENQUIRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enquiry getEnquiry() {
		if (enquiry != null && enquiry.eIsProxy()) {
			InternalEObject oldEnquiry = (InternalEObject)enquiry;
			enquiry = (Enquiry)eResolveProxy(oldEnquiry);
			if (enquiry != oldEnquiry) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PatternPackage.INITIAL_ENQUIRY__ENQUIRY, oldEnquiry, enquiry));
			}
		}
		return enquiry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enquiry basicGetEnquiry() {
		return enquiry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnquiry(Enquiry newEnquiry) {
		Enquiry oldEnquiry = enquiry;
		enquiry = newEnquiry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.INITIAL_ENQUIRY__ENQUIRY, oldEnquiry, enquiry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PatternPackage.INITIAL_ENQUIRY__ENQUIRY:
				if (resolve) return getEnquiry();
				return basicGetEnquiry();
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
			case PatternPackage.INITIAL_ENQUIRY__ENQUIRY:
				setEnquiry((Enquiry)newValue);
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
			case PatternPackage.INITIAL_ENQUIRY__ENQUIRY:
				setEnquiry((Enquiry)null);
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
			case PatternPackage.INITIAL_ENQUIRY__ENQUIRY:
				return enquiry != null;
		}
		return super.eIsSet(featureID);
	}

} //InitialEnquiryImpl
