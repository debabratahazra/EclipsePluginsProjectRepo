/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Widget</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getTypeName <em>Type Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getLibraryName <em>Library Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getModel <em>Model</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getEvents <em>Events</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getSnippets <em>Snippets</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getToolTips <em>Tool Tips</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.WidgetImpl#getLabels <em>Labels</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WidgetImpl extends MinimalEObjectImpl.Container implements Widget {

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> properties;

	/**
	 * The cached value of the '{@link #getContents() <em>Contents</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getContents()
	 * @generated
	 * @ordered
	 */
	protected EList<Widget> contents;

	/**
	 * The default value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
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
	 * The cached value of the '{@link #getEvents() <em>Events</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getEvents()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> events;

	/**
	 * The cached value of the '{@link #getSnippets() <em>Snippets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSnippets()
	 * @generated
	 * @ordered
	 */
	protected EList<Snippet> snippets;

	/**
	 * The cached value of the '{@link #getToolTips() <em>Tool Tips</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolTips()
	 * @generated
	 * @ordered
	 */
	protected EList<Translation> toolTips;

	/**
	 * The cached value of the '{@link #getLabels() <em>Labels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabels()
	 * @generated
	 * @ordered
	 */
	protected EList<Translation> labels;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected WidgetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.WIDGET;
	}



	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.WIDGET__PROPERTIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getProperties()).basicAdd(otherEnd, msgs);
			case ModelPackage.WIDGET__CONTENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getContents()).basicAdd(otherEnd, msgs);
			case ModelPackage.WIDGET__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((Widget)otherEnd, msgs);
			case ModelPackage.WIDGET__MODEL:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetModel((Model)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}
	
    /**
     * Gets the value of the property. Returns null if the PropertyType is null
     * of the property does not exist.
     * 
     * @param propertyName The name of the Property
     * @return String The value of the Property
     */
    public String getPropertyValue(String propertyName) {
    	Property p = findProperty(propertyName);
    	
    	if (p == null) {
    		return null;
    	}
    	
		return p.getValue();
    }

    /**
     * Gets the value of the property. Returns null if the PropertyType is null
     * of the property does not exist.
     * 
     * @param propertyType The PropertyType
     * @return String The value of the Property
     */
    public String getPropertyValue(PropertyType propertyType) {
    	if (propertyType == null) {
    		return null;
    	}
    	
    	Property p = findProperty(propertyType);
    	if (p == null) {
    		return null;
    	}
    	
		return p.getValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Widget> getContents() {
		if (contents == null) {
			contents = new EObjectContainmentWithInverseEList<Widget>(Widget.class, this, ModelPackage.WIDGET__CONTENTS, ModelPackage.WIDGET__PARENT);
		}
		return contents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentWithInverseEList<Property>(Property.class, this, ModelPackage.WIDGET__PROPERTIES, ModelPackage.PROPERTY__WIDGET);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Widget
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Widget getParent() {
		if (eContainerFeatureID() != ModelPackage.WIDGET__PARENT) return null;
		return (Widget)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @param newParent
	 * @param msgs
	 * @return NotificationChain <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Widget newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, ModelPackage.WIDGET__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newParent
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Widget newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != ModelPackage.WIDGET__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, ModelPackage.WIDGET__CONTENTS, Widget.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.WIDGET__PARENT, newParent, newParent));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.WIDGET__TYPE_NAME, oldTypeName, typeName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.WIDGET__LIBRARY_NAME, oldLibraryName, libraryName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * Overridden since only the root Widget is directly linked to the model. We have added a
	 * convenience loop for finding the model.
	 * @return Model
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Model getModel() {
		Model m = null;
		if (eContainerFeatureID() == ModelPackage.WIDGET__MODEL) {
			m = (Model) eContainer();
		}

		if (m != null) {
			return m;
		}

		// Try the parent. This is recursive
		Widget p = getParent();
		if (p != null) {
			return p.getModel();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @param newModel
	 * @param msgs
	 * @return NotificationChain <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModel(Model newModel, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newModel, ModelPackage.WIDGET__MODEL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModel(Model newModel) {
		if (newModel != eInternalContainer() || (eContainerFeatureID() != ModelPackage.WIDGET__MODEL && newModel != null)) {
			if (EcoreUtil.isAncestor(this, newModel))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newModel != null)
				msgs = ((InternalEObject)newModel).eInverseAdd(this, ModelPackage.MODEL__WIDGET, Model.class, msgs);
			msgs = basicSetModel(newModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.WIDGET__MODEL, newModel, newModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Event> getEvents() {
		if (events == null) {
			events = new EObjectContainmentEList<Event>(Event.class, this, ModelPackage.WIDGET__EVENTS);
		}
		return events;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Snippet> getSnippets() {
		if (snippets == null) {
			snippets = new EObjectContainmentEList<Snippet>(Snippet.class, this, ModelPackage.WIDGET__SNIPPETS);
		}
		return snippets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Translation> getToolTips() {
		if (toolTips == null) {
			toolTips = new EObjectContainmentEList<Translation>(Translation.class, this, ModelPackage.WIDGET__TOOL_TIPS);
		}
		return toolTips;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Translation> getLabels() {
		if (labels == null) {
			labels = new EObjectContainmentEList<Translation>(Translation.class, this, ModelPackage.WIDGET__LABELS);
		}
		return labels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @param value Changes the visibility of this widget <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setVisible(boolean value) {
		Property p = findProperty(PropertyTypeConstants.IS_WIDGET_VISIBLE);
		if (p != null) {
			p.setValue(value + "");
			if (! p.isReadonly()) {
				p.setValue(value+"");
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @return the visibility of this widget <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isVisible() {
		Property p = findProperty(PropertyTypeConstants.IS_WIDGET_VISIBLE);
		if (p != null) {
			return p.getBooleanValue();
		}
		// default, all widgets are visible
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param value
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setSelected(boolean value) {
		Property p = findProperty(PropertyTypeConstants.IS_WIDGET_SELECTED);
		if (p != null) {
			p.setValue(value);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return boolean
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isSelected() {
		Property p = findProperty(PropertyTypeConstants.IS_WIDGET_SELECTED);
		if (p != null) {
			return p.getBooleanValue();
		}
		// default, all widgets are not selected
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param index
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setIndexOfSelectedChild(int index) {
		Property p = findProperty(PropertyTypeConstants.INDEX_OF_SELECTED_CHILD);
		if (p != null) {
			p.setValue(index);
		}
		
	     // Unset the old index
        for (Widget w : getContents()) {
            p = w.findProperty(PropertyTypeConstants.IS_WIDGET_SELECTED);
            if (p != null) {
                p.setValue("false");
            }
            
            p = w.findProperty(PropertyTypeConstants.IS_WIDGET_VISIBLE);
            if (p != null) {
                p.setValue("false");
            }
        }
       
        // Set the new index
        Widget w = getContents().get(index);
        p = w.findProperty(PropertyTypeConstants.IS_WIDGET_SELECTED);
        if (p != null) {
            p.setValue("true");
        }
        
        p = w.findProperty(PropertyTypeConstants.IS_WIDGET_VISIBLE);
        if (p != null) {
            p.setValue("true");
        }
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return int
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int getIndexOfSelectedChild() {
		Property p = findProperty(PropertyTypeConstants.INDEX_OF_SELECTED_CHILD);
		if (p != null) {
			return p.getIntValue();
		}
		// default, no selection
		return -1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return boolean
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isSelectable() {
		Property p = findProperty(PropertyTypeConstants.IS_WIDGET_SELECTABLE);
		if (p != null) {
			return p.getBooleanValue();
		}
		// default, all widgets can be selected
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return boolean
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isDeletable() {
		Property p = findProperty(PropertyTypeConstants.IS_WIDGET_DELETABLE);
		if (p != null) {
			return p.getBooleanValue();
		}
		// default, all widgets can be deleted
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.WIDGET__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case ModelPackage.WIDGET__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case ModelPackage.WIDGET__PARENT:
				return basicSetParent(null, msgs);
			case ModelPackage.WIDGET__MODEL:
				return basicSetModel(null, msgs);
			case ModelPackage.WIDGET__EVENTS:
				return ((InternalEList<?>)getEvents()).basicRemove(otherEnd, msgs);
			case ModelPackage.WIDGET__SNIPPETS:
				return ((InternalEList<?>)getSnippets()).basicRemove(otherEnd, msgs);
			case ModelPackage.WIDGET__TOOL_TIPS:
				return ((InternalEList<?>)getToolTips()).basicRemove(otherEnd, msgs);
			case ModelPackage.WIDGET__LABELS:
				return ((InternalEList<?>)getLabels()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ModelPackage.WIDGET__PARENT:
				return eInternalContainer().eInverseRemove(this, ModelPackage.WIDGET__CONTENTS, Widget.class, msgs);
			case ModelPackage.WIDGET__MODEL:
				return eInternalContainer().eInverseRemove(this, ModelPackage.MODEL__WIDGET, Model.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.WIDGET__PROPERTIES:
				return getProperties();
			case ModelPackage.WIDGET__CONTENTS:
				return getContents();
			case ModelPackage.WIDGET__PARENT:
				return getParent();
			case ModelPackage.WIDGET__TYPE_NAME:
				return getTypeName();
			case ModelPackage.WIDGET__LIBRARY_NAME:
				return getLibraryName();
			case ModelPackage.WIDGET__MODEL:
				return getModel();
			case ModelPackage.WIDGET__EVENTS:
				return getEvents();
			case ModelPackage.WIDGET__SNIPPETS:
				return getSnippets();
			case ModelPackage.WIDGET__TOOL_TIPS:
				return getToolTips();
			case ModelPackage.WIDGET__LABELS:
				return getLabels();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.WIDGET__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property>)newValue);
				return;
			case ModelPackage.WIDGET__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends Widget>)newValue);
				return;
			case ModelPackage.WIDGET__PARENT:
				setParent((Widget)newValue);
				return;
			case ModelPackage.WIDGET__TYPE_NAME:
				setTypeName((String)newValue);
				return;
			case ModelPackage.WIDGET__LIBRARY_NAME:
				setLibraryName((String)newValue);
				return;
			case ModelPackage.WIDGET__MODEL:
				setModel((Model)newValue);
				return;
			case ModelPackage.WIDGET__EVENTS:
				getEvents().clear();
				getEvents().addAll((Collection<? extends Event>)newValue);
				return;
			case ModelPackage.WIDGET__SNIPPETS:
				getSnippets().clear();
				getSnippets().addAll((Collection<? extends Snippet>)newValue);
				return;
			case ModelPackage.WIDGET__TOOL_TIPS:
				getToolTips().clear();
				getToolTips().addAll((Collection<? extends Translation>)newValue);
				return;
			case ModelPackage.WIDGET__LABELS:
				getLabels().clear();
				getLabels().addAll((Collection<? extends Translation>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.WIDGET__PROPERTIES:
				getProperties().clear();
				return;
			case ModelPackage.WIDGET__CONTENTS:
				getContents().clear();
				return;
			case ModelPackage.WIDGET__PARENT:
				setParent((Widget)null);
				return;
			case ModelPackage.WIDGET__TYPE_NAME:
				setTypeName(TYPE_NAME_EDEFAULT);
				return;
			case ModelPackage.WIDGET__LIBRARY_NAME:
				setLibraryName(LIBRARY_NAME_EDEFAULT);
				return;
			case ModelPackage.WIDGET__MODEL:
				setModel((Model)null);
				return;
			case ModelPackage.WIDGET__EVENTS:
				getEvents().clear();
				return;
			case ModelPackage.WIDGET__SNIPPETS:
				getSnippets().clear();
				return;
			case ModelPackage.WIDGET__TOOL_TIPS:
				getToolTips().clear();
				return;
			case ModelPackage.WIDGET__LABELS:
				getLabels().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.WIDGET__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case ModelPackage.WIDGET__CONTENTS:
				return contents != null && !contents.isEmpty();
			case ModelPackage.WIDGET__PARENT:
				return getParent() != null;
			case ModelPackage.WIDGET__TYPE_NAME:
				return TYPE_NAME_EDEFAULT == null ? typeName != null : !TYPE_NAME_EDEFAULT.equals(typeName);
			case ModelPackage.WIDGET__LIBRARY_NAME:
				return LIBRARY_NAME_EDEFAULT == null ? libraryName != null : !LIBRARY_NAME_EDEFAULT.equals(libraryName);
			case ModelPackage.WIDGET__MODEL:
				return getModel() != null;
			case ModelPackage.WIDGET__EVENTS:
				return events != null && !events.isEmpty();
			case ModelPackage.WIDGET__SNIPPETS:
				return snippets != null && !snippets.isEmpty();
			case ModelPackage.WIDGET__TOOL_TIPS:
				return toolTips != null && !toolTips.isEmpty();
			case ModelPackage.WIDGET__LABELS:
				return labels != null && !labels.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (typeName: ");
		result.append(typeName);
		result.append(", libraryName: ");
		result.append(libraryName);
		result.append(')');
		return result.toString();
	}

	/**
	 * Gets the Library containing this WidgetType.
	 * 
	 * @return Library The Library containing this WidgetType
	 */
	public WidgetLibrary getLibrary() {
		return getMetaModel().findWidgetLibrary(getLibraryName());
	}
	
	private WidgetType wType = null;

	/**
	 * Gets the WidgetType containing the meta-information about this Widget.
	 * 
	 * @return WidgetType The WidgetType containing the meta-information about this Widget
	 */
	public WidgetType getType() {
		if (wType == null) {
			wType = getMetaModel().findWidgetType(libraryName, typeName);
		}
		return wType;
	}

	/**
	 * Gets the Widgets of the specified type which are contained within this Widget. This method is generic and takes
	 * into account WidgetTypes which are children of the specified type.
	 * 
	 * @param type
	 *            The WidgetType to look for
	 * @param inspectInclude
	 * @return Unmodifiable List of Widgets which match the type
	 */
	public List<Widget> getWidgets(WidgetType type, boolean inspectInclude) {
		return WidgetUtils.filter(getContents(), type, inspectInclude);
	}

	private Map<String, Property> map;
	
	private Map<String, Property> getPropertyMap() {
		if (map != null) {
			if (map.size() != getProperties().size()) {
				map = null;
			}
		}
		if (map == null) {
			map = new HashMap<String, Property>();
			for (Property p : getProperties()) {
				map.put(p.getTypeName(), p);
			}
		}
		return map;
	}
	
	/**
	 * Finds the property given the name.
	 * 
	 * @param name
	 *            The name of the Property
	 * @return Property The Property or null if no Property can be found
	 */
	public Property findProperty(String name) {
    	return getPropertyMap().get(name);
	}
	
    /**
     * Finds the property given the type.
     * 
     * @param type
     *                The type of the Property
     * @return Property The Property or null if no Property can be found
     */
    public Property findProperty(PropertyType type) {
    	return getPropertyMap().get(type.getName());
    }

	/**
	 * Gets the first Widget in the Widget hierarhcy which has a Property with the specified name and value. The search
	 * starts with this Widget.
	 * 
	 * @param propertyName
	 *            The name to look for
	 * @param value
	 *            The value to look for
	 * @return The first Widget which has this PropertyType or null if no Widget has this role
	 */
	public Widget findWidgetIncludeParent(String propertyName, String value) {
		Widget w = this;
		while (w != null) {
			Property p = w.findProperty(propertyName);
			if (p != null) {
				if (ObjectUtils.equals(value, p.getValue())) {
					// We have found a parent with the specified role and value
					return w;
				}
			}

			w = w.getParent();
		}

		// Not found
		return null;
	}

	/**
	 * Gets the xmi:id of the model. This method returns null if the resource is NOT an XMLResource.
	 * 
	 * @return String The xmi:id of the Model
	 */
	public String getXmiId() {
		Resource r = eResource();
		if (r == null || !(r instanceof XMLResource)) {
			return null;
		}
		XMLResource xmlr = (XMLResource) r;
		return xmlr.getID(this);
	}

	/**
	 * Get the MetaModel.
	 * 
	 * @return MetaModel The MetaModel
	 */
	private static MetaModel getMetaModel() {
		return MetaModelRegistry.getMetaModel();
	}

	/**
	 * Returns true if the child can be moved left.
	 * 
	 * @return boolean
	 */
	public boolean canMoveLeftSelectedChild() {
		int index = getIndexOfSelectedChild();
		return (index > 0 && index <= (getContents().size() - 1));
	}

	/**
	 * Returns true if the child can be moved right.
	 * 
	 * @return boolean
	 */
	public boolean canMoveRightSelectedChild() {
		int index = getIndexOfSelectedChild();
		return (index >= 0 && index <= (getContents().size() - 2));
	}

	/**
	 * Returns true if the child can be deleted.
	 * 
	 * @return boolean
	 */
	public boolean canDeleteSelectedChild() {
		int index = getIndexOfSelectedChild();
		return (index >= 0 && index < (getContents().size() - 1))
				&& getContents().get(index).isDeletable();
	}

    /**
     * Sets the value of the Property.
     * 
     * @param propertyName The name of the property
     * @param value The value to set
     */
    public void setPropertyValue(String propertyName, String value) {
    	setPropertyValue(propertyName, value, false);
    }
    
    /**
     * Sets the value of the Property.
     * 
     * @param propertyName The name of the property
     * @param value The value to set
     * @param readonly if the property must be set read only
     */
    public void setPropertyValue(String propertyName, String value, boolean readonly) {
		Property p = findProperty(propertyName);
		if (p == null) {
			return;
		}
		p.setValue(value);
		if (readonly) {
			p.setReadonly(readonly);
		}
	}
    
	
    /**
     * Sets the Widget's properties. If a Property does not exist
     * it is ignored.
     * 
     * @param properties The properties to set
     * 		Key: Property Name
     * 		Value: Property Value
     */
    public void setPropertyValues(Map<String, String> properties) {
    	if (properties == null) {
    		return;
    	}
    	
    	for (Map.Entry<String, String> e : properties.entrySet()) {
    		String name = e.getKey();
    		Property p = findProperty(name);
    		if (p != null) {
    			String value = e.getValue();
    			p.setValue(value);
    		}
    	}
    }
    
    /**
     * Gets the root Widget. This is the root of the Widget hierarchy.
     * 
     * @return Widget The root Widget
     */
    public Widget getRootWidget() {
    	Widget w = this;
    	Widget p = w;
    	while (w != null) {
    		p = w;
    		w = w.getParent();
    	}
    	return p;
    }
    
    /**
     * Finds the first specified property in this Widget and its ancestor widgets. Returns null if the Property cannot
     * be found.
     * 
     * @param name The name of the Property
     * @return Property The Property or null if no Property could be found
     */
    public Property findPropertyInParent(String name) {
        Widget w = this;
        while (w != null) {
            Property p = w.findProperty(name);
            if (p != null) {
                return p;
            }

            w = w.getParent();
        }

        // Not found
        return null;
    } 
    
    /**
     * Finds the value of the first specified property in this Widget and its ancestor widgets. Returns null if the Property cannot
     * be found.
     * 
     * @param name The name of the Property
     * @return String The value of the Property or null if no Property could be found
     */
    public String findPropertyValueInParent(String name) {
        Property p = findPropertyInParent(name);
        if (p == null) {
            return null;
        }
        return p.getValue();
    }
    
    /**
     * Finds the MdfModelElement associated with this Widget.
     * 
     * @param repository The DomainRepository
     * @return MdfModelElement or null if no MdfModelElement is associated with this Widget
     */
    public MdfModelElement findDomainObject(DomainRepository repository) {
    	
    	MdfModelElement element = null;
    	
        // Find the parent Dataset
        Property pe = findPropertyInParent(PropertyTypeConstants.DOMAIN_ENTITY);
        String datasetName = pe != null ? pe.getValue() : ""; 
        if (StringUtils.isNotEmpty(datasetName)) {
        	
        	// retrieve the dataset
		MdfDataset dataset = repository.getDataset((MdfName)MdfNameFactory.createMdfName(datasetName));
        	if (dataset != null) {
  
		        // Find the Domain Attribute or Association
		        String da = getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		        if (StringUtils.isEmpty(da)) {
		            // Not an Attribute. Maybe it is an Association
		            da = getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
		        }
		        
		        if (StringUtils.isEmpty(da)) {
		        	// Neither an attribute nor an association.
		        	element = dataset;
		        } else {
		        	element = dataset.getProperty(da);
		        }
        	}
	
        }
        
        return element;
    }
    
    /**
     * Finds the MdfModelElement associated with this Widget.
     * 
     * @param repository The DomainRepository
     * @param name the name of a property
     * @return MdfModelElement or null if no MdfModelElement is associated with this Widget
     */
    public MdfModelElement findDomainObject(DomainRepository repository, String name) {
    	
    	MdfModelElement element = null;
	
        // Find the parent Dataset
        Property pe = findPropertyInParent(PropertyTypeConstants.DOMAIN_ENTITY);
        String datasetName = pe != null ? pe.getValue() : ""; 
        if (StringUtils.isNotEmpty(datasetName)) {
        	
        	// retrieve the dataset
        	MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));

        	// retrieve the property from the dataset
        	if (dataset != null && StringUtils.isNotEmpty(name)) {
	        	element = dataset.getProperty(name);
	        } else {
	        	element = dataset;
	        }
        }
        
        return element;
  
    }    
    
    /**
     * Find the dataset attribute bound to this widget, or null if not found
     * 
     * @param repository The DomainRepository
     * @param datasetName The name of the dataset
     * @param attribute name The name of an attribute within the dataset 
     * @return MdfModelElement or null if no dataset property is associated with this Widget
     */
    public MdfModelElement findDatasetProperty(DomainRepository repository, String datasetName, String attributeName) {
    	MdfModelElement element = null;
        if (StringUtils.isNotBlank(datasetName)) {
        	MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
        	if (dataset != null && StringUtils.isNotEmpty(attributeName)) {
	        	element = dataset.getProperty(attributeName);
        	}
        }
    	return element;
    }
    
    
    /**
     * @return <code>true</code> if the widget is bound to a domain element, 
     * otherwise it returns <code>false</false>.
     */
    public boolean isDomainWidget() {
        return StringUtils.isNotBlank(getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE));
    }

	/**
	 * @see com.odcgroup.page.model.Widget#getTranslationId()
	 * @generated NOT
	 */
	@Override
	public long getTranslationId() {
		long tid = 0;
		Property prop = findProperty("tid");
		if (prop != null) {
			tid = prop.getLongValue();
		}
		return tid;
	}

	/**
	 * @see com.odcgroup.page.model.Widget#setTranslationId(long)
	 * @generated NOT
	 */
	@Override
	public final void setTranslationId(long tid) {
		setPropertyValue("tid", tid+"");
	}
	
	@Override
	public final void setID(String newId) {
		String value = null != newId ? newId : "";
		setPropertyValue(PropertyTypeConstants.ID, value);
	}
	
	@Override
	public final String getID() {
		String id = getPropertyValue(PropertyTypeConstants.ID);
		return null != id ? id : ""; 
	}

	@Override
	public Widget findAncestor(String widgetType) {
		Widget tmp = this;
		while (tmp != null && ! tmp.getTypeName().equals(widgetType)) {
			tmp = tmp.getParent();
		}
		return tmp;
	}
    
} // WidgetImpl
