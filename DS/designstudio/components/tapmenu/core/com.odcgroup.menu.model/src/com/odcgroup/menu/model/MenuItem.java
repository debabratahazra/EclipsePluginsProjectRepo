/**
 * <copyright>
 * </copyright>
 *

 */
package com.odcgroup.menu.model;

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
 *   <li>{@link com.odcgroup.menu.model.menu.MenuItem#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.MenuItem#getPageflow <em>Pageflow</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.MenuItem#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.MenuItem#isDisplayTabs <em>Display Tabs</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.MenuItem#getSecurityRole <em>Security Role</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.MenuItem#getLabels <em>Labels</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.MenuItem#getSubmenus <em>Submenus</em>}</li>
 *   <li>{@link com.odcgroup.menu.model.menu.MenuItem#getShortcut <em>Shortcut</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem()
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
	 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem_Name()
	 * @model
	 * @generated
	 */
  String getName();

  /**
	 * Sets the value of the '{@link com.odcgroup.menu.model.menu.MenuItem#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
  void setName(String value);

  /**
	 * Returns the value of the '<em><b>Pageflow</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pageflow</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Pageflow</em>' attribute.
	 * @see #setPageflow(String)
	 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem_Pageflow()
	 * @model
	 * @generated
	 */
  String getPageflow();

  /**
	 * Sets the value of the '{@link com.odcgroup.menu.model.menu.MenuItem#getPageflow <em>Pageflow</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pageflow</em>' attribute.
	 * @see #getPageflow()
	 * @generated
	 */
  void setPageflow(String value);

  /**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.menu.model.menu.Enabled}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Enabled</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see com.odcgroup.menu.model.menu.Enabled
	 * @see #setEnabled(Enabled)
	 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem_Enabled()
	 * @model
	 * @generated
	 */
  Enabled getEnabled();

  /**
	 * Sets the value of the '{@link com.odcgroup.menu.model.menu.MenuItem#getEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see com.odcgroup.menu.model.menu.Enabled
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
	 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem_DisplayTabs()
	 * @model
	 * @generated
	 */
  boolean isDisplayTabs();

  /**
	 * Sets the value of the '{@link com.odcgroup.menu.model.menu.MenuItem#isDisplayTabs <em>Display Tabs</em>}' attribute.
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
	 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem_SecurityRole()
	 * @model
	 * @generated
	 */
  String getSecurityRole();

  /**
	 * Sets the value of the '{@link com.odcgroup.menu.model.menu.MenuItem#getSecurityRole <em>Security Role</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Security Role</em>' attribute.
	 * @see #getSecurityRole()
	 * @generated
	 */
  void setSecurityRole(String value);

  /**
	 * Returns the value of the '<em><b>Labels</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.menu.model.menu.Translation}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Labels</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Labels</em>' containment reference list.
	 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem_Labels()
	 * @model containment="true"
	 * @generated
	 */
  EList<Translation> getLabels();

  /**
	 * Returns the value of the '<em><b>Submenus</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.menu.model.menu.MenuItem}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Submenus</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Submenus</em>' containment reference list.
	 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem_Submenus()
	 * @model containment="true"
	 * @generated
	 */
  EList<MenuItem> getSubmenus();

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
	 * @see com.odcgroup.menu.model.menu.MenuPackage#getMenuItem_Shortcut()
	 * @model
	 * @generated
	 */
  String getShortcut();

  /**
	 * Sets the value of the '{@link com.odcgroup.menu.model.menu.MenuItem#getShortcut <em>Shortcut</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shortcut</em>' attribute.
	 * @see #getShortcut()
	 * @generated
	 */
  void setShortcut(String value);

} // MenuItem
