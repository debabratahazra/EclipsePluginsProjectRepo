/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.CallRoutine;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Routine;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Call Routine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.CallRoutineImpl#getRoutine <em>Routine</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CallRoutineImpl extends ConversionImpl implements CallRoutine
{
  /**
   * The cached value of the '{@link #getRoutine() <em>Routine</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRoutine()
   * @generated
   * @ordered
   */
  protected Routine routine;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CallRoutineImpl()
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
    return EnquiryPackage.Literals.CALL_ROUTINE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Routine getRoutine()
  {
    return routine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetRoutine(Routine newRoutine, NotificationChain msgs)
  {
    Routine oldRoutine = routine;
    routine = newRoutine;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.CALL_ROUTINE__ROUTINE, oldRoutine, newRoutine);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRoutine(Routine newRoutine)
  {
    if (newRoutine != routine)
    {
      NotificationChain msgs = null;
      if (routine != null)
        msgs = ((InternalEObject)routine).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.CALL_ROUTINE__ROUTINE, null, msgs);
      if (newRoutine != null)
        msgs = ((InternalEObject)newRoutine).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.CALL_ROUTINE__ROUTINE, null, msgs);
      msgs = basicSetRoutine(newRoutine, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.CALL_ROUTINE__ROUTINE, newRoutine, newRoutine));
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
      case EnquiryPackage.CALL_ROUTINE__ROUTINE:
        return basicSetRoutine(null, msgs);
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
      case EnquiryPackage.CALL_ROUTINE__ROUTINE:
        return getRoutine();
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
      case EnquiryPackage.CALL_ROUTINE__ROUTINE:
        setRoutine((Routine)newValue);
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
      case EnquiryPackage.CALL_ROUTINE__ROUTINE:
        setRoutine((Routine)null);
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
      case EnquiryPackage.CALL_ROUTINE__ROUTINE:
        return routine != null;
    }
    return super.eIsSet(featureID);
  }

} //CallRoutineImpl
