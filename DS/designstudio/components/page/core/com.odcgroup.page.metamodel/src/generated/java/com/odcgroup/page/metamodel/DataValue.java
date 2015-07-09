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
 * A representation of the model object '<em><b>Data Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.DataValue#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.DataValue#getOrdinal <em>Ordinal</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.DataValue#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataValue()
 * @model
 * @generated
 */
public interface DataValue extends EObject {
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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataValue_Value()
	 * @model required="true"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.DataValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Ordinal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ordinal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ordinal</em>' attribute.
	 * @see #setOrdinal(int)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataValue_Ordinal()
	 * @model required="true"
	 * @generated
	 */
	int getOrdinal();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.DataValue#getOrdinal <em>Ordinal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ordinal</em>' attribute.
	 * @see #getOrdinal()
	 * @generated
	 */
	void setOrdinal(int value);

    /**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataValue_DisplayName()
	 * @model
	 * @generated
	 */
    String getDisplayName();

    /**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.DataValue#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
    void setDisplayName(String value);

} // DataValue