/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getWidth <em>Width</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getHeight <em>Height</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getOrientation <em>Orientation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDimension()
 * @model
 * @generated
 */
public interface Dimension extends EObject
{
  /**
   * Returns the value of the '<em><b>Width</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Width</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Width</em>' attribute.
   * @see #setWidth(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDimension_Width()
   * @model default="0"
   * @generated
   */
  int getWidth();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getWidth <em>Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Width</em>' attribute.
   * @see #getWidth()
   * @generated
   */
  void setWidth(int value);

  /**
   * Returns the value of the '<em><b>Height</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Height</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Height</em>' attribute.
   * @see #setHeight(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDimension_Height()
   * @model default="0"
   * @generated
   */
  int getHeight();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getHeight <em>Height</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Height</em>' attribute.
   * @see #getHeight()
   * @generated
   */
  void setHeight(int value);

  /**
   * Returns the value of the '<em><b>Orientation</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.Orientation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Orientation</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Orientation</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.Orientation
   * @see #setOrientation(Orientation)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDimension_Orientation()
   * @model
   * @generated
   */
  Orientation getOrientation();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getOrientation <em>Orientation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Orientation</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.Orientation
   * @see #getOrientation()
   * @generated
   */
  void setOrientation(Orientation value);

} // Dimension
