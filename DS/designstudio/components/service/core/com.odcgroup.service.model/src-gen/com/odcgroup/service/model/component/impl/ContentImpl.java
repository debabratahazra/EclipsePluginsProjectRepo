/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.Constant;
import com.odcgroup.service.model.component.Content;
import com.odcgroup.service.model.component.Interface;
import com.odcgroup.service.model.component.Method;
import com.odcgroup.service.model.component.Property;
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
 * An implementation of the model object '<em><b>Content</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.impl.ContentImpl#getInterface <em>Interface</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ContentImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ContentImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ContentImpl#getConstant <em>Constant</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ContentImpl#getTable <em>Table</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContentImpl extends MinimalEObjectImpl.Container implements Content
{
  /**
   * The cached value of the '{@link #getInterface() <em>Interface</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterface()
   * @generated
   * @ordered
   */
  protected Interface interface_;

  /**
   * The cached value of the '{@link #getMethod() <em>Method</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMethod()
   * @generated
   * @ordered
   */
  protected EList<Method> method;

  /**
   * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProperty()
   * @generated
   * @ordered
   */
  protected EList<Property> property;

  /**
   * The cached value of the '{@link #getConstant() <em>Constant</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstant()
   * @generated
   * @ordered
   */
  protected EList<Constant> constant;

  /**
   * The cached value of the '{@link #getTable() <em>Table</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTable()
   * @generated
   * @ordered
   */
  protected EList<Table> table;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ContentImpl()
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
    return ComponentPackage.Literals.CONTENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Interface getInterface()
  {
    return interface_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInterface(Interface newInterface, NotificationChain msgs)
  {
    Interface oldInterface = interface_;
    interface_ = newInterface;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.CONTENT__INTERFACE, oldInterface, newInterface);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInterface(Interface newInterface)
  {
    if (newInterface != interface_)
    {
      NotificationChain msgs = null;
      if (interface_ != null)
        msgs = ((InternalEObject)interface_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.CONTENT__INTERFACE, null, msgs);
      if (newInterface != null)
        msgs = ((InternalEObject)newInterface).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.CONTENT__INTERFACE, null, msgs);
      msgs = basicSetInterface(newInterface, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONTENT__INTERFACE, newInterface, newInterface));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Method> getMethod()
  {
    if (method == null)
    {
      method = new EObjectContainmentEList<Method>(Method.class, this, ComponentPackage.CONTENT__METHOD);
    }
    return method;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Property> getProperty()
  {
    if (property == null)
    {
      property = new EObjectContainmentEList<Property>(Property.class, this, ComponentPackage.CONTENT__PROPERTY);
    }
    return property;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Constant> getConstant()
  {
    if (constant == null)
    {
      constant = new EObjectContainmentEList<Constant>(Constant.class, this, ComponentPackage.CONTENT__CONSTANT);
    }
    return constant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Table> getTable()
  {
    if (table == null)
    {
      table = new EObjectContainmentEList<Table>(Table.class, this, ComponentPackage.CONTENT__TABLE);
    }
    return table;
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
      case ComponentPackage.CONTENT__INTERFACE:
        return basicSetInterface(null, msgs);
      case ComponentPackage.CONTENT__METHOD:
        return ((InternalEList<?>)getMethod()).basicRemove(otherEnd, msgs);
      case ComponentPackage.CONTENT__PROPERTY:
        return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
      case ComponentPackage.CONTENT__CONSTANT:
        return ((InternalEList<?>)getConstant()).basicRemove(otherEnd, msgs);
      case ComponentPackage.CONTENT__TABLE:
        return ((InternalEList<?>)getTable()).basicRemove(otherEnd, msgs);
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
      case ComponentPackage.CONTENT__INTERFACE:
        return getInterface();
      case ComponentPackage.CONTENT__METHOD:
        return getMethod();
      case ComponentPackage.CONTENT__PROPERTY:
        return getProperty();
      case ComponentPackage.CONTENT__CONSTANT:
        return getConstant();
      case ComponentPackage.CONTENT__TABLE:
        return getTable();
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
      case ComponentPackage.CONTENT__INTERFACE:
        setInterface((Interface)newValue);
        return;
      case ComponentPackage.CONTENT__METHOD:
        getMethod().clear();
        getMethod().addAll((Collection<? extends Method>)newValue);
        return;
      case ComponentPackage.CONTENT__PROPERTY:
        getProperty().clear();
        getProperty().addAll((Collection<? extends Property>)newValue);
        return;
      case ComponentPackage.CONTENT__CONSTANT:
        getConstant().clear();
        getConstant().addAll((Collection<? extends Constant>)newValue);
        return;
      case ComponentPackage.CONTENT__TABLE:
        getTable().clear();
        getTable().addAll((Collection<? extends Table>)newValue);
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
      case ComponentPackage.CONTENT__INTERFACE:
        setInterface((Interface)null);
        return;
      case ComponentPackage.CONTENT__METHOD:
        getMethod().clear();
        return;
      case ComponentPackage.CONTENT__PROPERTY:
        getProperty().clear();
        return;
      case ComponentPackage.CONTENT__CONSTANT:
        getConstant().clear();
        return;
      case ComponentPackage.CONTENT__TABLE:
        getTable().clear();
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
      case ComponentPackage.CONTENT__INTERFACE:
        return interface_ != null;
      case ComponentPackage.CONTENT__METHOD:
        return method != null && !method.isEmpty();
      case ComponentPackage.CONTENT__PROPERTY:
        return property != null && !property.isEmpty();
      case ComponentPackage.CONTENT__CONSTANT:
        return constant != null && !constant.isEmpty();
      case ComponentPackage.CONTENT__TABLE:
        return table != null && !table.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ContentImpl
