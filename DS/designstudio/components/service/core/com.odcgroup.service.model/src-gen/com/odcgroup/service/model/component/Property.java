/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.Property#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Property#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Property#getReadOnly <em>Read Only</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Property#getPropertyName <em>Property Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Property#getType1 <em>Type1</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Property#getType2 <em>Type2</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Property#isArray <em>Array</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Property#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getProperty()
 * @model
 * @generated
 */
public interface Property extends EObject
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
   * @see com.odcgroup.service.model.component.ComponentPackage#getProperty_Documentation()
   * @model
   * @generated
   */
  String getDocumentation();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Property#getDocumentation <em>Documentation</em>}' attribute.
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
   * @see com.odcgroup.service.model.component.ComponentPackage#getProperty_AccessSpecifier()
   * @model
   * @generated
   */
  AccessSpecifier getAccessSpecifier();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Property#getAccessSpecifier <em>Access Specifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Access Specifier</em>' attribute.
   * @see com.odcgroup.service.model.component.AccessSpecifier
   * @see #getAccessSpecifier()
   * @generated
   */
  void setAccessSpecifier(AccessSpecifier value);

  /**
   * Returns the value of the '<em><b>Read Only</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.service.model.component.ReadWrite}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Read Only</em>' attribute.
   * @see com.odcgroup.service.model.component.ReadWrite
   * @see #setReadOnly(ReadWrite)
   * @see com.odcgroup.service.model.component.ComponentPackage#getProperty_ReadOnly()
   * @model
   * @generated
   */
  ReadWrite getReadOnly();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Property#getReadOnly <em>Read Only</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Read Only</em>' attribute.
   * @see com.odcgroup.service.model.component.ReadWrite
   * @see #getReadOnly()
   * @generated
   */
  void setReadOnly(ReadWrite value);

  /**
   * Returns the value of the '<em><b>Property Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Property Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Property Name</em>' attribute.
   * @see #setPropertyName(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getProperty_PropertyName()
   * @model
   * @generated
   */
  String getPropertyName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Property#getPropertyName <em>Property Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Property Name</em>' attribute.
   * @see #getPropertyName()
   * @generated
   */
  void setPropertyName(String value);

  /**
   * Returns the value of the '<em><b>Type1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type1</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type1</em>' attribute.
   * @see #setType1(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getProperty_Type1()
   * @model
   * @generated
   */
  String getType1();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Property#getType1 <em>Type1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type1</em>' attribute.
   * @see #getType1()
   * @generated
   */
  void setType1(String value);

  /**
   * Returns the value of the '<em><b>Type2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type2</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type2</em>' attribute.
   * @see #setType2(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getProperty_Type2()
   * @model
   * @generated
   */
  String getType2();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Property#getType2 <em>Type2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type2</em>' attribute.
   * @see #getType2()
   * @generated
   */
  void setType2(String value);

  /**
   * Returns the value of the '<em><b>Array</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Array</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Array</em>' attribute.
   * @see #setArray(boolean)
   * @see com.odcgroup.service.model.component.ComponentPackage#getProperty_Array()
   * @model
   * @generated
   */
  boolean isArray();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Property#isArray <em>Array</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Array</em>' attribute.
   * @see #isArray()
   * @generated
   */
  void setArray(boolean value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(int)
   * @see com.odcgroup.service.model.component.ComponentPackage#getProperty_Value()
   * @model
   * @generated
   */
  int getValue();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Property#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(int value);

} // Property
