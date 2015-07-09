/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.odcgroup.page.metamodel.MetaModelPackage;

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
 * @see com.odcgroup.page.uimodel.UIModelFactory
 * @model kind="package"
 * @generated
 */
public interface UIModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "uimodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/page/uimodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.odcgroup.page.uimodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UIModelPackage eINSTANCE = com.odcgroup.page.uimodel.impl.UIModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.UIModelImpl <em>UI Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.UIModelImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getUIModel()
	 * @generated
	 */
	int UI_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_MODEL__PALETTE = 0;

	/**
	 * The feature id for the '<em><b>Renderers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_MODEL__RENDERERS = 1;

	/**
	 * The feature id for the '<em><b>Palette Group Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_MODEL__PALETTE_GROUP_ITEMS = 2;

	/**
	 * The feature id for the '<em><b>Default Palette</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_MODEL__DEFAULT_PALETTE = 3;

	/**
	 * The feature id for the '<em><b>Menus</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_MODEL__MENUS = 4;

	/**
	 * The feature id for the '<em><b>Action Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_MODEL__ACTION_GROUPS = 5;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_MODEL__ACTIONS = 6;

	/**
	 * The number of structural features of the '<em>UI Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_MODEL_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.PaletteImpl <em>Palette</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.PaletteImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getPalette()
	 * @generated
	 */
	int PALETTE = 1;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__GROUPS = 0;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__WIDGET_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__PROPERTY_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Property Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__PROPERTY_VALUE = 3;

	/**
	 * The number of structural features of the '<em>Palette</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.PaletteGroupImpl <em>Palette Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.PaletteGroupImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getPaletteGroup()
	 * @generated
	 */
	int PALETTE_GROUP = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__ITEMS = 1;

	/**
	 * The feature id for the '<em><b>Expanded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__EXPANDED = 2;

	/**
	 * The number of structural features of the '<em>Palette Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl <em>Palette Group Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getPaletteGroupItem()
	 * @generated
	 */
	int PALETTE_GROUP_ITEM = 3;

	/**
	 * The feature id for the '<em><b>Small Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_ITEM__SMALL_IMAGE = 0;

	/**
	 * The feature id for the '<em><b>Large Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_ITEM__LARGE_IMAGE = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_ITEM__LABEL = 2;

	/**
	 * The feature id for the '<em><b>Tooltip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_ITEM__TOOLTIP = 3;

	/**
	 * The feature id for the '<em><b>Widget Template</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_ITEM__WIDGET_TEMPLATE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_ITEM__NAME = 5;

	/**
	 * The number of structural features of the '<em>Palette Group Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_ITEM_FEATURE_COUNT = 6;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.RenderersImpl <em>Renderers</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.RenderersImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getRenderers()
	 * @generated
	 */
	int RENDERERS = 4;

	/**
	 * The feature id for the '<em><b>Renderer Infos</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERERS__RENDERER_INFOS = 0;

	/**
	 * The number of structural features of the '<em>Renderers</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERERS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl <em>Renderer Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.RendererInfoImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getRendererInfo()
	 * @generated
	 */
	int RENDERER_INFO = 5;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__WIDGET_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Figure</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__FIGURE = 1;

	/**
	 * The feature id for the '<em><b>Edit Part</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__EDIT_PART = 2;

	/**
	 * The feature id for the '<em><b>Design Widget Spacing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__DESIGN_WIDGET_SPACING = 3;

	/**
	 * The feature id for the '<em><b>Preview Widget Spacing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__PREVIEW_WIDGET_SPACING = 4;

	/**
	 * The feature id for the '<em><b>Request Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__REQUEST_HANDLER = 5;

	/**
	 * The feature id for the '<em><b>Direct Edit Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__DIRECT_EDIT_MODE = 6;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__ROLES = 7;

	/**
	 * The feature id for the '<em><b>Direct Edit Manager</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__DIRECT_EDIT_MANAGER = 8;

	/**
	 * The feature id for the '<em><b>Delete Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__DELETE_COMMAND = 9;

	/**
	 * The feature id for the '<em><b>Drag Tracker</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO__DRAG_TRACKER = 10;

	/**
	 * The number of structural features of the '<em>Renderer Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENDERER_INFO_FEATURE_COUNT = 11;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.MenusImpl <em>Menus</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.MenusImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getMenus()
	 * @generated
	 */
	int MENUS = 6;

	/**
	 * The feature id for the '<em><b>Menus</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENUS__MENUS = 0;

	/**
	 * The number of structural features of the '<em>Menus</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENUS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.WidgetMenuImpl <em>Widget Menu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.WidgetMenuImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getWidgetMenu()
	 * @generated
	 */
	int WIDGET_MENU = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__NAME = MetaModelPackage.NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__DESCRIPTION = MetaModelPackage.NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__TRANSLATION_SUPPORT = MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__TYPE = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__PROPERTY_VALUE = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__PARENT = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__PROPERTY_TYPE = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Widget Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__WIDGET_TYPES = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Menu Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU__MENU_ITEMS = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Widget Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_MENU_FEATURE_COUNT = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.ActionGroupsImpl <em>Action Groups</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.ActionGroupsImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getActionGroups()
	 * @generated
	 */
	int ACTION_GROUPS = 8;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_GROUPS__GROUPS = 0;

	/**
	 * The number of structural features of the '<em>Action Groups</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_GROUPS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.ActionsImpl <em>Actions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.ActionsImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getActions()
	 * @generated
	 */
	int ACTIONS = 9;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIONS__ACTIONS = 0;

	/**
	 * The number of structural features of the '<em>Actions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.ActionGroupImpl <em>Action Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.ActionGroupImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getActionGroup()
	 * @generated
	 */
	int ACTION_GROUP = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_GROUP__NAME = MetaModelPackage.NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_GROUP__DESCRIPTION = MetaModelPackage.NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_GROUP__TRANSLATION_SUPPORT = MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_GROUP__ACTIONS = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Action Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_GROUP_FEATURE_COUNT = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.ActionImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__NAME = MetaModelPackage.NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__DESCRIPTION = MetaModelPackage.NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__TRANSLATION_SUPPORT = MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__PROVIDER = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__PROPERTY = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__MODE = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__ID = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Property Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__PROPERTY_VALUE = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Template Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__TEMPLATE_NAME = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Delegate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__DELEGATE = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Accelerator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__ACCELERATOR = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__PROPERTY_TYPE = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.EditPolicyRoleImpl <em>Edit Policy Role</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.EditPolicyRoleImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getEditPolicyRole()
	 * @generated
	 */
	int EDIT_POLICY_ROLE = 12;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_POLICY_ROLE__MODE = 0;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_POLICY_ROLE__ROLE = 1;

	/**
	 * The feature id for the '<em><b>Implementation Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_POLICY_ROLE__IMPLEMENTATION_CLASS = 2;

	/**
	 * The number of structural features of the '<em>Edit Policy Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_POLICY_ROLE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.impl.MenuItemImpl <em>Menu Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.impl.MenuItemImpl
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getMenuItem()
	 * @generated
	 */
	int MENU_ITEM = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__NAME = MetaModelPackage.NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__DESCRIPTION = MetaModelPackage.NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__TRANSLATION_SUPPORT = MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__PARENT = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__GROUPS = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Menu Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM_FEATURE_COUNT = MetaModelPackage.NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.MenuType <em>Menu Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.MenuType
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getMenuType()
	 * @generated
	 */
	int MENU_TYPE = 14;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.EditorMode <em>Editor Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.EditorMode
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getEditorMode()
	 * @generated
	 */
	int EDITOR_MODE = 15;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.uimodel.EditPolicyRoleType <em>Edit Policy Role Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.uimodel.EditPolicyRoleType
	 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getEditPolicyRoleType()
	 * @generated
	 */
	int EDIT_POLICY_ROLE_TYPE = 16;

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.UIModel <em>UI Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UI Model</em>'.
	 * @see com.odcgroup.page.uimodel.UIModel
	 * @generated
	 */
	EClass getUIModel();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.UIModel#getPalette <em>Palette</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Palette</em>'.
	 * @see com.odcgroup.page.uimodel.UIModel#getPalette()
	 * @see #getUIModel()
	 * @generated
	 */
	EReference getUIModel_Palette();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.uimodel.UIModel#getRenderers <em>Renderers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Renderers</em>'.
	 * @see com.odcgroup.page.uimodel.UIModel#getRenderers()
	 * @see #getUIModel()
	 * @generated
	 */
	EReference getUIModel_Renderers();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.UIModel#getPaletteGroupItems <em>Palette Group Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Palette Group Items</em>'.
	 * @see com.odcgroup.page.uimodel.UIModel#getPaletteGroupItems()
	 * @see #getUIModel()
	 * @generated
	 */
	EReference getUIModel_PaletteGroupItems();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.uimodel.UIModel#getDefaultPalette <em>Default Palette</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Default Palette</em>'.
	 * @see com.odcgroup.page.uimodel.UIModel#getDefaultPalette()
	 * @see #getUIModel()
	 * @generated
	 */
	EReference getUIModel_DefaultPalette();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.uimodel.UIModel#getMenus <em>Menus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Menus</em>'.
	 * @see com.odcgroup.page.uimodel.UIModel#getMenus()
	 * @see #getUIModel()
	 * @generated
	 */
	EReference getUIModel_Menus();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.uimodel.UIModel#getActionGroups <em>Action Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action Groups</em>'.
	 * @see com.odcgroup.page.uimodel.UIModel#getActionGroups()
	 * @see #getUIModel()
	 * @generated
	 */
	EReference getUIModel_ActionGroups();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.UIModel#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see com.odcgroup.page.uimodel.UIModel#getActions()
	 * @see #getUIModel()
	 * @generated
	 */
	EReference getUIModel_Actions();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.Palette <em>Palette</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Palette</em>'.
	 * @see com.odcgroup.page.uimodel.Palette
	 * @generated
	 */
	EClass getPalette();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.Palette#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Groups</em>'.
	 * @see com.odcgroup.page.uimodel.Palette#getGroups()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_Groups();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.Palette#getWidgetType <em>Widget Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Widget Type</em>'.
	 * @see com.odcgroup.page.uimodel.Palette#getWidgetType()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_WidgetType();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.Palette#getPropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property Type</em>'.
	 * @see com.odcgroup.page.uimodel.Palette#getPropertyType()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_PropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.Palette#getPropertyValue <em>Property Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Value</em>'.
	 * @see com.odcgroup.page.uimodel.Palette#getPropertyValue()
	 * @see #getPalette()
	 * @generated
	 */
	EAttribute getPalette_PropertyValue();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.PaletteGroup <em>Palette Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Palette Group</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroup
	 * @generated
	 */
	EClass getPaletteGroup();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.PaletteGroup#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroup#getLabel()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Label();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.uimodel.PaletteGroup#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Items</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroup#getItems()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EReference getPaletteGroup_Items();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.PaletteGroup#isExpanded <em>Expanded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expanded</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroup#isExpanded()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Expanded();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.PaletteGroupItem <em>Palette Group Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Palette Group Item</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroupItem
	 * @generated
	 */
	EClass getPaletteGroupItem();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getSmallImage <em>Small Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Image</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroupItem#getSmallImage()
	 * @see #getPaletteGroupItem()
	 * @generated
	 */
	EAttribute getPaletteGroupItem_SmallImage();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getLargeImage <em>Large Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Large Image</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroupItem#getLargeImage()
	 * @see #getPaletteGroupItem()
	 * @generated
	 */
	EAttribute getPaletteGroupItem_LargeImage();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroupItem#getLabel()
	 * @see #getPaletteGroupItem()
	 * @generated
	 */
	EAttribute getPaletteGroupItem_Label();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getTooltip <em>Tooltip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tooltip</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroupItem#getTooltip()
	 * @see #getPaletteGroupItem()
	 * @generated
	 */
	EAttribute getPaletteGroupItem_Tooltip();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getWidgetTemplate <em>Widget Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Widget Template</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroupItem#getWidgetTemplate()
	 * @see #getPaletteGroupItem()
	 * @generated
	 */
	EReference getPaletteGroupItem_WidgetTemplate();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.page.uimodel.PaletteGroupItem#getName()
	 * @see #getPaletteGroupItem()
	 * @generated
	 */
	EAttribute getPaletteGroupItem_Name();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.Renderers <em>Renderers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Renderers</em>'.
	 * @see com.odcgroup.page.uimodel.Renderers
	 * @generated
	 */
	EClass getRenderers();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.Renderers#getRendererInfos <em>Renderer Infos</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Renderer Infos</em>'.
	 * @see com.odcgroup.page.uimodel.Renderers#getRendererInfos()
	 * @see #getRenderers()
	 * @generated
	 */
	EReference getRenderers_RendererInfos();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.RendererInfo <em>Renderer Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Renderer Info</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo
	 * @generated
	 */
	EClass getRendererInfo();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.RendererInfo#getWidgetType <em>Widget Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Widget Type</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getWidgetType()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EReference getRendererInfo_WidgetType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getFigure <em>Figure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Figure</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getFigure()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_Figure();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getEditPart <em>Edit Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edit Part</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getEditPart()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_EditPart();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getDesignWidgetSpacing <em>Design Widget Spacing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Design Widget Spacing</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getDesignWidgetSpacing()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_DesignWidgetSpacing();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getPreviewWidgetSpacing <em>Preview Widget Spacing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Preview Widget Spacing</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getPreviewWidgetSpacing()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_PreviewWidgetSpacing();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getRequestHandler <em>Request Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Request Handler</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getRequestHandler()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_RequestHandler();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getDirectEditMode <em>Direct Edit Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direct Edit Mode</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getDirectEditMode()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_DirectEditMode();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.RendererInfo#getRoles <em>Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Roles</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getRoles()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EReference getRendererInfo_Roles();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getDirectEditManager <em>Direct Edit Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direct Edit Manager</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getDirectEditManager()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_DirectEditManager();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getDeleteCommand <em>Delete Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delete Command</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getDeleteCommand()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_DeleteCommand();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.RendererInfo#getDragTracker <em>Drag Tracker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Drag Tracker</em>'.
	 * @see com.odcgroup.page.uimodel.RendererInfo#getDragTracker()
	 * @see #getRendererInfo()
	 * @generated
	 */
	EAttribute getRendererInfo_DragTracker();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.Menus <em>Menus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Menus</em>'.
	 * @see com.odcgroup.page.uimodel.Menus
	 * @generated
	 */
	EClass getMenus();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.Menus#getMenus <em>Menus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Menus</em>'.
	 * @see com.odcgroup.page.uimodel.Menus#getMenus()
	 * @see #getMenus()
	 * @generated
	 */
	EReference getMenus_Menus();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.WidgetMenu <em>Widget Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget Menu</em>'.
	 * @see com.odcgroup.page.uimodel.WidgetMenu
	 * @generated
	 */
	EClass getWidgetMenu();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.WidgetMenu#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.odcgroup.page.uimodel.WidgetMenu#getType()
	 * @see #getWidgetMenu()
	 * @generated
	 */
	EAttribute getWidgetMenu_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.WidgetMenu#getPropertyValue <em>Property Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Value</em>'.
	 * @see com.odcgroup.page.uimodel.WidgetMenu#getPropertyValue()
	 * @see #getWidgetMenu()
	 * @generated
	 */
	EAttribute getWidgetMenu_PropertyValue();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.WidgetMenu#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.odcgroup.page.uimodel.WidgetMenu#getParent()
	 * @see #getWidgetMenu()
	 * @generated
	 */
	EReference getWidgetMenu_Parent();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.WidgetMenu#getPropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property Type</em>'.
	 * @see com.odcgroup.page.uimodel.WidgetMenu#getPropertyType()
	 * @see #getWidgetMenu()
	 * @generated
	 */
	EReference getWidgetMenu_PropertyType();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.uimodel.WidgetMenu#getWidgetTypes <em>Widget Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Widget Types</em>'.
	 * @see com.odcgroup.page.uimodel.WidgetMenu#getWidgetTypes()
	 * @see #getWidgetMenu()
	 * @generated
	 */
	EReference getWidgetMenu_WidgetTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.WidgetMenu#getMenuItems <em>Menu Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Menu Items</em>'.
	 * @see com.odcgroup.page.uimodel.WidgetMenu#getMenuItems()
	 * @see #getWidgetMenu()
	 * @generated
	 */
	EReference getWidgetMenu_MenuItems();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.ActionGroups <em>Action Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Groups</em>'.
	 * @see com.odcgroup.page.uimodel.ActionGroups
	 * @generated
	 */
	EClass getActionGroups();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.ActionGroups#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Groups</em>'.
	 * @see com.odcgroup.page.uimodel.ActionGroups#getGroups()
	 * @see #getActionGroups()
	 * @generated
	 */
	EReference getActionGroups_Groups();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.Actions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Actions</em>'.
	 * @see com.odcgroup.page.uimodel.Actions
	 * @generated
	 */
	EClass getActions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.uimodel.Actions#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see com.odcgroup.page.uimodel.Actions#getActions()
	 * @see #getActions()
	 * @generated
	 */
	EReference getActions_Actions();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.ActionGroup <em>Action Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Group</em>'.
	 * @see com.odcgroup.page.uimodel.ActionGroup
	 * @generated
	 */
	EClass getActionGroup();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.uimodel.ActionGroup#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Actions</em>'.
	 * @see com.odcgroup.page.uimodel.ActionGroup#getActions()
	 * @see #getActionGroup()
	 * @generated
	 */
	EReference getActionGroup_Actions();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see com.odcgroup.page.uimodel.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.Action#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Provider</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getProvider()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Provider();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.Action#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getProperty()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.Action#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getMode()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Mode();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.Action#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getId()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.Action#getPropertyValue <em>Property Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Value</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getPropertyValue()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_PropertyValue();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.Action#getTemplateName <em>Template Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Template Name</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getTemplateName()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_TemplateName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.Action#getDelegate <em>Delegate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delegate</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getDelegate()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Delegate();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.Action#getAccelerator <em>Accelerator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Accelerator</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getAccelerator()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Accelerator();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.Action#getPropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property Type</em>'.
	 * @see com.odcgroup.page.uimodel.Action#getPropertyType()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_PropertyType();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.EditPolicyRole <em>Edit Policy Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edit Policy Role</em>'.
	 * @see com.odcgroup.page.uimodel.EditPolicyRole
	 * @generated
	 */
	EClass getEditPolicyRole();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.EditPolicyRole#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see com.odcgroup.page.uimodel.EditPolicyRole#getMode()
	 * @see #getEditPolicyRole()
	 * @generated
	 */
	EAttribute getEditPolicyRole_Mode();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.EditPolicyRole#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Role</em>'.
	 * @see com.odcgroup.page.uimodel.EditPolicyRole#getRole()
	 * @see #getEditPolicyRole()
	 * @generated
	 */
	EAttribute getEditPolicyRole_Role();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.uimodel.EditPolicyRole#getImplementationClass <em>Implementation Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Class</em>'.
	 * @see com.odcgroup.page.uimodel.EditPolicyRole#getImplementationClass()
	 * @see #getEditPolicyRole()
	 * @generated
	 */
	EAttribute getEditPolicyRole_ImplementationClass();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.uimodel.MenuItem <em>Menu Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Menu Item</em>'.
	 * @see com.odcgroup.page.uimodel.MenuItem
	 * @generated
	 */
	EClass getMenuItem();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.uimodel.MenuItem#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.odcgroup.page.uimodel.MenuItem#getParent()
	 * @see #getMenuItem()
	 * @generated
	 */
	EReference getMenuItem_Parent();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.uimodel.MenuItem#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Groups</em>'.
	 * @see com.odcgroup.page.uimodel.MenuItem#getGroups()
	 * @see #getMenuItem()
	 * @generated
	 */
	EReference getMenuItem_Groups();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.page.uimodel.MenuType <em>Menu Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Menu Type</em>'.
	 * @see com.odcgroup.page.uimodel.MenuType
	 * @generated
	 */
	EEnum getMenuType();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.page.uimodel.EditorMode <em>Editor Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Editor Mode</em>'.
	 * @see com.odcgroup.page.uimodel.EditorMode
	 * @generated
	 */
	EEnum getEditorMode();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.page.uimodel.EditPolicyRoleType <em>Edit Policy Role Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Edit Policy Role Type</em>'.
	 * @see com.odcgroup.page.uimodel.EditPolicyRoleType
	 * @generated
	 */
	EEnum getEditPolicyRoleType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UIModelFactory getUIModelFactory();

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
	interface Literals  {
		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.UIModelImpl <em>UI Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.UIModelImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getUIModel()
		 * @generated
		 */
		EClass UI_MODEL = eINSTANCE.getUIModel();

		/**
		 * The meta object literal for the '<em><b>Palette</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UI_MODEL__PALETTE = eINSTANCE.getUIModel_Palette();

		/**
		 * The meta object literal for the '<em><b>Renderers</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UI_MODEL__RENDERERS = eINSTANCE.getUIModel_Renderers();

		/**
		 * The meta object literal for the '<em><b>Palette Group Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UI_MODEL__PALETTE_GROUP_ITEMS = eINSTANCE.getUIModel_PaletteGroupItems();

		/**
		 * The meta object literal for the '<em><b>Default Palette</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UI_MODEL__DEFAULT_PALETTE = eINSTANCE.getUIModel_DefaultPalette();

		/**
		 * The meta object literal for the '<em><b>Menus</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UI_MODEL__MENUS = eINSTANCE.getUIModel_Menus();

		/**
		 * The meta object literal for the '<em><b>Action Groups</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UI_MODEL__ACTION_GROUPS = eINSTANCE.getUIModel_ActionGroups();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UI_MODEL__ACTIONS = eINSTANCE.getUIModel_Actions();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.PaletteImpl <em>Palette</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.PaletteImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getPalette()
		 * @generated
		 */
		EClass PALETTE = eINSTANCE.getPalette();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__GROUPS = eINSTANCE.getPalette_Groups();

		/**
		 * The meta object literal for the '<em><b>Widget Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__WIDGET_TYPE = eINSTANCE.getPalette_WidgetType();

		/**
		 * The meta object literal for the '<em><b>Property Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__PROPERTY_TYPE = eINSTANCE.getPalette_PropertyType();

		/**
		 * The meta object literal for the '<em><b>Property Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE__PROPERTY_VALUE = eINSTANCE.getPalette_PropertyValue();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.PaletteGroupImpl <em>Palette Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.PaletteGroupImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getPaletteGroup()
		 * @generated
		 */
		EClass PALETTE_GROUP = eINSTANCE.getPaletteGroup();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__LABEL = eINSTANCE.getPaletteGroup_Label();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_GROUP__ITEMS = eINSTANCE.getPaletteGroup_Items();

		/**
		 * The meta object literal for the '<em><b>Expanded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__EXPANDED = eINSTANCE.getPaletteGroup_Expanded();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl <em>Palette Group Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getPaletteGroupItem()
		 * @generated
		 */
		EClass PALETTE_GROUP_ITEM = eINSTANCE.getPaletteGroupItem();

		/**
		 * The meta object literal for the '<em><b>Small Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP_ITEM__SMALL_IMAGE = eINSTANCE.getPaletteGroupItem_SmallImage();

		/**
		 * The meta object literal for the '<em><b>Large Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP_ITEM__LARGE_IMAGE = eINSTANCE.getPaletteGroupItem_LargeImage();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP_ITEM__LABEL = eINSTANCE.getPaletteGroupItem_Label();

		/**
		 * The meta object literal for the '<em><b>Tooltip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP_ITEM__TOOLTIP = eINSTANCE.getPaletteGroupItem_Tooltip();

		/**
		 * The meta object literal for the '<em><b>Widget Template</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_GROUP_ITEM__WIDGET_TEMPLATE = eINSTANCE.getPaletteGroupItem_WidgetTemplate();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP_ITEM__NAME = eINSTANCE.getPaletteGroupItem_Name();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.RenderersImpl <em>Renderers</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.RenderersImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getRenderers()
		 * @generated
		 */
		EClass RENDERERS = eINSTANCE.getRenderers();

		/**
		 * The meta object literal for the '<em><b>Renderer Infos</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RENDERERS__RENDERER_INFOS = eINSTANCE.getRenderers_RendererInfos();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl <em>Renderer Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.RendererInfoImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getRendererInfo()
		 * @generated
		 */
		EClass RENDERER_INFO = eINSTANCE.getRendererInfo();

		/**
		 * The meta object literal for the '<em><b>Widget Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RENDERER_INFO__WIDGET_TYPE = eINSTANCE.getRendererInfo_WidgetType();

		/**
		 * The meta object literal for the '<em><b>Figure</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__FIGURE = eINSTANCE.getRendererInfo_Figure();

		/**
		 * The meta object literal for the '<em><b>Edit Part</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__EDIT_PART = eINSTANCE.getRendererInfo_EditPart();

		/**
		 * The meta object literal for the '<em><b>Design Widget Spacing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__DESIGN_WIDGET_SPACING = eINSTANCE.getRendererInfo_DesignWidgetSpacing();

		/**
		 * The meta object literal for the '<em><b>Preview Widget Spacing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__PREVIEW_WIDGET_SPACING = eINSTANCE.getRendererInfo_PreviewWidgetSpacing();

		/**
		 * The meta object literal for the '<em><b>Request Handler</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__REQUEST_HANDLER = eINSTANCE.getRendererInfo_RequestHandler();

		/**
		 * The meta object literal for the '<em><b>Direct Edit Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__DIRECT_EDIT_MODE = eINSTANCE.getRendererInfo_DirectEditMode();

		/**
		 * The meta object literal for the '<em><b>Roles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RENDERER_INFO__ROLES = eINSTANCE.getRendererInfo_Roles();

		/**
		 * The meta object literal for the '<em><b>Direct Edit Manager</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__DIRECT_EDIT_MANAGER = eINSTANCE.getRendererInfo_DirectEditManager();

		/**
		 * The meta object literal for the '<em><b>Delete Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__DELETE_COMMAND = eINSTANCE.getRendererInfo_DeleteCommand();

		/**
		 * The meta object literal for the '<em><b>Drag Tracker</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENDERER_INFO__DRAG_TRACKER = eINSTANCE.getRendererInfo_DragTracker();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.MenusImpl <em>Menus</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.MenusImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getMenus()
		 * @generated
		 */
		EClass MENUS = eINSTANCE.getMenus();

		/**
		 * The meta object literal for the '<em><b>Menus</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MENUS__MENUS = eINSTANCE.getMenus_Menus();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.WidgetMenuImpl <em>Widget Menu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.WidgetMenuImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getWidgetMenu()
		 * @generated
		 */
		EClass WIDGET_MENU = eINSTANCE.getWidgetMenu();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_MENU__TYPE = eINSTANCE.getWidgetMenu_Type();

		/**
		 * The meta object literal for the '<em><b>Property Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_MENU__PROPERTY_VALUE = eINSTANCE.getWidgetMenu_PropertyValue();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_MENU__PARENT = eINSTANCE.getWidgetMenu_Parent();

		/**
		 * The meta object literal for the '<em><b>Property Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_MENU__PROPERTY_TYPE = eINSTANCE.getWidgetMenu_PropertyType();

		/**
		 * The meta object literal for the '<em><b>Widget Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_MENU__WIDGET_TYPES = eINSTANCE.getWidgetMenu_WidgetTypes();

		/**
		 * The meta object literal for the '<em><b>Menu Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_MENU__MENU_ITEMS = eINSTANCE.getWidgetMenu_MenuItems();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.ActionGroupsImpl <em>Action Groups</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.ActionGroupsImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getActionGroups()
		 * @generated
		 */
		EClass ACTION_GROUPS = eINSTANCE.getActionGroups();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_GROUPS__GROUPS = eINSTANCE.getActionGroups_Groups();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.ActionsImpl <em>Actions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.ActionsImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getActions()
		 * @generated
		 */
		EClass ACTIONS = eINSTANCE.getActions();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIONS__ACTIONS = eINSTANCE.getActions_Actions();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.ActionGroupImpl <em>Action Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.ActionGroupImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getActionGroup()
		 * @generated
		 */
		EClass ACTION_GROUP = eINSTANCE.getActionGroup();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_GROUP__ACTIONS = eINSTANCE.getActionGroup_Actions();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.ActionImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__PROVIDER = eINSTANCE.getAction_Provider();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__PROPERTY = eINSTANCE.getAction_Property();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__MODE = eINSTANCE.getAction_Mode();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__ID = eINSTANCE.getAction_Id();

		/**
		 * The meta object literal for the '<em><b>Property Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__PROPERTY_VALUE = eINSTANCE.getAction_PropertyValue();

		/**
		 * The meta object literal for the '<em><b>Template Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__TEMPLATE_NAME = eINSTANCE.getAction_TemplateName();

		/**
		 * The meta object literal for the '<em><b>Delegate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__DELEGATE = eINSTANCE.getAction_Delegate();

		/**
		 * The meta object literal for the '<em><b>Accelerator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__ACCELERATOR = eINSTANCE.getAction_Accelerator();

		/**
		 * The meta object literal for the '<em><b>Property Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__PROPERTY_TYPE = eINSTANCE.getAction_PropertyType();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.EditPolicyRoleImpl <em>Edit Policy Role</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.EditPolicyRoleImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getEditPolicyRole()
		 * @generated
		 */
		EClass EDIT_POLICY_ROLE = eINSTANCE.getEditPolicyRole();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDIT_POLICY_ROLE__MODE = eINSTANCE.getEditPolicyRole_Mode();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDIT_POLICY_ROLE__ROLE = eINSTANCE.getEditPolicyRole_Role();

		/**
		 * The meta object literal for the '<em><b>Implementation Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDIT_POLICY_ROLE__IMPLEMENTATION_CLASS = eINSTANCE.getEditPolicyRole_ImplementationClass();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.impl.MenuItemImpl <em>Menu Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.impl.MenuItemImpl
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getMenuItem()
		 * @generated
		 */
		EClass MENU_ITEM = eINSTANCE.getMenuItem();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MENU_ITEM__PARENT = eINSTANCE.getMenuItem_Parent();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MENU_ITEM__GROUPS = eINSTANCE.getMenuItem_Groups();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.MenuType <em>Menu Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.MenuType
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getMenuType()
		 * @generated
		 */
		EEnum MENU_TYPE = eINSTANCE.getMenuType();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.EditorMode <em>Editor Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.EditorMode
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getEditorMode()
		 * @generated
		 */
		EEnum EDITOR_MODE = eINSTANCE.getEditorMode();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.uimodel.EditPolicyRoleType <em>Edit Policy Role Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.uimodel.EditPolicyRoleType
		 * @see com.odcgroup.page.uimodel.impl.UIModelPackageImpl#getEditPolicyRoleType()
		 * @generated
		 */
		EEnum EDIT_POLICY_ROLE_TYPE = eINSTANCE.getEditPolicyRoleType();

	}

} //UIModelPackage
