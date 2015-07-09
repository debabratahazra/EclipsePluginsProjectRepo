/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.WebService;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Web Service</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.WebServiceImpl#getPublishWebService <em>Publish Web Service</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.WebServiceImpl#getWebServiceNames <em>Web Service Names</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.WebServiceImpl#getWebServiceActivity <em>Web Service Activity</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.WebServiceImpl#getWebServiceDescription <em>Web Service Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WebServiceImpl extends MinimalEObjectImpl.Container implements WebService
{
  /**
   * The default value of the '{@link #getPublishWebService() <em>Publish Web Service</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPublishWebService()
   * @generated
   * @ordered
   */
  protected static final Boolean PUBLISH_WEB_SERVICE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPublishWebService() <em>Publish Web Service</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPublishWebService()
   * @generated
   * @ordered
   */
  protected Boolean publishWebService = PUBLISH_WEB_SERVICE_EDEFAULT;

  /**
   * The cached value of the '{@link #getWebServiceNames() <em>Web Service Names</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceNames()
   * @generated
   * @ordered
   */
  protected EList<String> webServiceNames;

  /**
   * The default value of the '{@link #getWebServiceActivity() <em>Web Service Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceActivity()
   * @generated
   * @ordered
   */
  protected static final String WEB_SERVICE_ACTIVITY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getWebServiceActivity() <em>Web Service Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceActivity()
   * @generated
   * @ordered
   */
  protected String webServiceActivity = WEB_SERVICE_ACTIVITY_EDEFAULT;

  /**
   * The default value of the '{@link #getWebServiceDescription() <em>Web Service Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceDescription()
   * @generated
   * @ordered
   */
  protected static final String WEB_SERVICE_DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getWebServiceDescription() <em>Web Service Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceDescription()
   * @generated
   * @ordered
   */
  protected String webServiceDescription = WEB_SERVICE_DESCRIPTION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WebServiceImpl()
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
    return EnquiryPackage.Literals.WEB_SERVICE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getPublishWebService()
  {
    return publishWebService;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPublishWebService(Boolean newPublishWebService)
  {
    Boolean oldPublishWebService = publishWebService;
    publishWebService = newPublishWebService;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.WEB_SERVICE__PUBLISH_WEB_SERVICE, oldPublishWebService, publishWebService));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getWebServiceNames()
  {
    if (webServiceNames == null)
    {
      webServiceNames = new EDataTypeEList<String>(String.class, this, EnquiryPackage.WEB_SERVICE__WEB_SERVICE_NAMES);
    }
    return webServiceNames;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getWebServiceActivity()
  {
    return webServiceActivity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWebServiceActivity(String newWebServiceActivity)
  {
    String oldWebServiceActivity = webServiceActivity;
    webServiceActivity = newWebServiceActivity;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.WEB_SERVICE__WEB_SERVICE_ACTIVITY, oldWebServiceActivity, webServiceActivity));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getWebServiceDescription()
  {
    return webServiceDescription;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWebServiceDescription(String newWebServiceDescription)
  {
    String oldWebServiceDescription = webServiceDescription;
    webServiceDescription = newWebServiceDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.WEB_SERVICE__WEB_SERVICE_DESCRIPTION, oldWebServiceDescription, webServiceDescription));
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
      case EnquiryPackage.WEB_SERVICE__PUBLISH_WEB_SERVICE:
        return getPublishWebService();
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_NAMES:
        return getWebServiceNames();
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_ACTIVITY:
        return getWebServiceActivity();
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_DESCRIPTION:
        return getWebServiceDescription();
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
      case EnquiryPackage.WEB_SERVICE__PUBLISH_WEB_SERVICE:
        setPublishWebService((Boolean)newValue);
        return;
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_NAMES:
        getWebServiceNames().clear();
        getWebServiceNames().addAll((Collection<? extends String>)newValue);
        return;
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_ACTIVITY:
        setWebServiceActivity((String)newValue);
        return;
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_DESCRIPTION:
        setWebServiceDescription((String)newValue);
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
      case EnquiryPackage.WEB_SERVICE__PUBLISH_WEB_SERVICE:
        setPublishWebService(PUBLISH_WEB_SERVICE_EDEFAULT);
        return;
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_NAMES:
        getWebServiceNames().clear();
        return;
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_ACTIVITY:
        setWebServiceActivity(WEB_SERVICE_ACTIVITY_EDEFAULT);
        return;
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_DESCRIPTION:
        setWebServiceDescription(WEB_SERVICE_DESCRIPTION_EDEFAULT);
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
      case EnquiryPackage.WEB_SERVICE__PUBLISH_WEB_SERVICE:
        return PUBLISH_WEB_SERVICE_EDEFAULT == null ? publishWebService != null : !PUBLISH_WEB_SERVICE_EDEFAULT.equals(publishWebService);
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_NAMES:
        return webServiceNames != null && !webServiceNames.isEmpty();
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_ACTIVITY:
        return WEB_SERVICE_ACTIVITY_EDEFAULT == null ? webServiceActivity != null : !WEB_SERVICE_ACTIVITY_EDEFAULT.equals(webServiceActivity);
      case EnquiryPackage.WEB_SERVICE__WEB_SERVICE_DESCRIPTION:
        return WEB_SERVICE_DESCRIPTION_EDEFAULT == null ? webServiceDescription != null : !WEB_SERVICE_DESCRIPTION_EDEFAULT.equals(webServiceDescription);
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
    result.append(" (publishWebService: ");
    result.append(publishWebService);
    result.append(", webServiceNames: ");
    result.append(webServiceNames);
    result.append(", webServiceActivity: ");
    result.append(webServiceActivity);
    result.append(", webServiceDescription: ");
    result.append(webServiceDescription);
    result.append(')');
    return result.toString();
  }

} //WebServiceImpl
