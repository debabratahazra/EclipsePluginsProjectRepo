/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pageflow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.Pageflow#getURI <em>URI</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Pageflow#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Pageflow#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.process.model.ProcessPackage#getPageflow()
 * @model
 * @generated
 */
public interface Pageflow extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Odyssey Financial Technologies";

	/**
	 * Returns the value of the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>URI</em>' attribute.
	 * @see #setURI(String)
	 * @see com.odcgroup.process.model.ProcessPackage#getPageflow_URI()
	 * @model unique="false" required="true"
	 * @generated
	 */
	String getURI();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Pageflow#getURI <em>URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>URI</em>' attribute.
	 * @see #getURI()
	 * @generated
	 */
	void setURI(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.odcgroup.process.model.ProcessPackage#getPageflow_Description()
	 * @model unique="false"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Pageflow#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.process.model.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.odcgroup.process.model.ProcessPackage#getPageflow_Property()
	 * @model type="com.odcgroup.process.model.Property" containment="true"
	 * @generated
	 */
	EList getProperty();

} // Pageflow
