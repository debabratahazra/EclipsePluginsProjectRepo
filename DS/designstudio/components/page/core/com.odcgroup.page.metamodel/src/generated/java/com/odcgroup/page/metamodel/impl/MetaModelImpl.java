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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.odcgroup.page.metamodel.Accountability;
import com.odcgroup.page.metamodel.DataTypes;
import com.odcgroup.page.metamodel.EventModel;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.SnippetModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.NamedTypeUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Meta Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.MetaModelImpl#getWidgetLibraries <em>Widget Libraries</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.MetaModelImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.MetaModelImpl#getContainability <em>Containability</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.MetaModelImpl#getIncludeability <em>Includeability</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.MetaModelImpl#getDataTypes <em>Data Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.MetaModelImpl#getEventModel <em>Event Model</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.MetaModelImpl#getSnippetModel <em>Snippet Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetaModelImpl extends NamedTypeImpl implements MetaModel {
	/**
	 * The cached value of the '{@link #getWidgetLibraries() <em>Widget Libraries</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList<WidgetLibrary> widgetLibraries;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContainability() <em>Containability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainability()
	 * @generated
	 * @ordered
	 */
	protected Accountability containability;

	/**
	 * The cached value of the '{@link #getIncludeability() <em>Includeability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeability()
	 * @generated
	 * @ordered
	 */
	protected Accountability includeability;

	/**
	 * The cached value of the '{@link #getDataTypes() <em>Data Types</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataTypes()
	 * @generated
	 * @ordered
	 */
	protected DataTypes dataTypes;

	/**
	 * The cached value of the '{@link #getEventModel() <em>Event Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventModel()
	 * @generated
	 * @ordered
	 */
	protected EventModel eventModel;

	/**
	 * The cached value of the '{@link #getSnippetModel() <em>Snippet Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSnippetModel()
	 * @generated
	 * @ordered
	 */
	protected SnippetModel snippetModel;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetaModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.META_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WidgetLibrary> getWidgetLibraries() {
		if (widgetLibraries == null) {
			widgetLibraries = new EObjectResolvingEList<WidgetLibrary>(WidgetLibrary.class, this, MetaModelPackage.META_MODEL__WIDGET_LIBRARIES);
		}
		return widgetLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newVersion 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return DataTypes
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypes getDataTypes() {
		if (dataTypes != null && dataTypes.eIsProxy()) {
			InternalEObject oldDataTypes = (InternalEObject)dataTypes;
			dataTypes = (DataTypes)eResolveProxy(oldDataTypes);
			if (dataTypes != oldDataTypes) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.META_MODEL__DATA_TYPES, oldDataTypes, dataTypes));
			}
		}
		return dataTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return DataTypes
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypes basicGetDataTypes() {
		return dataTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDataTypes 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataTypes(DataTypes newDataTypes) {
		DataTypes oldDataTypes = dataTypes;
		dataTypes = newDataTypes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__DATA_TYPES, oldDataTypes, dataTypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventModel getEventModel() {
		return eventModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEventModel
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEventModel(EventModel newEventModel, NotificationChain msgs) {
		EventModel oldEventModel = eventModel;
		eventModel = newEventModel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__EVENT_MODEL, oldEventModel, newEventModel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEventModel 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventModel(EventModel newEventModel) {
		if (newEventModel != eventModel) {
			NotificationChain msgs = null;
			if (eventModel != null)
				msgs = ((InternalEObject)eventModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetaModelPackage.META_MODEL__EVENT_MODEL, null, msgs);
			if (newEventModel != null)
				msgs = ((InternalEObject)newEventModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetaModelPackage.META_MODEL__EVENT_MODEL, null, msgs);
			msgs = basicSetEventModel(newEventModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__EVENT_MODEL, newEventModel, newEventModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetModel getSnippetModel() {
		return snippetModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSnippetModel(SnippetModel newSnippetModel, NotificationChain msgs) {
		SnippetModel oldSnippetModel = snippetModel;
		snippetModel = newSnippetModel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__SNIPPET_MODEL, oldSnippetModel, newSnippetModel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSnippetModel(SnippetModel newSnippetModel) {
		if (newSnippetModel != snippetModel) {
			NotificationChain msgs = null;
			if (snippetModel != null)
				msgs = ((InternalEObject)snippetModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetaModelPackage.META_MODEL__SNIPPET_MODEL, null, msgs);
			if (newSnippetModel != null)
				msgs = ((InternalEObject)newSnippetModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetaModelPackage.META_MODEL__SNIPPET_MODEL, null, msgs);
			msgs = basicSetSnippetModel(newSnippetModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__SNIPPET_MODEL, newSnippetModel, newSnippetModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Accountability
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Accountability getContainability() {
		return containability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newContainability
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainability(Accountability newContainability, NotificationChain msgs) {
		Accountability oldContainability = containability;
		containability = newContainability;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__CONTAINABILITY, oldContainability, newContainability);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newContainability 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainability(Accountability newContainability) {
		if (newContainability != containability) {
			NotificationChain msgs = null;
			if (containability != null)
				msgs = ((InternalEObject)containability).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetaModelPackage.META_MODEL__CONTAINABILITY, null, msgs);
			if (newContainability != null)
				msgs = ((InternalEObject)newContainability).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetaModelPackage.META_MODEL__CONTAINABILITY, null, msgs);
			msgs = basicSetContainability(newContainability, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__CONTAINABILITY, newContainability, newContainability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Accountability
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Accountability getIncludeability() {
		return includeability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newIncludeability
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIncludeability(Accountability newIncludeability, NotificationChain msgs) {
		Accountability oldIncludeability = includeability;
		includeability = newIncludeability;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__INCLUDEABILITY, oldIncludeability, newIncludeability);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newIncludeability 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncludeability(Accountability newIncludeability) {
		if (newIncludeability != includeability) {
			NotificationChain msgs = null;
			if (includeability != null)
				msgs = ((InternalEObject)includeability).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetaModelPackage.META_MODEL__INCLUDEABILITY, null, msgs);
			if (newIncludeability != null)
				msgs = ((InternalEObject)newIncludeability).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetaModelPackage.META_MODEL__INCLUDEABILITY, null, msgs);
			msgs = basicSetIncludeability(newIncludeability, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.META_MODEL__INCLUDEABILITY, newIncludeability, newIncludeability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.META_MODEL__CONTAINABILITY:
				return basicSetContainability(null, msgs);
			case MetaModelPackage.META_MODEL__INCLUDEABILITY:
				return basicSetIncludeability(null, msgs);
			case MetaModelPackage.META_MODEL__EVENT_MODEL:
				return basicSetEventModel(null, msgs);
			case MetaModelPackage.META_MODEL__SNIPPET_MODEL:
				return basicSetSnippetModel(null, msgs);
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
			case MetaModelPackage.META_MODEL__WIDGET_LIBRARIES:
				return getWidgetLibraries();
			case MetaModelPackage.META_MODEL__VERSION:
				return getVersion();
			case MetaModelPackage.META_MODEL__CONTAINABILITY:
				return getContainability();
			case MetaModelPackage.META_MODEL__INCLUDEABILITY:
				return getIncludeability();
			case MetaModelPackage.META_MODEL__DATA_TYPES:
				if (resolve) return getDataTypes();
				return basicGetDataTypes();
			case MetaModelPackage.META_MODEL__EVENT_MODEL:
				return getEventModel();
			case MetaModelPackage.META_MODEL__SNIPPET_MODEL:
				return getSnippetModel();
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
			case MetaModelPackage.META_MODEL__WIDGET_LIBRARIES:
				getWidgetLibraries().clear();
				getWidgetLibraries().addAll((Collection<? extends WidgetLibrary>)newValue);
				return;
			case MetaModelPackage.META_MODEL__VERSION:
				setVersion((String)newValue);
				return;
			case MetaModelPackage.META_MODEL__CONTAINABILITY:
				setContainability((Accountability)newValue);
				return;
			case MetaModelPackage.META_MODEL__INCLUDEABILITY:
				setIncludeability((Accountability)newValue);
				return;
			case MetaModelPackage.META_MODEL__DATA_TYPES:
				setDataTypes((DataTypes)newValue);
				return;
			case MetaModelPackage.META_MODEL__EVENT_MODEL:
				setEventModel((EventModel)newValue);
				return;
			case MetaModelPackage.META_MODEL__SNIPPET_MODEL:
				setSnippetModel((SnippetModel)newValue);
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
			case MetaModelPackage.META_MODEL__WIDGET_LIBRARIES:
				getWidgetLibraries().clear();
				return;
			case MetaModelPackage.META_MODEL__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case MetaModelPackage.META_MODEL__CONTAINABILITY:
				setContainability((Accountability)null);
				return;
			case MetaModelPackage.META_MODEL__INCLUDEABILITY:
				setIncludeability((Accountability)null);
				return;
			case MetaModelPackage.META_MODEL__DATA_TYPES:
				setDataTypes((DataTypes)null);
				return;
			case MetaModelPackage.META_MODEL__EVENT_MODEL:
				setEventModel((EventModel)null);
				return;
			case MetaModelPackage.META_MODEL__SNIPPET_MODEL:
				setSnippetModel((SnippetModel)null);
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
			case MetaModelPackage.META_MODEL__WIDGET_LIBRARIES:
				return widgetLibraries != null && !widgetLibraries.isEmpty();
			case MetaModelPackage.META_MODEL__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case MetaModelPackage.META_MODEL__CONTAINABILITY:
				return containability != null;
			case MetaModelPackage.META_MODEL__INCLUDEABILITY:
				return includeability != null;
			case MetaModelPackage.META_MODEL__DATA_TYPES:
				return dataTypes != null;
			case MetaModelPackage.META_MODEL__EVENT_MODEL:
				return eventModel != null;
			case MetaModelPackage.META_MODEL__SNIPPET_MODEL:
				return snippetModel != null;
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
		result.append(" (version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

	/**
	 * Finds the WidgetLibrary given its name.
	 * 
	 * @param libraryName The name of the WidgetLibrary
	 * @return WidgetLibrary The WidgetLibrary or null if no WidgetLibrary can be found
	 */
	public WidgetLibrary findWidgetLibrary(String libraryName) {
		return (WidgetLibrary) NamedTypeUtils.findByName(getWidgetLibraries(), libraryName);
	}

	/**
	 * Finds the WidgetType given its name and the name of the library that it 
	 * belongs to.
	 * 
	 * @param libraryName The name of the WidgetLibrary
	 * @param name The name of the WidgetType
	 * @return WidgetType The WidgetType or null if no WidgetType can be found
	 */
	public WidgetType findWidgetType(String libraryName, String name) {
		WidgetLibrary wl = findWidgetLibrary(libraryName);
		return wl.findWidgetType(name);
	}
	
	/**
	 * Finds the PropertyType given its name and the name of the library that it 
	 * belongs to.
	 * 
	 * @param libraryName The name of the WidgetLibrary
	 * @param name The name of the PropertyType
	 * @return PropertyType The PropertyType or null if no PropertyType can be found
	 */
	public PropertyType findPropertyType(String libraryName, String name) {
		WidgetLibrary wl = findWidgetLibrary(libraryName);
		return wl.findPropertyType(name);	
	}

} //MetaModelImpl