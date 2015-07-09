/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Selection Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.SelectionExpression#getSelection <em>Selection</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelectionExpression()
 * @model
 * @generated
 */
public interface SelectionExpression extends EObject
{
  /**
   * Returns the value of the '<em><b>Selection</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Selection}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Selection</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Selection</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelectionExpression_Selection()
   * @model containment="true"
   * @generated
   */
  EList<Selection> getSelection();

} // SelectionExpression
