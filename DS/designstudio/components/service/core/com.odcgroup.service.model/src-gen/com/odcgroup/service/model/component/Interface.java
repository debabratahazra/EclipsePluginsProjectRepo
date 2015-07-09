/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.Interface#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Interface#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Interface#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Interface#getArguments <em>Arguments</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getInterface()
 * @model
 * @generated
 */
public interface Interface extends EObject
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
   * @see com.odcgroup.service.model.component.ComponentPackage#getInterface_Documentation()
   * @model
   * @generated
   */
  String getDocumentation();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Interface#getDocumentation <em>Documentation</em>}' attribute.
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
   * @see com.odcgroup.service.model.component.ComponentPackage#getInterface_AccessSpecifier()
   * @model
   * @generated
   */
  AccessSpecifier getAccessSpecifier();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Interface#getAccessSpecifier <em>Access Specifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Access Specifier</em>' attribute.
   * @see com.odcgroup.service.model.component.AccessSpecifier
   * @see #getAccessSpecifier()
   * @generated
   */
  void setAccessSpecifier(AccessSpecifier value);

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
   * @see com.odcgroup.service.model.component.ComponentPackage#getInterface_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Interface#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.service.model.component.Argument}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arguments</em>' containment reference list.
   * @see com.odcgroup.service.model.component.ComponentPackage#getInterface_Arguments()
   * @model containment="true"
   * @generated
   */
  EList<Argument> getArguments();

} // Interface
