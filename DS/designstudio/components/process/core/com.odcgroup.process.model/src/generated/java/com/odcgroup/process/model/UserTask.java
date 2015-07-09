/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.UserTask#getPageflow <em>Pageflow</em>}</li>
 *   <li>{@link com.odcgroup.process.model.UserTask#getRule <em>Rule</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.process.model.ProcessPackage#getUserTask()
 * @model
 * @generated
 */
public interface UserTask extends Task {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Odyssey Financial Technologies";

	/**
	 * Returns the value of the '<em><b>Pageflow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pageflow</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pageflow</em>' containment reference.
	 * @see #setPageflow(Pageflow)
	 * @see com.odcgroup.process.model.ProcessPackage#getUserTask_Pageflow()
	 * @model containment="true"
	 * @generated
	 */
	Pageflow getPageflow();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.UserTask#getPageflow <em>Pageflow</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pageflow</em>' containment reference.
	 * @see #getPageflow()
	 * @generated
	 */
	void setPageflow(Pageflow value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(Rule)
	 * @see com.odcgroup.process.model.ProcessPackage#getUserTask_Rule()
	 * @model containment="true"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.UserTask#getRule <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

} // UserTask
