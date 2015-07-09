/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Break Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.BreakCondition#getBreak <em>Break</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.BreakCondition#getField <em>Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getBreakCondition()
 * @model
 * @generated
 */
public interface BreakCondition extends EObject
{
  /**
   * Returns the value of the '<em><b>Break</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.BreakKind}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Break</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Break</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakKind
   * @see #setBreak(BreakKind)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getBreakCondition_Break()
   * @model
   * @generated
   */
  BreakKind getBreak();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.BreakCondition#getBreak <em>Break</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Break</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakKind
   * @see #getBreak()
   * @generated
   */
  void setBreak(BreakKind value);

  /**
   * Returns the value of the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field</em>' attribute.
   * @see #setField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getBreakCondition_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.BreakCondition#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

} // BreakCondition
