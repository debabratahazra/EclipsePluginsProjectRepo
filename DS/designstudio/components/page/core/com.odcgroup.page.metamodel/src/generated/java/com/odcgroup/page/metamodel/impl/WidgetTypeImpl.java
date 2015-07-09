/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.NamedTypeUtils;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Widget Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#getPropertyTypes <em>Property Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#getLibrary <em>Library</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#getExcludedPropertyTypes <em>Excluded Property Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#getPropertyFilterClass <em>Property Filter Class</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#isDerivable <em>Derivable</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#isStrictAccountability <em>Strict Accountability</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#isDomainWidget <em>Domain Widget</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#isCanDropDomainElement <em>Can Drop Domain Element</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl#isRichtext <em>Richtext</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WidgetTypeImpl extends NamedTypeImpl implements WidgetType {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected WidgetType parent;

	/**
	 * The cached value of the '{@link #getPropertyTypes() <em>Property Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyType> propertyTypes;

	/**
	 * The cached value of the '{@link #getExcludedPropertyTypes() <em>Excluded Property Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludedPropertyTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyType> excludedPropertyTypes;
	
	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropertyFilterClass() <em>Property Filter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyFilterClass()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_FILTER_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyFilterClass() <em>Property Filter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyFilterClass()
	 * @generated
	 * @ordered
	 */
	protected String propertyFilterClass = PROPERTY_FILTER_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #isDerivable() <em>Derivable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDerivable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DERIVABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDerivable() <em>Derivable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDerivable()
	 * @generated
	 * @ordered
	 */
	protected boolean derivable = DERIVABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #isStrictAccountability() <em>Strict Accountability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrictAccountability()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRICT_ACCOUNTABILITY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStrictAccountability() <em>Strict Accountability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrictAccountability()
	 * @generated
	 * @ordered
	 */
	protected boolean strictAccountability = STRICT_ACCOUNTABILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isDomainWidget() <em>Domain Widget</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDomainWidget()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DOMAIN_WIDGET_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDomainWidget() <em>Domain Widget</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDomainWidget()
	 * @generated
	 * @ordered
	 */
	protected boolean domainWidget = DOMAIN_WIDGET_EDEFAULT;

	/**
	 * The default value of the '{@link #isCanDropDomainElement() <em>Can Drop Domain Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCanDropDomainElement()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CAN_DROP_DOMAIN_ELEMENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCanDropDomainElement() <em>Can Drop Domain Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCanDropDomainElement()
	 * @generated
	 * @ordered
	 */
	protected boolean canDropDomainElement = CAN_DROP_DOMAIN_ELEMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isRichtext() <em>Richtext</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRichtext()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RICHTEXT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRichtext() <em>Richtext</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRichtext()
	 * @generated
	 * @ordered
	 */
	protected boolean richtext = RICHTEXT_EDEFAULT;

	/** 
	 * A cache of all the PropertyTypes declared within this WidgetTypes and its ancestors.
	 * Excluded PropertyTypes are not included in this Map.
	 */
	private Map<String, PropertyType> allPropertyTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WidgetTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.WIDGET_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (WidgetType)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.WIDGET_TYPE__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newParent
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(WidgetType newParent) {
		WidgetType oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyType> getPropertyTypes() {
		if (propertyTypes == null) {
			propertyTypes = new EObjectResolvingEList<PropertyType>(PropertyType.class, this, MetaModelPackage.WIDGET_TYPE__PROPERTY_TYPES);
		}
		return propertyTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetLibrary
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetLibrary getLibrary() {
		if (eContainerFeatureID() != MetaModelPackage.WIDGET_TYPE__LIBRARY) return null;
		return (WidgetLibrary)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newLibrary
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLibrary(WidgetLibrary newLibrary, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newLibrary, MetaModelPackage.WIDGET_TYPE__LIBRARY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newLibrary
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLibrary(WidgetLibrary newLibrary) {
		if (newLibrary != eInternalContainer() || (eContainerFeatureID() != MetaModelPackage.WIDGET_TYPE__LIBRARY && newLibrary != null)) {
			if (EcoreUtil.isAncestor(this, newLibrary))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLibrary != null)
				msgs = ((InternalEObject)newLibrary).eInverseAdd(this, MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES, WidgetLibrary.class, msgs);
			msgs = basicSetLibrary(newLibrary, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__LIBRARY, newLibrary, newLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyType> getExcludedPropertyTypes() {
		if (excludedPropertyTypes == null) {
			excludedPropertyTypes = new EObjectResolvingEList<PropertyType>(PropertyType.class, this, MetaModelPackage.WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES);
		}
		return excludedPropertyTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getDisplayName() {
		if (StringUtils.isEmpty(displayName)) {
			return getName();
		}
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDisplayName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyFilterClass() {
		return propertyFilterClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newPropertyFilterClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyFilterClass(String newPropertyFilterClass) {
		String oldPropertyFilterClass = propertyFilterClass;
		propertyFilterClass = newPropertyFilterClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__PROPERTY_FILTER_CLASS, oldPropertyFilterClass, propertyFilterClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDerivable() {
		return derivable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDerivable(boolean newDerivable) {
		boolean oldDerivable = derivable;
		derivable = newDerivable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__DERIVABLE, oldDerivable, derivable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStrictAccountability() {
		return strictAccountability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrictAccountability(boolean newStrictAccountability) {
		boolean oldStrictAccountability = strictAccountability;
		strictAccountability = newStrictAccountability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__STRICT_ACCOUNTABILITY, oldStrictAccountability, strictAccountability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDomainWidget() {
		return domainWidget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainWidget(boolean newDomainWidget) {
		boolean oldDomainWidget = domainWidget;
		domainWidget = newDomainWidget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__DOMAIN_WIDGET, oldDomainWidget, domainWidget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCanDropDomainElement() {
		return canDropDomainElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCanDropDomainElement(boolean newCanDropDomainElement) {
		boolean oldCanDropDomainElement = canDropDomainElement;
		canDropDomainElement = newCanDropDomainElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT, oldCanDropDomainElement, canDropDomainElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRichtext() {
		return richtext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRichtext(boolean newRichtext) {
		boolean oldRichtext = richtext;
		richtext = newRichtext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_TYPE__RICHTEXT, oldRichtext, richtext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.WIDGET_TYPE__LIBRARY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetLibrary((WidgetLibrary)otherEnd, msgs);
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
			case MetaModelPackage.WIDGET_TYPE__LIBRARY:
				return basicSetLibrary(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case MetaModelPackage.WIDGET_TYPE__LIBRARY:
				return eInternalContainer().eInverseRemove(this, MetaModelPackage.WIDGET_LIBRARY__WIDGET_TYPES, WidgetLibrary.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * Gets all the PropertyTypes of this Widget. This includes the PropertyTypes
	 * of all the supertypes (parents) of this WidgetType. Excluded PropertyTypes
	 * are not added to the Map. Note that this can be fairly complex. In order
	 * to determine if we should include / exclude a PropertyType we remount the
	 * ancestor tree looking for the excluded PropertyType. If the excluded PropertyType
	 * has a larger index, in other words, is defined in a more distant ancestor
	 * than the PropertyType is included in the result otherwise it is excluded.
	 * 
	 * For example:
	 * 
	 * GreatGrandparent - Include PropertyType
	 * GreatParent - Exclude PropertyType
	 * Parent - Not defined
	 * Child - Not defined
	 * In this case the PropertyType is EXCLUDED from the child.
	 * 
	 * GreatGrandparent - Include PropertyType
	 * GreatParent - Exclude PropertyType
	 * Parent - Include PropertyType
	 * Child - Not defined
	 * In this case the PropertyType is INCLUDED from the child.
	 * 
	 * @return Map An unmodifiable Map of PropertyTypes
	 *		Key - Name of the PropertyType
	 *		Value - The PropertyType
	 */
	public Map<String, PropertyType> getAllPropertyTypes() {
		if (allPropertyTypes == null) {
			allPropertyTypes = new HashMap<String, PropertyType>();
			WidgetType wt = this;
			int parentIndex = 0;
			while (wt != null) {
				addPropertyTypesToMap(allPropertyTypes, wt, parentIndex);
				wt = wt.getParent();
				parentIndex++;
			}
		}
		return Collections.unmodifiableMap(allPropertyTypes);
	}
	
	/**
	 * Adds the PropertyTypes of WidgetType to the Map.
	 * 
	 * @param map The Map to add the PropertyTypes to
	 * @param type The WidgetType
	 * @param includeParentIndex Indicates how far we have remounted in the ancestor tree
	 * 		0 - This WidgetType
	 * 		1 - Parent WidgetType
	 * 		2 - GrandParent WidgetType etc.
	 */
	private void addPropertyTypesToMap(Map<String, PropertyType> map, WidgetType type, int includeParentIndex) {
		for (Iterator it = type.getPropertyTypes().iterator(); it.hasNext();) {
			PropertyType pt = (PropertyType) it.next();
			
			if (map.containsKey(pt.getName())) {
				// The PropertyType has already been included
				continue;
			}
			
			// If the PropertyType has been included (-1) we still need to determine
			// whether is was excluded at a higher level and then RE-included later
			int excludeParentIndex = getExcludedPropertyTypeParentIndex(pt);
			if ((excludeParentIndex == -1) || includeParentIndex <= excludeParentIndex) {
				map.put(pt.getName(), pt);
			}
		}
	}	
	
	/**
	 * Calculates how far we have to remount into the ancestor tree to find
	 * the excluded PropertyType. Returns -1 if the PropertyType cannot be found.
	 * 
	 * @param propertyType The PropertyType to look for
	 * @return int The index into the ancestor tree
	 */
	private int getExcludedPropertyTypeParentIndex(PropertyType propertyType) {
		int index = 0;
		WidgetType wt = this;
		while (wt != null) {
			PropertyType pt = (PropertyType) NamedTypeUtils.findByName(wt.getExcludedPropertyTypes(), propertyType.getName());
			if (pt != null) {
				return index;
			}
			wt = wt.getParent();
			index++;
		}
		
		// Not found
		return -1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.WIDGET_TYPE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_TYPES:
				return getPropertyTypes();
			case MetaModelPackage.WIDGET_TYPE__LIBRARY:
				return getLibrary();
			case MetaModelPackage.WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES:
				return getExcludedPropertyTypes();
			case MetaModelPackage.WIDGET_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_FILTER_CLASS:
				return getPropertyFilterClass();
			case MetaModelPackage.WIDGET_TYPE__DERIVABLE:
				return isDerivable();
			case MetaModelPackage.WIDGET_TYPE__STRICT_ACCOUNTABILITY:
				return isStrictAccountability();
			case MetaModelPackage.WIDGET_TYPE__DOMAIN_WIDGET:
				return isDomainWidget();
			case MetaModelPackage.WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT:
				return isCanDropDomainElement();
			case MetaModelPackage.WIDGET_TYPE__RICHTEXT:
				return isRichtext();
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
			case MetaModelPackage.WIDGET_TYPE__PARENT:
				setParent((WidgetType)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_TYPES:
				getPropertyTypes().clear();
				getPropertyTypes().addAll((Collection<? extends PropertyType>)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__LIBRARY:
				setLibrary((WidgetLibrary)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES:
				getExcludedPropertyTypes().clear();
				getExcludedPropertyTypes().addAll((Collection<? extends PropertyType>)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_FILTER_CLASS:
				setPropertyFilterClass((String)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__DERIVABLE:
				setDerivable((Boolean)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__STRICT_ACCOUNTABILITY:
				setStrictAccountability((Boolean)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__DOMAIN_WIDGET:
				setDomainWidget((Boolean)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT:
				setCanDropDomainElement((Boolean)newValue);
				return;
			case MetaModelPackage.WIDGET_TYPE__RICHTEXT:
				setRichtext((Boolean)newValue);
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
			case MetaModelPackage.WIDGET_TYPE__PARENT:
				setParent((WidgetType)null);
				return;
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_TYPES:
				getPropertyTypes().clear();
				return;
			case MetaModelPackage.WIDGET_TYPE__LIBRARY:
				setLibrary((WidgetLibrary)null);
				return;
			case MetaModelPackage.WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES:
				getExcludedPropertyTypes().clear();
				return;
			case MetaModelPackage.WIDGET_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_FILTER_CLASS:
				setPropertyFilterClass(PROPERTY_FILTER_CLASS_EDEFAULT);
				return;
			case MetaModelPackage.WIDGET_TYPE__DERIVABLE:
				setDerivable(DERIVABLE_EDEFAULT);
				return;
			case MetaModelPackage.WIDGET_TYPE__STRICT_ACCOUNTABILITY:
				setStrictAccountability(STRICT_ACCOUNTABILITY_EDEFAULT);
				return;
			case MetaModelPackage.WIDGET_TYPE__DOMAIN_WIDGET:
				setDomainWidget(DOMAIN_WIDGET_EDEFAULT);
				return;
			case MetaModelPackage.WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT:
				setCanDropDomainElement(CAN_DROP_DOMAIN_ELEMENT_EDEFAULT);
				return;
			case MetaModelPackage.WIDGET_TYPE__RICHTEXT:
				setRichtext(RICHTEXT_EDEFAULT);
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
			case MetaModelPackage.WIDGET_TYPE__PARENT:
				return parent != null;
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_TYPES:
				return propertyTypes != null && !propertyTypes.isEmpty();
			case MetaModelPackage.WIDGET_TYPE__LIBRARY:
				return getLibrary() != null;
			case MetaModelPackage.WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES:
				return excludedPropertyTypes != null && !excludedPropertyTypes.isEmpty();
			case MetaModelPackage.WIDGET_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_FILTER_CLASS:
				return PROPERTY_FILTER_CLASS_EDEFAULT == null ? propertyFilterClass != null : !PROPERTY_FILTER_CLASS_EDEFAULT.equals(propertyFilterClass);
			case MetaModelPackage.WIDGET_TYPE__DERIVABLE:
				return derivable != DERIVABLE_EDEFAULT;
			case MetaModelPackage.WIDGET_TYPE__STRICT_ACCOUNTABILITY:
				return strictAccountability != STRICT_ACCOUNTABILITY_EDEFAULT;
			case MetaModelPackage.WIDGET_TYPE__DOMAIN_WIDGET:
				return domainWidget != DOMAIN_WIDGET_EDEFAULT;
			case MetaModelPackage.WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT:
				return canDropDomainElement != CAN_DROP_DOMAIN_ELEMENT_EDEFAULT;
			case MetaModelPackage.WIDGET_TYPE__RICHTEXT:
				return richtext != RICHTEXT_EDEFAULT;
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
		result.append(" (displayName: ");
		result.append(displayName);
		result.append(", propertyFilterClass: ");
		result.append(propertyFilterClass);
		result.append(", derivable: ");
		result.append(derivable);
		result.append(", strictAccountability: ");
		result.append(strictAccountability);
		result.append(", domainWidget: ");
		result.append(domainWidget);
		result.append(", canDropDomainElement: ");
		result.append(canDropDomainElement);
		result.append(", richtext: ");
		result.append(richtext);
		result.append(')');
		return result.toString();
	}

	/**
	 * Returns true if the WidgetType is an instance of the specified WidgetType.
	 * This method attempts to simulate the instanceof keyword in Java.
	 * 
	 * @param type The WidgetType to test
	 * @return boolean True if the WidgetType is an instance of the specified WidgetType
	 */
	public boolean isInstanceOf(WidgetType type) {
		WidgetType toTest = this;
		while (toTest != null) {
			if (toTest.getName().equals(type.getName())) {
				return true;
			}
			toTest = toTest.getParent();
		}
		
		// Not found
		return false;
	}
	
	/**
	 * Finds the PropertyType given the name.
	 * 
	 * @param name The name of the PropertyType
	 * @param ignoreCase True if we should ignore the case in the comparison
	 * @return PropertyType The PropertyType or null if no PropertyType can be found
	 */
	public PropertyType findPropertyType(String name, boolean ignoreCase) {
		return (PropertyType) NamedTypeUtils.findByName(getAllPropertyTypes().values(), name, ignoreCase);
	}

} //WidgetTypeImpl