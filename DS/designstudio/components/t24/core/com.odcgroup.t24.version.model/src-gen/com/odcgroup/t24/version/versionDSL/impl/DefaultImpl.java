/**
 */
package com.odcgroup.t24.version.versionDSL.impl;

import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Default</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.DefaultImpl#getMv <em>Mv</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.DefaultImpl#getSv <em>Sv</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.DefaultImpl#getDefaultIfOldValueEquals <em>Default If Old Value Equals</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.DefaultImpl#getDefaultNewValueOrAtRoutine <em>Default New Value Or At Routine</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DefaultImpl extends MinimalEObjectImpl.Container implements Default
{
  /**
   * The default value of the '{@link #getMv() <em>Mv</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMv()
   * @generated
   * @ordered
   */
  protected static final Integer MV_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMv() <em>Mv</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMv()
   * @generated
   * @ordered
   */
  protected Integer mv = MV_EDEFAULT;

  /**
   * The default value of the '{@link #getSv() <em>Sv</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSv()
   * @generated
   * @ordered
   */
  protected static final Integer SV_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSv() <em>Sv</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSv()
   * @generated
   * @ordered
   */
  protected Integer sv = SV_EDEFAULT;

  /**
   * The default value of the '{@link #getDefaultIfOldValueEquals() <em>Default If Old Value Equals</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaultIfOldValueEquals()
   * @generated
   * @ordered
   */
  protected static final String DEFAULT_IF_OLD_VALUE_EQUALS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDefaultIfOldValueEquals() <em>Default If Old Value Equals</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaultIfOldValueEquals()
   * @generated
   * @ordered
   */
  protected String defaultIfOldValueEquals = DEFAULT_IF_OLD_VALUE_EQUALS_EDEFAULT;

  /**
   * The cached value of the '{@link #getDefaultNewValueOrAtRoutine() <em>Default New Value Or At Routine</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaultNewValueOrAtRoutine()
   * @generated
   * @ordered
   */
  protected ValueOrAtRoutine defaultNewValueOrAtRoutine;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DefaultImpl()
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
    return VersionDSLPackage.Literals.DEFAULT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getMv()
  {
    return mv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMv(Integer newMv)
  {
    Integer oldMv = mv;
    mv = newMv;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.DEFAULT__MV, oldMv, mv));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getSv()
  {
    return sv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSv(Integer newSv)
  {
    Integer oldSv = sv;
    sv = newSv;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.DEFAULT__SV, oldSv, sv));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDefaultIfOldValueEquals()
  {
    return defaultIfOldValueEquals;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDefaultIfOldValueEquals(String newDefaultIfOldValueEquals)
  {
    String oldDefaultIfOldValueEquals = defaultIfOldValueEquals;
    defaultIfOldValueEquals = newDefaultIfOldValueEquals;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS, oldDefaultIfOldValueEquals, defaultIfOldValueEquals));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ValueOrAtRoutine getDefaultNewValueOrAtRoutine()
  {
    return defaultNewValueOrAtRoutine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDefaultNewValueOrAtRoutine(ValueOrAtRoutine newDefaultNewValueOrAtRoutine, NotificationChain msgs)
  {
    ValueOrAtRoutine oldDefaultNewValueOrAtRoutine = defaultNewValueOrAtRoutine;
    defaultNewValueOrAtRoutine = newDefaultNewValueOrAtRoutine;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE, oldDefaultNewValueOrAtRoutine, newDefaultNewValueOrAtRoutine);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDefaultNewValueOrAtRoutine(ValueOrAtRoutine newDefaultNewValueOrAtRoutine)
  {
    if (newDefaultNewValueOrAtRoutine != defaultNewValueOrAtRoutine)
    {
      NotificationChain msgs = null;
      if (defaultNewValueOrAtRoutine != null)
        msgs = ((InternalEObject)defaultNewValueOrAtRoutine).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE, null, msgs);
      if (newDefaultNewValueOrAtRoutine != null)
        msgs = ((InternalEObject)newDefaultNewValueOrAtRoutine).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE, null, msgs);
      msgs = basicSetDefaultNewValueOrAtRoutine(newDefaultNewValueOrAtRoutine, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE, newDefaultNewValueOrAtRoutine, newDefaultNewValueOrAtRoutine));
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
      case VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE:
        return basicSetDefaultNewValueOrAtRoutine(null, msgs);
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
      case VersionDSLPackage.DEFAULT__MV:
        return getMv();
      case VersionDSLPackage.DEFAULT__SV:
        return getSv();
      case VersionDSLPackage.DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS:
        return getDefaultIfOldValueEquals();
      case VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE:
        return getDefaultNewValueOrAtRoutine();
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
      case VersionDSLPackage.DEFAULT__MV:
        setMv((Integer)newValue);
        return;
      case VersionDSLPackage.DEFAULT__SV:
        setSv((Integer)newValue);
        return;
      case VersionDSLPackage.DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS:
        setDefaultIfOldValueEquals((String)newValue);
        return;
      case VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE:
        setDefaultNewValueOrAtRoutine((ValueOrAtRoutine)newValue);
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
      case VersionDSLPackage.DEFAULT__MV:
        setMv(MV_EDEFAULT);
        return;
      case VersionDSLPackage.DEFAULT__SV:
        setSv(SV_EDEFAULT);
        return;
      case VersionDSLPackage.DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS:
        setDefaultIfOldValueEquals(DEFAULT_IF_OLD_VALUE_EQUALS_EDEFAULT);
        return;
      case VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE:
        setDefaultNewValueOrAtRoutine((ValueOrAtRoutine)null);
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
      case VersionDSLPackage.DEFAULT__MV:
        return MV_EDEFAULT == null ? mv != null : !MV_EDEFAULT.equals(mv);
      case VersionDSLPackage.DEFAULT__SV:
        return SV_EDEFAULT == null ? sv != null : !SV_EDEFAULT.equals(sv);
      case VersionDSLPackage.DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS:
        return DEFAULT_IF_OLD_VALUE_EQUALS_EDEFAULT == null ? defaultIfOldValueEquals != null : !DEFAULT_IF_OLD_VALUE_EQUALS_EDEFAULT.equals(defaultIfOldValueEquals);
      case VersionDSLPackage.DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE:
        return defaultNewValueOrAtRoutine != null;
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
    result.append(" (mv: ");
    result.append(mv);
    result.append(", sv: ");
    result.append(sv);
    result.append(", defaultIfOldValueEquals: ");
    result.append(defaultIfOldValueEquals);
    result.append(')');
    return result.toString();
  }

} //DefaultImpl
