/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.process.model.Pageflow;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.Rule;
import com.odcgroup.process.model.UserTask;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.impl.UserTaskImpl#getPageflow <em>Pageflow</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.UserTaskImpl#getRule <em>Rule</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UserTaskImpl extends TaskImpl implements UserTask {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * The cached value of the '{@link #getPageflow() <em>Pageflow</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPageflow()
	 * @generated
	 * @ordered
	 */
	protected Pageflow pageflow;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Rule rule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserTaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ProcessPackage.Literals.USER_TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pageflow getPageflow() {
		return pageflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPageflow(Pageflow newPageflow, NotificationChain msgs) {
		Pageflow oldPageflow = pageflow;
		pageflow = newPageflow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProcessPackage.USER_TASK__PAGEFLOW, oldPageflow, newPageflow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPageflow(Pageflow newPageflow) {
		if (newPageflow != pageflow) {
			NotificationChain msgs = null;
			if (pageflow != null)
				msgs = ((InternalEObject)pageflow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.USER_TASK__PAGEFLOW, null, msgs);
			if (newPageflow != null)
				msgs = ((InternalEObject)newPageflow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.USER_TASK__PAGEFLOW, null, msgs);
			msgs = basicSetPageflow(newPageflow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.USER_TASK__PAGEFLOW, newPageflow, newPageflow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRule(Rule newRule, NotificationChain msgs) {
		Rule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProcessPackage.USER_TASK__RULE, oldRule, newRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(Rule newRule) {
		if (newRule != rule) {
			NotificationChain msgs = null;
			if (rule != null)
				msgs = ((InternalEObject)rule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.USER_TASK__RULE, null, msgs);
			if (newRule != null)
				msgs = ((InternalEObject)newRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.USER_TASK__RULE, null, msgs);
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.USER_TASK__RULE, newRule, newRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProcessPackage.USER_TASK__PAGEFLOW:
				return basicSetPageflow(null, msgs);
			case ProcessPackage.USER_TASK__RULE:
				return basicSetRule(null, msgs);
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
			case ProcessPackage.USER_TASK__PAGEFLOW:
				return getPageflow();
			case ProcessPackage.USER_TASK__RULE:
				return getRule();
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
			case ProcessPackage.USER_TASK__PAGEFLOW:
				setPageflow((Pageflow)newValue);
				return;
			case ProcessPackage.USER_TASK__RULE:
				setRule((Rule)newValue);
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
			case ProcessPackage.USER_TASK__PAGEFLOW:
				setPageflow((Pageflow)null);
				return;
			case ProcessPackage.USER_TASK__RULE:
				setRule((Rule)null);
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
			case ProcessPackage.USER_TASK__PAGEFLOW:
				return pageflow != null;
			case ProcessPackage.USER_TASK__RULE:
				return rule != null;
		}
		return super.eIsSet(featureID);
	}

} //UserTaskImpl
