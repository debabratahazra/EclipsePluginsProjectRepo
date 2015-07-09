/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.DecisionOperand;
import com.odcgroup.t24.enquiry.enquiry.DecisionOperation;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Decision Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl#getLeftField <em>Left Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl#getOperand <em>Operand</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl#getRightField <em>Right Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl#getFirstField <em>First Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl#getSecondField <em>Second Field</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DecisionOperationImpl extends OperationImpl implements DecisionOperation
{
  /**
   * The default value of the '{@link #getLeftField() <em>Left Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLeftField()
   * @generated
   * @ordered
   */
  protected static final String LEFT_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLeftField() <em>Left Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLeftField()
   * @generated
   * @ordered
   */
  protected String leftField = LEFT_FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getOperand() <em>Operand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperand()
   * @generated
   * @ordered
   */
  protected static final DecisionOperand OPERAND_EDEFAULT = DecisionOperand.NONE;

  /**
   * The cached value of the '{@link #getOperand() <em>Operand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperand()
   * @generated
   * @ordered
   */
  protected DecisionOperand operand = OPERAND_EDEFAULT;

  /**
   * The default value of the '{@link #getRightField() <em>Right Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRightField()
   * @generated
   * @ordered
   */
  protected static final String RIGHT_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRightField() <em>Right Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRightField()
   * @generated
   * @ordered
   */
  protected String rightField = RIGHT_FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getFirstField() <em>First Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstField()
   * @generated
   * @ordered
   */
  protected static final String FIRST_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFirstField() <em>First Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstField()
   * @generated
   * @ordered
   */
  protected String firstField = FIRST_FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getSecondField() <em>Second Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSecondField()
   * @generated
   * @ordered
   */
  protected static final String SECOND_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSecondField() <em>Second Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSecondField()
   * @generated
   * @ordered
   */
  protected String secondField = SECOND_FIELD_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DecisionOperationImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return EnquiryPackage.Literals.DECISION_OPERATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLeftField()
  {
    return leftField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLeftField(String newLeftField)
  {
    String oldLeftField = leftField;
    leftField = newLeftField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DECISION_OPERATION__LEFT_FIELD, oldLeftField, leftField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DecisionOperand getOperand()
  {
    return operand;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOperand(DecisionOperand newOperand)
  {
    DecisionOperand oldOperand = operand;
    operand = newOperand == null ? OPERAND_EDEFAULT : newOperand;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DECISION_OPERATION__OPERAND, oldOperand, operand));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRightField()
  {
    return rightField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRightField(String newRightField)
  {
    String oldRightField = rightField;
    rightField = newRightField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DECISION_OPERATION__RIGHT_FIELD, oldRightField, rightField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFirstField()
  {
    return firstField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFirstField(String newFirstField)
  {
    String oldFirstField = firstField;
    firstField = newFirstField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DECISION_OPERATION__FIRST_FIELD, oldFirstField, firstField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSecondField()
  {
    return secondField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSecondField(String newSecondField)
  {
    String oldSecondField = secondField;
    secondField = newSecondField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DECISION_OPERATION__SECOND_FIELD, oldSecondField, secondField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case EnquiryPackage.DECISION_OPERATION__LEFT_FIELD:
        return getLeftField();
      case EnquiryPackage.DECISION_OPERATION__OPERAND:
        return getOperand();
      case EnquiryPackage.DECISION_OPERATION__RIGHT_FIELD:
        return getRightField();
      case EnquiryPackage.DECISION_OPERATION__FIRST_FIELD:
        return getFirstField();
      case EnquiryPackage.DECISION_OPERATION__SECOND_FIELD:
        return getSecondField();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case EnquiryPackage.DECISION_OPERATION__LEFT_FIELD:
        setLeftField((String)newValue);
        return;
      case EnquiryPackage.DECISION_OPERATION__OPERAND:
        setOperand((DecisionOperand)newValue);
        return;
      case EnquiryPackage.DECISION_OPERATION__RIGHT_FIELD:
        setRightField((String)newValue);
        return;
      case EnquiryPackage.DECISION_OPERATION__FIRST_FIELD:
        setFirstField((String)newValue);
        return;
      case EnquiryPackage.DECISION_OPERATION__SECOND_FIELD:
        setSecondField((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case EnquiryPackage.DECISION_OPERATION__LEFT_FIELD:
        setLeftField(LEFT_FIELD_EDEFAULT);
        return;
      case EnquiryPackage.DECISION_OPERATION__OPERAND:
        setOperand(OPERAND_EDEFAULT);
        return;
      case EnquiryPackage.DECISION_OPERATION__RIGHT_FIELD:
        setRightField(RIGHT_FIELD_EDEFAULT);
        return;
      case EnquiryPackage.DECISION_OPERATION__FIRST_FIELD:
        setFirstField(FIRST_FIELD_EDEFAULT);
        return;
      case EnquiryPackage.DECISION_OPERATION__SECOND_FIELD:
        setSecondField(SECOND_FIELD_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case EnquiryPackage.DECISION_OPERATION__LEFT_FIELD:
        return LEFT_FIELD_EDEFAULT == null ? leftField != null : !LEFT_FIELD_EDEFAULT.equals(leftField);
      case EnquiryPackage.DECISION_OPERATION__OPERAND:
        return operand != OPERAND_EDEFAULT;
      case EnquiryPackage.DECISION_OPERATION__RIGHT_FIELD:
        return RIGHT_FIELD_EDEFAULT == null ? rightField != null : !RIGHT_FIELD_EDEFAULT.equals(rightField);
      case EnquiryPackage.DECISION_OPERATION__FIRST_FIELD:
        return FIRST_FIELD_EDEFAULT == null ? firstField != null : !FIRST_FIELD_EDEFAULT.equals(firstField);
      case EnquiryPackage.DECISION_OPERATION__SECOND_FIELD:
        return SECOND_FIELD_EDEFAULT == null ? secondField != null : !SECOND_FIELD_EDEFAULT.equals(secondField);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (leftField: ");
    result.append(leftField);
    result.append(", operand: ");
    result.append(operand);
    result.append(", rightField: ");
    result.append(rightField);
    result.append(", firstField: ");
    result.append(firstField);
    result.append(", secondField: ");
    result.append(secondField);
    result.append(')');
    return result.toString();
  }

} //DecisionOperationImpl
