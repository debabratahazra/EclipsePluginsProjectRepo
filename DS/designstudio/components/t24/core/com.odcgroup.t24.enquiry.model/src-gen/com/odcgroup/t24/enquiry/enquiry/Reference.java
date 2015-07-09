/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Reference#getFile <em>File</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Reference#getField <em>Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getReference()
 * @model
 * @generated
 */
public interface Reference extends EObject
{
  /**
   * Returns the value of the '<em><b>File</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>File</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File</em>' attribute.
   * @see #setFile(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getReference_File()
   * @model
   * @generated
   */
  String getFile();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Reference#getFile <em>File</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File</em>' attribute.
   * @see #getFile()
   * @generated
   */
  void setFile(String value);

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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getReference_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Reference#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

} // Reference
