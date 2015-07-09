/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.ProblemManagement;
import com.odcgroup.pageflow.model.TransitionAction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionActionImpl#getProblemManagement <em>Problem Management</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionActionImpl extends ActionImpl implements TransitionAction {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The default value of the '{@link #getProblemManagement() <em>Problem Management</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProblemManagement()
	 * @generated
	 * @ordered
	 */
	protected static final ProblemManagement PROBLEM_MANAGEMENT_EDEFAULT = ProblemManagement.CONTINUE_LITERAL;

	/**
	 * The cached value of the '{@link #getProblemManagement() <em>Problem Management</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProblemManagement()
	 * @generated
	 * @ordered
	 */
	protected ProblemManagement problemManagement = PROBLEM_MANAGEMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PageflowPackage.Literals.TRANSITION_ACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProblemManagement getProblemManagement() {
		return problemManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProblemManagement(ProblemManagement newProblemManagement) {
		ProblemManagement oldProblemManagement = problemManagement;
		problemManagement = newProblemManagement == null ? PROBLEM_MANAGEMENT_EDEFAULT : newProblemManagement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION_ACTION__PROBLEM_MANAGEMENT, oldProblemManagement, problemManagement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PageflowPackage.TRANSITION_ACTION__PROBLEM_MANAGEMENT:
				return getProblemManagement();
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
			case PageflowPackage.TRANSITION_ACTION__PROBLEM_MANAGEMENT:
				setProblemManagement((ProblemManagement)newValue);
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
			case PageflowPackage.TRANSITION_ACTION__PROBLEM_MANAGEMENT:
				setProblemManagement(PROBLEM_MANAGEMENT_EDEFAULT);
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
			case PageflowPackage.TRANSITION_ACTION__PROBLEM_MANAGEMENT:
				return problemManagement != PROBLEM_MANAGEMENT_EDEFAULT;
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
		result.append(" (problemManagement: ");
		result.append(problemManagement);
		result.append(')');
		return result.toString();
	}

} //TransitionActionImpl
