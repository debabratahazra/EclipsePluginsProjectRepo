/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.Companies;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Companies</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.CompaniesImpl#getAll <em>All</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.CompaniesImpl#getCode <em>Code</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompaniesImpl extends MinimalEObjectImpl.Container implements Companies
{
  /**
   * The default value of the '{@link #getAll() <em>All</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAll()
   * @generated
   * @ordered
   */
  protected static final Boolean ALL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAll() <em>All</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAll()
   * @generated
   * @ordered
   */
  protected Boolean all = ALL_EDEFAULT;

  /**
   * The cached value of the '{@link #getCode() <em>Code</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCode()
   * @generated
   * @ordered
   */
  protected EList<String> code;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CompaniesImpl()
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
    return EnquiryPackage.Literals.COMPANIES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getAll()
  {
    return all;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAll(Boolean newAll)
  {
    Boolean oldAll = all;
    all = newAll;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.COMPANIES__ALL, oldAll, all));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getCode()
  {
    if (code == null)
    {
      code = new EDataTypeEList<String>(String.class, this, EnquiryPackage.COMPANIES__CODE);
    }
    return code;
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
      case EnquiryPackage.COMPANIES__ALL:
        return getAll();
      case EnquiryPackage.COMPANIES__CODE:
        return getCode();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case EnquiryPackage.COMPANIES__ALL:
        setAll((Boolean)newValue);
        return;
      case EnquiryPackage.COMPANIES__CODE:
        getCode().clear();
        getCode().addAll((Collection<? extends String>)newValue);
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
      case EnquiryPackage.COMPANIES__ALL:
        setAll(ALL_EDEFAULT);
        return;
      case EnquiryPackage.COMPANIES__CODE:
        getCode().clear();
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
      case EnquiryPackage.COMPANIES__ALL:
        return ALL_EDEFAULT == null ? all != null : !ALL_EDEFAULT.equals(all);
      case EnquiryPackage.COMPANIES__CODE:
        return code != null && !code.isEmpty();
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
    result.append(" (all: ");
    result.append(all);
    result.append(", code: ");
    result.append(code);
    result.append(')');
    return result.toString();
  }

} //CompaniesImpl
