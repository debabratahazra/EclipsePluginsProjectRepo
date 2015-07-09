/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.mdf.metamodel.MdfEntity;

import com.odcgroup.service.model.component.Argument;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.InOutType;
import com.odcgroup.service.model.component.Multiplicity;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Argument</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.impl.ArgumentImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ArgumentImpl#getInout <em>Inout</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ArgumentImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ArgumentImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.impl.ArgumentImpl#getMultiplicity <em>Multiplicity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArgumentImpl extends MinimalEObjectImpl.Container implements Argument
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
   * The default value of the '{@link #getInout() <em>Inout</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInout()
   * @generated
   * @ordered
   */
  protected static final InOutType INOUT_EDEFAULT = InOutType.IN;

  /**
   * The cached value of the '{@link #getInout() <em>Inout</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInout()
   * @generated
   * @ordered
   */
  protected InOutType inout = INOUT_EDEFAULT;

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
   * The cached value of the '{@link #getType() <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected MdfEntity type;

  /**
   * The default value of the '{@link #getMultiplicity() <em>Multiplicity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMultiplicity()
   * @generated
   * @ordered
   */
  protected static final Multiplicity MULTIPLICITY_EDEFAULT = Multiplicity.OPTIONAL;

  /**
   * The cached value of the '{@link #getMultiplicity() <em>Multiplicity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMultiplicity()
   * @generated
   * @ordered
   */
  protected Multiplicity multiplicity = MULTIPLICITY_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ArgumentImpl()
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
    return ComponentPackage.Literals.ARGUMENT;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ARGUMENT__DOCUMENTATION, oldDocumentation, documentation));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InOutType getInout()
  {
    return inout;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInout(InOutType newInout)
  {
    InOutType oldInout = inout;
    inout = newInout == null ? INOUT_EDEFAULT : newInout;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ARGUMENT__INOUT, oldInout, inout));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ARGUMENT__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MdfEntity getType()
  {
    if (type != null && ((EObject)type).eIsProxy())
    {
      InternalEObject oldType = (InternalEObject)type;
      type = (MdfEntity)eResolveProxy(oldType);
      if (type != oldType)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComponentPackage.ARGUMENT__TYPE, oldType, type));
      }
    }
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MdfEntity basicGetType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(MdfEntity newType)
  {
    MdfEntity oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ARGUMENT__TYPE, oldType, type));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Multiplicity getMultiplicity()
  {
    return multiplicity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMultiplicity(Multiplicity newMultiplicity)
  {
    Multiplicity oldMultiplicity = multiplicity;
    multiplicity = newMultiplicity == null ? MULTIPLICITY_EDEFAULT : newMultiplicity;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ARGUMENT__MULTIPLICITY, oldMultiplicity, multiplicity));
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
      case ComponentPackage.ARGUMENT__DOCUMENTATION:
        return getDocumentation();
      case ComponentPackage.ARGUMENT__INOUT:
        return getInout();
      case ComponentPackage.ARGUMENT__NAME:
        return getName();
      case ComponentPackage.ARGUMENT__TYPE:
        if (resolve) return getType();
        return basicGetType();
      case ComponentPackage.ARGUMENT__MULTIPLICITY:
        return getMultiplicity();
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
      case ComponentPackage.ARGUMENT__DOCUMENTATION:
        setDocumentation((String)newValue);
        return;
      case ComponentPackage.ARGUMENT__INOUT:
        setInout((InOutType)newValue);
        return;
      case ComponentPackage.ARGUMENT__NAME:
        setName((String)newValue);
        return;
      case ComponentPackage.ARGUMENT__TYPE:
        setType((MdfEntity)newValue);
        return;
      case ComponentPackage.ARGUMENT__MULTIPLICITY:
        setMultiplicity((Multiplicity)newValue);
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
      case ComponentPackage.ARGUMENT__DOCUMENTATION:
        setDocumentation(DOCUMENTATION_EDEFAULT);
        return;
      case ComponentPackage.ARGUMENT__INOUT:
        setInout(INOUT_EDEFAULT);
        return;
      case ComponentPackage.ARGUMENT__NAME:
        setName(NAME_EDEFAULT);
        return;
      case ComponentPackage.ARGUMENT__TYPE:
        setType((MdfEntity)null);
        return;
      case ComponentPackage.ARGUMENT__MULTIPLICITY:
        setMultiplicity(MULTIPLICITY_EDEFAULT);
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
      case ComponentPackage.ARGUMENT__DOCUMENTATION:
        return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
      case ComponentPackage.ARGUMENT__INOUT:
        return inout != INOUT_EDEFAULT;
      case ComponentPackage.ARGUMENT__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case ComponentPackage.ARGUMENT__TYPE:
        return type != null;
      case ComponentPackage.ARGUMENT__MULTIPLICITY:
        return multiplicity != MULTIPLICITY_EDEFAULT;
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
    result.append(", inout: ");
    result.append(inout);
    result.append(", name: ");
    result.append(name);
    result.append(", multiplicity: ");
    result.append(multiplicity);
    result.append(')');
    return result.toString();
  }

} //ArgumentImpl
