/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.Argument;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.Method;
import com.odcgroup.service.model.component.MethodAnnotation;

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
 * An implementation of the model object '<em><b>Method</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.impl.MethodImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.MethodImpl#getAccessSpecifier <em>Access Specifier</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.MethodImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.MethodImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.MethodImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.MethodImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MethodImpl extends MinimalEObjectImpl.Container implements Method
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
   * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnotations()
   * @generated
   * @ordered
   */
  protected EList<MethodAnnotation> annotations;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MethodImpl()
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
    return ComponentPackage.Literals.METHOD;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.METHOD__DOCUMENTATION, oldDocumentation, documentation));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.METHOD__ACCESS_SPECIFIER, oldAccessSpecifier, accessSpecifier));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.METHOD__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<MethodAnnotation> getAnnotations()
  {
    if (annotations == null)
    {
      annotations = new EObjectContainmentEList<MethodAnnotation>(MethodAnnotation.class, this, ComponentPackage.METHOD__ANNOTATIONS);
    }
    return annotations;
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
      arguments = new EObjectContainmentEList<Argument>(Argument.class, this, ComponentPackage.METHOD__ARGUMENTS);
    }
    return arguments;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.METHOD__TYPE, oldType, type));
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
      case ComponentPackage.METHOD__ANNOTATIONS:
        return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
      case ComponentPackage.METHOD__ARGUMENTS:
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
      case ComponentPackage.METHOD__DOCUMENTATION:
        return getDocumentation();
      case ComponentPackage.METHOD__ACCESS_SPECIFIER:
        return getAccessSpecifier();
      case ComponentPackage.METHOD__NAME:
        return getName();
      case ComponentPackage.METHOD__ANNOTATIONS:
        return getAnnotations();
      case ComponentPackage.METHOD__ARGUMENTS:
        return getArguments();
      case ComponentPackage.METHOD__TYPE:
        return getType();
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
      case ComponentPackage.METHOD__DOCUMENTATION:
        setDocumentation((String)newValue);
        return;
      case ComponentPackage.METHOD__ACCESS_SPECIFIER:
        setAccessSpecifier((AccessSpecifier)newValue);
        return;
      case ComponentPackage.METHOD__NAME:
        setName((String)newValue);
        return;
      case ComponentPackage.METHOD__ANNOTATIONS:
        getAnnotations().clear();
        getAnnotations().addAll((Collection<? extends MethodAnnotation>)newValue);
        return;
      case ComponentPackage.METHOD__ARGUMENTS:
        getArguments().clear();
        getArguments().addAll((Collection<? extends Argument>)newValue);
        return;
      case ComponentPackage.METHOD__TYPE:
        setType((String)newValue);
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
      case ComponentPackage.METHOD__DOCUMENTATION:
        setDocumentation(DOCUMENTATION_EDEFAULT);
        return;
      case ComponentPackage.METHOD__ACCESS_SPECIFIER:
        setAccessSpecifier(ACCESS_SPECIFIER_EDEFAULT);
        return;
      case ComponentPackage.METHOD__NAME:
        setName(NAME_EDEFAULT);
        return;
      case ComponentPackage.METHOD__ANNOTATIONS:
        getAnnotations().clear();
        return;
      case ComponentPackage.METHOD__ARGUMENTS:
        getArguments().clear();
        return;
      case ComponentPackage.METHOD__TYPE:
        setType(TYPE_EDEFAULT);
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
      case ComponentPackage.METHOD__DOCUMENTATION:
        return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
      case ComponentPackage.METHOD__ACCESS_SPECIFIER:
        return accessSpecifier != ACCESS_SPECIFIER_EDEFAULT;
      case ComponentPackage.METHOD__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case ComponentPackage.METHOD__ANNOTATIONS:
        return annotations != null && !annotations.isEmpty();
      case ComponentPackage.METHOD__ARGUMENTS:
        return arguments != null && !arguments.isEmpty();
      case ComponentPackage.METHOD__TYPE:
        return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
    result.append(", type: ");
    result.append(type);
    result.append(')');
    return result.toString();
  }

} //MethodImpl
