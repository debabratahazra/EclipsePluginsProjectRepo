/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.Property;
import com.odcgroup.service.model.component.ReadWrite;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.impl.PropertyImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.PropertyImpl#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.PropertyImpl#getReadOnly <em>Read Only</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.PropertyImpl#getPropertyName <em>Property Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.PropertyImpl#getType1 <em>Type1</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.PropertyImpl#getType2 <em>Type2</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.PropertyImpl#isArray <em>Array</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.PropertyImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyImpl extends MinimalEObjectImpl.Container implements Property
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
   * The default value of the '{@link #getReadOnly() <em>Read Only</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReadOnly()
   * @generated
   * @ordered
   */
  protected static final ReadWrite READ_ONLY_EDEFAULT = ReadWrite.READONLY;

  /**
   * The cached value of the '{@link #getReadOnly() <em>Read Only</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReadOnly()
   * @generated
   * @ordered
   */
  protected ReadWrite readOnly = READ_ONLY_EDEFAULT;

  /**
   * The default value of the '{@link #getPropertyName() <em>Property Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPropertyName()
   * @generated
   * @ordered
   */
  protected static final String PROPERTY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPropertyName() <em>Property Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPropertyName()
   * @generated
   * @ordered
   */
  protected String propertyName = PROPERTY_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getType1() <em>Type1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType1()
   * @generated
   * @ordered
   */
  protected static final String TYPE1_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType1() <em>Type1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType1()
   * @generated
   * @ordered
   */
  protected String type1 = TYPE1_EDEFAULT;

  /**
   * The default value of the '{@link #getType2() <em>Type2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType2()
   * @generated
   * @ordered
   */
  protected static final String TYPE2_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType2() <em>Type2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType2()
   * @generated
   * @ordered
   */
  protected String type2 = TYPE2_EDEFAULT;

  /**
   * The default value of the '{@link #isArray() <em>Array</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArray()
   * @generated
   * @ordered
   */
  protected static final boolean ARRAY_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isArray() <em>Array</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArray()
   * @generated
   * @ordered
   */
  protected boolean array = ARRAY_EDEFAULT;

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
  protected PropertyImpl()
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
    return ComponentPackage.Literals.PROPERTY;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PROPERTY__DOCUMENTATION, oldDocumentation, documentation));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PROPERTY__ACCESS_SPECIFIER, oldAccessSpecifier, accessSpecifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReadWrite getReadOnly()
  {
    return readOnly;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setReadOnly(ReadWrite newReadOnly)
  {
    ReadWrite oldReadOnly = readOnly;
    readOnly = newReadOnly == null ? READ_ONLY_EDEFAULT : newReadOnly;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PROPERTY__READ_ONLY, oldReadOnly, readOnly));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getPropertyName()
  {
    return propertyName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPropertyName(String newPropertyName)
  {
    String oldPropertyName = propertyName;
    propertyName = newPropertyName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PROPERTY__PROPERTY_NAME, oldPropertyName, propertyName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType1()
  {
    return type1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType1(String newType1)
  {
    String oldType1 = type1;
    type1 = newType1;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PROPERTY__TYPE1, oldType1, type1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType2()
  {
    return type2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType2(String newType2)
  {
    String oldType2 = type2;
    type2 = newType2;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PROPERTY__TYPE2, oldType2, type2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isArray()
  {
    return array;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setArray(boolean newArray)
  {
    boolean oldArray = array;
    array = newArray;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PROPERTY__ARRAY, oldArray, array));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PROPERTY__VALUE, oldValue, value));
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
      case ComponentPackage.PROPERTY__DOCUMENTATION:
        return getDocumentation();
      case ComponentPackage.PROPERTY__ACCESS_SPECIFIER:
        return getAccessSpecifier();
      case ComponentPackage.PROPERTY__READ_ONLY:
        return getReadOnly();
      case ComponentPackage.PROPERTY__PROPERTY_NAME:
        return getPropertyName();
      case ComponentPackage.PROPERTY__TYPE1:
        return getType1();
      case ComponentPackage.PROPERTY__TYPE2:
        return getType2();
      case ComponentPackage.PROPERTY__ARRAY:
        return isArray();
      case ComponentPackage.PROPERTY__VALUE:
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
      case ComponentPackage.PROPERTY__DOCUMENTATION:
        setDocumentation((String)newValue);
        return;
      case ComponentPackage.PROPERTY__ACCESS_SPECIFIER:
        setAccessSpecifier((AccessSpecifier)newValue);
        return;
      case ComponentPackage.PROPERTY__READ_ONLY:
        setReadOnly((ReadWrite)newValue);
        return;
      case ComponentPackage.PROPERTY__PROPERTY_NAME:
        setPropertyName((String)newValue);
        return;
      case ComponentPackage.PROPERTY__TYPE1:
        setType1((String)newValue);
        return;
      case ComponentPackage.PROPERTY__TYPE2:
        setType2((String)newValue);
        return;
      case ComponentPackage.PROPERTY__ARRAY:
        setArray((Boolean)newValue);
        return;
      case ComponentPackage.PROPERTY__VALUE:
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
      case ComponentPackage.PROPERTY__DOCUMENTATION:
        setDocumentation(DOCUMENTATION_EDEFAULT);
        return;
      case ComponentPackage.PROPERTY__ACCESS_SPECIFIER:
        setAccessSpecifier(ACCESS_SPECIFIER_EDEFAULT);
        return;
      case ComponentPackage.PROPERTY__READ_ONLY:
        setReadOnly(READ_ONLY_EDEFAULT);
        return;
      case ComponentPackage.PROPERTY__PROPERTY_NAME:
        setPropertyName(PROPERTY_NAME_EDEFAULT);
        return;
      case ComponentPackage.PROPERTY__TYPE1:
        setType1(TYPE1_EDEFAULT);
        return;
      case ComponentPackage.PROPERTY__TYPE2:
        setType2(TYPE2_EDEFAULT);
        return;
      case ComponentPackage.PROPERTY__ARRAY:
        setArray(ARRAY_EDEFAULT);
        return;
      case ComponentPackage.PROPERTY__VALUE:
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
      case ComponentPackage.PROPERTY__DOCUMENTATION:
        return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
      case ComponentPackage.PROPERTY__ACCESS_SPECIFIER:
        return accessSpecifier != ACCESS_SPECIFIER_EDEFAULT;
      case ComponentPackage.PROPERTY__READ_ONLY:
        return readOnly != READ_ONLY_EDEFAULT;
      case ComponentPackage.PROPERTY__PROPERTY_NAME:
        return PROPERTY_NAME_EDEFAULT == null ? propertyName != null : !PROPERTY_NAME_EDEFAULT.equals(propertyName);
      case ComponentPackage.PROPERTY__TYPE1:
        return TYPE1_EDEFAULT == null ? type1 != null : !TYPE1_EDEFAULT.equals(type1);
      case ComponentPackage.PROPERTY__TYPE2:
        return TYPE2_EDEFAULT == null ? type2 != null : !TYPE2_EDEFAULT.equals(type2);
      case ComponentPackage.PROPERTY__ARRAY:
        return array != ARRAY_EDEFAULT;
      case ComponentPackage.PROPERTY__VALUE:
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
    result.append(", readOnly: ");
    result.append(readOnly);
    result.append(", propertyName: ");
    result.append(propertyName);
    result.append(", type1: ");
    result.append(type1);
    result.append(", type2: ");
    result.append(type2);
    result.append(", array: ");
    result.append(array);
    result.append(", value: ");
    result.append(value);
    result.append(')');
    return result.toString();
  }

} //PropertyImpl
