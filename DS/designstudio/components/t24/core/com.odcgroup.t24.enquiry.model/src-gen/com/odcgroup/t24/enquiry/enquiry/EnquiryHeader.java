/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Header</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getColumn <em>Column</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getLine <em>Line</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiryHeader()
 * @model
 * @generated
 */
public interface EnquiryHeader extends EObject
{
  /**
   * Returns the value of the '<em><b>Label</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label</em>' containment reference.
   * @see #setLabel(Translations)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiryHeader_Label()
   * @model containment="true"
   * @generated
   */
  Translations getLabel();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getLabel <em>Label</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' containment reference.
   * @see #getLabel()
   * @generated
   */
  void setLabel(Translations value);

  /**
   * Returns the value of the '<em><b>Column</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Column</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Column</em>' attribute.
   * @see #setColumn(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiryHeader_Column()
   * @model default="0"
   * @generated
   */
  int getColumn();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getColumn <em>Column</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Column</em>' attribute.
   * @see #getColumn()
   * @generated
   */
  void setColumn(int value);

  /**
   * Returns the value of the '<em><b>Line</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Line</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Line</em>' attribute.
   * @see #setLine(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiryHeader_Line()
   * @model default="0"
   * @generated
   */
  int getLine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getLine <em>Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Line</em>' attribute.
   * @see #getLine()
   * @generated
   */
  void setLine(int value);

} // EnquiryHeader
