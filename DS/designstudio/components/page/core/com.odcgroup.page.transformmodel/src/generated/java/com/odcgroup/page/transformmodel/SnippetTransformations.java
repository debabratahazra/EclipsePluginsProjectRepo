/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Snippet Transformations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.SnippetTransformations#getTransformations <em>Transformations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSnippetTransformations()
 * @model
 * @generated
 */
public interface SnippetTransformations extends EObject {
	/**
	 * Returns the value of the '<em><b>Transformations</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.transformmodel.SnippetTransformation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformations</em>' containment reference list.
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSnippetTransformations_Transformations()
	 * @model containment="true"
	 * @generated
	 */
	EList<SnippetTransformation> getTransformations();

} // SnippetTransformations
