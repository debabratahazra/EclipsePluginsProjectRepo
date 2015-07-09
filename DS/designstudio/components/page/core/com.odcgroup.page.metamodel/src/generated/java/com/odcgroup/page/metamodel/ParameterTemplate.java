/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.ParameterTemplate#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.ParameterTemplate#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.ParameterTemplate#isUserDefined <em>User Defined</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.ParameterTemplate#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getParameterTemplate()
 * @model
 * @generated
 */
public interface ParameterTemplate extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(ParameterType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getParameterTemplate_Type()
	 * @model
	 * @generated
	 */
	ParameterType getType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.ParameterTemplate#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ParameterType value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getParameterTemplate_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.ParameterTemplate#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Defined</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Defined</em>' attribute.
	 * @see #setUserDefined(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getParameterTemplate_UserDefined()
	 * @model
	 * @generated
	 */
	boolean isUserDefined();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.ParameterTemplate#isUserDefined <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Defined</em>' attribute.
	 * @see #isUserDefined()
	 * @generated
	 */
	void setUserDefined(boolean value);

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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getParameterTemplate_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.ParameterTemplate#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // ParameterTemplate
