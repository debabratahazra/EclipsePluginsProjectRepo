/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FileVersion#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFileVersion()
 * @model
 * @generated
 */
public interface FileVersion extends EObject
{
  /**
   * Returns the value of the '<em><b>Values</b></em>' attribute list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.FileVersionOption}.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.FileVersionOption}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Values</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Values</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.FileVersionOption
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFileVersion_Values()
   * @model unique="false"
   * @generated
   */
  EList<FileVersionOption> getValues();

} // FileVersion
