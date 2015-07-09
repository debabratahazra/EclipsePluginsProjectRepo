/**
 */
package com.odcgroup.t24.version.versionDSL.impl;

import com.odcgroup.t24.version.versionDSL.DealSlip;
import com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deal Slip</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.DealSlipImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.DealSlipImpl#getFunction <em>Function</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DealSlipImpl extends MinimalEObjectImpl.Container implements DealSlip
{
  /**
   * The default value of the '{@link #getFormat() <em>Format</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFormat()
   * @generated
   * @ordered
   */
  protected static final String FORMAT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFormat() <em>Format</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFormat()
   * @generated
   * @ordered
   */
  protected String format = FORMAT_EDEFAULT;

  /**
   * The default value of the '{@link #getFunction() <em>Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunction()
   * @generated
   * @ordered
   */
  protected static final DealSlipFormatFunction FUNCTION_EDEFAULT = DealSlipFormatFunction.NONE;

  /**
   * The cached value of the '{@link #getFunction() <em>Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunction()
   * @generated
   * @ordered
   */
  protected DealSlipFormatFunction function = FUNCTION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DealSlipImpl()
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
    return VersionDSLPackage.Literals.DEAL_SLIP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFormat()
  {
    return format;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFormat(String newFormat)
  {
    String oldFormat = format;
    format = newFormat;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.DEAL_SLIP__FORMAT, oldFormat, format));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DealSlipFormatFunction getFunction()
  {
    return function;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFunction(DealSlipFormatFunction newFunction)
  {
    DealSlipFormatFunction oldFunction = function;
    function = newFunction == null ? FUNCTION_EDEFAULT : newFunction;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.DEAL_SLIP__FUNCTION, oldFunction, function));
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
      case VersionDSLPackage.DEAL_SLIP__FORMAT:
        return getFormat();
      case VersionDSLPackage.DEAL_SLIP__FUNCTION:
        return getFunction();
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
      case VersionDSLPackage.DEAL_SLIP__FORMAT:
        setFormat((String)newValue);
        return;
      case VersionDSLPackage.DEAL_SLIP__FUNCTION:
        setFunction((DealSlipFormatFunction)newValue);
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
      case VersionDSLPackage.DEAL_SLIP__FORMAT:
        setFormat(FORMAT_EDEFAULT);
        return;
      case VersionDSLPackage.DEAL_SLIP__FUNCTION:
        setFunction(FUNCTION_EDEFAULT);
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
      case VersionDSLPackage.DEAL_SLIP__FORMAT:
        return FORMAT_EDEFAULT == null ? format != null : !FORMAT_EDEFAULT.equals(format);
      case VersionDSLPackage.DEAL_SLIP__FUNCTION:
        return function != FUNCTION_EDEFAULT;
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
    result.append(" (format: ");
    result.append(format);
    result.append(", function: ");
    result.append(function);
    result.append(')');
    return result.toString();
  }

} //DealSlipImpl
