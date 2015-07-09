/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Security;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Security</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SecurityImpl#getApplication <em>Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SecurityImpl#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SecurityImpl#isAbort <em>Abort</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SecurityImpl extends MinimalEObjectImpl.Container implements Security
{
  /**
   * The default value of the '{@link #getApplication() <em>Application</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getApplication()
   * @generated
   * @ordered
   */
  protected static final String APPLICATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getApplication() <em>Application</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getApplication()
   * @generated
   * @ordered
   */
  protected String application = APPLICATION_EDEFAULT;

  /**
   * The default value of the '{@link #getField() <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getField()
   * @generated
   * @ordered
   */
  protected static final String FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getField() <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getField()
   * @generated
   * @ordered
   */
  protected String field = FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #isAbort() <em>Abort</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAbort()
   * @generated
   * @ordered
   */
  protected static final boolean ABORT_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isAbort() <em>Abort</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAbort()
   * @generated
   * @ordered
   */
  protected boolean abort = ABORT_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SecurityImpl()
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
    return EnquiryPackage.Literals.SECURITY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getApplication()
  {
    return application;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setApplication(String newApplication)
  {
    String oldApplication = application;
    application = newApplication;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SECURITY__APPLICATION, oldApplication, application));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getField()
  {
    return field;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setField(String newField)
  {
    String oldField = field;
    field = newField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SECURITY__FIELD, oldField, field));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isAbort()
  {
    return abort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAbort(boolean newAbort)
  {
    boolean oldAbort = abort;
    abort = newAbort;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SECURITY__ABORT, oldAbort, abort));
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
      case EnquiryPackage.SECURITY__APPLICATION:
        return getApplication();
      case EnquiryPackage.SECURITY__FIELD:
        return getField();
      case EnquiryPackage.SECURITY__ABORT:
        return isAbort();
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
      case EnquiryPackage.SECURITY__APPLICATION:
        setApplication((String)newValue);
        return;
      case EnquiryPackage.SECURITY__FIELD:
        setField((String)newValue);
        return;
      case EnquiryPackage.SECURITY__ABORT:
        setAbort((Boolean)newValue);
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
      case EnquiryPackage.SECURITY__APPLICATION:
        setApplication(APPLICATION_EDEFAULT);
        return;
      case EnquiryPackage.SECURITY__FIELD:
        setField(FIELD_EDEFAULT);
        return;
      case EnquiryPackage.SECURITY__ABORT:
        setAbort(ABORT_EDEFAULT);
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
      case EnquiryPackage.SECURITY__APPLICATION:
        return APPLICATION_EDEFAULT == null ? application != null : !APPLICATION_EDEFAULT.equals(application);
      case EnquiryPackage.SECURITY__FIELD:
        return FIELD_EDEFAULT == null ? field != null : !FIELD_EDEFAULT.equals(field);
      case EnquiryPackage.SECURITY__ABORT:
        return abort != ABORT_EDEFAULT;
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
    result.append(" (application: ");
    result.append(application);
    result.append(", field: ");
    result.append(field);
    result.append(", abort: ");
    result.append(abort);
    result.append(')');
    return result.toString();
  }

} //SecurityImpl
