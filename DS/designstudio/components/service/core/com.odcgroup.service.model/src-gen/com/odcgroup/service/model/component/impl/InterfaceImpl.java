/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.Argument;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.Interface;

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
 * An implementation of the model object '<em><b>Interface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.impl.InterfaceImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.InterfaceImpl#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.InterfaceImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.InterfaceImpl#getArguments <em>Arguments</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InterfaceImpl extends MinimalEObjectImpl.Container implements Interface
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
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArguments()
   * @generated
   * @ordered
   */
  protected EList<Argument> arguments;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InterfaceImpl()
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
    return ComponentPackage.Literals.INTERFACE;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.INTERFACE__DOCUMENTATION, oldDocumentation, documentation));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.INTERFACE__ACCESS_SPECIFIER, oldAccessSpecifier, accessSpecifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.INTERFACE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Argument> getArguments()
  {
    if (arguments == null)
    {
      arguments = new EObjectContainmentEList<Argument>(Argument.class, this, ComponentPackage.INTERFACE__ARGUMENTS);
    }
    return arguments;
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
      case ComponentPackage.INTERFACE__ARGUMENTS:
        return ((InternalEList<?>)getArguments()).basicRemove(otherEnd, msgs);
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
      case ComponentPackage.INTERFACE__DOCUMENTATION:
        return getDocumentation();
      case ComponentPackage.INTERFACE__ACCESS_SPECIFIER:
        return getAccessSpecifier();
      case ComponentPackage.INTERFACE__NAME:
        return getName();
      case ComponentPackage.INTERFACE__ARGUMENTS:
        return getArguments();
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
      case ComponentPackage.INTERFACE__DOCUMENTATION:
        setDocumentation((String)newValue);
        return;
      case ComponentPackage.INTERFACE__ACCESS_SPECIFIER:
        setAccessSpecifier((AccessSpecifier)newValue);
        return;
      case ComponentPackage.INTERFACE__NAME:
        setName((String)newValue);
        return;
      case ComponentPackage.INTERFACE__ARGUMENTS:
        getArguments().clear();
        getArguments().addAll((Collection<? extends Argument>)newValue);
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
      case ComponentPackage.INTERFACE__DOCUMENTATION:
        setDocumentation(DOCUMENTATION_EDEFAULT);
        return;
      case ComponentPackage.INTERFACE__ACCESS_SPECIFIER:
        setAccessSpecifier(ACCESS_SPECIFIER_EDEFAULT);
        return;
      case ComponentPackage.INTERFACE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case ComponentPackage.INTERFACE__ARGUMENTS:
        getArguments().clear();
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
      case ComponentPackage.INTERFACE__DOCUMENTATION:
        return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
      case ComponentPackage.INTERFACE__ACCESS_SPECIFIER:
        return accessSpecifier != ACCESS_SPECIFIER_EDEFAULT;
      case ComponentPackage.INTERFACE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case ComponentPackage.INTERFACE__ARGUMENTS:
        return arguments != null && !arguments.isEmpty();
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
    result.append(", name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //InterfaceImpl
