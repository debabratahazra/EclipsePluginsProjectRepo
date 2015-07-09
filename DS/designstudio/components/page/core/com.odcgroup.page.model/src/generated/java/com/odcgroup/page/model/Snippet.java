/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Snippet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.Snippet#getTypeName <em>Type Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Snippet#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Snippet#getContents <em>Contents</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.model.ModelPackage#getSnippet()
 * @model
 * @generated
 */
public interface Snippet extends EObject {
	/**
	 * Returns the value of the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Name</em>' attribute.
	 * @see #setTypeName(String)
	 * @see com.odcgroup.page.model.ModelPackage#getSnippet_TypeName()
	 * @model
	 * @generated
	 */
	String getTypeName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Snippet#getTypeName <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Name</em>' attribute.
	 * @see #getTypeName()
	 * @generated
	 */
	void setTypeName(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getSnippet_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Snippet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contents</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getSnippet_Contents()
	 * @model containment="true"
	 * @generated
	 */
	EList<Snippet> getContents();

	/**
	 * Returns the property with the given name, or <code>null</code> if 
	 * it does not exists.   
	 * @param name the name of the requested property
	 * @return the property
	 */
	Property findProperty(String name);

} // Snippet
