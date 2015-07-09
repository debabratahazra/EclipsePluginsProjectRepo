/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Value Converter</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyValueConverter()
 * @model interface="true" abstract="true"
 * @generated NOT
 */
public interface PropertyValueConverter /*extends EObject*/ {
	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * @param value
	 * @return Object
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Object toObject(String value);

	/**
	 * <!-- begin-user-doc -->
	 * @param value
	 * @return String
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String toString(Object value);

} // PropertyValueConverter