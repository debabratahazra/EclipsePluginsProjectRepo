/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field Position</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getPageThrow <em>Page Throw</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getColumn <em>Column</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getRelative <em>Relative</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getLine <em>Line</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getMultiLine <em>Multi Line</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldPosition()
 * @model
 * @generated
 */
public interface FieldPosition extends EObject
{
  /**
   * Returns the value of the '<em><b>Page Throw</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Page Throw</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Page Throw</em>' attribute.
   * @see #setPageThrow(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldPosition_PageThrow()
   * @model
   * @generated
   */
  Boolean getPageThrow();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getPageThrow <em>Page Throw</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Page Throw</em>' attribute.
   * @see #getPageThrow()
   * @generated
   */
  void setPageThrow(Boolean value);

  /**
   * Returns the value of the '<em><b>Column</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Column</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Column</em>' attribute.
   * @see #setColumn(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldPosition_Column()
   * @model
   * @generated
   */
  Integer getColumn();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getColumn <em>Column</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Column</em>' attribute.
   * @see #getColumn()
   * @generated
   */
  void setColumn(Integer value);

  /**
   * Returns the value of the '<em><b>Relative</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Relative</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relative</em>' attribute.
   * @see #setRelative(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldPosition_Relative()
   * @model
   * @generated
   */
  String getRelative();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getRelative <em>Relative</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Relative</em>' attribute.
   * @see #getRelative()
   * @generated
   */
  void setRelative(String value);

  /**
   * Returns the value of the '<em><b>Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Line</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Line</em>' attribute.
   * @see #setLine(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldPosition_Line()
   * @model
   * @generated
   */
  Integer getLine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getLine <em>Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Line</em>' attribute.
   * @see #getLine()
   * @generated
   */
  void setLine(Integer value);

  /**
   * Returns the value of the '<em><b>Multi Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Multi Line</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Multi Line</em>' attribute.
   * @see #setMultiLine(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldPosition_MultiLine()
   * @model
   * @generated
   */
  Boolean getMultiLine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getMultiLine <em>Multi Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Multi Line</em>' attribute.
   * @see #getMultiLine()
   * @generated
   */
  void setMultiLine(Boolean value);

} // FieldPosition
