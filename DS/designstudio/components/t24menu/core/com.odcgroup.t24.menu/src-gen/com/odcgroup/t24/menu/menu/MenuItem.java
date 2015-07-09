/**
 */
package com.odcgroup.t24.menu.menu;

import com.odcgroup.edge.t24ui.CompositeScreen;

import com.odcgroup.mdf.metamodel.MdfClass;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;

import com.odcgroup.t24.version.versionDSL.Version;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#isDisplayTabs <em>Display Tabs</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getSecurityRole <em>Security Role</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getShortcut <em>Shortcut</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getLabels <em>Labels</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getVersion <em>Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getEnquiry <em>Enquiry</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getCompositeScreen <em>Composite Screen</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getIncludedMenu <em>Included Menu</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getApplication <em>Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuItem#getSubmenus <em>Submenus</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem()
 * @model
 * @generated
 */
public interface MenuItem extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Enabled</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.menu.menu.Enabled}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Enabled</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Enabled</em>' attribute.
   * @see com.odcgroup.t24.menu.menu.Enabled
   * @see #setEnabled(Enabled)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Enabled()
   * @model
   * @generated
   */
  Enabled getEnabled();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getEnabled <em>Enabled</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Enabled</em>' attribute.
   * @see com.odcgroup.t24.menu.menu.Enabled
   * @see #getEnabled()
   * @generated
   */
  void setEnabled(Enabled value);

  /**
   * Returns the value of the '<em><b>Display Tabs</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Display Tabs</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Display Tabs</em>' attribute.
   * @see #setDisplayTabs(boolean)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_DisplayTabs()
   * @model
   * @generated
   */
  boolean isDisplayTabs();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#isDisplayTabs <em>Display Tabs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Display Tabs</em>' attribute.
   * @see #isDisplayTabs()
   * @generated
   */
  void setDisplayTabs(boolean value);

  /**
   * Returns the value of the '<em><b>Security Role</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Security Role</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Security Role</em>' attribute.
   * @see #setSecurityRole(String)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_SecurityRole()
   * @model
   * @generated
   */
  String getSecurityRole();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getSecurityRole <em>Security Role</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Security Role</em>' attribute.
   * @see #getSecurityRole()
   * @generated
   */
  void setSecurityRole(String value);

  /**
   * Returns the value of the '<em><b>Shortcut</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Shortcut</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Shortcut</em>' attribute.
   * @see #setShortcut(String)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Shortcut()
   * @model
   * @generated
   */
  String getShortcut();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getShortcut <em>Shortcut</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Shortcut</em>' attribute.
   * @see #getShortcut()
   * @generated
   */
  void setShortcut(String value);

  /**
   * Returns the value of the '<em><b>Labels</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.menu.menu.Translation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Labels</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Labels</em>' containment reference list.
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Labels()
   * @model containment="true"
   * @generated
   */
  EList<Translation> getLabels();

  /**
   * Returns the value of the '<em><b>Version</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Version</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Version</em>' reference.
   * @see #setVersion(Version)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Version()
   * @model
   * @generated
   */
  Version getVersion();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getVersion <em>Version</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Version</em>' reference.
   * @see #getVersion()
   * @generated
   */
  void setVersion(Version value);

  /**
   * Returns the value of the '<em><b>Enquiry</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Enquiry</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Enquiry</em>' reference.
   * @see #setEnquiry(Enquiry)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Enquiry()
   * @model
   * @generated
   */
  Enquiry getEnquiry();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getEnquiry <em>Enquiry</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Enquiry</em>' reference.
   * @see #getEnquiry()
   * @generated
   */
  void setEnquiry(Enquiry value);

  /**
   * Returns the value of the '<em><b>Composite Screen</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Composite Screen</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Composite Screen</em>' reference.
   * @see #setCompositeScreen(CompositeScreen)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_CompositeScreen()
   * @model
   * @generated
   */
  CompositeScreen getCompositeScreen();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getCompositeScreen <em>Composite Screen</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Composite Screen</em>' reference.
   * @see #getCompositeScreen()
   * @generated
   */
  void setCompositeScreen(CompositeScreen value);

  /**
   * Returns the value of the '<em><b>Included Menu</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Included Menu</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Included Menu</em>' reference.
   * @see #setIncludedMenu(MenuRoot)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_IncludedMenu()
   * @model
   * @generated
   */
  MenuRoot getIncludedMenu();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getIncludedMenu <em>Included Menu</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Included Menu</em>' reference.
   * @see #getIncludedMenu()
   * @generated
   */
  void setIncludedMenu(MenuRoot value);

  /**
   * Returns the value of the '<em><b>Application</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Application</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Application</em>' reference.
   * @see #setApplication(MdfClass)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Application()
   * @model
   * @generated
   */
  MdfClass getApplication();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getApplication <em>Application</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Application</em>' reference.
   * @see #getApplication()
   * @generated
   */
  void setApplication(MdfClass value);

  /**
   * Returns the value of the '<em><b>Parameters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameters</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' attribute.
   * @see #setParameters(String)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Parameters()
   * @model
   * @generated
   */
  String getParameters();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuItem#getParameters <em>Parameters</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameters</em>' attribute.
   * @see #getParameters()
   * @generated
   */
  void setParameters(String value);

  /**
   * Returns the value of the '<em><b>Submenus</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.menu.menu.MenuItem}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Submenus</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Submenus</em>' containment reference list.
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuItem_Submenus()
   * @model containment="true"
   * @generated
   */
  EList<MenuItem> getSubmenus();

} // MenuItem
