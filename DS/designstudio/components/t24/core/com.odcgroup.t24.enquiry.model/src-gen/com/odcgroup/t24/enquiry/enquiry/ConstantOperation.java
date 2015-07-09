/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constant Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.ConstantOperation#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getConstantOperation()
 * @model
 * @generated
 */
public interface ConstantOperation extends Operation
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getConstantOperation_Value()
   * @model
   * @generated
   */
  String getValue();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.ConstantOperation#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(String value);

} // ConstantOperation
