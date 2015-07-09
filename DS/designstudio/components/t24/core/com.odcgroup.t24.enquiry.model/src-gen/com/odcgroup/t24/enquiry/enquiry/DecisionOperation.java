/**
 */
package com.odcgroup.t24.enquiry.enquiry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Decision Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getLeftField <em>Left Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getOperand <em>Operand</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getRightField <em>Right Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getFirstField <em>First Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getSecondField <em>Second Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDecisionOperation()
 * @model
 * @generated
 */
public interface DecisionOperation extends Operation
{
  /**
   * Returns the value of the '<em><b>Left Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Left Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left Field</em>' attribute.
   * @see #setLeftField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDecisionOperation_LeftField()
   * @model
   * @generated
   */
  String getLeftField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getLeftField <em>Left Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left Field</em>' attribute.
   * @see #getLeftField()
   * @generated
   */
  void setLeftField(String value);

  /**
   * Returns the value of the '<em><b>Operand</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.DecisionOperand}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operand</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operand</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperand
   * @see #setOperand(DecisionOperand)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDecisionOperation_Operand()
   * @model
   * @generated
   */
  DecisionOperand getOperand();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getOperand <em>Operand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operand</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperand
   * @see #getOperand()
   * @generated
   */
  void setOperand(DecisionOperand value);

  /**
   * Returns the value of the '<em><b>Right Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right Field</em>' attribute.
   * @see #setRightField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDecisionOperation_RightField()
   * @model
   * @generated
   */
  String getRightField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getRightField <em>Right Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right Field</em>' attribute.
   * @see #getRightField()
   * @generated
   */
  void setRightField(String value);

  /**
   * Returns the value of the '<em><b>First Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>First Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>First Field</em>' attribute.
   * @see #setFirstField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDecisionOperation_FirstField()
   * @model
   * @generated
   */
  String getFirstField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getFirstField <em>First Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>First Field</em>' attribute.
   * @see #getFirstField()
   * @generated
   */
  void setFirstField(String value);

  /**
   * Returns the value of the '<em><b>Second Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Second Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Second Field</em>' attribute.
   * @see #setSecondField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDecisionOperation_SecondField()
   * @model
   * @generated
   */
  String getSecondField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getSecondField <em>Second Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Second Field</em>' attribute.
   * @see #getSecondField()
   * @generated
   */
  void setSecondField(String value);

} // DecisionOperation
