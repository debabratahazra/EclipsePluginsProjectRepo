/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.service.model.component.Attribute;
import com.odcgroup.service.model.component.ComponentPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.impl.AttributeImpl#getAttrName <em>Attr Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.AttributeImpl#getJBCName <em>JBC Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.AttributeImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeImpl extends MinimalEObjectImpl.Container implements Attribute
{
  /**
   * The default value of the '{@link #getAttrName() <em>Attr Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttrName()
   * @generated
   * @ordered
   */
  protected static final String ATTR_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAttrName() <em>Attr Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttrName()
   * @generated
   * @ordered
   */
  protected String attrName = ATTR_NAME_EDEFAULT;

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
  protected AttributeImpl()
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
    return ComponentPackage.Literals.ATTRIBUTE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAttrName()
  {
    return attrName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAttrName(String newAttrName)
  {
    String oldAttrName = attrName;
    attrName = newAttrName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ATTRIBUTE__ATTR_NAME, oldAttrName, attrName));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ATTRIBUTE__JBC_NAME, oldJBCName, jBCName));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ATTRIBUTE__VALUE, oldValue, value));
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
      case ComponentPackage.ATTRIBUTE__ATTR_NAME:
        return getAttrName();
      case ComponentPackage.ATTRIBUTE__JBC_NAME:
        return getJBCName();
      case ComponentPackage.ATTRIBUTE__VALUE:
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
      case ComponentPackage.ATTRIBUTE__ATTR_NAME:
        setAttrName((String)newValue);
        return;
      case ComponentPackage.ATTRIBUTE__JBC_NAME:
        setJBCName((String)newValue);
        return;
      case ComponentPackage.ATTRIBUTE__VALUE:
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
      case ComponentPackage.ATTRIBUTE__ATTR_NAME:
        setAttrName(ATTR_NAME_EDEFAULT);
        return;
      case ComponentPackage.ATTRIBUTE__JBC_NAME:
        setJBCName(JBC_NAME_EDEFAULT);
        return;
      case ComponentPackage.ATTRIBUTE__VALUE:
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
      case ComponentPackage.ATTRIBUTE__ATTR_NAME:
        return ATTR_NAME_EDEFAULT == null ? attrName != null : !ATTR_NAME_EDEFAULT.equals(attrName);
      case ComponentPackage.ATTRIBUTE__JBC_NAME:
        return JBC_NAME_EDEFAULT == null ? jBCName != null : !JBC_NAME_EDEFAULT.equals(jBCName);
      case ComponentPackage.ATTRIBUTE__VALUE:
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
    result.append(" (attrName: ");
    result.append(attrName);
    result.append(", jBCName: ");
    result.append(jBCName);
    result.append(", value: ");
    result.append(value);
    result.append(')');
    return result.toString();
  }

} //AttributeImpl
