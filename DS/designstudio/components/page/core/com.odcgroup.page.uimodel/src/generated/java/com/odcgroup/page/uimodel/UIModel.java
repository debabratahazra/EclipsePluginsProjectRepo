/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UI Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.UIModel#getPalette <em>Palette</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.UIModel#getRenderers <em>Renderers</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.UIModel#getPaletteGroupItems <em>Palette Group Items</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.UIModel#getDefaultPalette <em>Default Palette</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.UIModel#getMenus <em>Menus</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.UIModel#getActionGroups <em>Action Groups</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.UIModel#getActions <em>Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getUIModel()
 * @model
 * @generated
 */
public interface UIModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Palette</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.Palette}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palette</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Palette</em>' containment reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getUIModel_Palette()
	 * @model containment="true"
	 * @generated
	 */
	EList<Palette> getPalette();

	/**
	 * Returns the value of the '<em><b>Renderers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Renderers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Renderers</em>' containment reference.
	 * @see #setRenderers(Renderers)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getUIModel_Renderers()
	 * @model containment="true"
	 * @generated
	 */
	Renderers getRenderers();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.UIModel#getRenderers <em>Renderers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Renderers</em>' containment reference.
	 * @see #getRenderers()
	 * @generated
	 */
	void setRenderers(Renderers value);

	/**
	 * Returns the value of the '<em><b>Palette Group Items</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.PaletteGroupItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palette Group Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Palette Group Items</em>' containment reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getUIModel_PaletteGroupItems()
	 * @model containment="true"
	 * @generated
	 */
	EList<PaletteGroupItem> getPaletteGroupItems();

	/**
	 * Returns the value of the '<em><b>Default Palette</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Palette</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Palette</em>' containment reference.
	 * @see #setDefaultPalette(Palette)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getUIModel_DefaultPalette()
	 * @model containment="true"
	 * @generated
	 */
	Palette getDefaultPalette();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.UIModel#getDefaultPalette <em>Default Palette</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Palette</em>' containment reference.
	 * @see #getDefaultPalette()
	 * @generated
	 */
	void setDefaultPalette(Palette value);

	/**
	 * Returns the value of the '<em><b>Menus</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menus</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menus</em>' containment reference.
	 * @see #setMenus(Menus)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getUIModel_Menus()
	 * @model containment="true"
	 * @generated
	 */
	Menus getMenus();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.UIModel#getMenus <em>Menus</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menus</em>' containment reference.
	 * @see #getMenus()
	 * @generated
	 */
	void setMenus(Menus value);

	/**
	 * Returns the value of the '<em><b>Action Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Groups</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Groups</em>' containment reference.
	 * @see #setActionGroups(ActionGroups)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getUIModel_ActionGroups()
	 * @model containment="true"
	 * @generated
	 */
	ActionGroups getActionGroups();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.UIModel#getActionGroups <em>Action Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Groups</em>' containment reference.
	 * @see #getActionGroups()
	 * @generated
	 */
	void setActionGroups(ActionGroups value);

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.Actions}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getUIModel_Actions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Actions> getActions();

	/**
	 * Finds the Palette for the WidgetType. If no Palette can be found
	 * for the WidgetType the default Palette is returned.
	 * 
	 * @param widgetType The WidgetType to find the Palette for
	 * @return Palette The Palette
	 */
	public Palette findPalette(WidgetType widgetType);
	
	/**
	 * @param widgetType
	 * @param propertyType
	 * @param propertyValue
	 * @return
	 */
	public Palette findPalette(WidgetType widgetType, PropertyType propertyType, String propertyValue);

} // UIModel