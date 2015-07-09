/**
 */
package com.odcgroup.t24.menu.menu;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.menu.menu.MenuFactory
 * @model kind="package"
 * @generated
 */
public interface MenuPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "menu";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.odcgroup.com/t24/menu/Menu";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "menu";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MenuPackage eINSTANCE = com.odcgroup.t24.menu.menu.impl.MenuPackageImpl.init();

  /**
   * The meta object id for the '{@link com.odcgroup.t24.menu.menu.impl.MenuRootImpl <em>Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.menu.menu.impl.MenuRootImpl
   * @see com.odcgroup.t24.menu.menu.impl.MenuPackageImpl#getMenuRoot()
   * @generated
   */
  int MENU_ROOT = 0;

  /**
   * The feature id for the '<em><b>Metamodel Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ROOT__METAMODEL_VERSION = 0;

  /**
   * The feature id for the '<em><b>Root Item</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ROOT__ROOT_ITEM = 1;

  /**
   * The number of structural features of the '<em>Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ROOT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl <em>Item</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.menu.menu.impl.MenuItemImpl
   * @see com.odcgroup.t24.menu.menu.impl.MenuPackageImpl#getMenuItem()
   * @generated
   */
  int MENU_ITEM = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__NAME = 0;

  /**
   * The feature id for the '<em><b>Enabled</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__ENABLED = 1;

  /**
   * The feature id for the '<em><b>Display Tabs</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__DISPLAY_TABS = 2;

  /**
   * The feature id for the '<em><b>Security Role</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__SECURITY_ROLE = 3;

  /**
   * The feature id for the '<em><b>Shortcut</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__SHORTCUT = 4;

  /**
   * The feature id for the '<em><b>Labels</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__LABELS = 5;

  /**
   * The feature id for the '<em><b>Version</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__VERSION = 6;

  /**
   * The feature id for the '<em><b>Enquiry</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__ENQUIRY = 7;

  /**
   * The feature id for the '<em><b>Composite Screen</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__COMPOSITE_SCREEN = 8;

  /**
   * The feature id for the '<em><b>Included Menu</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__INCLUDED_MENU = 9;

  /**
   * The feature id for the '<em><b>Application</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__APPLICATION = 10;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__PARAMETERS = 11;

  /**
   * The feature id for the '<em><b>Submenus</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM__SUBMENUS = 12;

  /**
   * The number of structural features of the '<em>Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MENU_ITEM_FEATURE_COUNT = 13;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.menu.menu.impl.TranslationImpl <em>Translation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.menu.menu.impl.TranslationImpl
   * @see com.odcgroup.t24.menu.menu.impl.MenuPackageImpl#getTranslation()
   * @generated
   */
  int TRANSLATION = 2;

  /**
   * The feature id for the '<em><b>Language</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSLATION__LANGUAGE = 0;

  /**
   * The feature id for the '<em><b>Message</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSLATION__MESSAGE = 1;

  /**
   * The number of structural features of the '<em>Translation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSLATION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.menu.menu.Enabled <em>Enabled</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.menu.menu.Enabled
   * @see com.odcgroup.t24.menu.menu.impl.MenuPackageImpl#getEnabled()
   * @generated
   */
  int ENABLED = 3;


  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.menu.menu.MenuRoot <em>Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Root</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuRoot
   * @generated
   */
  EClass getMenuRoot();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.MenuRoot#getMetamodelVersion <em>Metamodel Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Metamodel Version</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuRoot#getMetamodelVersion()
   * @see #getMenuRoot()
   * @generated
   */
  EAttribute getMenuRoot_MetamodelVersion();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.menu.menu.MenuRoot#getRootItem <em>Root Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Root Item</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuRoot#getRootItem()
   * @see #getMenuRoot()
   * @generated
   */
  EReference getMenuRoot_RootItem();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.menu.menu.MenuItem <em>Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Item</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem
   * @generated
   */
  EClass getMenuItem();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.MenuItem#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getName()
   * @see #getMenuItem()
   * @generated
   */
  EAttribute getMenuItem_Name();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.MenuItem#getEnabled <em>Enabled</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Enabled</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getEnabled()
   * @see #getMenuItem()
   * @generated
   */
  EAttribute getMenuItem_Enabled();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.MenuItem#isDisplayTabs <em>Display Tabs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Display Tabs</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#isDisplayTabs()
   * @see #getMenuItem()
   * @generated
   */
  EAttribute getMenuItem_DisplayTabs();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.MenuItem#getSecurityRole <em>Security Role</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Security Role</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getSecurityRole()
   * @see #getMenuItem()
   * @generated
   */
  EAttribute getMenuItem_SecurityRole();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.MenuItem#getShortcut <em>Shortcut</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Shortcut</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getShortcut()
   * @see #getMenuItem()
   * @generated
   */
  EAttribute getMenuItem_Shortcut();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.menu.menu.MenuItem#getLabels <em>Labels</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Labels</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getLabels()
   * @see #getMenuItem()
   * @generated
   */
  EReference getMenuItem_Labels();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.menu.menu.MenuItem#getVersion <em>Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Version</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getVersion()
   * @see #getMenuItem()
   * @generated
   */
  EReference getMenuItem_Version();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.menu.menu.MenuItem#getEnquiry <em>Enquiry</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Enquiry</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getEnquiry()
   * @see #getMenuItem()
   * @generated
   */
  EReference getMenuItem_Enquiry();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.menu.menu.MenuItem#getCompositeScreen <em>Composite Screen</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Composite Screen</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getCompositeScreen()
   * @see #getMenuItem()
   * @generated
   */
  EReference getMenuItem_CompositeScreen();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.menu.menu.MenuItem#getIncludedMenu <em>Included Menu</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Included Menu</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getIncludedMenu()
   * @see #getMenuItem()
   * @generated
   */
  EReference getMenuItem_IncludedMenu();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.menu.menu.MenuItem#getApplication <em>Application</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Application</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getApplication()
   * @see #getMenuItem()
   * @generated
   */
  EReference getMenuItem_Application();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.MenuItem#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Parameters</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getParameters()
   * @see #getMenuItem()
   * @generated
   */
  EAttribute getMenuItem_Parameters();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.menu.menu.MenuItem#getSubmenus <em>Submenus</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Submenus</em>'.
   * @see com.odcgroup.t24.menu.menu.MenuItem#getSubmenus()
   * @see #getMenuItem()
   * @generated
   */
  EReference getMenuItem_Submenus();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.menu.menu.Translation <em>Translation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Translation</em>'.
   * @see com.odcgroup.t24.menu.menu.Translation
   * @generated
   */
  EClass getTranslation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.Translation#getLanguage <em>Language</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Language</em>'.
   * @see com.odcgroup.t24.menu.menu.Translation#getLanguage()
   * @see #getTranslation()
   * @generated
   */
  EAttribute getTranslation_Language();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.menu.menu.Translation#getMessage <em>Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Message</em>'.
   * @see com.odcgroup.t24.menu.menu.Translation#getMessage()
   * @see #getTranslation()
   * @generated
   */
  EAttribute getTranslation_Message();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.menu.menu.Enabled <em>Enabled</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Enabled</em>'.
   * @see com.odcgroup.t24.menu.menu.Enabled
   * @generated
   */
  EEnum getEnabled();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  MenuFactory getMenuFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link com.odcgroup.t24.menu.menu.impl.MenuRootImpl <em>Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.menu.menu.impl.MenuRootImpl
     * @see com.odcgroup.t24.menu.menu.impl.MenuPackageImpl#getMenuRoot()
     * @generated
     */
    EClass MENU_ROOT = eINSTANCE.getMenuRoot();

    /**
     * The meta object literal for the '<em><b>Metamodel Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MENU_ROOT__METAMODEL_VERSION = eINSTANCE.getMenuRoot_MetamodelVersion();

    /**
     * The meta object literal for the '<em><b>Root Item</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MENU_ROOT__ROOT_ITEM = eINSTANCE.getMenuRoot_RootItem();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.menu.menu.impl.MenuItemImpl <em>Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.menu.menu.impl.MenuItemImpl
     * @see com.odcgroup.t24.menu.menu.impl.MenuPackageImpl#getMenuItem()
     * @generated
     */
    EClass MENU_ITEM = eINSTANCE.getMenuItem();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MENU_ITEM__NAME = eINSTANCE.getMenuItem_Name();

    /**
     * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MENU_ITEM__ENABLED = eINSTANCE.getMenuItem_Enabled();

    /**
     * The meta object literal for the '<em><b>Display Tabs</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MENU_ITEM__DISPLAY_TABS = eINSTANCE.getMenuItem_DisplayTabs();

    /**
     * The meta object literal for the '<em><b>Security Role</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MENU_ITEM__SECURITY_ROLE = eINSTANCE.getMenuItem_SecurityRole();

    /**
     * The meta object literal for the '<em><b>Shortcut</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MENU_ITEM__SHORTCUT = eINSTANCE.getMenuItem_Shortcut();

    /**
     * The meta object literal for the '<em><b>Labels</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MENU_ITEM__LABELS = eINSTANCE.getMenuItem_Labels();

    /**
     * The meta object literal for the '<em><b>Version</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MENU_ITEM__VERSION = eINSTANCE.getMenuItem_Version();

    /**
     * The meta object literal for the '<em><b>Enquiry</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MENU_ITEM__ENQUIRY = eINSTANCE.getMenuItem_Enquiry();

    /**
     * The meta object literal for the '<em><b>Composite Screen</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MENU_ITEM__COMPOSITE_SCREEN = eINSTANCE.getMenuItem_CompositeScreen();

    /**
     * The meta object literal for the '<em><b>Included Menu</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MENU_ITEM__INCLUDED_MENU = eINSTANCE.getMenuItem_IncludedMenu();

    /**
     * The meta object literal for the '<em><b>Application</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MENU_ITEM__APPLICATION = eINSTANCE.getMenuItem_Application();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MENU_ITEM__PARAMETERS = eINSTANCE.getMenuItem_Parameters();

    /**
     * The meta object literal for the '<em><b>Submenus</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MENU_ITEM__SUBMENUS = eINSTANCE.getMenuItem_Submenus();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.menu.menu.impl.TranslationImpl <em>Translation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.menu.menu.impl.TranslationImpl
     * @see com.odcgroup.t24.menu.menu.impl.MenuPackageImpl#getTranslation()
     * @generated
     */
    EClass TRANSLATION = eINSTANCE.getTranslation();

    /**
     * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRANSLATION__LANGUAGE = eINSTANCE.getTranslation_Language();

    /**
     * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRANSLATION__MESSAGE = eINSTANCE.getTranslation_Message();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.menu.menu.Enabled <em>Enabled</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.menu.menu.Enabled
     * @see com.odcgroup.t24.menu.menu.impl.MenuPackageImpl#getEnabled()
     * @generated
     */
    EEnum ENABLED = eINSTANCE.getEnabled();

  }

} //MenuPackage
