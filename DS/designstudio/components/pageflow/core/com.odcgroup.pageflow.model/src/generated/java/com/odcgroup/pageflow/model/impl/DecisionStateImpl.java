/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.pageflow.model.DecisionAction;
import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Decision State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.impl.DecisionStateImpl#getAction <em>Action</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.DecisionStateImpl#getTechDesc <em>Tech Desc</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DecisionStateImpl extends StateImpl implements DecisionState {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The cached value of the '{@link #getAction() <em>Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected DecisionAction action;

	/**
	 * The default value of the '{@link #getTechDesc() <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTechDesc()
	 * @generated
	 * @ordered
	 */
	protected static final String TECH_DESC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTechDesc() <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTechDesc()
	 * @generated
	 * @ordered
	 */
	protected String techDesc = TECH_DESC_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DecisionStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PageflowPackage.Literals.DECISION_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionAction getAction() {
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAction(DecisionAction newAction, NotificationChain msgs) {
		DecisionAction oldAction = action;
		action = newAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PageflowPackage.DECISION_STATE__ACTION, oldAction, newAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAction(DecisionAction newAction) {
		if (newAction != action) {
			NotificationChain msgs = null;
			if (action != null)
				msgs = ((InternalEObject)action).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PageflowPackage.DECISION_STATE__ACTION, null, msgs);
			if (newAction != null)
				msgs = ((InternalEObject)newAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PageflowPackage.DECISION_STATE__ACTION, null, msgs);
			msgs = basicSetAction(newAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.DECISION_STATE__ACTION, newAction, newAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTechDesc() {
		return techDesc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTechDesc(String newTechDesc) {
		String oldTechDesc = techDesc;
		techDesc = newTechDesc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.DECISION_STATE__TECH_DESC, oldTechDesc, techDesc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PageflowPackage.DECISION_STATE__ACTION:
				return basicSetAction(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PageflowPackage.DECISION_STATE__ACTION:
				return getAction();
			case PageflowPackage.DECISION_STATE__TECH_DESC:
				return getTechDesc();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PageflowPackage.DECISION_STATE__ACTION:
				setAction((DecisionAction)newValue);
				return;
			case PageflowPackage.DECISION_STATE__TECH_DESC:
				setTechDesc((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case PageflowPackage.DECISION_STATE__ACTION:
				setAction((DecisionAction)null);
				return;
			case PageflowPackage.DECISION_STATE__TECH_DESC:
				setTechDesc(TECH_DESC_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PageflowPackage.DECISION_STATE__ACTION:
				return action != null;
			case PageflowPackage.DECISION_STATE__TECH_DESC:
				return TECH_DESC_EDEFAULT == null ? techDesc != null : !TECH_DESC_EDEFAULT.equals(techDesc);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (techDesc: ");
		result.append(techDesc);
		result.append(')');
		return result.toString();
	}

} //DecisionStateImpl
