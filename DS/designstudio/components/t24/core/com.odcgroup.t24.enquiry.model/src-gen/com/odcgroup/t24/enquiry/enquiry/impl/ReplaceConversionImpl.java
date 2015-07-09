/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.ReplaceConversion;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace Conversion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ReplaceConversionImpl#getOldData <em>Old Data</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ReplaceConversionImpl#getNewData <em>New Data</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReplaceConversionImpl extends ConversionImpl implements ReplaceConversion
{
  /**
   * The default value of the '{@link #getOldData() <em>Old Data</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOldData()
   * @generated
   * @ordered
   */
  protected static final String OLD_DATA_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getOldData() <em>Old Data</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOldData()
   * @generated
   * @ordered
   */
  protected String oldData = OLD_DATA_EDEFAULT;

  /**
   * The default value of the '{@link #getNewData() <em>New Data</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewData()
   * @generated
   * @ordered
   */
  protected static final String NEW_DATA_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNewData() <em>New Data</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewData()
   * @generated
   * @ordered
   */
  protected String newData = NEW_DATA_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ReplaceConversionImpl()
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
    return EnquiryPackage.Literals.REPLACE_CONVERSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getOldData()
  {
    return oldData;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOldData(String newOldData)
  {
    String oldOldData = oldData;
    oldData = newOldData;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.REPLACE_CONVERSION__OLD_DATA, oldOldData, oldData));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getNewData()
  {
    return newData;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNewData(String newNewData)
  {
    String oldNewData = newData;
    newData = newNewData;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.REPLACE_CONVERSION__NEW_DATA, oldNewData, newData));
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
      case EnquiryPackage.REPLACE_CONVERSION__OLD_DATA:
        return getOldData();
      case EnquiryPackage.REPLACE_CONVERSION__NEW_DATA:
        return getNewData();
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
      case EnquiryPackage.REPLACE_CONVERSION__OLD_DATA:
        setOldData((String)newValue);
        return;
      case EnquiryPackage.REPLACE_CONVERSION__NEW_DATA:
        setNewData((String)newValue);
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
      case EnquiryPackage.REPLACE_CONVERSION__OLD_DATA:
        setOldData(OLD_DATA_EDEFAULT);
        return;
      case EnquiryPackage.REPLACE_CONVERSION__NEW_DATA:
        setNewData(NEW_DATA_EDEFAULT);
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
      case EnquiryPackage.REPLACE_CONVERSION__OLD_DATA:
        return OLD_DATA_EDEFAULT == null ? oldData != null : !OLD_DATA_EDEFAULT.equals(oldData);
      case EnquiryPackage.REPLACE_CONVERSION__NEW_DATA:
        return NEW_DATA_EDEFAULT == null ? newData != null : !NEW_DATA_EDEFAULT.equals(newData);
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
    result.append(" (oldData: ");
    result.append(oldData);
    result.append(", newData: ");
    result.append(newData);
    result.append(')');
    return result.toString();
  }

} //ReplaceConversionImpl
