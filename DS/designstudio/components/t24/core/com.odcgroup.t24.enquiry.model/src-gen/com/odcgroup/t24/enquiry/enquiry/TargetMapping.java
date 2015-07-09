/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.TargetMapping#getFromField <em>From Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.TargetMapping#getToField <em>To Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTargetMapping()
 * @model
 * @generated
 */
public interface TargetMapping extends EObject
{
  /**
   * Returns the value of the '<em><b>From Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>From Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>From Field</em>' attribute.
   * @see #setFromField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTargetMapping_FromField()
   * @model
   * @generated
   */
  String getFromField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.TargetMapping#getFromField <em>From Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Field</em>' attribute.
   * @see #getFromField()
   * @generated
   */
  void setFromField(String value);

  /**
   * Returns the value of the '<em><b>To Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>To Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>To Field</em>' attribute.
   * @see #setToField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTargetMapping_ToField()
   * @model
   * @generated
   */
  String getToField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.TargetMapping#getToField <em>To Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>To Field</em>' attribute.
   * @see #getToField()
   * @generated
   */
  void setToField(String value);

} // TargetMapping
