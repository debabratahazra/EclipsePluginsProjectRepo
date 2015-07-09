/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scale</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Scale#getX <em>X</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Scale#getY <em>Y</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getScale()
 * @model
 * @generated
 */
public interface Scale extends EObject
{
  /**
   * Returns the value of the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>X</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>X</em>' attribute.
   * @see #setX(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getScale_X()
   * @model
   * @generated
   */
  Integer getX();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Scale#getX <em>X</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>X</em>' attribute.
   * @see #getX()
   * @generated
   */
  void setX(Integer value);

  /**
   * Returns the value of the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Y</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Y</em>' attribute.
   * @see #setY(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getScale_Y()
   * @model
   * @generated
   */
  Integer getY();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Scale#getY <em>Y</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Y</em>' attribute.
   * @see #getY()
   * @generated
   */
  void setY(Integer value);

} // Scale
