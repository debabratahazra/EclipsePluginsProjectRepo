/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.Table#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Table#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Table#getTableName <em>Table Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Table#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Table#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getTable()
 * @model
 * @generated
 */
public interface Table extends EObject
{
  /**
   * Returns the value of the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Documentation</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Documentation</em>' attribute.
   * @see #setDocumentation(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getTable_Documentation()
   * @model
   * @generated
   */
  String getDocumentation();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Table#getDocumentation <em>Documentation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Documentation</em>' attribute.
   * @see #getDocumentation()
   * @generated
   */
  void setDocumentation(String value);

  /**
   * Returns the value of the '<em><b>Access Specifier</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.service.model.component.AccessSpecifier}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Access Specifier</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Access Specifier</em>' attribute.
   * @see com.odcgroup.service.model.component.AccessSpecifier
   * @see #setAccessSpecifier(AccessSpecifier)
   * @see com.odcgroup.service.model.component.ComponentPackage#getTable_AccessSpecifier()
   * @model
   * @generated
   */
  AccessSpecifier getAccessSpecifier();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Table#getAccessSpecifier <em>Access Specifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Access Specifier</em>' attribute.
   * @see com.odcgroup.service.model.component.AccessSpecifier
   * @see #getAccessSpecifier()
   * @generated
   */
  void setAccessSpecifier(AccessSpecifier value);

  /**
   * Returns the value of the '<em><b>Table Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Table Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Table Name</em>' attribute.
   * @see #setTableName(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getTable_TableName()
   * @model
   * @generated
   */
  String getTableName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Table#getTableName <em>Table Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Table Name</em>' attribute.
   * @see #getTableName()
   * @generated
   */
  void setTableName(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getTable_Type()
   * @model
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Table#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Returns the value of the '<em><b>Attribute</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.service.model.component.Attribute}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attribute</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attribute</em>' containment reference list.
   * @see com.odcgroup.service.model.component.ComponentPackage#getTable_Attribute()
   * @model containment="true"
   * @generated
   */
  EList<Attribute> getAttribute();

} // Table
