/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Security</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Security#getApplication <em>Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Security#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Security#isAbort <em>Abort</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSecurity()
 * @model
 * @generated
 */
public interface Security extends EObject
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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSecurity_Application()
   * @model
   * @generated
   */
  String getApplication();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Security#getApplication <em>Application</em>}' attribute.
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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSecurity_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Security#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

  /**
   * Returns the value of the '<em><b>Abort</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Abort</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Abort</em>' attribute.
   * @see #setAbort(boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSecurity_Abort()
   * @model
   * @generated
   */
  boolean isAbort();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Security#isAbort <em>Abort</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Abort</em>' attribute.
   * @see #isAbort()
   * @generated
   */
  void setAbort(boolean value);

} // Security
