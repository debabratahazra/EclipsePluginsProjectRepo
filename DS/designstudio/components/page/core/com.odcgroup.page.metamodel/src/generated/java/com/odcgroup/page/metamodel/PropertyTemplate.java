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
 * A representation of the model object '<em><b>Property Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyTemplate#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyTemplate#isReadonly <em>Readonly</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyTemplate#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyTemplate()
 * @model
 * @generated
 */
public interface PropertyTemplate extends EObject {
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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyTemplate_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyTemplate#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Readonly</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Readonly</em>' attribute.
	 * @see #setReadonly(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyTemplate_Readonly()
	 * @model
	 * @generated
	 */
	boolean isReadonly();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyTemplate#isReadonly <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Readonly</em>' attribute.
	 * @see #isReadonly()
	 * @generated
	 */
	void setReadonly(boolean value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(PropertyType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyTemplate_Type()
	 * @model
	 * @generated
	 */
	PropertyType getType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyTemplate#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(PropertyType value);

} // PropertyTemplate