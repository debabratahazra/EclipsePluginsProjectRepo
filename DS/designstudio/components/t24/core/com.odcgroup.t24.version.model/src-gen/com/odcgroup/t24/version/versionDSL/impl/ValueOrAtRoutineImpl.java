/**
 */
package com.odcgroup.t24.version.versionDSL.impl;

import com.odcgroup.t24.version.versionDSL.AtRoutine;
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
 * An implementation of the model object '<em><b>Value Or At Routine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.ValueOrAtRoutineImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.ValueOrAtRoutineImpl#getAtRoutine <em>At Routine</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValueOrAtRoutineImpl extends MinimalEObjectImpl.Container implements ValueOrAtRoutine
{
  /**
   * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected static final String VALUE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected String value = VALUE_EDEFAULT;

  /**
   * The cached value of the '{@link #getAtRoutine() <em>At Routine</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAtRoutine()
   * @generated
   * @ordered
   */
  protected AtRoutine atRoutine;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ValueOrAtRoutineImpl()
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
    return VersionDSLPackage.Literals.VALUE_OR_AT_ROUTINE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setValue(String newValue)
  {
    String oldValue = value;
    value = newValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VALUE_OR_AT_ROUTINE__VALUE, oldValue, value));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AtRoutine getAtRoutine()
  {
    return atRoutine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAtRoutine(AtRoutine newAtRoutine, NotificationChain msgs)
  {
    AtRoutine oldAtRoutine = atRoutine;
    atRoutine = newAtRoutine;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE, oldAtRoutine, newAtRoutine);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAtRoutine(AtRoutine newAtRoutine)
  {
    if (newAtRoutine != atRoutine)
    {
      NotificationChain msgs = null;
      if (atRoutine != null)
        msgs = ((InternalEObject)atRoutine).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE, null, msgs);
      if (newAtRoutine != null)
        msgs = ((InternalEObject)newAtRoutine).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE, null, msgs);
      msgs = basicSetAtRoutine(newAtRoutine, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE, newAtRoutine, newAtRoutine));
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
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE:
        return basicSetAtRoutine(null, msgs);
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
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__VALUE:
        return getValue();
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE:
        return getAtRoutine();
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
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__VALUE:
        setValue((String)newValue);
        return;
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE:
        setAtRoutine((AtRoutine)newValue);
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
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__VALUE:
        setValue(VALUE_EDEFAULT);
        return;
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE:
        setAtRoutine((AtRoutine)null);
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
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__VALUE:
        return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE__AT_ROUTINE:
        return atRoutine != null;
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
    result.append(')');
    return result.toString();
  }

} //ValueOrAtRoutineImpl
