/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.FunctionType#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.FunctionType#getEventTypes <em>Event Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.FunctionType#isAllowUserParameters <em>Allow User Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getFunctionType()
 * @model
 * @generated
 */
public interface FunctionType extends NamedType {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.ParameterType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getFunctionType_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterType> getParameters();

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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getFunctionType_EventTypes()
	 * @model
	 * @generated
	 */
	EList<EventType> getEventTypes();
	
	/**
	 * Returns the value of the '<em><b>Allow User Parameters</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allow User Parameters</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allow User Parameters</em>' attribute.
	 * @see #setAllowUserParameters(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getFunctionType_AllowUserParameters()
	 * @model default="false"
	 * @generated
	 */
	boolean isAllowUserParameters();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.FunctionType#isAllowUserParameters <em>Allow User Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allow User Parameters</em>' attribute.
	 * @see #isAllowUserParameters()
	 * @generated
	 */
	void setAllowUserParameters(boolean value);

	/**
	 * Finds the ParameterType with the given name.
	 * 
	 * @param name The name of the ParameterType
	 * @return ParameterType or null if no ParameterType can be found with this name
	 */
	public ParameterType findParameterType(String name);

} // FunctionType
