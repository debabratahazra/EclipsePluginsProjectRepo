/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Decision State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.DecisionState#getAction <em>Action</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.DecisionState#getTechDesc <em>Tech Desc</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getDecisionState()
 * @model
 * @generated
 */
public interface DecisionState extends State {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' containment reference.
	 * @see #setAction(DecisionAction)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getDecisionState_Action()
	 * @model containment="true" required="true"
	 * @generated
	 */
	DecisionAction getAction();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.DecisionState#getAction <em>Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' containment reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(DecisionAction value);

	/**
	 * Returns the value of the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tech Desc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tech Desc</em>' attribute.
	 * @see #setTechDesc(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getDecisionState_TechDesc()
	 * @model unique="false"
	 * @generated
	 */
	String getTechDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.DecisionState#getTechDesc <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tech Desc</em>' attribute.
	 * @see #getTechDesc()
	 * @generated
	 */
	void setTechDesc(String value);

} // DecisionState
