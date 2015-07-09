/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.GetFromConversion;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get From Conversion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFromConversionImpl#getApplication <em>Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFromConversionImpl#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFromConversionImpl#isLanguage <em>Language</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetFromConversionImpl extends ConversionImpl implements GetFromConversion
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
   * The default value of the '{@link #isLanguage() <em>Language</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isLanguage()
   * @generated
   * @ordered
   */
  protected static final boolean LANGUAGE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isLanguage() <em>Language</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isLanguage()
   * @generated
   * @ordered
   */
  protected boolean language = LANGUAGE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GetFromConversionImpl()
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
    return EnquiryPackage.Literals.GET_FROM_CONVERSION;
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
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GET_FROM_CONVERSION__APPLICATION, oldApplication, application));
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
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GET_FROM_CONVERSION__FIELD, oldField, field));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isLanguage()
  {
    return language;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLanguage(boolean newLanguage)
  {
    boolean oldLanguage = language;
    language = newLanguage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GET_FROM_CONVERSION__LANGUAGE, oldLanguage, language));
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
      case EnquiryPackage.GET_FROM_CONVERSION__APPLICATION:
        return getApplication();
      case EnquiryPackage.GET_FROM_CONVERSION__FIELD:
        return getField();
      case EnquiryPackage.GET_FROM_CONVERSION__LANGUAGE:
        return isLanguage();
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
      case EnquiryPackage.GET_FROM_CONVERSION__APPLICATION:
        setApplication((String)newValue);
        return;
      case EnquiryPackage.GET_FROM_CONVERSION__FIELD:
        setField((String)newValue);
        return;
      case EnquiryPackage.GET_FROM_CONVERSION__LANGUAGE:
        setLanguage((Boolean)newValue);
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
      case EnquiryPackage.GET_FROM_CONVERSION__APPLICATION:
        setApplication(APPLICATION_EDEFAULT);
        return;
      case EnquiryPackage.GET_FROM_CONVERSION__FIELD:
        setField(FIELD_EDEFAULT);
        return;
      case EnquiryPackage.GET_FROM_CONVERSION__LANGUAGE:
        setLanguage(LANGUAGE_EDEFAULT);
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
      case EnquiryPackage.GET_FROM_CONVERSION__APPLICATION:
        return APPLICATION_EDEFAULT == null ? application != null : !APPLICATION_EDEFAULT.equals(application);
      case EnquiryPackage.GET_FROM_CONVERSION__FIELD:
        return FIELD_EDEFAULT == null ? field != null : !FIELD_EDEFAULT.equals(field);
      case EnquiryPackage.GET_FROM_CONVERSION__LANGUAGE:
        return language != LANGUAGE_EDEFAULT;
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
    result.append(", language: ");
    result.append(language);
    result.append(')');
    return result.toString();
  }

} //GetFromConversionImpl
