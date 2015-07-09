/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value Conversion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.ValueConversion#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.ValueConversion#getSubValue <em>Sub Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getValueConversion()
 * @model
 * @generated
 */
public interface ValueConversion extends Conversion
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getValueConversion_Value()
   * @model default="0"
   * @generated
   */
  int getValue();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.ValueConversion#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(int value);

  /**
   * Returns the value of the '<em><b>Sub Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sub Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sub Value</em>' attribute.
   * @see #setSubValue(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getValueConversion_SubValue()
   * @model
   * @generated
   */
  Integer getSubValue();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.ValueConversion#getSubValue <em>Sub Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sub Value</em>' attribute.
   * @see #getSubValue()
   * @generated
   */
  void setSubValue(Integer value);

} // ValueConversion
