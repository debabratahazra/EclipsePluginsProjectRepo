/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field Number Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation#getNumber <em>Number</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldNumberOperation()
 * @model
 * @generated
 */
public interface FieldNumberOperation extends FieldOperation
{
  /**
   * Returns the value of the '<em><b>Number</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Number</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Number</em>' attribute.
   * @see #setNumber(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldNumberOperation_Number()
   * @model default="0"
   * @generated
   */
  int getNumber();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation#getNumber <em>Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number</em>' attribute.
   * @see #getNumber()
   * @generated
   */
  void setNumber(int value);

} // FieldNumberOperation
