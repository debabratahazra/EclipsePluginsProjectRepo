/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.Attribute;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.Table;

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
 * An implementation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.impl.TableImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.TableImpl#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.TableImpl#getTableName <em>Table Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.TableImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.TableImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TableImpl extends MinimalEObjectImpl.Container implements Table
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
   * The default value of the '{@link #getTableName() <em>Table Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTableName()
   * @generated
   * @ordered
   */
  protected static final String TABLE_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTableName() <em>Table Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTableName()
   * @generated
   * @ordered
   */
  protected String tableName = TABLE_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final String TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected String type = TYPE_EDEFAULT;

  /**
   * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttribute()
   * @generated
   * @ordered
   */
  protected EList<Attribute> attribute;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TableImpl()
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
    return ComponentPackage.Literals.TABLE;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TABLE__DOCUMENTATION, oldDocumentation, documentation));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TABLE__ACCESS_SPECIFIER, oldAccessSpecifier, accessSpecifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTableName()
  {
    return tableName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTableName(String newTableName)
  {
    String oldTableName = tableName;
    tableName = newTableName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TABLE__TABLE_NAME, oldTableName, tableName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(String newType)
  {
    String oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TABLE__TYPE, oldType, type));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Attribute> getAttribute()
  {
    if (attribute == null)
    {
      attribute = new EObjectContainmentEList<Attribute>(Attribute.class, this, ComponentPackage.TABLE__ATTRIBUTE);
    }
    return attribute;
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
      case ComponentPackage.TABLE__ATTRIBUTE:
        return ((InternalEList<?>)getAttribute()).basicRemove(otherEnd, msgs);
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
      case ComponentPackage.TABLE__DOCUMENTATION:
        return getDocumentation();
      case ComponentPackage.TABLE__ACCESS_SPECIFIER:
        return getAccessSpecifier();
      case ComponentPackage.TABLE__TABLE_NAME:
        return getTableName();
      case ComponentPackage.TABLE__TYPE:
        return getType();
      case ComponentPackage.TABLE__ATTRIBUTE:
        return getAttribute();
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
      case ComponentPackage.TABLE__DOCUMENTATION:
        setDocumentation((String)newValue);
        return;
      case ComponentPackage.TABLE__ACCESS_SPECIFIER:
        setAccessSpecifier((AccessSpecifier)newValue);
        return;
      case ComponentPackage.TABLE__TABLE_NAME:
        setTableName((String)newValue);
        return;
      case ComponentPackage.TABLE__TYPE:
        setType((String)newValue);
        return;
      case ComponentPackage.TABLE__ATTRIBUTE:
        getAttribute().clear();
        getAttribute().addAll((Collection<? extends Attribute>)newValue);
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
      case ComponentPackage.TABLE__DOCUMENTATION:
        setDocumentation(DOCUMENTATION_EDEFAULT);
        return;
      case ComponentPackage.TABLE__ACCESS_SPECIFIER:
        setAccessSpecifier(ACCESS_SPECIFIER_EDEFAULT);
        return;
      case ComponentPackage.TABLE__TABLE_NAME:
        setTableName(TABLE_NAME_EDEFAULT);
        return;
      case ComponentPackage.TABLE__TYPE:
        setType(TYPE_EDEFAULT);
        return;
      case ComponentPackage.TABLE__ATTRIBUTE:
        getAttribute().clear();
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
      case ComponentPackage.TABLE__DOCUMENTATION:
        return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
      case ComponentPackage.TABLE__ACCESS_SPECIFIER:
        return accessSpecifier != ACCESS_SPECIFIER_EDEFAULT;
      case ComponentPackage.TABLE__TABLE_NAME:
        return TABLE_NAME_EDEFAULT == null ? tableName != null : !TABLE_NAME_EDEFAULT.equals(tableName);
      case ComponentPackage.TABLE__TYPE:
        return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
      case ComponentPackage.TABLE__ATTRIBUTE:
        return attribute != null && !attribute.isEmpty();
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
    result.append(", tableName: ");
    result.append(tableName);
    result.append(", type: ");
    result.append(type);
    result.append(')');
    return result.toString();
  }

} //TableImpl
