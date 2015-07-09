/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.uimodel.Action;
import com.odcgroup.page.uimodel.ActionGroup;
import com.odcgroup.page.uimodel.ActionGroups;
import com.odcgroup.page.uimodel.Actions;
import com.odcgroup.page.uimodel.EditPolicyRole;
import com.odcgroup.page.uimodel.EditPolicyRoleType;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.MenuItem;
import com.odcgroup.page.uimodel.MenuType;
import com.odcgroup.page.uimodel.Menus;
import com.odcgroup.page.uimodel.Palette;
import com.odcgroup.page.uimodel.PaletteGroup;
import com.odcgroup.page.uimodel.PaletteGroupItem;
import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.Renderers;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.UIModelFactory;
import com.odcgroup.page.uimodel.UIModelPackage;
import com.odcgroup.page.uimodel.WidgetMenu;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UIModelPackageImpl extends EPackageImpl implements UIModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uiModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paletteEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paletteGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paletteGroupItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass renderersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rendererInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass menusEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass widgetMenuEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionGroupsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass editPolicyRoleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass menuItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum menuTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum editorModeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum editPolicyRoleTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.odcgroup.page.uimodel.UIModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UIModelPackageImpl() {
		super(eNS_URI, UIModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link UIModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * @return UIModelPackage
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UIModelPackage init() {
		if (isInited) return (UIModelPackage)EPackage.Registry.INSTANCE.getEPackage(UIModelPackage.eNS_URI);

		// Obtain or create and register package
		UIModelPackageImpl theUIModelPackage = (UIModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UIModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UIModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		MetaModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUIModelPackage.createPackageContents();

		// Initialize created meta-data
		theUIModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUIModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UIModelPackage.eNS_URI, theUIModelPackage);
		return theUIModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUIModel() {
		return uiModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUIModel_Palette() {
		return (EReference)uiModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUIModel_Renderers() {
		return (EReference)uiModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUIModel_PaletteGroupItems() {
		return (EReference)uiModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUIModel_DefaultPalette() {
		return (EReference)uiModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUIModel_Menus() {
		return (EReference)uiModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUIModel_ActionGroups() {
		return (EReference)uiModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUIModel_Actions() {
		return (EReference)uiModelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPalette() {
		return paletteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPalette_Groups() {
		return (EReference)paletteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPalette_WidgetType() {
		return (EReference)paletteEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPalette_PropertyType() {
		return (EReference)paletteEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPalette_PropertyValue() {
		return (EAttribute)paletteEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPaletteGroup() {
		return paletteGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Label() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteGroup_Items() {
		return (EReference)paletteGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Expanded() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPaletteGroupItem() {
		return paletteGroupItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroupItem_SmallImage() {
		return (EAttribute)paletteGroupItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroupItem_LargeImage() {
		return (EAttribute)paletteGroupItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroupItem_Label() {
		return (EAttribute)paletteGroupItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroupItem_Tooltip() {
		return (EAttribute)paletteGroupItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteGroupItem_WidgetTemplate() {
		return (EReference)paletteGroupItemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroupItem_Name() {
		return (EAttribute)paletteGroupItemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRenderers() {
		return renderersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRenderers_RendererInfos() {
		return (EReference)renderersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRendererInfo() {
		return rendererInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRendererInfo_WidgetType() {
		return (EReference)rendererInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_Figure() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_EditPart() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_DesignWidgetSpacing() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_PreviewWidgetSpacing() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_RequestHandler() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_DirectEditMode() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRendererInfo_Roles() {
		return (EReference)rendererInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_DirectEditManager() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_DeleteCommand() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRendererInfo_DragTracker() {
		return (EAttribute)rendererInfoEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMenus() {
		return menusEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMenus_Menus() {
		return (EReference)menusEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWidgetMenu() {
		return widgetMenuEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetMenu_Type() {
		return (EAttribute)widgetMenuEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetMenu_PropertyValue() {
		return (EAttribute)widgetMenuEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetMenu_Parent() {
		return (EReference)widgetMenuEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetMenu_PropertyType() {
		return (EReference)widgetMenuEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetMenu_WidgetTypes() {
		return (EReference)widgetMenuEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetMenu_MenuItems() {
		return (EReference)widgetMenuEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionGroups() {
		return actionGroupsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionGroups_Groups() {
		return (EReference)actionGroupsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActions() {
		return actionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActions_Actions() {
		return (EReference)actionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionGroup() {
		return actionGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionGroup_Actions() {
		return (EReference)actionGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAction() {
		return actionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Provider() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAction_Property() {
		return (EReference)actionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Mode() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Id() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_PropertyValue() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_TemplateName() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Delegate() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Accelerator() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAction_PropertyType() {
		return (EReference)actionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditPolicyRole() {
		return editPolicyRoleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditPolicyRole_Mode() {
		return (EAttribute)editPolicyRoleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditPolicyRole_Role() {
		return (EAttribute)editPolicyRoleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditPolicyRole_ImplementationClass() {
		return (EAttribute)editPolicyRoleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMenuItem() {
		return menuItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMenuItem_Parent() {
		return (EReference)menuItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMenuItem_Groups() {
		return (EReference)menuItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EEnum
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMenuType() {
		return menuTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EEnum
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEditorMode() {
		return editorModeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EEnum
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEditPolicyRoleType() {
		return editPolicyRoleTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return UIModelFactory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UIModelFactory getUIModelFactory() {
		return (UIModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		uiModelEClass = createEClass(UI_MODEL);
		createEReference(uiModelEClass, UI_MODEL__PALETTE);
		createEReference(uiModelEClass, UI_MODEL__RENDERERS);
		createEReference(uiModelEClass, UI_MODEL__PALETTE_GROUP_ITEMS);
		createEReference(uiModelEClass, UI_MODEL__DEFAULT_PALETTE);
		createEReference(uiModelEClass, UI_MODEL__MENUS);
		createEReference(uiModelEClass, UI_MODEL__ACTION_GROUPS);
		createEReference(uiModelEClass, UI_MODEL__ACTIONS);

		paletteEClass = createEClass(PALETTE);
		createEReference(paletteEClass, PALETTE__GROUPS);
		createEReference(paletteEClass, PALETTE__WIDGET_TYPE);
		createEReference(paletteEClass, PALETTE__PROPERTY_TYPE);
		createEAttribute(paletteEClass, PALETTE__PROPERTY_VALUE);

		paletteGroupEClass = createEClass(PALETTE_GROUP);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__LABEL);
		createEReference(paletteGroupEClass, PALETTE_GROUP__ITEMS);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__EXPANDED);

		paletteGroupItemEClass = createEClass(PALETTE_GROUP_ITEM);
		createEAttribute(paletteGroupItemEClass, PALETTE_GROUP_ITEM__SMALL_IMAGE);
		createEAttribute(paletteGroupItemEClass, PALETTE_GROUP_ITEM__LARGE_IMAGE);
		createEAttribute(paletteGroupItemEClass, PALETTE_GROUP_ITEM__LABEL);
		createEAttribute(paletteGroupItemEClass, PALETTE_GROUP_ITEM__TOOLTIP);
		createEReference(paletteGroupItemEClass, PALETTE_GROUP_ITEM__WIDGET_TEMPLATE);
		createEAttribute(paletteGroupItemEClass, PALETTE_GROUP_ITEM__NAME);

		renderersEClass = createEClass(RENDERERS);
		createEReference(renderersEClass, RENDERERS__RENDERER_INFOS);

		rendererInfoEClass = createEClass(RENDERER_INFO);
		createEReference(rendererInfoEClass, RENDERER_INFO__WIDGET_TYPE);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__FIGURE);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__EDIT_PART);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__DESIGN_WIDGET_SPACING);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__PREVIEW_WIDGET_SPACING);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__REQUEST_HANDLER);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__DIRECT_EDIT_MODE);
		createEReference(rendererInfoEClass, RENDERER_INFO__ROLES);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__DIRECT_EDIT_MANAGER);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__DELETE_COMMAND);
		createEAttribute(rendererInfoEClass, RENDERER_INFO__DRAG_TRACKER);

		menusEClass = createEClass(MENUS);
		createEReference(menusEClass, MENUS__MENUS);

		widgetMenuEClass = createEClass(WIDGET_MENU);
		createEAttribute(widgetMenuEClass, WIDGET_MENU__TYPE);
		createEAttribute(widgetMenuEClass, WIDGET_MENU__PROPERTY_VALUE);
		createEReference(widgetMenuEClass, WIDGET_MENU__PARENT);
		createEReference(widgetMenuEClass, WIDGET_MENU__PROPERTY_TYPE);
		createEReference(widgetMenuEClass, WIDGET_MENU__WIDGET_TYPES);
		createEReference(widgetMenuEClass, WIDGET_MENU__MENU_ITEMS);

		actionGroupsEClass = createEClass(ACTION_GROUPS);
		createEReference(actionGroupsEClass, ACTION_GROUPS__GROUPS);

		actionsEClass = createEClass(ACTIONS);
		createEReference(actionsEClass, ACTIONS__ACTIONS);

		actionGroupEClass = createEClass(ACTION_GROUP);
		createEReference(actionGroupEClass, ACTION_GROUP__ACTIONS);

		actionEClass = createEClass(ACTION);
		createEAttribute(actionEClass, ACTION__PROVIDER);
		createEReference(actionEClass, ACTION__PROPERTY);
		createEAttribute(actionEClass, ACTION__MODE);
		createEAttribute(actionEClass, ACTION__ID);
		createEAttribute(actionEClass, ACTION__PROPERTY_VALUE);
		createEAttribute(actionEClass, ACTION__TEMPLATE_NAME);
		createEAttribute(actionEClass, ACTION__DELEGATE);
		createEAttribute(actionEClass, ACTION__ACCELERATOR);
		createEReference(actionEClass, ACTION__PROPERTY_TYPE);

		editPolicyRoleEClass = createEClass(EDIT_POLICY_ROLE);
		createEAttribute(editPolicyRoleEClass, EDIT_POLICY_ROLE__MODE);
		createEAttribute(editPolicyRoleEClass, EDIT_POLICY_ROLE__ROLE);
		createEAttribute(editPolicyRoleEClass, EDIT_POLICY_ROLE__IMPLEMENTATION_CLASS);

		menuItemEClass = createEClass(MENU_ITEM);
		createEReference(menuItemEClass, MENU_ITEM__PARENT);
		createEReference(menuItemEClass, MENU_ITEM__GROUPS);

		// Create enums
		menuTypeEEnum = createEEnum(MENU_TYPE);
		editorModeEEnum = createEEnum(EDITOR_MODE);
		editPolicyRoleTypeEEnum = createEEnum(EDIT_POLICY_ROLE_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		MetaModelPackage theMetaModelPackage = (MetaModelPackage)EPackage.Registry.INSTANCE.getEPackage(MetaModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		widgetMenuEClass.getESuperTypes().add(theMetaModelPackage.getNamedType());
		actionGroupEClass.getESuperTypes().add(theMetaModelPackage.getNamedType());
		actionEClass.getESuperTypes().add(theMetaModelPackage.getNamedType());
		menuItemEClass.getESuperTypes().add(theMetaModelPackage.getNamedType());

		// Initialize classes and features; add operations and parameters
		initEClass(uiModelEClass, UIModel.class, "UIModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUIModel_Palette(), this.getPalette(), null, "palette", null, 0, -1, UIModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUIModel_Renderers(), this.getRenderers(), null, "renderers", null, 0, 1, UIModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUIModel_PaletteGroupItems(), this.getPaletteGroupItem(), null, "paletteGroupItems", null, 0, -1, UIModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUIModel_DefaultPalette(), this.getPalette(), null, "defaultPalette", null, 0, 1, UIModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUIModel_Menus(), this.getMenus(), null, "menus", null, 0, 1, UIModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUIModel_ActionGroups(), this.getActionGroups(), null, "actionGroups", null, 0, 1, UIModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUIModel_Actions(), this.getActions(), null, "actions", null, 0, -1, UIModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paletteEClass, Palette.class, "Palette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPalette_Groups(), this.getPaletteGroup(), null, "groups", null, 0, -1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPalette_WidgetType(), theMetaModelPackage.getWidgetType(), null, "widgetType", null, 0, 1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPalette_PropertyType(), theMetaModelPackage.getPropertyType(), null, "propertyType", null, 0, 1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPalette_PropertyValue(), ecorePackage.getEString(), "propertyValue", null, 0, 1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paletteGroupEClass, PaletteGroup.class, "PaletteGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaletteGroup_Label(), ecorePackage.getEString(), "label", null, 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaletteGroup_Items(), this.getPaletteGroupItem(), null, "items", null, 0, -1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_Expanded(), ecorePackage.getEBoolean(), "expanded", null, 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paletteGroupItemEClass, PaletteGroupItem.class, "PaletteGroupItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaletteGroupItem_SmallImage(), ecorePackage.getEString(), "smallImage", null, 0, 1, PaletteGroupItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroupItem_LargeImage(), ecorePackage.getEString(), "largeImage", null, 0, 1, PaletteGroupItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroupItem_Label(), ecorePackage.getEString(), "label", null, 0, 1, PaletteGroupItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroupItem_Tooltip(), ecorePackage.getEString(), "tooltip", null, 0, 1, PaletteGroupItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaletteGroupItem_WidgetTemplate(), theMetaModelPackage.getWidgetTemplate(), null, "widgetTemplate", null, 0, 1, PaletteGroupItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroupItem_Name(), ecorePackage.getEString(), "name", null, 0, 1, PaletteGroupItem.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(renderersEClass, Renderers.class, "Renderers", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRenderers_RendererInfos(), this.getRendererInfo(), null, "rendererInfos", null, 0, -1, Renderers.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rendererInfoEClass, RendererInfo.class, "RendererInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRendererInfo_WidgetType(), theMetaModelPackage.getWidgetType(), null, "widgetType", null, 0, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_Figure(), ecorePackage.getEString(), "figure", null, 0, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_EditPart(), ecorePackage.getEString(), "editPart", null, 0, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_DesignWidgetSpacing(), ecorePackage.getEInt(), "designWidgetSpacing", "8", 1, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_PreviewWidgetSpacing(), ecorePackage.getEInt(), "previewWidgetSpacing", "4", 1, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_RequestHandler(), ecorePackage.getEString(), "requestHandler", null, 0, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_DirectEditMode(), this.getEditorMode(), "directEditMode", "None", 0, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRendererInfo_Roles(), this.getEditPolicyRole(), null, "roles", null, 0, -1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_DirectEditManager(), ecorePackage.getEString(), "directEditManager", null, 0, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_DeleteCommand(), ecorePackage.getEString(), "deleteCommand", "", 0, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRendererInfo_DragTracker(), ecorePackage.getEString(), "dragTracker", null, 0, 1, RendererInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menusEClass, Menus.class, "Menus", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMenus_Menus(), this.getWidgetMenu(), null, "menus", null, 0, -1, Menus.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(widgetMenuEClass, WidgetMenu.class, "WidgetMenu", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWidgetMenu_Type(), this.getMenuType(), "type", null, 0, 1, WidgetMenu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidgetMenu_PropertyValue(), ecorePackage.getEString(), "propertyValue", null, 0, 1, WidgetMenu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetMenu_Parent(), this.getWidgetMenu(), null, "parent", null, 0, 1, WidgetMenu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetMenu_PropertyType(), theMetaModelPackage.getPropertyType(), null, "propertyType", null, 0, 1, WidgetMenu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetMenu_WidgetTypes(), theMetaModelPackage.getWidgetType(), null, "widgetTypes", null, 0, -1, WidgetMenu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetMenu_MenuItems(), this.getMenuItem(), null, "menuItems", null, 0, -1, WidgetMenu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionGroupsEClass, ActionGroups.class, "ActionGroups", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActionGroups_Groups(), this.getActionGroup(), null, "groups", null, 0, -1, ActionGroups.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionsEClass, Actions.class, "Actions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActions_Actions(), this.getAction(), null, "actions", null, 0, -1, Actions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionGroupEClass, ActionGroup.class, "ActionGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActionGroup_Actions(), this.getAction(), null, "actions", null, 0, -1, ActionGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionEClass, Action.class, "Action", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAction_Provider(), ecorePackage.getEString(), "provider", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAction_Property(), theMetaModelPackage.getPropertyType(), null, "property", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_Mode(), this.getEditorMode(), "mode", "DesignMode", 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_Id(), ecorePackage.getEString(), "id", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_PropertyValue(), ecorePackage.getEString(), "propertyValue", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_TemplateName(), ecorePackage.getEString(), "templateName", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_Delegate(), ecorePackage.getEString(), "delegate", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_Accelerator(), ecorePackage.getEString(), "accelerator", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAction_PropertyType(), theMetaModelPackage.getPropertyType(), null, "propertyType", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(editPolicyRoleEClass, EditPolicyRole.class, "EditPolicyRole", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEditPolicyRole_Mode(), this.getEditorMode(), "mode", null, 0, 1, EditPolicyRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEditPolicyRole_Role(), this.getEditPolicyRoleType(), "role", null, 0, 1, EditPolicyRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEditPolicyRole_ImplementationClass(), ecorePackage.getEString(), "implementationClass", null, 0, 1, EditPolicyRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menuItemEClass, MenuItem.class, "MenuItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMenuItem_Parent(), this.getMenuItem(), null, "parent", null, 0, 1, MenuItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMenuItem_Groups(), this.getActionGroup(), null, "groups", null, 0, -1, MenuItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(menuTypeEEnum, MenuType.class, "MenuType");
		addEEnumLiteral(menuTypeEEnum, MenuType.CONTEXTUAL);
		addEEnumLiteral(menuTypeEEnum, MenuType.TOOLBAR);

		initEEnum(editorModeEEnum, EditorMode.class, "EditorMode");
		addEEnumLiteral(editorModeEEnum, EditorMode.ALL);
		addEEnumLiteral(editorModeEEnum, EditorMode.DESIGN_MODE);
		addEEnumLiteral(editorModeEEnum, EditorMode.PREVIEW_MODE);
		addEEnumLiteral(editorModeEEnum, EditorMode.NONE);

		initEEnum(editPolicyRoleTypeEEnum, EditPolicyRoleType.class, "EditPolicyRoleType");
		addEEnumLiteral(editPolicyRoleTypeEEnum, EditPolicyRoleType.DIRECT_EDIT_POLICY);

		// Create resource
		createResource(eNS_URI);
	}

} //UIModelPackageImpl
