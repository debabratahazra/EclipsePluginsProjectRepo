/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.page.metamodel.NamedType;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Widget Menu</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.WidgetMenu#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.WidgetMenu#getPropertyValue <em>Property Value</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.WidgetMenu#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.WidgetMenu#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.WidgetMenu#getWidgetTypes <em>Widget Types</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.WidgetMenu#getMenuItems <em>Menu Items</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getWidgetMenu()
 * @model
 * @generated
 */
public interface WidgetMenu extends NamedType {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.page.uimodel.MenuType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.odcgroup.page.uimodel.MenuType
	 * @see #setType(MenuType)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getWidgetMenu_Type()
	 * @model
	 * @generated
	 */
	MenuType getType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.WidgetMenu#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.odcgroup.page.uimodel.MenuType
	 * @see #getType()
	 * @generated
	 */
	void setType(MenuType value);

	/**
	 * Returns the value of the '<em><b>Property Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Value</em>' attribute.
	 * @see #setPropertyValue(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getWidgetMenu_PropertyValue()
	 * @model
	 * @generated
	 */
	String getPropertyValue();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.WidgetMenu#getPropertyValue <em>Property Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Value</em>' attribute.
	 * @see #getPropertyValue()
	 * @generated
	 */
	void setPropertyValue(String value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(WidgetMenu)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getWidgetMenu_Parent()
	 * @model
	 * @generated
	 */
	WidgetMenu getParent();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.WidgetMenu#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(WidgetMenu value);

	/**
	 * Returns the value of the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Type</em>' reference.
	 * @see #setPropertyType(PropertyType)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getWidgetMenu_PropertyType()
	 * @model
	 * @generated
	 */
	PropertyType getPropertyType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.WidgetMenu#getPropertyType <em>Property Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Type</em>' reference.
	 * @see #getPropertyType()
	 * @generated
	 */
	void setPropertyType(PropertyType value);

	/**
	 * Returns the value of the '<em><b>Widget Types</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.WidgetType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Types</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Types</em>' reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getWidgetMenu_WidgetTypes()
	 * @model
	 * @generated
	 */
	EList<WidgetType> getWidgetTypes();

	/**
	 * Returns the value of the '<em><b>Menu Items</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.MenuItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menu Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menu Items</em>' containment reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getWidgetMenu_MenuItems()
	 * @model containment="true"
	 * @generated
	 */
	EList<MenuItem> getMenuItems();

} // WidgetMenu
