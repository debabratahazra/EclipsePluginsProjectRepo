/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.impl.NamedTypeImpl;
import com.odcgroup.page.uimodel.MenuItem;
import com.odcgroup.page.uimodel.MenuType;
import com.odcgroup.page.uimodel.UIModelPackage;
import com.odcgroup.page.uimodel.WidgetMenu;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Widget Menu</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.WidgetMenuImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.WidgetMenuImpl#getPropertyValue <em>Property Value</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.WidgetMenuImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.WidgetMenuImpl#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.WidgetMenuImpl#getWidgetTypes <em>Widget Types</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.WidgetMenuImpl#getMenuItems <em>Menu Items</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WidgetMenuImpl extends NamedTypeImpl implements WidgetMenu {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final MenuType TYPE_EDEFAULT = MenuType.CONTEXTUAL;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected MenuType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropertyValue() <em>Property Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyValue()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyValue() <em>Property Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyValue()
	 * @generated
	 * @ordered
	 */
	protected String propertyValue = PROPERTY_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected WidgetMenu parent;

	/**
	 * The cached value of the '{@link #getPropertyType() <em>Property Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyType()
	 * @generated
	 * @ordered
	 */
	protected PropertyType propertyType;

	/**
	 * The cached value of the '{@link #getWidgetTypes() <em>Widget Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<WidgetType> widgetTypes;

	/**
	 * The cached value of the '{@link #getMenuItems() <em>Menu Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMenuItems()
	 * @generated
	 * @ordered
	 */
	protected EList<MenuItem> menuItems;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WidgetMenuImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.WIDGET_MENU;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return MenuType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(MenuType newType) {
		MenuType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.WIDGET_MENU__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyValue() {
		return propertyValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newPropertyValue
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyValue(String newPropertyValue) {
		String oldPropertyValue = propertyValue;
		propertyValue = newPropertyValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.WIDGET_MENU__PROPERTY_VALUE, oldPropertyValue, propertyValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetMenu
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetMenu getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (WidgetMenu)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.WIDGET_MENU__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetMenu
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetMenu basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newParent
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(WidgetMenu newParent) {
		WidgetMenu oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.WIDGET_MENU__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType getPropertyType() {
		if (propertyType != null && propertyType.eIsProxy()) {
			InternalEObject oldPropertyType = (InternalEObject)propertyType;
			propertyType = (PropertyType)eResolveProxy(oldPropertyType);
			if (propertyType != oldPropertyType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.WIDGET_MENU__PROPERTY_TYPE, oldPropertyType, propertyType));
			}
		}
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType basicGetPropertyType() {
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newPropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyType(PropertyType newPropertyType) {
		PropertyType oldPropertyType = propertyType;
		propertyType = newPropertyType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.WIDGET_MENU__PROPERTY_TYPE, oldPropertyType, propertyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return List
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WidgetType> getWidgetTypes() {
		if (widgetTypes == null) {
			widgetTypes = new EObjectResolvingEList<WidgetType>(WidgetType.class, this, UIModelPackage.WIDGET_MENU__WIDGET_TYPES);
		}
		return widgetTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return List
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MenuItem> getMenuItems() {
		if (menuItems == null) {
			menuItems = new EObjectContainmentEList<MenuItem>(MenuItem.class, this, UIModelPackage.WIDGET_MENU__MENU_ITEMS);
		}
		return menuItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UIModelPackage.WIDGET_MENU__MENU_ITEMS:
				return ((InternalEList<?>)getMenuItems()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UIModelPackage.WIDGET_MENU__TYPE:
				return getType();
			case UIModelPackage.WIDGET_MENU__PROPERTY_VALUE:
				return getPropertyValue();
			case UIModelPackage.WIDGET_MENU__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case UIModelPackage.WIDGET_MENU__PROPERTY_TYPE:
				if (resolve) return getPropertyType();
				return basicGetPropertyType();
			case UIModelPackage.WIDGET_MENU__WIDGET_TYPES:
				return getWidgetTypes();
			case UIModelPackage.WIDGET_MENU__MENU_ITEMS:
				return getMenuItems();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case UIModelPackage.WIDGET_MENU__TYPE:
				setType((MenuType)newValue);
				return;
			case UIModelPackage.WIDGET_MENU__PROPERTY_VALUE:
				setPropertyValue((String)newValue);
				return;
			case UIModelPackage.WIDGET_MENU__PARENT:
				setParent((WidgetMenu)newValue);
				return;
			case UIModelPackage.WIDGET_MENU__PROPERTY_TYPE:
				setPropertyType((PropertyType)newValue);
				return;
			case UIModelPackage.WIDGET_MENU__WIDGET_TYPES:
				getWidgetTypes().clear();
				getWidgetTypes().addAll((Collection<? extends WidgetType>)newValue);
				return;
			case UIModelPackage.WIDGET_MENU__MENU_ITEMS:
				getMenuItems().clear();
				getMenuItems().addAll((Collection<? extends MenuItem>)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case UIModelPackage.WIDGET_MENU__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case UIModelPackage.WIDGET_MENU__PROPERTY_VALUE:
				setPropertyValue(PROPERTY_VALUE_EDEFAULT);
				return;
			case UIModelPackage.WIDGET_MENU__PARENT:
				setParent((WidgetMenu)null);
				return;
			case UIModelPackage.WIDGET_MENU__PROPERTY_TYPE:
				setPropertyType((PropertyType)null);
				return;
			case UIModelPackage.WIDGET_MENU__WIDGET_TYPES:
				getWidgetTypes().clear();
				return;
			case UIModelPackage.WIDGET_MENU__MENU_ITEMS:
				getMenuItems().clear();
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case UIModelPackage.WIDGET_MENU__TYPE:
				return type != TYPE_EDEFAULT;
			case UIModelPackage.WIDGET_MENU__PROPERTY_VALUE:
				return PROPERTY_VALUE_EDEFAULT == null ? propertyValue != null : !PROPERTY_VALUE_EDEFAULT.equals(propertyValue);
			case UIModelPackage.WIDGET_MENU__PARENT:
				return parent != null;
			case UIModelPackage.WIDGET_MENU__PROPERTY_TYPE:
				return propertyType != null;
			case UIModelPackage.WIDGET_MENU__WIDGET_TYPES:
				return widgetTypes != null && !widgetTypes.isEmpty();
			case UIModelPackage.WIDGET_MENU__MENU_ITEMS:
				return menuItems != null && !menuItems.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(", propertyValue: ");
		result.append(propertyValue);
		result.append(')');
		return result.toString();
	}

} //WidgetMenuImpl
