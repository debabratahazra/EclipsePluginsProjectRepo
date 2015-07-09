/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.ValueConversion;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Value Conversion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ValueConversionImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ValueConversionImpl#getSubValue <em>Sub Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValueConversionImpl extends ConversionImpl implements ValueConversion
{
  /**
   * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected static final int VALUE_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected int value = VALUE_EDEFAULT;

  /**
   * The default value of the '{@link #getSubValue() <em>Sub Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubValue()
   * @generated
   * @ordered
   */
  protected static final Integer SUB_VALUE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSubValue() <em>Sub Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubValue()
   * @generated
   * @ordered
   */
  protected Integer subValue = SUB_VALUE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ValueConversionImpl()
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
    return EnquiryPackage.Literals.VALUE_CONVERSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setValue(int newValue)
  {
    int oldValue = value;
    value = newValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.VALUE_CONVERSION__VALUE, oldValue, value));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getSubValue()
  {
    return subValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSubValue(Integer newSubValue)
  {
    Integer oldSubValue = subValue;
    subValue = newSubValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.VALUE_CONVERSION__SUB_VALUE, oldSubValue, subValue));
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
      case EnquiryPackage.VALUE_CONVERSION__VALUE:
        return getValue();
      case EnquiryPackage.VALUE_CONVERSION__SUB_VALUE:
        return getSubValue();
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
      case EnquiryPackage.VALUE_CONVERSION__VALUE:
        setValue((Integer)newValue);
        return;
      case EnquiryPackage.VALUE_CONVERSION__SUB_VALUE:
        setSubValue((Integer)newValue);
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
      case EnquiryPackage.VALUE_CONVERSION__VALUE:
        setValue(VALUE_EDEFAULT);
        return;
      case EnquiryPackage.VALUE_CONVERSION__SUB_VALUE:
        setSubValue(SUB_VALUE_EDEFAULT);
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
      case EnquiryPackage.VALUE_CONVERSION__VALUE:
        return value != VALUE_EDEFAULT;
      case EnquiryPackage.VALUE_CONVERSION__SUB_VALUE:
        return SUB_VALUE_EDEFAULT == null ? subValue != null : !SUB_VALUE_EDEFAULT.equals(subValue);
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
    result.append(" (value: ");
    result.append(value);
    result.append(", subValue: ");
    result.append(subValue);
    result.append(')');
    return result.toString();
  }

} //ValueConversionImpl
