/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.BasicConversion;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Basic Conversion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.BasicConversionImpl#getInstruction <em>Instruction</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BasicConversionImpl extends ConversionImpl implements BasicConversion
{
  /**
   * The default value of the '{@link #getInstruction() <em>Instruction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstruction()
   * @generated
   * @ordered
   */
  protected static final String INSTRUCTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInstruction() <em>Instruction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstruction()
   * @generated
   * @ordered
   */
  protected String instruction = INSTRUCTION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BasicConversionImpl()
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
    return EnquiryPackage.Literals.BASIC_CONVERSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getInstruction()
  {
    return instruction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInstruction(String newInstruction)
  {
    String oldInstruction = instruction;
    instruction = newInstruction;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.BASIC_CONVERSION__INSTRUCTION, oldInstruction, instruction));
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
      case EnquiryPackage.BASIC_CONVERSION__INSTRUCTION:
        return getInstruction();
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
      case EnquiryPackage.BASIC_CONVERSION__INSTRUCTION:
        setInstruction((String)newValue);
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
      case EnquiryPackage.BASIC_CONVERSION__INSTRUCTION:
        setInstruction(INSTRUCTION_EDEFAULT);
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
      case EnquiryPackage.BASIC_CONVERSION__INSTRUCTION:
        return INSTRUCTION_EDEFAULT == null ? instruction != null : !INSTRUCTION_EDEFAULT.equals(instruction);
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
    result.append(" (instruction: ");
    result.append(instruction);
    result.append(')');
    return result.toString();
  }

} //BasicConversionImpl
