/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Snippet Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.SnippetType#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.SnippetType#getPropertyTypes <em>Property Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getSnippetType()
 * @model
 * @generated
 */
public interface SnippetType extends NamedType {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(SnippetType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getSnippetType_Parent()
	 * @model
	 * @generated
	 */
	SnippetType getParent();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.SnippetType#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(SnippetType value);

	/**
	 * Returns the value of the '<em><b>Property Types</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.PropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Types</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Types</em>' reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getSnippetType_PropertyTypes()
	 * @model
	 * @generated
	 */
	EList<PropertyType> getPropertyTypes();

} // SnippetType
