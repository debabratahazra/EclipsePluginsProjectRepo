/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.Constant#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Constant#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Constant#getConstantName <em>Constant Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Constant#getJBCName <em>JBC Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Constant#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getConstant()
 * @model
 * @generated
 */
public interface Constant extends EObject
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
   * @see com.odcgroup.service.model.component.ComponentPackage#getConstant_Documentation()
   * @model
   * @generated
   */
  String getDocumentation();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Constant#getDocumentation <em>Documentation</em>}' attribute.
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
   * @see com.odcgroup.service.model.component.ComponentPackage#getConstant_AccessSpecifier()
   * @model
   * @generated
   */
  AccessSpecifier getAccessSpecifier();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Constant#getAccessSpecifier <em>Access Specifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Access Specifier</em>' attribute.
   * @see com.odcgroup.service.model.component.AccessSpecifier
   * @see #getAccessSpecifier()
   * @generated
   */
  void setAccessSpecifier(AccessSpecifier value);

  /**
   * Returns the value of the '<em><b>Constant Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constant Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constant Name</em>' attribute.
   * @see #setConstantName(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getConstant_ConstantName()
   * @model
   * @generated
   */
  String getConstantName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Constant#getConstantName <em>Constant Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constant Name</em>' attribute.
   * @see #getConstantName()
   * @generated
   */
  void setConstantName(String value);

  /**
   * Returns the value of the '<em><b>JBC Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>JBC Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>JBC Name</em>' attribute.
   * @see #setJBCName(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getConstant_JBCName()
   * @model
   * @generated
   */
  String getJBCName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Constant#getJBCName <em>JBC Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>JBC Name</em>' attribute.
   * @see #getJBCName()
   * @generated
   */
  void setJBCName(String value);

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
   * @see com.odcgroup.service.model.component.ComponentPackage#getConstant_Value()
   * @model
   * @generated
   */
  int getValue();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Constant#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(int value);

} // Constant
