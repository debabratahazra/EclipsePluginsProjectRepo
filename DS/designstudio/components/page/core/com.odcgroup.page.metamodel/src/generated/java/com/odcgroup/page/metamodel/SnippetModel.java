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
 * A representation of the model object '<em><b>Snippet Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.SnippetModel#getSnippets <em>Snippets</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.SnippetModel#getPropertyTypes <em>Property Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.SnippetModel#getWidgets <em>Widgets</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.SnippetModel#getEvents <em>Events</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getSnippetModel()
 * @model
 * @generated
 */
public interface SnippetModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Snippets</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.SnippetType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Snippets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Snippets</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getSnippetModel_Snippets()
	 * @model containment="true"
	 * @generated
	 */
	EList<SnippetType> getSnippets();

	/**
	 * Returns the value of the '<em><b>Property Types</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.PropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Types</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getSnippetModel_PropertyTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyType> getPropertyTypes();

	/**
	 * Returns the value of the '<em><b>Widgets</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.WidgetSnippet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widgets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widgets</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getSnippetModel_Widgets()
	 * @model containment="true"
	 * @generated
	 */
	EList<WidgetSnippet> getWidgets();

	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.EventSnippet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getSnippetModel_Events()
	 * @model containment="true"
	 * @generated
	 */
	EList<EventSnippet> getEvents();

} // SnippetModel
