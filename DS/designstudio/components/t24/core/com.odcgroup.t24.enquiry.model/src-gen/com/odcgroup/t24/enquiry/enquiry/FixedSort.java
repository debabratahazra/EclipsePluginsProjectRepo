/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fixed Sort</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FixedSort#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FixedSort#getOrder <em>Order</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFixedSort()
 * @model
 * @generated
 */
public interface FixedSort extends EObject
{
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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFixedSort_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.FixedSort#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

  /**
   * Returns the value of the '<em><b>Order</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.SortOrder}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Order</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Order</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.SortOrder
   * @see #setOrder(SortOrder)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFixedSort_Order()
   * @model
   * @generated
   */
  SortOrder getOrder();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.FixedSort#getOrder <em>Order</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Order</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.SortOrder
   * @see #getOrder()
   * @generated
   */
  void setOrder(SortOrder value);

} // FixedSort
