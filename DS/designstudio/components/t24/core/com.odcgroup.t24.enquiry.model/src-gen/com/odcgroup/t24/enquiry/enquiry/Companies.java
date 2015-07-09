/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Companies</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Companies#getAll <em>All</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Companies#getCode <em>Code</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCompanies()
 * @model
 * @generated
 */
public interface Companies extends EObject
{
  /**
   * Returns the value of the '<em><b>All</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>All</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>All</em>' attribute.
   * @see #setAll(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCompanies_All()
   * @model
   * @generated
   */
  Boolean getAll();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Companies#getAll <em>All</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>All</em>' attribute.
   * @see #getAll()
   * @generated
   */
  void setAll(Boolean value);

  /**
   * Returns the value of the '<em><b>Code</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Code</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Code</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCompanies_Code()
   * @model unique="false"
   * @generated
   */
  EList<String> getCode();

} // Companies
