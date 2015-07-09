/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.impl;

import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;
import com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry;

import com.odcgroup.t24.version.versionDSL.Version;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Context Enquiry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.VersionContextEnquiryImpl#getAppliesTo <em>Applies To</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.VersionContextEnquiryImpl#getAppliesToField <em>Applies To Field</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VersionContextEnquiryImpl extends ContextEnquiryImpl implements VersionContextEnquiry {
	/**
	 * The cached value of the '{@link #getAppliesTo() <em>Applies To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliesTo()
	 * @generated
	 * @ordered
	 */
	protected Version appliesTo;

	/**
	 * The default value of the '{@link #getAppliesToField() <em>Applies To Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliesToField()
	 * @generated
	 * @ordered
	 */
	protected static final String APPLIES_TO_FIELD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAppliesToField() <em>Applies To Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliesToField()
	 * @generated
	 * @ordered
	 */
	protected String appliesToField = APPLIES_TO_FIELD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionContextEnquiryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContextEnquiryPackage.Literals.VERSION_CONTEXT_ENQUIRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version getAppliesTo() {
		if (appliesTo != null && appliesTo.eIsProxy()) {
			InternalEObject oldAppliesTo = (InternalEObject)appliesTo;
			appliesTo = (Version)eResolveProxy(oldAppliesTo);
			if (appliesTo != oldAppliesTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO, oldAppliesTo, appliesTo));
			}
		}
		return appliesTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version basicGetAppliesTo() {
		return appliesTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAppliesTo(Version newAppliesTo) {
		Version oldAppliesTo = appliesTo;
		appliesTo = newAppliesTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO, oldAppliesTo, appliesTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAppliesToField() {
		return appliesToField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAppliesToField(String newAppliesToField) {
		String oldAppliesToField = appliesToField;
		appliesToField = newAppliesToField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD, oldAppliesToField, appliesToField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO:
				if (resolve) return getAppliesTo();
				return basicGetAppliesTo();
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD:
				return getAppliesToField();
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
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO:
				setAppliesTo((Version)newValue);
				return;
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD:
				setAppliesToField((String)newValue);
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
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO:
				setAppliesTo((Version)null);
				return;
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD:
				setAppliesToField(APPLIES_TO_FIELD_EDEFAULT);
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
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO:
				return appliesTo != null;
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD:
				return APPLIES_TO_FIELD_EDEFAULT == null ? appliesToField != null : !APPLIES_TO_FIELD_EDEFAULT.equals(appliesToField);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (appliesToField: ");
		result.append(appliesToField);
		result.append(')');
		return result.toString();
	}

} //VersionContextEnquiryImpl
