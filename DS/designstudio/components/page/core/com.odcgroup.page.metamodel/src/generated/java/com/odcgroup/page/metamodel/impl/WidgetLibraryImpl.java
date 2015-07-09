/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.NamedTypeUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Widget Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetLibraryImpl#getWidgetTypes <em>Widget Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetLibraryImpl#getPropertyTypes <em>Property Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetLibraryImpl#getWidgetTemplates <em>Widget Templates</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WidgetLibraryImpl extends NamedTypeImpl implements WidgetLibrary {
	
	/**
	 * The cached value of the '{@link #getWidgetTypes() <em>Widget Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<WidgetType> widgetTypes;

	/**
	 * The cached value of the '{@link #getPropertyTypes() <em>Property Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyType> propertyTypes;

	/**
	 * The cached value of the '{@link #getWidgetTemplates() <em>Widget Templates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetTemplates()
	 * @generated
	 * @ordered
	 */
	protected EList<WidgetTemplate> widgetTemplates;

	/**
	 * <!-- begin-user-doc -->
	 * Constructor
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WidgetLibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * Gets the eStatic class
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.WIDGET_LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * Gets the widget types
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WidgetType> getWidgetTypes() {
		if (widgetTypes == null) {
			widgetTypes = new EObjectContainmentWithInverseEList<WidgetType>(WidgetType.class, this, MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES, MetaModelPackage.WIDGET_TYPE__LIBRARY);
		}
		return widgetTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Gets the property types.
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyType> getPropertyTypes() {
		if (propertyTypes == null) {
			propertyTypes = new EObjectContainmentWithInverseEList<PropertyType>(PropertyType.class, this, MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES, MetaModelPackage.PROPERTY_TYPE__LIBRARY);
		}
		return propertyTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Gets the widget templates.
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WidgetTemplate> getWidgetTemplates() {
		if (widgetTemplates == null) {
			widgetTemplates = new EObjectContainmentEList<WidgetTemplate>(WidgetTemplate.class, this, MetaModelPackage.WIDGET_LIBRARY__WIDGET_TEMPLATES);
		}
		return widgetTemplates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getWidgetTypes()).basicAdd(otherEnd, msgs);
			case MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPropertyTypes()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES:
				return ((InternalEList<?>)getWidgetTypes()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES:
				return ((InternalEList<?>)getPropertyTypes()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TEMPLATES:
				return ((InternalEList<?>)getWidgetTemplates()).basicRemove(otherEnd, msgs);
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
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES:
				return getWidgetTypes();
			case MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES:
				return getPropertyTypes();
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TEMPLATES:
				return getWidgetTemplates();
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
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES:
				getWidgetTypes().clear();
				getWidgetTypes().addAll((Collection<? extends WidgetType>)newValue);
				return;
			case MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES:
				getPropertyTypes().clear();
				getPropertyTypes().addAll((Collection<? extends PropertyType>)newValue);
				return;
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TEMPLATES:
				getWidgetTemplates().clear();
				getWidgetTemplates().addAll((Collection<? extends WidgetTemplate>)newValue);
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
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES:
				getWidgetTypes().clear();
				return;
			case MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES:
				getPropertyTypes().clear();
				return;
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TEMPLATES:
				getWidgetTemplates().clear();
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
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES:
				return widgetTypes != null && !widgetTypes.isEmpty();
			case MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES:
				return propertyTypes != null && !propertyTypes.isEmpty();
			case MetaModelPackage.WIDGET_LIBRARY__WIDGET_TEMPLATES:
				return widgetTemplates != null && !widgetTemplates.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Finds the WidgetType specified by its name.
	 * 
	 * @param name The name of the WidgetType
	 * @return WidgetType The WidgetType or null if no WidgetType of this name was found
	 */
	public WidgetType findWidgetType(String name) {
		return (WidgetType) NamedTypeUtils.findByName(getWidgetTypes(), name);
	}
	
	/**
	 * Finds the WidgetType specified by its name.
	 * 
	 * @param name The name of the WidgetType
	 * @param ignoreCase True if we should ignore the case in the comparison
	 * @return WidgetType The WidgetType or null if no WidgetType of this name was found
	 */
	public WidgetType findWidgetType(String name, boolean ignoreCase) {
		return (WidgetType) NamedTypeUtils.findByName(getWidgetTypes(), name, ignoreCase);
	}

	/**
	 * Finds the WidgetTemplate specified by its name.
	 * 
	 * @param name The name of the WidgetTemplate
	 * @return WidgetTemplate The WidgetTemplate or null if no WidgetTemplate of this name was found
	 */
	public WidgetTemplate findWidgetTemplate(String name) {
		return (WidgetTemplate) NamedTypeUtils.findByName(getWidgetTemplates(), name);
	}
	
	Map<String, PropertyType> propertyTypeMap = null;
	
	/**
	 * Finds the PropertyType specified by its name.
	 * 
	 * @param name The name of the PropertyType
	 * @return PropertyType The PropertyType or null if no PropertyType of this name was found
	 */
	public PropertyType findPropertyType(String name) {
		List<PropertyType> properties = getPropertyTypes();
		if (propertyTypeMap == null) {
			propertyTypeMap = new HashMap<String, PropertyType>();
			for (PropertyType pt : properties) {
				propertyTypeMap.put(pt.getName(), pt);
			}
		}
		return propertyTypeMap.get(name);
	}
	
	/**
	 * Finds the PropertyType specified by its name.
	 * 
	 * @param name The name of the PropertyType
	 * @param ignoreCase True if we should ignore the case in the comparison
	 * @return PropertyType The PropertyType or null if no WidgetType of this name was found
	 */
	public PropertyType findPropertyType(String name, boolean ignoreCase) {
		return (PropertyType) NamedTypeUtils.findByName(getPropertyTypes(), name, ignoreCase);
	}

} //WidgetLibraryImpl