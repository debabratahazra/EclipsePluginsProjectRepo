/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.NamedType;
import com.odcgroup.page.uimodel.*;
import com.odcgroup.page.uimodel.Action;
import com.odcgroup.page.uimodel.ActionGroup;
import com.odcgroup.page.uimodel.ActionGroups;
import com.odcgroup.page.uimodel.Actions;
import com.odcgroup.page.uimodel.EditPolicyRole;
import com.odcgroup.page.uimodel.MenuItem;
import com.odcgroup.page.uimodel.Menus;
import com.odcgroup.page.uimodel.Palette;
import com.odcgroup.page.uimodel.PaletteGroup;
import com.odcgroup.page.uimodel.PaletteGroupItem;
import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.Renderers;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.UIModelPackage;
import com.odcgroup.page.uimodel.WidgetMenu;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * @param <T>
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.uimodel.UIModelPackage
 * @generated
 */
public class UIModelSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static UIModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UIModelSwitch() {
		if (modelPackage == null) {
			modelPackage = UIModelPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * @param theEObject
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * @param theEClass
	 * @param theEObject
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * @param classifierID
	 * @param theEObject
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case UIModelPackage.UI_MODEL: {
				UIModel uiModel = (UIModel)theEObject;
				T result = caseUIModel(uiModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.PALETTE: {
				Palette palette = (Palette)theEObject;
				T result = casePalette(palette);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.PALETTE_GROUP: {
				PaletteGroup paletteGroup = (PaletteGroup)theEObject;
				T result = casePaletteGroup(paletteGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.PALETTE_GROUP_ITEM: {
				PaletteGroupItem paletteGroupItem = (PaletteGroupItem)theEObject;
				T result = casePaletteGroupItem(paletteGroupItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.RENDERERS: {
				Renderers renderers = (Renderers)theEObject;
				T result = caseRenderers(renderers);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.RENDERER_INFO: {
				RendererInfo rendererInfo = (RendererInfo)theEObject;
				T result = caseRendererInfo(rendererInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.MENUS: {
				Menus menus = (Menus)theEObject;
				T result = caseMenus(menus);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.WIDGET_MENU: {
				WidgetMenu widgetMenu = (WidgetMenu)theEObject;
				T result = caseWidgetMenu(widgetMenu);
				if (result == null) result = caseNamedType(widgetMenu);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.ACTION_GROUPS: {
				ActionGroups actionGroups = (ActionGroups)theEObject;
				T result = caseActionGroups(actionGroups);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.ACTIONS: {
				Actions actions = (Actions)theEObject;
				T result = caseActions(actions);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.ACTION_GROUP: {
				ActionGroup actionGroup = (ActionGroup)theEObject;
				T result = caseActionGroup(actionGroup);
				if (result == null) result = caseNamedType(actionGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.ACTION: {
				Action action = (Action)theEObject;
				T result = caseAction(action);
				if (result == null) result = caseNamedType(action);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.EDIT_POLICY_ROLE: {
				EditPolicyRole editPolicyRole = (EditPolicyRole)theEObject;
				T result = caseEditPolicyRole(editPolicyRole);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UIModelPackage.MENU_ITEM: {
				MenuItem menuItem = (MenuItem)theEObject;
				T result = caseMenuItem(menuItem);
				if (result == null) result = caseNamedType(menuItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UI Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UI Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUIModel(UIModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Palette</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Palette</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePalette(Palette object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Palette Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Palette Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaletteGroup(PaletteGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Palette Group Item</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Palette Group Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaletteGroupItem(PaletteGroupItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Renderers</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Renderers</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRenderers(Renderers object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Renderer Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Renderer Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRendererInfo(RendererInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Menus</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Menus</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMenus(Menus object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Widget Menu</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Widget Menu</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWidgetMenu(WidgetMenu object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Groups</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Groups</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionGroups(ActionGroups object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Actions</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Actions</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActions(Actions object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionGroup(ActionGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAction(Action object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Edit Policy Role</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Edit Policy Role</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEditPolicyRole(EditPolicyRole object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Menu Item</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Menu Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMenuItem(MenuItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedType(NamedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //UIModelSwitch
