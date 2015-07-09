/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Legend</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Legend#getX <em>X</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Legend#getY <em>Y</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getLegend()
 * @model
 * @generated
 */
public interface Legend extends EObject
{
  /**
   * Returns the value of the '<em><b>X</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>X</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>X</em>' attribute.
   * @see #setX(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getLegend_X()
   * @model default="0"
   * @generated
   */
  int getX();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Legend#getX <em>X</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>X</em>' attribute.
   * @see #getX()
   * @generated
   */
  void setX(int value);

  /**
   * Returns the value of the '<em><b>Y</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Y</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Y</em>' attribute.
   * @see #setY(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getLegend_Y()
   * @model default="0"
   * @generated
   */
  int getY();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Legend#getY <em>Y</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Y</em>' attribute.
   * @see #getY()
   * @generated
   */
  void setY(int value);

} // Legend
