/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.EventType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.EventTransformation#getTypeName <em>Type Name</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.EventTransformation#getEventType <em>Event Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getEventTransformation()
 * @model
 * @generated
 */
public interface EventTransformation extends EObject {
	/**
	 * Returns the value of the '<em><b>Event Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Type</em>' reference.
	 * @see #setEventType(EventType)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getEventTransformation_EventType()
	 * @model
	 * @generated
	 */
	EventType getEventType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.EventTransformation#getEventType <em>Event Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Type</em>' reference.
	 * @see #getEventType()
	 * @generated
	 */
	void setEventType(EventType value);

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
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getEventTransformation_TypeName()
	 * @model
	 * @generated
	 */
	String getTypeName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.EventTransformation#getTypeName <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Name</em>' attribute.
	 * @see #getTypeName()
	 * @generated
	 */
	void setTypeName(String value);

} // EventTransformation
