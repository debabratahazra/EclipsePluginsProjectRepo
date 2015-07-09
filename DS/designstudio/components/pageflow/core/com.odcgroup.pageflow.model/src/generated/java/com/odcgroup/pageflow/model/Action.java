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
 * A representation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.Action#getProperty <em>Property</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Action#getUri <em>Uri</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Action#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Action#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Action#isStopOnFailure <em>Stop On Failure</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getAction()
 * @model abstract="true"
 * @generated
 */
public interface Action extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.pageflow.model.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getAction_Property()
	 * @model type="com.odcgroup.pageflow.model.Property" containment="true"
	 * @generated
	 */
	EList getProperty();

	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getAction_Uri()
	 * @model unique="false" required="true"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Action#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

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
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getAction_Desc()
	 * @model unique="false"
	 * @generated
	 */
	String getDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Action#getDesc <em>Desc</em>}' attribute.
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
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getAction_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Action#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Stop On Failure</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stop On Failure</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stop On Failure</em>' attribute.
	 * @see #setStopOnFailure(boolean)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getAction_StopOnFailure()
	 * @model default="true"
	 * @generated
	 */
	boolean isStopOnFailure();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Action#isStopOnFailure <em>Stop On Failure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stop On Failure</em>' attribute.
	 * @see #isStopOnFailure()
	 * @generated
	 */
	void setStopOnFailure(boolean value);

} // Action
