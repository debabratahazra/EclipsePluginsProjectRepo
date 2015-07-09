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
 * A representation of the model object '<em><b>Event Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.EventModel#getFunctions <em>Functions</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.EventModel#getEvents <em>Events</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.EventModel#getEventTypes <em>Event Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventModel()
 * @model
 * @generated
 */
public interface EventModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.FunctionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventModel_Functions()
	 * @model containment="true"
	 * @generated
	 */
	EList<FunctionType> getFunctions();

	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.WidgetEvent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventModel_Events()
	 * @model containment="true"
	 * @generated
	 */
	EList<WidgetEvent> getEvents();

	/**
	 * Returns the value of the '<em><b>Event Types</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.EventType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Types</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Types</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventModel_EventTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<EventType> getEventTypes();

} // EventModel
