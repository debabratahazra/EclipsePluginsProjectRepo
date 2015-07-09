/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetLibrary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl#getLibrary <em>Library</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl#getSourceAdapter <em>Source Adapter</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl#getSubCategory <em>Sub Category</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl#isVisibleInDomain <em>Visible In Domain</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyTypeImpl extends NamedTypeImpl implements PropertyType {
	/**
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected DataType dataType;

	/**
	 * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected static final PropertyCategory CATEGORY_EDEFAULT = PropertyCategory.NONE_LITERAL;

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected PropertyCategory category = CATEGORY_EDEFAULT;

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
	 * The default value of the '{@link #getSourceAdapter() <em>Source Adapter</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSourceAdapter()
	 * @generated
	 * @ordered
	 */
    protected static final String SOURCE_ADAPTER_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getSourceAdapter() <em>Source Adapter</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSourceAdapter()
	 * @generated
	 * @ordered
	 */
    protected String sourceAdapter = SOURCE_ADAPTER_EDEFAULT;

    /**
	 * The default value of the '{@link #getSubCategory() <em>Sub Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubCategory()
	 * @generated
	 * @ordered
	 */
	protected static final String SUB_CATEGORY_EDEFAULT = null;

				/**
	 * The cached value of the '{@link #getSubCategory() <em>Sub Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubCategory()
	 * @generated
	 * @ordered
	 */
	protected String subCategory = SUB_CATEGORY_EDEFAULT;

				/**
	 * The default value of the '{@link #isVisibleInDomain() <em>Visible In Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisibleInDomain()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_IN_DOMAIN_EDEFAULT = false;

				/**
	 * The cached value of the '{@link #isVisibleInDomain() <em>Visible In Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisibleInDomain()
	 * @generated
	 * @ordered
	 */
	protected boolean visibleInDomain = VISIBLE_IN_DOMAIN_EDEFAULT;

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.PROPERTY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDefaultValue
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultValue(String newDefaultValue) {
		String oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TYPE__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return DataType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType getDataType() {
		if (dataType != null && dataType.eIsProxy()) {
			InternalEObject oldDataType = (InternalEObject)dataType;
			dataType = (DataType)eResolveProxy(oldDataType);
			if (dataType != oldDataType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.PROPERTY_TYPE__DATA_TYPE, oldDataType, dataType));
			}
		}
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return DataType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType basicGetDataType() {
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDataType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataType(DataType newDataType) {
		DataType oldDataType = dataType;
		dataType = newDataType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TYPE__DATA_TYPE, oldDataType, dataType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetLibrary
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetLibrary getLibrary() {
		if (eContainerFeatureID() != MetaModelPackage.PROPERTY_TYPE__LIBRARY) return null;
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
		msgs = eBasicSetContainer((InternalEObject)newLibrary, MetaModelPackage.PROPERTY_TYPE__LIBRARY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newLibrary
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLibrary(WidgetLibrary newLibrary) {
		if (newLibrary != eInternalContainer() || (eContainerFeatureID() != MetaModelPackage.PROPERTY_TYPE__LIBRARY && newLibrary != null)) {
			if (EcoreUtil.isAncestor(this, newLibrary))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLibrary != null)
				msgs = ((InternalEObject)newLibrary).eInverseAdd(this, MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES, WidgetLibrary.class, msgs);
			msgs = basicSetLibrary(newLibrary, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TYPE__LIBRARY, newLibrary, newLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyCategory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyCategory getCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newCategory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(PropertyCategory newCategory) {
		PropertyCategory oldCategory = category;
		category = newCategory == null ? CATEGORY_EDEFAULT : newCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TYPE__CATEGORY, oldCategory, category));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
     * @return String
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getSourceAdapter() {
		return sourceAdapter;
	}

    /**
	 * <!-- begin-user-doc -->
     * @param newSourceAdapter
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSourceAdapter(String newSourceAdapter) {
		String oldSourceAdapter = sourceAdapter;
		sourceAdapter = newSourceAdapter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TYPE__SOURCE_ADAPTER, oldSourceAdapter, sourceAdapter));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSubCategory() {
		return subCategory;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubCategory(String newSubCategory) {
		String oldSubCategory = subCategory;
		subCategory = newSubCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TYPE__SUB_CATEGORY, oldSubCategory, subCategory));
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVisibleInDomain() {
		return visibleInDomain;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisibleInDomain(boolean newVisibleInDomain) {
		boolean oldVisibleInDomain = visibleInDomain;
		visibleInDomain = newVisibleInDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.PROPERTY_TYPE__VISIBLE_IN_DOMAIN, oldVisibleInDomain, visibleInDomain));
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.PROPERTY_TYPE__LIBRARY:
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
			case MetaModelPackage.PROPERTY_TYPE__LIBRARY:
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
			case MetaModelPackage.PROPERTY_TYPE__LIBRARY:
				return eInternalContainer().eInverseRemove(this, MetaModelPackage.WIDGET_LIBRARY__PROPERTY_TYPES, WidgetLibrary.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.PROPERTY_TYPE__DEFAULT_VALUE:
				return getDefaultValue();
			case MetaModelPackage.PROPERTY_TYPE__DATA_TYPE:
				if (resolve) return getDataType();
				return basicGetDataType();
			case MetaModelPackage.PROPERTY_TYPE__LIBRARY:
				return getLibrary();
			case MetaModelPackage.PROPERTY_TYPE__CATEGORY:
				return getCategory();
			case MetaModelPackage.PROPERTY_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case MetaModelPackage.PROPERTY_TYPE__SOURCE_ADAPTER:
				return getSourceAdapter();
			case MetaModelPackage.PROPERTY_TYPE__SUB_CATEGORY:
				return getSubCategory();
			case MetaModelPackage.PROPERTY_TYPE__VISIBLE_IN_DOMAIN:
				return isVisibleInDomain();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetaModelPackage.PROPERTY_TYPE__DEFAULT_VALUE:
				setDefaultValue((String)newValue);
				return;
			case MetaModelPackage.PROPERTY_TYPE__DATA_TYPE:
				setDataType((DataType)newValue);
				return;
			case MetaModelPackage.PROPERTY_TYPE__LIBRARY:
				setLibrary((WidgetLibrary)newValue);
				return;
			case MetaModelPackage.PROPERTY_TYPE__CATEGORY:
				setCategory((PropertyCategory)newValue);
				return;
			case MetaModelPackage.PROPERTY_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case MetaModelPackage.PROPERTY_TYPE__SOURCE_ADAPTER:
				setSourceAdapter((String)newValue);
				return;
			case MetaModelPackage.PROPERTY_TYPE__SUB_CATEGORY:
				setSubCategory((String)newValue);
				return;
			case MetaModelPackage.PROPERTY_TYPE__VISIBLE_IN_DOMAIN:
				setVisibleInDomain((Boolean)newValue);
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
			case MetaModelPackage.PROPERTY_TYPE__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
				return;
			case MetaModelPackage.PROPERTY_TYPE__DATA_TYPE:
				setDataType((DataType)null);
				return;
			case MetaModelPackage.PROPERTY_TYPE__LIBRARY:
				setLibrary((WidgetLibrary)null);
				return;
			case MetaModelPackage.PROPERTY_TYPE__CATEGORY:
				setCategory(CATEGORY_EDEFAULT);
				return;
			case MetaModelPackage.PROPERTY_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case MetaModelPackage.PROPERTY_TYPE__SOURCE_ADAPTER:
				setSourceAdapter(SOURCE_ADAPTER_EDEFAULT);
				return;
			case MetaModelPackage.PROPERTY_TYPE__SUB_CATEGORY:
				setSubCategory(SUB_CATEGORY_EDEFAULT);
				return;
			case MetaModelPackage.PROPERTY_TYPE__VISIBLE_IN_DOMAIN:
				setVisibleInDomain(VISIBLE_IN_DOMAIN_EDEFAULT);
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
			case MetaModelPackage.PROPERTY_TYPE__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case MetaModelPackage.PROPERTY_TYPE__DATA_TYPE:
				return dataType != null;
			case MetaModelPackage.PROPERTY_TYPE__LIBRARY:
				return getLibrary() != null;
			case MetaModelPackage.PROPERTY_TYPE__CATEGORY:
				return category != CATEGORY_EDEFAULT;
			case MetaModelPackage.PROPERTY_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case MetaModelPackage.PROPERTY_TYPE__SOURCE_ADAPTER:
				return SOURCE_ADAPTER_EDEFAULT == null ? sourceAdapter != null : !SOURCE_ADAPTER_EDEFAULT.equals(sourceAdapter);
			case MetaModelPackage.PROPERTY_TYPE__SUB_CATEGORY:
				return SUB_CATEGORY_EDEFAULT == null ? subCategory != null : !SUB_CATEGORY_EDEFAULT.equals(subCategory);
			case MetaModelPackage.PROPERTY_TYPE__VISIBLE_IN_DOMAIN:
				return visibleInDomain != VISIBLE_IN_DOMAIN_EDEFAULT;
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
		result.append(" (defaultValue: ");
		result.append(defaultValue);
		result.append(", category: ");
		result.append(category);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", sourceAdapter: ");
		result.append(sourceAdapter);
		result.append(", subCategory: ");
		result.append(subCategory);
		result.append(", visibleInDomain: ");
		result.append(visibleInDomain);
		result.append(')');
		return result.toString();
	}

} //PropertyTypeImpl