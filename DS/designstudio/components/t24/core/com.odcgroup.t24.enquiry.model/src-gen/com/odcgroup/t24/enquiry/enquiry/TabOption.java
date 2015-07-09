/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tab Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.TabOption#getTabName <em>Tab Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.TabOption#getReference <em>Reference</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.TabOption#getFieldParameter <em>Field Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTabOption()
 * @model
 * @generated
 */
public interface TabOption extends DrillDownOption
{
  /**
   * Returns the value of the '<em><b>Tab Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tab Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tab Name</em>' attribute.
   * @see #setTabName(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTabOption_TabName()
   * @model
   * @generated
   */
  String getTabName();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.TabOption#getTabName <em>Tab Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tab Name</em>' attribute.
   * @see #getTabName()
   * @generated
   */
  void setTabName(String value);

  /**
   * Returns the value of the '<em><b>Reference</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Reference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Reference</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Reference</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTabOption_Reference()
   * @model containment="true"
   * @generated
   */
  EList<Reference> getReference();

  /**
   * Returns the value of the '<em><b>Field Parameter</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Parameter}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field Parameter</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field Parameter</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTabOption_FieldParameter()
   * @model containment="true"
   * @generated
   */
  EList<Parameter> getFieldParameter();

} // TabOption
