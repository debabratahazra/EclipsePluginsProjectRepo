/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.Constant;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.impl.ConstantImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ConstantImpl#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ConstantImpl#getConstantName <em>Constant Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ConstantImpl#getJBCName <em>JBC Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ConstantImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConstantImpl extends MinimalEObjectImpl.Container implements Constant
{
  /**
   * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDocumentation()
   * @generated
   * @ordered
   */
  protected static final String DOCUMENTATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDocumentation()
   * @generated
   * @ordered
   */
  protected String documentation = DOCUMENTATION_EDEFAULT;

  /**
   * The default value of the '{@link #getAccessSpecifier() <em>Access Specifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccessSpecifier()
   * @generated
   * @ordered
   */
  protected static final AccessSpecifier ACCESS_SPECIFIER_EDEFAULT = AccessSpecifier.MODULE;

  /**
   * The cached value of the '{@link #getAccessSpecifier() <em>Access Specifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccessSpecifier()
   * @generated
   * @ordered
   */
  protected AccessSpecifier accessSpecifier = ACCESS_SPECIFIER_EDEFAULT;

  /**
   * The default value of the '{@link #getConstantName() <em>Constant Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstantName()
   * @generated
   * @ordered
   */
  protected static final String CONSTANT_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getConstantName() <em>Constant Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstantName()
   * @generated
   * @ordered
   */
  protected String constantName = CONSTANT_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getJBCName() <em>JBC Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJBCName()
   * @generated
   * @ordered
   */
  protected static final String JBC_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getJBCName() <em>JBC Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJBCName()
   * @generated
   * @ordered
   */
  protected String jBCName = JBC_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected static final int VALUE_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected int value = VALUE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConstantImpl()
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
    return ComponentPackage.Literals.CONSTANT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDocumentation()
  {
    return documentation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDocumentation(String newDocumentation)
  {
    String oldDocumentation = documentation;
    documentation = newDocumentation;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONSTANT__DOCUMENTATION, oldDocumentation, documentation));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAccessSpecifier(AccessSpecifier newAccessSpecifier)
  {
    AccessSpecifier oldAccessSpecifier = accessSpecifier;
    accessSpecifier = newAccessSpecifier == null ? ACCESS_SPECIFIER_EDEFAULT : newAccessSpecifier;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONSTANT__ACCESS_SPECIFIER, oldAccessSpecifier, accessSpecifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getConstantName()
  {
    return constantName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstantName(String newConstantName)
  {
    String oldConstantName = constantName;
    constantName = newConstantName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONSTANT__CONSTANT_NAME, oldConstantName, constantName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getJBCName()
  {
    return jBCName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setJBCName(String newJBCName)
  {
    String oldJBCName = jBCName;
    jBCName = newJBCName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONSTANT__JBC_NAME, oldJBCName, jBCName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setValue(int newValue)
  {
    int oldValue = value;
    value = newValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONSTANT__VALUE, oldValue, value));
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
      case ComponentPackage.CONSTANT__DOCUMENTATION:
        return getDocumentation();
      case ComponentPackage.CONSTANT__ACCESS_SPECIFIER:
        return getAccessSpecifier();
      case ComponentPackage.CONSTANT__CONSTANT_NAME:
        return getConstantName();
      case ComponentPackage.CONSTANT__JBC_NAME:
        return getJBCName();
      case ComponentPackage.CONSTANT__VALUE:
        return getValue();
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
      case ComponentPackage.CONSTANT__DOCUMENTATION:
        setDocumentation((String)newValue);
        return;
      case ComponentPackage.CONSTANT__ACCESS_SPECIFIER:
        setAccessSpecifier((AccessSpecifier)newValue);
        return;
      case ComponentPackage.CONSTANT__CONSTANT_NAME:
        setConstantName((String)newValue);
        return;
      case ComponentPackage.CONSTANT__JBC_NAME:
        setJBCName((String)newValue);
        return;
      case ComponentPackage.CONSTANT__VALUE:
        setValue((Integer)newValue);
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
      case ComponentPackage.CONSTANT__DOCUMENTATION:
        setDocumentation(DOCUMENTATION_EDEFAULT);
        return;
      case ComponentPackage.CONSTANT__ACCESS_SPECIFIER:
        setAccessSpecifier(ACCESS_SPECIFIER_EDEFAULT);
        return;
      case ComponentPackage.CONSTANT__CONSTANT_NAME:
        setConstantName(CONSTANT_NAME_EDEFAULT);
        return;
      case ComponentPackage.CONSTANT__JBC_NAME:
        setJBCName(JBC_NAME_EDEFAULT);
        return;
      case ComponentPackage.CONSTANT__VALUE:
        setValue(VALUE_EDEFAULT);
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
      case ComponentPackage.CONSTANT__DOCUMENTATION:
        return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
      case ComponentPackage.CONSTANT__ACCESS_SPECIFIER:
        return accessSpecifier != ACCESS_SPECIFIER_EDEFAULT;
      case ComponentPackage.CONSTANT__CONSTANT_NAME:
        return CONSTANT_NAME_EDEFAULT == null ? constantName != null : !CONSTANT_NAME_EDEFAULT.equals(constantName);
      case ComponentPackage.CONSTANT__JBC_NAME:
        return JBC_NAME_EDEFAULT == null ? jBCName != null : !JBC_NAME_EDEFAULT.equals(jBCName);
      case ComponentPackage.CONSTANT__VALUE:
        return value != VALUE_EDEFAULT;
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
    result.append(" (documentation: ");
    result.append(documentation);
    result.append(", accessSpecifier: ");
    result.append(accessSpecifier);
    result.append(", constantName: ");
    result.append(constantName);
    result.append(", jBCName: ");
    result.append(jBCName);
    result.append(", value: ");
    result.append(value);
    result.append(')');
    return result.toString();
  }

} //ConstantImpl
