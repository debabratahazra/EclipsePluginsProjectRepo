/**
 * <copyright>
 * </copyright>
 *

 */
package com.odcgroup.menu.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.menu.model.MenuRoot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuRootImpl#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuRootImpl#getRootItem <em>Root Item</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MenuRootImpl extends MinimalEObjectImpl.Container implements MenuRoot
{
  /**
	 * The default value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()
	 * @generated
	 * @ordered
	 */
  protected static final String METAMODEL_VERSION_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()
	 * @generated
	 * @ordered
	 */
  protected String metamodelVersion = METAMODEL_VERSION_EDEFAULT;

  /**
	 * The cached value of the '{@link #getRootItem() <em>Root Item</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getRootItem()
	 * @generated
	 * @ordered
	 */
  protected MenuItem rootItem;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected MenuRootImpl()
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
		return MenuPackage.Literals.MENU_ROOT;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getMetamodelVersion()
  {
		return metamodelVersion;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setMetamodelVersion(String newMetamodelVersion)
  {
		String oldMetamodelVersion = metamodelVersion;
		metamodelVersion = newMetamodelVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ROOT__METAMODEL_VERSION, oldMetamodelVersion, metamodelVersion));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public MenuItem getRootItem()
  {
		return rootItem;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetRootItem(MenuItem newRootItem, NotificationChain msgs)
  {
		MenuItem oldRootItem = rootItem;
		rootItem = newRootItem;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ROOT__ROOT_ITEM, oldRootItem, newRootItem);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setRootItem(MenuItem newRootItem)
  {
		if (newRootItem != rootItem) {
			NotificationChain msgs = null;
			if (rootItem != null)
				msgs = ((InternalEObject)rootItem).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MenuPackage.MENU_ROOT__ROOT_ITEM, null, msgs);
			if (newRootItem != null)
				msgs = ((InternalEObject)newRootItem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MenuPackage.MENU_ROOT__ROOT_ITEM, null, msgs);
			msgs = basicSetRootItem(newRootItem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ROOT__ROOT_ITEM, newRootItem, newRootItem));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
		switch (featureID) {
			case MenuPackage.MENU_ROOT__ROOT_ITEM:
				return basicSetRootItem(null, msgs);
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
		switch (featureID) {
			case MenuPackage.MENU_ROOT__METAMODEL_VERSION:
				return getMetamodelVersion();
			case MenuPackage.MENU_ROOT__ROOT_ITEM:
				return getRootItem();
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
		switch (featureID) {
			case MenuPackage.MENU_ROOT__METAMODEL_VERSION:
				setMetamodelVersion((String)newValue);
				return;
			case MenuPackage.MENU_ROOT__ROOT_ITEM:
				setRootItem((MenuItem)newValue);
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
		switch (featureID) {
			case MenuPackage.MENU_ROOT__METAMODEL_VERSION:
				setMetamodelVersion(METAMODEL_VERSION_EDEFAULT);
				return;
			case MenuPackage.MENU_ROOT__ROOT_ITEM:
				setRootItem((MenuItem)null);
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
		switch (featureID) {
			case MenuPackage.MENU_ROOT__METAMODEL_VERSION:
				return METAMODEL_VERSION_EDEFAULT == null ? metamodelVersion != null : !METAMODEL_VERSION_EDEFAULT.equals(metamodelVersion);
			case MenuPackage.MENU_ROOT__ROOT_ITEM:
				return rootItem != null;
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
		result.append(" (metamodelVersion: ");
		result.append(metamodelVersion);
		result.append(')');
		return result.toString();
	}



} //MenuRootImpl
