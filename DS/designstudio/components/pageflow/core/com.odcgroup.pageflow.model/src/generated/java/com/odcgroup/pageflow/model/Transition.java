/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.Transition#getActions <em>Actions</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Transition#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Transition#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Transition#getFromState <em>From State</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Transition#getToState <em>To State</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Transition#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Transition#getTechDesc <em>Tech Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Transition#isIsIdempotent <em>Is Idempotent</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.pageflow.model.TransitionAction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition_Actions()
	 * @model type="com.odcgroup.pageflow.model.TransitionAction" containment="true"
	 * @generated
	 */
	EList getActions();

	/**
	 * Returns the value of the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Desc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Desc</em>' attribute.
	 * @see #setDesc(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition_Desc()
	 * @model unique="false"
	 * @generated
	 */
	String getDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Transition#getDesc <em>Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Desc</em>' attribute.
	 * @see #getDesc()
	 * @generated
	 */
	void setDesc(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Transition#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>From State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From State</em>' reference.
	 * @see #setFromState(State)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition_FromState()
	 * @model required="true"
	 * @generated
	 */
	State getFromState();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Transition#getFromState <em>From State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From State</em>' reference.
	 * @see #getFromState()
	 * @generated
	 */
	void setFromState(State value);

	/**
	 * Returns the value of the '<em><b>To State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To State</em>' reference.
	 * @see #setToState(State)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition_ToState()
	 * @model required="true"
	 * @generated
	 */
	State getToState();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Transition#getToState <em>To State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To State</em>' reference.
	 * @see #getToState()
	 * @generated
	 */
	void setToState(State value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition_DisplayName()
	 * @model required="true"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Transition#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

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
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition_TechDesc()
	 * @model unique="false"
	 * @generated
	 */
	String getTechDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Transition#getTechDesc <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tech Desc</em>' attribute.
	 * @see #getTechDesc()
	 * @generated
	 */
	void setTechDesc(String value);

	/**
	 * Returns the value of the '<em><b>Is Idempotent</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Idempotent</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Idempotent</em>' attribute.
	 * @see #setIsIdempotent(boolean)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransition_IsIdempotent()
	 * @model default="true"
	 * @generated
	 */
	boolean isIsIdempotent();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Transition#isIsIdempotent <em>Is Idempotent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Idempotent</em>' attribute.
	 * @see #isIsIdempotent()
	 * @generated
	 */
	void setIsIdempotent(boolean value);

} // Transition
