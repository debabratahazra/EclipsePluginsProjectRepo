/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Format#getFormat <em>Format</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Format#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Format#getPattern <em>Pattern</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFormat()
 * @model
 * @generated
 */
public interface Format extends EObject
{
  /**
   * Returns the value of the '<em><b>Format</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.FieldFormat}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Format</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Format</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldFormat
   * @see #setFormat(FieldFormat)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFormat_Format()
   * @model
   * @generated
   */
  FieldFormat getFormat();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Format#getFormat <em>Format</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Format</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldFormat
   * @see #getFormat()
   * @generated
   */
  void setFormat(FieldFormat value);

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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFormat_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Format#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

  /**
   * Returns the value of the '<em><b>Pattern</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.CurrencyPattern}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pattern</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pattern</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.CurrencyPattern
   * @see #setPattern(CurrencyPattern)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFormat_Pattern()
   * @model
   * @generated
   */
  CurrencyPattern getPattern();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Format#getPattern <em>Pattern</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pattern</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.CurrencyPattern
   * @see #getPattern()
   * @generated
   */
  void setPattern(CurrencyPattern value);

} // Format
