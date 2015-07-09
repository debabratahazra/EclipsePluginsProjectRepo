/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Conversion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.ReplaceConversion#getOldData <em>Old Data</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.ReplaceConversion#getNewData <em>New Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getReplaceConversion()
 * @model
 * @generated
 */
public interface ReplaceConversion extends Conversion
{
  /**
   * Returns the value of the '<em><b>Old Data</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Old Data</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Old Data</em>' attribute.
   * @see #setOldData(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getReplaceConversion_OldData()
   * @model
   * @generated
   */
  String getOldData();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.ReplaceConversion#getOldData <em>Old Data</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Old Data</em>' attribute.
   * @see #getOldData()
   * @generated
   */
  void setOldData(String value);

  /**
   * Returns the value of the '<em><b>New Data</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>New Data</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>New Data</em>' attribute.
   * @see #setNewData(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getReplaceConversion_NewData()
   * @model
   * @generated
   */
  String getNewData();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.ReplaceConversion#getNewData <em>New Data</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>New Data</em>' attribute.
   * @see #getNewData()
   * @generated
   */
  void setNewData(String value);

} // ReplaceConversion
