/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.MethodAnnotation#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getMethodAnnotation()
 * @model
 * @generated
 */
public interface MethodAnnotation extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.service.model.component.T24MethodStereotype}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see com.odcgroup.service.model.component.T24MethodStereotype
   * @see #setName(T24MethodStereotype)
   * @see com.odcgroup.service.model.component.ComponentPackage#getMethodAnnotation_Name()
   * @model
   * @generated
   */
  T24MethodStereotype getName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.MethodAnnotation#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see com.odcgroup.service.model.component.T24MethodStereotype
   * @see #getName()
   * @generated
   */
  void setName(T24MethodStereotype value);

} // MethodAnnotation
