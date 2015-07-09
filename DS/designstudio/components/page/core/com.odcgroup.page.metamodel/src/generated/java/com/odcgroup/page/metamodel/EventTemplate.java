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
 * A representation of the model object '<em><b>Event Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.EventTemplate#getParameterTemplates <em>Parameter Templates</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.EventTemplate#getFunctionType <em>Function Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.EventTemplate#getEventType <em>Event Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.EventTemplate#getNature <em>Nature</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventTemplate()
 * @model
 * @generated
 */
public interface EventTemplate extends EObject {
	/**
	 * Returns the value of the '<em><b>Parameter Templates</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.ParameterTemplate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Templates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Templates</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventTemplate_ParameterTemplates()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterTemplate> getParameterTemplates();

	/**
	 * Returns the value of the '<em><b>Function Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Type</em>' reference.
	 * @see #setFunctionType(FunctionType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventTemplate_FunctionType()
	 * @model
	 * @generated
	 */
	FunctionType getFunctionType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.EventTemplate#getFunctionType <em>Function Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Type</em>' reference.
	 * @see #getFunctionType()
	 * @generated
	 */
	void setFunctionType(FunctionType value);

	/**
	 * Returns the value of the '<em><b>Event Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Type</em>' attribute.
	 * @see #setEventType(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventTemplate_EventType()
	 * @model
	 * @generated
	 */
	String getEventType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.EventTemplate#getEventType <em>Event Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Type</em>' attribute.
	 * @see #getEventType()
	 * @generated
	 */
	void setEventType(String value);

	/**
	 * Returns the value of the '<em><b>Nature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nature</em>' attribute.
	 * @see #setNature(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getEventTemplate_Nature()
	 * @model
	 * @generated
	 */
	String getNature();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.EventTemplate#getNature <em>Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nature</em>' attribute.
	 * @see #getNature()
	 * @generated
	 */
	void setNature(String value);

} // EventTemplate
