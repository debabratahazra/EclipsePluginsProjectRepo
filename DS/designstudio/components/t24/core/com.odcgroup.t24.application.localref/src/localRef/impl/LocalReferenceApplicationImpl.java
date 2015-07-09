/**
 */
package localRef.impl;

import java.util.Collection;

import localRef.LocalRefPackage;
import localRef.LocalReferenceApplication;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Reference Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link localRef.impl.LocalReferenceApplicationImpl#getLocalField <em>Local Field</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalReferenceApplicationImpl extends MinimalEObjectImpl.Container implements LocalReferenceApplication
{
  /**
   * The cached value of the '{@link #getLocalField() <em>Local Field</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLocalField()
   * @generated
   * @ordered
   */
  protected EList<String> localField;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LocalReferenceApplicationImpl()
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
    return LocalRefPackage.Literals.LOCAL_REFERENCE_APPLICATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getLocalField()
  {
    if (localField == null)
    {
      localField = new EDataTypeEList<String>(String.class, this, LocalRefPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD);
    }
    return localField;
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
      case LocalRefPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
        return getLocalField();
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
      case LocalRefPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
        getLocalField().clear();
        getLocalField().addAll((Collection<? extends String>)newValue);
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
      case LocalRefPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
        getLocalField().clear();
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
      case LocalRefPackage.LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD:
        return localField != null && !localField.isEmpty();
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
    result.append(" (localField: ");
    result.append(localField);
    result.append(')');
    return result.toString();
  }

} //LocalReferenceApplicationImpl
