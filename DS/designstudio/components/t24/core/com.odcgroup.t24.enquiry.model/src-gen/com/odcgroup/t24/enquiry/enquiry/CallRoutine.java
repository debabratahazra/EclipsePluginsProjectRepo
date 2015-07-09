/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Call Routine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.CallRoutine#getRoutine <em>Routine</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCallRoutine()
 * @model
 * @generated
 */
public interface CallRoutine extends Conversion
{
  /**
   * Returns the value of the '<em><b>Routine</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Routine</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Routine</em>' containment reference.
   * @see #setRoutine(Routine)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCallRoutine_Routine()
   * @model containment="true"
   * @generated
   */
  Routine getRoutine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.CallRoutine#getRoutine <em>Routine</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Routine</em>' containment reference.
   * @see #getRoutine()
   * @generated
   */
  void setRoutine(Routine value);

} // CallRoutine
