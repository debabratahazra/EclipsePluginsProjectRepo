/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Snippet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.EventSnippet#getSnippets <em>Snippets</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventSnippet()
 * @model
 * @generated
 */
public interface EventSnippet extends EObject {
	/**
	 * Returns the value of the '<em><b>Snippets</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.SnippetType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Snippets</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Snippets</em>' reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventSnippet_Snippets()
	 * @model
	 * @generated
	 */
	EList<SnippetType> getSnippets();

} // EventSnippet
