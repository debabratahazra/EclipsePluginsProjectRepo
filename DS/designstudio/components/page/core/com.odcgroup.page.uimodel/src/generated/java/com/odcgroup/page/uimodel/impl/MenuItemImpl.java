/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.odcgroup.page.metamodel.impl.NamedTypeImpl;
import com.odcgroup.page.uimodel.ActionGroup;
import com.odcgroup.page.uimodel.MenuItem;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Menu Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.MenuItemImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.MenuItemImpl#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MenuItemImpl extends NamedTypeImpl implements MenuItem {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected MenuItem parent;

	/**
	 * The cached value of the '{@link #getGroups() <em>Groups</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<ActionGroup> groups;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MenuItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.MENU_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return MenuItem
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuItem getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (MenuItem)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.MENU_ITEM__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return MenuItem
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuItem basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newParent
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(MenuItem newParent) {
		MenuItem oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.MENU_ITEM__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return List
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ActionGroup> getGroups() {
		if (groups == null) {
			groups = new EObjectResolvingEList<ActionGroup>(ActionGroup.class, this, UIModelPackage.MENU_ITEM__GROUPS);
		}
		return groups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UIModelPackage.MENU_ITEM__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case UIModelPackage.MENU_ITEM__GROUPS:
				return getGroups();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case UIModelPackage.MENU_ITEM__PARENT:
				setParent((MenuItem)newValue);
				return;
			case UIModelPackage.MENU_ITEM__GROUPS:
				getGroups().clear();
				getGroups().addAll((Collection<? extends ActionGroup>)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case UIModelPackage.MENU_ITEM__PARENT:
				setParent((MenuItem)null);
				return;
			case UIModelPackage.MENU_ITEM__GROUPS:
				getGroups().clear();
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case UIModelPackage.MENU_ITEM__PARENT:
				return parent != null;
			case UIModelPackage.MENU_ITEM__GROUPS:
				return groups != null && !groups.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MenuItemImpl
