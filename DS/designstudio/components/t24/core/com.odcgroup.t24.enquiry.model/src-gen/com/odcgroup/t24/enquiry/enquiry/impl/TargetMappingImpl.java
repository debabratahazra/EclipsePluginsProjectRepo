/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Target Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetMappingImpl#getFromField <em>From Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetMappingImpl#getToField <em>To Field</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TargetMappingImpl extends MinimalEObjectImpl.Container implements TargetMapping
{
  /**
   * The default value of the '{@link #getFromField() <em>From Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromField()
   * @generated
   * @ordered
   */
  protected static final String FROM_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFromField() <em>From Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromField()
   * @generated
   * @ordered
   */
  protected String fromField = FROM_FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getToField() <em>To Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToField()
   * @generated
   * @ordered
   */
  protected static final String TO_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getToField() <em>To Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToField()
   * @generated
   * @ordered
   */
  protected String toField = TO_FIELD_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TargetMappingImpl()
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
    return EnquiryPackage.Literals.TARGET_MAPPING;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFromField()
  {
    return fromField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFromField(String newFromField)
  {
    String oldFromField = fromField;
    fromField = newFromField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.TARGET_MAPPING__FROM_FIELD, oldFromField, fromField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getToField()
  {
    return toField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setToField(String newToField)
  {
    String oldToField = toField;
    toField = newToField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.TARGET_MAPPING__TO_FIELD, oldToField, toField));
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
      case EnquiryPackage.TARGET_MAPPING__FROM_FIELD:
        return getFromField();
      case EnquiryPackage.TARGET_MAPPING__TO_FIELD:
        return getToField();
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
      case EnquiryPackage.TARGET_MAPPING__FROM_FIELD:
        setFromField((String)newValue);
        return;
      case EnquiryPackage.TARGET_MAPPING__TO_FIELD:
        setToField((String)newValue);
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
      case EnquiryPackage.TARGET_MAPPING__FROM_FIELD:
        setFromField(FROM_FIELD_EDEFAULT);
        return;
      case EnquiryPackage.TARGET_MAPPING__TO_FIELD:
        setToField(TO_FIELD_EDEFAULT);
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
      case EnquiryPackage.TARGET_MAPPING__FROM_FIELD:
        return FROM_FIELD_EDEFAULT == null ? fromField != null : !FROM_FIELD_EDEFAULT.equals(fromField);
      case EnquiryPackage.TARGET_MAPPING__TO_FIELD:
        return TO_FIELD_EDEFAULT == null ? toField != null : !TO_FIELD_EDEFAULT.equals(toField);
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
    result.append(" (fromField: ");
    result.append(fromField);
    result.append(", toField: ");
    result.append(toField);
    result.append(')');
    return result.toString();
  }

} //TargetMappingImpl
