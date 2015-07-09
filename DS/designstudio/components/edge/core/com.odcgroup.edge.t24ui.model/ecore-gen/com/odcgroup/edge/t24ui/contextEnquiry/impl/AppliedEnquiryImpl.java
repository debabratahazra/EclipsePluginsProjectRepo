/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.AutoLaunch;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;
import com.odcgroup.edge.t24ui.contextEnquiry.Description;
import com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Applied Enquiry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.AppliedEnquiryImpl#getEnquiry <em>Enquiry</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.AppliedEnquiryImpl#getSelectionFields <em>Selection Fields</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.AppliedEnquiryImpl#getDescriptions <em>Descriptions</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.AppliedEnquiryImpl#getAutoLaunch <em>Auto Launch</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AppliedEnquiryImpl extends EObjectImpl implements AppliedEnquiry {
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
	 * The cached value of the '{@link #getSelectionFields() <em>Selection Fields</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectionFields()
	 * @generated
	 * @ordered
	 */
	protected EList<SelectionCriteria> selectionFields;

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
	 * The cached value of the '{@link #getAutoLaunch() <em>Auto Launch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoLaunch()
	 * @generated
	 * @ordered
	 */
	protected AutoLaunch autoLaunch;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AppliedEnquiryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContextEnquiryPackage.Literals.APPLIED_ENQUIRY;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextEnquiryPackage.APPLIED_ENQUIRY__ENQUIRY, oldEnquiry, enquiry));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.APPLIED_ENQUIRY__ENQUIRY, oldEnquiry, enquiry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SelectionCriteria> getSelectionFields() {
		if (selectionFields == null) {
			selectionFields = new EObjectContainmentEList<SelectionCriteria>(SelectionCriteria.class, this, ContextEnquiryPackage.APPLIED_ENQUIRY__SELECTION_FIELDS);
		}
		return selectionFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Description> getDescriptions() {
		if (descriptions == null) {
			descriptions = new EObjectContainmentEList<Description>(Description.class, this, ContextEnquiryPackage.APPLIED_ENQUIRY__DESCRIPTIONS);
		}
		return descriptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AutoLaunch getAutoLaunch() {
		return autoLaunch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAutoLaunch(AutoLaunch newAutoLaunch, NotificationChain msgs) {
		AutoLaunch oldAutoLaunch = autoLaunch;
		autoLaunch = newAutoLaunch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH, oldAutoLaunch, newAutoLaunch);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoLaunch(AutoLaunch newAutoLaunch) {
		if (newAutoLaunch != autoLaunch) {
			NotificationChain msgs = null;
			if (autoLaunch != null)
				msgs = ((InternalEObject)autoLaunch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH, null, msgs);
			if (newAutoLaunch != null)
				msgs = ((InternalEObject)newAutoLaunch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH, null, msgs);
			msgs = basicSetAutoLaunch(newAutoLaunch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH, newAutoLaunch, newAutoLaunch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ContextEnquiryPackage.APPLIED_ENQUIRY__SELECTION_FIELDS:
				return ((InternalEList<?>)getSelectionFields()).basicRemove(otherEnd, msgs);
			case ContextEnquiryPackage.APPLIED_ENQUIRY__DESCRIPTIONS:
				return ((InternalEList<?>)getDescriptions()).basicRemove(otherEnd, msgs);
			case ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH:
				return basicSetAutoLaunch(null, msgs);
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
			case ContextEnquiryPackage.APPLIED_ENQUIRY__ENQUIRY:
				if (resolve) return getEnquiry();
				return basicGetEnquiry();
			case ContextEnquiryPackage.APPLIED_ENQUIRY__SELECTION_FIELDS:
				return getSelectionFields();
			case ContextEnquiryPackage.APPLIED_ENQUIRY__DESCRIPTIONS:
				return getDescriptions();
			case ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH:
				return getAutoLaunch();
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
			case ContextEnquiryPackage.APPLIED_ENQUIRY__ENQUIRY:
				setEnquiry((Enquiry)newValue);
				return;
			case ContextEnquiryPackage.APPLIED_ENQUIRY__SELECTION_FIELDS:
				getSelectionFields().clear();
				getSelectionFields().addAll((Collection<? extends SelectionCriteria>)newValue);
				return;
			case ContextEnquiryPackage.APPLIED_ENQUIRY__DESCRIPTIONS:
				getDescriptions().clear();
				getDescriptions().addAll((Collection<? extends Description>)newValue);
				return;
			case ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH:
				setAutoLaunch((AutoLaunch)newValue);
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
			case ContextEnquiryPackage.APPLIED_ENQUIRY__ENQUIRY:
				setEnquiry((Enquiry)null);
				return;
			case ContextEnquiryPackage.APPLIED_ENQUIRY__SELECTION_FIELDS:
				getSelectionFields().clear();
				return;
			case ContextEnquiryPackage.APPLIED_ENQUIRY__DESCRIPTIONS:
				getDescriptions().clear();
				return;
			case ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH:
				setAutoLaunch((AutoLaunch)null);
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
			case ContextEnquiryPackage.APPLIED_ENQUIRY__ENQUIRY:
				return enquiry != null;
			case ContextEnquiryPackage.APPLIED_ENQUIRY__SELECTION_FIELDS:
				return selectionFields != null && !selectionFields.isEmpty();
			case ContextEnquiryPackage.APPLIED_ENQUIRY__DESCRIPTIONS:
				return descriptions != null && !descriptions.isEmpty();
			case ContextEnquiryPackage.APPLIED_ENQUIRY__AUTO_LAUNCH:
				return autoLaunch != null;
		}
		return super.eIsSet(featureID);
	}

} //AppliedEnquiryImpl
