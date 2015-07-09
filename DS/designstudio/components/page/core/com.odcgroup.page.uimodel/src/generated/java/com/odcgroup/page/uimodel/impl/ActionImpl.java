/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.impl.NamedTypeImpl;
import com.odcgroup.page.uimodel.Action;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getPropertyValue <em>Property Value</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getTemplateName <em>Template Name</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getDelegate <em>Delegate</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getAccelerator <em>Accelerator</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.ActionImpl#getPropertyType <em>Property Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionImpl extends NamedTypeImpl implements Action {
	/**
	 * The default value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected static final String PROVIDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected String provider = PROVIDER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected PropertyType property;

	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final EditorMode MODE_EDEFAULT = EditorMode.DESIGN_MODE;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected EditorMode mode = MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

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
	 * The default value of the '{@link #getTemplateName() <em>Template Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateName()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMPLATE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemplateName() <em>Template Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateName()
	 * @generated
	 * @ordered
	 */
	protected String templateName = TEMPLATE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDelegate() <em>Delegate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelegate()
	 * @generated
	 * @ordered
	 */
	protected static final String DELEGATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDelegate() <em>Delegate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelegate()
	 * @generated
	 * @ordered
	 */
	protected String delegate = DELEGATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAccelerator() <em>Accelerator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccelerator()
	 * @generated
	 * @ordered
	 */
	protected static final String ACCELERATOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAccelerator() <em>Accelerator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccelerator()
	 * @generated
	 * @ordered
	 */
	protected String accelerator = ACCELERATOR_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.ACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newProvider
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(String newProvider) {
		String oldProvider = provider;
		provider = newProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__PROVIDER, oldProvider, provider));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType getProperty() {
		if (property != null && property.eIsProxy()) {
			InternalEObject oldProperty = (InternalEObject)property;
			property = (PropertyType)eResolveProxy(oldProperty);
			if (property != oldProperty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.ACTION__PROPERTY, oldProperty, property));
			}
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType basicGetProperty() {
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newProperty
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperty(PropertyType newProperty) {
		PropertyType oldProperty = property;
		property = newProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__PROPERTY, oldProperty, property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ActionMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditorMode getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMode(EditorMode newMode) {
		EditorMode oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__MODE, oldMode, mode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newId
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__PROPERTY_VALUE, oldPropertyValue, propertyValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newTemplateName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplateName(String newTemplateName) {
		String oldTemplateName = templateName;
		templateName = newTemplateName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__TEMPLATE_NAME, oldTemplateName, templateName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDelegate() {
		return delegate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDelegate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDelegate(String newDelegate) {
		String oldDelegate = delegate;
		delegate = newDelegate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__DELEGATE, oldDelegate, delegate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAccelerator() {
		return accelerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newAccelerator
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccelerator(String newAccelerator) {
		String oldAccelerator = accelerator;
		accelerator = newAccelerator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__ACCELERATOR, oldAccelerator, accelerator));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.ACTION__PROPERTY_TYPE, oldPropertyType, propertyType));
			}
		}
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return
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
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.ACTION__PROPERTY_TYPE, oldPropertyType, propertyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UIModelPackage.ACTION__PROVIDER:
				return getProvider();
			case UIModelPackage.ACTION__PROPERTY:
				if (resolve) return getProperty();
				return basicGetProperty();
			case UIModelPackage.ACTION__MODE:
				return getMode();
			case UIModelPackage.ACTION__ID:
				return getId();
			case UIModelPackage.ACTION__PROPERTY_VALUE:
				return getPropertyValue();
			case UIModelPackage.ACTION__TEMPLATE_NAME:
				return getTemplateName();
			case UIModelPackage.ACTION__DELEGATE:
				return getDelegate();
			case UIModelPackage.ACTION__ACCELERATOR:
				return getAccelerator();
			case UIModelPackage.ACTION__PROPERTY_TYPE:
				if (resolve) return getPropertyType();
				return basicGetPropertyType();
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
			case UIModelPackage.ACTION__PROVIDER:
				setProvider((String)newValue);
				return;
			case UIModelPackage.ACTION__PROPERTY:
				setProperty((PropertyType)newValue);
				return;
			case UIModelPackage.ACTION__MODE:
				setMode((EditorMode)newValue);
				return;
			case UIModelPackage.ACTION__ID:
				setId((String)newValue);
				return;
			case UIModelPackage.ACTION__PROPERTY_VALUE:
				setPropertyValue((String)newValue);
				return;
			case UIModelPackage.ACTION__TEMPLATE_NAME:
				setTemplateName((String)newValue);
				return;
			case UIModelPackage.ACTION__DELEGATE:
				setDelegate((String)newValue);
				return;
			case UIModelPackage.ACTION__ACCELERATOR:
				setAccelerator((String)newValue);
				return;
			case UIModelPackage.ACTION__PROPERTY_TYPE:
				setPropertyType((PropertyType)newValue);
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
			case UIModelPackage.ACTION__PROVIDER:
				setProvider(PROVIDER_EDEFAULT);
				return;
			case UIModelPackage.ACTION__PROPERTY:
				setProperty((PropertyType)null);
				return;
			case UIModelPackage.ACTION__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case UIModelPackage.ACTION__ID:
				setId(ID_EDEFAULT);
				return;
			case UIModelPackage.ACTION__PROPERTY_VALUE:
				setPropertyValue(PROPERTY_VALUE_EDEFAULT);
				return;
			case UIModelPackage.ACTION__TEMPLATE_NAME:
				setTemplateName(TEMPLATE_NAME_EDEFAULT);
				return;
			case UIModelPackage.ACTION__DELEGATE:
				setDelegate(DELEGATE_EDEFAULT);
				return;
			case UIModelPackage.ACTION__ACCELERATOR:
				setAccelerator(ACCELERATOR_EDEFAULT);
				return;
			case UIModelPackage.ACTION__PROPERTY_TYPE:
				setPropertyType((PropertyType)null);
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
			case UIModelPackage.ACTION__PROVIDER:
				return PROVIDER_EDEFAULT == null ? provider != null : !PROVIDER_EDEFAULT.equals(provider);
			case UIModelPackage.ACTION__PROPERTY:
				return property != null;
			case UIModelPackage.ACTION__MODE:
				return mode != MODE_EDEFAULT;
			case UIModelPackage.ACTION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case UIModelPackage.ACTION__PROPERTY_VALUE:
				return PROPERTY_VALUE_EDEFAULT == null ? propertyValue != null : !PROPERTY_VALUE_EDEFAULT.equals(propertyValue);
			case UIModelPackage.ACTION__TEMPLATE_NAME:
				return TEMPLATE_NAME_EDEFAULT == null ? templateName != null : !TEMPLATE_NAME_EDEFAULT.equals(templateName);
			case UIModelPackage.ACTION__DELEGATE:
				return DELEGATE_EDEFAULT == null ? delegate != null : !DELEGATE_EDEFAULT.equals(delegate);
			case UIModelPackage.ACTION__ACCELERATOR:
				return ACCELERATOR_EDEFAULT == null ? accelerator != null : !ACCELERATOR_EDEFAULT.equals(accelerator);
			case UIModelPackage.ACTION__PROPERTY_TYPE:
				return propertyType != null;
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
		result.append(" (provider: ");
		result.append(provider);
		result.append(", mode: ");
		result.append(mode);
		result.append(", id: ");
		result.append(id);
		result.append(", propertyValue: ");
		result.append(propertyValue);
		result.append(", templateName: ");
		result.append(templateName);
		result.append(", delegate: ");
		result.append(delegate);
		result.append(", accelerator: ");
		result.append(accelerator);
		result.append(')');
		return result.toString();
	}

} //ActionImpl
