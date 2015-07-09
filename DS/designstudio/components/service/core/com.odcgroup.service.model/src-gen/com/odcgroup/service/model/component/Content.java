/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.Content#getInterface <em>Interface</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Content#getMethod <em>Method</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Content#getProperty <em>Property</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Content#getConstant <em>Constant</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Content#getTable <em>Table</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getContent()
 * @model
 * @generated
 */
public interface Content extends EObject
{
  /**
   * Returns the value of the '<em><b>Interface</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Interface</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interface</em>' containment reference.
   * @see #setInterface(Interface)
   * @see com.odcgroup.service.model.component.ComponentPackage#getContent_Interface()
   * @model containment="true"
   * @generated
   */
  Interface getInterface();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Content#getInterface <em>Interface</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interface</em>' containment reference.
   * @see #getInterface()
   * @generated
   */
  void setInterface(Interface value);

  /**
   * Returns the value of the '<em><b>Method</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.service.model.component.Method}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Method</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Method</em>' containment reference list.
   * @see com.odcgroup.service.model.component.ComponentPackage#getContent_Method()
   * @model containment="true"
   * @generated
   */
  EList<Method> getMethod();

  /**
   * Returns the value of the '<em><b>Property</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.service.model.component.Property}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Property</em>' containment reference list.
   * @see com.odcgroup.service.model.component.ComponentPackage#getContent_Property()
   * @model containment="true"
   * @generated
   */
  EList<Property> getProperty();

  /**
   * Returns the value of the '<em><b>Constant</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.service.model.component.Constant}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constant</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constant</em>' containment reference list.
   * @see com.odcgroup.service.model.component.ComponentPackage#getContent_Constant()
   * @model containment="true"
   * @generated
   */
  EList<Constant> getConstant();

  /**
   * Returns the value of the '<em><b>Table</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.service.model.component.Table}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Table</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Table</em>' containment reference list.
   * @see com.odcgroup.service.model.component.ComponentPackage#getContent_Table()
   * @model containment="true"
   * @generated
   */
  EList<Table> getTable();

} // Content
