/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extract Conversion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getFrom <em>From</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getTo <em>To</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getDelimiter <em>Delimiter</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getExtractConversion()
 * @model
 * @generated
 */
public interface ExtractConversion extends Conversion
{
  /**
   * Returns the value of the '<em><b>From</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>From</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>From</em>' attribute.
   * @see #setFrom(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getExtractConversion_From()
   * @model default="0"
   * @generated
   */
  int getFrom();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getFrom <em>From</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From</em>' attribute.
   * @see #getFrom()
   * @generated
   */
  void setFrom(int value);

  /**
   * Returns the value of the '<em><b>To</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>To</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>To</em>' attribute.
   * @see #setTo(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getExtractConversion_To()
   * @model default="0"
   * @generated
   */
  int getTo();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getTo <em>To</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>To</em>' attribute.
   * @see #getTo()
   * @generated
   */
  void setTo(int value);

  /**
   * Returns the value of the '<em><b>Delimiter</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Delimiter</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Delimiter</em>' attribute.
   * @see #setDelimiter(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getExtractConversion_Delimiter()
   * @model
   * @generated
   */
  String getDelimiter();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getDelimiter <em>Delimiter</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Delimiter</em>' attribute.
   * @see #getDelimiter()
   * @generated
   */
  void setDelimiter(String value);

} // ExtractConversion
