/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model.impl;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyValueConverter;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.util.ClassUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.impl.PropertyImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.PropertyImpl#isReadonly <em>Readonly</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.PropertyImpl#getTypeName <em>Type Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.PropertyImpl#getLibraryName <em>Library Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.PropertyImpl#getWidget <em>Widget</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.PropertyImpl#getModel <em>Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyImpl extends MinimalEObjectImpl.Container implements Property {
	
	public static final Logger logger = LoggerFactory.getLogger(PropertyImpl.class);
	
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected boolean readonly = READONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected String typeName = TYPE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLibraryName() <em>Library Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLibraryName()
	 * @generated
	 * @ordered
	 */
	protected static final String LIBRARY_NAME_EDEFAULT = "xgui";

	/**
	 * The cached value of the '{@link #getLibraryName() <em>Library Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLibraryName()
	 * @generated
	 * @ordered
	 */
	protected String libraryName = LIBRARY_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModel() <em>Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected Model model;
	
	
	/**
	 * The PropertyType of this property
	 */
	private PropertyType pType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Initialise the value with the default value.
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getValue() {
		if (value == null && getType()!=null) {
			value = getType().getDefaultValue();
		}
		return value;
	}
	
	/**
	 * @see com.odcgroup.page.model.Property#getIntValue()
	 * @generated NOT
	 */
	public int getIntValue() {
		return Integer.parseInt(getValue());
	}


	/**
	 * @see com.odcgroup.page.model.Property#getLongValue()
	 * @generated NOT
	 */
	@Override
	public long getLongValue() {
		return Long.parseLong(getValue());
	}		
	
	/**
	 * Gets the value of the Property as a boolean.<p>
	 * 
	 * It returns <code>true</code> if the internal property value is equals to 
	 * "true" (case insensitive), otherwise it returns <code>false</code>.
	 * 
	 * @return boolean The boolean value of the Property 
	 * 
	 * @exception NumberFormatException
	 * @generated NOT
	 */
	public boolean getBooleanValue() {
		return Boolean.parseBoolean(getValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * Remove blanks 
	 * @param newValue
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		if (newValue != null) {
			value = newValue.trim();
			//value = value.replace("&", "&amp;");
		}

		boolean isEquals = StringUtils.equals(oldValue, newValue);
		
		if ( ! isEquals) {
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY__VALUE, oldValue, value));
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * The new value is stored as a string
	 * @param newValue
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setValue(boolean newValue) {
		setValue(newValue+"");
	}

	/**
	 * <!-- begin-user-doc -->
	 * The new value is stored as a string.
	 * @param newValue
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setValue(int newValue) {
		setValue(newValue+"");
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return boolean
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newReadonly
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadonly(boolean newReadonly) {
		boolean oldReadonly = readonly;
		readonly = newReadonly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY__READONLY, oldReadonly, readonly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newTypeName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeName(String newTypeName) {
		String oldTypeName = typeName;
		typeName = newTypeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY__TYPE_NAME, oldTypeName, typeName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLibraryName() {
		return libraryName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newLibraryName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLibraryName(String newLibraryName) {
		String oldLibraryName = libraryName;
		libraryName = newLibraryName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY__LIBRARY_NAME, oldLibraryName, libraryName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Widget
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Widget getWidget() {
		if (eContainerFeatureID() != ModelPackage.PROPERTY__WIDGET) return null;
		return (Widget)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidget
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWidget(Widget newWidget, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newWidget, ModelPackage.PROPERTY__WIDGET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidget
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidget(Widget newWidget) {
		if (newWidget != eInternalContainer() || (eContainerFeatureID() != ModelPackage.PROPERTY__WIDGET && newWidget != null)) {
			if (EcoreUtil.isAncestor(this, newWidget))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newWidget != null)
				msgs = ((InternalEObject)newWidget).eInverseAdd(this, ModelPackage.WIDGET__PROPERTIES, Widget.class, msgs);
			msgs = basicSetWidget(newWidget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY__WIDGET, newWidget, newWidget));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model getModel() {
		if (model != null && model.eIsProxy()) {
			InternalEObject oldModel = (InternalEObject)model;
			model = (Model)eResolveProxy(oldModel);
			if (model != oldModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.PROPERTY__MODEL, oldModel, model));
			}
		}
		return model;
	}
	
	/**
	 * @return the URI of the referenced model
	 * @generated NOT
	 */
	public URI getModelURI() {
		if(model == null) {
			return null;
		}
		return EcoreUtil.getURI(model);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model basicGetModel() {
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModel(Model newModel) {
		Model oldModel = model;
		model = newModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY__MODEL, oldModel, model));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void resetValue() {
		setValue(getType().getDefaultValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return boolean
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isValueSet() {
		String defValue = getType().getDefaultValue();
		boolean isSet = true;
		if (this.value == null) {
			isSet = defValue != null;
		} else if (defValue == null) {
			// default value is null
			isSet = true;
		} else {
			// both are not null
			 isSet = ! this.value.equals(defValue);
		}
		return isSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.PROPERTY__WIDGET:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetWidget((Widget)otherEnd, msgs);
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
			case ModelPackage.PROPERTY__WIDGET:
				return basicSetWidget(null, msgs);
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
			case ModelPackage.PROPERTY__WIDGET:
				return eInternalContainer().eInverseRemove(this, ModelPackage.WIDGET__PROPERTIES, Widget.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getEditorName() {
		   if (getType() != null) {
			   return getType().getDataType().getEditorName();
		   }

		return "";
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getValidatorName() {
		if (getType() != null) {
			   return getType().getDataType().getValidatorName();
		}
		return "";
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getConverterName() {
			if (getType() != null) {
			   return getType().getDataType().getConverterName();
			}
		
		return "";
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.PROPERTY__VALUE:
				return getValue();
			case ModelPackage.PROPERTY__READONLY:
				return isReadonly();
			case ModelPackage.PROPERTY__TYPE_NAME:
				return getTypeName();
			case ModelPackage.PROPERTY__LIBRARY_NAME:
				return getLibraryName();
			case ModelPackage.PROPERTY__WIDGET:
				return getWidget();
			case ModelPackage.PROPERTY__MODEL:
				if (resolve) return getModel();
				return basicGetModel();
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
			case ModelPackage.PROPERTY__VALUE:
				setValue((String)newValue);
				return;
			case ModelPackage.PROPERTY__READONLY:
				setReadonly((Boolean)newValue);
				return;
			case ModelPackage.PROPERTY__TYPE_NAME:
				setTypeName((String)newValue);
				return;
			case ModelPackage.PROPERTY__LIBRARY_NAME:
				setLibraryName((String)newValue);
				return;
			case ModelPackage.PROPERTY__WIDGET:
				setWidget((Widget)newValue);
				return;
			case ModelPackage.PROPERTY__MODEL:
				setModel((Model)newValue);
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
			case ModelPackage.PROPERTY__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case ModelPackage.PROPERTY__READONLY:
				setReadonly(READONLY_EDEFAULT);
				return;
			case ModelPackage.PROPERTY__TYPE_NAME:
				setTypeName(TYPE_NAME_EDEFAULT);
				return;
			case ModelPackage.PROPERTY__LIBRARY_NAME:
				setLibraryName(LIBRARY_NAME_EDEFAULT);
				return;
			case ModelPackage.PROPERTY__WIDGET:
				setWidget((Widget)null);
				return;
			case ModelPackage.PROPERTY__MODEL:
				setModel((Model)null);
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
			case ModelPackage.PROPERTY__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case ModelPackage.PROPERTY__READONLY:
				return readonly != READONLY_EDEFAULT;
			case ModelPackage.PROPERTY__TYPE_NAME:
				return TYPE_NAME_EDEFAULT == null ? typeName != null : !TYPE_NAME_EDEFAULT.equals(typeName);
			case ModelPackage.PROPERTY__LIBRARY_NAME:
				return LIBRARY_NAME_EDEFAULT == null ? libraryName != null : !LIBRARY_NAME_EDEFAULT.equals(libraryName);
			case ModelPackage.PROPERTY__WIDGET:
				return getWidget() != null;
			case ModelPackage.PROPERTY__MODEL:
				return model != null;
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
		result.append(" (value: ");
		result.append(value);
		result.append(", readonly: ");
		result.append(readonly);
		result.append(", typeName: ");
		result.append(typeName);
		result.append(", libraryName: ");
		result.append(libraryName);
		result.append(')');
		return result.toString();
	}

	/**
	 * Returns true if the Property's current value is equal to its default value.
	 * 
	 * @return boolean True if the Property's current value is equal to its default value
	 */
	public boolean isDefaultValue() {
		return ObjectUtils.equals(getValue(), getType().getDefaultValue());
	}	
	
	/**
	 * Gets the PropertyType containing the meta-information about this Property.
	 * 
	 * @return PropertyType The PropertyType containing the meta-information about this Property
	 */
	public PropertyType getType() {
		if (pType == null) {
			MetaModel mm = MetaModelRegistry.getMetaModel();
			libraryName = StringUtils.isEmpty(libraryName) ? "xgui" : libraryName;
			pType = mm.findPropertyType(libraryName, typeName);
		}
		return pType;
	}
	
	/**
	 * Gets the converted value of the Property.
	 * 
	 * @return Object The value of the Property converted using the
	 * correct Converter for the DataType
	 */
	public Object getConvertedValue() {
		String cn = getConverterName();
		PropertyValueConverter pvc = (PropertyValueConverter) ClassUtils.newInstance(getClass().getClassLoader(), cn);
		return pvc.toObject(value);
	}

	/** (non-Javadoc)
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eResolveProxy(org.eclipse.emf.ecore.InternalEObject)
	 */
	@Override
	public EObject eResolveProxy(InternalEObject proxy) {
		EObject eObj = super.eResolveProxy(proxy);
		if (eObj.eIsProxy()) {
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(this.eResource());
			URI uri = proxy.eProxyURI().trimFragment();
			if (ofsProject != null) {
				EObject resolvedEObj = getUpdatedModel(uri);
				if(resolvedEObj!=null) eObj = resolvedEObj;
			} else {
				logger.trace("No eResource available when resolving the proxy for {}.", uri.toString());
			}
		}
		return eObj;
	}

	
	/**
	 * @param modelUri
	 * @return eObj;
	 */
	private EObject getUpdatedModel(URI modelUri) {
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(this.eResource());
		EObject eObj = null;
		if (ofsProject != null) {
			try {
				IOfsModelResource mResource = ofsProject.getOfsModelResource(modelUri);
				List<EObject> models = mResource.getEMFModel();
				if (!models.isEmpty()) {
					eObj = models.get(0);
				}
			} catch (ModelNotFoundException e) {
				logger.debug("Could not find model {}.", modelUri.toString());
			} catch (Exception e) {
				logger.trace("An unexpected error occurred when resolving the proxy for {}.", modelUri.toString(), e);
			} 
		}
		return eObj;
	}
	
} //PropertyImpl
