/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get From Conversion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#getApplication <em>Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#isLanguage <em>Language</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGetFromConversion()
 * @model
 * @generated
 */
public interface GetFromConversion extends Conversion
{
  /**
   * Returns the value of the '<em><b>Application</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Application</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Application</em>' attribute.
   * @see #setApplication(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGetFromConversion_Application()
   * @model
   * @generated
   */
  String getApplication();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#getApplication <em>Application</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Application</em>' attribute.
   * @see #getApplication()
   * @generated
   */
  void setApplication(String value);

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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGetFromConversion_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

  /**
   * Returns the value of the '<em><b>Language</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Language</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Language</em>' attribute.
   * @see #setLanguage(boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGetFromConversion_Language()
   * @model
   * @generated
   */
  boolean isLanguage();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#isLanguage <em>Language</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Language</em>' attribute.
   * @see #isLanguage()
   * @generated
   */
  void setLanguage(boolean value);

} // GetFromConversion
