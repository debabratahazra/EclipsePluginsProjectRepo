/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import com.odcgroup.page.metamodel.PropertyType;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.uimodel.Palette;
import com.odcgroup.page.uimodel.PaletteGroup;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Palette</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteImpl#getGroups <em>Groups</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteImpl#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteImpl#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteImpl#getPropertyValue <em>Property Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PaletteImpl extends MinimalEObjectImpl.Container implements Palette {
	/**
	 * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<PaletteGroup> groups;

	/**
	 * The cached value of the '{@link #getWidgetType() <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetType()
	 * @generated
	 * @ordered
	 */
	protected WidgetType widgetType;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaletteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.PALETTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PaletteGroup> getGroups() {
		if (groups == null) {
			groups = new EObjectContainmentEList<PaletteGroup>(PaletteGroup.class, this, UIModelPackage.PALETTE__GROUPS);
		}
		return groups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getWidgetType() {
		if (widgetType != null && widgetType.eIsProxy()) {
			InternalEObject oldWidgetType = (InternalEObject)widgetType;
			widgetType = (WidgetType)eResolveProxy(oldWidgetType);
			if (widgetType != oldWidgetType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.PALETTE__WIDGET_TYPE, oldWidgetType, widgetType));
			}
		}
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WudgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetWidgetType() {
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidgetType(WidgetType newWidgetType) {
		WidgetType oldWidgetType = widgetType;
		widgetType = newWidgetType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE__WIDGET_TYPE, oldWidgetType, widgetType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType getPropertyType() {
		if (propertyType != null && propertyType.eIsProxy()) {
			InternalEObject oldPropertyType = (InternalEObject)propertyType;
			propertyType = (PropertyType)eResolveProxy(oldPropertyType);
			if (propertyType != oldPropertyType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.PALETTE__PROPERTY_TYPE, oldPropertyType, propertyType));
			}
		}
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType basicGetPropertyType() {
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyType(PropertyType newPropertyType) {
		PropertyType oldPropertyType = propertyType;
		propertyType = newPropertyType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE__PROPERTY_TYPE, oldPropertyType, propertyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyValue() {
		return propertyValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyValue(String newPropertyValue) {
		String oldPropertyValue = propertyValue;
		propertyValue = newPropertyValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE__PROPERTY_VALUE, oldPropertyValue, propertyValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UIModelPackage.PALETTE__GROUPS:
				return ((InternalEList<?>)getGroups()).basicRemove(otherEnd, msgs);
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
			case UIModelPackage.PALETTE__GROUPS:
				return getGroups();
			case UIModelPackage.PALETTE__WIDGET_TYPE:
				if (resolve) return getWidgetType();
				return basicGetWidgetType();
			case UIModelPackage.PALETTE__PROPERTY_TYPE:
				if (resolve) return getPropertyType();
				return basicGetPropertyType();
			case UIModelPackage.PALETTE__PROPERTY_VALUE:
				return getPropertyValue();
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
			case UIModelPackage.PALETTE__GROUPS:
				getGroups().clear();
				getGroups().addAll((Collection<? extends PaletteGroup>)newValue);
				return;
			case UIModelPackage.PALETTE__WIDGET_TYPE:
				setWidgetType((WidgetType)newValue);
				return;
			case UIModelPackage.PALETTE__PROPERTY_TYPE:
				setPropertyType((PropertyType)newValue);
				return;
			case UIModelPackage.PALETTE__PROPERTY_VALUE:
				setPropertyValue((String)newValue);
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
			case UIModelPackage.PALETTE__GROUPS:
				getGroups().clear();
				return;
			case UIModelPackage.PALETTE__WIDGET_TYPE:
				setWidgetType((WidgetType)null);
				return;
			case UIModelPackage.PALETTE__PROPERTY_TYPE:
				setPropertyType((PropertyType)null);
				return;
			case UIModelPackage.PALETTE__PROPERTY_VALUE:
				setPropertyValue(PROPERTY_VALUE_EDEFAULT);
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
			case UIModelPackage.PALETTE__GROUPS:
				return groups != null && !groups.isEmpty();
			case UIModelPackage.PALETTE__WIDGET_TYPE:
				return widgetType != null;
			case UIModelPackage.PALETTE__PROPERTY_TYPE:
				return propertyType != null;
			case UIModelPackage.PALETTE__PROPERTY_VALUE:
				return PROPERTY_VALUE_EDEFAULT == null ? propertyValue != null : !PROPERTY_VALUE_EDEFAULT.equals(propertyValue);
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
		result.append(" (propertyValue: ");
		result.append(propertyValue);
		result.append(')');
		return result.toString();
	}

} //PaletteImpl