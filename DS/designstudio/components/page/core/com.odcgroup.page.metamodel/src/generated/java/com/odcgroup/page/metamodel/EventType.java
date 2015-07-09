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
 * A representation of the model object '<em><b>Event Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.EventType#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.EventType#getPropertyTypes <em>Property Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventType()
 * @model
 * @generated
 */
public interface EventType extends EObject {
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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventType_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.EventType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventType_PropertyTypes()
	 * @model
	 * @generated
	 */
	EList<PropertyType> getPropertyTypes();

} // EventType
