/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.impl;

import com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;
import com.odcgroup.edge.t24ui.contextEnquiry.Description;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context Enquiry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryImpl#getDescriptions <em>Descriptions</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryImpl#getEnquiries <em>Enquiries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ContextEnquiryImpl extends EObjectImpl implements ContextEnquiry {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDescriptions() <em>Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescriptions()
	 * @generated
	 * @ordered
	 */
	protected EList<Description> descriptions;

	/**
	 * The cached value of the '{@link #getEnquiries() <em>Enquiries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnquiries()
	 * @generated
	 * @ordered
	 */
	protected EList<AppliedEnquiry> enquiries;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContextEnquiryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContextEnquiryPackage.Literals.CONTEXT_ENQUIRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.CONTEXT_ENQUIRY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Description> getDescriptions() {
		if (descriptions == null) {
			descriptions = new EObjectContainmentEList<Description>(Description.class, this, ContextEnquiryPackage.CONTEXT_ENQUIRY__DESCRIPTIONS);
		}
		return descriptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AppliedEnquiry> getEnquiries() {
		if (enquiries == null) {
			enquiries = new EObjectContainmentEList<AppliedEnquiry>(AppliedEnquiry.class, this, ContextEnquiryPackage.CONTEXT_ENQUIRY__ENQUIRIES);
		}
		return enquiries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__DESCRIPTIONS:
				return ((InternalEList<?>)getDescriptions()).basicRemove(otherEnd, msgs);
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__ENQUIRIES:
				return ((InternalEList<?>)getEnquiries()).basicRemove(otherEnd, msgs);
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
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__NAME:
				return getName();
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__DESCRIPTIONS:
				return getDescriptions();
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__ENQUIRIES:
				return getEnquiries();
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
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__NAME:
				setName((String)newValue);
				return;
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__DESCRIPTIONS:
				getDescriptions().clear();
				getDescriptions().addAll((Collection<? extends Description>)newValue);
				return;
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__ENQUIRIES:
				getEnquiries().clear();
				getEnquiries().addAll((Collection<? extends AppliedEnquiry>)newValue);
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
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__DESCRIPTIONS:
				getDescriptions().clear();
				return;
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__ENQUIRIES:
				getEnquiries().clear();
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
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__DESCRIPTIONS:
				return descriptions != null && !descriptions.isEmpty();
			case ContextEnquiryPackage.CONTEXT_ENQUIRY__ENQUIRIES:
				return enquiries != null && !enquiries.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ContextEnquiryImpl
