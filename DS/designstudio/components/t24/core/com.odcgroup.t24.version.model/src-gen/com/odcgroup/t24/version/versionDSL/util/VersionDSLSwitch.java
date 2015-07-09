/**
 */
package com.odcgroup.t24.version.versionDSL.util;

import com.odcgroup.t24.version.versionDSL.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage
 * @generated
 */
public class VersionDSLSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static VersionDSLPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VersionDSLSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = VersionDSLPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case VersionDSLPackage.VERSION:
      {
        Version version = (Version)theEObject;
        T result = caseVersion(version);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VersionDSLPackage.FIELD:
      {
        Field field = (Field)theEObject;
        T result = caseField(field);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VersionDSLPackage.DEFAULT:
      {
        Default default_ = (Default)theEObject;
        T result = caseDefault(default_);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VersionDSLPackage.ROUTINE:
      {
        Routine routine = (Routine)theEObject;
        T result = caseRoutine(routine);
        if (result == null) result = caseAtRoutine(routine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VersionDSLPackage.AT_ROUTINE:
      {
        AtRoutine atRoutine = (AtRoutine)theEObject;
        T result = caseAtRoutine(atRoutine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE:
      {
        ValueOrAtRoutine valueOrAtRoutine = (ValueOrAtRoutine)theEObject;
        T result = caseValueOrAtRoutine(valueOrAtRoutine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VersionDSLPackage.JBC_ROUTINE:
      {
        JBCRoutine jbcRoutine = (JBCRoutine)theEObject;
        T result = caseJBCRoutine(jbcRoutine);
        if (result == null) result = caseRoutine(jbcRoutine);
        if (result == null) result = caseAtRoutine(jbcRoutine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VersionDSLPackage.JAVA_ROUTINE:
      {
        JavaRoutine javaRoutine = (JavaRoutine)theEObject;
        T result = caseJavaRoutine(javaRoutine);
        if (result == null) result = caseRoutine(javaRoutine);
        if (result == null) result = caseAtRoutine(javaRoutine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VersionDSLPackage.DEAL_SLIP:
      {
        DealSlip dealSlip = (DealSlip)theEObject;
        T result = caseDealSlip(dealSlip);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Version</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Version</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVersion(Version object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Field</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Field</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseField(Field object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Default</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Default</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDefault(Default object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRoutine(Routine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>At Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>At Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAtRoutine(AtRoutine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Value Or At Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Value Or At Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseValueOrAtRoutine(ValueOrAtRoutine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>JBC Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>JBC Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJBCRoutine(JBCRoutine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Java Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Java Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJavaRoutine(JavaRoutine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Deal Slip</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Deal Slip</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDealSlip(DealSlip object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //VersionDSLSwitch
