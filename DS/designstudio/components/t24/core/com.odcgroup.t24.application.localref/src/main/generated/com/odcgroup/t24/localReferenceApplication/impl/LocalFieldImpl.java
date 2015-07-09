/**
 */
package com.odcgroup.t24.localReferenceApplication.impl;

import com.odcgroup.t24.localReferenceApplication.LocalField;
import com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl#getGroupName <em>Group Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl#getLocalRefID <em>Local Ref ID</em>}</li>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl#getToolTip <em>Tool Tip</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalFieldImpl extends EObjectImpl implements LocalField {
	/**
	 * The default value of the '{@link #getGroupName() <em>Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupName()
	 * @generated
	 * @ordered
	 */
	protected static final String GROUP_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGroupName() <em>Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupName()
	 * @generated
	 * @ordered
	 */
	protected String groupName = GROUP_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocalRefID() <em>Local Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalRefID()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCAL_REF_ID_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getLocalRefID() <em>Local Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalRefID()
	 * @generated
	 * @ordered
	 */
	protected String localRefID = LOCAL_REF_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected Translations label;

	/**
	 * The cached value of the '{@link #getToolTip() <em>Tool Tip</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolTip()
	 * @generated
	 * @ordered
	 */
	protected Translations toolTip;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocalFieldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LocalReferenceApplicationPackage.Literals.LOCAL_FIELD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupName(String newGroupName) {
		String oldGroupName = groupName;
		groupName = newGroupName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalReferenceApplicationPackage.LOCAL_FIELD__GROUP_NAME, oldGroupName, groupName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocalRefID() {
		return localRefID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalRefID(String newLocalRefID) {
		String oldLocalRefID = localRefID;
		localRefID = newLocalRefID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalReferenceApplicationPackage.LOCAL_FIELD__LOCAL_REF_ID, oldLocalRefID, localRefID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Translations getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLabel(Translations newLabel, NotificationChain msgs) {
		Translations oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL, oldLabel, newLabel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(Translations newLabel) {
		if (newLabel != label) {
			NotificationChain msgs = null;
			if (label != null)
				msgs = ((InternalEObject)label).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL, null, msgs);
			if (newLabel != null)
				msgs = ((InternalEObject)newLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL, null, msgs);
			msgs = basicSetLabel(newLabel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL, newLabel, newLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Translations getToolTip() {
		return toolTip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetToolTip(Translations newToolTip, NotificationChain msgs) {
		Translations oldToolTip = toolTip;
		toolTip = newToolTip;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP, oldToolTip, newToolTip);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToolTip(Translations newToolTip) {
		if (newToolTip != toolTip) {
			NotificationChain msgs = null;
			if (toolTip != null)
				msgs = ((InternalEObject)toolTip).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP, null, msgs);
			if (newToolTip != null)
				msgs = ((InternalEObject)newToolTip).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP, null, msgs);
			msgs = basicSetToolTip(newToolTip, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP, newToolTip, newToolTip));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL:
				return basicSetLabel(null, msgs);
			case LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP:
				return basicSetToolTip(null, msgs);
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
			case LocalReferenceApplicationPackage.LOCAL_FIELD__GROUP_NAME:
				return getGroupName();
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LOCAL_REF_ID:
				return getLocalRefID();
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL:
				return getLabel();
			case LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP:
				return getToolTip();
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
			case LocalReferenceApplicationPackage.LOCAL_FIELD__GROUP_NAME:
				setGroupName((String)newValue);
				return;
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LOCAL_REF_ID:
				setLocalRefID((String)newValue);
				return;
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL:
				setLabel((Translations)newValue);
				return;
			case LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP:
				setToolTip((Translations)newValue);
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
			case LocalReferenceApplicationPackage.LOCAL_FIELD__GROUP_NAME:
				setGroupName(GROUP_NAME_EDEFAULT);
				return;
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LOCAL_REF_ID:
				setLocalRefID(LOCAL_REF_ID_EDEFAULT);
				return;
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL:
				setLabel((Translations)null);
				return;
			case LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP:
				setToolTip((Translations)null);
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
			case LocalReferenceApplicationPackage.LOCAL_FIELD__GROUP_NAME:
				return GROUP_NAME_EDEFAULT == null ? groupName != null : !GROUP_NAME_EDEFAULT.equals(groupName);
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LOCAL_REF_ID:
				return LOCAL_REF_ID_EDEFAULT == null ? localRefID != null : !LOCAL_REF_ID_EDEFAULT.equals(localRefID);
			case LocalReferenceApplicationPackage.LOCAL_FIELD__LABEL:
				return label != null;
			case LocalReferenceApplicationPackage.LOCAL_FIELD__TOOL_TIP:
				return toolTip != null;
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
		result.append(" (groupName: ");
		result.append(groupName);
		result.append(", localRefID: ");
		result.append(localRefID);
		result.append(')');
		return result.toString();
	}

} //LocalFieldImpl
