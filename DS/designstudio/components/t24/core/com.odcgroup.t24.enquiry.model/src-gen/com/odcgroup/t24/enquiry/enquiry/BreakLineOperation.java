/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Break Line Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.BreakLineOperation#getLine <em>Line</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getBreakLineOperation()
 * @model
 * @generated
 */
public interface BreakLineOperation extends BreakOperation
{
  /**
   * Returns the value of the '<em><b>Line</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Line</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Line</em>' attribute.
   * @see #setLine(int)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getBreakLineOperation_Line()
   * @model default="0"
   * @generated
   */
  int getLine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.BreakLineOperation#getLine <em>Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Line</em>' attribute.
   * @see #getLine()
   * @generated
   */
  void setLine(int value);

} // BreakLineOperation
