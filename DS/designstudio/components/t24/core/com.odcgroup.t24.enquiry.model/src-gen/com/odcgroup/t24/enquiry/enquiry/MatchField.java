/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Match Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.MatchField#getPattern <em>Pattern</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.MatchField#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getMatchField()
 * @model
 * @generated
 */
public interface MatchField extends Conversion
{
  /**
   * Returns the value of the '<em><b>Pattern</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pattern</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pattern</em>' attribute.
   * @see #setPattern(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getMatchField_Pattern()
   * @model
   * @generated
   */
  String getPattern();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.MatchField#getPattern <em>Pattern</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pattern</em>' attribute.
   * @see #getPattern()
   * @generated
   */
  void setPattern(String value);

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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getMatchField_Value()
   * @model
   * @generated
   */
  String getValue();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.MatchField#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(String value);

} // MatchField
