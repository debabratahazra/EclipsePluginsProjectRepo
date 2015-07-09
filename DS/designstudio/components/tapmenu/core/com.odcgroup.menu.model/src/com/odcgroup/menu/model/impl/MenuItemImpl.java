/**
 * <copyright>
 * </copyright>
 *

 */
package com.odcgroup.menu.model.impl;

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

import com.odcgroup.menu.model.Enabled;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.menu.model.Translation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuItemImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuItemImpl#getPageflow <em>Pageflow</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuItemImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuItemImpl#isDisplayTabs <em>Display Tabs</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuItemImpl#getSecurityRole <em>Security Role</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuItemImpl#getLabels <em>Labels</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuItemImpl#getSubmenus <em>Submenus</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.impl.MenuItemImpl#getShortcut <em>Shortcut</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MenuItemImpl extends MinimalEObjectImpl.Container implements MenuItem
{
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
	 * The default value of the '{@link #getPageflow() <em>Pageflow</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getPageflow()
	 * @generated
	 * @ordered
	 */
  protected static final String PAGEFLOW_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getPageflow() <em>Pageflow</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getPageflow()
	 * @generated
	 * @ordered
	 */
  protected String pageflow = PAGEFLOW_EDEFAULT;

  /**
	 * The default value of the '{@link #getEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getEnabled()
	 * @generated
	 * @ordered
	 */
  protected static final Enabled ENABLED_EDEFAULT = Enabled.TRUE;

  /**
	 * The cached value of the '{@link #getEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getEnabled()
	 * @generated
	 * @ordered
	 */
  protected Enabled enabled = ENABLED_EDEFAULT;

  /**
	 * The default value of the '{@link #isDisplayTabs() <em>Display Tabs</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isDisplayTabs()
	 * @generated
	 * @ordered
	 */
  protected static final boolean DISPLAY_TABS_EDEFAULT = false;

  /**
	 * The cached value of the '{@link #isDisplayTabs() <em>Display Tabs</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isDisplayTabs()
	 * @generated
	 * @ordered
	 */
  protected boolean displayTabs = DISPLAY_TABS_EDEFAULT;

  /**
	 * The default value of the '{@link #getSecurityRole() <em>Security Role</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSecurityRole()
	 * @generated
	 * @ordered
	 */
  protected static final String SECURITY_ROLE_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getSecurityRole() <em>Security Role</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSecurityRole()
	 * @generated
	 * @ordered
	 */
  protected String securityRole = SECURITY_ROLE_EDEFAULT;

  /**
	 * The cached value of the '{@link #getLabels() <em>Labels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getLabels()
	 * @generated
	 * @ordered
	 */
  protected EList<Translation> labels;

  /**
	 * The cached value of the '{@link #getSubmenus() <em>Submenus</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSubmenus()
	 * @generated
	 * @ordered
	 */
  protected EList<MenuItem> submenus;

  /**
	 * The default value of the '{@link #getShortcut() <em>Shortcut</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getShortcut()
	 * @generated
	 * @ordered
	 */
  protected static final String SHORTCUT_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getShortcut() <em>Shortcut</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getShortcut()
	 * @generated
	 * @ordered
	 */
  protected String shortcut = SHORTCUT_EDEFAULT;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected MenuItemImpl()
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
		return MenuPackage.Literals.MENU_ITEM;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__NAME, oldName, name));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getPageflow()
  {
		return pageflow;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setPageflow(String newPageflow)
  {
		String oldPageflow = pageflow;
		pageflow = newPageflow;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__PAGEFLOW, oldPageflow, pageflow));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Enabled getEnabled()
  {
		return enabled;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setEnabled(Enabled newEnabled)
  {
		Enabled oldEnabled = enabled;
		enabled = newEnabled == null ? ENABLED_EDEFAULT : newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__ENABLED, oldEnabled, enabled));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public boolean isDisplayTabs()
  {
		return displayTabs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setDisplayTabs(boolean newDisplayTabs)
  {
		boolean oldDisplayTabs = displayTabs;
		displayTabs = newDisplayTabs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__DISPLAY_TABS, oldDisplayTabs, displayTabs));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getSecurityRole()
  {
		return securityRole;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setSecurityRole(String newSecurityRole)
  {
		String oldSecurityRole = securityRole;
		securityRole = newSecurityRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__SECURITY_ROLE, oldSecurityRole, securityRole));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<Translation> getLabels()
  {
		if (labels == null) {
			labels = new EObjectContainmentEList<Translation>(Translation.class, this, MenuPackage.MENU_ITEM__LABELS);
		}
		return labels;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<MenuItem> getSubmenus()
  {
		if (submenus == null) {
			submenus = new EObjectContainmentEList<MenuItem>(MenuItem.class, this, MenuPackage.MENU_ITEM__SUBMENUS);
		}
		return submenus;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getShortcut()
  {
		return shortcut;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setShortcut(String newShortcut)
  {
		String oldShortcut = shortcut;
		shortcut = newShortcut;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__SHORTCUT, oldShortcut, shortcut));
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
			case MenuPackage.MENU_ITEM__LABELS:
				return ((InternalEList<?>)getLabels()).basicRemove(otherEnd, msgs);
			case MenuPackage.MENU_ITEM__SUBMENUS:
				return ((InternalEList<?>)getSubmenus()).basicRemove(otherEnd, msgs);
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
			case MenuPackage.MENU_ITEM__NAME:
				return getName();
			case MenuPackage.MENU_ITEM__PAGEFLOW:
				return getPageflow();
			case MenuPackage.MENU_ITEM__ENABLED:
				return getEnabled();
			case MenuPackage.MENU_ITEM__DISPLAY_TABS:
				return isDisplayTabs();
			case MenuPackage.MENU_ITEM__SECURITY_ROLE:
				return getSecurityRole();
			case MenuPackage.MENU_ITEM__LABELS:
				return getLabels();
			case MenuPackage.MENU_ITEM__SUBMENUS:
				return getSubmenus();
			case MenuPackage.MENU_ITEM__SHORTCUT:
				return getShortcut();
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
		switch (featureID) {
			case MenuPackage.MENU_ITEM__NAME:
				setName((String)newValue);
				return;
			case MenuPackage.MENU_ITEM__PAGEFLOW:
				setPageflow((String)newValue);
				return;
			case MenuPackage.MENU_ITEM__ENABLED:
				setEnabled((Enabled)newValue);
				return;
			case MenuPackage.MENU_ITEM__DISPLAY_TABS:
				setDisplayTabs((Boolean)newValue);
				return;
			case MenuPackage.MENU_ITEM__SECURITY_ROLE:
				setSecurityRole((String)newValue);
				return;
			case MenuPackage.MENU_ITEM__LABELS:
				getLabels().clear();
				getLabels().addAll((Collection<? extends Translation>)newValue);
				return;
			case MenuPackage.MENU_ITEM__SUBMENUS:
				getSubmenus().clear();
				getSubmenus().addAll((Collection<? extends MenuItem>)newValue);
				return;
			case MenuPackage.MENU_ITEM__SHORTCUT:
				setShortcut((String)newValue);
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
			case MenuPackage.MENU_ITEM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__PAGEFLOW:
				setPageflow(PAGEFLOW_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__DISPLAY_TABS:
				setDisplayTabs(DISPLAY_TABS_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__SECURITY_ROLE:
				setSecurityRole(SECURITY_ROLE_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__LABELS:
				getLabels().clear();
				return;
			case MenuPackage.MENU_ITEM__SUBMENUS:
				getSubmenus().clear();
				return;
			case MenuPackage.MENU_ITEM__SHORTCUT:
				setShortcut(SHORTCUT_EDEFAULT);
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
			case MenuPackage.MENU_ITEM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MenuPackage.MENU_ITEM__PAGEFLOW:
				return PAGEFLOW_EDEFAULT == null ? pageflow != null : !PAGEFLOW_EDEFAULT.equals(pageflow);
			case MenuPackage.MENU_ITEM__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case MenuPackage.MENU_ITEM__DISPLAY_TABS:
				return displayTabs != DISPLAY_TABS_EDEFAULT;
			case MenuPackage.MENU_ITEM__SECURITY_ROLE:
				return SECURITY_ROLE_EDEFAULT == null ? securityRole != null : !SECURITY_ROLE_EDEFAULT.equals(securityRole);
			case MenuPackage.MENU_ITEM__LABELS:
				return labels != null && !labels.isEmpty();
			case MenuPackage.MENU_ITEM__SUBMENUS:
				return submenus != null && !submenus.isEmpty();
			case MenuPackage.MENU_ITEM__SHORTCUT:
				return SHORTCUT_EDEFAULT == null ? shortcut != null : !SHORTCUT_EDEFAULT.equals(shortcut);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", pageflow: ");
		result.append(pageflow);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(", displayTabs: ");
		result.append(displayTabs);
		result.append(", securityRole: ");
		result.append(securityRole);
		result.append(", shortcut: ");
		result.append(shortcut);
		result.append(')');
		return result.toString();
	}


} //MenuItemImpl
