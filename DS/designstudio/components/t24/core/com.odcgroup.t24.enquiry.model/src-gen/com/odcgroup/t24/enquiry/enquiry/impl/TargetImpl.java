/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Target</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetImpl#getApplication <em>Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetImpl#getScreen <em>Screen</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetImpl#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TargetImpl extends MinimalEObjectImpl.Container implements Target
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
   * The default value of the '{@link #getScreen() <em>Screen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getScreen()
   * @generated
   * @ordered
   */
  protected static final String SCREEN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getScreen() <em>Screen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getScreen()
   * @generated
   * @ordered
   */
  protected String screen = SCREEN_EDEFAULT;

  /**
   * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMappings()
   * @generated
   * @ordered
   */
  protected EList<TargetMapping> mappings;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TargetImpl()
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
    return EnquiryPackage.Literals.TARGET;
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
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.TARGET__APPLICATION, oldApplication, application));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getScreen()
  {
    return screen;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setScreen(String newScreen)
  {
    String oldScreen = screen;
    screen = newScreen;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.TARGET__SCREEN, oldScreen, screen));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TargetMapping> getMappings()
  {
    if (mappings == null)
    {
      mappings = new EObjectContainmentEList<TargetMapping>(TargetMapping.class, this, EnquiryPackage.TARGET__MAPPINGS);
    }
    return mappings;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case EnquiryPackage.TARGET__MAPPINGS:
        return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case EnquiryPackage.TARGET__APPLICATION:
        return getApplication();
      case EnquiryPackage.TARGET__SCREEN:
        return getScreen();
      case EnquiryPackage.TARGET__MAPPINGS:
        return getMappings();
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
      case EnquiryPackage.TARGET__APPLICATION:
        setApplication((String)newValue);
        return;
      case EnquiryPackage.TARGET__SCREEN:
        setScreen((String)newValue);
        return;
      case EnquiryPackage.TARGET__MAPPINGS:
        getMappings().clear();
        getMappings().addAll((Collection<? extends TargetMapping>)newValue);
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
      case EnquiryPackage.TARGET__APPLICATION:
        setApplication(APPLICATION_EDEFAULT);
        return;
      case EnquiryPackage.TARGET__SCREEN:
        setScreen(SCREEN_EDEFAULT);
        return;
      case EnquiryPackage.TARGET__MAPPINGS:
        getMappings().clear();
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
      case EnquiryPackage.TARGET__APPLICATION:
        return APPLICATION_EDEFAULT == null ? application != null : !APPLICATION_EDEFAULT.equals(application);
      case EnquiryPackage.TARGET__SCREEN:
        return SCREEN_EDEFAULT == null ? screen != null : !SCREEN_EDEFAULT.equals(screen);
      case EnquiryPackage.TARGET__MAPPINGS:
        return mappings != null && !mappings.isEmpty();
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
    result.append(", screen: ");
    result.append(screen);
    result.append(')');
    return result.toString();
  }

} //TargetImpl
