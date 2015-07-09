/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.impl;

import com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;

import com.odcgroup.mdf.metamodel.MdfClass;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Context Enquiry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ApplicationContextEnquiryImpl#getAppliesToField <em>Applies To Field</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ApplicationContextEnquiryImpl#getAppliesTo <em>Applies To</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicationContextEnquiryImpl extends ContextEnquiryImpl implements ApplicationContextEnquiry {
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
	 * The cached value of the '{@link #getAppliesTo() <em>Applies To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliesTo()
	 * @generated
	 * @ordered
	 */
	protected MdfClass appliesTo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationContextEnquiryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContextEnquiryPackage.Literals.APPLICATION_CONTEXT_ENQUIRY;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD, oldAppliesToField, appliesToField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass getAppliesTo() {
		if (appliesTo != null && ((EObject)appliesTo).eIsProxy()) {
			InternalEObject oldAppliesTo = (InternalEObject)appliesTo;
			appliesTo = (MdfClass)eResolveProxy(oldAppliesTo);
			if (appliesTo != oldAppliesTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO, oldAppliesTo, appliesTo));
			}
		}
		return appliesTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass basicGetAppliesTo() {
		return appliesTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAppliesTo(MdfClass newAppliesTo) {
		MdfClass oldAppliesTo = appliesTo;
		appliesTo = newAppliesTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO, oldAppliesTo, appliesTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD:
				return getAppliesToField();
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO:
				if (resolve) return getAppliesTo();
				return basicGetAppliesTo();
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
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD:
				setAppliesToField((String)newValue);
				return;
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO:
				setAppliesTo((MdfClass)newValue);
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
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD:
				setAppliesToField(APPLIES_TO_FIELD_EDEFAULT);
				return;
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO:
				setAppliesTo((MdfClass)null);
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
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD:
				return APPLIES_TO_FIELD_EDEFAULT == null ? appliesToField != null : !APPLIES_TO_FIELD_EDEFAULT.equals(appliesToField);
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO:
				return appliesTo != null;
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

} //ApplicationContextEnquiryImpl
