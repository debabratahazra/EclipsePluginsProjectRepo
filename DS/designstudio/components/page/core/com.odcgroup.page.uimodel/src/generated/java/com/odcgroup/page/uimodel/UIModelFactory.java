/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.uimodel.UIModelPackage
 * @generated
 */
public interface UIModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UIModelFactory eINSTANCE = com.odcgroup.page.uimodel.impl.UIModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>UI Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UI Model</em>'.
	 * @generated
	 */
	UIModel createUIModel();

	/**
	 * Returns a new object of class '<em>Palette</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Palette</em>'.
	 * @generated
	 */
	Palette createPalette();

	/**
	 * Returns a new object of class '<em>Palette Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Palette Group</em>'.
	 * @generated
	 */
	PaletteGroup createPaletteGroup();

	/**
	 * Returns a new object of class '<em>Palette Group Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Palette Group Item</em>'.
	 * @generated
	 */
	PaletteGroupItem createPaletteGroupItem();

	/**
	 * Returns a new object of class '<em>Renderers</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Renderers</em>'.
	 * @generated
	 */
	Renderers createRenderers();

	/**
	 * Returns a new object of class '<em>Renderer Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Renderer Info</em>'.
	 * @generated
	 */
	RendererInfo createRendererInfo();

	/**
	 * Returns a new object of class '<em>Menus</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Menus</em>'.
	 * @generated
	 */
	Menus createMenus();

	/**
	 * Returns a new object of class '<em>Widget Menu</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Widget Menu</em>'.
	 * @generated
	 */
	WidgetMenu createWidgetMenu();

	/**
	 * Returns a new object of class '<em>Action Groups</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Groups</em>'.
	 * @generated
	 */
	ActionGroups createActionGroups();

	/**
	 * Returns a new object of class '<em>Actions</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Actions</em>'.
	 * @generated
	 */
	Actions createActions();

	/**
	 * Returns a new object of class '<em>Action Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Group</em>'.
	 * @generated
	 */
	ActionGroup createActionGroup();

	/**
	 * Returns a new object of class '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action</em>'.
	 * @generated
	 */
	Action createAction();

	/**
	 * Returns a new object of class '<em>Edit Policy Role</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edit Policy Role</em>'.
	 * @generated
	 */
	EditPolicyRole createEditPolicyRole();

	/**
	 * Returns a new object of class '<em>Menu Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Menu Item</em>'.
	 * @generated
	 */
	MenuItem createMenuItem();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UIModelPackage getUIModelPackage();

} //UIModelFactory
