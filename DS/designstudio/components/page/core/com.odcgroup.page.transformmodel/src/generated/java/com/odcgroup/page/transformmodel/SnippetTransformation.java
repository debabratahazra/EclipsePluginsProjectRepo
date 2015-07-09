/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import com.odcgroup.page.metamodel.SnippetType;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Snippet Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.SnippetTransformation#getSnippetType <em>Snippet Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSnippetTransformation()
 * @model
 * @generated
 */
public interface SnippetTransformation extends EObject {
	/**
	 * Returns the value of the '<em><b>Snippet Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Snippet Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Snippet Type</em>' reference.
	 * @see #setSnippetType(SnippetType)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSnippetTransformation_SnippetType()
	 * @model
	 * @generated
	 */
	SnippetType getSnippetType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.SnippetTransformation#getSnippetType <em>Snippet Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Snippet Type</em>' reference.
	 * @see #getSnippetType()
	 * @generated
	 */
	void setSnippetType(SnippetType value);

} // SnippetTransformation
