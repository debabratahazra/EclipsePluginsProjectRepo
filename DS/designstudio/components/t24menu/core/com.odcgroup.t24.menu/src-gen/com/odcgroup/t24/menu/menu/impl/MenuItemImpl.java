/**
 */
package com.odcgroup.t24.menu.menu.impl;

import com.odcgroup.edge.t24ui.CompositeScreen;

import com.odcgroup.mdf.metamodel.MdfClass;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;

import com.odcgroup.t24.menu.menu.Enabled;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuPackage;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.Translation;

import com.odcgroup.t24.version.versionDSL.Version;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#isDisplayTabs <em>Display Tabs</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getSecurityRole <em>Security Role</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getShortcut <em>Shortcut</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getLabels <em>Labels</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getEnquiry <em>Enquiry</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getCompositeScreen <em>Composite Screen</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getIncludedMenu <em>Included Menu</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getApplication <em>Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl#getSubmenus <em>Submenus</em>}</li>
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
   * The cached value of the '{@link #getLabels() <em>Labels</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabels()
   * @generated
   * @ordered
   */
  protected EList<Translation> labels;

  /**
   * The cached value of the '{@link #getVersion() <em>Version</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVersion()
   * @generated
   * @ordered
   */
  protected Version version;

  /**
   * The cached value of the '{@link #getEnquiry() <em>Enquiry</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnquiry()
   * @generated
   * @ordered
   */
  protected Enquiry enquiry;

  /**
   * The cached value of the '{@link #getCompositeScreen() <em>Composite Screen</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompositeScreen()
   * @generated
   * @ordered
   */
  protected CompositeScreen compositeScreen;

  /**
   * The cached value of the '{@link #getIncludedMenu() <em>Included Menu</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIncludedMenu()
   * @generated
   * @ordered
   */
  protected MenuRoot includedMenu;

  /**
   * The cached value of the '{@link #getApplication() <em>Application</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getApplication()
   * @generated
   * @ordered
   */
  protected MdfClass application;

  /**
   * The default value of the '{@link #getParameters() <em>Parameters</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected static final String PARAMETERS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected String parameters = PARAMETERS_EDEFAULT;

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
  public EList<Translation> getLabels()
  {
    if (labels == null)
    {
      labels = new EObjectContainmentEList<Translation>(Translation.class, this, MenuPackage.MENU_ITEM__LABELS);
    }
    return labels;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version getVersion()
  {
    if (version != null && version.eIsProxy())
    {
      InternalEObject oldVersion = (InternalEObject)version;
      version = (Version)eResolveProxy(oldVersion);
      if (version != oldVersion)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MenuPackage.MENU_ITEM__VERSION, oldVersion, version));
      }
    }
    return version;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version basicGetVersion()
  {
    return version;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVersion(Version newVersion)
  {
    Version oldVersion = version;
    version = newVersion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__VERSION, oldVersion, version));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Enquiry getEnquiry()
  {
    if (enquiry != null && enquiry.eIsProxy())
    {
      InternalEObject oldEnquiry = (InternalEObject)enquiry;
      enquiry = (Enquiry)eResolveProxy(oldEnquiry);
      if (enquiry != oldEnquiry)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MenuPackage.MENU_ITEM__ENQUIRY, oldEnquiry, enquiry));
      }
    }
    return enquiry;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Enquiry basicGetEnquiry()
  {
    return enquiry;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnquiry(Enquiry newEnquiry)
  {
    Enquiry oldEnquiry = enquiry;
    enquiry = newEnquiry;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__ENQUIRY, oldEnquiry, enquiry));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompositeScreen getCompositeScreen()
  {
    if (compositeScreen != null && compositeScreen.eIsProxy())
    {
      InternalEObject oldCompositeScreen = (InternalEObject)compositeScreen;
      compositeScreen = (CompositeScreen)eResolveProxy(oldCompositeScreen);
      if (compositeScreen != oldCompositeScreen)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MenuPackage.MENU_ITEM__COMPOSITE_SCREEN, oldCompositeScreen, compositeScreen));
      }
    }
    return compositeScreen;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompositeScreen basicGetCompositeScreen()
  {
    return compositeScreen;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCompositeScreen(CompositeScreen newCompositeScreen)
  {
    CompositeScreen oldCompositeScreen = compositeScreen;
    compositeScreen = newCompositeScreen;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__COMPOSITE_SCREEN, oldCompositeScreen, compositeScreen));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MenuRoot getIncludedMenu()
  {
    if (includedMenu != null && includedMenu.eIsProxy())
    {
      InternalEObject oldIncludedMenu = (InternalEObject)includedMenu;
      includedMenu = (MenuRoot)eResolveProxy(oldIncludedMenu);
      if (includedMenu != oldIncludedMenu)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MenuPackage.MENU_ITEM__INCLUDED_MENU, oldIncludedMenu, includedMenu));
      }
    }
    return includedMenu;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MenuRoot basicGetIncludedMenu()
  {
    return includedMenu;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIncludedMenu(MenuRoot newIncludedMenu)
  {
    MenuRoot oldIncludedMenu = includedMenu;
    includedMenu = newIncludedMenu;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__INCLUDED_MENU, oldIncludedMenu, includedMenu));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MdfClass getApplication()
  {
    if (application != null && ((EObject)application).eIsProxy())
    {
      InternalEObject oldApplication = (InternalEObject)application;
      application = (MdfClass)eResolveProxy(oldApplication);
      if (application != oldApplication)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MenuPackage.MENU_ITEM__APPLICATION, oldApplication, application));
      }
    }
    return application;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MdfClass basicGetApplication()
  {
    return application;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setApplication(MdfClass newApplication)
  {
    MdfClass oldApplication = application;
    application = newApplication;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__APPLICATION, oldApplication, application));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getParameters()
  {
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setParameters(String newParameters)
  {
    String oldParameters = parameters;
    parameters = newParameters;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__PARAMETERS, oldParameters, parameters));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<MenuItem> getSubmenus()
  {
    if (submenus == null)
    {
      submenus = new EObjectContainmentEList<MenuItem>(MenuItem.class, this, MenuPackage.MENU_ITEM__SUBMENUS);
    }
    return submenus;
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
    switch (featureID)
    {
      case MenuPackage.MENU_ITEM__NAME:
        return getName();
      case MenuPackage.MENU_ITEM__ENABLED:
        return getEnabled();
      case MenuPackage.MENU_ITEM__DISPLAY_TABS:
        return isDisplayTabs();
      case MenuPackage.MENU_ITEM__SECURITY_ROLE:
        return getSecurityRole();
      case MenuPackage.MENU_ITEM__SHORTCUT:
        return getShortcut();
      case MenuPackage.MENU_ITEM__LABELS:
        return getLabels();
      case MenuPackage.MENU_ITEM__VERSION:
        if (resolve) return getVersion();
        return basicGetVersion();
      case MenuPackage.MENU_ITEM__ENQUIRY:
        if (resolve) return getEnquiry();
        return basicGetEnquiry();
      case MenuPackage.MENU_ITEM__COMPOSITE_SCREEN:
        if (resolve) return getCompositeScreen();
        return basicGetCompositeScreen();
      case MenuPackage.MENU_ITEM__INCLUDED_MENU:
        if (resolve) return getIncludedMenu();
        return basicGetIncludedMenu();
      case MenuPackage.MENU_ITEM__APPLICATION:
        if (resolve) return getApplication();
        return basicGetApplication();
      case MenuPackage.MENU_ITEM__PARAMETERS:
        return getParameters();
      case MenuPackage.MENU_ITEM__SUBMENUS:
        return getSubmenus();
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
      case MenuPackage.MENU_ITEM__NAME:
        setName((String)newValue);
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
      case MenuPackage.MENU_ITEM__SHORTCUT:
        setShortcut((String)newValue);
        return;
      case MenuPackage.MENU_ITEM__LABELS:
        getLabels().clear();
        getLabels().addAll((Collection<? extends Translation>)newValue);
        return;
      case MenuPackage.MENU_ITEM__VERSION:
        setVersion((Version)newValue);
        return;
      case MenuPackage.MENU_ITEM__ENQUIRY:
        setEnquiry((Enquiry)newValue);
        return;
      case MenuPackage.MENU_ITEM__COMPOSITE_SCREEN:
        setCompositeScreen((CompositeScreen)newValue);
        return;
      case MenuPackage.MENU_ITEM__INCLUDED_MENU:
        setIncludedMenu((MenuRoot)newValue);
        return;
      case MenuPackage.MENU_ITEM__APPLICATION:
        setApplication((MdfClass)newValue);
        return;
      case MenuPackage.MENU_ITEM__PARAMETERS:
        setParameters((String)newValue);
        return;
      case MenuPackage.MENU_ITEM__SUBMENUS:
        getSubmenus().clear();
        getSubmenus().addAll((Collection<? extends MenuItem>)newValue);
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
      case MenuPackage.MENU_ITEM__NAME:
        setName(NAME_EDEFAULT);
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
      case MenuPackage.MENU_ITEM__SHORTCUT:
        setShortcut(SHORTCUT_EDEFAULT);
        return;
      case MenuPackage.MENU_ITEM__LABELS:
        getLabels().clear();
        return;
      case MenuPackage.MENU_ITEM__VERSION:
        setVersion((Version)null);
        return;
      case MenuPackage.MENU_ITEM__ENQUIRY:
        setEnquiry((Enquiry)null);
        return;
      case MenuPackage.MENU_ITEM__COMPOSITE_SCREEN:
        setCompositeScreen((CompositeScreen)null);
        return;
      case MenuPackage.MENU_ITEM__INCLUDED_MENU:
        setIncludedMenu((MenuRoot)null);
        return;
      case MenuPackage.MENU_ITEM__APPLICATION:
        setApplication((MdfClass)null);
        return;
      case MenuPackage.MENU_ITEM__PARAMETERS:
        setParameters(PARAMETERS_EDEFAULT);
        return;
      case MenuPackage.MENU_ITEM__SUBMENUS:
        getSubmenus().clear();
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
      case MenuPackage.MENU_ITEM__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case MenuPackage.MENU_ITEM__ENABLED:
        return enabled != ENABLED_EDEFAULT;
      case MenuPackage.MENU_ITEM__DISPLAY_TABS:
        return displayTabs != DISPLAY_TABS_EDEFAULT;
      case MenuPackage.MENU_ITEM__SECURITY_ROLE:
        return SECURITY_ROLE_EDEFAULT == null ? securityRole != null : !SECURITY_ROLE_EDEFAULT.equals(securityRole);
      case MenuPackage.MENU_ITEM__SHORTCUT:
        return SHORTCUT_EDEFAULT == null ? shortcut != null : !SHORTCUT_EDEFAULT.equals(shortcut);
      case MenuPackage.MENU_ITEM__LABELS:
        return labels != null && !labels.isEmpty();
      case MenuPackage.MENU_ITEM__VERSION:
        return version != null;
      case MenuPackage.MENU_ITEM__ENQUIRY:
        return enquiry != null;
      case MenuPackage.MENU_ITEM__COMPOSITE_SCREEN:
        return compositeScreen != null;
      case MenuPackage.MENU_ITEM__INCLUDED_MENU:
        return includedMenu != null;
      case MenuPackage.MENU_ITEM__APPLICATION:
        return application != null;
      case MenuPackage.MENU_ITEM__PARAMETERS:
        return PARAMETERS_EDEFAULT == null ? parameters != null : !PARAMETERS_EDEFAULT.equals(parameters);
      case MenuPackage.MENU_ITEM__SUBMENUS:
        return submenus != null && !submenus.isEmpty();
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
    result.append(", enabled: ");
    result.append(enabled);
    result.append(", displayTabs: ");
    result.append(displayTabs);
    result.append(", securityRole: ");
    result.append(securityRole);
    result.append(", shortcut: ");
    result.append(shortcut);
    result.append(", parameters: ");
    result.append(parameters);
    result.append(')');
    return result.toString();
  }

} //MenuItemImpl
