/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.EventNature;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.impl.EventImpl#getEventName <em>Event Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.EventImpl#getFunctionName <em>Function Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.EventImpl#getNature <em>Nature</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.EventImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.EventImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.EventImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.EventImpl#getSnippets <em>Snippets</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.EventImpl#getMessages <em>Messages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventImpl extends MinimalEObjectImpl.Container implements Event {
	/**
	 * The default value of the '{@link #getEventName() <em>Event Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventName()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getEventName() <em>Event Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventName()
	 * @generated
	 * @ordered
	 */
	protected String eventName = EVENT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFunctionName() <em>Function Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionName()
	 * @generated
	 * @ordered
	 */
	protected static final String FUNCTION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFunctionName() <em>Function Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionName()
	 * @generated
	 * @ordered
	 */
	protected String functionName = FUNCTION_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getNature() <em>Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNature()
	 * @generated
	 * @ordered
	 */
	protected static final EventNature NATURE_EDEFAULT = EventNature.ADVANCED;

	/**
	 * The cached value of the '{@link #getNature() <em>Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNature()
	 * @generated
	 * @ordered
	 */
	protected EventNature nature = NATURE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> properties;

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
	 * The cached value of the '{@link #getMessages() <em>Messages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessages()
	 * @generated
	 * @ordered
	 */
	protected EList<Translation> messages;
	
	
	/**
	 * 
	 */
	protected Widget parent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEventName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventName(String newEventName) {
		String oldEventName = eventName;
		eventName = newEventName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EVENT__EVENT_NAME, oldEventName, eventName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newFunctionName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionName(String newFunctionName) {
		String oldFunctionName = functionName;
		functionName = newFunctionName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EVENT__FUNCTION_NAME, oldFunctionName, functionName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, ModelPackage.EVENT__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<Property>(Property.class, this, ModelPackage.EVENT__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EVENT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Snippet> getSnippets() {
		if (snippets == null) {
			snippets = new EObjectContainmentEList<Snippet>(Snippet.class, this, ModelPackage.EVENT__SNIPPETS);
		}
		return snippets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Translation> getMessages() {
		if (messages == null) {
			messages = new EObjectContainmentEList<Translation>(Translation.class, this, ModelPackage.EVENT__MESSAGES);
		}
		return messages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventNature getNature() {
		return nature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNature(EventNature newNature) {
		EventNature oldNature = nature;
		nature = newNature == null ? NATURE_EDEFAULT : newNature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EVENT__NATURE, oldNature, nature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.EVENT__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case ModelPackage.EVENT__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case ModelPackage.EVENT__SNIPPETS:
				return ((InternalEList<?>)getSnippets()).basicRemove(otherEnd, msgs);
			case ModelPackage.EVENT__MESSAGES:
				return ((InternalEList<?>)getMessages()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.EVENT__EVENT_NAME:
				return getEventName();
			case ModelPackage.EVENT__FUNCTION_NAME:
				return getFunctionName();
			case ModelPackage.EVENT__NATURE:
				return getNature();
			case ModelPackage.EVENT__DESCRIPTION:
				return getDescription();
			case ModelPackage.EVENT__PARAMETERS:
				return getParameters();
			case ModelPackage.EVENT__PROPERTIES:
				return getProperties();
			case ModelPackage.EVENT__SNIPPETS:
				return getSnippets();
			case ModelPackage.EVENT__MESSAGES:
				return getMessages();
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
			case ModelPackage.EVENT__EVENT_NAME:
				setEventName((String)newValue);
				return;
			case ModelPackage.EVENT__FUNCTION_NAME:
				setFunctionName((String)newValue);
				return;
			case ModelPackage.EVENT__NATURE:
				setNature((EventNature)newValue);
				return;
			case ModelPackage.EVENT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ModelPackage.EVENT__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case ModelPackage.EVENT__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property>)newValue);
				return;
			case ModelPackage.EVENT__SNIPPETS:
				getSnippets().clear();
				getSnippets().addAll((Collection<? extends Snippet>)newValue);
				return;
			case ModelPackage.EVENT__MESSAGES:
				getMessages().clear();
				getMessages().addAll((Collection<? extends Translation>)newValue);
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
			case ModelPackage.EVENT__EVENT_NAME:
				setEventName(EVENT_NAME_EDEFAULT);
				return;
			case ModelPackage.EVENT__FUNCTION_NAME:
				setFunctionName(FUNCTION_NAME_EDEFAULT);
				return;
			case ModelPackage.EVENT__NATURE:
				setNature(NATURE_EDEFAULT);
				return;
			case ModelPackage.EVENT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ModelPackage.EVENT__PARAMETERS:
				getParameters().clear();
				return;
			case ModelPackage.EVENT__PROPERTIES:
				getProperties().clear();
				return;
			case ModelPackage.EVENT__SNIPPETS:
				getSnippets().clear();
				return;
			case ModelPackage.EVENT__MESSAGES:
				getMessages().clear();
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
			case ModelPackage.EVENT__EVENT_NAME:
				return EVENT_NAME_EDEFAULT == null ? eventName != null : !EVENT_NAME_EDEFAULT.equals(eventName);
			case ModelPackage.EVENT__FUNCTION_NAME:
				return FUNCTION_NAME_EDEFAULT == null ? functionName != null : !FUNCTION_NAME_EDEFAULT.equals(functionName);
			case ModelPackage.EVENT__NATURE:
				return nature != NATURE_EDEFAULT;
			case ModelPackage.EVENT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ModelPackage.EVENT__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case ModelPackage.EVENT__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case ModelPackage.EVENT__SNIPPETS:
				return snippets != null && !snippets.isEmpty();
			case ModelPackage.EVENT__MESSAGES:
				return messages != null && !messages.isEmpty();
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
		result.append(" (eventName: ");
		result.append(eventName);
		result.append(", functionName: ");
		result.append(functionName);
		result.append(", nature: ");
		result.append(nature);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}
	
	/**
	 * Gets the Widget that this Event is for.
	 * 
	 * @return Widget The Widget that this Event is for
	 */
	public Widget getWidget() {
		return (Widget) eContainer();
	}
	
    /**
     * Gets the type of the Event.
     * 
     * @return EventType
     */
    public EventType getEventType() {
        return MetaModelRegistry.findEventType(getEventName());
    }
    
    /**
     * Gets the type of the Function.
     * 
     * @return FunctionType
     */
    public FunctionType getFunctionType() {
        return MetaModelRegistry.findFunctionType(getFunctionName());
    }
    
    /**
     * Finds the Parameter. Returns null if the Parameter does not exist
     * 
     * @param name The name of the parameter
     * @return Parameter The Parameter or null if it does not exist
     */
    public Parameter findParameter(String name) {
        for (Parameter p : getParameters()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        
        // Not found
        return null;
    }
    
    /**
     * Finds the Property. Returns null if the Property does not exist
     * 
     * @param name The name of the parameter
     * @return Property The Property or null if it does not exist
     */
    public Property findProperty(String name) {
        for (Iterator it = getProperties().iterator(); it.hasNext();) {
            Property p = (Property) it.next();
            if (p.getTypeName().equals(name)) {
                return p;
            }
        }

        // Not found
        return null; 
    }

	/**
	 * @see com.odcgroup.page.model.Event#isAdvancedEvent()
	 */
	public boolean isAdvancedEvent() {
		return EventNature.ADVANCED.equals(getNature());
	}

	/**
	 * @see com.odcgroup.page.model.Event#isSimplifiedEvent()
	 */
	public boolean isSimplifiedEvent() {
		return EventNature.SIMPLIFIED.equals(getNature());
	}
	
	/**
	 * @see com.odcgroup.page.model.Event#getTranslationId()
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
	 * @see com.odcgroup.page.model.Event#setTranslationId(long)
	 * @generated NOT
	 */
	@Override
	public final void setTranslationId(long tid) {
		Property prop = findProperty("tid");
		if (prop != null) {
			prop.setValue(tid+"");
		}
	}

	@Override
	public Widget getParent() {
		return this.parent;
	}

	@Override
	public void setParent(Widget widget) {
		this.parent = widget;		
	}	

} //EventImpl
