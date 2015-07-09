/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sub Pageflow State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.SubPageflowState#getSubPageflow <em>Sub Pageflow</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.SubPageflowState#getTransitionMappings <em>Transition Mappings</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.SubPageflowState#getTechDesc <em>Tech Desc</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getSubPageflowState()
 * @model
 * @generated
 */
public interface SubPageflowState extends State {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>Sub Pageflow</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Pageflow</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Pageflow</em>' reference.
	 * @see #setSubPageflow(Pageflow)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getSubPageflowState_SubPageflow()
	 * @model required="true"
	 * @generated
	 */
	Pageflow getSubPageflow();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.SubPageflowState#getSubPageflow <em>Sub Pageflow</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub Pageflow</em>' reference.
	 * @see #getSubPageflow()
	 * @generated
	 */
	void setSubPageflow(Pageflow value);

	/**
	 * Returns the value of the '<em><b>Transition Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.pageflow.model.TransitionMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transition Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transition Mappings</em>' containment reference list.
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getSubPageflowState_TransitionMappings()
	 * @model type="com.odcgroup.pageflow.model.TransitionMapping" containment="true"
	 * @generated
	 */
	EList getTransitionMappings();

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
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getSubPageflowState_TechDesc()
	 * @model unique="false"
	 * @generated
	 */
	String getTechDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.SubPageflowState#getTechDesc <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tech Desc</em>' attribute.
	 * @see #getTechDesc()
	 * @generated
	 */
	void setTechDesc(String value);

} // SubPageflowState
