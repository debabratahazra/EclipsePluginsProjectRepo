/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.EventTemplate;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.PropertyTemplate;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Widget Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTemplateImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTemplateImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTemplateImpl#getPropertyTemplates <em>Property Templates</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTemplateImpl#getEventTemplates <em>Event Templates</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WidgetTemplateImpl extends NamedTypeImpl implements WidgetTemplate {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected WidgetType type;

	/**
	 * The cached value of the '{@link #getContents() <em>Contents</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContents()
	 * @generated
	 * @ordered
	 */
	protected EList<WidgetTemplate> contents;

	/**
	 * The cached value of the '{@link #getPropertyTemplates() <em>Property Templates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyTemplates()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyTemplate> propertyTemplates;

	/**
	 * The cached value of the '{@link #getEventTemplates() <em>Event Templates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventTemplates()
	 * @generated
	 * @ordered
	 */
	protected EList<EventTemplate> eventTemplates;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WidgetTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.WIDGET_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (WidgetType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.WIDGET_TEMPLATE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(WidgetType newType) {
		WidgetType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TEMPLATE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WidgetTemplate> getContents() {
		if (contents == null) {
			contents = new EObjectContainmentEList<WidgetTemplate>(WidgetTemplate.class, this, MetaModelPackage.WIDGET_TEMPLATE__CONTENTS);
		}
		return contents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyTemplate> getPropertyTemplates() {
		if (propertyTemplates == null) {
			propertyTemplates = new EObjectContainmentEList<PropertyTemplate>(PropertyTemplate.class, this, MetaModelPackage.WIDGET_TEMPLATE__PROPERTY_TEMPLATES);
		}
		return propertyTemplates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EventTemplate> getEventTemplates() {
		if (eventTemplates == null) {
			eventTemplates = new EObjectContainmentEList<EventTemplate>(EventTemplate.class, this, MetaModelPackage.WIDGET_TEMPLATE__EVENT_TEMPLATES);
		}
		return eventTemplates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.WIDGET_TEMPLATE__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.WIDGET_TEMPLATE__PROPERTY_TEMPLATES:
				return ((InternalEList<?>)getPropertyTemplates()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.WIDGET_TEMPLATE__EVENT_TEMPLATES:
				return ((InternalEList<?>)getEventTemplates()).basicRemove(otherEnd, msgs);
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
			case MetaModelPackage.WIDGET_TEMPLATE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case MetaModelPackage.WIDGET_TEMPLATE__CONTENTS:
				return getContents();
			case MetaModelPackage.WIDGET_TEMPLATE__PROPERTY_TEMPLATES:
				return getPropertyTemplates();
			case MetaModelPackage.WIDGET_TEMPLATE__EVENT_TEMPLATES:
				return getEventTemplates();
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
			case MetaModelPackage.WIDGET_TEMPLATE__TYPE:
				setType((WidgetType)newValue);
				return;
			case MetaModelPackage.WIDGET_TEMPLATE__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends WidgetTemplate>)newValue);
				return;
			case MetaModelPackage.WIDGET_TEMPLATE__PROPERTY_TEMPLATES:
				getPropertyTemplates().clear();
				getPropertyTemplates().addAll((Collection<? extends PropertyTemplate>)newValue);
				return;
			case MetaModelPackage.WIDGET_TEMPLATE__EVENT_TEMPLATES:
				getEventTemplates().clear();
				getEventTemplates().addAll((Collection<? extends EventTemplate>)newValue);
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
			case MetaModelPackage.WIDGET_TEMPLATE__TYPE:
				setType((WidgetType)null);
				return;
			case MetaModelPackage.WIDGET_TEMPLATE__CONTENTS:
				getContents().clear();
				return;
			case MetaModelPackage.WIDGET_TEMPLATE__PROPERTY_TEMPLATES:
				getPropertyTemplates().clear();
				return;
			case MetaModelPackage.WIDGET_TEMPLATE__EVENT_TEMPLATES:
				getEventTemplates().clear();
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
			case MetaModelPackage.WIDGET_TEMPLATE__TYPE:
				return type != null;
			case MetaModelPackage.WIDGET_TEMPLATE__CONTENTS:
				return contents != null && !contents.isEmpty();
			case MetaModelPackage.WIDGET_TEMPLATE__PROPERTY_TEMPLATES:
				return propertyTemplates != null && !propertyTemplates.isEmpty();
			case MetaModelPackage.WIDGET_TEMPLATE__EVENT_TEMPLATES:
				return eventTemplates != null && !eventTemplates.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * Gets the property template with the specified name. Returns null
	 * if the PropertyTemplate does not exist.
	 * 
	 * @param name The name of the property template
	 * @return PropertyTemplate The PropertyTemplate
	 */
	public PropertyTemplate getPropertyTemplate(String name) {
		for (PropertyTemplate pt : getPropertyTemplates()) {
			if (name.equalsIgnoreCase(pt.getType().getName())) {
				return pt;
			}
		}
		
		// Not found
		return null;
	}	
	
    /**
     * Sets the value of the Property.
     * 
     * @param propertyName The name of the property
     * @param value The value to set
     */
    public void setPropertyTemplateValue(String propertyName, String value) {
    	PropertyTemplate pt = getPropertyTemplate(propertyName);
    	if (pt == null) {
    		return;
    	}
    	pt.setValue(value);
    }	

} //WidgetTemplateImpl