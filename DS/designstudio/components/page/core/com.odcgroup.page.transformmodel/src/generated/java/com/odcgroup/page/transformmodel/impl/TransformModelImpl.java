/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.AbstractPropertyTransformer;
import com.odcgroup.page.transformmodel.AbstractWidgetTransformer;
import com.odcgroup.page.transformmodel.EventTransformations;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.PropertyTransformer;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transform Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.TransformModelImpl#getNamespaces <em>Namespaces</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.TransformModelImpl#getWidgetTransformers <em>Widget Transformers</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.TransformModelImpl#getDefaultNamespace <em>Default Namespace</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.TransformModelImpl#getPropertyTransformers <em>Property Transformers</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.TransformModelImpl#getDefaultWidgetLibrary <em>Default Widget Library</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.TransformModelImpl#getEventTransformations <em>Event Transformations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransformModelImpl extends MinimalEObjectImpl.Container implements TransformModel {
	
	/** The DefaultWidgetTransformer. */
	private DefaultWidgetTransformerImpl defaultWidgetTransformer;
	
	/** The DefaultPropertyTransformer. */
	private DefaultPropertyTransformerImpl defaultPropertyTransformer;
	
	/**
	 * The cached value of the '{@link #getNamespaces() <em>Namespaces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaces()
	 * @generated
	 * @ordered
	 */
	protected EList<Namespace> namespaces;

	/**
	 * The cached value of the '{@link #getWidgetTransformers() <em>Widget Transformers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetTransformers()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractWidgetTransformer> widgetTransformers;
	
	/**
	 * the cached mapping of widget type and the respective transformer
	 */
	protected Map<WidgetType, WidgetTransformer> widgetTransformerMap = new HashMap<WidgetType, WidgetTransformer>();

	/**
	 * The cached value of the '{@link #getDefaultNamespace() <em>Default Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultNamespace()
	 * @generated
	 * @ordered
	 */
	protected Namespace defaultNamespace;

	/**
	 * The cached value of the '{@link #getPropertyTransformers() <em>Property Transformers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyTransformers()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractPropertyTransformer> propertyTransformers;
	
	/**
	 * the cached mapping of property type and the respective transformer
	 */
	protected Map<PropertyType, PropertyTransformer> propertyTransformerMap = new HashMap<PropertyType, PropertyTransformer>();

	/**
	 * The cached value of the '{@link #getDefaultWidgetLibrary() <em>Default Widget Library</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultWidgetLibrary()
	 * @generated
	 * @ordered
	 */
	protected WidgetLibrary defaultWidgetLibrary;

	/**
	 * The cached value of the '{@link #getEventTransformations() <em>Event Transformations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventTransformations()
	 * @generated
	 * @ordered
	 */
	protected EventTransformations eventTransformations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransformModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.TRANSFORM_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Namespace> getNamespaces() {
		if (namespaces == null) {
			namespaces = new EObjectContainmentEList<Namespace>(Namespace.class, this, TransformModelPackage.TRANSFORM_MODEL__NAMESPACES);
		}
		return namespaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AbstractWidgetTransformer> getWidgetTransformers() {
		if (widgetTransformers == null) {
			widgetTransformers = new EObjectContainmentWithInverseEList<AbstractWidgetTransformer>(AbstractWidgetTransformer.class, this, TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS, TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL);
		}
		return widgetTransformers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Namespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Namespace getDefaultNamespace() {
		if (defaultNamespace != null && defaultNamespace.eIsProxy()) {
			InternalEObject oldDefaultNamespace = (InternalEObject)defaultNamespace;
			defaultNamespace = (Namespace)eResolveProxy(oldDefaultNamespace);
			if (defaultNamespace != oldDefaultNamespace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.TRANSFORM_MODEL__DEFAULT_NAMESPACE, oldDefaultNamespace, defaultNamespace));
			}
		}
		return defaultNamespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Namespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Namespace basicGetDefaultNamespace() {
		return defaultNamespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDefaultNamespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultNamespace(Namespace newDefaultNamespace) {
		Namespace oldDefaultNamespace = defaultNamespace;
		defaultNamespace = newDefaultNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.TRANSFORM_MODEL__DEFAULT_NAMESPACE, oldDefaultNamespace, defaultNamespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AbstractPropertyTransformer> getPropertyTransformers() {
		if (propertyTransformers == null) {
			propertyTransformers = new EObjectContainmentWithInverseEList<AbstractPropertyTransformer>(AbstractPropertyTransformer.class, this, TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS, TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL);			
		}
		return propertyTransformers;
	}
	
	/**
	 * 
	 */
	private Map<PropertyType, PropertyTransformer> getPropertyTransformerMap() {
		if (propertyTransformerMap.isEmpty()) {
			for (AbstractPropertyTransformer pt : getPropertyTransformers()) {
				propertyTransformerMap.put(pt.getPropertyType(), pt);				
			}
		}
		return propertyTransformerMap;
	}
	
	/**
	 * 
	 */
	private Map<WidgetType, WidgetTransformer> getWidgetTransformerMap() {
		if (widgetTransformerMap.isEmpty()) {
			for (AbstractWidgetTransformer wt : getWidgetTransformers()) {
				widgetTransformerMap.put(wt.getWidgetType(), wt);				
			}
		}
		return widgetTransformerMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetLibrary
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetLibrary getDefaultWidgetLibrary() {
		if (defaultWidgetLibrary != null && defaultWidgetLibrary.eIsProxy()) {
			InternalEObject oldDefaultWidgetLibrary = (InternalEObject)defaultWidgetLibrary;
			defaultWidgetLibrary = (WidgetLibrary)eResolveProxy(oldDefaultWidgetLibrary);
			if (defaultWidgetLibrary != oldDefaultWidgetLibrary) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY, oldDefaultWidgetLibrary, defaultWidgetLibrary));
			}
		}
		return defaultWidgetLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetLibrary
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetLibrary basicGetDefaultWidgetLibrary() {
		return defaultWidgetLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDefaultWidgetLibrary
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultWidgetLibrary(WidgetLibrary newDefaultWidgetLibrary) {
		WidgetLibrary oldDefaultWidgetLibrary = defaultWidgetLibrary;
		defaultWidgetLibrary = newDefaultWidgetLibrary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY, oldDefaultWidgetLibrary, defaultWidgetLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventTransformations
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventTransformations getEventTransformations() {
		return eventTransformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEventTransformations
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEventTransformations(EventTransformations newEventTransformations, NotificationChain msgs) {
		EventTransformations oldEventTransformations = eventTransformations;
		eventTransformations = newEventTransformations;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS, oldEventTransformations, newEventTransformations);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEventTransformations
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventTransformations(EventTransformations newEventTransformations) {
		if (newEventTransformations != eventTransformations) {
			NotificationChain msgs = null;
			if (eventTransformations != null)
				msgs = ((InternalEObject)eventTransformations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS, null, msgs);
			if (newEventTransformations != null)
				msgs = ((InternalEObject)newEventTransformations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS, null, msgs);
			msgs = basicSetEventTransformations(newEventTransformations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS, newEventTransformations, newEventTransformations));
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
			case TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getWidgetTransformers()).basicAdd(otherEnd, msgs);
			case TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPropertyTransformers()).basicAdd(otherEnd, msgs);
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
			case TransformModelPackage.TRANSFORM_MODEL__NAMESPACES:
				return ((InternalEList<?>)getNamespaces()).basicRemove(otherEnd, msgs);
			case TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS:
				return ((InternalEList<?>)getWidgetTransformers()).basicRemove(otherEnd, msgs);
			case TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS:
				return ((InternalEList<?>)getPropertyTransformers()).basicRemove(otherEnd, msgs);
			case TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS:
				return basicSetEventTransformations(null, msgs);
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
			case TransformModelPackage.TRANSFORM_MODEL__NAMESPACES:
				return getNamespaces();
			case TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS:
				return getWidgetTransformers();
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_NAMESPACE:
				if (resolve) return getDefaultNamespace();
				return basicGetDefaultNamespace();
			case TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS:
				return getPropertyTransformers();
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY:
				if (resolve) return getDefaultWidgetLibrary();
				return basicGetDefaultWidgetLibrary();
			case TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS:
				return getEventTransformations();
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
			case TransformModelPackage.TRANSFORM_MODEL__NAMESPACES:
				getNamespaces().clear();
				getNamespaces().addAll((Collection<? extends Namespace>)newValue);
				return;
			case TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS:
				getWidgetTransformers().clear();
				getWidgetTransformers().addAll((Collection<? extends AbstractWidgetTransformer>)newValue);
				return;
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_NAMESPACE:
				setDefaultNamespace((Namespace)newValue);
				return;
			case TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS:
				getPropertyTransformers().clear();
				getPropertyTransformers().addAll((Collection<? extends AbstractPropertyTransformer>)newValue);
				return;
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY:
				setDefaultWidgetLibrary((WidgetLibrary)newValue);
				return;
			case TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS:
				setEventTransformations((EventTransformations)newValue);
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
			case TransformModelPackage.TRANSFORM_MODEL__NAMESPACES:
				getNamespaces().clear();
				return;
			case TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS:
				getWidgetTransformers().clear();
				widgetTransformerMap.clear();
				return;
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_NAMESPACE:
				setDefaultNamespace((Namespace)null);
				return;
			case TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS:
				getPropertyTransformers().clear();
				propertyTransformerMap.clear();
				return;
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY:
				setDefaultWidgetLibrary((WidgetLibrary)null);
				return;
			case TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS:
				setEventTransformations((EventTransformations)null);
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
			case TransformModelPackage.TRANSFORM_MODEL__NAMESPACES:
				return namespaces != null && !namespaces.isEmpty();
			case TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS:
				return widgetTransformers != null && !widgetTransformers.isEmpty();
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_NAMESPACE:
				return defaultNamespace != null;
			case TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS:
				return propertyTransformers != null && !propertyTransformers.isEmpty();
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY:
				return defaultWidgetLibrary != null;
			case TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS:
				return eventTransformations != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Finds the PropertyTransformer. Returns the default one if no PropertyTransformer can be found.
	 * 
	 * @param property The Property
	 * @return PropertyTransformer The PropertyTransformer
	 */
	public PropertyTransformer findPropertyTransformer(Property property) {
		PropertyTransformer transformer = getPropertyTransformerMap().get(property.getType());
		if (transformer != null) {
			return transformer;
		}		
		// Not found. Use the default one
		return getDefaultPropertyTransformer();
	}
	
	/**
	 * Gets the DefaultPropertyTransformer.
	 * 
	 * @return DefaultPropertyTransformer The DefaultPropertyTransformer
	 */
	private DefaultPropertyTransformerImpl getDefaultPropertyTransformer() {
		if (defaultPropertyTransformer == null) {
			defaultPropertyTransformer = new DefaultPropertyTransformerImpl();
		}
		return defaultPropertyTransformer;
	}	

	/**
	 * Finds the WidgetTransformer given a widget. 
	 * Returns the default one if no WidgetTransformer can be found.
	 * 
	 * @param widget The Widget
	 * @return WidgetTransformer The WidgetTransformer
	 */
	public WidgetTransformer findWidgetTransformer(Widget widget) {
		return findWidgetTransformer(widget.getType());
	}
	
	/**
	 * Finds the WidgetTransformer given a widget type. 
	 * Returns the default one if no WidgetTransformer can be found.
	 * 
	 * @param widgetType The WidgetType
	 * @return WidgetTransformer The WidgetTransformer
	 */
	public WidgetTransformer findWidgetTransformer(WidgetType widgetType) {
		WidgetTransformer wt = getWidgetTransformerMap().get(widgetType);
		if (wt != null) {
			return wt;
		}		
		// Not found. Use the default one
		return getDefaultWidgetTransformer();
	}

	
	/**
	 * Gets the DefaultWidgetTransformer.
	 * 
	 * @return DefaultWidgetTransformer The DefaultWidgetTransformer
	 */
	private DefaultWidgetTransformerImpl getDefaultWidgetTransformer() {
		if (defaultWidgetTransformer == null) {
			defaultWidgetTransformer = new DefaultWidgetTransformerImpl();
		}
		return defaultWidgetTransformer;
	}
	
	
	private Map<String, Namespace> uriMap = null;
	
	/**
	 * Finds the Namespace. Returns the default one if no Namespace can be found.
	 * 
	 * @param uri The URI
	 * @return Namespace The Namespace
	 */
	public Namespace findNamespace(String uri) {
		
		if (uriMap == null) {
			uriMap = new HashMap<String, Namespace>();
			for (Namespace ns : getNamespaces()) {
				uriMap.put(ns.getUri(), ns);
			}
		}
		
		Namespace ns = uriMap.get(uri);
		if (ns == null) ns = getDefaultNamespace();
		return ns;
		
//		for (Iterator it = getNamespaces().iterator(); it.hasNext();) {
//			Namespace ns = (Namespace) it.next();
//			if (ns.getUri().equals(uri)) {
//				return ns;
//			}
//		}
//		
//		// Not found. use default one
//		return getDefaultNamespace();
	}
	
	/**
	 * Finds the Namespace by it's prefix. Returns the default one if no Namespace can be found.
	 * 
	 * @param prefix The URI
	 * @return Namespace The Namespace
	 */
	public Namespace findNamespaceByPrefix(String prefix) {
		for (Iterator<Namespace> it = getNamespaces().iterator(); it.hasNext();) {
			Namespace ns = it.next();
			if (ns.getPrefix().equals(prefix)) {
				return ns;
			}
		}
		// Not found. use default one
		return getDefaultNamespace();
	}

	/**
	 * Finds the Namespaces by it's origin.
	 * 
	 * @param origin
	 * 				The origin of the namespaces
	 * @return List
	 * 				The namespaces list
	 */
	public List findNamespacesByOrigin(String origin) {
		List result = new ArrayList();
		for (Iterator it = getNamespaces().iterator(); it.hasNext();) {
			Namespace ns = (Namespace) it.next();
			if (ns.getOrigin().equals(origin)) {
				result.add(ns);
			}		
		}
		return result;
	}

} //TransformModelImpl