/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FunctionKind;
import com.odcgroup.t24.enquiry.enquiry.Parameters;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameters</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl#isAuto <em>Auto</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl#isRunImmediately <em>Run Immediately</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl#getPwActivity <em>Pw Activity</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl#getFieldName <em>Field Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl#getVariable <em>Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParametersImpl extends MinimalEObjectImpl.Container implements Parameters
{
  /**
   * The default value of the '{@link #getFunction() <em>Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunction()
   * @generated
   * @ordered
   */
  protected static final FunctionKind FUNCTION_EDEFAULT = FunctionKind.UNSPECIFIED;

  /**
   * The cached value of the '{@link #getFunction() <em>Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunction()
   * @generated
   * @ordered
   */
  protected FunctionKind function = FUNCTION_EDEFAULT;

  /**
   * The default value of the '{@link #isAuto() <em>Auto</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAuto()
   * @generated
   * @ordered
   */
  protected static final boolean AUTO_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isAuto() <em>Auto</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAuto()
   * @generated
   * @ordered
   */
  protected boolean auto = AUTO_EDEFAULT;

  /**
   * The default value of the '{@link #isRunImmediately() <em>Run Immediately</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isRunImmediately()
   * @generated
   * @ordered
   */
  protected static final boolean RUN_IMMEDIATELY_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isRunImmediately() <em>Run Immediately</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isRunImmediately()
   * @generated
   * @ordered
   */
  protected boolean runImmediately = RUN_IMMEDIATELY_EDEFAULT;

  /**
   * The default value of the '{@link #getPwActivity() <em>Pw Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPwActivity()
   * @generated
   * @ordered
   */
  protected static final String PW_ACTIVITY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPwActivity() <em>Pw Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPwActivity()
   * @generated
   * @ordered
   */
  protected String pwActivity = PW_ACTIVITY_EDEFAULT;

  /**
   * The cached value of the '{@link #getFieldName() <em>Field Name</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFieldName()
   * @generated
   * @ordered
   */
  protected EList<String> fieldName;

  /**
   * The cached value of the '{@link #getVariable() <em>Variable</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariable()
   * @generated
   * @ordered
   */
  protected EList<String> variable;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ParametersImpl()
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
    return EnquiryPackage.Literals.PARAMETERS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FunctionKind getFunction()
  {
    return function;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFunction(FunctionKind newFunction)
  {
    FunctionKind oldFunction = function;
    function = newFunction == null ? FUNCTION_EDEFAULT : newFunction;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.PARAMETERS__FUNCTION, oldFunction, function));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isAuto()
  {
    return auto;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAuto(boolean newAuto)
  {
    boolean oldAuto = auto;
    auto = newAuto;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.PARAMETERS__AUTO, oldAuto, auto));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isRunImmediately()
  {
    return runImmediately;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRunImmediately(boolean newRunImmediately)
  {
    boolean oldRunImmediately = runImmediately;
    runImmediately = newRunImmediately;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.PARAMETERS__RUN_IMMEDIATELY, oldRunImmediately, runImmediately));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getPwActivity()
  {
    return pwActivity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPwActivity(String newPwActivity)
  {
    String oldPwActivity = pwActivity;
    pwActivity = newPwActivity;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.PARAMETERS__PW_ACTIVITY, oldPwActivity, pwActivity));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getFieldName()
  {
    if (fieldName == null)
    {
      fieldName = new EDataTypeEList<String>(String.class, this, EnquiryPackage.PARAMETERS__FIELD_NAME);
    }
    return fieldName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getVariable()
  {
    if (variable == null)
    {
      variable = new EDataTypeEList<String>(String.class, this, EnquiryPackage.PARAMETERS__VARIABLE);
    }
    return variable;
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
      case EnquiryPackage.PARAMETERS__FUNCTION:
        return getFunction();
      case EnquiryPackage.PARAMETERS__AUTO:
        return isAuto();
      case EnquiryPackage.PARAMETERS__RUN_IMMEDIATELY:
        return isRunImmediately();
      case EnquiryPackage.PARAMETERS__PW_ACTIVITY:
        return getPwActivity();
      case EnquiryPackage.PARAMETERS__FIELD_NAME:
        return getFieldName();
      case EnquiryPackage.PARAMETERS__VARIABLE:
        return getVariable();
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
      case EnquiryPackage.PARAMETERS__FUNCTION:
        setFunction((FunctionKind)newValue);
        return;
      case EnquiryPackage.PARAMETERS__AUTO:
        setAuto((Boolean)newValue);
        return;
      case EnquiryPackage.PARAMETERS__RUN_IMMEDIATELY:
        setRunImmediately((Boolean)newValue);
        return;
      case EnquiryPackage.PARAMETERS__PW_ACTIVITY:
        setPwActivity((String)newValue);
        return;
      case EnquiryPackage.PARAMETERS__FIELD_NAME:
        getFieldName().clear();
        getFieldName().addAll((Collection<? extends String>)newValue);
        return;
      case EnquiryPackage.PARAMETERS__VARIABLE:
        getVariable().clear();
        getVariable().addAll((Collection<? extends String>)newValue);
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
      case EnquiryPackage.PARAMETERS__FUNCTION:
        setFunction(FUNCTION_EDEFAULT);
        return;
      case EnquiryPackage.PARAMETERS__AUTO:
        setAuto(AUTO_EDEFAULT);
        return;
      case EnquiryPackage.PARAMETERS__RUN_IMMEDIATELY:
        setRunImmediately(RUN_IMMEDIATELY_EDEFAULT);
        return;
      case EnquiryPackage.PARAMETERS__PW_ACTIVITY:
        setPwActivity(PW_ACTIVITY_EDEFAULT);
        return;
      case EnquiryPackage.PARAMETERS__FIELD_NAME:
        getFieldName().clear();
        return;
      case EnquiryPackage.PARAMETERS__VARIABLE:
        getVariable().clear();
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
      case EnquiryPackage.PARAMETERS__FUNCTION:
        return function != FUNCTION_EDEFAULT;
      case EnquiryPackage.PARAMETERS__AUTO:
        return auto != AUTO_EDEFAULT;
      case EnquiryPackage.PARAMETERS__RUN_IMMEDIATELY:
        return runImmediately != RUN_IMMEDIATELY_EDEFAULT;
      case EnquiryPackage.PARAMETERS__PW_ACTIVITY:
        return PW_ACTIVITY_EDEFAULT == null ? pwActivity != null : !PW_ACTIVITY_EDEFAULT.equals(pwActivity);
      case EnquiryPackage.PARAMETERS__FIELD_NAME:
        return fieldName != null && !fieldName.isEmpty();
      case EnquiryPackage.PARAMETERS__VARIABLE:
        return variable != null && !variable.isEmpty();
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
    result.append(" (function: ");
    result.append(function);
    result.append(", auto: ");
    result.append(auto);
    result.append(", runImmediately: ");
    result.append(runImmediately);
    result.append(", pwActivity: ");
    result.append(pwActivity);
    result.append(", fieldName: ");
    result.append(fieldName);
    result.append(", variable: ");
    result.append(variable);
    result.append(')');
    return result.toString();
  }

} //ParametersImpl
