/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.TransitionMapping#getEndState <em>End State</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.TransitionMapping#getTransition <em>Transition</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransitionMapping()
 * @model
 * @generated
 */
public interface TransitionMapping extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>End State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End State</em>' reference.
	 * @see #setEndState(EndState)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransitionMapping_EndState()
	 * @model required="true"
	 * @generated
	 */
	EndState getEndState();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.TransitionMapping#getEndState <em>End State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End State</em>' reference.
	 * @see #getEndState()
	 * @generated
	 */
	void setEndState(EndState value);

	/**
	 * Returns the value of the '<em><b>Transition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transition</em>' reference.
	 * @see #setTransition(Transition)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getTransitionMapping_Transition()
	 * @model required="true"
	 * @generated
	 */
	Transition getTransition();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.TransitionMapping#getTransition <em>Transition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transition</em>' reference.
	 * @see #getTransition()
	 * @generated
	 */
	void setTransition(Transition value);

} // TransitionMapping
