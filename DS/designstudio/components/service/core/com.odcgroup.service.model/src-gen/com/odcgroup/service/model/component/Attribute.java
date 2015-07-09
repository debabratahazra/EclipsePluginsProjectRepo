/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.Attribute#getAttrName <em>Attr Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Attribute#getJBCName <em>JBC Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Attribute#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject
{
  /**
   * Returns the value of the '<em><b>Attr Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attr Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attr Name</em>' attribute.
   * @see #setAttrName(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getAttribute_AttrName()
   * @model
   * @generated
   */
  String getAttrName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Attribute#getAttrName <em>Attr Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Attr Name</em>' attribute.
   * @see #getAttrName()
   * @generated
   */
  void setAttrName(String value);

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
   * @see com.odcgroup.service.model.component.ComponentPackage#getAttribute_JBCName()
   * @model
   * @generated
   */
  String getJBCName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Attribute#getJBCName <em>JBC Name</em>}' attribute.
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
   * @see com.odcgroup.service.model.component.ComponentPackage#getAttribute_Value()
   * @model
   * @generated
   */
  int getValue();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Attribute#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(int value);

} // Attribute
