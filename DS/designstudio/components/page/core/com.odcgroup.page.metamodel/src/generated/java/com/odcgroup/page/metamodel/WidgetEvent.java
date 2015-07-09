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
 * A representation of the model object '<em><b>Widget Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetEvent#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetEvent#getEventTypes <em>Event Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetEvent()
 * @model
 * @generated
 */
public interface WidgetEvent extends EObject {
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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetEvent_WidgetType()
	 * @model required="true"
	 * @generated
	 */
	WidgetType getWidgetType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetEvent#getWidgetType <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget Type</em>' reference.
	 * @see #getWidgetType()
	 * @generated
	 */
	void setWidgetType(WidgetType value);

	/**
	 * Returns the value of the '<em><b>Event Types</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.EventType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Types</em>' reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetEvent_EventTypes()
	 * @model
	 * @generated
	 */
	EList<EventType> getEventTypes();

} // WidgetEvent
