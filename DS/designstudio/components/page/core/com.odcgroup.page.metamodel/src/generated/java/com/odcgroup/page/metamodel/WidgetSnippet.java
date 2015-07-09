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
 * A representation of the model object '<em><b>Widget Snippet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetSnippet#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetSnippet#getSnippets <em>Snippets</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetSnippet()
 * @model
 * @generated
 */
public interface WidgetSnippet extends EObject {
	/**
	 * Returns the value of the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Type</em>' reference.
	 * @see #setWidgetType(WidgetType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetSnippet_WidgetType()
	 * @model required="true"
	 * @generated
	 */
	WidgetType getWidgetType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetSnippet#getWidgetType <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget Type</em>' reference.
	 * @see #getWidgetType()
	 * @generated
	 */
	void setWidgetType(WidgetType value);

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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetSnippet_Snippets()
	 * @model
	 * @generated
	 */
	EList<SnippetType> getSnippets();

} // WidgetSnippet
