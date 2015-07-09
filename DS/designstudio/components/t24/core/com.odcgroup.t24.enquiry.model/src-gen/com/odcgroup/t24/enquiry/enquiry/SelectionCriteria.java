/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Selection Criteria</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getOperand <em>Operand</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelectionCriteria()
 * @model
 * @generated
 */
public interface SelectionCriteria extends EObject
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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelectionCriteria_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

  /**
   * Returns the value of the '<em><b>Operand</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.CriteriaOperator}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operand</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operand</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.CriteriaOperator
   * @see #setOperand(CriteriaOperator)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelectionCriteria_Operand()
   * @model
   * @generated
   */
  CriteriaOperator getOperand();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getOperand <em>Operand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operand</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.CriteriaOperator
   * @see #getOperand()
   * @generated
   */
  void setOperand(CriteriaOperator value);

  /**
   * Returns the value of the '<em><b>Values</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Values</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Values</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelectionCriteria_Values()
   * @model unique="false"
   * @generated
   */
  EList<String> getValues();

} // SelectionCriteria
