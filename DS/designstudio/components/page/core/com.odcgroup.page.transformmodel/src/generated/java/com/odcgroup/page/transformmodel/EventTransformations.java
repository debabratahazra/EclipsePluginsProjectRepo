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
 * A representation of the model object '<em><b>Event Transformations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.EventTransformations#getTransformations <em>Transformations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getEventTransformations()
 * @model
 * @generated
 */
public interface EventTransformations extends EObject {
	/**
	 * Returns the value of the '<em><b>Transformations</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.transformmodel.EventTransformation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformations</em>' containment reference list.
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getEventTransformations_Transformations()
	 * @model containment="true"
	 * @generated
	 */
	EList<EventTransformation> getTransformations();
	
	/**
	 * Finds the EventTransformation given the eventName.
	 * 
	 * @param eventName The name of the event
	 * @return EventTransformation The EventTransformation or null if no event transformation can be found
	 */
	public EventTransformation findEventTransformation(String eventName);	

	/**
	 * Finds the EventTransformation given the typeName.
	 * 
	 * @param typeName The type name
	 * @return EventTransformation The EventTransformation or null if no event transformation can be found
	 */
	public EventTransformation findEventTransformationFromTypeName(String typeName);
	
} // EventTransformations
