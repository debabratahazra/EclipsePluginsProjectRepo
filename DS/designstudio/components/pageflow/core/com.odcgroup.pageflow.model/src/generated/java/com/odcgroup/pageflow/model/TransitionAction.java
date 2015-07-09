/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.TransitionAction#getProblemManagement <em>Problem Management</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransitionAction()
 * @model
 * @generated
 */
public interface TransitionAction extends Action {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>Problem Management</b></em>' attribute.
	 * The default value is <code>"Continue"</code>.
	 * The literals are from the enumeration {@link com.odcgroup.pageflow.model.ProblemManagement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Problem Management</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Problem Management</em>' attribute.
	 * @see com.odcgroup.pageflow.model.ProblemManagement
	 * @see #setProblemManagement(ProblemManagement)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransitionAction_ProblemManagement()
	 * @model default="Continue" required="true"
	 * @generated
	 */
	ProblemManagement getProblemManagement();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.TransitionAction#getProblemManagement <em>Problem Management</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Problem Management</em>' attribute.
	 * @see com.odcgroup.pageflow.model.ProblemManagement
	 * @see #getProblemManagement()
	 * @generated
	 */
	void setProblemManagement(ProblemManagement value);

} // TransitionAction
